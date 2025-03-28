/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;

@Getter
public abstract class NM1Segment extends Segment {
    public static final String SEGMENT_ID = "NM1";

    protected final String nm101;
    protected final String nm102;
    protected final String nm103;
    protected final String nm104;
    protected final String nm105;
    protected final String nm106;
    protected final String nm107;
    protected final String nm108;
    protected final String nm109;

    protected NM1Segment(AbstractBuilder<?> builder) throws ValidationException {
        this.nm101 = builder.nm101;
        this.nm102 = builder.nm102;
        this.nm103 = builder.nm103;
        this.nm104 = builder.nm104;
        this.nm105 = builder.nm105;
        this.nm106 = builder.nm106;
        this.nm107 = builder.nm107;
        this.nm108 = builder.nm108;
        this.nm109 = builder.nm109;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{nm101, nm102, nm103, nm104, nm105, nm106, nm107, nm108, nm109};
    }

    public String getEntityIdentifierCode() {
        return getNm101();
    }

    public String getEntityTypeQualifier() {
        return getNm102();
    }

    public String getLastName() {
        return getNm103();
    }

    public String getFirstName() {
        return getNm104();
    }

    public String getMiddleName() {
        return getNm105();
    }

    public String getNamePrefix() {
        return getNm106();
    }

    public String getNameSuffix() {
        return getNm107();
    }

    public String getIdentificationCodeQualifier() {
        return getNm108();
    }

    public String getIdentificationCode() {
        return getNm109();
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String nm101;
        protected String nm102;
        protected String nm103;
        protected String nm104;
        protected String nm105;
        protected String nm106;
        protected String nm107;
        protected String nm108;
        protected String nm109;

        protected abstract T self();

        public abstract NM1Segment build() throws ValidationException;

        public T setEntityIdentifierCode(String value) {
            this.nm101 = value;
            return self();
        }

        public T setEntityTypeQualifier(String value) {
            this.nm102 = value;
            return self();
        }

        public T setLastName(String value) {
            this.nm103 = value;
            return self();
        }

        public T setFirstName(String value) {
            this.nm104 = value;
            return self();
        }

        public T setMiddleName(String value) {
            this.nm105 = value;
            return self();
        }

        public T setNamePrefix(String value) {
            this.nm106 = value;
            return self();
        }

        public T setNameSuffix(String value) {
            this.nm107 = value;
            return self();
        }

        public T setIdentificationCodeQualifier(String value) {
            this.nm108 = value;
            return self();
        }

        public T setIdentificationCode(String value) {
            this.nm109 = value;
            return self();
        }

        public T setNm101(String value) {
            return setEntityIdentifierCode(value);
        }

        public T setNm102(String value) {
            return setEntityTypeQualifier(value);
        }

        public T setNm103(String value) {
            return setLastName(value);
        }

        public T setNm104(String value) {
            return setFirstName(value);
        }

        public T setNm105(String value) {
            return setMiddleName(value);
        }

        public T setNm106(String value) {
            return setNamePrefix(value);
        }

        public T setNm107(String value) {
            return setNameSuffix(value);
        }

        public T setNm108(String value) {
            return setIdentificationCodeQualifier(value);
        }

        public T setNm109(String value) {
            return setIdentificationCode(value);
        }
    }
}