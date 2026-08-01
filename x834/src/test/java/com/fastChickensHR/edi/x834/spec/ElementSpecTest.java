/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ElementSpecTest {

  private static ElementSpec employmentStatus() {
    return X834Spec.at("2000 INS08").orElseThrow();
  }

  private static ElementSpec memberLastName() {
    return X834Spec.at("2100A NM103").orElseThrow();
  }

  @Test
  void aSubsetOfThePermittedCodesPasses() {
    SubsetResult result = employmentStatus().permits(List.of("AC", "TE", "FT"));

    assertTrue(result.ok(), result.describe());
    assertEquals(List.of(), result.unknownCodes());
    assertEquals("2000 INS08 permits every proposed code", result.describe());
  }

  @Test
  void aCodeThatIsNotAMemberIsReportedByName() {
    SubsetResult result = employmentStatus().permits(List.of("AC", "ZZZ", "TE", "1"));

    assertFalse(result.ok());
    assertEquals(List.of("ZZZ", "1"), result.unknownCodes(), "in the order they were proposed");
    assertEquals("2000 INS08 does not permit: ZZZ, 1", result.describe());
  }

  @Test
  void membershipIsStrictNotTheForgivingLookupTheBuildersUse() {
    // MaintenanceTypeCode.fromString("add") resolves to 001 — that is the right behaviour when
    // taking loose input, and the wrong behaviour for a conformance check. A spec check that
    // accepted "add" at INS03 would not be checking the spec.
    assertEquals(MaintenanceTypeCode.ADDITION, MaintenanceTypeCode.fromString("add"));

    ElementSpec maintenanceType = X834Spec.at("2000 INS03").orElseThrow();
    assertFalse(maintenanceType.permits(List.of("add")).ok(), "an alias is not a code");
    assertFalse(maintenanceType.permits(List.of("Addition")).ok(), "a description is not a code");
    assertFalse(maintenanceType.permits(List.of("1")).ok(), "a shorthand is not a code");
    assertTrue(maintenanceType.permits(List.of("001")).ok());
  }

  @Test
  void membershipIsCaseSensitive() {
    assertTrue(employmentStatus().permits(List.of("AC")).ok());
    assertFalse(employmentStatus().permits(List.of("ac")).ok());
  }

  @Test
  void anEmptyProposalNarrowsNothingAndPasses() {
    assertTrue(employmentStatus().permits(List.of()).ok());
  }

  @Test
  void anAbsentOrBlankEntryIsReportedRatherThanSkipped() {
    SubsetResult result = employmentStatus().permits(Arrays.asList("AC", null, "  ", ""));

    assertFalse(result.ok());
    assertEquals(List.of("<null>", "  ", ""), result.unknownCodes());
  }

  @Test
  void aFreeTextPositionRefusesToAnswerASubsetCheck() {
    ElementSpec lastName = memberLastName();
    assertFalse(lastName.isCoded());

    IllegalStateException thrown =
        assertThrows(IllegalStateException.class, () -> lastName.permits(List.of("DOE")));
    assertTrue(thrown.getMessage().contains("2100A NM103"), thrown.getMessage());
    assertTrue(thrown.getMessage().contains("not a coded element"), thrown.getMessage());
  }

  @Test
  void anIdPositionWithNoPublishedListSaysSoRatherThanBlessingEverything() {
    ElementSpec entityTypeQualifier = X834Spec.at("2100A NM102").orElseThrow();
    assertEquals(DataType.ID, entityTypeQualifier.type());
    assertFalse(entityTypeQualifier.isCoded());

    IllegalStateException thrown =
        assertThrows(IllegalStateException.class, () -> entityTypeQualifier.permits(List.of("1")));
    assertTrue(thrown.getMessage().contains("does not publish"), thrown.getMessage());
  }

  @Test
  void codeSetKeepsTheStandardsOrder() {
    List<String> codes = List.copyOf(X834Spec.at("2000 INS03").orElseThrow().codeSet());

    assertEquals("001", codes.getFirst());
    assertEquals(
        Arrays.stream(MaintenanceTypeCode.values()).map(MaintenanceTypeCode::getCode).toList(),
        codes);
  }

  @Test
  void theWidestCharacterSetFollowsTheType() {
    assertEquals(
        CharacterClass.EXTENDED, memberLastName().characterClass(), "a name is a string element");
    assertEquals(
        CharacterClass.BASIC, employmentStatus().characterClass(), "a code needs no lower case");
    assertEquals(
        CharacterClass.BASIC,
        X834Spec.at("HEADER GS04").orElseThrow().characterClass(),
        "nor does a date");
  }

  @Test
  void rejectsIncoherentMetadata() {
    ElementPosition position = ElementPosition.parse("2000 INS08");
    List<CodeValue> codes = List.of(new CodeValue("AC", "Active"));

    assertThrows(
        IllegalArgumentException.class,
        () -> new ElementSpec(position, " ", "Employment Status Code", DataType.ID, 2, 2, codes));
    assertThrows(
        IllegalArgumentException.class,
        () -> new ElementSpec(position, "584", " ", DataType.ID, 2, 2, codes));
    assertThrows(
        IllegalArgumentException.class,
        () -> new ElementSpec(position, "584", "Employment Status Code", DataType.ID, 0, 2, codes));
    assertThrows(
        IllegalArgumentException.class,
        () -> new ElementSpec(position, "584", "Employment Status Code", DataType.ID, 3, 2, codes));
    assertThrows(
        IllegalArgumentException.class,
        () -> new ElementSpec(position, "584", "Employment Status Code", DataType.AN, 2, 2, codes),
        "only an ID element carries a code list");
  }

  @Test
  void rejectsACodeValueWithoutADescriptionToShow() {
    assertThrows(IllegalArgumentException.class, () -> new CodeValue("AC", " "));
    assertThrows(IllegalArgumentException.class, () -> new CodeValue(" ", "Active"));
  }
}
