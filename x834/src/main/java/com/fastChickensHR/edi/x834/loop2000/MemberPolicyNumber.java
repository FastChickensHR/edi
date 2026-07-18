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
 * Member policy number as a REF segment in Loop 2000 of the X12 834 (005010X220A1).
 * <p>
 * Renders {@code REF*1L*<policy number>}: REF01 is fixed to the qualifier {@code 1L}
 * ("Group or Policy Number") and REF02 carries the member's group/policy number.
 */
public class MemberPolicyNumber extends RefSegment {
    /** REF01 qualifier {@code 1L} — Group or Policy Number. */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "1L";

    private MemberPolicyNumber(MemberPolicyNumber.Builder builder) throws ValidationException {
        super(builder);
    }

    public static MemberPolicyNumber.Builder builder() {
        return new MemberPolicyNumber.Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends RefSegment.AbstractBuilder<MemberPolicyNumber.Builder> {
        public Builder() {
            setRef01(DEFAULT_ENTITY_IDENTIFIER_CODE);
        }

        @Override
        protected MemberPolicyNumber.Builder self() {
            return this;
        }

        @Override
        public MemberPolicyNumber build() throws ValidationException {
            if (ref02 == null || ref02.isEmpty()) {
                throw new ValidationException("ref02 (Subscriber Number) is required");
            }
            return new MemberPolicyNumber(this);
        }
    }
}
