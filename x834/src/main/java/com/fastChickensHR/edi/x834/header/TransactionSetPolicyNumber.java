/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.RefSegment;
import lombok.Getter;

/**
 * Master policy number reference (REF*38) in the header portion of the X12 834 (005010X220A1).
 * This segment uses the REF segment format with the {@code "38"} qualifier for the master policy number.
 */
@Getter
public class TransactionSetPolicyNumber extends RefSegment {
    /** Default REF01 reference identification qualifier {@code "38"} (Master Policy Number). */
    private final static String DEFAULT_REFERENCE_IDENTIFICATION_QUALIFIER = "38";

    protected TransactionSetPolicyNumber(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Domain-specific accessor for the master policy number (REF02).
     *
     * @return the master policy number
     */
    public String getMasterPolicyNumber() {
        return getRef02();
    }

    /**
     * Builder for the TransactionSetPolicyNumber. Seeds the master-policy-number qualifier (REF01=38).
     */
    public static class Builder extends RefSegment.AbstractBuilder<TransactionSetPolicyNumber.Builder> {

        public Builder() {
            super();
            this.setRef01(DEFAULT_REFERENCE_IDENTIFICATION_QUALIFIER);
        }

        @Override
        protected TransactionSetPolicyNumber.Builder self() {
            return this;
        }

        /**
         * Domain-specific setter for the master policy number (REF02).
         *
         * @param value the master policy number
         * @return this builder instance
         */
        public Builder setMasterPolicyNumber(String value) {
            return setRef02(value);
        }

        /**
         * Builds a new TransactionSetPolicyNumber instance
         *
         * @return A new TransactionSetPolicyNumber instance
         * @throws ValidationException if validation fails
         */
        public TransactionSetPolicyNumber build() throws ValidationException {
            return new TransactionSetPolicyNumber(this);
        }
    }
}