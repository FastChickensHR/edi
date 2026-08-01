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
 * Code values for the X12 Disability Type Code (data element 1146), which identifies an
 * individual's disability status. In the X12 834 (005010X220A1) it appears as DSB01, the mandatory
 * element opening Loop 2200.
 *
 * <p>{@link #NO_DISABILITY} is a stated answer rather than an absence — a sponsor saying "this
 * member is not disabled" is different from sending no Loop 2200 at all, and only the former
 * overwrites a disability a carrier already holds.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum DisabilityTypeCode implements EdiCodeEnum {
  SHORT_TERM_DISABILITY("1", "Short Term Disability"),
  LONG_TERM_DISABILITY("2", "Long Term Disability"),
  PERMANENT_OR_TOTAL_DISABILITY("3", "Permanent or Total Disability"),
  NO_DISABILITY("4", "No Disability"),
  PARTIAL_DISABILITY("5", "Partial Disability"),
  MUTUALLY_DEFINED("Z", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<DisabilityTypeCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            DisabilityTypeCode.class,
            "Disability Type Code",
            Map.ofEntries(
                Map.entry("std", SHORT_TERM_DISABILITY),
                Map.entry("short term", SHORT_TERM_DISABILITY),
                Map.entry("ltd", LONG_TERM_DISABILITY),
                Map.entry("long term", LONG_TERM_DISABILITY),
                Map.entry("permanent", PERMANENT_OR_TOTAL_DISABILITY),
                Map.entry("total", PERMANENT_OR_TOTAL_DISABILITY),
                Map.entry("none", NO_DISABILITY),
                Map.entry("not disabled", NO_DISABILITY),
                Map.entry("partial", PARTIAL_DISABILITY)));
  }

  DisabilityTypeCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a DisabilityTypeCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching DisabilityTypeCode
   * @throws IllegalArgumentException if no match is found
   */
  public static DisabilityTypeCode fromString(String input) {
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
