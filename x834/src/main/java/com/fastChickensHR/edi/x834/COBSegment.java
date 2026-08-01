/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.CoordinationOfBenefitsCode;
import com.fastChickensHR.edi.x834.data.PayerResponsibilitySequenceCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the COB (Coordination of Benefits) segment in the X12 834 (005010X220A1) Benefit
 * Enrollment and Maintenance transaction.
 *
 * <p>The segment opens Loop 2320 and says how this member's coverage relates to another plan they
 * also hold: where the other payer sits in the payment order (COB01), that plan's identifier
 * (COB02), and whether benefits are coordinated at all (COB03).
 *
 * <p>Element/position map (data elements per the 834 TR3):
 *
 * <ul>
 *   <li>COB01 = payer responsibility sequence number code (1138)
 *   <li>COB02 = reference identification — the other plan's policy number (127, AN 1/50)
 *   <li>COB03 = coordination of benefits code (1143)
 * </ul>
 *
 * <b>COB04</b> (service type code, element 1365) is <em>not</em> modelled. It is a repeating
 * element, a shape this library's flat element rendering has no mechanism for, and no profiled
 * carrier asks for it. Leaving the position unemittable is preferable to emitting it wrongly.
 *
 * <p>X12 marks every COB element optional, but a COB carrying no payer responsibility says nothing
 * a receiver can act on, so COB01 is required here.
 */
@Getter
abstract class COBSegment extends Segment {
  public static final String SEGMENT_ID = "COB";

  /** Element 127 is AN 1/50 — a longer policy identifier cannot be represented. */
  public static final int MAX_REFERENCE_IDENTIFICATION_LENGTH = 50;

  protected final PayerResponsibilitySequenceCode cob01;
  protected final String cob02;
  protected final CoordinationOfBenefitsCode cob03;

  protected COBSegment(AbstractBuilder<?> builder) throws ValidationException {
    this.cob01 = builder.cob01;
    this.cob02 = builder.cob02;
    this.cob03 = builder.cob03;

    validate();
  }

  private void validate() throws ValidationException {
    if (cob01 == null) {
      throw new ValidationException(
          "Payer Responsibility Sequence Number Code (COB01) is required");
    }
    if (cob02 != null && cob02.length() > MAX_REFERENCE_IDENTIFICATION_LENGTH) {
      throw new ValidationException(
          "Reference Identification (COB02) must be "
              + MAX_REFERENCE_IDENTIFICATION_LENGTH
              + " characters or less");
    }
  }

  @Override
  public String getSegmentIdentifier() {
    return SEGMENT_ID;
  }

  @Override
  public String[] getElementValues() {
    return new String[] {cob01.getCode(), cob02, cob03 == null ? null : cob03.getCode()};
  }

  /**
   * @return COB01 — where the other payer sits in the payment order.
   */
  public PayerResponsibilitySequenceCode getPayerResponsibility() {
    return getCob01();
  }

  /**
   * @return COB02 — the other plan's policy identifier.
   */
  public String getReferenceIdentification() {
    return getCob02();
  }

  /**
   * @return COB03 — whether benefits are coordinated, and for whom.
   */
  public CoordinationOfBenefitsCode getCoordinationOfBenefitsCode() {
    return getCob03();
  }

  /**
   * Abstract builder for COB segments.
   *
   * @param <T> the concrete builder type, for chaining
   */
  public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
    protected PayerResponsibilitySequenceCode cob01;
    protected String cob02;
    protected CoordinationOfBenefitsCode cob03;

    protected abstract T self();

    /** Sets COB01 (payer responsibility sequence number code). */
    public T setPayerResponsibility(PayerResponsibilitySequenceCode value) {
      this.cob01 = value;
      return self();
    }

    /** Sets COB02 (the other plan's policy identifier). */
    public T setReferenceIdentification(String value) {
      this.cob02 = value;
      return self();
    }

    /** Sets COB03 (coordination of benefits code). */
    public T setCoordinationOfBenefitsCode(CoordinationOfBenefitsCode value) {
      this.cob03 = value;
      return self();
    }

    /**
     * @return the built segment.
     */
    public abstract COBSegment build() throws ValidationException;
  }
}
