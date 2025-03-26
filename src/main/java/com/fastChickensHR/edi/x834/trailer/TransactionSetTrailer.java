/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.common.segments.SESegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the Transaction Set Trailer in an EDI 834 transaction.
 * This class extends the SESegment (SE - Transaction Set Trailer segment).
 */
@Getter
public class TransactionSetTrailer extends SESegment {

    private TransactionSetTrailer(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for TransactionSetTrailer.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for TransactionSetTrailer.
     */
    @Setter
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public TransactionSetTrailer build() throws ValidationException {
            return new TransactionSetTrailer(this);
        }
    }
}