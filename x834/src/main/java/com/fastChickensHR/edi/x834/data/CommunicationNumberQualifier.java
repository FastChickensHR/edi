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
 * Code values for the X12 Communication Number Qualifier (data element 365), which states what kind
 * of communication number the element that follows it carries. In the X12 834 (005010X220A1) it
 * appears as PER03, PER05 and PER07, each qualifying the number in the PER04/PER06/PER08 that
 * follows it.
 *
 * <p>This is the 834 subset — the qualifiers the TR3 permits on a member communications {@code
 * PER}. Carriers select from it per product: BCBSM MembersEdge asks for {@code EM}/{@code
 * HP}/{@code WP} and its Medicare Advantage product for {@code AP}/{@code CP}/{@code EM}/{@code
 * HP}/{@code TE}, while Anthem sends {@code HP} plus {@code EM}.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum CommunicationNumberQualifier implements EdiCodeEnum {
  ALTERNATE_TELEPHONE("AP", "Alternate Telephone"),
  CELLULAR_PHONE("CP", "Cellular Phone"),
  ELECTRONIC_MAIL("EM", "Electronic Mail"),
  TELEPHONE_EXTENSION("EX", "Telephone Extension"),
  FACSIMILE("FX", "Facsimile"),
  HOME_PHONE("HP", "Home Phone Number"),
  TELEPHONE("TE", "Telephone"),
  WORK_PHONE("WP", "Work Phone Number");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<CommunicationNumberQualifier> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            CommunicationNumberQualifier.class,
            "Communication Number Qualifier",
            Map.ofEntries(
                Map.entry("email", ELECTRONIC_MAIL),
                Map.entry("e-mail", ELECTRONIC_MAIL),
                Map.entry("home", HOME_PHONE),
                Map.entry("work", WORK_PHONE),
                Map.entry("office", WORK_PHONE),
                Map.entry("cell", CELLULAR_PHONE),
                Map.entry("mobile", CELLULAR_PHONE),
                Map.entry("fax", FACSIMILE),
                Map.entry("phone", TELEPHONE),
                Map.entry("extension", TELEPHONE_EXTENSION)));
  }

  CommunicationNumberQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a CommunicationNumberQualifier instance from any input string. Matches against codes,
   * names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching CommunicationNumberQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static CommunicationNumberQualifier fromString(String input) {
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
