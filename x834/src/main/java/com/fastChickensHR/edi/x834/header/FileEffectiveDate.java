/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.Segment;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.DateTimeQualifier;
import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import java.time.LocalDateTime;

/**
 * File effective date (DTP*007) in the header portion of the X12 834 (005010X220A1).
 *
 * <p>Produces the DTP segment carrying the master effective date for the file: DTP01 = date/time
 * qualifier (defaults to {@code 007}, Effective), DTP02 = the context's date format, DTP03 = the
 * formatted date value.
 */
class FileEffectiveDate extends Segment {
  public static final String SEGMENT_ID = "DTP";

  /** Default DTP01 date/time qualifier {@code "007"} (Effective). */
  public static final String DEFAULT_DATE_TIME_QUALIFIER = "007";

  /** DTP01 — date/time qualifier stating what the date represents. */
  private final DateTimeQualifier dtp01;

  /** DTP02 — date/time format qualifier stating how DTP03 is encoded. */
  private final DateFormat dtp02;

  /** DTP03 — the date/time period value. */
  private final String dtp03;

  private FileEffectiveDate(Builder builder) throws ValidationException {
    this.dtp01 = builder.dtp01;
    this.dtp02 = builder.dtp02;
    this.dtp03 = builder.dtp03;

    if (this.dtp03 == null || this.dtp03.isEmpty()) {
      throw new ValidationException("dtp03 (Date Time Period) is required");
    }
  }

  @Override
  public String getSegmentIdentifier() {
    return SEGMENT_ID;
  }

  @Override
  public String[] getElementValues() {
    return new String[] {dtp01.getCode(), dtp02.getFormat(), dtp03};
  }

  /**
   * @return DTP01 — the date/time qualifier.
   */
  public DateTimeQualifier getDateTimeQualifier() {
    return dtp01;
  }

  /**
   * @return DTP01 — the date/time qualifier (element alias).
   */
  public DateTimeQualifier getDtp01() {
    return dtp01;
  }

  /**
   * Builder for FileEffectiveDate. Seeds the effective-date qualifier (DTP01=007), the context's
   * date format (DTP02), and the context's formatted document date (DTP03).
   */
  public static class Builder {
    private DateTimeQualifier dtp01;
    private DateFormat dtp02;
    private String dtp03;

    public Builder(X834Context context) {
      this.dtp01 = DateTimeQualifier.fromString(DEFAULT_DATE_TIME_QUALIFIER);
      this.dtp02 = context.getDateFormat();
      this.dtp03 = context.getFormattedDocumentDate();
    }

    /** Sets DTP01 (date/time qualifier) from its string code. */
    public Builder setDateTimeQualifier(String value) {
      this.dtp01 = DateTimeQualifier.fromString(value);
      return this;
    }

    /** Sets DTP02 (date/time format qualifier). */
    public Builder setDateTimeFormat(DateFormat value) {
      this.dtp02 = value;
      return this;
    }

    /** Sets DTP03 (date/time period), formatting the value with the previously set DTP02 format. */
    public Builder setDateTimePeriod(LocalDateTime value) {
      this.dtp03 = this.dtp02.format(value);
      return this;
    }

    /** Element alias for {@link #setDateTimeQualifier(String)}. */
    public Builder setDtp01(String value) {
      return setDateTimeQualifier(value);
    }

    /** Element alias for {@link #setDateTimeFormat(DateFormat)}. */
    public Builder setDtp02(DateFormat value) {
      return setDateTimeFormat(value);
    }

    /** Element alias for {@link #setDateTimePeriod(LocalDateTime)}. */
    public Builder setDtp03(LocalDateTime value) {
      return setDateTimePeriod(value);
    }

    /**
     * Builds a new FileEffectiveDate instance.
     *
     * @return A new FileEffectiveDate instance
     * @throws ValidationException if the date/time period (DTP03) is missing
     */
    public FileEffectiveDate build() throws ValidationException {
      return new FileEffectiveDate(this);
    }
  }
}
