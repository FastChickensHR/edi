/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiCodeEnum;
import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Individual Relationship Codes used in the INS02 field
 * of the EDI 834 Member Level Detail (INS) segment.
 */
@Getter
public enum IndividualRelationshipCode implements EdiCodeEnum {
    SPOUSE("01", "Spouse"),
    CHILD("19", "Child"),
    EMPLOYEE("20", "Employee"),
    DISABLED_DEPENDENT("22", "Disabled Dependent"),
    SELF("18", "Self"),
    LIFE_PARTNER("53", "Life Partner"),
    OTHER_RELATED("29", "Other Related");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<IndividualRelationshipCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                IndividualRelationshipCode.class,
                "Individual Relationship Code",
                Map.ofEntries(
                        Map.entry("wife", SPOUSE),
                        Map.entry("husband", SPOUSE),

                        Map.entry("son", CHILD),
                        Map.entry("daughter", CHILD),

                        Map.entry("domesticpartner", LIFE_PARTNER)
                )
        );
    }

    IndividualRelationshipCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an IndividualRelationshipCode instance from any input string.
     * Matches against codes, names, descriptions, and aliases.
     *
     * @param input the string to look up
     * @return the matching IndividualRelationshipCode
     * @throws IllegalArgumentException if no match is found
     */
    public static IndividualRelationshipCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}