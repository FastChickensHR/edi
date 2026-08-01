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
 * Code values for the X12 Frequency Code (data element 594), which states the period an amount or
 * quantity covers. In the X12 834 (005010X220A1) it appears as ICM01, saying over what period the
 * member's income in ICM02 is earned — a wage of 2000 means nothing until you know whether that is
 * weekly, monthly or annual.
 *
 * <p>No default value is defined for this element, and none is assumed here: guessing the period of
 * someone's pay would silently misstate their income by an order of magnitude. {@link
 * #fromString(String)} resolves a value from its code, enum name, description, or a common synonym,
 * and throws {@link IllegalArgumentException} when the input matches none.
 */
@Getter
public enum FrequencyCode implements EdiCodeEnum {
  /** Annualized; 12-month equivalent — X12 code "0". */
  ANNUALIZED("0", "Annualized; 12-month equivalent"),
  /** Weekly — X12 code "1". */
  WEEKLY("1", "Weekly"),
  /** Biweekly — X12 code "2". */
  BIWEEKLY("2", "Biweekly"),
  /** Semimonthly — X12 code "3". */
  SEMIMONTHLY("3", "Semimonthly"),
  /** Monthly — X12 code "4". */
  MONTHLY("4", "Monthly"),
  /** Other — X12 code "5". */
  OTHER("5", "Other"),
  /** Daily — X12 code "6". */
  DAILY("6", "Daily"),
  /** Annual — X12 code "7". */
  ANNUAL("7", "Annual"),
  /** Two Calendar Months — X12 code "8". */
  TWO_CALENDAR_MONTHS("8", "Two Calendar Months"),
  /** Lump-Sum Separation Allowance — X12 code "9". */
  LUMP_SUM_SEPARATION_ALLOWANCE("9", "Lump-Sum Separation Allowance"),
  /** Quarter-to-Date — X12 code "A". */
  QUARTER_TO_DATE("A", "Quarter-to-Date"),
  /** Year-to-Date — X12 code "B". */
  YEAR_TO_DATE("B", "Year-to-Date"),
  /** Single — X12 code "C". */
  SINGLE("C", "Single"),
  /** Policy Period — X12 code "D". */
  POLICY_PERIOD("D", "Policy Period"),
  /** Claim Period — X12 code "E". */
  CLAIM_PERIOD("E", "Claim Period"),
  /** Unit Report Identifier — X12 code "F". */
  UNIT_REPORT_IDENTIFIER("F", "Unit Report Identifier"),
  /** Month-to-Date — X12 code "G". */
  MONTH_TO_DATE("G", "Month-to-Date"),
  /** Hourly — X12 code "H". */
  HOURLY("H", "Hourly"),
  /** Current Period — X12 code "J". */
  CURRENT_PERIOD("J", "Current Period"),
  /** Quarterly — X12 code "Q". */
  QUARTERLY("Q", "Quarterly"),
  /** Semiannual — X12 code "S". */
  SEMIANNUAL("S", "Semiannual"),
  /** Unknown — X12 code "U". */
  UNKNOWN("U", "Unknown"),
  /** Mutually Defined — X12 code "Z". */
  MUTUALLY_DEFINED("Z", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<FrequencyCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            FrequencyCode.class,
            "Frequency Code",
            Map.ofEntries(
                Map.entry("per week", WEEKLY),
                Map.entry("every two weeks", BIWEEKLY),
                Map.entry("fortnightly", BIWEEKLY),
                Map.entry("twice monthly", SEMIMONTHLY),
                Map.entry("per month", MONTHLY),
                Map.entry("per day", DAILY),
                Map.entry("per year", ANNUAL),
                Map.entry("yearly", ANNUAL),
                Map.entry("per hour", HOURLY),
                Map.entry("ytd", YEAR_TO_DATE),
                Map.entry("qtd", QUARTER_TO_DATE),
                Map.entry("mtd", MONTH_TO_DATE)));
  }

  FrequencyCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a FrequencyCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching FrequencyCode
   * @throws IllegalArgumentException if no match is found
   */
  public static FrequencyCode fromString(String input) {
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
