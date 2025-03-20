/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.HealthCoverageDateQualifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCoverageDatesTest {

    @Test
    void testEffectiveDate() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder()
                .setDtp01(HealthCoverageDateQualifier.EFFECTIVE_DATE.getCode())
                .setDtp02("D8")
                .setDtp03("20250101")
                .build();

        assertEquals("DTP", dates.getSegmentIdentifier());
        assertEquals("348", dates.getDtp01());
        assertEquals("D8", dates.getDtp02());
        assertEquals("20250101", dates.getDtp03());
    }

    @Test
    void testExpirationDate() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder()
                .setDtp01(HealthCoverageDateQualifier.EXPIRATION_DATE.getCode())
                .setDtp02("D8")
                .setDtp03("20251231")
                .build();

        assertEquals("349", dates.getDtp01());
        assertEquals("D8", dates.getDtp02());
        assertEquals("20251231", dates.getDtp03());
    }

    @Test
    void testEligibilityRange() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder()
                .setDtp01(HealthCoverageDateQualifier.ELIGIBILITY_BEGIN.getCode())
                .setDtp02("RD8")
                .setDtp03("20250101-20251231")
                .build();

        assertEquals("356", dates.getDtp01());
        assertEquals("RD8", dates.getDtp02());
        assertEquals("20250101-20251231", dates.getDtp03());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder()
                .setDtp01(HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE.getCode())
                .setDtp02("D8")
                .setDtp03("20250630")
                .build();

        String[] elements = dates.getElementValues();
        assertEquals(3, elements.length);
        assertEquals("309", elements[0]);
        assertEquals("D8", elements[1]);
        assertEquals("20250630", elements[2]);
    }
}