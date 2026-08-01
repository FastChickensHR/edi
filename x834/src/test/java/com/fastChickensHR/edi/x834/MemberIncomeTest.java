/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fastChickensHR.edi.x834.data.FrequencyCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

class MemberIncomeTest {
  private final X834Context context = new X834Context();

  private String render(ICMSegment segment) {
    segment.setContext(context);
    return segment.render().trim();
  }

  @Test
  void rendersTheBcbsKansasShapeWithTheDepartmentNumberOnIcm04() throws ValidationException {
    // Kansas places its department number at ICM04 — "Location Identification Code … Department
    // Number if applicable" — and the wage at ICM02. ICM03 is empty between them, so it renders
    // as an empty element rather than being trimmed.
    ICMSegment income =
        MemberIncome.builder()
            .setFrequencyCode(FrequencyCode.MONTHLY)
            .setMonetaryAmount("4500")
            .setLocationIdentifier("DEPT42")
            .build();

    assertEquals("ICM*4*4500**DEPT42~", render(income));
  }

  @Test
  void rendersTheMandatoryPairAloneWithNoTrailingElements() throws ValidationException {
    ICMSegment income =
        MemberIncome.builder()
            .setFrequencyCode(FrequencyCode.HOURLY)
            .setMonetaryAmount("24.50")
            .build();

    assertEquals("ICM*H*24.50~", render(income));
  }

  @Test
  void carriesEveryElementWhenAllArePresent() throws ValidationException {
    ICMSegment income =
        MemberIncome.builder()
            .setFrequencyCode(FrequencyCode.WEEKLY)
            .setMonetaryAmount("1200")
            .setQuantity("40")
            .setLocationIdentifier("DEPT42")
            .setSalaryGrade("G7")
            .setCurrencyCode("USD")
            .build();

    assertEquals("ICM*1*1200*40*DEPT42*G7*USD~", render(income));
    assertEquals("ICM", income.getSegmentIdentifier());
    assertEquals(FrequencyCode.WEEKLY, income.getFrequencyCode());
    assertEquals("DEPT42", income.getLocationIdentifier());
  }

  @Test
  void preservesTheCallersOwnAmountFormatting() throws ValidationException {
    // The amount is carried as written. Reformatting or rounding it here would be a silent
    // restatement of someone's pay.
    ICMSegment income =
        MemberIncome.builder()
            .setFrequencyCode(FrequencyCode.ANNUAL)
            .setMonetaryAmount("52000.00")
            .build();

    assertEquals("ICM*7*52000.00~", render(income));
  }

  @Test
  void rejectsAnIncomeWithNoFrequency() {
    // ICM01 is mandatory, and an amount without its period is meaningless — 2000 could be
    // weekly or annual.
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> MemberIncome.builder().setMonetaryAmount("4500").build());

    assertTrue(ex.getMessage().contains("ICM01"), ex.getMessage());
  }

  @Test
  void rejectsALocationIdentifierSentWithoutTheMandatoryPair() {
    // The case a carrier hits wanting only Kansas's ICM04 department number: the 834 will not
    // carry it alone, because ICM01/ICM02 are mandatory.
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> MemberIncome.builder().setLocationIdentifier("DEPT42").build());

    assertTrue(ex.getMessage().contains("ICM01"), ex.getMessage());
  }

  @Test
  void rejectsAnIncomeWithNoAmount() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> MemberIncome.builder().setFrequencyCode(FrequencyCode.MONTHLY).build());

    assertTrue(ex.getMessage().contains("ICM02"), ex.getMessage());
  }

  @Test
  void rejectsALocationIdentifierLongerThanElement310Allows() {
    String tooLong = "D".repeat(ICMSegment.MAX_LOCATION_IDENTIFIER_LENGTH + 1);

    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberIncome.builder()
                    .setFrequencyCode(FrequencyCode.MONTHLY)
                    .setMonetaryAmount("4500")
                    .setLocationIdentifier(tooLong)
                    .build());

    assertTrue(ex.getMessage().contains("30"), ex.getMessage());
  }

  @Test
  void rejectsASalaryGradeLongerThanElement1214Allows() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberIncome.builder()
                    .setFrequencyCode(FrequencyCode.MONTHLY)
                    .setMonetaryAmount("4500")
                    .setSalaryGrade("GRADE7")
                    .build());

    assertTrue(ex.getMessage().contains("5"), ex.getMessage());
  }

  @Test
  void rejectsACurrencyCodeThatIsNotThreeCharacters() {
    // Element 100 is ID 3/3 — "US" is not a currency code.
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberIncome.builder()
                    .setFrequencyCode(FrequencyCode.MONTHLY)
                    .setMonetaryAmount("4500")
                    .setCurrencyCode("US")
                    .build());

    assertTrue(ex.getMessage().contains("ICM06"), ex.getMessage());
  }
}
