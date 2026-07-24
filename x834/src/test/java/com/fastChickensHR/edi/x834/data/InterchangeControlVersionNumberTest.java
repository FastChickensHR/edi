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

class InterchangeControlVersionNumberTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(InterchangeControlVersionNumber.class)
    void resolvesFromCodeNameAndDescription(InterchangeControlVersionNumber constant) {
        assertEquals(constant, InterchangeControlVersionNumber.fromString(constant.getCode()));
        assertEquals(constant, InterchangeControlVersionNumber.fromString(constant.name()));
        assertEquals(constant, InterchangeControlVersionNumber.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, InterchangeControlVersionNumber expected) {
        assertEquals(expected, InterchangeControlVersionNumber.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("00200", InterchangeControlVersionNumber.X12_1987),
                Arguments.of("1987", InterchangeControlVersionNumber.X12_1987),
                Arguments.of("00201", InterchangeControlVersionNumber.X12_DRAFT_1988),
                Arguments.of("1988", InterchangeControlVersionNumber.X12_DRAFT_1988),
                Arguments.of("00204", InterchangeControlVersionNumber.X12_DRAFT_1989),
                Arguments.of("1989", InterchangeControlVersionNumber.X12_DRAFT_1989),
                Arguments.of("00300", InterchangeControlVersionNumber.X12_1992),
                Arguments.of("00301", InterchangeControlVersionNumber.X12_DRAFT_OCT_1990),
                Arguments.of("1990", InterchangeControlVersionNumber.X12_DRAFT_OCT_1990),
                Arguments.of("00302", InterchangeControlVersionNumber.X12_DRAFT_OCT_1991),
                Arguments.of("1991", InterchangeControlVersionNumber.X12_DRAFT_OCT_1991),
                Arguments.of("00303", InterchangeControlVersionNumber.X12_DRAFT_OCT_1992),
                Arguments.of("00304", InterchangeControlVersionNumber.X12_DRAFT_OCT_1993),
                Arguments.of("1993", InterchangeControlVersionNumber.X12_DRAFT_OCT_1993),
                Arguments.of("00305", InterchangeControlVersionNumber.X12_DRAFT_OCT_1994),
                Arguments.of("1994", InterchangeControlVersionNumber.X12_DRAFT_OCT_1994),
                Arguments.of("00306", InterchangeControlVersionNumber.X12_DRAFT_OCT_1995),
                Arguments.of("1995", InterchangeControlVersionNumber.X12_DRAFT_OCT_1995),
                Arguments.of("00307", InterchangeControlVersionNumber.X12_DRAFT_OCT_1996),
                Arguments.of("1996", InterchangeControlVersionNumber.X12_DRAFT_OCT_1996),
                Arguments.of("00400", InterchangeControlVersionNumber.X12_1997),
                Arguments.of("1997", InterchangeControlVersionNumber.X12_1997),
                Arguments.of("00401", InterchangeControlVersionNumber.X12_OCT_1997),
                Arguments.of("00402", InterchangeControlVersionNumber.X12_OCT_1998),
                Arguments.of("1998", InterchangeControlVersionNumber.X12_OCT_1998),
                Arguments.of("00403", InterchangeControlVersionNumber.X12_OCT_1999),
                Arguments.of("1999", InterchangeControlVersionNumber.X12_OCT_1999),
                Arguments.of("00404", InterchangeControlVersionNumber.X12_OCT_2000),
                Arguments.of("2000", InterchangeControlVersionNumber.X12_OCT_2000),
                Arguments.of("00405", InterchangeControlVersionNumber.X12_OCT_2001),
                Arguments.of("2001", InterchangeControlVersionNumber.X12_OCT_2001),
                Arguments.of("00406", InterchangeControlVersionNumber.X12_OCT_2002),
                Arguments.of("2002", InterchangeControlVersionNumber.X12_OCT_2002),
                Arguments.of("00500", InterchangeControlVersionNumber.X12_2003),
                Arguments.of("00501", InterchangeControlVersionNumber.X12_OCT_2003));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> InterchangeControlVersionNumber.fromString(input));
    }
}
