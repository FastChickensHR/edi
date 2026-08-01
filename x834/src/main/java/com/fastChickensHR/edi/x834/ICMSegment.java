/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.FrequencyCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the ICM (Individual Income) segment in the X12 834 (005010X220A1) Benefit Enrollment
 * and Maintenance transaction.
 *
 * <p>The segment carries what the member earns, in Loop 2100A — the input to eligibility,
 * deductibles and contribution calculations that depend on pay. The 834 transmits it only "when
 * such transmission is required under the insurance contract between the sponsor and payer".
 *
 * <p>Element/position map (data elements per the 834 TR3):
 *
 * <ul>
 *   <li>ICM01 = frequency code (594) — <b>mandatory</b>; the period ICM02 covers
 *   <li>ICM02 = monetary amount (782, R 1/18) — <b>mandatory</b>; the wage itself
 *   <li>ICM03 = quantity (380, R 1/15) — hours worked in that period
 *   <li>ICM04 = location identifier (310, AN 1/30) — where the member works
 *   <li>ICM05 = salary grade (1214, AN 1/5)
 *   <li>ICM06 = currency code (100, ID 3/3)
 * </ul>
 *
 * <p><b>ICM01 and ICM02 are mandatory, which has a consequence worth stating plainly:</b> the later
 * elements cannot be sent on their own. A carrier that wants only the location identifier — BCBS
 * Kansas places its department number at ICM04 — still has to supply a frequency and an amount,
 * because an ICM without them is not a conformant segment. That is a property of the 834, not a
 * choice made here; this class refuses to emit the alternative rather than shipping a segment with
 * empty mandatory elements.
 */
@Getter
abstract class ICMSegment extends Segment {
  public static final String SEGMENT_ID = "ICM";

  /** Element 782 is R 1/18. */
  public static final int MAX_MONETARY_AMOUNT_LENGTH = 18;

  /** Element 380 is R 1/15. */
  public static final int MAX_QUANTITY_LENGTH = 15;

  /** Element 310 is AN 1/30. */
  public static final int MAX_LOCATION_IDENTIFIER_LENGTH = 30;

  /** Element 1214 is AN 1/5. */
  public static final int MAX_SALARY_GRADE_LENGTH = 5;

  /** Element 100 is ID 3/3. */
  public static final int CURRENCY_CODE_LENGTH = 3;

  protected final FrequencyCode icm01;
  protected final String icm02;
  protected final String icm03;
  protected final String icm04;
  protected final String icm05;
  protected final String icm06;

  protected ICMSegment(AbstractBuilder<?> builder) throws ValidationException {
    this.icm01 = builder.icm01;
    this.icm02 = builder.icm02;
    this.icm03 = builder.icm03;
    this.icm04 = builder.icm04;
    this.icm05 = builder.icm05;
    this.icm06 = builder.icm06;

    validate();
  }

  private void validate() throws ValidationException {
    if (icm01 == null) {
      throw new ValidationException("Frequency Code (ICM01) is required");
    }
    if (icm02 == null || icm02.isEmpty()) {
      throw new ValidationException("Monetary Amount (ICM02) is required");
    }
    maxLength(icm02, MAX_MONETARY_AMOUNT_LENGTH, "Monetary Amount (ICM02)");
    maxLength(icm03, MAX_QUANTITY_LENGTH, "Quantity (ICM03)");
    maxLength(icm04, MAX_LOCATION_IDENTIFIER_LENGTH, "Location Identifier (ICM04)");
    maxLength(icm05, MAX_SALARY_GRADE_LENGTH, "Salary Grade (ICM05)");
    if (icm06 != null && !icm06.isEmpty() && icm06.length() != CURRENCY_CODE_LENGTH) {
      throw new ValidationException(
          "Currency Code (ICM06) must be exactly "
              + CURRENCY_CODE_LENGTH
              + " characters; got '"
              + icm06
              + "'");
    }
  }

  private static void maxLength(String value, int max, String label) throws ValidationException {
    if (value != null && value.length() > max) {
      throw new ValidationException(label + " must be " + max + " characters or less");
    }
  }

  @Override
  public String getSegmentIdentifier() {
    return SEGMENT_ID;
  }

  @Override
  public String[] getElementValues() {
    return new String[] {icm01.getCode(), icm02, icm03, icm04, icm05, icm06};
  }

  /**
   * @return ICM01 — the period {@link #getMonetaryAmount()} covers.
   */
  public FrequencyCode getFrequencyCode() {
    return getIcm01();
  }

  /**
   * @return ICM02 — what the member earns in that period.
   */
  public String getMonetaryAmount() {
    return getIcm02();
  }

  /**
   * @return ICM03 — hours worked in that period.
   */
  public String getQuantity() {
    return getIcm03();
  }

  /**
   * @return ICM04 — where the member works.
   */
  public String getLocationIdentifier() {
    return getIcm04();
  }

  /**
   * @return ICM05 — the member's salary grade.
   */
  public String getSalaryGrade() {
    return getIcm05();
  }

  /**
   * @return ICM06 — the currency the amount is in.
   */
  public String getCurrencyCode() {
    return getIcm06();
  }

  /**
   * Abstract builder for ICM segments.
   *
   * @param <T> the concrete builder type, for chaining
   */
  public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
    protected FrequencyCode icm01;
    protected String icm02;
    protected String icm03;
    protected String icm04;
    protected String icm05;
    protected String icm06;

    protected abstract T self();

    /** Sets ICM01 (the period the amount covers). */
    public T setFrequencyCode(FrequencyCode value) {
      this.icm01 = value;
      return self();
    }

    /** Sets ICM02 (what the member earns in that period). */
    public T setMonetaryAmount(String value) {
      this.icm02 = value;
      return self();
    }

    /** Sets ICM03 (hours worked in that period). */
    public T setQuantity(String value) {
      this.icm03 = value;
      return self();
    }

    /** Sets ICM04 (where the member works). */
    public T setLocationIdentifier(String value) {
      this.icm04 = value;
      return self();
    }

    /** Sets ICM05 (the member's salary grade). */
    public T setSalaryGrade(String value) {
      this.icm05 = value;
      return self();
    }

    /** Sets ICM06 (the currency the amount is in). */
    public T setCurrencyCode(String value) {
      this.icm06 = value;
      return self();
    }

    /**
     * @return the built segment.
     */
    public abstract ICMSegment build() throws ValidationException;
  }
}
