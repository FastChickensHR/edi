/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.common.segments.HDSegment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCoverageTest {

    @Test
    void testValidHealthCoverage() throws ValidationException {
        HDSegment healthCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("HMO")
                .build();

        assertEquals("021", healthCoverage.getMaintenanceTypeCode());
        assertEquals("HMO", healthCoverage.getInsuranceLineCode());
        assertEquals("HD", healthCoverage.getSegmentIdentifier());
    }

    @Test
    void testCompleteHealthCoverage() throws ValidationException {
        HDSegment healthCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd02("18")
                .setHd03("HMO")
                .setHd04("Premium Health Plan")
                .setHd05("FAM")
                .setHd06("MA")
                .setHd07("1")
                .setHd08("1")
                .setHd09("FT")
                .setHd10("F")
                .setHd11("N")
                .setHd12("D8")
                .setHd13("19800101")
                .build();

        assertEquals("021", healthCoverage.getMaintenanceTypeCode());
        assertEquals("18", healthCoverage.getMaintenanceReasonCode());
        assertEquals("HMO", healthCoverage.getInsuranceLineCode());
        assertEquals("Premium Health Plan", healthCoverage.getPlanCoverageDescription());
        assertEquals("FAM", healthCoverage.getCoverageLevelCode());
        assertEquals("MA", healthCoverage.getMedicarePlanCode());
        assertEquals("1", healthCoverage.getMedicareEligibilityReasonCode());
        assertEquals("1", healthCoverage.getCobraQualifyingEventCode());
        assertEquals("FT", healthCoverage.getEmploymentStatusCode());
        assertEquals("F", healthCoverage.getStudentStatusCode());
        assertEquals("N", healthCoverage.getHandicapIndicator());
        assertEquals("D8", healthCoverage.getDateQualifier());
        assertEquals("19800101", healthCoverage.getBirthDate());
    }

    @Test
    void testMissingMaintenanceTypeCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            HealthCoverage.builder()
                    .setHd03("HMO")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Maintenance Type Code"));
    }

    @Test
    void testEmptyMaintenanceTypeCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            HealthCoverage.builder()
                    .setHd01("")
                    .setHd03("HMO")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Maintenance Type Code"));
    }

    @Test
    void testMissingInsuranceLineCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            HealthCoverage.builder()
                    .setHd01("021")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Insurance Line Code"));
    }

    @Test
    void testEmptyInsuranceLineCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            HealthCoverage.builder()
                    .setHd01("021")
                    .setHd03("")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Insurance Line Code"));
    }

    @Test
    void testElementValues() throws ValidationException {
        HDSegment healthCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("HMO")
                .setHd05("FAM")
                .build();

        String[] elements = healthCoverage.getElementValues();
        assertEquals("021", elements[0]);
        assertNull(elements[1]);
        assertEquals("HMO", elements[2]);
        assertNull(elements[3]);
        assertEquals("FAM", elements[4]);
    }

    @Test
    void testSemanticAccessors() throws ValidationException {
        HealthCoverage healthCoverage = HealthCoverage.builder()
                .setMaintenanceTypeCode("021")
                .setInsuranceLineCode("HMO")
                .setCoverageLevelCode("FAM")
                .build();

        assertEquals("021", healthCoverage.getHd01());
        assertEquals("HMO", healthCoverage.getHd03());
        assertEquals("FAM", healthCoverage.getHd05());
    }

    @Test
    void testMixedAccessorsSameResult() throws ValidationException {
        HDSegment healthCoverage1 = HealthCoverage.builder()
                .setMaintenanceTypeCode("021")
                .setHd03("HMO")
                .setCoverageLevelCode("FAM")
                .build();

        HDSegment healthCoverage2 = HealthCoverage.builder()
                .setHd01("021")
                .setInsuranceLineCode("HMO")
                .setHd05("FAM")
                .build();

        assertEquals(healthCoverage1.getMaintenanceTypeCode(), healthCoverage2.getMaintenanceTypeCode());
        assertEquals(healthCoverage1.getInsuranceLineCode(), healthCoverage2.getInsuranceLineCode());
        assertEquals(healthCoverage1.getCoverageLevelCode(), healthCoverage2.getCoverageLevelCode());
    }

    @Test
    void testCommonMaintenanceTypeCodes() throws ValidationException {
        HDSegment additionCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("HMO")
                .build();
        assertEquals("021", additionCoverage.getMaintenanceTypeCode());

        HDSegment terminationCoverage = HealthCoverage.builder()
                .setHd01("024")
                .setHd03("HMO")
                .build();
        assertEquals("024", terminationCoverage.getMaintenanceTypeCode());

        HDSegment reinstatementCoverage = HealthCoverage.builder()
                .setHd01("025")
                .setHd03("HMO")
                .build();
        assertEquals("025", reinstatementCoverage.getMaintenanceTypeCode());
    }

    @Test
    void testCommonInsuranceLineCodes() throws ValidationException {
        HDSegment hmoCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("HMO")
                .build();
        assertEquals("HMO", hmoCoverage.getInsuranceLineCode());

        HDSegment ppoCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("PPO")
                .build();
        assertEquals("PPO", ppoCoverage.getInsuranceLineCode());

        HDSegment dentalCoverage = HealthCoverage.builder()
                .setHd01("021")
                .setHd03("DEN")
                .build();
        assertEquals("DEN", dentalCoverage.getInsuranceLineCode());
    }
}