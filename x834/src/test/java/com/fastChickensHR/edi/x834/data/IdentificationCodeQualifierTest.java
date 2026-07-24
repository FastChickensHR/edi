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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * First test coverage for {@link IdentificationCodeQualifier} — a ~240-constant enum (X12 data
 * element 66) that shipped with a full {@code fromString}/{@code LOOKUP} and throw contract but no
 * test at all.
 */
class IdentificationCodeQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(value = IdentificationCodeQualifier.class, mode = EnumSource.Mode.EXCLUDE, names = "POSTAL_CODE")
    void resolvesFromCodeNameAndDescription(IdentificationCodeQualifier constant) {
        assertEquals(constant, IdentificationCodeQualifier.fromString(constant.getCode()));
        assertEquals(constant, IdentificationCodeQualifier.fromString(constant.name()));
        assertEquals(constant, IdentificationCodeQualifier.fromString(constant.getDescription()));
    }

    /**
     * Surfaced collision (pinned as current behavior, not fixed under this test-only wave): the alias
     * {@code "postal code" -> ZIP_CODE} normalizes to {@code "postalcode"}, which is exactly what
     * {@link IdentificationCodeQualifier#POSTAL_CODE}'s enum name normalizes to. Because additional
     * mappings are applied after the per-constant entries, {@code POSTAL_CODE} is unreachable by its own
     * name (it resolves to {@link #ZIP_CODE}); its code {@code "AA"} and description still resolve
     * correctly. Owner follow-up: drop or re-point the ambiguous alias.
     */
    @Test
    void postalCodeNameIsShadowedByZipCodeAlias() {
        assertEquals(IdentificationCodeQualifier.POSTAL_CODE, IdentificationCodeQualifier.fromString("AA"));
        assertEquals(IdentificationCodeQualifier.POSTAL_CODE,
                IdentificationCodeQualifier.fromString(IdentificationCodeQualifier.POSTAL_CODE.getDescription()));
        assertEquals(IdentificationCodeQualifier.ZIP_CODE, IdentificationCodeQualifier.fromString("postal code"));
        assertEquals(IdentificationCodeQualifier.ZIP_CODE, IdentificationCodeQualifier.fromString("POSTAL_CODE"));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, IdentificationCodeQualifier expected) {
        assertEquals(expected, IdentificationCodeQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("duns", IdentificationCodeQualifier.DUNS),
                Arguments.of("dunsnumber", IdentificationCodeQualifier.DUNS),
                Arguments.of("dun and bradstreet", IdentificationCodeQualifier.DUNS),
                Arguments.of("phone number", IdentificationCodeQualifier.PHONE),
                Arguments.of("telephone", IdentificationCodeQualifier.PHONE),
                Arguments.of("ssn", IdentificationCodeQualifier.SSN),
                Arguments.of("social security", IdentificationCodeQualifier.SSN),
                Arguments.of("ein", IdentificationCodeQualifier.EIN),
                Arguments.of("employer id", IdentificationCodeQualifier.EIN),
                Arguments.of("tax id", IdentificationCodeQualifier.EIN),
                Arguments.of("zip", IdentificationCodeQualifier.ZIP_CODE),
                Arguments.of("postal code", IdentificationCodeQualifier.ZIP_CODE),
                Arguments.of("npi", IdentificationCodeQualifier.CMS_NPI),
                Arguments.of("national provider identifier", IdentificationCodeQualifier.CMS_NPI),
                Arguments.of("upin", IdentificationCodeQualifier.UPIN),
                Arguments.of("physician id", IdentificationCodeQualifier.UPIN),
                Arguments.of("website", IdentificationCodeQualifier.URL),
                Arguments.of("web address", IdentificationCodeQualifier.URL));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> IdentificationCodeQualifier.fromString(input));
    }
}
