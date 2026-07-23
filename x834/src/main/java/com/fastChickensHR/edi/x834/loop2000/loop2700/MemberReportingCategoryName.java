/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2700;

import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.N1Segment;
import lombok.Getter;

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
@Getter
public class MemberReportingCategoryName extends N1Segment {

    private MemberReportingCategoryName(Builder builder) throws ValidationException {
        super(builder);
    }

    /** @return N102 — the reporting-category name. */
    public String getReportingCategoryName() {
        return getN102();
    }

    /** @return a new {@link Builder}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link MemberReportingCategoryName}; N101 defaults to {@code 75} (Participant). */
    public static class Builder extends N1Segment.AbstractBuilder<Builder> {
        public Builder() {
            this.n101 = EntityIdentifierCode.PARTICIPANT;
        }

        @Override
        protected Builder self() {
            return this;
        }

        /** Sets N102 (the reporting-category name). */
        public Builder setReportingCategoryName(String value) {
            return setPlanSponsorName(value);
        }

        @Override
        public MemberReportingCategoryName build() throws ValidationException {
            return new MemberReportingCategoryName(this);
        }
    }
}
