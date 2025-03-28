/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.common.segments.N3Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents a Member Residence Street Address segment in X834 EDI format
 * Implements address line 1 (N301) and address line 2 (N302)
 */
@Getter
public class MemberResidenceStreetAddress extends N3Segment {

    private MemberResidenceStreetAddress(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for MemberResidenceStreetAddress
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberResidenceStreetAddress build() throws ValidationException {
            return new MemberResidenceStreetAddress(this);
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