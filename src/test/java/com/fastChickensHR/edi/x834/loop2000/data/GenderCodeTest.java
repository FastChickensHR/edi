/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GenderCodeTest {

    @Test
    void testEnumValues() {
        // Verify enum constants exist and match expected values
        assertEquals(5, GenderCode.values().length);

        assertTrue(Arrays.asList(GenderCode.values()).contains(GenderCode.MALE));
        assertTrue(Arrays.asList(GenderCode.values()).contains(GenderCode.FEMALE));
        assertTrue(Arrays.asList(GenderCode.values()).contains(GenderCode.UNKNOWN));
        assertTrue(Arrays.asList(GenderCode.values()).contains(GenderCode.NON_BINARY));
        assertTrue(Arrays.asList(GenderCode.values()).contains(GenderCode.NOT_SPECIFIED));
    }

    @Test
    void testEnumProperties() {
        // Test code and description for each enum value
        assertEquals("M", GenderCode.MALE.getCode());
        assertEquals("Male", GenderCode.MALE.getDescription());

        assertEquals("F", GenderCode.FEMALE.getCode());
        assertEquals("Female", GenderCode.FEMALE.getDescription());

        assertEquals("U", GenderCode.UNKNOWN.getCode());
        assertEquals("Unknown", GenderCode.UNKNOWN.getDescription());

        assertEquals("N", GenderCode.NON_BINARY.getCode());
        assertEquals("Non-Binary", GenderCode.NON_BINARY.getDescription());

        assertEquals("X", GenderCode.NOT_SPECIFIED.getCode());
        assertEquals("Not Specified", GenderCode.NOT_SPECIFIED.getDescription());

        // Test toString() returns the code
        for (GenderCode code : GenderCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @Test
    void testFromString() {
        assertEquals(GenderCode.MALE, GenderCode.fromString("M"));
        assertEquals(GenderCode.FEMALE, GenderCode.fromString("F"));
        assertEquals(GenderCode.UNKNOWN, GenderCode.fromString("U"));
        assertEquals(GenderCode.NON_BINARY, GenderCode.fromString("N"));
        assertEquals(GenderCode.NOT_SPECIFIED, GenderCode.fromString("X"));

        // Test that invalid inputs throw exceptions
        assertThrows(IllegalArgumentException.class, () -> GenderCode.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, GenderCode expected) throws Exception {
        Field lookupField = GenderCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<GenderCode> lookup = (EdiEnumLookup<GenderCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("M", GenderCode.MALE),
                Arguments.of("F", GenderCode.FEMALE),
                Arguments.of("U", GenderCode.UNKNOWN),
                Arguments.of("N", GenderCode.NON_BINARY),
                Arguments.of("X", GenderCode.NOT_SPECIFIED),

                Arguments.of("MALE", GenderCode.MALE),
                Arguments.of("FEMALE", GenderCode.FEMALE),
                Arguments.of("UNKNOWN", GenderCode.UNKNOWN),
                Arguments.of("NON_BINARY", GenderCode.NON_BINARY),
                Arguments.of("NOT_SPECIFIED", GenderCode.NOT_SPECIFIED),

                Arguments.of("Male", GenderCode.MALE),
                Arguments.of("Female", GenderCode.FEMALE),
                Arguments.of("Unknown", GenderCode.UNKNOWN),
                Arguments.of("Non-Binary", GenderCode.NON_BINARY),
                Arguments.of("Not Specified", GenderCode.NOT_SPECIFIED),

                Arguments.of("m", GenderCode.MALE),
                Arguments.of("man", GenderCode.MALE),
                Arguments.of("boy", GenderCode.MALE),
                Arguments.of("masculine", GenderCode.MALE),

                Arguments.of("f", GenderCode.FEMALE),
                Arguments.of("woman", GenderCode.FEMALE),
                Arguments.of("girl", GenderCode.FEMALE),
                Arguments.of("feminine", GenderCode.FEMALE),

                Arguments.of("u", GenderCode.UNKNOWN),
                Arguments.of("unspecified", GenderCode.UNKNOWN),
                Arguments.of("undetermined", GenderCode.UNKNOWN),
                Arguments.of("undisclosed", GenderCode.UNKNOWN),

                Arguments.of("n", GenderCode.NON_BINARY),
                Arguments.of("nonbinary", GenderCode.NON_BINARY),
                Arguments.of("nb", GenderCode.NON_BINARY),
                Arguments.of("enby", GenderCode.NON_BINARY),
                Arguments.of("genderqueer", GenderCode.NON_BINARY),

                Arguments.of("x", GenderCode.NOT_SPECIFIED),
                Arguments.of("declined", GenderCode.NOT_SPECIFIED),
                Arguments.of("not disclosed", GenderCode.NOT_SPECIFIED),
                Arguments.of("prefer not to say", GenderCode.NOT_SPECIFIED),
                Arguments.of("withheld", GenderCode.NOT_SPECIFIED)
        );
    }
}