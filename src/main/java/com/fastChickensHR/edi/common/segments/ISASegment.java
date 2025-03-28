/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.TextUtils;
import com.fastChickensHR.edi.common.data.*;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Represents the ISA (Interchange Control Header) segment in EDI formats.
 * This segment initiates and identifies an interchange of electronic data.
 */
@Getter
abstract public class ISASegment extends Segment {
    public static final String SEGMENT_ID = "ISA";

    protected final AuthorizationInformationQualifier isa01;
    protected final String isa02;
    protected final SecurityInformationQualifier isa03;
    protected final String isa04;
    protected final InterchangeIdQualifier isa05;
    protected final String isa06; // Interchange Sender ID
    protected final InterchangeIdQualifier isa07;
    protected final String isa08; // Interchange Receiver ID
    protected final String isa09; // Interchange Date (YYMMDD)
    protected final String isa10; // Interchange Time (HHMM)
    protected final String isa11; // Repetition Separator
    protected final InterchangeControlVersionNumber isa12;
    protected final String isa13; // Interchange Control Number
    protected final AcknowledgmentRequested isa14;
    protected final InterchangeUsageIndicator isa15;
    protected final String isa16; // Component Element Separator

    protected ISASegment(AbstractBuilder<?> builder) throws ValidationException {
        this.isa01 = builder.isa01;
        this.isa02 = builder.isa02;
        this.isa03 = builder.isa03;
        this.isa04 = builder.isa04;
        this.isa05 = builder.isa05;
        this.isa06 = builder.isa06;
        this.isa07 = builder.isa07;
        this.isa08 = builder.isa08;
        this.isa09 = builder.isa09;
        this.isa10 = builder.isa10;
        this.isa11 = builder.isa11;
        this.isa12 = builder.isa12;
        this.isa13 = builder.isa13;
        this.isa14 = builder.isa14;
        this.isa15 = builder.isa15;
        this.isa16 = builder.isa16;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if(isa01 == null) {
            throw new ValidationException("ISA01 (Authorization Information Qualifier) cannot be blank");
        }
        if(isa02 == null) {
            throw new ValidationException("ISA02 (Authorization Information) cannot be blank");
        }
        if (isa02.length() != 10) {
            throw new ValidationException("ISA02 (Authorization Information) must be 10 characters in length");
        }
        if (isa03 == null) {
            throw new ValidationException("ISA03 (Security Information Qualifier) cannot be blank");
        }
        if (isa04 == null) {
            throw new ValidationException("ISA04 (Security Information) cannot be blank");
        }
        if (isa04.length() != 10) {
            throw new ValidationException("ISA04 (Security Information) must be 10 characters in length");
        }
        if (isa05 == null) {
            throw new ValidationException("ISA05 (Interchange ID Qualifier) cannot be blank");
        }
        if (isa06 == null || isa06.trim().isEmpty()) {
            throw new ValidationException("ISA06 (Interchange Sender ID) cannot be blank");
        }
        if (isa06.length() != 15) {
            throw new ValidationException("ISA06 (Interchange Sender ID must be 15 characters in length");
        }
        if (isa07 == null) {
            throw new ValidationException("ISA07 (Interchange Receiver ID) cannot be blank");
        }
        if (isa08 == null || isa08.trim().isEmpty()) {
            throw new ValidationException("ISA08 (Interchange Receiver ID) cannot be blank");
        }
        if (isa09 == null || isa09.trim().isEmpty()) {
            throw new ValidationException("ISA09 (Interchange Date) cannot be blank");
        }
        if (isa10 == null || isa10.trim().isEmpty()) {
            throw new ValidationException("ISA10 (Interchange Time) cannot be blank");
        }
        if (isa11 == null || isa11.trim().isEmpty()) {
            throw new ValidationException("ISA11 (Repetition Separator) cannot be blank");
        }
        if (isa12 == null) {
            throw new ValidationException("ISA12 (Interchange Control Version Number) cannot be blank");
        }
        if (isa13 == null || isa13.trim().isEmpty()) {
            throw new ValidationException("ISA13 (Interchange Control Number) cannot be blank");
        }
        if (isa14 == null) {
            throw new ValidationException("ISA14 (Acknowledgment Requested) cannot be blank");
        }
        if (isa15 == null) {
            throw new ValidationException("ISA15 (Interchange Usage Indicator) cannot be blank");
        }
        if (isa16 == null || isa16.trim().isEmpty()) {
            throw new ValidationException("ISA16 (Component Element Separator) cannot be blank");
        }
        if (isa16.length() != 1) {
            throw new ValidationException("ISA16 (Component Element Separator) must be exactly 1 character");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                isa01.getCode(), isa02, isa03.getCode(), isa04, isa05.getCode(), isa06, isa07.getCode(), isa08,
                isa09, isa10, isa11, isa12.getCode(), isa13, isa14.getCode(), isa15.getCode(), isa16
        };
    }

    public AuthorizationInformationQualifier getAuthorizationInformationQualifier() {
        return isa01;
    }

    public String getAuthorizationInformation() {
        return isa02;
    }

    public SecurityInformationQualifier getSecurityInformationQualifier() {
        return isa03;
    }

    public String getSecurityInformation() {
        return isa04;
    }

    public InterchangeIdQualifier getInterchangeSenderQualifier() {
        return isa05;
    }

    public String getInterchangeSenderID() {
        return isa06;
    }

    public InterchangeIdQualifier getInterchangeReceiverQualifier() {
        return isa07;
    }

    public String getInterchangeReceiverID() {
        return isa08;
    }

    public String getInterchangeDate() {
        return isa09;
    }

    public String getInterchangeTime() {
        return isa10;
    }

    public String getInterchangeControlStandardsIdentifier() {
        return isa11;
    }

    public InterchangeControlVersionNumber getInterchangeControlVersionNumber() {
        return isa12;
    }

    public String getInterchangeControlNumber() {
        return isa13;
    }

    public AcknowledgmentRequested getAcknowledgmentRequested() {
        return isa14;
    }

    public InterchangeUsageIndicator getUsageIndicator() {
        return isa15;
    }

    public String getComponentElementSeparator() {
        return isa16;
    }

    /**
     * Abstract builder for ISA segments.
     *
     * @param <T> The concrete builder type for method chaining
     */
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected AuthorizationInformationQualifier isa01;
        protected String isa02;
        protected SecurityInformationQualifier isa03;
        protected String isa04;
        protected InterchangeIdQualifier isa05;
        protected String isa06;
        protected InterchangeIdQualifier isa07;
        protected String isa08;
        protected String isa09;
        protected String isa10;
        protected String isa11;
        protected InterchangeControlVersionNumber isa12;
        protected String isa13;
        protected AcknowledgmentRequested isa14;
        protected InterchangeUsageIndicator isa15;
        protected String isa16;

        public AbstractBuilder() {
        }

        public T setIsa01(String value) {
            this.isa01 = AuthorizationInformationQualifier.fromString(value);
            return self();
        }

        public T setIsa02(String isa02) {
            this.isa02 = isa02;
            return self();
        }

        public T setIsa03(String value) {
            this.isa03 = SecurityInformationQualifier.fromString(value);
            return self();
        }

        public T setIsa04(String isa04) {
            this.isa04 = isa04;
            return self();
        }

        public T setIsa05(String value) {
            this.isa05 = InterchangeIdQualifier.fromString(value);
            return self();
        }

        public T setIsa06(String isa06) {
            this.isa06 = TextUtils.padRight(isa06, 15);
            return self();
        }

        public T setIsa07(String value) {
            this.isa07 = InterchangeIdQualifier.fromString(value);
            return self();
        }

        public T setIsa08(String isa08) {
            this.isa08 = TextUtils.padRight(isa08, 15);
            return self();
        }

        public T setIsa09(String isa09) {
            this.isa09 = isa09;
            return self();
        }

        public T setIsa10(String isa10) {
            this.isa10 = isa10;
            return self();
        }

        public T setIsa11(String isa11) {
            this.isa11 = isa11;
            return self();
        }

        public T setIsa12(String value) {
            this.isa12 = InterchangeControlVersionNumber.fromString(value);
            return self();
        }

        public T setIsa13(String isa13) {
            this.isa13 = isa13;
            return self();
        }

        public T setIsa14(String value) {
            this.isa14 = AcknowledgmentRequested.fromString(value);
            return self();
        }

        public T setIsa15(String value) {
            this.isa15 = InterchangeUsageIndicator.fromString(value);
            return self();
        }

        public T setIsa16(String isa16) {
            this.isa16 = isa16;
            return self();
        }

        public T setAuthorizationInformationQualifier(String value) {
            return setIsa01(value);
        }

        public T setAuthorizationInformation(String info) {
            return setIsa02(info);
        }

        public T setSecurityInformationQualifier(String qualifier) {
            return setIsa03(qualifier);
        }

        public T setSecurityInformation(String info) {
            return setIsa04(info);
        }

        public T setInterchangeSenderQualifier(String qualifier) {
            return setIsa05(qualifier);
        }

        public T setInterchangeSenderID(String id) {
            return setIsa06(id);
        }

        public T setInterchangeReceiverQualifier(String qualifier) {
            return setIsa07(qualifier);
        }

        public T setInterchangeReceiverID(String id) {
            return setIsa08(id);
        }

        public T setInterchangeDate(String date) {
            return setIsa09(date);
        }

        public T setInterchangeTime(String time) {
            return setIsa10(time);
        }

        public T setInterchangeControlStandardsIdentifier(String identifier) {
            return setIsa11(identifier);
        }

        public T setInterchangeControlVersionNumber(String version) {
            return setIsa12(version);
        }

        public T setInterchangeControlNumber(String number) {
            return setIsa13(number);
        }

        public T setAcknowledgmentRequested(String ack) {
            return setIsa14(ack);
        }

        public T setUsageIndicator(String indicator) {
            return setIsa15(indicator);
        }

        public T setComponentElementSeparator(String separator) {
            return setIsa16(separator);
        }

        /**
         * Returns this builder cast to the concrete type.
         *
         * @return This builder
         */
        @SuppressWarnings("unchecked")
        protected T self() {
            return (T) this;
        }

        /**
         * Builds the ISA segment.
         *
         * @return The built ISA segment
         * @throws ValidationException if validation fails
         */
        public abstract ISASegment build() throws ValidationException;
    }
}