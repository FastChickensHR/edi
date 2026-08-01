/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.ReferenceIdentificationQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the REF (Reference Information) segment in the X12 834 (005010X220A1) Benefit
 * Enrollment and Maintenance transaction.
 *
 * <p>This segment attaches a secondary reference identifier to the enclosing loop: a qualifier
 * states what kind of reference it is, followed by the reference value and/or a free-form
 * description.
 *
 * <p>Element/position map:
 *
 * <ul>
 *   <li>REF01 = reference identification qualifier (what kind of reference)
 *   <li>REF02 = reference identification value (X12 element 127, max 50 characters)
 *   <li>REF03 = reference description (X12 element 352, max 80 characters)
 * </ul>
 *
 * At least one of REF02 or REF03 must be supplied.
 */
@Getter
public abstract class RefSegment extends Segment {

  /** The X12 segment identifier for this segment: {@code REF}. */
  public static final String SEGMENT_ID = "REF";

  /** Maximum length of REF02 (Reference Identification, X12 element 127: AN 1/50 in 005010). */
  public static final int REF02_MAX_LENGTH = 50;

  /** Maximum length of REF03 (Reference Description, X12 element 352: AN 1/80). */
  public static final int REF03_MAX_LENGTH = 80;

  /** REF01 — reference identification qualifier (what kind of reference this is). */
  protected final ReferenceIdentificationQualifier ref01;

  /** REF02 — reference identification value (element 127, max 50 characters). */
  protected final String ref02;

  /** REF03 — reference description (element 352, max 80 characters). */
  protected final String ref03;

  /**
   * Creates a REF segment from a builder's captured element values.
   *
   * @param builder the builder carrying REF01–REF03
   * @throws ValidationException if both REF02 and REF03 are absent, or either exceeds its maximum
   *     length
   */
  protected RefSegment(RefSegment.AbstractBuilder<?> builder) throws ValidationException {
    this.ref01 = builder.ref01;
    this.ref02 = builder.ref02;
    this.ref03 = builder.ref03;

    validateRequiredFields();
  }

  private void validateRequiredFields() throws ValidationException {
    if ((ref02 == null || ref02.trim().isEmpty()) && (ref03 == null || ref03.trim().isEmpty())) {
      throw new ValidationException(
          "Either Reference Identification (REF02) or Reference Description (REF03) is required");
    }
    if (ref02 != null && ref02.length() > REF02_MAX_LENGTH) {
      throw new ValidationException(
          "REF02 (Reference Identification) must be " + REF02_MAX_LENGTH + " characters or less");
    }
    if (ref03 != null && ref03.length() > REF03_MAX_LENGTH) {
      throw new ValidationException(
          "REF03 (Reference Description) must be " + REF03_MAX_LENGTH + " characters or less");
    }
  }

  @Override
  public String getSegmentIdentifier() {
    return SEGMENT_ID;
  }

  @Override
  public String[] getElementValues() {
    return new String[] {ref01.getCode(), ref02, ref03};
  }

  /**
   * Returns the kind of reference this segment carries.
   *
   * @return REF01 — reference identification qualifier.
   */
  public ReferenceIdentificationQualifier getReferenceIdentificationQualifier() {
    return getRef01();
  }

  /**
   * Returns the reference value itself.
   *
   * @return REF02 — reference identification value.
   */
  public String getReferenceIdentification() {
    return getRef02();
  }

  /**
   * Returns the free-form description accompanying the reference.
   *
   * @return REF03 — reference description.
   */
  public String getReferenceDescription() {
    return getRef03();
  }

  /**
   * Abstract builder for REF segments.
   *
   * @param <T> the concrete builder type for method chaining
   */
  public abstract static class AbstractBuilder<T extends RefSegment.AbstractBuilder<T>> {

    /** Creates a builder with no element values set; used by concrete builder subclasses. */
    protected AbstractBuilder() {}

    /** REF01 — reference identification qualifier to build with. */
    protected ReferenceIdentificationQualifier ref01;

    /** REF02 — reference identification value to build with. */
    protected String ref02;

    /** REF03 — reference description to build with. */
    protected String ref03;

    /**
     * Self-typing hook for method chaining.
     *
     * @return this builder cast to the concrete type.
     */
    protected abstract T self();

    /**
     * Builds and validates the REF segment.
     *
     * @return a new {@link RefSegment} instance
     * @throws ValidationException if the segment is mis-constructed — the construction-phase side
     *     of the failure contract; see {@link ValidationException}
     */
    public abstract RefSegment build() throws ValidationException;

    /**
     * Sets REF01 (reference identification qualifier) from its string code.
     *
     * @param value the qualifier code, resolved via {@link
     *     ReferenceIdentificationQualifier#fromString(String)}
     * @return this builder
     */
    public T setReferenceIdentificationQualifier(String value) {
      this.ref01 = ReferenceIdentificationQualifier.fromString(value);
      return self();
    }

    /**
     * Sets REF02 (reference identification value).
     *
     * @param value the reference identification (element 127, max 50 characters)
     * @return this builder
     */
    public T setReferenceIdentification(String value) {
      this.ref02 = value;
      return self();
    }

    /**
     * Sets REF03 (reference description).
     *
     * @param value the reference description (element 352, max 80 characters)
     * @return this builder
     */
    public T setReferenceDescription(String value) {
      this.ref03 = value;
      return self();
    }

    /**
     * Element alias for {@link #setReferenceIdentificationQualifier(String)}.
     *
     * @param value the qualifier code for REF01
     * @return this builder
     */
    public T setRef01(String value) {
      return setReferenceIdentificationQualifier(value);
    }

    /**
     * Element alias for {@link #setReferenceIdentification(String)}.
     *
     * @param value the reference identification for REF02
     * @return this builder
     */
    public T setRef02(String value) {
      return setReferenceIdentification(value);
    }

    /**
     * Element alias for {@link #setReferenceDescription(String)}.
     *
     * @param value the reference description for REF03
     * @return this builder
     */
    public T setRef03(String value) {
      return setReferenceDescription(value);
    }
  }

  /** Concrete builder implementation for {@link RefSegment}. */
  public static class Builder extends AbstractBuilder<Builder> {

    /** Creates a builder with no element values set. */
    public Builder() {}

    @Override
    protected RefSegment.Builder self() {
      return this;
    }

    @Override
    public RefSegment build() throws ValidationException {
      return new RefSegmentImpl(this);
    }
  }

  /** Concrete implementation of {@link RefSegment} used by the default Builder. */
  private static class RefSegmentImpl extends RefSegment {
    private RefSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
      super(builder);
    }
  }
}
