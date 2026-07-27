/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.generate;

import com.fastChickensHR.edi.core.FileGenerator;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Address;
import com.fastChickensHR.edi.x834.loop2000.AddressType;
import com.fastChickensHR.edi.x834.loop2000.BaseMember;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.data.CoordinationOfBenefitsCode;
import com.fastChickensHR.edi.x834.data.DisabilityTypeCode;
import com.fastChickensHR.edi.x834.data.PayerResponsibilitySequenceCode;
import com.fastChickensHR.edi.x834.data.FrequencyCode;
import com.fastChickensHR.edi.x834.data.HealthRelatedCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.HealthInformation;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Income;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Language;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunication;
import com.fastChickensHR.edi.x834.loop2000.loop2310.Provider;
import com.fastChickensHR.edi.x834.loop2000.loop2200.Disability;
import com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefits;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;
import com.fastChickensHR.edi.x834.loop2000.loop2300.HealthCoverage;
import com.fastChickensHR.edi.x834.segments.RefSegment;
import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.X834Document;
import com.fastChickensHR.edi.x834.GenerationError;
import com.fastChickensHR.edi.x834.GenerationResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The 834 implementation of the {@link FileGenerator} seam: serializes a format-neutral
 * {@link FileContent} into an X12 834 document. It holds no domain logic — every Value has already
 * been resolved upstream by the consuming application. It only interprets each
 * {@link com.fastChickensHR.edi.core.Location} (via {@link X834Location}) onto the library's own typed
 * builders (`X834Context`, `Header`, `Member`, `HealthCoverage`, `RefSegment`), so the emitted bytes
 * are by construction what those builders produce.
 *
 * <p>Structure produced: the header/envelope (from file fields), then one {@link Member} per
 * Record (dependents attached as child members), then — after all members, in Record order — each
 * Record's custom REF extensions followed by its HD coverage segment, then the trailer. Control
 * numbers, segment counts and SE/GE/IEA are generated internally by the library.
 */
public final class X834FileGenerator implements FileGenerator {

    @Override
    public String generate(FileContent file) {
        try {
            Map<String, String> fileLoc = byLocation(file.fileFields());
            X834Context context = buildContext(fileLoc);
            Header header = buildHeader(fileLoc, context);

            X834Document.Builder document = new X834Document.Builder(context)
                    .withHeader(header)
                    .withTrailer(new Trailer.Builder(context));

            for (Record record : file.records()) {
                Member member = buildMember(record);
                // This Record's REF extensions then its HD (Loop 2300) coverages attach to the
                // member, so they are emitted inside that member's own loop rather than after
                // every member.
                for (Segment ref : refExtensions(record.fields())) {
                    member.addSegment(ref);
                }
                for (HealthCoverage coverage : healthCoverage(record.fields())) {
                    member.addHealthCoverage(coverage);
                }
                document.addMember(member);
            }

            // The FileGenerator seam returns a String, so a failed result surfaces as the seam's
            // unchecked exception — but carrying every reason, not swallowing them (#123).
            return switch (document.build().generateDocument()) {
                case GenerationResult.Success success -> success.document();
                case GenerationResult.Failure failure -> throw new IllegalStateException(
                        "Failed to generate 834:\n  - " + failure.errors().stream()
                                .map(GenerationError::formatted)
                                .collect(Collectors.joining("\n  - ")));
            };
        } catch (ValidationException e) {
            throw new IllegalStateException("Failed to generate 834: " + e.getMessage(), e);
        }
    }

    private X834Context buildContext(Map<String, String> file) {
        X834Context context = new X834Context();
        apply(file, X834Location.SENDER_ID, context::setSenderID);
        apply(file, X834Location.RECEIVER_ID, context::setReceiverID);
        apply(file, X834Location.APPLICATION_SENDER_CODE, context::setApplicationSenderCode);
        apply(file, X834Location.APPLICATION_RECEIVER_CODE, context::setApplicationReceiverCode);
        apply(file, X834Location.INTERCHANGE_CONTROL_NUMBER, context::setInterchangeControlNumber);
        apply(file, X834Location.GROUP_CONTROL_NUMBER, context::setGroupControlNumber);
        apply(file, X834Location.TRANSACTION_SET_CONTROL_NUMBER, context::setTransactionSetControlNumber);
        apply(file, X834Location.DOCUMENT_DATE, v -> context.setDocumentDate(parseDateTime(v)));
        apply(file, X834Location.ACKNOWLEDGMENT_REQUESTED, context::setAcknowledgmentRequested);
        return context;
    }

    private Header buildHeader(Map<String, String> file, X834Context context) {
        Header.Builder header = new Header.Builder(context);
        apply(file, X834Location.TRANSACTION_SET_ID, header::setTransactionSetIdentifierCode);
        apply(file, X834Location.REFERENCE_IDENTIFICATION, header::setReferenceIdentification);
        apply(file, X834Location.MASTER_POLICY_NUMBER, header::setMasterPolicyNumber);
        apply(file, X834Location.PLAN_SPONSOR_NAME, header::setPlanSponsorName);
        apply(file, X834Location.PAYER_NAME, header::setPayerName);
        return header.build();
    }

    /** Build the subscriber {@link Member} for a Record, attaching each child Record as a dependent. */
    private Member buildMember(Record record) {
        Member member = new Member();
        populate(member, byLocation(record.fields()));
        for (Record child : record.children()) {
            DependentMember dependent = new DependentMember();
            populate(dependent, byLocation(child.fields()));
            dependent.setPrimaryMember(member);
            member.addDependent(dependent);
        }
        return member;
    }

    /** Set the wire-relevant fields shared by subscriber and dependent members. */
    private void populate(BaseMember member, Map<String, String> loc) {
        apply(loc, X834Location.MEMBER_INDICATOR, v -> member.setMemberIndicator(MemberIndicator.fromString(v)));
        apply(loc, X834Location.RELATIONSHIP_CODE, v -> member.setRelationshipCode(IndividualRelationshipCode.fromString(v)));
        apply(loc, X834Location.MAINTENANCE_TYPE_CODE, v -> member.setMaintenanceTypeCode(MaintenanceTypeCode.fromString(v)));
        apply(loc, X834Location.MAINTENANCE_REASON_CODE, v -> member.setMaintenanceReasonCode(MaintenanceReasonCode.fromString(v)));
        apply(loc, X834Location.EMPLOYMENT_STATUS_CODE, v -> member.setEmploymentStatusCode(EmploymentStatusCode.fromString(v)));
        apply(loc, X834Location.POLICY_NUMBER, member::setPolicyNumber);
        apply(loc, X834Location.MEMBER_ID, member::setMemberId);
        apply(loc, X834Location.MEMBER_ID_QUALIFIER, member::setMemberIdQualifier);
        apply(loc, X834Location.SUBSCRIBER_NUMBER, member::setSubscriberNumber);
        apply(loc, X834Location.ENROLLMENT_DATE, v -> member.setEnrollmentDate(parseDateTime(v)));
        apply(loc, X834Location.COVERAGE_START_DATE, v -> member.setCoverageStartDate(parseDateTime(v)));
        apply(loc, X834Location.COVERAGE_END_DATE, v -> member.setCoverageEndDate(parseDateTime(v)));

        // Loop 2100A name / demographics / residence address. The writer emits the
        // matching NM1/DMG/N3/N4 only when these are present, so absent fields change nothing.
        apply(loc, X834Location.LAST_NAME, member::setLastName);
        apply(loc, X834Location.FIRST_NAME, member::setFirstName);
        apply(loc, X834Location.MIDDLE_NAME, member::setMiddleName);
        apply(loc, X834Location.NAME_ID_QUALIFIER, member::setNameIdQualifier);
        apply(loc, X834Location.NAME_ID, member::setNameId);
        apply(loc, X834Location.BIRTH_DATE, v -> member.setBirthDate(parseDateTime(v)));
        apply(loc, X834Location.GENDER, member::setGender);
        apply(loc, X834Location.ADDRESS_LINE_1, member::setAddressLine1);
        apply(loc, X834Location.ADDRESS_LINE_2, member::setAddressLine2);
        apply(loc, X834Location.CITY, member::setCity);
        apply(loc, X834Location.STATE, member::setState);
        apply(loc, X834Location.ZIP_CODE, member::setZipCode);

        // Loop 2100A PER / ICM / HLH / LUI. Each is built only from the keys actually present, and
        // the writer emits the matching segment only when the member carries one, so a Record with
        // none of these renders exactly as before.
        communications(loc).forEach(member::addCommunication);
        income(loc).ifPresent(member::setIncome);
        healthInformation(loc).ifPresent(member::setHealthInformation);
        languages(loc).forEach(member::addLanguage);

        // Loops 2310 and 2320/2330, one occurrence per indexed group.
        providers(loc).forEach(member::addProvider);
        coordinationOfBenefits(loc).forEach(member::addCoordinationOfBenefits);
        disabilities(loc).forEach(member::addDisability);
        reportingCategories(loc).forEach(member::addReportingCategory);

        // Loop 2100C mailing address (optional, when the member's mailing address differs).
        mailingAddress(loc).ifPresent(member::addAddress);
    }

    /**
     * Loop 2100A PER: one communication per {@code "per.<qualifier>"} field, in the order the Record
     * lists them. The qualifier is the location's own suffix, so a member cannot carry two of a
     * channel — the same property {@code "ref.<qualifier>"} has.
     */
    private static List<MemberCommunication> communications(Map<String, String> loc) {
        List<MemberCommunication> channels = new ArrayList<>();
        for (Map.Entry<String, String> entry : loc.entrySet()) {
            if (!entry.getKey().startsWith(X834Location.COMMUNICATION_PREFIX) || entry.getValue().isBlank()) {
                continue;
            }
            String qualifier = entry.getKey().substring(X834Location.COMMUNICATION_PREFIX.length());
            channels.add(new MemberCommunication(
                    CommunicationNumberQualifier.fromString(qualifier), entry.getValue()));
        }
        return channels;
    }

    /**
     * Loop 2100A ICM, built when any {@code icm.} field is present.
     * <p>
     * A partial income is deliberately passed through rather than dropped: ICM01 and ICM02 are
     * mandatory, so a Record carrying only {@link X834Location#ICM_LOCATION_IDENTIFIER} — the BCBS
     * Kansas department-number case — fails loudly when written instead of silently losing the
     * department number it did supply.
     */
    private static Optional<Income> income(Map<String, String> loc) {
        Income income = new Income();
        boolean any = false;
        any |= set(loc, X834Location.ICM_FREQUENCY, v -> income.setFrequency(FrequencyCode.fromString(v)));
        any |= set(loc, X834Location.ICM_AMOUNT, income::setAmount);
        any |= set(loc, X834Location.ICM_HOURS, income::setHours);
        any |= set(loc, X834Location.ICM_LOCATION_IDENTIFIER, income::setLocationIdentifier);
        any |= set(loc, X834Location.ICM_SALARY_GRADE, income::setSalaryGrade);
        any |= set(loc, X834Location.ICM_CURRENCY_CODE, income::setCurrencyCode);
        return any ? Optional.of(income) : Optional.empty();
    }

    /** Loop 2100A HLH, built when any {@code hlh.} field is present. */
    private static Optional<HealthInformation> healthInformation(Map<String, String> loc) {
        HealthInformation health = new HealthInformation();
        boolean any = false;
        any |= set(loc, X834Location.HLH_HEALTH_RELATED_CODE,
                v -> health.setHealthRelatedCode(HealthRelatedCode.fromString(v)));
        any |= set(loc, X834Location.HLH_HEIGHT, health::setHeight);
        any |= set(loc, X834Location.HLH_CURRENT_WEIGHT, health::setCurrentWeight);
        any |= set(loc, X834Location.HLH_PREVIOUS_WEIGHT, health::setPreviousWeight);
        any |= set(loc, X834Location.HLH_DESCRIPTION, health::setDescription);
        return any ? Optional.of(health) : Optional.empty();
    }

    /**
     * Loop 2100A LUI: one language per {@code lui.<i>.} group, in ascending index order, with
     * un-indexed {@code lui.} fields forming a single implicit language — mirroring how
     * {@link X834Location#hd(int, String)} groups coverages.
     */
    private static List<Language> languages(Map<String, String> loc) {
        List<Language> languages = new ArrayList<>();
        for (Map<String, String> group : groupsByIndex(loc, X834Location.LUI_PREFIX).values()) {
            Language language = new Language();
            set(group, "codeQualifier", v -> language.setCodeQualifier(IdentificationCodeQualifier.fromString(v)));
            set(group, "code", language::setCode);
            set(group, "description", language::setDescription);
            languages.add(language);
        }
        return languages;
    }

    /**
     * Loop 2310: one provider per {@code provider.<i>.} group, in ascending index order.
     * <p>
     * A change action with no date, or an identifier with no qualifier, is passed through rather than
     * quietly repaired — the writer rejects each, which is the honest answer to a half-stated change.
     */
    private static List<Provider> providers(Map<String, String> loc) {
        List<Provider> providers = new ArrayList<>();
        for (Map<String, String> group : groupsByIndex(loc, X834Location.PROVIDER_PREFIX).values()) {
            Provider provider = new Provider();
            set(group, "lastName", provider::setLastName);
            set(group, "firstName", provider::setFirstName);
            set(group, "middleName", provider::setMiddleName);
            set(group, "idQualifier",
                    v -> provider.setIdentifierQualifier(IdentificationCodeQualifier.fromString(v)));
            set(group, "id", provider::setIdentifier);
            set(group, "changeAction", v -> provider.setChangeAction(ActionCode.fromString(v)));
            set(group, "changeDate", v -> provider.setChangeDate(parseDateTime(v)));
            set(group, "changeReason",
                    v -> provider.setChangeReason(MaintenanceReasonCode.fromString(v)));
            providers.add(provider);
        }
        return providers;
    }

    /** Loops 2320/2330: one other plan per {@code cob.<i>.} group, in ascending index order. */
    private static List<CoordinationOfBenefits> coordinationOfBenefits(Map<String, String> loc) {
        List<CoordinationOfBenefits> others = new ArrayList<>();
        for (Map<String, String> group : groupsByIndex(loc, X834Location.COB_PREFIX).values()) {
            CoordinationOfBenefits cob = new CoordinationOfBenefits();
            set(group, "payerResponsibility",
                    v -> cob.setPayerResponsibility(PayerResponsibilitySequenceCode.fromString(v)));
            set(group, "policyIdentifier", cob::setPolicyIdentifier);
            set(group, "benefitsCoordination",
                    v -> cob.setBenefitsCoordination(CoordinationOfBenefitsCode.fromString(v)));
            // Left at its 6P default unless the caller names another qualifier.
            set(group, "groupNumberQualifier", cob::setGroupNumberQualifier);
            set(group, "groupNumber", cob::setGroupNumber);
            set(group, "beginDate", v -> cob.setBeginDate(parseDateTime(v)));
            set(group, "endDate", v -> cob.setEndDate(parseDateTime(v)));
            set(group, "relatedEntityName", cob::setRelatedEntityName);
            others.add(cob);
        }
        return others;
    }

    /** Loop 2200: one disability per {@code disability.<i>.} group, in ascending index order. */
    private static List<Disability> disabilities(Map<String, String> loc) {
        List<Disability> disabilities = new ArrayList<>();
        for (Map<String, String> group : groupsByIndex(loc, X834Location.DISABILITY_PREFIX).values()) {
            Disability disability = new Disability();
            set(group, "type", v -> disability.setType(DisabilityTypeCode.fromString(v)));
            set(group, "quantity", disability::setQuantity);
            set(group, "occupationCode", disability::setOccupationCode);
            set(group, "workIntensityCode", disability::setWorkIntensityCode);
            set(group, "productOptionCode", disability::setProductOptionCode);
            set(group, "monetaryAmount", disability::setMonetaryAmount);
            set(group, "startDate", v -> disability.setStartDate(parseDateTime(v)));
            set(group, "endDate", v -> disability.setEndDate(parseDateTime(v)));
            disabilities.add(disability);
        }
        return disabilities;
    }

    /**
     * Loops 2700/2750: one reporting category per {@code category.<i>.} group, in ascending index
     * order. The writer wraps them in a single {@code LS*2700} … {@code LE*2700} block and assigns
     * the {@code LX} numbers, so nothing here addresses those positions directly.
     */
    private static List<ReportingCategory> reportingCategories(Map<String, String> loc) {
        List<ReportingCategory> categories = new ArrayList<>();
        for (Map<String, String> group : groupsByIndex(loc, X834Location.CATEGORY_PREFIX).values()) {
            ReportingCategory category = new ReportingCategory();
            set(group, "name", category::setName);
            set(group, "value", category::setValue);
            // Left at its ZZ default unless the caller names another qualifier.
            set(group, "referenceQualifier", category::setReferenceQualifier);
            set(group, "date", v -> category.setDate(parseDateTime(v)));
            set(group, "dateQualifier", category::setDateQualifier);
            categories.add(category);
        }
        return categories;
    }

    /**
     * Groups {@code <prefix><i>.<suffix>} keys by {@code <i>}, keyed by suffix. Un-indexed
     * {@code <prefix><suffix>} keys form one group ordered ahead of the numbered ones, so a caller
     * that never indexes behaves as though it had used index zero.
     */
    private static Map<Integer, Map<String, String>> groupsByIndex(Map<String, String> loc, String prefix) {
        Map<Integer, Map<String, String>> groups = new java.util.TreeMap<>();
        for (Map.Entry<String, String> entry : loc.entrySet()) {
            if (!entry.getKey().startsWith(prefix)) {
                continue;
            }
            String rest = entry.getKey().substring(prefix.length());
            int dot = rest.indexOf('.');
            int index = -1;
            String suffix = rest;
            if (dot > 0 && rest.substring(0, dot).chars().allMatch(Character::isDigit)) {
                index = Integer.parseInt(rest.substring(0, dot));
                suffix = rest.substring(dot + 1);
            }
            groups.computeIfAbsent(index, k -> new LinkedHashMap<>()).put(suffix, entry.getValue());
        }
        return groups;
    }

    /** Like {@link #apply}, but reports whether the key carried a usable value. */
    private static boolean set(Map<String, String> source, String key, Consumer<String> setter) {
        String value = source.get(key);
        if (value == null || value.isBlank()) {
            return false;
        }
        setter.accept(value);
        return true;
    }

    /** Build the member's {@link AddressType#MAILING} address from the {@code mailing*} fields. */
    private static Optional<Address> mailingAddress(Map<String, String> loc) {
        Address mailing = new Address();
        mailing.setType(AddressType.MAILING);
        apply(loc, X834Location.MAILING_ADDRESS_LINE_1, mailing::setLine1);
        apply(loc, X834Location.MAILING_ADDRESS_LINE_2, mailing::setLine2);
        apply(loc, X834Location.MAILING_CITY, mailing::setCity);
        apply(loc, X834Location.MAILING_STATE, mailing::setState);
        apply(loc, X834Location.MAILING_ZIP_CODE, mailing::setZipCode);
        return mailing.hasStreet() ? Optional.of(mailing) : Optional.empty();
    }

    /** Custom REF extensions: any {@code "ref.<qualifier>"} field becomes a {@code REF} segment. */
    private List<Segment> refExtensions(List<Field> fields) throws ValidationException {
        List<Segment> refs = new java.util.ArrayList<>();
        for (Field field : fields) {
            String location = field.location().name();
            if (field.isOmitted() || !location.startsWith(X834Location.REF_EXTENSION_PREFIX)) {
                continue;
            }
            String qualifier = location.substring(X834Location.REF_EXTENSION_PREFIX.length());
            refs.add(new RefSegment.Builder()
                    .setReferenceIdentificationQualifier(qualifier)
                    .setReferenceIdentification(field.value())
                    .build());
        }
        return refs;
    }

    /**
     * The Record's HD (Loop 2300) coverages — one per coverage group, in ascending index order.
     * A member can carry multiple coverages: fields addressed with the indexed form
     * {@link X834Location#hd(int, String)} ({@code "hd.<i>.<suffix>"}) are grouped by {@code <i>},
     * each group becoming its own {@link HealthCoverage} (rendered as an HD segment + begin/end
     * DTPs by the member writer). Un-indexed {@code "hd.<suffix>"} fields form a single implicit
     * group (the legacy single-coverage shape), so existing callers are byte-identical.
     */
    private List<HealthCoverage> healthCoverage(List<Field> fields) {
        List<HealthCoverage> coverages = new java.util.ArrayList<>();
        for (Map<String, String> group : hdGroupsByIndex(fields).values()) {
            coverages.add(oneHealthCoverage(group));
        }
        return coverages;
    }

    /** Read one coverage group into a {@link HealthCoverage} value object. */
    private HealthCoverage oneHealthCoverage(Map<String, String> loc) {
        HealthCoverage coverage = new HealthCoverage();
        // The 220A1 HD segment carries only HD01/HD03/HD04/HD05. HD02 and HD06+ are Not Used —
        // employment status, in particular, belongs on INS08, never HD.
        apply(loc, X834Location.HD_MAINTENANCE_TYPE_CODE, coverage::setMaintenanceTypeCode);
        apply(loc, X834Location.HD_INSURANCE_LINE_CODE, coverage::setInsuranceLineCode);
        apply(loc, X834Location.HD_PLAN_COVERAGE_DESCRIPTION, coverage::setPlanCoverageDescription);
        apply(loc, X834Location.HD_COVERAGE_LEVEL_CODE, coverage::setCoverageLevelCode);
        apply(loc, X834Location.HD_BENEFIT_BEGIN_DATE, v -> coverage.setStartDate(parseDateTime(v)));
        apply(loc, X834Location.HD_BENEFIT_END_DATE, v -> coverage.setEndDate(parseDateTime(v)));
        return coverage;
    }

    /**
     * Group a Record's HD fields by coverage-loop index, each group keyed by the canonical un-indexed
     * {@code "hd.<suffix>"} name so {@link #oneHealthCoverage} reads it with the same {@code HD_*}
     * constants. Un-indexed {@code "hd.<suffix>"} fields collapse into one implicit group (sentinel key
     * {@code -1}); indexed {@code "hd.<i>.<suffix>"} fields group by {@code <i>}. Insertion order is
     * preserved and the map is sorted so groups emit in ascending index order. Walks the raw field list
     * (not the deduped map) so repeated HD groups survive, mirroring {@link #refExtensions}.
     */
    private static Map<Integer, Map<String, String>> hdGroupsByIndex(List<Field> fields) {
        Map<Integer, Map<String, String>> groups = new java.util.TreeMap<>();
        for (Field field : fields) {
            String name = field.location().name();
            if (field.isOmitted() || !name.startsWith(X834Location.HD_PREFIX)) {
                continue;
            }
            String rest = name.substring(X834Location.HD_PREFIX.length());
            int dot = rest.indexOf('.');
            int index = -1;
            String suffix = rest;
            if (dot > 0 && rest.substring(0, dot).chars().allMatch(Character::isDigit)) {
                index = Integer.parseInt(rest.substring(0, dot));
                suffix = rest.substring(dot + 1);
            }
            groups.computeIfAbsent(index, k -> new LinkedHashMap<>())
                    .put(X834Location.HD_PREFIX + suffix, field.value());
        }
        return groups;
    }

    /** Index a Record's non-omitted fields by their location (a built-in location is unique). */
    private static Map<String, String> byLocation(List<Field> fields) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Field field : fields) {
            if (!field.isOmitted()) {
                map.put(field.location().name(), field.value());
            }
        }
        return map;
    }

    /** Invoke {@code setter} with the value at {@code key} when present and non-blank. */
    private static void apply(Map<String, String> source, String key, Consumer<String> setter) {
        String value = source.get(key);
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }

    /** Parse an ISO date ({@code yyyy-MM-dd}) or date-time into a {@link LocalDateTime}. */
    private static LocalDateTime parseDateTime(String value) {
        return value.contains("T") ? LocalDateTime.parse(value) : LocalDate.parse(value).atStartOfDay();
    }
}
