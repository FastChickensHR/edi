/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000C;

import com.fastChickensHR.edi.x834.segments.N1Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Loop 1000C third-party administrator name (N1*TV) in the X12 834 (005010X220A1).
 * <p>
 * Produces the N1 segment that identifies the TPA/broker in the header portion of the
 * 834, following the payer (Loop 1000B). Extends the generic {@link N1Segment}.
 */
@Getter
public class TPA extends N1Segment {
    /** Default N101 entity identifier code {@code "TV"} (Third Party Administrator). */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "TV";

    private TPA(Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder instance for TPA.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Domain-specific accessor for the TPA name (N102).
     *
     * @return the TPA name
     */
    public String getTPAName() {
        return getN102();
    }

    /**
     * Domain-specific accessor for the TPA identifier (N104).
     *
     * @return the TPA identification code
     */
    public String getTPAIdentifier() {
        return getN104();
    }

    /**
     * Builder for TPA. Pre-seeds the third-party-administrator entity code (N101=TV) default.
     */
    @Setter
    @Accessors(chain = true)
    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            this.setN101(DEFAULT_ENTITY_IDENTIFIER_CODE);
        }

        /**
         * Domain-specific setter for the TPA name (N102).
         *
         * @param value the TPA name
         * @return this builder instance
         */
        public Builder setTPAName(String value) {
            return setN102(value);
        }

        /**
         * Domain-specific setter for the TPA identifier (N104).
         *
         * @param value the TPA identification code
         * @return this builder instance
         */
        public Builder setTPAIdentifier(String value) {
            return setN104(value);
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Builds a new TPA instance.
         *
         * @return A new TPA instance
         * @throws ValidationException if validation fails
         */
        @Override
        public TPA build() throws ValidationException {
            return new TPA(this);
        }
    }
}