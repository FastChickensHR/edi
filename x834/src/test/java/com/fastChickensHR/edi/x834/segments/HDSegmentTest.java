/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for HDSegment.
 */
class HDSegmentTest {

    private X834Context context;

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
        context = new X834Context();
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
        // Test rendering a segment with all four 220A1 HD elements populated. HD02 is Not Used,
        // so it renders as an empty slot (the "**") between HD01 and HD03.
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code (HD01)
                .setHd03("HLT") // Insurance line code (HD03)
                .setHd04("MEDICAL") // Plan coverage description (HD04)
                .setHd05("FAM") // Coverage level code (HD05)
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001**HLT*MEDICAL*FAM~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testRenderWithNullMiddleElements() throws ValidationException {
        // Test rendering with a null in the middle (HD04 skipped) — the empty slot is preserved
        // so HD05 keeps its position, and HD02 remains its always-empty slot.
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code (HD01)
                .setHd03("HLT") // Insurance line code (HD03)
                // Skipping hd04 - plan coverage description
                .setHd05("FAM") // Coverage level code (HD05)
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001**HLT**FAM~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testRenderWithNullEndElements() throws ValidationException {
        // Test rendering with a trailing null element (HD05 skipped) — trailing nulls are trimmed.
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code (HD01)
                .setHd03("HLT") // Insurance line code (HD03)
                .setHd04("MEDICAL") // Plan coverage description (HD04)
                .build();

        hdSegment.setContext(context);

        String expected = "HD*001**HLT*MEDICAL~\n";
        assertEquals(expected, hdSegment.render());
    }

    @Test
    void testGetterMethods() throws ValidationException {
        // Test that all getter methods return correct values for the four 220A1 HD elements.
        HDSegment hdSegment = TestHDSegment.builder()
                .setHd01("001") // Maintenance type code (HD01)
                .setHd03("HLT") // Insurance line code (HD03)
                .setHd04("MEDICAL") // Plan coverage description (HD04)
                .setHd05("FAM") // Coverage level code (HD05)
                .build();

        assertEquals("001", hdSegment.getMaintenanceTypeCode());
        assertEquals("001", hdSegment.getHd01());

        assertEquals("HLT", hdSegment.getInsuranceLineCode());
        assertEquals("HLT", hdSegment.getHd03());

        assertEquals("MEDICAL", hdSegment.getPlanCoverageDescription());
        assertEquals("MEDICAL", hdSegment.getHd04());

        assertEquals("FAM", hdSegment.getCoverageLevelCode());
        assertEquals("FAM", hdSegment.getHd05());
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