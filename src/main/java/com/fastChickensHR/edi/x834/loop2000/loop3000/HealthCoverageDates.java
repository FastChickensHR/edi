/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.common.segments.DTPSegment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
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
        if (!dtp02.equals("D8") && !dtp02.equals("RD8")) {
            throw new ValidationException("Health Coverage Dates must have a Date Time Format (DTP02) of D8 or RD8");
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
}