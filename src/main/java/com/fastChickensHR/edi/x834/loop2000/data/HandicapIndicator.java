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
 * Enumeration representing Handicap Indicator codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum HandicapIndicator implements EdiCodeEnum {
    YES("Y", "Yes - Individual has a handicap"),
    NO("N", "No - Individual does not have a handicap"),
    UNKNOWN("U", "Unknown handicap status");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<HandicapIndicator> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                HandicapIndicator.class,
                "Handicap Indicator",
                Map.ofEntries(
                        Map.entry("y", YES),
                        Map.entry("yes", YES),
                        Map.entry("true", YES),
                        Map.entry("t", YES),
                        Map.entry("1", YES),
                        Map.entry("disabled", YES),
                        Map.entry("disability", YES),
                        Map.entry("has disability", YES),
                        Map.entry("has handicap", YES),
                        Map.entry("handicapped", YES),

                        Map.entry("n", NO),
                        Map.entry("no", NO),
                        Map.entry("false", NO),
                        Map.entry("f", NO),
                        Map.entry("0", NO),
                        Map.entry("not disabled", NO),
                        Map.entry("no disability", NO),
                        Map.entry("no handicap", NO),
                        Map.entry("not handicapped", NO),

                        Map.entry("u", UNKNOWN),
                        Map.entry("unknown", UNKNOWN),
                        Map.entry("undisclosed", UNKNOWN),
                        Map.entry("not disclosed", UNKNOWN),
                        Map.entry("not specified", UNKNOWN),
                        Map.entry("unspecified", UNKNOWN),
                        Map.entry("?", UNKNOWN)
                )
        );
    }

    HandicapIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a HandicapIndicator instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching HandicapIndicator
     * @throws IllegalArgumentException if no match is found
     */
    public static HandicapIndicator fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}