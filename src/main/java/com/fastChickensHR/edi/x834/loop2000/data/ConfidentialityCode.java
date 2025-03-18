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
 * Enumeration representing Confidentiality Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum ConfidentialityCode {
    UNRESTRICTED("U", "Unrestricted"),
    RESTRICTED("R", "Restricted"),
    CONFIDENTIAL("C", "Confidential"),
    VERY_RESTRICTED("V", "Very Restricted"),
    NORMAL("N", "Normal"),
    LOW("L", "Low"),
    MEDIUM("M", "Medium"),
    HIGH("H", "High");

    private final String code;
    private final String description;

    ConfidentialityCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a ConfidentialityCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching ConfidentialityCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static ConfidentialityCode fromCode(String code) {
        for (ConfidentialityCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Confidentiality Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}

