/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000A;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public abstract class SponsorName extends Segment {
    public static final String SEGMENT_ID = "N1";
    public static final String DEFAULT_ENTITY_IDENTIFIER_CODE = "P5";
    public static final String DEFAULT_IDENTIFICATION_CODE_QUALIFIER = "FI";

    private final String n101;
    private final String n102;
    private final String n103;
    private final String n104;

    protected SponsorName(Builder builder) throws ValidationException {
        this.n101 = builder.n101;
        this.n102 = builder.n102;
        this.n103 = builder.n103;
        this.n104 = builder.n104;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{n101, n102, n103, n104};
    }

    public String getEntityIdentifierCode() {
        return getN101();
    }

    public String getPlanSponsorName() {
        return getN102();
    }

    public String getIdentificationCodeQualifier() {
        return getN103();
    }

    public String getSponsorIdentifier() {
        return getN104();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private String n101 = DEFAULT_ENTITY_IDENTIFIER_CODE;
        private String n102;
        private String n103 = DEFAULT_IDENTIFICATION_CODE_QUALIFIER;
        private String n104;

        public Builder setEntityIdentifierCode(String value) {
            return setN101(value);
        }

        public Builder setPlanSponsorName(String value) {
            return setN102(value);
        }

        public Builder setIdentificationCodeQualifier(String value) {
            return setN103(value);
        }

        public Builder setSponsorIdentifier(String value) {
            return setN104(value);
        }

        public SponsorName build() throws ValidationException {
            return new SponsorName.SponsorNameImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class SponsorNameImpl extends SponsorName {
        private SponsorNameImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}
