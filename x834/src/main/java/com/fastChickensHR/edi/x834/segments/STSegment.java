/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.TransactionSetIdentifierCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.TransactionSetHeader;
import lombok.Getter;

/**
 * Represents the ST (Transaction Set Header) segment in the X12 834
 * (005010X220A1) format.
 * <p>
 * This segment opens a transaction set, naming the transaction type, a control
 * number, and the implementation convention it conforms to. It is closed by a
 * matching {@link SESegment}.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>ST01 = transaction set identifier code (e.g. "834")</li>
 *     <li>ST02 = transaction set control number (4–9 characters; matched by SE02)</li>
 *     <li>ST03 = implementation convention reference (optional, max 35 characters)</li>
 * </ul>
 */
@Getter
abstract public class STSegment extends Segment {
    /** The identifier for this segment type, always "ST" */
    public static final String SEGMENT_ID = "ST";

    /** Transaction Set Identifier Code */
    protected final TransactionSetIdentifierCode st01;

    /** Transaction Set Control Number */
    protected final String st02;

    /** Implementation Convention Reference */
    protected final String st03;

    protected STSegment(STSegment.AbstractBuilder<?> builder) throws ValidationException {
        this.st01 = builder.st01;
        this.st02 = builder.st02;
        this.st03 = builder.st03;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (st01 == null) {
            throw new ValidationException("ST01 (Transaction Set Identifier Code) cannot be blank");
        }
        if (st02 == null || st02.trim().isEmpty()) {
            throw new ValidationException("ST02 (Transaction Set Control Number) cannot be blank");
        }
        if (st02.length() < 4 || st02.length() >= 10) {
            throw new ValidationException("ST02 (Transaction Set Control Number) must be between 4 and 9 characters");
        }
        if (st03 != null && st03.trim().isEmpty()) {
            throw new ValidationException("ST03 (Implementation Convention Reference) cannot be blank");
        }
        if (st03 != null && st03.length() > 35) {
            throw new ValidationException("ST03 (Implementation Convention Reference) must be 35 characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{this.st01.getCode(), this.st02, this.st03};
    }

    /** @return ST01 — transaction set identifier code. */
    public TransactionSetIdentifierCode getTransactionSetIdentifierCode() {
        return getSt01();
    }

    /** @return ST02 — transaction set control number. */
    public String getTransactionSetControlNumber() {
        return getSt02();
    }

    /** @return ST03 — implementation convention reference. */
    public String getImplementationConventionReference() {
        return getSt03();
    }

    /**
     * Abstract builder for ST segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    public abstract static class AbstractBuilder<T extends STSegment.AbstractBuilder<T>> {
        private TransactionSetIdentifierCode st01;
        private String st02;
        private String st03;

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the ST segment.
         *
         * @return a new {@link STSegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract STSegment build() throws ValidationException;

        /** Sets ST01 (transaction set identifier code) from its string code. */
        public T setSt01(String value) {
            this.st01 = TransactionSetIdentifierCode.fromString(value);
            return self();
        }

        /** Domain alias for {@link #setSt01(String)}. */
        public T setTransactionSetIdentifierCode(String value) {
            return setSt01(value);
        }

        /** Sets ST02 (transaction set control number). */
        public T setSt02(String value) {
            this.st02 = value;
            return self();
        }

        /** Domain alias for {@link #setSt02(String)}. */
        public T setTransactionSetControlNumber(String value) {
            return setSt02(value);
        }

        /** Sets ST03 (implementation convention reference). */
        public T setSt03(String value) {
            this.st03 = value;
            return self();
        }

        /** Domain alias for {@link #setSt03(String)}. */
        public T setImplementationConventionReference(String value) {
            return setSt03(value);
        }
    }

    /**
     * Concrete builder implementation for STSegment
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public STSegment build() throws ValidationException {
            return new STSegmentImpl(this);
        }
    }

    /**
     * Concrete implementation of STSegment
     */
    private static class STSegmentImpl extends STSegment {
        private STSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}