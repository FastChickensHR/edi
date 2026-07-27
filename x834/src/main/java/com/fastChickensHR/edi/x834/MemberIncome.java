/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * The ICM (Individual Income) segment in Loop 2100A of the X12 834 (005010X220A1) — what the member
 * earns.
 * <p>
 * Nothing is defaulted. A frequency guessed wrong misstates someone's pay by an order of magnitude,
 * and a currency assumed is a currency unstated, so both are supplied by the caller or absent.
 * BCBS Kansas's use renders as {@code ICM*4*4500**DEPT42~} — a monthly wage, with the department
 * number riding ICM04.
 */
@Getter
class MemberIncome extends ICMSegment {

    private MemberIncome(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberIncome}. */
    public static class Builder extends ICMSegment.AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberIncome build() throws ValidationException {
            return new MemberIncome(this);
        }
    }
}
