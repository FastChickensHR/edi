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

class ActionCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("0", ActionCode.AUTHORIZE.getCode());
        assertEquals("Authorize", ActionCode.AUTHORIZE.getDescription());

        assertEquals("00", ActionCode.AUTHORIZE_AND_SETTLE.getCode());
        assertEquals("Authorize and Settle Combination", ActionCode.AUTHORIZE_AND_SETTLE.getDescription());

        assertEquals("1", ActionCode.ADD.getCode());
        assertEquals("Add", ActionCode.ADD.getDescription());

        assertEquals("2", ActionCode.CHANGE.getCode());
        assertEquals("Change (Update)", ActionCode.CHANGE.getDescription());

        assertEquals("3", ActionCode.DELETE.getCode());
        assertEquals("Delete", ActionCode.DELETE.getDescription());

        assertEquals("17", ActionCode.CREATE.getCode());
        assertEquals("Create", ActionCode.CREATE.getDescription());

        assertEquals("51", ActionCode.COMPLETE.getCode());
        assertEquals("Complete", ActionCode.COMPLETE.getDescription());

        assertEquals("91", ActionCode.IN_PROGRESS.getCode());
        assertEquals("In Progress", ActionCode.IN_PROGRESS.getDescription());

        assertEquals("97", ActionCode.SEND_RECORD_INTERSESSION.getCode());
        assertEquals("Send Record at End of the Intersession Term", ActionCode.SEND_RECORD_INTERSESSION.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (ActionCode actionCode : ActionCode.values()) {
            assertNotNull(actionCode.getCode(), "Code should not be null for " + actionCode.name());
            assertNotNull(actionCode.getDescription(), "Description should not be null for " + actionCode.name());
            assertFalse(actionCode.getCode().isEmpty(), "Code should not be empty for " + actionCode.name());
            assertFalse(actionCode.getDescription().isEmpty(), "Description should not be empty for " + actionCode.name());
        }
    }

    @Test
    void testFromString() {
        assertEquals(ActionCode.AUTHORIZE, ActionCode.fromString("0"));
        assertEquals(ActionCode.ADD, ActionCode.fromString("1"));
        assertEquals(ActionCode.CHANGE, ActionCode.fromString("2"));
        assertEquals(ActionCode.DELETE, ActionCode.fromString("3"));
        assertEquals(ActionCode.CREATE, ActionCode.fromString("17"));
        assertEquals(ActionCode.COMPLETE, ActionCode.fromString("51"));

        assertEquals(ActionCode.ADD, ActionCode.fromString("add"));
        assertEquals(ActionCode.ADD, ActionCode.fromString("Add"));
        assertEquals(ActionCode.ADD, ActionCode.fromString("ADD"));
        assertEquals(ActionCode.ADD, ActionCode.fromString(" add "));

        assertEquals(ActionCode.AUTHORIZE, ActionCode.fromString("Authorize"));
        assertEquals(ActionCode.CHANGE, ActionCode.fromString("Change (Update)"));
        assertEquals(ActionCode.DELETE, ActionCode.fromString("Delete"));

        assertEquals(ActionCode.ADD, ActionCode.fromString("insert"));
        assertEquals(ActionCode.CHANGE, ActionCode.fromString("update"));
        assertEquals(ActionCode.CHANGE, ActionCode.fromString("modify"));
        assertEquals(ActionCode.DELETE, ActionCode.fromString("remove"));
        assertEquals(ActionCode.VERIFY, ActionCode.fromString("validate"));
        assertEquals(ActionCode.APPROVE, ActionCode.fromString("approval"));
        assertEquals(ActionCode.DISAPPROVE, ActionCode.fromString("reject"));
        assertEquals(ActionCode.CREATE, ActionCode.fromString("generate"));
        assertEquals(ActionCode.ON_HOLD, ActionCode.fromString("hold"));
        assertEquals(ActionCode.COMPLETE, ActionCode.fromString("finished"));
        assertEquals(ActionCode.REACTIVATE, ActionCode.fromString("restore"));

        assertThrows(IllegalArgumentException.class, () -> ActionCode.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> ActionCode.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> ActionCode.fromString("invalid_code"));
        assertThrows(IllegalArgumentException.class, () -> ActionCode.fromString("999"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, ActionCode expected) throws Exception {
        Field lookupField = ActionCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        Object lookupObject = lookupField.get(null);

        assertTrue(lookupObject instanceof EdiEnumLookup);

        @SuppressWarnings("unchecked")
        EdiEnumLookup<ActionCode> lookup = (EdiEnumLookup<ActionCode>) lookupObject;

        assertEquals(expected, lookup.fromString(input));
        assertEquals(expected, ActionCode.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("0", ActionCode.AUTHORIZE),
                Arguments.of("00", ActionCode.AUTHORIZE_AND_SETTLE),
                Arguments.of("1", ActionCode.ADD),
                Arguments.of("2", ActionCode.CHANGE),
                Arguments.of("3", ActionCode.DELETE),
                Arguments.of("4", ActionCode.VERIFY),
                Arguments.of("5", ActionCode.SEND),
                Arguments.of("17", ActionCode.CREATE),
                Arguments.of("51", ActionCode.COMPLETE),
                Arguments.of("79", ActionCode.REACTIVATE),
                Arguments.of("91", ActionCode.IN_PROGRESS),

                Arguments.of("AUTHORIZE", ActionCode.AUTHORIZE),
                Arguments.of("ADD", ActionCode.ADD),
                Arguments.of("CHANGE", ActionCode.CHANGE),
                Arguments.of("DELETE", ActionCode.DELETE),
                Arguments.of("CREATE", ActionCode.CREATE),
                Arguments.of("COMPLETE", ActionCode.COMPLETE),
                Arguments.of("IN_PROGRESS", ActionCode.IN_PROGRESS),

                Arguments.of("Authorize", ActionCode.AUTHORIZE),
                Arguments.of("Add", ActionCode.ADD),
                Arguments.of("Change (Update)", ActionCode.CHANGE),
                Arguments.of("Delete", ActionCode.DELETE),
                Arguments.of("Create", ActionCode.CREATE),
                Arguments.of("Complete", ActionCode.COMPLETE),
                Arguments.of("In Progress", ActionCode.IN_PROGRESS),

                Arguments.of("auth", ActionCode.AUTHORIZE),
                Arguments.of("authorization", ActionCode.AUTHORIZE),
                Arguments.of("auth and settle", ActionCode.AUTHORIZE_AND_SETTLE),
                Arguments.of("insert", ActionCode.ADD),
                Arguments.of("update", ActionCode.CHANGE),
                Arguments.of("modify", ActionCode.CHANGE),
                Arguments.of("edit", ActionCode.CHANGE),
                Arguments.of("remove", ActionCode.DELETE),
                Arguments.of("erase", ActionCode.DELETE),
                Arguments.of("validate", ActionCode.VERIFY),
                Arguments.of("check", ActionCode.VERIFY),
                Arguments.of("transmit", ActionCode.SEND),
                Arguments.of("deliver", ActionCode.SEND),
                Arguments.of("accept", ActionCode.RECEIVE),
                Arguments.of("get", ActionCode.RECEIVE),
                Arguments.of("ask", ActionCode.REQUEST),
                Arguments.of("inquire", ActionCode.REQUEST),
                Arguments.of("unable", ActionCode.NOT_CAPABLE),
                Arguments.of("approval", ActionCode.APPROVE),
                Arguments.of("accepted", ActionCode.APPROVE),
                Arguments.of("reject", ActionCode.DISAPPROVE),
                Arguments.of("denied", ActionCode.DISAPPROVE),
                Arguments.of("generate", ActionCode.CREATE),
                Arguments.of("new", ActionCode.CREATE),
                Arguments.of("hold", ActionCode.ON_HOLD),
                Arguments.of("pause", ActionCode.ON_HOLD),
                Arguments.of("activated", ActionCode.ACTIVE),
                Arguments.of("completed", ActionCode.COMPLETE),
                Arguments.of("finished", ActionCode.COMPLETE),
                Arguments.of("done", ActionCode.COMPLETE),
                Arguments.of("restore", ActionCode.REACTIVATE),
                Arguments.of("reinstate", ActionCode.REACTIVATE),
                Arguments.of("followup", ActionCode.FOLLOW_UP),
                Arguments.of("ongoing", ActionCode.IN_PROGRESS),
                Arguments.of("processing", ActionCode.IN_PROGRESS)
        );
    }

    @Test
    void testToString() {
        assertEquals("0", ActionCode.AUTHORIZE.toString());
        assertEquals("00", ActionCode.AUTHORIZE_AND_SETTLE.toString());
        assertEquals("1", ActionCode.ADD.toString());
        assertEquals("2", ActionCode.CHANGE.toString());
        assertEquals("3", ActionCode.DELETE.toString());
        assertEquals("17", ActionCode.CREATE.toString());
        assertEquals("51", ActionCode.COMPLETE.toString());
        assertEquals("91", ActionCode.IN_PROGRESS.toString());
    }
}