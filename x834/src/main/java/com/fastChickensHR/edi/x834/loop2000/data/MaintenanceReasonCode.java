/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import java.util.Map;
import lombok.Getter;

/**
 * Enumeration representing Maintenance Reason Codes (X12 element 1203), emitted at the HD02 field
 * of the EDI 834 Health Coverage (HD) segment.
 *
 * <p>The codes below are transcribed verbatim from the X12 005010 base standard so the enum is the
 * widest legal ring at that position (per-carrier/TR3 subsets are narrowed downstream by the
 * requirements ratchet). The former list carried nine invented codes (BH, CO, DI, DN, ET, MA, ST,
 * TN, VO) that do not exist in element 1203 and mislabeled {@code AC} as "Active" when element 1203
 * defines it as "Inconvenient Office Location"; both have been corrected.
 */
@Getter
public enum MaintenanceReasonCode implements EdiCodeEnum {
  /** Divorce — X12 code "01". */
  DIVORCE("01", "Divorce"),
  /** Birth — X12 code "02". */
  BIRTH("02", "Birth"),
  /** Death — X12 code "03". */
  DEATH("03", "Death"),
  /** Retirement — X12 code "04". */
  RETIREMENT("04", "Retirement"),
  /** Business Name Change — X12 code "4A". */
  BUSINESS_NAME_CHANGE("4A", "Business Name Change"),
  /** Business Name Correction — X12 code "4B". */
  BUSINESS_NAME_CORRECTION("4B", "Business Name Correction"),
  /** Physical or Mailing Address Correction — X12 code "4C". */
  ADDRESS_CORRECTION("4C", "Physical or Mailing Address Correction"),
  /** Adoption — X12 code "05". */
  ADOPTION("05", "Adoption"),
  /** Strike — X12 code "06". */
  STRIKE("06", "Strike"),
  /** Termination of Benefits — X12 code "07". */
  TERMINATION_OF_BENEFITS("07", "Termination of Benefits"),
  /** Termination of Employment — X12 code "08". */
  TERMINATION_OF_EMPLOYMENT("08", "Termination of Employment"),
  /** Consolidation Omnibus Budget Reconciliation Act (COBRA) — X12 code "09". */
  COBRA("09", "Consolidation Omnibus Budget Reconciliation Act (COBRA)"),
  /** Consolidation Omnibus Budget Reconciliation Act (COBRA) Premium Paid — X12 code "10". */
  COBRA_PREMIUM_PAID("10", "Consolidation Omnibus Budget Reconciliation Act (COBRA) Premium Paid"),
  /** Surviving Spouse — X12 code "11". */
  SURVIVING_SPOUSE("11", "Surviving Spouse"),
  /** Lay Off — X12 code "12". */
  LAY_OFF("12", "Lay Off"),
  /** Leave of Absence — X12 code "13". */
  LEAVE_OF_ABSENCE("13", "Leave of Absence"),
  /** Voluntary Withdrawal — X12 code "14". */
  VOLUNTARY_WITHDRAWAL("14", "Voluntary Withdrawal"),
  /** Primary Care Provider (PCP) Change — X12 code "15". */
  PCP_CHANGE("15", "Primary Care Provider (PCP) Change"),
  /** Quit — X12 code "16". */
  QUIT("16", "Quit"),
  /** Fired — X12 code "17". */
  FIRED("17", "Fired"),
  /** Suspended — X12 code "18". */
  SUSPENDED("18", "Suspended"),
  /** Sabbatical — X12 code "19". */
  SABBATICAL("19", "Sabbatical"),
  /** Active — X12 code "20". */
  ACTIVE("20", "Active"),
  /** Disability — X12 code "21". */
  DISABILITY("21", "Disability"),
  /** Plan Change — X12 code "22". */
  PLAN_CHANGE("22", "Plan Change"),
  /** Furloughed — X12 code "23". */
  FURLOUGHED("23", "Furloughed"),
  /** Resigned — X12 code "24". */
  RESIGNED("24", "Resigned"),
  /** Change in Identifying Data Elements — X12 code "25". */
  CHANGE_IN_IDENTIFYING_DATA_ELEMENTS("25", "Change in Identifying Data Elements"),
  /** Declined Coverage — X12 code "26". */
  DECLINED_COVERAGE("26", "Declined Coverage"),
  /** Pre-Enrollment — X12 code "27". */
  PRE_ENROLLMENT("27", "Pre-Enrollment"),
  /** Initial Enrollment — X12 code "28". */
  INITIAL_ENROLLMENT("28", "Initial Enrollment"),
  /** Benefit Selection — X12 code "29". */
  BENEFIT_SELECTION("29", "Benefit Selection"),
  /** Discrimination Test — X12 code "30". */
  DISCRIMINATION_TEST("30", "Discrimination Test"),
  /** Legal Separation — X12 code "31". */
  LEGAL_SEPARATION("31", "Legal Separation"),
  /** Marriage — X12 code "32". */
  MARRIAGE("32", "Marriage"),
  /** Personnel Data — X12 code "33". */
  PERSONNEL_DATA("33", "Personnel Data"),
  /** Investment Elections and Contribution Rates — X12 code "34". */
  INVESTMENT_ELECTIONS("34", "Investment Elections and Contribution Rates"),
  /** Loan Repayment — X12 code "35". */
  LOAN_REPAYMENT("35", "Loan Repayment"),
  /** Contribution or Plan Allocation — X12 code "36". */
  CONTRIBUTION_OR_PLAN_ALLOCATION("36", "Contribution or Plan Allocation"),
  /** Leave of Absence with Benefits — X12 code "37". */
  LEAVE_OF_ABSENCE_WITH_BENEFITS("37", "Leave of Absence with Benefits"),
  /** Leave of Absence without Benefits — X12 code "38". */
  LEAVE_OF_ABSENCE_WITHOUT_BENEFITS("38", "Leave of Absence without Benefits"),
  /** Lay Off with Benefits — X12 code "39". */
  LAY_OFF_WITH_BENEFITS("39", "Lay Off with Benefits"),
  /** Lay Off without Benefits — X12 code "40". */
  LAY_OFF_WITHOUT_BENEFITS("40", "Lay Off without Benefits"),
  /** Re-enrollment — X12 code "41". */
  RE_ENROLLMENT("41", "Re-enrollment"),
  /** New Entity — X12 code "42". */
  NEW_ENTITY("42", "New Entity"),
  /** Change of Location — X12 code "43". */
  CHANGE_OF_LOCATION("43", "Change of Location"),
  /** Change of Telephone Number — X12 code "44". */
  CHANGE_OF_TELEPHONE_NUMBER("44", "Change of Telephone Number"),
  /** Went Out of Business — X12 code "45". */
  WENT_OUT_OF_BUSINESS("45", "Went Out of Business"),
  /** Current Customer Information File in Error — X12 code "46". */
  CUSTOMER_INFO_FILE_IN_ERROR("46", "Current Customer Information File in Error"),
  /** Account Balance Reporting — X12 code "47". */
  ACCOUNT_BALANCE_REPORTING("47", "Account Balance Reporting"),
  /** Fees Processing — X12 code "48". */
  FEES_PROCESSING("48", "Fees Processing"),
  /** Interfund Transfer — X12 code "49". */
  INTERFUND_TRANSFER("49", "Interfund Transfer"),
  /** Loan Request — X12 code "50". */
  LOAN_REQUEST("50", "Loan Request"),
  /** Enrollment in Subsequent Benefit Plan — X12 code "51". */
  ENROLLMENT_IN_SUBSEQUENT_BENEFIT_PLAN("51", "Enrollment in Subsequent Benefit Plan"),
  /** Health Care Facility Change — X12 code "52". */
  HEALTH_CARE_FACILITY_CHANGE("52", "Health Care Facility Change"),
  /** Name Synonym Add — X12 code "53". */
  NAME_SYNONYM_ADD("53", "Name Synonym Add"),
  /** Sub Location Add — X12 code "54". */
  SUB_LOCATION_ADD("54", "Sub Location Add"),
  /** Sub Location Change — X12 code "55". */
  SUB_LOCATION_CHANGE("55", "Sub Location Change"),
  /** Sub Location Expire — X12 code "56". */
  SUB_LOCATION_EXPIRE("56", "Sub Location Expire"),
  /** Buyout — X12 code "57". */
  BUYOUT("57", "Buyout"),
  /** Merger — X12 code "58". */
  MERGER("58", "Merger"),
  /** Non Payment — X12 code "59". */
  NON_PAYMENT("59", "Non Payment"),
  /** Coverage Placed Elsewhere — X12 code "60". */
  COVERAGE_PLACED_ELSEWHERE("60", "Coverage Placed Elsewhere"),
  /** Duplicate Coverage — X12 code "61". */
  DUPLICATE_COVERAGE("61", "Duplicate Coverage"),
  /** Change in Ownership — X12 code "62". */
  CHANGE_IN_OWNERSHIP("62", "Change in Ownership"),
  /** Business Sold — X12 code "63". */
  BUSINESS_SOLD("63", "Business Sold"),
  /** Underwriting Reason — X12 code "64". */
  UNDERWRITING_REASON("64", "Underwriting Reason"),
  /** No Employees, Exposure or Operations — X12 code "65". */
  NO_EMPLOYEES_EXPOSURE_OR_OPERATIONS("65", "No Employees, Exposure or Operations"),
  /** Revocation of Voluntary Market Acceptance — X12 code "66". */
  REVOCATION_OF_VOLUNTARY_MARKET_ACCEPTANCE("66", "Revocation of Voluntary Market Acceptance"),
  /** Include Primary Business Management — X12 code "67". */
  INCLUDE_PRIMARY_BUSINESS_MANAGEMENT("67", "Include Primary Business Management"),
  /** Exclude Primary Business Management — X12 code "68". */
  EXCLUDE_PRIMARY_BUSINESS_MANAGEMENT("68", "Exclude Primary Business Management"),
  /** Failure to Pay Deductible — X12 code "69". */
  FAILURE_TO_PAY_DEDUCTIBLE("69", "Failure to Pay Deductible"),
  /** Misrepresented Information — X12 code "70". */
  MISREPRESENTED_INFORMATION("70", "Misrepresented Information"),
  /** Rewritten — X12 code "71". */
  REWRITTEN("71", "Rewritten"),
  /** Adding a Jurisdiction — X12 code "72". */
  ADDING_A_JURISDICTION("72", "Adding a Jurisdiction"),
  /** Deleting a Jurisdiction — X12 code "73". */
  DELETING_A_JURISDICTION("73", "Deleting a Jurisdiction"),
  /** Occupational Illness — X12 code "75". */
  OCCUPATIONAL_ILLNESS("75", "Occupational Illness"),
  /** Change Insured Federal Employer Identification Number (FEIN) — X12 code "76". */
  CHANGE_INSURED_FEIN("76", "Change Insured Federal Employer Identification Number (FEIN)"),
  /** Change Employer Federal Employer Identification Number (FEIN) — X12 code "77". */
  CHANGE_EMPLOYER_FEIN("77", "Change Employer Federal Employer Identification Number (FEIN)"),
  /** Change Employer Unemployment Insurance (UI) Code — X12 code "78". */
  CHANGE_EMPLOYER_UI_CODE("78", "Change Employer Unemployment Insurance (UI) Code"),
  /** Change Policy Number — X12 code "79". */
  CHANGE_POLICY_NUMBER("79", "Change Policy Number"),
  /** Modification without a Specific Operating Unit Location in Jurisdiction — X12 code "80". */
  MODIFICATION_WITHOUT_SPECIFIC_LOCATION(
      "80", "Modification without a Specific Operating Unit Location in Jurisdiction"),
  /** Change Policy Effective Date — X12 code "81". */
  CHANGE_POLICY_EFFECTIVE_DATE("81", "Change Policy Effective Date"),
  /** Change Policy Expiration Date — X12 code "82". */
  CHANGE_POLICY_EXPIRATION_DATE("82", "Change Policy Expiration Date"),
  /** Change Insurer Federal Employer Identification Number (FEIN) — X12 code "83". */
  CHANGE_INSURER_FEIN("83", "Change Insurer Federal Employer Identification Number (FEIN)"),
  /** No Eligible Employees — X12 code "84". */
  NO_ELIGIBLE_EMPLOYEES("84", "No Eligible Employees"),
  /** Reinstatement - Canceled in Error — X12 code "85". */
  REINSTATEMENT_CANCELED_IN_ERROR("85", "Reinstatement - Canceled in Error"),
  /** Change in Insured Information — X12 code "86". */
  CHANGE_IN_INSURED_INFORMATION("86", "Change in Insured Information"),
  /** Change in Employer Information — X12 code "87". */
  CHANGE_IN_EMPLOYER_INFORMATION("87", "Change in Employer Information"),
  /** Parent Identification Change — X12 code "88". */
  PARENT_IDENTIFICATION_CHANGE("88", "Parent Identification Change"),
  /** Change to Expiration Date — X12 code "89". */
  CHANGE_TO_EXPIRATION_DATE("89", "Change to Expiration Date"),
  /** Phone Verify Only — X12 code "90". */
  PHONE_VERIFY_ONLY("90", "Phone Verify Only"),
  /** Name Synonym Delete — X12 code "91". */
  NAME_SYNONYM_DELETE("91", "Name Synonym Delete"),
  /** Duplicate Entry on Customer Identification File — X12 code "92". */
  DUPLICATE_ENTRY_ON_CUSTOMER_ID_FILE("92", "Duplicate Entry on Customer Identification File"),
  /** Removal of the Customer Identification File Merge ID — X12 code "93". */
  REMOVAL_OF_CUSTOMER_ID_MERGE("93", "Removal of the Customer Identification File Merge ID"),
  /** Removal of the Customer Identification File Buyout ID — X12 code "94". */
  REMOVAL_OF_CUSTOMER_ID_BUYOUT("94", "Removal of the Customer Identification File Buyout ID"),
  /** Removal of the Customer Identification File in Error ID — X12 code "95". */
  REMOVAL_OF_CUSTOMER_ID_IN_ERROR("95", "Removal of the Customer Identification File in Error ID"),
  /** Re-activation of an Out-of-Business Customer — X12 code "96". */
  REACTIVATION_OF_OUT_OF_BUSINESS_CUSTOMER("96", "Re-activation of an Out-of-Business Customer"),
  /** Sub-location Reinstatement — X12 code "97". */
  SUB_LOCATION_REINSTATEMENT("97", "Sub-location Reinstatement"),
  /** Dissatisfaction with Office Staff — X12 code "AA". */
  DISSATISFACTION_OFFICE_STAFF("AA", "Dissatisfaction with Office Staff"),
  /** Dissatisfaction with Medical Care/Services Rendered — X12 code "AB". */
  DISSATISFACTION_MEDICAL_CARE("AB", "Dissatisfaction with Medical Care/Services Rendered"),
  /** Inconvenient Office Location — X12 code "AC". */
  INCONVENIENT_OFFICE_LOCATION("AC", "Inconvenient Office Location"),
  /** Dissatisfaction with Office Hours — X12 code "AD". */
  DISSATISFACTION_OFFICE_HOURS("AD", "Dissatisfaction with Office Hours"),
  /** Unable to Schedule Appointments in a Timely Manner — X12 code "AE". */
  UNABLE_TO_SCHEDULE_APPOINTMENTS("AE", "Unable to Schedule Appointments in a Timely Manner"),
  /** Dissatisfaction with Physician's Referral Policy — X12 code "AF". */
  DISSATISFACTION_REFERRAL_POLICY("AF", "Dissatisfaction with Physician's Referral Policy"),
  /** Less Respect and Attention Time Given than to Other Patients — X12 code "AG". */
  LESS_RESPECT_AND_ATTENTION("AG", "Less Respect and Attention Time Given than to Other Patients"),
  /** Patient Moved to a New Location — X12 code "AH". */
  PATIENT_MOVED("AH", "Patient Moved to a New Location"),
  /** No Reason Given — X12 code "AI". */
  NO_REASON_GIVEN("AI", "No Reason Given"),
  /** Appointment Times not Met in a Timely Manner — X12 code "AJ". */
  APPOINTMENT_TIMES_NOT_MET("AJ", "Appointment Times not Met in a Timely Manner"),
  /** Algorithm Assigned Benefit Selection — X12 code "AL". */
  ALGORITHM_ASSIGNED_BENEFIT_SELECTION("AL", "Algorithm Assigned Benefit Selection"),
  /** Member Benefit Selection — X12 code "EC". */
  MEMBER_BENEFIT_SELECTION("EC", "Member Benefit Selection"),
  /** Became Medical Only — X12 code "XB". */
  BECAME_MEDICAL_ONLY("XB", "Became Medical Only"),
  /** Indemnity — X12 code "XI". */
  INDEMNITY("XI", "Indemnity"),
  /** Became Lost Time — X12 code "XL". */
  BECAME_LOST_TIME("XL", "Became Lost Time"),
  /** Medical Only — X12 code "XM". */
  MEDICAL_ONLY("XM", "Medical Only"),
  /** Notification Only — X12 code "XN". */
  NOTIFICATION_ONLY("XN", "Notification Only"),
  /** Transfer — X12 code "XT". */
  TRANSFER("XT", "Transfer"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<MaintenanceReasonCode> LOOKUP;

  static {
    // A modest set of colloquial aliases; codes, enum names and descriptions are
    // matched automatically by EdiEnumLookup.
    LOOKUP =
        new EdiEnumLookup<>(
            MaintenanceReasonCode.class,
            "Maintenance Reason Code",
            Map.<String, MaintenanceReasonCode>ofEntries(
                Map.entry("newborn", BIRTH),
                Map.entry("new child", BIRTH),
                Map.entry("baby", BIRTH),
                Map.entry("deceased", DEATH),
                Map.entry("died", DEATH),
                Map.entry("passed away", DEATH),
                Map.entry("retired", RETIREMENT),
                Map.entry("pension", RETIREMENT),
                Map.entry("married", MARRIAGE),
                Map.entry("wedding", MARRIAGE),
                Map.entry("continuation coverage", COBRA),
                Map.entry("terminated", TERMINATION_OF_EMPLOYMENT),
                Map.entry("fired", FIRED),
                Map.entry("quit", QUIT),
                Map.entry("resigned", RESIGNED),
                Map.entry("disabled", DISABILITY),
                Map.entry("loa", LEAVE_OF_ABSENCE),
                Map.entry("new hire", INITIAL_ENROLLMENT),
                Map.entry("correction", CHANGE_IN_IDENTIFYING_DATA_ELEMENTS),
                Map.entry("notification", NOTIFICATION_ONLY)));
  }

  MaintenanceReasonCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a MaintenanceReasonCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching MaintenanceReasonCode
   * @throws IllegalArgumentException if no match is found
   */
  public static MaintenanceReasonCode fromString(String input) {
    return LOOKUP.fromString(input);
  }

  @Override
  public String toString() {
    return code;
  }
}
