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
 * The LUI (Language Use) segment in Loop 2100A of the X12 834 (005010X220A1) — a language the
 * member uses.
 *
 * <p>A member may carry several, the 834 permitting more than one LUI. Which coding scheme names
 * the language is the carrier's to state rather than this library's to assume, so the qualifier is
 * supplied per language.
 */
@Getter
class MemberLanguage extends LUISegment {

  private MemberLanguage(Builder builder) throws ValidationException {
    super(builder);
  }

  /**
   * @return a new {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link MemberLanguage}. */
  public static class Builder extends LUISegment.AbstractBuilder<Builder> {
    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public MemberLanguage build() throws ValidationException {
      return new MemberLanguage(this);
    }
  }
}
