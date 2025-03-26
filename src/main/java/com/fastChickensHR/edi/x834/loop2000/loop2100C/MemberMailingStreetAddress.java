/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.x834.common.segments.N3Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the member's mailing street address in Loop 2100C.
 * This extends the N3 segment to provide address information for a member.
 */
@Getter
public class MemberMailingStreetAddress extends N3Segment {

    private MemberMailingStreetAddress(Builder builder) throws ValidationException {
        super(builder);
        validate();
    }

    protected void validate() throws ValidationException {
        if (n301 == null || n301.trim().isEmpty()) {
            throw new ValidationException("Address Line 1 (N301) is required for Member Mailing Street Address");
        }
        // N302 is optional, no validation required
    }

    /**
     * Builder for MemberMailingStreetAddress.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberMailingStreetAddress build() throws ValidationException {
            return new MemberMailingStreetAddress(this);
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