/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

import java.util.Optional;

/**
 * One resolved value at a {@link Location}. A {@code null} value means <b>OMIT</b> — the location
 * is described, but nothing is generated for this subject (e.g. a situational REF with no value).
 *
 * @param location the address this value belongs to
 * @param value the resolved value, or {@code null} to OMIT
 */
public record Field(Location location, String value) {
  /** Rejects a null location; a null value stays null and means OMIT. */
  public Field {
    if (location == null) {
      throw new IllegalArgumentException("location is required");
    }
  }

  /**
   * States whether this field is the OMIT case.
   *
   * @return {@code true} when this field generates nothing (OMIT)
   */
  public boolean isOmitted() {
    return value == null;
  }

  /**
   * The value with the OMIT case made explicit.
   *
   * @return the resolved value, or empty when this field is omitted
   */
  public Optional<String> valueIfPresent() {
    return Optional.ofNullable(value);
  }
}
