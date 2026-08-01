/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import lombok.Getter;
import lombok.Setter;

/**
 * A language the member uses — one Loop 2100A {@code LUI} of the X12 834 (005010X220A1). A member
 * may carry several.
 *
 * <p>A language can be given as a {@link #code} under the {@link #codeQualifier} naming its scheme,
 * as a plain {@link #description}, or both; at least one of the two is required, since an LUI
 * naming no language is not a statement. BCBSM's BCN Advantage product publishes its own code
 * protocol, which is why the scheme is supplied rather than assumed.
 *
 * <p>This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class Language {
  /** What kind of code {@link #code} is (LUI01); required when a code is given. */
  private IdentificationCodeQualifier codeQualifier;

  /** The language code (LUI02); paired with {@link #codeQualifier}. */
  private String code;

  /** The language named in words (LUI03). */
  private String description;

  public Language() {}

  /**
   * @param codeQualifier what kind of code follows (LUI01)
   * @param code the language code (LUI02)
   */
  public Language(IdentificationCodeQualifier codeQualifier, String code) {
    this.codeQualifier = codeQualifier;
    this.code = code;
  }

  /**
   * Names a language in words alone, with no code.
   *
   * @param description the language named in words (LUI03)
   */
  public Language(String description) {
    this.description = description;
  }
}
