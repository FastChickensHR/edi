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
 * Enumeration representing Date Time Qualifiers used in the DTP01 field
 * of the EDI 834 Health Coverage Dates (DTP) segment.
 */
@Getter
public enum HealthCoverageDateQualifier {
    EFFECTIVE_DATE("348", "Plan Begin Date"),
    EXPIRATION_DATE("349", "Plan End Date"),
    ELIGIBILITY_BEGIN("356", "Eligibility Begin Date"),
    ELIGIBILITY_END("357", "Eligibility End Date"),
    COBRA_BEGIN("343", "COBRA Begin Date"),
    COBRA_END("344", "COBRA End Date"),
    PREMIUM_PAID_TO_DATE("309", "Premium Paid To Date");

    private final String code;
    private final String description;

    HealthCoverageDateQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a HealthCoverageDateQualifier instance from its code value.
     *
     * @param code the code to look up
     * @return the matching HealthCoverageDateQualifier
     * @throws IllegalArgumentException if no matching code is found
     */
    public static HealthCoverageDateQualifier fromCode(String code) {
        for (HealthCoverageDateQualifier value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Health Coverage Date Qualifier: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
