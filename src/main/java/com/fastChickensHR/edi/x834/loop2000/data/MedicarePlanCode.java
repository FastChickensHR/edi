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
 * Enumeration representing Medicare Plan Codes used in the EDI 834
 * Health Insurance Enrollment transaction.
 */
@Getter
public enum MedicarePlanCode implements EdiCodeEnum {
    HOSPITAL_ONLY("A", "Hospital Only (Part A)"),
    MEDICAL_ONLY("B", "Medical Only (Part B)"),
    HOSPITAL_AND_MEDICAL("C", "Hospital and Medical (Parts A and B)"),
    MEDICARE_ADVANTAGE("D", "Medicare Advantage (Part C)"),
    PRESCRIPTION_DRUG("E", "Prescription Drug (Part D)"),
    MEDIGAP("F", "Medigap (Medicare Supplement)"),
    NO_MEDICARE("N", "No Medicare Coverage");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MedicarePlanCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                MedicarePlanCode.class,
                "Medicare Plan Code",
                Map.ofEntries(
                        Map.entry("a", HOSPITAL_ONLY),
                        Map.entry("part a", HOSPITAL_ONLY),
                        Map.entry("hospital insurance", HOSPITAL_ONLY),
                        Map.entry("inpatient", HOSPITAL_ONLY),
                        Map.entry("hospital care", HOSPITAL_ONLY),
                        Map.entry("skilled nursing", HOSPITAL_ONLY),

                        Map.entry("part b", MEDICAL_ONLY),
                        Map.entry("b", MEDICAL_ONLY),
                        Map.entry("medical insurance", MEDICAL_ONLY),
                        Map.entry("outpatient", MEDICAL_ONLY),
                        Map.entry("doctor visits", MEDICAL_ONLY),
                        Map.entry("physician services", MEDICAL_ONLY),
                        Map.entry("preventive services", MEDICAL_ONLY),

                        Map.entry("parts a and b", HOSPITAL_AND_MEDICAL),
                        Map.entry("ab", HOSPITAL_AND_MEDICAL),
                        Map.entry("a and b", HOSPITAL_AND_MEDICAL),
                        Map.entry("a & b", HOSPITAL_AND_MEDICAL),
                        Map.entry("hospital and medical", HOSPITAL_AND_MEDICAL),
                        Map.entry("original medicare", HOSPITAL_AND_MEDICAL),
                        Map.entry("traditional medicare", HOSPITAL_AND_MEDICAL),

                        Map.entry("part c", MEDICARE_ADVANTAGE),
                        Map.entry("c", MEDICARE_ADVANTAGE),
                        Map.entry("advantage", MEDICARE_ADVANTAGE),
                        Map.entry("ma", MEDICARE_ADVANTAGE),
                        Map.entry("managed care", MEDICARE_ADVANTAGE),
                        Map.entry("medicare health plan", MEDICARE_ADVANTAGE),
                        Map.entry("hmo", MEDICARE_ADVANTAGE),
                        Map.entry("ppo", MEDICARE_ADVANTAGE),

                        Map.entry("part d", PRESCRIPTION_DRUG),
                        Map.entry("d", PRESCRIPTION_DRUG),
                        Map.entry("drug coverage", PRESCRIPTION_DRUG),
                        Map.entry("prescription coverage", PRESCRIPTION_DRUG),
                        Map.entry("rx", PRESCRIPTION_DRUG),
                        Map.entry("medication", PRESCRIPTION_DRUG),
                        Map.entry("pdp", PRESCRIPTION_DRUG),
                        Map.entry("pharmacy", PRESCRIPTION_DRUG),

                        Map.entry("supplement", MEDIGAP),
                        Map.entry("supplemental", MEDIGAP),
                        Map.entry("gap coverage", MEDIGAP),
                        Map.entry("med supp", MEDIGAP),
                        Map.entry("supplementary", MEDIGAP),
                        Map.entry("secondary insurance", MEDIGAP),
                        Map.entry("gap insurance", MEDIGAP),

                        Map.entry("no coverage", NO_MEDICARE),
                        Map.entry("not enrolled", NO_MEDICARE),
                        Map.entry("without medicare", NO_MEDICARE),
                        Map.entry("non-medicare", NO_MEDICARE),
                        Map.entry("no enrollment", NO_MEDICARE),
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