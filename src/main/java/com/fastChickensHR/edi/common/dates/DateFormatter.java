/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.dates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for handling EDI date and time formats.
 */
public class DateFormatter {
    /**
     * Format a date in the specified EDI format.
     *
     * @param format The EDI date format to use
     * @param date   The date to format
     * @return A string formatted according to the specified format
     */
    public static String formatDate(DateFormat format, LocalDateTime date) {
        return switch (format) {
            case DATE, D8 -> date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case CENTURY_YEAR -> Integer.toString(date.getYear());
            case MONTH_DAY -> date.format(DateTimeFormatter.ofPattern("MMdd"));
        };
    }

    /**
     * Format a date in the specified EDI format.
     *
     * @param format The EDI date format to use
     * @param time   The date to format
     * @return A string formatted according to the specified format
     */
    public static String formatTime(TimeFormat format, LocalDateTime time) {
        return switch (format) {
            case TIME -> time.format(DateTimeFormatter.ofPattern("HHmm"));
            case TIME_WITH_SECONDS -> time.format(DateTimeFormatter.ofPattern("HHmmss"));
        };
    }

}