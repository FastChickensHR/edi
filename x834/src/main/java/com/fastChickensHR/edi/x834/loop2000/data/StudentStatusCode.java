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
 * Enumeration representing Student Status Codes (X12 element 1220) emitted at the
 * INS09 field of the EDI 834 transaction.
 *
 * <p>Element 1220 defines exactly three codes: {@code F}, {@code N}, {@code P}. The
 * former list carried five invented codes ({@code C}, {@code G}, {@code B}, {@code L},
 * {@code U}) that do not exist in element 1220 and have been removed.
 */
@Getter
public enum StudentStatusCode implements EdiCodeEnum {
    FULL_TIME("F", "Full-time"),
    NOT_A_STUDENT("N", "Not a Student"),
    PART_TIME("P", "Part-time");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<StudentStatusCode> LOOKUP;

    static {
        // Colloquial aliases; codes, enum names and descriptions are matched automatically.
        LOOKUP = new EdiEnumLookup<>(
                StudentStatusCode.class,
                "Student Status Code",
                Map.ofEntries(
                        Map.entry("ft", FULL_TIME),
                        Map.entry("full", FULL_TIME),
                        Map.entry("full load", FULL_TIME),
                        Map.entry("full course load", FULL_TIME),
                        Map.entry("regular student", FULL_TIME),

                        Map.entry("pt", PART_TIME),
                        Map.entry("partial", PART_TIME),
                        Map.entry("partial load", PART_TIME),
                        Map.entry("reduced schedule", PART_TIME),
                        Map.entry("half time", PART_TIME),

                        Map.entry("not student", NOT_A_STUDENT),
                        Map.entry("non student", NOT_A_STUDENT),
                        Map.entry("not enrolled", NOT_A_STUDENT),
                        Map.entry("not in school", NOT_A_STUDENT),
                        Map.entry("not attending", NOT_A_STUDENT)
                )
        );
    }

    StudentStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a StudentStatusCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching StudentStatusCode
     * @throws IllegalArgumentException if no match is found
     */
    public static StudentStatusCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Gets a StudentStatusCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching StudentStatusCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static StudentStatusCode fromCode(String code) {
        for (StudentStatusCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Student Status Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
