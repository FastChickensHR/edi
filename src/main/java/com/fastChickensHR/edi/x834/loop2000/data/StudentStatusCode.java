/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import lombok.Getter;

/**
 * Enumeration representing Student Status Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum StudentStatusCode {
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

    StudentStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
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

