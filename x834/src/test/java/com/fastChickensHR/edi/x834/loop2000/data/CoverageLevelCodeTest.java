/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CoverageLevelCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(10, CoverageLevelCode.values().length,
                "CoverageLevelCode should have 10 enum values");

        assertTrue(Arrays.asList(CoverageLevelCode.values()).contains(CoverageLevelCode.FAMILY));
        assertTrue(Arrays.asList(CoverageLevelCode.values()).contains(CoverageLevelCode.EMPLOYEE_ONLY));
        assertTrue(Arrays.asList(CoverageLevelCode.values()).contains(CoverageLevelCode.INDIVIDUAL));
    }

    @Test
    void testEnumProperties() {
        assertEquals("TWO", CoverageLevelCode.TWO_PARTY.getCode());
        assertEquals("CHD", CoverageLevelCode.CHILDREN_ONLY.getCode());
    }

    @Test
    void testToStringReturnsCode() {
        assertEquals("FAM", CoverageLevelCode.FAMILY.toString());
        assertEquals("EMP", CoverageLevelCode.EMPLOYEE_ONLY.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFromString() throws Exception {
        Field lookupField = CoverageLevelCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<CoverageLevelCode> lookup =
                (EdiEnumLookup<CoverageLevelCode>) lookupField.get(null);

        assertEquals(CoverageLevelCode.FAMILY, lookup.fromString("FAM"));
        assertEquals(CoverageLevelCode.FAMILY, lookup.fromString("Family"));
        assertEquals(CoverageLevelCode.FAMILY, lookup.fromString("family coverage"));

        assertEquals(CoverageLevelCode.EMPLOYEE_ONLY, lookup.fromString("self"));
        assertEquals(CoverageLevelCode.EMPLOYEE_ONLY, lookup.fromString("ee only"));
        assertEquals(CoverageLevelCode.INDIVIDUAL, lookup.fromString("single"));
        assertEquals(CoverageLevelCode.TWO_PARTY, lookup.fromString("couple"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(null));
    }

    @Test
    void testStaticFromString() {
        assertEquals(CoverageLevelCode.FAMILY, CoverageLevelCode.fromString("FAM"));
        assertEquals(CoverageLevelCode.EMPLOYEE_ONLY, CoverageLevelCode.fromString("self"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    @SuppressWarnings("unchecked")
    void testAllLookupValues(String input, CoverageLevelCode expected) throws Exception {
        Field lookupField = CoverageLevelCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<CoverageLevelCode> lookup =
                (EdiEnumLookup<CoverageLevelCode>) lookupField.get(null);
        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("FAM", CoverageLevelCode.FAMILY),
                Arguments.of("EMP", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("IND", CoverageLevelCode.INDIVIDUAL),
                Arguments.of("ECH", CoverageLevelCode.EMPLOYEE_AND_CHILDREN),
                Arguments.of("ESP", CoverageLevelCode.EMPLOYEE_AND_SPOUSE),
                Arguments.of("SPC", CoverageLevelCode.SPOUSE_AND_CHILDREN),
                Arguments.of("SPO", CoverageLevelCode.SPOUSE_ONLY),
                Arguments.of("CHD", CoverageLevelCode.CHILDREN_ONLY),
                Arguments.of("TWO", CoverageLevelCode.TWO_PARTY),
                Arguments.of("DEP", CoverageLevelCode.DEPENDENTS_ONLY),

                Arguments.of("family coverage", CoverageLevelCode.FAMILY),
                Arguments.of("whole family", CoverageLevelCode.FAMILY),
                Arguments.of("self", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("just employee", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("single", CoverageLevelCode.INDIVIDUAL),
                Arguments.of("couple", CoverageLevelCode.TWO_PARTY),
                Arguments.of("partner only", CoverageLevelCode.SPOUSE_ONLY),
                Arguments.of("kids only", CoverageLevelCode.CHILDREN_ONLY)
        );
    }
}
