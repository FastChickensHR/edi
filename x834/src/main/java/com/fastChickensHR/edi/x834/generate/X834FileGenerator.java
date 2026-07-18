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
import com.fastChickensHR.edi.x834.loop2000.BaseMember;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import com.fastChickensHR.edi.x834.segments.RefSegment;
import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.X834Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The 834 implementation of the {@link FileGenerator} seam: serializes a format-neutral
 * {@link FileContent} into an X12 834 document. It holds no domain logic — every Value has already
 * been resolved upstream by the app's requirements engine. It only interprets each
 * {@link com.fastChickensHR.edi.core.Location} (via {@link X834Location}) onto the library's own typed
 * builders (`X834Context`, `Header`, `Member`, `HealthCoverage`, `RefSegment`), so the emitted bytes
 * match the legacy converter path by construction.
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
                document.addMember(buildMember(record));
                // After the members, in Record order: this Record's REF extensions, then its HD.
                for (Segment ref : refExtensions(record.fields())) {
                    document.addSegment(ref);
                }
                healthCoverage(record.fields()).ifPresent(document::addSegment);
            }

            return document.build().generateDocument().orElseThrow(() ->
                    new IllegalStateException("834 generation produced no document (validation failed)"));
        } catch (ValidationException e) {
            throw new IllegalStateException("Failed to generate 834: " + e.getMessage(), e);
        }
    }

    private X834Context buildContext(Map<String, String> file) {
        X834Context context = new X834Context();
        apply(file, X834Location.SENDER_ID, context::setSenderID);
        apply(file, X834Location.RECEIVER_ID, context::setReceiverID);
        apply(file, X834Location.INTERCHANGE_CONTROL_NUMBER, context::setInterchangeControlNumber);
        apply(file, X834Location.GROUP_CONTROL_NUMBER, context::setGroupControlNumber);
        apply(file, X834Location.TRANSACTION_SET_CONTROL_NUMBER, context::setTransactionSetControlNumber);
        apply(file, X834Location.DOCUMENT_DATE, v -> context.setDocumentDate(parseDateTime(v)));
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
        apply(loc, X834Location.POLICY_NUMBER, member::setPolicyNumber);
        apply(loc, X834Location.MEMBER_ID, member::setMemberId);
        apply(loc, X834Location.MEMBER_ID_QUALIFIER, member::setMemberIdQualifier);
        apply(loc, X834Location.SUBSCRIBER_NUMBER, member::setSubscriberNumber);
        apply(loc, X834Location.ENROLLMENT_DATE, v -> member.setEnrollmentDate(parseDateTime(v)));
        apply(loc, X834Location.COVERAGE_START_DATE, v -> member.setCoverageStartDate(parseDateTime(v)));
        apply(loc, X834Location.COVERAGE_END_DATE, v -> member.setCoverageEndDate(parseDateTime(v)));
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

    /** Build the HD coverage segment when the Record carries any {@code "hd."} field. */
    private Optional<Segment> healthCoverage(List<Field> fields) throws ValidationException {
        Map<String, String> loc = byLocation(fields);
        boolean anyHd = loc.keySet().stream().anyMatch(k -> k.startsWith(X834Location.HD_PREFIX));
        if (!anyHd) {
            return Optional.empty();
        }
        HealthCoverage.Builder hd = HealthCoverage.builder();
        apply(loc, X834Location.HD_MAINTENANCE_TYPE_CODE, hd::setMaintenanceTypeCode);
        apply(loc, X834Location.HD_MAINTENANCE_REASON_CODE, hd::setMaintenanceReasonCode);
        apply(loc, X834Location.HD_INSURANCE_LINE_CODE, hd::setInsuranceLineCode);
        apply(loc, X834Location.HD_PLAN_COVERAGE_DESCRIPTION, hd::setPlanCoverageDescription);
        apply(loc, X834Location.HD_COVERAGE_LEVEL_CODE, hd::setCoverageLevelCode);
        apply(loc, X834Location.HD_EMPLOYMENT_STATUS_CODE, hd::setEmploymentStatusCode);
        return Optional.of(hd.build());
    }

    /** Index a Record's non-omitted fields by their location (a built-in position is unique). */
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
