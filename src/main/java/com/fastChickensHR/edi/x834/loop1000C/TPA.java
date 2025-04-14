/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000C;

import com.fastChickensHR.edi.common.segments.N1Segment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class TPA extends N1Segment {
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "TP";

    private TPA(Builder builder) throws ValidationException {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTPAName() {
        return getN102();
    }

    public String getTPAIdentifier() {
        return getN104();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setN101(DEFAULT_ENTITY_IDENTIFIER_CODE);
        }

        public Builder setTPAName(String value) {
            return setN102(value);
        }

        public Builder setTPAIdentifier(String value) {
            return setN104(value);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public TPA build() throws ValidationException {
            return new TPA(this);
        }
    }
}