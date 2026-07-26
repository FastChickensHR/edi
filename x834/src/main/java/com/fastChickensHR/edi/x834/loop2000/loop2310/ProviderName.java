/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2310;

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.NM1Segment;
import lombok.Getter;

/**
 * The NM1 segment naming the member's provider in Loop 2310 of the X12 834 (005010X220A1) —
 * typically the primary care physician the enrollee has selected.
 * <p>
 * NM101 defaults to {@link EntityIdentifierCode#PROVIDER} ({@code 1P}) and NM102 to
 * {@link #PERSON_ENTITY_TYPE} ({@code 1}), a physician being a person. NM103–NM105 carry the
 * provider's name and NM108/NM109 identify them.
 * <p>
 * <b>Which identifier</b> is a carrier answer, not a default: Florida Blue mandates
 * {@link IdentificationCodeQualifier#CMS_NPI} ({@code XX}) — "Only the above code is valid" —
 * while CareFirst uses {@link IdentificationCodeQualifier#SERVICE_PROVIDER} ({@code SV}), its own
 * legacy ID, "until CareFirst provides the NPI". BCBSM asks for the NPI "when available", falling
 * back to its own physician number. So the qualifier is supplied per provider.
 * <p>
 * NM108 and NM109 are a paired presence, enforced here: an identifier with no qualifier does not
 * say what kind of number it is, and a qualifier with no identifier says nothing at all.
 */
@Getter
public class ProviderName extends NM1Segment {

    /** NM102 entity type qualifier {@code 1} — Person; a provider is an individual. */
    public static final String PERSON_ENTITY_TYPE = "1";

    private ProviderName(Builder builder) throws ValidationException {
        super(builder);
        validateIdentification();
    }

    private void validateIdentification() throws ValidationException {
        boolean qualifierPresent = getNm108() != null && !getNm108().isEmpty();
        boolean identifierPresent = getNm109() != null && !getNm109().isEmpty();
        if (qualifierPresent != identifierPresent) {
            throw new ValidationException(
                    "Provider identification: qualifier (NM108) and identifier (NM109) must be present together");
        }
    }

    /** @return NM103 — the provider's last or organization name. */
    public String getProviderLastName() {
        return getNm103();
    }

    /** @return NM109 — the provider's identifier. */
    public String getProviderIdentifier() {
        return getNm109();
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ProviderName}; NM101 defaults to {@code 1P} (Provider) and NM102 to
     * {@link #PERSON_ENTITY_TYPE}.
     */
    public static class Builder extends NM1Segment.AbstractBuilder<Builder> {
        public Builder() {
            setEntityIdentifierCode(EntityIdentifierCode.PROVIDER.getCode());
            setEntityTypeQualifier(PERSON_ENTITY_TYPE);
        }

        @Override
        protected Builder self() {
            return this;
        }

        /**
         * Sets NM108/NM109 — how the provider is identified, and the identifier itself.
         *
         * @param qualifier what kind of identifier this is (NM108), e.g. {@code XX} for an NPI
         * @param identifier the provider's identifier (NM109)
         */
        public Builder setProviderIdentification(IdentificationCodeQualifier qualifier, String identifier) {
            setIdentificationCodeQualifier(qualifier == null ? null : qualifier.getCode());
            return setIdentificationCode(identifier);
        }

        @Override
        public ProviderName build() throws ValidationException {
            return new ProviderName(this);
        }
    }
}
