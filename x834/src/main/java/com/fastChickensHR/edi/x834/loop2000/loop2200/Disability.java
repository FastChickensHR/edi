/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2200;

import com.fastChickensHR.edi.x834.data.DisabilityTypeCode;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * A disability the member has — one occurrence of Loop 2200 in the X12 834 (005010X220A1).
 *
 * <p>{@link #type} is the mandatory statement of what kind of disability it is; {@link #startDate}
 * and {@link #endDate} are the period, which is what BCBSM actually asks for ("Report 360 Initial
 * Disability Period Start… 361 Initial Disability Period End"). The remaining elements describe the
 * member's work and the benefit around it.
 *
 * <p>Because {@link #type} is mandatory in the segment, <b>the dates cannot travel without it</b>:
 * a sponsor sending a disability period must also say what kind of disability it is.
 *
 * <p>This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class Disability {
  /** What kind of disability this is (DSB01) — required. */
  private DisabilityTypeCode type;

  /** The associated quantity (DSB02). */
  private String quantity;

  /** The member's occupation (DSB03). */
  private String occupationCode;

  /** How intensively the member works (DSB04). */
  private String workIntensityCode;

  /** The product option (DSB05). */
  private String productOptionCode;

  /** The associated amount (DSB06). */
  private String monetaryAmount;

  /** When the disability period began ({@code DTP*360}). */
  private LocalDateTime startDate;

  /** When it ended ({@code DTP*361}). */
  private LocalDateTime endDate;

  /** Creates an empty disability; callers set the required {@code type} afterwards. */
  public Disability() {}

  /**
   * Creates a disability of the given kind.
   *
   * @param type what kind of disability this is (DSB01)
   */
  public Disability(DisabilityTypeCode type) {
    this.type = type;
  }
}
