/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member (employee/participant/person) in an 834 file.
 * May be a primary member (subscriber) or dependent.
 */
@Getter
@Setter
public class Member {
    private x834Context context;
    // Member identification
    private String memberId;
    private String memberIdQualifier;
    private String subscriberNumber;
    private String policyNumber;

    // Demographics
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDateTime birthDate;
    private String gender;

    // Enrollment details
    private MemberIndicator memberIndicator;
    private MaintenanceTypeCode maintenanceTypeCode;
    private LocalDateTime enrollmentDate;
    private LocalDateTime coverageStartDate;
    private LocalDateTime coverageEndDate;

    // Relationship
    private IndividualRelationshipCode relationshipCode;

    // Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

    // Contact info
    private String phoneNumber;
    private String email;

    // Dependents of this member
    private final List<Member> dependents = new ArrayList<>();

    /**
     * Adds a dependent to this member
     *
     * @param dependent The dependent member
     */
    public void addDependent(Member dependent) {
        dependents.add(dependent);
    }

    /**
     * Adds multiple dependents to this member
     *
     * @param dependentList List of dependent members
     * @return This member instance for method chaining
     */
    public Member addDependents(List<Member> dependentList) {
        dependentList.forEach(this::addDependent);
        return this;
    }

    /**
     * Validates this member has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    public void validate() throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (memberId == null || memberId.isEmpty()) {
            throw new ValidationException("Member ID is required");
        }

        if (memberIndicator == null) {
            throw new ValidationException("Member indicator is required");
        }

        if (maintenanceTypeCode == null || maintenanceTypeCode.getCode() == null) {
            throw new ValidationException("Maintenance type code is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Member validation failed: " + String.join("\n", errors));
        }

        for (Member dependent : dependents) {
            dependent.validate();
        }
    }

    /**
     * Generates all the segments for this member and its dependents
     *
     * @return List of segments in the correct order
     */
    public List<Segment> generateSegments() throws ValidationException {
        List<Segment> segments = new ArrayList<>();
            MemberLevelDetail memberLevelDetail = new MemberLevelDetail.Builder()
                    .setMaintenanceTypeCode(maintenanceTypeCode.getCode())
                    .setIndividualRelationshipCode(relationshipCode.getCode())
                    .setBenefitStatusCode(BenefitStatusCode.ACTIVE.getCode())
                    .setMemberIndicator(memberIndicator.getCode())
                    .build();
            segments.add(memberLevelDetail);

            if (policyNumber != null && !policyNumber.isEmpty()) {
                MemberPolicyNumber policyNumberSegment = new MemberPolicyNumber.Builder()
                        .setReferenceIdentification(policyNumber)
                        .build();
                segments.add(policyNumberSegment);
            }

            if (memberId != null && !memberId.isEmpty()) {
                MemberIdentificationNumber idNumberSegment = new MemberIdentificationNumber.Builder()
                        .setReferenceIdentification(memberId)
                        .setReferenceIdentificationQualifier(memberIdQualifier)
                        .build();
                segments.add(idNumberSegment);
            }

            if (subscriberNumber != null && !subscriberNumber.isEmpty()) {
                SubscriberNumber subscriberNumberSegment = new SubscriberNumber.Builder()
                        .setReferenceIdentification(subscriberNumber)
                        .build();
                segments.add(subscriberNumberSegment);
            }

            if (enrollmentDate != null || coverageStartDate != null || coverageEndDate != null) {
                MemberLevelDates.Builder datesBuilder = new MemberLevelDates.Builder(context);

                if (enrollmentDate != null) {
                    datesBuilder.setDateQualifier(MemberDateQualifier.COVERAGE_BEGIN);
                    datesBuilder.setDateTimePeriod(enrollmentDate);
                }

                if (coverageStartDate != null) {
                    datesBuilder.setDateQualifier(MemberDateQualifier.COVERAGE_BEGIN);
                    datesBuilder.setDateTimePeriod(coverageStartDate);
                }

                if (coverageEndDate != null) {
                    datesBuilder.setDateQualifier(MemberDateQualifier.COVERAGE_END);
                    datesBuilder.setDateTimePeriod(coverageEndDate);
                }

                segments.add(datesBuilder.build());
            }

            for (Member dependent : dependents) {
                segments.addAll(dependent.generateSegments());
            }


        return segments;
    }
}