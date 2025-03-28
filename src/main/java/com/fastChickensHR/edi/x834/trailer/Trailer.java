/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.common.segments.Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the complete trailer section of an 834 file.
 * This includes Transaction Set Trailer, Functional Group Trailer,
 * and Interchange Control Trailer.
 */
@Getter
public class Trailer {
    private final x834Context context;

    // Component trailers
    private final TransactionSetTrailer transactionSetTrailer;
    private final FunctionalGroupTrailer functionalGroupTrailer;
    private final InterchangeControlTrailer interchangeControlTrailer;

    /**
     * Private constructor used by the Builder class
     *
     * @param builder The builder containing configuration
     */
    private Trailer(Builder builder) throws ValidationException {
        this.context = builder.context;

        // Build the Transaction Set Trailer
        TransactionSetTrailer.Builder tsBuilder = builder.customTransactionSetBuilder;
        if (tsBuilder == null) {
            tsBuilder = TransactionSetTrailer.builder()
                    .setSetControlNumber(builder.transactionSetControlNumber)
                    .setTransactionSegmentCount(builder.numberOfIncludedSegments);
        }
        this.transactionSetTrailer = tsBuilder.build();

        // Build the Functional Group Trailer
        FunctionalGroupTrailer.Builder fgBuilder = builder.customFunctionalGroupBuilder;
        if (fgBuilder == null) {
            fgBuilder = FunctionalGroupTrailer.builder()
                    .setNumberOfTransactionSets(builder.numberOfTransactionSetsIncluded)
                    .setGroupControlNumber(builder.groupControlNumber);
        }
        this.functionalGroupTrailer = fgBuilder.build();

        // Build the Interchange Control Trailer
        InterchangeControlTrailer.Builder icBuilder = builder.customInterchangeControlBuilder;
        if (icBuilder == null) {
            icBuilder = InterchangeControlTrailer.builder()
                    .setNumberOfIncludedGroups(builder.numberOfIncludedFunctionalGroups)
                    .setInterchangeControlNumber(builder.interchangeControlNumber);
        }
        this.interchangeControlTrailer = icBuilder.build();
    }

    /**
     * Validates this trailer has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    public void validate() throws ValidationException {
        // Add any trailer-specific validation here
        if (transactionSetTrailer == null) {
            throw new ValidationException("Transaction Set Trailer is required");
        }

        if (functionalGroupTrailer == null) {
            throw new ValidationException("Functional Group Trailer is required");
        }

        if (interchangeControlTrailer == null) {
            throw new ValidationException("Interchange Control Trailer is required");
        }
    }

    /**
     * Generates all the segments for this trailer
     *
     * @return List of segments in the correct order
     */
    public List<Segment> generateSegments() throws ValidationException {
        validate();

        List<Segment> segments = new ArrayList<>();
        segments.add(transactionSetTrailer);
        segments.add(functionalGroupTrailer);
        segments.add(interchangeControlTrailer);

        return segments;
    }

    /**
     * Builder for the Trailer class
     */
    public static class Builder {
        @Getter
        private final x834Context context;

        private String transactionSetControlNumber = "0001";
        private String numberOfIncludedSegments = "10";
        private String numberOfTransactionSetsIncluded = "1";
        private String groupControlNumber = "1";
        private String numberOfIncludedFunctionalGroups = "1";
        private String interchangeControlNumber = "000000001";

        // Custom builders for advanced configuration
        private TransactionSetTrailer.Builder customTransactionSetBuilder;
        private FunctionalGroupTrailer.Builder customFunctionalGroupBuilder;
        private InterchangeControlTrailer.Builder customInterchangeControlBuilder;

        /**
         * Creates a new Builder with the specified context
         *
         * @param context The 834 context to use for this trailer
         * @throws IllegalArgumentException if context is null
         */
        public Builder(x834Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            this.context = context;
        }

        /**
         * Sets the transaction set control number.
         * This number must match the transaction set control number in the ST segment.
         *
         * @param transactionSetControlNumber The transaction set control number
         * @return This builder instance
         */
        public Builder setTransactionSetControlNumber(String transactionSetControlNumber) {
            this.transactionSetControlNumber = transactionSetControlNumber;
            return this;
        }

        /**
         * Sets the number of included segments.
         * This is the total count of segments in the transaction set, including the ST and SE segments.
         *
         * @param numberOfIncludedSegments The number of included segments
         * @return This builder instance
         */
        public Builder setNumberOfIncludedSegments(String numberOfIncludedSegments) {
            this.numberOfIncludedSegments = numberOfIncludedSegments;
            return this;
        }

        /**
         * Sets the number of transaction sets included.
         * This is the total count of transaction sets (ST/SE pairs) included in the functional group.
         *
         * @param numberOfTransactionSetsIncluded The number of transaction sets included
         * @return This builder instance
         */
        public Builder setNumberOfTransactionSetsIncluded(String numberOfTransactionSetsIncluded) {
            this.numberOfTransactionSetsIncluded = numberOfTransactionSetsIncluded;
            return this;
        }

        /**
         * Sets the group control number.
         * This number must match the group control number in the GS segment.
         *
         * @param groupControlNumber The group control number
         * @return This builder instance
         */
        public Builder setGroupControlNumber(String groupControlNumber) {
            this.groupControlNumber = groupControlNumber;
            return this;
        }

        /**
         * Sets the number of included functional groups.
         * This is the total count of functional groups (GS/GE pairs) included in the interchange.
         *
         * @param numberOfIncludedFunctionalGroups The number of included functional groups
         * @return This builder instance
         */
        public Builder setNumberOfIncludedFunctionalGroups(String numberOfIncludedFunctionalGroups) {
            this.numberOfIncludedFunctionalGroups = numberOfIncludedFunctionalGroups;
            return this;
        }

        /**
         * Sets the interchange control number.
         * This number must match the interchange control number in the ISA segment.
         *
         * @param interchangeControlNumber The interchange control number
         * @return This builder instance
         */
        public Builder setInterchangeControlNumber(String interchangeControlNumber) {
            this.interchangeControlNumber = interchangeControlNumber;
            return this;
        }

        /**
         * Sets a custom TransactionSetTrailer builder for advanced configuration.
         *
         * @param builder A custom transaction set trailer builder
         * @return This builder instance
         */
        public Builder withTransactionSetTrailer(TransactionSetTrailer.Builder builder) {
            this.customTransactionSetBuilder = builder;
            return this;
        }

        /**
         * Sets a custom FunctionalGroupTrailer builder for advanced configuration.
         *
         * @param builder A custom functional group trailer builder
         * @return This builder instance
         */
        public Builder withFunctionalGroupTrailer(FunctionalGroupTrailer.Builder builder) {
            this.customFunctionalGroupBuilder = builder;
            return this;
        }

        /**
         * Sets a custom InterchangeControlTrailer builder for advanced configuration.
         *
         * @param builder A custom interchange control trailer builder
         * @return This builder instance
         */
        public Builder withInterchangeControlTrailer(InterchangeControlTrailer.Builder builder) {
            this.customInterchangeControlBuilder = builder;
            return this;
        }

        /**
         * Builds the final Trailer object
         *
         * @return The configured Trailer
         * @throws ValidationException If validation fails
         */
        public Trailer build() throws ValidationException {
            return new Trailer(this);
        }
    }
}