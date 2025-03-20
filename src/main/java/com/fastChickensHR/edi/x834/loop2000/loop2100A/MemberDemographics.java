/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.common.DMGSegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents Member Demographics information in X834 EDI format
 */
@Getter
public class MemberDemographics extends DMGSegment {

    private MemberDemographics(Builder builder) throws ValidationException {
        super(builder);
    }

    public static class Builder extends AbstractBuilder<Builder> {

        protected void validate() throws ValidationException {
            // At minimum, the format qualifier and birth date are required
            if (dmg01 == null || dmg01.isEmpty()) {
                throw new ValidationException("Date Time Period Format Qualifier (DMG01) is required");
            }
            if (dmg02 == null || dmg02.isEmpty()) {
                throw new ValidationException("Birth Date (DMG02) is required");
            }
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberDemographics build() throws ValidationException {
            validate();
            return new MemberDemographics(this);
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