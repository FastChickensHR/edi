/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import lombok.Getter;
import lombok.Setter;

/**
 * One way to reach a member — a single qualifier/number pair of the Loop 2100A {@code PER} in the
 * X12 834 (005010X220A1).
 *
 * <p>The {@link #qualifier} states what kind of number this is (home phone, email, cellular, …) and
 * {@link #number} is the number itself. A member may carry several; the writer emits them as the
 * PER03/04, PER05/06 and PER07/08 pairs of a single {@code PER} segment, which is why the 834
 * permits at most {@value #MAX_PER_MEMBER} per member.
 *
 * <p>Which channels a carrier wants is a per-carrier choice rather than a fixed list — BCBSM
 * MembersEdge asks for email, home and work; its Medicare Advantage product for alternate,
 * cellular, email, home and telephone — so the qualifier is supplied per occurrence.
 *
 * <p>This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class MemberCommunication {

  /**
   * The most communications the 834 permits per member — fixed by the single Loop 2100A {@code
   * PER}'s three qualifier/number element pairs (PER03/04, PER05/06, PER07/08).
   */
  public static final int MAX_PER_MEMBER = 3;

  /** What kind of number this is (PER03/05/07) — required. */
  private CommunicationNumberQualifier qualifier;

  /** The number itself (PER04/06/08) — required. */
  private String number;

  /** Creates an empty communication; callers set the qualifier and number afterwards. */
  public MemberCommunication() {}

  /**
   * Creates a communication from its qualifier/number pair.
   *
   * @param qualifier what kind of number this is (PER03/05/07)
   * @param number the number itself (PER04/06/08)
   */
  public MemberCommunication(CommunicationNumberQualifier qualifier, String number) {
    this.qualifier = qualifier;
    this.number = number;
  }
}
