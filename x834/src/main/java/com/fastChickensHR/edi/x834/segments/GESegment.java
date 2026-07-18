/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the GE (Functional Group Trailer) segment in the X12 834
 * (005010X220A1) format.
 * <p>
 * This segment marks the end of a functional group opened by a {@link GSSegment}
 * and reconciles it via the transaction-set count and matching group control number.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>GE01 = number of transaction sets included in the group</li>
 *     <li>GE02 = group control number (must match the GS06 of the paired GS segment)</li>
 * </ul>
 */
@Getter
abstract public class GESegment extends Segment {
    public static final String SEGMENT_ID = "GE";
    /** GE01 — number of transaction sets included in the functional group. */
    protected final String ge01;
    /** GE02 — group control number, matching the paired GS06. */
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

    /** @return GE01 — number of transaction sets included in the group. */
    public String getNumberOfTransactionSets() {
        return getGe01();
    }

    /** @return GE02 — group control number. */
    public String getGroupControlNumber() {
        return getGe02();
    }

    /**
     * Abstract builder for GE segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends GESegment.AbstractBuilder<T>> {
        private String ge01;
        private String ge02;

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the GE segment.
         *
         * @return a new {@link GESegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract GESegment build() throws ValidationException;

        /** Sets GE01 (number of transaction sets). */
        public T setGe01(String value) {
            this.ge01 = value;
            return self();
        }

        /** Element alias for {@link #setGe01(String)}. */
        public T setNumberOfTransactionSets(String value) {
            return setGe01(value);
        }

        /** Sets GE02 (group control number). */
        public T setGe02(String value) {
            this.ge02 = value;
            return self();
        }

        /** Element alias for {@link #setGe02(String)}. */
        public T setGroupControlNumber(String value) {
            return setGe02(value);
        }
    }
}