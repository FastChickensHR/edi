/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.util.TextUtils;
import com.fastChickensHR.edi.x834.data.*;
import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.dates.DateFormatter;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.ISASegment;
import com.fastChickensHR.edi.x834.X834Context;
import lombok.Getter;

/**
 * Represents the Interchange Control Header (ISA) segment specific to the X834 format
 * (005010X220A1). The ISA is the outermost segment of the interchange envelope and is
 * paired with the closing IEA. Extends the generic ISA segment with X834-specific defaults.
 */
@Getter
public class InterchangeControlHeader extends ISASegment {
    private final X834Context context;
    /** Default ISA01 authorization information qualifier {@code "00"} (No authorization information present). */
    public static final AuthorizationInformationQualifier DEFAULT_AUTHORIZATION_INFO_QUALIFIER = AuthorizationInformationQualifier.fromString("00");
    /** Default ISA02 authorization information: 10 spaces (fixed-width, no data). */
    public static final String DEFAULT_AUTHORIZATION_INFO = TextUtils.spaces(10);
    /** Default ISA03 security information qualifier {@code "00"} (No security information present). */
    public static final SecurityInformationQualifier DEFAULT_SECURITY_INFO_QUALIFIER = SecurityInformationQualifier.fromString("00");
    /** Default ISA04 security information: 10 spaces (fixed-width, no data). */
    public static final String DEFAULT_SECURITY_INFO = TextUtils.spaces(10);
    /** Default ISA05 interchange sender ID qualifier {@code "30"} (U.S. Federal Tax Identification Number). */
    public static final InterchangeIdQualifier DEFAULT_INTERCHANGE_SENDER_QUALIFIER = InterchangeIdQualifier.fromString("30");
    /** Default ISA07 interchange receiver ID qualifier {@code "ZZ"} (Mutually Defined). */
    public static final InterchangeIdQualifier DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER = InterchangeIdQualifier.fromString("ZZ");
    /** Default ISA11 repetition separator {@code "^"}. */
    public static final String DEFAULT_REPETITION_SEPARATOR = "^";
    /** Default ISA12 interchange control version number {@code "00501"} (5010). */
    public static final InterchangeControlVersionNumber DEFAULT_INTERCHANGE_CONTROL_VERSION = InterchangeControlVersionNumber.fromString("00501");
    /** Default ISA14 acknowledgment requested flag {@code "0"} (No interchange acknowledgment requested). */
    public static final AcknowledgmentRequested DEFAULT_ACKNOWLEDGMENT_REQUESTED = AcknowledgmentRequested.fromString("0");
    /** Default ISA15 usage indicator {@code "T"} (Test). */
    public static final InterchangeUsageIndicator DEFAULT_USAGE_INDICATOR = InterchangeUsageIndicator.fromString("T");
    /** Default ISA16 component element separator {@code ":"}. */
    public static final String DEFAULT_COMPONENT_ELEMENT_SEPARATOR = ":";

    protected InterchangeControlHeader(Builder builder) throws ValidationException {
        super(builder);
        this.context = builder.context;
    }

    /**
     * Builder for the InterchangeControlHeader.
     */
    public static class Builder extends ISASegment.AbstractBuilder<Builder> {
        protected X834Context context;

        /**
         * Constructor that initializes the builder with context information
         *
         * @param context The X834 context containing document-level information
         */
        public Builder(X834Context context) {
            super();
            this.context = context;
            this.isa01 = DEFAULT_AUTHORIZATION_INFO_QUALIFIER;
            this.isa02 = DEFAULT_AUTHORIZATION_INFO;
            this.isa03 = DEFAULT_SECURITY_INFO_QUALIFIER;
            this.isa04 = DEFAULT_SECURITY_INFO;
            this.isa05 = DEFAULT_INTERCHANGE_SENDER_QUALIFIER;
            this.setIsa06(context.getSenderID());
            this.isa07 = DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER;
            this.setIsa08(context.getReceiverID());
            // ISA09 is the fixed 6-digit YYMMDD interchange date, independent of the
            // document's D8 (CCYYMMDD) format used by GS04/BGN03/DTP.
            this.isa09 = DateFormatter.formatDate(DateFormat.D6, context.getDocumentDate());
            this.isa10 = context.getFormattedDocumentTime();
            this.isa11 = DEFAULT_REPETITION_SEPARATOR;
            this.isa12 = DEFAULT_INTERCHANGE_CONTROL_VERSION;
            this.isa13 = context.getInterchangeControlNumber();
            this.isa14 = (context.getAcknowledgmentRequested() != null && !context.getAcknowledgmentRequested().isBlank())
                    ? AcknowledgmentRequested.fromString(context.getAcknowledgmentRequested())
                    : DEFAULT_ACKNOWLEDGMENT_REQUESTED;
            this.isa15 = DEFAULT_USAGE_INDICATOR;
            this.isa16 = DEFAULT_COMPONENT_ELEMENT_SEPARATOR;
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Builds a new InterchangeControlHeader instance.
         *
         * @return A new InterchangeControlHeader instance
         * @throws ValidationException if validation fails
         */
        @Override
        public InterchangeControlHeader build() throws ValidationException {
            return new InterchangeControlHeader(this);
        }
    }
}