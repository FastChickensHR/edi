/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AcknowledgmentRequestedTest {

    @Test
    void testEnumValues() {
        assertEquals(2, AcknowledgmentRequested.values().length);

        assertNotNull(AcknowledgmentRequested.NO_ACKNOWLEDGMENT);
        assertNotNull(AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED);
    }

    @Test
    void testEnumProperties() {
        assertEquals("0", AcknowledgmentRequested.NO_ACKNOWLEDGMENT.getCode());
        assertEquals("No Interchange Acknowledgment Requested",
                AcknowledgmentRequested.NO_ACKNOWLEDGMENT.getDescription());

        assertEquals("1", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED.getCode());
        assertEquals("Interchange Acknowledgment Requested (TA1)",
                AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(AcknowledgmentRequested.NO_ACKNOWLEDGMENT,
                AcknowledgmentRequested.fromString("0"));
        assertEquals(AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED,
                AcknowledgmentRequested.fromString("1"));

        assertEquals(AcknowledgmentRequested.NO_ACKNOWLEDGMENT,
                AcknowledgmentRequested.fromString("no"));
        assertEquals(AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED,
                AcknowledgmentRequested.fromString("yes"));

        assertThrows(IllegalArgumentException.class, () ->
                AcknowledgmentRequested.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, AcknowledgmentRequested expected) {
        assertEquals(expected, AcknowledgmentRequested.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("0", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("no", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("none", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("not required", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("no acknowledgment", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),
                Arguments.of("no ack", AcknowledgmentRequested.NO_ACKNOWLEDGMENT),

                Arguments.of("1", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("yes", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("required", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("requested", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("ack", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("ta1", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED),
                Arguments.of("acknowledgment", AcknowledgmentRequested.ACKNOWLEDGMENT_REQUESTED)
        );
    }
}