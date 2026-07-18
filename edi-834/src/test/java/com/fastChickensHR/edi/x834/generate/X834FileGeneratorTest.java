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
        // HD and REF-extension render AFTER all members (document-level trailing segments)
        assertTrue(out.indexOf("HD*001") > out.indexOf("INS*Y*01"), "HD must render after the members");
        assertTrue(out.indexOf("REF*ZZ*NORTH") > out.indexOf("INS*Y*01"), "REF extension must render after the members");
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
