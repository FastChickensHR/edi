/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the IDC (Identification Card) segment used by the X12 834
 * Benefit Enrollment and Maintenance transaction (Loop 2300 - Health Coverage
 * Policy, Identification Card).
 * <p>
 * Element layout per HIPAA 005010X220A1:
 * <ul>
 *     <li>IDC01 - Plan Coverage Description (required)</li>
 *     <li>IDC02 - Identification Card Type Code (required)</li>
 *     <li>IDC03 - Identification Card Action Code (required)</li>
 *     <li>IDC04 - Identification Card Count (optional)</li>
 * </ul>
 */
@Getter
public abstract class IDCSegment extends Segment {
    public static final String SEGMENT_ID = "IDC";

    protected final String idc01;
    protected final String idc02;
    protected final String idc03;
    protected final String idc04;

    protected IDCSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.idc01 = builder.idc01;
        this.idc02 = builder.idc02;
        this.idc03 = builder.idc03;
        this.idc04 = builder.idc04;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (idc01 == null || idc01.trim().isEmpty()) {
            throw new ValidationException("Plan Coverage Description (IDC01) is required");
        }
        if (idc01.length() > 50) {
            throw new ValidationException("IDC01 (Plan Coverage Description) must be 50 characters or less");
        }
        if (idc02 == null || idc02.trim().isEmpty()) {
            throw new ValidationException("Identification Card Type Code (IDC02) is required");
        }
        if (idc03 == null || idc03.trim().isEmpty()) {
            throw new ValidationException("Identification Card Action Code (IDC03) is required");
        }
        if (idc04 != null && !idc04.isEmpty()) {
            try {
                int count = Integer.parseInt(idc04);
                if (count < 0) {
                    throw new ValidationException("Identification Card Count (IDC04) must be non-negative");
                }
            } catch (NumberFormatException e) {
                throw new ValidationException("Identification Card Count (IDC04) must be a numeric value");
            }
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{idc01, idc02, idc03, idc04};
    }

    /**
     * @return the plan coverage description (IDC01)
     */
    public String getPlanCoverageDescription() {
        return getIdc01();
    }

    /**
     * @return the identification card type code (IDC02)
     */
    public String getIdentificationCardTypeCode() {
        return getIdc02();
    }

    /**
     * @return the identification card action code (IDC03)
     */
    public String getIdentificationCardActionCode() {
        return getIdc03();
    }

    /**
     * @return the identification card count (IDC04)
     */
    public String getIdentificationCardCount() {
        return getIdc04();
    }

    /**
     * Abstract builder for IDCSegment.
     *
     * @param <T> the concrete builder type
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String idc01;
        protected String idc02;
        protected String idc03;
        protected String idc04;

        protected abstract T self();

        public abstract IDCSegment build() throws ValidationException;

        public T setPlanCoverageDescription(String value) {
            this.idc01 = value;
            return self();
        }

        public T setIdentificationCardTypeCode(String value) {
            this.idc02 = value;
            return self();
        }

        public T setIdentificationCardActionCode(String value) {
            this.idc03 = value;
            return self();
        }

        public T setIdentificationCardCount(String value) {
            this.idc04 = value;
            return self();
        }

        public T setIdc01(String value) {
            return setPlanCoverageDescription(value);
        }

        public T setIdc02(String value) {
            return setIdentificationCardTypeCode(value);
        }

        public T setIdc03(String value) {
            return setIdentificationCardActionCode(value);
        }

        public T setIdc04(String value) {
            return setIdentificationCardCount(value);
        }
    }

    /**
     * Concrete builder implementation for IDCSegment.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public IDCSegment build() throws ValidationException {
            return new IDCSegmentImpl(this);
        }
    }

    /**
     * Concrete implementation of IDCSegment used by the default Builder.
     */
    private static class IDCSegmentImpl extends IDCSegment {
        private IDCSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}
