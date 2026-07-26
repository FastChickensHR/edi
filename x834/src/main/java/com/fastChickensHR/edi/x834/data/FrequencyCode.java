/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Code values for the X12 Frequency Code (data element 594), which states the period an amount or
 * quantity covers. In the X12 834 (005010X220A1) it appears as ICM01, saying over what period the
 * member's income in ICM02 is earned — a wage of 2000 means nothing until you know whether that is
 * weekly, monthly or annual.
 *
 * <p>No default value is defined for this element, and none is assumed here: guessing the period of
 * someone's pay would silently misstate their income by an order of magnitude.
 * {@link #fromString(String)} resolves a value from its code, enum name, description, or a common
 * synonym, and throws {@link IllegalArgumentException} when the input matches none.
 */
@Getter
public enum FrequencyCode implements EdiCodeEnum {
    ANNUALIZED("0", "Annualized; 12-month equivalent"),
    WEEKLY("1", "Weekly"),
    BIWEEKLY("2", "Biweekly"),
    SEMIMONTHLY("3", "Semimonthly"),
    MONTHLY("4", "Monthly"),
    OTHER("5", "Other"),
    DAILY("6", "Daily"),
    ANNUAL("7", "Annual"),
    TWO_CALENDAR_MONTHS("8", "Two Calendar Months"),
    LUMP_SUM_SEPARATION_ALLOWANCE("9", "Lump-Sum Separation Allowance"),
    QUARTER_TO_DATE("A", "Quarter-to-Date"),
    YEAR_TO_DATE("B", "Year-to-Date"),
    SINGLE("C", "Single"),
    POLICY_PERIOD("D", "Policy Period"),
    CLAIM_PERIOD("E", "Claim Period"),
    UNIT_REPORT_IDENTIFIER("F", "Unit Report Identifier"),
    MONTH_TO_DATE("G", "Month-to-Date"),
    HOURLY("H", "Hourly"),
    CURRENT_PERIOD("J", "Current Period"),
    QUARTERLY("Q", "Quarterly"),
    SEMIANNUAL("S", "Semiannual"),
    UNKNOWN("U", "Unknown"),
    MUTUALLY_DEFINED("Z", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<FrequencyCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                FrequencyCode.class,
                "Frequency Code",
                Map.ofEntries(
                        Map.entry("per week", WEEKLY),
                        Map.entry("every two weeks", BIWEEKLY),
                        Map.entry("fortnightly", BIWEEKLY),
                        Map.entry("twice monthly", SEMIMONTHLY),
                        Map.entry("per month", MONTHLY),
                        Map.entry("per day", DAILY),
                        Map.entry("per year", ANNUAL),
                        Map.entry("yearly", ANNUAL),
                        Map.entry("per hour", HOURLY),
                        Map.entry("ytd", YEAR_TO_DATE),
                        Map.entry("qtd", QUARTER_TO_DATE),
                        Map.entry("mtd", MONTH_TO_DATE)
                )
        );
    }

    FrequencyCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a FrequencyCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching FrequencyCode
     * @throws IllegalArgumentException if no match is found
     */
    public static FrequencyCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Returns the raw X12 code value for this constant (not the enum name), so the enum
     * renders directly into an EDI element.
     */
    @Override
    public String toString() {
        return code;
    }
}
