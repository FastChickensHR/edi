/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.segments.DTPSegment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.experimental.Accessors;

public class FileEffectiveDate extends DTPSegment {
    public static final String DEFAULT_DATE_TIME_QUALIFIER = "007";

    private FileEffectiveDate(FileEffectiveDate.Builder builder) throws ValidationException {
        super(builder);
    }

    public static FileEffectiveDate.Builder builder(x834Context context) {
        return new FileEffectiveDate.Builder(context);
    }

    @Accessors(chain = true)
    public static class Builder extends DTPSegment.AbstractBuilder<Builder> {

        public Builder(x834Context context) {
            super(context);
            this.dtp01 = DEFAULT_DATE_TIME_QUALIFIER;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public FileEffectiveDate build() throws ValidationException {
            if (dtp03 == null || dtp03.isEmpty()) {
                throw new ValidationException("dtp03 (Date Time Period) is required");
            }
            return new FileEffectiveDate(this);
        }
    }
}