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
 * Represents the LS (Loop Header) segment in the X12 834 (005010X220A1)
 * Benefit Enrollment and Maintenance transaction.
 * <p>
 * LS opens a bounded loop and is closed by a matching {@link LESegment}; both
 * carry the same loop identifier code so the pair brackets the enclosed
 * occurrences. In the 834 this brackets Loop 2700 (Member Reporting Categories).
 * <p>
 * Element/position map:
 * <ul>
 *     <li>LS01 = loop identifier code — the loop this header opens (e.g. "2700")</li>
 * </ul>
 */
@Getter
public class LSSegment extends Segment {
    public static final String SEGMENT_ID = "LS";

    /** LS01 — loop identifier code naming the loop this header opens. */
    protected final String ls01;

    private LSSegment(Builder builder) throws ValidationException {
        this.ls01 = builder.ls01;
        validate();
    }

    private void validate() throws ValidationException {
        if (ls01 == null || ls01.trim().isEmpty()) {
            throw new ValidationException("LS01 (Loop Identifier Code) is required");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{ls01};
    }

    /** @return LS01 — loop identifier code. */
    public String getLoopIdentifierCode() {
        return getLs01();
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link LSSegment}. */
    public static class Builder {
        private String ls01;

        /** Sets LS01 (loop identifier code). */
        public Builder setLoopIdentifierCode(String value) {
            this.ls01 = value;
            return this;
        }

        /** Element alias for {@link #setLoopIdentifierCode(String)}. */
        public Builder setLs01(String value) {
            return setLoopIdentifierCode(value);
        }

        /**
         * Builds and validates the LS segment.
         *
         * @return a new {@link LSSegment}
         * @throws ValidationException if validation fails
         */
        public LSSegment build() throws ValidationException {
            return new LSSegment(this);
        }
    }
}
