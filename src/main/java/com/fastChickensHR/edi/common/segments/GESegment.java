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
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the GE (Functional Group Trailer) segment in the EDI 834 format.
 * This segment defines the end of a functional group and provides the included transaction set count
 * and the group control number.
 */
@Getter
abstract public class GESegment extends Segment {
    public static final String SEGMENT_ID = "GE";
    protected final String ge01;
    protected final String ge02;

    protected GESegment(GESegment.AbstractBuilder<?> builder) throws ValidationException {
        this.ge01 = builder.ge01;
        this.ge02 = builder.ge02;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{this.ge01, this.ge02};
    }

    public String getNumberOfTransactionSets() {
        return getGe01();
    }

    public String getGroupControlNumber() {
        return getGe02();
    }

    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends GESegment.AbstractBuilder<T>> {
        private String ge01;
        private String ge02;

        protected abstract T self();

        public abstract GESegment build() throws ValidationException;

        public T setGe01(String value) {
            this.ge01 = value;
            return self();
        }

        public T setNumberOfTransactionSets(String value) {
            return setGe01(value);
        }

        public T setGe02(String value) {
            this.ge02 = value;
            return self();
        }

        public T setGroupControlNumber(String value) {
            return setGe02(value);
        }
    }
}