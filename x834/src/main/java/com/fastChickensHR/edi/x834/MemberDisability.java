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
 * The DSB segment opening Loop 2200 of the X12 834 (005010X220A1) — the member's disability status.
 * <p>
 * Nothing is defaulted: {@code 4} (No Disability) is a stated answer, not the absence of one, and
 * assuming it would tell a carrier something the sponsor never said.
 */
@Getter
class MemberDisability extends DSBSegment {

    private MemberDisability(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberDisability}. */
    public static class Builder extends DSBSegment.AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberDisability build() throws ValidationException {
            return new MemberDisability(this);
        }
    }
}
