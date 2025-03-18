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
 * Enumeration representing Handicap Indicator codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum HandicapIndicator {
    YES("Y", "Yes - Individual has a handicap"),
    NO("N", "No - Individual does not have a handicap"),
    UNKNOWN("U", "Unknown handicap status");

    private final String code;
    private final String description;

    HandicapIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a HandicapIndicator instance from its code value.
     *
     * @param code the code to look up
     * @return the matching HandicapIndicator
     * @throws IllegalArgumentException if no matching code is found
     */
    public static HandicapIndicator fromCode(String code) {
        for (HandicapIndicator value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Handicap Indicator code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}

