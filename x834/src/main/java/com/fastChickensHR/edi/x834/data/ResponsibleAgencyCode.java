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
 * Code values for the Responsible Agency Code (GS07, X12 data element 455) in the
 * Functional Group Header (GS). Names the body responsible for the standard used in the
 * functional group; X12 834 groups (005010X220A1) use {@link #ASC_X12} ("X").
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a
 * value from its code, enum name, description, or a common synonym, and throws
 * {@link IllegalArgumentException} when the input matches none.
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

    /**
     * Returns the raw X12 code value for this constant (not the enum name), so the enum
     * renders directly into an EDI element.
     */
    @Override
    public String toString() {
        return code;
    }
}