/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.x834.common.DTPSegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.HealthCoverageDateQualifier;
import lombok.Getter;

/**
 * Represents health coverage dates information in Loop 3000 of the X834 format.
 * This extends the DTP segment to provide specific date information related to health coverage.
 */
@Getter
public class HealthCoverageDates extends DTPSegment {

    private HealthCoverageDates(Builder builder) throws ValidationException {
        super(builder);
        validate();
    }

    /**
     * Validates the health coverage dates segment data.
     *
     * @throws ValidationException if validation fails
     */
    protected void validate() throws ValidationException {
        if (dtp01 == null || dtp01.trim().isEmpty()) {
            throw new ValidationException("Date Time Qualifier (DTP01) is required for Health Coverage Dates");
        }
        if (dtp02 == null || dtp02.trim().isEmpty()) {
            throw new ValidationException("Date Time Format (DTP02) is required for Health Coverage Dates");
        }
        if (dtp03 == null || dtp03.trim().isEmpty()) {
            throw new ValidationException("Date Time Period (DTP03) is required for Health Coverage Dates");
        }
    }

    /**
     * Builder for HealthCoverageDates.
     */
    public static class Builder extends AbstractBuilder<Builder> {

        public Builder(x834Context context) {
            super(context);
        }

        public Builder() {
            super(null);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public HealthCoverageDates build() throws ValidationException {
            return new HealthCoverageDates(this);
        }
    }

    /**
     * Creates a new Builder instance.
     *
     * @return a new Builder with no context
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new Builder instance with the provided context.
     *
     * @param context the X834 context
     * @return a new Builder with the specified context
     */
    public static Builder builder(x834Context context) {
        return new Builder(context);
    }

    /**
     * Gets the health coverage date qualifier as an enum if possible.
     *
     * @return the qualifier as enum, or null if not a valid qualifier
     */
    public HealthCoverageDateQualifier getQualifierAsEnum() {
        try {
            return HealthCoverageDateQualifier.fromCode(dtp01);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}