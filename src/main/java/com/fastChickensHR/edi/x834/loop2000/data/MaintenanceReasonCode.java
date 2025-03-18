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
 * Enumeration representing Maintenance Reason Codes used in the HD04 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum MaintenanceReasonCode {
    ACTIVE("AC", "Active"),
    BIRTH("BH", "Birth"),
    COBRA("CO", "COBRA"),
    DISABILITY("DI", "Disability"),
    DEATH("DN", "Death"),
    RETIREMENT("ET", "Retirement"),
    MARRIAGE("MA", "Marriage"),
    STRIKE("ST", "Strike"),
    TERMINATION("TN", "Termination"),
    VOLUNTARY_WITHDRAWAL("VO", "Voluntary Withdrawal"),
    ADMINISTRATIVE_ERROR("XN", "Administrative Error");

    private final String code;
    private final String description;

    MaintenanceReasonCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MaintenanceReasonCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching MaintenanceReasonCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static MaintenanceReasonCode fromCode(String code) {
        for (MaintenanceReasonCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Maintenance Reason Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}