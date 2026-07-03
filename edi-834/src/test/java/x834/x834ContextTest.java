/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class x834ContextTest {

    private x834Context validContext() {
        return new x834Context()
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("1");
    }

    @Test
    void testValidContextPassesValidation() {
        assertDoesNotThrow(() -> validContext().validate());
    }

    @Test
    void testNullInterchangeControlNumberThrows() {
        x834Context ctx = validContext().setInterchangeControlNumber(null);
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testEmptyInterchangeControlNumberThrows() {
        x834Context ctx = validContext().setInterchangeControlNumber("");
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testShortInterchangeControlNumberThrows() {
        x834Context ctx = validContext().setInterchangeControlNumber("123");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testNonNumericInterchangeControlNumberThrows() {
        x834Context ctx = validContext().setInterchangeControlNumber("ABC123456");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testTenDigitInterchangeControlNumberThrows() {
        x834Context ctx = validContext().setInterchangeControlNumber("0000000001");
        ValidationException ex = assertThrows(ValidationException.class, ctx::validate);
        assertTrue(ex.getMessage().contains("9 numeric digits"));
    }

    @Test
    void testNullGroupControlNumberThrows() {
        x834Context ctx = validContext().setGroupControlNumber(null);
        assertThrows(ValidationException.class, ctx::validate);
    }

    @Test
    void testEmptyGroupControlNumberThrows() {
        x834Context ctx = validContext().setGroupControlNumber("");
        assertThrows(ValidationException.class, ctx::validate);
    }
}
