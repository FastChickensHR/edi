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
 * Enumeration representing Gender Codes used in the EDI 834
 * Health Insurance Enrollment transaction, specifically in INS17 segment.
 */
@Getter
public enum GenderCode {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    UNKNOWN("U", "Unknown"),
    NON_BINARY("N", "Non-Binary"),
    NOT_SPECIFIED("X", "Not Specified");

    private final String code;
    private final String description;

    GenderCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a GenderCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching GenderCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static GenderCode fromCode(String code) {
        for (GenderCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Gender Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}

