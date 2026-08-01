/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import java.util.Map;
import lombok.Getter;

/**
 * Code values for the X12 Action Code (data element 306), a shared ASC X12 code set that specifies
 * the action to be taken on the associated data. Exposed by the X12 834 (005010X220A1) library as a
 * reusable code list.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum ActionCode implements EdiCodeEnum {
  /** Authorize — X12 code "0". */
  AUTHORIZE("0", "Authorize"),
  /** Authorize and Settle Combination — X12 code "00". */
  AUTHORIZE_AND_SETTLE("00", "Authorize and Settle Combination"),
  /** Add — X12 code "1". */
  ADD("1", "Add"),
  /** Change (Update) — X12 code "2". */
  CHANGE("2", "Change (Update)"),
  /** Delete — X12 code "3". */
  DELETE("3", "Delete"),
  /** Verify — X12 code "4". */
  VERIFY("4", "Verify"),
  /** Send — X12 code "5". */
  SEND("5", "Send"),
  /** Receive — X12 code "6". */
  RECEIVE("6", "Receive"),
  /** Request — X12 code "7". */
  REQUEST("7", "Request"),
  /** In Production Send — X12 code "8". */
  IN_PRODUCTION_SEND("8", "In Production Send"),
  /** Not Capable of Taking Action — X12 code "9". */
  NOT_CAPABLE("9", "Not Capable of Taking Action"),
  /** Adjourn — X12 code "10". */
  ADJOURN("10", "Adjourn"),
  /** Approve — X12 code "11". */
  APPROVE("11", "Approve"),
  /** Auction — X12 code "12". */
  AUCTION("12", "Auction"),
  /** Cleared — X12 code "13". */
  CLEARED("13", "Cleared"),
  /** Compose — X12 code "14". */
  COMPOSE("14", "Compose"),
  /** Correct and Resubmit Claim — X12 code "15". */
  CORRECT_RESUBMIT_CLAIM("15", "Correct and Resubmit Claim"),
  /** Consider — X12 code "16". */
  CONSIDER("16", "Consider"),
  /** Create — X12 code "17". */
  CREATE("17", "Create"),
  /** Decide — X12 code "18". */
  DECIDE("18", "Decide"),
  /** Declare — X12 code "19". */
  DECLARE("19", "Declare"),
  /** Decree Recall — X12 code "20". */
  DECREE_RECALL("20", "Decree Recall"),
  /** Disapprove — X12 code "21". */
  DISAPPROVE("21", "Disapprove"),
  /** Dissolve — X12 code "22". */
  DISSOLVE("22", "Dissolve"),
  /** Escalation — X12 code "23". */
  ESCALATION("23", "Escalation"),
  /** On-Hold — X12 code "24". */
  ON_HOLD("24", "On-Hold"),
  /** Dropped — X12 code "25". */
  DROPPED("25", "Dropped"),
  /** Bankruptcy Filed - Review Account — X12 code "26". */
  BANKRUPTCY_FILED("26", "Bankruptcy Filed - Review Account"),
  /** Moved - Follow Up — X12 code "27". */
  MOVED_FOLLOW_UP("27", "Moved - Follow Up"),
  /** Change Phone Number — X12 code "28". */
  CHANGE_PHONE_NUMBER("28", "Change Phone Number"),
  /** Payment Received - Follow Up — X12 code "29". */
  PAYMENT_RECEIVED("29", "Payment Received - Follow Up"),
  /** Account Active - Pursue — X12 code "30". */
  ACCOUNT_ACTIVE("30", "Account Active - Pursue"),
  /** Return per Client Request — X12 code "31". */
  RETURN_PER_CLIENT("31", "Return per Client Request"),
  /** Pursue Legal Action — X12 code "32". */
  PURSUE_LEGAL_ACTION("32", "Pursue Legal Action"),
  /** Active — X12 code "33". */
  ACTIVE("33", "Active"),
  /** Pursue Garnishment — X12 code "34". */
  PURSUE_GARNISHMENT("34", "Pursue Garnishment"),
  /** New Assignment - Proceed — X12 code "35". */
  NEW_ASSIGNMENT("35", "New Assignment - Proceed"),
  /** Repossess Merchandise — X12 code "36". */
  REPOSSESS_MERCHANDISE("36", "Repossess Merchandise"),
  /** Adjust Payment — X12 code "37". */
  ADJUST_PAYMENT("37", "Adjust Payment"),
  /** Change Address — X12 code "38". */
  CHANGE_ADDRESS("38", "Change Address"),
  /** Skiptrace Account — X12 code "39". */
  SKIPTRACE_ACCOUNT("39", "Skiptrace Account"),
  /** Close Account - Deceased — X12 code "40". */
  CLOSE_ACCOUNT_DECEASED("40", "Close Account - Deceased"),
  /** Update to Inactive — X12 code "41". */
  UPDATE_TO_INACTIVE("41", "Update to Inactive"),
  /** Account Paid in Full - Close Account — X12 code "42". */
  ACCOUNT_PAID_CLOSE("42", "Account Paid in Full - Close Account"),
  /** Refused to Pay - Review Account — X12 code "43". */
  REFUSED_TO_PAY("43", "Refused to Pay - Review Account"),
  /** Account Disputed - Review — X12 code "44". */
  ACCOUNT_DISPUTED("44", "Account Disputed - Review"),
  /** Do Not Contact - Fair Debt Collection Practices Act (FDCPA) — X12 code "45". */
  DO_NOT_CONTACT("45", "Do Not Contact - Fair Debt Collection Practices Act (FDCPA)"),
  /** Forward Account — X12 code "46". */
  FORWARD_ACCOUNT("46", "Forward Account"),
  /** Enforce — X12 code "47". */
  ENFORCE("47", "Enforce"),
  /** Extinguish — X12 code "48". */
  EXTINGUISH("48", "Extinguish"),
  /** Judgment for Defendant — X12 code "49". */
  JUDGMENT_DEFENDANT("49", "Judgment for Defendant"),
  /** Judgment for Plaintiff — X12 code "50". */
  JUDGMENT_PLAINTIFF("50", "Judgment for Plaintiff"),
  /** Complete — X12 code "51". */
  COMPLETE("51", "Complete"),
  /** Justified — X12 code "52". */
  JUSTIFIED("52", "Justified"),
  /** Legal Moratorium on Debts Incurred to Date — X12 code "53". */
  LEGAL_MORATORIUM("53", "Legal Moratorium on Debts Incurred to Date"),
  /** Meeting Held — X12 code "54". */
  MEETING_HELD("54", "Meeting Held"),
  /** Meeting Held and Opened — X12 code "55". */
  MEETING_HELD_OPENED("55", "Meeting Held and Opened"),
  /** Moratorium — X12 code "56". */
  MORATORIUM("56", "Moratorium"),
  /** Not Filed — X12 code "57". */
  NOT_FILED("57", "Not Filed"),
  /** Not Justified — X12 code "58". */
  NOT_JUSTIFIED("58", "Not Justified"),
  /** Partial Release — X12 code "59". */
  PARTIAL_RELEASE("59", "Partial Release"),
  /** Provisional Moratorium — X12 code "60". */
  PROVISIONAL_MORATORIUM("60", "Provisional Moratorium"),
  /** Readjudicate — X12 code "61". */
  READJUDICATE("61", "Readjudicate"),
  /** Resolve — X12 code "62". */
  RESOLVE("62", "Resolve"),
  /** Resulted in a Suit — X12 code "63". */
  RESULTED_IN_SUIT("63", "Resulted in a Suit"),
  /** Resulted in No Liquidation — X12 code "64". */
  RESULTED_NO_LIQUIDATION("64", "Resulted in No Liquidation"),
  /** Set Aside — X12 code "65". */
  SET_ASIDE("65", "Set Aside"),
  /** Settled out of Court — X12 code "66". */
  SETTLED_OUT_OF_COURT("66", "Settled out of Court"),
  /** Sold — X12 code "67". */
  SOLD("67", "Sold"),
  /** Stayed — X12 code "68". */
  STAYED("68", "Stayed"),
  /** Subordination — X12 code "69". */
  SUBORDINATION("69", "Subordination"),
  /** Surrender — X12 code "70". */
  SURRENDER("70", "Surrender"),
  /** Term Expired — X12 code "71". */
  TERM_EXPIRED("71", "Term Expired"),
  /** Unsatisfied — X12 code "72". */
  UNSATISFIED("72", "Unsatisfied"),
  /** Void — X12 code "73". */
  VOID("73", "Void"),
  /** Suspended, 24 Hours — X12 code "74". */
  SUSPENDED("74", "Suspended, 24 Hours"),
  /** Dispute — X12 code "75". */
  DISPUTE("75", "Dispute"),
  /** Assign — X12 code "76". */
  ASSIGN("76", "Assign"),
  /** Agent Change — X12 code "77". */
  AGENT_CHANGE("77", "Agent Change"),
  /** Agent Hierarchy Change — X12 code "78". */
  AGENT_HIERARCHY_CHANGE("78", "Agent Hierarchy Change"),
  /** Reactivate — X12 code "79". */
  REACTIVATE("79", "Reactivate"),
  /** Reconcile — X12 code "80". */
  RECONCILE("80", "Reconcile"),
  /** Renew — X12 code "81". */
  RENEW("81", "Renew"),
  /** Follow Up — X12 code "82". */
  FOLLOW_UP("82", "Follow Up"),
  /** Future — X12 code "83". */
  FUTURE("83", "Future"),
  /** Letter of Authority Sent — X12 code "84". */
  LETTER_OF_AUTHORITY("84", "Letter of Authority Sent"),
  /** New Premium Only — X12 code "85". */
  NEW_PREMIUM_ONLY("85", "New Premium Only"),
  /** Pended for Follow Up — X12 code "86". */
  PENDED_FOR_FOLLOW_UP("86", "Pended for Follow Up"),
  /** Countersue — X12 code "87". */
  COUNTERSUE("87", "Countersue"),
  /** Contact via Telephone Call — X12 code "88". */
  CONTACT_VIA_TELEPHONE("88", "Contact via Telephone Call"),
  /** Contact via Fax — X12 code "89". */
  CONTACT_VIA_FAX("89", "Contact via Fax"),
  /** Mark — X12 code "90". */
  MARK("90", "Mark"),
  /** In Progress — X12 code "91". */
  IN_PROGRESS("91", "In Progress"),
  /** Reconfirm — X12 code "92". */
  RECONFIRM("92", "Reconfirm"),
  /** Send Record at End of the Fall Term — X12 code "93". */
  SEND_RECORD_FALL("93", "Send Record at End of the Fall Term"),
  /** Send Record at End of the Winter Term — X12 code "94". */
  SEND_RECORD_WINTER("94", "Send Record at End of the Winter Term"),
  /** Send Record at End of the Spring Term — X12 code "95". */
  SEND_RECORD_SPRING("95", "Send Record at End of the Spring Term"),
  /** Send Record at End of the Summer Term — X12 code "96". */
  SEND_RECORD_SUMMER("96", "Send Record at End of the Summer Term"),
  /** Certified in total — X12 code "A1". */
  CERTIFIED_TOTAL("A1", "Certified in total"),
  /** Send Record at End of the Intersession Term — X12 code "97". */
  SEND_RECORD_INTERSESSION("97", "Send Record at End of the Intersession Term"),
  /** Replace — X12 code "RX". */
  REPLACE("RX", "Replace");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<ActionCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("processing", IN_PROGRESS)));
  }

  ActionCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an ActionCode instance from any input string. Matches against codes, names, descriptions,
   * and common variations.
   *
   * @param input the string to look up
   * @return the matching ActionCode
   * @throws IllegalArgumentException if no match is found
   */
  public static ActionCode fromString(String input) {
    return LOOKUP.fromString(input);
  }

  /**
   * Returns the raw X12 code value for this constant (not the enum name), so the enum renders
   * directly into an EDI element.
   */
  @Override
  public String toString() {
    return code;
  }
}
