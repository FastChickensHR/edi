/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.common.segments.N4Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the member's residence city, state, and zip code information in Loop 2100A.
 * This extends the N4 segment to provide geographic location information for a member's residence.
 */
@Getter
public class MemberResidenceCityStateZipCode extends N4Segment {

    private MemberResidenceCityStateZipCode(Builder builder) throws ValidationException {
        super(builder);
        validate();
    }

    protected void validate() throws ValidationException {
        if (n401 == null || n401.trim().isEmpty()) {
            throw new ValidationException("City Name (N401) is required for Member Residence City State Zip Code");
        }
        if (n402 == null || n402.trim().isEmpty()) {
            throw new ValidationException("State or Province Code (N402) is required for Member Residence City State Zip Code");
        }
        if (n403 == null || n403.trim().isEmpty()) {
            throw new ValidationException("Postal Code (N403) is required for Member Residence City State Zip Code");
        }
    }

    /**
     * Builder for MemberResidenceCityStateZipCode.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberResidenceCityStateZipCode build() throws ValidationException {
            return new MemberResidenceCityStateZipCode(this);
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