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
import com.fastChickensHR.edi.x834.loop2000.data.BenefitStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member (employee/participant/person) in an 834 file.
 * May be a primary member (subscriber) or dependent.
 */
@Getter
@Setter
public class Member extends BaseMember {
    private final List<DependentMember> dependents = new ArrayList<>();

    /**
     * Creates a new Member with the specified context
     *
     * @param context The 834 context to use for this member
     * @throws IllegalArgumentException if context is null
     */
    public Member(x834Context context) {
        super(context);
    }

    /**
     * Adds a dependent to this member
     *
     * @param dependent The dependent member
     */
    public void addDependent(DependentMember dependent) {
        dependents.add(dependent);
    }

    /**
     * Validates this member has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    @Override
    public void validate() throws ValidationException {
        // Implementation omitted for shortness
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

        for (DependentMember dependent : dependents) {
            segments.addAll(dependent.generateSegments());
        }

        return segments;
    }
}