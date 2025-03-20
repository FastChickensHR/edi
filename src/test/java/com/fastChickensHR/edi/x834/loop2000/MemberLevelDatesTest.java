/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.dates.DateFormat;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberLevelDatesTest {

    private x834Context context;
    private final String dateQualifierCode = MemberDateQualifier.BIRTH.getCode();
    private final String dateFormatQualifier = DateFormat.DATE.getFormat();
    private final String dateValue = "20000101";

    @BeforeEach
    void setUp() {
        context = new x834Context();
        context.setDateFormat(DateFormat.DATE);
    }

    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDtp01(dateQualifierCode)
                .setDtp03(dateValue)
                .build();

        assertEquals("DTP", dates.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDtp01(dateQualifierCode)
                .setDtp03(dateValue)
                .build();

        String[] elements = dates.getElementValues();
        assertEquals(3, elements.length);
        assertEquals(dateQualifierCode, elements[0]);
        assertEquals(dateFormatQualifier, elements[1]);
        assertEquals(dateValue, elements[2]);
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDtp01(dateQualifierCode)
                .setDtp02(dateFormatQualifier)
                .setDtp03(dateValue)
                .build();

        assertEquals(dateQualifierCode, dates.getDateTimeQualifier());
        assertEquals(dateFormatQualifier, dates.getDateTimeFormat());
        assertEquals(dateValue, dates.getDateTimePeriod());
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDateTimeQualifier(dateQualifierCode)
                .setDateTimeFormat(dateFormatQualifier)
                .setDateTimePeriod(dateValue)
                .build();

        assertEquals(dateQualifierCode, dates.getDtp01());
        assertEquals(dateFormatQualifier, dates.getDtp02());
        assertEquals(dateValue, dates.getDtp03());
    }

    @Test
    void testSetDateQualifierWithEnum() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDateQualifier(MemberDateQualifier.BIRTH)
                .setDateTimePeriod(dateValue)
                .build();

        assertEquals(MemberDateQualifier.BIRTH.getCode(), dates.getDtp01());
        assertEquals(MemberDateQualifier.BIRTH.toString(), dates.getDateTimeQualifier());
    }

    @Test
    void testGetMemberDateQualifier() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDtp01(MemberDateQualifier.COVERAGE_BEGIN.getCode())
                .setDtp03(dateValue)
                .build();

        assertEquals(MemberDateQualifier.COVERAGE_BEGIN.getCode(), dates.getDateTimeQualifier());
    }

    @Test
    void testRender() throws ValidationException {
        MemberLevelDates segment = new MemberLevelDates.Builder(context)
                .setDateQualifier(MemberDateQualifier.BIRTH)
                .setDateTimePeriod(dateValue)
                .build();

        segment.setContext(context);

        String rendered = segment.render();
        assertEquals("DTP*007*CCYYMMDD*20000101~", rendered.trim());
    }

    @Test
    void testValidationRequiresDtp01() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            new MemberLevelDates.Builder(context)
                    .setDtp01("")  // Empty value
                    .setDtp03(dateValue)
                    .build();
        });

        assertTrue(exception.getMessage().contains("dtp01"));
    }

    @Test
    void testValidationRequiresDtp02() {
        // Create a context without default date format
        x834Context emptyContext = new x834Context();

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            new MemberLevelDates.Builder(emptyContext)
                    .setDtp01(dateQualifierCode)
                    .setDtp02("")  // Empty value
                    .setDtp03(dateValue)
                    .build();
        });

        assertTrue(exception.getMessage().contains("dtp02"));
    }

    @Test
    void testValidationRequiresDtp03() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            new MemberLevelDates.Builder(context)
                    .setDtp01(dateQualifierCode)
                    .setDtp03("")  // Empty value
                    .build();
        });

        assertTrue(exception.getMessage().contains("dtp03"));
    }

    @Test
    void testDefaultsFromContext() throws ValidationException {
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDtp01(dateQualifierCode)
                .setDtp03(dateValue)
                .build();

        assertEquals(DateFormat.DATE.getFormat(), dates.getDtp02());
    }

    @Test
    void testDifferentQualifiersWorkCorrectly() throws ValidationException {
        // Test a few different qualifiers
        MemberDateQualifier[] qualifiers = {
                MemberDateQualifier.EFFECTIVE,
                MemberDateQualifier.HIRE,
                MemberDateQualifier.ENROLLMENT,
                MemberDateQualifier.COBRA_BEGIN
        };

        for (MemberDateQualifier qualifier : qualifiers) {
            MemberLevelDates dates = new MemberLevelDates.Builder(context)
                    .setDateQualifier(qualifier)
                    .setDateTimePeriod(dateValue)
                    .build();

            assertEquals(qualifier.getCode(), dates.getDtp01());
            assertEquals(qualifier.toString(), dates.getDateTimeQualifier());
        }
    }

    @Test
    void testBuilderIsProperlyChainable() throws ValidationException {
        // Test that all builder methods can be chained
        MemberLevelDates dates = new MemberLevelDates.Builder(context)
                .setDateQualifier(MemberDateQualifier.BIRTH)
                .setDateTimeFormat(DateFormat.DATE.getFormat())
                .setDateTimePeriod("01012000")
                .build();

        assertEquals(MemberDateQualifier.BIRTH.getCode(), dates.getDtp01());
        assertEquals(DateFormat.DATE.getFormat(), dates.getDtp02());
        assertEquals("01012000", dates.getDtp03());
    }
}