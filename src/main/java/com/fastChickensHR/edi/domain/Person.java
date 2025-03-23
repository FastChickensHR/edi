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

/**
 * Domain model representing a person and their enrollment information
 * independent of any EDI format
 */
@Data
public class Person {
    // Identification
    private String id;
    private String ssn;
    private String employeeId;
    // Demographics
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    // Contact information
    private Address address;
    private String phoneNumber;
    private String email;
    // Employment and enrollment
    private LocalDate hireDate;
    private LocalDate enrollmentDate;
    private LocalDate coverageStartDate;
    private LocalDate coverageEndDate;
    private String employmentStatus;
    // Coverage details
    private List<Coverage> coverages = new ArrayList<>();
    // Relationships
    private List<Dependent> dependents = new ArrayList<>();

    /**
     * Adds a dependent to this person
     */
    public void addDependent(Dependent dependent) {
        dependents.add(dependent);
    }

    /**
     * Adds a coverage to this person
     */
    public void addCoverage(Coverage coverage) {
        coverages.add(coverage);
    }
}