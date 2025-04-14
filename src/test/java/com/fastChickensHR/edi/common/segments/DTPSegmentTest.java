/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DTPSegmentTest {
    x834Context context = new x834Context();

    @Test
    void testSegmentIdentifier() throws ValidationException {
        DTPSegment segment = new DTPSegment.Builder()
                .setDtp01("001")
                .setDtp02(DateFormat.D8)
                .setDtp03(LocalDateTime.of(2025, 1, 1, 0, 0))
                .build();

        assertEquals("DTP", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String qualifier = "001";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);

        DTPSegment segment = new DTPSegment.Builder()
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(3, elements.length);
        assertEquals(qualifier, elements[0]);
    }

    @Test
    void testDomainGetters() throws ValidationException {
        String qualifier = "001";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);

        DTPSegment segment = new DTPSegment.Builder()
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDateTimeQualifier().getCode());
        assertEquals(qualifier, segment.getDtp01().getCode());
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String qualifier = "001";
        DateFormat format = DateFormat.DATE;
        LocalDateTime date = LocalDateTime.of(2025, 12, 31, 0, 0);

        DTPSegment segment = new DTPSegment.Builder()
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDtp01().getCode());
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String qualifier = "001";
        DateFormat format = DateFormat.DATE;
        LocalDateTime date = LocalDateTime.of(2025, 12, 31, 0, 0);

        DTPSegment segment = new DTPSegment.Builder()
                .setDateTimeQualifier(qualifier)
                .setDateTimeFormat(format)
                .setDateTimePeriod(date)
                .build();

        assertEquals(qualifier, segment.getDtp01().getCode());
    }

    @Test
    void testRender() throws ValidationException {
        String qualifier = "001";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025, 6, 15, 0, 0);

        DTPSegment segment = new DTPSegment.Builder()
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();
        segment.setContext(context);

        String rendered = segment.render();
        assertEquals("DTP*001*D8*20250615~", rendered.trim());
    }
}