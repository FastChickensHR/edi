/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the NM1 (Individual or Organizational Name) segment in the X12 834 (005010X220A1).
 * <p>
 * Element positions: NM101 entity identifier code, NM102 entity type qualifier
 * (1 = person, 2 = non-person), NM103 last name / organization name, NM104 first name,
 * NM105 middle name, NM106 name prefix, NM107 name suffix, NM108 identification code
 * qualifier, NM109 identification code. Concrete subclasses (e.g. member/sponsor/payer name)
 * fix the qualifier elements and supply the loop-specific defaults.
 */
@Getter
public abstract class NM1Segment extends Segment {
    public static final String SEGMENT_ID = "NM1";

    /** NM101 — entity identifier code (the party's role). */
    protected final String nm101;
    /** NM102 — entity type qualifier (1 = person, 2 = non-person / organization). */
    protected final String nm102;
    /** NM103 — last name or organization name. */
    protected final String nm103;
    /** NM104 — first name. */
    protected final String nm104;
    /** NM105 — middle name. */
    protected final String nm105;
    /** NM106 — name prefix. */
    protected final String nm106;
    /** NM107 — name suffix. */
    protected final String nm107;
    /** NM108 — identification code qualifier (what NM109 is). */
    protected final String nm108;
    /** NM109 — identification code value. */
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

    /** @return NM101 — entity identifier code. */
    public String getEntityIdentifierCode() {
        return getNm101();
    }

    /** @return NM102 — entity type qualifier (1 = person, 2 = non-person). */
    public String getEntityTypeQualifier() {
        return getNm102();
    }

    /** @return NM103 — last name or organization name. */
    public String getLastName() {
        return getNm103();
    }

    /** @return NM104 — first name. */
    public String getFirstName() {
        return getNm104();
    }

    /** @return NM105 — middle name. */
    public String getMiddleName() {
        return getNm105();
    }

    /** @return NM106 — name prefix. */
    public String getNamePrefix() {
        return getNm106();
    }

    /** @return NM107 — name suffix. */
    public String getNameSuffix() {
        return getNm107();
    }

    /** @return NM108 — identification code qualifier. */
    public String getIdentificationCodeQualifier() {
        return getNm108();
    }

    /** @return NM109 — identification code value. */
    public String getIdentificationCode() {
        return getNm109();
    }

    /**
     * Abstract builder for NM1 segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
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

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the NM1 segment.
         *
         * @return a new {@link NM1Segment} instance
         * @throws ValidationException if validation fails
         */
        public abstract NM1Segment build() throws ValidationException;

        /** Sets NM101 (entity identifier code). */
        public T setEntityIdentifierCode(String value) {
            this.nm101 = value;
            return self();
        }

        /** Sets NM102 (entity type qualifier; 1 = person, 2 = non-person). */
        public T setEntityTypeQualifier(String value) {
            this.nm102 = value;
            return self();
        }

        /** Sets NM103 (last name or organization name). */
        public T setLastName(String value) {
            this.nm103 = value;
            return self();
        }

        /** Sets NM104 (first name). */
        public T setFirstName(String value) {
            this.nm104 = value;
            return self();
        }

        /** Sets NM105 (middle name). */
        public T setMiddleName(String value) {
            this.nm105 = value;
            return self();
        }

        /** Sets NM106 (name prefix). */
        public T setNamePrefix(String value) {
            this.nm106 = value;
            return self();
        }

        /** Sets NM107 (name suffix). */
        public T setNameSuffix(String value) {
            this.nm107 = value;
            return self();
        }

        /** Sets NM108 (identification code qualifier). */
        public T setIdentificationCodeQualifier(String value) {
            this.nm108 = value;
            return self();
        }

        /** Sets NM109 (identification code value). */
        public T setIdentificationCode(String value) {
            this.nm109 = value;
            return self();
        }

        /** Element alias for {@link #setEntityIdentifierCode(String)}. */
        public T setNm101(String value) {
            return setEntityIdentifierCode(value);
        }

        /** Element alias for {@link #setEntityTypeQualifier(String)}. */
        public T setNm102(String value) {
            return setEntityTypeQualifier(value);
        }

        /** Element alias for {@link #setLastName(String)}. */
        public T setNm103(String value) {
            return setLastName(value);
        }

        /** Element alias for {@link #setFirstName(String)}. */
        public T setNm104(String value) {
            return setFirstName(value);
        }

        /** Element alias for {@link #setMiddleName(String)}. */
        public T setNm105(String value) {
            return setMiddleName(value);
        }

        /** Element alias for {@link #setNamePrefix(String)}. */
        public T setNm106(String value) {
            return setNamePrefix(value);
        }

        /** Element alias for {@link #setNameSuffix(String)}. */
        public T setNm107(String value) {
            return setNameSuffix(value);
        }

        /** Element alias for {@link #setIdentificationCodeQualifier(String)}. */
        public T setNm108(String value) {
            return setIdentificationCodeQualifier(value);
        }

        /** Element alias for {@link #setIdentificationCode(String)}. */
        public T setNm109(String value) {
            return setIdentificationCode(value);
        }
    }
}