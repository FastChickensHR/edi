/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IEASegmentTest {

    /**
     * Test implementation of IEASegment used for testing
     */
    private static class TestIEASegment extends IEASegment {
        private TestIEASegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestIEASegment build() throws ValidationException {
                return new TestIEASegment(this);
            }
        }
    }

    @Test
    void testConstructionAndGetters() throws ValidationException {
        String numberOfIncludedGroups = "3";
        String interchangeControlNumber = "000012345";

        TestIEASegment segment = TestIEASegment.builder()
                .setIea01(numberOfIncludedGroups)
                .setIea02(interchangeControlNumber)
                .build();

        assertEquals(numberOfIncludedGroups, segment.getIea01());
        assertEquals(interchangeControlNumber, segment.getIea02());
        assertEquals(numberOfIncludedGroups, segment.getNumberOfIncludedGroups());
        assertEquals(interchangeControlNumber, segment.getInterchangeControlNumber());
    }

    @Test
    void testAlternativeSetters() throws ValidationException {
        String numberOfIncludedGroups = "5";
        String interchangeControlNumber = "000067890";

        TestIEASegment segment = TestIEASegment.builder()
                .setNumberOfIncludedGroups(numberOfIncludedGroups)
                .setInterchangeControlNumber(interchangeControlNumber)
                .build();

        assertEquals(numberOfIncludedGroups, segment.getIea01());
        assertEquals(interchangeControlNumber, segment.getIea02());
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestIEASegment segment = TestIEASegment.builder()
                .setIea01("2")
                .setIea02("000054321")
                .build();

        assertEquals("IEA", segment.getSegmentIdentifier());
        assertEquals(IEASegment.SEGMENT_ID, segment.getSegmentIdentifier());
    }

    /**
     * Render golden for the IEA interchange trailer: {@code IEA*<groupCount>*<interchangeControlNumber>~}.
     * Whole-string equality pins the element order and terminator that a {@code getElementValues()}
     * array check leaves unrendered. Delimiters come from the default {@link X834Context}.
     */
    @Test
    void rendersIeaSegment() throws ValidationException {
        TestIEASegment segment = TestIEASegment.builder()
                .setNumberOfIncludedGroups("4")
                .setInterchangeControlNumber("000098765")
                .build();
        segment.setContext(new X834Context());

        assertEquals("IEA*4*000098765~\n", segment.render());
    }

    @Test
    void testBuilderMethodChaining() throws ValidationException {
        TestIEASegment segment = TestIEASegment.builder()
                .setIea01("7")
                .setIea02("000011111")
                .build();

        assertEquals("7", segment.getIea01());
        assertEquals("000011111", segment.getIea02());
    }

    @Test
    void testBlankIea01ThrowsValidationException() {
        assertThrows(ValidationException.class, () ->
                TestIEASegment.builder()
                        .setIea02("000000001")
                        .build()
        );
    }

    @Test
    void testBlankIea02ThrowsValidationException() {
        assertThrows(ValidationException.class, () ->
                TestIEASegment.builder()
                        .setIea01("1")
                        .build()
        );
    }

    @Test
    void testBothFieldsNullThrowsValidationException() {
        assertThrows(ValidationException.class, () ->
                TestIEASegment.builder().build()
        );
    }
}