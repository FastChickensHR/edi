/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2310;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.PLASegment;
import lombok.Getter;

/**
 * The PLA segment stating what is happening to a member's provider assignment, closing Loop 2310 of
 * the X12 834 (005010X220A1).
 * <p>
 * PLA02 defaults to {@code 1P} (Provider) to agree with the {@link ProviderName} it follows —
 * the action applies to the provider that loop just named. The action, date and optional reason are
 * supplied per change.
 */
@Getter
public class ProviderChange extends PLASegment {

    private ProviderChange(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ProviderChange}; PLA02 defaults to {@code 1P} (Provider). */
    public static class Builder extends PLASegment.AbstractBuilder<Builder> {
        public Builder() {
            setEntityIdentifierCode(com.fastChickensHR.edi.x834.data.EntityIdentifierCode.PROVIDER);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ProviderChange build() throws ValidationException {
            return new ProviderChange(this);
        }
    }
}
