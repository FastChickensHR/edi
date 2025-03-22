/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the IEA (Interchange Control Trailer) segment in the EDI 834 format.
 * This segment defines the end of an interchange envelope and provides the number of included
 * functional groups and the interchange control number.
 */
@Getter
abstract public class IEASegment extends Segment {
    public static final String SEGMENT_ID = "IEA";
    protected final String iea01;
    protected final String iea02;

    protected IEASegment(IEASegment.AbstractBuilder<?> builder) throws ValidationException {
        this.iea01 = builder.iea01;
        this.iea02 = builder.iea02;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{this.iea01, this.iea02};
    }

    public String getNumberOfIncludedGroups() {
        return getIea01();
    }

    public String getInterchangeControlNumber() {
        return getIea02();
    }

    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends IEASegment.AbstractBuilder<T>> {
        private String iea01;
        private String iea02;

        protected abstract T self();

        public abstract IEASegment build() throws ValidationException;

        public T setIea01(String value) {
            this.iea01 = value;
            return self();
        }

        public T setNumberOfIncludedGroups(String value) {
            return setIea01(value);
        }

        public T setIea02(String value) {
            this.iea02 = value;
            return self();
        }

        public T setInterchangeControlNumber(String value) {
            return setIea02(value);
        }
    }
}