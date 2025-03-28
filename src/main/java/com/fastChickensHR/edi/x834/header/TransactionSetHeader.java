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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Abstract class representing the beginning segment of the header loop in an EDI document.
 * This segment is commonly referred to as the "ST" segment and includes key fields such as
 * the transaction set identifier code (ST01), transaction set control number (ST02),
 * and implementation convention reference (ST03).
 */
@Getter
public abstract class TransactionSetHeader extends Segment {
    // Constants for segment and field identifiers
    public static final String SEGMENT_ID = "ST";
    public static final String DEFAULT_TRANSACTION_SET_ID = "834";
    public static final String DEFAULT_CONTROL_NUMBER = "0001";
    public static final String DEFAULT_CONVENTION_REFERENCE = "005010X220A1";

    private final String st01; // Transaction Set Identifier Code
    private final String st02; // Transaction Set Control Number
    private final String st03; // Implementation Convention Reference

    protected TransactionSetHeader(Builder builder) throws ValidationException {
        this.st01 = builder.st01;
        this.st02 = builder.st02;
        this.st03 = builder.st03;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (st01 == null || st01.trim().isEmpty()) {
            throw new ValidationException("ST01 (Transaction Set Identifier Code) cannot be blank");
        }
        if (st02 == null || st02.trim().isEmpty()) {
            throw new ValidationException("ST02 (Transaction Control Number) cannot be blank");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{st01, st02, st03};
    }

    public String getTransactionSetIdentifierCode() {
        return st01;
    }

    public String getTransactionSetControlNumber() {
        return st02;
    }

    public String getImplementationConventionReference() {
        return st03;
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private String st01 = DEFAULT_TRANSACTION_SET_ID;
        private String st02 = DEFAULT_CONTROL_NUMBER;
        private String st03 = DEFAULT_CONVENTION_REFERENCE;

        public Builder setTransactionSetIdentifierCode(String code) {
            return setSt01(code);
        }

        public Builder setTransactionSetControlNumber(String number) {
            return setSt02(number);
        }

        public Builder setImplementationConventionReference(String reference) {
            return setSt03(reference);
        }

        /**
         * Builds a new TransactionSetHeader instance
         */
        public TransactionSetHeader build() throws ValidationException {
            return new TransactionSetHeaderImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class TransactionSetHeaderImpl extends TransactionSetHeader {
        private TransactionSetHeaderImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}