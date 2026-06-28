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
 * Enumeration representing Identification Card Type Codes used in the IDC02 field
 * of the EDI 834 Identification Card (IDC) segment in Loop 2300.
 * <p>
 * Source: HIPAA 005010X220A1 Benefit Enrollment and Maintenance implementation guide.
 */
@Getter
public enum IdentificationCardTypeCode implements EdiCodeEnum {
    DENTAL("D", "Dental"),
    DRUG("G", "Drug"),
    HEALTH("H", "Health"),
    LONG_TERM_CARE("L", "Long-Term Care"),
    MEDICAL("M", "Medical"),
    PHARMACY("P", "Pharmacy"),
    VISION("V", "Vision");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<IdentificationCardTypeCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                IdentificationCardTypeCode.class,
                "Identification Card Type Code",
                Map.<String, IdentificationCardTypeCode>ofEntries(
                        Map.entry("dental card", DENTAL),
                        Map.entry("teeth", DENTAL),

                        Map.entry("drug card", DRUG),
                        Map.entry("rx card", DRUG),

                        Map.entry("health card", HEALTH),
                        Map.entry("insurance card", HEALTH),
                        Map.entry("medical id card", HEALTH),

                        Map.entry("ltc", LONG_TERM_CARE),
                        Map.entry("long term care", LONG_TERM_CARE),
                        Map.entry("nursing card", LONG_TERM_CARE),

                        Map.entry("medical card", MEDICAL),

                        Map.entry("pharmacy card", PHARMACY),
                        Map.entry("prescription card", PHARMACY),

                        Map.entry("vision card", VISION),
                        Map.entry("eye card", VISION),
                        Map.entry("optical card", VISION)
                )
        );
    }

    IdentificationCardTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an IdentificationCardTypeCode instance from any input string.
     *
     * @param input the string to look up
     * @return the matching IdentificationCardTypeCode
     * @throws IllegalArgumentException if no match is found
     */
    public static IdentificationCardTypeCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
