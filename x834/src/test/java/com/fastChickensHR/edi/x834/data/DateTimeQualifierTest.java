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

class DateTimeQualifierTest {

    /**
     * Every one of the ~400 qualifiers resolves from its own X12 code and its enum name — the unique
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers per constant.
     * Driving this from {@link EnumSource} also guarantees no constant's code or name silently collides
     * with another's in the shared lookup map. Description-based resolution is exercised by the curated
     * {@link #aliases()} sample and the Placement-Date guard below rather than exhaustively, since many
     * of these qualifiers share overlapping descriptive wording.
     */
    @ParameterizedTest
    @EnumSource(DateTimeQualifier.class)
    void resolvesFromCodeAndName(DateTimeQualifier constant) {
        assertEquals(constant, DateTimeQualifier.fromString(constant.getCode()));
        assertEquals(constant, DateTimeQualifier.fromString(constant.name()));
    }

    /**
     * Qualifier 332's official X12 description is "Placement Date". A mechanical rename once corrupted
     * this domain literal to "Field Date", which broke description-based lookup; this pins the correct
     * wording and the code/description round-trip for that constant.
     */
    @Test
    void resolvesPlacementDateByDescription() {
        assertEquals("Placement Date", DateTimeQualifier.PLACEMENT_DATE.getDescription());
        assertEquals(DateTimeQualifier.PLACEMENT_DATE, DateTimeQualifier.fromString("Placement Date"));
        assertEquals(DateTimeQualifier.PLACEMENT_DATE, DateTimeQualifier.fromString("332"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeQualifier.fromString("Field Date"));
    }

    /**
     * A representative sample of resolution by short numeric code, description, and whitespace-padded
     * input — the normalization the lookup applies (trim, case- and separator-insensitive).
     */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, DateTimeQualifier expected) {
        assertEquals(expected, DateTimeQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("001", DateTimeQualifier.CANCEL_AFTER),
                Arguments.of("003", DateTimeQualifier.INVOICE),
                Arguments.of("007", DateTimeQualifier.EFFECTIVE),
                Arguments.of("011", DateTimeQualifier.SHIPPED),
                Arguments.of("035", DateTimeQualifier.DELIVERED),
                Arguments.of("Cancel After", DateTimeQualifier.CANCEL_AFTER),
                Arguments.of("Invoice", DateTimeQualifier.INVOICE),
                Arguments.of("Purchase Order", DateTimeQualifier.PURCHASE_ORDER),
                Arguments.of("Effective", DateTimeQualifier.EFFECTIVE),
                Arguments.of("Delivered", DateTimeQualifier.DELIVERED),
                Arguments.of("invoice", DateTimeQualifier.INVOICE),
                Arguments.of(" invoice ", DateTimeQualifier.INVOICE),
                Arguments.of("shipped", DateTimeQualifier.SHIPPED),
                Arguments.of(" shipped ", DateTimeQualifier.SHIPPED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> DateTimeQualifier.fromString(input));
    }
}
