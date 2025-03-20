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
                .setDtp02("D8")
                .setDtp03("20250101")
                .build();

        assertEquals("DTP", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String qualifier = "456";
        String format = "D8";
        String date = "20250101";

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(3, elements.length);
        assertEquals(qualifier, elements[0]);
        assertEquals(format, elements[1]);
        assertEquals(date, elements[2]);
    }

    @Test
    void testDomainGetters() throws ValidationException {
        String qualifier = "456";
        String format = "D8";
        String date = "20250101";

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDateTimeQualifier());
        assertEquals(format, segment.getDateTimeFormat());
        assertEquals(date, segment.getDateTimePeriod());

        assertEquals(qualifier, segment.getDtp01());
        assertEquals(format, segment.getDtp02());
        assertEquals(date, segment.getDtp03());
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String qualifier = "789";
        String format = "RD8";
        String date = "20251231";

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDtp01(qualifier)
                .setDtp02(format)
                .setDtp03(date)
                .build();

        assertEquals(qualifier, segment.getDtp01());
        assertEquals(format, segment.getDtp02());
        assertEquals(date, segment.getDtp03());
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String qualifier = "789";
        String format = "RD8";
        String date = "20251231";

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .setDateTimeQualifier(qualifier)
                .setDateTimeFormat(format)
                .setDateTimePeriod(date)
                .build();

        assertEquals(qualifier, segment.getDtp01());
        assertEquals(format, segment.getDtp02());
        assertEquals(date, segment.getDtp03());
    }

    @Test
    void testRender() throws ValidationException {
        String qualifier = "789";
        String format = "D8";
        String date = "20250615";

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
        context.setDocumentDate(LocalDate.of(2025, 3, 30));

        TestDTPSegment segment = new TestDTPSegment.Builder(context)
                .build();

        assertEquals("123", segment.getDtp01()); // Default from TestDtpSegment
        assertEquals("CCYY", segment.getDtp02()); // From context date format
        assertEquals("2025", segment.getDtp03()); // From context document date
    }

    @Test
    void testValidationRequiresDtp01AndDtp03() {
        // Test missing dtp01
        ValidationException exception1 = assertThrows(ValidationException.class, () -> {
            new TestDTPSegment.Builder(context)
                    .setDtp01("") // Empty value
                    .setDtp02("D8")
                    .setDtp03("20250101")
                    .build();
        });
        assertTrue(exception1.getMessage().contains("dtp01"));

        // Test missing dtp03
        ValidationException exception2 = assertThrows(ValidationException.class, () -> {
            new TestDTPSegment.Builder(context)
                    .setDtp01("123")
                    .setDtp02("D8")
                    .setDtp03("") // Empty value
                    .build();
        });
        assertTrue(exception2.getMessage().contains("dtp03"));
    }
}