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

class TransactionSetIdentifierCodeTest {

    @Test
    void testEnumValues() {
        // Verify the exact number of enum values
        TransactionSetIdentifierCode[] values = TransactionSetIdentifierCode.values();
        assertEquals(69, values.length, "The number of enum values should match the expected count");

        assertEquals(TransactionSetIdentifierCode.INSURANCE_PLAN_DESCRIPTION.getCode(), "100");
        assertEquals(TransactionSetIdentifierCode.AIR_FREIGHT_DETAILS_AND_INVOICE.getCode(), "110");
        assertEquals(TransactionSetIdentifierCode.STUDENT_EDUCATIONAL_RECORD.getCode(), "130");
    }

    @Test
    void testEnumProperties() {
        // Test specific enum values have correct properties
        assertEquals("100", TransactionSetIdentifierCode.INSURANCE_PLAN_DESCRIPTION.getCode());
        assertEquals("Insurance Plan Description", TransactionSetIdentifierCode.INSURANCE_PLAN_DESCRIPTION.getDescription());

        assertEquals("110", TransactionSetIdentifierCode.AIR_FREIGHT_DETAILS_AND_INVOICE.getCode());
        assertEquals("Air Freight Details and Invoice", TransactionSetIdentifierCode.AIR_FREIGHT_DETAILS_AND_INVOICE.getDescription());

        assertEquals("130", TransactionSetIdentifierCode.STUDENT_EDUCATIONAL_RECORD.getCode());
        assertEquals("Student Educational Record (Transcript)", TransactionSetIdentifierCode.STUDENT_EDUCATIONAL_RECORD.getDescription());
    }

    @Test
    void testFromString() {
        // Test valid codes
        assertEquals(TransactionSetIdentifierCode.INSURANCE_PLAN_DESCRIPTION,
                TransactionSetIdentifierCode.fromString("100"));
        assertEquals(TransactionSetIdentifierCode.AIR_FREIGHT_DETAILS_AND_INVOICE,
                TransactionSetIdentifierCode.fromString("110"));
        assertEquals(TransactionSetIdentifierCode.STUDENT_EDUCATIONAL_RECORD,
                TransactionSetIdentifierCode.fromString("130"));

        // Test invalid codes
        assertThrows(IllegalArgumentException.class, () -> TransactionSetIdentifierCode.fromString("999"));
        assertThrows(IllegalArgumentException.class, () -> TransactionSetIdentifierCode.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> TransactionSetIdentifierCode.fromString(""));
    }


    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, TransactionSetIdentifierCode expected) throws Exception {
        // Verify lookup works for all enum values
        Field lookupField = TransactionSetIdentifierCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<TransactionSetIdentifierCode> lookup =
                (EdiEnumLookup<TransactionSetIdentifierCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Arrays.stream(TransactionSetIdentifierCode.values())
                .map(code -> Arguments.of(code.getCode(), code));
    }
}