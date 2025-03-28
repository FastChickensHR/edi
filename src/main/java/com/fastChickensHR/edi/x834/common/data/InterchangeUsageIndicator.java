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
 * Enumeration representing Interchange Usage Indicators used in EDI transactions,
 * specifically in the ISA15 segment of the interchange control header.
 */
@Getter
public enum InterchangeUsageIndicator implements EdiCodeEnum {
    INFORMATION("I", "Information"),
    PRODUCTION("P", "Production Data"),
    TEST("T", "Test Data");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<InterchangeUsageIndicator> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                InterchangeUsageIndicator.class,
                "Interchange Usage Indicator",
                Map.ofEntries(
                        Map.entry("i", INFORMATION),
                        Map.entry("info", INFORMATION),
                        Map.entry("information", INFORMATION),
                        Map.entry("informational", INFORMATION),

                        Map.entry("p", PRODUCTION),
                        Map.entry("prod", PRODUCTION),
                        Map.entry("production", PRODUCTION),
                        Map.entry("production data", PRODUCTION),
                        Map.entry("live", PRODUCTION),
                        Map.entry("real", PRODUCTION),

                        Map.entry("t", TEST),
                        Map.entry("test", TEST),
                        Map.entry("testing", TEST),
                        Map.entry("test data", TEST),
                        Map.entry("dev", TEST),
                        Map.entry("development", TEST),
                        Map.entry("sandbox", TEST)
                )
        );
    }

    InterchangeUsageIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an InterchangeUsageIndicator instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching InterchangeUsageIndicator
     * @throws IllegalArgumentException if no match is found
     */
    public static InterchangeUsageIndicator fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}