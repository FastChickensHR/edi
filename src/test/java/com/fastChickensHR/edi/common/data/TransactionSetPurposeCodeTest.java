/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionSetPurposeCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("00", TransactionSetPurposeCode.ORIGINAL.getCode());
        assertEquals("01", TransactionSetPurposeCode.CANCELLATION.getCode());
        assertEquals("5C", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION.getCode());
        assertEquals("CN", TransactionSetPurposeCode.COMPLETION_NOTIFICATION.getCode());
        assertEquals("ZZ", TransactionSetPurposeCode.MUTUALLY_DEFINED.getCode());

        assertEquals("Original", TransactionSetPurposeCode.ORIGINAL.getDescription());
        assertEquals("Cancellation", TransactionSetPurposeCode.CANCELLATION.getDescription());
        assertEquals("Chargeable Resubmission", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION.getDescription());
        assertEquals("Completion Notification", TransactionSetPurposeCode.COMPLETION_NOTIFICATION.getDescription());
        assertEquals("Mutually Defined", TransactionSetPurposeCode.MUTUALLY_DEFINED.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (TransactionSetPurposeCode code : TransactionSetPurposeCode.values()) {
            assertNotNull(code.getCode(), "Code should not be null");
            assertNotNull(code.getDescription(), "Description should not be null");
            assertFalse(code.getCode().isEmpty(), "Code should not be empty");
            assertFalse(code.getDescription().isEmpty(), "Description should not be empty");
        }
    }

    @Test
    void testFromString() {
        assertEquals(TransactionSetPurposeCode.ORIGINAL, TransactionSetPurposeCode.fromString("00"));
        assertEquals(TransactionSetPurposeCode.CANCELLATION, TransactionSetPurposeCode.fromString("01"));
        assertEquals(TransactionSetPurposeCode.ADD, TransactionSetPurposeCode.fromString("02"));

        assertEquals(TransactionSetPurposeCode.ORIGINAL, TransactionSetPurposeCode.fromString("original"));
        assertEquals(TransactionSetPurposeCode.ORIGINAL, TransactionSetPurposeCode.fromString("ORIGINAL"));
        assertEquals(TransactionSetPurposeCode.ORIGINAL, TransactionSetPurposeCode.fromString("Original"));

        assertEquals(TransactionSetPurposeCode.ADD, TransactionSetPurposeCode.fromString("create"));
        assertEquals(TransactionSetPurposeCode.DELETE, TransactionSetPurposeCode.fromString("remove"));
        assertEquals(TransactionSetPurposeCode.CHANGE, TransactionSetPurposeCode.fromString("update"));

        assertThrows(IllegalArgumentException.class, () -> TransactionSetPurposeCode.fromString("non-existent-value"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, TransactionSetPurposeCode expected) throws Exception {
        Field lookupField = TransactionSetPurposeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<TransactionSetPurposeCode> lookup =
                (EdiEnumLookup<TransactionSetPurposeCode>) lookupField.get(null);

        TransactionSetPurposeCode actual = lookup.fromString(input);
        assertEquals(expected, actual,
                "Input '" + input + "' should map to " + expected + " but mapped to " + actual);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                // Test exact codes
                Arguments.of("00", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("01", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("02", TransactionSetPurposeCode.ADD),
                Arguments.of("03", TransactionSetPurposeCode.DELETE),
                Arguments.of("04", TransactionSetPurposeCode.CHANGE),
                Arguments.of("05", TransactionSetPurposeCode.REPLACE),
                Arguments.of("5C", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION),
                Arguments.of("5c", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION),

                Arguments.of("original", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("ORIGINAL", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("Original", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("cancellation", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("add", TransactionSetPurposeCode.ADD),
                Arguments.of("ADD", TransactionSetPurposeCode.ADD),

                Arguments.of("new", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("initial submission", TransactionSetPurposeCode.ORIGINAL),
                Arguments.of("cancel", TransactionSetPurposeCode.CANCELLATION),
                Arguments.of("void", TransactionSetPurposeCode.CANCELLATION),
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
                Arguments.of("agreed upon", TransactionSetPurposeCode.MUTUALLY_DEFINED)
        );
    }

    @Test
    void testToString() {
        assertEquals("00", TransactionSetPurposeCode.ORIGINAL.toString());
        assertEquals("01", TransactionSetPurposeCode.CANCELLATION.toString());
        assertEquals("5C", TransactionSetPurposeCode.CHARGEABLE_RESUBMISSION.toString());
        assertEquals("CN", TransactionSetPurposeCode.COMPLETION_NOTIFICATION.toString());
        assertEquals("ZZ", TransactionSetPurposeCode.MUTUALLY_DEFINED.toString());
    }

    @Test
    void testEnumCompleteness() {
        assertEquals(76, TransactionSetPurposeCode.values().length,
                "There should be 83 transaction set purpose codes");

        long uniqueValues = Arrays.stream(TransactionSetPurposeCode.values())
                .map(TransactionSetPurposeCode::getCode)
                .distinct()
                .count();

        assertEquals(TransactionSetPurposeCode.values().length, uniqueValues,
                "All enum codes should be unique");
    }
}