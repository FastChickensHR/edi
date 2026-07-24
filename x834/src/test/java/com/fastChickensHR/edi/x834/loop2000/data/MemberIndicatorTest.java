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

class MemberIndicatorTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(MemberIndicator.class)
    void resolvesFromCodeNameAndDescription(MemberIndicator constant) {
        assertEquals(constant, MemberIndicator.fromString(constant.getCode()));
        assertEquals(constant, MemberIndicator.fromString(constant.name()));
        assertEquals(constant, MemberIndicator.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, MemberIndicator expected) {
        assertEquals(expected, MemberIndicator.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("y", MemberIndicator.INSURED),
                Arguments.of("yes", MemberIndicator.INSURED),
                Arguments.of("covered", MemberIndicator.INSURED),
                Arguments.of("enrolled", MemberIndicator.INSURED),
                Arguments.of("active", MemberIndicator.INSURED),
                Arguments.of("eligible", MemberIndicator.INSURED),
                Arguments.of("has coverage", MemberIndicator.INSURED),
                Arguments.of("has insurance", MemberIndicator.INSURED),
                Arguments.of("policyholder", MemberIndicator.INSURED),
                Arguments.of("member", MemberIndicator.INSURED),
                Arguments.of("participant", MemberIndicator.INSURED),
                Arguments.of("n", MemberIndicator.NOT_INSURED),
                Arguments.of("no", MemberIndicator.NOT_INSURED),
                Arguments.of("not covered", MemberIndicator.NOT_INSURED),
                Arguments.of("not enrolled", MemberIndicator.NOT_INSURED),
                Arguments.of("inactive", MemberIndicator.NOT_INSURED),
                Arguments.of("ineligible", MemberIndicator.NOT_INSURED),
                Arguments.of("no coverage", MemberIndicator.NOT_INSURED),
                Arguments.of("no insurance", MemberIndicator.NOT_INSURED),
                Arguments.of("non-member", MemberIndicator.NOT_INSURED),
                Arguments.of("non member", MemberIndicator.NOT_INSURED),
                Arguments.of("terminated", MemberIndicator.NOT_INSURED),
                Arguments.of("cancelled", MemberIndicator.NOT_INSURED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> MemberIndicator.fromString(input));
    }
}
