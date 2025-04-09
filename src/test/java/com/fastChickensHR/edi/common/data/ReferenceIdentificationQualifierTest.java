/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReferenceIdentificationQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals("00", ReferenceIdentificationQualifier.CONTRACTING_DISTRICT_NUMBER.getCode());
        assertEquals("0A", ReferenceIdentificationQualifier.SUPERVISORY_APPRAISER_CERTIFICATION_NUMBER.getCode());
        assertEquals("0F", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER.getCode());
        assertEquals("1W", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER.getCode());
        assertEquals("2Y", ReferenceIdentificationQualifier.WAGE_DETERMINATION.getCode());

        assertEquals("Contracting District Number",
                ReferenceIdentificationQualifier.CONTRACTING_DISTRICT_NUMBER.getDescription());
        assertEquals("Member Identification Number",
                ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER.getDescription());
        assertEquals("Wage Determination",
                ReferenceIdentificationQualifier.WAGE_DETERMINATION.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (ReferenceIdentificationQualifier qualifier : ReferenceIdentificationQualifier.values()) {
            assertNotNull(qualifier.getCode(), "Code should not be null for " + qualifier.name());
            assertNotNull(qualifier.getDescription(), "Description should not be null for " + qualifier.name());
            assertFalse(qualifier.getCode().isEmpty(), "Code should not be empty for " + qualifier.name());
            assertFalse(qualifier.getDescription().isEmpty(), "Description should not be empty for " + qualifier.name());
        }

        assertEquals("0F", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER.toString());
        assertEquals("1W", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER.toString());
    }

    @Test
    void testFromString() {
        assertEquals(ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER,
                ReferenceIdentificationQualifier.fromString("0F"));
        assertEquals(ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER,
                ReferenceIdentificationQualifier.fromString("1W"));

        assertEquals(ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER,
                ReferenceIdentificationQualifier.fromString("subscriber_number"));
        assertEquals(ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER,
                ReferenceIdentificationQualifier.fromString("member_identification_number"));

        assertEquals(ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER,
                ReferenceIdentificationQualifier.fromString("member id"));
        assertEquals(ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER,
                ReferenceIdentificationQualifier.fromString("subscriber"));

        assertThrows(IllegalArgumentException.class,
                () -> ReferenceIdentificationQualifier.fromString("invalid_qualifier_code"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, ReferenceIdentificationQualifier expected) throws Exception {
        Field lookupField = ReferenceIdentificationQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<ReferenceIdentificationQualifier> lookup =
                (EdiEnumLookup<ReferenceIdentificationQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("00", ReferenceIdentificationQualifier.CONTRACTING_DISTRICT_NUMBER),
                Arguments.of("0A", ReferenceIdentificationQualifier.SUPERVISORY_APPRAISER_CERTIFICATION_NUMBER),
                Arguments.of("0F", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("1W", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),

                Arguments.of("SUBSCRIBER_NUMBER", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("MEMBER_IDENTIFICATION_NUMBER", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),

                Arguments.of("member id", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),
                Arguments.of("subscriber", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("subscriber id", ReferenceIdentificationQualifier.SUBSCRIBER_NUMBER),
                Arguments.of("member number", ReferenceIdentificationQualifier.MEMBER_IDENTIFICATION_NUMBER),
                Arguments.of("tracking", ReferenceIdentificationQualifier.TRACKING_NUMBER),
                Arguments.of("state license", ReferenceIdentificationQualifier.STATE_LICENSE_NUMBER),
                Arguments.of("medicare", ReferenceIdentificationQualifier.MEDICARE_PROVIDER_NUMBER),
                Arguments.of("medicaid", ReferenceIdentificationQualifier.MEDICAID_PROVIDER_NUMBER),
                Arguments.of("policy number", ReferenceIdentificationQualifier.GROUP_OR_POLICY_NUMBER),
                Arguments.of("mortgage id", ReferenceIdentificationQualifier.MORTGAGE_IDENTIFICATION_NUMBER),
                Arguments.of("tracking id", ReferenceIdentificationQualifier.TRACKING_NUMBER),
                Arguments.of("claim number", ReferenceIdentificationQualifier.PAYERS_CLAIM_NUMBER)
        );
    }

    @Test
    void testEnumCount() {
        assertEquals(81, ReferenceIdentificationQualifier.values().length);
    }

    @Test
    void testUniqueCodes() {
        long uniqueCodes = Arrays.stream(ReferenceIdentificationQualifier.values())
                .map(ReferenceIdentificationQualifier::getCode)
                .distinct()
                .count();

        assertEquals(ReferenceIdentificationQualifier.values().length, uniqueCodes,
                "Each enum value should have a unique code");
    }
}