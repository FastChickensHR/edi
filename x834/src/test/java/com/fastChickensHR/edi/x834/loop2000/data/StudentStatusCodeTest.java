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

class StudentStatusCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(StudentStatusCode.class)
    void resolvesFromCodeNameAndDescription(StudentStatusCode constant) {
        assertEquals(constant, StudentStatusCode.fromString(constant.getCode()));
        assertEquals(constant, StudentStatusCode.fromString(constant.name()));
        assertEquals(constant, StudentStatusCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, StudentStatusCode expected) {
        assertEquals(expected, StudentStatusCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("ft", StudentStatusCode.FULL_TIME),
                Arguments.of("full", StudentStatusCode.FULL_TIME),
                Arguments.of("full load", StudentStatusCode.FULL_TIME),
                Arguments.of("full course load", StudentStatusCode.FULL_TIME),
                Arguments.of("regular student", StudentStatusCode.FULL_TIME),
                Arguments.of("pt", StudentStatusCode.PART_TIME),
                Arguments.of("partial", StudentStatusCode.PART_TIME),
                Arguments.of("partial load", StudentStatusCode.PART_TIME),
                Arguments.of("reduced schedule", StudentStatusCode.PART_TIME),
                Arguments.of("half time", StudentStatusCode.PART_TIME),
                Arguments.of("not student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("non student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not enrolled", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not in school", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not attending", StudentStatusCode.NOT_A_STUDENT));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> StudentStatusCode.fromString(input));
    }
}
