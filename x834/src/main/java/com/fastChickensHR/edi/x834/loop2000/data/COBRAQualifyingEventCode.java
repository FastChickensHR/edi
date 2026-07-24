/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing COBRA Qualifying Event Codes (X12 element 1219) emitted at the
 * INS07 field of the EDI 834 Member Level Detail (INS) segment.
 *
 * <p>The full element 1219 list. The former list attached the wrong descriptions to
 * codes {@code 3}-{@code 9} (a "logical" ordering that did not match element 1219's
 * actual assignments — e.g. code {@code 3} is Medicare, not "Death of Employee") and was
 * missing {@code 10} and {@code ZZ}; the descriptions are now the verbatim element-1219
 * text.
 */
@Getter
public enum COBRAQualifyingEventCode implements EdiCodeEnum {
    TERMINATION_OF_EMPLOYMENT("1", "Termination of Employment"),
    REDUCTION_OF_WORK_HOURS("2", "Reduction of work hours"),
    MEDICARE("3", "Medicare"),
    DEATH("4", "Death"),
    DIVORCE("5", "Divorce"),
    SEPARATION("6", "Separation"),
    INELIGIBLE_CHILD("7", "Ineligible Child"),
    BANKRUPTCY("8", "Bankruptcy of Retiree's Former Employer (26 U.S.C. 4980B(f)(3)(F))"),
    LAYOFF("9", "Layoff"),
    LEAVE_OF_ABSENCE("10", "Leave of Absence"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<COBRAQualifyingEventCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                COBRAQualifyingEventCode.class,
                "COBRA Qualifying Event Code",
                Map.ofEntries(
                        Map.entry("fired", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("terminated", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("quit", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("resignation", TERMINATION_OF_EMPLOYMENT),

                        Map.entry("reduced hours", REDUCTION_OF_WORK_HOURS),
                        Map.entry("reduction in hours", REDUCTION_OF_WORK_HOURS),
                        Map.entry("parttime", REDUCTION_OF_WORK_HOURS),

                        Map.entry("deceased", DEATH),
                        Map.entry("passed away", DEATH),

                        Map.entry("legal separation", SEPARATION),

                        Map.entry("aged out", INELIGIBLE_CHILD),
                        Map.entry("no longer dependent", INELIGIBLE_CHILD),
                        Map.entry("dependent child ceases", INELIGIBLE_CHILD),

                        Map.entry("chapter11", BANKRUPTCY),

                        Map.entry("downsized", LAYOFF),
                        Map.entry("laid off", LAYOFF),

                        Map.entry("sabbatical", LEAVE_OF_ABSENCE),
                        Map.entry("fmla", LEAVE_OF_ABSENCE),
                        Map.entry("loa", LEAVE_OF_ABSENCE)
                )
        );
    }

    COBRAQualifyingEventCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a COBRAQualifyingEventCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching COBRAQualifyingEventCode
     * @throws IllegalArgumentException if no match is found
     */
    public static COBRAQualifyingEventCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
