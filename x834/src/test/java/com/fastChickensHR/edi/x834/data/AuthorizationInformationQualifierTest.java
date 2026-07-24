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

class AuthorizationInformationQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(AuthorizationInformationQualifier.class)
    void resolvesFromCodeNameAndDescription(AuthorizationInformationQualifier constant) {
        assertEquals(constant, AuthorizationInformationQualifier.fromString(constant.getCode()));
        assertEquals(constant, AuthorizationInformationQualifier.fromString(constant.name()));
        assertEquals(constant, AuthorizationInformationQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, AuthorizationInformationQualifier expected) {
        assertEquals(expected, AuthorizationInformationQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("none", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("noauth", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("ucs", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID),
                Arguments.of("edx", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID),
                Arguments.of("additionaldata", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID),
                Arguments.of("rail", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID),
                Arguments.of("dod", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("defense", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("federal", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),
                Arguments.of("govt", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),
                Arguments.of("usgov", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> AuthorizationInformationQualifier.fromString(input));
    }
}
