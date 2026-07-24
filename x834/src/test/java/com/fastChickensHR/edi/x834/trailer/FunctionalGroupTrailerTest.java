/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.segments.GESegment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupTrailerTest {

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

    /**
     * Render golden: the Functional Group Trailer renders as its underlying GE segment,
     * {@code GE*<count>*<groupControlNumber>~}. Whole-string equality pins the segment id, element
     * order, and terminator that the getter/{@code getElementValues()} echo left unrendered.
     * Delimiters come from the default {@link X834Context}.
     */
    @Test
    void rendersAsGeSegment() throws ValidationException {
        FunctionalGroupTrailer trailer = FunctionalGroupTrailer.builder()
                .setNumberOfTransactionSets("15")
                .setGroupControlNumber("5678")
                .build();
        trailer.setContext(new X834Context());

        assertEquals("GE*15*5678~\n", trailer.render());
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