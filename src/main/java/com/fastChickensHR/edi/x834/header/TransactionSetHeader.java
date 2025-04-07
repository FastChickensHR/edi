/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.STSegment;
import com.fastChickensHR.edi.x834.x834Context;
import lombok.Getter;

/**
 * Abstract class representing the beginning segment of the header loop in an EDI document.
 * This segment is commonly referred to as the "ST" segment and includes key fields such as
 * the transaction set identifier code (ST01), transaction set control number (ST02),
 * and implementation convention reference (ST03).
 */
@Getter
public class TransactionSetHeader extends STSegment {
    private final static String DEFAULT_TRANSACTION_SET_ID = "834";
    private final static String DEFAULT_CONTROL_NUMBER = "0001";
    private final static String DEFAULT_CONVENTION_REFERENCE = "005010X220A1";

    protected TransactionSetHeader(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for the FunctionalGroupHeader.
     */
    public static class Builder extends STSegment.AbstractBuilder<TransactionSetHeader.Builder> {
        protected x834Context context;

        public Builder() {
            super();
            this.setSt01(DEFAULT_TRANSACTION_SET_ID);
            this.setSt02(DEFAULT_CONTROL_NUMBER);
            this.setSt03(DEFAULT_CONVENTION_REFERENCE);
        }

        @Override
        protected TransactionSetHeader.Builder self() {
            return this;
        }

        /**
         * Builds a new FunctionalGroupHeader instance
         *
         * @return A new FunctionalGroupHeader instance
         * @throws ValidationException if validation fails
         */
        public TransactionSetHeader build() throws ValidationException {
            return new TransactionSetHeader(this);
        }
    }
}