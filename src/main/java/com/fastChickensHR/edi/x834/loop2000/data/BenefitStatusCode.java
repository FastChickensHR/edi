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
 * Enumeration representing Benefit Status Codes used in the HD05 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum BenefitStatusCode implements EdiCodeEnum {
    ACTIVE("A", "Active"),
    COBRA("C", "COBRA"),
    DISABLED("D", "Disabled"),
    RETIREE("R", "Retiree"),
    SURVIVING_INSURED("S", "Surviving Insured"),
    TERMINATED("T", "Terminated");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<BenefitStatusCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                BenefitStatusCode.class,
                "Benefit Status Code",
                Map.ofEntries(
                        Map.entry("a", ACTIVE),
                        Map.entry("active coverage", ACTIVE),
                        Map.entry("current", ACTIVE),
                        Map.entry("enrolled", ACTIVE),

                        Map.entry("c", COBRA),
                        Map.entry("cobra coverage", COBRA),
                        Map.entry("consolidated omnibus budget reconciliation act", COBRA),
                        Map.entry("continuation", COBRA),
                        Map.entry("continuation coverage", COBRA),
                        Map.entry("continued benefits", COBRA),
                        Map.entry("extended coverage", COBRA),
                        Map.entry("cobra eligible", COBRA),
                        Map.entry("cobra qualified", COBRA),

                        Map.entry("d", DISABLED),
                        Map.entry("disability", DISABLED),
                        Map.entry("on disability", DISABLED),
                        Map.entry("disability benefits", DISABLED),
                        Map.entry("ltd", DISABLED),
                        Map.entry("long term disability", DISABLED),
                        Map.entry("short term disability", DISABLED),
                        Map.entry("std", DISABLED),

                        Map.entry("r", RETIREE),
                        Map.entry("retired", RETIREE),
                        Map.entry("retirement", RETIREE),
                        Map.entry("retirement benefits", RETIREE),
                        Map.entry("pension", RETIREE),
                        Map.entry("pensioner", RETIREE),
                        Map.entry("former employee", RETIREE),
                        Map.entry("emeritus", RETIREE),
                        Map.entry("retired employee", RETIREE),

                        Map.entry("s", SURVIVING_INSURED),
                        Map.entry("survivor", SURVIVING_INSURED),
                        Map.entry("surviving dependent", SURVIVING_INSURED),
                        Map.entry("widow", SURVIVING_INSURED),
                        Map.entry("widower", SURVIVING_INSURED),
                        Map.entry("bereaved", SURVIVING_INSURED),
                        Map.entry("surviving spouse", SURVIVING_INSURED),
                        Map.entry("survivor benefits", SURVIVING_INSURED),
                        Map.entry("death benefit", SURVIVING_INSURED),

                        Map.entry("t", TERMINATED),
                        Map.entry("term", TERMINATED),
                        Map.entry("termed", TERMINATED),
                        Map.entry("termination", TERMINATED),
                        Map.entry("cancelled", TERMINATED),
                        Map.entry("canceled", TERMINATED),
                        Map.entry("ended", TERMINATED),
                        Map.entry("expired", TERMINATED),
                        Map.entry("discontinued", TERMINATED),
                        Map.entry("no longer active", TERMINATED),
                        Map.entry("no longer employed", TERMINATED),
                        Map.entry("separation", TERMINATED),
                        Map.entry("let go", TERMINATED),
                        Map.entry("laid off", TERMINATED),
                        Map.entry("fired", TERMINATED),
                        Map.entry("inactive", TERMINATED)
                )
        );
    }

    BenefitStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a BenefitStatusCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching BenefitStatusCode
     * @throws IllegalArgumentException if no match is found
     */
    public static BenefitStatusCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Gets a BenefitStatusCode instance from its code value.
     *
     * @param code the code to look up
     * @return the matching BenefitStatusCode
     * @throws IllegalArgumentException if no matching code is found
     */
    public static BenefitStatusCode fromCode(String code) {
        for (BenefitStatusCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Benefit Status Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}