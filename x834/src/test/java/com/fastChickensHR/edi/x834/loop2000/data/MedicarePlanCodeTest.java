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

class MedicarePlanCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(MedicarePlanCode.class)
    void resolvesFromCodeNameAndDescription(MedicarePlanCode constant) {
        assertEquals(constant, MedicarePlanCode.fromString(constant.getCode()));
        assertEquals(constant, MedicarePlanCode.fromString(constant.name()));
        assertEquals(constant, MedicarePlanCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, MedicarePlanCode expected) {
        assertEquals(expected, MedicarePlanCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("part a", MedicarePlanCode.PART_A),
                Arguments.of("hospital insurance", MedicarePlanCode.PART_A),
                Arguments.of("inpatient", MedicarePlanCode.PART_A),
                Arguments.of("part b", MedicarePlanCode.PART_B),
                Arguments.of("medical insurance", MedicarePlanCode.PART_B),
                Arguments.of("outpatient", MedicarePlanCode.PART_B),
                Arguments.of("parts a and b", MedicarePlanCode.PART_A_AND_B),
                Arguments.of("a and b", MedicarePlanCode.PART_A_AND_B),
                Arguments.of("original medicare", MedicarePlanCode.PART_A_AND_B),
                Arguments.of("has medicare", MedicarePlanCode.MEDICARE),
                Arguments.of("medicare eligible", MedicarePlanCode.MEDICARE),
                Arguments.of("no coverage", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("not enrolled", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("without medicare", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("non-medicare", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("not eligible", MedicarePlanCode.NO_MEDICARE));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> MedicarePlanCode.fromString(input));
    }
}
