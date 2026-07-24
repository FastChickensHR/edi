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

class TransactionTypeCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(TransactionTypeCode.class)
    void resolvesFromCodeNameAndDescription(TransactionTypeCode constant) {
        assertEquals(constant, TransactionTypeCode.fromString(constant.getCode()));
        assertEquals(constant, TransactionTypeCode.fromString(constant.name()));
        assertEquals(constant, TransactionTypeCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, TransactionTypeCode expected) {
        assertEquals(expected, TransactionTypeCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("location address", TransactionTypeCode.LOCATION_ADDRESS),
                Arguments.of("address message", TransactionTypeCode.LOCATION_ADDRESS),
                Arguments.of("tracking control", TransactionTypeCode.UNIQUE_TRACKING_CONTROL),
                Arguments.of("control report", TransactionTypeCode.UNIQUE_TRACKING_CONTROL),
                Arguments.of("tracking reconciliation", TransactionTypeCode.UNIQUE_TRACKING_RECONCILIATION),
                Arguments.of("report reconciliation", TransactionTypeCode.UNIQUE_TRACKING_RECONCILIATION),
                Arguments.of("data change", TransactionTypeCode.UNIQUE_TRACKING_DATA_CHANGE),
                Arguments.of("item data change", TransactionTypeCode.UNIQUE_TRACKING_DATA_CHANGE),
                Arguments.of("new enrollment", TransactionTypeCode.NEW_GROUP_ENROLLMENT),
                Arguments.of("initial enrollment", TransactionTypeCode.NEW_GROUP_ENROLLMENT),
                Arguments.of("location relation", TransactionTypeCode.LOCATION_RELATION),
                Arguments.of("relation information", TransactionTypeCode.LOCATION_RELATION),
                Arguments.of("report", TransactionTypeCode.REPORT_MESSAGE),
                Arguments.of("report message", TransactionTypeCode.REPORT_MESSAGE),
                Arguments.of("supporting info", TransactionTypeCode.SUPPORTING_INFORMATION),
                Arguments.of("support information", TransactionTypeCode.SUPPORTING_INFORMATION),
                Arguments.of("email", TransactionTypeCode.ELECTRONIC_MAIL),
                Arguments.of("e-mail", TransactionTypeCode.ELECTRONIC_MAIL),
                Arguments.of("mail message", TransactionTypeCode.ELECTRONIC_MAIL),
                Arguments.of("coop request", TransactionTypeCode.REQUEST_FOR_COOP),
                Arguments.of("request coop", TransactionTypeCode.REQUEST_FOR_COOP),
                Arguments.of("guideline", TransactionTypeCode.GUIDELINES),
                Arguments.of("guidelines", TransactionTypeCode.GUIDELINES),
                Arguments.of("accomplishment renewal", TransactionTypeCode.ACCOMPLISHMENT_RENEWAL),
                Arguments.of("accomplishment based", TransactionTypeCode.ACCOMPLISHMENT_RENEWAL),
                Arguments.of("competitive", TransactionTypeCode.COMPETITIVE_RENEWAL),
                Arguments.of("comp renewal", TransactionTypeCode.COMPETITIVE_RENEWAL),
                Arguments.of("non-competitive", TransactionTypeCode.NON_COMPETITIVE_RENEWAL),
                Arguments.of("noncompetitive", TransactionTypeCode.NON_COMPETITIVE_RENEWAL),
                Arguments.of("resubmit", TransactionTypeCode.RESUBMISSION),
                Arguments.of("resubmission", TransactionTypeCode.RESUBMISSION),
                Arguments.of("supplement", TransactionTypeCode.SUPPLEMENTAL),
                Arguments.of("supplemental", TransactionTypeCode.SUPPLEMENTAL),
                Arguments.of("budget", TransactionTypeCode.BUDGET),
                Arguments.of("budget report", TransactionTypeCode.BUDGET),
                Arguments.of("commitment", TransactionTypeCode.COMMITMENT),
                Arguments.of("commit", TransactionTypeCode.COMMITMENT),
                Arguments.of("coop actual", TransactionTypeCode.COOP_ACTUAL),
                Arguments.of("co-op actual", TransactionTypeCode.COOP_ACTUAL),
                Arguments.of("distribution", TransactionTypeCode.DISTRIBUTION),
                Arguments.of("dist", TransactionTypeCode.DISTRIBUTION),
                Arguments.of("property", TransactionTypeCode.PROPERTY_TRANSACTION),
                Arguments.of("real estate", TransactionTypeCode.PROPERTY_TRANSACTION),
                Arguments.of("physician", TransactionTypeCode.PHYSICIAN_REPORT),
                Arguments.of("doctor report", TransactionTypeCode.PHYSICIAN_REPORT),
                Arguments.of("maintenance request", TransactionTypeCode.MAINTENANCE_REQUEST),
                Arguments.of("maint request", TransactionTypeCode.MAINTENANCE_REQUEST),
                Arguments.of("maintenance response", TransactionTypeCode.MAINTENANCE_RESPONSE),
                Arguments.of("maint response", TransactionTypeCode.MAINTENANCE_RESPONSE),
                Arguments.of("immediate no followup", TransactionTypeCode.REQUEST_IMMEDIATE_NO_FOLLOWUP),
                Arguments.of("no follow-up", TransactionTypeCode.REQUEST_IMMEDIATE_NO_FOLLOWUP),
                Arguments.of("immediate with followup", TransactionTypeCode.REQUEST_IMMEDIATE_WITH_FOLLOWUP),
                Arguments.of("with follow-up", TransactionTypeCode.REQUEST_IMMEDIATE_WITH_FOLLOWUP),
                Arguments.of("response to mailbox", TransactionTypeCode.REQUEST_RESPONSE_TO_MAILBOX),
                Arguments.of("mailbox response", TransactionTypeCode.REQUEST_RESPONSE_TO_MAILBOX),
                Arguments.of("no further updates", TransactionTypeCode.RESPONSE_NO_UPDATES),
                Arguments.of("no updates", TransactionTypeCode.RESPONSE_NO_UPDATES),
                Arguments.of("further updates", TransactionTypeCode.RESPONSE_UPDATES_FOLLOW),
                Arguments.of("updates follow", TransactionTypeCode.RESPONSE_UPDATES_FOLLOW),
                Arguments.of("air export", TransactionTypeCode.AIR_EXPORT_WAYBILL),
                Arguments.of("waybill", TransactionTypeCode.AIR_EXPORT_WAYBILL),
                Arguments.of("export waybill", TransactionTypeCode.AIR_EXPORT_WAYBILL));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> TransactionTypeCode.fromString(input));
    }
}
