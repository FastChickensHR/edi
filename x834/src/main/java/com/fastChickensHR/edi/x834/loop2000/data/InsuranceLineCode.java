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
 * Enumeration representing Insurance Line Codes (X12 element 1205), rendered at the HD03 field of
 * the EDI 834 Health Coverage (HD) segment in Loop 2300.
 *
 * <p>The full element 1205 list. The former list carried two invented codes ({@code AL}, {@code
 * HSP}) and — more insidiously — scrambled several legal codes onto the wrong meanings ({@code AJ}
 * labeled "Long-term Care" when 1205 defines it as Medicare Risk, {@code AK} labeled "Medicare
 * Risk" when it is Mental Health, {@code PDG} labeled "Pharmacy" when it is Prescription Drug,
 * {@code PRA} labeled "Prescription Drug" when it is Practitioners). Descriptions are the verbatim
 * element-1205 text.
 */
@Getter
public enum InsuranceLineCode implements EdiCodeEnum {
  /** 403(B) Tax Sheltered Annuity — X12 code "403". */
  TAX_SHELTERED_ANNUITY_403B("403", "403(B) Tax Sheltered Annuity"),
  /** Durable Medical Equipment — X12 code "AAA". */
  DURABLE_MEDICAL_EQUIPMENT("AAA", "Durable Medical Equipment"),
  /** Foot Care — X12 code "AAB". */
  FOOT_CARE("AAB", "Foot Care"),
  /** Substance Abuse — X12 code "AAC". */
  SUBSTANCE_ABUSE("AAC", "Substance Abuse"),
  /** Basic Life — X12 code "AC". */
  BASIC_LIFE("AC", "Basic Life"),
  /** Accidental Death or Dismemberment — X12 code "ADD". */
  ACCIDENTAL_DEATH_OR_DISMEMBERMENT("ADD", "Accidental Death or Dismemberment"),
  /** Supplemental Life — X12 code "AF". */
  SUPPLEMENTAL_LIFE("AF", "Supplemental Life"),
  /** Preventative Care/Wellness — X12 code "AG". */
  PREVENTATIVE_CARE_WELLNESS("AG", "Preventative Care/Wellness"),
  /** 24 Hour Care — X12 code "AH". */
  TWENTY_FOUR_HOUR_CARE("AH", "24 Hour Care"),
  /** Workers Compensation — X12 code "AI". */
  WORKERS_COMPENSATION("AI", "Workers Compensation"),
  /** Medicare Risk — X12 code "AJ". */
  MEDICARE_RISK("AJ", "Medicare Risk"),
  /** Mental Health — X12 code "AK". */
  MENTAL_HEALTH("AK", "Mental Health"),
  /** Alternative Medicine — X12 code "AM". */
  ALTERNATIVE_MEDICINE("AM", "Alternative Medicine"),
  /** Paid Up Life — X12 code "AP". */
  PAID_UP_LIFE("AP", "Paid Up Life"),
  /** Dependent Life — X12 code "AR". */
  DEPENDENT_LIFE("AR", "Dependent Life"),
  /** Acupuncture — X12 code "AU". */
  ACUPUNCTURE("AU", "Acupuncture"),
  /** Death and Dismemberment — X12 code "BC". */
  DEATH_AND_DISMEMBERMENT("BC", "Death and Dismemberment"),
  /** Supplemental Death and Dismemberment — X12 code "BE". */
  SUPPLEMENTAL_DEATH_AND_DISMEMBERMENT("BE", "Supplemental Death and Dismemberment"),
  /** Weekly Indemnity — X12 code "BH". */
  WEEKLY_INDEMNITY("BH", "Weekly Indemnity"),
  /** Weekly Indemnity - New York Employees — X12 code "BK". */
  WEEKLY_INDEMNITY_NEW_YORK("BK", "Weekly Indemnity - New York Employees"),
  /** Chiropractic Care — X12 code "CC". */
  CHIROPRACTIC_CARE("CC", "Chiropractic Care"),
  /** 403(C) Church Exempt Annuity Plans Covered by ERISA — X12 code "CHU". */
  CHURCH_EXEMPT_ANNUITY_403C("CHU", "403(C) Church Exempt Annuity Plans Covered by ERISA"),
  /** Contributory Life — X12 code "CLF". */
  CONTRIBUTORY_LIFE("CLF", "Contributory Life"),
  /** Employee Comprehensive — X12 code "CV". */
  EMPLOYEE_COMPREHENSIVE("CV", "Employee Comprehensive"),
  /** Dental Capitation — X12 code "DCP". */
  DENTAL_CAPITATION("DCP", "Dental Capitation"),
  /** Dental — X12 code "DEN". */
  DENTAL("DEN", "Dental"),
  /**
   * 408(K) Employer Sponsored Qualified Defined Distribution Plans Funded with Individual IRA's —
   * X12 code "EMP".
   */
  EMPLOYER_SPONSORED_408K(
      "EMP",
      "408(K) Employer Sponsored Qualified Defined Distribution Plans Funded with Individual IRA's"),
  /** Exclusive Provider Organization — X12 code "EPO". */
  EXCLUSIVE_PROVIDER_ORGANIZATION("EPO", "Exclusive Provider Organization"),
  /** Facility — X12 code "FAC". */
  FACILITY("FAC", "Facility"),
  /** Flexible Spending — X12 code "FSA". */
  FLEXIBLE_SPENDING("FSA", "Flexible Spending"),
  /** 457(B) Government Deferred Compensation — X12 code "GDC". */
  GOVERNMENT_DEFERRED_COMPENSATION_457B("GDC", "457(B) Government Deferred Compensation"),
  /** Hearing — X12 code "HE". */
  HEARING("HE", "Hearing"),
  /** Health — X12 code "HLT". */
  HEALTH("HLT", "Health"),
  /** Health Maintenance Organization — X12 code "HMO". */
  HEALTH_MAINTENANCE_ORGANIZATION("HMO", "Health Maintenance Organization"),
  /** Group Individual Retirement Account — X12 code "IRA". */
  GROUP_IRA("IRA", "Group Individual Retirement Account"),
  /** 408(B) Individual Retirement Account (IRA) Annuity Contract — X12 code "IRC". */
  IRA_ANNUITY_CONTRACT_408B("IRC", "408(B) Individual Retirement Account (IRA) Annuity Contract"),
  /** Lifestyle Life (Individualized Basic Life) — X12 code "LL". */
  LIFESTYLE_LIFE("LL", "Lifestyle Life (Individualized Basic Life)"),
  /** Long-Term Care — X12 code "LTC". */
  LONG_TERM_CARE("LTC", "Long-Term Care"),
  /** Long-Term Disability — X12 code "LTD". */
  LONG_TERM_DISABILITY("LTD", "Long-Term Disability"),
  /** Major Medical — X12 code "MM". */
  MAJOR_MEDICAL("MM", "Major Medical"),
  /** Mail Order Drug — X12 code "MOD". */
  MAIL_ORDER_DRUG("MOD", "Mail Order Drug"),
  /** 457(F) Non-Government Deferred Compensation — X12 code "NGD". */
  NON_GOVERNMENT_DEFERRED_COMPENSATION_457F("NGD", "457(F) Non-Government Deferred Compensation"),
  /** Non-Qualified — X12 code "NQ". */
  NON_QUALIFIED("NQ", "Non-Qualified"),
  /** Prescription Drug — X12 code "PDG". */
  PRESCRIPTION_DRUG("PDG", "Prescription Drug"),
  /** Point of Service — X12 code "POS". */
  POINT_OF_SERVICE("POS", "Point of Service"),
  /** Preferred Provider Organization — X12 code "PPO". */
  PREFERRED_PROVIDER_ORGANIZATION("PPO", "Preferred Provider Organization"),
  /** Practitioners — X12 code "PRA". */
  PRACTITIONERS("PRA", "Practitioners"),
  /** Profit-Sharing Plan — X12 code "PSP". */
  PROFIT_SHARING_PLAN("PSP", "Profit-Sharing Plan"),
  /** 401(K) Qualified Cash or Deferred Arrangement — X12 code "QDA". */
  QUALIFIED_CASH_DEFERRED_401K("QDA", "401(K) Qualified Cash or Deferred Arrangement"),
  /** 401(A) Qualified Defined Contribution — X12 code "QDC". */
  QUALIFIED_DEFINED_CONTRIBUTION_401A("QDC", "401(A) Qualified Defined Contribution"),
  /** Short-Term Disability — X12 code "STD". */
  SHORT_TERM_DISABILITY("STD", "Short-Term Disability"),
  /** Universal Life — X12 code "UL". */
  UNIVERSAL_LIFE("UL", "Universal Life"),
  /** Utilization Review — X12 code "UR". */
  UTILIZATION_REVIEW("UR", "Utilization Review"),
  /** Vision — X12 code "VIS". */
  VISION("VIS", "Vision"),
  /** Mutually Defined — X12 code "ZZZ". */
  MUTUALLY_DEFINED("ZZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<InsuranceLineCode> LOOKUP;

  static {
    // Colloquial aliases; codes, enum names and descriptions are matched automatically.
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("eyewear", VISION)));
  }

  InsuranceLineCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an InsuranceLineCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
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
