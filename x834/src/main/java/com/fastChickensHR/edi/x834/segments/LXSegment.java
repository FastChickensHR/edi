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
 * Represents the LX (Assigned Number) segment in the X12 834 (005010X220A1)
 * Benefit Enrollment and Maintenance transaction.
 * <p>
 * LX carries a sequential number that distinguishes one occurrence of a
 * repeating loop from the next. In the 834 it opens each occurrence of Loop
 * 2710 within the Member Reporting Categories block (Loop 2700), so the number
 * is assigned at render time over the occurrences actually emitted.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>LX01 = assigned number — a positive sequential counter (max 6 digits)</li>
 * </ul>
 */
@Getter
public class LXSegment extends Segment {
    public static final String SEGMENT_ID = "LX";

    /** LX01 — assigned number distinguishing this loop occurrence. */
    protected final int lx01;

    private LXSegment(Builder builder) throws ValidationException {
        this.lx01 = builder.lx01;
        validate();
    }

    private void validate() throws ValidationException {
        if (lx01 < 1) {
            throw new ValidationException("LX01 (Assigned Number) must be a positive integer");
        }
        if (lx01 > 999999) {
            throw new ValidationException("LX01 (Assigned Number) must be 6 digits or fewer");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{Integer.toString(lx01)};
    }

    /** @return LX01 — assigned number. */
    public int getAssignedNumber() {
        return getLx01();
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link LXSegment}. */
    public static class Builder {
        private int lx01;

        /** Sets LX01 (assigned number). */
        public Builder setAssignedNumber(int value) {
            this.lx01 = value;
            return this;
        }

        /** Element alias for {@link #setAssignedNumber(int)}. */
        public Builder setLx01(int value) {
            return setAssignedNumber(value);
        }

        /**
         * Builds and validates the LX segment.
         *
         * @return a new {@link LXSegment}
         * @throws ValidationException if validation fails
         */
        public LXSegment build() throws ValidationException {
            return new LXSegment(this);
        }
    }
}
