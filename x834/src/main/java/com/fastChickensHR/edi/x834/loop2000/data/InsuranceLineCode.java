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
 * Enumeration representing Insurance Line Codes (X12 element 1205), rendered at the
 * HD03 field of the EDI 834 Health Coverage (HD) segment in Loop 2300.
 *
 * <p>The full element 1205 list. The former list carried two invented codes ({@code AL},
 * {@code HSP}) and — more insidiously — scrambled several legal codes onto the wrong
 * meanings ({@code AJ} labeled "Long-term Care" when 1205 defines it as Medicare Risk,
 * {@code AK} labeled "Medicare Risk" when it is Mental Health, {@code PDG} labeled
 * "Pharmacy" when it is Prescription Drug, {@code PRA} labeled "Prescription Drug" when
 * it is Practitioners). Descriptions are the verbatim element-1205 text.
 */
@Getter
public enum InsuranceLineCode implements EdiCodeEnum {
    TAX_SHELTERED_ANNUITY_403B("403", "403(B) Tax Sheltered Annuity"),
    DURABLE_MEDICAL_EQUIPMENT("AAA", "Durable Medical Equipment"),
    FOOT_CARE("AAB", "Foot Care"),
    SUBSTANCE_ABUSE("AAC", "Substance Abuse"),
    BASIC_LIFE("AC", "Basic Life"),
    ACCIDENTAL_DEATH_OR_DISMEMBERMENT("ADD", "Accidental Death or Dismemberment"),
    SUPPLEMENTAL_LIFE("AF", "Supplemental Life"),
    PREVENTATIVE_CARE_WELLNESS("AG", "Preventative Care/Wellness"),
    TWENTY_FOUR_HOUR_CARE("AH", "24 Hour Care"),
    WORKERS_COMPENSATION("AI", "Workers Compensation"),
    MEDICARE_RISK("AJ", "Medicare Risk"),
    MENTAL_HEALTH("AK", "Mental Health"),
    ALTERNATIVE_MEDICINE("AM", "Alternative Medicine"),
    PAID_UP_LIFE("AP", "Paid Up Life"),
    DEPENDENT_LIFE("AR", "Dependent Life"),
    ACUPUNCTURE("AU", "Acupuncture"),
    DEATH_AND_DISMEMBERMENT("BC", "Death and Dismemberment"),
    SUPPLEMENTAL_DEATH_AND_DISMEMBERMENT("BE", "Supplemental Death and Dismemberment"),
    WEEKLY_INDEMNITY("BH", "Weekly Indemnity"),
    WEEKLY_INDEMNITY_NEW_YORK("BK", "Weekly Indemnity - New York Employees"),
    CHIROPRACTIC_CARE("CC", "Chiropractic Care"),
    CHURCH_EXEMPT_ANNUITY_403C("CHU", "403(C) Church Exempt Annuity Plans Covered by ERISA"),
    CONTRIBUTORY_LIFE("CLF", "Contributory Life"),
    EMPLOYEE_COMPREHENSIVE("CV", "Employee Comprehensive"),
    DENTAL_CAPITATION("DCP", "Dental Capitation"),
    DENTAL("DEN", "Dental"),
    EMPLOYER_SPONSORED_408K("EMP", "408(K) Employer Sponsored Qualified Defined Distribution Plans Funded with Individual IRA's"),
    EXCLUSIVE_PROVIDER_ORGANIZATION("EPO", "Exclusive Provider Organization"),
    FACILITY("FAC", "Facility"),
    FLEXIBLE_SPENDING("FSA", "Flexible Spending"),
    GOVERNMENT_DEFERRED_COMPENSATION_457B("GDC", "457(B) Government Deferred Compensation"),
    HEARING("HE", "Hearing"),
    HEALTH("HLT", "Health"),
    HEALTH_MAINTENANCE_ORGANIZATION("HMO", "Health Maintenance Organization"),
    GROUP_IRA("IRA", "Group Individual Retirement Account"),
    IRA_ANNUITY_CONTRACT_408B("IRC", "408(B) Individual Retirement Account (IRA) Annuity Contract"),
    LIFESTYLE_LIFE("LL", "Lifestyle Life (Individualized Basic Life)"),
    LONG_TERM_CARE("LTC", "Long-Term Care"),
    LONG_TERM_DISABILITY("LTD", "Long-Term Disability"),
    MAJOR_MEDICAL("MM", "Major Medical"),
    MAIL_ORDER_DRUG("MOD", "Mail Order Drug"),
    NON_GOVERNMENT_DEFERRED_COMPENSATION_457F("NGD", "457(F) Non-Government Deferred Compensation"),
    NON_QUALIFIED("NQ", "Non-Qualified"),
    PRESCRIPTION_DRUG("PDG", "Prescription Drug"),
    POINT_OF_SERVICE("POS", "Point of Service"),
    PREFERRED_PROVIDER_ORGANIZATION("PPO", "Preferred Provider Organization"),
    PRACTITIONERS("PRA", "Practitioners"),
    PROFIT_SHARING_PLAN("PSP", "Profit-Sharing Plan"),
    QUALIFIED_CASH_DEFERRED_401K("QDA", "401(K) Qualified Cash or Deferred Arrangement"),
    QUALIFIED_DEFINED_CONTRIBUTION_401A("QDC", "401(A) Qualified Defined Contribution"),
    SHORT_TERM_DISABILITY("STD", "Short-Term Disability"),
    UNIVERSAL_LIFE("UL", "Universal Life"),
    UTILIZATION_REVIEW("UR", "Utilization Review"),
    VISION("VIS", "Vision"),
    MUTUALLY_DEFINED("ZZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<InsuranceLineCode> LOOKUP;

    static {
        // Colloquial aliases; codes, enum names and descriptions are matched automatically.
        LOOKUP = new EdiEnumLookup<>(
                InsuranceLineCode.class,
                "Insurance Line Code",
                Map.<String, InsuranceLineCode>ofEntries(
                        Map.entry("wellness", PREVENTATIVE_CARE_WELLNESS),
                        Map.entry("preventive", PREVENTATIVE_CARE_WELLNESS),

                        Map.entry("medicare", MEDICARE_RISK),
                        Map.entry("medicare advantage", MEDICARE_RISK),

                        Map.entry("mental", MENTAL_HEALTH),
                        Map.entry("behavioral health", MENTAL_HEALTH),
                        Map.entry("psych", MENTAL_HEALTH),

                        Map.entry("dental insurance", DENTAL),
                        Map.entry("teeth", DENTAL),

                        Map.entry("medical", HEALTH),
                        Map.entry("health insurance", HEALTH),
                        Map.entry("health plan", HEALTH),

                        Map.entry("long term care", LONG_TERM_CARE),
                        Map.entry("nursing home", LONG_TERM_CARE),

                        Map.entry("ltd", LONG_TERM_DISABILITY),
                        Map.entry("std", SHORT_TERM_DISABILITY),

                        Map.entry("major med", MAJOR_MEDICAL),
                        Map.entry("mail order", MAIL_ORDER_DRUG),

                        Map.entry("rx", PRESCRIPTION_DRUG),
                        Map.entry("drug", PRESCRIPTION_DRUG),
                        Map.entry("prescription", PRESCRIPTION_DRUG),

                        Map.entry("practitioner", PRACTITIONERS),

                        Map.entry("utilization", UTILIZATION_REVIEW),
                        Map.entry("optical", VISION),
                        Map.entry("eye", VISION),
                        Map.entry("eyewear", VISION)
                )
        );
    }

    InsuranceLineCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an InsuranceLineCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching InsuranceLineCode
     * @throws IllegalArgumentException if no match is found
     */
    public static InsuranceLineCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
