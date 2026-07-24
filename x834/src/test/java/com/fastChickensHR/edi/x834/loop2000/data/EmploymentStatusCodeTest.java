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

class EmploymentStatusCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(EmploymentStatusCode.class)
    void resolvesFromCodeNameAndDescription(EmploymentStatusCode constant) {
        assertEquals(constant, EmploymentStatusCode.fromString(constant.getCode()));
        assertEquals(constant, EmploymentStatusCode.fromString(constant.name()));
        assertEquals(constant, EmploymentStatusCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, EmploymentStatusCode expected) {
        assertEquals(expected, EmploymentStatusCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("current", EmploymentStatusCode.ACTIVE),
                Arguments.of("employed", EmploymentStatusCode.ACTIVE),
                Arguments.of("working", EmploymentStatusCode.ACTIVE),
                Arguments.of("ft", EmploymentStatusCode.FULL_TIME),
                Arguments.of("fulltime", EmploymentStatusCode.FULL_TIME),
                Arguments.of("40hours", EmploymentStatusCode.FULL_TIME),
                Arguments.of("pt", EmploymentStatusCode.PART_TIME),
                Arguments.of("parttime", EmploymentStatusCode.PART_TIME),
                Arguments.of("hourly", EmploymentStatusCode.PART_TIME),
                Arguments.of("pension", EmploymentStatusCode.RETIRED),
                Arguments.of("retiree", EmploymentStatusCode.RETIRED),
                Arguments.of("laid off", EmploymentStatusCode.TERMINATED),
                Arguments.of("fired", EmploymentStatusCode.TERMINATED),
                Arguments.of("resigned", EmploymentStatusCode.TERMINATED),
                Arguments.of("quit", EmploymentStatusCode.TERMINATED),
                Arguments.of("loa", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("sabbatical", EmploymentStatusCode.SABBATICAL_LEAVE_OF_ABSENCE),
                Arguments.of("fmla", EmploymentStatusCode.FMLA_LEAVE_OF_ABSENCE),
                Arguments.of("medical leave", EmploymentStatusCode.SICKNESS_LEAVE_OF_ABSENCE),
                Arguments.of("maternity", EmploymentStatusCode.MATERNITY_LEAVE_OF_ABSENCE),
                Arguments.of("jury", EmploymentStatusCode.JURY_DUTY),
                Arguments.of("disability", EmploymentStatusCode.DISABLED),
                Arguments.of("ltd", EmploymentStatusCode.DISABLED),
                Arguments.of("cobra", EmploymentStatusCode.COBRA),
                Arguments.of("contractor", EmploymentStatusCode.CONTRACTOR),
                Arguments.of("temp", EmploymentStatusCode.TEMPORARY),
                Arguments.of("temporary", EmploymentStatusCode.TEMPORARY),
                Arguments.of("seasonal", EmploymentStatusCode.SEASONAL),
                Arguments.of("military", EmploymentStatusCode.ACTIVE_MILITARY_USA),
                Arguments.of("deceased", EmploymentStatusCode.DECEASED),
                Arguments.of("died", EmploymentStatusCode.DECEASED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmploymentStatusCode.fromString(input));
    }
}
