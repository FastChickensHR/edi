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
 * Represents the IEA (Interchange Control Trailer) segment in the X12 834
 * (005010X220A1) format.
 * <p>
 * This segment closes the interchange envelope opened by an {@link ISASegment} and
 * reconciles it via the count of functional groups and the matching interchange
 * control number.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>IEA01 = number of included functional groups (required)</li>
 *     <li>IEA02 = interchange control number (required; matches the ISA13 of the paired ISA)</li>
 * </ul>
 */
@Getter
abstract public class IEASegment extends Segment {
    public static final String SEGMENT_ID = "IEA";
    /** IEA01 — number of functional groups included in the interchange. */
    protected final String iea01; // Number of Included Functional Groups
    /** IEA02 — interchange control number, matching the paired ISA13. */
    protected final String iea02; // Interchange Control Number

    protected IEASegment(IEASegment.AbstractBuilder<?> builder) throws ValidationException {
        this.iea01 = builder.iea01;
        this.iea02 = builder.iea02;
        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (iea01 == null || iea01.isBlank()) {
            throw new ValidationException("IEA01 (Number of Included Functional Groups) cannot be blank");
        }
        if (iea02 == null || iea02.isBlank()) {
            throw new ValidationException("IEA02 (Interchange Control Number) cannot be blank");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{this.iea01, this.iea02};
    }

    /** @return IEA01 — number of included functional groups. */
    public String getNumberOfIncludedGroups() {
        return getIea01();
    }

    /** @return IEA02 — interchange control number. */
    public String getInterchangeControlNumber() {
        return getIea02();
    }

    /**
     * Abstract builder for IEA segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends IEASegment.AbstractBuilder<T>> {
        private String iea01;
        private String iea02;

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the IEA segment.
         *
         * @return a new {@link IEASegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract IEASegment build() throws ValidationException;

        /** Sets IEA01 (number of included functional groups). */
        public T setIea01(String value) {
            this.iea01 = value;
            return self();
        }

        /** Element alias for {@link #setIea01(String)}. */
        public T setNumberOfIncludedGroups(String value) {
            return setIea01(value);
        }

        /** Sets IEA02 (interchange control number). */
        public T setIea02(String value) {
            this.iea02 = value;
            return self();
        }

        /** Element alias for {@link #setIea02(String)}. */
        public T setInterchangeControlNumber(String value) {
            return setIea02(value);
        }
    }
}