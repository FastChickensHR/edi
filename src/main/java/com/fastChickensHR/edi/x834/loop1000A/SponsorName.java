/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000A;

import com.fastChickensHR.edi.x834.common.segments.N1Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.experimental.Accessors;

public class SponsorName extends N1Segment {
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "P5";
    public static final String DEFAULT_IDENTIFICATION_CODE_QUALIFIER = "FI";

    private SponsorName(Builder builder) throws ValidationException {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.n101 = DEFAULT_ENTITY_IDENTIFIER_CODE;
            this.n103 = DEFAULT_IDENTIFICATION_CODE_QUALIFIER;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SponsorName build() throws ValidationException {
            return new SponsorName(this);
        }
    }
}