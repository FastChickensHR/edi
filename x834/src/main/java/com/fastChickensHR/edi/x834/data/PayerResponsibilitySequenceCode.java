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
 * Code values for the X12 Payer Responsibility Sequence Number Code (data element 1138), which
 * identifies a carrier's level of responsibility for paying a claim. In the X12 834 (005010X220A1)
 * it appears as COB01, stating where the other plan sits relative to this one.
 *
 * <p>Carriers use a small part of this list and gloss it in their own terms: BCBSM's Medicare
 * coordination block accepts {@link #PRIMARY} ("Primary (Retired)") and {@link #SECONDARY}
 * ("Secondary (Employed)"), while CareFirst sends {@link #UNKNOWN} on every row.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum PayerResponsibilitySequenceCode implements EdiCodeEnum {
  PAYER_RESPONSIBILITY_FOUR("A", "Payer Responsibility Four"),
  PAYER_RESPONSIBILITY_FIVE("B", "Payer Responsibility Five"),
  PAYER_RESPONSIBILITY_SIX("C", "Payer Responsibility Six"),
  PAYER_RESPONSIBILITY_SEVEN("D", "Payer Responsibility Seven"),
  PAYER_RESPONSIBILITY_EIGHT("E", "Payer Responsibility Eight"),
  PAYER_RESPONSIBILITY_NINE("F", "Payer Responsibility Nine"),
  PAYER_RESPONSIBILITY_TEN("G", "Payer Responsibility Ten"),
  PAYER_RESPONSIBILITY_ELEVEN("H", "Payer Responsibility Eleven"),
  UNCONFIRMED("N", "Unconfirmed"),
  NONCAPITATED_AGREEMENT("O", "Noncapitated Agreement"),
  PRIMARY("P", "Primary"),
  SECONDARY("S", "Secondary"),
  TERTIARY("T", "Tertiary"),
  UNKNOWN("U", "Unknown");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<PayerResponsibilitySequenceCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            PayerResponsibilitySequenceCode.class,
            "Payer Responsibility Sequence Number Code",
            Map.ofEntries(
                Map.entry("first", PRIMARY),
                Map.entry("second", SECONDARY),
                Map.entry("third", TERTIARY)));
  }

  PayerResponsibilitySequenceCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a PayerResponsibilitySequenceCode instance from any input string. Matches against codes,
   * names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching PayerResponsibilitySequenceCode
   * @throws IllegalArgumentException if no match is found
   */
  public static PayerResponsibilitySequenceCode fromString(String input) {
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
