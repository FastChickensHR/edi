/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.*;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Represents the BGN (Beginning Segment) in the EDI format.
 * <p>
 * The BGN segment defines the beginning of a transaction set and provides
 * transaction purpose, reference identification, and date/time information.
 * </p>
 *
 * <h3>X12 834 Reference</h3>
 * <ul>
 *     <li>BGN01 — Transaction Set Purpose Code (M, ID 2/2)</li>
 *     <li>BGN02 — Reference Identification (M, AN 1/50)</li>
 *     <li>BGN03 — Date, CCYYMMDD (M, DT 8/8)</li>
 *     <li>BGN04 — Time, HHMM/HHMMSS/HHMMSSDD (O, TM 4/8)</li>
 *     <li>BGN05 — Time Zone Code (O, ID 2/2)</li>
 *     <li>BGN06 — Original Reference Number (O, AN 1/50)</li>
 *     <li>BGN07 — Transaction Type Code (O, ID 2/2)</li>
 *     <li>BGN08 — Action Code (O, ID 1/2)</li>
 *     <li>BGN09 — Security Level Code (O, ID 2/2)</li>
 * </ul>
 *
 * <h3>Default Handling</h3>
 * <p>
 * Segment-specific defaults are exposed as {@code DEFAULT_*} constants. Concrete
 * subclasses (e.g. {@code BeginningSegment} in the x834 header) are responsible
 * for applying defaults to optional fields where appropriate.
 * </p>
 */
@Getter
abstract public class BGNSegment extends Segment {
    /** The identifier for this segment type, always "BGN". */
    public static final String SEGMENT_ID = "BGN";

    /** Default Transaction Set Purpose Code ("00" — Original). */
    public static final String DEFAULT_TRANSACTION_SET_PURPOSE_CODE = "00";

    /** Maximum length of BGN02 (Reference Identification, X12 element 127: AN 1/50 in 005010). */
    public static final int BGN02_MAX_LENGTH = 50;
    /** Required length of BGN03 (Date, CCYYMMDD). */
    public static final int BGN03_LENGTH = 8;
    /** Minimum length of BGN04 (Time). */
    public static final int BGN04_MIN_LENGTH = 4;
    /** Maximum length of BGN04 (Time). */
    public static final int BGN04_MAX_LENGTH = 8;
    /** Maximum length of BGN06 (Original Reference Number, X12 element 127: AN 1/50 in 005010). */
    public static final int BGN06_MAX_LENGTH = 50;

    /** BGN01 — Transaction Set Purpose Code (required). */
    protected final TransactionSetPurposeCode bgn01;

    /** BGN02 — Reference Identification (required, max 80 chars). */
    protected final String bgn02;

    /** BGN03 — Date in CCYYMMDD format (required, exactly 8 digits). */
    protected final String bgn03;

    /** BGN04 — Time in HHMM, HHMMSS or HHMMSSDD format (optional). */
    protected final String bgn04;

    /** BGN05 — Time Zone Code (optional). */
    protected final TimeCode bgn05;

    /** BGN06 — Original Reference Number (optional, max 80 chars). */
    protected final String bgn06;

    /** BGN07 — Transaction Type Code (optional). */
    protected final TransactionTypeCode bgn07;

    /** BGN08 — Action Code (optional). */
    protected final ActionCode bgn08;

    /** BGN09 — Security Level Code (optional). */
    protected final SecurityLevelCode bgn09;

    /**
     * Constructs a new BGN segment from the supplied builder and performs
     * field-level validation.
     *
     * @param builder the builder providing the field values
     * @throws ValidationException if any required field is missing or invalid
     */
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

    /**
     * Performs field-level validation of required and optional BGN elements,
     * including length and format constraints.
     *
     * @throws ValidationException if a constraint is violated
     */
    private void validateRequiredFields() throws ValidationException {
        if (bgn01 == null) {
            throw new ValidationException("Transaction Set Purpose Code (BGN01) is required");
        }
        if (bgn02 == null || bgn02.trim().isEmpty()) {
            throw new ValidationException("Reference Identification (BGN02) is required");
        }
        if (bgn02.length() > BGN02_MAX_LENGTH) {
            throw new ValidationException("Reference Identification (BGN02) must be " + BGN02_MAX_LENGTH + " characters or less");
        }
        if (bgn03 == null || bgn03.isEmpty()) {
            throw new ValidationException("Date (BGN03) is required");
        }
        if (bgn03.length() != BGN03_LENGTH) {
            throw new ValidationException("Date (BGN03) must be " + BGN03_LENGTH + " characters");
        }
        if (!bgn03.chars().allMatch(Character::isDigit)) {
            throw new ValidationException("Date (BGN03) must contain only digits (CCYYMMDD)");
        }
        if (bgn04 != null && !bgn04.isEmpty()) {
            if (bgn04.length() < BGN04_MIN_LENGTH || bgn04.length() > BGN04_MAX_LENGTH) {
                throw new ValidationException("Time (BGN04) must be between " + BGN04_MIN_LENGTH + " and " + BGN04_MAX_LENGTH + " characters");
            }
            if (!bgn04.chars().allMatch(Character::isDigit)) {
                throw new ValidationException("Time (BGN04) must contain only digits");
            }
        }
        if (bgn06 != null && bgn06.length() > BGN06_MAX_LENGTH) {
            throw new ValidationException("Original Reference Number (BGN06) must be " + BGN06_MAX_LENGTH + " characters or less");
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    /** {@inheritDoc} */
    @Override
    public String[] getElementValues() {
        return new String[]{
                bgn01.getCode(), bgn02, bgn03, bgn04, bgn05 != null ? bgn05.getCode() : null,
                bgn06, bgn07 != null ? bgn07.getCode() : null, bgn08 != null ? bgn08.getCode() : null, bgn09 != null ? bgn09.getCode() : null
        };
    }

    /** @return BGN01 — Transaction Set Purpose Code */
    public TransactionSetPurposeCode getTransactionSetPurposeCode() {
        return getBgn01();
    }

    /** @return BGN02 — Reference Identification */
    public String getReferenceIdentification() {
        return getBgn02();
    }

    /** @return BGN03 — Date in CCYYMMDD format */
    public String getDate() {
        return getBgn03();
    }

    /** @return BGN04 — Time */
    public String getTime() {
        return getBgn04();
    }

    /** @return BGN05 — Time Zone Code */
    public TimeCode getTimeZoneCode() {
        return getBgn05();
    }

    /** @return BGN06 — Original Reference Number */
    public String getOriginalReferenceNumber() {
        return getBgn06();
    }

    /** @return BGN07 — Transaction Type Code */
    public TransactionTypeCode getTransactionTypeCode() {
        return getBgn07();
    }

    /** @return BGN08 — Action Code */
    public ActionCode getActionCode() {
        return getBgn08();
    }

    /** @return BGN09 — Security Level Code */
    public SecurityLevelCode getSecurityLevelCode() {
        return getBgn09();
    }

    /**
     * Abstract builder for BGN segments. Concrete subclasses are free to apply
     * segment-specific defaults (e.g. {@link #DEFAULT_TRANSACTION_SET_PURPOSE_CODE})
     * inside their constructors prior to {@link #build()} being called.
     *
     * @param <T> the concrete builder type for method chaining
     */
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends BGNSegment.AbstractBuilder<T>> {
        protected TransactionSetPurposeCode bgn01;
        protected String bgn02;
        protected String bgn03;
        protected String bgn04;
        protected TimeCode bgn05;
        protected String bgn06;
        protected TransactionTypeCode bgn07;
        protected ActionCode bgn08;
        protected SecurityLevelCode bgn09;

        /** Default constructor. */
        public AbstractBuilder() {
        }

        /**
         * @return this builder cast to the concrete type
         */
        protected abstract T self();

        /**
         * Builds and validates the BGN segment.
         *
         * @return a new {@link BGNSegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract BGNSegment build() throws ValidationException;

        /** Sets BGN01 from its string code. */
        public T setBgn01(String value) {
            this.bgn01 = TransactionSetPurposeCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setBgn01(String)}. */
        public T setTransactionSetPurposeCode(String value) {
            return setBgn01(value);
        }

        /** Sets BGN02 (Reference Identification). */
        public T setBgn02(String value) {
            this.bgn02 = value;
            return self();
        }

        /** Domain alias for {@link #setBgn02(String)}. */
        public T setReferenceIdentification(String value) {
            return setBgn02(value);
        }

        /** Sets BGN03 (Date, CCYYMMDD). */
        public T setBgn03(String value) {
            this.bgn03 = value;
            return self();
        }

        /** Domain alias for {@link #setBgn03(String)}. */
        public T setDate(String value) {
            return setBgn03(value);
        }

        /** Sets BGN04 (Time). */
        public T setBgn04(String value) {
            this.bgn04 = value;
            return self();
        }

        /** Domain alias for {@link #setBgn04(String)}. */
        public T setTime(String value) {
            return setBgn04(value);
        }

        /** Sets BGN05 (Time Zone Code) from its string code. */
        public T setBgn05(String value) {
            this.bgn05 = TimeCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setBgn05(String)}. */
        public T setTimeZoneCode(String value) {
            return setBgn05(value);
        }

        /** Sets BGN06 (Original Reference Number). */
        public T setBgn06(String value) {
            this.bgn06 = value;
            return self();
        }

        /** Domain alias for {@link #setBgn06(String)}. */
        public T setOriginalReferenceNumber(String value) {
            return setBgn06(value);
        }

        /** Sets BGN07 (Transaction Type Code) from its string code. */
        public T setBgn07(String value) {
            this.bgn07 = TransactionTypeCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setBgn07(String)}. */
        public T setTransactionTypeCode(String value) {
            return setBgn07(value);
        }

        /** Sets BGN08 (Action Code) from its string code. */
        public T setBgn08(String value) {
            this.bgn08 = ActionCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setBgn08(String)}. */
        public T setActionCode(String value) {
            return setBgn08(value);
        }

        /** Sets BGN09 (Security Level Code) from its string code. */
        public T setBgn09(String value) {
            this.bgn09 = SecurityLevelCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setBgn09(String)}. */
        public T setSecurityLevelCode(String value) {
            return setBgn09(value);
        }
    }

    /**
     * Concrete builder implementation for {@link BGNSegment}.
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
     * Concrete implementation of {@link BGNSegment} for the public builder.
     */
    private static class BGNSegmentImpl extends BGNSegment {
        private BGNSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}
