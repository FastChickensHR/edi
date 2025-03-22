/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GESegmentTest {

    /**
     * Test implementation of GESegment used for testing
     */
    private static class TestGESegment extends GESegment {
        private TestGESegment(AbstractBuilder<?> builder) throws ValidationException {
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
            public TestGESegment build() throws ValidationException {
                return new TestGESegment(this);
            }
        }
    }

    @Test
    void testConstructionAndGetters() throws ValidationException {
        String numberOfTransactionSets = "5";
        String groupControlNumber = "12345";

        TestGESegment segment = TestGESegment.builder()
                .setGe01(numberOfTransactionSets)
                .setGe02(groupControlNumber)
                .build();

        assertEquals(numberOfTransactionSets, segment.getGe01());
        assertEquals(groupControlNumber, segment.getGe02());
        assertEquals(numberOfTransactionSets, segment.getNumberOfTransactionSets());
        assertEquals(groupControlNumber, segment.getGroupControlNumber());
    }

    @Test
    void testAlternativeSetters() throws ValidationException {
        String numberOfTransactionSets = "10";
        String groupControlNumber = "67890";

        TestGESegment segment = TestGESegment.builder()
                .setNumberOfTransactionSets(numberOfTransactionSets)
                .setGroupControlNumber(groupControlNumber)
                .build();

        assertEquals(numberOfTransactionSets, segment.getGe01());
        assertEquals(groupControlNumber, segment.getGe02());
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestGESegment segment = TestGESegment.builder()
                .setGe01("3")
                .setGe02("54321")
                .build();

        assertEquals("GE", segment.getSegmentIdentifier());
        assertEquals(GESegment.SEGMENT_ID, segment.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        String numberOfTransactionSets = "7";
        String groupControlNumber = "98765";

        TestGESegment segment = TestGESegment.builder()
                .setNumberOfTransactionSets(numberOfTransactionSets)
                .setGroupControlNumber(groupControlNumber)
                .build();

        String[] elementValues = segment.getElementValues();

        assertEquals(2, elementValues.length);
        assertEquals(numberOfTransactionSets, elementValues[0]);
        assertEquals(groupControlNumber, elementValues[1]);
    }

    @Test
    void testBuilderMethodChaining() throws ValidationException {
        TestGESegment segment = TestGESegment.builder()
                .setGe01("15")
                .setGe02("11111")
                .build();

        assertEquals("15", segment.getGe01());
        assertEquals("11111", segment.getGe02());
    }

    @Test
    void testNullValues() throws ValidationException {
        TestGESegment segment = TestGESegment.builder()
                .build();

        assertNull(segment.getGe01());
        assertNull(segment.getGe02());
    }
}