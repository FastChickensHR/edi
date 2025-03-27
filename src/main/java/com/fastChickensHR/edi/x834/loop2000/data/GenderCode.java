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
 * Enumeration representing Gender Codes used in the EDI 834
 * Health Insurance Enrollment transaction, specifically in INS17 segment.
 */
@Getter
public enum GenderCode implements EdiCodeEnum {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    UNKNOWN("U", "Unknown"),
    NON_BINARY("N", "Non-Binary"),
    NOT_SPECIFIED("X", "Not Specified");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<GenderCode> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                GenderCode.class,
                "Gender Code",
                Map.ofEntries(
                        Map.entry("m", MALE),
                        Map.entry("man", MALE),
                        Map.entry("boy", MALE),
                        Map.entry("masculine", MALE),
                        Map.entry("f", FEMALE),
                        Map.entry("woman", FEMALE),
                        Map.entry("girl", FEMALE),
                        Map.entry("feminine", FEMALE),
                        Map.entry("u", UNKNOWN),
                        Map.entry("unspecified", UNKNOWN),
                        Map.entry("undetermined", UNKNOWN),
                        Map.entry("undisclosed", UNKNOWN),
                        Map.entry("n", NON_BINARY),
                        Map.entry("nonbinary", NON_BINARY),
                        Map.entry("nb", NON_BINARY),
                        Map.entry("enby", NON_BINARY),
                        Map.entry("genderqueer", NON_BINARY),
                        Map.entry("x", NOT_SPECIFIED),
                        Map.entry("declined", NOT_SPECIFIED),
                        Map.entry("not disclosed", NOT_SPECIFIED),
                        Map.entry("prefer not to say", NOT_SPECIFIED),
                        Map.entry("withheld", NOT_SPECIFIED)
                )
        );
    }

    GenderCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a GenderCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching GenderCode
     * @throws IllegalArgumentException if no match is found
     */
    public static GenderCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}