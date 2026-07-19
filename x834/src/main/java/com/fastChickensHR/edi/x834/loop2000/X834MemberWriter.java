/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.dates.DateFormatter;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.loop2000.data.BenefitStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberDemographics;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberName;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberResidenceCityStateZipCode;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberResidenceStreetAddress;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingAddress;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingCityStateZipCode;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingStreetAddress;

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
    private final X834Context context;

    /**
     * @param context The 834 context to use when emitting segments.
     * @throws IllegalArgumentException if {@code context} is null
     */
    public X834MemberWriter(X834Context context) {
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

        // Loop 2100A (member detail): NM1 name, N3/N4 residence address, DMG demographics —
        // in the order required by the 834 spec. Each is emitted only when its source data is
        // present, so a member carrying only INS-level data renders exactly as before.
        appendMemberName(segments, member);
        appendResidenceAddress(segments, member);
        appendDemographics(segments, member);

        // Loop 2100C (member mailing address), emitted after the 2100A block when the member
        // carries a distinct mailing address.
        appendMailingAddress(segments, member);

        // Loop 2300: this member's own trailing segments (health coverage HD, REF extensions).
        // Emitting them here keeps a member's coverage nested inside its own loop, before any
        // dependent's loop begins.
        segments.addAll(member.getAdditionalSegments());
    }

    /** Loop 2100A NM1 (member name). Emitted when a last name is present. */
    private void appendMemberName(List<Segment> segments, BaseMember member) throws ValidationException {
        if (isBlank(member.getLastName())) {
            return;
        }
        MemberName.Builder name = MemberName.builder()
                .setLastName(member.getLastName())
                .setFirstName(emptyToNull(member.getFirstName()))
                .setMiddleName(emptyToNull(member.getMiddleName()));
        // NM108/NM109 (e.g. 34 + SSN) — set together or not at all (MemberName enforces the pairing).
        String idQualifier = emptyToNull(member.getNameIdQualifier());
        String id = emptyToNull(member.getNameId());
        if (idQualifier != null && id != null) {
            name.setIdentificationCodeQualifier(idQualifier).setIdentificationCode(id);
        }
        segments.add(name.build());
    }

    /**
     * Loop 2100A N3/N4 (member residence address). The N3 is emitted when a street address is
     * present; the N4 requires city, state and postal code together (per the segment's own
     * validation), so a partial address emits only the N3.
     */
    private void appendResidenceAddress(List<Segment> segments, BaseMember member) throws ValidationException {
        if (!isBlank(member.getAddressLine1())) {
            segments.add(MemberResidenceStreetAddress.builder()
                    .setAddressLine1(member.getAddressLine1())
                    .setAddressLine2(emptyToNull(member.getAddressLine2()))
                    .build());
        }
        if (!isBlank(member.getCity()) && !isBlank(member.getState()) && !isBlank(member.getZipCode())) {
            segments.add(MemberResidenceCityStateZipCode.builder()
                    .setCityName(member.getCity())
                    .setStateOrProvinceCode(member.getState())
                    .setPostalCode(member.getZipCode())
                    .build());
        }
    }

    /**
     * Loop 2100C (member mailing address): NM1*31 postal-address marker, then N3/N4. Emitted only
     * when the member carries a {@link AddressType#MAILING} address with a street line; the N4 is
     * added when city/state/zip are all present.
     */
    private void appendMailingAddress(List<Segment> segments, BaseMember member) throws ValidationException {
        Address mailing = member.getAddress(AddressType.MAILING).orElse(null);
        if (mailing == null || !mailing.hasStreet()) {
            return;
        }
        segments.add(MemberMailingAddress.builder().build());
        segments.add(MemberMailingStreetAddress.builder()
                .setAddressLine1(mailing.getLine1())
                .setAddressLine2(emptyToNull(mailing.getLine2()))
                .build());
        if (mailing.hasCityStateZip()) {
            segments.add(MemberMailingCityStateZipCode.builder()
                    .setCityName(mailing.getCity())
                    .setStateOrProvinceCode(mailing.getState())
                    .setPostalCode(mailing.getZipCode())
                    .build());
        }
    }

    /** Loop 2100A DMG (member demographics). Emitted when a birth date is present. */
    private void appendDemographics(List<Segment> segments, BaseMember member) throws ValidationException {
        if (member.getBirthDate() == null) {
            return;
        }
        segments.add(new MemberDemographics.Builder()
                .setDateTimePeriodFormatQualifier(DateFormat.D8.getFormat())
                .setBirthDate(DateFormatter.formatDate(DateFormat.D8, member.getBirthDate()))
                .setGenderCode(emptyToNull(member.getGender()))
                .build());
    }

    private static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    private static String emptyToNull(String value) {
        return isBlank(value) ? null : value;
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
