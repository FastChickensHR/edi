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
 * Enumeration representing Employment Status Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum EmploymentStatusCode {
    ACTIVE("1", "Active"),
    FULL_TIME("2", "Full-time"),
    PART_TIME("3", "Part-time"),
    RETIRED("4", "Retired"),
    TERMINATED("5", "Terminated"),
    LEAVE_OF_ABSENCE("6", "Leave of absence"),
    DISABLED("7", "Disabled"),
    MILITARY_DUTY("9", "Military duty"),
    COBRA("20", "COBRA"),
    SURVIVING_INSURED("21", "Surviving insured"),
    CONTRACT_EMPLOYEE("22", "Contract employee"),
    ON_CALL_EMPLOYEE("23", "On call employee"),
    SEASONAL_EMPLOYEE("24", "Seasonal employee");

    private final String code;
    private final String description;

    EmploymentStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an EmploymentStatusCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching EmploymentStatusCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static EmploymentStatusCode fromCode(String code) {
        for (EmploymentStatusCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Employment Status Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}

