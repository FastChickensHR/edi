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
import com.fastChickensHR.edi.x834.header.*;
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
    private final InterchangeControlHeader interchangeControlHeader;
    private final FunctionalGroupHeader functionalGroupHeader;
    private final TransactionSetHeader transactionSetHeader;
    private final BeginningSegment beginningSegment;
    private final FileEffectiveDate fileEffectiveDate;
    private final TransactionSetPolicyNumber transactionSetPolicyNumber;

    // List to maintain segment order
    private final List<Segment> segments = new ArrayList<>();

    // For tracking errors
    private final List<String> buildErrors = new ArrayList<>();
    private boolean isValid = true;

    private x834Document(Builder builder) {
        this.interchangeControlHeader = builder.interchangeControlHeader;
        this.functionalGroupHeader = builder.functionalGroupHeader;
        this.transactionSetHeader = builder.transactionSetHeader;
        this.beginningSegment = builder.beginningSegment;
        this.fileEffectiveDate = builder.fileEffectiveDate;
        this.transactionSetPolicyNumber = builder.transactionSetPolicyNumber;
        this.buildErrors.addAll(builder.buildErrors);
        this.isValid = builder.isValid;

        // Add header segments in the correct order
        if (interchangeControlHeader != null) segments.add(interchangeControlHeader);
        if (functionalGroupHeader != null) segments.add(functionalGroupHeader);
        if (transactionSetHeader != null) segments.add(transactionSetHeader);
        if (beginningSegment != null) segments.add(beginningSegment);
        if (fileEffectiveDate != null) segments.add(fileEffectiveDate);
        if (transactionSetPolicyNumber != null) segments.add(transactionSetPolicyNumber);

        // Add other segments
        segments.addAll(builder.additionalSegments);

        if (isValid) {
            // Set context for all segments
            x834Context context = new x834Context();
            for (Segment segment : segments) {
                segment.setContext(context);
            }
        }
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
     *         or empty if the document is invalid
     */
    public Optional<String> generateDocument() {
        if (!isValid) {
            return Optional.empty();
        }

        StringBuilder document = new StringBuilder();
        for (Segment segment : segments) {
            document.append(segment.render());
        }

        return Optional.of(document.toString());
    }

    public static class Builder {
        // Required header components
        private InterchangeControlHeader interchangeControlHeader;
        private FunctionalGroupHeader functionalGroupHeader;
        private TransactionSetHeader transactionSetHeader;
        private BeginningSegment beginningSegment;
        private FileEffectiveDate fileEffectiveDate;
        private TransactionSetPolicyNumber transactionSetPolicyNumber;

        // Other segments
        private final List<Segment> additionalSegments = new ArrayList<>();

        // For tracking which builders have been provided
        private InterchangeControlHeader.Builder interchangeBuilder;
        private FunctionalGroupHeader.Builder functionalGroupBuilder;
        private TransactionSetHeader.Builder transactionSetBuilder;
        private BeginningSegment.Builder beginningSegmentBuilder;
        private FileEffectiveDate.Builder fileEffectiveDateBuilder;
        private TransactionSetPolicyNumber.Builder policyNumberBuilder;

        // For tracking errors
        private final List<String> buildErrors = new ArrayList<>();
        private boolean isValid = true;

        // Shared context for all builders
        private final x834Context context = new x834Context();

        public Builder() {
        }

        /**
         * Provide the InterchangeControlHeader builder
         */
        public Builder withInterchangeControlHeader(InterchangeControlHeader.Builder builder) {
            this.interchangeBuilder = builder;
            return this;
        }

        /**
         * Provide the FunctionalGroupHeader builder
         */
        public Builder withFunctionalGroupHeader(FunctionalGroupHeader.Builder builder) {
            this.functionalGroupBuilder = builder;
            return this;
        }

        /**
         * Provide the TransactionSetHeader builder
         */
        public Builder withTransactionSetHeader(TransactionSetHeader.Builder builder) {
            this.transactionSetBuilder = builder;
            return this;
        }

        /**
         * Provide the BeginningSegment builder
         */
        public Builder withBeginningSegment(BeginningSegment.Builder builder) {
            this.beginningSegmentBuilder = builder;
            return this;
        }

        /**
         * Provide the FileEffectiveDate builder
         */
        public Builder withFileEffectiveDate(FileEffectiveDate.Builder builder) {
            this.fileEffectiveDateBuilder = builder;
            return this;
        }

        /**
         * Provide the TransactionSetPolicyNumber builder
         */
        public Builder withTransactionSetPolicyNumber(TransactionSetPolicyNumber.Builder builder) {
            this.policyNumberBuilder = builder;
            return this;
        }

        /**
         * Add additional segments to the document
         */
        public Builder addSegment(Segment segment) {
            this.additionalSegments.add(segment);
            return this;
        }

        /**
         * Build the complete x834Document with all required headers.
         * Will not throw exceptions for validation errors but will return a document
         * that reports its validity status and any errors.
         *
         * @return An x834Document that may or may not be valid
         */
        public x834Document build() {
            validateBuilders();
            if (isValid) {
                assembleComponents();
            }
            return new x834Document(this);
        }

        /**
         * Validates that all required builders have been provided
         */
        private void validateBuilders() {
            if (interchangeBuilder == null) {
                buildErrors.add("InterchangeControlHeader builder is required");
                isValid = false;
            }
            if (functionalGroupBuilder == null) {
                buildErrors.add("FunctionalGroupHeader builder is required");
                isValid = false;
            }
            if (transactionSetBuilder == null) {
                buildErrors.add("TransactionSetHeader builder is required");
                isValid = false;
            }
            if (beginningSegmentBuilder == null) {
                buildErrors.add("BeginningSegment builder is required");
                isValid = false;
            }
            if (fileEffectiveDateBuilder == null) {
                buildErrors.add("FileEffectiveDate builder is required");
                isValid = false;
            }
            if (policyNumberBuilder == null) {
                buildErrors.add("TransactionSetPolicyNumber builder is required");
                isValid = false;
            }
        }

        /**
         * Builds all components in the correct order using their respective builders
         */
        private void assembleComponents() {
            try {
                // Build all components in the correct order
                interchangeControlHeader = interchangeBuilder.build();
                functionalGroupHeader = functionalGroupBuilder.build();
                transactionSetHeader = transactionSetBuilder.build();
                beginningSegment = beginningSegmentBuilder.build();
                fileEffectiveDate = fileEffectiveDateBuilder.build();
                transactionSetPolicyNumber = policyNumberBuilder.build();
            } catch (ValidationException e) {
                buildErrors.add("Validation error: " + e.getMessage());
                isValid = false;
            } catch (Exception e) {
                buildErrors.add("Unexpected error: " + e.getMessage());
                isValid = false;
            }
        }
    }
}