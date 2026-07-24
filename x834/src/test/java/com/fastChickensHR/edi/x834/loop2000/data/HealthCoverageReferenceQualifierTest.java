/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HealthCoverageReferenceQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(HealthCoverageReferenceQualifier.class)
    void resolvesFromCodeNameAndDescription(HealthCoverageReferenceQualifier constant) {
        assertEquals(constant, HealthCoverageReferenceQualifier.fromString(constant.getCode()));
        assertEquals(constant, HealthCoverageReferenceQualifier.fromString(constant.name()));
        assertEquals(constant, HealthCoverageReferenceQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, HealthCoverageReferenceQualifier expected) {
        assertEquals(expected, HealthCoverageReferenceQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("group number", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("policy number", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("group policy", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("group", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("reporting category", HealthCoverageReferenceQualifier.CLIENT_REPORTING_CATEGORY),
                Arguments.of("client reporting", HealthCoverageReferenceQualifier.CLIENT_REPORTING_CATEGORY),
                Arguments.of("payment cat", HealthCoverageReferenceQualifier.PAYMENT_CATEGORY),
                Arguments.of("payment", HealthCoverageReferenceQualifier.PAYMENT_CATEGORY),
                Arguments.of("class of contract", HealthCoverageReferenceQualifier.CLASS_OF_CONTRACT_CODE),
                Arguments.of("contract class", HealthCoverageReferenceQualifier.CLASS_OF_CONTRACT_CODE),
                Arguments.of("service contract", HealthCoverageReferenceQualifier.SERVICE_CONTRACT_COVERAGE_NUMBER),
                Arguments.of("coverage number", HealthCoverageReferenceQualifier.SERVICE_CONTRACT_COVERAGE_NUMBER),
                Arguments.of("plan network", HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER),
                Arguments.of("network id", HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER),
                Arguments.of("network number", HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER),
                Arguments.of("rate code", HealthCoverageReferenceQualifier.RATE_CODE_NUMBER),
                Arguments.of("rate number", HealthCoverageReferenceQualifier.RATE_CODE_NUMBER),
                Arguments.of("internal control", HealthCoverageReferenceQualifier.INTERNAL_CONTROL_NUMBER),
                Arguments.of("control number", HealthCoverageReferenceQualifier.INTERNAL_CONTROL_NUMBER),
                Arguments.of("mutually defined", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED),
                Arguments.of("custom", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED),
                Arguments.of("user defined", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> HealthCoverageReferenceQualifier.fromString(input));
    }
}
