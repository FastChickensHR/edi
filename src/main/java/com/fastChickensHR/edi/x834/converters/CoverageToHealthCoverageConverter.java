/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.converters;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.domain.Coverage;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;

/**
 * Converts a domain {@link Coverage} into an X12 834 {@link HealthCoverage} (HD segment).
 * <p>
 * Domain coverage types ("Medical", "Dental", "Vision") and coverage levels
 * ("Family", "Employee+Spouse", etc.) are mapped to their X12 equivalents via the
 * {@link InsuranceLineCode#fromString} and {@link CoverageLevelCode#fromString} lookups,
 * which support both code values and natural-language descriptions.
 */
public final class CoverageToHealthCoverageConverter {

    private CoverageToHealthCoverageConverter() {
    }

    /**
     * Converts a domain {@link Coverage} to a {@link HealthCoverage} segment.
     *
     * @param coverage the domain coverage to convert
     * @return a fully populated {@link HealthCoverage}
     * @throws ValidationException if HD01 (maintenance type) or HD03 (insurance line) cannot
     *                             be determined from the provided coverage data
     */
    public static HealthCoverage convert(Coverage coverage) throws ValidationException {
        HealthCoverage.Builder builder = HealthCoverage.builder()
                .setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION.getCode());

        boolean insuranceLineSet = false;
        if (coverage.getInsuranceType() != null) {
            try {
                InsuranceLineCode line = InsuranceLineCode.fromString(coverage.getInsuranceType());
                builder.setInsuranceLineCode(line.getCode());
                insuranceLineSet = true;
            } catch (IllegalArgumentException ignored) {
                // fall through to coverageType
            }
        }

        if (!insuranceLineSet && coverage.getCoverageType() != null) {
            try {
                InsuranceLineCode line = InsuranceLineCode.fromString(coverage.getCoverageType());
                builder.setInsuranceLineCode(line.getCode());
            } catch (IllegalArgumentException ignored) {
                // HD03 validation will surface the error
            }
        }

        if (coverage.getCoverageLevel() != null) {
            try {
                CoverageLevelCode level = CoverageLevelCode.fromString(coverage.getCoverageLevel());
                builder.setCoverageLevelCode(level.getCode());
            } catch (IllegalArgumentException ignored) {
                // coverage level is optional (HD05); skip
            }
        }

        if (coverage.getPlanId() != null) {
            builder.setPlanCoverageDescription(coverage.getPlanId());
        }

        return builder.build();
    }
}
