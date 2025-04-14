/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeQualifierTest {

    @Test
    void testEnumValues() {
        DateTimeQualifier[] values = DateTimeQualifier.values();

        assertEquals(398, values.length, "DateTimeQualifier should have values");

        assertTrue(Arrays.stream(values).anyMatch(v -> v == DateTimeQualifier.INVOICE));
        assertTrue(Arrays.stream(values).anyMatch(v -> v == DateTimeQualifier.PURCHASE_ORDER));
        assertTrue(Arrays.stream(values).anyMatch(v -> v == DateTimeQualifier.SHIPPED));
        assertTrue(Arrays.stream(values).anyMatch(v -> v == DateTimeQualifier.EFFECTIVE));
        assertTrue(Arrays.stream(values).anyMatch(v -> v == DateTimeQualifier.DELIVERED));
    }

    @Test
    void testEnumProperties() {
        DateTimeQualifier invoice = DateTimeQualifier.INVOICE;
        DateTimeQualifier shipped = DateTimeQualifier.SHIPPED;
        DateTimeQualifier effective = DateTimeQualifier.EFFECTIVE;
        DateTimeQualifier delivered = DateTimeQualifier.DELIVERED;

        assertEquals("003", invoice.getCode());
        assertEquals("Invoice", invoice.getDescription());

        assertEquals("011", shipped.getCode());
        assertEquals("Shipped", shipped.getDescription());

        assertEquals("007", effective.getCode());
        assertEquals("Effective", effective.getDescription());

        assertEquals("035", delivered.getCode());
        assertEquals("Delivered", delivered.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(DateTimeQualifier.INVOICE, DateTimeQualifier.fromString("003"));
        assertEquals(DateTimeQualifier.SHIPPED, DateTimeQualifier.fromString("011"));
        assertEquals(DateTimeQualifier.DELIVERED, DateTimeQualifier.fromString("035"));

        assertEquals(DateTimeQualifier.INVOICE, DateTimeQualifier.fromString("INVOICE"));
        assertEquals(DateTimeQualifier.SHIPPED, DateTimeQualifier.fromString("SHIPPED"));
        assertEquals(DateTimeQualifier.DELIVERED, DateTimeQualifier.fromString("DELIVERED"));

        assertThrows(IllegalArgumentException.class, () -> DateTimeQualifier.fromString("xyz"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeQualifier.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> DateTimeQualifier.fromString(null));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, DateTimeQualifier expected) throws Exception {
        DateTimeQualifier actual = DateTimeQualifier.fromString(input);

        assertEquals(expected, actual, "Failed for input: " + input);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("001", DateTimeQualifier.CANCEL_AFTER),
                Arguments.of("002", DateTimeQualifier.DELIVERY_REQUESTED),
                Arguments.of("003", DateTimeQualifier.INVOICE),
                Arguments.of("004", DateTimeQualifier.PURCHASE_ORDER),
                Arguments.of("005", DateTimeQualifier.SAILING),
                Arguments.of("006", DateTimeQualifier.SOLD),
                Arguments.of("007", DateTimeQualifier.EFFECTIVE),
                Arguments.of("008", DateTimeQualifier.PURCHASE_ORDER_RECEIVED),
                Arguments.of("009", DateTimeQualifier.PROCESS),
                Arguments.of("010", DateTimeQualifier.REQUESTED_SHIP),
                Arguments.of("011", DateTimeQualifier.SHIPPED),
                Arguments.of("035", DateTimeQualifier.DELIVERED),

                Arguments.of("CANCEL_AFTER", DateTimeQualifier.CANCEL_AFTER),
                Arguments.of("DELIVERY_REQUESTED", DateTimeQualifier.DELIVERY_REQUESTED),
                Arguments.of("INVOICE", DateTimeQualifier.INVOICE),
                Arguments.of("PURCHASE_ORDER", DateTimeQualifier.PURCHASE_ORDER),
                Arguments.of("SAILING", DateTimeQualifier.SAILING),
                Arguments.of("SOLD", DateTimeQualifier.SOLD),
                Arguments.of("EFFECTIVE", DateTimeQualifier.EFFECTIVE),
                Arguments.of("DELIVERED", DateTimeQualifier.DELIVERED),

                Arguments.of("Cancel After", DateTimeQualifier.CANCEL_AFTER),
                Arguments.of("Delivery Requested", DateTimeQualifier.DELIVERY_REQUESTED),
                Arguments.of("Invoice", DateTimeQualifier.INVOICE),
                Arguments.of("Purchase Order", DateTimeQualifier.PURCHASE_ORDER),
                Arguments.of("Sailing", DateTimeQualifier.SAILING),
                Arguments.of("Sold", DateTimeQualifier.SOLD),
                Arguments.of("Effective", DateTimeQualifier.EFFECTIVE),
                Arguments.of("Delivered", DateTimeQualifier.DELIVERED),

                Arguments.of("invoice", DateTimeQualifier.INVOICE),
                Arguments.of("INVOICE", DateTimeQualifier.INVOICE),
                Arguments.of(" invoice ", DateTimeQualifier.INVOICE),
                Arguments.of("shipped", DateTimeQualifier.SHIPPED),
                Arguments.of("SHIPPED", DateTimeQualifier.SHIPPED),
                Arguments.of(" shipped ", DateTimeQualifier.SHIPPED)
        );
    }

    @Test
    void testToString() {
        assertEquals("003", DateTimeQualifier.INVOICE.toString());
        assertEquals("011", DateTimeQualifier.SHIPPED.toString());
        assertEquals("035", DateTimeQualifier.DELIVERED.toString());
    }
}