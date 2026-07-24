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

class SecurityInformationQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(SecurityInformationQualifier.class)
    void resolvesFromCodeNameAndDescription(SecurityInformationQualifier constant) {
        assertEquals(constant, SecurityInformationQualifier.fromString(constant.getCode()));
        assertEquals(constant, SecurityInformationQualifier.fromString(constant.name()));
        assertEquals(constant, SecurityInformationQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, SecurityInformationQualifier expected) {
        assertEquals(expected, SecurityInformationQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("none", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("no security", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("nosecurity", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("no info", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("noinfo", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("empty", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("blank", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("pwd", SecurityInformationQualifier.PASSWORD),
                Arguments.of("pass", SecurityInformationQualifier.PASSWORD),
                Arguments.of("passcode", SecurityInformationQualifier.PASSWORD),
                Arguments.of("secret", SecurityInformationQualifier.PASSWORD),
                Arguments.of("credentials", SecurityInformationQualifier.PASSWORD),
                Arguments.of("authentication", SecurityInformationQualifier.PASSWORD));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> SecurityInformationQualifier.fromString(input));
    }
}
