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
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CommunicationNumberQualifierTest {

  /**
   * Every constant resolves from its own X12 code, its enum name, and its description — the three
   * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
   */
  @ParameterizedTest
  @EnumSource(CommunicationNumberQualifier.class)
  void resolvesFromCodeNameAndDescription(CommunicationNumberQualifier constant) {
    assertEquals(constant, CommunicationNumberQualifier.fromString(constant.getCode()));
    assertEquals(constant, CommunicationNumberQualifier.fromString(constant.name()));
    assertEquals(constant, CommunicationNumberQualifier.fromString(constant.getDescription()));
  }

  /** Every constant has a distinct code, so none is unreachable by code in the shared lookup. */
  @Test
  void codesAreUnique() {
    Map<String, CommunicationNumberQualifier> byCode = new HashMap<>();
    for (CommunicationNumberQualifier constant : CommunicationNumberQualifier.values()) {
      CommunicationNumberQualifier prior = byCode.put(constant.getCode(), constant);
      assertNull(prior, () -> "duplicate code '" + constant.getCode() + "'");
    }
  }

  /**
   * The 834 subset the carriers profiled in this project actually ask for: BCBSM MembersEdge {@code
   * EM}/{@code HP}/{@code WP}, its Medicare Advantage product {@code AP}/{@code CP}/{@code
   * EM}/{@code HP}/{@code TE}, and Anthem {@code HP} + {@code EM}.
   */
  @ParameterizedTest
  @ValueSource(strings = {"AP", "CP", "EM", "EX", "FX", "HP", "TE", "WP"})
  void carriesTheCodesTheProfiledCarriersDemand(String code) {
    assertEquals(code, CommunicationNumberQualifier.fromString(code).getCode());
  }

  /** The human-friendly aliases callers actually type resolve to the right constant. */
  @ParameterizedTest
  @MethodSource("aliases")
  void resolvesFromCommonAliases(String input, CommunicationNumberQualifier expected) {
    assertEquals(expected, CommunicationNumberQualifier.fromString(input));
  }

  private static Stream<Arguments> aliases() {
    return Stream.of(
        Arguments.of("email", CommunicationNumberQualifier.ELECTRONIC_MAIL),
        Arguments.of("e-mail", CommunicationNumberQualifier.ELECTRONIC_MAIL),
        Arguments.of("home", CommunicationNumberQualifier.HOME_PHONE),
        Arguments.of("work", CommunicationNumberQualifier.WORK_PHONE),
        Arguments.of("office", CommunicationNumberQualifier.WORK_PHONE),
        Arguments.of("cell", CommunicationNumberQualifier.CELLULAR_PHONE),
        Arguments.of("mobile", CommunicationNumberQualifier.CELLULAR_PHONE),
        Arguments.of("fax", CommunicationNumberQualifier.FACSIMILE),
        Arguments.of("phone", CommunicationNumberQualifier.TELEPHONE),
        Arguments.of("extension", CommunicationNumberQualifier.TELEPHONE_EXTENSION));
  }

  /** The enum renders as its raw X12 code, so it drops straight into an element. */
  @Test
  void toStringIsTheRawCode() {
    assertEquals("HP", CommunicationNumberQualifier.HOME_PHONE.toString());
  }

  /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"   ", "not-a-real-value"})
  void rejectsNullEmptyAndUnknown(String input) {
    assertThrows(
        IllegalArgumentException.class, () -> CommunicationNumberQualifier.fromString(input));
  }
}
