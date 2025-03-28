/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for HDSegment.
 */
class HDSegmentTest {

    private x834Context context;

    // Concrete implementation of HDSegment for testing
    private static class TestHDSegment extends HDSegment {
        protected TestHDSegment(TestBuilder builder) throws ValidationException {
            super(builder);
        }

        public static TestBuilder builder() {
            return new TestBuilder();
        }

        public static class TestBuilder extends AbstractBuilder<TestBuilder> {
            @Override
            protected TestBuilder self() {
                return this;
            }

            public TestHDSegment build() throws ValidationException {
                return new TestHDSegment(this);
            }
        }
    }

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testSuccessfulBuild() throws ValidationException {
        // Test that a minimal HDSegment can be built with required fields
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd03("HLT") // Insurance line code
                .build();

        assertNotNull(hdSegment);
        assertEquals("001", hdSegment.getMaintenanceTypeCode());
        assertEquals("HLT", hdSegment.getInsuranceLineCode());
    }

    @Test
    void testMissingMaintenanceTypeCode() {
        // Test that validation fails when maintenance type code is missing
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            TestHDSegment.builder()
                    .setHd03("HLT") // Insurance line code
                    .build();
        });

        assertTrue(exception.getMessage().contains("Maintenance Type Code"),
                "Validation exception should mention Maintenance Type Code");
    }

    @Test
    void testMissingInsuranceLineCode() {
        // Test that validation fails when insurance line code is missing
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            TestHDSegment.builder()
                    .setHd01("001") // Maintenance type code
                    .build();
        });

        assertTrue(exception.getMessage().contains("Insurance Line Code"),
                "Validation exception should mention Insurance Line Code");
    }

    @Test
    void testRenderMinimalSegment() throws ValidationException {
        // Test rendering a minimal segment with only required fields
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd03("HLT") // Insurance line code
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001**HLT~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testRenderFullSegment() throws ValidationException {
        // Test rendering a segment with all fields populated
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd02("024") // Maintenance reason code
                .setHd03("HLT") // Insurance line code
                .setHd04("MEDICAL") // Plan coverage description
                .setHd05("FAM") // Coverage level code
                .setHd06("A") // Medicare plan code
                .setHd07("1") // Medicare eligibility reason code
                .setHd08("1") // COBRA qualifying event code
                .setHd09("FT") // Employment status code
                .setHd10("F") // Student status code
                .setHd11("N") // Handicap indicator
                .setHd12("D8") // Date qualifier
                .setHd13("19800101") // Birth date
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001*024*HLT*MEDICAL*FAM*A*1*1*FT*F*N*D8*19800101~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testRenderWithNullMiddleElements() throws ValidationException {
        // Test rendering with some null elements in the middle
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd02("024") // Maintenance reason code
                .setHd03("HLT") // Insurance line code
                .setHd05("FAM") // Coverage level code
                // Skipping hd04 - plan coverage description
                // Skipping hd06 - medicare plan code
                .setHd11("N") // Handicap indicator
                .setHd13("19800101") // Birth date
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001*024*HLT**FAM******N**19800101~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testRenderWithNullEndElements() throws ValidationException {
        // Test rendering with trailing null elements
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd02("024") // Maintenance reason code
                .setHd03("HLT") // Insurance line code
                .setHd05("FAM") // Coverage level code
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001*024*HLT**FAM~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testGetterMethods() throws ValidationException {
        // Test that all getter methods return correct values
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd02("024") // Maintenance reason code
                .setHd03("HLT") // Insurance line code
                .setHd04("MEDICAL") // Plan coverage description
                .setHd05("FAM") // Coverage level code
                .setHd06("A") // Medicare plan code
                .setHd07("1") // Medicare eligibility reason code
                .setHd08("1") // COBRA qualifying event code
                .setHd09("FT") // Employment status code
                .setHd10("F") // Student status code
                .setHd11("N") // Handicap indicator
                .setHd12("D8") // Date qualifier
                .setHd13("19800101") // Birth date
                .build();

        assertEquals("001", hdSegment.getMaintenanceTypeCode());
        assertEquals("001", hdSegment.getHd01());

        assertEquals("024", hdSegment.getMaintenanceReasonCode());
        assertEquals("024", hdSegment.getHd02());

        assertEquals("HLT", hdSegment.getInsuranceLineCode());
        assertEquals("HLT", hdSegment.getHd03());

        assertEquals("MEDICAL", hdSegment.getPlanCoverageDescription());
        assertEquals("MEDICAL", hdSegment.getHd04());

        assertEquals("FAM", hdSegment.getCoverageLevelCode());
        assertEquals("FAM", hdSegment.getHd05());

        assertEquals("A", hdSegment.getMedicarePlanCode());
        assertEquals("A", hdSegment.getHd06());

        assertEquals("1", hdSegment.getMedicareEligibilityReasonCode());
        assertEquals("1", hdSegment.getHd07());

        assertEquals("1", hdSegment.getCobraQualifyingEventCode());
        assertEquals("1", hdSegment.getHd08());

        assertEquals("FT", hdSegment.getEmploymentStatusCode());
        assertEquals("FT", hdSegment.getHd09());

        assertEquals("F", hdSegment.getStudentStatusCode());
        assertEquals("F", hdSegment.getHd10());

        assertEquals("N", hdSegment.getHandicapIndicator());
        assertEquals("N", hdSegment.getHd11());

        assertEquals("D8", hdSegment.getDateQualifier());
        assertEquals("D8", hdSegment.getHd12());

        assertEquals("19800101", hdSegment.getBirthDate());
        assertEquals("19800101", hdSegment.getHd13());
    }

    @Test
    void testSegmentIdConstant() {
        assertEquals("HD", HDSegment.SEGMENT_ID);
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd03("HLT") // Insurance line code
                .build();

        assertEquals("HD", hdSegment.getSegmentIdentifier());
    }

    @Test
    void testNullContext() throws ValidationException {
        // Test that rendering fails when context is null
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code
                .setHd03("HLT") // Insurance line code
                .build();

        assertThrows(IllegalStateException.class, hdSegment::render);
    }
}