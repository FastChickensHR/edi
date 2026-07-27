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
 * The PER (Member Communications Numbers) segment in Loop 2100A of the X12 834
 * (005010X220A1).
 * <p>
 * PER01 is fixed to {@link #INSURED_PARTY} — the member named by the 2100A {@code NM1} is the
 * contact — and PER02 (contact name) is left absent, since that member is already named by the
 * preceding {@code NM1}. What varies is the up-to-three qualifier/number pairs, added with
 * {@link PERSegment.AbstractBuilder#addCommunicationNumber}. A carrier requiring a home phone
 * and an email therefore renders as {@code PER*IP**HP*5551234567*EM*a@b.com~}.
 */
@Getter
class MemberCommunicationsNumbers extends PERSegment {

    /**
     * PER01 contact function code {@code IP} — Insured Party. The 834 names the member itself as
     * the contact for its own communication numbers.
     */
    public static final String INSURED_PARTY = "IP";

    private MemberCommunicationsNumbers(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberCommunicationsNumbers}; PER01 defaults to {@link #INSURED_PARTY}. */
    public static class Builder extends PERSegment.AbstractBuilder<Builder> {
        public Builder() {
            this.per01 = INSURED_PARTY;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberCommunicationsNumbers build() throws ValidationException {
            return new MemberCommunicationsNumbers(this);
        }
    }
}
