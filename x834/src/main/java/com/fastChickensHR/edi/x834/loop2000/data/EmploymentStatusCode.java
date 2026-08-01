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
 * Enumeration representing Employment Status Codes (X12 element 584) used in the INS08 field of the
 * EDI 834 Health Insurance Enrollment transaction.
 *
 * <p>Element 584 is a purely alphabetic code list; the codes below are transcribed verbatim from
 * the X12 005010 base standard so the enum is the widest legal ring at INS08 (per-carrier/TR3
 * subsets are narrowed downstream by the requirements ratchet). The former numeric list ({@code
 * 1}-{@code 24}) matched no X12 element and has been removed.
 */
@Getter
public enum EmploymentStatusCode implements EdiCodeEnum {
  /** Substitute — X12 code "00". */
  SUBSTITUTE("00", "Substitute"),
  /** Leave of Absence with Pay — X12 code "AA". */
  LEAVE_OF_ABSENCE_WITH_PAY("AA", "Leave of Absence with Pay"),
  /** Leave of Absence without Pay — X12 code "AB". */
  LEAVE_OF_ABSENCE_WITHOUT_PAY("AB", "Leave of Absence without Pay"),
  /** Active — X12 code "AC". */
  ACTIVE("AC", "Active"),
  /** Apprenticeship Full-time — X12 code "AD". */
  APPRENTICESHIP_FULL_TIME("AD", "Apprenticeship Full-time"),
  /** Active Reserve — X12 code "AE". */
  ACTIVE_RESERVE("AE", "Active Reserve"),
  /** Flexible Work Plan — X12 code "AF". */
  FLEXIBLE_WORK_PLAN("AF", "Flexible Work Plan"),
  /** Alerted — X12 code "AG". */
  ALERTED("AG", "Alerted"),
  /** Assigned — X12 code "AH". */
  ASSIGNED("AH", "Assigned"),
  /** Affiliated with Outside Organization — X12 code "AI". */
  AFFILIATED_WITH_OUTSIDE_ORGANIZATION("AI", "Affiliated with Outside Organization"),
  /** Adjunct — X12 code "AJ". */
  ADJUNCT("AJ", "Adjunct"),
  /** Active Military - Overseas — X12 code "AO". */
  ACTIVE_MILITARY_OVERSEAS("AO", "Active Military - Overseas"),
  /** Apprenticeship Part-time — X12 code "AP". */
  APPRENTICESHIP_PART_TIME("AP", "Apprenticeship Part-time"),
  /** Apprenticeship — X12 code "AQ". */
  APPRENTICESHIP("AQ", "Apprenticeship"),
  /** Academy Student — X12 code "AS". */
  ACADEMY_STUDENT("AS", "Academy Student"),
  /** Presidential Appointee — X12 code "AT". */
  PRESIDENTIAL_APPOINTEE("AT", "Presidential Appointee"),
  /** Active Military - USA — X12 code "AU". */
  ACTIVE_MILITARY_USA("AU", "Active Military - USA"),
  /** Non-applicable Employment Status Category — X12 code "CA". */
  NON_APPLICABLE_EMPLOYMENT_STATUS_CATEGORY("CA", "Non-applicable Employment Status Category"),
  /** Contractor — X12 code "CC". */
  CONTRACTOR("CC", "Contractor"),
  /** Consolidated Omnibus Budget Reconciliation Act (COBRA) — X12 code "CO". */
  COBRA("CO", "Consolidated Omnibus Budget Reconciliation Act (COBRA)"),
  /** Continued — X12 code "CT". */
  CONTINUED("CT", "Continued"),
  /** Discharged or Terminated for Cause — X12 code "DC". */
  DISCHARGED_OR_TERMINATED_FOR_CAUSE("DC", "Discharged or Terminated for Cause"),
  /** Dishonorably Discharged — X12 code "DD". */
  DISHONORABLY_DISCHARGED("DD", "Dishonorably Discharged"),
  /** Deceased — X12 code "DI". */
  DECEASED("DI", "Deceased"),
  /** Disqualified: Medical or Physical Condition — X12 code "DQ". */
  DISQUALIFIED_MEDICAL_OR_PHYSICAL_CONDITION("DQ", "Disqualified: Medical or Physical Condition"),
  /** Disqualified: Other — X12 code "DR". */
  DISQUALIFIED_OTHER("DR", "Disqualified: Other"),
  /** Disabled — X12 code "DS". */
  DISABLED("DS", "Disabled"),
  /** Employed by Outside Organization — X12 code "EO". */
  EMPLOYED_BY_OUTSIDE_ORGANIZATION("EO", "Employed by Outside Organization"),
  /** Furloughed: Job Abolished, Force Reduction — X12 code "FA". */
  FURLOUGHED_JOB_ABOLISHED("FA", "Furloughed: Job Abolished, Force Reduction"),
  /** Furloughed: Bumped or Displaced — X12 code "FB". */
  FURLOUGHED_BUMPED_OR_DISPLACED("FB", "Furloughed: Bumped or Displaced"),
  /** Furloughed: Facility Closed — X12 code "FC". */
  FURLOUGHED_FACILITY_CLOSED("FC", "Furloughed: Facility Closed"),
  /** Furloughed: Other — X12 code "FO". */
  FURLOUGHED_OTHER("FO", "Furloughed: Other"),
  /** Full-time — X12 code "FT". */
  FULL_TIME("FT", "Full-time"),
  /** Honorably Discharged — X12 code "HD". */
  HONORABLY_DISCHARGED("HD", "Honorably Discharged"),
  /** Inactive — X12 code "IA". */
  INACTIVE("IA", "Inactive"),
  /** Inactive Reserves — X12 code "IR". */
  INACTIVE_RESERVES("IR", "Inactive Reserves"),
  /** Leave of Absence — X12 code "L1". */
  LEAVE_OF_ABSENCE("L1", "Leave of Absence"),
  /** Administrative Leave of Absence — X12 code "L2". */
  ADMINISTRATIVE_LEAVE_OF_ABSENCE("L2", "Administrative Leave of Absence"),
  /** Annual Leave of Absence — X12 code "L3". */
  ANNUAL_LEAVE_OF_ABSENCE("L3", "Annual Leave of Absence"),
  /** Leave of Absence due to Bereavement — X12 code "L4". */
  BEREAVEMENT_LEAVE_OF_ABSENCE("L4", "Leave of Absence due to Bereavement"),
  /** Jury Duty — X12 code "L5". */
  JURY_DUTY("L5", "Jury Duty"),
  /** Suspension — X12 code "L6". */
  SUSPENSION("L6", "Suspension"),
  /** Sabbatical Leave of Absence — X12 code "L7". */
  SABBATICAL_LEAVE_OF_ABSENCE("L7", "Sabbatical Leave of Absence"),
  /** Leave of Absence: Personal — X12 code "LA". */
  PERSONAL_LEAVE_OF_ABSENCE("LA", "Leave of Absence: Personal"),
  /** Leave of Absence: Education — X12 code "LE". */
  EDUCATION_LEAVE_OF_ABSENCE("LE", "Leave of Absence: Education"),
  /** Leave of Absence: Family Medical Leave Act (FMLA) — X12 code "LF". */
  FMLA_LEAVE_OF_ABSENCE("LF", "Leave of Absence: Family Medical Leave Act (FMLA)"),
  /** Leave of Absence: Maternity — X12 code "LM". */
  MATERNITY_LEAVE_OF_ABSENCE("LM", "Leave of Absence: Maternity"),
  /** Leave of Absence for Non-Military Government Request Other Than Jury Duty — X12 code "LO". */
  GOVERNMENT_LEAVE_OF_ABSENCE(
      "LO", "Leave of Absence for Non-Military Government Request Other Than Jury Duty"),
  /** Leave of Absence: Sickness — X12 code "LS". */
  SICKNESS_LEAVE_OF_ABSENCE("LS", "Leave of Absence: Sickness"),
  /** Leave of Absence: Union — X12 code "LU". */
  UNION_LEAVE_OF_ABSENCE("LU", "Leave of Absence: Union"),
  /** Leave of Absence: Without Permission, Unauthorized — X12 code "LW". */
  UNAUTHORIZED_LEAVE_OF_ABSENCE("LW", "Leave of Absence: Without Permission, Unauthorized"),
  /** Leave of Absence: Military — X12 code "LX". */
  MILITARY_LEAVE_OF_ABSENCE("LX", "Leave of Absence: Military"),
  /** Not Employed — X12 code "NE". */
  NOT_EMPLOYED("NE", "Not Employed"),
  /** On Strike — X12 code "OS". */
  ON_STRIKE("OS", "On Strike"),
  /** Other — X12 code "OT". */
  OTHER("OT", "Other"),
  /** Promoted — X12 code "PA". */
  PROMOTED("PA", "Promoted"),
  /** Part-time Contractual — X12 code "PC". */
  PART_TIME_CONTRACTUAL("PC", "Part-time Contractual"),
  /** Plan to Enlist — X12 code "PE". */
  PLAN_TO_ENLIST("PE", "Plan to Enlist"),
  /** Permanent — X12 code "PM". */
  PERMANENT("PM", "Permanent"),
  /** Part-time Noncontractual — X12 code "PN". */
  PART_TIME_NONCONTRACTUAL("PN", "Part-time Noncontractual"),
  /** Probationary — X12 code "PR". */
  PROBATIONARY("PR", "Probationary"),
  /** Part-time — X12 code "PT". */
  PART_TIME("PT", "Part-time"),
  /** Previous — X12 code "PV". */
  PREVIOUS("PV", "Previous"),
  /** Piece Worker — X12 code "PW". */
  PIECE_WORKER("PW", "Piece Worker"),
  /** Resigned: Retired — X12 code "RA". */
  RESIGNED_RETIRED("RA", "Resigned: Retired"),
  /** Relocated — X12 code "RB". */
  RELOCATED("RB", "Relocated"),
  /** Reassigned — X12 code "RC". */
  REASSIGNED("RC", "Reassigned"),
  /** Resigned: Moved — X12 code "RD". */
  RESIGNED_MOVED("RD", "Resigned: Moved"),
  /** Recommissioned — X12 code "RE". */
  RECOMMISSIONED("RE", "Recommissioned"),
  /** Resigned: Injury — X12 code "RI". */
  RESIGNED_INJURY("RI", "Resigned: Injury"),
  /** Retired Military - Overseas — X12 code "RM". */
  RETIRED_MILITARY_OVERSEAS("RM", "Retired Military - Overseas"),
  /** Resigned: Personal Reasons — X12 code "RP". */
  RESIGNED_PERSONAL_REASONS("RP", "Resigned: Personal Reasons"),
  /** Retired Without Recall — X12 code "RR". */
  RETIRED_WITHOUT_RECALL("RR", "Retired Without Recall"),
  /** Retired — X12 code "RT". */
  RETIRED("RT", "Retired"),
  /** Retired Military - USA — X12 code "RU". */
  RETIRED_MILITARY_USA("RU", "Retired Military - USA"),
  /** Dual Retired Status — X12 code "RW". */
  DUAL_RETIRED_STATUS("RW", "Dual Retired Status"),
  /** Resigned: Accepted Separation Allowance — X12 code "SA". */
  RESIGNED_SEPARATION_ALLOWANCE("SA", "Resigned: Accepted Separation Allowance"),
  /** Separated — X12 code "SB". */
  SEPARATED("SB", "Separated"),
  /** Self-Employed — X12 code "SE". */
  SELF_EMPLOYED("SE", "Self-Employed"),
  /** Seasonal — X12 code "SL". */
  SEASONAL("SL", "Seasonal"),
  /** Suspended — X12 code "SU". */
  SUSPENDED("SU", "Suspended"),
  /** Terminated — X12 code "TE". */
  TERMINATED("TE", "Terminated"),
  /** Temporary Full-Time — X12 code "TF". */
  TEMPORARY_FULL_TIME("TF", "Temporary Full-Time"),
  /** Temporary — X12 code "TM". */
  TEMPORARY("TM", "Temporary"),
  /** Tenured — X12 code "TN". */
  TENURED("TN", "Tenured"),
  /** Temporary Part-Time — X12 code "TP". */
  TEMPORARY_PART_TIME("TP", "Temporary Part-Time"),
  /** Transferred — X12 code "TR". */
  TRANSFERRED("TR", "Transferred"),
  /** Unknown — X12 code "UK". */
  UNKNOWN("UK", "Unknown"),
  /** Volunteer — X12 code "VO". */
  VOLUNTEER("VO", "Volunteer"),
  /** Extra Duties Not Requiring Certification — X12 code "XD". */
  EXTRA_DUTIES("XD", "Extra Duties Not Requiring Certification"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<EmploymentStatusCode> LOOKUP;

  static {
    // A modest set of colloquial aliases; codes, enum names and descriptions are
    // matched automatically by EdiEnumLookup.
    LOOKUP =
        new EdiEnumLookup<>(
            EmploymentStatusCode.class,
            "Employment Status Code",
            Map.ofEntries(
                Map.entry("current", ACTIVE),
                Map.entry("employed", ACTIVE),
                Map.entry("working", ACTIVE),
                Map.entry("ft", FULL_TIME),
                Map.entry("fulltime", FULL_TIME),
                Map.entry("40hours", FULL_TIME),
                Map.entry("pt", PART_TIME),
                Map.entry("parttime", PART_TIME),
                Map.entry("hourly", PART_TIME),
                Map.entry("pension", RETIRED),
                Map.entry("retiree", RETIRED),
                Map.entry("laid off", TERMINATED),
                Map.entry("fired", TERMINATED),
                Map.entry("resigned", TERMINATED),
                Map.entry("quit", TERMINATED),
                Map.entry("loa", LEAVE_OF_ABSENCE),
                Map.entry("sabbatical", SABBATICAL_LEAVE_OF_ABSENCE),
                Map.entry("fmla", FMLA_LEAVE_OF_ABSENCE),
                Map.entry("medical leave", SICKNESS_LEAVE_OF_ABSENCE),
                Map.entry("maternity", MATERNITY_LEAVE_OF_ABSENCE),
                Map.entry("jury", JURY_DUTY),
                Map.entry("disability", DISABLED),
                Map.entry("ltd", DISABLED),
                Map.entry("cobra", COBRA),
                Map.entry("contractor", CONTRACTOR),
                Map.entry("temp", TEMPORARY),
                Map.entry("seasonal", SEASONAL),
                Map.entry("military", ACTIVE_MILITARY_USA),
                Map.entry("deceased", DECEASED),
                Map.entry("died", DECEASED)));
  }

  EmploymentStatusCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an EmploymentStatusCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching EmploymentStatusCode
   * @throws IllegalArgumentException if no match is found
   */
  public static EmploymentStatusCode fromString(String input) {
    return LOOKUP.fromString(input);
  }

  @Override
  public String toString() {
    return code;
  }
}
