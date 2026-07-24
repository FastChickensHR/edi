/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.api.Test;
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
     * ({@code LONG_TERM_CARE} is excluded — see {@link #longTermCareNameCollidesWithLtcVariant()}.)
     */
    @ParameterizedTest
    @EnumSource(value = InsuranceLineCode.class, mode = EnumSource.Mode.EXCLUDE, names = "LONG_TERM_CARE")
    void resolvesFromCodeNameAndDescription(InsuranceLineCode constant) {
        assertEquals(constant, InsuranceLineCode.fromString(constant.getCode()));
        assertEquals(constant, InsuranceLineCode.fromString(constant.name()));
        assertEquals(constant, InsuranceLineCode.fromString(constant.getDescription()));
    }

    /**
     * Surfaced collision (pinned as current behavior, not fixed under this test-only wave): two
     * constants describe long-term care — {@link InsuranceLineCode#LONG_TERM_CARE} ("Long-term Care",
     * code {@code AJ}) and {@link InsuranceLineCode#LONG_TERM_CARE_LTC} ("Long Term Care", code
     * {@code LTC}). Both the name {@code LONG_TERM_CARE} and both descriptions normalize to
     * {@code "longtermcare"}, so that key resolves to whichever registered last ({@code LONG_TERM_CARE_LTC}).
     * {@code LONG_TERM_CARE} therefore is reachable only by its code {@code AJ}. Owner follow-up:
     * reconcile the duplicate long-term-care constants or disambiguate their descriptions.
     */
    @Test
    void longTermCareNameCollidesWithLtcVariant() {
        assertEquals(InsuranceLineCode.LONG_TERM_CARE, InsuranceLineCode.fromString("AJ"));
        assertEquals(InsuranceLineCode.LONG_TERM_CARE_LTC, InsuranceLineCode.fromString("LONG_TERM_CARE"));
        assertEquals(InsuranceLineCode.LONG_TERM_CARE_LTC, InsuranceLineCode.fromString("Long-term Care"));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, InsuranceLineCode expected) {
        assertEquals(expected, InsuranceLineCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("wellness", InsuranceLineCode.PREVENTIVE_CARE_WELLNESS),
                Arguments.of("preventive", InsuranceLineCode.PREVENTIVE_CARE_WELLNESS),
                Arguments.of("preventative", InsuranceLineCode.PREVENTIVE_CARE_WELLNESS),
                Arguments.of("24 hour", InsuranceLineCode.TWENTY_FOUR_HOUR_CARE),
                Arguments.of("24-hour", InsuranceLineCode.TWENTY_FOUR_HOUR_CARE),
                Arguments.of("long term", InsuranceLineCode.LONG_TERM_CARE_LTC),
                Arguments.of("long-term care", InsuranceLineCode.LONG_TERM_CARE_LTC),
                Arguments.of("nursing home", InsuranceLineCode.LONG_TERM_CARE_LTC),
                Arguments.of("medicare", InsuranceLineCode.MEDICARE_RISK),
                Arguments.of("medicare advantage", InsuranceLineCode.MEDICARE_RISK),
                Arguments.of("mental", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("behavioral health", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("psych", InsuranceLineCode.MENTAL_HEALTH),
                Arguments.of("dental cap", InsuranceLineCode.DENTAL_CAPITATION),
                Arguments.of("dental capitation plan", InsuranceLineCode.DENTAL_CAPITATION),
                Arguments.of("dental insurance", InsuranceLineCode.DENTAL),
                Arguments.of("teeth", InsuranceLineCode.DENTAL),
                Arguments.of("epo plan", InsuranceLineCode.EXCLUSIVE_PROVIDER_ORGANIZATION),
                Arguments.of("facility coverage", InsuranceLineCode.FACILITY),
                Arguments.of("hearing aid", InsuranceLineCode.HEARING),
                Arguments.of("audiology", InsuranceLineCode.HEARING),
                Arguments.of("medical", InsuranceLineCode.HEALTH),
                Arguments.of("health insurance", InsuranceLineCode.HEALTH),
                Arguments.of("health plan", InsuranceLineCode.HEALTH),
                Arguments.of("hmo plan", InsuranceLineCode.HEALTH_MAINTENANCE_ORGANIZATION),
                Arguments.of("hospital coverage", InsuranceLineCode.HOSPITAL),
                Arguments.of("inpatient", InsuranceLineCode.HOSPITAL),
                Arguments.of("ltd", InsuranceLineCode.LONG_TERM_DISABILITY),
                Arguments.of("long term disability", InsuranceLineCode.LONG_TERM_DISABILITY),
                Arguments.of("major med", InsuranceLineCode.MAJOR_MEDICAL),
                Arguments.of("comprehensive medical", InsuranceLineCode.MAJOR_MEDICAL),
                Arguments.of("mail order", InsuranceLineCode.MAIL_ORDER_DRUG),
                Arguments.of("mail order pharmacy", InsuranceLineCode.MAIL_ORDER_DRUG),
                Arguments.of("rx", InsuranceLineCode.PHARMACY),
                Arguments.of("drug", InsuranceLineCode.PHARMACY),
                Arguments.of("pharmacy benefit", InsuranceLineCode.PHARMACY),
                Arguments.of("pos plan", InsuranceLineCode.POINT_OF_SERVICE),
                Arguments.of("prescription", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("prescription drugs", InsuranceLineCode.PRESCRIPTION_DRUG),
                Arguments.of("ppo plan", InsuranceLineCode.PREFERRED_PROVIDER_ORGANIZATION),
                Arguments.of("std", InsuranceLineCode.SHORT_TERM_DISABILITY),
                Arguments.of("short term disability", InsuranceLineCode.SHORT_TERM_DISABILITY),
                Arguments.of("utilization", InsuranceLineCode.UTILIZATION_REVIEW),
                Arguments.of("ur review", InsuranceLineCode.UTILIZATION_REVIEW),
                Arguments.of("vision", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("optical", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("eye", InsuranceLineCode.VISION_OPTICAL),
                Arguments.of("eyewear", InsuranceLineCode.VISION_OPTICAL));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> InsuranceLineCode.fromString(input));
    }
}
