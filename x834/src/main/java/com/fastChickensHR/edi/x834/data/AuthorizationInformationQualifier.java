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
 * Code values for the Authorization Information Qualifier (ISA01, X12 data element I01) in the
 * Interchange Control Header (ISA) of an X12 834 interchange (005010X220A1). Qualifies the type of
 * authorization information carried in ISA02.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum AuthorizationInformationQualifier implements EdiCodeEnum {
  /** No Authorization — X12 code "00". */
  NO_AUTHORIZATION_INFORMATION("00", "No Authorization"),
  /** UCS Communications ID — X12 code "01". */
  UCS_COMMUNICATIONS_ID("01", "UCS Communications ID"),
  /** EDX Communications ID — X12 code "02". */
  EDX_COMMUNICATIONS_ID("02", "EDX Communications ID"),
  /** Additional Data Identification — X12 code "03". */
  ADDITIONAL_DATA_ID("03", "Additional Data Identification"),
  /** Rail Communications ID — X12 code "04". */
  RAIL_COMMUNICATIONS_ID("04", "Rail Communications ID"),
  /** Department of Defense (DoD) Communication Identifier — X12 code "05". */
  DOD_COMMUNICATION_ID("05", "Department of Defense (DoD) Communication Identifier"),
  /** United States Federal Government Communication Identifier — X12 code "06". */
  US_FEDERAL_GOVT_COMM_ID("06", "United States Federal Government Communication Identifier");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<AuthorizationInformationQualifier> LOOKUP;

  static {
    // Include some additional common terms and abbreviations
    LOOKUP =
        new EdiEnumLookup<>(
            AuthorizationInformationQualifier.class,
            "Authorization Information Qualifier",
            Map.ofEntries(
                Map.entry("none", NO_AUTHORIZATION_INFORMATION),
                Map.entry("noauth", NO_AUTHORIZATION_INFORMATION),
                Map.entry("ucs", UCS_COMMUNICATIONS_ID),
                Map.entry("edx", EDX_COMMUNICATIONS_ID),
                Map.entry("additionaldata", ADDITIONAL_DATA_ID),
                Map.entry("rail", RAIL_COMMUNICATIONS_ID),
                Map.entry("dod", DOD_COMMUNICATION_ID),
                Map.entry("defense", DOD_COMMUNICATION_ID),
                Map.entry("federal", US_FEDERAL_GOVT_COMM_ID),
                Map.entry("govt", US_FEDERAL_GOVT_COMM_ID),
                Map.entry("usgov", US_FEDERAL_GOVT_COMM_ID)));
  }

  AuthorizationInformationQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an AuthorizationInformationQualifier instance from any input string. Matches against
   * codes, names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching AuthorizationInformationQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static AuthorizationInformationQualifier fromString(String input) {
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
