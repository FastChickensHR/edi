/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileEffectiveDateTest {
    x834Context context = new x834Context();
     LocalDateTime localDateTime = LocalDateTime.of(2025, 3,13,0,0,0);

    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .setDateTimePeriod(localDateTime)
                .build();
        segment.setContext(context);

        assertEquals("DTP", segment.getSegmentIdentifier(), "Expected segment identifier should be 'DTP'");
        assertEquals("DTP*007*CCYYMMDD*20250313~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    public void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        String dateTimeQualifier = "1";
        DateFormat dateTimeFormat = DateFormat.DATE;
        LocalDateTime dateTimePeriod = context.getDocumentDate();
        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .setDtp01(dateTimeQualifier)
                .setDtp02(dateTimeFormat)
                .setDtp03(dateTimePeriod)
                .build();

        assertEquals(dateTimeQualifier, segment.getDateTimeQualifier(), "DateTimeQualifier should match DTP01");
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    public void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        String dateTimeQualifier = "1";
        DateFormat dateTimeFormat = DateFormat.DATE;
        LocalDateTime dateTimePeriod = context.getDocumentDate();
        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .setDateTimeQualifier(dateTimeQualifier)
                .setDateTimeFormat(dateTimeFormat)
                .setDateTimePeriod(dateTimePeriod)
                .build();

        assertEquals(dateTimeQualifier, segment.getDtp01(), "DateTimeQualifier should match DTP01");
    }
}