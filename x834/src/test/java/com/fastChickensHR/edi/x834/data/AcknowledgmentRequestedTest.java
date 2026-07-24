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

class AcknowledgmentRequestedTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(AcknowledgmentRequested.class)
    void resolvesFromCodeNameAndDescription(AcknowledgmentRequested constant) {
        assertEquals(constant, AcknowledgmentRequested.fromString(constant.getCode()));
        assertEquals(constant, AcknowledgmentRequested.fromString(constant.name()));
        assertEquals(constant, AcknowledgmentRequested.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, AcknowledgmentRequested expected) {
        assertEquals(expected, AcknowledgmentRequested.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("no", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("none", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("not required", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("no acknowledgment", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("no ack", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("yes", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("required", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("requested", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("ack", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("ta1", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("acknowledgment", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> AcknowledgmentRequested.fromString(input));
    }
}
