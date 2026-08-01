/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.dates;

import lombok.Getter;

/** Enumeration of standard EDI time formats. */
@Getter
public enum TimeFormat {
  /** Time format HHMM (e.g., 1230) */
  TIME("HHMM", "^\\d{4}$"),

  /** Time format with seconds HHMMSS (e.g., 123045) */
  TIME_WITH_SECONDS("HHMMSS", "^\\d{6}$");

  private final String format;
  private final String validationPattern;

  // Proper enum constructor
  TimeFormat(String format, String validationPattern) {
    this.format = format;
    this.validationPattern = validationPattern;
  }

  /**
   * Renders the given date/time as a string encoded in this format.
   *
   * @param time the date/time to format
   * @return the formatted value (e.g. {@code 1230} for {@link #TIME})
   */
  public String format(java.time.LocalDateTime time) {
    return DateFormatter.formatTime(this, time);
  }
}
