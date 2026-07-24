/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000A;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.N1Segment;
import lombok.experimental.Accessors;

/**
 * Loop 1000A plan sponsor name (N1*P5) in the X12 834 (005010X220A1).
 * <p>
 * Produces the N1 segment that identifies the plan sponsor (typically the employer)
 * in the header portion of the 834, after the transaction set header/BGN and before
 * the payer (Loop 1000B). Extends the generic {@link N1Segment}.
 */
public class SponsorName extends N1Segment {
    /** Default N101 entity identifier code {@code "P5"} (Plan Sponsor). */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "P5";
    /** Default N103 identification code qualifier {@code "FI"} (Federal Taxpayer's Identification Number). */
    public static final String DEFAULT_IDENTIFICATION_CODE_QUALIFIER = "FI";

    private SponsorName(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for SponsorName.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for SponsorName. Pre-seeds only the plan-sponsor entity code (N101=P5).
     * <p>
     * The identification qualifier (N103={@value #DEFAULT_IDENTIFICATION_CODE_QUALIFIER}) is
     * <em>not</em> pre-seeded: under X12 rule P0304 the qualifier and the identifier (N104) must
     * travel together, so a caller that supplies a sponsor identifier is responsible for supplying
     * the matching qualifier alongside it. A name-only sponsor (the common case — the header never
     * assigns the sponsor an id) therefore emits {@code N1*P5*<name>} with no dangling qualifier.
     */
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setN101(DEFAULT_ENTITY_IDENTIFIER_CODE);
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Builds a new SponsorName instance.
         *
         * @return A new SponsorName instance
         * @throws ValidationException if validation fails
         */
        @Override
        public SponsorName build() throws ValidationException {
            return new SponsorName(this);
        }
    }
}