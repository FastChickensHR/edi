/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.DateTimeQualifier;
import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.dates.DateFormatter;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Represents the DTP (Date or Time or Period) segment in the X12 834
 * (005010X220A1) Benefit Enrollment and Maintenance transaction.
 * <p>
 * This segment expresses a date, time, or period together with a qualifier that
 * states what the date means and a format code that states how it is encoded.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>DTP01 = date/time qualifier — what the date represents (required)</li>
 *     <li>DTP02 = date/time format qualifier — how DTP03 is formatted (required)</li>
 *     <li>DTP03 = date/time period value, max 35 characters (required)</li>
 * </ul>
 */
@Getter
public abstract class DTPSegment extends Segment {
    public static final String SEGMENT_ID = "DTP";

    /** DTP01 — date/time qualifier stating what the date represents (required). */
    protected final DateTimeQualifier dtp01;
    /** DTP02 — date/time format qualifier stating how DTP03 is encoded (required). */
    protected final DateFormat dtp02;
    /** DTP03 — the date/time period value, max 35 characters (required). */
    protected final String dtp03;

    protected DTPSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.dtp01 = builder.dtp01;
        this.dtp02 = builder.dtp02;
        this.dtp03 = builder.dtp03;

        validate();
    }

    private void validate() throws ValidationException {
        if (this.dtp01 == null) {
            throw new ValidationException("DTP01 (Date Time Qualifier) is required");
        }
        if (this.dtp02 == null) {
            throw new ValidationException("DTP02 (Date Time Format) is required");
        }
        if (this.dtp03 == null || this.dtp03.isEmpty()) {
            throw new ValidationException("DTP03 (Date Time Period) is required");
        }
        if (this.dtp03.length() > 35) {
            throw new ValidationException("DTP03 (Date Time Period) must be 35 characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{dtp01.getCode(), dtp02.getFormat(), dtp03};
    }

    /** @return DTP01 — the date/time qualifier. */
    public DateTimeQualifier getDateTimeQualifier() {
        return getDtp01();
    }

    /** @return DTP02 — the date/time format qualifier. */
    public DateFormat getDateTimeFormat() {
        return getDtp02();
    }

    /** @return DTP03 — the date/time period value. */
    public String getDateTimePeriod() {
        return getDtp03();
    }

    /**
     * Abstract builder for DTP segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected DateTimeQualifier dtp01;
        protected DateFormat dtp02;
        protected String dtp03;

        protected AbstractBuilder() {
            // No default initialization
        }

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the DTP segment.
         *
         * @return a new {@link DTPSegment} instance
         * @throws ValidationException if validation fails
         */
        public abstract DTPSegment build() throws ValidationException;

        /** Sets DTP01 (date/time qualifier) from its string code. */
        public T setDateTimeQualifier(String value) {
            this.dtp01 = DateTimeQualifier.fromString(value);
            return self();
        }

        /** Sets DTP01 (date/time qualifier). */
        public T setDateTimeQualifier(DateTimeQualifier value) {
            this.dtp01 = value;
            return self();
        }

        /** Sets DTP02 (date/time format qualifier). */
        public T setDateTimeFormat(DateFormat value) {
            this.dtp02 = value;
            return self();
        }

        /** Sets DTP03 (date/time period), formatting the value with the previously set DTP02 format. */
        public T setDateTimePeriod(LocalDateTime value) {
            this.dtp03 = DateFormatter.formatDate(this.dtp02, value);
            return self();
        }

        /** Sets DTP03 (date/time period), formatting the value with the supplied format. */
        public T setDateTimePeriod(LocalDateTime value, DateFormat format) {
            this.dtp03 = DateFormatter.formatDate(format, value);
            return self();
        }

        /** Element alias for {@link #setDateTimeQualifier(String)}. */
        public T setDtp01(String value) {
            return setDateTimeQualifier(value);
        }

        /** Element alias for {@link #setDateTimeQualifier(DateTimeQualifier)}. */
        public T setDtp01(DateTimeQualifier value) {
            return setDateTimeQualifier(value);
        }

        /** Element alias for {@link #setDateTimeFormat(DateFormat)}. */
        public T setDtp02(DateFormat value) {
            return setDateTimeFormat(value);
        }

        /** Element alias for {@link #setDateTimePeriod(LocalDateTime)}. */
        public T setDtp03(LocalDateTime value) {
            return setDateTimePeriod(value);
        }

        /** Element alias for {@link #setDateTimePeriod(LocalDateTime, DateFormat)}. */
        public T setDtp03(LocalDateTime value, DateFormat format) {
            return setDateTimePeriod(value, format);
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public DTPSegment build() throws ValidationException {
            return new DTPSegmentImpl(this);
        }

        private static class DTPSegmentImpl extends DTPSegment {
            public DTPSegmentImpl(AbstractBuilder<?> builder) throws ValidationException {
                super(builder);
            }
        }
    }
}