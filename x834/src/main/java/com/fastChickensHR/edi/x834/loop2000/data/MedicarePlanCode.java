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
 * Enumeration representing Medicare Plan Codes (X12 element 1218) emitted at the
 * INS06 (composite C052-01) field of the EDI 834 transaction.
 *
 * <p>The full element 1218 list. The former list carried two invented codes
 * ({@code F} Medigap, {@code N} No Medicare) and attached invented "Part C / Part D"
 * meanings to {@code D} and {@code E}: in element 1218 {@code D} is the general Medicare
 * program code and {@code E} is "No Medicare" (the declined/none code carriers default
 * to).
 */
@Getter
public enum MedicarePlanCode implements EdiCodeEnum {
    PART_A("A", "Medicare Part A"),
    PART_B("B", "Medicare Part B"),
    PART_A_AND_B("C", "Medicare Part A and B"),
    MEDICARE("D", "Medicare"),
    NO_MEDICARE("E", "No Medicare");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MedicarePlanCode> LOOKUP;

    static {
        // Colloquial aliases; codes, enum names and descriptions are matched automatically.
        LOOKUP = new EdiEnumLookup<>(
                MedicarePlanCode.class,
                "Medicare Plan Code",
                Map.ofEntries(
                        Map.entry("part a", PART_A),
                        Map.entry("hospital insurance", PART_A),
                        Map.entry("inpatient", PART_A),

                        Map.entry("part b", PART_B),
                        Map.entry("medical insurance", PART_B),
                        Map.entry("outpatient", PART_B),

                        Map.entry("parts a and b", PART_A_AND_B),
                        Map.entry("a and b", PART_A_AND_B),
                        Map.entry("original medicare", PART_A_AND_B),

                        Map.entry("has medicare", MEDICARE),
                        Map.entry("medicare eligible", MEDICARE),

                        Map.entry("no coverage", NO_MEDICARE),
                        Map.entry("not enrolled", NO_MEDICARE),
                        Map.entry("without medicare", NO_MEDICARE),
                        Map.entry("non-medicare", NO_MEDICARE),
                        Map.entry("not eligible", NO_MEDICARE)
                )
        );
    }

    MedicarePlanCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MedicarePlanCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching MedicarePlanCode
     * @throws IllegalArgumentException if no match is found
     */
    public static MedicarePlanCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
