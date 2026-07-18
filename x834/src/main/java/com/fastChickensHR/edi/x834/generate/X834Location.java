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
    public static final String ENROLLMENT_DATE = "enrollmentDate";         // DTP*356
    public static final String COVERAGE_START_DATE = "coverageStartDate";  // DTP*356
    public static final String COVERAGE_END_DATE = "coverageEndDate";      // DTP*357

    // ---- Member level: health coverage (HD segment) ----
    public static final String HD_PREFIX = "hd.";
    public static final String HD_MAINTENANCE_TYPE_CODE = "hd.maintenanceTypeCode";        // HD01
    public static final String HD_MAINTENANCE_REASON_CODE = "hd.maintenanceReasonCode";    // HD02
    public static final String HD_INSURANCE_LINE_CODE = "hd.insuranceLineCode";            // HD03
    public static final String HD_PLAN_COVERAGE_DESCRIPTION = "hd.planCoverageDescription"; // HD04
    public static final String HD_COVERAGE_LEVEL_CODE = "hd.coverageLevelCode";            // HD05
    public static final String HD_EMPLOYMENT_STATUS_CODE = "hd.employmentStatusCode";      // HD09

    /** Custom REF extension: location is {@code "ref." + qualifier}, e.g. {@code "ref.ZZ"}. */
    public static final String REF_EXTENSION_PREFIX = "ref.";
}
