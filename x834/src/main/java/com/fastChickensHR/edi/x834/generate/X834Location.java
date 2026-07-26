/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.generate;

/**
 * The 834 dialect's {@code Location.name} vocabulary — the contract between the consuming application
 * (which builds a {@link com.fastChickensHR.edi.core.FileContent}) and {@link X834FileGenerator} (which
 * serializes it). Each constant names <em>where</em> a resolved value goes; the generator maps it onto
 * the library's own typed builders, so the wire output is exactly what those builders emit.
 *
 * <p>File-level keys feed the envelope/header ({@code fileFields}); member-level keys feed each
 * subscriber/dependent Record. Custom REF extensions use the {@link #REF_EXTENSION_PREFIX} + qualifier
 * (e.g. {@code "ref.ZZ"}); HD (coverage) keys are {@code "hd."}-prefixed.
 */
public final class X834Location {
    private X834Location() {
    }

    // ---- File level: envelope + control numbers (X834Context) ----
    public static final String SENDER_ID = "senderId";       // ISA06
    public static final String RECEIVER_ID = "receiverId";   // ISA08
    /** GS02 — application sender's code; falls back to {@link #SENDER_ID} when absent. */
    public static final String APPLICATION_SENDER_CODE = "applicationSenderCode";
    /** GS03 — application receiver's code; falls back to {@link #RECEIVER_ID} when absent. */
    public static final String APPLICATION_RECEIVER_CODE = "applicationReceiverCode";
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
    /**
     * INS04 — maintenance reason code (element 1203): why this maintenance is happening, e.g. {@code 07}
     * (termination of benefits) or {@code XN} (notification only). The member-level home for a reason
     * code; the 220A1's HD segment has no HD02 to put one in.
     */
    public static final String MAINTENANCE_REASON_CODE = "maintenanceReasonCode";
    /**
     * INS08 — employment status code (element 584), e.g. {@code AC} (active), {@code TE} (terminated),
     * {@code RT} (retired). Element 584 is alphabetic; the numeric statuses some payroll systems carry
     * are not element-584 codes.
     */
    public static final String EMPLOYMENT_STATUS_CODE = "employmentStatusCode";
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

    // ---- Member level: Loop 2100A PER (communications numbers) ----
    /**
     * A member communication number: the location is {@link #COMMUNICATION_PREFIX} + the
     * communication number qualifier, and the value is the number — {@code "per.HP"} a home phone,
     * {@code "per.EM"} an email. Same shape as {@link #REF_EXTENSION_PREFIX}, and for the same
     * reason: the qualifier <em>is</em> the address, so one member cannot carry two of a channel.
     *
     * <p>The 834 permits three per member. A fourth is rejected when written rather than dropped.
     */
    public static final String COMMUNICATION_PREFIX = "per.";

    // ---- Member level: Loop 2100A ICM (member income) ----
    /** ICM01 — the period {@link #ICM_AMOUNT} covers, e.g. {@code 4} (monthly). Required with it. */
    public static final String ICM_FREQUENCY = "icm.frequency";
    /** ICM02 — what the member earns in that period. Required with {@link #ICM_FREQUENCY}. */
    public static final String ICM_AMOUNT = "icm.amount";
    /** ICM03 — hours worked in that period. */
    public static final String ICM_HOURS = "icm.hours";
    /** ICM04 — where the member works; BCBS Kansas carries its department number here. */
    public static final String ICM_LOCATION_IDENTIFIER = "icm.locationIdentifier";
    /** ICM05 — the member's salary grade. */
    public static final String ICM_SALARY_GRADE = "icm.salaryGrade";
    /** ICM06 — the currency {@link #ICM_AMOUNT} is in, e.g. {@code USD}. */
    public static final String ICM_CURRENCY_CODE = "icm.currencyCode";

    // ---- Member level: Loop 2100A HLH (member health information) ----
    /** HLH01 — the member's tobacco/substance status. */
    public static final String HLH_HEALTH_RELATED_CODE = "hlh.healthRelatedCode";
    /** HLH02 — the member's height. */
    public static final String HLH_HEIGHT = "hlh.height";
    /** HLH03 — the member's current weight. */
    public static final String HLH_CURRENT_WEIGHT = "hlh.currentWeight";
    /** HLH04 — the member's previous weight. */
    public static final String HLH_PREVIOUS_WEIGHT = "hlh.previousWeight";
    /** HLH05 — why the weight changed. */
    public static final String HLH_DESCRIPTION = "hlh.description";

    // ---- Member level: Loop 2100A LUI (member language) ----
    public static final String LUI_PREFIX = "lui.";
    /** LUI01 — what kind of code {@link #LUI_CODE} is. Required with it. */
    public static final String LUI_CODE_QUALIFIER = "lui.codeQualifier";
    /** LUI02 — the language code. Required with {@link #LUI_CODE_QUALIFIER}. */
    public static final String LUI_CODE = "lui.code";
    /** LUI03 — the language named in words. At least one of this or {@link #LUI_CODE} is required. */
    public static final String LUI_DESCRIPTION = "lui.description";

    /**
     * The position name for the {@code index}-th language of one member — the indexed form of a
     * {@code LUI_*} constant, so a member Record can carry <em>several</em> languages.
     * {@code lui(0, LUI_CODE)} &rarr; {@code "lui.0.code"}; {@code lui(1, LUI_DESCRIPTION)} &rarr;
     * {@code "lui.1.description"}. Languages emit in ascending index order, and the un-indexed
     * {@code LUI_*} constants remain a single implicit language — the same convention
     * {@link #hd(int, String)} uses for coverage groups.
     */
    public static String lui(int index, String luiField) {
        return LUI_PREFIX + index + "." + luiField.substring(LUI_PREFIX.length());
    }

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
