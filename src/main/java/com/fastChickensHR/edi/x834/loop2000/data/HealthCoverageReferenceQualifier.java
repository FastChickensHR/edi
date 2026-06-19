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
 * Enumeration representing Reference Identification Qualifier codes that are valid
 * for the REF01 field of REF segments inside Loop 2300 (Health Coverage) of the
 * EDI 834 transaction.
 * <p>
 * This is a curated subset of the global X12 ReferenceIdentificationQualifier
 * code list, restricted to those qualifiers permitted by the HIPAA 005010X220A1
 * implementation guide for Loop 2300 (REF*1L, REF*17, REF*9V, REF*CE, REF*E8,
 * REF*M7, REF*RB, REF*X9, REF*ZZ, etc.).
 */
@Getter
public enum HealthCoverageReferenceQualifier implements EdiCodeEnum {
    GROUP_OR_POLICY_NUMBER("1L", "Group or Policy Number"),
    CLIENT_REPORTING_CATEGORY("17", "Client Reporting Category"),
    PAYMENT_CATEGORY("9V", "Payment Category"),
    CLASS_OF_CONTRACT_CODE("CE", "Class of Contract Code"),
    SERVICE_CONTRACT_COVERAGE_NUMBER("E8", "Service Contract (Coverage) Number"),
    PLAN_NETWORK_IDENTIFICATION_NUMBER("M7", "Plan Network Identification Number"),
    RATE_CODE_NUMBER("RB", "Rate Code Number"),
    INTERNAL_CONTROL_NUMBER("X9", "Internal Control Number"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<HealthCoverageReferenceQualifier> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                HealthCoverageReferenceQualifier.class,
                "Health Coverage Reference Qualifier",
                Map.<String, HealthCoverageReferenceQualifier>ofEntries(
                        Map.entry("group number", GROUP_OR_POLICY_NUMBER),
                        Map.entry("policy number", GROUP_OR_POLICY_NUMBER),
                        Map.entry("group policy", GROUP_OR_POLICY_NUMBER),
                        Map.entry("group", GROUP_OR_POLICY_NUMBER),

                        Map.entry("reporting category", CLIENT_REPORTING_CATEGORY),
                        Map.entry("client reporting", CLIENT_REPORTING_CATEGORY),

                        Map.entry("payment cat", PAYMENT_CATEGORY),
                        Map.entry("payment", PAYMENT_CATEGORY),

                        Map.entry("class of contract", CLASS_OF_CONTRACT_CODE),
                        Map.entry("contract class", CLASS_OF_CONTRACT_CODE),

                        Map.entry("service contract", SERVICE_CONTRACT_COVERAGE_NUMBER),
                        Map.entry("coverage number", SERVICE_CONTRACT_COVERAGE_NUMBER),

                        Map.entry("plan network", PLAN_NETWORK_IDENTIFICATION_NUMBER),
                        Map.entry("network id", PLAN_NETWORK_IDENTIFICATION_NUMBER),
                        Map.entry("network number", PLAN_NETWORK_IDENTIFICATION_NUMBER),

                        Map.entry("rate code", RATE_CODE_NUMBER),
                        Map.entry("rate number", RATE_CODE_NUMBER),

                        Map.entry("internal control", INTERNAL_CONTROL_NUMBER),
                        Map.entry("control number", INTERNAL_CONTROL_NUMBER),

                        Map.entry("mutually defined", MUTUALLY_DEFINED),
                        Map.entry("custom", MUTUALLY_DEFINED),
                        Map.entry("user defined", MUTUALLY_DEFINED)
                )
        );
    }

    HealthCoverageReferenceQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a HealthCoverageReferenceQualifier instance from any input string.
     *
     * @param input the string to look up
     * @return the matching qualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static HealthCoverageReferenceQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
