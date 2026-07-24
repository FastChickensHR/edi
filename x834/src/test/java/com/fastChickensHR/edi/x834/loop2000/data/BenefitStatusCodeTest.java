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

class BenefitStatusCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(BenefitStatusCode.class)
    void resolvesFromCodeNameAndDescription(BenefitStatusCode constant) {
        assertEquals(constant, BenefitStatusCode.fromString(constant.getCode()));
        assertEquals(constant, BenefitStatusCode.fromString(constant.name()));
        assertEquals(constant, BenefitStatusCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, BenefitStatusCode expected) {
        assertEquals(expected, BenefitStatusCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("active coverage", BenefitStatusCode.ACTIVE),
                Arguments.of("current", BenefitStatusCode.ACTIVE),
                Arguments.of("enrolled", BenefitStatusCode.ACTIVE),
                Arguments.of("cobra", BenefitStatusCode.COBRA),
                Arguments.of("cobra coverage", BenefitStatusCode.COBRA),
                Arguments.of("continuation", BenefitStatusCode.COBRA),
                Arguments.of("continuation coverage", BenefitStatusCode.COBRA),
                Arguments.of("involuntary loss", BenefitStatusCode.INVOLUNTARY),
                Arguments.of("survivor", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("surviving spouse", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("widow", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("widower", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("tefra", BenefitStatusCode.TEFRA),
                Arguments.of("voluntary loss", BenefitStatusCode.VOLUNTARY));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> BenefitStatusCode.fromString(input));
    }
}
