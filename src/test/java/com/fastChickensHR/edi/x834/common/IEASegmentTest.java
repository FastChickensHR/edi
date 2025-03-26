/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.segments.IEASegment;
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

    @Test
    void testElementValues() throws ValidationException {
        String numberOfIncludedGroups = "4";
        String interchangeControlNumber = "000098765";

        TestIEASegment segment = TestIEASegment.builder()
                .setNumberOfIncludedGroups(numberOfIncludedGroups)
                .setInterchangeControlNumber(interchangeControlNumber)
                .build();

        String[] elementValues = segment.getElementValues();

        assertEquals(2, elementValues.length);
        assertEquals(numberOfIncludedGroups, elementValues[0]);
        assertEquals(interchangeControlNumber, elementValues[1]);
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
    void testNullValues() throws ValidationException {
        TestIEASegment segment = TestIEASegment.builder()
                .build();

        assertNull(segment.getIea01());
        assertNull(segment.getIea02());
    }
}