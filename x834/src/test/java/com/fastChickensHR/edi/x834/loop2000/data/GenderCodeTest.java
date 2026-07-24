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

class GenderCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(GenderCode.class)
    void resolvesFromCodeNameAndDescription(GenderCode constant) {
        assertEquals(constant, GenderCode.fromString(constant.getCode()));
        assertEquals(constant, GenderCode.fromString(constant.name()));
        assertEquals(constant, GenderCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, GenderCode expected) {
        assertEquals(expected, GenderCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
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
                Arguments.of("withheld", GenderCode.NOT_SPECIFIED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> GenderCode.fromString(input));
    }
}
