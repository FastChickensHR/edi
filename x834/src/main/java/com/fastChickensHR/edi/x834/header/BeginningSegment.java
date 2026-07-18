/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.BGNSegment;
import com.fastChickensHR.edi.x834.X834Context;
import lombok.Getter;

/**
 * Represents the Beginning Segment (BGN) for a transaction set in an EDI 834 document
 * (005010X220A1). The BGN follows the transaction set header (ST) in the header portion
 * of the 834 and provides transaction set purpose and control information.
 */
@Getter
public class BeginningSegment extends BGNSegment {
    /** Default BGN01 transaction set purpose code {@code "00"} (Original). */
    public static final String DEFAULT_TRANSACTION_SET_PURPOSE_CODE = "00";

    protected BeginningSegment(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for the BeginningSegment.
     */
    public static class Builder extends BGNSegment.AbstractBuilder<BeginningSegment.Builder> {

        /**
         * Creates a builder seeded with the default purpose code (BGN01=00) and the
         * context's formatted document date as the transaction set reference date (BGN03).
         *
         * @param context The X834 context supplying the document date
         */
        public Builder(X834Context context) {
            super();
            this.setTransactionSetPurposeCode(DEFAULT_TRANSACTION_SET_PURPOSE_CODE);
            this.setBgn03(context.getFormattedDocumentDate());
        }

        @Override
        protected BeginningSegment.Builder self() {
            return this;
        }

        /**
         * Builds a new BeginningSegment instance
         *
         * @return A new BeginningSegment instance
         * @throws ValidationException if validation fails
         */
        @Override
        public BeginningSegment build() throws ValidationException {
            return new BeginningSegment(this);
        }
    }
}