/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.segments.IEASegment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterchangeControlTrailerTest {

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

    /**
     * Render golden: the Interchange Control Trailer renders as its underlying IEA segment,
     * {@code IEA*<groupCount>*<interchangeControlNumber>~}. Whole-string equality pins the segment id,
     * element order, and terminator that the getter/{@code getElementValues()} echo left unrendered.
     * Delimiters come from the default {@link X834Context}.
     */
    @Test
    void rendersAsIeaSegment() throws ValidationException {
        InterchangeControlTrailer trailer = InterchangeControlTrailer.builder()
                .setNumberOfIncludedGroups("7")
                .setInterchangeControlNumber("000054321")
                .build();
        trailer.setContext(new X834Context());

        assertEquals("IEA*7*000054321~\n", trailer.render());
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