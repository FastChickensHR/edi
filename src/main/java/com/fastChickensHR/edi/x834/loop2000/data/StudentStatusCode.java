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
 * Enumeration representing Student Status Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum StudentStatusCode implements EdiCodeEnum {
    FULL_TIME("F", "Full-time Student"),
    PART_TIME("P", "Part-time Student"),
    NOT_A_STUDENT("N", "Not a Student"),
    CONTINUING_EDUCATION("C", "Continuing Education"),
    GRADUATED("G", "Graduated"),
    ON_BREAK("B", "On School Break/Vacation"),
    LEAVE_OF_ABSENCE("L", "Leave of Absence"),
    UNKNOWN("U", "Unknown");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<StudentStatusCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                StudentStatusCode.class,
                "Student Status Code",
                Map.ofEntries(
                        Map.entry("fulltime", FULL_TIME),
                        Map.entry("full time", FULL_TIME),
                        Map.entry("full-time", FULL_TIME),
                        Map.entry("ft", FULL_TIME),
                        Map.entry("full", FULL_TIME),
                        Map.entry("full load", FULL_TIME),
                        Map.entry("full course load", FULL_TIME),
                        Map.entry("regular student", FULL_TIME),

                        Map.entry("parttime", PART_TIME),
                        Map.entry("part time", PART_TIME),
                        Map.entry("part-time", PART_TIME),
                        Map.entry("pt", PART_TIME),
                        Map.entry("partial", PART_TIME),
                        Map.entry("partial load", PART_TIME),
                        Map.entry("reduced schedule", PART_TIME),
                        Map.entry("half time", PART_TIME),

                        Map.entry("not student", NOT_A_STUDENT),
                        Map.entry("non student", NOT_A_STUDENT),
                        Map.entry("non-student", NOT_A_STUDENT),
                        Map.entry("not enrolled", NOT_A_STUDENT),
                        Map.entry("no longer enrolled", NOT_A_STUDENT),
                        Map.entry("not in school", NOT_A_STUDENT),
                        Map.entry("no school", NOT_A_STUDENT),
                        Map.entry("not attending", NOT_A_STUDENT),

                        Map.entry("cont ed", CONTINUING_EDUCATION),
                        Map.entry("continuing ed", CONTINUING_EDUCATION),
                        Map.entry("cont education", CONTINUING_EDUCATION),
                        Map.entry("adult education", CONTINUING_EDUCATION),
                        Map.entry("extension", CONTINUING_EDUCATION),
                        Map.entry("professional development", CONTINUING_EDUCATION),
                        Map.entry("certificate program", CONTINUING_EDUCATION),
                        Map.entry("non-degree", CONTINUING_EDUCATION),
                        Map.entry("non degree", CONTINUING_EDUCATION),

                        Map.entry("grad", GRADUATED),
                        Map.entry("alumni", GRADUATED),
                        Map.entry("alumnus", GRADUATED),
                        Map.entry("alumna", GRADUATED),
                        Map.entry("completed", GRADUATED),
                        Map.entry("finished", GRADUATED),
                        Map.entry("degree earned", GRADUATED),
                        Map.entry("completed program", GRADUATED),
                        Map.entry("earned degree", GRADUATED),
                        Map.entry("commencement", GRADUATED),

                        Map.entry("break", ON_BREAK),
                        Map.entry("vacation", ON_BREAK),
                        Map.entry("school break", ON_BREAK),
                        Map.entry("semester break", ON_BREAK),
                        Map.entry("holiday", ON_BREAK),
                        Map.entry("between terms", ON_BREAK),
                        Map.entry("between semesters", ON_BREAK),
                        Map.entry("summer break", ON_BREAK),
                        Map.entry("winter break", ON_BREAK),
                        Map.entry("spring break", ON_BREAK),

                        Map.entry("leave", LEAVE_OF_ABSENCE),
                        Map.entry("loa", LEAVE_OF_ABSENCE),
                        Map.entry("absence", LEAVE_OF_ABSENCE),
                        Map.entry("medical leave", LEAVE_OF_ABSENCE),
                        Map.entry("temporary leave", LEAVE_OF_ABSENCE),
                        Map.entry("sabbatical", LEAVE_OF_ABSENCE),
                        Map.entry("study abroad", LEAVE_OF_ABSENCE),
                        Map.entry("personal leave", LEAVE_OF_ABSENCE),
                        Map.entry("family leave", LEAVE_OF_ABSENCE),

                        Map.entry("unk", UNKNOWN),
                        Map.entry("unspecified", UNKNOWN),
                        Map.entry("not provided", UNKNOWN),
                        Map.entry("not specified", UNKNOWN),
                        Map.entry("undetermined", UNKNOWN),
                        Map.entry("unclear", UNKNOWN),
                        Map.entry("not reported", UNKNOWN),
                        Map.entry("missing", UNKNOWN),
                        Map.entry("not available", UNKNOWN),
                        Map.entry("na", UNKNOWN)
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