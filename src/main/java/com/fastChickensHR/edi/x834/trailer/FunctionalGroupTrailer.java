/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.common.GESegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the Functional Group Trailer in an EDI 834 transaction.
 * This class extends the GESegment (GE - Functional Group Trailer segment).
 */
@Getter
public class FunctionalGroupTrailer extends GESegment {

    private FunctionalGroupTrailer(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for FunctionalGroupTrailer.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for FunctionalGroupTrailer.
     */
    @Setter
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public FunctionalGroupTrailer build() throws ValidationException {
            return new FunctionalGroupTrailer(this);
        }
    }
}