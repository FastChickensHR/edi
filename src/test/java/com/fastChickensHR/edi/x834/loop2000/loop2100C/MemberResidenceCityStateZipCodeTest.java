/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberResidenceCityStateZipCodeTest {

    @Test
    void testCreateWithRequiredFields() throws ValidationException {
        String cityName = "Springfield";
        String stateCode = "IL";
        String postalCode = "62701";

        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateCode)
                .setPostalCode(postalCode)
                .build();

        assertEquals(cityName, location.getCityName());
        assertEquals(stateCode, location.getStateOrProvinceCode());
        assertEquals(postalCode, location.getPostalCode());

        // Check direct element access too
        assertEquals(cityName, location.getN401());
        assertEquals(stateCode, location.getN402());
        assertEquals(postalCode, location.getN403());
    }

    @Test
    void testCreateWithAllFields() throws ValidationException {
        String cityName = "Springfield";
        String stateCode = "IL";
        String postalCode = "62701";
        String countryCode = "US";
        String locationQualifier = "ZZ";
        String locationIdentifier = "12345";
        String countrySubdivision = "CENTRAL";

        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .setLocationQualifier(locationQualifier)
                .setLocationIdentifier(locationIdentifier)
                .setCountrySubdivisionCode(countrySubdivision)
                .build();

        assertEquals(cityName, location.getCityName());
        assertEquals(stateCode, location.getStateOrProvinceCode());
        assertEquals(postalCode, location.getPostalCode());
        assertEquals(countryCode, location.getCountryCode());
        assertEquals(locationQualifier, location.getLocationQualifier());
        assertEquals(locationIdentifier, location.getLocationIdentifier());
        assertEquals(countrySubdivision, location.getCountrySubdivisionCode());
    }

    @Test
    void testCreateWithDirectN4ElementSetters() throws ValidationException {
        String cityName = "Springfield";
        String stateCode = "IL";
        String postalCode = "62701";

        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setN401(cityName)
                .setN402(stateCode)
                .setN403(postalCode)
                .build();

        assertEquals(cityName, location.getCityName());
        assertEquals(stateCode, location.getStateOrProvinceCode());
        assertEquals(postalCode, location.getPostalCode());
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode("62701")
                .build();

        assertEquals("N4", location.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        String cityName = "Springfield";
        String stateCode = "IL";
        String postalCode = "62701";
        String countryCode = "US";

        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .build();

        String[] elements = location.getElementValues();

        assertEquals(cityName, elements[0]);
        assertEquals(stateCode, elements[1]);
        assertEquals(postalCode, elements[2]);
        assertEquals(countryCode, elements[3]);
    }

    @Test
    void testPostalCodeFormatWithHyphen() throws ValidationException {
        String postalCode = "62701-1234";

        MemberResidenceCityStateZipCode location = MemberResidenceCityStateZipCode.builder()
                .setCityName("Springfield")
                .setStateOrProvinceCode("IL")
                .setPostalCode(postalCode)
                .build();

        assertEquals(postalCode, location.getPostalCode());
    }
}