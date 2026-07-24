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

class IdentificationCardTypeCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(IdentificationCardTypeCode.class)
    void resolvesFromCodeNameAndDescription(IdentificationCardTypeCode constant) {
        assertEquals(constant, IdentificationCardTypeCode.fromString(constant.getCode()));
        assertEquals(constant, IdentificationCardTypeCode.fromString(constant.name()));
        assertEquals(constant, IdentificationCardTypeCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, IdentificationCardTypeCode expected) {
        assertEquals(expected, IdentificationCardTypeCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("dental card", IdentificationCardTypeCode.DENTAL),
                Arguments.of("teeth", IdentificationCardTypeCode.DENTAL),
                Arguments.of("drug card", IdentificationCardTypeCode.DRUG),
                Arguments.of("rx card", IdentificationCardTypeCode.DRUG),
                Arguments.of("health card", IdentificationCardTypeCode.HEALTH),
                Arguments.of("insurance card", IdentificationCardTypeCode.HEALTH),
                Arguments.of("medical id card", IdentificationCardTypeCode.HEALTH),
                Arguments.of("ltc", IdentificationCardTypeCode.LONG_TERM_CARE),
                Arguments.of("long term care", IdentificationCardTypeCode.LONG_TERM_CARE),
                Arguments.of("nursing card", IdentificationCardTypeCode.LONG_TERM_CARE),
                Arguments.of("medical card", IdentificationCardTypeCode.MEDICAL),
                Arguments.of("pharmacy card", IdentificationCardTypeCode.PHARMACY),
                Arguments.of("prescription card", IdentificationCardTypeCode.PHARMACY),
                Arguments.of("vision card", IdentificationCardTypeCode.VISION),
                Arguments.of("eye card", IdentificationCardTypeCode.VISION),
                Arguments.of("optical card", IdentificationCardTypeCode.VISION));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> IdentificationCardTypeCode.fromString(input));
    }
}
