/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

@Getter
public abstract class RefSegment extends Segment {
    public static final String SEGMENT_ID = "REF";

    protected final String ref01;
    protected final String ref02;

    protected RefSegment(AbstractBuilder<?> builder) throws ValidationException {
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

    public String getReferenceIdentificationQualifier() {
        return getRef01();
    }

    public String getReferenceIdentification() {
        return getRef02();
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String ref01;
        protected String ref02;

        protected abstract T self();

        public abstract RefSegment build() throws ValidationException;

        public T setReferenceIdentificationQualifier(String value) {
            this.ref01 = value;
            return self();
        }

        public T setReferenceIdentification(String value) {
            this.ref02 = value;
            return self();
        }

        public T setRef01(String value) {
            return setReferenceIdentificationQualifier(value);
        }

        public T setRef02(String value) {
            return setReferenceIdentification(value);
        }
    }
}