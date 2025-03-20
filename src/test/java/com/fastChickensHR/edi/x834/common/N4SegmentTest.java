/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class N4SegmentTest {

    private x834Context context;
    private final String cityName = "SEATTLE";
    private final String stateOrProvinceCode = "WA";
    private final String postalCode = "98101";
    private final String countryCode = "US";
    private final String locationQualifier = "CY";
    private final String locationIdentifier = "SEA";
    private final String countrySubdivisionCode = "KG";

    // Test implementation of N4Segment for testing purposes
    private static class TestN4Segment extends N4Segment {
        protected TestN4Segment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestN4Segment build() throws ValidationException {
                validateRequiredFields();
                return new TestN4Segment(this);
            }

            private void validateRequiredFields() throws ValidationException {
                if (n401 == null || n401.isEmpty()) {
                    throw new ValidationException("City Name (N401) is required");
                }
            }
        }
    }

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .build();
        assertEquals("N4", segment.getSegmentIdentifier());
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        TestN4Segment.Builder builder = TestN4Segment.builder();

        // Setting using spec field names
        builder.n401 = cityName;
        builder.n402 = stateOrProvinceCode;
        builder.n403 = postalCode;
        builder.n404 = countryCode;
        builder.n405 = locationQualifier;
        builder.n406 = locationIdentifier;
        builder.n407 = countrySubdivisionCode;

        TestN4Segment segment = builder.build();

        // Getting using domain field names
        assertEquals(cityName, segment.getCityName());
        assertEquals(stateOrProvinceCode, segment.getStateOrProvinceCode());
        assertEquals(postalCode, segment.getPostalCode());
        assertEquals(countryCode, segment.getCountryCode());
        assertEquals(locationQualifier, segment.getLocationQualifier());
        assertEquals(locationIdentifier, segment.getLocationIdentifier());
        assertEquals(countrySubdivisionCode, segment.getCountrySubdivisionCode());
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .setLocationQualifier(locationQualifier)
                .setLocationIdentifier(locationIdentifier)
                .setCountrySubdivisionCode(countrySubdivisionCode)
                .build();

        // Get the element values array which uses the spec field names internally
        String[] elements = segment.getElementValues();

        assertEquals(cityName, elements[0]); // n401
        assertEquals(stateOrProvinceCode, elements[1]); // n402
        assertEquals(postalCode, elements[2]); // n403
        assertEquals(countryCode, elements[3]); // n404
        assertEquals(locationQualifier, elements[4]); // n405
        assertEquals(locationIdentifier, elements[5]); // n406
        assertEquals(countrySubdivisionCode, elements[6]); // n407
    }

    @Test
    void testDirectAccessToSpecFields() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .setLocationQualifier(locationQualifier)
                .setLocationIdentifier(locationIdentifier)
                .setCountrySubdivisionCode(countrySubdivisionCode)
                .build();

        assertEquals(cityName, segment.getN401());
        assertEquals(stateOrProvinceCode, segment.getN402());
        assertEquals(postalCode, segment.getN403());
        assertEquals(countryCode, segment.getN404());
        assertEquals(locationQualifier, segment.getN405());
        assertEquals(locationIdentifier, segment.getN406());
        assertEquals(countrySubdivisionCode, segment.getN407());
    }

    @Test
    void testRenderWithAllFields() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .setLocationQualifier(locationQualifier)
                .setLocationIdentifier(locationIdentifier)
                .setCountrySubdivisionCode(countrySubdivisionCode)
                .build();

        segment.setContext(context);

        String rendered = segment.render().trim();
        String expected = "N4*" + cityName + "*" + stateOrProvinceCode + "*" + postalCode + "*" +
                countryCode + "*" + locationQualifier + "*" + locationIdentifier + "*" +
                countrySubdivisionCode + "~";

        assertEquals(expected, rendered);
    }

    @Test
    void testRenderWithMinimalRequiredFields() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .build();

        segment.setContext(context);

        String rendered = segment.render().trim();
        String expected = "N4*" + cityName + "~";

        assertEquals(expected, rendered);
    }

    @Test
    void testRenderWithSomeFields() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .build();

        segment.setContext(context);

        String rendered = segment.render().trim();
        String expected = "N4*" + cityName + "*" + stateOrProvinceCode + "*" + postalCode + "~";

        assertEquals(expected, rendered);
    }

    @Test
    void testValidationRequiresCityName() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            TestN4Segment.Builder builder = TestN4Segment.builder();
            builder.n401 = null;
            builder.build();
        });

        assertTrue(exception.getMessage().contains("City Name"));
    }

    @Test
    void testBuilderIsProperlyChainable() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setStateOrProvinceCode(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .setCountryCode(countryCode)
                .setLocationQualifier(locationQualifier)
                .setLocationIdentifier(locationIdentifier)
                .setCountrySubdivisionCode(countrySubdivisionCode)
                .build();

        assertNotNull(segment);
        assertEquals(cityName, segment.getCityName());
        assertEquals(stateOrProvinceCode, segment.getStateOrProvinceCode());
        assertEquals(postalCode, segment.getPostalCode());
        assertEquals(countryCode, segment.getCountryCode());
        assertEquals(locationQualifier, segment.getLocationQualifier());
        assertEquals(locationIdentifier, segment.getLocationIdentifier());
        assertEquals(countrySubdivisionCode, segment.getCountrySubdivisionCode());
    }

    @Test
    void testBuilderMethodsUsingSpecNames() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setN401(cityName)
                .setN402(stateOrProvinceCode)
                .setN403(postalCode)
                .setN404(countryCode)
                .setN405(locationQualifier)
                .setN406(locationIdentifier)
                .setN407(countrySubdivisionCode)
                .build();

        assertNotNull(segment);
        assertEquals(cityName, segment.getN401());
        assertEquals(stateOrProvinceCode, segment.getN402());
        assertEquals(postalCode, segment.getN403());
        assertEquals(countryCode, segment.getN404());
        assertEquals(locationQualifier, segment.getN405());
        assertEquals(locationIdentifier, segment.getN406());
        assertEquals(countrySubdivisionCode, segment.getN407());
    }

    @Test
    void testMixedSpecAndDomainSetters() throws ValidationException {
        TestN4Segment segment = TestN4Segment.builder()
                .setCityName(cityName)
                .setN402(stateOrProvinceCode)
                .setPostalCode(postalCode)
                .setN404(countryCode)
                .build();

        assertNotNull(segment);
        assertEquals(cityName, segment.getCityName());
        assertEquals(stateOrProvinceCode, segment.getStateOrProvinceCode());
        assertEquals(postalCode, segment.getPostalCode());
        assertEquals(countryCode, segment.getCountryCode());
    }
}