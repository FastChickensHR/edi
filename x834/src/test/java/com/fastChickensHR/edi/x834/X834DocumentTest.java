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
import com.fastChickensHR.edi.x834.testsupport.TestFixtures;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class X834DocumentTest {

    private X834Context context;

    @BeforeEach
    void setUp() {
        context = new X834Context()
                .setSenderID("SENDER01")
                .setReceiverID("RECEIVER1")
                .setDocumentDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("1");
    }

    private Header buildHeader() {
        return new Header.Builder(context)
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

    @Test
    void singleMinimalMemberRendersExpected834() throws ValidationException {
        // The golden pins the whole builder-path document, including the SE01 transaction segment
        // count (ISA/GS sit outside the ST/SE envelope; header + one INS + SE == 8).
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        assertTrue(doc.isValid(), "Document should be valid");
        String output = doc.generateDocument().orElseThrow(() -> new AssertionError("document should generate"));

        TestFixtures.assertMatchesGolden("golden/builder-single-minimal-member.834", output);
    }

    @Test
    void multipleMinimalMembersRenderExpected834() throws ValidationException {
        // The golden pins a three-member document: SE01 grows by exactly one INS per member, while
        // GE01 and IEA01 stay at 1 — one transaction set inside one functional group inside one interchange.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .build();

        String output = doc.generateDocument().orElseThrow(() -> new AssertionError("document should generate"));

        TestFixtures.assertMatchesGolden("golden/builder-three-minimal-members.834", output);
    }

    @Test
    void testDocumentRequiresTrailerBuilder() {
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .addMember(buildMinimalMember())
                .build();

        assertFalse(doc.isValid(), "Document without trailer should be invalid");
        assertTrue(doc.getErrors().stream().anyMatch(e -> e.contains("Trailer")));
    }
}
