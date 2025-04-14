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
            case CC -> date.format(DateTimeFormatter.ofPattern("yy"));
            case CD -> date.format(DateTimeFormatter.ofPattern("MMMM"));  // Full month name
            case CM, MM -> date.format(DateTimeFormatter.ofPattern("MM"));    // Month number
            case CQ -> String.valueOf((date.getMonthValue() - 1) / 3 + 1); // Quarter (1-4)
            case CY -> date.format(DateTimeFormatter.ofPattern("yyyy"));   // Four-digit year
            case D6 -> date.format(DateTimeFormatter.ofPattern("yyMMdd")); // Six-digit date
            case DA -> date.format(DateTimeFormatter.ofPattern("dd"));     // Day of month
            case DB -> date.format(DateTimeFormatter.ofPattern("ddMMM"));  // Day and abbreviated month
            case DD -> date.format(DateTimeFormatter.ofPattern("dd"));     // Day of month
            case DDT -> date.format(DateTimeFormatter.ofPattern("dd'T'HHmm")); // Day + time
            case DT -> date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")); // ISO-like date+time
            case DTD -> date.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // Date in yyyyMMdd format
            case DTS -> date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); // Date-time stamp
            case EH -> date.format(DateTimeFormatter.ofPattern("HH"));     // Hour (24-hour format)
            case KA -> date.format(DateTimeFormatter.ofPattern("HHmm"));   // Time in HHMM format
            case MCY -> date.format(DateTimeFormatter.ofPattern("MMMM yyyy")); // Month name and year
            case MD -> date.format(DateTimeFormatter.ofPattern("MMdd"));   // Month and day
            case RD -> date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); // MM/DD/YYYY format
            case RD2 -> date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")); // MM-DD-YYYY format
            case RD4 -> date.format(DateTimeFormatter.ofPattern("MM.dd.yyyy")); // MM.DD.YYYY format
            case RD5 -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // YYYY-MM-DD format
            case RD6 -> date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // YYYY/MM/DD format
            case RD8 -> date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")); // YYYY.MM.DD format
            case RDM -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // DD/MM/YYYY format
            case RDT -> date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")); // MM/DD/YYYY HH:MM:SS
            case RMD -> date.format(DateTimeFormatter.ofPattern("M/d/yyyy")); // M/D/YYYY (no leading zeros)
            case RMY -> date.format(DateTimeFormatter.ofPattern("MMM yyyy")); // Month abbreviation and year
            case RTM -> date.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Time with colons
            case RTS -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")); // ISO 8601
            case TC -> date.format(DateTimeFormatter.ofPattern("HH"));     // Hour component
            case TM -> date.format(DateTimeFormatter.ofPattern("mm"));     // Minute component
            case TQ -> date.format(DateTimeFormatter.ofPattern("HHmm"));   // HHMM time format
            case TR -> date.format(DateTimeFormatter.ofPattern("HH:mm"));  // HH:MM time format
            case TS -> date.format(DateTimeFormatter.ofPattern("ss"));     // Second component
            case TT -> date.format(DateTimeFormatter.ofPattern("HHmmss")); // HHMMSS time format
            case TU -> date.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // HH:MM:SS time format
            case UN -> date.format(DateTimeFormatter.ofPattern("u"));      // Day of week (1=Monday, 7=Sunday)
            case YM -> date.format(DateTimeFormatter.ofPattern("yyyyMM")); // Year and month
            case YMM -> date.format(DateTimeFormatter.ofPattern("yyyy MMM")); // Year and month abbreviation
            case YY -> date.format(DateTimeFormatter.ofPattern("yy"));     // Two-digit year

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