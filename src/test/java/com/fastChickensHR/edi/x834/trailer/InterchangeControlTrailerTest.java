/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.common.IEASegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterchangeControlTrailerTest {

    @Test
    void testInheritance() {
        InterchangeControlTrailer trailer = null;
        try {
            trailer = InterchangeControlTrailer.builder()
                    .setNumberOfIncludedGroups("3")
                    .setInterchangeControlNumber("000012345")
                    .build();
        } catch (ValidationException e) {
            fail("Failed to build InterchangeControlTrailer: " + e.getMessage());
        }

        assertNotNull(trailer);
        assertTrue(trailer instanceof IEASegment);
    }

    @Test
    void testBuilderCreation() {
        InterchangeControlTrailer.Builder builder = InterchangeControlTrailer.builder();
        assertNotNull(builder);
    }

    @Test
    void testUsageInInterchangeContext() throws ValidationException {
        InterchangeControlTrailer trailer = InterchangeControlTrailer.builder()
                .setNumberOfIncludedGroups("5")
                .setInterchangeControlNumber("000098765")
                .build();

        IEASegment ieaSegment = trailer;

        assertEquals("5", ieaSegment.getNumberOfIncludedGroups());
        assertEquals("000098765", ieaSegment.getInterchangeControlNumber());
    }

    @Test
    void testSegmentComposition() throws ValidationException {
        InterchangeControlTrailer trailer = InterchangeControlTrailer.builder()
                .setNumberOfIncludedGroups("7")
                .setInterchangeControlNumber("000054321")
                .build();

        String[] elements = trailer.getElementValues();

        assertEquals("IEA", trailer.getSegmentIdentifier());
        assertEquals(2, elements.length);
        assertEquals("7", elements[0]);
        assertEquals("000054321", elements[1]);
    }

    @Test
    void testBuilderFluentInterface() throws ValidationException {
        InterchangeControlTrailer trailer = InterchangeControlTrailer.builder()
                .setIea01("8")
                .setIea02("000087654")
                .build();

        assertEquals("8", trailer.getNumberOfIncludedGroups());
        assertEquals("000087654", trailer.getInterchangeControlNumber());
    }
}