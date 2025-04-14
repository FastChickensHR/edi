/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop3000;

import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.HealthCoverageDateQualifier;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HealthCoverageDatesTest {
    x834Context context = new x834Context();

    @Test
    void testEffectiveDate() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder(context)
                .setDtp01(HealthCoverageDateQualifier.EFFECTIVE_DATE.getCode())
                .setDtp02(DateFormat.D8)
                .setDtp03(LocalDateTime.of(2025,1,1,0, 0))
                .build();

        assertEquals("DTP", dates.getSegmentIdentifier());
        assertEquals("348", dates.getDtp01().getCode());
        assertEquals("D8", dates.getDtp02().getFormat());
        assertEquals("20250101", dates.getDtp03());
    }

    @Test
    void testExpirationDate() throws ValidationException {
        HealthCoverageDates dates = HealthCoverageDates.builder(context)
                .setDtp01(HealthCoverageDateQualifier.EXPIRATION_DATE.getCode())
                .setDtp02(DateFormat.D8)
                .setDtp03(LocalDateTime.of(2025,12,31,0, 0))
                .build();

        assertEquals("349", dates.getDtp01().getCode());
        assertEquals("D8", dates.getDtp02().getFormat());
        assertEquals("20251231", dates.getDtp03());
    }
}