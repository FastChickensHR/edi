package com.fastChickensHR.edi.x834.common.dates;

/**
 * Enumeration of standard EDI time formats.
 */
public enum TimeFormat {
    /**
     * Time format HHMM (e.g., 1230)
     */
    TIME("HHMM", "^\\d{4}$"),

    /**
     * Time format with seconds HHMMSS (e.g., 123045)
     */
    TIME_WITH_SECONDS("HHMMSS", "^\\d{6}$");

    private final String format;
    private final String validationPattern;

    // Proper enum constructor
    TimeFormat(String format, String validationPattern) {
        this.format = format;
        this.validationPattern = validationPattern;
    }

    public String getFormat() {
        return format;
    }

    public String getValidationPattern() {
        return validationPattern;
    }
}