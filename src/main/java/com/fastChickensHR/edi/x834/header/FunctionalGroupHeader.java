package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.dates.DateFormatter;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Abstract class representing the Functional Group Header segment in an EDI document.
 * Now using the x834Context for document-level information.
 */
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
        return new String[]{
                gs01,
                gs02,
                gs03,
                gs04,
                gs05,
                gs06,
                gs07,
                gs08
        };
    }

    private void validateRequiredFields() throws ValidationException {
        if (gs01 == null || gs01.trim().isEmpty()) {
            throw new ValidationException("GS01 (Functional Identifier Code) cannot be set to a blank or null value");
        }
        if (gs04 == null || gs04.trim().isEmpty()) {
            throw new ValidationException("GS04 (Transaction Set Creation Date) cannot be set to a blank or null value");
        }
        if (gs08 == null || gs08.trim().isEmpty()) {
            throw new ValidationException("GS08 (Industry Identifier Code) cannot be set to a blank or null value");
        }
    }

    /**
     * Gets the GS01 element value (Functional Identifier Code)
     *
     * @return the functional identifier code
     */
    public String getGs01() {
        return gs01;
    }

    /**
     * Gets the functional identifier code (GS01)
     *
     * @return the functional identifier code
     */
    public String getFunctionalIdentifierCode() {
        return getGs01();
    }

    /**
     * Gets the GS02 element value (Application Sender's Code)
     *
     * @return the application sender's code
     */
    public String getGs02() {
        return gs02;
    }

    /**
     * Gets the application sender's code (GS02)
     *
     * @return the application sender's code
     */
    public String getApplicationSenderCode() {
        return getGs02();
    }

    /**
     * Gets the GS03 element value (Application Receiver's Code)
     *
     * @return the application receiver's code
     */
    public String getGs03() {
        return gs03;
    }

    /**
     * Gets the application receiver's code (GS03)
     *
     * @return the application receiver's code
     */
    public String getApplicationReceiverCode() {
        return getGs03();
    }

    /**
     * Gets the GS04 element value (Date)
     *
     * @return the date in YYYYMMDD format
     */
    public String getGs04() {
        return gs04;
    }

    /**
     * Gets the date (GS04)
     *
     * @return the date in YYYYMMDD format
     */
    public String getTransactionSetCreationDate() {
        return getGs04();
    }

    /**
     * Gets the GS05 element value (Time)
     *
     * @return the time in HHMM format
     */
    public String getGs05() {
        return gs05;
    }

    /**
     * Gets the time (GS05)
     *
     * @return the time in HHMM format
     */
    public String getTime() {
        return getGs05();
    }

    /**
     * Gets the GS06 element value (Group Control Number)
     *
     * @return the group control number
     */
    public String getGs06() {
        return gs06;
    }

    /**
     * Gets the group control number (GS06)
     *
     * @return the group control number
     */
    public String getGroupControlNumber() {
        return getGs06();
    }

    /**
     * Gets the GS07 element value (Responsible Agency Code)
     *
     * @return the responsible agency code
     */
    public String getGs07() {
        return gs07;
    }

    /**
     * Gets the responsible agency code (GS07)
     *
     * @return the responsible agency code
     */
    public String getResponsibleAgencyCode() {
        return getGs07();
    }

    /**
     * Gets the GS08 element value (Version/Release/Industry Identifier Code)
     *
     * @return the version/release/industry identifier code
     */
    public String getGs08() {
        return gs08;
    }

    /**
     * Gets the version/release/industry identifier code (GS08)
     *
     * @return the version/release/industry identifier code
     */
    public String getVersionReleaseIndustryCode() {
        return getGs08();
    }

    /**
     * Builder for FunctionalGroupHeader
     */
    public static class Builder {
        private String gs01;
        private String gs02;
        private String gs03;
        private String gs04;
        private String gs05;
        private String gs06;
        private String gs07;
        private String gs08;
        private final x834Context context;

        // Passing context through Builder's constructor
        public Builder(x834Context context) {
            this.context = context;
        }


        public Builder setGs01(String value) {
            this.gs01 = value;
            return this;
        }

        public Builder setFunctionalIdentifierCode(String value) {
            return setGs01(value);
        }

        public Builder setGs02(String value) {
            this.gs02 = value;
            return this;
        }

        public Builder setApplicationSenderCode(String value) {
            return setGs02(value);
        }

        public Builder setGs03(String value) {
            this.gs03 = value;
            return this;
        }

        public Builder setApplicationReceiverCode(String value) {
            return setGs03(value);
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

        public Builder setGs06(String value) {
            this.gs06 = value;
            return this;
        }

        public Builder setGroupControlNumber(String value) {
            return setGs06(value);
        }

        public Builder setGs07(String value) {
            this.gs07 = value;
            return this;
        }

        public Builder setResponsibleAgencyCode(String value) {
            return setGs07(value);
        }

        public Builder setGs08(String value) {
            this.gs08 = value;
            return this;
        }

        public Builder setVersionReleaseIndustryCode(String value) {
            return setGs08(value);
        }

        /**
         * Validates the required fields before building the segment.
         *
         * @throws ValidationException If any required field is invalid
         */
        private void validateRequiredFields() throws ValidationException {
            if (gs02 == null || gs02.trim().isEmpty()) {
                throw new ValidationException("GS02 (Application Sender Code) is required");
            }
            if (gs03 == null || gs03.trim().isEmpty()) {
                throw new ValidationException("GS03 (Application Receiver Code) is required");
            }
            if (gs06 == null || gs06.trim().isEmpty()) {
                throw new ValidationException("GS06 (Group Control Number) is required");
            }
        }

        /**
         * Validates and builds a FunctionalGroupHeader instance.
         *
         * @return A new FunctionalGroupHeader instance
         * @throws ValidationException If validation fails
         */
        public FunctionalGroupHeader build() throws ValidationException {
            validateRequiredFields();
            return new ConcreteFunctionalGroupHeaderSegment(this);
        }
    }

    private static class ConcreteFunctionalGroupHeaderSegment extends FunctionalGroupHeader {
        protected ConcreteFunctionalGroupHeaderSegment(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}

