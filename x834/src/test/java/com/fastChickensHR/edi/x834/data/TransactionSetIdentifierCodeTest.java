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

class TransactionSetIdentifierCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(TransactionSetIdentifierCode.class)
    void resolvesFromCodeNameAndDescription(TransactionSetIdentifierCode constant) {
        assertEquals(constant, TransactionSetIdentifierCode.fromString(constant.getCode()));
        assertEquals(constant, TransactionSetIdentifierCode.fromString(constant.name()));
        assertEquals(constant, TransactionSetIdentifierCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, TransactionSetIdentifierCode expected) {
        assertEquals(expected, TransactionSetIdentifierCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("benefit enrollment", TransactionSetIdentifierCode.BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Arguments.of("enrollment", TransactionSetIdentifierCode.BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Arguments.of("healthcare enrollment", TransactionSetIdentifierCode.BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Arguments.of("human resources", TransactionSetIdentifierCode.HUMAN_RESOURCE_INFORMATION),
                Arguments.of("hr", TransactionSetIdentifierCode.HUMAN_RESOURCE_INFORMATION));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> TransactionSetIdentifierCode.fromString(input));
    }
}
