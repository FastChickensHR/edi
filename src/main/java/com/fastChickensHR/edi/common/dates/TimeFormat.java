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
 * Enumeration of standard EDI time formats.
 */
@Getter
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

}