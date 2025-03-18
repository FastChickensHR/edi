/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration representing Individual Relationship Codes used in the INS02 field
 * of the EDI 834 Member Level Detail (INS) segment.
 */
@Getter
public enum IndividualRelationshipCode {
    SPOUSE("01", "Spouse"),
    CHILD("19", "Child"),
    EMPLOYEE("20", "Employee"),
    DISABLED_DEPENDENT("22", "Disabled Dependent"),
    SELF("18", "Self"),
    LIFE_PARTNER("53", "Life Partner"),
    OTHER_RELATED("29", "Other Related");

    private final String code;
    private final String description;
    private static final Map<String, IndividualRelationshipCode> CODE_MAP;
    private static final Map<String, IndividualRelationshipCode> TEXT_MAP;

    static {
        Map<String, IndividualRelationshipCode> codeMap = new HashMap<>();
        for (IndividualRelationshipCode value : values()) {
            codeMap.put(value.code, value);
        }
        CODE_MAP = Collections.unmodifiableMap(codeMap);

        TEXT_MAP = Map.ofEntries(
                Map.entry("spouse", SPOUSE),
                Map.entry("wife", SPOUSE),
                Map.entry("husband", SPOUSE),
                Map.entry("child", CHILD),
                Map.entry("son", CHILD),
                Map.entry("daughter", CHILD),
                Map.entry("employee", EMPLOYEE),
                Map.entry("disableddependent", DISABLED_DEPENDENT),
                Map.entry("self", SELF),
                Map.entry("lifepartner", LIFE_PARTNER),
                Map.entry("domesticpartner", LIFE_PARTNER),
                Map.entry("otherrelated", OTHER_RELATED),
                Map.entry("other", OTHER_RELATED)
        );
    }

    IndividualRelationshipCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an IndividualRelationshipCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching IndividualRelationshipCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static IndividualRelationshipCode fromCode(String code) {
        IndividualRelationshipCode result = CODE_MAP.get(code);
        if (result == null) {
            throw new IllegalArgumentException("Invalid Individual Relationship Code: " + code);
        }
        return result;
    }

    /**
     * Gets an IndividualRelationshipCode instance from a text description.
     * This lookup is case-insensitive and handles spaces and underscores.
     *
     * @param text the text to look up
     * @return the matching IndividualRelationshipCode
     * @throws IllegalArgumentException if no matching text is found
     */
    public static IndividualRelationshipCode fromText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        String normalizedText = normalizeText(text);

        IndividualRelationshipCode result = TEXT_MAP.get(normalizedText);

        if (result == null) {
            throw new IllegalArgumentException("Invalid Individual Relationship text: " + text);
        }
        return result;
    }

    /**
     * Normalizes text for comparison by trimming whitespace, converting to lowercase,
     * and handling other special characters.
     */
    private static String normalizeText(String text) {
        return text
                .trim()
                .toLowerCase()
                .replace("_", "")
                .replace(" ", "");
    }

    @Override
    public String toString() {
        return code;
    }
}