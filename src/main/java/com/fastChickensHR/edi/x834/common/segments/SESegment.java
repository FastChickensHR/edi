/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.segments;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the SE (Transaction Set Trailer) segment in the EDI 834 format.
 * This segment defines the end of a transaction set and provides the segment count and control number.
 */
@Getter
abstract public class SESegment extends Segment {
    public static final String SEGMENT_ID = "SE";
    protected final String se01;
    protected final String se02;

    protected SESegment(SESegment.AbstractBuilder<?> builder) throws ValidationException {
        this.se01 = builder.se01;
        this.se02 = builder.se02;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{this.se01, this.se02};
    }

    public String getTransactionSegmentCount() {
        return getSe01();
    }

    public String getSetControlNumber() {
        return getSe02();
    }


    public abstract static class AbstractBuilder<T extends SESegment.AbstractBuilder<T>> {
        private String se01;
        private String se02;

        protected abstract T self();

        public abstract SESegment build() throws ValidationException;

        public T setSe01(String value) {
            this.se01 = value;
            return self();
        }

        public T setTransactionSegmentCount(String value) {
            return setSe01(value);
        }

        public T setSe02(String value) {
            this.se02 = value;
            return self();
        }

        public T setSetControlNumber(String value) {
            return setSe02(value);
        }
    }
}