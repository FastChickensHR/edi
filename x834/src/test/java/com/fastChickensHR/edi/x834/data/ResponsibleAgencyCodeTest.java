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

class ResponsibleAgencyCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(ResponsibleAgencyCode.class)
    void resolvesFromCodeNameAndDescription(ResponsibleAgencyCode constant) {
        assertEquals(constant, ResponsibleAgencyCode.fromString(constant.getCode()));
        assertEquals(constant, ResponsibleAgencyCode.fromString(constant.name()));
        assertEquals(constant, ResponsibleAgencyCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, ResponsibleAgencyCode expected) {
        assertEquals(expected, ResponsibleAgencyCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("tdcc", ResponsibleAgencyCode.TDCC),
                Arguments.of("transportation", ResponsibleAgencyCode.TDCC),
                Arguments.of("transportation data", ResponsibleAgencyCode.TDCC),
                Arguments.of("x12", ResponsibleAgencyCode.ASC_X12),
                Arguments.of("asc", ResponsibleAgencyCode.ASC_X12),
                Arguments.of("asc x12", ResponsibleAgencyCode.ASC_X12),
                Arguments.of("accredited standards", ResponsibleAgencyCode.ASC_X12),
                Arguments.of("ansi", ResponsibleAgencyCode.ASC_X12));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> ResponsibleAgencyCode.fromString(input));
    }
}
