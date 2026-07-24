/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Benefit Status Codes (X12 element 1216) emitted at the
 * INS05 field of the EDI 834 Member Level Detail (INS) segment.
 *
 * <p>The full element 1216 list. The former list carried two invented codes
 * ({@code D}, {@code R}), was missing {@code I} and {@code V}, and — most dangerously —
 * labeled {@code T} as "Terminated" when element 1216 defines {@code T} as TEFRA;
 * a pick list built on the old descriptions would have led an administrator to file a
 * member into a TEFRA status while intending to terminate them.
 */
@Getter
public enum BenefitStatusCode implements EdiCodeEnum {
    ACTIVE("A", "Active"),
    COBRA("C", "Consolidated Omnibus Budget Reconciliation Act (COBRA)"),
    INVOLUNTARY("I", "Involuntary"),
    SURVIVING_INSURED("S", "Surviving Insured"),
    TEFRA("T", "Tax Equity and Fiscal Responsibility Act (TEFRA)"),
    VOLUNTARY("V", "Voluntary");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<BenefitStatusCode> LOOKUP;

    static {
        // Colloquial aliases; codes, enum names and descriptions are matched automatically.
        LOOKUP = new EdiEnumLookup<>(
                BenefitStatusCode.class,
                "Benefit Status Code",
                Map.ofEntries(
                        Map.entry("active coverage", ACTIVE),
                        Map.entry("current", ACTIVE),
                        Map.entry("enrolled", ACTIVE),

                        Map.entry("cobra", COBRA),
                        Map.entry("cobra coverage", COBRA),
                        Map.entry("continuation", COBRA),
                        Map.entry("continuation coverage", COBRA),

                        Map.entry("involuntary loss", INVOLUNTARY),

                        Map.entry("survivor", SURVIVING_INSURED),
                        Map.entry("surviving spouse", SURVIVING_INSURED),
                        Map.entry("widow", SURVIVING_INSURED),
                        Map.entry("widower", SURVIVING_INSURED),

                        Map.entry("tefra", TEFRA),

                        Map.entry("voluntary loss", VOLUNTARY)
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
