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
 * Enumeration representing COBRA Qualifying Event Codes used in the INS07 field
 * of the EDI 834 Member Level Detail (INS) segment.
 */
@Getter
public enum COBRAQualifyingEventCode implements EdiCodeEnum {
    TERMINATION_OF_EMPLOYMENT("1", "Termination of Employment"),
    REDUCTION_IN_HOURS("2", "Reduction in Hours"),
    DEATH_OF_EMPLOYEE("3", "Death of Employee"),
    DIVORCE_LEGAL_SEPARATION("4", "Divorce or Legal Separation"),
    MEDICARE_ENTITLEMENT("5", "Medicare Entitlement"),
    DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT("6", "Dependent Child Ceases to be Dependent"),
    BANKRUPTCY("7", "Bankruptcy (Retirees and Dependents)"),
    LAYOFF("8", "Layoff"),
    LEAVE_OF_ABSENCE("9", "Leave of Absence");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<COBRAQualifyingEventCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                COBRAQualifyingEventCode.class,
                "COBRA Qualifying Event Code",
                Map.ofEntries(
                        Map.entry("fired", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("terminated", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("quit", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("resignation", TERMINATION_OF_EMPLOYMENT),
                        Map.entry("reduced hours", REDUCTION_IN_HOURS),
                        Map.entry("parttime", REDUCTION_IN_HOURS),
                        Map.entry("deceased", DEATH_OF_EMPLOYEE),
                        Map.entry("passed away", DEATH_OF_EMPLOYEE),
                        Map.entry("divorce", DIVORCE_LEGAL_SEPARATION),
                        Map.entry("separated", DIVORCE_LEGAL_SEPARATION),
                        Map.entry("medicare", MEDICARE_ENTITLEMENT),
                        Map.entry("agedout", DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                        Map.entry("no longer dependent", DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
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