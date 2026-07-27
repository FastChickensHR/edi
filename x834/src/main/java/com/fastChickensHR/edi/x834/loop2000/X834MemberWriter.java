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
import com.fastChickensHR.edi.x834.segments.DTPSegment;
import com.fastChickensHR.edi.x834.segments.LESegment;
import com.fastChickensHR.edi.x834.segments.LSSegment;
import com.fastChickensHR.edi.x834.segments.LXSegment;
import com.fastChickensHR.edi.x834.segments.RefSegment;
import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.data.DateTimeQualifier;
import com.fastChickensHR.edi.x834.loop2000.data.BenefitStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberDateQualifier;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.HealthInformation;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Income;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Language;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunication;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunicationsNumbers;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberDemographics;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberHealthInformation;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberIncome;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberLanguage;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberName;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberResidenceCityStateZipCode;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberResidenceStreetAddress;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingAddress;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingCityStateZipCode;
import com.fastChickensHR.edi.x834.loop2000.loop2100C.MemberMailingStreetAddress;
import com.fastChickensHR.edi.x834.loop2000.loop2200.Disability;
import com.fastChickensHR.edi.x834.loop2000.loop2200.MemberDisability;
import com.fastChickensHR.edi.x834.loop2000.loop2300.HealthCoverage;
import com.fastChickensHR.edi.x834.loop2000.loop2310.Provider;
import com.fastChickensHR.edi.x834.loop2000.loop2310.ProviderChange;
import com.fastChickensHR.edi.x834.loop2000.loop2310.ProviderName;
import com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefits;
import com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefitsRelatedEntityName;
import com.fastChickensHR.edi.x834.loop2000.loop2320.MemberCoordinationOfBenefits;
import com.fastChickensHR.edi.x834.loop2000.loop2700.MemberReportingCategoryName;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;

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
        INSSegment.Builder ins = new INSSegment.Builder()
                .setMaintenanceTypeCode(member.getMaintenanceTypeCode().getCode())
                .setIndividualRelationshipCode(member.getRelationshipCode().getCode())
                .setBenefitStatusCode(BenefitStatusCode.ACTIVE.getCode())
                .setMemberIndicator(member.getMemberIndicator().getCode());
        // INS04 and INS08 are optional; each renders only when the member carries one, so a member
        // without them emits the same INS as before.
        if (member.getMaintenanceReasonCode() != null) {
            ins.setMaintenanceReasonCode(member.getMaintenanceReasonCode().getCode());
        }
        if (member.getEmploymentStatusCode() != null) {
            ins.setEmploymentStatusCode(member.getEmploymentStatusCode().getCode());
        }
        segments.add(ins.build());

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

        // Emit one DTP segment per non-null member-level date so each is preserved.
        // enrollmentDate → DTP*300 (Enrollment Signature Date), the member-level enrollment code
        // the 834 TR3 permits at Loop 2000 (grounded in edi #167). It is distinct from
        // coverageStartDate → DTP*356 (Eligibility Begin): the earlier mislabeling emitted both
        // under 356, so the enrollment DTP was suppressed until the correct qualifier was grounded.
        addDateSegment(segments, MemberDateQualifier.ENROLLMENT, member.getEnrollmentDate());
        addDateSegment(segments, MemberDateQualifier.COVERAGE_BEGIN, member.getCoverageStartDate());
        addDateSegment(segments, MemberDateQualifier.COVERAGE_END, member.getCoverageEndDate());

        // Loop 2100A (member detail): NM1 name, PER communications, N3/N4 residence address,
        // DMG demographics, ICM income — in the order required by the 834 spec. Each is emitted only
        // when its source data is present, so a member carrying only INS-level data renders as before.
        appendMemberName(segments, member);
        appendCommunications(segments, member);
        appendResidenceAddress(segments, member);
        appendDemographics(segments, member);
        appendIncome(segments, member);
        appendHealthInformation(segments, member);
        appendLanguages(segments, member);

        // Loop 2100C (member mailing address), emitted after the 2100A block when the member
        // carries a distinct mailing address.
        appendMailingAddress(segments, member);

        // Loop 2200 (disability), emitted after the 2100 loops and before the 2300 block.
        appendDisabilities(segments, member);

        // This member's own trailing segments (custom REF extensions), then its Loop 2300
        // coverage blocks. Emitting them here keeps a member's coverage nested inside its own
        // loop, before any dependent's loop begins.
        segments.addAll(member.getAdditionalSegments());
        appendHealthCoverages(segments, member);

        // Loop 2310 (provider), emitted after the 2300 block and before 2320.
        appendProviders(segments, member);

        // Loop 2320/2330 (coordination of benefits), emitted after the 2300 block and before 2700.
        appendCoordinationOfBenefits(segments, member);

        // Loop 2700/2710/2750 (member reporting categories), emitted after the 2300 block.
        appendReportingCategories(segments, member);
    }

    /**
     * Loop 2700/2710/2750 (member reporting categories): an {@code LS*2700} … {@code LE*2700}
     * block wrapping one {@code LX}/{@code N1*75}/{@code REF}(/{@code DTP}) occurrence per
     * {@link ReportingCategory}. The whole block is suppressed when the member carries none, and
     * the {@code LX} assigned number is counted over the emitted occurrences (1-based).
     */
    private void appendReportingCategories(List<Segment> segments, BaseMember member)
            throws ValidationException {
        List<ReportingCategory> categories = member.getReportingCategories();
        if (categories.isEmpty()) {
            return;
        }
        segments.add(LSSegment.builder().setLoopIdentifierCode(REPORTING_CATEGORY_LOOP_ID).build());
        int assignedNumber = 1;
        for (ReportingCategory category : categories) {
            segments.add(LXSegment.builder().setAssignedNumber(assignedNumber++).build());
            segments.add(MemberReportingCategoryName.builder()
                    .setReportingCategoryName(category.getName())
                    .build());
            segments.add(new RefSegment.Builder()
                    .setReferenceIdentificationQualifier(category.getReferenceQualifier())
                    .setReferenceIdentification(category.getValue())
                    .build());
            if (category.getDate() != null && category.getDateQualifier() != null) {
                segments.add(new DTPSegment.Builder()
                        .setDateTimeQualifier(category.getDateQualifier())
                        .setDateTimeFormat(DateFormat.D8)
                        .setDateTimePeriod(category.getDate(), DateFormat.D8)
                        .build());
            }
        }
        segments.add(LESegment.builder().setLoopIdentifierCode(REPORTING_CATEGORY_LOOP_ID).build());
    }

    /** LS01/LE01 loop identifier for the Member Reporting Categories loop (2700). */
    private static final String REPORTING_CATEGORY_LOOP_ID = "2700";

    /**
     * Loop 2310 (provider): per provider the member is assigned to, an {@code LX} assigned number,
     * the {@code NM1} naming them, and — when the assignment is being changed rather than merely
     * stated — a {@code PLA} saying what is happening. Emitted after the 2300 coverage segments and
     * before the 2320 block, and suppressed entirely when the member has no provider.
     * <p>
     * The {@code LX} counter is assigned at render time over the emitted occurrences (1-based),
     * matching how the 2700 block numbers its own. Thirty occurrences are permitted; a member
     * carrying more is rejected rather than truncated.
     * <p>
     * The 2310 {@code N3}/{@code N4}/{@code PER} segments are not modelled — no profiled carrier
     * asks for a provider's address or phone, only their name and identifier.
     */
    private void appendProviders(List<Segment> segments, BaseMember member) throws ValidationException {
        List<Provider> providers = member.getProviders();
        if (providers.isEmpty()) {
            return;
        }
        if (providers.size() > Provider.MAX_PER_MEMBER) {
            throw new ValidationException("A member carries at most " + Provider.MAX_PER_MEMBER
                    + " provider loops (2310); got " + providers.size());
        }
        int assignedNumber = 1;
        for (Provider provider : providers) {
            segments.add(LXSegment.builder().setAssignedNumber(assignedNumber++).build());
            segments.add(ProviderName.builder()
                    .setLastName(provider.getLastName())
                    .setFirstName(emptyToNull(provider.getFirstName()))
                    .setMiddleName(emptyToNull(provider.getMiddleName()))
                    .setProviderIdentification(provider.getIdentifierQualifier(),
                            emptyToNull(provider.getIdentifier()))
                    .build());
            appendProviderChange(segments, provider);
        }
    }

    /**
     * The 2310 {@code PLA}, emitted only when the assignment is actually changing. A change with no
     * effective date is rejected: PLA03 is mandatory, and "this provider changed, at no particular
     * time" is not something a receiver can apply.
     */
    private void appendProviderChange(List<Segment> segments, Provider provider) throws ValidationException {
        if (provider.getChangeAction() == null) {
            return;
        }
        if (provider.getChangeDate() == null) {
            throw new ValidationException(
                    "A provider change (PLA01) requires its effective date (PLA03)");
        }
        segments.add(ProviderChange.builder()
                .setActionCode(provider.getChangeAction())
                .setDate(DateFormatter.formatDate(DateFormat.D8, provider.getChangeDate()))
                .setMaintenanceReasonCode(provider.getChangeReason())
                .build());
    }

    /**
     * Loop 2320/2330 (coordination of benefits): per other plan the member holds, a {@code COB}
     * followed by its optional group-number {@code REF}, its coordination {@code DTP*344}/
     * {@code DTP*345} dates, and the 2330 {@code NM1} naming that plan. Emitted after the 2300
     * coverage segments and before the 2700 block, and suppressed entirely when the member has no
     * other coverage.
     * <p>
     * The 834 permits five occurrences. A member carrying more is rejected rather than truncated —
     * dropping someone's other insurance would produce a file that reads as though they simply do
     * not have it.
     */
    private void appendCoordinationOfBenefits(List<Segment> segments, BaseMember member)
            throws ValidationException {
        List<CoordinationOfBenefits> others = member.getCoordinationOfBenefits();
        if (others.isEmpty()) {
            return;
        }
        if (others.size() > CoordinationOfBenefits.MAX_PER_MEMBER) {
            throw new ValidationException("A member carries at most " + CoordinationOfBenefits.MAX_PER_MEMBER
                    + " coordination-of-benefits loops (2320); got " + others.size());
        }
        for (CoordinationOfBenefits other : others) {
            segments.add(MemberCoordinationOfBenefits.builder()
                    .setPayerResponsibility(other.getPayerResponsibility())
                    .setReferenceIdentification(emptyToNull(other.getPolicyIdentifier()))
                    .setCoordinationOfBenefitsCode(other.getBenefitsCoordination())
                    .build());
            if (!isBlank(other.getGroupNumber())) {
                segments.add(new RefSegment.Builder()
                        .setReferenceIdentificationQualifier(other.getGroupNumberQualifier())
                        .setReferenceIdentification(other.getGroupNumber())
                        .build());
            }
            addQualifiedDate(segments, DateTimeQualifier.COORDINATION_OF_BENEFITS_BEGIN, other.getBeginDate());
            addQualifiedDate(segments, DateTimeQualifier.COORDINATION_OF_BENEFITS_END, other.getEndDate());
            if (!isBlank(other.getRelatedEntityName())) {
                segments.add(CoordinationOfBenefitsRelatedEntityName.builder()
                        .setRelatedEntityName(other.getRelatedEntityName())
                        .build());
            }
        }
    }

    /** A date under an explicit DTP qualifier, emitted only when present. */
    private void addQualifiedDate(List<Segment> segments, DateTimeQualifier qualifier, LocalDateTime date)
            throws ValidationException {
        if (date == null) {
            return;
        }
        segments.add(new DTPSegment.Builder()
                .setDateTimeQualifier(qualifier.getCode())
                .setDateTimeFormat(DateFormat.D8)
                .setDateTimePeriod(date, DateFormat.D8)
                .build());
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
     * Loop 2100A PER (member communications numbers): a single {@code PER*IP} carrying one
     * qualifier/number pair per channel the member has, emitted after the {@code NM1}. Suppressed
     * when the member has no way to be reached.
     * <p>
     * The member's explicit {@link MemberCommunication}s come first, in the order they were added.
     * The {@code phoneNumber} and {@code email} conveniences then contribute {@code HP} and
     * {@code EM} — but only when no explicit communication already claims that qualifier, so a
     * member given both a {@code phoneNumber} and an explicit work number does not emit the same
     * channel twice. Precedence is the explicit one's: it says which qualifier the caller meant.
     * <p>
     * A PER carries at most three pairs, and a member with more is rejected by the builder rather
     * than truncated — losing a member's fourth contact number silently is the class of bug this
     * whole segment exists to end.
     */
    private void appendCommunications(List<Segment> segments, BaseMember member) throws ValidationException {
        List<MemberCommunication> channels = new ArrayList<>();
        for (MemberCommunication communication : member.getCommunications()) {
            if (communication != null && communication.getQualifier() != null
                    && !isBlank(communication.getNumber())) {
                channels.add(communication);
            }
        }
        addConvenience(channels, CommunicationNumberQualifier.HOME_PHONE, member.getPhoneNumber());
        addConvenience(channels, CommunicationNumberQualifier.ELECTRONIC_MAIL, member.getEmail());

        if (channels.isEmpty()) {
            return;
        }
        MemberCommunicationsNumbers.Builder per = MemberCommunicationsNumbers.builder();
        for (MemberCommunication channel : channels) {
            per.addCommunicationNumber(channel.getQualifier(), channel.getNumber());
        }
        segments.add(per.build());
    }

    /** Adds a {@code phoneNumber}/{@code email} convenience unless an explicit channel claims its qualifier. */
    private static void addConvenience(List<MemberCommunication> channels,
                                       CommunicationNumberQualifier qualifier, String number) {
        if (isBlank(number)) {
            return;
        }
        for (MemberCommunication channel : channels) {
            if (channel.getQualifier() == qualifier) {
                return;
            }
        }
        channels.add(new MemberCommunication(qualifier, number));
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

    /**
     * Loop 2100A ICM (member income), emitted after the DMG when the member carries one. The 834
     * sends income only when the sponsor's contract with the payer requires it, so absence is the
     * normal case.
     * <p>
     * ICM01 (frequency) and ICM02 (amount) are mandatory, so an income carrying only the later
     * elements is rejected rather than emitted with empty mandatory slots. This is the case a
     * carrier hits when it wants just the ICM04 location identifier — BCBS Kansas puts its
     * department number there — and it is a property of the 834 rather than a rule invented here.
     */
    private void appendIncome(List<Segment> segments, BaseMember member) throws ValidationException {
        Income income = member.getIncome();
        if (income == null) {
            return;
        }
        segments.add(MemberIncome.builder()
                .setFrequencyCode(income.getFrequency())
                .setMonetaryAmount(emptyToNull(income.getAmount()))
                .setQuantity(emptyToNull(income.getHours()))
                .setLocationIdentifier(emptyToNull(income.getLocationIdentifier()))
                .setSalaryGrade(emptyToNull(income.getSalaryGrade()))
                .setCurrencyCode(emptyToNull(income.getCurrencyCode()))
                .build());
    }

    /**
     * Loop 2100A HLH (member health information), emitted after the ICM when the member carries it.
     * BCBSM notes the health-related code "may be required for specific employer groups", so
     * whether to send it is a config-time answer and absence is the normal case.
     */
    private void appendHealthInformation(List<Segment> segments, BaseMember member) throws ValidationException {
        HealthInformation health = member.getHealthInformation();
        if (health == null) {
            return;
        }
        segments.add(MemberHealthInformation.builder()
                .setHealthRelatedCode(health.getHealthRelatedCode())
                .setHeight(emptyToNull(health.getHeight()))
                .setCurrentWeight(emptyToNull(health.getCurrentWeight()))
                .setPreviousWeight(emptyToNull(health.getPreviousWeight()))
                .setDescription(emptyToNull(health.getDescription()))
                .build());
    }

    /**
     * Loop 2100A LUI (member language), emitted after the HLH — one per language the member uses,
     * in the order added. Suppressed when the member carries none.
     */
    private void appendLanguages(List<Segment> segments, BaseMember member) throws ValidationException {
        for (Language language : member.getLanguages()) {
            segments.add(MemberLanguage.builder()
                    .setLanguage(language.getCodeQualifier(), emptyToNull(language.getCode()))
                    .setDescription(emptyToNull(language.getDescription()))
                    .build());
        }
    }

    /**
     * Loop 2200 (disability): per disability, a {@code DSB} followed by its {@code DTP*360}/
     * {@code DTP*361} period dates. Emitted after the 2100 loops and before the 2300 coverage
     * segments, and suppressed when the member has none.
     * <p>
     * BCBSM asks for the period rather than the detail, but DSB01 is mandatory, so the dates cannot
     * travel alone — a sponsor sending a disability period must also say what kind of disability it
     * is. The guide itself is wrong on this point, reading "DTP01 … Start / DTP02 … End" when DTP02
     * is the date-format qualifier; two dates are two DTP segments, which is what this emits.
     */
    /**
     * Loop 2300 (health coverage): per coverage, an {@code HD} followed by its benefit-period
     * {@code DTP*348}/{@code DTP*349} dates. Emitted after the member's trailing segments and
     * before the 2310 block, and suppressed when the member has none. HD01 and HD03 are required
     * by the segment, so a coverage missing either fails generation.
     */
    private void appendHealthCoverages(List<Segment> segments, BaseMember member) throws ValidationException {
        for (HealthCoverage coverage : member.getHealthCoverages()) {
            segments.add(new HealthCoverageSegment(
                    coverage.getMaintenanceTypeCode(),
                    coverage.getInsuranceLineCode(),
                    emptyToNull(coverage.getPlanCoverageDescription()),
                    emptyToNull(coverage.getCoverageLevelCode())));
            addQualifiedDate(segments, DateTimeQualifier.BENEFIT_BEGIN, coverage.getStartDate());
            addQualifiedDate(segments, DateTimeQualifier.BENEFIT_END, coverage.getEndDate());
        }
    }

    private void appendDisabilities(List<Segment> segments, BaseMember member) throws ValidationException {
        for (Disability disability : member.getDisabilities()) {
            segments.add(MemberDisability.builder()
                    .setDisabilityTypeCode(disability.getType())
                    .setQuantity(emptyToNull(disability.getQuantity()))
                    .setOccupationCode(emptyToNull(disability.getOccupationCode()))
                    .setWorkIntensityCode(emptyToNull(disability.getWorkIntensityCode()))
                    .setProductOptionCode(emptyToNull(disability.getProductOptionCode()))
                    .setMonetaryAmount(emptyToNull(disability.getMonetaryAmount()))
                    .build());
            addQualifiedDate(segments, DateTimeQualifier.INITIAL_DISABILITY_PERIOD_START,
                    disability.getStartDate());
            addQualifiedDate(segments, DateTimeQualifier.INITIAL_DISABILITY_PERIOD_END,
                    disability.getEndDate());
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
                .setGenderCode(member.getGender() == null ? null : member.getGender().getCode())
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
