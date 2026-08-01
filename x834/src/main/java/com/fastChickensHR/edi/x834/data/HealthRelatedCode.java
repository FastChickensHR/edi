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
 * Code values for the X12 Health-Related Code (data element 1212), which states a specific health
 * situation. In the X12 834 (005010X220A1) it appears as HLH01 in Loop 2100A — in practice the
 * member's tobacco and substance-use status, a rating input for individual and small-group
 * products.
 *
 * <p>Note that {@link #NONE} and {@link #UNKNOWN} are different answers: {@code N} asserts the
 * member uses neither, while {@code U} says nobody asked. Collapsing them would turn a missing
 * answer into a negative one, which for a rated product is a statement about price.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum HealthRelatedCode implements EdiCodeEnum {
  /** None — X12 code "N". */
  NONE("N", "None"),
  /** Substance Abuse — X12 code "S". */
  SUBSTANCE_ABUSE("S", "Substance Abuse"),
  /** Tobacco Use — X12 code "T". */
  TOBACCO_USE("T", "Tobacco Use"),
  /** Unknown — X12 code "U". */
  UNKNOWN("U", "Unknown"),
  /** Tobacco Use and Substance Abuse — X12 code "X". */
  TOBACCO_USE_AND_SUBSTANCE_ABUSE("X", "Tobacco Use and Substance Abuse");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<HealthRelatedCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            HealthRelatedCode.class,
            "Health-Related Code",
            Map.ofEntries(
                Map.entry("tobacco", TOBACCO_USE),
                Map.entry("smoker", TOBACCO_USE),
                Map.entry("non smoker", NONE),
                Map.entry("neither", NONE),
                Map.entry("both", TOBACCO_USE_AND_SUBSTANCE_ABUSE),
                Map.entry("not asked", UNKNOWN)));
  }

  HealthRelatedCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a HealthRelatedCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching HealthRelatedCode
   * @throws IllegalArgumentException if no match is found
   */
  public static HealthRelatedCode fromString(String input) {
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
