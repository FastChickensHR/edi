/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.trailer;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrailerTest {

    private x834Context context;

    @BeforeEach
    void setUp() {
        context = new x834Context();
        // Set up any context properties needed for testing
    }

    @Test
    void testTrailerConstruction() throws ValidationException {
        // Test basic construction with default values
        Trailer trailer = new Trailer.Builder(context).build();

        assertNotNull(trailer);
        assertEquals(context, trailer.getContext());
        assertNotNull(trailer.getTransactionSetTrailer());
        assertNotNull(trailer.getFunctionalGroupTrailer());
        assertNotNull(trailer.getInterchangeControlTrailer());
    }

    @Test
    void testSegmentGeneration() throws ValidationException {
        // Test segment generation
        Trailer trailer = new Trailer.Builder(context)
                .setTransactionSetControlNumber("1234")
                .setNumberOfIncludedSegments("42")
                .build();

        List<Segment> segments = trailer.generateSegments();

        assertNotNull(segments);
        assertFalse(segments.isEmpty());
        assertEquals(3, segments.size()); // Should have SE, GE, and IEA segments

        // Verify segment types and order
        assertEquals("SE", segments.get(0).getSegmentIdentifier());
        assertEquals("GE", segments.get(1).getSegmentIdentifier());
        assertEquals("IEA", segments.get(2).getSegmentIdentifier());
    }

    @Test
    void testBuilderParameterSetting() throws ValidationException {
        // Test that builder parameters are correctly set
        String transactionSetControlNumber = "5678";
        String numberOfIncludedSegments = "50";
        String numberOfTransactionSetsIncluded = "2";
        String groupControlNumber = "88";
        String numberOfIncludedFunctionalGroups = "3";
        String interchangeControlNumber = "123456789";

        Trailer trailer = new Trailer.Builder(context)
                .setTransactionSetControlNumber(transactionSetControlNumber)
                .setNumberOfIncludedSegments(numberOfIncludedSegments)
                .setNumberOfTransactionSetsIncluded(numberOfTransactionSetsIncluded)
                .setGroupControlNumber(groupControlNumber)
                .setNumberOfIncludedFunctionalGroups(numberOfIncludedFunctionalGroups)
                .setInterchangeControlNumber(interchangeControlNumber)
                .build();

        // Verify values are correctly passed through to the component trailers
        assertEquals(transactionSetControlNumber, trailer.getTransactionSetTrailer().getSetControlNumber());
        assertEquals(numberOfIncludedSegments, trailer.getTransactionSetTrailer().getTransactionSegmentCount());

        assertEquals(numberOfTransactionSetsIncluded, trailer.getFunctionalGroupTrailer().getNumberOfTransactionSets());
        assertEquals(groupControlNumber, trailer.getFunctionalGroupTrailer().getGroupControlNumber());

        assertEquals(numberOfIncludedFunctionalGroups, trailer.getInterchangeControlTrailer().getNumberOfIncludedGroups());
        assertEquals(interchangeControlNumber, trailer.getInterchangeControlTrailer().getInterchangeControlNumber());
    }

    @Test
    void testBuilderWithNullContext() {
        // Test that builder throws exception when context is null
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Trailer.Builder(null);
        });

        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    void testCustomBuilders() throws ValidationException {
        // Test using custom builders for the component trailers
        TransactionSetTrailer.Builder transBuilder = new TransactionSetTrailer.Builder();
        transBuilder.setSetControlNumber("9999");

        FunctionalGroupTrailer.Builder functionalBuilder = new FunctionalGroupTrailer.Builder();
        functionalBuilder.setGroupControlNumber("8888");

        InterchangeControlTrailer.Builder interchangeBuilder = new InterchangeControlTrailer.Builder();
        interchangeBuilder.setInterchangeControlNumber("7777777");

        Trailer trailer = new Trailer.Builder(context)
                .withTransactionSetTrailer(transBuilder)
                .withFunctionalGroupTrailer(functionalBuilder)
                .withInterchangeControlTrailer(interchangeBuilder)
                .build();

        assertEquals("9999", trailer.getTransactionSetTrailer().getSetControlNumber());
        assertEquals("8888", trailer.getFunctionalGroupTrailer().getGroupControlNumber());
        assertEquals("7777777", trailer.getInterchangeControlTrailer().getInterchangeControlNumber());
    }

    @Test
    void testDefaultValues() throws ValidationException {
        // Test default values
        Trailer trailer = new Trailer.Builder(context).build();

        // Check default values against what we expect
        assertEquals("0001", trailer.getTransactionSetTrailer().getSetControlNumber());
        assertEquals("10", trailer.getTransactionSetTrailer().getTransactionSegmentCount());
        assertEquals("1", trailer.getFunctionalGroupTrailer().getNumberOfTransactionSets());
        assertEquals("1", trailer.getFunctionalGroupTrailer().getGroupControlNumber());
        assertEquals("1", trailer.getInterchangeControlTrailer().getNumberOfIncludedGroups());
        assertEquals("000000001", trailer.getInterchangeControlTrailer().getInterchangeControlNumber());
    }
}