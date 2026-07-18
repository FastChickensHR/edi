/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.loop1000A.SponsorName;
import com.fastChickensHR.edi.x834.loop1000B.Payer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the complete header section of an 834 file.
 * This includes Interchange Control Header, Functional Group Header, Transaction Set Header,
 * Beginning Segment, File Effective Date, Transaction Set Policy Number,
 * Sponsor, and Payer information.
 */
@Getter
@Setter
public class Header {
    private final X834Context context;

    // Default fields for simple configuration
    private String transactionSetIdentifierCode;
    private String referenceIdentification;
    private String masterPolicyNumber;
    private String planSponsorName;
    private String payerName;
    private String payerIdentification;

    // Custom builders to allow advanced customization
    private InterchangeControlHeader.Builder customInterchangeBuilder;
    private FunctionalGroupHeader.Builder customFunctionalBuilder;
    private TransactionSetHeader.Builder customTransactionSetBuilder;
    private BeginningSegment.Builder customBeginningSegmentBuilder;
    private FileEffectiveDate.Builder customFileEffectiveDateBuilder;
    private TransactionSetPolicyNumber.Builder customPolicyNumberBuilder;
    private SponsorName.Builder customSponsorBuilder;
    private Payer.Builder customPayerBuilder;

    /**
     * Creates a new Header instance with the specified context
     *
     * @param context The 834 context to use for this header
     */
    public Header(X834Context context) {
        this.context = context;
    }

    /**
     * Validates this header has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    public void validate() throws ValidationException {
        if (customTransactionSetBuilder == null && (transactionSetIdentifierCode == null || transactionSetIdentifierCode.isEmpty())) {
            throw new ValidationException("Transaction Set Identifier Code is required");
        }
    }

    /**
     * Generates all the segments for this header
     *
     * @return List of segments in the correct order
     */
    public List<Segment> generateSegments() throws ValidationException {
        validate();

        List<Segment> segments = new ArrayList<>();

        // Create or use builders as appropriate
        InterchangeControlHeader.Builder interchangeBuilder = customInterchangeBuilder != null ?
                customInterchangeBuilder :
                new InterchangeControlHeader.Builder(context);

        FunctionalGroupHeader.Builder functionalBuilder = customFunctionalBuilder != null ?
                customFunctionalBuilder :
                new FunctionalGroupHeader.Builder(context);

        TransactionSetHeader.Builder transactionSetBuilder = customTransactionSetBuilder != null ?
                customTransactionSetBuilder :
                new TransactionSetHeader.Builder(context)
                        .setTransactionSetIdentifierCode(transactionSetIdentifierCode);

        BeginningSegment.Builder beginningSegmentBuilder = customBeginningSegmentBuilder != null ?
                customBeginningSegmentBuilder :
                new BeginningSegment.Builder(context).setReferenceIdentification(referenceIdentification);

        FileEffectiveDate.Builder fileEffectiveDateBuilder = customFileEffectiveDateBuilder != null ?
                customFileEffectiveDateBuilder :
                new FileEffectiveDate.Builder(context);

        TransactionSetPolicyNumber.Builder policyNumberBuilder = customPolicyNumberBuilder != null ?
                customPolicyNumberBuilder :
                new TransactionSetPolicyNumber.Builder().setMasterPolicyNumber(masterPolicyNumber);

        SponsorName.Builder sponsorBuilder = customSponsorBuilder != null ?
                customSponsorBuilder :
                new SponsorName.Builder().setPlanSponsorName(planSponsorName);

        Payer.Builder payerBuilder = customPayerBuilder != null ?
                customPayerBuilder :
                createDefaultPayerBuilder();

        segments.add(interchangeBuilder.build());
        segments.add(functionalBuilder.build());
        segments.add(transactionSetBuilder.build());
        segments.add(beginningSegmentBuilder.build());
        segments.add(fileEffectiveDateBuilder.build());
        segments.add(policyNumberBuilder.build());
        segments.add(sponsorBuilder.build());
        segments.add(payerBuilder.build());

        return segments;
    }

    private Payer.Builder createDefaultPayerBuilder() {
        Payer.Builder builder = new Payer.Builder();
        if (payerName != null) {
            builder.setPlanSponsorName(payerName);
        }
        if (payerIdentification != null) {
            builder.setSponsorIdentifier(payerIdentification);
        }
        return builder;
    }

    /**
     * Builder for Header class
     */
    public static class Builder {
        /** Default transaction set identifier code for an 834 (ST01 / BGN transaction set). */
        private static final String DEFAULT_TRANSACTION_SET_IDENTIFIER_CODE = "834";

        private final X834Context context;

        private String transactionSetIdentifierCode = DEFAULT_TRANSACTION_SET_IDENTIFIER_CODE;
        private String referenceIdentification;
        private String masterPolicyNumber;
        private String planSponsorName;
        private String payerName;
        private String payerIdentification;

        // Custom builders
        private InterchangeControlHeader.Builder customInterchangeBuilder;
        private FunctionalGroupHeader.Builder customFunctionalBuilder;
        private TransactionSetHeader.Builder customTransactionSetBuilder;
        private BeginningSegment.Builder customBeginningSegmentBuilder;
        private FileEffectiveDate.Builder customFileEffectiveDateBuilder;
        private TransactionSetPolicyNumber.Builder customPolicyNumberBuilder;
        private SponsorName.Builder customSponsorBuilder;
        private Payer.Builder customPayerBuilder;

        /**
         * Creates a new Builder with the required context
         *
         * @param context The 834 context to use for this header
         */
        public Builder(X834Context context) {
            this.context = context;
        }

        // Simple setters
        /**
         * Sets the transaction set identifier code (ST01); defaults to "834".
         *
         * @param transactionSetIdentifierCode the transaction set identifier code
         * @return this builder instance
         */
        public Builder setTransactionSetIdentifierCode(String transactionSetIdentifierCode) {
            this.transactionSetIdentifierCode = transactionSetIdentifierCode;
            return this;
        }

        /**
         * Sets the reference identification used by the Beginning Segment (BGN02).
         *
         * @param referenceIdentification the reference identification
         * @return this builder instance
         */
        public Builder setReferenceIdentification(String referenceIdentification) {
            this.referenceIdentification = referenceIdentification;
            return this;
        }

        /**
         * Sets the master policy number (REF*38).
         *
         * @param masterPolicyNumber the master policy number
         * @return this builder instance
         */
        public Builder setMasterPolicyNumber(String masterPolicyNumber) {
            this.masterPolicyNumber = masterPolicyNumber;
            return this;
        }

        /**
         * Sets the plan sponsor name (Loop 1000A, N1*P5).
         *
         * @param planSponsorName the plan sponsor name
         * @return this builder instance
         */
        public Builder setPlanSponsorName(String planSponsorName) {
            this.planSponsorName = planSponsorName;
            return this;
        }

        /**
         * Sets the payer name (Loop 1000B, N1*IN).
         *
         * @param payerName the payer name
         * @return this builder instance
         */
        public Builder setPayerName(String payerName) {
            this.payerName = payerName;
            return this;
        }

        /**
         * Sets the payer identification (Loop 1000B, N104).
         *
         * @param payerIdentification the payer identification
         * @return this builder instance
         */
        public Builder setPayerIdentification(String payerIdentification) {
            this.payerIdentification = payerIdentification;
            return this;
        }

        // Custom builder setters
        /**
         * Supplies a custom Interchange Control Header (ISA) builder, overriding the default.
         *
         * @param builder the custom ISA builder
         * @return this builder instance
         */
        public Builder setInterchangeControlHeaderBuilder(InterchangeControlHeader.Builder builder) {
            this.customInterchangeBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Functional Group Header (GS) builder, overriding the default.
         *
         * @param builder the custom GS builder
         * @return this builder instance
         */
        public Builder setFunctionalGroupHeaderBuilder(FunctionalGroupHeader.Builder builder) {
            this.customFunctionalBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Transaction Set Header (ST) builder, overriding the default.
         *
         * @param builder the custom ST builder
         * @return this builder instance
         */
        public Builder setTransactionSetHeaderBuilder(TransactionSetHeader.Builder builder) {
            this.customTransactionSetBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Beginning Segment (BGN) builder, overriding the default.
         *
         * @param builder the custom BGN builder
         * @return this builder instance
         */
        public Builder setBeginningSegmentBuilder(BeginningSegment.Builder builder) {
            this.customBeginningSegmentBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom File Effective Date (DTP*007) builder, overriding the default.
         *
         * @param builder the custom DTP builder
         * @return this builder instance
         */
        public Builder setFileEffectiveDateBuilder(FileEffectiveDate.Builder builder) {
            this.customFileEffectiveDateBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Transaction Set Policy Number (REF*38) builder, overriding the default.
         *
         * @param builder the custom REF builder
         * @return this builder instance
         */
        public Builder setTransactionSetPolicyNumberBuilder(TransactionSetPolicyNumber.Builder builder) {
            this.customPolicyNumberBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Sponsor Name (Loop 1000A, N1*P5) builder, overriding the default.
         *
         * @param builder the custom sponsor builder
         * @return this builder instance
         */
        public Builder setSponsorNameBuilder(SponsorName.Builder builder) {
            this.customSponsorBuilder = builder;
            return this;
        }

        /**
         * Supplies a custom Payer (Loop 1000B, N1*IN) builder, overriding the default.
         *
         * @param builder the custom payer builder
         * @return this builder instance
         */
        public Builder setPayerBuilder(Payer.Builder builder) {
            this.customPayerBuilder = builder;
            return this;
        }

        /**
         * Builds a new Header instance
         *
         * @return The configured Header instance
         */
        public Header build() {
            Header header = new Header(context);

            header.transactionSetIdentifierCode = this.transactionSetIdentifierCode;
            header.referenceIdentification = this.referenceIdentification;
            header.masterPolicyNumber = this.masterPolicyNumber;
            header.planSponsorName = this.planSponsorName;
            header.payerName = this.payerName;
            header.payerIdentification = this.payerIdentification;

            header.customInterchangeBuilder = this.customInterchangeBuilder;
            header.customFunctionalBuilder = this.customFunctionalBuilder;
            header.customTransactionSetBuilder = this.customTransactionSetBuilder;
            header.customBeginningSegmentBuilder = this.customBeginningSegmentBuilder;
            header.customFileEffectiveDateBuilder = this.customFileEffectiveDateBuilder;
            header.customPolicyNumberBuilder = this.customPolicyNumberBuilder;
            header.customSponsorBuilder = this.customSponsorBuilder;
            header.customPayerBuilder = this.customPayerBuilder;

            return header;
        }
    }
}