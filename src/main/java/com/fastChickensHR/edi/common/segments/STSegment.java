/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.TransactionSetIdentifierCode;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.TransactionSetHeader;
import lombok.Getter;

/**
 * Represents the ST (Transaction Set Header) segment in the EDI format.
 * This segment defines the beginning of a transaction set and provides
 * the transaction set identifier, control number, and implementation convention reference.
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

    public TransactionSetIdentifierCode getTransactionSetIdentifierCode() {
        return getSt01();
    }

    public String getTransactionSetControlNumber() {
        return getSt02();
    }

    public String getImplementationConventionReference() {
        return getSt03();
    }

    public abstract static class AbstractBuilder<T extends STSegment.AbstractBuilder<T>> {
        private TransactionSetIdentifierCode st01;
        private String st02;
        private String st03;

        protected abstract T self();

        public abstract STSegment build() throws ValidationException;

        public T setSt01(String value) {
            this.st01 = TransactionSetIdentifierCode.fromString(value);
            return self();
        }

        public T setTransactionSetIdentifierCode(String value) {
            return setSt01(value);
        }

        public T setSt02(String value) {
            this.st02 = value;
            return self();
        }

        public T setTransactionSetControlNumber(String value) {
            return setSt02(value);
        }

        public T setSt03(String value) {
            this.st03 = value;
            return self();
        }

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