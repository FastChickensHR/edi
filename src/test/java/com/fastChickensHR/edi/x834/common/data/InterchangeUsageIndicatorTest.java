/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InterchangeUsageIndicatorTest {

    @Test
    void testEnumValues() {
        assertEquals(3, InterchangeUsageIndicator.values().length);

        assertNotNull(InterchangeUsageIndicator.INFORMATION);
        assertNotNull(InterchangeUsageIndicator.PRODUCTION);
        assertNotNull(InterchangeUsageIndicator.TEST);
    }

    @Test
    void testEnumProperties() {
        assertEquals("I", InterchangeUsageIndicator.INFORMATION.getCode());
        assertEquals("Information", InterchangeUsageIndicator.INFORMATION.getDescription());

        assertEquals("P", InterchangeUsageIndicator.PRODUCTION.getCode());
        assertEquals("Production Data", InterchangeUsageIndicator.PRODUCTION.getDescription());

        assertEquals("T", InterchangeUsageIndicator.TEST.getCode());
        assertEquals("Test Data", InterchangeUsageIndicator.TEST.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(InterchangeUsageIndicator.INFORMATION, InterchangeUsageIndicator.fromString("I"));
        assertEquals(InterchangeUsageIndicator.PRODUCTION, InterchangeUsageIndicator.fromString("P"));
        assertEquals(InterchangeUsageIndicator.TEST, InterchangeUsageIndicator.fromString("T"));

        assertEquals(InterchangeUsageIndicator.INFORMATION, InterchangeUsageIndicator.fromString("i"));
        assertEquals(InterchangeUsageIndicator.PRODUCTION, InterchangeUsageIndicator.fromString("p"));
        assertEquals(InterchangeUsageIndicator.TEST, InterchangeUsageIndicator.fromString("t"));

        assertEquals(InterchangeUsageIndicator.INFORMATION, InterchangeUsageIndicator.fromString("information"));
        assertEquals(InterchangeUsageIndicator.PRODUCTION, InterchangeUsageIndicator.fromString("production"));
        assertEquals(InterchangeUsageIndicator.TEST, InterchangeUsageIndicator.fromString("test"));

        assertThrows(IllegalArgumentException.class, () ->
                InterchangeUsageIndicator.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, InterchangeUsageIndicator expected) {
        assertEquals(expected, InterchangeUsageIndicator.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("i", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("info", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("information", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("informational", InterchangeUsageIndicator.INFORMATION),

                Arguments.of("p", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("prod", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("production", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("production data", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("live", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("real", InterchangeUsageIndicator.PRODUCTION),

                Arguments.of("t", InterchangeUsageIndicator.TEST),
                Arguments.of("test", InterchangeUsageIndicator.TEST),
                Arguments.of("testing", InterchangeUsageIndicator.TEST),
                Arguments.of("test data", InterchangeUsageIndicator.TEST),
                Arguments.of("dev", InterchangeUsageIndicator.TEST),
                Arguments.of("development", InterchangeUsageIndicator.TEST),
                Arguments.of("sandbox", InterchangeUsageIndicator.TEST)
        );
    }
}