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
    MONTH_DAY("MMDD", "^\\d{4}$"),

    /**
     * First two digits of year (e.g., 20)
     */
    CC("CC", "^\\d{2}$"),

    /**
     * Month and year in format MMMYYYY (e.g., JAN1994)
     */
    CD("CD", "^[A-Z]{3}\\d{4}$"),

    /**
     * Date in format YYYYMM (e.g., 202311)
     */
    CM("CM", "^\\d{6}$"),

    /**
     * Date in format YYYYQ (year and quarter) (e.g., 2023Q)
     */
    CQ("CQ", "^\\d{4}[1-4Q]$"),

    /**
     * Year in format YYYY (e.g., 2023)
     */
    CY("CY", "^\\d{4}$"),

    /**
     * Date in format YYMMDD (e.g., 231115)
     */
    D6("D6", "^\\d{6}$"),

    /**
     * Range of days within a month in format DD-DD (e.g., 01-15)
     */
    DA("DA", "^\\d{2}-\\d{2}$"),

    /**
     * Date in format MMDDYYYY (e.g., 11152023)
     */
    DB("DB", "^\\d{8}$"),

    /**
     * Day of month with leading zero (e.g., 01-31)
     */
    DD("DD", "^\\d{2}$"),

    /**
     * Range of dates and time in format YYYYMMDD-YYYYMMDDHHMM
     */
    DDT("DDT", "^\\d{8}-\\d{12}$"),

    /**
     * Date and time in format YYYYMMDDHHMM (e.g., 202311151430)
     */
    DT("DT", "^\\d{12}$"),

    /**
     * Range of dates and time in format YYYYMMDDHHMM-YYYYMMDD
     */
    DTD("DTD", "^\\d{12}-\\d{8}$"),

    /**
     * Range of date and time in format YYYYMMDDHHMMSS-YYYYMMDDHHMMSS
     */
    DTS("DTS", "^\\d{14}-\\d{14}$"),

    /**
     * Last digit of year and Julian date in format YDDD (e.g., 3319)
     */
    EH("EH", "^\\d{4}$"),

    /**
     * Date in format YYMMMDD (e.g., 23NOV15)
     */
    KA("KA", "^\\d{2}[A-Z]{3}\\d{2}$"),

    /**
     * Date in format MMYYYY (e.g., 112023)
     */
    MCY("MCY", "^\\d{6}$"),

    /**
     * Month and day in format MMDD (e.g., 1115)
     */
    MD("MD", "^\\d{4}$"),

    /**
     * Month with leading zero (e.g., 01-12)
     */
    MM("MM", "^\\d{2}$"),

    /**
     * Range of dates in format MMDDYYYY-MMDDYYYY
     */
    RD("RD", "^\\d{8}-\\d{8}$"),

    /**
     * Range of years in format YY-YY (e.g., 22-23)
     */
    RD2("RD2", "^\\d{2}-\\d{2}$"),

    /**
     * Range of years in format YYYY-YYYY (e.g., 2022-2023)
     */
    RD4("RD4", "^\\d{4}-\\d{4}$"),

    /**
     * Range of years and months in format YYYYMM-YYYYMM
     */
    RD5("RD5", "^\\d{6}-\\d{6}$"),

    /**
     * Range of dates in format YYMMDD-YYMMDD
     */
    RD6("RD6", "^\\d{6}-\\d{6}$"),

    /**
     * Range of dates in format YYYYMMDD-YYYYMMDD
     */
    RD8("RD8", "^\\d{8}-\\d{8}$"),

    /**
     * Range of dates in format YYMMDD-MMDD
     */
    RDM("RDM", "^\\d{6}-\\d{4}$"),

    /**
     * Range of date and time in format YYYYMMDDHHMM-YYYYMMDDHHMM
     */
    RDT("RDT", "^\\d{12}-\\d{12}$"),

    /**
     * Range of months and days in format MMDD-MMDD
     */
    RMD("RMD", "^\\d{4}-\\d{4}$"),

    /**
     * Range of years and months in format YYMM-YYMM
     */
    RMY("RMY", "^\\d{4}-\\d{4}$"),

    /**
     * Range of time in format HHMM-HHMM
     */
    RTM("RTM", "^\\d{4}-\\d{4}$"),

    /**
     * Date and time in format YYYYMMDDHHMMSS
     */
    RTS("RTS", "^\\d{14}$"),

    /**
     * Julian date in format DDD (e.g., 319)
     */
    TC("TC", "^\\d{3}$"),

    /**
     * Time in format HHMM (e.g., 1430)
     */
    TM("TM", "^\\d{4}$"),

    /**
     * Date in format MMYY (e.g., 1123)
     */
    TQ("TQ", "^\\d{4}$"),

    /**
     * Date and time in format DDMMYYHHMM
     */
    TR("TR", "^\\d{10}$"),

    /**
     * Time in format HHMMSS (e.g., 143015)
     */
    TS("TS", "^\\d{6}$"),

    /**
     * Date in format MMDDYY (e.g., 111523)
     */
    TT("TT", "^\\d{6}$"),

    /**
     * Date in format YYDDD (Julian date, e.g., 23319)
     */
    TU("TU", "^\\d{5}$"),

    /**
     * Unstructured date format
     */
    UN("UN", ".*"),

    /**
     * Year and month in format YYMM (e.g., 2311)
     */
    YM("YM", "^\\d{4}$"),

    /**
     * Range of year and months in format YYYYMMM-MMM (e.g., 2023JAN-DEC)
     */
    YMM("YMM", "^\\d{4}[A-Z]{3}-[A-Z]{3}$"),

    /**
     * Last two digits of year (e.g., 23)
     */
    YY("YY", "^\\d{2}$");

    private final String format;
    private final String validationPattern;

    DateFormat(String format, String validationPattern) {
        this.format = format;
        this.validationPattern = validationPattern;
    }
}