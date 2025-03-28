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
 * Represents the N4 segment in X12 834 format.
 * This segment is used for geographic location information.
 */
@Getter
public abstract class N4Segment extends Segment {
    public static final String SEGMENT_ID = "N4";

    protected final String n401;
    protected final String n402;
    protected final String n403;
    protected final String n404;
    protected final String n405;
    protected final String n406;
    protected final String n407;

    protected N4Segment(AbstractBuilder<?> builder) throws ValidationException {
        this.n401 = builder.n401;
        this.n402 = builder.n402;
        this.n403 = builder.n403;
        this.n404 = builder.n404;
        this.n405 = builder.n405;
        this.n406 = builder.n406;
        this.n407 = builder.n407;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{n401, n402, n403, n404, n405, n406, n407};
    }

    /**
     * Gets the city name.
     * @return city name
     */
    public String getCityName() {
        return getN401();
    }

    /**
     * Gets the state or province code.
     * @return state or province code
     */
    public String getStateOrProvinceCode() {
        return getN402();
    }

    /**
     * Gets the postal code.
     * @return postal code
     */
    public String getPostalCode() {
        return getN403();
    }

    /**
     * Gets the country code.
     * @return country code
     */
    public String getCountryCode() {
        return getN404();
    }

    /**
     * Gets the location qualifier.
     * @return location qualifier
     */
    public String getLocationQualifier() {
        return getN405();
    }

    /**
     * Gets the location identifier.
     * @return location identifier
     */
    public String getLocationIdentifier() {
        return getN406();
    }

    /**
     * Gets the country subdivision code.
     * @return country subdivision code
     */
    public String getCountrySubdivisionCode() {
        return getN407();
    }

    /**
     * Abstract builder for N4Segment.
     * @param <T> the builder type
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String n401;
        protected String n402;
        protected String n403;
        protected String n404;
        protected String n405;
        protected String n406;
        protected String n407;

        /**
         * Returns this builder.
         * @return this builder
         */
        protected abstract T self();

        /**
         * Builds a new N4Segment instance.
         * @return a new N4Segment
         * @throws ValidationException if validation fails
         */
        public abstract N4Segment build() throws ValidationException;

        /**
         * Sets the city name (N401).
         * @param value city name
         * @return this builder
         */
        public T setCityName(String value) {
            this.n401 = value;
            return self();
        }

        /**
         * Sets the state or province code (N402).
         * @param value state or province code
         * @return this builder
         */
        public T setStateOrProvinceCode(String value) {
            this.n402 = value;
            return self();
        }

        /**
         * Sets the postal code (N403).
         * @param value postal code
         * @return this builder
         */
        public T setPostalCode(String value) {
            this.n403 = value;
            return self();
        }

        /**
         * Sets the country code (N404).
         * @param value country code
         * @return this builder
         */
        public T setCountryCode(String value) {
            this.n404 = value;
            return self();
        }

        /**
         * Sets the location qualifier (N405).
         * @param value location qualifier
         * @return this builder
         */
        public T setLocationQualifier(String value) {
            this.n405 = value;
            return self();
        }

        /**
         * Sets the location identifier (N406).
         * @param value location identifier
         * @return this builder
         */
        public T setLocationIdentifier(String value) {
            this.n406 = value;
            return self();
        }

        /**
         * Sets the country subdivision code (N407).
         * @param value country subdivision code
         * @return this builder
         */
        public T setCountrySubdivisionCode(String value) {
            this.n407 = value;
            return self();
        }

        /**
         * Sets the N401 element (city name).
         * @param value N401 value
         * @return this builder
         */
        public T setN401(String value) {
            return setCityName(value);
        }

        /**
         * Sets the N402 element (state or province code).
         * @param value N402 value
         * @return this builder
         */
        public T setN402(String value) {
            return setStateOrProvinceCode(value);
        }

        /**
         * Sets the N403 element (postal code).
         * @param value N403 value
         * @return this builder
         */
        public T setN403(String value) {
            return setPostalCode(value);
        }

        /**
         * Sets the N404 element (country code).
         * @param value N404 value
         * @return this builder
         */
        public T setN404(String value) {
            return setCountryCode(value);
        }

        /**
         * Sets the N405 element (location qualifier).
         * @param value N405 value
         * @return this builder
         */
        public T setN405(String value) {
            return setLocationQualifier(value);
        }

        /**
         * Sets the N406 element (location identifier).
         * @param value N406 value
         * @return this builder
         */
        public T setN406(String value) {
            return setLocationIdentifier(value);
        }

        /**
         * Sets the N407 element (country subdivision code).
         * @param value N407 value
         * @return this builder
         */
        public T setN407(String value) {
            return setCountrySubdivisionCode(value);
        }
    }
}