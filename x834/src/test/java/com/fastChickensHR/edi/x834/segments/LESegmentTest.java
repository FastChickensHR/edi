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

class LESegmentTest {
    private final X834Context context = new X834Context();

    @Test
    void rendersLoopTrailerWithIdentifier() throws ValidationException {
        LESegment segment = LESegment.builder().setLoopIdentifierCode("2700").build();
        segment.setContext(context);

        assertEquals("LE", segment.getSegmentIdentifier());
        assertEquals("2700", segment.getLoopIdentifierCode());
        assertEquals("LE*2700~", segment.render().trim());
    }

    @Test
    void rejectsMissingLoopIdentifier() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> LESegment.builder().build());
        assertTrue(ex.getMessage().contains("LE01"));
    }
}
