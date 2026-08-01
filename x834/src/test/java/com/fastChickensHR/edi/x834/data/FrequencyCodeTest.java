/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FrequencyCodeTest {

  @ParameterizedTest
  @EnumSource(FrequencyCode.class)
  void resolvesFromCodeNameAndDescription(FrequencyCode constant) {
    assertEquals(constant, FrequencyCode.fromString(constant.getCode()));
    assertEquals(constant, FrequencyCode.fromString(constant.name()));
    assertEquals(constant, FrequencyCode.fromString(constant.getDescription()));
  }

  @Test
  void codesAreUnique() {
    Map<String, FrequencyCode> byCode = new HashMap<>();
    for (FrequencyCode constant : FrequencyCode.values()) {
      FrequencyCode prior = byCode.put(constant.getCode(), constant);
      assertNull(prior, () -> "duplicate code '" + constant.getCode() + "'");
    }
  }

  /** The complete element 594 list — note it skips I, K–P, R and T–Y. */
  @ParameterizedTest
  @CsvSource({
    "0,ANNUALIZED",
    "1,WEEKLY",
    "2,BIWEEKLY",
    "3,SEMIMONTHLY",
    "4,MONTHLY",
    "5,OTHER",
    "6,DAILY",
    "7,ANNUAL",
    "8,TWO_CALENDAR_MONTHS",
    "9,LUMP_SUM_SEPARATION_ALLOWANCE",
    "A,QUARTER_TO_DATE",
    "B,YEAR_TO_DATE",
    "C,SINGLE",
    "D,POLICY_PERIOD",
    "E,CLAIM_PERIOD",
    "F,UNIT_REPORT_IDENTIFIER",
    "G,MONTH_TO_DATE",
    "H,HOURLY",
    "J,CURRENT_PERIOD",
    "Q,QUARTERLY",
    "S,SEMIANNUAL",
    "U,UNKNOWN",
    "Z,MUTUALLY_DEFINED"
  })
  void mapsEachCodeToItsElement594Meaning(String code, FrequencyCode expected) {
    assertEquals(expected, FrequencyCode.fromString(code));
  }

  /**
   * {@code 0} (Annualized; 12-month equivalent) and {@code 7} (Annual) are distinct: the first is a
   * figure scaled up to a year, the second a figure actually paid over one. Conflating them would
   * restate a part-year wage as a full-year one.
   */
  @Test
  void annualizedIsNotAnnual() {
    assertEquals("0", FrequencyCode.ANNUALIZED.getCode());
    assertEquals("7", FrequencyCode.ANNUAL.getCode());
  }

  @Test
  void toStringIsTheRawCode() {
    assertEquals("4", FrequencyCode.MONTHLY.toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"   ", "not-a-real-value", "I", "K", "T"})
  void rejectsNullEmptyAndUnknown(String input) {
    assertThrows(IllegalArgumentException.class, () -> FrequencyCode.fromString(input));
  }
}
