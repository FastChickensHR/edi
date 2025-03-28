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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the Beginning Segment (BGN) for a transaction set in an EDI 834 document.
 * This segment identifies the transaction set and provides control information.
 */
@Getter
public abstract class BeginningSegment extends Segment {
    public static final String SEGMENT_ID = "BGN";
    public static final String DEFAULT_TRANSACTION_SET_PURPOSE_CODE = "00"; // Original

    private final String bgn01; // Transaction Set Purpose Code
    private final String bgn02; // Reference Identification
    private final String bgn03; // Date (CCYYMMDD)
    private final String bgn04; // Time (HHMM) - Optional
    private final String bgn05; // Time Zone Code - Optional
    private final String bgn06; // Original Reference Number - Optional
    private final String bgn07; // Transaction Type Code - Optional
    private final String bgn08; // Action Code - Optional
    private final String bgn09; // Security Level Code - Optional

    protected BeginningSegment(Builder builder) throws ValidationException {
        this.bgn01 = builder.bgn01;
        this.bgn02 = builder.bgn02;
        this.bgn03 = builder.bgn03;
        this.bgn04 = builder.bgn04;
        this.bgn05 = builder.bgn05;
        this.bgn06 = builder.bgn06;
        this.bgn07 = builder.bgn07;
        this.bgn08 = builder.bgn08;
        this.bgn09 = builder.bgn09;

        this.context = builder.context;
        validateRequiredFields();
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{bgn01, bgn02, bgn03, bgn04, bgn05, bgn06, bgn07, bgn08, bgn09};
    }

    private void validateRequiredFields() throws ValidationException {
        if (bgn01 == null || bgn01.trim().isEmpty()) {
            throw new ValidationException("Transaction Set Purpose Code (BGN01) is required");
        }
        if (bgn02 == null || bgn02.trim().isEmpty()) {
            throw new ValidationException("Reference Identification (BGN02) is required");
        }
        if (bgn03 == null || bgn03.trim().isEmpty()) {
            throw new ValidationException("Date (BGN03) is required");
        }
    }

    // Domain-specific accessor methods
    public String getTransactionSetPurposeCode() {
        return getBgn01();
    }

    public String getReferenceIdentification() {
        return getBgn02();
    }

    public String getDate() {
        return getBgn03();
    }

    public String getTime() {
        return getBgn04();
    }

    public String getTimeZoneCode() {
        return getBgn05();
    }

    public String getOriginalReferenceNumber() {
        return getBgn06();
    }

    public String getTransactionTypeCode() {
        return getBgn07();
    }

    public String getActionCode() {
        return getBgn08();
    }

    public String getSecurityLevelCode() {
        return getBgn09();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private final x834Context context;
        private String bgn01 = DEFAULT_TRANSACTION_SET_PURPOSE_CODE;
        private String bgn02;
        private String bgn03;
        private String bgn04;
        private String bgn05;
        private String bgn06;
        private String bgn07;
        private String bgn08;
        private String bgn09;

        public Builder(x834Context context) {
            this.context = context;
            this.bgn02 = context.getTransactionSetControlNumber();
            this.bgn03 = context.getFormattedDocumentDate();
        }

        public Builder setTransactionSetPurposeCode(String code) {
            return setBgn01(code);
        }

        public Builder setReferenceIdentification(String reference) {
            return setBgn02(reference);
        }

        public Builder setDate(String date) {
            return setBgn03(date);
        }

        public Builder setTime(String time) {
            return setBgn04(time);
        }

        public Builder setTimeZoneCode(String timeZoneCode) {
            return setBgn05(timeZoneCode);
        }

        public Builder setOriginalReferenceNumber(String originalReferenceNumber) {
            return setBgn06(originalReferenceNumber);
        }

        public Builder setTransactionTypeCode(String transactionTypeCode) {
            return setBgn07(transactionTypeCode);
        }

        public Builder setActionCode(String actionCode) {
            return setBgn08(actionCode);
        }

        public Builder setSecurityLevelCode(String securityLevelCode) {
            return setBgn09(securityLevelCode);
        }

        public BeginningSegment build() throws ValidationException {
            return new BeginningSegment.BeginningSegmentImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class BeginningSegmentImpl extends BeginningSegment {
        private BeginningSegmentImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}
