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
import java.util.ArrayList;
import java.util.List;

@Data
public class Dependent {
    // Identification
    private String id;
    private String ssn;
    // Demographics
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    // Relationship to primary
    private String relationshipCode;
    // Enrollment
    private LocalDate enrollmentDate;
    private LocalDate coverageStartDate;
    private LocalDate coverageEndDate;
    // Coverage details
    private List<Coverage> coverages = new ArrayList<>();

    /**
     * Adds a coverage to this dependent
     */
    public void addCoverage(Coverage coverage) {
        coverages.add(coverage);
    }
}

