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
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class X834FileGeneratorTest {

    private final X834FileGenerator generator = new X834FileGenerator();

    @Test
    void emitsAWellFormed834FromAPlannedFile() {
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
                emp(X834Location.HD_MAINTENANCE_REASON_CODE, "AC"),
                emp(X834Location.HD_INSURANCE_LINE_CODE, "HLT"),
                emp(X834Location.HD_PLAN_COVERAGE_DESCRIPTION, "ACME CORP Health"),
                emp(X834Location.HD_COVERAGE_LEVEL_CODE, "ESP"),
                emp(X834Location.HD_EMPLOYMENT_STATUS_CODE, "1"),
                emp(X834Location.REF_EXTENSION_PREFIX + "ZZ", "NORTH")),
                List.of(dependent));

        String out = generator.generate(new FileContent(Direction.OUTBOUND, envelope, List.of(subscriber)));

        assertFalse(out == null || out.isBlank(), "expected a rendered 834");
        // Envelope + header
        contains(out, "ISA*00*");
        contains(out, "SENDER123");
        contains(out, "RECV456");
        contains(out, "ST*834*0001*");
        contains(out, "BGN*00*REFID001*20260115");
        contains(out, "REF*38*MP-100");
        contains(out, "N1*P5*ACME CORP*FI");
        contains(out, "N1*IN*BLUE CROSS*FI");
        // Subscriber loop
        contains(out, "INS*Y*20*001**A");
        contains(out, "REF*1L*POL-9");
        contains(out, "REF*0F*E12345");
        contains(out, "REF*OF*E12345");
        // Dependent loop
        contains(out, "INS*Y*01*001**A");
        // Custom REF extension + coverage (HD01-05 exact; trailing empties left to the library)
        contains(out, "REF*ZZ*NORTH");
        contains(out, "HD*001*AC*HLT*ACME CORP Health*ESP");
        // Trailer
        contains(out, "SE*");
        contains(out, "GE*1*1");
        contains(out, "IEA*1*000000001");
        // HD and REF-extension belong to the subscriber (INS*Y*20) and render inside that
        // subscriber's own loop (Loop 2300): after its INS, but BEFORE the dependent's loop
        // (INS*Y*01) begins — not batched after every member.
        assertTrue(out.indexOf("HD*001") > out.indexOf("INS*Y*20"), "HD must render after its subscriber's INS");
        assertTrue(out.indexOf("HD*001") < out.indexOf("INS*Y*01"), "HD must render before the dependent loop");
        assertTrue(out.indexOf("REF*ZZ*NORTH") > out.indexOf("INS*Y*20"), "REF extension renders inside the subscriber loop");
        assertTrue(out.indexOf("REF*ZZ*NORTH") < out.indexOf("INS*Y*01"), "REF extension renders before the dependent loop");
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

        // Each subscriber's HD sits between its own REF*OF and the next subscriber's REF*OF —
        // i.e. HD is nested in its member's loop, not batched at the end of the transaction.
        int sub1Ref = out.indexOf("REF*OF*SUB1");
        int sub1Hd = out.indexOf("PLAN-ONE");
        int sub2Ref = out.indexOf("REF*OF*SUB2");
        int sub2Hd = out.indexOf("PLAN-TWO");
        assertTrue(sub1Ref < sub1Hd && sub1Hd < sub2Ref, "subscriber 1's HD must precede subscriber 2's loop");
        assertTrue(sub2Ref < sub2Hd, "subscriber 2's HD must follow subscriber 2's INS/REF");
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

        contains(out, "INS*Y*18*001**A");
        contains(out, "NM1*IL*1*DOE*JANE*Q~");
        contains(out, "N3*123 MAIN ST*APT 4~");
        contains(out, "N4*SPRINGFIELD*IL*62704~");
        contains(out, "DMG*D8*19800115*F~");
        // Loop 2100A order: NM1 -> N3 -> N4 -> DMG, all after the INS.
        assertTrue(out.indexOf("NM1*IL") > out.indexOf("INS*Y*18"), "NM1 after INS");
        assertTrue(out.indexOf("N3*123") > out.indexOf("NM1*IL"), "N3 after NM1");
        assertTrue(out.indexOf("N4*SPRING") > out.indexOf("N3*123"), "N4 after N3");
        assertTrue(out.indexOf("DMG*D8") > out.indexOf("N4*SPRING"), "DMG after N4");
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

        contains(out, "N3*123 MAIN ST~");     // residence 2100A
        contains(out, "N4*SPRINGFIELD*IL*62704~");
        contains(out, "NM1*31~");             // mailing 2100C marker
        contains(out, "N3*PO BOX 99~");
        contains(out, "N4*SPRINGFIELD*IL*62705~");
        assertTrue(out.indexOf("NM1*31") > out.indexOf("N4*SPRINGFIELD*IL*62704"),
                "mailing 2100C must render after residence 2100A");
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

        contains(out, "HD*001");
        contains(out, "DTP*348*D8*20260101~");   // Loop 2300 coverage begin
        contains(out, "DTP*349*D8*20261231~");   // Loop 2300 coverage end
        // The coverage-date DTPs render after the HD segment, inside the same 2300 loop.
        assertTrue(out.indexOf("DTP*348") > out.indexOf("HD*001"), "begin DTP must follow HD");
        assertTrue(out.indexOf("DTP*349") > out.indexOf("DTP*348"), "end DTP must follow begin DTP");
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

        contains(out, "NM1*IL*1*DOE*JANE****34*123456789~");
    }

    private static void contains(String haystack, String needle) {
        assertTrue(haystack.contains(needle), () -> "expected 834 to contain: " + needle + "\n---\n" + haystack);
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
