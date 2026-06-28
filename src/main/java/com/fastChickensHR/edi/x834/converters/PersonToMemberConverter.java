/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.converters;

import com.fastChickensHR.edi.domain.Dependent;
import com.fastChickensHR.edi.domain.Person;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;

/**
 * Converts a domain {@link Person} into an X12 834 {@link Member}.
 * <p>
 * Handles all structural type conversions (LocalDate → LocalDateTime,
 * {@link com.fastChickensHR.edi.domain.Address} → flat fields on
 * {@link com.fastChickensHR.edi.x834.loop2000.BaseMember}). The caller supplies
 * an {@link EnrollmentContext} for plan-level configuration that the domain model
 * intentionally does not carry.
 * <p>
 * Each domain {@link Dependent} on the person is automatically converted to a
 * {@link com.fastChickensHR.edi.x834.loop2000.DependentMember} using the same
 * enrollment context.
 */
public final class PersonToMemberConverter {

    private PersonToMemberConverter() {
    }

    /**
     * Converts a domain {@link Person} (and all their dependents) to a
     * fully-populated X12 834 {@link Member}.
     *
     * @param person the domain person to convert
     * @param ctx    enrollment-level configuration
     * @return a fully populated {@link Member} with dependents attached
     */
    public static Member convert(Person person, EnrollmentContext ctx) {
        Member member = new Member();

        member.setMemberId(person.getEmployeeId());
        member.setMemberIdQualifier(ctx.getMemberIdQualifier());
        member.setSubscriberNumber(deriveSubscriberNumber(person, ctx));
        member.setPolicyNumber(ctx.getPolicyNumber());

        member.setFirstName(person.getFirstName());
        member.setLastName(person.getLastName());
        member.setMiddleName(person.getMiddleName());
        member.setGender(person.getGender());

        if (person.getBirthDate() != null) {
            member.setBirthDate(person.getBirthDate().atStartOfDay());
        }

        member.setRelationshipCode(IndividualRelationshipCode.EMPLOYEE);
        member.setMemberIndicator(ctx.getMemberIndicator());
        member.setMaintenanceTypeCode(ctx.getMaintenanceTypeCode());

        member.setEnrollmentDate(
                person.getEnrollmentDate() != null
                        ? person.getEnrollmentDate().atStartOfDay()
                        : ctx.getEnrollmentDate());

        member.setCoverageStartDate(
                person.getCoverageStartDate() != null
                        ? person.getCoverageStartDate().atStartOfDay()
                        : ctx.getCoverageStartDate());

        if (person.getCoverageEndDate() != null) {
            member.setCoverageEndDate(person.getCoverageEndDate().atStartOfDay());
        } else if (ctx.getCoverageEndDate() != null) {
            member.setCoverageEndDate(ctx.getCoverageEndDate());
        }

        if (person.getAddress() != null) {
            member.setAddressLine1(person.getAddress().getAddressLine1());
            member.setAddressLine2(person.getAddress().getAddressLine2());
            member.setCity(person.getAddress().getCity());
            member.setState(person.getAddress().getState());
            member.setZipCode(person.getAddress().getZipCode());
        }

        member.setPhoneNumber(person.getPhoneNumber());
        member.setEmail(person.getEmail());

        for (Dependent dependent : person.getDependents()) {
            member.addDependent(DependentToMemberConverter.convert(dependent, member, ctx));
        }

        return member;
    }

    private static String deriveSubscriberNumber(Person person, EnrollmentContext ctx) {
        if (ctx.getSubscriberNumber() != null) {
            return ctx.getSubscriberNumber();
        }
        String employeeId = person.getEmployeeId();
        if (employeeId == null) {
            return null;
        }
        return employeeId.length() >= 6 ? employeeId.substring(0, 6) : employeeId;
    }
}
