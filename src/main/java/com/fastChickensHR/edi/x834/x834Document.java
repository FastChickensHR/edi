/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.common.segments.Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents an EDI 834 document for benefit enrollment and maintenance.
 * This class handles document structure, formatting, and validation.
 */
@Getter
public class x834Document {
    private final Header header;
    private final List<Member> members;
    private final List<Segment> additionalSegments;
    private final List<String> buildErrors;
    private final boolean isValid;
    private final x834Context context;
    private final Trailer trailer;

    /**
     * Private constructor used by the Builder class
     *
     * @param builder The builder containing configuration
     */
    private x834Document(Builder builder) {
        this.context = builder.context;
        this.header = builder.header;
        this.members = builder.members;
        this.additionalSegments = builder.additionalSegments;
        this.buildErrors = builder.buildErrors;
        this.isValid = builder.isValid;
        this.trailer = builder.trailer;
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
     * Generates the complete EDI document string if the document is valid
     *
     * @return An Optional containing the formatted EDI 834 document as a string,
     * or empty if the document is invalid
     */
    public Optional<String> generateDocument() throws ValidationException {
        if (!isValid) {
            return Optional.empty();
        }

        StringBuilder document = new StringBuilder();

        if (header != null) {
            List<Segment> headerSegments = header.generateSegments();
            for (Segment segment : headerSegments) {
                segment.setContext(context);
                document.append(segment.render());
            }
        }

        for (Member member : members) {
            List<Segment> memberSegments = member.generateSegments();
            for (Segment segment : memberSegments) {
                segment.setContext(context);
                document.append(segment.render());
            }
        }

        for (Segment segment : additionalSegments) {
            segment.setContext(context);
            document.append(segment.render());
        }

        if (trailer != null) {
            List<Segment> trailerSegments = trailer.generateSegments();
            for (Segment segment : trailerSegments) {
                segment.setContext(context);
                document.append(segment.render());
            }
        }

        return Optional.of(document.toString());
    }

    /**
     * Builder class for constructing an x834Document
     */
    public static class Builder {
        private Header header;
        private Trailer trailer;
        private final List<Member> members = new ArrayList<>();
        private final List<Segment> additionalSegments = new ArrayList<>();
        private final List<String> buildErrors = new ArrayList<>();
        private boolean isValid = true;

        @Getter
        private final x834Context context;

        /**
         * Creates a new Builder with the specified context.
         * Context is required to ensure proper document generation.
         *
         * @param context The context to use for this document and its components
         * @throws IllegalArgumentException if context is null
         */
        public Builder(x834Context context) {
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
         * Sets the trailer for this document
         *
         * @param trailer The completely configured trailer
         * @return This builder instance
         */
        public Builder withTrailer(Trailer trailer) {
            this.trailer = trailer;
            return this;
        }

        /**
         * Convenience method to set the trailer using a trailer builder
         *
         * @param trailerBuilder The trailer builder to use
         * @return This builder instance
         */
        public Builder withTrailer(Trailer.Builder trailerBuilder) {
            try {
                this.trailer = trailerBuilder.build();
            } catch (ValidationException e) {
                buildErrors.add("Trailer validation error: " + e.getMessage());
                isValid = false;
            }
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
         * Builds the final x834Document
         *
         * @return The configured x834Document
         */
        public x834Document build() {
            // Validate required components
            if (header == null) {
                buildErrors.add("Header is required");
                isValid = false;
            }

            if (trailer == null) {
                buildErrors.add("Trailer is required");
                isValid = false;
            }

            if (members.isEmpty()) {
                buildErrors.add("At least one member is required");
                isValid = false;
            }

            // Additional validation
            try {
                if (header != null) {
                    header.validate();
                }

                if (trailer != null) {
                    trailer.validate();
                }

                for (Member member : members) {
                    member.validate();
                }
            } catch (ValidationException e) {
                buildErrors.add("Validation error: " + e.getMessage());
                isValid = false;
            }

            return new x834Document(this);
        }
    }
}