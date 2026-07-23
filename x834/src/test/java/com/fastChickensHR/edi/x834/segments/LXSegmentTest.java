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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LXSegmentTest {
    private final X834Context context = new X834Context();

    @Test
    void rendersAssignedNumber() throws ValidationException {
        LXSegment segment = LXSegment.builder().setAssignedNumber(1).build();
        segment.setContext(context);

        assertEquals("LX", segment.getSegmentIdentifier());
        assertEquals(1, segment.getAssignedNumber());
        assertEquals("LX*1~", segment.render().trim());
    }

    @Test
    void rejectsNonPositiveAssignedNumber() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> LXSegment.builder().setAssignedNumber(0).build());
        assertTrue(ex.getMessage().contains("LX01"));
    }
}
