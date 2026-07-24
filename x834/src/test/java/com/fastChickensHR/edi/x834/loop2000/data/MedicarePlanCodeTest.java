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
                Arguments.of("part a", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("hospital insurance", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("inpatient", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("hospital care", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("skilled nursing", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("part b", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("medical insurance", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("outpatient", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("doctor visits", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("physician services", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("preventive services", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("parts a and b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("ab", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("a and b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("a & b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("hospital and medical", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("original medicare", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("traditional medicare", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("part c", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("advantage", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("ma", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("managed care", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("medicare health plan", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("hmo", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("ppo", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("part d", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("drug coverage", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("prescription coverage", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("rx", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("medication", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("pdp", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("pharmacy", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("supplement", MedicarePlanCode.MEDIGAP),
                Arguments.of("supplemental", MedicarePlanCode.MEDIGAP),
                Arguments.of("gap coverage", MedicarePlanCode.MEDIGAP),
                Arguments.of("med supp", MedicarePlanCode.MEDIGAP),
                Arguments.of("supplementary", MedicarePlanCode.MEDIGAP),
                Arguments.of("secondary insurance", MedicarePlanCode.MEDIGAP),
                Arguments.of("gap insurance", MedicarePlanCode.MEDIGAP),
                Arguments.of("no coverage", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("not enrolled", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("without medicare", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("non-medicare", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("no enrollment", MedicarePlanCode.NO_MEDICARE),
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
