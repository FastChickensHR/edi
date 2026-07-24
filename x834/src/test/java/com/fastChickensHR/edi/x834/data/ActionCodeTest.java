/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ActionCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(ActionCode.class)
    void resolvesFromCodeNameAndDescription(ActionCode constant) {
        assertEquals(constant, ActionCode.fromString(constant.getCode()));
        assertEquals(constant, ActionCode.fromString(constant.name()));
        assertEquals(constant, ActionCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, ActionCode expected) {
        assertEquals(expected, ActionCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("auth", ActionCode.AUTHORIZE),
                Arguments.of("authorize", ActionCode.AUTHORIZE),
                Arguments.of("authorization", ActionCode.AUTHORIZE),
                Arguments.of("auth and settle", ActionCode.AUTHORIZE_AND_SETTLE),
                Arguments.of("authorize settle", ActionCode.AUTHORIZE_AND_SETTLE),
                Arguments.of("combination", ActionCode.AUTHORIZE_AND_SETTLE),
                Arguments.of("add", ActionCode.ADD),
                Arguments.of("create entry", ActionCode.ADD),
                Arguments.of("insert", ActionCode.ADD),
                Arguments.of("change", ActionCode.CHANGE),
                Arguments.of("update", ActionCode.CHANGE),
                Arguments.of("modify", ActionCode.CHANGE),
                Arguments.of("edit", ActionCode.CHANGE),
                Arguments.of("delete", ActionCode.DELETE),
                Arguments.of("remove", ActionCode.DELETE),
                Arguments.of("erase", ActionCode.DELETE),
                Arguments.of("verify", ActionCode.VERIFY),
                Arguments.of("validate", ActionCode.VERIFY),
                Arguments.of("check", ActionCode.VERIFY),
                Arguments.of("send", ActionCode.SEND),
                Arguments.of("transmit", ActionCode.SEND),
                Arguments.of("deliver", ActionCode.SEND),
                Arguments.of("receive", ActionCode.RECEIVE),
                Arguments.of("accept", ActionCode.RECEIVE),
                Arguments.of("get", ActionCode.RECEIVE),
                Arguments.of("request", ActionCode.REQUEST),
                Arguments.of("ask", ActionCode.REQUEST),
                Arguments.of("inquire", ActionCode.REQUEST),
                Arguments.of("production send", ActionCode.IN_PRODUCTION_SEND),
                Arguments.of("live send", ActionCode.IN_PRODUCTION_SEND),
                Arguments.of("not capable", ActionCode.NOT_CAPABLE),
                Arguments.of("unable", ActionCode.NOT_CAPABLE),
                Arguments.of("incapable", ActionCode.NOT_CAPABLE),
                Arguments.of("approve", ActionCode.APPROVE),
                Arguments.of("approval", ActionCode.APPROVE),
                Arguments.of("accepted", ActionCode.APPROVE),
                Arguments.of("disapprove", ActionCode.DISAPPROVE),
                Arguments.of("reject", ActionCode.DISAPPROVE),
                Arguments.of("denied", ActionCode.DISAPPROVE),
                Arguments.of("create", ActionCode.CREATE),
                Arguments.of("generate", ActionCode.CREATE),
                Arguments.of("new", ActionCode.CREATE),
                Arguments.of("on hold", ActionCode.ON_HOLD),
                Arguments.of("hold", ActionCode.ON_HOLD),
                Arguments.of("pause", ActionCode.ON_HOLD),
                Arguments.of("active", ActionCode.ACTIVE),
                Arguments.of("activated", ActionCode.ACTIVE),
                Arguments.of("complete", ActionCode.COMPLETE),
                Arguments.of("completed", ActionCode.COMPLETE),
                Arguments.of("finished", ActionCode.COMPLETE),
                Arguments.of("done", ActionCode.COMPLETE),
                Arguments.of("reactivate", ActionCode.REACTIVATE),
                Arguments.of("restore", ActionCode.REACTIVATE),
                Arguments.of("reinstate", ActionCode.REACTIVATE),
                Arguments.of("follow up", ActionCode.FOLLOW_UP),
                Arguments.of("followup", ActionCode.FOLLOW_UP),
                Arguments.of("in progress", ActionCode.IN_PROGRESS),
                Arguments.of("ongoing", ActionCode.IN_PROGRESS),
                Arguments.of("processing", ActionCode.IN_PROGRESS));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> ActionCode.fromString(input));
    }
}
