/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.segments.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public abstract class TransactionSetPolicyNumber extends Segment {
    public static final String SEGMENT_ID = "REF";
    public static final String DEFAULT_REFERENCE_IDENTIFICATION_QUALIFIER = "38";

    private final String ref01;
    private final String ref02;

    protected TransactionSetPolicyNumber(TransactionSetPolicyNumber.Builder builder) throws ValidationException {
        this.ref01 = builder.ref01;
        this.ref02 = builder.ref02;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{ref01, ref02};
    }

    // Domain-specific accessor methods
    public String getReferenceIdentificationQualifier() {
        return getRef01();
    }

    public String getMasterPolicyNumber() {
        return getRef02();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private String ref01 = DEFAULT_REFERENCE_IDENTIFICATION_QUALIFIER;
        private String ref02;

        public Builder() {
        }

        public Builder setReferenceIdentificationQualifier(String value) {
            return setRef01(value);
        }

        public Builder setMasterPolicyNumber(String value) {
            return setRef02(value);
        }

        public TransactionSetPolicyNumber build() throws ValidationException {
            return new TransactionSetPolicyNumber.TransactionSetPolicyNumberImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class TransactionSetPolicyNumberImpl extends TransactionSetPolicyNumber {
        private TransactionSetPolicyNumberImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}
