/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public abstract class FileEffectiveDate extends Segment {
    public static final String SEGMENT_ID = "DTP";
    public static final String DEFAULT_DATE_TIME_QUALIFIER = "007";

    private final String dtp01;
    private final String dtp02;
    private final String dtp03;

    protected FileEffectiveDate(FileEffectiveDate.Builder builder) throws ValidationException {
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

    // Domain-specific accessor methods
    public String getDateTimeQualifier() {
        return getDtp01();
    }

    public String getDateTimeFormat() {
        return getDtp02();
    }

    public String getDateTimePeriod() {
        return getDtp03();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private String dtp01 = DEFAULT_DATE_TIME_QUALIFIER;
        private String dtp02;
        private String dtp03;

        public Builder(x834Context context) {
            this.dtp02 = context.getDateFormat().getFormat();
            this.dtp03 = context.getFormattedDocumentDate();
        }

        public Builder setDateTimeQualifier(String value) {
            return setDtp01(value);
        }

        public Builder setDateTimeFormat(String value) {
            return setDtp02(value);
        }

        public Builder setDateTimePeriod(String value) {
            return setDtp03(value);
        }

        public FileEffectiveDate build() throws ValidationException {
            return new FileEffectiveDate.FileEffectiveDateImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class FileEffectiveDateImpl extends FileEffectiveDate {
        private FileEffectiveDateImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}
