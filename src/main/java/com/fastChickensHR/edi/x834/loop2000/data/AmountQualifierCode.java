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
 * Enumeration representing Amount Qualifier Codes used in the AMT01 field
 * of the EDI 834 Health Coverage Policy Amounts (AMT) segment in Loop 2300.
 * <p>
 * Source: HIPAA 005010X220A1 Benefit Enrollment and Maintenance implementation guide.
 */
@Getter
public enum AmountQualifierCode implements EdiCodeEnum {
    COINSURANCE_ACTUAL("B9", "Co-Insurance - Actual"),
    DEDUCTIBLE_AMOUNT("D2", "Deductible Amount"),
    PREMIUM_AMOUNT("FK", "Premium Amount"),
    SPEND_DOWN("P3", "Spend Down"),
    EXPECTED_EXPENDITURE_AMOUNT("R", "Expected Expenditure Amount");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<AmountQualifierCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                AmountQualifierCode.class,
                "Amount Qualifier Code",
                Map.<String, AmountQualifierCode>ofEntries(
                        Map.entry("coinsurance", COINSURANCE_ACTUAL),
                        Map.entry("co-insurance", COINSURANCE_ACTUAL),
                        Map.entry("co insurance", COINSURANCE_ACTUAL),
                        Map.entry("actual coinsurance", COINSURANCE_ACTUAL),

                        Map.entry("deductible", DEDUCTIBLE_AMOUNT),
                        Map.entry("annual deductible", DEDUCTIBLE_AMOUNT),
                        Map.entry("ded", DEDUCTIBLE_AMOUNT),

                        Map.entry("premium", PREMIUM_AMOUNT),
                        Map.entry("monthly premium", PREMIUM_AMOUNT),
                        Map.entry("policy premium", PREMIUM_AMOUNT),

                        Map.entry("spenddown", SPEND_DOWN),
                        Map.entry("spend-down", SPEND_DOWN),
                        Map.entry("medicaid spend down", SPEND_DOWN),

                        Map.entry("expected expenditure", EXPECTED_EXPENDITURE_AMOUNT),
                        Map.entry("expenditure", EXPECTED_EXPENDITURE_AMOUNT)
                )
        );
    }

    AmountQualifierCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an AmountQualifierCode instance from any input string.
     *
     * @param input the string to look up
     * @return the matching AmountQualifierCode
     * @throws IllegalArgumentException if no match is found
     */
    public static AmountQualifierCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
