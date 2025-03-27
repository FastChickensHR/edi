/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IndividualRelationshipCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(7, IndividualRelationshipCode.values().length);

        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.SPOUSE));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.CHILD));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.EMPLOYEE));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.DISABLED_DEPENDENT));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.SELF));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.LIFE_PARTNER));
        assertTrue(Arrays.asList(IndividualRelationshipCode.values()).contains(IndividualRelationshipCode.OTHER_RELATED));
    }

    @Test
    void testEnumProperties() {
        // Test code and description for each enum value
        assertEquals("01", IndividualRelationshipCode.SPOUSE.getCode());
        assertEquals("Spouse", IndividualRelationshipCode.SPOUSE.getDescription());

        assertEquals("19", IndividualRelationshipCode.CHILD.getCode());
        assertEquals("Child", IndividualRelationshipCode.CHILD.getDescription());

        assertEquals("20", IndividualRelationshipCode.EMPLOYEE.getCode());
        assertEquals("Employee", IndividualRelationshipCode.EMPLOYEE.getDescription());

        assertEquals("22", IndividualRelationshipCode.DISABLED_DEPENDENT.getCode());
        assertEquals("Disabled Dependent", IndividualRelationshipCode.DISABLED_DEPENDENT.getDescription());

        assertEquals("18", IndividualRelationshipCode.SELF.getCode());
        assertEquals("Self", IndividualRelationshipCode.SELF.getDescription());

        assertEquals("53", IndividualRelationshipCode.LIFE_PARTNER.getCode());
        assertEquals("Life Partner", IndividualRelationshipCode.LIFE_PARTNER.getDescription());

        assertEquals("29", IndividualRelationshipCode.OTHER_RELATED.getCode());
        assertEquals("Other Related", IndividualRelationshipCode.OTHER_RELATED.getDescription());

        for (IndividualRelationshipCode code : IndividualRelationshipCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @Test
    void testFromString() {
        assertEquals(IndividualRelationshipCode.SPOUSE, IndividualRelationshipCode.fromString("01"));
        assertEquals(IndividualRelationshipCode.CHILD, IndividualRelationshipCode.fromString("19"));
        assertEquals(IndividualRelationshipCode.EMPLOYEE, IndividualRelationshipCode.fromString("20"));
        assertEquals(IndividualRelationshipCode.DISABLED_DEPENDENT, IndividualRelationshipCode.fromString("22"));
        assertEquals(IndividualRelationshipCode.SELF, IndividualRelationshipCode.fromString("18"));
        assertEquals(IndividualRelationshipCode.LIFE_PARTNER, IndividualRelationshipCode.fromString("53"));
        assertEquals(IndividualRelationshipCode.OTHER_RELATED, IndividualRelationshipCode.fromString("29"));

        assertThrows(IllegalArgumentException.class, () -> IndividualRelationshipCode.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, IndividualRelationshipCode expected) throws Exception {
        Field lookupField = IndividualRelationshipCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<IndividualRelationshipCode> lookup = (EdiEnumLookup<IndividualRelationshipCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("01", IndividualRelationshipCode.SPOUSE),
                Arguments.of("19", IndividualRelationshipCode.CHILD),
                Arguments.of("20", IndividualRelationshipCode.EMPLOYEE),
                Arguments.of("22", IndividualRelationshipCode.DISABLED_DEPENDENT),
                Arguments.of("18", IndividualRelationshipCode.SELF),
                Arguments.of("53", IndividualRelationshipCode.LIFE_PARTNER),
                Arguments.of("29", IndividualRelationshipCode.OTHER_RELATED),

                Arguments.of("SPOUSE", IndividualRelationshipCode.SPOUSE),
                Arguments.of("CHILD", IndividualRelationshipCode.CHILD),
                Arguments.of("EMPLOYEE", IndividualRelationshipCode.EMPLOYEE),
                Arguments.of("DISABLED_DEPENDENT", IndividualRelationshipCode.DISABLED_DEPENDENT),
                Arguments.of("SELF", IndividualRelationshipCode.SELF),
                Arguments.of("LIFE_PARTNER", IndividualRelationshipCode.LIFE_PARTNER),
                Arguments.of("OTHER_RELATED", IndividualRelationshipCode.OTHER_RELATED),

                Arguments.of("Spouse", IndividualRelationshipCode.SPOUSE),
                Arguments.of("Child", IndividualRelationshipCode.CHILD),
                Arguments.of("Employee", IndividualRelationshipCode.EMPLOYEE),
                Arguments.of("Disabled Dependent", IndividualRelationshipCode.DISABLED_DEPENDENT),
                Arguments.of("Self", IndividualRelationshipCode.SELF),
                Arguments.of("Life Partner", IndividualRelationshipCode.LIFE_PARTNER),
                Arguments.of("Other Related", IndividualRelationshipCode.OTHER_RELATED),

                Arguments.of("wife", IndividualRelationshipCode.SPOUSE),
                Arguments.of("husband", IndividualRelationshipCode.SPOUSE),
                Arguments.of("son", IndividualRelationshipCode.CHILD),
                Arguments.of("daughter", IndividualRelationshipCode.CHILD),
                Arguments.of("domesticpartner", IndividualRelationshipCode.LIFE_PARTNER)
        );
    }
}