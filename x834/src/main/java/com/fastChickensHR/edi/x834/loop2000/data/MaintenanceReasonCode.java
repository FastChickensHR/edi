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
 * Enumeration representing Maintenance Reason Codes (X12 element 1203), emitted at the
 * HD02 field of the EDI 834 Health Coverage (HD) segment.
 *
 * <p>The codes below are transcribed verbatim from the X12 005010 base standard so the
 * enum is the widest legal ring at that position (per-carrier/TR3 subsets are narrowed
 * downstream by the requirements ratchet). The former list carried nine invented codes
 * (BH, CO, DI, DN, ET, MA, ST, TN, VO) that do not exist in element 1203 and mislabeled
 * {@code AC} as "Active" when element 1203 defines it as "Inconvenient Office Location";
 * both have been corrected.
 */
@Getter
public enum MaintenanceReasonCode implements EdiCodeEnum {
    DIVORCE("01", "Divorce"),
    BIRTH("02", "Birth"),
    DEATH("03", "Death"),
    RETIREMENT("04", "Retirement"),
    BUSINESS_NAME_CHANGE("4A", "Business Name Change"),
    BUSINESS_NAME_CORRECTION("4B", "Business Name Correction"),
    ADDRESS_CORRECTION("4C", "Physical or Mailing Address Correction"),
    ADOPTION("05", "Adoption"),
    STRIKE("06", "Strike"),
    TERMINATION_OF_BENEFITS("07", "Termination of Benefits"),
    TERMINATION_OF_EMPLOYMENT("08", "Termination of Employment"),
    COBRA("09", "Consolidation Omnibus Budget Reconciliation Act (COBRA)"),
    COBRA_PREMIUM_PAID("10", "Consolidation Omnibus Budget Reconciliation Act (COBRA) Premium Paid"),
    SURVIVING_SPOUSE("11", "Surviving Spouse"),
    LAY_OFF("12", "Lay Off"),
    LEAVE_OF_ABSENCE("13", "Leave of Absence"),
    VOLUNTARY_WITHDRAWAL("14", "Voluntary Withdrawal"),
    PCP_CHANGE("15", "Primary Care Provider (PCP) Change"),
    QUIT("16", "Quit"),
    FIRED("17", "Fired"),
    SUSPENDED("18", "Suspended"),
    SABBATICAL("19", "Sabbatical"),
    ACTIVE("20", "Active"),
    DISABILITY("21", "Disability"),
    PLAN_CHANGE("22", "Plan Change"),
    FURLOUGHED("23", "Furloughed"),
    RESIGNED("24", "Resigned"),
    CHANGE_IN_IDENTIFYING_DATA_ELEMENTS("25", "Change in Identifying Data Elements"),
    DECLINED_COVERAGE("26", "Declined Coverage"),
    PRE_ENROLLMENT("27", "Pre-Enrollment"),
    INITIAL_ENROLLMENT("28", "Initial Enrollment"),
    BENEFIT_SELECTION("29", "Benefit Selection"),
    DISCRIMINATION_TEST("30", "Discrimination Test"),
    LEGAL_SEPARATION("31", "Legal Separation"),
    MARRIAGE("32", "Marriage"),
    PERSONNEL_DATA("33", "Personnel Data"),
    INVESTMENT_ELECTIONS("34", "Investment Elections and Contribution Rates"),
    LOAN_REPAYMENT("35", "Loan Repayment"),
    CONTRIBUTION_OR_PLAN_ALLOCATION("36", "Contribution or Plan Allocation"),
    LEAVE_OF_ABSENCE_WITH_BENEFITS("37", "Leave of Absence with Benefits"),
    LEAVE_OF_ABSENCE_WITHOUT_BENEFITS("38", "Leave of Absence without Benefits"),
    LAY_OFF_WITH_BENEFITS("39", "Lay Off with Benefits"),
    LAY_OFF_WITHOUT_BENEFITS("40", "Lay Off without Benefits"),
    RE_ENROLLMENT("41", "Re-enrollment"),
    NEW_ENTITY("42", "New Entity"),
    CHANGE_OF_LOCATION("43", "Change of Location"),
    CHANGE_OF_TELEPHONE_NUMBER("44", "Change of Telephone Number"),
    WENT_OUT_OF_BUSINESS("45", "Went Out of Business"),
    CUSTOMER_INFO_FILE_IN_ERROR("46", "Current Customer Information File in Error"),
    ACCOUNT_BALANCE_REPORTING("47", "Account Balance Reporting"),
    FEES_PROCESSING("48", "Fees Processing"),
    INTERFUND_TRANSFER("49", "Interfund Transfer"),
    LOAN_REQUEST("50", "Loan Request"),
    ENROLLMENT_IN_SUBSEQUENT_BENEFIT_PLAN("51", "Enrollment in Subsequent Benefit Plan"),
    HEALTH_CARE_FACILITY_CHANGE("52", "Health Care Facility Change"),
    NAME_SYNONYM_ADD("53", "Name Synonym Add"),
    SUB_LOCATION_ADD("54", "Sub Location Add"),
    SUB_LOCATION_CHANGE("55", "Sub Location Change"),
    SUB_LOCATION_EXPIRE("56", "Sub Location Expire"),
    BUYOUT("57", "Buyout"),
    MERGER("58", "Merger"),
    NON_PAYMENT("59", "Non Payment"),
    COVERAGE_PLACED_ELSEWHERE("60", "Coverage Placed Elsewhere"),
    DUPLICATE_COVERAGE("61", "Duplicate Coverage"),
    CHANGE_IN_OWNERSHIP("62", "Change in Ownership"),
    BUSINESS_SOLD("63", "Business Sold"),
    UNDERWRITING_REASON("64", "Underwriting Reason"),
    NO_EMPLOYEES_EXPOSURE_OR_OPERATIONS("65", "No Employees, Exposure or Operations"),
    REVOCATION_OF_VOLUNTARY_MARKET_ACCEPTANCE("66", "Revocation of Voluntary Market Acceptance"),
    INCLUDE_PRIMARY_BUSINESS_MANAGEMENT("67", "Include Primary Business Management"),
    EXCLUDE_PRIMARY_BUSINESS_MANAGEMENT("68", "Exclude Primary Business Management"),
    FAILURE_TO_PAY_DEDUCTIBLE("69", "Failure to Pay Deductible"),
    MISREPRESENTED_INFORMATION("70", "Misrepresented Information"),
    REWRITTEN("71", "Rewritten"),
    ADDING_A_JURISDICTION("72", "Adding a Jurisdiction"),
    DELETING_A_JURISDICTION("73", "Deleting a Jurisdiction"),
    OCCUPATIONAL_ILLNESS("75", "Occupational Illness"),
    CHANGE_INSURED_FEIN("76", "Change Insured Federal Employer Identification Number (FEIN)"),
    CHANGE_EMPLOYER_FEIN("77", "Change Employer Federal Employer Identification Number (FEIN)"),
    CHANGE_EMPLOYER_UI_CODE("78", "Change Employer Unemployment Insurance (UI) Code"),
    CHANGE_POLICY_NUMBER("79", "Change Policy Number"),
    MODIFICATION_WITHOUT_SPECIFIC_LOCATION("80", "Modification without a Specific Operating Unit Location in Jurisdiction"),
    CHANGE_POLICY_EFFECTIVE_DATE("81", "Change Policy Effective Date"),
    CHANGE_POLICY_EXPIRATION_DATE("82", "Change Policy Expiration Date"),
    CHANGE_INSURER_FEIN("83", "Change Insurer Federal Employer Identification Number (FEIN)"),
    NO_ELIGIBLE_EMPLOYEES("84", "No Eligible Employees"),
    REINSTATEMENT_CANCELED_IN_ERROR("85", "Reinstatement - Canceled in Error"),
    CHANGE_IN_INSURED_INFORMATION("86", "Change in Insured Information"),
    CHANGE_IN_EMPLOYER_INFORMATION("87", "Change in Employer Information"),
    PARENT_IDENTIFICATION_CHANGE("88", "Parent Identification Change"),
    CHANGE_TO_EXPIRATION_DATE("89", "Change to Expiration Date"),
    PHONE_VERIFY_ONLY("90", "Phone Verify Only"),
    NAME_SYNONYM_DELETE("91", "Name Synonym Delete"),
    DUPLICATE_ENTRY_ON_CUSTOMER_ID_FILE("92", "Duplicate Entry on Customer Identification File"),
    REMOVAL_OF_CUSTOMER_ID_MERGE("93", "Removal of the Customer Identification File Merge ID"),
    REMOVAL_OF_CUSTOMER_ID_BUYOUT("94", "Removal of the Customer Identification File Buyout ID"),
    REMOVAL_OF_CUSTOMER_ID_IN_ERROR("95", "Removal of the Customer Identification File in Error ID"),
    REACTIVATION_OF_OUT_OF_BUSINESS_CUSTOMER("96", "Re-activation of an Out-of-Business Customer"),
    SUB_LOCATION_REINSTATEMENT("97", "Sub-location Reinstatement"),
    DISSATISFACTION_OFFICE_STAFF("AA", "Dissatisfaction with Office Staff"),
    DISSATISFACTION_MEDICAL_CARE("AB", "Dissatisfaction with Medical Care/Services Rendered"),
    INCONVENIENT_OFFICE_LOCATION("AC", "Inconvenient Office Location"),
    DISSATISFACTION_OFFICE_HOURS("AD", "Dissatisfaction with Office Hours"),
    UNABLE_TO_SCHEDULE_APPOINTMENTS("AE", "Unable to Schedule Appointments in a Timely Manner"),
    DISSATISFACTION_REFERRAL_POLICY("AF", "Dissatisfaction with Physician's Referral Policy"),
    LESS_RESPECT_AND_ATTENTION("AG", "Less Respect and Attention Time Given than to Other Patients"),
    PATIENT_MOVED("AH", "Patient Moved to a New Location"),
    NO_REASON_GIVEN("AI", "No Reason Given"),
    APPOINTMENT_TIMES_NOT_MET("AJ", "Appointment Times not Met in a Timely Manner"),
    ALGORITHM_ASSIGNED_BENEFIT_SELECTION("AL", "Algorithm Assigned Benefit Selection"),
    MEMBER_BENEFIT_SELECTION("EC", "Member Benefit Selection"),
    BECAME_MEDICAL_ONLY("XB", "Became Medical Only"),
    INDEMNITY("XI", "Indemnity"),
    BECAME_LOST_TIME("XL", "Became Lost Time"),
    MEDICAL_ONLY("XM", "Medical Only"),
    NOTIFICATION_ONLY("XN", "Notification Only"),
    TRANSFER("XT", "Transfer"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MaintenanceReasonCode> LOOKUP;

    static {
        // A modest set of colloquial aliases; codes, enum names and descriptions are
        // matched automatically by EdiEnumLookup.
        LOOKUP = new EdiEnumLookup<>(
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
                        Map.entry("notification", NOTIFICATION_ONLY)
                )
        );
    }

    MaintenanceReasonCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MaintenanceReasonCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
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
