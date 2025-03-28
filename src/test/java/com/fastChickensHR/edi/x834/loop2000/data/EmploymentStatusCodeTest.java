/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentStatusCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(13, EmploymentStatusCode.values().length);

        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.ACTIVE));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.FULL_TIME));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.PART_TIME));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.RETIRED));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.TERMINATED));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.LEAVE_OF_ABSENCE));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.DISABLED));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.MILITARY_DUTY));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.COBRA));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.SURVIVING_INSURED));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.CONTRACT_EMPLOYEE));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.ON_CALL_EMPLOYEE));
        assertTrue(Arrays.asList(EmploymentStatusCode.values()).contains(EmploymentStatusCode.SEASONAL_EMPLOYEE));
    }

    @Test
    void testEnumProperties() {
        // Test code and description for each enum value
        assertEquals("1", EmploymentStatusCode.ACTIVE.getCode());
        assertEquals("Active", EmploymentStatusCode.ACTIVE.getDescription());

        assertEquals("2", EmploymentStatusCode.FULL_TIME.getCode());
        assertEquals("Full-time", EmploymentStatusCode.FULL_TIME.getDescription());

        assertEquals("3", EmploymentStatusCode.PART_TIME.getCode());
        assertEquals("Part-time", EmploymentStatusCode.PART_TIME.getDescription());

        assertEquals("4", EmploymentStatusCode.RETIRED.getCode());
        assertEquals("Retired", EmploymentStatusCode.RETIRED.getDescription());

        assertEquals("5", EmploymentStatusCode.TERMINATED.getCode());
        assertEquals("Terminated", EmploymentStatusCode.TERMINATED.getDescription());

        assertEquals("6", EmploymentStatusCode.LEAVE_OF_ABSENCE.getCode());
        assertEquals("Leave of absence", EmploymentStatusCode.LEAVE_OF_ABSENCE.getDescription());

        assertEquals("7", EmploymentStatusCode.DISABLED.getCode());
        assertEquals("Disabled", EmploymentStatusCode.DISABLED.getDescription());

        assertEquals("9", EmploymentStatusCode.MILITARY_DUTY.getCode());
        assertEquals("Military duty", EmploymentStatusCode.MILITARY_DUTY.getDescription());

        assertEquals("20", EmploymentStatusCode.COBRA.getCode());
        assertEquals("COBRA", EmploymentStatusCode.COBRA.getDescription());

        assertEquals("21", EmploymentStatusCode.SURVIVING_INSURED.getCode());
        assertEquals("Surviving insured", EmploymentStatusCode.SURVIVING_INSURED.getDescription());

        assertEquals("22", EmploymentStatusCode.CONTRACT_EMPLOYEE.getCode());
        assertEquals("Contract employee", EmploymentStatusCode.CONTRACT_EMPLOYEE.getDescription());

        assertEquals("23", EmploymentStatusCode.ON_CALL_EMPLOYEE.getCode());
        assertEquals("On call employee", EmploymentStatusCode.ON_CALL_EMPLOYEE.getDescription());

        assertEquals("24", EmploymentStatusCode.SEASONAL_EMPLOYEE.getCode());
        assertEquals("Seasonal employee", EmploymentStatusCode.SEASONAL_EMPLOYEE.getDescription());

        // Test toString() returns the code
        for (EmploymentStatusCode code : EmploymentStatusCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, EmploymentStatusCode expected) throws Exception {
        Field lookupField = EmploymentStatusCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<EmploymentStatusCode> lookup = (EdiEnumLookup<EmploymentStatusCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                // Direct code lookups
                Arguments.of("1", EmploymentStatusCode.ACTIVE),
                Arguments.of("2", EmploymentStatusCode.FULL_TIME),
                Arguments.of("3", EmploymentStatusCode.PART_TIME),
                Arguments.of("4", EmploymentStatusCode.RETIRED),
                Arguments.of("5", EmploymentStatusCode.TERMINATED),
                Arguments.of("6", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("7", EmploymentStatusCode.DISABLED),
                Arguments.of("9", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("20", EmploymentStatusCode.COBRA),
                Arguments.of("21", EmploymentStatusCode.SURVIVING_INSURED),
                Arguments.of("22", EmploymentStatusCode.CONTRACT_EMPLOYEE),
                Arguments.of("23", EmploymentStatusCode.ON_CALL_EMPLOYEE),
                Arguments.of("24", EmploymentStatusCode.SEASONAL_EMPLOYEE),

                // Name lookups
                Arguments.of("ACTIVE", EmploymentStatusCode.ACTIVE),
                Arguments.of("FULL_TIME", EmploymentStatusCode.FULL_TIME),
                Arguments.of("PART_TIME", EmploymentStatusCode.PART_TIME),
                Arguments.of("RETIRED", EmploymentStatusCode.RETIRED),
                Arguments.of("TERMINATED", EmploymentStatusCode.TERMINATED),
                Arguments.of("LEAVE_OF_ABSENCE", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("DISABLED", EmploymentStatusCode.DISABLED),
                Arguments.of("MILITARY_DUTY", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("COBRA", EmploymentStatusCode.COBRA),
                Arguments.of("SURVIVING_INSURED", EmploymentStatusCode.SURVIVING_INSURED),
                Arguments.of("CONTRACT_EMPLOYEE", EmploymentStatusCode.CONTRACT_EMPLOYEE),
                Arguments.of("ON_CALL_EMPLOYEE", EmploymentStatusCode.ON_CALL_EMPLOYEE),
                Arguments.of("SEASONAL_EMPLOYEE", EmploymentStatusCode.SEASONAL_EMPLOYEE),

                // Description lookups
                Arguments.of("Active", EmploymentStatusCode.ACTIVE),
                Arguments.of("Full-time", EmploymentStatusCode.FULL_TIME),
                Arguments.of("Part-time", EmploymentStatusCode.PART_TIME),
                Arguments.of("Retired", EmploymentStatusCode.RETIRED),
                Arguments.of("Terminated", EmploymentStatusCode.TERMINATED),
                Arguments.of("Leave of absence", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("Disabled", EmploymentStatusCode.DISABLED),
                Arguments.of("Military duty", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("COBRA", EmploymentStatusCode.COBRA),
                Arguments.of("Surviving insured", EmploymentStatusCode.SURVIVING_INSURED),
                Arguments.of("Contract employee", EmploymentStatusCode.CONTRACT_EMPLOYEE),
                Arguments.of("On call employee", EmploymentStatusCode.ON_CALL_EMPLOYEE),
                Arguments.of("Seasonal employee", EmploymentStatusCode.SEASONAL_EMPLOYEE),

                // Alias lookups defined in the LOOKUP map
                Arguments.of("current", EmploymentStatusCode.ACTIVE),
                Arguments.of("employed", EmploymentStatusCode.ACTIVE),
                Arguments.of("working", EmploymentStatusCode.ACTIVE),

                Arguments.of("ft", EmploymentStatusCode.FULL_TIME),
                Arguments.of("fulltime", EmploymentStatusCode.FULL_TIME),
                Arguments.of("40hours", EmploymentStatusCode.FULL_TIME),

                Arguments.of("pt", EmploymentStatusCode.PART_TIME),
                Arguments.of("parttime", EmploymentStatusCode.PART_TIME),
                Arguments.of("hourly", EmploymentStatusCode.PART_TIME),

                Arguments.of("pension", EmploymentStatusCode.RETIRED),
                Arguments.of("retiree", EmploymentStatusCode.RETIRED),

                Arguments.of("laid off", EmploymentStatusCode.TERMINATED),
                Arguments.of("fired", EmploymentStatusCode.TERMINATED),
                Arguments.of("resigned", EmploymentStatusCode.TERMINATED),
                Arguments.of("quit", EmploymentStatusCode.TERMINATED),

                Arguments.of("loa", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("sabbatical", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("fmla", EmploymentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("medical leave", EmploymentStatusCode.LEAVE_OF_ABSENCE),

                Arguments.of("disability", EmploymentStatusCode.DISABLED),
                Arguments.of("ltd", EmploymentStatusCode.DISABLED),

                Arguments.of("military", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("army", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("navy", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("airforce", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("marines", EmploymentStatusCode.MILITARY_DUTY),
                Arguments.of("reserve", EmploymentStatusCode.MILITARY_DUTY),

                Arguments.of("consolidated omnibus budget reconciliation act", EmploymentStatusCode.COBRA),

                Arguments.of("survivor", EmploymentStatusCode.SURVIVING_INSURED),
                Arguments.of("widow", EmploymentStatusCode.SURVIVING_INSURED),
                Arguments.of("widower", EmploymentStatusCode.SURVIVING_INSURED),

                Arguments.of("contractor", EmploymentStatusCode.CONTRACT_EMPLOYEE),
                Arguments.of("1099", EmploymentStatusCode.CONTRACT_EMPLOYEE),
                Arguments.of("temporary", EmploymentStatusCode.CONTRACT_EMPLOYEE),

                Arguments.of("oncall", EmploymentStatusCode.ON_CALL_EMPLOYEE)
        );
    }
}