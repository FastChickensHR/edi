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

class ConfidentialityCodeTest {

    @Test
    void testEnumValues() {
        // Verify enum constants exist and match expected values
        assertEquals(8, ConfidentialityCode.values().length);

        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.UNRESTRICTED));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.RESTRICTED));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.CONFIDENTIAL));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.VERY_RESTRICTED));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.NORMAL));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.LOW));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.MEDIUM));
        assertTrue(Arrays.asList(ConfidentialityCode.values()).contains(ConfidentialityCode.HIGH));
    }

    @Test
    void testEnumProperties() {
        // Test code and description for each enum value
        assertEquals("U", ConfidentialityCode.UNRESTRICTED.getCode());
        assertEquals("Unrestricted", ConfidentialityCode.UNRESTRICTED.getDescription());

        assertEquals("R", ConfidentialityCode.RESTRICTED.getCode());
        assertEquals("Restricted", ConfidentialityCode.RESTRICTED.getDescription());

        assertEquals("C", ConfidentialityCode.CONFIDENTIAL.getCode());
        assertEquals("Confidential", ConfidentialityCode.CONFIDENTIAL.getDescription());

        assertEquals("V", ConfidentialityCode.VERY_RESTRICTED.getCode());
        assertEquals("Very Restricted", ConfidentialityCode.VERY_RESTRICTED.getDescription());

        assertEquals("N", ConfidentialityCode.NORMAL.getCode());
        assertEquals("Normal", ConfidentialityCode.NORMAL.getDescription());

        assertEquals("L", ConfidentialityCode.LOW.getCode());
        assertEquals("Low", ConfidentialityCode.LOW.getDescription());

        assertEquals("M", ConfidentialityCode.MEDIUM.getCode());
        assertEquals("Medium", ConfidentialityCode.MEDIUM.getDescription());

        assertEquals("H", ConfidentialityCode.HIGH.getCode());
        assertEquals("High", ConfidentialityCode.HIGH.getDescription());

        // Test toString() returns the code
        for (ConfidentialityCode code : ConfidentialityCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, ConfidentialityCode expected) throws Exception {
        Field lookupField = ConfidentialityCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<ConfidentialityCode> lookup = (EdiEnumLookup<ConfidentialityCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("U", ConfidentialityCode.UNRESTRICTED),
                Arguments.of("R", ConfidentialityCode.RESTRICTED),
                Arguments.of("C", ConfidentialityCode.CONFIDENTIAL),
                Arguments.of("V", ConfidentialityCode.VERY_RESTRICTED),
                Arguments.of("N", ConfidentialityCode.NORMAL),
                Arguments.of("L", ConfidentialityCode.LOW),
                Arguments.of("M", ConfidentialityCode.MEDIUM),
                Arguments.of("H", ConfidentialityCode.HIGH),

                Arguments.of("UNRESTRICTED", ConfidentialityCode.UNRESTRICTED),
                Arguments.of("RESTRICTED", ConfidentialityCode.RESTRICTED),
                Arguments.of("CONFIDENTIAL", ConfidentialityCode.CONFIDENTIAL),
                Arguments.of("VERY_RESTRICTED", ConfidentialityCode.VERY_RESTRICTED),
                Arguments.of("NORMAL", ConfidentialityCode.NORMAL),
                Arguments.of("LOW", ConfidentialityCode.LOW),
                Arguments.of("MEDIUM", ConfidentialityCode.MEDIUM),
                Arguments.of("HIGH", ConfidentialityCode.HIGH),

                Arguments.of("Unrestricted", ConfidentialityCode.UNRESTRICTED),
                Arguments.of("Restricted", ConfidentialityCode.RESTRICTED),
                Arguments.of("Confidential", ConfidentialityCode.CONFIDENTIAL),
                Arguments.of("Very Restricted", ConfidentialityCode.VERY_RESTRICTED),
                Arguments.of("Normal", ConfidentialityCode.NORMAL),
                Arguments.of("Low", ConfidentialityCode.LOW),
                Arguments.of("Medium", ConfidentialityCode.MEDIUM),
                Arguments.of("High", ConfidentialityCode.HIGH),

                Arguments.of("u", ConfidentialityCode.UNRESTRICTED),
                Arguments.of("open", ConfidentialityCode.UNRESTRICTED),

                Arguments.of("r", ConfidentialityCode.RESTRICTED),

                Arguments.of("c", ConfidentialityCode.CONFIDENTIAL),

                Arguments.of("v", ConfidentialityCode.VERY_RESTRICTED),

                Arguments.of("n", ConfidentialityCode.NORMAL),
                Arguments.of("standard", ConfidentialityCode.NORMAL),

                Arguments.of("l", ConfidentialityCode.LOW),

                Arguments.of("m", ConfidentialityCode.MEDIUM),

                Arguments.of("h", ConfidentialityCode.HIGH)
        );
    }
}