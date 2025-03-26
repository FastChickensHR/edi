/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.ISASegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;

/**
 * Represents the Interchange Control Header segment specific to the X834 format.
 * This extends the generic ISA segment with X834-specific functionality.
 */
@Getter
public class InterchangeControlHeader extends ISASegment {
    private final x834Context context;

    protected InterchangeControlHeader(Builder builder) throws ValidationException {
        super(builder);
        this.context = builder.context;
    }

    /**
     * Builder for the InterchangeControlHeader.
     */
    public static class Builder extends ISASegment.AbstractBuilder<Builder> {
        protected x834Context context;

        /**
         * Constructor that initializes the builder with context information
         * @param context The X834 context containing document-level information
         */
        public Builder(x834Context context) {
            super(context);
            this.context = context;
        }

        /**
         * Default constructor
         */
        public Builder() {
            super();
        }

        /**
         * Sets the x834 document context.
         * @param context The context to use
         * @return This builder instance for method chaining
         */
        @Override
        public Builder context(x834Context context) {
            super.context(context);
            this.context = context;
            return self();
        }

        @Override
        public InterchangeControlHeader build() throws ValidationException {
            return new InterchangeControlHeader(this);
        }
    }
}