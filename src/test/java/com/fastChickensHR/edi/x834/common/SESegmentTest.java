/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.segments.SESegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SESegmentTest {
    private static class TestSESegment extends SESegment {
        private TestSESegment(AbstractBuilder<?> builder) throws ValidationException {
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
            public TestSESegment build() throws ValidationException {
                return new TestSESegment(this);
            }
        }
    }

    @Test
    void testConstructionAndGetters() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setSe01("15")
                .setSe02("0001")
                .build();

        assertEquals("15", segment.getSe01());
        assertEquals("0001", segment.getSe02());
        assertEquals("15", segment.getTransactionSegmentCount());
        assertEquals("0001", segment.getSetControlNumber());
    }

    @Test
    void testAlternativeSetters() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setTransactionSegmentCount("25")
                .setSetControlNumber("1234")
                .build();

        assertEquals("25", segment.getSe01());
        assertEquals("1234", segment.getSe02());
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setSe01("10")
                .setSe02("9999")
                .build();

        assertEquals("SE", segment.getSegmentIdentifier());
        assertEquals(SESegment.SEGMENT_ID, segment.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setSe01("42")
                .setSe02("5678")
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(2, elements.length);
        assertEquals("42", elements[0]);
        assertEquals("5678", elements[1]);
    }

    @Test
    void testBuilderMethodChaining() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setSe01("50")
                .setSe02("1111")
                .build();

        assertEquals("50", segment.getSe01());
        assertEquals("1111", segment.getSe02());
    }

    @Test
    void testNullValues() throws ValidationException {
        SESegment segment = TestSESegment.builder()
                .setSe01(null)
                .setSe02(null)
                .build();

        assertNull(segment.getSe01());
        assertNull(segment.getSe02());
    }
}