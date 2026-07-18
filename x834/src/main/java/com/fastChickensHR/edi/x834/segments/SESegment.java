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

/**
 * Represents the SE (Transaction Set Trailer) segment in the X12 834
 * (005010X220A1) format.
 * <p>
 * This segment closes a transaction set opened by an {@link STSegment} and
 * reconciles it via the count of included segments and the matching control number.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>SE01 = number of segments included in the transaction set (inclusive of ST and SE)</li>
 *     <li>SE02 = transaction set control number (matches the ST02 of the paired ST segment)</li>
 * </ul>
 */
@Getter
abstract public class SESegment extends Segment {
    public static final String SEGMENT_ID = "SE";
    /** SE01 — number of segments included in the transaction set. */
    protected final String se01;
    /** SE02 — transaction set control number, matching the paired ST02. */
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

    /** @return SE01 — number of segments in the transaction set. */
    public String getTransactionSegmentCount() {
        return getSe01();
    }

    /** @return SE02 — transaction set control number. */
    public String getSetControlNumber() {
        return getSe02();
    }


    /**
     * Abstract builder for SE segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    public abstract static class AbstractBuilder<T extends SESegment.AbstractBuilder<T>> {
        private String se01;
        private String se02;

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the SE segment.
         *
         * @return a new {@link SESegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract SESegment build() throws ValidationException;

        /** Sets SE01 (number of segments in the transaction set). */
        public T setSe01(String value) {
            this.se01 = value;
            return self();
        }

        /** Element alias for {@link #setSe01(String)}. */
        public T setTransactionSegmentCount(String value) {
            return setSe01(value);
        }

        /** Sets SE02 (transaction set control number). */
        public T setSe02(String value) {
            this.se02 = value;
            return self();
        }

        /** Element alias for {@link #setSe02(String)}. */
        public T setSetControlNumber(String value) {
            return setSe02(value);
        }
    }
}