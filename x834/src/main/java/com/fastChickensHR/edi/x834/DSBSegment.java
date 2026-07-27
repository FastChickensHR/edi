/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.DisabilityTypeCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the DSB (Disability Information) segment in the X12 834 (005010X220A1) Benefit
 * Enrollment and Maintenance transaction.
 * <p>
 * The segment opens Loop 2200 and states the member's disability status. BCBSM's interest is the
 * period rather than the detail — it asks for the initial disability period start and end, which
 * ride the {@code DTP} segments that follow this one in the same loop.
 * <p>
 * Element/position map (data elements per the 834 TR3):
 * <ul>
 *     <li>DSB01 = disability type code (1146) — <b>mandatory</b></li>
 *     <li>DSB02 = quantity (380, R 1/15)</li>
 *     <li>DSB03 = occupation code (1149, ID 4/6)</li>
 *     <li>DSB04 = work intensity code (1154, ID 1/1)</li>
 *     <li>DSB05 = product option code (1161, ID 1/2)</li>
 *     <li>DSB06 = monetary amount (782, R 1/18)</li>
 * </ul>
 * <b>DSB07</b> (product/service ID qualifier) and <b>DSB08</b> (medical code value) are not
 * modelled. They are a conditional pair at the tail of the segment, no profiled carrier asks for
 * them, and modelling half of a pair would be worse than modelling neither.
 * <p>
 * DSB01 being mandatory means the disability <em>dates</em> cannot travel alone: a sponsor sending
 * a disability period must also say what kind of disability it is. That is the 834's constraint,
 * and this class refuses the alternative rather than emitting a segment with an empty mandatory
 * element.
 */
@Getter
abstract class DSBSegment extends Segment {
    public static final String SEGMENT_ID = "DSB";

    /** Element 380 is R 1/15. */
    public static final int MAX_QUANTITY_LENGTH = 15;
    /** Element 1149 is ID 4/6. */
    public static final int MIN_OCCUPATION_CODE_LENGTH = 4;
    /** Element 1149 is ID 4/6. */
    public static final int MAX_OCCUPATION_CODE_LENGTH = 6;
    /** Element 782 is R 1/18. */
    public static final int MAX_MONETARY_AMOUNT_LENGTH = 18;

    protected final DisabilityTypeCode dsb01;
    protected final String dsb02;
    protected final String dsb03;
    protected final String dsb04;
    protected final String dsb05;
    protected final String dsb06;

    protected DSBSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.dsb01 = builder.dsb01;
        this.dsb02 = builder.dsb02;
        this.dsb03 = builder.dsb03;
        this.dsb04 = builder.dsb04;
        this.dsb05 = builder.dsb05;
        this.dsb06 = builder.dsb06;

        validate();
    }

    private void validate() throws ValidationException {
        if (dsb01 == null) {
            throw new ValidationException("Disability Type Code (DSB01) is required");
        }
        if (dsb02 != null && dsb02.length() > MAX_QUANTITY_LENGTH) {
            throw new ValidationException("Quantity (DSB02) must be "
                    + MAX_QUANTITY_LENGTH + " characters or less");
        }
        if (dsb03 != null && !dsb03.isEmpty()
                && (dsb03.length() < MIN_OCCUPATION_CODE_LENGTH || dsb03.length() > MAX_OCCUPATION_CODE_LENGTH)) {
            throw new ValidationException("Occupation Code (DSB03) must be between "
                    + MIN_OCCUPATION_CODE_LENGTH + " and " + MAX_OCCUPATION_CODE_LENGTH
                    + " characters; got '" + dsb03 + "'");
        }
        if (dsb06 != null && dsb06.length() > MAX_MONETARY_AMOUNT_LENGTH) {
            throw new ValidationException("Monetary Amount (DSB06) must be "
                    + MAX_MONETARY_AMOUNT_LENGTH + " characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{dsb01.getCode(), dsb02, dsb03, dsb04, dsb05, dsb06};
    }

    /** @return DSB01 — what kind of disability this is. */
    public DisabilityTypeCode getDisabilityTypeCode() {
        return getDsb01();
    }

    /** @return DSB02 — the associated quantity. */
    public String getQuantity() {
        return getDsb02();
    }

    /** @return DSB03 — the member's occupation. */
    public String getOccupationCode() {
        return getDsb03();
    }

    /** @return DSB04 — how intensively the member works. */
    public String getWorkIntensityCode() {
        return getDsb04();
    }

    /** @return DSB05 — the product option. */
    public String getProductOptionCode() {
        return getDsb05();
    }

    /** @return DSB06 — the associated amount. */
    public String getMonetaryAmount() {
        return getDsb06();
    }

    /**
     * Abstract builder for DSB segments.
     *
     * @param <T> the concrete builder type, for chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected DisabilityTypeCode dsb01;
        protected String dsb02;
        protected String dsb03;
        protected String dsb04;
        protected String dsb05;
        protected String dsb06;

        protected abstract T self();

        /** Sets DSB01 (what kind of disability this is). */
        public T setDisabilityTypeCode(DisabilityTypeCode value) {
            this.dsb01 = value;
            return self();
        }

        /** Sets DSB02 (the associated quantity). */
        public T setQuantity(String value) {
            this.dsb02 = value;
            return self();
        }

        /** Sets DSB03 (the member's occupation). */
        public T setOccupationCode(String value) {
            this.dsb03 = value;
            return self();
        }

        /** Sets DSB04 (how intensively the member works). */
        public T setWorkIntensityCode(String value) {
            this.dsb04 = value;
            return self();
        }

        /** Sets DSB05 (the product option). */
        public T setProductOptionCode(String value) {
            this.dsb05 = value;
            return self();
        }

        /** Sets DSB06 (the associated amount). */
        public T setMonetaryAmount(String value) {
            this.dsb06 = value;
            return self();
        }

        /** @return the built segment. */
        public abstract DSBSegment build() throws ValidationException;
    }
}
