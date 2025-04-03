/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.common.data.ResponsibleAgencyCode;
import com.fastChickensHR.edi.common.data.VersionCode;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.GSSegment;
import com.fastChickensHR.edi.x834.x834Context;
import lombok.Getter;

/**
 * Represents the Functional Group Header segment specific to the X834 format.
 * This extends the generic GS segment with X834-specific functionality.
 */
@Getter
public class FunctionalGroupHeader extends GSSegment {
    public static final FunctionalIdentifierCode DEFAULT_FUNCTIONAL_ID_CODE = FunctionalIdentifierCode.fromString("BE");  // Benefits Enrollment
    public static final ResponsibleAgencyCode DEFAULT_RESPONSIBLE_AGENCY_CODE = ResponsibleAgencyCode.ASC_X12;
    public static final VersionCode DEFAULT_VERSION_CODE = VersionCode.fromString("005010X220A1");

    private final x834Context context;

    protected FunctionalGroupHeader(Builder builder) throws ValidationException {
        super(builder);
        this.context = builder.context;
    }

    /**
     * Builder for the FunctionalGroupHeader.
     */
    public static class Builder extends GSSegment.AbstractBuilder<Builder> {
        protected x834Context context;

        /**
         * Constructor that initializes the builder with context information
         *
         * @param context The X834 context containing document-level information
         */
        public Builder(x834Context context) {
            super();
            this.context = context;
            this.gs01 = DEFAULT_FUNCTIONAL_ID_CODE;
            this.gs02 = context.getSenderID();
            this.gs03 = context.getReceiverID();
            this.gs04 = context.getFormattedDocumentDate();
            this.gs05 = context.getFormattedDocumentTime();
            this.gs06 = context.getGroupControlNumber();
            this.gs07 = DEFAULT_RESPONSIBLE_AGENCY_CODE;
            this.gs08 = DEFAULT_VERSION_CODE;
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Builds a new FunctionalGroupHeader instance
         *
         * @return A new FunctionalGroupHeader instance
         * @throws ValidationException if validation fails
         */
        public FunctionalGroupHeader build() throws ValidationException {
            return new FunctionalGroupHeader(this);
        }
    }
}