/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.HealthRelatedCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the HLH (Health Information) segment in the X12 834 (005010X220A1) Benefit Enrollment
 * and Maintenance transaction.
 * <p>
 * The segment carries the member's health-related status in Loop 2100A — chiefly tobacco and
 * substance use, a rating input for individual and small-group products. BCBSM notes the code "may
 * be required for specific employer groups", so requiredness is delegated out-of-band and the
 * segment is emitted only when a member carries the information.
 * <p>
 * Element/position map (data elements per the 834 TR3):
 * <ul>
 *     <li>HLH01 = health-related code (1212)</li>
 *     <li>HLH02 = height (65, R 1/8)</li>
 *     <li>HLH03 = current weight (81, R 1/10)</li>
 *     <li>HLH04 = previous weight (81, R 1/10)</li>
 *     <li>HLH05 = description — the reason for a change in weight (352, AN 1/80)</li>
 * </ul>
 * <b>HLH06</b> (current health condition code, element 1213) and <b>HLH07</b> (the reason for the
 * last doctor visit) are not modelled. Both sit at the tail, so omitting them costs no element
 * slot, and no profiled carrier asks for either; adding them later disturbs nothing.
 * <p>
 * Every element is optional in X12, but a segment carrying none of them says nothing, so an empty
 * HLH is rejected rather than emitted as a bare {@code HLH~}.
 */
@Getter
public abstract class HLHSegment extends Segment {
    public static final String SEGMENT_ID = "HLH";

    /** Element 65 is R 1/8. */
    public static final int MAX_HEIGHT_LENGTH = 8;
    /** Element 81 is R 1/10. */
    public static final int MAX_WEIGHT_LENGTH = 10;
    /** Element 352 is AN 1/80. */
    public static final int MAX_DESCRIPTION_LENGTH = 80;

    protected final HealthRelatedCode hlh01;
    protected final String hlh02;
    protected final String hlh03;
    protected final String hlh04;
    protected final String hlh05;

    protected HLHSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.hlh01 = builder.hlh01;
        this.hlh02 = builder.hlh02;
        this.hlh03 = builder.hlh03;
        this.hlh04 = builder.hlh04;
        this.hlh05 = builder.hlh05;

        validate();
    }

    private void validate() throws ValidationException {
        if (hlh01 == null && isBlank(hlh02) && isBlank(hlh03) && isBlank(hlh04) && isBlank(hlh05)) {
            throw new ValidationException("HLH carries no health information; at least one element is required");
        }
        maxLength(hlh02, MAX_HEIGHT_LENGTH, "Height (HLH02)");
        maxLength(hlh03, MAX_WEIGHT_LENGTH, "Current Weight (HLH03)");
        maxLength(hlh04, MAX_WEIGHT_LENGTH, "Previous Weight (HLH04)");
        maxLength(hlh05, MAX_DESCRIPTION_LENGTH, "Description (HLH05)");
    }

    private static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    private static void maxLength(String value, int max, String label) throws ValidationException {
        if (value != null && value.length() > max) {
            throw new ValidationException(label + " must be " + max + " characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{hlh01 == null ? null : hlh01.getCode(), hlh02, hlh03, hlh04, hlh05};
    }

    /** @return HLH01 — the member's health-related status. */
    public HealthRelatedCode getHealthRelatedCode() {
        return getHlh01();
    }

    /** @return HLH02 — the member's height. */
    public String getHeight() {
        return getHlh02();
    }

    /** @return HLH03 — the member's current weight. */
    public String getCurrentWeight() {
        return getHlh03();
    }

    /** @return HLH04 — the member's previous weight. */
    public String getPreviousWeight() {
        return getHlh04();
    }

    /** @return HLH05 — why the weight changed. */
    public String getDescription() {
        return getHlh05();
    }

    /**
     * Abstract builder for HLH segments.
     *
     * @param <T> the concrete builder type, for chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected HealthRelatedCode hlh01;
        protected String hlh02;
        protected String hlh03;
        protected String hlh04;
        protected String hlh05;

        protected abstract T self();

        /** Sets HLH01 (the member's health-related status). */
        public T setHealthRelatedCode(HealthRelatedCode value) {
            this.hlh01 = value;
            return self();
        }

        /** Sets HLH02 (the member's height). */
        public T setHeight(String value) {
            this.hlh02 = value;
            return self();
        }

        /** Sets HLH03 (the member's current weight). */
        public T setCurrentWeight(String value) {
            this.hlh03 = value;
            return self();
        }

        /** Sets HLH04 (the member's previous weight). */
        public T setPreviousWeight(String value) {
            this.hlh04 = value;
            return self();
        }

        /** Sets HLH05 (why the weight changed). */
        public T setDescription(String value) {
            this.hlh05 = value;
            return self();
        }

        /** @return the built segment. */
        public abstract HLHSegment build() throws ValidationException;
    }
}
