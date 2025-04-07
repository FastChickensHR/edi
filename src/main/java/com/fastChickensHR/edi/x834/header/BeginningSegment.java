/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.BGNSegment;
import com.fastChickensHR.edi.x834.x834Context;
import lombok.Getter;

/**
 * Represents the Beginning Segment (BGN) for a transaction set in an EDI 834 document.
 * This segment identifies the transaction set and provides control information.
 */
@Getter
public class BeginningSegment extends BGNSegment {
    public static final String DEFAULT_TRANSACTION_SET_PURPOSE_CODE = "00";

    protected BeginningSegment(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for the BeginningSegment.
     */
    public static class Builder extends BGNSegment.AbstractBuilder<BeginningSegment.Builder> {

        public Builder(x834Context context) {
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