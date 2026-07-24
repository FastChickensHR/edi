/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.x834.segments.NM1Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Member mailing address as an NM1 segment in Loop 2100C of the X12 834.
 * <p>
 * NM101 defaults to the entity identifier {@code 31} (Postal Mailing Address) and NM102 to the
 * entity type qualifier {@code 1} (Person) — the member whose mailing address this is. NM102 is a
 * mandatory NM1 element (X12 element 1065), so it must be present even though the mailing NM1
 * carries no name; the segment previously rendered a bare {@code NM1*31~}.
 */
@Getter
public class MemberMailingAddress extends NM1Segment {

    /** NM102 entity type qualifier {@code 1} — Person; the fixed default for the mailing NM1. */
    public static final String PERSON_ENTITY_TYPE = "1";

    /**
     * Entity Identifier Code (NM101) values for the member mailing address.
     */
    @Getter
    public enum EntityIdentifierCode {
        /** NM101 {@code 31} — Postal Mailing Address; the default for this segment. */
        POSTAL_MAILING_ADDRESS("31", "Postal Mailing Address");

        private final String code;
        private final String description;

        EntityIdentifierCode(String code, String description) {
            this.code = code;
            this.description = description;
        }

    }

    private MemberMailingAddress(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for MemberMailingAddress.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setNm101(EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getCode());
            this.setNm102(PERSON_ENTITY_TYPE);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberMailingAddress build() throws ValidationException {
            return new MemberMailingAddress(this);
        }

        /**
         * Sets the Entity Identifier Code (NM101) using the enum value
         *
         * @param value The Entity Identifier Code enum value
         * @return this builder instance
         */
        public Builder setEntityIdentifierCode(EntityIdentifierCode value) {
            return setNm101(value.getCode());
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