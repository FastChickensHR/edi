/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.x834.data.ResponsibleAgencyCode;
import com.fastChickensHR.edi.x834.data.VersionCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.GSSegment;
import com.fastChickensHR.edi.x834.X834Context;
import lombok.Getter;

/**
 * Represents the Functional Group Header (GS) segment specific to the X834 format
 * (005010X220A1). The GS opens the functional group inside the ISA/IEA interchange
 * envelope and precedes the transaction set header (ST). Extends the generic GS segment
 * with X834-specific defaults.
 */
@Getter
public class FunctionalGroupHeader extends GSSegment {
    /** Default GS01 functional identifier code {@code "BE"} (Benefit Enrollment and Maintenance, 834). */
    private static final FunctionalIdentifierCode DEFAULT_FUNCTIONAL_ID_CODE = FunctionalIdentifierCode.fromString("BE");
    /** Default GS07 responsible agency code (ASC X12). */
    private static final ResponsibleAgencyCode DEFAULT_RESPONSIBLE_AGENCY_CODE = ResponsibleAgencyCode.ASC_X12;
    /** Default GS08 version/release/industry identifier code {@code "005010X220A1"} (the 834 guide). */
    private static final VersionCode DEFAULT_VERSION_CODE = VersionCode.fromString("005010X220A1");

    private final X834Context context;

    protected FunctionalGroupHeader(Builder builder) throws ValidationException {
        super(builder);
        this.context = builder.context;
    }

    /**
     * Builder for the FunctionalGroupHeader.
     */
    public static class Builder extends GSSegment.AbstractBuilder<Builder> {
        protected X834Context context;

        /**
         * Constructor that initializes the builder with context information.
         *
         * <p>GS02/GS03 come from the context's {@link X834Context#getApplicationSenderCode()
         * applicationSenderCode} / {@link X834Context#getApplicationReceiverCode()
         * applicationReceiverCode}, each falling back to the corresponding interchange identifier
         * ({@link X834Context#getSenderID() senderID} / {@link X834Context#getReceiverID()
         * receiverID}) when unset. GS mirroring ISA is the common case, so callers that never set
         * the application codes emit exactly what they did before.
         *
         * @param context The X834 context containing document-level information
         */
        public Builder(X834Context context) {
            super();
            this.context = context;
            this.gs01 = DEFAULT_FUNCTIONAL_ID_CODE;
            this.gs02 = orFallback(context.getApplicationSenderCode(), context.getSenderID());
            this.gs03 = orFallback(context.getApplicationReceiverCode(), context.getReceiverID());
            this.gs04 = context.getFormattedDocumentDate();
            this.gs05 = context.getFormattedDocumentTime();
            this.gs06 = context.getGroupControlNumber();
            this.gs07 = DEFAULT_RESPONSIBLE_AGENCY_CODE;
            this.gs08 = DEFAULT_VERSION_CODE;
        }

        /** @return {@code value} when set (non-null, non-blank), otherwise {@code fallback}. */
        private static String orFallback(String value, String fallback) {
            return (value == null || value.isBlank()) ? fallback : value;
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