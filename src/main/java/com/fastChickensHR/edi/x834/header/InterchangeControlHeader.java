/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.TextUtils;
import com.fastChickensHR.edi.common.data.*;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.ISASegment;
import com.fastChickensHR.edi.x834.x834Context;
import lombok.Getter;

/**
 * Represents the Interchange Control Header segment specific to the X834 format.
 * This extends the generic ISA segment with X834-specific functionality.
 */
@Getter
public class InterchangeControlHeader extends ISASegment {
    private final x834Context context;
    public static final AuthorizationInformationQualifier DEFAULT_AUTHORIZATION_INFO_QUALIFIER = AuthorizationInformationQualifier.fromString("00");
    public static final String DEFAULT_AUTHORIZATION_INFO = TextUtils.spaces(10);
    public static final SecurityInformationQualifier DEFAULT_SECURITY_INFO_QUALIFIER = SecurityInformationQualifier.fromString("00");
    public static final String DEFAULT_SECURITY_INFO = TextUtils.spaces(10);
    public static final InterchangeIdQualifier DEFAULT_INTERCHANGE_SENDER_QUALIFIER = InterchangeIdQualifier.fromString("30");
    public static final InterchangeIdQualifier DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER = InterchangeIdQualifier.fromString("ZZ");
    public static final String DEFAULT_REPETITION_SEPARATOR = "^";
    public static final InterchangeControlVersionNumber DEFAULT_INTERCHANGE_CONTROL_VERSION = InterchangeControlVersionNumber.fromString("00501");
    public static final AcknowledgmentRequested DEFAULT_ACKNOWLEDGMENT_REQUESTED = AcknowledgmentRequested.fromString("0");
    public static final InterchangeUsageIndicator DEFAULT_USAGE_INDICATOR = InterchangeUsageIndicator.fromString("T");
    public static final String DEFAULT_COMPONENT_ELEMENT_SEPARATOR = ":";

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
         *
         * @param context The X834 context containing document-level information
         */
        public Builder(x834Context context) {
            super();
            this.context = context;
            this.isa01 = DEFAULT_AUTHORIZATION_INFO_QUALIFIER;
            this.isa02 = DEFAULT_AUTHORIZATION_INFO;
            this.isa03 = DEFAULT_SECURITY_INFO_QUALIFIER;
            this.isa04 = DEFAULT_SECURITY_INFO;
            this.isa05 = DEFAULT_INTERCHANGE_SENDER_QUALIFIER;
            this.setIsa06(context.getSenderID());
            this.isa07 = DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER;
            this.isa08 = context.getReceiverID();
            this.isa09 = context.getFormattedDocumentDate();
            this.isa10 = context.getFormattedDocumentTime();
            this.isa11 = DEFAULT_REPETITION_SEPARATOR;
            this.isa12 = DEFAULT_INTERCHANGE_CONTROL_VERSION;
            this.isa14 = DEFAULT_ACKNOWLEDGMENT_REQUESTED;
            this.isa15 = DEFAULT_USAGE_INDICATOR;
            this.isa16 = DEFAULT_COMPONENT_ELEMENT_SEPARATOR;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public InterchangeControlHeader build() throws ValidationException {
            return new InterchangeControlHeader(this);
        }
    }
}