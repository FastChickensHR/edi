/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.common.NM1Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents a Member Residence Street Address segment in X834 EDI format
 * Currently implementing only the 01 and 02 elements
 */
@Getter
public class MemberResidenceStreetAddress extends NM1Segment {

    /**
     * Entity Identifier Code (NM101) values for Member Residence Street Address
     */
    @Getter
    public enum EntityIdentifierCode {
        POSTAL_MAILING_ADDRESS("31", "Postal Mailing Address");

        private final String code;
        private final String description;

        EntityIdentifierCode(String code, String description) {
            this.code = code;
            this.description = description;
        }

    }

    private MemberResidenceStreetAddress(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Builder for MemberResidenceStreetAddress
     */
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setNm101(EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getCode());
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public MemberResidenceStreetAddress build() throws ValidationException {
            return new MemberResidenceStreetAddress(this);
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