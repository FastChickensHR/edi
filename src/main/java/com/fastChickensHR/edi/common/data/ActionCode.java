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
 * Enumeration representing Action Codes used in EDI transactions.
 * These codes define specific actions to be taken on data.
 */
@Getter
public enum ActionCode implements EdiCodeEnum {
    AUTHORIZE("0", "Authorize"),
    AUTHORIZE_AND_SETTLE("00", "Authorize and Settle Combination"),
    ADD("1", "Add"),
    CHANGE("2", "Change (Update)"),
    DELETE("3", "Delete"),
    VERIFY("4", "Verify"),
    SEND("5", "Send"),
    RECEIVE("6", "Receive"),
    REQUEST("7", "Request"),
    IN_PRODUCTION_SEND("8", "In Production Send"),
    NOT_CAPABLE("9", "Not Capable of Taking Action"),
    ADJOURN("10", "Adjourn"),
    APPROVE("11", "Approve"),
    AUCTION("12", "Auction"),
    CLEARED("13", "Cleared"),
    COMPOSE("14", "Compose"),
    CORRECT_RESUBMIT_CLAIM("15", "Correct and Resubmit Claim"),
    CONSIDER("16", "Consider"),
    CREATE("17", "Create"),
    DECIDE("18", "Decide"),
    DECLARE("19", "Declare"),
    DECREE_RECALL("20", "Decree Recall"),
    DISAPPROVE("21", "Disapprove"),
    DISSOLVE("22", "Dissolve"),
    ESCALATION("23", "Escalation"),
    ON_HOLD("24", "On-Hold"),
    DROPPED("25", "Dropped"),
    BANKRUPTCY_FILED("26", "Bankruptcy Filed - Review Account"),
    MOVED_FOLLOW_UP("27", "Moved - Follow Up"),
    CHANGE_PHONE_NUMBER("28", "Change Phone Number"),
    PAYMENT_RECEIVED("29", "Payment Received - Follow Up"),
    ACCOUNT_ACTIVE("30", "Account Active - Pursue"),
    RETURN_PER_CLIENT("31", "Return per Client Request"),
    PURSUE_LEGAL_ACTION("32", "Pursue Legal Action"),
    ACTIVE("33", "Active"),
    PURSUE_GARNISHMENT("34", "Pursue Garnishment"),
    NEW_ASSIGNMENT("35", "New Assignment - Proceed"),
    REPOSSESS_MERCHANDISE("36", "Repossess Merchandise"),
    ADJUST_PAYMENT("37", "Adjust Payment"),
    CHANGE_ADDRESS("38", "Change Address"),
    SKIPTRACE_ACCOUNT("39", "Skiptrace Account"),
    CLOSE_ACCOUNT_DECEASED("40", "Close Account - Deceased"),
    UPDATE_TO_INACTIVE("41", "Update to Inactive"),
    ACCOUNT_PAID_CLOSE("42", "Account Paid in Full - Close Account"),
    REFUSED_TO_PAY("43", "Refused to Pay - Review Account"),
    ACCOUNT_DISPUTED("44", "Account Disputed - Review"),
    DO_NOT_CONTACT("45", "Do Not Contact - Fair Debt Collection Practices Act (FDCPA)"),
    FORWARD_ACCOUNT("46", "Forward Account"),
    ENFORCE("47", "Enforce"),
    EXTINGUISH("48", "Extinguish"),
    JUDGMENT_DEFENDANT("49", "Judgment for Defendant"),
    JUDGMENT_PLAINTIFF("50", "Judgment for Plaintiff"),
    COMPLETE("51", "Complete"),
    JUSTIFIED("52", "Justified"),
    LEGAL_MORATORIUM("53", "Legal Moratorium on Debts Incurred to Date"),
    MEETING_HELD("54", "Meeting Held"),
    MEETING_HELD_OPENED("55", "Meeting Held and Opened"),
    MORATORIUM("56", "Moratorium"),
    NOT_FILED("57", "Not Filed"),
    NOT_JUSTIFIED("58", "Not Justified"),
    PARTIAL_RELEASE("59", "Partial Release"),
    PROVISIONAL_MORATORIUM("60", "Provisional Moratorium"),
    READJUDICATE("61", "Readjudicate"),
    RESOLVE("62", "Resolve"),
    RESULTED_IN_SUIT("63", "Resulted in a Suit"),
    RESULTED_NO_LIQUIDATION("64", "Resulted in No Liquidation"),
    SET_ASIDE("65", "Set Aside"),
    SETTLED_OUT_OF_COURT("66", "Settled out of Court"),
    SOLD("67", "Sold"),
    STAYED("68", "Stayed"),
    SUBORDINATION("69", "Subordination"),
    SURRENDER("70", "Surrender"),
    TERM_EXPIRED("71", "Term Expired"),
    UNSATISFIED("72", "Unsatisfied"),
    VOID("73", "Void"),
    SUSPENDED("74", "Suspended, 24 Hours"),
    DISPUTE("75", "Dispute"),
    ASSIGN("76", "Assign"),
    AGENT_CHANGE("77", "Agent Change"),
    AGENT_HIERARCHY_CHANGE("78", "Agent Hierarchy Change"),
    REACTIVATE("79", "Reactivate"),
    RECONCILE("80", "Reconcile"),
    RENEW("81", "Renew"),
    FOLLOW_UP("82", "Follow Up"),
    FUTURE("83", "Future"),
    LETTER_OF_AUTHORITY("84", "Letter of Authority Sent"),
    NEW_PREMIUM_ONLY("85", "New Premium Only"),
    PENDED_FOR_FOLLOW_UP("86", "Pended for Follow Up"),
    COUNTERSUE("87", "Countersue"),
    CONTACT_VIA_TELEPHONE("88", "Contact via Telephone Call"),
    CONTACT_VIA_FAX("89", "Contact via Fax"),
    MARK("90", "Mark"),
    IN_PROGRESS("91", "In Progress"),
    RECONFIRM("92", "Reconfirm"),
    SEND_RECORD_FALL("93", "Send Record at End of the Fall Term"),
    SEND_RECORD_WINTER("94", "Send Record at End of the Winter Term"),
    SEND_RECORD_SPRING("95", "Send Record at End of the Spring Term"),
    SEND_RECORD_SUMMER("96", "Send Record at End of the Summer Term"),
    SEND_RECORD_INTERSESSION("97", "Send Record at End of the Intersession Term");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<ActionCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                ActionCode.class,
                "Action Code",
                Map.<String, ActionCode>ofEntries(
                        Map.entry("auth", AUTHORIZE),
                        Map.entry("authorize", AUTHORIZE),
                        Map.entry("authorization", AUTHORIZE),

                        Map.entry("auth and settle", AUTHORIZE_AND_SETTLE),
                        Map.entry("authorize settle", AUTHORIZE_AND_SETTLE),
                        Map.entry("combination", AUTHORIZE_AND_SETTLE),

                        Map.entry("add", ADD),
                        Map.entry("create entry", ADD),
                        Map.entry("insert", ADD),

                        Map.entry("change", CHANGE),
                        Map.entry("update", CHANGE),
                        Map.entry("modify", CHANGE),
                        Map.entry("edit", CHANGE),

                        Map.entry("delete", DELETE),
                        Map.entry("remove", DELETE),
                        Map.entry("erase", DELETE),

                        Map.entry("verify", VERIFY),
                        Map.entry("validate", VERIFY),
                        Map.entry("check", VERIFY),

                        Map.entry("send", SEND),
                        Map.entry("transmit", SEND),
                        Map.entry("deliver", SEND),

                        Map.entry("receive", RECEIVE),
                        Map.entry("accept", RECEIVE),
                        Map.entry("get", RECEIVE),

                        Map.entry("request", REQUEST),
                        Map.entry("ask", REQUEST),
                        Map.entry("inquire", REQUEST),

                        Map.entry("production send", IN_PRODUCTION_SEND),
                        Map.entry("live send", IN_PRODUCTION_SEND),

                        Map.entry("not capable", NOT_CAPABLE),
                        Map.entry("unable", NOT_CAPABLE),
                        Map.entry("incapable", NOT_CAPABLE),

                        Map.entry("approve", APPROVE),
                        Map.entry("approval", APPROVE),
                        Map.entry("accepted", APPROVE),

                        Map.entry("disapprove", DISAPPROVE),
                        Map.entry("reject", DISAPPROVE),
                        Map.entry("denied", DISAPPROVE),

                        Map.entry("create", CREATE),
                        Map.entry("generate", CREATE),
                        Map.entry("new", CREATE),

                        Map.entry("on hold", ON_HOLD),
                        Map.entry("hold", ON_HOLD),
                        Map.entry("pause", ON_HOLD),

                        Map.entry("active", ACTIVE),
                        Map.entry("activated", ACTIVE),

                        Map.entry("complete", COMPLETE),
                        Map.entry("completed", COMPLETE),
                        Map.entry("finished", COMPLETE),
                        Map.entry("done", COMPLETE),

                        Map.entry("reactivate", REACTIVATE),
                        Map.entry("restore", REACTIVATE),
                        Map.entry("reinstate", REACTIVATE),

                        Map.entry("follow up", FOLLOW_UP),
                        Map.entry("followup", FOLLOW_UP),

                        Map.entry("in progress", IN_PROGRESS),
                        Map.entry("ongoing", IN_PROGRESS),
                        Map.entry("processing", IN_PROGRESS)
                )
        );
    }

    ActionCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an ActionCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching ActionCode
     * @throws IllegalArgumentException if no match is found
     */
    public static ActionCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}