/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.segments.NM1Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.experimental.Accessors;

/**
 * Represents the NM1 segment for a member name in the 2100A loop.
 * This contains identifying information about the member including
 * name and identification details.
 */
public class MemberName extends NM1Segment {
    /** NM101 entity identifier {@code IL} — Insured or Subscriber; the fixed default for the member NM1. */
    public static final String ENTITY_IDENTIFIER_CODE = "IL";
    /** NM102 entity type qualifier {@code 1} — Person; the fixed default for the member NM1. */
    public static final String PERSON_ENTITY_TYPE = "1";

    protected MemberName(Builder builder) throws ValidationException {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(chain = true)
    public static class Builder extends NM1Segment.AbstractBuilder<MemberName.Builder> {
        public Builder() {
            this.nm101 = ENTITY_IDENTIFIER_CODE;
            this.nm102 = PERSON_ENTITY_TYPE;
        }

        @Override
        protected MemberName.Builder self() {
            return this;
        }

        @Override
        public MemberName build() throws ValidationException {

            validateRequiredFields();
            return new MemberName(this);
        }

        private void validateRequiredFields() throws ValidationException {
            if (nm101 == null || nm101.isEmpty()) {
                throw new ValidationException("Entity Identifier Code (NM101) is required");
            }

            if (nm102 == null || nm102.isEmpty()) {
                throw new ValidationException("Entity Type Qualifier (NM102) is required");
            }

            // Conditional requirement: a person (NM102 = 1) must carry a last/family name (NM103).
            if (PERSON_ENTITY_TYPE.equals(nm102) && (nm103 == null || nm103.isEmpty())) {
                throw new ValidationException(
                        "Last Name (NM103) is required when the Entity Type Qualifier (NM102) is a person (1)");
            }

            // Mutual pairing (X12 syntax rule P0809): the identification code qualifier (NM108) and
            // the identification code (NM109) must either both be present or both be absent.
            boolean hasQualifier = nm108 != null && !nm108.isEmpty();
            boolean hasCode = nm109 != null && !nm109.isEmpty();
            if (hasQualifier != hasCode) {
                throw new ValidationException(
                        "Identification Code Qualifier (NM108) and Identification Code (NM109) must be provided together");
            }
        }
    }
}