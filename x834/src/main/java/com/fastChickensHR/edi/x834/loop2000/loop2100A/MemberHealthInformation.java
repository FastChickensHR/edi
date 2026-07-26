/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.HLHSegment;
import lombok.Getter;

/**
 * The HLH (Health Information) segment in Loop 2100A of the X12 834 (005010X220A1) — the member's
 * health-related status.
 * <p>
 * Nothing is defaulted. BCBSM's tobacco use renders as {@code HLH*T~}; a member stated to use
 * neither tobacco nor substances as {@code HLH*N~}. Those are different answers from sending no
 * HLH at all, which says only that nobody asked.
 */
@Getter
public class MemberHealthInformation extends HLHSegment {

    private MemberHealthInformation(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberHealthInformation}. */
    public static class Builder extends HLHSegment.AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberHealthInformation build() throws ValidationException {
            return new MemberHealthInformation(this);
        }
    }
}
