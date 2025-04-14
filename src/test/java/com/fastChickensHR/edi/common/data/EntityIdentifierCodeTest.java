/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EntityIdentifierCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("00", EntityIdentifierCode.ALTERNATE_INSURER.getCode());
        assertEquals("01", EntityIdentifierCode.LOAN_APPLICANT.getCode());
        assertEquals("1A", EntityIdentifierCode.SUBGROUP.getCode());
        assertEquals("2A", EntityIdentifierCode.FEDERAL_STATE_COUNTY_CITY_FACILITY.getCode());

        assertEquals("Alternate Insurer", EntityIdentifierCode.ALTERNATE_INSURER.getDescription());
        assertEquals("Loan Applicant", EntityIdentifierCode.LOAN_APPLICANT.getDescription());
    }

    @Test
    void testEnumProperties() {
        // Ensure all enum values have valid properties set
        for (EntityIdentifierCode code : EntityIdentifierCode.values()) {
            assertNotNull(code.getCode(), "Code should not be null for " + code.name());
            assertFalse(code.getCode().isEmpty(), "Code should not be empty for " + code.name());

            assertNotNull(code.getDescription(), "Description should not be null for " + code.name());
            assertFalse(code.getDescription().isEmpty(), "Description should not be empty for " + code.name());
        }
    }

    @Test
    void testFromString() {
        assertEquals(EntityIdentifierCode.ALTERNATE_INSURER, EntityIdentifierCode.fromString("00"));
        assertEquals(EntityIdentifierCode.LOAN_APPLICANT, EntityIdentifierCode.fromString("01"));
        assertEquals(EntityIdentifierCode.SUBGROUP, EntityIdentifierCode.fromString("1A"));
        assertEquals(EntityIdentifierCode.SUBGROUP, EntityIdentifierCode.fromString("1a"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, EntityIdentifierCode expected) {
        assertEquals(expected, EntityIdentifierCode.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Arrays.stream(EntityIdentifierCode.values())
                .map(code -> Arguments.of(code.getCode(), code));
    }

    @Test
    void testToString() {
        assertEquals("00", EntityIdentifierCode.ALTERNATE_INSURER.toString());
        assertEquals("01", EntityIdentifierCode.LOAN_APPLICANT.toString());
        assertEquals("1A", EntityIdentifierCode.SUBGROUP.toString());
        assertEquals("2A", EntityIdentifierCode.FEDERAL_STATE_COUNTY_CITY_FACILITY.toString());
    }
}