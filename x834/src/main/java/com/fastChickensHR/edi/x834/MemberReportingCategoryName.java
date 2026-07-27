/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;

/**
 * The N1 (Party Identification) segment that names a member reporting category in
 * Loop 2750 of the X12 834 (005010X220A1).
 * <p>
 * N101 is fixed to {@code 75} (Participant); N102 carries the reporting-category
 * <em>name</em> — the label the carrier uses to identify what this occurrence
 * reports (e.g. {@code INDIVIDUALREPNAME}, {@code RELATIONSHIP}). The category's
 * value rides the REF segment that follows this one in the same 2750 occurrence.
 * <p>
 * The name is a carrier-specific label, so it is supplied per occurrence rather
 * than drawn from a fixed enum.
 */
class MemberReportingCategoryName extends Segment {
    public static final String SEGMENT_ID = "N1";

    /** N101 — entity identifier code, fixed to {@code 75} (Participant). */
    private final EntityIdentifierCode n101;
    /** N102 — the reporting-category name (free-form, max 60 characters). */
    private final String n102;

    private MemberReportingCategoryName(Builder builder) throws ValidationException {
        this.n101 = builder.n101;
        this.n102 = builder.n102;

        validate();
    }

    private void validate() throws ValidationException {
        if (n101 == null) {
            throw new ValidationException("Entity Identifier Code (N101) is required");
        }
        if (n102 == null || n102.isEmpty()) {
            throw new ValidationException("One of Plan Sponsor Name (N102) or Identification Code Qualifier (N103) are required");
        }
        if (n102.length() > 60) {
            throw new ValidationException("Plan Sponsor Name (N102) must be 60 characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{n101.getCode(), n102, null, null};
    }

    /** @return N102 — the reporting-category name. */
    public String getReportingCategoryName() {
        return n102;
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberReportingCategoryName}; N101 defaults to {@code 75} (Participant). */
    public static class Builder {
        private final EntityIdentifierCode n101 = EntityIdentifierCode.PARTICIPANT;
        private String n102;

        /** Sets N102 (the reporting-category name). */
        public Builder setReportingCategoryName(String value) {
            this.n102 = value;
            return this;
        }

        /** Builds and validates the segment. */
        public MemberReportingCategoryName build() throws ValidationException {
            return new MemberReportingCategoryName(this);
        }
    }
}
