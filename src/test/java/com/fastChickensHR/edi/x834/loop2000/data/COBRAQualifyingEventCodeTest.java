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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class COBRAQualifyingEventCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(9, COBRAQualifyingEventCode.values().length);

        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.REDUCTION_IN_HOURS));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.BANKRUPTCY));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.LAYOFF));
        assertTrue(Arrays.asList(COBRAQualifyingEventCode.values()).contains(COBRAQualifyingEventCode.LEAVE_OF_ABSENCE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("1", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT.getCode());
        assertEquals("Termination of Employment", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT.getDescription());

        assertEquals("2", COBRAQualifyingEventCode.REDUCTION_IN_HOURS.getCode());
        assertEquals("Reduction in Hours", COBRAQualifyingEventCode.REDUCTION_IN_HOURS.getDescription());

        assertEquals("3", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE.getCode());
        assertEquals("Death of Employee", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE.getDescription());

        assertEquals("4", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION.getCode());
        assertEquals("Divorce or Legal Separation", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION.getDescription());

        assertEquals("5", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT.getCode());
        assertEquals("Medicare Entitlement", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT.getDescription());

        assertEquals("6", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT.getCode());
        assertEquals("Dependent Child Ceases to be Dependent", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT.getDescription());

        assertEquals("7", COBRAQualifyingEventCode.BANKRUPTCY.getCode());
        assertEquals("Bankruptcy (Retirees and Dependents)", COBRAQualifyingEventCode.BANKRUPTCY.getDescription());

        assertEquals("8", COBRAQualifyingEventCode.LAYOFF.getCode());
        assertEquals("Layoff", COBRAQualifyingEventCode.LAYOFF.getDescription());

        assertEquals("9", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE.getCode());
        assertEquals("Leave of Absence", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE.getDescription());

        for (COBRAQualifyingEventCode code : COBRAQualifyingEventCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, COBRAQualifyingEventCode expected) throws Exception {
        Field lookupField = COBRAQualifyingEventCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<COBRAQualifyingEventCode> lookup = (EdiEnumLookup<COBRAQualifyingEventCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                // Direct code lookups
                Arguments.of("1", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("2", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("3", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("4", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("5", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT),
                Arguments.of("6", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("7", COBRAQualifyingEventCode.BANKRUPTCY),
                Arguments.of("8", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("9", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),

                // Name lookups
                Arguments.of("TERMINATION_OF_EMPLOYMENT", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("REDUCTION_IN_HOURS", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("DEATH_OF_EMPLOYEE", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("DIVORCE_LEGAL_SEPARATION", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("MEDICARE_ENTITLEMENT", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT),
                Arguments.of("DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("BANKRUPTCY", COBRAQualifyingEventCode.BANKRUPTCY),
                Arguments.of("LAYOFF", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("LEAVE_OF_ABSENCE", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),

                // Description lookups
                Arguments.of("Termination of Employment", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("Reduction in Hours", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("Death of Employee", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("Divorce or Legal Separation", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("Medicare Entitlement", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT),
                Arguments.of("Dependent Child Ceases to be Dependent", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("Bankruptcy (Retirees and Dependents)", COBRAQualifyingEventCode.BANKRUPTCY),
                Arguments.of("Layoff", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("Leave of Absence", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),

                // Alias lookups
                Arguments.of("fired", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("terminated", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("quit", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),
                Arguments.of("resignation", COBRAQualifyingEventCode.TERMINATION_OF_EMPLOYMENT),

                Arguments.of("reduced hours", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),
                Arguments.of("parttime", COBRAQualifyingEventCode.REDUCTION_IN_HOURS),

                Arguments.of("deceased", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),
                Arguments.of("passed away", COBRAQualifyingEventCode.DEATH_OF_EMPLOYEE),

                Arguments.of("divorce", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),
                Arguments.of("separated", COBRAQualifyingEventCode.DIVORCE_LEGAL_SEPARATION),

                Arguments.of("medicare", COBRAQualifyingEventCode.MEDICARE_ENTITLEMENT),

                Arguments.of("agedout", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),
                Arguments.of("no longer dependent", COBRAQualifyingEventCode.DEPENDENT_CHILD_CEASES_TO_BE_DEPENDENT),

                Arguments.of("chapter11", COBRAQualifyingEventCode.BANKRUPTCY),

                Arguments.of("downsized", COBRAQualifyingEventCode.LAYOFF),
                Arguments.of("laid off", COBRAQualifyingEventCode.LAYOFF),

                Arguments.of("sabbatical", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),
                Arguments.of("fmla", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE),
                Arguments.of("loa", COBRAQualifyingEventCode.LEAVE_OF_ABSENCE)
        );
    }
}