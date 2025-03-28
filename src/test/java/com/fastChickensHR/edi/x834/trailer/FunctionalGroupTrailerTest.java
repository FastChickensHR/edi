/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.common.segments.GESegment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupTrailerTest {

    @Test
    void testInheritance() {
        FunctionalGroupTrailer trailer = null;
        try {
            trailer = FunctionalGroupTrailer.builder()
                    .setNumberOfTransactionSets("10")
                    .setGroupControlNumber("1234")
                    .build();
        } catch (ValidationException e) {
            fail("Failed to build FunctionalGroupTrailer: " + e.getMessage());
        }

        assertNotNull(trailer);
        assertTrue(trailer instanceof GESegment);
    }

    @Test
    void testBuilderCreation() {
        FunctionalGroupTrailer.Builder builder = FunctionalGroupTrailer.builder();
        assertNotNull(builder);
    }

    @Test
    void testUsageInGroupContext() throws ValidationException {
        FunctionalGroupTrailer trailer = FunctionalGroupTrailer.builder()
                .setNumberOfTransactionSets("5")
                .setGroupControlNumber("9876")
                .build();

        GESegment geSegment = trailer;

        assertEquals("5", geSegment.getNumberOfTransactionSets());
        assertEquals("9876", geSegment.getGroupControlNumber());
    }

    @Test
    void testSegmentComposition() throws ValidationException {
        FunctionalGroupTrailer trailer = FunctionalGroupTrailer.builder()
                .setNumberOfTransactionSets("15")
                .setGroupControlNumber("5678")
                .build();

        String[] elements = trailer.getElementValues();

        assertEquals("GE", trailer.getSegmentIdentifier());
        assertEquals(2, elements.length);
        assertEquals("15", elements[0]);
        assertEquals("5678", elements[1]);
    }

    @Test
    void testBuilderFluentInterface() throws ValidationException {
        FunctionalGroupTrailer trailer = FunctionalGroupTrailer.builder()
                .setNumberOfTransactionSets("3")
                .setGroupControlNumber("4321")
                .build();

        assertEquals("3", trailer.getNumberOfTransactionSets());
        assertEquals("4321", trailer.getGroupControlNumber());
    }
}