/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000B;

import com.fastChickensHR.edi.x834.segments.N1Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.experimental.Accessors;

/**
 * Loop 1000B payer name (N1*IN) in the X12 834 (005010X220A1).
 * <p>
 * Produces the N1 segment that identifies the payer (insurer) in the header portion
 * of the 834, following the plan sponsor (Loop 1000A). Extends the generic {@link N1Segment}.
 */
public class Payer extends N1Segment {
    /** Default N101 entity identifier code {@code "IN"} (Insurer). */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "IN";
    /** Default N103 identification code qualifier {@code "FI"} (Federal Taxpayer's Identification Number). */
    public static final String DEFAULT_IDENTIFICATION_CODE_QUALIFIER = "FI";

    private Payer(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for Payer.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for Payer. Pre-seeds the insurer entity code (N101=IN) and
     * identification qualifier (N103=FI) defaults.
     */
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

        /**
         * Builds a new Payer instance.
         *
         * @return A new Payer instance
         * @throws ValidationException if validation fails
         */
        @Override
        public Payer build() throws ValidationException {
            return new Payer(this);
        }
    }
}