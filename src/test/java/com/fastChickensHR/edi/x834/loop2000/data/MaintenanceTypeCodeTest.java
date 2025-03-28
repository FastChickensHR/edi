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

class MaintenanceTypeCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(11, MaintenanceTypeCode.values().length,
                "MaintenanceTypeCode should have 11 enum values");

        assertTrue(Arrays.asList(MaintenanceTypeCode.values()).contains(MaintenanceTypeCode.ADDITION),
                "ADDITION enum value should exist");
        assertTrue(Arrays.asList(MaintenanceTypeCode.values()).contains(MaintenanceTypeCode.CANCELLATION),
                "CANCELLATION enum value should exist");
    }

    @Test
    void testEnumProperties() {
        assertEquals("001", MaintenanceTypeCode.ADDITION.getCode());
        assertEquals("Addition", MaintenanceTypeCode.ADDITION.getDescription());

        assertEquals("002", MaintenanceTypeCode.CHANGE.getCode());
        assertEquals("Change", MaintenanceTypeCode.CHANGE.getDescription());

        assertEquals("003", MaintenanceTypeCode.CANCELLATION.getCode());
        assertEquals("Cancellation", MaintenanceTypeCode.CANCELLATION.getDescription());

        assertEquals("004", MaintenanceTypeCode.REINSTATEMENT.getCode());
        assertEquals("Reinstatement", MaintenanceTypeCode.REINSTATEMENT.getDescription());

        assertEquals("021", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID.getCode());
        assertEquals("Change with Member ID Change", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID.getDescription());
    }

    @Test
    void testFromString() throws Exception {
        Field lookupField = MaintenanceTypeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MaintenanceTypeCode> lookup = (EdiEnumLookup<MaintenanceTypeCode>) lookupField.get(null);

        assertEquals(MaintenanceTypeCode.ADDITION, lookup.fromString("001"));
        assertEquals(MaintenanceTypeCode.CHANGE, lookup.fromString("002"));
        assertEquals(MaintenanceTypeCode.CANCELLATION, lookup.fromString("003"));

        assertEquals(MaintenanceTypeCode.ADDITION, lookup.fromString("Addition"));
        assertEquals(MaintenanceTypeCode.CHANGE, lookup.fromString("Change"));

        assertEquals(MaintenanceTypeCode.ADDITION, lookup.fromString("addition"));
        assertEquals(MaintenanceTypeCode.CHANGE, lookup.fromString("CHANGE"));

        assertEquals(MaintenanceTypeCode.ADDITION, lookup.fromString("add"));
        assertEquals(MaintenanceTypeCode.CHANGE, lookup.fromString("modify"));
        assertEquals(MaintenanceTypeCode.CANCELLATION, lookup.fromString("cancel"));

        assertEquals(MaintenanceTypeCode.ADDITION, lookup.fromString("1"));
        assertEquals(MaintenanceTypeCode.CHANGE, lookup.fromString("2"));
        assertEquals(MaintenanceTypeCode.CANCELLATION, lookup.fromString("3"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(null));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, MaintenanceTypeCode expected) throws Exception {
        Field lookupField = MaintenanceTypeCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MaintenanceTypeCode> lookup = (EdiEnumLookup<MaintenanceTypeCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("001", MaintenanceTypeCode.ADDITION),
                Arguments.of("002", MaintenanceTypeCode.CHANGE),
                Arguments.of("003", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("004", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("021", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("022", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("023", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("024", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("025", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("026", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("030", MaintenanceTypeCode.COBRA_ELECTION),

                Arguments.of("1", MaintenanceTypeCode.ADDITION),
                Arguments.of("2", MaintenanceTypeCode.CHANGE),
                Arguments.of("3", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("4", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("21", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("22", MaintenanceTypeCode.CHANGE_ADDRESS),

                Arguments.of("Addition", MaintenanceTypeCode.ADDITION),
                Arguments.of("Change", MaintenanceTypeCode.CHANGE),
                Arguments.of("Cancellation", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("Reinstatement", MaintenanceTypeCode.REINSTATEMENT),

                Arguments.of("ADDITION", MaintenanceTypeCode.ADDITION),
                Arguments.of("CHANGE", MaintenanceTypeCode.CHANGE),
                Arguments.of("CANCELLATION", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("REINSTATEMENT", MaintenanceTypeCode.REINSTATEMENT),

                Arguments.of("add", MaintenanceTypeCode.ADDITION),
                Arguments.of("new", MaintenanceTypeCode.ADDITION),
                Arguments.of("enroll", MaintenanceTypeCode.ADDITION),
                Arguments.of("enrollment", MaintenanceTypeCode.ADDITION),
                Arguments.of("create", MaintenanceTypeCode.ADDITION),
                Arguments.of("join", MaintenanceTypeCode.ADDITION),

                Arguments.of("modify", MaintenanceTypeCode.CHANGE),
                Arguments.of("update", MaintenanceTypeCode.CHANGE),
                Arguments.of("edit", MaintenanceTypeCode.CHANGE),
                Arguments.of("alter", MaintenanceTypeCode.CHANGE),
                Arguments.of("amend", MaintenanceTypeCode.CHANGE),
                Arguments.of("revise", MaintenanceTypeCode.CHANGE),

                Arguments.of("cancel", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("terminate", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("end", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("delete", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("remove", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("stop", MaintenanceTypeCode.CANCELLATION),

                Arguments.of("reinstate", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("restore", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("reactivate", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("resume", MaintenanceTypeCode.REINSTATEMENT),

                Arguments.of("id change", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("change id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("update id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("new member id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),

                Arguments.of("address change", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("update address", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("new address", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("moved", MaintenanceTypeCode.CHANGE_ADDRESS)
        );
    }
}