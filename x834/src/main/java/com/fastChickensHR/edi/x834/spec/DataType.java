/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

/**
 * The X12 data type of an element position, as the standard names it.
 *
 * <p>Only the types the 834 uses are modelled. Composite elements are not a data type here: a
 * composite is addressed one component at a time (see {@link ElementPosition#component()}), and
 * each component carries its own type.
 */
public enum DataType {
  /** Identifier — the value must be a member of a closed code list. */
  ID("Identifier"),
  /** String — free text within the interchange's character set. */
  AN("String"),
  /** Date, {@code CCYYMMDD} (or {@code YYMMDD} in the envelope). */
  DT("Date"),
  /** Time, {@code HHMM} optionally extended with seconds/decimals. */
  TM("Time"),
  /** Numeric with an implied decimal count of zero — digits only. */
  N0("Numeric"),
  /** Decimal number — digits with an optional explicit decimal point and sign. */
  R("Decimal number");

  private final String description;

  DataType(String description) {
    this.description = description;
  }

  /**
   * The standard's name for this type, e.g. {@code "Identifier"} for {@link #ID}.
   *
   * @return the human-readable type name
   */
  public String getDescription() {
    return description;
  }
}
