/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Everything the standard says about one element position: its X12 element number, the standard's
 * name for it, its {@link DataType}, its length bounds, and — for a coded element — the codes it
 * permits.
 *
 * <p>The code list is the <em>widest legal ring</em> at this position: a consumer may narrow it (a
 * trading partner's restriction, an organisation's house rules) but may never widen it, and {@link
 * #permits(Collection)} is the check that a proposed narrowing really is a narrowing. Lists are
 * projected from this library's code enums rather than transcribed, so the published metadata and
 * the codes the builders accept cannot drift apart.
 *
 * <p>{@code elementId} is the X12 element number as the standard writes it — an integer for a data
 * element ({@code "584"}), an I-number for an envelope element ({@code "I05"}) — and {@code name}
 * is the name at <em>this position</em>, which for a general-purpose element is the one the 834
 * gives it: element 1073 is the "Member Indicator" at {@code 2000 INS01} and the "Handicap
 * Indicator" at {@code 2000 INS10}.
 *
 * <p>An {@link DataType#ID} position with an empty code list means this library does not publish
 * that list, never that any value is legal there: {@link #isCoded()} is false and {@link #permits}
 * refuses to answer rather than blessing everything.
 *
 * <p>Membership is <strong>strict</strong>: codes are compared verbatim, case-sensitively. The
 * forgiving {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} — which resolves descriptions
 * and colloquial aliases, so that {@code "fired"} finds a code — is deliberately unreachable from
 * here. Resolving user input and checking conformance are different jobs, and only the second one
 * belongs to the spec.
 */
public record ElementSpec(
    ElementPosition position,
    String elementId,
    String name,
    DataType type,
    int minLength,
    int maxLength,
    List<CodeValue> codes) {

  public ElementSpec {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null");
    }
    if (elementId == null || elementId.isBlank()) {
      throw new IllegalArgumentException("Element id cannot be null or blank at " + position);
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Element name cannot be null or blank at " + position);
    }
    if (type == null) {
      throw new IllegalArgumentException("Data type cannot be null at " + position);
    }
    if (minLength < 1 || maxLength < minLength) {
      throw new IllegalArgumentException(
          "Length bounds must satisfy 1 <= min <= max at "
              + position
              + ", got: "
              + minLength
              + "/"
              + maxLength);
    }
    codes = List.copyOf(codes);
    if (!codes.isEmpty() && type != DataType.ID) {
      throw new IllegalArgumentException(
          "Only an ID element carries a code list, but " + position + " is " + type);
    }
  }

  /** Whether this position is coded — i.e. its legal values are a closed list. */
  public boolean isCoded() {
    return !codes.isEmpty();
  }

  /**
   * The widest character set a value at this position may draw from, given its type: string
   * elements may carry {@link CharacterClass#EXTENDED}, while codes, dates, times and numbers are
   * spelled with upper-case letters and digits and so never need more than {@link
   * CharacterClass#BASIC}. What an individual interchange permits is narrower still — that is the
   * trading partner's pick, not the element's property.
   */
  public CharacterClass characterClass() {
    return type == DataType.AN ? CharacterClass.EXTENDED : CharacterClass.BASIC;
  }

  /** The permitted codes as a set, in the order the standard lists them. Empty when not coded. */
  public Set<String> codeSet() {
    Set<String> set = new LinkedHashSet<>(codes.size());
    for (CodeValue value : codes) {
      set.add(value.code());
    }
    return set;
  }

  /**
   * Whether {@code proposed} is a subset of the codes permitted at this position — the check a
   * consumer runs when it narrows the ring, e.g. "this trading partner permits only {@code AC},
   * {@code RT}, {@code TE} at {@code 2000 INS08}: are those all real element-584 codes?".
   *
   * <p>Comparison is verbatim and case-sensitive; a {@code null} or blank entry is reported as
   * unknown rather than ignored, since neither is a code. An empty proposal trivially passes: it
   * narrows nothing.
   *
   * @throws IllegalStateException if this position is not coded — proposing a code list for a
   *     free-text element is a mistake in the caller's data, not a failed check, and silently
   *     answering "not permitted" would hide it
   */
  public SubsetResult permits(Collection<String> proposed) {
    if (!isCoded()) {
      throw new IllegalStateException(
          type == DataType.ID
              ? position.display()
                  + " is an ID element ("
                  + elementId
                  + ") whose code list this library does not publish, so there is nothing to check against"
              : position.display()
                  + " is not a coded element ("
                  + type
                  + " "
                  + minLength
                  + "/"
                  + maxLength
                  + "), so it has no code list to check against");
    }
    if (proposed == null) {
      throw new IllegalArgumentException("Proposed codes cannot be null");
    }
    Set<String> permitted = codeSet();
    List<String> unknown = new ArrayList<>();
    for (String code : proposed) {
      if (code == null || code.isBlank() || !permitted.contains(code)) {
        unknown.add(code == null ? "<null>" : code);
      }
    }
    return new SubsetResult(position, unknown);
  }
}
