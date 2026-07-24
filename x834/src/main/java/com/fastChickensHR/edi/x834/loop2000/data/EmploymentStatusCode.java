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
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Employment Status Codes (X12 element 584) used in the
 * INS08 field of the EDI 834 Health Insurance Enrollment transaction.
 *
 * <p>Element 584 is a purely alphabetic code list; the codes below are transcribed
 * verbatim from the X12 005010 base standard so the enum is the widest legal ring at
 * INS08 (per-carrier/TR3 subsets are narrowed downstream by the requirements ratchet).
 * The former numeric list ({@code 1}-{@code 24}) matched no X12 element and has been
 * removed.
 */
@Getter
public enum EmploymentStatusCode implements EdiCodeEnum {
    SUBSTITUTE("00", "Substitute"),
    LEAVE_OF_ABSENCE_WITH_PAY("AA", "Leave of Absence with Pay"),
    LEAVE_OF_ABSENCE_WITHOUT_PAY("AB", "Leave of Absence without Pay"),
    ACTIVE("AC", "Active"),
    APPRENTICESHIP_FULL_TIME("AD", "Apprenticeship Full-time"),
    ACTIVE_RESERVE("AE", "Active Reserve"),
    FLEXIBLE_WORK_PLAN("AF", "Flexible Work Plan"),
    ALERTED("AG", "Alerted"),
    ASSIGNED("AH", "Assigned"),
    AFFILIATED_WITH_OUTSIDE_ORGANIZATION("AI", "Affiliated with Outside Organization"),
    ADJUNCT("AJ", "Adjunct"),
    ACTIVE_MILITARY_OVERSEAS("AO", "Active Military - Overseas"),
    APPRENTICESHIP_PART_TIME("AP", "Apprenticeship Part-time"),
    APPRENTICESHIP("AQ", "Apprenticeship"),
    ACADEMY_STUDENT("AS", "Academy Student"),
    PRESIDENTIAL_APPOINTEE("AT", "Presidential Appointee"),
    ACTIVE_MILITARY_USA("AU", "Active Military - USA"),
    NON_APPLICABLE_EMPLOYMENT_STATUS_CATEGORY("CA", "Non-applicable Employment Status Category"),
    CONTRACTOR("CC", "Contractor"),
    COBRA("CO", "Consolidated Omnibus Budget Reconciliation Act (COBRA)"),
    CONTINUED("CT", "Continued"),
    DISCHARGED_OR_TERMINATED_FOR_CAUSE("DC", "Discharged or Terminated for Cause"),
    DISHONORABLY_DISCHARGED("DD", "Dishonorably Discharged"),
    DECEASED("DI", "Deceased"),
    DISQUALIFIED_MEDICAL_OR_PHYSICAL_CONDITION("DQ", "Disqualified: Medical or Physical Condition"),
    DISQUALIFIED_OTHER("DR", "Disqualified: Other"),
    DISABLED("DS", "Disabled"),
    EMPLOYED_BY_OUTSIDE_ORGANIZATION("EO", "Employed by Outside Organization"),
    FURLOUGHED_JOB_ABOLISHED("FA", "Furloughed: Job Abolished, Force Reduction"),
    FURLOUGHED_BUMPED_OR_DISPLACED("FB", "Furloughed: Bumped or Displaced"),
    FURLOUGHED_FACILITY_CLOSED("FC", "Furloughed: Facility Closed"),
    FURLOUGHED_OTHER("FO", "Furloughed: Other"),
    FULL_TIME("FT", "Full-time"),
    HONORABLY_DISCHARGED("HD", "Honorably Discharged"),
    INACTIVE("IA", "Inactive"),
    INACTIVE_RESERVES("IR", "Inactive Reserves"),
    LEAVE_OF_ABSENCE("L1", "Leave of Absence"),
    ADMINISTRATIVE_LEAVE_OF_ABSENCE("L2", "Administrative Leave of Absence"),
    ANNUAL_LEAVE_OF_ABSENCE("L3", "Annual Leave of Absence"),
    BEREAVEMENT_LEAVE_OF_ABSENCE("L4", "Leave of Absence due to Bereavement"),
    JURY_DUTY("L5", "Jury Duty"),
    SUSPENSION("L6", "Suspension"),
    SABBATICAL_LEAVE_OF_ABSENCE("L7", "Sabbatical Leave of Absence"),
    PERSONAL_LEAVE_OF_ABSENCE("LA", "Leave of Absence: Personal"),
    EDUCATION_LEAVE_OF_ABSENCE("LE", "Leave of Absence: Education"),
    FMLA_LEAVE_OF_ABSENCE("LF", "Leave of Absence: Family Medical Leave Act (FMLA)"),
    MATERNITY_LEAVE_OF_ABSENCE("LM", "Leave of Absence: Maternity"),
    GOVERNMENT_LEAVE_OF_ABSENCE("LO", "Leave of Absence for Non-Military Government Request Other Than Jury Duty"),
    SICKNESS_LEAVE_OF_ABSENCE("LS", "Leave of Absence: Sickness"),
    UNION_LEAVE_OF_ABSENCE("LU", "Leave of Absence: Union"),
    UNAUTHORIZED_LEAVE_OF_ABSENCE("LW", "Leave of Absence: Without Permission, Unauthorized"),
    MILITARY_LEAVE_OF_ABSENCE("LX", "Leave of Absence: Military"),
    NOT_EMPLOYED("NE", "Not Employed"),
    ON_STRIKE("OS", "On Strike"),
    OTHER("OT", "Other"),
    PROMOTED("PA", "Promoted"),
    PART_TIME_CONTRACTUAL("PC", "Part-time Contractual"),
    PLAN_TO_ENLIST("PE", "Plan to Enlist"),
    PERMANENT("PM", "Permanent"),
    PART_TIME_NONCONTRACTUAL("PN", "Part-time Noncontractual"),
    PROBATIONARY("PR", "Probationary"),
    PART_TIME("PT", "Part-time"),
    PREVIOUS("PV", "Previous"),
    PIECE_WORKER("PW", "Piece Worker"),
    RESIGNED_RETIRED("RA", "Resigned: Retired"),
    RELOCATED("RB", "Relocated"),
    REASSIGNED("RC", "Reassigned"),
    RESIGNED_MOVED("RD", "Resigned: Moved"),
    RECOMMISSIONED("RE", "Recommissioned"),
    RESIGNED_INJURY("RI", "Resigned: Injury"),
    RETIRED_MILITARY_OVERSEAS("RM", "Retired Military - Overseas"),
    RESIGNED_PERSONAL_REASONS("RP", "Resigned: Personal Reasons"),
    RETIRED_WITHOUT_RECALL("RR", "Retired Without Recall"),
    RETIRED("RT", "Retired"),
    RETIRED_MILITARY_USA("RU", "Retired Military - USA"),
    DUAL_RETIRED_STATUS("RW", "Dual Retired Status"),
    RESIGNED_SEPARATION_ALLOWANCE("SA", "Resigned: Accepted Separation Allowance"),
    SEPARATED("SB", "Separated"),
    SELF_EMPLOYED("SE", "Self-Employed"),
    SEASONAL("SL", "Seasonal"),
    SUSPENDED("SU", "Suspended"),
    TERMINATED("TE", "Terminated"),
    TEMPORARY_FULL_TIME("TF", "Temporary Full-Time"),
    TEMPORARY("TM", "Temporary"),
    TENURED("TN", "Tenured"),
    TEMPORARY_PART_TIME("TP", "Temporary Part-Time"),
    TRANSFERRED("TR", "Transferred"),
    UNKNOWN("UK", "Unknown"),
    VOLUNTEER("VO", "Volunteer"),
    EXTRA_DUTIES("XD", "Extra Duties Not Requiring Certification"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<EmploymentStatusCode> LOOKUP;

    static {
        // A modest set of colloquial aliases; codes, enum names and descriptions are
        // matched automatically by EdiEnumLookup.
        LOOKUP = new EdiEnumLookup<>(
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
                        Map.entry("died", DECEASED)
                )
        );
    }

    EmploymentStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an EmploymentStatusCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
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
