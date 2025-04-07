/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Transaction Type Codes used in EDI transactions.
 * These codes identify the purpose and content of a transaction set.
 */
@Getter
public enum TransactionTypeCode implements EdiCodeEnum {
    LOCATION_ADDRESS("01", "Location Address Message"),
    UNIQUE_TRACKING_CONTROL("1A", "Unique Item Tracking Control Report"),
    UNIQUE_TRACKING_RECONCILIATION("1B", "Unique Item Tracking Report Reconciliation"),
    UNIQUE_TRACKING_DATA_CHANGE("1C", "Unique Item Tracking Item Data Change"),
    NEW_GROUP_ENROLLMENT("1E", "New Group Initial Enrollment"),
    LOCATION_RELATION("02", "Location Relation Information"),
    REPORT_MESSAGE("03", "Report Message"),
    SUPPORTING_INFORMATION("3M", "Supporting Information"),
    ELECTRONIC_MAIL("04", "Electronic Mail Message"),
    REQUEST_FOR_COOP("05", "Request for Co-op"),
    GUIDELINES("06", "Guidelines"),
    ACCOMPLISHMENT_RENEWAL("6A", "Accomplishment Based Renewal"),
    COMPETITIVE_RENEWAL("6C", "Competitive Renewal"),
    NON_COMPETITIVE_RENEWAL("6N", "Non-competitive Renewal"),
    RESUBMISSION("6R", "Resubmission"),
    SUPPLEMENTAL("6S", "Supplemental"),
    BUDGET("07", "Budget"),
    COMMITMENT("08", "Commitment"),
    COOP_ACTUAL("09", "Co-op Actual"),
    DISTRIBUTION("10", "Distribution"),
    PROPERTY_TRANSACTION("11", "National Property Registry System Real Estate Property Transaction"),
    PHYSICIAN_REPORT("12", "Physician's Report"),
    MAINTENANCE_REQUEST("13", "Maintenance Request"),
    MAINTENANCE_RESPONSE("14", "Maintenance Response"),
    REQUEST_IMMEDIATE_NO_FOLLOWUP("15", "Request with Immediate Response Required (No Follow-up)"),
    REQUEST_IMMEDIATE_WITH_FOLLOWUP("16", "Request with Immediate Response Required (Follow-up Required)"),
    REQUEST_RESPONSE_TO_MAILBOX("17", "Request with Immediate Response to Mailbox"),
    RESPONSE_NO_UPDATES("18", "Response - No Further Updates to Follow"),
    RESPONSE_UPDATES_FOLLOW("19", "Response - Further Updates to Follow"),
    AIR_EXPORT_WAYBILL("20", "Air Export Waybill and Invoice"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<TransactionTypeCode> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                TransactionTypeCode.class,
                "Transaction Type Code",
                Map.<String, TransactionTypeCode>ofEntries(
                        Map.entry("location address", LOCATION_ADDRESS),
                        Map.entry("address message", LOCATION_ADDRESS),

                        Map.entry("tracking control", UNIQUE_TRACKING_CONTROL),
                        Map.entry("control report", UNIQUE_TRACKING_CONTROL),

                        Map.entry("tracking reconciliation", UNIQUE_TRACKING_RECONCILIATION),
                        Map.entry("report reconciliation", UNIQUE_TRACKING_RECONCILIATION),

                        Map.entry("data change", UNIQUE_TRACKING_DATA_CHANGE),
                        Map.entry("item data change", UNIQUE_TRACKING_DATA_CHANGE),

                        Map.entry("new enrollment", NEW_GROUP_ENROLLMENT),
                        Map.entry("initial enrollment", NEW_GROUP_ENROLLMENT),

                        Map.entry("location relation", LOCATION_RELATION),
                        Map.entry("relation information", LOCATION_RELATION),

                        Map.entry("report", REPORT_MESSAGE),
                        Map.entry("report message", REPORT_MESSAGE),

                        Map.entry("supporting info", SUPPORTING_INFORMATION),
                        Map.entry("support information", SUPPORTING_INFORMATION),

                        Map.entry("email", ELECTRONIC_MAIL),
                        Map.entry("e-mail", ELECTRONIC_MAIL),
                        Map.entry("mail message", ELECTRONIC_MAIL),

                        Map.entry("coop request", REQUEST_FOR_COOP),
                        Map.entry("request coop", REQUEST_FOR_COOP),

                        Map.entry("guideline", GUIDELINES),
                        Map.entry("guidelines", GUIDELINES),

                        Map.entry("accomplishment renewal", ACCOMPLISHMENT_RENEWAL),
                        Map.entry("accomplishment based", ACCOMPLISHMENT_RENEWAL),

                        Map.entry("competitive", COMPETITIVE_RENEWAL),
                        Map.entry("comp renewal", COMPETITIVE_RENEWAL),

                        Map.entry("non-competitive", NON_COMPETITIVE_RENEWAL),
                        Map.entry("noncompetitive", NON_COMPETITIVE_RENEWAL),

                        Map.entry("resubmit", RESUBMISSION),
                        Map.entry("resubmission", RESUBMISSION),

                        Map.entry("supplement", SUPPLEMENTAL),
                        Map.entry("supplemental", SUPPLEMENTAL),

                        Map.entry("budget", BUDGET),
                        Map.entry("budget report", BUDGET),

                        Map.entry("commitment", COMMITMENT),
                        Map.entry("commit", COMMITMENT),

                        Map.entry("coop actual", COOP_ACTUAL),
                        Map.entry("co-op actual", COOP_ACTUAL),

                        Map.entry("distribution", DISTRIBUTION),
                        Map.entry("dist", DISTRIBUTION),

                        Map.entry("property", PROPERTY_TRANSACTION),
                        Map.entry("real estate", PROPERTY_TRANSACTION),

                        Map.entry("physician", PHYSICIAN_REPORT),
                        Map.entry("doctor report", PHYSICIAN_REPORT),

                        Map.entry("maintenance request", MAINTENANCE_REQUEST),
                        Map.entry("maint request", MAINTENANCE_REQUEST),

                        Map.entry("maintenance response", MAINTENANCE_RESPONSE),
                        Map.entry("maint response", MAINTENANCE_RESPONSE),

                        Map.entry("immediate no followup", REQUEST_IMMEDIATE_NO_FOLLOWUP),
                        Map.entry("no follow-up", REQUEST_IMMEDIATE_NO_FOLLOWUP),

                        Map.entry("immediate with followup", REQUEST_IMMEDIATE_WITH_FOLLOWUP),
                        Map.entry("with follow-up", REQUEST_IMMEDIATE_WITH_FOLLOWUP),

                        Map.entry("response to mailbox", REQUEST_RESPONSE_TO_MAILBOX),
                        Map.entry("mailbox response", REQUEST_RESPONSE_TO_MAILBOX),

                        Map.entry("no further updates", RESPONSE_NO_UPDATES),
                        Map.entry("no updates", RESPONSE_NO_UPDATES),

                        Map.entry("further updates", RESPONSE_UPDATES_FOLLOW),
                        Map.entry("updates follow", RESPONSE_UPDATES_FOLLOW),

                        Map.entry("air export", AIR_EXPORT_WAYBILL),
                        Map.entry("waybill", AIR_EXPORT_WAYBILL),
                        Map.entry("export waybill", AIR_EXPORT_WAYBILL)
                )
        );
    }

    TransactionTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a TransactionTypeCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching TransactionTypeCode
     * @throws IllegalArgumentException if no match is found
     */
    public static TransactionTypeCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}