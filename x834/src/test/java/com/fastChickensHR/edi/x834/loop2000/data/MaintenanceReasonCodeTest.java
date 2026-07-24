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

class MaintenanceReasonCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(MaintenanceReasonCode.class)
    void resolvesFromCodeNameAndDescription(MaintenanceReasonCode constant) {
        assertEquals(constant, MaintenanceReasonCode.fromString(constant.getCode()));
        assertEquals(constant, MaintenanceReasonCode.fromString(constant.name()));
        assertEquals(constant, MaintenanceReasonCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, MaintenanceReasonCode expected) {
        assertEquals(expected, MaintenanceReasonCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("newborn", MaintenanceReasonCode.BIRTH),
                Arguments.of("new child", MaintenanceReasonCode.BIRTH),
                Arguments.of("baby", MaintenanceReasonCode.BIRTH),
                Arguments.of("deceased", MaintenanceReasonCode.DEATH),
                Arguments.of("died", MaintenanceReasonCode.DEATH),
                Arguments.of("passed away", MaintenanceReasonCode.DEATH),
                Arguments.of("retired", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("pension", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("married", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("wedding", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("continuation coverage", MaintenanceReasonCode.COBRA),
                Arguments.of("terminated", MaintenanceReasonCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("fired", MaintenanceReasonCode.FIRED),
                Arguments.of("quit", MaintenanceReasonCode.QUIT),
                Arguments.of("resigned", MaintenanceReasonCode.RESIGNED),
                Arguments.of("disabled", MaintenanceReasonCode.DISABILITY),
                Arguments.of("loa", MaintenanceReasonCode.LEAVE_OF_ABSENCE),
                Arguments.of("new hire", MaintenanceReasonCode.INITIAL_ENROLLMENT),
                Arguments.of("correction", MaintenanceReasonCode.CHANGE_IN_IDENTIFYING_DATA_ELEMENTS),
                Arguments.of("notification", MaintenanceReasonCode.NOTIFICATION_ONLY));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> MaintenanceReasonCode.fromString(input));
    }
}
