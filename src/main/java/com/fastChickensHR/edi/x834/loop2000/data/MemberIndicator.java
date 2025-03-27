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
 * Enumeration representing the insurance status of a member in an EDI 834 transaction.
 * Used to indicate whether a member is insured or not insured.
 */
@Getter
public enum MemberIndicator implements EdiCodeEnum {
    INSURED("Y", "Insured"),
    NOT_INSURED("N", "Not Insured");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MemberIndicator> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                MemberIndicator.class,
                "Member Indicator",
                Map.ofEntries(
                        Map.entry("y", INSURED),
                        Map.entry("yes", INSURED),
                        Map.entry("true", INSURED),
                        Map.entry("1", INSURED),
                        Map.entry("covered", INSURED),
                        Map.entry("enrolled", INSURED),
                        Map.entry("active", INSURED),
                        Map.entry("eligible", INSURED),
                        Map.entry("has coverage", INSURED),
                        Map.entry("has insurance", INSURED),
                        Map.entry("policyholder", INSURED),
                        Map.entry("member", INSURED),
                        Map.entry("participant", INSURED),

                        Map.entry("n", NOT_INSURED),
                        Map.entry("no", NOT_INSURED),
                        Map.entry("false", NOT_INSURED),
                        Map.entry("0", NOT_INSURED),
                        Map.entry("not covered", NOT_INSURED),
                        Map.entry("not enrolled", NOT_INSURED),
                        Map.entry("inactive", NOT_INSURED),
                        Map.entry("ineligible", NOT_INSURED),
                        Map.entry("no coverage", NOT_INSURED),
                        Map.entry("no insurance", NOT_INSURED),
                        Map.entry("non-member", NOT_INSURED),
                        Map.entry("non member", NOT_INSURED),
                        Map.entry("terminated", NOT_INSURED),
                        Map.entry("cancelled", NOT_INSURED)
                )
        );
    }

    MemberIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MemberIndicator instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching MemberIndicator
     * @throws IllegalArgumentException if no match is found
     */
    public static MemberIndicator fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Gets a MemberIndicator instance from its code value.
     *
     * @param code the code to look up (Y or N)
     * @return the matching MemberIndicator
     * @throws IllegalArgumentException if no matching code is found
     */
    public static MemberIndicator fromCode(String code) {
        for (MemberIndicator value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Member Indicator code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}