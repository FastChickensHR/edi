/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RefSegmentTest {
    x834Context context = new x834Context();

    @Test
    void testCreation_WithValidValues_ShouldCreateSegment() throws ValidationException {
        RefSegment segment = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();

        assertEquals("38", segment.getReferenceIdentificationQualifier().getCode());
        assertEquals("12345", segment.getReferenceIdentification());
        assertEquals("REF", segment.getSegmentIdentifier());
    }

    @Test
    void testCreation_UsingDirectSetters_ShouldCreateSegment() throws ValidationException {
        // Arrange & Act
        RefSegment segment = new RefSegment.Builder()
                .setRef01("ZZ")
                .setRef02("ABC123")
                .build();

        assertEquals("ZZ", segment.getRef01().getCode());
        assertEquals("ABC123", segment.getRef02());
    }

    @Test
    void testToString_ShouldReturnFormattedString() throws ValidationException {
        RefSegment segment = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();
        segment.setContext(context);
        String segmentString = segment.render().trim();

        assertEquals("REF*38*12345~", segmentString);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void testValidation_InvalidReferenceIdentificationQualifier_ShouldThrowException(String invalidValue) {
        RefSegment.Builder builder = new RefSegment.Builder()
                .setReferenceIdentification("12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.setReferenceIdentificationQualifier(invalidValue));
        assertTrue(exception.getMessage().contains("Input cannot be null or empty"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void testValidation_InvalidReferenceIdentification_ShouldThrowException(String invalidValue) {
        RefSegment.Builder builder = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification(invalidValue);

        ValidationException exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("REF02"));
    }

    @Test
    void testGetElementValues_ShouldReturnCorrectArray() throws ValidationException {
        RefSegment segment = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();

        String[] elements = segment.getElementValues();

        assertEquals(3, elements.length);
        assertEquals("38", elements[0]);
        assertEquals("12345", elements[1]);
    }

    @Test
    void testChainedBuilderMethods_ShouldCreateSegment() throws ValidationException {
        RefSegment segment = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();

        assertEquals("38", segment.getReferenceIdentificationQualifier().getCode());
        assertEquals("12345", segment.getReferenceIdentification());
    }

    @Test
    void testSetterAliases_ShouldSetCorrectFields() throws ValidationException {
        RefSegment segment1 = new RefSegment.Builder()
                .setRef01("38")
                .setRef02("9876")
                .build();

        RefSegment segment2 = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("9876")
                .build();

        assertEquals(segment1.getRef01(), segment2.getRef01());
        assertEquals(segment1.getRef02(), segment2.getRef02());
    }

    @Test
    void testEquals_WithDifferentSegments_ShouldNotBeEqual() throws ValidationException {
        RefSegment segment1 = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();

        RefSegment segment2 = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("ZZ")
                .setReferenceIdentification("12345")
                .build();

        assertNotEquals(segment1, segment2);
    }

    @Test
    void testAccessorMethods_ShouldReturnCorrectValues() throws ValidationException {
        RefSegment segment = new RefSegment.Builder()
                .setReferenceIdentificationQualifier("38")
                .setReferenceIdentification("12345")
                .build();

        assertEquals("38", segment.getReferenceIdentificationQualifier().getCode());
        assertEquals("38", segment.getRef01().getCode());
        assertEquals("12345", segment.getReferenceIdentification());
        assertEquals("12345", segment.getRef02());
    }
}