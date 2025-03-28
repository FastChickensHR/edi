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

class HandicapIndicatorTest {

    @Test
    void testEnumValues() {
        assertEquals(3, HandicapIndicator.values().length);

        assertTrue(Arrays.asList(HandicapIndicator.values()).contains(HandicapIndicator.YES));
        assertTrue(Arrays.asList(HandicapIndicator.values()).contains(HandicapIndicator.NO));
        assertTrue(Arrays.asList(HandicapIndicator.values()).contains(HandicapIndicator.UNKNOWN));
    }

    @Test
    void testEnumProperties() {
        assertEquals("Y", HandicapIndicator.YES.getCode());
        assertEquals("Yes - Individual has a handicap", HandicapIndicator.YES.getDescription());

        assertEquals("N", HandicapIndicator.NO.getCode());
        assertEquals("No - Individual does not have a handicap", HandicapIndicator.NO.getDescription());

        assertEquals("U", HandicapIndicator.UNKNOWN.getCode());
        assertEquals("Unknown handicap status", HandicapIndicator.UNKNOWN.getDescription());

        for (HandicapIndicator indicator : HandicapIndicator.values()) {
            assertEquals(indicator.getCode(), indicator.toString());
        }
    }

    @Test
    void testFromString() {
        assertEquals(HandicapIndicator.YES, HandicapIndicator.fromString("Y"));
        assertEquals(HandicapIndicator.NO, HandicapIndicator.fromString("N"));
        assertEquals(HandicapIndicator.UNKNOWN, HandicapIndicator.fromString("U"));

        assertThrows(IllegalArgumentException.class, () -> HandicapIndicator.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, HandicapIndicator expected) throws Exception {
        Field lookupField = HandicapIndicator.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<HandicapIndicator> lookup = (EdiEnumLookup<HandicapIndicator>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("Y", HandicapIndicator.YES),
                Arguments.of("N", HandicapIndicator.NO),
                Arguments.of("U", HandicapIndicator.UNKNOWN),

                Arguments.of("YES", HandicapIndicator.YES),
                Arguments.of("NO", HandicapIndicator.NO),
                Arguments.of("UNKNOWN", HandicapIndicator.UNKNOWN),

                Arguments.of("Yes - Individual has a handicap", HandicapIndicator.YES),
                Arguments.of("No - Individual does not have a handicap", HandicapIndicator.NO),
                Arguments.of("Unknown handicap status", HandicapIndicator.UNKNOWN),

                Arguments.of("y", HandicapIndicator.YES),
                Arguments.of("yes", HandicapIndicator.YES),
                Arguments.of("true", HandicapIndicator.YES),
                Arguments.of("t", HandicapIndicator.YES),
                Arguments.of("disabled", HandicapIndicator.YES),
                Arguments.of("disability", HandicapIndicator.YES),
                Arguments.of("has disability", HandicapIndicator.YES),
                Arguments.of("has handicap", HandicapIndicator.YES),
                Arguments.of("handicapped", HandicapIndicator.YES),

                Arguments.of("n", HandicapIndicator.NO),
                Arguments.of("no", HandicapIndicator.NO),
                Arguments.of("false", HandicapIndicator.NO),
                Arguments.of("f", HandicapIndicator.NO),
                Arguments.of("not disabled", HandicapIndicator.NO),
                Arguments.of("no disability", HandicapIndicator.NO),
                Arguments.of("no handicap", HandicapIndicator.NO),
                Arguments.of("not handicapped", HandicapIndicator.NO),

                Arguments.of("u", HandicapIndicator.UNKNOWN),
                Arguments.of("unknown", HandicapIndicator.UNKNOWN),
                Arguments.of("undisclosed", HandicapIndicator.UNKNOWN),
                Arguments.of("not disclosed", HandicapIndicator.UNKNOWN),
                Arguments.of("not specified", HandicapIndicator.UNKNOWN),
                Arguments.of("unspecified", HandicapIndicator.UNKNOWN),
                Arguments.of("?", HandicapIndicator.UNKNOWN)
        );
    }
}