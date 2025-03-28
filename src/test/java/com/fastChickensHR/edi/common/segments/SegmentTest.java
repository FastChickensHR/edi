/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.constants.SegmentTerminator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    private x834Context context;

    private static class TestSegment extends Segment {
        private final String segmentId;
        private final String[] elements;

        public TestSegment(String segmentId, String[] elements) {
            this.segmentId = segmentId;
            this.elements = elements;
        }

        @Override
        public String getSegmentIdentifier() {
            return segmentId;
        }

        @Override
        public String[] getElementValues() {
            return elements;
        }
    }

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testRenderWithAllNonNullElements() {
        TestSegment segment = new TestSegment("TST", new String[]{"E1", "E2", "E3"});
        segment.setContext(context);

        String expected = "TST*E1*E2*E3~";
        assertEquals(expected, segment.render().trim());
    }

    @Test
    void testRenderWithTrailingNullElements() {
        TestSegment segment = new TestSegment("TST", new String[]{"E1", "E2", null, null});
        segment.setContext(context);

        String expected = "TST*E1*E2~";
        assertEquals(expected, segment.render().trim());
    }

    @Test
    void testRenderWithMiddleNullElements() {
        TestSegment segment = new TestSegment("TST", new String[]{"E1", null, "E3", null, "E5", null});
        segment.setContext(context);

        String expected = "TST*E1**E3**E5~";
        assertEquals(expected, segment.render().trim());
    }

    @Test
    void testRenderWithAllNullElements() {
        TestSegment segment = new TestSegment("TST", new String[]{null, null, null});
        segment.setContext(context);

        String expected = "TST~";
        assertEquals(expected, segment.render().trim());
    }

    @Test
    void testRenderWithEmptyElementArrayThrowsException() {
        TestSegment segment = new TestSegment("TST", new String[]{});
        segment.setContext(context);

        IllegalStateException exception = assertThrows(IllegalStateException.class, segment::render);
        assertTrue(exception.getMessage().contains("cannot be null or empty"),
                "Exception message should mention 'null or empty'");
    }

    @Test
    void testRenderWithNullElementArrayThrowsException() {
        TestSegment segment = new TestSegment("TST", null);
        segment.setContext(context);

        IllegalStateException exception = assertThrows(IllegalStateException.class, segment::render);
        assertTrue(exception.getMessage().contains("cannot be null or empty"),
                "Exception message should mention 'null or empty'");
    }


    @Test
    void testRenderWithCustomDelimiters() {
        context.setElementSeparator(ElementSeparator.PIPE);
        context.setSegmentTerminator(SegmentTerminator.EXCLAMATION_POINT);

        TestSegment segment = new TestSegment("TST", new String[]{"E1", "E2", "E3"});
        segment.setContext(context);

        String expected = "TST|E1|E2|E3!";
        assertEquals(expected, segment.render().trim());
    }

    @Test
    void testRenderWithoutContextThrowsException() {
        TestSegment segment = new TestSegment("TST", new String[]{"E1", "E2", "E3"});

        assertThrows(IllegalStateException.class, segment::render);
    }
}