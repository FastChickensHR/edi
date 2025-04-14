/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.data.DateTimeQualifier;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.DTPSegment;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import com.fastChickensHR.edi.x834.x834Context;

/**
 * Represents a Member Level Date segment in the 834 EDI format.
 * This segment is used to indicate various date values associated with a member in Loop 2000.
 */
public class MemberLevelDates extends DTPSegment {

    private MemberLevelDates(Builder builder) throws ValidationException {
        super(builder);
    }

    public static class Builder extends DTPSegment.AbstractBuilder<Builder> {
        public Builder(x834Context context) {
            super();
            setDtp02(context.getDateFormat());
            setDtp03(context.getDocumentDate());
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
            this.dtp01 = DateTimeQualifier.fromString(qualifier.getCode());
            return this;
        }

        @Override
        public MemberLevelDates build() throws ValidationException {
            return new MemberLevelDates(this);
        }
    }
}