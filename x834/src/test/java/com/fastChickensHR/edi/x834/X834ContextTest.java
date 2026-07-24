/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class X834ContextTest {

    private X834Context validContext() {
        return new X834Context()
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("1");
    }

    @Test
    void testValidContextPassesValidation() {
        assertDoesNotThrow(() -> validContext().validate());
    }

    @Test
    void testNullInterchangeControlNumberThrows() {
        X834Context ctx = validContext().setInterchangeControlNumber(null);
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testEmptyInterchangeControlNumberThrows() {
        X834Context ctx = validContext().setInterchangeControlNumber("");
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testShortInterchangeControlNumberThrows() {
        X834Context ctx = validContext().setInterchangeControlNumber("123");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testNonNumericInterchangeControlNumberThrows() {
        X834Context ctx = validContext().setInterchangeControlNumber("ABC123456");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testTenDigitInterchangeControlNumberThrows() {
        X834Context ctx = validContext().setInterchangeControlNumber("0000000001");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testNullGroupControlNumberThrows() {
        X834Context ctx = validContext().setGroupControlNumber(null);
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testEmptyGroupControlNumberThrows() {
        X834Context ctx = validContext().setGroupControlNumber("");
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testFormattedDateAndTimeBothFollowSetDocumentDate() {
        X834Context ctx = new X834Context()
                .setDocumentDate(LocalDateTime.of(2024, 1, 1, 0, 0));

        assertEquals("20240101", ctx.getFormattedDocumentDate(),
                "ISA09/GS04 date should come from the configured document date");
        assertEquals("0000", ctx.getFormattedDocumentTime(),
                "ISA10/GS05 time should come from the configured document date, not construction wall-clock");
    }

    @Test
    void testFormattedTimeIsNotFrozenAtConstruction() {
        X834Context ctx = new X834Context()
                .setDocumentDate(LocalDateTime.of(2023, 11, 15, 12, 30, 45));

        assertEquals("20231115", ctx.getFormattedDocumentDate());
        assertEquals("1230", ctx.getFormattedDocumentTime());

        ctx.setDocumentDate(LocalDateTime.of(2025, 6, 30, 23, 59, 0));
        assertEquals("20250630", ctx.getFormattedDocumentDate());
        assertEquals("2359", ctx.getFormattedDocumentTime());
    }

    @Test
    void testFormatChangesAfterConstructionAreReflectedOnRead() {
        X834Context ctx = new X834Context()
                .setDocumentDate(LocalDateTime.of(2023, 11, 15, 12, 30, 0));

        ctx.setDateFormat(DateFormat.D6);
        assertEquals("231115", ctx.getFormattedDocumentDate(),
                "changing the date format after construction should be reflected in the formatted date");
    }
}
