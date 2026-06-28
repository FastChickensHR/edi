/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.dates;

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
        switch (format) {
            case DATE:
            case D8:
                return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case CENTURY_YEAR:
                return Integer.toString(date.getYear());
            case MONTH_DAY:
                return date.format(DateTimeFormatter.ofPattern("MMdd"));
            case CC:
                return date.format(DateTimeFormatter.ofPattern("yy"));
            case CD:
                return date.format(DateTimeFormatter.ofPattern("MMMM"));  // Full month name
            case CM:
            case MM:
                return date.format(DateTimeFormatter.ofPattern("MM"));    // Month number
            case CQ:
                return String.valueOf((date.getMonthValue() - 1) / 3 + 1); // Quarter (1-4)
            case CY:
                return date.format(DateTimeFormatter.ofPattern("yyyy"));   // Four-digit year
            case D6:
                return date.format(DateTimeFormatter.ofPattern("yyMMdd")); // Six-digit date
            case DA:
                return date.format(DateTimeFormatter.ofPattern("dd"));     // Day of month
            case DB:
                return date.format(DateTimeFormatter.ofPattern("ddMMM"));  // Day and abbreviated month
            case DD:
                return date.format(DateTimeFormatter.ofPattern("dd"));     // Day of month
            case DDT:
                return date.format(DateTimeFormatter.ofPattern("dd'T'HHmm")); // Day + time
            case DT:
                return date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")); // ISO-like date+time
            case DTD:
                return date.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // Date in yyyyMMdd format
            case DTS:
                return date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); // Date-time stamp
            case EH:
                return date.format(DateTimeFormatter.ofPattern("HH"));     // Hour (24-hour format)
            case KA:
                return date.format(DateTimeFormatter.ofPattern("HHmm"));   // Time in HHMM format
            case MCY:
                return date.format(DateTimeFormatter.ofPattern("MMMM yyyy")); // Month name and year
            case MD:
                return date.format(DateTimeFormatter.ofPattern("MMdd"));   // Month and day
            case RD:
                return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); // MM/DD/YYYY format
            case RD2:
                return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")); // MM-DD-YYYY format
            case RD4:
                return date.format(DateTimeFormatter.ofPattern("MM.dd.yyyy")); // MM.DD.YYYY format
            case RD5:
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // YYYY-MM-DD format
            case RD6:
                return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // YYYY/MM/DD format
            case RD8:
                return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")); // YYYY.MM.DD format
            case RDM:
                return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // DD/MM/YYYY format
            case RDT:
                return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")); // MM/DD/YYYY HH:MM:SS
            case RMD:
                return date.format(DateTimeFormatter.ofPattern("M/d/yyyy")); // M/D/YYYY (no leading zeros)
            case RMY:
                return date.format(DateTimeFormatter.ofPattern("MMM yyyy")); // Month abbreviation and year
            case RTM:
                return date.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Time with colons
            case RTS:
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")); // ISO 8601
            case TC:
                return date.format(DateTimeFormatter.ofPattern("HH"));     // Hour component
            case TM:
                return date.format(DateTimeFormatter.ofPattern("mm"));     // Minute component
            case TQ:
                return date.format(DateTimeFormatter.ofPattern("HHmm"));   // HHMM time format
            case TR:
                return date.format(DateTimeFormatter.ofPattern("HH:mm"));  // HH:MM time format
            case TS:
                return date.format(DateTimeFormatter.ofPattern("ss"));     // Second component
            case TT:
                return date.format(DateTimeFormatter.ofPattern("HHmmss")); // HHMMSS time format
            case TU:
                return date.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // HH:MM:SS time format
            case UN:
                return date.format(DateTimeFormatter.ofPattern("u"));      // Day of week (1=Monday, 7=Sunday)
            case YM:
                return date.format(DateTimeFormatter.ofPattern("yyyyMM")); // Year and month
            case YMM:
                return date.format(DateTimeFormatter.ofPattern("yyyy MMM")); // Year and month abbreviation
            case YY:
                return date.format(DateTimeFormatter.ofPattern("yy"));     // Two-digit year
            default:
                throw new IllegalArgumentException("Unsupported date format: " + format);
        }
    }

    /**
     * Format a date in the specified EDI format.
     *
     * @param format The EDI date format to use
     * @param time   The date to format
     * @return A string formatted according to the specified format
     */
    public static String formatTime(TimeFormat format, LocalDateTime time) {
        switch (format) {
            case TIME:
                return time.format(DateTimeFormatter.ofPattern("HHmm"));
            case TIME_WITH_SECONDS:
                return time.format(DateTimeFormatter.ofPattern("HHmmss"));
            default:
                throw new IllegalArgumentException("Unsupported time format: " + format);
        }
    }

}