/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.common.segments.Segment;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.BenefitStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializes {@link Member} (and its {@link DependentMember}s) into the
 * sequence of X12 834 segments that make up Loop 2000.
 * <p>
 * The X834-specific concerns (delimiters, date formatting, default
 * benefit status, segment ordering, etc.) live here rather than on the
 * domain objects themselves.
 */
public class X834MemberWriter {
    private final x834Context context;

    /**
     * @param context The 834 context to use when emitting segments.
     * @throws IllegalArgumentException if {@code context} is null
     */
    public X834MemberWriter(x834Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
    }

    /**
     * Generates all 834 segments for the given member and, recursively,
     * its dependents — in the order required by the 834 specification.
     *
     * @param member The (primary) member to serialize.
     * @return List of segments in the correct order.
     */
    public List<Segment> toSegments(Member member) throws ValidationException {
        List<Segment> segments = new ArrayList<>();
        appendMemberSegments(segments, member);
        for (DependentMember dependent : member.getDependents()) {
            appendMemberSegments(segments, dependent);
        }
        return segments;
    }

    private void appendMemberSegments(List<Segment> segments, BaseMember member) throws ValidationException {
        INSSegment insSegment = new INSSegment.Builder()
                .setMaintenanceTypeCode(member.getMaintenanceTypeCode().getCode())
                .setIndividualRelationshipCode(member.getRelationshipCode().getCode())
                .setBenefitStatusCode(BenefitStatusCode.ACTIVE.getCode())
                .setMemberIndicator(member.getMemberIndicator().getCode())
                .build();
        segments.add(insSegment);

        String policyNumber = member.getPolicyNumber();
        if (policyNumber != null && !policyNumber.isEmpty()) {
            segments.add(new MemberPolicyNumber.Builder()
                    .setReferenceIdentification(policyNumber)
                    .build());
        }

        String memberId = member.getMemberId();
        if (memberId != null && !memberId.isEmpty()) {
            segments.add(new MemberIdentificationNumber.Builder()
                    .setReferenceIdentification(memberId)
                    .setReferenceIdentificationQualifier(member.getMemberIdQualifier())
                    .build());
        }

        String subscriberNumber = member.getSubscriberNumber();
        if (subscriberNumber != null && !subscriberNumber.isEmpty()) {
            segments.add(new SubscriberNumber.Builder()
                    .setReferenceIdentification(subscriberNumber)
                    .build());
        }

        // Emit one DTP segment per non-null date so each date is preserved.
        addDateSegment(segments, MemberDateQualifier.COVERAGE_BEGIN, member.getEnrollmentDate());
        addDateSegment(segments, MemberDateQualifier.COVERAGE_BEGIN, member.getCoverageStartDate());
        addDateSegment(segments, MemberDateQualifier.COVERAGE_END, member.getCoverageEndDate());
    }

    private void addDateSegment(List<Segment> segments, MemberDateQualifier qualifier, LocalDateTime date)
            throws ValidationException {
        if (date == null) {
            return;
        }
        segments.add(new MemberLevelDates.Builder(context)
                .setDateQualifier(qualifier)
                .setDateTimePeriod(date)
                .build());
    }
}
