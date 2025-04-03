/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Confidentiality Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum ConfidentialityCode implements EdiCodeEnum {
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
    private static final EdiEnumLookup<ConfidentialityCode> LOOKUP;

    static {
        // Include additional synonyms and related terms
        LOOKUP = new EdiEnumLookup<>(
                ConfidentialityCode.class,
                "Confidentiality Code",
                Map.ofEntries(
                        Map.entry("open", UNRESTRICTED),
                        Map.entry("standard", NORMAL)
                )
        );
    }

    ConfidentialityCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a ConfidentialityCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching ConfidentialityCode
     * @throws IllegalArgumentException if no match is found
     */
    public static ConfidentialityCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}