/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.segments.DMGSegment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Member demographics as a DMG segment in Loop 2100A of the X12 834.
 * <p>
 * Carries the member's demographic detail — the date format qualifier (DMG01)
 * and birth date (DMG02) are required; gender and related elements follow.
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