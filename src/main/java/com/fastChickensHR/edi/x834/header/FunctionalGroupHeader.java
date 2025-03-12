package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;

/**
 * Abstract class representing the Functional Group Header segment in an EDI document.
 * This segment is commonly referred to as the "GS" segment and includes key fields such as
 * the Functional Identifier Code (GS01), Application Sender's Code (GS02),
 * Application Receiver's Code (GS03), Date (GS04), Time (GS05),
 * Group Control Number (GS06), Responsible Agency Code (GS07),
 * and Version/Release/Industry Identifier Code (GS08).
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
    private String gs06; // Group Control Number
    private final String gs07; // Responsible Agency Code
    private final String gs08; // Version/Release/Industry Identifier Code

    protected FunctionalGroupHeader(Builder builder) {
        super();
        this.gs01 = builder.gs01;
        this.gs02 = builder.gs02;
        this.gs03 = builder.gs03;
        this.gs04 = builder.gs04;
        this.gs05 = builder.gs05;
        this.gs06 = builder.gs06;
        this.gs07 = builder.gs07;
        this.gs08 = builder.gs08;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{gs01, gs02, gs03, gs04, gs05, gs06, gs07, gs08};
    }

    /**
     * @return The Functional Identifier Code (GS01) value
     */
    public String getGs01() {
        return gs01;
    }

    /**
     * @return The Functional Identifier Code (GS01) value
     */
    public String getFunctionalIdentifierCode() {
        return gs01;
    }

    /**
     * @return The Application Sender's Code (GS02) value
     */
    public String getGs02() {
        return gs02;
    }

    /**
     * @return The Application Sender's Code (GS02) value
     */
    public String getApplicationSenderCode() {
        return gs02;
    }

    /**
     * @return The Application Receiver's Code (GS03) value
     */
    public String getGs03() {
        return gs03;
    }

    /**
     * @return The Application Receiver's Code (GS03) value
     */
    public String getApplicationReceiverCode() {
        return gs03;
    }

    /**
     * @return The Date (GS04) value in format YYYYMMDD
     */
    public String getGs04() {
        return gs04;
    }

    /**
     * @return The Date (GS04) value in format YYYYMMDD
     */
    public String getDate() {
        return gs04;
    }

    /**
     * @return The Time (GS05) value in format HHMM
     */
    public String getGs05() {
        return gs05;
    }

    /**
     * @return The Time (GS05) value in format HHMM
     */
    public String getTime() {
        return gs05;
    }

    /**
     * @return The Group Control Number (GS06) value
     */
    public String getGs06() {
        return gs06;
    }

    /**
     * @return The Group Control Number (GS06) value
     */
    public String getGroupControlNumber() {
        return gs06;
    }

    /**
     * Sets the Group Control Number.
     *
     * @param value The control number to set (must match GE02)
     */
    public void setGs06(String value) {
        this.gs06 = value;
    }

    /**
     * Sets the Group Control Number.
     *
     * @param value The control number to set (must match GE02)
     */
    public void setGroupControlNumber(String value) {
        this.gs06 = value;
    }

    /**
     * @return The Responsible Agency Code (GS07) value
     */
    public String getGs07() {
        return gs07;
    }

    /**
     * @return The Responsible Agency Code (GS07) value
     */
    public String getResponsibleAgencyCode() {
        return gs07;
    }

    /**
     * @return The Version/Release/Industry Identifier Code (GS08) value
     */
    public String getGs08() {
        return gs08;
    }

    /**
     * @return The Version/Release/Industry Identifier Code (GS08) value
     */
    public String getVersionReleaseIndustryCode() {
        return gs08;
    }

    public static class Builder {
        // Fields with default values where appropriate
        private String gs01 = DEFAULT_FUNCTIONAL_ID_CODE;
        private String gs02; // Required - Application Sender's Code (no default)
        private String gs03; // Required - Application Receiver's Code (no default)
        private String gs04; // Required - Date (no default)
        private String gs05; // Required - Time (no default)
        private String gs06 = "1"; // Default Group Control Number
        private String gs07 = DEFAULT_RESPONSIBLE_AGENCY_CODE;
        private String gs08 = DEFAULT_VERSION_CODE;

        /**
         * Sets the GS01 field value (Functional Identifier Code)
         */
        public Builder setGs01(String gs01) {
            this.gs01 = gs01;
            return this;
        }

        /**
         * Sets the GS02 field value (Application Sender's Code)
         */
        public Builder setGs02(String gs02) {
            this.gs02 = gs02;
            return this;
        }

        /**
         * Sets the GS03 field value (Application Receiver's Code)
         */
        public Builder setGs03(String gs03) {
            this.gs03 = gs03;
            return this;
        }

        /**
         * Sets the GS04 field value (Date in format YYYYMMDD)
         */
        public Builder setGs04(String gs04) {
            this.gs04 = gs04;
            return this;
        }

        /**
         * Sets the GS05 field value (Time in format HHMM)
         */
        public Builder setGs05(String gs05) {
            this.gs05 = gs05;
            return this;
        }

        /**
         * Sets the GS06 field value (Group Control Number)
         */
        public Builder setGs06(String gs06) {
            this.gs06 = gs06;
            return this;
        }

        /**
         * Sets the GS07 field value (Responsible Agency Code)
         */
        public Builder setGs07(String gs07) {
            this.gs07 = gs07;
            return this;
        }

        /**
         * Sets the GS08 field value (Version/Release/Industry Identifier Code)
         */
        public Builder setGs08(String gs08) {
            this.gs08 = gs08;
            return this;
        }

        /**
         * Sets the Functional Identifier Code (GS01)
         */
        public Builder setFunctionalIdentifierCode(String code) {
            this.gs01 = code;
            return this;
        }

        /**
         * Sets the Application Sender's Code (GS02)
         */
        public Builder setApplicationSenderCode(String code) {
            this.gs02 = code;
            return this;
        }

        /**
         * Sets the Application Receiver's Code (GS03)
         */
        public Builder setApplicationReceiverCode(String code) {
            this.gs03 = code;
            return this;
        }

        /**
         * Sets the Date (GS04) in format YYYYMMDD
         */
        public Builder setDate(String date) {
            this.gs04 = date;
            return this;
        }

        /**
         * Sets the Time (GS05) in format HHMM
         */
        public Builder setTime(String time) {
            this.gs05 = time;
            return this;
        }

        /**
         * Sets the Group Control Number (GS06)
         */
        public Builder setGroupControlNumber(String number) {
            this.gs06 = number;
            return this;
        }

        /**
         * Sets the Responsible Agency Code (GS07)
         */
        public Builder setResponsibleAgencyCode(String code) {
            this.gs07 = code;
            return this;
        }

        /**
         * Sets the Version/Release/Industry Identifier Code (GS08)
         */
        public Builder setVersionReleaseIndustryCode(String code) {
            this.gs08 = code;
            return this;
        }

        public FunctionalGroupHeader build() throws ValidationException {
            validateRequiredFields();
            return new ConcreteFunctionalGroupHeader(this);
        }

        private void validateRequiredFields() throws ValidationException {
            if (gs01 == null || gs01.trim().isEmpty()) {
                throw new ValidationException("GS01 (Functional Identifier Code) is required");
            }
            if (gs02 == null || gs02.trim().isEmpty()) {
                throw new ValidationException("GS02 (Application Sender's Code) is required");
            }
            if (gs03 == null || gs03.trim().isEmpty()) {
                throw new ValidationException("GS03 (Application Receiver's Code) is required");
            }
            if (gs04 == null || gs04.trim().isEmpty()) {
                throw new ValidationException("GS04 (Date) is required");
            }
            if (gs05 == null || gs05.trim().isEmpty()) {
                throw new ValidationException("GS05 (Time) is required");
            }
            if (gs06 == null || gs06.trim().isEmpty()) {
                throw new ValidationException("GS06 (Group Control Number) is required");
            }
            if (gs07 == null || gs07.trim().isEmpty()) {
                throw new ValidationException("GS07 (Responsible Agency Code) is required");
            }
            if (gs08 == null || gs08.trim().isEmpty()) {
                throw new ValidationException("GS08 (Version/Release/Industry Identifier Code) is required");
            }
        }
    }

    /**
     * Concrete implementation of the FunctionalGroupHeader
     */
    private static class ConcreteFunctionalGroupHeader extends FunctionalGroupHeader {
        public ConcreteFunctionalGroupHeader(Builder builder) {
            super(builder);
        }
    }
}