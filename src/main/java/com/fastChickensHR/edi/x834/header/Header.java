/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.segments.Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
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
    private final x834Context context;

    // Default fields for simple configuration
    private String interchangeControlNumber;
    private String groupControlNumber;
    private String transactionSetIdentifierCode;
    private String transactionSetControlNumber;
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
    public Header(x834Context context) {
        this.context = context;
    }

    /**
     * Validates this header has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    public void validate() throws ValidationException {
        // Only validate basic fields if custom builders aren't set
        if (customInterchangeBuilder == null && (interchangeControlNumber == null || interchangeControlNumber.isEmpty())) {
            throw new ValidationException("Interchange Control Number is required");
        }
        if (customFunctionalBuilder == null && (groupControlNumber == null || groupControlNumber.isEmpty())) {
            throw new ValidationException("Group Control Number is required");
        }
        if (customTransactionSetBuilder == null && (transactionSetIdentifierCode == null || transactionSetIdentifierCode.isEmpty())) {
            throw new ValidationException("Transaction Set Identifier Code is required");
        }
        // Add other validation as needed
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
                new InterchangeControlHeader.Builder(context).setInterchangeControlNumber(interchangeControlNumber);

        FunctionalGroupHeader.Builder functionalBuilder = customFunctionalBuilder != null ?
                customFunctionalBuilder :
                new FunctionalGroupHeader.Builder(context).setGroupControlNumber(groupControlNumber);

        TransactionSetHeader.Builder transactionSetBuilder = customTransactionSetBuilder != null ?
                customTransactionSetBuilder :
                new TransactionSetHeader.Builder()
                        .setTransactionSetIdentifierCode(transactionSetIdentifierCode)
                        .setTransactionSetControlNumber(transactionSetControlNumber);

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

        // Build all segments and add them to the list
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
            builder.setEntityIdentifierCode(payerIdentification);
        }
        return builder;
    }

    /**
     * Builder for Header class
     */
    public static class Builder {
        private final x834Context context;

        // Simple fields
        private String interchangeControlNumber;
        private String groupControlNumber = "1";
        private String transactionSetIdentifierCode = "834";
        private String transactionSetControlNumber = "0001";
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
        public Builder(x834Context context) {
            this.context = context;
        }

        // Simple setters
        public Builder setInterchangeControlNumber(String interchangeControlNumber) {
            this.interchangeControlNumber = interchangeControlNumber;
            return this;
        }

        public Builder setGroupControlNumber(String groupControlNumber) {
            this.groupControlNumber = groupControlNumber;
            return this;
        }

        public Builder setTransactionSetIdentifierCode(String transactionSetIdentifierCode) {
            this.transactionSetIdentifierCode = transactionSetIdentifierCode;
            return this;
        }

        public Builder setTransactionSetControlNumber(String transactionSetControlNumber) {
            this.transactionSetControlNumber = transactionSetControlNumber;
            return this;
        }

        public Builder setReferenceIdentification(String referenceIdentification) {
            this.referenceIdentification = referenceIdentification;
            return this;
        }

        public Builder setMasterPolicyNumber(String masterPolicyNumber) {
            this.masterPolicyNumber = masterPolicyNumber;
            return this;
        }

        public Builder setPlanSponsorName(String planSponsorName) {
            this.planSponsorName = planSponsorName;
            return this;
        }

        public Builder setPayerName(String payerName) {
            this.payerName = payerName;
            return this;
        }

        public Builder setPayerIdentification(String payerIdentification) {
            this.payerIdentification = payerIdentification;
            return this;
        }

        // Custom builder setters
        public Builder setInterchangeControlHeaderBuilder(InterchangeControlHeader.Builder builder) {
            this.customInterchangeBuilder = builder;
            return this;
        }

        public Builder setFunctionalGroupHeaderBuilder(FunctionalGroupHeader.Builder builder) {
            this.customFunctionalBuilder = builder;
            return this;
        }

        public Builder setTransactionSetHeaderBuilder(TransactionSetHeader.Builder builder) {
            this.customTransactionSetBuilder = builder;
            return this;
        }

        public Builder setBeginningSegmentBuilder(BeginningSegment.Builder builder) {
            this.customBeginningSegmentBuilder = builder;
            return this;
        }

        public Builder setFileEffectiveDateBuilder(FileEffectiveDate.Builder builder) {
            this.customFileEffectiveDateBuilder = builder;
            return this;
        }

        public Builder setTransactionSetPolicyNumberBuilder(TransactionSetPolicyNumber.Builder builder) {
            this.customPolicyNumberBuilder = builder;
            return this;
        }

        public Builder setSponsorNameBuilder(SponsorName.Builder builder) {
            this.customSponsorBuilder = builder;
            return this;
        }

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

            // Set simple fields
            header.interchangeControlNumber = this.interchangeControlNumber;
            header.groupControlNumber = this.groupControlNumber;
            header.transactionSetIdentifierCode = this.transactionSetIdentifierCode;
            header.transactionSetControlNumber = this.transactionSetControlNumber;
            header.referenceIdentification = this.referenceIdentification;
            header.masterPolicyNumber = this.masterPolicyNumber;
            header.planSponsorName = this.planSponsorName;
            header.payerName = this.payerName;
            header.payerIdentification = this.payerIdentification;

            // Set custom builders
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