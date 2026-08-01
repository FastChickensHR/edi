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

import com.fastChickensHR.edi.x834.data.HealthRelatedCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

class MemberHealthAndLanguageTest {
  private final X834Context context = new X834Context();

  private String render(Segment segment) {
    segment.setContext(context);
    return segment.render().trim();
  }

  // ---- HLH (2100A member health information) ----

  @Test
  void rendersTobaccoUseAloneAsHlh01() throws ValidationException {
    HLHSegment health =
        MemberHealthInformation.builder()
            .setHealthRelatedCode(HealthRelatedCode.TOBACCO_USE)
            .build();

    assertEquals("HLH*T~", render(health));
    assertEquals(HealthRelatedCode.TOBACCO_USE, health.getHealthRelatedCode());
  }

  @Test
  void distinguishesStatedNoneFromUnknown() throws ValidationException {
    // N asserts the member uses neither; U says nobody asked. For a rated product that is a
    // statement about price, so the two must not collapse.
    assertEquals(
        "HLH*N~",
        render(
            MemberHealthInformation.builder()
                .setHealthRelatedCode(HealthRelatedCode.NONE)
                .build()));
    assertEquals(
        "HLH*U~",
        render(
            MemberHealthInformation.builder()
                .setHealthRelatedCode(HealthRelatedCode.UNKNOWN)
                .build()));
  }

  @Test
  void carriesHeightAndWeightAlongsideTheCode() throws ValidationException {
    HLHSegment health =
        MemberHealthInformation.builder()
            .setHealthRelatedCode(HealthRelatedCode.NONE)
            .setHeight("70")
            .setCurrentWeight("180")
            .setPreviousWeight("195")
            .setDescription("DIET")
            .build();

    assertEquals("HLH*N*70*180*195*DIET~", render(health));
  }

  @Test
  void rendersHeightAndWeightWithoutAHealthCode() throws ValidationException {
    // HLH01 is optional, so an empty first element must still hold its slot.
    HLHSegment health =
        MemberHealthInformation.builder().setHeight("70").setCurrentWeight("180").build();

    assertEquals("HLH**70*180~", render(health));
  }

  @Test
  void rejectsAnHlhCarryingNothing() {
    ValidationException ex =
        assertThrows(ValidationException.class, () -> MemberHealthInformation.builder().build());

    assertTrue(ex.getMessage().contains("no health information"), ex.getMessage());
  }

  @Test
  void rejectsAWeightLongerThanElement81Allows() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberHealthInformation.builder()
                    .setHealthRelatedCode(HealthRelatedCode.NONE)
                    .setCurrentWeight("1".repeat(HLHSegment.MAX_WEIGHT_LENGTH + 1))
                    .build());

    assertTrue(ex.getMessage().contains("HLH03"), ex.getMessage());
  }

  // ---- LUI (2100A member language) ----

  @Test
  void namesALanguageByCodeUnderItsScheme() throws ValidationException {
    LUISegment language =
        MemberLanguage.builder().setLanguage(IdentificationCodeQualifier.CMS_NPI, "SPA").build();

    assertEquals("LUI*XX*SPA~", render(language));
    assertEquals("SPA", language.getLanguageCode());
  }

  @Test
  void namesALanguageInWordsAlone() throws ValidationException {
    LUISegment language = MemberLanguage.builder().setDescription("SPANISH").build();

    assertEquals("LUI***SPANISH~", render(language));
  }

  @Test
  void carriesBothCodeAndDescription() throws ValidationException {
    LUISegment language =
        MemberLanguage.builder()
            .setLanguage(IdentificationCodeQualifier.CMS_NPI, "SPA")
            .setDescription("SPANISH")
            .build();

    assertEquals("LUI*XX*SPA*SPANISH~", render(language));
  }

  @Test
  void rejectsACodeWithoutItsQualifier() {
    // The 834's own relational condition: LUI01 and LUI02 require each other.
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> MemberLanguage.builder().setLanguage(null, "SPA").build());

    assertTrue(ex.getMessage().contains("LUI01"), ex.getMessage());
  }

  @Test
  void rejectsAQualifierWithoutItsCode() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberLanguage.builder()
                    .setLanguage(IdentificationCodeQualifier.CMS_NPI, null)
                    .build());

    assertTrue(ex.getMessage().contains("LUI02"), ex.getMessage());
  }

  @Test
  void rejectsALuiNamingNoLanguageAtAll() {
    ValidationException ex =
        assertThrows(ValidationException.class, () -> MemberLanguage.builder().build());

    assertTrue(ex.getMessage().contains("names no language"), ex.getMessage());
  }

  @Test
  void rejectsALanguageCodeShorterThanElement67Allows() {
    // Element 67 is AN 2/80 — a single character is not a language code.
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () ->
                MemberLanguage.builder()
                    .setLanguage(IdentificationCodeQualifier.CMS_NPI, "S")
                    .build());

    assertTrue(ex.getMessage().contains("at least 2"), ex.getMessage());
  }
}
