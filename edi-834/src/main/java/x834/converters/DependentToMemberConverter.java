/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.converters;

import com.fastChickensHR.edi.domain.Dependent;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;

import java.time.LocalDateTime;

/**
 * Converts a domain {@link Dependent} into an X12 834 {@link DependentMember}.
 * <p>
 * Handles all structural type conversions (LocalDate → LocalDateTime,
 * String relationship code → {@link IndividualRelationshipCode} enum). The
 * caller supplies an {@link EnrollmentContext} for plan-level configuration
 * that the domain model intentionally does not carry.
 */
public final class DependentToMemberConverter {

    private DependentToMemberConverter() {
    }

    /**
     * Converts a domain {@link Dependent} to a {@link DependentMember}.
     *
     * @param dependent the domain dependent to convert
     * @param primary   the already-converted primary member this dependent belongs to
     * @param ctx       enrollment-level configuration
     * @return a fully populated {@link DependentMember}
     */
    public static DependentMember convert(Dependent dependent, Member primary, EnrollmentContext ctx) {
        DependentMember member = new DependentMember();

        member.setMemberId(dependent.getId());
        member.setMemberIdQualifier(ctx.getMemberIdQualifier());
        member.setFirstName(dependent.getFirstName());
        member.setLastName(dependent.getLastName());
        member.setMiddleName(dependent.getMiddleName());
        member.setGender(dependent.getGender());

        if (dependent.getBirthDate() != null) {
            member.setBirthDate(dependent.getBirthDate().atStartOfDay());
        }

        member.setRelationshipCode(resolveRelationshipCode(dependent.getRelationshipCode()));

        member.setSubscriberNumber(primary.getSubscriberNumber());
        member.setPolicyNumber(ctx.getPolicyNumber());
        member.setMemberIndicator(ctx.getMemberIndicator());
        member.setMaintenanceTypeCode(ctx.getMaintenanceTypeCode());

        LocalDateTime enrollmentDate = dependent.getEnrollmentDate() != null
                ? dependent.getEnrollmentDate().atStartOfDay()
                : ctx.getEnrollmentDate();
        member.setEnrollmentDate(enrollmentDate);

        LocalDateTime coverageStartDate = dependent.getCoverageStartDate() != null
                ? dependent.getCoverageStartDate().atStartOfDay()
                : ctx.getCoverageStartDate();
        member.setCoverageStartDate(coverageStartDate);

        if (dependent.getCoverageEndDate() != null) {
            member.setCoverageEndDate(dependent.getCoverageEndDate().atStartOfDay());
        } else if (ctx.getCoverageEndDate() != null) {
            member.setCoverageEndDate(ctx.getCoverageEndDate());
        }

        // Dependents in the domain model have no address; inherit from the primary member.
        member.setAddressLine1(primary.getAddressLine1());
        member.setAddressLine2(primary.getAddressLine2());
        member.setCity(primary.getCity());
        member.setState(primary.getState());
        member.setZipCode(primary.getZipCode());

        member.setPrimaryMember(primary);

        return member;
    }

    private static IndividualRelationshipCode resolveRelationshipCode(String code) {
        if (code == null || code.isBlank()) {
            return IndividualRelationshipCode.OTHER_RELATED;
        }
        try {
            return IndividualRelationshipCode.fromString(code);
        } catch (IllegalArgumentException e) {
            return IndividualRelationshipCode.OTHER_RELATED;
        }
    }
}
