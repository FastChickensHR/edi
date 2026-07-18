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
 * Member identification number carried as a REF segment in Loop 2000 of the X12 834.
 * <p>
 * Renders {@code REF*DX*<identification number>}: REF01 is fixed to the qualifier
 * {@code DX} and REF02 carries the member's identification number.
 */
public class MemberIdentificationNumber extends RefSegment {
    /** REF01 qualifier {@code DX} — the fixed default reference qualifier for this member-identification REF segment. */
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "DX";

    private MemberIdentificationNumber(MemberIdentificationNumber.Builder builder) throws ValidationException {
        super(builder);
    }

    public static MemberIdentificationNumber.Builder builder() {
        return new MemberIdentificationNumber.Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends RefSegment.AbstractBuilder<MemberIdentificationNumber.Builder> {
        public Builder() {
            setRef01(DEFAULT_ENTITY_IDENTIFIER_CODE);
        }

        @Override
        protected MemberIdentificationNumber.Builder self() {
            return this;
        }

        @Override
        public MemberIdentificationNumber build() throws ValidationException {
            if (ref02 == null || ref02.isEmpty()) {
                throw new ValidationException("ref02 (Subscriber Number) is required");
            }
            return new MemberIdentificationNumber(this);
        }
    }
}

