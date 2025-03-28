/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionSetTrailerTest {

    @Test
    void testInheritance() {
        TransactionSetTrailer trailer = null;
        try {
            trailer = TransactionSetTrailer.builder()
                    .setTransactionSegmentCount("10")
                    .setSetControlNumber("1234")
                    .build();
        } catch (ValidationException e) {
            fail("Failed to build TransactionSetTrailer: " + e.getMessage());
        }

        assertNotNull(trailer);
    }

    @Test
    void testBuilderCreation() {
        TransactionSetTrailer.Builder builder = TransactionSetTrailer.builder();
        assertNotNull(builder);
    }

    @Test
    void testUsageInTransactionContext() throws ValidationException {
        TransactionSetTrailer trailer = TransactionSetTrailer.builder()
                .setTransactionSegmentCount("25")
                .setSetControlNumber("9876")
                .build();

        assertEquals("25", trailer.getTransactionSegmentCount());
        assertEquals("9876", trailer.getSetControlNumber());
        assertEquals("SE", trailer.getSegmentIdentifier());

        String[] elements = trailer.getElementValues();
        assertEquals(2, elements.length);
        assertEquals("25", elements[0]);
        assertEquals("9876", elements[1]);
    }

    @Test
    void testToString() throws ValidationException {
        TransactionSetTrailer trailer = TransactionSetTrailer.builder()
                .setTransactionSegmentCount("15")
                .setSetControlNumber("5678")
                .build();
        trailer.setContext(new x834Context());

        String segment = trailer.render().trim();

        assertEquals("SE*15*5678~", segment);
    }
}