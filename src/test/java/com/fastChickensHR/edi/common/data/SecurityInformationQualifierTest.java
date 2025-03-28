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

class SecurityInformationQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(2, SecurityInformationQualifier.values().length);
        assertEquals("00", SecurityInformationQualifier.NO_SECURITY_INFORMATION.getCode());
        assertEquals("01", SecurityInformationQualifier.PASSWORD.getCode());
    }

    @Test
    void testEnumProperties() {
        assertEquals("No Security Information Present", SecurityInformationQualifier.NO_SECURITY_INFORMATION.getDescription());
        assertEquals("Password", SecurityInformationQualifier.PASSWORD.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(SecurityInformationQualifier.NO_SECURITY_INFORMATION, SecurityInformationQualifier.fromString("00"));
        assertEquals(SecurityInformationQualifier.PASSWORD, SecurityInformationQualifier.fromString("01"));

        assertEquals(SecurityInformationQualifier.NO_SECURITY_INFORMATION, SecurityInformationQualifier.fromString("NO_SECURITY_INFORMATION"));
        assertEquals(SecurityInformationQualifier.PASSWORD, SecurityInformationQualifier.fromString("PASSWORD"));

        assertEquals(SecurityInformationQualifier.NO_SECURITY_INFORMATION, SecurityInformationQualifier.fromString("No Security Information Present"));
        assertEquals(SecurityInformationQualifier.PASSWORD, SecurityInformationQualifier.fromString("Password"));

        assertThrows(IllegalArgumentException.class, () -> SecurityInformationQualifier.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, SecurityInformationQualifier expected) throws Exception {
        Field lookupField = SecurityInformationQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<SecurityInformationQualifier> lookup =
                (EdiEnumLookup<SecurityInformationQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));

        assertEquals(expected, SecurityInformationQualifier.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("00", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("01", SecurityInformationQualifier.PASSWORD),

                Arguments.of("NO_SECURITY_INFORMATION", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("PASSWORD", SecurityInformationQualifier.PASSWORD),

                Arguments.of("No Security Information Present", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("Password", SecurityInformationQualifier.PASSWORD),

                Arguments.of("none", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("no security", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("nosecurity", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("no info", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("noinfo", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("empty", SecurityInformationQualifier.NO_SECURITY_INFORMATION),
                Arguments.of("blank", SecurityInformationQualifier.NO_SECURITY_INFORMATION),

                // Test common terms for PASSWORD
                Arguments.of("pwd", SecurityInformationQualifier.PASSWORD),
                Arguments.of("pass", SecurityInformationQualifier.PASSWORD),
                Arguments.of("passcode", SecurityInformationQualifier.PASSWORD),
                Arguments.of("secret", SecurityInformationQualifier.PASSWORD),
                Arguments.of("credentials", SecurityInformationQualifier.PASSWORD),
                Arguments.of("authentication", SecurityInformationQualifier.PASSWORD)
        );
    }
}