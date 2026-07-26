/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.GenerationError.Phase;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.spec.CharacterClass;
import com.fastChickensHR.edi.x834.testsupport.TestFixtures;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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

    private Header buildHeaderWithSponsor(String planSponsorName) {
        return new Header.Builder(context)
                .setReferenceIdentification("TEST834")
                .setMasterPolicyNumber("TEST-POL-001")
                .setPlanSponsorName(planSponsorName)
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

    /** Unwraps a successful generation, failing the test if it did not succeed. */
    private static String assertSuccess(GenerationResult result) {
        if (result instanceof GenerationResult.Success success) {
            return success.document();
        }
        return fail("expected successful generation, got: " + result);
    }

    /** Unwraps a failed generation's error list, failing the test if it actually succeeded. */
    private static List<GenerationError> assertFailure(GenerationResult result) {
        if (result instanceof GenerationResult.Failure failure) {
            return failure.errors();
        }
        return fail("expected failed generation, got: " + result);
    }

    @Test
    void singleMinimalMemberRendersExpected834() {
        // The golden pins the whole builder-path document, including the SE01 transaction segment
        // count (ISA/GS sit outside the ST/SE envelope; header + one INS + SE == 8).
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        String output = assertSuccess(doc.generateDocument());

        TestFixtures.assertMatchesGolden("golden/builder-single-minimal-member.834", output);
    }

    @Test
    void multipleMinimalMembersRenderExpected834() {
        // The golden pins a three-member document: SE01 grows by exactly one INS per member, while
        // GE01 and IEA01 stay at 1 — one transaction set inside one functional group inside one interchange.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .addMember(buildMinimalMember())
                .build();

        String output = assertSuccess(doc.generateDocument());

        TestFixtures.assertMatchesGolden("golden/builder-three-minimal-members.834", output);
    }

    @Test
    void missingTrailerFailsWithBuildErrorLocatedAtTrailer() {
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .addMember(buildMinimalMember())
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.phase() == Phase.BUILD && e.location().equals("Trailer")),
                "a BUILD-phase error should be located at the Trailer");
    }

    @Test
    void rejectsElementSeparatorInAValue() {
        // The default element separator is '*'. A sponsor name carrying it would, unescaped,
        // split one N1 element into two — X12 has no escape mechanism, so generation must reject.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("ACME*CORP"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().allMatch(e -> e.phase() == Phase.RENDER), "delimiter violations are render-phase");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("ACME*CORP")),
                "an error should name the offending value");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("element separator")),
                "an error should name the delimiter");
    }

    @Test
    void rejectsSegmentTerminatorInAValue() {
        // The default segment terminator is '~'. Embedded in a value it would terminate the
        // segment early, truncating the interchange.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("ACME~CORP"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("segment terminator")),
                "an error should name the delimiter");
    }

    @Test
    void aggregatesEveryDelimiterViolationInOneResult() {
        // The pass collects all offending values across the document, so a single run reports
        // them together rather than one failed generation at a time.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("ACME*CORP~LLC"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("element separator")),
                "should report the '*' violation");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("segment terminator")),
                "should report the '~' violation");
    }

    @Test
    void acceptsDelimiterFreeValues() {
        // A document whose values carry no reserved delimiter still generates normally —
        // the guard rejects only genuine corruption.
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("ACME CORP LLC"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        assertInstanceOf(GenerationResult.Success.class, doc.generateDocument());
    }

    @Test
    void interchangesSpeakTheExtendedCharacterSetUnlessNarrowed() {
        // The default has to be the wider set: lower case is legal 834 content, and narrowing is the
        // trading partner's choice to make, not the library's.
        assertEquals(CharacterClass.EXTENDED, new X834Context().getCharacterClass());

        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("Acme Corp"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        assertInstanceOf(GenerationResult.Success.class, doc.generateDocument());
    }

    @Test
    void rejectsLowerCaseWhenTheInterchangeIsNarrowedToTheBasicSet() {
        // Some partners accept only the basic set. The same document that generates above must now
        // fail, naming the offending character and where in the value it sits.
        context.setCharacterClass(CharacterClass.BASIC);
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("Acme Corp"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(buildMinimalMember())
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().allMatch(e -> e.phase() == Phase.RENDER), "character-set violations are render-phase");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("'c' at position 1")),
                "an error should name the character and its position, got: " + errors);
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("BASIC")),
                "an error should name the set that rejected it, got: " + errors);
    }

    @Test
    void rejectsAValueLongerThanItsElementPermits() {
        // NM103 is element 1035, AN 1/60, and nothing on the NM1 builder checks it — this is a
        // position whose only length enforcement is the published spec.
        Member member = buildMinimalMember();
        member.setLastName("D".repeat(61));
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(member)
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.location().equals("NM1") && e.message().contains("element 1035")),
                "an error should name the element whose bounds were exceeded, got: " + errors);
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("61 characters")),
                "an error should say how long the value actually is, got: " + errors);
    }

    @Test
    void rejectsACodeTheElementDoesNotPermit() {
        // DMG03 takes a raw string all the way to the wire: before the spec ring, nothing anywhere
        // checked that a gender was an element-1068 code.
        Member member = buildMinimalMember();
        member.setLastName("DOE");
        member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
        member.setGender("Q");
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(member)
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.location().equals("DMG") && e.message().contains("element 1068")),
                "an error should name the element whose code list rejected the value, got: " + errors);
    }

    @Test
    void acceptsACodeTheElementDoesPermit() {
        Member member = buildMinimalMember();
        member.setLastName("DOE");
        member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
        member.setGender("F");
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeader())
                .withTrailer(new Trailer.Builder(context))
                .addMember(member)
                .build();

        assertInstanceOf(GenerationResult.Success.class, doc.generateDocument());
    }

    @Test
    void aggregatesSpecViolationsWithEachOtherAndWithDelimiterViolations() {
        // One channel, one pass: a caller fixing its data should see every problem at once rather
        // than one failed generation at a time.
        Member member = buildMinimalMember();
        member.setLastName("D".repeat(61));
        member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
        member.setGender("Q");
        X834Document doc = new X834Document.Builder(context)
                .withHeader(buildHeaderWithSponsor("ACME*CORP"))
                .withTrailer(new Trailer.Builder(context))
                .addMember(member)
                .build();

        List<GenerationError> errors = assertFailure(doc.generateDocument());
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("element separator")), "the delimiter violation");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("element 1035")), "the length violation");
        assertTrue(errors.stream().anyMatch(e -> e.message().contains("element 1068")), "the code violation");
    }
}
