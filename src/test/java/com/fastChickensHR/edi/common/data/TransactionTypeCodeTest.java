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

class TransactionTypeCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("01", TransactionTypeCode.LOCATION_ADDRESS.getCode());
        assertEquals("Location Address Message", TransactionTypeCode.LOCATION_ADDRESS.getDescription());

        assertEquals("1A", TransactionTypeCode.UNIQUE_TRACKING_CONTROL.getCode());
        assertEquals("Unique Item Tracking Control Report", TransactionTypeCode.UNIQUE_TRACKING_CONTROL.getDescription());

        assertEquals("20", TransactionTypeCode.AIR_EXPORT_WAYBILL.getCode());
        assertEquals("Air Export Waybill and Invoice", TransactionTypeCode.AIR_EXPORT_WAYBILL.getDescription());

        assertEquals("ZZ", TransactionTypeCode.MUTUALLY_DEFINED.getCode());
        assertEquals("Mutually Defined", TransactionTypeCode.MUTUALLY_DEFINED.getDescription());
    }

    @Test
    void testEnumProperties() {
        // Verify that all enum values have valid codes and descriptions
        for (TransactionTypeCode code : TransactionTypeCode.values()) {
            assertNotNull(code.getCode(), "Code should not be null for " + code.name());
            assertNotNull(code.getDescription(), "Description should not be null for " + code.name());
            assertFalse(code.getCode().isEmpty(), "Code should not be empty for " + code.name());
            assertFalse(code.getDescription().isEmpty(), "Description should not be empty for " + code.name());
        }
    }

    @Test
    void testToString() {
        // Verify toString returns the code value
        assertEquals("01", TransactionTypeCode.LOCATION_ADDRESS.toString());
        assertEquals("1A", TransactionTypeCode.UNIQUE_TRACKING_CONTROL.toString());
        assertEquals("3M", TransactionTypeCode.SUPPORTING_INFORMATION.toString());
        assertEquals("20", TransactionTypeCode.AIR_EXPORT_WAYBILL.toString());
    }

    @Test
    void testFromString() {
        assertEquals(TransactionTypeCode.LOCATION_ADDRESS, TransactionTypeCode.fromString("01"));
        assertEquals(TransactionTypeCode.UNIQUE_TRACKING_CONTROL, TransactionTypeCode.fromString("1A"));
        assertEquals(TransactionTypeCode.MAINTENANCE_REQUEST, TransactionTypeCode.fromString("13"));
        assertEquals(TransactionTypeCode.AIR_EXPORT_WAYBILL, TransactionTypeCode.fromString("20"));

        assertEquals(TransactionTypeCode.LOCATION_ADDRESS, TransactionTypeCode.fromString("location_address"));
        assertEquals(TransactionTypeCode.AIR_EXPORT_WAYBILL, TransactionTypeCode.fromString("air_export_waybill"));

        assertEquals(TransactionTypeCode.LOCATION_ADDRESS, TransactionTypeCode.fromString("Location Address Message"));
        assertEquals(TransactionTypeCode.AIR_EXPORT_WAYBILL, TransactionTypeCode.fromString("Air Export Waybill and Invoice"));

        assertEquals(TransactionTypeCode.LOCATION_ADDRESS, TransactionTypeCode.fromString("location address"));
        assertEquals(TransactionTypeCode.UNIQUE_TRACKING_CONTROL, TransactionTypeCode.fromString("tracking control"));

        assertThrows(IllegalArgumentException.class, () -> TransactionTypeCode.fromString("invalid code"));
        assertThrows(IllegalArgumentException.class, () -> TransactionTypeCode.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> TransactionTypeCode.fromString(null));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, TransactionTypeCode expected) throws Exception {
        Field lookupField = TransactionTypeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<TransactionTypeCode> lookup =
                (EdiEnumLookup<TransactionTypeCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("location address", TransactionTypeCode.LOCATION_ADDRESS),
                Arguments.of("address message", TransactionTypeCode.LOCATION_ADDRESS),

                Arguments.of("tracking control", TransactionTypeCode.UNIQUE_TRACKING_CONTROL),
                Arguments.of("control report", TransactionTypeCode.UNIQUE_TRACKING_CONTROL),

                Arguments.of("tracking reconciliation", TransactionTypeCode.UNIQUE_TRACKING_RECONCILIATION),
                Arguments.of("report reconciliation", TransactionTypeCode.UNIQUE_TRACKING_RECONCILIATION),

                Arguments.of("data change", TransactionTypeCode.UNIQUE_TRACKING_DATA_CHANGE),
                Arguments.of("item data change", TransactionTypeCode.UNIQUE_TRACKING_DATA_CHANGE),

                Arguments.of("new enrollment", TransactionTypeCode.NEW_GROUP_ENROLLMENT),
                Arguments.of("initial enrollment", TransactionTypeCode.NEW_GROUP_ENROLLMENT),

                Arguments.of("location relation", TransactionTypeCode.LOCATION_RELATION),
                Arguments.of("relation information", TransactionTypeCode.LOCATION_RELATION),

                Arguments.of("report", TransactionTypeCode.REPORT_MESSAGE),
                Arguments.of("report message", TransactionTypeCode.REPORT_MESSAGE),

                Arguments.of("supporting info", TransactionTypeCode.SUPPORTING_INFORMATION),
                Arguments.of("support information", TransactionTypeCode.SUPPORTING_INFORMATION),

                Arguments.of("email", TransactionTypeCode.ELECTRONIC_MAIL),
                Arguments.of("e-mail", TransactionTypeCode.ELECTRONIC_MAIL),
                Arguments.of("mail message", TransactionTypeCode.ELECTRONIC_MAIL),

                Arguments.of("air export", TransactionTypeCode.AIR_EXPORT_WAYBILL),
                Arguments.of("waybill", TransactionTypeCode.AIR_EXPORT_WAYBILL),
                Arguments.of("export waybill", TransactionTypeCode.AIR_EXPORT_WAYBILL)
        );
    }
}