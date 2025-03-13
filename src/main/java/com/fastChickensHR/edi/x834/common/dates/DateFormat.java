package com.fastChickensHR.edi.x834.common.dates;

/**
 * Enumeration of standard EDI date formats.
 */
public enum DateFormat {
    /**
     * Date format YYYYMMDD (e.g., 20231115)
     */
    DATE("CCYYMMDD", "^\\d{8}$"),

    /**
     * Century and year format CCYY (e.g., 2023)
     */
    CENTURY_YEAR("CCYY", "^\\d{4}$"),

    /**
     * Month and day format MMDD (e.g., 1115)
     */
    MONTH_DAY("MMDD", "^\\d{4}$");

    private final String format;
    private final String validationPattern;

    // Proper enum constructor
    DateFormat(String format, String validationPattern) {
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