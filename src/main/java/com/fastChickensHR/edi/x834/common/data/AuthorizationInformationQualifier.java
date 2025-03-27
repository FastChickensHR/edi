/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.data;

import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Authorization Information Qualifiers used in the ISA01 field
 * of the EDI 834 Interchange Control Header (ISA) segment.
 */
@Getter
public enum AuthorizationInformationQualifier implements EdiCodeEnum {
    NO_AUTHORIZATION_INFORMATION("00", "No Authorization"),
    UCS_COMMUNICATIONS_ID("01", "UCS Communications ID"),
    EDX_COMMUNICATIONS_ID("02", "EDX Communications ID"),
    ADDITIONAL_DATA_ID("03", "Additional Data Identification"),
    RAIL_COMMUNICATIONS_ID("04", "Rail Communications ID"),
    DOD_COMMUNICATION_ID("05", "Department of Defense (DoD) Communication Identifier"),
    US_FEDERAL_GOVT_COMM_ID("06", "United States Federal Government Communication Identifier");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<AuthorizationInformationQualifier> LOOKUP;

    static {
        // Include some additional common terms and abbreviations
        LOOKUP = new EdiEnumLookup<>(
                AuthorizationInformationQualifier.class,
                "Authorization Information Qualifier",
                Map.ofEntries(
                        Map.entry("none", NO_AUTHORIZATION_INFORMATION),
                        Map.entry("noauth", NO_AUTHORIZATION_INFORMATION),
                        Map.entry("ucs", UCS_COMMUNICATIONS_ID),
                        Map.entry("edx", EDX_COMMUNICATIONS_ID),
                        Map.entry("additionaldata", ADDITIONAL_DATA_ID),
                        Map.entry("rail", RAIL_COMMUNICATIONS_ID),
                        Map.entry("dod", DOD_COMMUNICATION_ID),
                        Map.entry("defense", DOD_COMMUNICATION_ID),
                        Map.entry("federal", US_FEDERAL_GOVT_COMM_ID),
                        Map.entry("govt", US_FEDERAL_GOVT_COMM_ID),
                        Map.entry("usgov", US_FEDERAL_GOVT_COMM_ID)
                )
        );
    }

    AuthorizationInformationQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an AuthorizationInformationQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching AuthorizationInformationQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static AuthorizationInformationQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}