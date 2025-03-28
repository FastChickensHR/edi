/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.dates;

import lombok.Getter;

/**
 * Enumeration of standard EDI date formats.
 */
@Getter
public enum DateFormat {
    /**
     * Date format YYYYMMDD (e.g., 20231115)
     */
    DATE("CCYYMMDD", "^\\d{8}$"),

    /**
     * Date format YYYYMMDD (e.g., 20231115)
     */
    D8("D8", "^\\d{8}$"),

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

    DateFormat(String format, String validationPattern) {
        this.format = format;
        this.validationPattern = validationPattern;
    }

}