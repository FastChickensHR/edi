package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.dates.DateFormatter;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents the header segment (GS segment) of a functional group in an EDI (Electronic Data Interchange) message.
 * This segment contains metadata about the functional group,
 * such as identifiers, sender and receiver codes, and version details.
 */
@Getter
public abstract class FunctionalGroupHeader extends Segment {
    // Constants for segment and field identifiers
    public static final String SEGMENT_ID = "GS";
    public static final String DEFAULT_FUNCTIONAL_ID_CODE = "BE";  // Benefits Enrollment
    public static final String DEFAULT_RESPONSIBLE_AGENCY_CODE = "X";  // Accredited Standards Committee X12
    public static final String DEFAULT_VERSION_CODE = "005010X220A1";

    private final String gs01; // Functional Identifier Code
    private final String gs02; // Application Sender's Code
    private final String gs03; // Application Receiver's Code
    private final String gs04; // Date (YYYYMMDD)
    private final String gs05; // Time (HHMM)
    private final String gs06; // Group Control Number
    private final String gs07; // Responsible Agency Code
    private final String gs08; // Version/Release/Industry Identifier Code

    protected FunctionalGroupHeader(Builder builder) throws ValidationException {
        this.gs01 = builder.gs01 != null ? builder.gs01 : DEFAULT_FUNCTIONAL_ID_CODE;
        this.gs02 = builder.gs02;
        this.gs03 = builder.gs03;
        this.gs04 = builder.gs04;
        this.gs05 = builder.gs05;
        this.gs06 = builder.gs06;
        this.gs07 = builder.gs07 != null ? builder.gs07 : DEFAULT_RESPONSIBLE_AGENCY_CODE;
        this.gs08 = builder.gs08 != null ? builder.gs08 : DEFAULT_VERSION_CODE;

        validateRequiredFields();
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{gs01, gs02, gs03, gs04, gs05, gs06, gs07, gs08};
    }

    private void validateRequiredFields() throws ValidationException {
        if (gs01 == null || gs01.trim().isEmpty()) {
            throw new ValidationException("GS01 (Functional Identifier Code) cannot be blank");
        }
        if (gs02 == null || gs02.trim().isEmpty()) {
            throw new ValidationException("GS02 (Application Sender's Code) cannot be blank");
        }
        if (gs03 == null || gs03.trim().isEmpty()) {
            throw new ValidationException("GS03 (Application Receiver's Code) cannot be blank");
        }
        if (gs04 == null || gs04.trim().isEmpty()) {
            throw new ValidationException("GS04 (Transaction Set Creation Date) cannot be blank");
        }
        if (gs06 == null || gs06.trim().isEmpty()) {
            throw new ValidationException("GS06 (Group Control Number) cannot be blank");
        }
        if (gs08 == null || gs08.trim().isEmpty()) {
            throw new ValidationException("GS08 (Industry Identifier Code) cannot be blank");
        }
    }

    // Keep these explicit alias methods since they provide meaningful domain names
    public String getFunctionalIdentifierCode() {
        return getGs01();
    }

    public String getApplicationSenderCode() {
        return getGs02();
    }

    public String getApplicationReceiverCode() {
        return getGs03();
    }

    public String getTransactionSetCreationDate() {
        return getGs04();
    }

    public String getTime() {
        return getGs05();
    }

    public String getGroupControlNumber() {
        return getGs06();
    }

    public String getResponsibleAgencyCode() {
        return getGs07();
    }

    public String getVersionReleaseIndustryCode() {
        return getGs08();
    }

    public static class Builder {
        private String gs01 = DEFAULT_FUNCTIONAL_ID_CODE;
        private String gs02;
        private String gs03;
        private String gs04;
        private String gs05;
        private String gs06;
        private String gs07 = DEFAULT_RESPONSIBLE_AGENCY_CODE;
        private String gs08 = DEFAULT_VERSION_CODE;
        private final x834Context context;

        // Passing context through Builder's constructor
        public Builder(x834Context context) {
            this.context = context;
        }

        public Builder setGs01(String gs01) {
            this.gs01 = gs01;
            return this;
        }

        public Builder setFunctionalIdentifierCode(String code) {
            return setGs01(code);
        }

        public Builder setGs02(String gs02) {
            this.gs02 = gs02;
            return this;
        }

        public Builder setApplicationSenderCode(String code) {
            return setGs02(code);
        }

        public Builder setGs03(String gs03) {
            this.gs03 = gs03;
            return this;
        }

        public Builder setApplicationReceiverCode(String code) {
            return setGs03(code);
        }

        public Builder setGs04(LocalDate value) {
            this.gs04 = DateFormatter.formatDate(context.getDateFormat(), value);
            return this;
        }

        public Builder setTransactionSetCreationDate(LocalDate value) {
            return setGs04(value);
        }

        public Builder setGs05(LocalDateTime value) {
            this.gs05 = DateFormatter.formatTime(context.getTimeFormat(), value);
            return this;
        }

        public Builder setTransactionSetCreationTime(LocalDateTime value) {
            return setGs05(value);
        }

        public Builder setGs06(String gs06) {
            this.gs06 = gs06;
            return this;
        }

        public Builder setGroupControlNumber(String number) {
            return setGs06(number);
        }

        public Builder setGs07(String gs07) {
            this.gs07 = gs07;
            return this;
        }

        public Builder setResponsibleAgencyCode(String code) {
            return setGs07(code);
        }

        public Builder setGs08(String gs08) {
            this.gs08 = gs08;
            return this;
        }

        public Builder setVersionReleaseIndustryCode(String code) {
            return setGs08(code);
        }

        public FunctionalGroupHeader build() throws ValidationException {
            return new FunctionalGroupHeaderImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class FunctionalGroupHeaderImpl extends FunctionalGroupHeader {
        private FunctionalGroupHeaderImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}