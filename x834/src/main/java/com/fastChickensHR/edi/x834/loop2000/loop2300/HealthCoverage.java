/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2300;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * A health coverage the member carries — one occurrence of Loop 2300 in the X12 834
 * (005010X220A1), rendered as an {@code HD} segment followed by its benefit-period dates
 * ({@code DTP*348}/{@code DTP*349}).
 * <p>
 * {@link #maintenanceTypeCode} (HD01) and {@link #insuranceLineCode} (HD03) are required by the
 * segment; a coverage missing either fails generation. The code dictionaries
 * {@link com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode},
 * {@link com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode} and
 * {@link com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode} publish the valid values
 * ({@code fromString(...).getCode()}).
 * <p>
 * This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class HealthCoverage {
    /** The maintenance action being applied (HD01) — required. */
    private String maintenanceTypeCode;
    /** The insurance line (HD03) — required. */
    private String insuranceLineCode;
    /** The plan coverage description (HD04). */
    private String planCoverageDescription;
    /** The coverage level (HD05). */
    private String coverageLevelCode;
    /** When the benefit begins ({@code DTP*348}). */
    private LocalDateTime startDate;
    /** When it ends ({@code DTP*349}). */
    private LocalDateTime endDate;

    public HealthCoverage() {
    }

    /**
     * @param maintenanceTypeCode the maintenance action being applied (HD01)
     * @param insuranceLineCode   the insurance line (HD03)
     */
    public HealthCoverage(String maintenanceTypeCode, String insuranceLineCode) {
        this.maintenanceTypeCode = maintenanceTypeCode;
        this.insuranceLineCode = insuranceLineCode;
    }
}
