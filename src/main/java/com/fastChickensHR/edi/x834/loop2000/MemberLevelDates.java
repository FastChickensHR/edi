/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.DtpSegment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import lombok.experimental.Accessors;

/**
 * Represents a Member Level Date segment in the 834 EDI format.
 * This segment is used to indicate various date values associated with a member in Loop 2000.
 */
public class MemberLevelDates extends DtpSegment {

    private MemberLevelDates(Builder builder) throws ValidationException {
        super(builder);
    }

    @Accessors(chain = true)
    public static class Builder extends DtpSegment.AbstractBuilder<Builder> {
        public Builder(x834Context context) {
            super(context);
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Sets the date qualifier using a MemberDateQualifier enum.
         *
         * @param qualifier The enum representing the date qualifier
         * @return This builder instance for method chaining
         */
        public Builder setDateQualifier(MemberDateQualifier qualifier) {
            this.dtp01 = qualifier.getCode();
            return this;
        }

        @Override
        public MemberLevelDates build() throws ValidationException {
            if (dtp01 == null || dtp01.isEmpty()) {
                throw new ValidationException("dtp01 (Date Time Qualifier) is required");
            }

            if (dtp02 == null || dtp02.isEmpty()) {
                throw new ValidationException("dtp02 (Date Time Format Qualifier) is required");
            }

            if (dtp03 == null || dtp03.isEmpty()) {
                throw new ValidationException("dtp03 (Date Time Period) is required");
            }

            return new MemberLevelDates(this);
        }
    }
}