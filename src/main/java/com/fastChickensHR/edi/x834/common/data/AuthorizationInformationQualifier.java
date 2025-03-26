/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.data;

import lombok.Getter;

/**
 * Enumeration representing Authorization Information Qualifiers used in the ISA01 field
 * of the EDI 834 Interchange Control Header (ISA) segment.
 */
@Getter
public enum AuthorizationInformationQualifier {
    NO_AUTHORIZATION_INFORMATION("00", "No Authorization"),
    UCS_COMMUNICATIONS_ID("01", "UCS Communications ID"),
    EDX_COMMUNICATIONS_ID("02", "EDX Communications ID"),
    ADDITIONAL_DATA_ID("03", "Additional Data Identification"),
    RAIL_COMMUNICATIONS_ID("04", "Rail Communications ID"),
    DOD_COMMUNICATION_ID("05", "Department of Defense (DoD) Communication Identifier"),
    US_FEDERAL_GOVT_COMM_ID("06", "United States Federal Government Communication Identifier");

    private final String code;
    private final String description;

    AuthorizationInformationQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an AuthorizationInformationQualifier instance from its code value.
     *
     * @param code the code to look up
     * @return the matching AuthorizationInformationQualifier
     * @throws IllegalArgumentException if no matching code is found
     */
    public static AuthorizationInformationQualifier fromCode(String code) {
        for (AuthorizationInformationQualifier value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Authorization Information Qualifier: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}