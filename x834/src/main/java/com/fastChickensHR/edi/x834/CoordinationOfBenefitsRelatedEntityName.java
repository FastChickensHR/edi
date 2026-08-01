/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * The NM1 segment naming the other plan in Loop 2330 of the X12 834 (005010X220A1) — the entity
 * whose coverage the enclosing 2320 {@code COB} coordinates with.
 *
 * <p>NM101 defaults to {@link EntityIdentifierCode#INSURER} ({@code IN}) and NM102 to {@link
 * #NON_PERSON_ENTITY} ({@code 2}), since the other plan is an organization rather than a person.
 * NM103 carries its name.
 *
 * <p>That name is a carrier-specific literal rather than a code, which is exactly why it is
 * supplied per occurrence: for the same Medicare concept BCBSM's BCN product wants {@code
 * MEDA}/{@code MEDB}, its Medicare Advantage product wants {@code MEDICARE PART A}/{@code MEDICARE
 * PART B}, and a third carrier asks for the insurance company's own name.
 */
@Getter
class CoordinationOfBenefitsRelatedEntityName extends NM1Segment {

  /**
   * NM102 entity type qualifier {@code 2} — Non-Person Entity; the other plan is an organization.
   */
  public static final String NON_PERSON_ENTITY = "2";

  private CoordinationOfBenefitsRelatedEntityName(Builder builder) throws ValidationException {
    super(builder);
  }

  /**
   * @return NM103 — the other plan's name.
   */
  public String getRelatedEntityName() {
    return getNm103();
  }

  /**
   * @return a new {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link CoordinationOfBenefitsRelatedEntityName}; NM101 defaults to {@code IN}
   * (Insurer) and NM102 to {@link #NON_PERSON_ENTITY}.
   */
  public static class Builder extends NM1Segment.AbstractBuilder<Builder> {
    public Builder() {
      setEntityIdentifierCode(EntityIdentifierCode.INSURER.getCode());
      setEntityTypeQualifier(NON_PERSON_ENTITY);
    }

    @Override
    protected Builder self() {
      return this;
    }

    /** Sets NM103 (the other plan's name). */
    public Builder setRelatedEntityName(String value) {
      return setLastName(value);
    }

    @Override
    public CoordinationOfBenefitsRelatedEntityName build() throws ValidationException {
      return new CoordinationOfBenefitsRelatedEntityName(this);
    }
  }
}
