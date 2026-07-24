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

class InterchangeUsageIndicatorTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(InterchangeUsageIndicator.class)
    void resolvesFromCodeNameAndDescription(InterchangeUsageIndicator constant) {
        assertEquals(constant, InterchangeUsageIndicator.fromString(constant.getCode()));
        assertEquals(constant, InterchangeUsageIndicator.fromString(constant.name()));
        assertEquals(constant, InterchangeUsageIndicator.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, InterchangeUsageIndicator expected) {
        assertEquals(expected, InterchangeUsageIndicator.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("i", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("info", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("information", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("informational", InterchangeUsageIndicator.INFORMATION),
                Arguments.of("p", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("prod", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("production", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("production data", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("live", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("real", InterchangeUsageIndicator.PRODUCTION),
                Arguments.of("t", InterchangeUsageIndicator.TEST),
                Arguments.of("test", InterchangeUsageIndicator.TEST),
                Arguments.of("testing", InterchangeUsageIndicator.TEST),
                Arguments.of("test data", InterchangeUsageIndicator.TEST),
                Arguments.of("dev", InterchangeUsageIndicator.TEST),
                Arguments.of("development", InterchangeUsageIndicator.TEST),
                Arguments.of("sandbox", InterchangeUsageIndicator.TEST));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> InterchangeUsageIndicator.fromString(input));
    }
}
