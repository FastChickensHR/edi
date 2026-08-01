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
 * Code values for the Transaction Set Purpose Code (BGN01, X12 data element 353) in the Beginning
 * Segment (BGN). In the X12 834 (005010X220A1) it states the intent of the transmission, for
 * example an original ("00") or a change ("04") file.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum TransactionSetPurposeCode implements EdiCodeEnum {
  /** Original — X12 code "00". */
  ORIGINAL("00", "Original"),
  /** Cancellation — X12 code "01". */
  CANCELLATION("01", "Cancellation"),
  /** Add — X12 code "02". */
  ADD("02", "Add"),
  /** Delete — X12 code "03". */
  DELETE("03", "Delete"),
  /** Change — X12 code "04". */
  CHANGE("04", "Change"),
  /** Replace — X12 code "05". */
  REPLACE("05", "Replace"),
  /** Chargeable Resubmission — X12 code "5C". */
  CHARGEABLE_RESUBMISSION("5C", "Chargeable Resubmission"),
  /** Confirmation — X12 code "06". */
  CONFIRMATION("06", "Confirmation"),
  /** Duplicate — X12 code "07". */
  DUPLICATE("07", "Duplicate"),
  /** Status — X12 code "08". */
  STATUS("08", "Status"),
  /** Not Found — X12 code "10". */
  NOT_FOUND("10", "Not Found"),
  /** Response — X12 code "11". */
  RESPONSE("11", "Response"),
  /** Not Processed — X12 code "12". */
  NOT_PROCESSED("12", "Not Processed"),
  /** Request — X12 code "13". */
  REQUEST("13", "Request"),
  /** Advance Notification — X12 code "14". */
  ADVANCE_NOTIFICATION("14", "Advance Notification"),
  /** Re-Submission — X12 code "15". */
  RESUBMISSION("15", "Re-Submission"),
  /** Proposed — X12 code "16". */
  PROPOSED("16", "Proposed"),
  /** Cancel, to be Reissued — X12 code "17". */
  CANCEL_TO_BE_REISSUED("17", "Cancel, to be Reissued"),
  /** Reissue — X12 code "18". */
  REISSUE("18", "Reissue"),
  /** Seller initiated change — X12 code "19". */
  SELLER_INITIATED_CHANGE("19", "Seller initiated change"),
  /** Final Transmission — X12 code "20". */
  FINAL_TRANSMISSION("20", "Final Transmission"),
  /** Transaction on Hold — X12 code "21". */
  TRANSACTION_ON_HOLD("21", "Transaction on Hold"),
  /** Information Copy — X12 code "22". */
  INFORMATION_COPY("22", "Information Copy"),
  /** Draft — X12 code "24". */
  DRAFT("24", "Draft"),
  /** Incremental — X12 code "25". */
  INCREMENTAL("25", "Incremental"),
  /** Replace - Specified Buyers Parts Only — X12 code "26". */
  REPLACE_SPECIFIED_BUYERS_PARTS_ONLY("26", "Replace - Specified Buyers Parts Only"),
  /** Verify — X12 code "27". */
  VERIFY("27", "Verify"),
  /** Query — X12 code "28". */
  QUERY("28", "Query"),
  /** Renewal — X12 code "30". */
  RENEWAL("30", "Renewal"),
  /** Allowance/Addition — X12 code "31". */
  ALLOWANCE_ADDITION("31", "Allowance/Addition"),
  /** Recovery/Deduction — X12 code "32". */
  RECOVERY_DEDUCTION("32", "Recovery/Deduction"),
  /** Request for Payment — X12 code "33". */
  REQUEST_FOR_PAYMENT("33", "Request for Payment"),
  /** Payment Declined — X12 code "34". */
  PAYMENT_DECLINED("34", "Payment Declined"),
  /** Request Authority — X12 code "35". */
  REQUEST_AUTHORITY("35", "Request Authority"),
  /** Authority to Deduct (Reply) — X12 code "36". */
  AUTHORITY_TO_DEDUCT_REPLY("36", "Authority to Deduct (Reply)"),
  /** Authority Declined (Reply) — X12 code "37". */
  AUTHORITY_DECLINED_REPLY("37", "Authority Declined (Reply)"),
  /** No Financial Value — X12 code "38". */
  NO_FINANCIAL_VALUE("38", "No Financial Value"),
  /** Response to Proposed Trip Plan — X12 code "39". */
  RESPONSE_TO_PROPOSED_TRIP_PLAN("39", "Response to Proposed Trip Plan"),
  /** Commitment Advice — X12 code "40". */
  COMMITMENT_ADVICE("40", "Commitment Advice"),
  /** Corrected and Verified — X12 code "41". */
  CORRECTED_AND_VERIFIED("41", "Corrected and Verified"),
  /** Temporary Record — X12 code "42". */
  TEMPORARY_RECORD("42", "Temporary Record"),
  /** Request Permission to Service — X12 code "43". */
  REQUEST_PERMISSION_TO_SERVICE("43", "Request Permission to Service"),
  /** Rejection — X12 code "44". */
  REJECTION("44", "Rejection"),
  /** Follow-up — X12 code "45". */
  FOLLOW_UP("45", "Follow-up"),
  /** Cancellation with Refund — X12 code "46". */
  CANCELLATION_WITH_REFUND("46", "Cancellation with Refund"),
  /** Transfer — X12 code "47". */
  TRANSFER("47", "Transfer"),
  /** Suspended — X12 code "48". */
  SUSPENDED("48", "Suspended"),
  /** Original - No Response Necessary — X12 code "49". */
  ORIGINAL_NO_RESPONSE_NECESSARY("49", "Original - No Response Necessary"),
  /** Register — X12 code "50". */
  REGISTER("50", "Register"),
  /** Historical Inquiry — X12 code "51". */
  HISTORICAL_INQUIRY("51", "Historical Inquiry"),
  /** Response to Historical Inquiry — X12 code "52". */
  RESPONSE_TO_HISTORICAL_INQUIRY("52", "Response to Historical Inquiry"),
  /** Completion — X12 code "53". */
  COMPLETION("53", "Completion"),
  /** Approval — X12 code "54". */
  APPROVAL("54", "Approval"),
  /** Excavation — X12 code "55". */
  EXCAVATION("55", "Excavation"),
  /** Expiration Notification — X12 code "56". */
  EXPIRATION_NOTIFICATION("56", "Expiration Notification"),
  /** Initial — X12 code "57". */
  INITIAL("57", "Initial"),
  /** Simulation Exercise — X12 code "77". */
  SIMULATION_EXERCISE("77", "Simulation Exercise"),
  /** Completion Notification — X12 code "CN". */
  COMPLETION_NOTIFICATION("CN", "Completion Notification"),
  /** Corrected — X12 code "CO". */
  CORRECTED("CO", "Corrected"),
  /** Final Loading Configuration — X12 code "EX". */
  FINAL_LOADING_CONFIGURATION("EX", "Final Loading Configuration"),
  /** Granted — X12 code "GR". */
  GRANTED("GR", "Granted"),
  /** Proposed Loading Configuration — X12 code "PR". */
  PROPOSED_LOADING_CONFIGURATION("PR", "Proposed Loading Configuration"),
  /** Release Hold — X12 code "RH". */
  RELEASE_HOLD("RH", "Release Hold"),
  /** Revised Loading Configuration — X12 code "RV". */
  REVISED_LOADING_CONFIGURATION("RV", "Revised Loading Configuration"),
  /** Status Update — X12 code "SU". */
  STATUS_UPDATE("SU", "Status Update"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<TransactionSetPurposeCode> LOOKUP;

  static {
    // Include additional common terms users might enter
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("status check", STATUS),
                Map.entry("inquiry", REQUEST),
                Map.entry("asking", REQUEST),
                Map.entry("resubmit", RESUBMISSION),
                Map.entry("resend", RESUBMISSION),
                Map.entry("complete", COMPLETION_NOTIFICATION),
                Map.entry("finished", COMPLETION_NOTIFICATION),
                Map.entry("custom", MUTUALLY_DEFINED),
                Map.entry("agreed upon", MUTUALLY_DEFINED)));
  }

  TransactionSetPurposeCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a TransactionSetPurposeCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching TransactionSetPurposeCode
   * @throws IllegalArgumentException if no match is found
   */
  public static TransactionSetPurposeCode fromString(String input) {
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
