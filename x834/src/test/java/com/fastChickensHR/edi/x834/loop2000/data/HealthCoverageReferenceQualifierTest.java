/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HealthCoverageReferenceQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(9, HealthCoverageReferenceQualifier.values().length);
        assertTrue(Arrays.asList(HealthCoverageReferenceQualifier.values())
                .contains(HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER));
    }

    @Test
    void testEnumProperties() {
        assertEquals("1L", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER.getCode());
        assertEquals("Group or Policy Number",
                HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER.getDescription());

        assertEquals("ZZ", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED.getCode());
    }

    @Test
    void testToStringReturnsCode() {
        assertEquals("1L", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER.toString());
        assertEquals("ZZ", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFromString() throws Exception {
        Field lookupField = HealthCoverageReferenceQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<HealthCoverageReferenceQualifier> lookup =
                (EdiEnumLookup<HealthCoverageReferenceQualifier>) lookupField.get(null);

        assertEquals(HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER, lookup.fromString("1L"));
        assertEquals(HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER, lookup.fromString("policy number"));
        assertEquals(HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER, lookup.fromString("group"));

        assertEquals(HealthCoverageReferenceQualifier.MUTUALLY_DEFINED, lookup.fromString("ZZ"));
        assertEquals(HealthCoverageReferenceQualifier.MUTUALLY_DEFINED, lookup.fromString("custom"));

        assertEquals(HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER, lookup.fromString("network id"));
        assertEquals(HealthCoverageReferenceQualifier.RATE_CODE_NUMBER, lookup.fromString("rate code"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
    }

    @Test
    void testStaticFromString() {
        assertEquals(HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER,
                HealthCoverageReferenceQualifier.fromString("1L"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    @SuppressWarnings("unchecked")
    void testAllLookupValues(String input, HealthCoverageReferenceQualifier expected) throws Exception {
        Field lookupField = HealthCoverageReferenceQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<HealthCoverageReferenceQualifier> lookup =
                (EdiEnumLookup<HealthCoverageReferenceQualifier>) lookupField.get(null);
        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("1L", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("17", HealthCoverageReferenceQualifier.CLIENT_REPORTING_CATEGORY),
                Arguments.of("9V", HealthCoverageReferenceQualifier.PAYMENT_CATEGORY),
                Arguments.of("CE", HealthCoverageReferenceQualifier.CLASS_OF_CONTRACT_CODE),
                Arguments.of("E8", HealthCoverageReferenceQualifier.SERVICE_CONTRACT_COVERAGE_NUMBER),
                Arguments.of("M7", HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER),
                Arguments.of("RB", HealthCoverageReferenceQualifier.RATE_CODE_NUMBER),
                Arguments.of("X9", HealthCoverageReferenceQualifier.INTERNAL_CONTROL_NUMBER),
                Arguments.of("ZZ", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED),

                Arguments.of("policy number", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("group number", HealthCoverageReferenceQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("network id", HealthCoverageReferenceQualifier.PLAN_NETWORK_IDENTIFICATION_NUMBER),
                Arguments.of("rate code", HealthCoverageReferenceQualifier.RATE_CODE_NUMBER),
                Arguments.of("custom", HealthCoverageReferenceQualifier.MUTUALLY_DEFINED),
                Arguments.of("control number", HealthCoverageReferenceQualifier.INTERNAL_CONTROL_NUMBER),
                Arguments.of("contract class", HealthCoverageReferenceQualifier.CLASS_OF_CONTRACT_CODE)
        );
    }
}
