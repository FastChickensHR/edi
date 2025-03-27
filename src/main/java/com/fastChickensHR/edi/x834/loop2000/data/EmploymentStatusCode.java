/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiCodeEnum;
import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Employment Status Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum EmploymentStatusCode implements EdiCodeEnum {
    ACTIVE("1", "Active"),
    FULL_TIME("2", "Full-time"),
    PART_TIME("3", "Part-time"),
    RETIRED("4", "Retired"),
    TERMINATED("5", "Terminated"),
    LEAVE_OF_ABSENCE("6", "Leave of absence"),
    DISABLED("7", "Disabled"),
    MILITARY_DUTY("9", "Military duty"),
    COBRA("20", "COBRA"),
    SURVIVING_INSURED("21", "Surviving insured"),
    CONTRACT_EMPLOYEE("22", "Contract employee"),
    ON_CALL_EMPLOYEE("23", "On call employee"),
    SEASONAL_EMPLOYEE("24", "Seasonal employee");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<EmploymentStatusCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                EmploymentStatusCode.class,
                "Employment Status Code",
                Map.ofEntries(
                        Map.entry("current", ACTIVE),
                        Map.entry("employed", ACTIVE),
                        Map.entry("working", ACTIVE),

                        Map.entry("ft", FULL_TIME),
                        Map.entry("fulltime", FULL_TIME),
                        Map.entry("40hours", FULL_TIME),

                        Map.entry("pt", PART_TIME),
                        Map.entry("parttime", PART_TIME),
                        Map.entry("hourly", PART_TIME),

                        Map.entry("pension", RETIRED),
                        Map.entry("retiree", RETIRED),

                        Map.entry("laid off", TERMINATED),
                        Map.entry("fired", TERMINATED),
                        Map.entry("resigned", TERMINATED),
                        Map.entry("quit", TERMINATED),

                        Map.entry("loa", LEAVE_OF_ABSENCE),
                        Map.entry("sabbatical", LEAVE_OF_ABSENCE),
                        Map.entry("fmla", LEAVE_OF_ABSENCE),
                        Map.entry("medical leave", LEAVE_OF_ABSENCE),

                        Map.entry("disability", DISABLED),
                        Map.entry("ltd", DISABLED),

                        Map.entry("military", MILITARY_DUTY),
                        Map.entry("army", MILITARY_DUTY),
                        Map.entry("navy", MILITARY_DUTY),
                        Map.entry("airforce", MILITARY_DUTY),
                        Map.entry("marines", MILITARY_DUTY),
                        Map.entry("reserve", MILITARY_DUTY),

                        Map.entry("consolidated omnibus budget reconciliation act", COBRA),

                        Map.entry("survivor", SURVIVING_INSURED),
                        Map.entry("widow", SURVIVING_INSURED),
                        Map.entry("widower", SURVIVING_INSURED),

                        Map.entry("contractor", CONTRACT_EMPLOYEE),
                        Map.entry("1099", CONTRACT_EMPLOYEE),
                        Map.entry("temporary", CONTRACT_EMPLOYEE),

                        Map.entry("oncall", ON_CALL_EMPLOYEE),
                        Map.entry("asneeded", ON_CALL_EMPLOYEE),

                        Map.entry("seasonal", SEASONAL_EMPLOYEE),
                        Map.entry("summer", SEASONAL_EMPLOYEE),
                        Map.entry("holiday", SEASONAL_EMPLOYEE),
                        Map.entry("temp", SEASONAL_EMPLOYEE)
                )
        );
    }

    EmploymentStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an EmploymentStatusCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching EmploymentStatusCode
     * @throws IllegalArgumentException if no match is found
     */
    public static EmploymentStatusCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}