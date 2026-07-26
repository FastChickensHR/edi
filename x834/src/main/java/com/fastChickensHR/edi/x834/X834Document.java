/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.GenerationError.Phase;
import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.spec.CharacterClass;
import com.fastChickensHR.edi.x834.spec.ElementSpec;
import com.fastChickensHR.edi.x834.spec.X834Spec;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.X834MemberWriter;
import com.fastChickensHR.edi.x834.trailer.Trailer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an EDI 834 document for benefit enrollment and maintenance.
 *
 * <p>Generation reports through exactly one channel: {@link #generateDocument()} returns a
 * {@link GenerationResult} — {@link GenerationResult.Success} carrying the document, or
 * {@link GenerationResult.Failure} carrying <em>every</em> reason it could not be produced. There is
 * no separate {@code isValid()}/{@code getErrors()} accessor and no thrown exception: build-time
 * (structure/configuration) and render-time (serialization) problems all surface as
 * {@link GenerationError}s in the {@code Failure}.
 */
public class X834Document {
    private final Header header;
    private final List<Member> members;
    private final List<Segment> additionalSegments;
    private final List<GenerationError> buildErrors;
    private final X834Context context;
    private final Trailer.Builder trailerBuilder;

    /**
     * Private constructor used by the Builder class
     *
     * @param builder The builder containing configuration
     */
    private X834Document(Builder builder) {
        this.context = builder.context;
        this.header = builder.header;
        this.members = builder.members;
        this.additionalSegments = builder.additionalSegments;
        this.buildErrors = List.copyOf(builder.buildErrors);
        this.trailerBuilder = builder.trailerBuilder;
    }

    /**
     * Generates the complete EDI 834 document, or reports why it could not be produced.
     * <p>
     * Segment counts (SE01, GE01, IEA01) are computed automatically from the rendered document
     * content and injected into the trailer before rendering.
     * <p>
     * Problems are <em>accumulated, not thrown</em>: build-time structural/configuration failures
     * (surfaced by {@link Builder#build()}) short-circuit rendering — a document whose structure
     * never validated cannot be serialized to surface further problems — while within rendering,
     * each member's assembly and each delimiter-unsafe value is collected independently so a single
     * call reports every render-time problem at once.
     *
     * @return a {@link GenerationResult.Success} with the formatted 834, or a
     * {@link GenerationResult.Failure} listing every {@link GenerationError}
     */
    public GenerationResult generateDocument() {
        // Build-time (structure/config) problems short-circuit render: a document whose structure
        // never validated cannot be serialized to surface further problems.
        if (!buildErrors.isEmpty()) {
            return new GenerationResult.Failure(buildErrors);
        }

        List<GenerationError> errors = new ArrayList<>();

        // Assemble the ordered segment list, collecting — never throwing on — each failure so one
        // pass reports every problem. Per-member assembly accumulates independently, so a bad
        // Member[3] and a bad Member[7] both surface from a single generateDocument() call.
        List<Segment> bodySegments = new ArrayList<>();
        if (header != null) {
            try {
                bodySegments.addAll(header.generateSegments());
            } catch (ValidationException e) {
                errors.add(new GenerationError(Phase.RENDER, "Header", e.getMessage()));
            }
        }

        X834MemberWriter memberWriter = new X834MemberWriter(context);
        for (int i = 0; i < members.size(); i++) {
            try {
                bodySegments.addAll(memberWriter.toSegments(members.get(i)));
            } catch (ValidationException e) {
                errors.add(new GenerationError(Phase.RENDER, "Member[" + i + "]", e.getMessage()));
            }
        }

        bodySegments.addAll(additionalSegments);

        // SE01 = count of segments from ST through SE inclusive.
        // ISA (index 0) and GS (index 1) are outside the ST/SE envelope; +1 for SE itself.
        int transactionSegmentCount = bodySegments.size() - 2 + 1;

        List<Segment> allSegments = new ArrayList<>(bodySegments);
        try {
            Trailer trailer = trailerBuilder
                    .setNumberOfIncludedSegments(String.valueOf(transactionSegmentCount))
                    .build();
            allSegments.addAll(trailer.generateSegments());
        } catch (ValidationException e) {
            errors.add(new GenerationError(Phase.RENDER, "Trailer", e.getMessage()));
        }

        // Guarantee delimiter safety over the exact segment list about to be rendered, reading each
        // segment's element values through the same accessor render() uses so this pass cannot drift
        // from what is emitted (#160). Each offending value is collected as its own error.
        errors.addAll(delimiterViolations(allSegments));

        // Backstop the same segment list against what the standard says about each position it fills:
        // the interchange's character set, the element's length bounds, and — where the position is
        // coded — membership of its code list (#188).
        errors.addAll(specViolations(allSegments));

        if (!errors.isEmpty()) {
            return new GenerationResult.Failure(errors);
        }

        StringBuilder document = new StringBuilder();
        for (Segment segment : allSegments) {
            segment.setContext(context);
            document.append(segment.render());
        }

        return new GenerationResult.Success(document.toString());
    }

    /**
     * Collects a {@link GenerationError} for every element value that contains a character X12
     * reserves as a structural delimiter under the active {@link X834Context}.
     * <p>
     * X12 has no in-band escape mechanism (X12 RFI 2611), so a delimiter appearing inside an element
     * value can only silently corrupt the interchange — {@code render()} would concatenate it
     * verbatim, splitting one element or terminating a segment early. Rather than emit a malformed
     * 834, every offending value across the document is reported (each as its own error, located by
     * segment identifier) so the source data can be fixed in a single round-trip.
     * <p>
     * The pass inspects the same {@link Segment#getElementValues()} that {@code render()} concatenates,
     * so it cannot miss or over-report what will actually be emitted. Note: no segment currently emits
     * a composite (sub-element-delimited) element; if one is ever added, this guard must be taught to
     * allow the sub-element separator inside that composite value.
     *
     * @param segments the full ordered segment list about to be rendered
     * @return one {@link GenerationError} per offending element value, empty if all values are safe
     */
    private List<GenerationError> delimiterViolations(List<Segment> segments) {
        Map<Character, String> reserved = new LinkedHashMap<>();
        reserved.put(context.getElementSeparator(), "element separator");
        reserved.put(context.getSegmentTerminator(), "segment terminator");
        reserved.put(context.getSubElementSeparator(), "sub-element separator");
        for (char terminatorChar : context.getLineTerminator().toCharArray()) {
            reserved.putIfAbsent(terminatorChar, "line terminator");
        }

        List<GenerationError> violations = new ArrayList<>();
        for (Segment segment : segments) {
            String[] values = segment.getElementValues();
            if (values == null) {
                continue;
            }
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value == null) {
                    continue;
                }
                for (Map.Entry<Character, String> entry : reserved.entrySet()) {
                    int position = value.indexOf(entry.getKey());
                    if (position >= 0) {
                        violations.add(new GenerationError(Phase.RENDER, segment.getSegmentIdentifier(),
                                String.format("element %02d (\"%s\") contains the %s '%c' at position %d",
                                        i + 1, value, entry.getValue(), entry.getKey(), position)));
                    }
                }
            }
        }
        return violations;
    }

    /**
     * Collects a {@link GenerationError} for every element value that contradicts what
     * {@link X834Spec} publishes about the position it is being written into.
     * <p>
     * Three checks, all driven from the published {@link ElementSpec} rather than hand-written per
     * segment, so a position is enforced because the standard describes it and not because its builder
     * happened to take an enum:
     * <ul>
     *   <li><strong>Character set</strong> — every value must draw from the interchange's
     *       {@link X834Context#getCharacterClass() character set}. Applies to every value, including the
     *       positions this library publishes no spec for, since the set is the partner's agreement about
     *       the whole interchange.</li>
     *   <li><strong>Length</strong> — the element's own bounds. A value that has been padded to width
     *       (the fixed-width ISA elements) satisfies them by construction; one that overruns is reported
     *       rather than silently truncated at render.</li>
     *   <li><strong>Code membership</strong> — for a coded position, strictly, via
     *       {@link ElementSpec#permits}. This is the only enforcement the raw-string positions
     *       (DMG03, HD03, HD05, NM101) have ever had.</li>
     * </ul>
     * <p>
     * This is a backstop, not a front line: a caller that pre-checks its data per member and holds the
     * bad record never reaches here. What it guarantees is that a non-conformant 834 is never emitted
     * silently — the last possible moment to notice, which is exactly where a guarantee belongs.
     * <p>
     * A position the library publishes no spec for is character-set checked and otherwise skipped; see
     * {@link X834Spec} for what is unpublished and why.
     *
     * @param segments the full ordered segment list about to be rendered
     * @return one {@link GenerationError} per offending element value, empty if every value conforms
     */
    private List<GenerationError> specViolations(List<Segment> segments) {
        CharacterClass characterClass = context.getCharacterClass();
        List<GenerationError> violations = new ArrayList<>();
        for (Segment segment : segments) {
            String[] values = segment.getElementValues();
            if (values == null) {
                continue;
            }
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value == null || value.isEmpty()) {
                    continue;
                }
                int ordinal = i + 1;
                characterClass.firstViolation(value).ifPresent(position -> violations.add(error(segment, ordinal, value,
                        "contains '%c' at position %d, which the %s character set does not permit"
                                .formatted(value.charAt(position), position, characterClass))));

                Optional<ElementSpec> published = X834Spec.atSegment(segment.getSegmentIdentifier(), ordinal);
                if (published.isEmpty()) {
                    continue;
                }
                ElementSpec spec = published.get();
                if (value.length() < spec.minLength() || value.length() > spec.maxLength()) {
                    violations.add(error(segment, ordinal, value,
                            "is %d characters, outside element %s's %d/%d"
                                    .formatted(value.length(), spec.elementId(), spec.minLength(), spec.maxLength())));
                }
                if (spec.isCoded() && !spec.permits(List.of(value)).ok()) {
                    violations.add(error(segment, ordinal, value,
                            "is not a code element %s permits".formatted(spec.elementId())));
                }
            }
        }
        return violations;
    }

    /** One violation, located by segment and element the way the delimiter pass locates its own. */
    private static GenerationError error(Segment segment, int ordinal, String value, String problem) {
        return new GenerationError(Phase.RENDER, segment.getSegmentIdentifier(),
                "element %02d (\"%s\") %s".formatted(ordinal, value, problem));
    }

    /**
     * Builder class for constructing an X834Document
     */
    public static class Builder {
        private Header header;
        private Trailer.Builder trailerBuilder;
        private final List<Member> members = new ArrayList<>();
        private final List<Segment> additionalSegments = new ArrayList<>();
        private final List<GenerationError> buildErrors = new ArrayList<>();

        private final X834Context context;

        /**
         * Creates a new Builder with the specified context.
         * Context is required to ensure proper document generation.
         *
         * @param context The context to use for this document and its components
         * @throws IllegalArgumentException if context is null
         */
        public Builder(X834Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            this.context = context;
        }

        /**
         * Sets the header for this document
         *
         * @param header The completely configured header
         * @return This builder instance
         */
        public Builder withHeader(Header header) {
            this.header = header;
            return this;
        }

        /**
         * Convenience method to set the header using a header builder
         *
         * @param headerBuilder The header builder to use
         * @return This builder instance
         */
        public Builder withHeader(Header.Builder headerBuilder) {
            this.header = headerBuilder.build();
            return this;
        }

        /**
         * Sets the trailer builder for this document. Segment counts (SE01, GE01, IEA01)
         * are computed automatically at render time and do not need to be set manually.
         *
         * @param trailerBuilder The trailer builder to use
         * @return This builder instance
         */
        public Builder withTrailer(Trailer.Builder trailerBuilder) {
            this.trailerBuilder = trailerBuilder;
            return this;
        }

        /**
         * Adds a member to the document
         *
         * @param member The member to add
         * @return This builder instance
         */
        public Builder addMember(Member member) {
            members.add(member);
            return this;
        }

        /**
         * Adds multiple members to the document
         *
         * @param members The list of members to add
         * @return This builder instance
         */
        public Builder withMembers(List<Member> members) {
            this.members.addAll(members);
            return this;
        }

        /**
         * Adds an additional segment to the document
         *
         * @param segment The segment to add
         * @return This builder instance
         */
        public Builder addSegment(Segment segment) {
            additionalSegments.add(segment);
            return this;
        }

        /**
         * Builds the final X834Document, capturing every build-time (structure/configuration)
         * problem as a {@link Phase#BUILD} {@link GenerationError}. Each component validates
         * independently so a single {@code build()} surfaces every problem, not just the first to
         * fail; the errors are then reported through {@link X834Document#generateDocument()}.
         *
         * @return The configured X834Document
         */
        public X834Document build() {
            if (header == null) {
                buildErrors.add(new GenerationError(Phase.BUILD, "Header", "Header is required"));
            }
            if (trailerBuilder == null) {
                buildErrors.add(new GenerationError(Phase.BUILD, "Trailer", "Trailer is required"));
            }
            if (members.isEmpty()) {
                buildErrors.add(new GenerationError(Phase.BUILD, "Members", "At least one member is required"));
            }

            // Each component validates independently so a single build() surfaces every
            // configuration problem, not just the first to throw.
            validate("Context", context::validate);
            if (header != null) {
                validate("Header", header::validate);
            }
            if (trailerBuilder != null) {
                validate("Trailer", () -> trailerBuilder.build().validate());
            }
            for (int i = 0; i < members.size(); i++) {
                validate("Member[" + i + "]", members.get(i)::validate);
            }

            return new X834Document(this);
        }

        /** Run one component's validation, recording any failure as a build-phase error. */
        private void validate(String location, ValidatingStep step) {
            try {
                step.run();
            } catch (ValidationException e) {
                buildErrors.add(new GenerationError(Phase.BUILD, location, e.getMessage()));
            }
        }

        /** A validation call that may fail with a {@link ValidationException}. */
        @FunctionalInterface
        private interface ValidatingStep {
            void run() throws ValidationException;
        }
    }
}
