/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(TimeCode.class)
    void resolvesFromCodeNameAndDescription(TimeCode constant) {
        assertEquals(constant, TimeCode.fromString(constant.getCode()));
        assertEquals(constant, TimeCode.fromString(constant.name()));
        assertEquals(constant, TimeCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, TimeCode expected) {
        assertEquals(expected, TimeCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("alaska daylight", TimeCode.ALASKA_DAYLIGHT),
                Arguments.of("alaska standard", TimeCode.ALASKA_STANDARD),
                Arguments.of("alaska", TimeCode.ALASKA_TIME),
                Arguments.of("central daylight", TimeCode.CENTRAL_DAYLIGHT),
                Arguments.of("central standard", TimeCode.CENTRAL_STANDARD),
                Arguments.of("central", TimeCode.CENTRAL_TIME),
                Arguments.of("eastern daylight", TimeCode.EASTERN_DAYLIGHT),
                Arguments.of("eastern standard", TimeCode.EASTERN_STANDARD),
                Arguments.of("eastern", TimeCode.EASTERN_TIME),
                Arguments.of("greenwich", TimeCode.GREENWICH_MEAN),
                Arguments.of("gmt", TimeCode.GREENWICH_MEAN),
                Arguments.of("hawaii daylight", TimeCode.HAWAII_ALEUTIAN_DAYLIGHT),
                Arguments.of("hawaii standard", TimeCode.HAWAII_ALEUTIAN_STANDARD),
                Arguments.of("hawaii", TimeCode.HAWAII_ALEUTIAN_TIME),
                Arguments.of("local", TimeCode.LOCAL_TIME),
                Arguments.of("mountain daylight", TimeCode.MOUNTAIN_DAYLIGHT),
                Arguments.of("mountain standard", TimeCode.MOUNTAIN_STANDARD),
                Arguments.of("mountain", TimeCode.MOUNTAIN_TIME),
                Arguments.of("newfoundland daylight", TimeCode.NEWFOUNDLAND_DAYLIGHT),
                Arguments.of("newfoundland standard", TimeCode.NEWFOUNDLAND_STANDARD),
                Arguments.of("newfoundland", TimeCode.NEWFOUNDLAND_TIME),
                Arguments.of("pacific daylight", TimeCode.PACIFIC_DAYLIGHT),
                Arguments.of("pacific standard", TimeCode.PACIFIC_STANDARD),
                Arguments.of("pacific", TimeCode.PACIFIC_TIME),
                Arguments.of("atlantic daylight", TimeCode.ATLANTIC_DAYLIGHT),
                Arguments.of("atlantic standard", TimeCode.ATLANTIC_STANDARD),
                Arguments.of("atlantic", TimeCode.ATLANTIC_TIME),
                Arguments.of("universal", TimeCode.UNIVERSAL_TIME),
                Arguments.of("utc", TimeCode.UNIVERSAL_TIME),
                Arguments.of("coordinated universal time", TimeCode.UNIVERSAL_TIME));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> TimeCode.fromString(input));
    }
}
