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

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.data.ReferenceIdentificationQualifier;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class X834SpecTest {

  /**
   * The number of element positions each segment class renders, per loop — the surface a value can
   * actually be written into. The published table must cover exactly this, so adding a position to
   * a segment (or a loop to the generator) fails here until its spec is published too.
   */
  private static final Map<String, Integer> RENDERED_ARITY = renderedArity();

  /**
   * Positions inside an emitted segment that the 220A1 does not use, so nothing is published there.
   */
  private static final Set<String> NOT_USED_IN_220A1 = Set.of("2300 HD02");

  private static Map<String, Integer> renderedArity() {
    Map<String, Integer> arity = new LinkedHashMap<>();
    arity.put("HEADER ISA", 16);
    arity.put("HEADER GS", 8);
    arity.put("HEADER ST", 3);
    arity.put("HEADER BGN", 9);
    arity.put("HEADER REF", 3);
    arity.put("HEADER DTP", 3);
    arity.put("1000A N1", 4);
    arity.put("1000B N1", 4);
    arity.put("1000C N1", 4);
    arity.put("2000 INS", 13);
    arity.put("2000 REF", 3);
    arity.put("2000 DTP", 3);
    arity.put("2100A NM1", 9);
    arity.put("2100A DMG", 11);
    arity.put("2100A N3", 2);
    arity.put("2100A N4", 7);
    arity.put("2100A PER", 8);
    arity.put("2100A ICM", 6);
    arity.put("2100A HLH", 5);
    arity.put("2100A LUI", 3);
    arity.put("2100C NM1", 9);
    arity.put("2100C N3", 2);
    arity.put("2100C N4", 7);
    arity.put("2200 DSB", 6);
    arity.put("2200 DTP", 3);
    arity.put("2300 HD", 5);
    arity.put("2300 DTP", 3);
    arity.put("2310 LX", 1);
    arity.put("2310 NM1", 9);
    arity.put("2310 PLA", 5);
    arity.put("2320 COB", 3);
    arity.put("2320 REF", 3);
    arity.put("2320 DTP", 3);
    arity.put("2330 NM1", 9);
    arity.put("2700 LS", 1);
    arity.put("2700 LX", 1);
    arity.put("2700 LE", 1);
    arity.put("2750 N1", 4);
    arity.put("2750 REF", 3);
    arity.put("2750 DTP", 3);
    arity.put("TRAILER SE", 2);
    arity.put("TRAILER GE", 2);
    arity.put("TRAILER IEA", 2);
    return arity;
  }

  @Test
  void publishesEveryPositionOfEveryEmittedSegment() {
    List<String> missing = new ArrayList<>();
    for (Map.Entry<String, Integer> segment : RENDERED_ARITY.entrySet()) {
      for (int ordinal = 1; ordinal <= segment.getValue(); ordinal++) {
        String position = "%s%02d".formatted(segment.getKey(), ordinal);
        if (NOT_USED_IN_220A1.contains(position)) {
          assertTrue(
              publishedAt(position).isEmpty(),
              position + " is Not Used, so nothing may be published");
          continue;
        }
        if (publishedAt(position).isEmpty()) {
          missing.add(position);
        }
      }
    }
    assertEquals(List.of(), missing, "every renderable position needs a published spec");
  }

  /** A flat position, or — for a composite element — its published components. */
  private static List<ElementSpec> publishedAt(String position) {
    ElementPosition flat = ElementPosition.parse(position);
    Optional<ElementSpec> exact = X834Spec.at(flat);
    if (exact.isPresent()) {
      return List.of(exact.get());
    }
    return X834Spec.all().stream()
        .filter(
            spec ->
                spec.position().loop().equals(flat.loop())
                    && spec.position().segment().equals(flat.segment())
                    && spec.position().ordinal() == flat.ordinal())
        .toList();
  }

  @Test
  void publishesNothingOutsideTheEmittedSegments() {
    Set<String> emitted = RENDERED_ARITY.keySet();
    for (ElementSpec spec : X834Spec.all()) {
      String segment = spec.position().loop() + " " + spec.position().segment();
      assertTrue(emitted.contains(segment), segment + " is not a segment the generator emits");
      assertTrue(
          spec.position().ordinal() <= RENDERED_ARITY.get(segment),
          spec.position() + " is beyond what " + segment + " renders");
    }
  }

  @Test
  void publishesCompositeElementsOneComponentAtATime() {
    assertTrue(X834Spec.at("2000 INS06").isEmpty(), "the composite itself is not a value position");

    ElementSpec medicarePlan = X834Spec.at("2000 INS06-1").orElseThrow();
    assertEquals("1218", medicarePlan.elementId());
    assertTrue(medicarePlan.isCoded());
  }

  @Test
  void everyPublishedCodeListIsTheEnumsOwnListSoNothingCanDrift() {
    assertCodesProjectFrom("2000 INS08", EmploymentStatusCode.values());
    assertCodesProjectFrom("2000 INS04", MaintenanceReasonCode.values());
    assertCodesProjectFrom("2000 REF01", ReferenceIdentificationQualifier.values());
    assertCodesProjectFrom("1000A N101", EntityIdentifierCode.values());
  }

  private static void assertCodesProjectFrom(String position, EdiCodeEnum[] constants) {
    List<CodeValue> expected = new ArrayList<>();
    for (EdiCodeEnum constant : constants) {
      expected.add(new CodeValue(constant.getCode(), constant.getDescription()));
    }
    assertEquals(
        expected,
        X834Spec.at(position).orElseThrow().codes(),
        position + " must project from its enum");
  }

  @Test
  void theSubscriberNumberQualifierIsTheCorrectedZeroF() {
    Set<String> qualifiers = X834Spec.at("2000 REF01").orElseThrow().codeSet();

    assertTrue(qualifiers.contains("0F"), "REF*0F is the Subscriber Number qualifier");
    assertFalse(qualifiers.contains("OF"), "the letter-O spelling was a phantom code");
  }

  @Test
  void publishesTheLengthsTheSegmentClassesGetWrong() {
    // Element 127 is AN 1/50; BGN02 and REF02 are checked against 80 in the segment classes today.
    // The table is the metadata those checks should be driven from, so it must be right here first.
    ElementSpec bgn02 = X834Spec.at("HEADER BGN02").orElseThrow();
    assertEquals("127", bgn02.elementId());
    assertEquals(50, bgn02.maxLength());

    ElementSpec ref02 = X834Spec.at("2000 REF02").orElseThrow();
    assertEquals("127", ref02.elementId());
    assertEquals(50, ref02.maxLength());

    ElementSpec ref03 = X834Spec.at("2000 REF03").orElseThrow();
    assertEquals("352", ref03.elementId());
    assertEquals(80, ref03.maxLength());
  }

  @Test
  void publishesGs08AsAVersionStringNotACodeList() {
    ElementSpec version = X834Spec.at("HEADER GS08").orElseThrow();

    assertEquals(
        DataType.AN,
        version.type(),
        "element 480 is AN 1/12: 005010X220A1 is not an enumerated code");
    assertFalse(version.isCoded());
    assertEquals(12, version.maxLength());
  }

  @Test
  void namesAGeneralPurposeElementByItsPositionsName() {
    assertEquals("1073", X834Spec.at("2000 INS01").orElseThrow().elementId());
    assertEquals("Member Indicator", X834Spec.at("2000 INS01").orElseThrow().name());
    assertEquals("1073", X834Spec.at("2000 INS10").orElseThrow().elementId());
    assertEquals("Handicap Indicator", X834Spec.at("2000 INS10").orElseThrow().name());
  }

  @Test
  void repeatedSegmentsPublishOnePositionCarryingTheUnionOfTheirQualifiers() {
    // Loop 2000 writes several REFs (subscriber number, policy number, member supplemental). One
    // published REF01 carries element 128's whole list, so any occurrence's qualifier checks out.
    Set<String> qualifiers = X834Spec.at("2000 REF01").orElseThrow().codeSet();

    assertTrue(
        qualifiers.containsAll(List.of("0F", "1L", "DX", "17")),
        "any occurrence's qualifier is a member");
  }

  @Test
  void publishesEnvelopeElementsUnderTheirINumbers() {
    assertEquals("I05", X834Spec.at("HEADER ISA05").orElseThrow().elementId());
    assertEquals(
        "I05",
        X834Spec.at("HEADER ISA07").orElseThrow().elementId(),
        "both ID qualifiers are element I05");
    assertEquals("I12", X834Spec.at("TRAILER IEA02").orElseThrow().elementId());
  }

  @Test
  void everyPublishedSpecIsAddressableByItsOwnPosition() {
    for (ElementSpec spec : X834Spec.all()) {
      assertEquals(Optional.of(spec), X834Spec.at(spec.position()));
      assertEquals(
          Optional.of(spec),
          X834Spec.at(spec.position().display()),
          "canonical spelling round-trips");
    }
  }

  @Test
  void allIsInTransactionSetOrderAndIsStable() {
    List<ElementSpec> first = X834Spec.all();
    assertEquals(first, X834Spec.all());
    assertEquals(first.stream().map(ElementSpec::position).toList(), X834Spec.positions());

    List<String> loops = first.stream().map(spec -> spec.position().loop()).distinct().toList();
    assertEquals(
        List.of(
            "HEADER", "1000A", "1000B", "1000C", "2000", "2100A", "2100C", "2200", "2300", "2310",
            "2320", "2330", "2700", "2750", "TRAILER"),
        loops);
  }

  @Test
  void publishesNoPositionTwice() {
    Set<String> seen = new TreeSet<>(Comparator.naturalOrder());
    for (ElementPosition position : X834Spec.positions()) {
      assertTrue(seen.add(position.display()), "duplicate position: " + position);
    }
  }

  @Test
  void anUnpublishedPositionIsEmptyAndAMalformedOneIsAnError() {
    assertTrue(X834Spec.at("2300 HD09").isEmpty(), "well-formed but not published");
    assertThrows(IllegalArgumentException.class, () -> X834Spec.at("not a position"));
  }

  @Test
  void everySegmentOrdinalMeansTheSameThingInEveryLoopThatPublishesIt() {
    // A renderer sees a segment and an element index, never a loop. That is only safe while the
    // loops agree about each ordinal — so assert it for the whole table rather than assuming it.
    Map<String, List<ElementSpec>> byPositionWithinSegment = new LinkedHashMap<>();
    for (ElementSpec spec : X834Spec.all()) {
      ElementPosition position = spec.position();
      String key = position.segment() + position.ordinal() + "-" + position.component();
      byPositionWithinSegment.computeIfAbsent(key, k -> new ArrayList<>()).add(spec);
    }

    for (Map.Entry<String, List<ElementSpec>> entry : byPositionWithinSegment.entrySet()) {
      List<ElementSpec> specs = entry.getValue();
      ElementSpec first = specs.getFirst();
      for (ElementSpec other : specs) {
        assertEquals(
            first.elementId(), other.elementId(), entry.getKey() + " disagrees on element number");
        assertEquals(first.type(), other.type(), entry.getKey() + " disagrees on type");
        assertEquals(
            first.minLength(), other.minLength(), entry.getKey() + " disagrees on min length");
        assertEquals(
            first.maxLength(), other.maxLength(), entry.getKey() + " disagrees on max length");
        assertEquals(first.codes(), other.codes(), entry.getKey() + " disagrees on codes");
      }
    }
  }

  @Test
  void everyOrdinalIsAnswerableWithoutALoopUnlessItsComponentsDisagree() {
    Map<Integer, List<ElementSpec>> byOrdinal = new LinkedHashMap<>();
    for (ElementSpec spec : X834Spec.all()) {
      byOrdinal.computeIfAbsent(spec.position().ordinal(), k -> new ArrayList<>()).add(spec);
    }

    for (ElementSpec spec : X834Spec.all()) {
      String segment = spec.position().segment();
      int ordinal = spec.position().ordinal();
      long distinctComponents =
          byOrdinal.get(ordinal).stream()
              .filter(candidate -> candidate.position().segment().equals(segment))
              .map(candidate -> candidate.position().component())
              .distinct()
              .count();
      Optional<ElementSpec> answer = X834Spec.atSegment(segment, ordinal);
      if (distinctComponents == 1) {
        assertTrue(answer.isPresent(), segment + ordinal + " should be answerable without a loop");
      } else {
        assertTrue(
            answer.isEmpty(),
            segment
                + ordinal
                + " publishes several components, so one flat slot cannot be resolved");
      }
    }
  }

  @Test
  void atSegmentRefusesAFlatSlotThatCouldHoldSeveralComponents() {
    // DMG05 is the C056 composite and publishes three components, so a renderer holding only the
    // flat DMG05 value gets no answer rather than the wrong one. INS06 publishes one, so it does.
    assertTrue(X834Spec.atSegment("DMG", 5).isEmpty());
    assertTrue(
        X834Spec.at("2100A DMG05-1").isPresent(),
        "the components are still published individually");
  }

  @Test
  void atSegmentAnswersForASegmentSharedAcrossLoops() {
    // N4 is published in 2100A and 2100C; a renderer holding only "N4" element 02 still gets an
    // answer.
    ElementSpec state = X834Spec.atSegment("N4", 2).orElseThrow();

    assertEquals("156", state.elementId());
    assertEquals(DataType.ID, state.type());
  }

  @Test
  void atSegmentAnswersAComposteSlotWithItsFirstComponent() {
    // The INS renders C052 into one flat slot, and the 834 uses only its first component.
    ElementSpec medicarePlan = X834Spec.atSegment("INS", 6).orElseThrow();

    assertEquals("1218", medicarePlan.elementId());
    assertEquals(1, medicarePlan.position().component());
  }

  @Test
  void atSegmentIsEmptyForAnythingUnpublished() {
    assertTrue(X834Spec.atSegment("AMT", 1).isEmpty(), "a segment nothing emits");
    assertTrue(X834Spec.atSegment("HD", 2).isEmpty(), "a position Not Used in the 220A1");
    assertTrue(X834Spec.atSegment("INS", 99).isEmpty(), "an ordinal past the segment");
  }

  @Test
  void theTableIsImmutable() {
    assertThrows(UnsupportedOperationException.class, () -> X834Spec.all().clear());
    assertThrows(
        UnsupportedOperationException.class,
        () -> X834Spec.at("2000 INS08").orElseThrow().codes().clear());
  }
}
