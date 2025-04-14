/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.DateTimeQualifier;
import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.dates.DateFormatter;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class DTPSegment extends Segment {
    public static final String SEGMENT_ID = "DTP";

    protected final DateTimeQualifier dtp01;
    protected final DateFormat dtp02;
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

    public DateTimeQualifier getDateTimeQualifier() {
        return getDtp01();
    }

    public DateFormat getDateTimeFormat() {
        return getDtp02();
    }

    public String getDateTimePeriod() {
        return getDtp03();
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected DateTimeQualifier dtp01;
        protected DateFormat dtp02;
        protected String dtp03;

        protected AbstractBuilder() {
            // No default initialization
        }

        protected abstract T self();

        public abstract DTPSegment build() throws ValidationException;

        public T setDateTimeQualifier(String value) {
            this.dtp01 = DateTimeQualifier.fromString(value);
            return self();
        }

        public T setDateTimeQualifier(DateTimeQualifier value) {
            this.dtp01 = value;
            return self();
        }

        public T setDateTimeFormat(DateFormat value) {
            this.dtp02 = value;
            return self();
        }

        public T setDateTimePeriod(LocalDateTime value) {
            this.dtp03 = DateFormatter.formatDate(this.dtp02, value);
            return self();
        }

        public T setDateTimePeriod(LocalDateTime value, DateFormat format) {
            this.dtp03 = DateFormatter.formatDate(format, value);
            return self();
        }

        public T setDtp01(String value) {
            return setDateTimeQualifier(value);
        }

        public T setDtp01(DateTimeQualifier value) {
            return setDateTimeQualifier(value);
        }

        public T setDtp02(DateFormat value) {
            return setDateTimeFormat(value);
        }

        public T setDtp03(LocalDateTime value) {
            return setDateTimePeriod(value);
        }

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