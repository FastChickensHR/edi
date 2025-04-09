/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.RefSegment;
import lombok.Getter;

/**
 * Represents a policy number reference segment in an X834 transaction set.
 * This segment uses the REF segment format with a specific qualifier for policy numbers.
 */
@Getter
public class TransactionSetPolicyNumber extends RefSegment {
    private final static String DEFAULT_REFERENCE_IDENTIFICATION_QUALIFIER = "38";

    protected TransactionSetPolicyNumber(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Domain-specific accessor method
     */
    public String getMasterPolicyNumber() {
        return getRef02();
    }

    /**
     * Builder for the TransactionSetPolicyNumber.
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
         * Domain-specific setter
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