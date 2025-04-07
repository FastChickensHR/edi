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
 * Enumeration representing Transaction Set Purpose Codes used in the EDI transactions,
 * specifically in BGN01 segment.
 */
@Getter
public enum TransactionSetPurposeCode implements EdiCodeEnum {
    ORIGINAL("00", "Original"),
    CANCELLATION("01", "Cancellation"),
    ADD("02", "Add"),
    DELETE("03", "Delete"),
    CHANGE("04", "Change"),
    REPLACE("05", "Replace"),
    CHARGEABLE_RESUBMISSION("5C", "Chargeable Resubmission"),
    CONFIRMATION("06", "Confirmation"),
    DUPLICATE("07", "Duplicate"),
    STATUS("08", "Status"),
    NOT_FOUND("10", "Not Found"),
    RESPONSE("11", "Response"),
    NOT_PROCESSED("12", "Not Processed"),
    REQUEST("13", "Request"),
    ADVANCE_NOTIFICATION("14", "Advance Notification"),
    RESUBMISSION("15", "Re-Submission"),
    PROPOSED("16", "Proposed"),
    CANCEL_TO_BE_REISSUED("17", "Cancel, to be Reissued"),
    REISSUE("18", "Reissue"),
    SELLER_INITIATED_CHANGE("19", "Seller initiated change"),
    FINAL_TRANSMISSION("20", "Final Transmission"),
    TRANSACTION_ON_HOLD("21", "Transaction on Hold"),
    INFORMATION_COPY("22", "Information Copy"),
    DRAFT("24", "Draft"),
    INCREMENTAL("25", "Incremental"),
    REPLACE_SPECIFIED_BUYERS_PARTS_ONLY("26", "Replace - Specified Buyers Parts Only"),
    VERIFY("27", "Verify"),
    QUERY("28", "Query"),
    RENEWAL("30", "Renewal"),
    ALLOWANCE_ADDITION("31", "Allowance/Addition"),
    RECOVERY_DEDUCTION("32", "Recovery/Deduction"),
    REQUEST_FOR_PAYMENT("33", "Request for Payment"),
    PAYMENT_DECLINED("34", "Payment Declined"),
    REQUEST_AUTHORITY("35", "Request Authority"),
    AUTHORITY_TO_DEDUCT_REPLY("36", "Authority to Deduct (Reply)"),
    AUTHORITY_DECLINED_REPLY("37", "Authority Declined (Reply)"),
    NO_FINANCIAL_VALUE("38", "No Financial Value"),
    RESPONSE_TO_PROPOSED_TRIP_PLAN("39", "Response to Proposed Trip Plan"),
    COMMITMENT_ADVICE("40", "Commitment Advice"),
    CORRECTED_AND_VERIFIED("41", "Corrected and Verified"),
    TEMPORARY_RECORD("42", "Temporary Record"),
    REQUEST_PERMISSION_TO_SERVICE("43", "Request Permission to Service"),
    REJECTION("44", "Rejection"),
    FOLLOW_UP("45", "Follow-up"),
    CANCELLATION_WITH_REFUND("46", "Cancellation with Refund"),
    TRANSFER("47", "Transfer"),
    SUSPENDED("48", "Suspended"),
    ORIGINAL_NO_RESPONSE_NECESSARY("49", "Original - No Response Necessary"),
    REGISTER("50", "Register"),
    HISTORICAL_INQUIRY("51", "Historical Inquiry"),
    RESPONSE_TO_HISTORICAL_INQUIRY("52", "Response to Historical Inquiry"),
    COMPLETION("53", "Completion"),
    APPROVAL("54", "Approval"),
    EXCAVATION("55", "Excavation"),
    EXPIRATION_NOTIFICATION("56", "Expiration Notification"),
    INITIAL("57", "Initial"),
    SIMULATION_EXERCISE("77", "Simulation Exercise"),
    COMPLETION_NOTIFICATION("CN", "Completion Notification"),
    CORRECTED("CO", "Corrected"),
    DELEGATE_TO_ALTERNATIVE("DA", "Delegate to Alternative"),
    EXHIBIT_DISPOSITION("ED", "Exhibit Disposition"),
    EXHIBIT_RECEIPT("ER", "Exhibit Receipt"),
    FINAL_LOADING_CONFIGURATION("EX", "Final Loading Configuration"),
    FORWARD_TO_ACTION_POINT("FA", "Forward to Action Point"),
    FORWARD_TO_CONTRACTOR("FC", "Forward to Contractor"),
    FORWARD_TO_SUPPORT_POINT("FS", "Forward to Support Point"),
    GRANTED("GR", "Granted"),
    MATERIEL_DISPOSITION("MD", "Materiel Disposition"),
    PROPOSED_LOADING_CONFIGURATION("PR", "Proposed Loading Configuration"),
    RELEASE_HOLD("RH", "Release Hold"),
    REOPEN("RO", "Re-open"),
    REPLY_REBUTTAL("RR", "Reply Rebuttal"),
    REVISED_LOADING_CONFIGURATION("RV", "Revised Loading Configuration"),
    SCAN_BASED_TRADING("SB", "Scan Based Trading"),
    STATUS_UPDATE("SU", "Status Update"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<TransactionSetPurposeCode> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                TransactionSetPurposeCode.class,
                "Transaction Set Purpose Code",
                Map.ofEntries(
                        Map.entry("new", ORIGINAL),
                        Map.entry("initial submission", ORIGINAL),

                        Map.entry("void", CANCELLATION),
                        Map.entry("cancel", CANCELLATION),
                        Map.entry("terminate", CANCELLATION),

                        Map.entry("addition", ADD),
                        Map.entry("insert", ADD),
                        Map.entry("create", ADD),

                        Map.entry("remove", DELETE),
                        Map.entry("erase", DELETE),

                        Map.entry("modify", CHANGE),
                        Map.entry("update", CHANGE),
                        Map.entry("amend", CHANGE),

                        Map.entry("substitution", REPLACE),
                        Map.entry("swap", REPLACE),

                        Map.entry("chargeable", CHARGEABLE_RESUBMISSION),
                        Map.entry("chargeable resubmit", CHARGEABLE_RESUBMISSION),

                        Map.entry("confirm", CONFIRMATION),
                        Map.entry("verified", CONFIRMATION),

                        Map.entry("copy", DUPLICATE),
                        Map.entry("replicate", DUPLICATE),

                        Map.entry("status update", STATUS),
                        Map.entry("status check", STATUS),

                        Map.entry("inquiry", REQUEST),
                        Map.entry("asking", REQUEST),

                        Map.entry("resubmit", RESUBMISSION),
                        Map.entry("resend", RESUBMISSION),

                        Map.entry("complete", COMPLETION_NOTIFICATION),
                        Map.entry("finished", COMPLETION_NOTIFICATION),

                        Map.entry("custom", MUTUALLY_DEFINED),
                        Map.entry("agreed upon", MUTUALLY_DEFINED)
                )
        );
    }

    TransactionSetPurposeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a TransactionSetPurposeCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching TransactionSetPurposeCode
     * @throws IllegalArgumentException if no match is found
     */
    public static TransactionSetPurposeCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}