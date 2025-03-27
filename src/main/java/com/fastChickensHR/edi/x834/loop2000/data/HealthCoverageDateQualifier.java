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
 * Enumeration representing Date Time Qualifiers used in the DTP01 field
 * of the EDI 834 Health Coverage Dates (DTP) segment.
 */
@Getter
public enum HealthCoverageDateQualifier implements EdiCodeEnum {
    EFFECTIVE_DATE("348", "Plan Begin Date"),
    EXPIRATION_DATE("349", "Plan End Date"),
    ELIGIBILITY_BEGIN("356", "Eligibility Begin Date"),
    ELIGIBILITY_END("357", "Eligibility End Date"),
    COBRA_BEGIN("343", "COBRA Begin Date"),
    COBRA_END("344", "COBRA End Date"),
    PREMIUM_PAID_TO_DATE("309", "Premium Paid To Date");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<HealthCoverageDateQualifier> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                HealthCoverageDateQualifier.class,
                "Health Coverage Date Qualifier",
                Map.ofEntries(
                        Map.entry("start date", EFFECTIVE_DATE),
                        Map.entry("begin date", EFFECTIVE_DATE),
                        Map.entry("start", EFFECTIVE_DATE),
                        Map.entry("begins", EFFECTIVE_DATE),
                        Map.entry("effective", EFFECTIVE_DATE),

                        Map.entry("end date", EXPIRATION_DATE),
                        Map.entry("termination date", EXPIRATION_DATE),
                        Map.entry("expires", EXPIRATION_DATE),
                        Map.entry("expiry", EXPIRATION_DATE),
                        Map.entry("ending", EXPIRATION_DATE),
                        Map.entry("termination", EXPIRATION_DATE),
                        Map.entry("cancellation", EXPIRATION_DATE),

                        Map.entry("eligibility start", ELIGIBILITY_BEGIN),
                        Map.entry("eligible from", ELIGIBILITY_BEGIN),
                        Map.entry("eligible start", ELIGIBILITY_BEGIN),

                        Map.entry("eligibility stop", ELIGIBILITY_END),
                        Map.entry("eligible until", ELIGIBILITY_END),
                        Map.entry("eligible end", ELIGIBILITY_END),
                        Map.entry("benefits end", ELIGIBILITY_END),
                        Map.entry("benefits stop", ELIGIBILITY_END),

                        Map.entry("cobra start", COBRA_BEGIN),
                        Map.entry("cobra effective", COBRA_BEGIN),
                        Map.entry("consolidated omnibus budget reconciliation act start", COBRA_BEGIN),

                        Map.entry("cobra stop", COBRA_END),
                        Map.entry("cobra termination", COBRA_END),
                        Map.entry("cobra expiration", COBRA_END),
                        Map.entry("consolidated omnibus budget reconciliation act end", COBRA_END),

                        Map.entry("premium paid", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid through", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid to", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid until", PREMIUM_PAID_TO_DATE),
                        Map.entry("premium through", PREMIUM_PAID_TO_DATE),
                        Map.entry("payment date", PREMIUM_PAID_TO_DATE)
                )
        );
    }

    HealthCoverageDateQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a HealthCoverageDateQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching HealthCoverageDateQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static HealthCoverageDateQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}