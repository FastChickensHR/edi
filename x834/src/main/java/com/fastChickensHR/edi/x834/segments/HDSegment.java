/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the HD (Health Coverage) segment in the X12 834
 * (005010X220A1) Benefit Enrollment and Maintenance transaction (Loop 2300).
 * <p>
 * This segment describes a member's health coverage — the maintenance action being
 * applied, the insurance line, and the plan/coverage-level attributes.
 * <p>
 * In the 220A1 TR3 the HD segment carries only four data elements — HD01, HD03,
 * HD04, HD05. <strong>HD02 is Not Used</strong> (maintenance reason rides on INS04,
 * not here), and <strong>HD06 and everything beyond is Not Used</strong> — the
 * medicare/COBRA/employment/student/handicap attributes that a base X12 HD once
 * carried live on the INS segment (Loop 2000) in this transaction, where the library
 * already models them ({@code INSSegment.ins06}–{@code ins10}). HD02 is still a real
 * element position, so it is emitted as an empty slot ({@code HD*001**HLT*…}) to keep
 * HD03+ in their correct positions; it is simply never populated.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>HD01 = maintenance type code (required)</li>
 *     <li>HD02 = <em>Not Used</em> (structurally empty)</li>
 *     <li>HD03 = insurance line code (required)</li>
 *     <li>HD04 = plan coverage description (situational)</li>
 *     <li>HD05 = coverage level code (situational)</li>
 *     <li>HD06+ = <em>Not Used</em> — never emitted</li>
 * </ul>
 */
@Getter
public abstract class HDSegment extends Segment {
    public static final String SEGMENT_ID = "HD";

    protected final String hd01;
    protected final String hd03;
    protected final String hd04;
    protected final String hd05;

    protected HDSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.hd01 = builder.hd01;
        this.hd03 = builder.hd03;
        this.hd04 = builder.hd04;
        this.hd05 = builder.hd05;

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
        // HD02 is Not Used in 220A1 but is a real element position, so it renders as an
        // empty slot to keep HD03/HD04/HD05 in their correct positions.
        return new String[]{hd01, null, hd03, hd04, hd05};
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
     * Abstract builder for HDSegment.
     *
     * @param <T> the builder type
     */
    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String hd01;
        protected String hd03;
        protected String hd04;
        protected String hd05;

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
    }
}