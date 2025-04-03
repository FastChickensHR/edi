/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResponsibleAgencyCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(2, ResponsibleAgencyCode.values().length, "Should have exactly 2 enum values");

        assertArrayEquals(
                new ResponsibleAgencyCode[]{
                        ResponsibleAgencyCode.TDCC,
                        ResponsibleAgencyCode.ASC_X12
                },
                ResponsibleAgencyCode.values(),
                "Enum values should match the expected order"
        );
    }

    @Test
    void testEnumProperties() {
        assertEquals("T", ResponsibleAgencyCode.TDCC.getCode(), "TDCC code should be 'T'");
        assertEquals("Transportation Data Coordinating Committee (TDCC)",
                ResponsibleAgencyCode.TDCC.getDescription(),
                "TDCC description should match");
        assertEquals("T", ResponsibleAgencyCode.TDCC.toString(), "toString() should return the code");

        assertEquals("X", ResponsibleAgencyCode.ASC_X12.getCode(), "ASC_X12 code should be 'X'");
        assertEquals("Accredited Standards Committee X12",
                ResponsibleAgencyCode.ASC_X12.getDescription(),
                "ASC_X12 description should match");
        assertEquals("X", ResponsibleAgencyCode.ASC_X12.toString(), "toString() should return the code");
    }

    @Test
    void testFromString() {
        assertEquals(ResponsibleAgencyCode.TDCC, ResponsibleAgencyCode.fromString("T"),
                "Should find TDCC by code 'T'");
        assertEquals(ResponsibleAgencyCode.ASC_X12, ResponsibleAgencyCode.fromString("X"),
                "Should find ASC_X12 by code 'X'");

        assertEquals(ResponsibleAgencyCode.TDCC, ResponsibleAgencyCode.fromString("t"),
                "Should find TDCC by lowercase 't'");
        assertEquals(ResponsibleAgencyCode.ASC_X12, ResponsibleAgencyCode.fromString("x"),
                "Should find ASC_X12 by lowercase 'x'");

        assertEquals(ResponsibleAgencyCode.TDCC, ResponsibleAgencyCode.fromString("TDCC"),
                "Should find by enum name TDCC");
        assertEquals(ResponsibleAgencyCode.ASC_X12, ResponsibleAgencyCode.fromString("ASC_X12"),
                "Should find by enum name ASC_X12");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ResponsibleAgencyCode.fromString("INVALID"));
        assertTrue(exception.getMessage().contains("Invalid Responsible Agency Code"),
                "Exception message should mention the enum type");
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, ResponsibleAgencyCode expected) throws Exception {
        assertEquals(expected, ResponsibleAgencyCode.fromString(input),
                "Should lookup " + input + " to " + expected);
    }

    private static Stream<Arguments> provideLookupValues() throws Exception {
        Field lookupField = ResponsibleAgencyCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<?> lookup = (EdiEnumLookup<?>) lookupField.get(null);
        Field lookupMapField = EdiEnumLookup.class.getDeclaredField("lookupMap");
        lookupMapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        var lookupMap = (java.util.Map<String, ResponsibleAgencyCode>) lookupMapField.get(lookup);

        Stream<Arguments> enumCases = Arrays.stream(ResponsibleAgencyCode.values())
                .flatMap(value -> Stream.of(
                        Arguments.of(value.getCode(), value),
                        Arguments.of(value.getCode().toLowerCase(), value),
                        Arguments.of(value.name(), value),
                        Arguments.of(value.name().toLowerCase(), value)
                ));

        Stream<Arguments> lookupCases = lookupMap.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));

        return Stream.concat(enumCases, lookupCases);
    }
}