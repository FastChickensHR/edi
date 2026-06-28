/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.fakeData;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.domain.Person;
import com.fastChickensHR.edi.x834.converters.EnrollmentContext;
import com.fastChickensHR.edi.x834.converters.PersonToMemberConverter;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.enrollment.SubscriberEnrollment;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import com.fastChickensHR.edi.x834.x834Context;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Fluent generator that produces a fully-populated subscriber {@link Member}, including
 * optional spouse and children as dependents.
 * <p>
 * Internally generates a domain {@link Person} via {@link PersonGenerator} (using
 * census-calibrated distributions) and converts it to an X12 834 {@link Member}
 * via {@link PersonToMemberConverter}. This keeps the two layers properly decoupled
 * while ensuring the fake-data pipeline flows through the same converter used in
 * production.
 */
public final class MemberGenerator {

    private final TestDataFaker parent;
    private final Faker faker;
    private final Random random;
    private final x834Context context;

    private boolean withSpouse;
    private int childCount;
    private List<InsuranceLineCode> coverageLines;

    MemberGenerator(TestDataFaker parent, x834Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.parent = parent;
        this.faker = parent.faker();
        this.random = parent.random();
        this.context = context;
    }

    /** Adds a spouse dependent to the generated subscriber. */
    public MemberGenerator withSpouse() {
        this.withSpouse = true;
        return this;
    }

    /** Adds {@code count} child dependents to the generated subscriber. */
    public MemberGenerator withChildren(int count) {
        if (count < 0) throw new IllegalArgumentException("child count must be >= 0");
        this.childCount = count;
        return this;
    }

    /**
     * Specifies the {@link InsuranceLineCode}s for which {@link HealthCoverage} (HD)
     * segments should be generated when calling {@link #buildEmployee()}.
     * If never called, a default set of Health/Dental/Vision is generated.
     */
    public MemberGenerator withCoverageLines(InsuranceLineCode... lines) {
        this.coverageLines = new ArrayList<>();
        for (InsuranceLineCode line : lines) {
            coverageLines.add(line);
        }
        return this;
    }

    /**
     * Builds a new subscriber {@link Member} together with the matching
     * {@link HealthCoverage} segments (Health/Dental/Vision by default, or
     * the lines configured with {@link #withCoverageLines(InsuranceLineCode...)}).
     */
    public SubscriberEnrollment buildEmployee() throws ValidationException {
        Member member = build();
        List<InsuranceLineCode> lines = coverageLines != null
                ? coverageLines
                : List.of(InsuranceLineCode.HEALTH,
                          InsuranceLineCode.DENTAL,
                          InsuranceLineCode.VISION_OPTICAL);
        CoverageLevelCode level = deriveCoverageLevel(member);
        List<HealthCoverage> coverages = new ArrayList<>(lines.size());
        for (InsuranceLineCode line : lines) {
            coverages.add(new HealthCoverageGenerator(parent)
                    .withInsuranceLine(line)
                    .withCoverageLevel(level)
                    .build());
        }
        return new SubscriberEnrollment(member, coverages);
    }

    /** Builds a new subscriber {@link Member}. */
    public Member build() {
        PersonGenerator personGen = new PersonGenerator(parent)
                .withMarriedRate(withSpouse ? 1.0 : 0.0)
                .withChildrenWeights(buildChildWeights());

        Person person = personGen.build();

        EnrollmentContext ctx = EnrollmentContext.builder()
                .policyNumber(faker.bothify("POL-#####"))
                .memberIdQualifier("0F")
                .memberIndicator(MemberIndicator.INSURED)
                .maintenanceTypeCode(MaintenanceTypeCode.ADDITION)
                .enrollmentDate(LocalDateTime.now().minusDays(random.nextInt(365)))
                .coverageStartDate(LocalDateTime.now().minusDays(random.nextInt(365)))
                .build();

        return PersonToMemberConverter.convert(person, ctx);
    }

    private double[] buildChildWeights() {
        if (childCount == 0) {
            return new double[]{1.0, 0.0, 0.0, 0.0};
        }
        double[] weights = new double[]{0.0, 0.0, 0.0, 0.0};
        int idx = Math.min(childCount, 3);
        weights[idx] = 1.0;
        return weights;
    }

    private CoverageLevelCode deriveCoverageLevel(Member member) {
        boolean hasSpouse = member.getDependents().stream()
                .anyMatch(d -> d.getRelationshipCode() == IndividualRelationshipCode.SPOUSE);
        boolean hasChildren = member.getDependents().stream()
                .anyMatch(d -> d.getRelationshipCode() == IndividualRelationshipCode.CHILD);
        if (hasSpouse && hasChildren) return CoverageLevelCode.FAMILY;
        if (hasSpouse) return CoverageLevelCode.EMPLOYEE_AND_SPOUSE;
        if (hasChildren) return CoverageLevelCode.EMPLOYEE_AND_CHILDREN;
        return CoverageLevelCode.EMPLOYEE_ONLY;
    }
}
