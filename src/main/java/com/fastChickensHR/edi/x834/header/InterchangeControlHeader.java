package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.TextUtils;
import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Abstract class representing the Interchange Control Header segment in an EDI document.
 * This segment is commonly referred to as the "ISA" segment and contains information
 * about the interchange, including sender and receiver identification and control numbers.
 */
@Getter
public abstract class InterchangeControlHeader extends Segment {
    // Constants for segment and field identifiers
    public static final String SEGMENT_ID = "ISA";
    public static final String DEFAULT_AUTHORIZATION_INFO_QUALIFIER = "00"; // No authorization info present
    public static final String DEFAULT_AUTHORIZATION_INFO = TextUtils.spaces(10);
    public static final String DEFAULT_SECURITY_INFO_QUALIFIER = "00"; // No security info present
    public static final String DEFAULT_SECURITY_INFO = TextUtils.spaces(10);
    public static final String DEFAULT_INTERCHANGE_SENDER_QUALIFIER = "ZZ"; // Mutually defined
    public static final String DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER = "ZZ"; // Mutually defined
    public static final String DEFAULT_REPETITION_SEPARATOR = "^"; // Standard repetition separator
    public static final String DEFAULT_INTERCHANGE_CONTROL_VERSION = "00501"; // Version 5.1
    public static final String DEFAULT_ACKNOWLEDGMENT_REQUESTED = "0"; // No acknowledgment requested
    public static final String DEFAULT_USAGE_INDICATOR = "P"; // Production data

    private final String isa01; // Authorization Information Qualifier
    private final String isa02; // Authorization Information
    private final String isa03; // Security Information Qualifier
    private final String isa04; // Security Information
    private final String isa05; // Interchange ID Qualifier (Sender)
    private final String isa06; // Interchange Sender ID
    private final String isa07; // Interchange ID Qualifier (Receiver)
    private final String isa08; // Interchange Receiver ID
    private final String isa09; // Interchange Date (YYMMDD)
    private final String isa10; // Interchange Time (HHMM)
    private final String isa11; // Interchange Control Standards Identifier
    private final String isa12; // Interchange Control Version Number
    private final String isa13; // Interchange Control Number
    private final String isa14; // Acknowledgment Requested
    private final String isa15; // Usage Indicator (Test/Production)
    private final String isa16; // Component Element Separator

    protected InterchangeControlHeader(Builder builder) throws ValidationException {
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
        if (isa06 == null || isa06.trim().isEmpty()) {
            throw new ValidationException("ISA06 (Interchange Sender ID) cannot be blank");
        }
        if (isa06.length() != 15) {
            throw new ValidationException("ISA06 (Interchange Sender ID must be 15 characters in length");
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
        if (isa13 == null || isa13.trim().isEmpty()) {
            throw new ValidationException("ISA13 (Interchange Control Number) cannot be blank");
        }
        if (isa16 == null || isa16.trim().isEmpty()) {
            throw new ValidationException("ISA16 (Component Element Separator) cannot be blank");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                isa01, isa02, isa03, isa04, isa05, isa06, isa07, isa08,
                isa09, isa10, isa11, isa12, isa13, isa14, isa15, isa16
        };
    }

    // Domain-specific accessor methods
    public String getAuthorizationInformationQualifier() {
        return isa01;
    }

    public String getAuthorizationInformation() {
        return isa02;
    }

    public String getSecurityInformationQualifier() {
        return isa03;
    }

    public String getSecurityInformation() {
        return isa04;
    }

    public String getInterchangeSenderQualifier() {
        return isa05;
    }

    public String getInterchangeSenderID() {
        return isa06;
    }

    public String getInterchangeReceiverQualifier() {
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

    public String getInterchangeControlVersionNumber() {
        return isa12;
    }

    public String getInterchangeControlNumber() {
        return isa13;
    }

    public String getAcknowledgmentRequested() {
        return isa14;
    }

    public String getUsageIndicator() {
        return isa15;
    }

    public String getComponentElementSeparator() {
        return isa16;
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private String isa01 = DEFAULT_AUTHORIZATION_INFO_QUALIFIER;
        private String isa02 = DEFAULT_AUTHORIZATION_INFO;
        private String isa03 = DEFAULT_SECURITY_INFO_QUALIFIER;
        private String isa04 = DEFAULT_SECURITY_INFO;
        private String isa05 = DEFAULT_INTERCHANGE_SENDER_QUALIFIER;
        private String isa06;
        private String isa07 = DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER;
        private String isa08;
        private String isa09;
        private String isa10;
        private String isa11 = DEFAULT_REPETITION_SEPARATOR;
        private String isa12 = DEFAULT_INTERCHANGE_CONTROL_VERSION;
        private String isa13;
        private String isa14 = DEFAULT_ACKNOWLEDGMENT_REQUESTED;
        private String isa15 = DEFAULT_USAGE_INDICATOR;
        private String isa16 = ":";


        public Builder(x834Context context) {
            this.isa09 = context.getFormattedDocumentDate();
            this.isa10 = context.getFormattedDocumentTime();
        }

        public Builder setIsa06(String isa06) {
            this.isa06 = TextUtils.padRight(isa06, 15);
            return this;
        }

        public Builder setIsa08(String isa08) {
            this.isa08 = TextUtils.padRight(isa08, 15);
            return this;
        }

        public Builder setAuthorizationInformationQualifier(String qualifier) {
            return setIsa01(qualifier);
        }

        public Builder setAuthorizationInformation(String info) {
            return setIsa02(info);
        }

        public Builder setSecurityInformationQualifier(String qualifier) {
            return setIsa03(qualifier);
        }

        public Builder setSecurityInformation(String info) {
            return setIsa04(info);
        }

        public Builder setInterchangeSenderQualifier(String qualifier) {
            return setIsa05(qualifier);
        }

        public Builder setInterchangeSenderID(String id) {
            return setIsa06(id);
        }

        public Builder setInterchangeReceiverQualifier(String qualifier) {
            return setIsa07(qualifier);
        }

        public Builder setInterchangeReceiverID(String id) {
            return setIsa08(id);
        }

        public Builder setInterchangeDate(String date) {
            return setIsa09(date);
        }

        public Builder setInterchangeTime(String time) {
            return setIsa10(time);
        }

        public Builder setInterchangeControlStandardsIdentifier(String identifier) {
            return setIsa11(identifier);
        }

        public Builder setInterchangeControlVersionNumber(String version) {
            return setIsa12(version);
        }

        public Builder setInterchangeControlNumber(String number) {
            return setIsa13(number);
        }

        public Builder setAcknowledgmentRequested(String ack) {
            return setIsa14(ack);
        }

        public Builder setUsageIndicator(String indicator) {
            return setIsa15(indicator);
        }

        public Builder setComponentElementSeparator(String separator) {
            return setIsa16(separator);
        }

        public InterchangeControlHeader build() throws ValidationException {
            return new InterchangeControlHeaderImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class InterchangeControlHeaderImpl extends InterchangeControlHeader {
        private InterchangeControlHeaderImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}
