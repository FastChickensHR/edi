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
 * Enumeration representing Identification Card Type Codes (X12 element 1215) emitted at
 * the IDC02 field of the EDI 834 Identification Card (IDC) segment in Loop 2300.
 *
 * <p>Element 1215 defines exactly five codes: {@code D}, {@code E}, {@code H}, {@code P},
 * {@code V}. The former list carried three invented codes ({@code G} Drug, {@code L}
 * Long-Term Care, {@code M} Medical), was missing {@code E} (Hearing Benefit), and
 * mislabeled {@code P} as "Pharmacy". Descriptions are the verbatim element-1215 text.
 */
@Getter
public enum IdentificationCardTypeCode implements EdiCodeEnum {
    DENTAL("D", "Dental Insurance"),
    HEARING("E", "Hearing Benefit"),
    HEALTH("H", "Health Insurance"),
    PRESCRIPTION_DRUG("P", "Prescription Drug Service Drug Insurance"),
    VISION("V", "Vision Benefit");

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

                        Map.entry("hearing card", HEARING),
                        Map.entry("hearing aid", HEARING),

                        Map.entry("health card", HEALTH),
                        Map.entry("insurance card", HEALTH),
                        Map.entry("medical id card", HEALTH),

                        Map.entry("rx card", PRESCRIPTION_DRUG),
                        Map.entry("drug card", PRESCRIPTION_DRUG),
                        Map.entry("pharmacy card", PRESCRIPTION_DRUG),
                        Map.entry("prescription card", PRESCRIPTION_DRUG),

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
