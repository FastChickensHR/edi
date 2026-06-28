/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class x834DocumentTest {

    private x834Context context;

    @BeforeEach
    void setUp() {
        context = new x834Context()
                .setSenderID("SENDER01")
                .setReceiverID("RECEIVER1")
                .setDocumentDate(LocalDateTime.of(2024, 1, 1, 0, 0));
    }

    private Header buildHeader() {
        return new Header.Builder(context)
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("1")
                .setReferenceIdentification("TEST834")
                .setMasterPolicyNumber("TEST-POL-001")
                .setPlanSponsorName("TEST SPONSOR")
                .setPayerName("TEST PAYER")
                .build();
    }

    private Member buildMinimalMember() {
        Member member = new Member();
        member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        member.setRelationshipCode(IndividualRelationshipCode.EMPLOYEE);
        member.setMemberIndicator(MemberIndicator.INSURED);
        return member;
    }

    /**
     * Extracts the SE01 value (transaction segment count) from the rendered document.
     * Finds the SE segment and parses the first element as an integer.
     */
    private int extractSe01(String document) {
        // SE segment format: "SE*<count>*<controlNumber>~"
        int seIndex = document.indexOf("\nSE*");
        if (seIndex == -1) {
            seIndex = document.indexOf("SE*");
        } else {
            seIndex++; // skip the newline
        }
        assertTrue(seIndex >= 0, "SE segment not found in document");
        int start = seIndex + 3; // skip "SE*"
        int end = document.indexOf('*', start);
        return Integer.parseInt(document.substring(start, end));
    }

    /**
     * Extracts the GE01 value (number of transaction sets) from the rendered document.
     */
    private int extractGe01(String document) {
        int geIndex = document.indexOf("\nGE*");
        if (geIndex == -1) {
            geIndex = document.indexOf("GE*");
        } else {
            geIndex++;
        }
        assertTrue(geIndex >= 0, "GE segment not found in document");
        int start = geIndex + 3; // skip "GE*"
        int end = document.indexOf('*', start);
        return Integer.parseInt(document.substring(start, end));
    }

    /**
     * Extracts the IEA01 value (number of included groups) from the rendered document.
     */
    private int extractIea01(String document) {
        int ieaIndex = document.indexOf("\nIEA*");
        if (ieaIndex == -1) {
            ieaIndex = document.indexOf("IEA*");
        } else {
            ieaIndex++;
        }
        assertTrue(ieaIndex >= 0, "IEA segment not found in document");
        int start = ieaIndex + 4; // skip "IEA*"
        int end = document.indexOf('*', start);
        return Integer.parseInt(document.substring(start, end));
    }

    @Test
    void testSingleMemberProducesCorrectSe01() throws ValidationException {
        // Header generates 8 segments: ISA, GS, ST, BGN, DTP, REF, N1(sponsor), N1(payer)
        // ISA and GS are outside the ST/SE envelope (-2)
        // Minimal member generates 1 segment: INS
        // SE itself counts as 1 (+1)
        // Expected SE01 = (8 - 2) + 1 + 1 = 8
        x834Document doc = new x834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        assertTrue(doc.isValid(), "Document should be valid");
        Optional<String> output = doc.generateDocument();
        assertTrue(output.isPresent(), "Document should generate successfully");

        assertEquals(8, extractSe01(output.get()));
    }

    @Test
    void testMultipleMembersProduceHigherSe01ThanSingleMember() throws ValidationException {
        x834Document singleMemberDoc = new x834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        x834Document tenMemberDoc = new x834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .build();

        String singleOutput = singleMemberDoc.generateDocument().orElseThrow();
        String tenOutput = tenMemberDoc.generateDocument().orElseThrow();

        int singleSe01 = extractSe01(singleOutput);
        int tenSe01 = extractSe01(tenOutput);

        // Each additional minimal member adds 1 INS segment, so SE01 increases by 1 per member
        assertEquals(singleSe01 + 9, tenSe01,
                "Ten-member document should have SE01 nine higher than single-member document");
    }

    @Test
    void testGe01AndIea01AreAlwaysOne() throws ValidationException {
        x834Document doc = new x834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .build();

        String output = doc.generateDocument().orElseThrow();

        assertEquals(1, extractGe01(output), "GE01 should always be 1 for an 834 document");
        assertEquals(1, extractIea01(output), "IEA01 should always be 1 for an 834 document");
    }

    @Test
    void testDocumentRequiresTrailerBuilder() {
        x834Document doc = new x834Document.Builder(context)
                .withHeader(buildHeader())
                .addMember(buildMinimalMember())
                .build();

        assertFalse(doc.isValid(), "Document without trailer should be invalid");
        assertTrue(doc.getErrors().stream().anyMatch(e -> e.contains("Trailer")));
    }
}
