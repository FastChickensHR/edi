/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Coverage {
    // Coverage identification
    private String coverageId;
    private String planId;
    private String groupId;
    // Coverage details
    private String coverageType;
    private String insuranceType;
    private String coverageLevel;
    // Medicare-specific fields
    private Boolean hasMedicare = false;
    private String medicarePartANumber;
    private LocalDate medicarePartAEffectiveDate;
    private String medicarePartBNumber;
    private LocalDate medicarePartBEffectiveDate;
    // Financial details
    private Double monthlyPremium;
    private Double employerContribution;
    private Double employeeContribution;
}
