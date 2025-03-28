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

class MemberResidenceCityStateZipCodeTest {

    @Test
    void testValidCityStateZip() throws ValidationException {
        MemberResidenceCityStateZipCode address = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode("62701")
                .build();

        assertEquals("Springfield", address.getCityName());
        assertEquals("IL", address.getStateOrProvinceCode());
        assertEquals("62701", address.getPostalCode());
        assertEquals("N4", address.getSegmentIdentifier());
    }

    @Test
    void testCompleteAddress() throws ValidationException {
        MemberResidenceCityStateZipCode address = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode("62701")
                .setCountryCode("US")
                .setLocationQualifier("ZZ")
                .setLocationIdentifier("12345")
                .setCountrySubdivisionCode("ABC")
                .build();

        assertEquals("Springfield", address.getCityName());
        assertEquals("IL", address.getStateOrProvinceCode());
        assertEquals("62701", address.getPostalCode());
        assertEquals("US", address.getCountryCode());
        assertEquals("ZZ", address.getLocationQualifier());
        assertEquals("12345", address.getLocationIdentifier());
        assertEquals("ABC", address.getCountrySubdivisionCode());
    }

    @Test
    void testMissingCityValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setStateOrProvinceCode("IL")
                    .setPostalCode("62701")
                    .build();
        });

        assertTrue(exception.getMessage().contains("City Name"));
    }

    @Test
    void testEmptyCityValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setCityName("")
                    .setStateOrProvinceCode("IL")
                    .setPostalCode("62701")
                    .build();
        });

        assertTrue(exception.getMessage().contains("City Name"));
    }

    @Test
    void testMissingStateValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setCityName("Springfield")
                    .setPostalCode("62701")
                    .build();
        });

        assertTrue(exception.getMessage().contains("State or Province Code"));
    }

    @Test
    void testEmptyStateValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setCityName("Springfield")
                    .setStateOrProvinceCode("")
                    .setPostalCode("62701")
                    .build();
        });

        assertTrue(exception.getMessage().contains("State or Province Code"));
    }

    @Test
    void testMissingPostalCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setCityName("Springfield")
                    .setStateOrProvinceCode("IL")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Postal Code"));
    }

    @Test
    void testEmptyPostalCodeValidation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberResidenceCityStateZipCode.builder()
                    .setCityName("Springfield")
                    .setStateOrProvinceCode("IL")
                    .setPostalCode("")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Postal Code"));
    }

    @Test
    void testDirectElementAccess() throws ValidationException {
        MemberResidenceCityStateZipCode address = MemberResidenceCityStateZipCode.builder()
                .setN401("Springfield")
                .setN402("IL")
                .setN403("62701")
                .setN404("US")
                .setN405("ZZ")
                .setN406("12345")
                .setN407("ABC")
                .build();

        assertEquals("Springfield", address.getN401());
        assertEquals("IL", address.getN402());
        assertEquals("62701", address.getN403());
        assertEquals("US", address.getN404());
        assertEquals("ZZ", address.getN405());
        assertEquals("12345", address.getN406());
        assertEquals("ABC", address.getN407());

        // Test array access
        String[] elements = address.getElementValues();
        assertEquals(7, elements.length);
        assertEquals("Springfield", elements[0]);
        assertEquals("IL", elements[1]);
        assertEquals("62701", elements[2]);
        assertEquals("US", elements[3]);
        assertEquals("ZZ", elements[4]);
        assertEquals("12345", elements[5]);
        assertEquals("ABC", elements[6]);
    }

    @Test
    void testMixedSetters() throws ValidationException {
        MemberResidenceCityStateZipCode address = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setN402("IL")
                .setPostalCode("62701")
                .build();

        assertEquals("Springfield", address.getCityName());
        assertEquals("IL", address.getStateOrProvinceCode());
        assertEquals("62701", address.getPostalCode());
    }

    @Test
    void testEquivalentSetters() throws ValidationException {
        MemberResidenceCityStateZipCode address1 = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode("62701")
                .build();

        MemberResidenceCityStateZipCode address2 = MemberResidenceCityStateZipCode.builder()
                .setN401("Springfield")
                .setN402("IL")
                .setN403("62701")
                .build();

        assertEquals(address1.getN401(), address2.getN401());
        assertEquals(address1.getN402(), address2.getN402());
        assertEquals(address1.getN403(), address2.getN403());
    }

    @Test
    void testExtendedZipCode() throws ValidationException {
        MemberResidenceCityStateZipCode address = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode("62701-1234")
                .build();

        assertEquals("62701-1234", address.getPostalCode());
    }
}