/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
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

    /**
     * Private constructor used by the Builder class
     *
     * @param builder The builder containing configuration
     */
    private x834Document(Builder builder) {
        this.context = builder.context;
        this.header = builder.header;
        this.members = new ArrayList<>(builder.members);
        this.additionalSegments = new ArrayList<>(builder.additionalSegments);
        this.buildErrors = new ArrayList<>(builder.buildErrors);
        this.isValid = builder.isValid;
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
        return new ArrayList<>(buildErrors);
    }

    /**
     * Generates the complete EDI document string if the document is valid
     *
     * @return An Optional containing the formatted EDI 834 document as a string,
     * or empty if the document is invalid
     */
    public Optional<String> generateDocument() throws ValidationException {
        if (!isValid()) {
            return Optional.empty();
        }

        // Generate all segments
        List<Segment> allSegments = new ArrayList<>();

        // Add header segments
        allSegments.addAll(header.generateSegments());

        // Add member segments
        for (Member member : members) {
            allSegments.addAll(member.generateSegments());
        }

        // Add any additional segments
        allSegments.addAll(additionalSegments);

        // Format segments into EDI document
        StringBuilder documentBuilder = new StringBuilder();
        for (Segment segment : allSegments) {
            segment.setContext( context);
            documentBuilder.append(segment.render());
        }

        return Optional.of(documentBuilder.toString());
    }

    /**
     * Builder class for constructing an x834Document
     */
    public static class Builder {
        private Header header;
        private final List<Member> members = new ArrayList<>();
        private final List<Segment> additionalSegments = new ArrayList<>();
        private final List<String> buildErrors = new ArrayList<>();
        private boolean isValid = true;
        /**
         * -- GETTER --
         *  Gets the context associated with this builder.
         *  Useful for creating components that need to share the same context.
         *
         * @return The context for this document
         */
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
            try {
                this.header = headerBuilder.build();
            } catch (Exception e) {
                recordError("Failed to build Header: " + e.getMessage());
            }
            return this;
        }

        /**
         * Creates and sets a header with default configuration using the document's context
         *
         * @return This builder instance
         */
        public Builder withDefaultHeader() {
            try {
                this.header = new Header.Builder(this.context).build();
            } catch (Exception e) {
                recordError("Failed to build default Header: " + e.getMessage());
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
            this.members.add(member);
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
            this.additionalSegments.add(segment);
            return this;
        }

        /**
         * Adds multiple additional segments to the document
         *
         * @param segments The list of segments to add
         * @return This builder instance
         */
        public Builder withAdditionalSegments(List<Segment> segments) {
            this.additionalSegments.addAll(segments);
            return this;
        }

        /**
         * Records an error during the build process
         *
         * @param error The error message
         */
        private void recordError(String error) {
            buildErrors.add(error);
            isValid = false;
        }

        /**
         * Validates that all required components are present
         */
        private void validate() {
            if (header == null) {
                recordError("Header is required");
            }

            if (members.isEmpty()) {
                recordError("At least one Member is required");
            }
        }

        /**
         * Builds the final x834Document
         *
         * @return The configured x834Document
         */
        public x834Document build() {
            validate();
            return new x834Document(this);
        }
    }
}