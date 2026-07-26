/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinationOfBenefitsCodeTest {

    @ParameterizedTest
    @EnumSource(CoordinationOfBenefitsCode.class)
    void resolvesFromCodeNameAndDescription(CoordinationOfBenefitsCode constant) {
        assertEquals(constant, CoordinationOfBenefitsCode.fromString(constant.getCode()));
        assertEquals(constant, CoordinationOfBenefitsCode.fromString(constant.name()));
        assertEquals(constant, CoordinationOfBenefitsCode.fromString(constant.getDescription()));
    }

    @Test
    void codesAreUnique() {
        Map<String, CoordinationOfBenefitsCode> byCode = new HashMap<>();
        for (CoordinationOfBenefitsCode constant : CoordinationOfBenefitsCode.values()) {
            CoordinationOfBenefitsCode prior = byCode.put(constant.getCode(), constant);
            assertNull(prior, () -> "duplicate code '" + constant.getCode() + "'");
        }
    }

    /** Element 1143 is the complete 1–9 list; the two the profiled carriers send are 1 and 6. */
    @ParameterizedTest
    @CsvSource({
            "1,COORDINATION_OF_BENEFITS",
            "2,COORDINATION_OF_BENEFITS_SPOUSE_ONLY",
            "3,COORDINATION_OF_BENEFITS_SPOUSE_AND_DEPENDENTS",
            "4,COORDINATION_OF_BENEFITS_DEPENDENTS_ONLY",
            "5,UNKNOWN",
            "6,NO_COORDINATION_OF_BENEFITS",
            "7,COORDINATION_OF_BENEFITS_SUBSCRIBER_ONLY",
            "8,CONFLICT_IN_COORDINATION_OF_BENEFIT",
            "9,COORDINATION_OF_BENEFITS_WHOLE_FAMILY"})
    void mapsEachCodeToItsElement1143Meaning(String code, CoordinationOfBenefitsCode expected) {
        assertEquals(expected, CoordinationOfBenefitsCode.fromString(code));
    }

    /**
     * COB03's {@code 5} (coordination unknown) is a different statement from COB01's {@code U}
     * (payer sequence unknown), and the two elements must not be conflated — CareFirst sends
     * {@code U} in COB01 alongside {@code 6} in COB03.
     */
    @Test
    void unknownHereIsCob03sFiveNotCob01sU() {
        assertEquals("5", CoordinationOfBenefitsCode.UNKNOWN.getCode());
        assertEquals("U", PayerResponsibilitySequenceCode.UNKNOWN.getCode());
    }

    @Test
    void toStringIsTheRawCode() {
        assertEquals("6", CoordinationOfBenefitsCode.NO_COORDINATION_OF_BENEFITS.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value", "0"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> CoordinationOfBenefitsCode.fromString(input));
    }
}
