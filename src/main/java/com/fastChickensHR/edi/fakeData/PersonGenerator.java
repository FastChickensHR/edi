/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.fakeData;

import com.fastChickensHR.edi.domain.Address;
import com.fastChickensHR.edi.domain.Dependent;
import com.fastChickensHR.edi.domain.Person;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Fluent generator that produces domain {@link Person} objects with family
 * structures calibrated to 2020 US Census / American Community Survey data.
 * <p>
 * Default household composition weights:
 * <ul>
 *   <li>48 % married (spouse present)</li>
 *   <li>57 % of households have no children under 18</li>
 *   <li>18 % have 1 child, 15 % have 2, 10 % have 3+</li>
 * </ul>
 *
 * <p>Generation order is intentionally inverted from the old approach:
 * <ol>
 *   <li>Draw household type (married vs single) from weighted distribution.</li>
 *   <li>Draw child count from weighted distribution.</li>
 *   <li>Generate the {@link Person} and {@link Dependent}s to match.</li>
 * </ol>
 * This means coverage level can be derived accurately from the actual family
 * structure rather than the other way around.
 *
 * <pre>{@code
 * Person person = TestDataFaker.withSeed(42L).person().build();
 * }</pre>
 */
public final class PersonGenerator {

    // 2020 ACS household type weights
    private static final double DEFAULT_MARRIED_RATE = 0.48;

    // 2020 ACS children-under-18 distribution weights: 0, 1, 2, 3+
    private static final double[] DEFAULT_CHILDREN_WEIGHTS = {0.57, 0.18, 0.15, 0.10};

    private final TestDataFaker parent;
    private final Faker faker;
    private final Random random;

    private double marriedRate = DEFAULT_MARRIED_RATE;
    private double[] childrenWeights = DEFAULT_CHILDREN_WEIGHTS;

    PersonGenerator(TestDataFaker parent) {
        this.parent = parent;
        this.faker = parent.faker();
        this.random = parent.random();
    }

    /** Override the probability (0.0–1.0) that the generated person has a spouse. */
    public PersonGenerator withMarriedRate(double rate) {
        if (rate < 0.0 || rate > 1.0) throw new IllegalArgumentException("marriedRate must be in [0,1]");
        this.marriedRate = rate;
        return this;
    }

    /**
     * Override the children-count distribution weights.
     * Must have exactly 4 elements representing probabilities for 0, 1, 2, and 3+
     * children respectively. Elements must sum to approximately 1.0.
     */
    public PersonGenerator withChildrenWeights(double[] weights) {
        if (weights == null || weights.length != 4) {
            throw new IllegalArgumentException("childrenWeights must have exactly 4 elements [0,1,2,3+]");
        }
        this.childrenWeights = weights.clone();
        return this;
    }

    /**
     * Generates a single {@link Person} with a census-calibrated family structure.
     */
    public Person build() {
        Person person = new Person();

        person.setId(faker.idNumber().valid());
        person.setSsn(faker.idNumber().ssnValid().replace("-", ""));
        person.setEmployeeId("EMP" + faker.number().digits(6));

        boolean isMale = random.nextBoolean();
        person.setGender(isMale ? "M" : "F");
        person.setFirstName(faker.name().firstName());
        person.setMiddleName(random.nextInt(3) != 0 ? String.valueOf(faker.name().firstName().charAt(0)) : null);
        person.setLastName(faker.name().lastName());

        person.setBirthDate(
                faker.date().birthday(21, 65).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        Address address = new Address();
        address.setAddressLine1(faker.address().streetAddress());
        address.setAddressLine2(random.nextInt(4) == 0 ? faker.address().secondaryAddress() : null);
        address.setCity(faker.address().city());
        address.setState(faker.address().stateAbbr());
        address.setZipCode(faker.address().zipCode());
        address.setCountry("USA");
        person.setAddress(address);

        person.setPhoneNumber(faker.phoneNumber().cellPhone());
        person.setEmail(faker.internet().emailAddress(
                person.getFirstName().toLowerCase() + "." + person.getLastName().toLowerCase()));

        LocalDate hireDate = faker.date().past(730, TimeUnit.DAYS)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        person.setHireDate(hireDate);

        LocalDate enrollmentDate = hireDate.plusDays(random.nextInt(30) + 1);
        person.setEnrollmentDate(enrollmentDate);

        LocalDate coverageStartDate = enrollmentDate.plusDays(random.nextInt(45) + 15);
        person.setCoverageStartDate(coverageStartDate);

        if (random.nextDouble() < 0.2) {
            person.setCoverageEndDate(coverageStartDate.plusMonths(random.nextInt(12) + 1));
        }

        person.setEmploymentStatus("Active");

        if (random.nextDouble() < marriedRate) {
            person.addDependent(buildSpouse(person));
        }

        int childCount = drawChildCount();
        for (int i = 0; i < childCount; i++) {
            person.addDependent(buildChild(person));
        }

        return person;
    }

    private Dependent buildSpouse(Person primary) {
        Dependent spouse = new Dependent();
        spouse.setId(faker.idNumber().valid());
        spouse.setSsn(faker.idNumber().ssnValid().replace("-", ""));
        spouse.setRelationshipCode("01"); // SPOUSE
        spouse.setGender("M".equals(primary.getGender()) ? "F" : "M");
        spouse.setFirstName(faker.name().firstName());
        spouse.setMiddleName(random.nextBoolean() ? String.valueOf(faker.name().firstName().charAt(0)) : null);
        spouse.setLastName(primary.getLastName());
        spouse.setBirthDate(
                faker.date().birthday(21, 65).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
        spouse.setEnrollmentDate(primary.getEnrollmentDate());
        spouse.setCoverageStartDate(primary.getCoverageStartDate());
        spouse.setCoverageEndDate(primary.getCoverageEndDate());
        return spouse;
    }

    private Dependent buildChild(Person primary) {
        Dependent child = new Dependent();
        child.setId(faker.idNumber().valid());
        child.setSsn(faker.idNumber().ssnValid().replace("-", ""));
        child.setRelationshipCode("19"); // CHILD
        child.setGender(random.nextBoolean() ? "M" : "F");
        child.setFirstName(faker.name().firstName());
        child.setMiddleName(random.nextBoolean() ? String.valueOf(faker.name().firstName().charAt(0)) : null);
        child.setLastName(primary.getLastName());
        child.setBirthDate(
                faker.date().birthday(0, 25).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
        child.setEnrollmentDate(primary.getEnrollmentDate());
        child.setCoverageStartDate(primary.getCoverageStartDate());
        child.setCoverageEndDate(primary.getCoverageEndDate());
        return child;
    }

    /**
     * Draws a child count (0, 1, 2, or 3) from the configured weighted distribution.
     * Weight index 3 represents "3 or more" — this implementation returns exactly 3.
     */
    private int drawChildCount() {
        double roll = random.nextDouble();
        double cumulative = 0.0;
        for (int i = 0; i < childrenWeights.length; i++) {
            cumulative += childrenWeights[i];
            if (roll < cumulative) {
                return i;
            }
        }
        return childrenWeights.length - 1;
    }
}
