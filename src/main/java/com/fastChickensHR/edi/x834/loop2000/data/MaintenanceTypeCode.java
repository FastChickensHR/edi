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
 * Enumeration representing Maintenance Type Codes used in the HD03 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum MaintenanceTypeCode {
    ADDITION("001", "Addition"),
    CHANGE("002", "Change"),
    CANCELLATION("003", "Cancellation"),
    REINSTATEMENT("004", "Reinstatement"),
    CHANGE_WITH_MEMBER_ID("021", "Change with Member ID Change"),
    CHANGE_ADDRESS("022", "Change Address"),
    CHANGE_DEMOGRAPHIC("023", "Change Demographic Data"),
    CHANGE_BENEFIT_COVERAGE("024", "Change Benefit/Coverage Data"),
    CHANGE_BILLING("025", "Change Billing/Payment Data"),
    CHANGE_EMPLOYMENT("026", "Change Employment Data"),
    COBRA_ELECTION("030", "COBRA Election");

    private final String code;
    private final String description;

    MaintenanceTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MaintenanceTypeCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching MaintenanceTypeCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static MaintenanceTypeCode fromCode(String code) {
        for (MaintenanceTypeCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Maintenance Type Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}