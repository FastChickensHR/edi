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

class AmountQualifierCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(AmountQualifierCode.class)
    void resolvesFromCodeNameAndDescription(AmountQualifierCode constant) {
        assertEquals(constant, AmountQualifierCode.fromString(constant.getCode()));
        assertEquals(constant, AmountQualifierCode.fromString(constant.name()));
        assertEquals(constant, AmountQualifierCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, AmountQualifierCode expected) {
        assertEquals(expected, AmountQualifierCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("coinsurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("co-insurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("co insurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("actual coinsurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("deductible", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("annual deductible", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("ded", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("premium", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("monthly premium", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("policy premium", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("spenddown", AmountQualifierCode.SPEND_DOWN),
                Arguments.of("spend-down", AmountQualifierCode.SPEND_DOWN),
                Arguments.of("medicaid spend down", AmountQualifierCode.SPEND_DOWN),
                Arguments.of("expected expenditure", AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT),
                Arguments.of("expenditure", AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> AmountQualifierCode.fromString(input));
    }
}
