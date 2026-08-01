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
 * Code values for the X12 Coordination of Benefits Code (data element 1143), which states whether —
 * and for whom — benefits are coordinated with another plan. In the X12 834 (005010X220A1) it
 * appears as COB03.
 *
 * <p>The two answers carriers actually send sit at opposite ends of this list: BCBSM's Medicare
 * block sends {@link #COORDINATION_OF_BENEFITS} (benefits are coordinated), while CareFirst sends
 * {@link #NO_COORDINATION_OF_BENEFITS} on every medical and dental row — {@code COB*U**6~}. Note
 * that {@link #UNKNOWN} here ({@code 5}) says the <em>coordination</em> is unknown, which is a
 * different statement from {@link PayerResponsibilitySequenceCode#UNKNOWN} ({@code U}) in COB01
 * saying the payer's <em>sequence</em> is unknown; CareFirst sends both at once.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum CoordinationOfBenefitsCode implements EdiCodeEnum {
  /** Coordination of Benefits — X12 code "1". */
  COORDINATION_OF_BENEFITS("1", "Coordination of Benefits"),
  /** Coordination of Benefits applies to Spouse Only — X12 code "2". */
  COORDINATION_OF_BENEFITS_SPOUSE_ONLY("2", "Coordination of Benefits applies to Spouse Only"),
  /** Coordination of Benefits applies to Spouse and Dependents — X12 code "3". */
  COORDINATION_OF_BENEFITS_SPOUSE_AND_DEPENDENTS(
      "3", "Coordination of Benefits applies to Spouse and Dependents"),
  /** Coordination of Benefits applies to Dependents Only — X12 code "4". */
  COORDINATION_OF_BENEFITS_DEPENDENTS_ONLY(
      "4", "Coordination of Benefits applies to Dependents Only"),
  /** Unknown — X12 code "5". */
  UNKNOWN("5", "Unknown"),
  /** No Coordination of Benefits — X12 code "6". */
  NO_COORDINATION_OF_BENEFITS("6", "No Coordination of Benefits"),
  /** Coordination of Benefits Applies to Subscriber Only — X12 code "7". */
  COORDINATION_OF_BENEFITS_SUBSCRIBER_ONLY(
      "7", "Coordination of Benefits Applies to Subscriber Only"),
  /** Conflict in Coordination of Benefit — X12 code "8". */
  CONFLICT_IN_COORDINATION_OF_BENEFIT("8", "Conflict in Coordination of Benefit"),
  /** Coordination of Benefits Applies to Whole Family — X12 code "9". */
  COORDINATION_OF_BENEFITS_WHOLE_FAMILY("9", "Coordination of Benefits Applies to Whole Family");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<CoordinationOfBenefitsCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            CoordinationOfBenefitsCode.class,
            "Coordination of Benefits Code",
            Map.ofEntries(
                Map.entry("cob", COORDINATION_OF_BENEFITS),
                Map.entry("coordinated", COORDINATION_OF_BENEFITS),
                Map.entry("none", NO_COORDINATION_OF_BENEFITS),
                Map.entry("no cob", NO_COORDINATION_OF_BENEFITS),
                Map.entry("whole family", COORDINATION_OF_BENEFITS_WHOLE_FAMILY),
                Map.entry("conflict", CONFLICT_IN_COORDINATION_OF_BENEFIT)));
  }

  CoordinationOfBenefitsCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a CoordinationOfBenefitsCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching CoordinationOfBenefitsCode
   * @throws IllegalArgumentException if no match is found
   */
  public static CoordinationOfBenefitsCode fromString(String input) {
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
