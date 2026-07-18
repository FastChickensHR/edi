/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.STSegment;
import com.fastChickensHR.edi.x834.X834Context;
import lombok.Getter;

/**
 * Represents the Transaction Set Header (ST) segment that opens the 834 transaction set
 * (005010X220A1), sitting just inside the GS/GE functional group. Includes key fields such as
 * the transaction set identifier code (ST01), transaction set control number (ST02),
 * and implementation convention reference (ST03). Extends the generic ST segment.
 */
@Getter
public class TransactionSetHeader extends STSegment {
    /** Default ST01 transaction set identifier code {@code "834"} (Benefit Enrollment and Maintenance). */
    private final static String DEFAULT_TRANSACTION_SET_ID = "834";
    /** Default ST02 transaction set control number {@code "0001"}. */
    private final static String DEFAULT_CONTROL_NUMBER = "0001";
    /** Default ST03 implementation convention reference {@code "005010X220A1"} (the 834 guide). */
    private final static String DEFAULT_CONVENTION_REFERENCE = "005010X220A1";

    protected TransactionSetHeader(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for the TransactionSetHeader.
     */
    public static class Builder extends STSegment.AbstractBuilder<TransactionSetHeader.Builder> {
        protected X834Context context;

        /**
         * Creates a builder seeded with the default ST01/ST02/ST03 values (834, 0001, 005010X220A1).
         */
        public Builder() {
            super();
            this.setSt01(DEFAULT_TRANSACTION_SET_ID);
            this.setSt02(DEFAULT_CONTROL_NUMBER);
            this.setSt03(DEFAULT_CONVENTION_REFERENCE);
        }

        /**
         * Creates a builder using the context's transaction set control number for ST02,
         * with the default ST01 (834) and ST03 (005010X220A1) values.
         *
         * @param context The X834 context supplying the transaction set control number
         */
        public Builder(X834Context context) {
            super();
            this.context = context;
            this.setSt01(DEFAULT_TRANSACTION_SET_ID);
            this.setSt02(context.getTransactionSetControlNumber());
            this.setSt03(DEFAULT_CONVENTION_REFERENCE);
        }

        @Override
        protected TransactionSetHeader.Builder self() {
            return this;
        }

        /**
         * Builds a new TransactionSetHeader instance
         *
         * @return A new TransactionSetHeader instance
         * @throws ValidationException if validation fails
         */
        public TransactionSetHeader build() throws ValidationException {
            return new TransactionSetHeader(this);
        }
    }
}