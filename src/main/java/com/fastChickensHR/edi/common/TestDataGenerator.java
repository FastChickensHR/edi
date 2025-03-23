/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common;

import com.fastChickensHR.edi.domain.Address;
import com.fastChickensHR.edi.domain.Coverage;
import com.fastChickensHR.edi.domain.Dependent;
import com.fastChickensHR.edi.domain.Person;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for generating sample domain data for testing
 */
public class TestDataGenerator {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    /**
     * Generates a list of persons with random data
     *
     * @param count Number of persons to generate
     * @return List of generated persons
     */
    public static List<Person> generatePersons(int count) {
        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            persons.add(generatePerson());
        }

        return persons;
    }

    /**
     * Generates a single person with random data
     *
     * @return A randomly generated person
     */
    public static Person generatePerson() {
        Person person = new Person();

        // Set identification
        person.setId(faker.idNumber().valid());
        person.setSsn(faker.idNumber().ssnValid().replace("-", ""));
        person.setEmployeeId("EMP" + faker.number().digits(6));

        // Set demographics
        person.setFirstName(faker.name().firstName());
        person.setMiddleName(faker.random().nextBoolean() ? faker.name().firstName() : null);
        person.setLastName(faker.name().lastName());
        person.setBirthDate(
                faker.date().birthday(18, 65).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
        person.setGender(random.nextBoolean() ? "M" : "F");

        // Set contact information
        Address address = new Address();
        address.setAddressLine1(faker.address().streetAddress());
        address.setAddressLine2(faker.random().nextBoolean() ? faker.address().secondaryAddress() : null);
        address.setCity(faker.address().city());
        address.setState(faker.address().stateAbbr());
        address.setZipCode(faker.address().zipCode());
        address.setCountry("USA");
        person.setAddress(address);

        person.setPhoneNumber(faker.phoneNumber().cellPhone());
        person.setEmail(faker.internet().emailAddress(
                person.getFirstName().toLowerCase() + "." + person.getLastName().toLowerCase()));

        // Set employment and enrollment
        LocalDate hireDate = faker.date()
                .past(730, TimeUnit.DAYS)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        person.setHireDate(hireDate);

        LocalDate enrollmentDate = hireDate.plusDays(random.nextInt(30) + 1);
        person.setEnrollmentDate(enrollmentDate);

        LocalDate coverageStartDate = enrollmentDate.plusDays(random.nextInt(45) + 15);
        person.setCoverageStartDate(coverageStartDate);

        // 80% have no end date, 20% have end date
        if (random.nextDouble() < 0.2) {
            LocalDate coverageEndDate = coverageStartDate.plusMonths(random.nextInt(12) + 1);
            person.setCoverageEndDate(coverageEndDate);
        }

        person.setEmploymentStatus("Active");

        // Generate 1-3 coverages
        int coverageCount = random.nextInt(3) + 1;
        for (int i = 0; i < coverageCount; i++) {
            Coverage coverage = generateCoverage(i);
            person.addCoverage(coverage);
        }

        // Generate 0-3 dependents
        int dependentCount = random.nextInt(4);
        for (int i = 0; i < dependentCount; i++) {
            Dependent dependent = generateDependent(person, i);
            person.addDependent(dependent);
        }

        return person;
    }

    /**
     * Generates a dependent for the given person
     *
     * @param primary The primary person this dependent belongs to
     * @param index   Index of the dependent (used to determine relationship)
     * @return A randomly generated dependent
     */
    public static Dependent generateDependent(Person primary, int index) {
        Dependent dependent = new Dependent();

        // Set identification
        dependent.setId(faker.idNumber().valid());
        dependent.setSsn(faker.idNumber().ssnValid().replace("-", ""));

        // Set relationship and demographics accordingly
        if (index == 0 && random.nextDouble() < 0.7) {
            // Spouse (70% chance for first dependent)
            dependent.setRelationshipCode("01");

            dependent.setFirstName(faker.name().firstName());
            dependent.setMiddleName(random.nextBoolean() ? faker.name().firstName() : null);
            dependent.setLastName(primary.getLastName());
            dependent.setGender(primary.getGender().equals("M") ? "F" : "M"); // Opposite gender

            dependent.setBirthDate(
                    faker.date().birthday(18, 65).toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
        } else {
            // Child
            dependent.setRelationshipCode("19");

            dependent.setFirstName(faker.name().firstName());
            dependent.setMiddleName(random.nextBoolean() ? faker.name().firstName() : null);
            dependent.setLastName(primary.getLastName());
            dependent.setGender(random.nextBoolean() ? "M" : "F");

            dependent.setBirthDate(
                    faker.date().birthday(1, 26).toInstant() // Up to 26 years old (for college coverage)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
        }

        // Set enrollment dates (same as primary)
        dependent.setEnrollmentDate(primary.getEnrollmentDate());
        dependent.setCoverageStartDate(primary.getCoverageStartDate());
        dependent.setCoverageEndDate(primary.getCoverageEndDate());

        // Apply primary's coverages to dependent
        for (Coverage primaryCoverage : primary.getCoverages()) {
            // Clone coverage but adjust for dependent
            Coverage dependentCoverage = new Coverage();
            dependentCoverage.setCoverageId(primaryCoverage.getCoverageId());
            dependentCoverage.setPlanId(primaryCoverage.getPlanId());
            dependentCoverage.setGroupId(primaryCoverage.getGroupId());
            dependentCoverage.setCoverageType(primaryCoverage.getCoverageType());
            dependentCoverage.setInsuranceType(primaryCoverage.getInsuranceType());
            dependentCoverage.setCoverageLevel("Dependent");

            // 5% chance for a dependent to have Medicare (e.g., disabled dependent)
            if (random.nextDouble() < 0.05) {
                dependentCoverage.setHasMedicare(true);
                dependentCoverage.setMedicarePartANumber(faker.number().digits(10));
                dependentCoverage.setMedicarePartAEffectiveDate(
                        faker.date().past(365, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                );

                if (random.nextBoolean()) {
                    dependentCoverage.setMedicarePartBNumber(faker.number().digits(10));
                    dependentCoverage.setMedicarePartBEffectiveDate(
                            dependentCoverage.getMedicarePartAEffectiveDate().plusDays(random.nextInt(90))
                    );
                }
            }

            dependent.addCoverage(dependentCoverage);
        }

        return dependent;
    }

    /**
     * Generates coverage information
     *
     * @param index Index used to determine coverage type
     * @return A randomly generated coverage
     */
    public static Coverage generateCoverage(int index) {
        Coverage coverage = new Coverage();

        String[] coverageTypes = {"Medical", "Dental", "Vision"};
        String coverageType = index < coverageTypes.length ? coverageTypes[index] : coverageTypes[0];

        coverage.setCoverageId("COV" + faker.number().digits(6));
        coverage.setPlanId("PLAN-" + coverageType + "-" + faker.letterify("??") + faker.number().digits(3));
        coverage.setGroupId("GRP" + faker.number().digits(6));
        coverage.setCoverageType(coverageType);

        // Insurance type based on coverage type
        if (coverageType.equals("Medical")) {
            String[] insuranceTypes = {"HMO", "PPO", "EPO", "POS", "HDHP"};
            coverage.setInsuranceType(insuranceTypes[random.nextInt(insuranceTypes.length)]);
        } else {
            coverage.setInsuranceType("Standard");
        }

        String[] coverageLevels = {"Individual", "Employee+Spouse", "Employee+Child", "Family"};
        coverage.setCoverageLevel(coverageLevels[random.nextInt(coverageLevels.length)]);

        // Medicare - 10% chance for primary to have Medicare
        if (random.nextDouble() < 0.1) {
            coverage.setHasMedicare(true);
            coverage.setMedicarePartANumber(faker.number().digits(10));
            coverage.setMedicarePartAEffectiveDate(
                    faker.date().past(365, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );

            if (random.nextBoolean()) {
                coverage.setMedicarePartBNumber(faker.number().digits(10));
                coverage.setMedicarePartBEffectiveDate(
                        coverage.getMedicarePartAEffectiveDate().plusDays(random.nextInt(90))
                );
            }
        }

        // Financial details
        switch (coverageType) {
            case "Medical":
                coverage.setMonthlyPremium(500.0 + random.nextDouble() * 700.0);
                break;
            case "Dental":
                coverage.setMonthlyPremium(30.0 + random.nextDouble() * 50.0);
                break;
            case "Vision":
                coverage.setMonthlyPremium(10.0 + random.nextDouble() * 25.0);
                break;
        }

        double employerContribution = coverage.getMonthlyPremium() * (0.6 + random.nextDouble() * 0.3);
        coverage.setEmployerContribution(Math.round(employerContribution * 100) / 100.0);
        coverage.setEmployeeContribution(Math.round((coverage.getMonthlyPremium() - employerContribution) * 100) / 100.0);

        return coverage;
    }

    /**
     * Generates a predetermined sample person for testing
     *
     * @return A person with predetermined test data
     */
    public static Person generateSamplePerson() {
        Person person = new Person();

        // Set predetermined test data
        person.setId("123456");
        person.setSsn("123456789");
        person.setEmployeeId("EMP123456");
        person.setFirstName("John");
        person.setMiddleName("A");
        person.setLastName("Doe");
        person.setBirthDate(LocalDate.of(1980, 1, 15));
        person.setGender("M");

        Address address = new Address();
        address.setAddressLine1("123 Main St");
        address.setCity("Lansing");
        address.setState("MI");
        address.setZipCode("48933");
        address.setCountry("USA");
        person.setAddress(address);

        person.setPhoneNumber("555-123-4567");
        person.setEmail("john.doe@example.com");
        person.setHireDate(LocalDate.of(2022, 1, 15));
        person.setEnrollmentDate(LocalDate.of(2022, 2, 1));
        person.setCoverageStartDate(LocalDate.of(2022, 3, 1));
        person.setEmploymentStatus("Active");

        // Add medical coverage
        Coverage medicalCoverage = new Coverage();
        medicalCoverage.setCoverageId("COV123456");
        medicalCoverage.setPlanId("PLAN-Medical-A123");
        medicalCoverage.setGroupId("GRP123456");
        medicalCoverage.setCoverageType("Medical");
        medicalCoverage.setInsuranceType("PPO");
        medicalCoverage.setCoverageLevel("Family");
        medicalCoverage.setMonthlyPremium(800.0);
        medicalCoverage.setEmployerContribution(600.0);
        medicalCoverage.setEmployeeContribution(200.0);
        person.addCoverage(medicalCoverage);

        // Add dependent (spouse)
        Dependent spouse = new Dependent();
        spouse.setId("789012");
        spouse.setSsn("987654321");
        spouse.setFirstName("Jane");
        spouse.setLastName("Doe");
        spouse.setBirthDate(LocalDate.of(1982, 6, 10));
        spouse.setGender("F");
        spouse.setRelationshipCode("01"); // Spouse
        spouse.setEnrollmentDate(person.getEnrollmentDate());
        spouse.setCoverageStartDate(person.getCoverageStartDate());

        // Add same coverage for spouse
        Coverage spouseCoverage = new Coverage();
        spouseCoverage.setCoverageId(medicalCoverage.getCoverageId());
        spouseCoverage.setPlanId(medicalCoverage.getPlanId());
        spouseCoverage.setGroupId(medicalCoverage.getGroupId());
        spouseCoverage.setCoverageType(medicalCoverage.getCoverageType());
        spouseCoverage.setInsuranceType(medicalCoverage.getInsuranceType());
        spouseCoverage.setCoverageLevel("Dependent");
        spouse.addCoverage(spouseCoverage);

        person.addDependent(spouse);

        return person;
    }
}