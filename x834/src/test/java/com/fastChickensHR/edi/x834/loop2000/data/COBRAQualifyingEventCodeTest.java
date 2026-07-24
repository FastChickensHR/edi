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

class COBRAQualifyingEventCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(COBRAQualifyingEventCode.class)
    void resolvesFromCodeNameAndDescription(COBRAQualifyingEventCode constant) {
        assertEquals(constant, COBRAQualifyingEventCode.fromString(constant.getCode()));
        assertEquals(constant, COBRAQualifyingEventCode.fromString(constant.name()));
        assertEquals(constant, COBRAQualifyingEventCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, COBRAQualifyingEventCode expected) {
        assertEquals(expected, COBRAQualifyingEventCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("fired", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("terminated", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("quit", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("resignation", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("reduced hours", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("parttime", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("deceased", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("passed away", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("divorce", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("separated", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("medicare", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT),
                Arguments.of("agedout", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("no longer dependent", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("chapter11", COBRAQualifyingEventCode.BANKRUPTCY),
                Arguments.of("downsized", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("laid off", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("sabbatical", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),
                Arguments.of("fmla", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),
                Arguments.of("loa", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> COBRAQualifyingEventCode.fromString(input));
    }
}
