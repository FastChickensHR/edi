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
 * Enumeration representing Benefit Status Codes used in the HD05 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum BenefitStatusCode {
    ACTIVE("A", "Active"),
    COBRA("C", "COBRA"),
    DISABLED("D", "Disabled"),
    RETIREE("R", "Retiree"),
    SURVIVING_INSURED("S", "Surviving Insured"),
    TERMINATED("T", "Terminated");

    private final String code;
    private final String description;

    BenefitStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a BenefitStatusCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching BenefitStatusCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static BenefitStatusCode fromCode(String code) {
        for (BenefitStatusCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Benefit Status Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
