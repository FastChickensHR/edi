/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DMGSegmentTest {

    /**
     * Concrete implementation of DMGSegment for testing purposes.
     */
    private static class TestDMGSegment extends DMGSegment {
        private TestDMGSegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        /**
         * Builder for TestDMGSegment.
         */
        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestDMGSegment build() throws ValidationException {
                return new TestDMGSegment(this);
            }
        }

        /**
         * Creates a new Builder instance.
         *
         * @return a new Builder
         */
        public static Builder builder() {
            return new Builder();
        }
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder().build();
        assertEquals("DMG", segment.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder()
                .setDmg01("D8")
                .setDmg02("19900101")
                .setDmg03("F")
                .setDmg04("S")
                .setDmg05("7")
                .setDmg06("1")
                .setDmg07("US")
                .setDmg08("A")
                .setDmg09("2")
                .setDmg10("AAA")
                .setDmg11("BBB")
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(11, elements.length);
        assertEquals("D8", elements[0]);
        assertEquals("19900101", elements[1]);
        assertEquals("F", elements[2]);
        assertEquals("S", elements[3]);
        assertEquals("7", elements[4]);
        assertEquals("1", elements[5]);
        assertEquals("US", elements[6]);
        assertEquals("A", elements[7]);
        assertEquals("2", elements[8]);
        assertEquals("AAA", elements[9]);
        assertEquals("BBB", elements[10]);
    }

    @Test
    void testGetters() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder()
                .setDateTimePeriodFormatQualifier("D8")
                .setBirthDate("19900101")
                .setGenderCode("F")
                .setMaritalStatusCode("S")
                .setRaceOrEthnicityCode("7")
                .setCitizenshipStatusCode("1")
                .setCountryCode("US")
                .setBasisOfVerificationCode("A")
                .setQuantity("2")
                .setCodeListQualifierCode("AAA")
                .setIndustryCode("BBB")
                .build();

        assertEquals("D8", segment.getDateTimePeriodFormatQualifier());
        assertEquals("D8", segment.getDmg01());
        assertEquals("19900101", segment.getBirthDate());
        assertEquals("19900101", segment.getDmg02());
        assertEquals("F", segment.getGenderCode());
        assertEquals("F", segment.getDmg03());
        assertEquals("S", segment.getMaritalStatusCode());
        assertEquals("S", segment.getDmg04());
        assertEquals("7", segment.getRaceOrEthnicityCode());
        assertEquals("7", segment.getDmg05());
        assertEquals("1", segment.getCitizenshipStatusCode());
        assertEquals("1", segment.getDmg06());
        assertEquals("US", segment.getCountryCode());
        assertEquals("US", segment.getDmg07());
        assertEquals("A", segment.getBasisOfVerificationCode());
        assertEquals("A", segment.getDmg08());
        assertEquals("2", segment.getQuantity());
        assertEquals("2", segment.getDmg09());
        assertEquals("AAA", segment.getCodeListQualifierCode());
        assertEquals("AAA", segment.getDmg10());
        assertEquals("BBB", segment.getIndustryCode());
        assertEquals("BBB", segment.getDmg11());
    }

    @Test
    void testBuilderWithNullValues() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder()
                .setDmg01("D8")
                .setDmg02("19900101")
                .build();

        assertEquals("D8", segment.getDmg01());
        assertEquals("19900101", segment.getDmg02());
        assertNull(segment.getDmg03());
        assertNull(segment.getDmg04());
        assertNull(segment.getDmg05());
        assertNull(segment.getDmg06());
        assertNull(segment.getDmg07());
        assertNull(segment.getDmg08());
        assertNull(segment.getDmg09());
        assertNull(segment.getDmg10());
        assertNull(segment.getDmg11());
    }

    @Test
    void testBuilderChaining() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder()
                .setDmg01("D8")
                .setDmg02("19900101")
                .setDmg03("M")
                .build();

        assertEquals("D8", segment.getDmg01());
        assertEquals("19900101", segment.getDmg02());
        assertEquals("M", segment.getDmg03());
    }

    @Test
    void testNamedSetters() throws ValidationException {
        // Test that the named setters correctly map to the generic dmg## setters
        TestDMGSegment.Builder builder = TestDMGSegment.builder();

        // Set values using named setters
        builder.setDateTimePeriodFormatQualifier("D8")
                .setBirthDate("19900101")
                .setGenderCode("M")
                .setMaritalStatusCode("S")
                .setRaceOrEthnicityCode("7")
                .setCitizenshipStatusCode("1")
                .setCountryCode("US")
                .setBasisOfVerificationCode("A")
                .setQuantity("2")
                .setCodeListQualifierCode("AAA")
                .setIndustryCode("BBB");

        // Build the segment
        DMGSegment segment = builder.build();

        // Verify all values were correctly set
        assertEquals("D8", segment.getDmg01());
        assertEquals("19900101", segment.getDmg02());
        assertEquals("M", segment.getDmg03());
        assertEquals("S", segment.getDmg04());
        assertEquals("7", segment.getDmg05());
        assertEquals("1", segment.getDmg06());
        assertEquals("US", segment.getDmg07());
        assertEquals("A", segment.getDmg08());
        assertEquals("2", segment.getDmg09());
        assertEquals("AAA", segment.getDmg10());
        assertEquals("BBB", segment.getDmg11());
    }

    @Test
    void testEmptySegment() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder().build();

        String[] values = segment.getElementValues();
        assertEquals(11, values.length);

        // All values should be null
        for (String value : values) {
            assertNull(value);
        }
    }

    @Test
    void testMixedNamedAndDirectSetters() throws ValidationException {
        DMGSegment segment = TestDMGSegment.builder()
                .setDateTimePeriodFormatQualifier("D8")  // Named setter
                .setDmg02("19900101")                    // Direct setter
                .setGenderCode("F")                      // Named setter
                .build();

        assertEquals("D8", segment.getDateTimePeriodFormatQualifier());
        assertEquals("19900101", segment.getBirthDate());
        assertEquals("F", segment.getGenderCode());
    }
}