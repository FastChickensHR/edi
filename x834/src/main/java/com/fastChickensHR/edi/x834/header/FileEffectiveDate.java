/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.DTPSegment;
import com.fastChickensHR.edi.x834.X834Context;
import lombok.experimental.Accessors;

/**
 * File effective date (DTP*007) in the header portion of the X12 834 (005010X220A1).
 * <p>
 * Produces the DTP segment carrying the master effective date for the file. Extends the
 * generic {@link DTPSegment}.
 */
public class FileEffectiveDate extends DTPSegment {
    /** Default DTP01 date/time qualifier {@code "007"} (Effective). */
    public static final String DEFAULT_DATE_TIME_QUALIFIER = "007";

    private FileEffectiveDate(FileEffectiveDate.Builder builder) throws ValidationException {
        super(builder);
    }

    /**
     * Creates a new Builder for FileEffectiveDate using the given context.
     *
     * @param context The X834 context supplying the date format and document date
     * @return a new Builder instance
     */
    public static FileEffectiveDate.Builder builder(X834Context context) {
        return new FileEffectiveDate.Builder(context);
    }

    /**
     * Builder for FileEffectiveDate. Seeds the effective-date qualifier (DTP01=007),
     * the context's date format (DTP02), and the context's formatted document date (DTP03).
     */
    @Accessors(chain = true)
    public static class Builder extends DTPSegment.AbstractBuilder<Builder> {

        public Builder(X834Context context) {
            super();
            this.setDtp01(DEFAULT_DATE_TIME_QUALIFIER);
            this.setDtp02(context.getDateFormat());
            this.dtp03 = context.getFormattedDocumentDate();
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Builds a new FileEffectiveDate instance.
         *
         * @return A new FileEffectiveDate instance
         * @throws ValidationException if the date/time period (DTP03) is missing
         */
        @Override
        public FileEffectiveDate build() throws ValidationException {
            if (dtp03 == null || dtp03.isEmpty()) {
                throw new ValidationException("dtp03 (Date Time Period) is required");
            }
            return new FileEffectiveDate(this);
        }
    }
}