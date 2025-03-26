/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.segments;

import com.fastChickensHR.edi.x834.common.dates.DateFormat;
import com.fastChickensHR.edi.x834.common.dates.DateFormatter;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class DTPSegment extends Segment {
    public static final String SEGMENT_ID = "DTP";

    protected final String dtp01;
    protected final String dtp02;
    protected final String dtp03;

    protected DTPSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.dtp01 = builder.dtp01;
        this.dtp02 = builder.dtp02;
        this.dtp03 = builder.dtp03;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{dtp01, dtp02, dtp03};
    }

    public String getDateTimeQualifier() {
        return getDtp01();
    }

    public String getDateTimeFormat() {
        return getDtp02();
    }

    public String getDateTimePeriod() {
        return getDtp03();
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String dtp01;
        protected String dtp02;
        protected String dtp03;
        protected x834Context context;

        protected AbstractBuilder(x834Context context) {
            this.context = context;
            if (context != null) {
                this.dtp02 = context.getDateFormat().getFormat();
                this.dtp03 = context.getFormattedDocumentDate();
            }
        }

        protected abstract T self();

        public abstract DTPSegment build() throws ValidationException;

        public T setDateTimeQualifier(String value) {
            this.dtp01 = value;
            return self();
        }

        public T setDateTimeFormat(DateFormat value) {
            this.dtp02 = value.getFormat();
            return self();
        }

        public T setDateTimePeriod(LocalDateTime value) {
            this.dtp03 = DateFormatter.formatDate(context.getDateFormat(), value);
            return self();
        }

        public T setDtp01(String value) {
            return setDateTimeQualifier(value);
        }

        public T setDtp02(DateFormat value) {
            return setDateTimeFormat(value);
        }

        public T setDtp03(LocalDateTime value) {
            return setDateTimePeriod(value);
        }
    }
}