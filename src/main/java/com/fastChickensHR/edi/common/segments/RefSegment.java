/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.ReferenceIdentificationQualifier;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

@Getter
public abstract class RefSegment extends Segment {
    public static final String SEGMENT_ID = "REF";

    protected final ReferenceIdentificationQualifier ref01;
    protected final String ref02;
    protected final String ref03;

    protected RefSegment(RefSegment.AbstractBuilder<?> builder) throws ValidationException {
        this.ref01 = builder.ref01;
        this.ref02 = builder.ref02;
        this.ref03 = builder.ref03;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if ((ref02 == null || ref02.trim().isEmpty()) &&
                (ref03 == null || ref03.trim().isEmpty())) {
            throw new ValidationException("Either Reference Identification (REF02) or Reference Description (REF03) is required");
        }
        if ( ref02 != null && ref02.length() > 80) {
            throw new ValidationException("REF02 (Reference Identification) must be 80 characters or less");
        }
        if ( ref03 != null && ref03.length() > 80) {
            throw new ValidationException("REF03 (Reference Description) must be 80 characters or less");
        }
    }


    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{ref01.getCode(), ref02, ref03};
    }

    public ReferenceIdentificationQualifier getReferenceIdentificationQualifier() {
        return getRef01();
    }

    public String getReferenceIdentification() {
        return getRef02();
    }

    public String getReferenceDescription() {
        return getRef03();
    }

    public abstract static class AbstractBuilder<T extends RefSegment.AbstractBuilder<T>> {
        protected ReferenceIdentificationQualifier ref01;
        protected String ref02;
        protected String ref03;

        protected abstract T self();

        public abstract RefSegment build() throws ValidationException;

        public T setReferenceIdentificationQualifier(String value) {
            this.ref01 = ReferenceIdentificationQualifier.fromString(value);
            return self();
        }

        public T setReferenceIdentification(String value) {
            this.ref02 = value;
            return self();
        }

        public T setReferenceDescription(String value) {
            this.ref03 = value;
            return self();
        }

        public T setRef01(String value) {
            return setReferenceIdentificationQualifier(value);
        }

        public T setRef02(String value) {
            return setReferenceIdentification(value);
        }

        public T setRef03(String value) {
            return setReferenceDescription(value);
        }
    }

    /**
     * Concrete builder implementation for RefSegment
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected RefSegment.Builder self() {
            return this;
        }

        @Override
        public RefSegment build() throws ValidationException {
            return new RefSegmentImpl(this);
        }
    }

    /**
     * Concrete implementation of STSegment
     */
    private static class RefSegmentImpl extends RefSegment {
        private RefSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}

