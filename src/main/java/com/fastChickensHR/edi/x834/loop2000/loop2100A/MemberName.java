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
import lombok.experimental.Accessors;

/**
 * Represents the NM1 segment for a member name in the 2100A loop.
 * This contains identifying information about the member including
 * name and identification details.
 */
public class MemberName extends NM1Segment {
    public static final String ENTITY_IDENTIFIER_CODE = "IL";
    public static final String PERSON_ENTITY_TYPE = "1";

    protected MemberName(Builder builder) throws ValidationException {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends NM1Segment.AbstractBuilder<MemberName.Builder> {
        public Builder() {
            this.nm101 = ENTITY_IDENTIFIER_CODE;
            this.nm102 = PERSON_ENTITY_TYPE;
        }

        @Override
        protected MemberName.Builder self() {
            return this;
        }

        @Override
        public MemberName build() throws ValidationException {

            validateRequiredFields();
            return new MemberName(this);
        }

        private void validateRequiredFields() throws ValidationException {
            if (nm101 == null || nm101.isEmpty()) {
                throw new ValidationException("Entity Identifier Code (NM101) is required");
            }

            if (nm102 == null || nm102.isEmpty()) {
                throw new ValidationException("Entity Type Qualifier (NM102) is required");
            }
        }
    }
}