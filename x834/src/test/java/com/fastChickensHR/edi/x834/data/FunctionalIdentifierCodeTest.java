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

class FunctionalIdentifierCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     * (The previous test exercised {@code Enum.valueOf} instead of the real {@code fromString} lookup.)
     */
    @ParameterizedTest
    @EnumSource(FunctionalIdentifierCode.class)
    void resolvesFromCodeNameAndDescription(FunctionalIdentifierCode constant) {
        assertEquals(constant, FunctionalIdentifierCode.fromString(constant.getCode()));
        assertEquals(constant, FunctionalIdentifierCode.fromString(constant.name()));
        assertEquals(constant, FunctionalIdentifierCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type — including bare transaction-set numbers. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, FunctionalIdentifierCode expected) {
        assertEquals(expected, FunctionalIdentifierCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("be", FunctionalIdentifierCode.BENEFIT_ENROLLMENT),
                Arguments.of("834", FunctionalIdentifierCode.BENEFIT_ENROLLMENT),
                Arguments.of("benefits enrollment", FunctionalIdentifierCode.BENEFIT_ENROLLMENT),
                Arguments.of("enrollment", FunctionalIdentifierCode.BENEFIT_ENROLLMENT),
                Arguments.of("hp", FunctionalIdentifierCode.HEALTH_CARE_CLAIM_PAYMENT),
                Arguments.of("835", FunctionalIdentifierCode.HEALTH_CARE_CLAIM_PAYMENT),
                Arguments.of("payment", FunctionalIdentifierCode.HEALTH_CARE_CLAIM_PAYMENT),
                Arguments.of("claim payment", FunctionalIdentifierCode.HEALTH_CARE_CLAIM_PAYMENT),
                Arguments.of("hc", FunctionalIdentifierCode.HEALTH_CARE_CLAIM),
                Arguments.of("837", FunctionalIdentifierCode.HEALTH_CARE_CLAIM),
                Arguments.of("claim", FunctionalIdentifierCode.HEALTH_CARE_CLAIM),
                Arguments.of("hs", FunctionalIdentifierCode.ELIGIBILITY_INQUIRY),
                Arguments.of("270", FunctionalIdentifierCode.ELIGIBILITY_INQUIRY),
                Arguments.of("eligibility request", FunctionalIdentifierCode.ELIGIBILITY_INQUIRY),
                Arguments.of("hb", FunctionalIdentifierCode.ELIGIBILITY_BENEFIT_INFO),
                Arguments.of("271", FunctionalIdentifierCode.ELIGIBILITY_BENEFIT_INFO),
                Arguments.of("eligibility response", FunctionalIdentifierCode.ELIGIBILITY_BENEFIT_INFO),
                Arguments.of("po", FunctionalIdentifierCode.PURCHASE_ORDER),
                Arguments.of("850", FunctionalIdentifierCode.PURCHASE_ORDER),
                Arguments.of("order", FunctionalIdentifierCode.PURCHASE_ORDER),
                Arguments.of("in", FunctionalIdentifierCode.INVOICE_INFORMATION),
                Arguments.of("810", FunctionalIdentifierCode.INVOICE_INFORMATION),
                Arguments.of("invoice", FunctionalIdentifierCode.INVOICE_INFORMATION),
                Arguments.of("sh", FunctionalIdentifierCode.SHIP_NOTICE_MANIFEST),
                Arguments.of("856", FunctionalIdentifierCode.SHIP_NOTICE_MANIFEST),
                Arguments.of("asn", FunctionalIdentifierCode.SHIP_NOTICE_MANIFEST),
                Arguments.of("advance shipment notice", FunctionalIdentifierCode.SHIP_NOTICE_MANIFEST),
                Arguments.of("fa", FunctionalIdentifierCode.FUNCTIONAL_ACKNOWLEDGMENT),
                Arguments.of("997", FunctionalIdentifierCode.FUNCTIONAL_ACKNOWLEDGMENT),
                Arguments.of("999", FunctionalIdentifierCode.FUNCTIONAL_ACKNOWLEDGMENT),
                Arguments.of("ack", FunctionalIdentifierCode.FUNCTIONAL_ACKNOWLEDGMENT),
                Arguments.of("acknowledgment", FunctionalIdentifierCode.FUNCTIONAL_ACKNOWLEDGMENT));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> FunctionalIdentifierCode.fromString(input));
    }
}
