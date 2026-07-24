/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the N1 (Party Identification) segment in the X12 834
 * (005010X220A1) Benefit Enrollment and Maintenance transaction.
 * <p>
 * This segment names a party in a loop — in the 834 it is used for the plan
 * sponsor / payer — via an entity identifier, a free-form name, and an optional
 * coded identifier.
 * <p>
 * Element/position map (meanings derived from the accessor names on this class):
 * <ul>
 *     <li>N101 = entity identifier code (the party's role)</li>
 *     <li>N102 = plan sponsor name (free-form, max 60 characters)</li>
 *     <li>N103 = identification code qualifier (what N104 is)</li>
 *     <li>N104 = sponsor identifier (the coded identification value)</li>
 * </ul>
 * At least one of N102 or N103 must be supplied.
 */
@Getter
public abstract class N1Segment extends Segment {
    public static final String SEGMENT_ID = "N1";

    /** N101 — entity identifier code (the party's role). */
    protected final EntityIdentifierCode n101;
    /** N102 — plan sponsor name (free-form, max 60 characters). */
    protected final String n102;
    /** N103 — identification code qualifier describing N104. */
    protected final IdentificationCodeQualifier n103;
    /** N104 — sponsor identifier (the coded identification value). */
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
        // X12 syntax rule P0304: N103 and N104 are a paired presence — if either the
        // Identification Code Qualifier (N103) or the Identification Code (N104) is present,
        // the other is required. A lone qualifier renders as a dangling "*FI" tail (and a lone
        // identifier as "**id"), both non-conformant. Treat an empty N104 as absent.
        boolean n103Present = n103 != null;
        boolean n104Present = n104 != null && !n104.isEmpty();
        if (n103Present != n104Present) {
            throw new ValidationException(
                    "N1 rule P0304: Identification Code Qualifier (N103) and Identification Code (N104) must be present together");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        // N103 is optional (an N1 may name a party by free-form N102 alone, e.g. the 2750
        // reporting-category party which carries only N101=75 + N102=<category name>). Guard the
        // dereference so a null qualifier renders as an empty trailing element rather than an NPE.
        return new String[]{n101.getCode(), n102, n103 == null ? null : n103.getCode(), n104};
    }

    /** @return N101 — entity identifier code. */
    public EntityIdentifierCode getEntityIdentifierCode() {
        return getN101();
    }

    /** @return N102 — plan sponsor name. */
    public String getPlanSponsorName() {
        return getN102();
    }

    /** @return N103 — identification code qualifier. */
    public IdentificationCodeQualifier getIdentificationCodeQualifier() {
        return getN103();
    }

    /** @return N104 — sponsor identifier. */
    public String getSponsorIdentifier() {
        return getN104();
    }

    /**
     * Abstract builder for N1 segments.
     *
     * @param <T> the concrete builder type for method chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected EntityIdentifierCode n101;
        protected String n102;
        protected IdentificationCodeQualifier n103;
        protected String n104;

        /** @return this builder cast to the concrete type. */
        protected abstract T self();

        /**
         * Builds and validates the N1 segment.
         *
         * @return a new {@link N1Segment} instance
         * @throws ValidationException if validation fails
         */
        public abstract N1Segment build() throws ValidationException;

        /** Sets N101 (entity identifier code) from its string code. */
        public T setEntityIdentifierCode(String value) {
            this.n101 = EntityIdentifierCode.fromString(value);
            return self();
        }

        /** Sets N102 (plan sponsor name). */
        public T setPlanSponsorName(String value) {
            this.n102 = value;
            return self();
        }

        /** Sets N103 (identification code qualifier) from its string code. */
        public T setIdentificationCodeQualifier(String value) {
            this.n103 = IdentificationCodeQualifier.fromString(value);
            return self();
        }

        /** Sets N104 (sponsor identifier). */
        public T setSponsorIdentifier(String value) {
            this.n104 = value;
            return self();
        }

        /** Element alias for {@link #setEntityIdentifierCode(String)}. */
        public T setN101(String value) {
            return setEntityIdentifierCode(value);
        }

        /** Element alias for {@link #setPlanSponsorName(String)}. */
        public T setN102(String value) {
            return setPlanSponsorName(value);
        }

        /** Element alias for {@link #setIdentificationCodeQualifier(String)}. */
        public T setN103(String value) {
            return setIdentificationCodeQualifier(value);
        }

        /** Element alias for {@link #setSponsorIdentifier(String)}. */
        public T setN104(String value) {
            return setSponsorIdentifier(value);
        }
    }
}