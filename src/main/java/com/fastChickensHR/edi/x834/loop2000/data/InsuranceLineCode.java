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
 * Enumeration representing Insurance Line Codes used in the HD03 field
 * of the EDI 834 Health Coverage (HD) segment in Loop 2300.
 * <p>
 * Source: HIPAA 005010X220A1 Benefit Enrollment and Maintenance implementation guide.
 */
@Getter
public enum InsuranceLineCode implements EdiCodeEnum {
    PREVENTIVE_CARE_WELLNESS("AG", "Preventive Care/Wellness"),
    TWENTY_FOUR_HOUR_CARE("AH", "Twenty-four Hour Care"),
    LONG_TERM_CARE("AJ", "Long-term Care"),
    MEDICARE_RISK("AK", "Medicare Risk"),
    MENTAL_HEALTH("AL", "Mental Health"),
    DENTAL_CAPITATION("DCP", "Dental Capitation"),
    DENTAL("DEN", "Dental"),
    EXCLUSIVE_PROVIDER_ORGANIZATION("EPO", "Exclusive Provider Organization"),
    FACILITY("FAC", "Facility"),
    HEARING("HE", "Hearing"),
    HEALTH("HLT", "Health"),
    HEALTH_MAINTENANCE_ORGANIZATION("HMO", "Health Maintenance Organization"),
    HOSPITAL("HSP", "Hospital"),
    LONG_TERM_CARE_LTC("LTC", "Long Term Care"),
    LONG_TERM_DISABILITY("LTD", "Long Term Disability"),
    MAJOR_MEDICAL("MM", "Major Medical"),
    MAIL_ORDER_DRUG("MOD", "Mail Order Drug"),
    PHARMACY("PDG", "Pharmacy"),
    POINT_OF_SERVICE("POS", "Point of Service"),
    PRESCRIPTION_DRUG("PRA", "Prescription Drug"),
    PREFERRED_PROVIDER_ORGANIZATION("PPO", "Preferred Provider Organization"),
    SHORT_TERM_DISABILITY("STD", "Short Term Disability"),
    UTILIZATION_REVIEW("UR", "Utilization Review"),
    VISION_OPTICAL("VIS", "Vision (Optical)");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<InsuranceLineCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                InsuranceLineCode.class,
                "Insurance Line Code",
                Map.<String, InsuranceLineCode>ofEntries(
                        Map.entry("wellness", PREVENTIVE_CARE_WELLNESS),
                        Map.entry("preventive", PREVENTIVE_CARE_WELLNESS),
                        Map.entry("preventative", PREVENTIVE_CARE_WELLNESS),

                        Map.entry("24 hour", TWENTY_FOUR_HOUR_CARE),
                        Map.entry("24-hour", TWENTY_FOUR_HOUR_CARE),

                        Map.entry("long term", LONG_TERM_CARE_LTC),
                        Map.entry("long-term care", LONG_TERM_CARE_LTC),
                        Map.entry("nursing home", LONG_TERM_CARE_LTC),

                        Map.entry("medicare", MEDICARE_RISK),
                        Map.entry("medicare advantage", MEDICARE_RISK),

                        Map.entry("mental", MENTAL_HEALTH),
                        Map.entry("behavioral health", MENTAL_HEALTH),
                        Map.entry("psych", MENTAL_HEALTH),

                        Map.entry("dental cap", DENTAL_CAPITATION),
                        Map.entry("dental capitation plan", DENTAL_CAPITATION),

                        Map.entry("dental insurance", DENTAL),
                        Map.entry("teeth", DENTAL),

                        Map.entry("epo plan", EXCLUSIVE_PROVIDER_ORGANIZATION),

                        Map.entry("facility coverage", FACILITY),

                        Map.entry("hearing aid", HEARING),
                        Map.entry("audiology", HEARING),

                        Map.entry("medical", HEALTH),
                        Map.entry("health insurance", HEALTH),
                        Map.entry("health plan", HEALTH),

                        Map.entry("hmo plan", HEALTH_MAINTENANCE_ORGANIZATION),

                        Map.entry("hospital coverage", HOSPITAL),
                        Map.entry("inpatient", HOSPITAL),

                        Map.entry("ltd", LONG_TERM_DISABILITY),
                        Map.entry("long term disability", LONG_TERM_DISABILITY),

                        Map.entry("major med", MAJOR_MEDICAL),
                        Map.entry("comprehensive medical", MAJOR_MEDICAL),

                        Map.entry("mail order", MAIL_ORDER_DRUG),
                        Map.entry("mail order pharmacy", MAIL_ORDER_DRUG),

                        Map.entry("rx", PHARMACY),
                        Map.entry("drug", PHARMACY),
                        Map.entry("pharmacy benefit", PHARMACY),

                        Map.entry("pos plan", POINT_OF_SERVICE),

                        Map.entry("prescription", PRESCRIPTION_DRUG),
                        Map.entry("prescription drugs", PRESCRIPTION_DRUG),

                        Map.entry("ppo plan", PREFERRED_PROVIDER_ORGANIZATION),

                        Map.entry("std", SHORT_TERM_DISABILITY),
                        Map.entry("short term disability", SHORT_TERM_DISABILITY),

                        Map.entry("utilization", UTILIZATION_REVIEW),
                        Map.entry("ur review", UTILIZATION_REVIEW),

                        Map.entry("vision", VISION_OPTICAL),
                        Map.entry("optical", VISION_OPTICAL),
                        Map.entry("eye", VISION_OPTICAL),
                        Map.entry("eyewear", VISION_OPTICAL)
                )
        );
    }

    InsuranceLineCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an InsuranceLineCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching InsuranceLineCode
     * @throws IllegalArgumentException if no match is found
     */
    public static InsuranceLineCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
