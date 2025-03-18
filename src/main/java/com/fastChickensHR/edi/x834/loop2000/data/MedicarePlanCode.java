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
 * Enumeration representing Medicare Plan Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum MedicarePlanCode {
    HOSPITAL_ONLY("A", "Hospital Only (Part A)"),
    MEDICAL_ONLY("B", "Medical Only (Part B)"),
    HOSPITAL_AND_MEDICAL("C", "Hospital and Medical (Parts A and B)"),
    MEDICARE_ADVANTAGE("D", "Medicare Advantage (Part C)"),
    PRESCRIPTION_DRUG("E", "Prescription Drug (Part D)"),
    MEDIGAP("F", "Medigap (Medicare Supplement)"),
    NO_MEDICARE("N", "No Medicare Coverage");

    private final String code;
    private final String description;

    MedicarePlanCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MedicarePlanCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching MedicarePlanCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static MedicarePlanCode fromCode(String code) {
        for (MedicarePlanCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Medicare Plan Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
