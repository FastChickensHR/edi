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

class PayerResponsibilitySequenceCodeTest {

  @ParameterizedTest
  @EnumSource(PayerResponsibilitySequenceCode.class)
  void resolvesFromCodeNameAndDescription(PayerResponsibilitySequenceCode constant) {
    assertEquals(constant, PayerResponsibilitySequenceCode.fromString(constant.getCode()));
    assertEquals(constant, PayerResponsibilitySequenceCode.fromString(constant.name()));
    assertEquals(constant, PayerResponsibilitySequenceCode.fromString(constant.getDescription()));
  }

  @Test
  void codesAreUnique() {
    Map<String, PayerResponsibilitySequenceCode> byCode = new HashMap<>();
    for (PayerResponsibilitySequenceCode constant : PayerResponsibilitySequenceCode.values()) {
      PayerResponsibilitySequenceCode prior = byCode.put(constant.getCode(), constant);
      assertNull(prior, () -> "duplicate code '" + constant.getCode() + "'");
    }
  }

  /** The complete element 1138 list — note it skips I–M and Q–R. */
  @ParameterizedTest
  @CsvSource({
    "A,PAYER_RESPONSIBILITY_FOUR",
    "B,PAYER_RESPONSIBILITY_FIVE",
    "C,PAYER_RESPONSIBILITY_SIX",
    "D,PAYER_RESPONSIBILITY_SEVEN",
    "E,PAYER_RESPONSIBILITY_EIGHT",
    "F,PAYER_RESPONSIBILITY_NINE",
    "G,PAYER_RESPONSIBILITY_TEN",
    "H,PAYER_RESPONSIBILITY_ELEVEN",
    "N,UNCONFIRMED",
    "O,NONCAPITATED_AGREEMENT",
    "P,PRIMARY",
    "S,SECONDARY",
    "T,TERTIARY",
    "U,UNKNOWN"
  })
  void mapsEachCodeToItsElement1138Meaning(String code, PayerResponsibilitySequenceCode expected) {
    assertEquals(expected, PayerResponsibilitySequenceCode.fromString(code));
  }

  /** The three the profiled carriers send: BCBSM's {P, S} and CareFirst's U. */
  @Test
  void carriesTheCodesTheProfiledCarriersDemand() {
    assertEquals("P", PayerResponsibilitySequenceCode.PRIMARY.getCode());
    assertEquals("S", PayerResponsibilitySequenceCode.SECONDARY.getCode());
    assertEquals("U", PayerResponsibilitySequenceCode.UNKNOWN.getCode());
  }

  @Test
  void toStringIsTheRawCode() {
    assertEquals("S", PayerResponsibilitySequenceCode.SECONDARY.toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"   ", "not-a-real-value", "I", "Q"})
  void rejectsNullEmptyAndUnknown(String input) {
    assertThrows(
        IllegalArgumentException.class, () -> PayerResponsibilitySequenceCode.fromString(input));
  }
}
