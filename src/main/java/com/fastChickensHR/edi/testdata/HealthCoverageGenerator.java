/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.testdata;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import net.datafaker.Faker;

import java.util.Random;

/**
 * Fluent generator that produces a fully-populated {@link HealthCoverage} (HD) segment
 * with realistic fake values for the most common fields.
 * <p>
 * Defaults: maintenance type {@link MaintenanceTypeCode#ADDITION},
 * insurance line {@link InsuranceLineCode#HEALTH}, coverage level
 * {@link CoverageLevelCode#EMPLOYEE_ONLY}, and a random plan description.
 */
public final class HealthCoverageGenerator {

    private final Faker faker;
    private final Random random;

    private MaintenanceTypeCode maintenanceTypeCode = MaintenanceTypeCode.ADDITION;
    private MaintenanceReasonCode maintenanceReasonCode;
    private InsuranceLineCode insuranceLineCode;
    private CoverageLevelCode coverageLevelCode;
    private String planDescription;
    private EmploymentStatusCode employmentStatusCode;

    HealthCoverageGenerator(TestDataFaker parent) {
        this.faker = parent.faker();
        this.random = parent.random();
    }

    public HealthCoverageGenerator withMaintenanceType(MaintenanceTypeCode code) {
        this.maintenanceTypeCode = code;
        return this;
    }

    public HealthCoverageGenerator withMaintenanceReason(MaintenanceReasonCode code) {
        this.maintenanceReasonCode = code;
        return this;
    }

    public HealthCoverageGenerator withInsuranceLine(InsuranceLineCode code) {
        this.insuranceLineCode = code;
        return this;
    }

    public HealthCoverageGenerator withCoverageLevel(CoverageLevelCode code) {
        this.coverageLevelCode = code;
        return this;
    }

    public HealthCoverageGenerator withPlanDescription(String description) {
        this.planDescription = description;
        return this;
    }

    public HealthCoverageGenerator withEmploymentStatus(EmploymentStatusCode code) {
        this.employmentStatusCode = code;
        return this;
    }

    /** Builds a {@link HealthCoverage} segment populated with the configured values. */
    public HealthCoverage build() throws ValidationException {
        InsuranceLineCode line = insuranceLineCode != null ? insuranceLineCode : randomInsuranceLine();
        CoverageLevelCode level = coverageLevelCode != null ? coverageLevelCode : CoverageLevelCode.EMPLOYEE_ONLY;
        MaintenanceReasonCode reason = maintenanceReasonCode != null
                ? maintenanceReasonCode
                : MaintenanceReasonCode.ACTIVE;
        EmploymentStatusCode employment = employmentStatusCode != null
                ? employmentStatusCode
                : EmploymentStatusCode.ACTIVE;
        String description = planDescription != null
                ? planDescription
                : faker.company().name() + " " + line.getDescription();

        return HealthCoverage.builder()
                .setMaintenanceTypeCode(maintenanceTypeCode.getCode())
                .setMaintenanceReasonCode(reason.getCode())
                .setInsuranceLineCode(line.getCode())
                .setPlanCoverageDescription(description)
                .setCoverageLevelCode(level.getCode())
                .setEmploymentStatusCode(employment.getCode())
                .build();
    }

    private InsuranceLineCode randomInsuranceLine() {
        InsuranceLineCode[] common = {
                InsuranceLineCode.HEALTH,
                InsuranceLineCode.DENTAL,
                InsuranceLineCode.VISION_OPTICAL
        };
        return common[random.nextInt(common.length)];
    }
}
