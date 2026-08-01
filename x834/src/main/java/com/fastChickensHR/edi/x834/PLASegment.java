/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import lombok.Getter;

/**
 * Represents the PLA (Place or Location) segment in the X12 834 (005010X220A1) Benefit Enrollment
 * and Maintenance transaction.
 *
 * <p>The segment closes Loop 2310 and states what is happening to the member's provider assignment:
 * the action (PLA01), the kind of party it applies to (PLA02), when it takes effect (PLA03), and
 * optionally why (PLA05). Anthem's PCP-change scenario renders as {@code PLA*2*1P*20260101**07~}.
 *
 * <p>Element/position map (data elements per the 834 TR3):
 *
 * <ul>
 *   <li>PLA01 = action code (306) — <b>mandatory</b>
 *   <li>PLA02 = entity identifier code (98) — <b>mandatory</b>
 *   <li>PLA03 = date (373, DT 8/8) — <b>mandatory</b>
 *   <li>PLA04 = time (337) — optional
 *   <li>PLA05 = maintenance reason code (1203) — optional
 * </ul>
 *
 * <b>PLA04</b> carries no setter. No profiled carrier asks for a time on a provider change, and a
 * date-grained assignment has no meaningful one. Its slot is still rendered, because PLA05 sits
 * after it and an element cannot be skipped — which is why Anthem's example shows the empty {@code
 * **} before the reason.
 */
@Getter
abstract class PLASegment extends Segment {
  public static final String SEGMENT_ID = "PLA";

  /** Element 373 is DT 8/8 — a date renders as {@code CCYYMMDD} and nothing else. */
  public static final int DATE_LENGTH = 8;

  protected final ActionCode pla01;
  protected final EntityIdentifierCode pla02;
  protected final String pla03;
  protected final MaintenanceReasonCode pla05;

  protected PLASegment(AbstractBuilder<?> builder) throws ValidationException {
    this.pla01 = builder.pla01;
    this.pla02 = builder.pla02;
    this.pla03 = builder.pla03;
    this.pla05 = builder.pla05;

    validate();
  }

  private void validate() throws ValidationException {
    if (pla01 == null) {
      throw new ValidationException("Action Code (PLA01) is required");
    }
    if (pla02 == null) {
      throw new ValidationException("Entity Identifier Code (PLA02) is required");
    }
    if (pla03 == null || pla03.isEmpty()) {
      throw new ValidationException("Date (PLA03) is required");
    }
    if (pla03.length() != DATE_LENGTH) {
      throw new ValidationException(
          "Date (PLA03) must be exactly "
              + DATE_LENGTH
              + " characters (CCYYMMDD); got '"
              + pla03
              + "'");
    }
  }

  @Override
  public String getSegmentIdentifier() {
    return SEGMENT_ID;
  }

  @Override
  public String[] getElementValues() {
    // PLA04 (time) is never populated but must hold its slot so PLA05 renders in position.
    return new String[] {
      pla01.getCode(), pla02.getCode(), pla03, null, pla05 == null ? null : pla05.getCode()
    };
  }

  /**
   * @return PLA01 — what is happening to the assignment.
   */
  public ActionCode getActionCode() {
    return getPla01();
  }

  /**
   * @return PLA02 — the kind of party the action applies to.
   */
  public EntityIdentifierCode getEntityIdentifierCode() {
    return getPla02();
  }

  /**
   * @return PLA03 — when it takes effect, as {@code CCYYMMDD}.
   */
  public String getDate() {
    return getPla03();
  }

  /**
   * @return PLA05 — why, if stated.
   */
  public MaintenanceReasonCode getMaintenanceReasonCode() {
    return getPla05();
  }

  /**
   * Abstract builder for PLA segments.
   *
   * @param <T> the concrete builder type, for chaining
   */
  public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
    protected ActionCode pla01;
    protected EntityIdentifierCode pla02;
    protected String pla03;
    protected MaintenanceReasonCode pla05;

    protected abstract T self();

    /** Sets PLA01 (action code). */
    public T setActionCode(ActionCode value) {
      this.pla01 = value;
      return self();
    }

    /** Sets PLA02 (entity identifier code). */
    public T setEntityIdentifierCode(EntityIdentifierCode value) {
      this.pla02 = value;
      return self();
    }

    /** Sets PLA03 (the date the action takes effect), formatted {@code CCYYMMDD}. */
    public T setDate(String value) {
      this.pla03 = value;
      return self();
    }

    /** Sets PLA05 (why the action is happening). */
    public T setMaintenanceReasonCode(MaintenanceReasonCode value) {
      this.pla05 = value;
      return self();
    }

    /**
     * @return the built segment.
     */
    public abstract PLASegment build() throws ValidationException;
  }
}
