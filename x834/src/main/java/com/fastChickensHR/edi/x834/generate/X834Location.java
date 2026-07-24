/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.generate;

/**
 * The 834 dialect's {@code Location.location} vocabulary — the contract between the app (which builds
 * a {@link com.fastChickensHR.edi.core.FileContent}) and {@link X834FileGenerator} (which serializes it).
 * Each constant names <em>where</em> a resolved value goes; the generator maps it onto the library's
 * typed builders so the wire output is identical to the legacy converter path.
 *
 * <p>File-level keys feed the envelope/header ({@code fileFields}); member-level keys feed each
 * subscriber/dependent Record. Custom REF extensions use the {@link #REF_EXTENSION_PREFIX} + qualifier
 * (e.g. {@code "ref.ZZ"}); HD (coverage) keys are {@code "hd."}-prefixed.
 */
public final class X834Location {
    private X834Location() {
    }

    // ---- File level: envelope + control numbers (X834Context) ----
    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String INTERCHANGE_CONTROL_NUMBER = "interchangeControlNumber";
    public static final String GROUP_CONTROL_NUMBER = "groupControlNumber";
    public static final String TRANSACTION_SET_CONTROL_NUMBER = "transactionSetControlNumber";
    public static final String DOCUMENT_DATE = "documentDate";
    public static final String ACKNOWLEDGMENT_REQUESTED = "acknowledgmentRequested"; // ISA14 ("1" requests a TA1/999)

    // ---- File level: header data (Header.Builder) ----
    public static final String TRANSACTION_SET_ID = "transactionSetId";
    public static final String REFERENCE_IDENTIFICATION = "referenceIdentification"; // BGN02
    public static final String MASTER_POLICY_NUMBER = "masterPolicyNumber";          // REF*38
    public static final String PLAN_SPONSOR_NAME = "planSponsorName";                // N1*P5
    public static final String PAYER_NAME = "payerName";                             // N1*IN

    // ---- Member level: INS / built-in REF / DTP (Member fields) ----
    public static final String MEMBER_INDICATOR = "memberIndicator";       // INS01
    public static final String RELATIONSHIP_CODE = "relationshipCode";     // INS02
    public static final String MAINTENANCE_TYPE_CODE = "maintenanceTypeCode"; // INS03
    public static final String POLICY_NUMBER = "policyNumber";             // REF*1L
    public static final String MEMBER_ID = "memberId";                     // REF*<qual>
    public static final String MEMBER_ID_QUALIFIER = "memberIdQualifier";  // that REF's qualifier
    public static final String SUBSCRIBER_NUMBER = "subscriberNumber";     // REF*OF
    public static final String ENROLLMENT_DATE = "enrollmentDate";         // DTP*300 (Enrollment Signature Date)
    public static final String COVERAGE_START_DATE = "coverageStartDate";  // DTP*356 (Eligibility Begin)
    public static final String COVERAGE_END_DATE = "coverageEndDate";      // DTP*357

    // ---- Member level: Loop 2100A name / demographics / residence address ----
    public static final String LAST_NAME = "lastName";        // NM103
    public static final String FIRST_NAME = "firstName";      // NM104
    public static final String MIDDLE_NAME = "middleName";    // NM105
    public static final String NAME_ID_QUALIFIER = "nameIdQualifier"; // NM108 (e.g. 34 = SSN)
    public static final String NAME_ID = "nameId";                    // NM109 (e.g. the SSN)
    public static final String BIRTH_DATE = "birthDate";      // DMG02 (D8)
    public static final String GENDER = "gender";             // DMG03
    public static final String ADDRESS_LINE_1 = "addressLine1"; // N301
    public static final String ADDRESS_LINE_2 = "addressLine2"; // N302
    public static final String CITY = "city";                 // N401
    public static final String STATE = "state";               // N402
    public static final String ZIP_CODE = "zipCode";          // N403

    // ---- Member level: Loop 2100C mailing address ----
    public static final String MAILING_ADDRESS_LINE_1 = "mailingAddressLine1"; // 2100C N301
    public static final String MAILING_ADDRESS_LINE_2 = "mailingAddressLine2"; // 2100C N302
    public static final String MAILING_CITY = "mailingCity";                   // 2100C N401
    public static final String MAILING_STATE = "mailingState";                 // 2100C N402
    public static final String MAILING_ZIP_CODE = "mailingZipCode";            // 2100C N403

    // ---- Member level: health coverage (HD segment) ----
    public static final String HD_PREFIX = "hd.";
    // The 220A1 HD segment carries only HD01/HD03/HD04/HD05; HD02 and HD06+ are Not Used
    // (employment status lives on INS08, not HD09), so there are no HD_* fields for them.
    public static final String HD_MAINTENANCE_TYPE_CODE = "hd.maintenanceTypeCode";        // HD01
    public static final String HD_INSURANCE_LINE_CODE = "hd.insuranceLineCode";            // HD03
    public static final String HD_PLAN_COVERAGE_DESCRIPTION = "hd.planCoverageDescription"; // HD04
    public static final String HD_COVERAGE_LEVEL_CODE = "hd.coverageLevelCode";            // HD05
    /** Loop 2300 coverage begin date — DTP*348 (D8). */
    public static final String HD_BENEFIT_BEGIN_DATE = "hd.benefitBeginDate";
    /** Loop 2300 coverage end date — DTP*349 (D8). */
    public static final String HD_BENEFIT_END_DATE = "hd.benefitEndDate";

    /**
     * The position name for the {@code index}-th HD (Loop 2300) coverage group of one member — the
     * indexed form of an {@code HD_*} constant, so a single member Record can carry <em>multiple</em>
     * HD loops (one per coverage). {@code hd(0, HD_INSURANCE_LINE_CODE)} → {@code "hd.0.insuranceLineCode"};
     * {@code hd(1, HD_BENEFIT_BEGIN_DATE)} → {@code "hd.1.benefitBeginDate"}. Groups emit in ascending
     * index order, each with its own begin/end DTPs. The un-indexed {@code HD_*} constants remain a
     * single implicit group, so existing single-coverage callers are unaffected.
     */
    public static String hd(int index, String hdField) {
        return HD_PREFIX + index + "." + hdField.substring(HD_PREFIX.length());
    }

    /** Custom REF extension: location is {@code "ref." + qualifier}, e.g. {@code "ref.ZZ"}. */
    public static final String REF_EXTENSION_PREFIX = "ref.";
}
