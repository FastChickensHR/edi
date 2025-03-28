/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.segments.RefSegment;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.experimental.Accessors;

public class SubscriberNumber extends RefSegment {
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "OF";

    private SubscriberNumber(SubscriberNumber.Builder builder) throws ValidationException {
        super(builder);
    }

    public static SubscriberNumber.Builder builder() {
        return new SubscriberNumber.Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends RefSegment.AbstractBuilder<SubscriberNumber.Builder> {
        public Builder() {
            this.ref01 = DEFAULT_ENTITY_IDENTIFIER_CODE;
        }

        @Override
        protected SubscriberNumber.Builder self() {
            return this;
        }

        @Override
        public SubscriberNumber build() throws ValidationException {
            if (ref02 == null || ref02.isEmpty()) {
                throw new ValidationException("ref02 (Subscriber Number) is required");
            }
            return new SubscriberNumber(this);
        }
    }
}
