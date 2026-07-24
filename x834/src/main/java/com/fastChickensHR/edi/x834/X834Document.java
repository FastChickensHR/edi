/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.X834MemberWriter;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an EDI 834 document for benefit enrollment and maintenance.
 * This class handles document structure, formatting, and validation.
 */
@Getter
public class X834Document {
    private final Header header;
    private final List<Member> members;
    private final List<Segment> additionalSegments;
    private final List<String> buildErrors;
    private final boolean isValid;
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
        this.buildErrors = builder.buildErrors;
        this.isValid = builder.isValid;
        this.trailerBuilder = builder.trailerBuilder;
    }

    /**
     * Checks if the document is valid
     *
     * @return true if the document is valid and can be generated
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Returns any errors encountered during document building
     *
     * @return List of error messages
     */
    public List<String> getErrors() {
        return buildErrors;
    }

    /**
     * Generates the complete EDI document string if the document is valid.
     * <p>
     * Segment counts (SE01, GE01, IEA01) are computed automatically from the
     * rendered document content and injected into the trailer before rendering.
     *
     * @return An Optional containing the formatted EDI 834 document as a string,
     * or empty if the document is invalid
     */
    public Optional<String> generateDocument() throws ValidationException {
        if (!isValid) {
            return Optional.empty();
        }

        // Collect all body segments before the trailer
        List<Segment> bodySegments = new ArrayList<>();

        if (header != null) {
            bodySegments.addAll(header.generateSegments());
        }

        X834MemberWriter memberWriter = new X834MemberWriter(context);
        for (Member member : members) {
            bodySegments.addAll(memberWriter.toSegments(member));
        }

        bodySegments.addAll(additionalSegments);

        // SE01 = count of segments from ST through SE inclusive.
        // ISA (index 0) and GS (index 1) are outside the ST/SE envelope; +1 for SE itself.
        int transactionSegmentCount = bodySegments.size() - 2 + 1;

        Trailer trailer = trailerBuilder
                .setNumberOfIncludedSegments(String.valueOf(transactionSegmentCount))
                .build();

        List<Segment> allSegments = new ArrayList<>(bodySegments);
        allSegments.addAll(trailer.generateSegments());

        // Guarantee delimiter safety over the exact segment list about to be rendered,
        // reading each segment's element values through the same accessor render() uses
        // so this pass cannot drift from what is emitted (#160).
        validateDelimiterSafety(allSegments);

        StringBuilder document = new StringBuilder();
        for (Segment segment : allSegments) {
            segment.setContext(context);
            document.append(segment.render());
        }

        return Optional.of(document.toString());
    }

    /**
     * Rejects generation if any element value contains a character X12 reserves as a
     * structural delimiter under the active {@link X834Context}.
     * <p>
     * X12 has no in-band escape mechanism (X12 RFI 2611), so a delimiter appearing inside
     * an element value can only silently corrupt the interchange — {@code render()} would
     * concatenate it verbatim, splitting one element or terminating a segment early. Rather
     * than emit a malformed 834, this pass collects <em>every</em> offending value across the
     * document and fails with one {@link ValidationException} naming each, so the source data
     * can be fixed in a single round-trip.
     * <p>
     * The pass inspects the same {@link Segment#getElementValues()} that {@code render()}
     * concatenates, so it cannot miss or over-report what will actually be emitted. Note: no
     * segment currently emits a composite (sub-element-delimited) element; if one is ever added,
     * this guard must be taught to allow the sub-element separator inside that composite value.
     *
     * @param segments the full ordered segment list about to be rendered
     * @throws ValidationException if one or more element values contain a reserved delimiter
     */
    private void validateDelimiterSafety(List<Segment> segments) throws ValidationException {
        Map<Character, String> reserved = new LinkedHashMap<>();
        reserved.put(context.getElementSeparator(), "element separator");
        reserved.put(context.getSegmentTerminator(), "segment terminator");
        reserved.put(context.getSubElementSeparator(), "sub-element separator");
        for (char terminatorChar : context.getLineTerminator().toCharArray()) {
            reserved.putIfAbsent(terminatorChar, "line terminator");
        }

        List<String> violations = new ArrayList<>();
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
                        violations.add(String.format(
                                "%s element %02d (\"%s\") contains the %s '%c' at position %d",
                                segment.getSegmentIdentifier(), i + 1, value,
                                entry.getValue(), entry.getKey(), position));
                    }
                }
            }
        }

        if (!violations.isEmpty()) {
            throw new ValidationException(
                    "Cannot generate 834: element values contain characters X12 reserves as "
                            + "delimiters, and X12 has no escape mechanism — fix the source data. "
                            + "Offending values:\n  - " + String.join("\n  - ", violations));
        }
    }

    /**
     * Builder class for constructing an X834Document
     */
    public static class Builder {
        private Header header;
        private Trailer.Builder trailerBuilder;
        private final List<Member> members = new ArrayList<>();
        private final List<Segment> additionalSegments = new ArrayList<>();
        private final List<String> buildErrors = new ArrayList<>();
        private boolean isValid = true;

        @Getter
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
         * Builds the final X834Document
         *
         * @return The configured X834Document
         */
        public X834Document build() {
            // Validate required components
            if (header == null) {
                buildErrors.add("Header is required");
                isValid = false;
            }

            if (trailerBuilder == null) {
                buildErrors.add("Trailer is required");
                isValid = false;
            }

            if (members.isEmpty()) {
                buildErrors.add("At least one member is required");
                isValid = false;
            }

            // Additional validation
            try {
                context.validate();

                if (header != null) {
                    header.validate();
                }

                if (trailerBuilder != null) {
                    trailerBuilder.build().validate();
                }

                for (Member member : members) {
                    member.validate();
                }
            } catch (ValidationException e) {
                buildErrors.add("Validation error: " + e.getMessage());
                isValid = false;
            }

            return new X834Document(this);
        }
    }
}
