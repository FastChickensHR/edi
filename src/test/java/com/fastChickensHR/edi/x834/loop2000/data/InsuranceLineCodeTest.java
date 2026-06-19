/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceLineCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(24, InsuranceLineCode.values().length,
                "InsuranceLineCode should have 24 enum values");

        assertTrue(Arrays.asList(InsuranceLineCode.values()).contains(InsuranceLineCode.HEALTH));
        assertTrue(Arrays.asList(InsuranceLineCode.values()).contains(InsuranceLineCode.DENTAL));
        assertTrue(Arrays.asList(InsuranceLineCode.values()).contains(InsuranceLineCode.VISION_OPTICAL));
    }

    @Test
    void testEnumProperties() {
        assertEquals("HLT", InsuranceLineCode.HEALTH.getCode());
        assertEquals("Health", InsuranceLineCode.HEALTH.getDescription());

        assertEquals("DEN", InsuranceLineCode.DENTAL.getCode());
        assertEquals("Dental", InsuranceLineCode.DENTAL.getDescription());

        assertEquals("VIS", InsuranceLineCode.VISION_OPTICAL.getCode());

        assertEquals("HMO", InsuranceLineCode.HEALTH_MAINTENANCE_ORGANIZATION.getCode());
        assertEquals("PPO", InsuranceLineCode.PREFERRED_PROVIDER_ORGANIZATION.getCode());
    }

    @Test
    void testToStringReturnsCode() {
        assertEquals("HLT", InsuranceLineCode.HEALTH.toString());
        assertEquals("DEN", InsuranceLineCode.DENTAL.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFromString() throws Exception {
        Field lookupField = InsuranceLineCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<InsuranceLineCode> lookup =
                (EdiEnumLookup<InsuranceLineCode>) lookupField.get(null);

        assertEquals(InsuranceLineCode.HEALTH, lookup.fromString("HLT"));
        assertEquals(InsuranceLineCode.DENTAL, lookup.fromString("DEN"));

        assertEquals(InsuranceLineCode.HEALTH, lookup.fromString("Health"));
        assertEquals(InsuranceLineCode.HEALTH, lookup.fromString("HEALTH"));
        assertEquals(InsuranceLineCode.HEALTH, lookup.fromString("health"));

        assertEquals(InsuranceLineCode.HEALTH, lookup.fromString("medical"));
        assertEquals(InsuranceLineCode.DENTAL, lookup.fromString("teeth"));
        assertEquals(InsuranceLineCode.VISION_OPTICAL, lookup.fromString("eye"));
        assertEquals(InsuranceLineCode.PHARMACY, lookup.fromString("rx"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(null));
    }

    @Test
    void testStaticFromString() {
        assertEquals(InsuranceLineCode.HEALTH, InsuranceLineCode.fromString("HLT"));
        assertEquals(InsuranceLineCode.DENTAL, InsuranceLineCode.fromString("teeth"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    @SuppressWarnings("unchecked")
    void testAllLookupValues(String input, InsuranceLineCode expected) throws Exception {
        Field lookupField = InsuranceLineCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<InsuranceLineCode> lookup =
                (EdiEnumLookup<InsuranceLineCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("HLT", InsuranceLineCode.HEALTH),
                Arguments.of("DEN", InsuranceLineCode.DENTAL),
                Arguments.of("VIS", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("HMO", InsuranceLineCode.HEALTH_MAINTENANCE_ORGANIZATION),
                Arguments.of("PPO", InsuranceLineCode.PREFERRED_PROVIDER_ORGANIZATION),
                Arguments.of("EPO", InsuranceLineCode.EXCLUSIVE_PROVIDER_ORGANIZATION),
                Arguments.of("PDG", InsuranceLineCode.PHARMACY),

                Arguments.of("medical", InsuranceLineCode.HEALTH),
                Arguments.of("health insurance", InsuranceLineCode.HEALTH),
                Arguments.of("teeth", InsuranceLineCode.DENTAL),
                Arguments.of("vision", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("optical", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("eyewear", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("prescription", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("drug", InsuranceLineCode.PHARMACY),
                Arguments.of("ltd", InsuranceLineCode.LONG_TERM_DISABILITY),
                Arguments.of("std", InsuranceLineCode.SHORT_TERM_DISABILITY),
                Arguments.of("nursing home", InsuranceLineCode.LONG_TERM_CARE_LTC),
                Arguments.of("mental", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("behavioral health", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("wellness", InsuranceLineCode.PREVENTIVE_CARE_WELLNESS)
        );
    }
}
