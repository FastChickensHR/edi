/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.data.FrequencyCode;
import lombok.Getter;
import lombok.Setter;

/**
 * What a member earns — the Loop 2100A {@code ICM} of the X12 834 (005010X220A1). A member has at
 * most one, since the 834 permits a single ICM per member.
 *
 * <p>{@link #frequency} and {@link #amount} are the pair that carries the meaning: an amount
 * without the period it covers says nothing, which is why the 834 makes both mandatory. The rest
 * describes the job around it — {@link #hours} worked in that period, the {@link
 * #locationIdentifier} the member works at, their {@link #salaryGrade}, and the {@link
 * #currencyCode} the amount is in.
 *
 * <p><b>The mandatory pair binds the optional elements.</b> BCBS Kansas asks for a department
 * number at {@code ICM04} and a wage at {@code ICM02}; a sponsor that holds the department but not
 * the pay cannot emit the department alone, because an ICM without a frequency and an amount is not
 * conformant. That constraint belongs to the 834, and this library surfaces it as a rejection
 * rather than emitting a malformed segment.
 *
 * <p>Amounts and quantities are carried as strings so the caller's own formatting reaches the wire
 * unchanged — a decimal rounded on the way through would be a silent restatement of someone's pay.
 *
 * <p>This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class Income {
  /** The period {@link #amount} covers (ICM01) — required. */
  private FrequencyCode frequency;

  /** What the member earns in that period (ICM02) — required. */
  private String amount;

  /** Hours worked in that period (ICM03). */
  private String hours;

  /** Where the member works (ICM04); BCBS Kansas carries its department number here. */
  private String locationIdentifier;

  /** The member's salary grade (ICM05). */
  private String salaryGrade;

  /** The currency {@link #amount} is in (ICM06), e.g. {@code USD}. */
  private String currencyCode;

  public Income() {}

  /**
   * @param frequency the period the amount covers (ICM01)
   * @param amount what the member earns in that period (ICM02)
   */
  public Income(FrequencyCode frequency, String amount) {
    this.frequency = frequency;
    this.amount = amount;
  }
}
