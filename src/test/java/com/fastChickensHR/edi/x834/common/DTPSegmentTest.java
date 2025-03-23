/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.dates.DateFormat;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DTPSegmentTest {
    x834Context context = new x834Context();

    private static class TestDTPSegment extends DTPSegment {
        public static final String TEST_QUALIFIER = "123";

        private TestDTPSegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends AbstractBuilder<Builder> {
            public Builder(x834Context context) {
                super(context);
                this.dtp01 = TEST_QUALIFIER;
            }

            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestDTPSegment build() throws ValidationException {
                if (dtp01 == null || dtp01.isEmpty()) {
                    throw new ValidationException("dtp01 (Date Time Qualifier) is required");
                }
                if (dtp03 == null || dtp03.isEmpty()) {
                    throw new ValidationException("dtp03 (Date Time Period) is required");
                }
                return new TestDTPSegment(this);
            }
        }
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp02(DateFormat.D8)
                .setDtp03(LocalDateTime.of(2025,1,1,0, 0))
                .build();

        assertEquals("DTP", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String qualifier = "456";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025,1,1,0, 0);

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
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
        String qualifier = "456";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025,1,1,0, 0);

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDateTimeQualifier());
        assertEquals(qualifier, segment.getDtp01());
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String qualifier = "789";
        DateFormat format = DateFormat.DATE;
        LocalDateTime date = LocalDateTime.of(2025,12,31,0, 0);

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDtp01());
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String qualifier = "789";
        DateFormat format = DateFormat.DATE;
        LocalDateTime date = LocalDateTime.of(2025,12,31,0, 0);

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDateTimeQualifier(qualifier)
                .setDateTimeFormat(format)
                .setDateTimePeriod(date)
                .build();

        assertEquals(qualifier, segment.getDtp01());
    }

    @Test
    void testRender() throws ValidationException {
        String qualifier = "789";
        DateFormat format = DateFormat.D8;
        LocalDateTime date = LocalDateTime.of(2025,6,15,0, 0);

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();
        segment.setContext(context);

        String rendered = segment.render();
        assertEquals("DTP*789*D8*20250615~", rendered.trim());
    }

    @Test
    void testContextBasedDefaults() throws ValidationException {
        // Create a mock context with predefined date format and document date
        x834Context context = new x834Context();
        context.setDateFormat(DateFormat.CENTURY_YEAR);
        context.setDocumentDate(LocalDateTime.of(2025, 3, 30, 0,0));

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .build();

        assertEquals("123", segment.getDtp01());
        assertEquals("CCYY", segment.getDtp02());
        assertEquals("2025", segment.getDtp03());
    }
}