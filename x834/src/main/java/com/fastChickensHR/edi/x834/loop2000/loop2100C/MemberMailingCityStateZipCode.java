/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.x834.segments.N4Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents a Member Mailing City, State, and Zip Code (N4) segment in Loop 2100C of the X834 format.
 * Implements city name (N401), state/province code (N402), and postal code (N403).
 */
@Getter
public class MemberMailingCityStateZipCode extends N4Segment {

    private MemberMailingCityStateZipCode(Builder builder) throws ValidationException {
        super(builder);
        // Mutual pairing (X12 N4 syntax rule P0506): Location Qualifier (N405) and Location
        // Identifier (N406) must either both be present or both be absent.
        boolean hasQualifier = n405 != null && !n405.trim().isEmpty();
        boolean hasIdentifier = n406 != null && !n406.trim().isEmpty();
        if (hasQualifier != hasIdentifier) {
            throw new ValidationException(
                    "Location Qualifier (N405) and Location Identifier (N406) must be provided together");
        }
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