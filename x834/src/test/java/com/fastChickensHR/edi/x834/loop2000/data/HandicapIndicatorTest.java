/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandicapIndicatorTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(HandicapIndicator.class)
    void resolvesFromCodeNameAndDescription(HandicapIndicator constant) {
        assertEquals(constant, HandicapIndicator.fromString(constant.getCode()));
        assertEquals(constant, HandicapIndicator.fromString(constant.name()));
        assertEquals(constant, HandicapIndicator.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, HandicapIndicator expected) {
        assertEquals(expected, HandicapIndicator.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
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
                Arguments.of("?", HandicapIndicator.UNKNOWN));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> HandicapIndicator.fromString(input));
    }
}
