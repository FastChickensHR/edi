/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReferenceIdentificationQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(ReferenceIdentificationQualifier.class)
    void resolvesFromCodeNameAndDescription(ReferenceIdentificationQualifier constant) {
        assertEquals(constant, ReferenceIdentificationQualifier.fromString(constant.getCode()));
        assertEquals(constant, ReferenceIdentificationQualifier.fromString(constant.name()));
        assertEquals(constant, ReferenceIdentificationQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, ReferenceIdentificationQualifier expected) {
        assertEquals(expected, ReferenceIdentificationQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("member id", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),
                Arguments.of("subscriber id", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("member number", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),
                Arguments.of("subscriber", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("tracking", ReferenceIdentificationQualifier.TRACKING_NUMBER),
                Arguments.of("state license", ReferenceIdentificationQualifier.STATE_LICENSE_NUMBER),
                Arguments.of("medicare", ReferenceIdentificationQualifier.MEDICARE_PROVIDER_NUMBER),
                Arguments.of("medicaid", ReferenceIdentificationQualifier.MEDICAID_PROVIDER_NUMBER),
                Arguments.of("policy number", ReferenceIdentificationQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("mortgage id", ReferenceIdentificationQualifier.MORTGAGE_IDENTIFICATION_NUMBER),
                Arguments.of("tracking id", ReferenceIdentificationQualifier.TRACKING_NUMBER),
                Arguments.of("claim number", ReferenceIdentificationQualifier.PAYERS_CLAIM_NUMBER),
                Arguments.of("ssn", ReferenceIdentificationQualifier.SOCIAL_SECURITY_NUMBER),
                Arguments.of("social security number", ReferenceIdentificationQualifier.SOCIAL_SECURITY_NUMBER));
    }

    /**
     * Every constant has a distinct code. A duplicate would silently clobber the earlier constant in
     * the shared lookup map (last registration wins), making it unreachable by code.
     */
    @Test
    void codesAreUnique() {
        Map<String, ReferenceIdentificationQualifier> byCode = new HashMap<>();
        for (ReferenceIdentificationQualifier constant : ReferenceIdentificationQualifier.values()) {
            ReferenceIdentificationQualifier prior = byCode.put(constant.getCode(), constant);
            assertNull(prior, () -> "duplicate code '" + constant.getCode() + "' shared by "
                    + prior + " and " + constant);
        }
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> ReferenceIdentificationQualifier.fromString(input));
    }
}
