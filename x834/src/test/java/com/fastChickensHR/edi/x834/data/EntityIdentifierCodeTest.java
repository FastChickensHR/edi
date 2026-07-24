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

class EntityIdentifierCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(EntityIdentifierCode.class)
    void resolvesFromCodeNameAndDescription(EntityIdentifierCode constant) {
        assertEquals(constant, EntityIdentifierCode.fromString(constant.getCode()));
        assertEquals(constant, EntityIdentifierCode.fromString(constant.name()));
        assertEquals(constant, EntityIdentifierCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, EntityIdentifierCode expected) {
        assertEquals(expected, EntityIdentifierCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("hmo", EntityIdentifierCode.HEALTH_MAINTENANCE_ORGANIZATION),
                Arguments.of("ppo", EntityIdentifierCode.PREFERRED_PROVIDER_ORGANIZATION),
                Arguments.of("tpa", EntityIdentifierCode.THIRD_PARTY_ADMINISTRATOR),
                Arguments.of("provider", EntityIdentifierCode.PROVIDER),
                Arguments.of("dependent", EntityIdentifierCode.DEPENDENT),
                Arguments.of("hospital", EntityIdentifierCode.ACUTE_CARE_HOSPITAL),
                Arguments.of("pharmacy", EntityIdentifierCode.RETAIL_PHARMACY),
                Arguments.of("lab", EntityIdentifierCode.LABORATORY),
                Arguments.of("hospice", EntityIdentifierCode.HOSPICE));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> EntityIdentifierCode.fromString(input));
    }
}
