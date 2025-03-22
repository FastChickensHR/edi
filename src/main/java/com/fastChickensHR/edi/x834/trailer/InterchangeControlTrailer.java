/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.common.IEASegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the Interchange Control Trailer in an EDI 834 transaction.
 * This class extends the IEASegment (IEA - Interchange Control Trailer segment).
 * It marks the end of an interchange envelope and contains information about
 * the number of functional groups included and the interchange control number.
 */
@Getter
public class InterchangeControlTrailer extends IEASegment {

    private InterchangeControlTrailer(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for InterchangeControlTrailer.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for InterchangeControlTrailer.
     */
    @Setter
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public InterchangeControlTrailer build() throws ValidationException {
            return new InterchangeControlTrailer(this);
        }
    }
}