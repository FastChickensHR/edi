/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaintenanceTypeCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(MaintenanceTypeCode.class)
    void resolvesFromCodeNameAndDescription(MaintenanceTypeCode constant) {
        assertEquals(constant, MaintenanceTypeCode.fromString(constant.getCode()));
        assertEquals(constant, MaintenanceTypeCode.fromString(constant.name()));
        assertEquals(constant, MaintenanceTypeCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, MaintenanceTypeCode expected) {
        assertEquals(expected, MaintenanceTypeCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("1", MaintenanceTypeCode.ADDITION),
                Arguments.of("add", MaintenanceTypeCode.ADDITION),
                Arguments.of("new", MaintenanceTypeCode.ADDITION),
                Arguments.of("enroll", MaintenanceTypeCode.ADDITION),
                Arguments.of("enrollment", MaintenanceTypeCode.ADDITION),
                Arguments.of("create", MaintenanceTypeCode.ADDITION),
                Arguments.of("join", MaintenanceTypeCode.ADDITION),
                Arguments.of("2", MaintenanceTypeCode.CHANGE),
                Arguments.of("modify", MaintenanceTypeCode.CHANGE),
                Arguments.of("update", MaintenanceTypeCode.CHANGE),
                Arguments.of("edit", MaintenanceTypeCode.CHANGE),
                Arguments.of("alter", MaintenanceTypeCode.CHANGE),
                Arguments.of("amend", MaintenanceTypeCode.CHANGE),
                Arguments.of("revise", MaintenanceTypeCode.CHANGE),
                Arguments.of("3", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("cancel", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("terminate", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("end", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("delete", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("remove", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("stop", MaintenanceTypeCode.CANCELLATION),
                Arguments.of("4", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("reinstate", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("restore", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("reactivate", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("resume", MaintenanceTypeCode.REINSTATEMENT),
                Arguments.of("21", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("id change", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("change id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("update id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("new member id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("member id update", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("subscriber id", MaintenanceTypeCode.CHANGE_WITH_MEMBER_ID),
                Arguments.of("22", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("address change", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("update address", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("new address", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("moved", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("relocation", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("address update", MaintenanceTypeCode.CHANGE_ADDRESS),
                Arguments.of("23", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("demographic change", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("change name", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("update personal info", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("personal information", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("name change", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("gender change", MaintenanceTypeCode.CHANGE_DEMOGRAPHIC),
                Arguments.of("24", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("benefit change", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("update benefits", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("change coverage", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("plan change", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("coverage update", MaintenanceTypeCode.CHANGE_BENEFIT_COVERAGE),
                Arguments.of("25", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("billing change", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("payment update", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("update payment", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("payment method", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("banking info", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("bank account", MaintenanceTypeCode.CHANGE_BILLING),
                Arguments.of("26", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("employment change", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("job change", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("employment update", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("job status", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("position change", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("department change", MaintenanceTypeCode.CHANGE_EMPLOYMENT),
                Arguments.of("30", MaintenanceTypeCode.COBRA_ELECTION),
                Arguments.of("cobra", MaintenanceTypeCode.COBRA_ELECTION),
                Arguments.of("elect cobra", MaintenanceTypeCode.COBRA_ELECTION),
                Arguments.of("cobra enrollment", MaintenanceTypeCode.COBRA_ELECTION),
                Arguments.of("consolidated omnibus", MaintenanceTypeCode.COBRA_ELECTION),
                Arguments.of("continuation coverage", MaintenanceTypeCode.COBRA_ELECTION));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> MaintenanceTypeCode.fromString(input));
    }
}
