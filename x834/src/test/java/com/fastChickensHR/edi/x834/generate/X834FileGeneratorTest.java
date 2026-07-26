/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.generate;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.RecordLevel;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.Location;
import com.fastChickensHR.edi.x834.testsupport.TestFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end goldens for the {@link X834FileGenerator} seam: each test plans a realistic
 * {@link FileContent} and asserts the <em>entire</em> emitted 834 against an on-disk golden. Whole-payload
 * equality pins segment order, element positions, terminators, and the internally-generated SE/GE/IEA
 * counts all at once — catching reordering and dropped segments that the previous scattered
 * {@code contains(...)} checks could not. The document date is pinned via the {@code DOCUMENT_DATE}
 * file field, so the generator path is fully deterministic with no production seam. Regenerate goldens
 * after an intentional format change with {@code -Dupdate.goldens=true} (see {@link TestFixtures}).
 */
class X834FileGeneratorTest {

    private final X834FileGenerator generator = new X834FileGenerator();

    @Test
    void emitsAWellFormed834FromAFileContent() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record dependent = Record.of(List.of(
                dep(X834Location.MEMBER_INDICATOR, "Y"),
                dep(X834Location.RELATIONSHIP_CODE, "01"),
                dep(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                dep(X834Location.MEMBER_ID, "D1"),
                dep(X834Location.MEMBER_ID_QUALIFIER, "0F"),
                dep(X834Location.ENROLLMENT_DATE, "2026-01-01")));

        Record subscriber = new Record(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "20"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.POLICY_NUMBER, "POL-9"),
                emp(X834Location.MEMBER_ID, "E12345"),
                emp(X834Location.MEMBER_ID_QUALIFIER, "0F"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                emp(X834Location.ENROLLMENT_DATE, "2026-01-01"),
                emp(X834Location.COVERAGE_START_DATE, "2026-01-01"),
                emp(X834Location.HD_MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.HD_INSURANCE_LINE_CODE, "HLT"),
                emp(X834Location.HD_PLAN_COVERAGE_DESCRIPTION, "ACME CORP Health"),
                emp(X834Location.HD_COVERAGE_LEVEL_CODE, "ESP"),
                emp(X834Location.REF_EXTENSION_PREFIX + "ZZ", "NORTH")),
                List.of(dependent));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins the full envelope + subscriber loop with its nested REF extension and HD
        // (Loop 2300) coverage, then the dependent loop — segment order and element positions included.
        TestFixtures.assertMatchesGolden("golden/subscriber-with-dependent.834", out);
    }

    @Test
    void eachSubscribersHdNestsInsideItsOwnLoopForMultiMemberFiles() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record sub1 = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "SUB1"),
                emp(X834Location.HD_MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.HD_INSURANCE_LINE_CODE, "HLT"),
                emp(X834Location.HD_PLAN_COVERAGE_DESCRIPTION, "PLAN-ONE")));
        Record sub2 = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "SUB2"),
                emp(X834Location.HD_MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.HD_INSURANCE_LINE_CODE, "DEN"),
                emp(X834Location.HD_PLAN_COVERAGE_DESCRIPTION, "PLAN-TWO")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(sub1, sub2)));

        // The golden pins each subscriber's HD nested inside its own member loop (between that
        // subscriber's REF*OF and the next subscriber's) — not batched at the end of the transaction.
        TestFixtures.assertMatchesGolden("golden/two-subscribers-hd-nesting.834", out);
    }

    @Test
    void aMemberCarriesMultipleHdLoopsOnePerCoverage() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        // One subscriber, two coverages → two HD (Loop 2300) loops nested in the same member loop,
        // each addressed with the indexed HD form and carrying its own begin/end DTPs.
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                // Coverage 0 — medical, family, begin + end.
                emp(X834Location.hd(0, X834Location.HD_MAINTENANCE_TYPE_CODE), "001"),
                emp(X834Location.hd(0, X834Location.HD_INSURANCE_LINE_CODE), "HLT"),
                emp(X834Location.hd(0, X834Location.HD_PLAN_COVERAGE_DESCRIPTION), "ACME CORP Health"),
                emp(X834Location.hd(0, X834Location.HD_COVERAGE_LEVEL_CODE), "FAM"),
                emp(X834Location.hd(0, X834Location.HD_BENEFIT_BEGIN_DATE), "2026-01-01"),
                emp(X834Location.hd(0, X834Location.HD_BENEFIT_END_DATE), "2026-06-30"),
                // Coverage 1 — dental, employee-only, open-ended (begin only).
                emp(X834Location.hd(1, X834Location.HD_MAINTENANCE_TYPE_CODE), "001"),
                emp(X834Location.hd(1, X834Location.HD_INSURANCE_LINE_CODE), "DEN"),
                emp(X834Location.hd(1, X834Location.HD_PLAN_COVERAGE_DESCRIPTION), "ACME CORP Dental"),
                emp(X834Location.hd(1, X834Location.HD_COVERAGE_LEVEL_CODE), "EMP"),
                emp(X834Location.hd(1, X834Location.HD_BENEFIT_BEGIN_DATE), "2026-01-01")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins both HD loops (HLT then DEN, ascending index) — each with its own DTP dates —
        // nested inside the one subscriber's member loop.
        TestFixtures.assertMatchesGolden("golden/member-with-two-hd-loops.834", out);
    }

    @Test
    void emitsFullMemberLoopWhenNameDemographicsAndAddressPresent() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                emp(X834Location.LAST_NAME, "DOE"),
                emp(X834Location.FIRST_NAME, "JANE"),
                emp(X834Location.MIDDLE_NAME, "Q"),
                emp(X834Location.BIRTH_DATE, "1980-01-15"),
                emp(X834Location.GENDER, "F"),
                emp(X834Location.ADDRESS_LINE_1, "123 MAIN ST"),
                emp(X834Location.ADDRESS_LINE_2, "APT 4"),
                emp(X834Location.CITY, "SPRINGFIELD"),
                emp(X834Location.STATE, "IL"),
                emp(X834Location.ZIP_CODE, "62704")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins the full Loop 2100A member loop in order: INS -> NM1 -> N3 -> N4 -> DMG.
        TestFixtures.assertMatchesGolden("golden/member-full-demographics-address.834", out);
    }

    @Test
    void emitsMaintenanceReasonAndEmploymentStatusOnTheINSSegment() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.MAINTENANCE_REASON_CODE, "07"),
                emp(X834Location.EMPLOYMENT_STATUS_CODE, "TE"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                emp(X834Location.LAST_NAME, "DOE")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The maintenance reason lands on INS04 and the employment status on INS08 — the positions the
        // 220A1 gives them. The golden pins both, including the empty INS06/INS07 slots between them.
        TestFixtures.assertMatchesGolden("golden/member-ins-reason-and-employment-status.834", out);
    }

    @Test
    void omitsMaintenanceReasonAndEmploymentStatusWhenTheMemberHasNeither() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                emp(X834Location.LAST_NAME, "DOE")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The INS ends at INS05 as it always has: two new optional positions must not widen the segment
        // for a member that carries neither.
        assertTrue(out.contains("INS*Y*18*001**A~"), out);
    }

    @Test
    void emitsMailingAddressLoop2100CFromKernelFields() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.LAST_NAME, "DOE"),
                emp(X834Location.ADDRESS_LINE_1, "123 MAIN ST"),
                emp(X834Location.CITY, "SPRINGFIELD"),
                emp(X834Location.STATE, "IL"),
                emp(X834Location.ZIP_CODE, "62704"),
                emp(X834Location.MAILING_ADDRESS_LINE_1, "PO BOX 99"),
                emp(X834Location.MAILING_CITY, "SPRINGFIELD"),
                emp(X834Location.MAILING_STATE, "IL"),
                emp(X834Location.MAILING_ZIP_CODE, "62705")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins the residence address (2100A) followed by the mailing address (2100C).
        TestFixtures.assertMatchesGolden("golden/member-with-mailing-address.834", out);
    }

    @Test
    void emitsCoverageDateDtpsInsideTheHdLoop2300() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.SUBSCRIBER_NUMBER, "E12345"),
                emp(X834Location.HD_MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.HD_INSURANCE_LINE_CODE, "HLT"),
                emp(X834Location.HD_BENEFIT_BEGIN_DATE, "2026-01-01"),
                emp(X834Location.HD_BENEFIT_END_DATE, "2026-12-31")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins the HD segment followed by its begin/end coverage-date DTPs inside Loop 2300.
        TestFixtures.assertMatchesGolden("golden/coverage-date-dtps.834", out);
    }

    @Test
    void emitsMemberNM1WithSsnFromNameIdKernelFields() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.LAST_NAME, "DOE"),
                emp(X834Location.FIRST_NAME, "JANE"),
                emp(X834Location.NAME_ID_QUALIFIER, "34"),
                emp(X834Location.NAME_ID, "123456789")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins the NM1 carrying the SSN in the name-id (34) positions.
        TestFixtures.assertMatchesGolden("golden/member-nm1-with-ssn.834", out);
    }

    @Test
    void isa14RequestsAnAcknowledgmentWhenTheKeyIsSet() {
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"),
                file(X834Location.ACKNOWLEDGMENT_REQUESTED, "1"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins ISA14 = 1 (Acknowledgment Requested) following ISA13, across the whole envelope.
        TestFixtures.assertMatchesGolden("golden/acknowledgment-requested.834", out);
    }

    @Test
    void gsCarriesTheApplicationPartyCodesIndependentlyOfTheIsaIds() {
        // A partner that assigns a sender code distinct from the interchange mailbox ID and mandates
        // a GS03 that is not the ISA08 receiver ID — all four envelope party identifiers differ.
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "MAILBOX01"),
                file(X834Location.RECEIVER_ID, "592015694"),
                file(X834Location.APPLICATION_SENDER_CODE, "SNDRCODE9"),
                file(X834Location.APPLICATION_RECEIVER_CODE, "RBG005010X220A1"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        // The golden pins GS02/GS03 as the application party codes while ISA06/ISA08 keep the
        // interchange IDs — the four identifiers are addressable independently.
        TestFixtures.assertMatchesGolden("golden/application-party-codes.834", out);
    }

    @Test
    void gsFallsBackToTheIsaIdsWhenNoApplicationPartyCodesAreSupplied() {
        // The common case (GS mirrors ISA): omitting both keys must emit exactly what it did before
        // the party-ID split existed — hence the shared golden with the ISA14 case's envelope shape.
        List<Field> envelope = List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"),
                file(X834Location.ACKNOWLEDGMENT_REQUESTED, "1"));

        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        TestFixtures.assertMatchesGolden("golden/acknowledgment-requested.834", out);
    }

    @Test
    void emitsThe2100ATailFromKernelFields() {
        // PER / ICM / HLH / LUI, the four 2100A structures #139 added, now reachable from the
        // generate path. The golden pins their order after the DMG as well as their content.
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.LAST_NAME, "DOE"),
                emp(X834Location.FIRST_NAME, "JANE"),
                emp(X834Location.BIRTH_DATE, "1980-01-15"),
                emp(X834Location.GENDER, "F"),
                emp(X834Location.COMMUNICATION_PREFIX + "HP", "5551234567"),
                emp(X834Location.COMMUNICATION_PREFIX + "EM", "jane@example.com"),
                emp(X834Location.ICM_FREQUENCY, "4"),
                emp(X834Location.ICM_AMOUNT, "4500"),
                emp(X834Location.ICM_LOCATION_IDENTIFIER, "DEPT42"),
                emp(X834Location.HLH_HEALTH_RELATED_CODE, "T"),
                emp(X834Location.lui(0, X834Location.LUI_DESCRIPTION), "SPANISH"),
                emp(X834Location.lui(1, X834Location.LUI_DESCRIPTION), "VIETNAMESE")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope(), List.of(subscriber)));

        TestFixtures.assertMatchesGolden("golden/member-2100a-tail.834", out);
    }

    @Test
    void namesEachCommunicationByItsQualifierSuffix() {
        // "per.<qualifier>" mirrors "ref.<qualifier>": the qualifier is the address, so a member
        // cannot carry two of a channel and no separate qualifier field is needed.
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.COMMUNICATION_PREFIX + "WP", "5559876543")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope(), List.of(subscriber)));

        assertTrue(out.contains("PER*IP**WP*5559876543~"),
                () -> "expected the work phone under WP; got:\n" + out);
    }

    @Test
    void treatsUnindexedLanguageFieldsAsASingleLanguage() {
        // The un-indexed lui.* keys form one implicit language, exactly as un-indexed hd.* fields
        // form a single coverage group.
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.LUI_CODE_QUALIFIER, "XX"),
                emp(X834Location.LUI_CODE, "SPA"),
                emp(X834Location.LUI_DESCRIPTION, "SPANISH")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope(), List.of(subscriber)));

        assertTrue(out.contains("LUI*XX*SPA*SPANISH~"),
                () -> "expected one LUI from the un-indexed fields; got:\n" + out);
    }

    @Test
    void failsLoudlyWhenAnIncomeCarriesOnlyItsDepartmentNumber() {
        // The BCBS Kansas case: ICM04 alone cannot be emitted, because ICM01/ICM02 are mandatory.
        // The generator passes the partial income through so the writer rejects it, rather than
        // dropping the department number the caller did supply.
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.ICM_LOCATION_IDENTIFIER, "DEPT42")));

        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> generator.generate(new FileContent(Direction.OUTBOUND, envelope(), List.of(subscriber))));

        assertTrue(thrown.getMessage().contains("ICM01"), thrown.getMessage());
    }

    @Test
    void emitsNo2100ATailSegmentsWhenTheRecordCarriesNoneOfThoseFields() {
        Record subscriber = Record.of(List.of(
                emp(X834Location.MEMBER_INDICATOR, "Y"),
                emp(X834Location.RELATIONSHIP_CODE, "18"),
                emp(X834Location.MAINTENANCE_TYPE_CODE, "001"),
                emp(X834Location.LAST_NAME, "DOE")));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope(), List.of(subscriber)));

        assertFalse(out.contains("PER"), () -> "no PER; got:\n" + out);
        assertFalse(out.contains("ICM"), () -> "no ICM; got:\n" + out);
        assertFalse(out.contains("HLH"), () -> "no HLH; got:\n" + out);
        assertFalse(out.contains("LUI"), () -> "no LUI; got:\n" + out);
    }

    /** The envelope every member-level test shares. */
    private static List<Field> envelope() {
        return List.of(
                file(X834Location.SENDER_ID, "SENDER123"),
                file(X834Location.RECEIVER_ID, "RECV456"),
                file(X834Location.INTERCHANGE_CONTROL_NUMBER, "000000001"),
                file(X834Location.GROUP_CONTROL_NUMBER, "1"),
                file(X834Location.TRANSACTION_SET_CONTROL_NUMBER, "0001"),
                file(X834Location.DOCUMENT_DATE, "2026-01-15"),
                file(X834Location.REFERENCE_IDENTIFICATION, "REFID001"),
                file(X834Location.MASTER_POLICY_NUMBER, "MP-100"),
                file(X834Location.PLAN_SPONSOR_NAME, "ACME CORP"),
                file(X834Location.PAYER_NAME, "BLUE CROSS"));
    }

    private static Field file(String location, String value) {
        return new Field(new Location(RecordLevel.FILE, location), value);
    }

    private static Field emp(String location, String value) {
        return new Field(new Location(RecordLevel.RECORD, location), value);
    }

    private static Field dep(String location, String value) {
        return new Field(new Location(RecordLevel.SUBRECORD, location), value);
    }
}
