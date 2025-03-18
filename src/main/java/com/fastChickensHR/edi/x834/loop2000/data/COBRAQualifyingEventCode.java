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
 * Enumeration representing COBRA Qualifying Event Codes used in the INS07 field
 * of the EDI 834 Member Level Detail (INS) segment.
 */
@Getter
public enum COBRAQualifyingEventCode {
    TERMINATION_OF_EMPLOYMENT("1", "Termination of Employment"),
    REDUCTION_IN_HOURS("2", "Reduction in Hours"),
    DEATH_OF_EMPLOYEE("3", "Death of Employee"),
    DIVORCE_LEGAL_SEPARATION("4", "Divorce or Legal Separation"),
    MEDICARE_ENTITLEMENT("5", "Medicare Entitlement"),
    DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT("6", "Dependent Child Ceases to be Dependent"),
    BANKRUPTCY("7", "Bankruptcy (Retirees and Dependents)"),
    LAYOFF("8", "Layoff"),
    LEAVE_OF_ABSENCE("9", "Leave of Absence");

    private final String code;
    private final String description;

    COBRAQualifyingEventCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a COBRAQualifyingEventCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching COBRAQualifyingEventCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static COBRAQualifyingEventCode fromCode(String code) {
        for (COBRAQualifyingEventCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid COBRA Qualifying Event Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
