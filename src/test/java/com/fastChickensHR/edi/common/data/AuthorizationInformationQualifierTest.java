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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationInformationQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(7, AuthorizationInformationQualifier.values().length);
        assertEquals("00", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION.getCode());
        assertEquals("01", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID.getCode());
        assertEquals("02", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID.getCode());
        assertEquals("03", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID.getCode());
        assertEquals("04", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID.getCode());
        assertEquals("05", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID.getCode());
        assertEquals("06", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID.getCode());
    }

    @Test
    void testEnumProperties() {
        assertEquals("No Authorization", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION.getDescription());
        assertEquals("UCS Communications ID", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID.getDescription());
        assertEquals("EDX Communications ID", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID.getDescription());
        assertEquals("Additional Data Identification", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID.getDescription());
        assertEquals("Rail Communications ID", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID.getDescription());
        assertEquals("Department of Defense (DoD) Communication Identifier", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID.getDescription());
        assertEquals("United States Federal Government Communication Identifier", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION, AuthorizationInformationQualifier.fromString("00"));
        assertEquals(AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID, AuthorizationInformationQualifier.fromString("01"));
        assertEquals(AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID, AuthorizationInformationQualifier.fromString("02"));
        assertEquals(AuthorizationInformationQualifier.ADDITIONAL_DATA_ID, AuthorizationInformationQualifier.fromString("03"));
        assertEquals(AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID, AuthorizationInformationQualifier.fromString("04"));
        assertEquals(AuthorizationInformationQualifier.DOD_COMMUNICATION_ID, AuthorizationInformationQualifier.fromString("05"));
        assertEquals(AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID, AuthorizationInformationQualifier.fromString("06"));

        assertEquals(AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION,
                AuthorizationInformationQualifier.fromString("NO_AUTHORIZATION_INFORMATION"));
        assertEquals(AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID,
                AuthorizationInformationQualifier.fromString("UCS_COMMUNICATIONS_ID"));

        assertEquals(AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION,
                AuthorizationInformationQualifier.fromString("No Authorization"));
        assertEquals(AuthorizationInformationQualifier.DOD_COMMUNICATION_ID,
                AuthorizationInformationQualifier.fromString("Department of Defense (DoD) Communication Identifier"));

        assertThrows(IllegalArgumentException.class, () -> AuthorizationInformationQualifier.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, AuthorizationInformationQualifier expected) throws Exception {
        Field lookupField = AuthorizationInformationQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<AuthorizationInformationQualifier> lookup =
                (EdiEnumLookup<AuthorizationInformationQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
        assertEquals(expected, AuthorizationInformationQualifier.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("00", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("01", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID),
                Arguments.of("02", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID),
                Arguments.of("03", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID),
                Arguments.of("04", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID),
                Arguments.of("05", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("06", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),

                Arguments.of("NO_AUTHORIZATION_INFORMATION", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("UCS_COMMUNICATIONS_ID", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID),
                Arguments.of("EDX_COMMUNICATIONS_ID", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID),
                Arguments.of("ADDITIONAL_DATA_ID", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID),
                Arguments.of("RAIL_COMMUNICATIONS_ID", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID),
                Arguments.of("DOD_COMMUNICATION_ID", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("US_FEDERAL_GOVT_COMM_ID", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),

                Arguments.of("No Authorization", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("UCS Communications ID", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID),
                Arguments.of("EDX Communications ID", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID),
                Arguments.of("Additional Data Identification", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID),
                Arguments.of("Rail Communications ID", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID),
                Arguments.of("Department of Defense (DoD) Communication Identifier", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("United States Federal Government Communication Identifier", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),

                Arguments.of("none", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("noauth", AuthorizationInformationQualifier.NO_AUTHORIZATION_INFORMATION),
                Arguments.of("ucs", AuthorizationInformationQualifier.UCS_COMMUNICATIONS_ID),
                Arguments.of("edx", AuthorizationInformationQualifier.EDX_COMMUNICATIONS_ID),
                Arguments.of("additionaldata", AuthorizationInformationQualifier.ADDITIONAL_DATA_ID),
                Arguments.of("rail", AuthorizationInformationQualifier.RAIL_COMMUNICATIONS_ID),
                Arguments.of("dod", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("defense", AuthorizationInformationQualifier.DOD_COMMUNICATION_ID),
                Arguments.of("federal", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),
                Arguments.of("govt", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID),
                Arguments.of("usgov", AuthorizationInformationQualifier.US_FEDERAL_GOVT_COMM_ID)
        );
    }
}