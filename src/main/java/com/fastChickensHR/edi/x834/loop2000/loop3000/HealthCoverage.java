/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.x834.common.HDSegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents health coverage information in Loop 3000 of the X834 format.
 * This extends the HD segment to provide specific health coverage details.
 */
@Getter
public class HealthCoverage extends HDSegment {

    private HealthCoverage(Builder builder) throws ValidationException {
        super(builder);
        validate();
    }

    /**
     * Validates the health coverage segment data.
     *
     * @throws ValidationException if validation fails
     */
    protected void validate() throws ValidationException {
        if (hd01 == null || hd01.trim().isEmpty()) {
            throw new ValidationException("Maintenance Type Code (HD01) is required for Health Coverage");
        }
        if (hd03 == null || hd03.trim().isEmpty()) {
            throw new ValidationException("Insurance Line Code (HD03) is required for Health Coverage");
        }
    }

    /**
     * Builder for HealthCoverage.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public HealthCoverage build() throws ValidationException {
            return new HealthCoverage(this);
        }
    }

    /**
     * Creates a new Builder instance.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}