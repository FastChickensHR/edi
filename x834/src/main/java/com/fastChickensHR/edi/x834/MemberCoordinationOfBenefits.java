/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * The COB segment that opens Loop 2320 of the X12 834 (005010X220A1) — this member's coordination
 * with another plan.
 *
 * <p>Nothing is defaulted: both answers carriers send are deliberate statements, and guessing
 * either would put words in a carrier's mouth. BCBSM's Medicare block renders as {@code
 * COB*S*<MBI>*1~} and CareFirst's every-row form as {@code COB*U**6~}.
 */
@Getter
class MemberCoordinationOfBenefits extends COBSegment {

  private MemberCoordinationOfBenefits(Builder builder) throws ValidationException {
    super(builder);
  }

  /**
   * @return a new {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link MemberCoordinationOfBenefits}. */
  public static class Builder extends COBSegment.AbstractBuilder<Builder> {
    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public MemberCoordinationOfBenefits build() throws ValidationException {
      return new MemberCoordinationOfBenefits(this);
    }
  }
}
