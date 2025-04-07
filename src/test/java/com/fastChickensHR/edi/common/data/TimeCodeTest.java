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

class TimeCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("01", TimeCode.ISO_P01.getCode());
        assertEquals("AD", TimeCode.ALASKA_DAYLIGHT.getCode());
        assertEquals("CT", TimeCode.CENTRAL_TIME.getCode());
        assertEquals("UT", TimeCode.UNIVERSAL_TIME.getCode());

        assertEquals("Equivalent to ISO P01", TimeCode.ISO_P01.getDescription());
        assertEquals("Alaska Daylight Time", TimeCode.ALASKA_DAYLIGHT.getDescription());
        assertEquals("Central Time", TimeCode.CENTRAL_TIME.getDescription());
        assertEquals("Universal Time Coordinate", TimeCode.UNIVERSAL_TIME.getDescription());
    }

    @Test
    void testEnumProperties() {
        assertEquals("01", TimeCode.ISO_P01.toString());
        assertEquals("CT", TimeCode.CENTRAL_TIME.toString());

        for (TimeCode timeCode : TimeCode.values()) {
            assertNotNull(timeCode.getCode(), "Code should not be null for " + timeCode.name());
            assertFalse(timeCode.getCode().isEmpty(), "Code should not be empty for " + timeCode.name());
            assertNotNull(timeCode.getDescription(), "Description should not be null for " + timeCode.name());
            assertFalse(timeCode.getDescription().isEmpty(), "Description should not be empty for " + timeCode.name());
        }
    }

    @Test
    void testFromString() {
        assertEquals(TimeCode.ISO_P01, TimeCode.fromString("01"));
        assertEquals(TimeCode.CENTRAL_TIME, TimeCode.fromString("CT"));
        assertEquals(TimeCode.UNIVERSAL_TIME, TimeCode.fromString("UT"));

        assertEquals(TimeCode.EASTERN_TIME, TimeCode.fromString("et"));
        assertEquals(TimeCode.PACIFIC_STANDARD, TimeCode.fromString("ps"));

        assertEquals(TimeCode.CENTRAL_TIME, TimeCode.fromString("central"));
        assertEquals(TimeCode.GREENWICH_MEAN, TimeCode.fromString("gmt"));
        assertEquals(TimeCode.PACIFIC_TIME, TimeCode.fromString("pacific"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> TimeCode.fromString("invalid code"));
        assertTrue(exception.getMessage().contains("Invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, TimeCode expected) throws Exception {
        Field lookupField = TimeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<TimeCode> lookup = (EdiEnumLookup<TimeCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("01", TimeCode.ISO_P01),
                Arguments.of("10", TimeCode.ISO_P10),
                Arguments.of("24", TimeCode.ISO_M01),
                Arguments.of("29", TimeCode.ISO_P10_30),

                Arguments.of("ad", TimeCode.ALASKA_DAYLIGHT),
                Arguments.of("AD", TimeCode.ALASKA_DAYLIGHT),
                Arguments.of("ct", TimeCode.CENTRAL_TIME),
                Arguments.of("CT", TimeCode.CENTRAL_TIME),
                Arguments.of("ut", TimeCode.UNIVERSAL_TIME),
                Arguments.of("UT", TimeCode.UNIVERSAL_TIME),

                Arguments.of("alaska daylight", TimeCode.ALASKA_DAYLIGHT),
                Arguments.of("alaska", TimeCode.ALASKA_TIME),
                Arguments.of("central", TimeCode.CENTRAL_TIME),
                Arguments.of("central standard", TimeCode.CENTRAL_STANDARD),
                Arguments.of("utc", TimeCode.UNIVERSAL_TIME),
                Arguments.of("greenwich", TimeCode.GREENWICH_MEAN),
                Arguments.of("gmt", TimeCode.GREENWICH_MEAN),
                Arguments.of("universal", TimeCode.UNIVERSAL_TIME),
                Arguments.of("pacific", TimeCode.PACIFIC_TIME),
                Arguments.of("pacific standard", TimeCode.PACIFIC_STANDARD)
        );
    }
}