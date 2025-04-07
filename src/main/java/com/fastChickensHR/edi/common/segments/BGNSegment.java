/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.*;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the BGN (Beginning Segment) in the EDI format.
 * This segment defines the beginning of a transaction set and provides
 * transaction purpose, reference identification, and date/time information.
 */
@Getter
abstract public class BGNSegment extends Segment {
    /**
     * The identifier for this segment type, always "BGN"
     */
    public static final String SEGMENT_ID = "BGN";

    /**
     * Transaction Set Purpose Code
     */
    protected final TransactionSetPurposeCode bgn01;

    /**
     * Reference Identification
     */
    protected final String bgn02;

    /**
     * Date (CCYYMMDD)
     */
    protected final String bgn03;

    /**
     * Time (HHMM) - Optional
     */
    protected final String bgn04;

    /**
     * Time Zone Code - Optional
     */
    protected final TimeCode bgn05;

    /**
     * Original Reference Number - Optional
     */
    protected final String bgn06;

    /**
     * Transaction Type Code - Optional
     */
    protected final TransactionTypeCode bgn07;

    /**
     * Action Code - Optional
     */
    protected final ActionCode bgn08;

    /**
     * Security Level Code - Optional
     */
    protected final SecurityLevelCode bgn09;

    protected BGNSegment(BGNSegment.AbstractBuilder<?> builder) throws ValidationException {
        this.bgn01 = builder.bgn01;
        this.bgn02 = builder.bgn02;
        this.bgn03 = builder.bgn03;
        this.bgn04 = builder.bgn04;
        this.bgn05 = builder.bgn05;
        this.bgn06 = builder.bgn06;
        this.bgn07 = builder.bgn07;
        this.bgn08 = builder.bgn08;
        this.bgn09 = builder.bgn09;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (bgn02 == null || bgn02.trim().isEmpty()) {
            throw new ValidationException("Reference Identification (BGN02) is required");
        }
        if (bgn02.length() > 80) {
            throw new ValidationException("Reference Identification (BGN02) must be 80 characters or less");
        }
        if (bgn03 == null || bgn03.isEmpty()) {
            throw new ValidationException("Date (BGN03) is required");
        }
        if (bgn03.length() != 8) {
            throw new ValidationException("Date (BGN03) must be 8 characters");
        }
        if (bgn04 != null && (bgn04.length() < 4 || bgn04.length() > 8)) {
            throw new ValidationException("Time (BGN04) must be between 4 and 8 characters");
        }
        if (bgn06 != null && bgn06.length() > 80) {
            throw new ValidationException("Original Reference Number (BGN06) must be 80 characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                bgn01.getCode(), bgn02, bgn03, bgn04, bgn05 != null ? bgn05.getCode() : null,
                bgn06, bgn07 != null ? bgn07.getCode() : null, bgn08 != null ? bgn08.getCode(): null, bgn09 != null ? bgn09.getCode() : null
        };
    }

    public TransactionSetPurposeCode getTransactionSetPurposeCode() {
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

    public TimeCode getTimeZoneCode() {
        return getBgn05();
    }

    public String getOriginalReferenceNumber() {
        return getBgn06();
    }

    public TransactionTypeCode getTransactionTypeCode() {
        return getBgn07();
    }

    public ActionCode getActionCode() {
        return getBgn08();
    }

    public SecurityLevelCode getSecurityLevelCode() {
        return getBgn09();
    }

    public abstract static class AbstractBuilder<T extends BGNSegment.AbstractBuilder<T>> {
        private TransactionSetPurposeCode bgn01;
        private String bgn02;
        private String bgn03;
        private String bgn04;
        private TimeCode bgn05;
        private String bgn06;
        private TransactionTypeCode bgn07;
        private ActionCode bgn08;
        private SecurityLevelCode bgn09;

        protected abstract T self();

        public abstract BGNSegment build() throws ValidationException;

        public T setBgn01(String value) {
            this.bgn01 = TransactionSetPurposeCode.fromString(value);
            return self();
        }

        public T setTransactionSetPurposeCode(String value) {
            return setBgn01(value);
        }

        public T setBgn02(String value) {
            this.bgn02 = value;
            return self();
        }

        public T setReferenceIdentification(String value) {
            return setBgn02(value);
        }

        public T setBgn03(String value) {
            this.bgn03 = value;
            return self();
        }

        public T setDate(String value) {
            return setBgn03(value);
        }

        public T setBgn04(String value) {
            this.bgn04 = value;
            return self();
        }

        public T setTime(String value) {
            return setBgn04(value);
        }

        public T setBgn05(String value) {
            this.bgn05 = TimeCode.fromString(value);
            return self();
        }

        public T setTimeZoneCode(String value) {
            return setBgn05(value);
        }

        public T setBgn06(String value) {
            this.bgn06 = value;
            return self();
        }

        public T setOriginalReferenceNumber(String value) {
            return setBgn06(value);
        }

        public T setBgn07(String value) {
            this.bgn07 = TransactionTypeCode.fromString(value);
            return self();
        }

        public T setTransactionTypeCode(String value) {
            return setBgn07(value);
        }

        public T setBgn08(String value) {
            this.bgn08 = ActionCode.fromString(value);
            return self();
        }

        public T setActionCode(String value) {
            return setBgn08(value);
        }

        public T setBgn09(String value) {
            this.bgn09 = SecurityLevelCode.fromString(value);
            return self();
        }

        public T setSecurityLevelCode(String value) {
            return setBgn09(value);
        }
    }

    /**
     * Concrete builder implementation for BGNSegment
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public BGNSegment build() throws ValidationException {
            return new BGNSegmentImpl(this);
        }
    }

    /**
     * Concrete implementation of BGNSegment
     */
    private static class BGNSegmentImpl extends BGNSegment {
        private BGNSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}