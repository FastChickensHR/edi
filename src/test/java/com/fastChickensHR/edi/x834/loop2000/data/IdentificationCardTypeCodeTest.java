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

class IdentificationCardTypeCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(7, IdentificationCardTypeCode.values().length);
        assertTrue(Arrays.asList(IdentificationCardTypeCode.values())
                .contains(IdentificationCardTypeCode.HEALTH));
    }

    @Test
    void testEnumProperties() {
        assertEquals("D", IdentificationCardTypeCode.DENTAL.getCode());
        assertEquals("G", IdentificationCardTypeCode.DRUG.getCode());
        assertEquals("H", IdentificationCardTypeCode.HEALTH.getCode());
        assertEquals("L", IdentificationCardTypeCode.LONG_TERM_CARE.getCode());
        assertEquals("M", IdentificationCardTypeCode.MEDICAL.getCode());
        assertEquals("P", IdentificationCardTypeCode.PHARMACY.getCode());
        assertEquals("V", IdentificationCardTypeCode.VISION.getCode());

        assertEquals("Health", IdentificationCardTypeCode.HEALTH.getDescription());
    }

    @Test
    void testToStringReturnsCode() {
        assertEquals("H", IdentificationCardTypeCode.HEALTH.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFromString() throws Exception {
        Field lookupField = IdentificationCardTypeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<IdentificationCardTypeCode> lookup =
                (EdiEnumLookup<IdentificationCardTypeCode>) lookupField.get(null);

        assertEquals(IdentificationCardTypeCode.HEALTH, lookup.fromString("H"));
        assertEquals(IdentificationCardTypeCode.HEALTH, lookup.fromString("Health"));
        assertEquals(IdentificationCardTypeCode.HEALTH, lookup.fromString("insurance card"));

        assertEquals(IdentificationCardTypeCode.DENTAL, lookup.fromString("D"));
        assertEquals(IdentificationCardTypeCode.DENTAL, lookup.fromString("teeth"));

        assertEquals(IdentificationCardTypeCode.PHARMACY, lookup.fromString("pharmacy card"));
        assertEquals(IdentificationCardTypeCode.VISION, lookup.fromString("eye card"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
    }

    @Test
    void testStaticFromString() {
        assertEquals(IdentificationCardTypeCode.HEALTH, IdentificationCardTypeCode.fromString("H"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    @SuppressWarnings("unchecked")
    void testAllLookupValues(String input, IdentificationCardTypeCode expected) throws Exception {
        Field lookupField = IdentificationCardTypeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<IdentificationCardTypeCode> lookup =
                (EdiEnumLookup<IdentificationCardTypeCode>) lookupField.get(null);
        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("D", IdentificationCardTypeCode.DENTAL),
                Arguments.of("G", IdentificationCardTypeCode.DRUG),
                Arguments.of("H", IdentificationCardTypeCode.HEALTH),
                Arguments.of("L", IdentificationCardTypeCode.LONG_TERM_CARE),
                Arguments.of("M", IdentificationCardTypeCode.MEDICAL),
                Arguments.of("P", IdentificationCardTypeCode.PHARMACY),
                Arguments.of("V", IdentificationCardTypeCode.VISION),

                Arguments.of("health card", IdentificationCardTypeCode.HEALTH),
                Arguments.of("insurance card", IdentificationCardTypeCode.HEALTH),
                Arguments.of("teeth", IdentificationCardTypeCode.DENTAL),
                Arguments.of("dental card", IdentificationCardTypeCode.DENTAL),
                Arguments.of("rx card", IdentificationCardTypeCode.DRUG),
                Arguments.of("ltc", IdentificationCardTypeCode.LONG_TERM_CARE),
                Arguments.of("pharmacy card", IdentificationCardTypeCode.PHARMACY),
                Arguments.of("vision card", IdentificationCardTypeCode.VISION),
                Arguments.of("optical card", IdentificationCardTypeCode.VISION)
        );
    }
}
