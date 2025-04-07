/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SecurityLevelCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("00", SecurityLevelCode.COMPANY_NON_CLASSIFIED.getCode());
        assertEquals("Company Non-Classified", SecurityLevelCode.COMPANY_NON_CLASSIFIED.getDescription());

        assertEquals("03", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED.getCode());
        assertEquals("Company Confidential, Restricted (Need to Know)", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED.getDescription());

        assertEquals("94", SecurityLevelCode.GOVERNMENT_TOP_SECRET.getCode());
        assertEquals("Government Top Secret", SecurityLevelCode.GOVERNMENT_TOP_SECRET.getDescription());

        assertEquals("ZZ", SecurityLevelCode.MUTUALLY_DEFINED.getCode());
        assertEquals("Mutually Defined", SecurityLevelCode.MUTUALLY_DEFINED.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (SecurityLevelCode code : SecurityLevelCode.values()) {
            assertNotNull(code.getCode(), "Code should not be null for " + code.name());
            assertFalse(code.getCode().isEmpty(), "Code should not be empty for " + code.name());

            assertNotNull(code.getDescription(), "Description should not be null for " + code.name());
            assertFalse(code.getDescription().isEmpty(), "Description should not be empty for " + code.name());
        }
    }

    @Test
    void testFromString() {
        assertEquals(SecurityLevelCode.COMPANY_NON_CLASSIFIED, SecurityLevelCode.fromString("00"));
        assertEquals(SecurityLevelCode.COMPANY_INTERNAL, SecurityLevelCode.fromString("01"));
        assertEquals(SecurityLevelCode.GOVERNMENT_SECRET, SecurityLevelCode.fromString("93"));
        assertEquals(SecurityLevelCode.MUTUALLY_DEFINED, SecurityLevelCode.fromString("ZZ"));

        assertEquals(SecurityLevelCode.COMPANY_NON_CLASSIFIED, SecurityLevelCode.fromString("non-classified"));
        assertEquals(SecurityLevelCode.COMPANY_INTERNAL, SecurityLevelCode.fromString("internal use"));
        assertEquals(SecurityLevelCode.GOVERNMENT_TOP_SECRET, SecurityLevelCode.fromString("top secret"));
        assertEquals(SecurityLevelCode.PERSONAL, SecurityLevelCode.fromString("private"));

        assertEquals(SecurityLevelCode.MUTUALLY_DEFINED, SecurityLevelCode.fromString("zz"));
        assertEquals(SecurityLevelCode.COMPANY_CONFIDENTIAL, SecurityLevelCode.fromString("CONFIDENTIAL"));

        assertThrows(IllegalArgumentException.class, () -> SecurityLevelCode.fromString("invalid-code"));
        assertThrows(IllegalArgumentException.class, () -> SecurityLevelCode.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> SecurityLevelCode.fromString(null));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, SecurityLevelCode expected) {
        assertEquals(expected, SecurityLevelCode.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                // Direct code values
                Arguments.of("00", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("01", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("02", SecurityLevelCode.COMPANY_CONFIDENTIAL),
                Arguments.of("03", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("04", SecurityLevelCode.COMPANY_REGISTERED),
                Arguments.of("05", SecurityLevelCode.PERSONAL),
                Arguments.of("06", SecurityLevelCode.SUPPLIER_PROPRIETARY),
                Arguments.of("09", SecurityLevelCode.COMPANY_DEFINED),
                Arguments.of("11", SecurityLevelCode.COMPETITION_SENSITIVE),
                Arguments.of("20", SecurityLevelCode.COURT_RESTRICTED),
                Arguments.of("21", SecurityLevelCode.JUVENILE_RECORD_RESTRICTED),
                Arguments.of("90", SecurityLevelCode.GOVERNMENT_NON_CLASSIFIED),
                Arguments.of("92", SecurityLevelCode.GOVERNMENT_CONFIDENTIAL),
                Arguments.of("93", SecurityLevelCode.GOVERNMENT_SECRET),
                Arguments.of("94", SecurityLevelCode.GOVERNMENT_TOP_SECRET),
                Arguments.of("99", SecurityLevelCode.GOVERNMENT_DEFINED),
                Arguments.of("ZZ", SecurityLevelCode.MUTUALLY_DEFINED),
                Arguments.of("zz", SecurityLevelCode.MUTUALLY_DEFINED),

                // Alternative terms for selected codes
                Arguments.of("non-classified", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("unclassified", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("public", SecurityLevelCode.COMPANY_NON_CLASSIFIED),

                Arguments.of("internal", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("internal use", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("internal only", SecurityLevelCode.COMPANY_INTERNAL),

                Arguments.of("confidential", SecurityLevelCode.COMPANY_CONFIDENTIAL),
                Arguments.of("company conf", SecurityLevelCode.COMPANY_CONFIDENTIAL),

                Arguments.of("restricted", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("need to know", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),

                Arguments.of("registered", SecurityLevelCode.COMPANY_REGISTERED),
                Arguments.of("signature required", SecurityLevelCode.COMPANY_REGISTERED),

                Arguments.of("private", SecurityLevelCode.PERSONAL),
                Arguments.of("personal information", SecurityLevelCode.PERSONAL),

                Arguments.of("proprietary", SecurityLevelCode.SUPPLIER_PROPRIETARY),
                Arguments.of("supplier", SecurityLevelCode.SUPPLIER_PROPRIETARY),

                Arguments.of("trading partner", SecurityLevelCode.COMPANY_DEFINED),

                Arguments.of("competitive", SecurityLevelCode.COMPETITION_SENSITIVE),
                Arguments.of("competitor sensitive", SecurityLevelCode.COMPETITION_SENSITIVE),

                Arguments.of("court", SecurityLevelCode.COURT_RESTRICTED),

                Arguments.of("juvenile", SecurityLevelCode.JUVENILE_RECORD_RESTRICTED),

                Arguments.of("gov unclassified", SecurityLevelCode.GOVERNMENT_NON_CLASSIFIED),

                Arguments.of("gov confidential", SecurityLevelCode.GOVERNMENT_CONFIDENTIAL),

                Arguments.of("secret", SecurityLevelCode.GOVERNMENT_SECRET),

                Arguments.of("top secret", SecurityLevelCode.GOVERNMENT_TOP_SECRET),
                Arguments.of("ts", SecurityLevelCode.GOVERNMENT_TOP_SECRET),

                Arguments.of("gov defined", SecurityLevelCode.GOVERNMENT_DEFINED),

                Arguments.of("mutual", SecurityLevelCode.MUTUALLY_DEFINED),
                Arguments.of("agreed upon", SecurityLevelCode.MUTUALLY_DEFINED),
                Arguments.of("custom", SecurityLevelCode.MUTUALLY_DEFINED)
        );
    }


    @Test
    void testToString() {
        Arrays.stream(SecurityLevelCode.values()).forEach(code ->
                assertEquals(code.getCode(), code.toString())
        );
    }

    @Test
    void testCompleteness() {
        assertEquals(17, SecurityLevelCode.values().length,
                "There should be exactly 17 security level codes");

        assertTrue(Arrays.stream(SecurityLevelCode.values())
                .anyMatch(code -> code.getCode().equals("00")));
        assertTrue(Arrays.stream(SecurityLevelCode.values())
                .anyMatch(code -> code.getCode().equals("03")));
        assertTrue(Arrays.stream(SecurityLevelCode.values())
                .anyMatch(code -> code.getCode().equals("94")));
        assertTrue(Arrays.stream(SecurityLevelCode.values())
                .anyMatch(code -> code.getCode().equals("ZZ")));
    }
}