/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Responsible Agency Codes used in EDI transactions,
 * specifically in the GS07 segment element of the Functional Group Header.
 */
@Getter
public enum ResponsibleAgencyCode implements EdiCodeEnum {
    TDCC("T", "Transportation Data Coordinating Committee (TDCC)"),
    ASC_X12("X", "Accredited Standards Committee X12");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<ResponsibleAgencyCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                ResponsibleAgencyCode.class,
                "Responsible Agency Code",
                Map.ofEntries(
                        Map.entry("tdcc", TDCC),
                        Map.entry("transportation", TDCC),
                        Map.entry("transportation data", TDCC),

                        Map.entry("x12", ASC_X12),
                        Map.entry("asc", ASC_X12),
                        Map.entry("asc x12", ASC_X12),
                        Map.entry("accredited standards", ASC_X12),
                        Map.entry("ansi", ASC_X12)
                )
        );
    }

    ResponsibleAgencyCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a ResponsibleAgencyCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching ResponsibleAgencyCode
     * @throws IllegalArgumentException if no match is found
     */
    public static ResponsibleAgencyCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}