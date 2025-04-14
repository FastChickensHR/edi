/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000B;

import com.fastChickensHR.edi.common.segments.N1Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.experimental.Accessors;

public class Payer extends N1Segment {
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "IN";
    public static final String DEFAULT_IDENTIFICATION_CODE_QUALIFIER = "FI";

    private Payer(Builder builder) throws ValidationException {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setN101(DEFAULT_ENTITY_IDENTIFIER_CODE);
            this.setN103(DEFAULT_IDENTIFICATION_CODE_QUALIFIER);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Payer build() throws ValidationException {
            return new Payer(this);
        }
    }
}