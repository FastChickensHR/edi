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
 * Represents the AMT (Monetary Amount Information) segment used by multiple X12
 * transaction sets, including the 834 Benefit Enrollment and Maintenance
 * transaction (Loop 2300 - Health Coverage Policy).
 * <p>
 * Element layout:
 * <ul>
 *     <li>AMT01 - Amount Qualifier Code (required)</li>
 *     <li>AMT02 - Monetary Amount (required)</li>
 *     <li>AMT03 - Credit/Debit Flag Code (optional)</li>
 * </ul>
 */
@Getter
public abstract class AMTSegment extends Segment {
    public static final String SEGMENT_ID = "AMT";

    protected final String amt01;
    protected final String amt02;
    protected final String amt03;

    protected AMTSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.amt01 = builder.amt01;
        this.amt02 = builder.amt02;
        this.amt03 = builder.amt03;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (amt01 == null || amt01.trim().isEmpty()) {
            throw new ValidationException("Amount Qualifier Code (AMT01) is required");
        }
        if (amt02 == null || amt02.trim().isEmpty()) {
            throw new ValidationException("Monetary Amount (AMT02) is required");
        }
        if (amt03 != null && !amt03.isEmpty() && !"C".equals(amt03) && !"D".equals(amt03)) {
            throw new ValidationException("Credit/Debit Flag Code (AMT03) must be 'C' or 'D' when present");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{amt01, amt02, amt03};
    }

    /**
     * @return the amount qualifier code (AMT01)
     */
    public String getAmountQualifierCode() {
        return getAmt01();
    }

    /**
     * @return the monetary amount (AMT02)
     */
    public String getMonetaryAmount() {
        return getAmt02();
    }

    /**
     * @return the credit/debit flag code (AMT03)
     */
    public String getCreditDebitFlagCode() {
        return getAmt03();
    }

    /**
     * Abstract builder for AMTSegment.
     *
     * @param <T> the concrete builder type
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String amt01;
        protected String amt02;
        protected String amt03;

        protected abstract T self();

        public abstract AMTSegment build() throws ValidationException;

        public T setAmountQualifierCode(String value) {
            this.amt01 = value;
            return self();
        }

        public T setMonetaryAmount(String value) {
            this.amt02 = value;
            return self();
        }

        public T setCreditDebitFlagCode(String value) {
            this.amt03 = value;
            return self();
        }

        public T setAmt01(String value) {
            return setAmountQualifierCode(value);
        }

        public T setAmt02(String value) {
            return setMonetaryAmount(value);
        }

        public T setAmt03(String value) {
            return setCreditDebitFlagCode(value);
        }
    }

    /**
     * Concrete builder implementation for AMTSegment.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public AMTSegment build() throws ValidationException {
            return new AMTSegmentImpl(this);
        }
    }

    /**
     * Concrete implementation of AMTSegment used by the default Builder.
     */
    private static class AMTSegmentImpl extends AMTSegment {
        private AMTSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }
    }
}
