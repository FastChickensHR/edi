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

class InsuranceLineCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(InsuranceLineCode.class)
    void resolvesFromCodeNameAndDescription(InsuranceLineCode constant) {
        assertEquals(constant, InsuranceLineCode.fromString(constant.getCode()));
        assertEquals(constant, InsuranceLineCode.fromString(constant.name()));
        assertEquals(constant, InsuranceLineCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, InsuranceLineCode expected) {
        assertEquals(expected, InsuranceLineCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("wellness", InsuranceLineCode.PREVENTATIVE_CARE_WELLNESS),
                Arguments.of("preventive", InsuranceLineCode.PREVENTATIVE_CARE_WELLNESS),
                Arguments.of("medicare", InsuranceLineCode.MEDICARE_RISK),
                Arguments.of("medicare advantage", InsuranceLineCode.MEDICARE_RISK),
                Arguments.of("mental", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("behavioral health", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("psych", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("dental insurance", InsuranceLineCode.DENTAL),
                Arguments.of("teeth", InsuranceLineCode.DENTAL),
                Arguments.of("medical", InsuranceLineCode.HEALTH),
                Arguments.of("health insurance", InsuranceLineCode.HEALTH),
                Arguments.of("health plan", InsuranceLineCode.HEALTH),
                Arguments.of("long term care", InsuranceLineCode.LONG_TERM_CARE),
                Arguments.of("nursing home", InsuranceLineCode.LONG_TERM_CARE),
                Arguments.of("ltd", InsuranceLineCode.LONG_TERM_DISABILITY),
                Arguments.of("std", InsuranceLineCode.SHORT_TERM_DISABILITY),
                Arguments.of("major med", InsuranceLineCode.MAJOR_MEDICAL),
                Arguments.of("mail order", InsuranceLineCode.MAIL_ORDER_DRUG),
                Arguments.of("rx", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("drug", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("prescription", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("practitioner", InsuranceLineCode.PRACTITIONERS),
                Arguments.of("utilization", InsuranceLineCode.UTILIZATION_REVIEW),
                Arguments.of("optical", InsuranceLineCode.VISION),
                Arguments.of("eye", InsuranceLineCode.VISION),
                Arguments.of("eyewear", InsuranceLineCode.VISION));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> InsuranceLineCode.fromString(input));
    }
}
