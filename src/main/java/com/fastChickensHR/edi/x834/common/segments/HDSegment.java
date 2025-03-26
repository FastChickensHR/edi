/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.segments;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the HD segment in X12 834 format.
 * This segment is used for health coverage information.
 */
@Getter
public abstract class HDSegment extends Segment {
    public static final String SEGMENT_ID = "HD";

    protected final String hd01;
    protected final String hd02;
    protected final String hd03;
    protected final String hd04;
    protected final String hd05;
    protected final String hd06;
    protected final String hd07;
    protected final String hd08;
    protected final String hd09;
    protected final String hd10;
    protected final String hd11;
    protected final String hd12;
    protected final String hd13;

    protected HDSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.hd01 = builder.hd01;
        this.hd02 = builder.hd02;
        this.hd03 = builder.hd03;
        this.hd04 = builder.hd04;
        this.hd05 = builder.hd05;
        this.hd06 = builder.hd06;
        this.hd07 = builder.hd07;
        this.hd08 = builder.hd08;
        this.hd09 = builder.hd09;
        this.hd10 = builder.hd10;
        this.hd11 = builder.hd11;
        this.hd12 = builder.hd12;
        this.hd13 = builder.hd13;

        if (hd01 == null || hd01.trim().isEmpty()) {
            throw new ValidationException("Maintenance Type Code (HD01) is required");
        }

        if (hd03 == null || hd03.trim().isEmpty()) {
            throw new ValidationException("Insurance Line Code (HD03) is required");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                hd01, hd02, hd03, hd04, hd05, hd06, hd07, hd08, hd09, hd10, hd11, hd12, hd13
        };
    }

    /**
     * Gets the maintenance type code.
     *
     * @return maintenance type code
     */
    public String getMaintenanceTypeCode() {
        return getHd01();
    }

    /**
     * Gets the maintenance reason code.
     *
     * @return maintenance reason code
     */
    public String getMaintenanceReasonCode() {
        return getHd02();
    }

    /**
     * Gets the insurance line code.
     *
     * @return insurance line code
     */
    public String getInsuranceLineCode() {
        return getHd03();
    }

    /**
     * Gets the plan coverage description.
     *
     * @return plan coverage description
     */
    public String getPlanCoverageDescription() {
        return getHd04();
    }

    /**
     * Gets the coverage level code.
     *
     * @return coverage level code
     */
    public String getCoverageLevelCode() {
        return getHd05();
    }

    /**
     * Gets the medicare plan code.
     *
     * @return medicare plan code
     */
    public String getMedicarePlanCode() {
        return getHd06();
    }

    /**
     * Gets the medicare eligibility reason code.
     *
     * @return medicare eligibility reason code
     */
    public String getMedicareEligibilityReasonCode() {
        return getHd07();
    }

    /**
     * Gets the COBRA qualifying event code.
     *
     * @return COBRA qualifying event code
     */
    public String getCobraQualifyingEventCode() {
        return getHd08();
    }

    /**
     * Gets the employment status code.
     *
     * @return employment status code
     */
    public String getEmploymentStatusCode() {
        return getHd09();
    }

    /**
     * Gets the student status code.
     *
     * @return student status code
     */
    public String getStudentStatusCode() {
        return getHd10();
    }

    /**
     * Gets the handicap indicator.
     *
     * @return handicap indicator (Y/N)
     */
    public String getHandicapIndicator() {
        return getHd11();
    }

    /**
     * Gets the date qualifier.
     *
     * @return date qualifier
     */
    public String getDateQualifier() {
        return getHd12();
    }

    /**
     * Gets the birth date.
     *
     * @return birth date
     */
    public String getBirthDate() {
        return getHd13();
    }

    /**
     * Abstract builder for HDSegment.
     *
     * @param <T> the builder type
     */
    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String hd01;
        protected String hd02;
        protected String hd03;
        protected String hd04;
        protected String hd05;
        protected String hd06;
        protected String hd07;
        protected String hd08;
        protected String hd09;
        protected String hd10;
        protected String hd11;
        protected String hd12;
        protected String hd13;

        /**
         * Returns this builder.
         *
         * @return this builder
         */
        protected abstract T self();

        /**
         * Builds a new HDSegment instance.
         *
         * @return a new HDSegment
         * @throws ValidationException if validation fails
         */
        public abstract HDSegment build() throws ValidationException;

        /**
         * Sets the maintenance type code (HD01).
         *
         * @param value maintenance type code
         * @return this builder
         */
        public T setMaintenanceTypeCode(String value) {
            this.hd01 = value;
            return self();
        }

        /**
         * Sets the maintenance reason code (HD02).
         *
         * @param value maintenance reason code
         * @return this builder
         */
        public T setMaintenanceReasonCode(String value) {
            this.hd02 = value;
            return self();
        }

        /**
         * Sets the insurance line code (HD03).
         *
         * @param value insurance line code
         * @return this builder
         */
        public T setInsuranceLineCode(String value) {
            this.hd03 = value;
            return self();
        }

        /**
         * Sets the plan coverage description (HD04).
         *
         * @param value plan coverage description
         * @return this builder
         */
        public T setPlanCoverageDescription(String value) {
            this.hd04 = value;
            return self();
        }

        /**
         * Sets the coverage level code (HD05).
         *
         * @param value coverage level code
         * @return this builder
         */
        public T setCoverageLevelCode(String value) {
            this.hd05 = value;
            return self();
        }

        /**
         * Sets the medicare plan code (HD06).
         *
         * @param value medicare plan code
         * @return this builder
         */
        public T setMedicarePlanCode(String value) {
            this.hd06 = value;
            return self();
        }

        /**
         * Sets the medicare eligibility reason code (HD07).
         *
         * @param value medicare eligibility reason code
         * @return this builder
         */
        public T setMedicareEligibilityReasonCode(String value) {
            this.hd07 = value;
            return self();
        }

        /**
         * Sets the COBRA qualifying event code (HD08).
         *
         * @param value COBRA qualifying event code
         * @return this builder
         */
        public T setCobraQualifyingEventCode(String value) {
            this.hd08 = value;
            return self();
        }

        /**
         * Sets the employment status code (HD09).
         *
         * @param value employment status code
         * @return this builder
         */
        public T setEmploymentStatusCode(String value) {
            this.hd09 = value;
            return self();
        }

        /**
         * Sets the student status code (HD10).
         *
         * @param value student status code
         * @return this builder
         */
        public T setStudentStatusCode(String value) {
            this.hd10 = value;
            return self();
        }

        /**
         * Sets the handicap indicator (HD11).
         *
         * @param value handicap indicator (Y/N)
         * @return this builder
         */
        public T setHandicapIndicator(String value) {
            this.hd11 = value;
            return self();
        }

        /**
         * Sets the date qualifier (HD12).
         *
         * @param value date qualifier
         * @return this builder
         */
        public T setDateQualifier(String value) {
            this.hd12 = value;
            return self();
        }

        /**
         * Sets the birth date (HD13).
         *
         * @param value birth date
         * @return this builder
         */
        public T setBirthDate(String value) {
            this.hd13 = value;
            return self();
        }
    }
}