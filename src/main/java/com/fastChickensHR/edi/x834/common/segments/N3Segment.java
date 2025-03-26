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
 * Represents the N3 segment in X12 834 format.
 * This segment is used for address information.
 */
@Getter
public abstract class N3Segment extends Segment {
    public static final String SEGMENT_ID = "N3";

    protected final String n301;
    protected final String n302;

    protected N3Segment(AbstractBuilder<?> builder) throws ValidationException {
        this.n301 = builder.n301;
        this.n302 = builder.n302;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{n301, n302};
    }

    /**
     * Gets the address line 1.
     * @return address line 1
     */
    public String getAddressLine1() {
        return getN301();
    }

    /**
     * Gets the address line 2.
     * @return address line 2
     */
    public String getAddressLine2() {
        return getN302();
    }

    /**
     * Abstract builder for N3Segment.
     * @param <T> the builder type
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String n301;
        protected String n302;

        /**
         * Returns this builder.
         * @return this builder
         */
        protected abstract T self();

        /**
         * Builds a new N3Segment instance.
         * @return a new N3Segment
         * @throws ValidationException if validation fails
         */
        public abstract N3Segment build() throws ValidationException;

        /**
         * Sets the address line 1.
         * @param value address line 1
         * @return this builder
         */
        public T setAddressLine1(String value) {
            this.n301 = value;
            return self();
        }

        /**
         * Sets the address line 2.
         * @param value address line 2
         * @return this builder
         */
        public T setAddressLine2(String value) {
            this.n302 = value;
            return self();
        }

        /**
         * Sets the N301 element (address line 1).
         * @param value N301 value
         * @return this builder
         */
        public T setN301(String value) {
            return setAddressLine1(value);
        }

        /**
         * Sets the N302 element (address line 2).
         * @param value N302 value
         * @return this builder
         */
        public T setN302(String value) {
            return setAddressLine2(value);
        }
    }
}