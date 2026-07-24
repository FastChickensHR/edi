/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.segments.RefSegment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.experimental.Accessors;

/**
 * Subscriber number carried as a REF segment in Loop 2000 of the X12 834.
 * <p>
 * Renders {@code REF*0F*<subscriber number>}: REF01 is fixed to the qualifier
 * {@code 0F} (zero-F, Subscriber Number) and REF02 carries the subscriber's number.
 * <p>
 * Note the leading digit zero: {@code 0F} is element 128's "Subscriber Number", whereas
 * the letter-O {@code OF} is "Operator Identification Number" — a different qualifier the
 * segment previously emitted by mistake.
 */
public class SubscriberNumber extends RefSegment {
    /** REF01 qualifier {@code 0F} (zero-F) — Subscriber Number; the fixed default for this REF segment. */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "0F";

    private SubscriberNumber(SubscriberNumber.Builder builder) throws ValidationException {
        super(builder);
    }

    public static SubscriberNumber.Builder builder() {
        return new SubscriberNumber.Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends RefSegment.AbstractBuilder<SubscriberNumber.Builder> {
        public Builder() {
            setRef01(DEFAULT_ENTITY_IDENTIFIER_CODE);
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
