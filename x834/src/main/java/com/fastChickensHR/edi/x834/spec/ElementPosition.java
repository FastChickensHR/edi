/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The address of one element position in the 005010X220A1 834 — a loop, a segment identifier, an
 * element ordinal, and (for composite elements) a component ordinal. {@code of("2000", "INS", 8)}
 * addresses {@code 2000 INS08}; {@code of("2000", "INS", 6, 1)} addresses the first component of
 * the INS06 composite, {@code 2000 INS06-1}.
 *
 * <p>Loops are named as the transaction set names them — {@code "2000"}, {@code "2100A"}, {@code
 * "2300"} — with the two envelope/control regions named {@code "HEADER"} (ISA/GS/ST/BGN and the
 * header's own REF/DTP) and {@code "TRAILER"} (SE/GE/IEA). The loop is part of the address because
 * the same segment appears in several loops with different permitted contents: {@code N1} is the
 * sponsor in 1000A, the payer in 1000B and the TPA in 1000C.
 *
 * <p>{@link #display()} produces the canonical spelling {@code "<loop> <SEG><nn>"} (two-digit
 * ordinal, {@code "-<c>"} suffix for a component). {@link #parse(String)} accepts that spelling and
 * the common hyphenated variant {@code "2100A NM1-08"}. Because an X12 element reference glues a
 * two-digit ordinal onto a segment identifier that may itself end in a digit, the ordinal is always
 * taken as the <em>last two digits</em>: {@code "2100A N401"} is N4 element 01, not N40 element 1.
 *
 * @param loop the loop name as the transaction set spells it, e.g. {@code "2000"}, {@code "2100A"},
 *     or the envelope regions {@code "HEADER"}/{@code "TRAILER"}
 * @param segment the 1-3 character X12 segment identifier, e.g. {@code "INS"}
 * @param ordinal the element ordinal within the segment, 1-99
 * @param component the component ordinal within a composite element, 1-9, or {@link #NO_COMPONENT}
 *     for an ordinary element
 */
public record ElementPosition(String loop, String segment, int ordinal, int component) {

  /** {@link #component()} value for an ordinary (non-composite) element position. */
  public static final int NO_COMPONENT = 0;

  private static final Pattern LOOP = Pattern.compile("[A-Z0-9]+");
  private static final Pattern SEGMENT = Pattern.compile("[A-Z][A-Z0-9]{0,2}");
  private static final Pattern TEXT =
      Pattern.compile("(\\S+)\\s+([A-Z][A-Z0-9]*?)-?(\\d{2})(?:-(\\d))?");

  /** Validates the address parts; see the record javadoc for each part's legal form. */
  public ElementPosition {
    if (loop == null || !LOOP.matcher(loop).matches()) {
      throw new IllegalArgumentException(
          "Loop must be an upper-case name like \"2000\" or \"HEADER\", got: " + loop);
    }
    if (segment == null || !SEGMENT.matcher(segment).matches()) {
      throw new IllegalArgumentException(
          "Segment must be a 1-3 character X12 segment identifier, got: " + segment);
    }
    if (ordinal < 1 || ordinal > 99) {
      throw new IllegalArgumentException("Element ordinal must be 1-99, got: " + ordinal);
    }
    if (component < NO_COMPONENT || component > 9) {
      throw new IllegalArgumentException(
          "Component ordinal must be 0 (none) or 1-9, got: " + component);
    }
  }

  /**
   * An ordinary element position, e.g. {@code of("2000", "INS", 8)} ⇔ {@code "2000 INS08"}.
   *
   * @param loop the loop name, e.g. {@code "2000"} or {@code "HEADER"}
   * @param segment the X12 segment identifier, e.g. {@code "INS"}
   * @param ordinal the element ordinal within the segment, 1-99
   * @return the position addressing that element
   */
  public static ElementPosition of(String loop, String segment, int ordinal) {
    return new ElementPosition(loop, segment, ordinal, NO_COMPONENT);
  }

  /**
   * One component of a composite element, e.g. {@code of("2000", "INS", 6, 1)} ⇔ {@code "2000
   * INS06-1"}.
   *
   * @param loop the loop name, e.g. {@code "2000"} or {@code "HEADER"}
   * @param segment the X12 segment identifier, e.g. {@code "INS"}
   * @param ordinal the element ordinal within the segment, 1-99
   * @param component the component ordinal within the composite, 1-9
   * @return the position addressing that component
   */
  public static ElementPosition of(String loop, String segment, int ordinal, int component) {
    return new ElementPosition(loop, segment, ordinal, component);
  }

  /**
   * Parses the canonical spelling, e.g. {@code "2000 INS08"}, {@code "HEADER BGN01"}, {@code "2000
   * INS06-1"}, {@code "2100A NM1-08"}.
   *
   * @param text the element position text to parse
   * @return the parsed position
   * @throws IllegalArgumentException if the text is not a loop followed by an element reference
   */
  public static ElementPosition parse(String text) {
    if (text == null) {
      throw new IllegalArgumentException("Element position text cannot be null");
    }
    Matcher matcher = TEXT.matcher(text.trim());
    if (!matcher.matches()) {
      throw new IllegalArgumentException(
          "Not an element position (expected e.g. \"2000 INS08\" or \"2000 INS06-1\"), got: "
              + text);
    }
    return new ElementPosition(
        matcher.group(1),
        matcher.group(2),
        Integer.parseInt(matcher.group(3)),
        matcher.group(4) == null ? NO_COMPONENT : Integer.parseInt(matcher.group(4)));
  }

  /**
   * Whether this position addresses one component of a composite element.
   *
   * @return true when {@link #component()} is not {@link #NO_COMPONENT}
   */
  public boolean isComponent() {
    return component != NO_COMPONENT;
  }

  /**
   * The canonical spelling, e.g. {@code "2000 INS08"} or {@code "2000 INS06-1"}.
   *
   * @return the spelling {@code "<loop> <SEG><nn>"}, with a {@code "-<c>"} suffix for a component
   */
  public String display() {
    String reference = "%s%02d".formatted(segment, ordinal);
    return loop + " " + (isComponent() ? reference + "-" + component : reference);
  }

  @Override
  public String toString() {
    return display();
  }
}
