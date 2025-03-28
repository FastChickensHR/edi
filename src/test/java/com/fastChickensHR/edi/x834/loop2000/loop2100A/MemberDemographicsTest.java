/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberDemographicsTest {

    @Test
    void testCreateWithRequiredFields() throws ValidationException {
        String formatQualifier = "D8";
        String birthDate = "19850315";

        MemberDemographics demographics = MemberDemographics.builder()
                .setDateTimePeriodFormatQualifier(formatQualifier)
                .setBirthDate(birthDate)
                .build();

        assertEquals(formatQualifier, demographics.getDateTimePeriodFormatQualifier());
        assertEquals(birthDate, demographics.getBirthDate());
        assertNull(demographics.getGenderCode());
    }

    @Test
    void testCreateWithGender() throws ValidationException {
        String formatQualifier = "D8";
        String birthDate = "19850315";
        String genderCode = "F";  // Female

        MemberDemographics demographics = MemberDemographics.builder()
                .setDateTimePeriodFormatQualifier(formatQualifier)
                .setBirthDate(birthDate)
                .setGenderCode(genderCode)
                .build();

        assertEquals(formatQualifier, demographics.getDateTimePeriodFormatQualifier());
        assertEquals(birthDate, demographics.getBirthDate());
        assertEquals(genderCode, demographics.getGenderCode());
    }

    @Test
    void testCreateWithAllDemographics() throws ValidationException {
        String formatQualifier = "D8";
        String birthDate = "19850315";
        String genderCode = "F";
        String maritalStatus = "S";
        String raceCode = "7";

        MemberDemographics demographics = MemberDemographics.builder()
                .setDateTimePeriodFormatQualifier(formatQualifier)
                .setBirthDate(birthDate)
                .setGenderCode(genderCode)
                .setMaritalStatusCode(maritalStatus)
                .setRaceOrEthnicityCode(raceCode)
                .build();

        assertEquals(formatQualifier, demographics.getDateTimePeriodFormatQualifier());
        assertEquals(birthDate, demographics.getBirthDate());
        assertEquals(genderCode, demographics.getGenderCode());
        assertEquals(maritalStatus, demographics.getMaritalStatusCode());
        assertEquals(raceCode, demographics.getRaceOrEthnicityCode());
    }

    @Test
    void testCreateWithDirectDMGElementSetters() throws ValidationException {
        String formatQualifier = "D8";
        String birthDate = "19850315";
        String genderCode = "M";

        MemberDemographics demographics = MemberDemographics.builder()
                .setDmg01(formatQualifier)
                .setDmg02(birthDate)
                .setDmg03(genderCode)
                .build();

        assertEquals(formatQualifier, demographics.getDateTimePeriodFormatQualifier());
        assertEquals(birthDate, demographics.getBirthDate());
        assertEquals(genderCode, demographics.getGenderCode());
    }

    @Test
    void testBuildWithoutFormatQualifierShouldFail() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> MemberDemographics.builder()
                        .setBirthDate("19850315")
                        .build()
        );

        assertTrue(exception.getMessage().contains("DMG01") ||
                exception.getMessage().contains("Format Qualifier"));
    }

    @Test
    void testBuildWithoutBirthDateShouldFail() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> MemberDemographics.builder()
                        .setDateTimePeriodFormatQualifier("D8")
                        .build()
        );

        assertTrue(exception.getMessage().contains("DMG02") ||
                exception.getMessage().contains("Birth Date"));
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        MemberDemographics demographics = MemberDemographics.builder()
                .setDateTimePeriodFormatQualifier("D8")
                .setBirthDate("19850315")
                .build();

        assertEquals("DMG", demographics.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        String formatQualifier = "D8";
        String birthDate = "19850315";
        String genderCode = "M";

        MemberDemographics demographics = MemberDemographics.builder()
                .setDateTimePeriodFormatQualifier(formatQualifier)
                .setBirthDate(birthDate)
                .setGenderCode(genderCode)
                .build();

        String[] elements = demographics.getElementValues();

        assertEquals(formatQualifier, elements[0]);
        assertEquals(birthDate, elements[1]);
        assertEquals(genderCode, elements[2]);
    }
}