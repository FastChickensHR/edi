/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.common.segments.N4Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents a Member Residence City, State, and Zip Code segment in X834 EDI format
 * Implements city name (N401), state/province code (N402), and postal code (N403)
 */
@Getter
public class MemberMailingCityStateZipCode extends N4Segment {

    private MemberMailingCityStateZipCode(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for MemberResidenceCityStateZipCode
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberMailingCityStateZipCode build() throws ValidationException {
            return new MemberMailingCityStateZipCode(this);
        }
    }

    /**
     * Creates a new Builder instance
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}