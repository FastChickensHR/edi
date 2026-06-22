/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.fakeData;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.BaseMember;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.GenderCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.enrollment.SubscriberEnrollment;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import com.fastChickensHR.edi.x834.x834Context;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Fluent generator that produces a fully-populated subscriber {@link Member}, including
 * optional spouse and children as {@link DependentMember}s.
 * <p>
 * All fields on {@link BaseMember} are populated with realistic fake values:
 * name, gender, date of birth, SSN-based member id, address, phone and email.
 */
public final class MemberGenerator {

    private final TestDataFaker parent;
    private final Faker faker;
    private final Random random;
    private final x834Context context;

    private boolean withSpouse;
    private int childCount;
    private GenderCode forcedGender;
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
        if (count < 0) {
            throw new IllegalArgumentException("child count must be >= 0");
        }
        this.childCount = count;
        return this;
    }

    /** Forces the generated subscriber's gender. */
    public MemberGenerator withGender(GenderCode gender) {
        this.forcedGender = gender;
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
            this.coverageLines.add(line);
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

    /** Builds a new subscriber {@link Member}. */
    public Member build() {
        Member member = new Member(context);
        GenderCode gender = forcedGender != null ? forcedGender : randomAdultGender();
        populateCommon(member, gender, adultBirthDate());
        member.setRelationshipCode(IndividualRelationshipCode.EMPLOYEE);
        member.setSubscriberNumber(faker.numerify("##########"));
        member.setPolicyNumber(faker.bothify("POL-#####"));

        if (withSpouse) {
            DependentMember spouse = new DependentMember(context);
            GenderCode spouseGender =
                    gender == GenderCode.MALE ? GenderCode.FEMALE : GenderCode.MALE;
            populateCommon(spouse, spouseGender, adultBirthDate());
            spouse.setRelationshipCode(IndividualRelationshipCode.SPOUSE);
            spouse.setLastName(member.getLastName());
            spouse.setAddressLine1(member.getAddressLine1());
            spouse.setAddressLine2(member.getAddressLine2());
            spouse.setCity(member.getCity());
            spouse.setState(member.getState());
            spouse.setZipCode(member.getZipCode());
            spouse.setPrimaryMember(member);
            member.addDependent(spouse);
        }

        for (int i = 0; i < childCount; i++) {
            DependentMember child = new DependentMember(context);
            populateCommon(child, randomAnyGender(), childBirthDate());
            child.setRelationshipCode(IndividualRelationshipCode.CHILD);
            child.setLastName(member.getLastName());
            child.setAddressLine1(member.getAddressLine1());
            child.setAddressLine2(member.getAddressLine2());
            child.setCity(member.getCity());
            child.setState(member.getState());
            child.setZipCode(member.getZipCode());
            child.setPrimaryMember(member);
            member.addDependent(child);
        }

        return member;
    }

    private void populateCommon(BaseMember m, GenderCode gender, LocalDateTime dob) {
        m.setFirstName(faker.name().firstName());
        m.setLastName(faker.name().lastName());
        m.setMiddleName(String.valueOf(faker.name().firstName().charAt(0)));
        m.setBirthDate(dob);
        m.setGender(gender.getCode());
        m.setMemberIndicator(MemberIndicator.INSURED);
        m.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        m.setMemberId(faker.numerify("#########"));
        m.setMemberIdQualifier("0F"); // Subscriber Number
        m.setAddressLine1(faker.address().streetAddress());
        if (random.nextInt(4) == 0) {
            m.setAddressLine2(faker.address().secondaryAddress());
        }
        m.setCity(faker.address().city());
        m.setState(faker.address().stateAbbr());
        m.setZipCode(faker.address().zipCode());
        m.setPhoneNumber(faker.phoneNumber().cellPhone());
        m.setEmail(faker.internet().emailAddress());
        LocalDateTime enroll = LocalDateTime.now().minusDays(random.nextInt(365));
        m.setEnrollmentDate(enroll);
        m.setCoverageStartDate(enroll);
    }

    private GenderCode randomAdultGender() {
        return random.nextBoolean() ? GenderCode.MALE : GenderCode.FEMALE;
    }

    private GenderCode randomAnyGender() {
        int r = random.nextInt(10);
        if (r < 5) return GenderCode.MALE;
        if (r < 10) return GenderCode.FEMALE;
        return GenderCode.UNKNOWN;
    }

    private LocalDateTime adultBirthDate() {
        return faker.timeAndDate().birthday(21, 65)
                .atStartOfDay(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private LocalDateTime childBirthDate() {
        return faker.timeAndDate().birthday(0, 20)
                .atStartOfDay(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
