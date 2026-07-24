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

class TransactionSetPurposeCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than reflecting into the private {@code LOOKUP} field
     * also guarantees no constant's code, name, or description silently collides with another's.
     */
    @ParameterizedTest
    @EnumSource(value = TransactionSetPurposeCode.class, mode = EnumSource.Mode.EXCLUDE, names = "STATUS_UPDATE")
    void resolvesFromCodeNameAndDescription(TransactionSetPurposeCode constant) {
        assertEquals(constant, TransactionSetPurposeCode.fromString(constant.getCode()));
        assertEquals(constant, TransactionSetPurposeCode.fromString(constant.name()));
        assertEquals(constant, TransactionSetPurposeCode.fromString(constant.getDescription()));
    }

    /**
     * Surfaced collision (pinned as current behavior, not fixed under this test-only wave): the alias
     * {@code "status update" -> STATUS} shadows {@link TransactionSetPurposeCode#STATUS_UPDATE}, whose
     * own name and description both normalize to {@code "statusupdate"} as well. Because additional
     * mappings are applied after the per-constant entries, {@code STATUS_UPDATE} is reachable only by
     * its code {@code "SU"}; by name or description it currently resolves to {@link #STATUS}. Owner
     * follow-up: drop or re-point the ambiguous alias so {@code STATUS_UPDATE} is reachable by name.
     */
    @Test
    void statusUpdateNameIsShadowedByStatusAlias() {
        assertEquals(TransactionSetPurposeCode.STATUS_UPDATE, TransactionSetPurposeCode.fromString("SU"));
        assertEquals(TransactionSetPurposeCode.STATUS, TransactionSetPurposeCode.fromString("status update"));
        assertEquals(TransactionSetPurposeCode.STATUS, TransactionSetPurposeCode.fromString("STATUS_UPDATE"));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, TransactionSetPurposeCode expected) {
        assertEquals(expected, TransactionSetPurposeCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("new", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("initial submission", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("void", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("cancel", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("terminate", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("addition", TransactionSetPurposeCode.ADD),
                Arguments.of("insert", TransactionSetPurposeCode.ADD),
                Arguments.of("create", TransactionSetPurposeCode.ADD),
                Arguments.of("remove", TransactionSetPurposeCode.DELETE),
                Arguments.of("erase", TransactionSetPurposeCode.DELETE),
                Arguments.of("modify", TransactionSetPurposeCode.CHANGE),
                Arguments.of("update", TransactionSetPurposeCode.CHANGE),
                Arguments.of("amend", TransactionSetPurposeCode.CHANGE),
                Arguments.of("substitution", TransactionSetPurposeCode.REPLACE),
                Arguments.of("swap", TransactionSetPurposeCode.REPLACE),
                Arguments.of("chargeable", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION),
                Arguments.of("chargeable resubmit", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION),
                Arguments.of("confirm", TransactionSetPurposeCode.CONFIRMATION),
                Arguments.of("verified", TransactionSetPurposeCode.CONFIRMATION),
                Arguments.of("copy", TransactionSetPurposeCode.DUPLICATE),
                Arguments.of("replicate", TransactionSetPurposeCode.DUPLICATE),
                Arguments.of("status update", TransactionSetPurposeCode.STATUS),
                Arguments.of("status check", TransactionSetPurposeCode.STATUS),
                Arguments.of("inquiry", TransactionSetPurposeCode.REQUEST),
                Arguments.of("asking", TransactionSetPurposeCode.REQUEST),
                Arguments.of("resubmit", TransactionSetPurposeCode.RESUBMISSION),
                Arguments.of("resend", TransactionSetPurposeCode.RESUBMISSION),
                Arguments.of("complete", TransactionSetPurposeCode.COMPLETION_NOTIFICATION),
                Arguments.of("finished", TransactionSetPurposeCode.COMPLETION_NOTIFICATION),
                Arguments.of("custom", TransactionSetPurposeCode.MUTUALLY_DEFINED),
                Arguments.of("agreed upon", TransactionSetPurposeCode.MUTUALLY_DEFINED));
    }

    /**
     * Every constant has a distinct code. A duplicate would silently clobber the earlier constant in
     * the shared lookup map (last registration wins), making it unreachable by code.
     */
    @Test
    void codesAreUnique() {
        Map<String, TransactionSetPurposeCode> byCode = new HashMap<>();
        for (TransactionSetPurposeCode constant : TransactionSetPurposeCode.values()) {
            TransactionSetPurposeCode prior = byCode.put(constant.getCode(), constant);
            assertNull(prior, () -> "duplicate code '" + constant.getCode() + "' shared by "
                    + prior + " and " + constant);
        }
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> TransactionSetPurposeCode.fromString(input));
    }
}
