/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.EntityIdentifierCode;
import com.fastChickensHR.edi.common.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

@Getter
public abstract class N1Segment extends Segment {
    public static final String SEGMENT_ID = "N1";

    protected final EntityIdentifierCode n101;
    protected final String n102;
    protected final IdentificationCodeQualifier n103;
    protected final String n104;

    protected N1Segment(AbstractBuilder<?> builder) throws ValidationException {
        this.n101 = builder.n101;
        this.n102 = builder.n102;
        this.n103 = builder.n103;
        this.n104 = builder.n104;

        validate();
    }

    private void validate() throws ValidationException {
        if (n101 == null) {
            throw new ValidationException("Entity Identifier Code (N101) is required");
        }
        if ((n102 == null || n102.isEmpty()) && n103 == null) {
            throw new ValidationException("One of Plan Sponsor Name (N102) or Identification Code Qualifier (N103) are required");
        }
        if (n102 != null && n102.length() > 60) {
            throw new ValidationException("Plan Sponsor Name (N102) must be 60 characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{n101.getCode(), n102, n103.getCode(), n104};
    }

    public EntityIdentifierCode getEntityIdentifierCode() {
        return getN101();
    }

    public String getPlanSponsorName() {
        return getN102();
    }

    public IdentificationCodeQualifier getIdentificationCodeQualifier() {
        return getN103();
    }

    public String getSponsorIdentifier() {
        return getN104();
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected EntityIdentifierCode n101;
        protected String n102;
        protected IdentificationCodeQualifier n103;
        protected String n104;

        protected abstract T self();

        public abstract N1Segment build() throws ValidationException;

        public T setEntityIdentifierCode(String value) {
            this.n101 = EntityIdentifierCode.fromString(value);
            return self();
        }

        public T setPlanSponsorName(String value) {
            this.n102 = value;
            return self();
        }

        public T setIdentificationCodeQualifier(String value) {
            this.n103 = IdentificationCodeQualifier.fromString(value);
            return self();
        }

        public T setSponsorIdentifier(String value) {
            this.n104 = value;
            return self();
        }

        public T setN101(String value) {
            return setEntityIdentifierCode(value);
        }

        public T setN102(String value) {
            return setPlanSponsorName(value);
        }

        public T setN103(String value) {
            return setIdentificationCodeQualifier(value);
        }

        public T setN104(String value) {
            return setSponsorIdentifier(value);
        }
    }
}