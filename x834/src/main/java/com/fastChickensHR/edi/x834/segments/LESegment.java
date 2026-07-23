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
 * Represents the LE (Loop Trailer) segment in the X12 834 (005010X220A1)
 * Benefit Enrollment and Maintenance transaction.
 * <p>
 * LE closes a bounded loop opened by a matching {@link LSSegment}; both carry
 * the same loop identifier code. In the 834 this closes Loop 2700 (Member
 * Reporting Categories).
 * <p>
 * Element/position map:
 * <ul>
 *     <li>LE01 = loop identifier code — the loop this trailer closes (e.g. "2700")</li>
 * </ul>
 */
@Getter
public class LESegment extends Segment {
    public static final String SEGMENT_ID = "LE";

    /** LE01 — loop identifier code naming the loop this trailer closes. */
    protected final String le01;

    private LESegment(Builder builder) throws ValidationException {
        this.le01 = builder.le01;
        validate();
    }

    private void validate() throws ValidationException {
        if (le01 == null || le01.trim().isEmpty()) {
            throw new ValidationException("LE01 (Loop Identifier Code) is required");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{le01};
    }

    /** @return LE01 — loop identifier code. */
    public String getLoopIdentifierCode() {
        return getLe01();
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link LESegment}. */
    public static class Builder {
        private String le01;

        /** Sets LE01 (loop identifier code). */
        public Builder setLoopIdentifierCode(String value) {
            this.le01 = value;
            return this;
        }

        /** Element alias for {@link #setLoopIdentifierCode(String)}. */
        public Builder setLe01(String value) {
            return setLoopIdentifierCode(value);
        }

        /**
         * Builds and validates the LE segment.
         *
         * @return a new {@link LESegment}
         * @throws ValidationException if validation fails
         */
        public LESegment build() throws ValidationException {
            return new LESegment(this);
        }
    }
}
