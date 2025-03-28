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
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents the DMG segment in X12 834 format.
 * This segment is used for demographic information such as birth date, gender, etc.
 */
@Getter
public abstract class DMGSegment extends Segment {
    public static final String SEGMENT_ID = "DMG";

    protected final String dmg01;
    protected final String dmg02;
    protected final String dmg03;
    protected final String dmg04;
    protected final String dmg05;
    protected final String dmg06;
    protected final String dmg07;
    protected final String dmg08;
    protected final String dmg09;
    protected final String dmg10;
    protected final String dmg11;

    protected DMGSegment(AbstractBuilder<?> builder) throws ValidationException {
        dmg01 = builder.dmg01;
        dmg02 = builder.dmg02;
        dmg03 = builder.dmg03;
        dmg04 = builder.dmg04;
        dmg05 = builder.dmg05;
        dmg06 = builder.dmg06;
        dmg07 = builder.dmg07;
        dmg08 = builder.dmg08;
        dmg09 = builder.dmg09;
        dmg10 = builder.dmg10;
        dmg11 = builder.dmg11;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                dmg01, dmg02, dmg03, dmg04, dmg05, dmg06, dmg07, dmg08, dmg09, dmg10, dmg11
        };
    }

    /**
     * Gets the date time period format qualifier.
     *
     * @return date time period format qualifier
     */
    public String getDateTimePeriodFormatQualifier() {
        return getDmg01();
    }

    /**
     * Gets the birth date in the specified format.
     *
     * @return birth date
     */
    public String getBirthDate() {
        return getDmg02();
    }

    /**
     * Gets the gender code.
     * Common values: M (Male), F (Female)
     *
     * @return gender code
     */
    public String getGenderCode() {
        return getDmg03();
    }

    /**
     * Gets the marital status code.
     *
     * @return marital status code
     */
    public String getMaritalStatusCode() {
        return getDmg04();
    }

    /**
     * Gets the race or ethnicity code.
     *
     * @return race or ethnicity code
     */
    public String getRaceOrEthnicityCode() {
        return getDmg05();
    }

    /**
     * Gets the citizenship status code.
     *
     * @return citizenship status code
     */
    public String getCitizenshipStatusCode() {
        return getDmg06();
    }

    /**
     * Gets the country code.
     *
     * @return country code
     */
    public String getCountryCode() {
        return getDmg07();
    }

    /**
     * Gets the basis of verification code.
     *
     * @return basis of verification code
     */
    public String getBasisOfVerificationCode() {
        return getDmg08();
    }

    /**
     * Gets the quantity.
     *
     * @return quantity
     */
    public String getQuantity() {
        return getDmg09();
    }

    /**
     * Gets the code list qualifier code.
     *
     * @return code list qualifier code
     */
    public String getCodeListQualifierCode() {
        return getDmg10();
    }

    /**
     * Gets the industry code.
     *
     * @return industry code
     */
    public String getIndustryCode() {
        return getDmg11();
    }

    /**
     * Abstract builder for DMGSegment.
     *
     * @param <T> the builder type
     */
    @Setter
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String dmg01;
        protected String dmg02;
        protected String dmg03;
        protected String dmg04;
        protected String dmg05;
        protected String dmg06;
        protected String dmg07;
        protected String dmg08;
        protected String dmg09;
        protected String dmg10;
        protected String dmg11;

        /**
         * Returns this builder.
         *
         * @return this builder
         */
        protected abstract T self();

        /**
         * Builds a new DMGSegment instance.
         *
         * @return a new DMGSegment
         * @throws ValidationException if validation fails
         */
        public abstract DMGSegment build() throws ValidationException;

        /**
         * Sets the date time period format qualifier (DMG01).
         *
         * @param value date time period format qualifier
         * @return this builder
         */
        public T setDateTimePeriodFormatQualifier(String value) {
            return setDmg01(value);
        }

        /**
         * Sets the birth date (DMG02).
         *
         * @param value birth date
         * @return this builder
         */
        public T setBirthDate(String value) {
            return setDmg02(value);
        }

        /**
         * Sets the gender code (DMG03).
         *
         * @param value gender code
         * @return this builder
         */
        public T setGenderCode(String value) {
            return setDmg03(value);
        }

        /**
         * Sets the marital status code (DMG04).
         *
         * @param value marital status code
         * @return this builder
         */
        public T setMaritalStatusCode(String value) {
            return setDmg04(value);
        }

        /**
         * Sets the race or ethnicity code (DMG05).
         *
         * @param value race or ethnicity code
         * @return this builder
         */
        public T setRaceOrEthnicityCode(String value) {
            return setDmg05(value);
        }

        /**
         * Sets the citizenship status code (DMG06).
         *
         * @param value citizenship status code
         * @return this builder
         */
        public T setCitizenshipStatusCode(String value) {
            return setDmg06(value);
        }

        /**
         * Sets the country code (DMG07).
         *
         * @param value country code
         * @return this builder
         */
        public T setCountryCode(String value) {
            return setDmg07(value);
        }

        /**
         * Sets the basis of verification code (DMG08).
         *
         * @param value basis of verification code
         * @return this builder
         */
        public T setBasisOfVerificationCode(String value) {
            return setDmg08(value);
        }

        /**
         * Sets the quantity (DMG09).
         *
         * @param value quantity
         * @return this builder
         */
        public T setQuantity(String value) {
            return setDmg09(value);
        }

        /**
         * Sets the code list qualifier code (DMG10).
         *
         * @param value code list qualifier code
         * @return this builder
         */
        public T setCodeListQualifierCode(String value) {
            return setDmg10(value);
        }

        /**
         * Sets the industry code (DMG11).
         *
         * @param value industry code
         * @return this builder
         */
        public T setIndustryCode(String value) {
            return setDmg11(value);
        }

        /**
         * Sets the DMG01 element.
         *
         * @param value DMG01 value
         * @return this builder
         */
        public T setDmg01(String value) {
            this.dmg01 = value;
            return self();
        }

        /**
         * Sets the DMG02 element.
         *
         * @param value DMG02 value
         * @return this builder
         */
        public T setDmg02(String value) {
            this.dmg02 = value;
            return self();
        }

        /**
         * Sets the DMG03 element.
         *
         * @param value DMG03 value
         * @return this builder
         */
        public T setDmg03(String value) {
            this.dmg03 = value;
            return self();
        }

        /**
         * Sets the DMG04 element.
         *
         * @param value DMG04 value
         * @return this builder
         */
        public T setDmg04(String value) {
            this.dmg04 = value;
            return self();
        }

        /**
         * Sets the DMG05 element.
         *
         * @param value DMG05 value
         * @return this builder
         */
        public T setDmg05(String value) {
            this.dmg05 = value;
            return self();
        }

        /**
         * Sets the DMG06 element.
         *
         * @param value DMG06 value
         * @return this builder
         */
        public T setDmg06(String value) {
            this.dmg06 = value;
            return self();
        }

        /**
         * Sets the DMG07 element.
         *
         * @param value DMG07 value
         * @return this builder
         */
        public T setDmg07(String value) {
            this.dmg07 = value;
            return self();
        }

        /**
         * Sets the DMG08 element.
         *
         * @param value DMG08 value
         * @return this builder
         */
        public T setDmg08(String value) {
            this.dmg08 = value;
            return self();
        }

        /**
         * Sets the DMG09 element.
         *
         * @param value DMG09 value
         * @return this builder
         */
        public T setDmg09(String value) {
            this.dmg09 = value;
            return self();
        }

        /**
         * Sets the DMG10 element.
         *
         * @param value DMG10 value
         * @return this builder
         */
        public T setDmg10(String value) {
            this.dmg10 = value;
            return self();
        }

        /**
         * Sets the DMG11 element.
         *
         * @param value DMG11 value
         * @return this builder
         */
        public T setDmg11(String value) {
            this.dmg11 = value;
            return self();
        }
    }
}