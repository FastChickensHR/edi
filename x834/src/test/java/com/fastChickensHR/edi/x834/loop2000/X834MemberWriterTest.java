/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;
import com.fastChickensHR.edi.x834.segments.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class X834MemberWriterTest {

    private final X834Context context = new X834Context()
            .setInterchangeControlNumber("000000001")
            .setGroupControlNumber("1");
    private final X834MemberWriter writer = new X834MemberWriter(context);

    private Member baseSubscriber() {
        Member member = new Member();
        member.setMemberIndicator(MemberIndicator.INSURED);
        member.setRelationshipCode(IndividualRelationshipCode.SELF);
        member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        return member;
    }

    private String render(List<Segment> segments) {
        StringBuilder sb = new StringBuilder();
        for (Segment segment : segments) {
            segment.setContext(context);
            sb.append(segment.render());
        }
        return sb.toString();
    }

    @Test
    void emitsMemberNameNM1WhenLastNamePresent() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setFirstName("JANE");
        member.setMiddleName("Q");

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("NM1*IL*1*DOE*JANE*Q~"),
                () -> "expected member-name NM1 loop 2100A; got:\n" + out);
    }

    @Test
    void emitsMemberNameWithIdentificationCodeWhenNM108AndNM109Present() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setFirstName("JANE");
        member.setNameIdQualifier("34"); // SSN
        member.setNameId("123456789");

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("NM1*IL*1*DOE*JANE****34*123456789~"),
                () -> "expected member NM1 with NM108/NM109 (SSN); got:\n" + out);
    }

    @Test
    void omitsIdentificationCodeWhenNM108AndNM109NotBothPresent() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setNameIdQualifier("34"); // qualifier only, no code

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("NM1*IL*1*DOE~"),
                () -> "unpaired NM108 must be dropped (no NM1 id elements); got:\n" + out);
    }

    @Test
    void emitsDemographicsDMGWhenBirthDatePresent() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
        member.setGender("M");

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("DMG*D8*19800115*M~"),
                () -> "expected member demographics DMG; got:\n" + out);
    }

    @Test
    void emitsResidenceAddressN3N4WhenAddressPresent() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setAddressLine1("123 MAIN ST");
        member.setAddressLine2("APT 4");
        member.setCity("SPRINGFIELD");
        member.setState("IL");
        member.setZipCode("62704");

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("N3*123 MAIN ST*APT 4~"), () -> "expected residence N3; got:\n" + out);
        assertTrue(out.contains("N4*SPRINGFIELD*IL*62704~"), () -> "expected residence N4; got:\n" + out);
    }

    @Test
    void emitsMember2100ASegmentsInSpecOrder() throws ValidationException {
        Member member = baseSubscriber();
        member.setSubscriberNumber("E12345");
        member.setLastName("DOE");
        member.setFirstName("JANE");
        member.setAddressLine1("123 MAIN ST");
        member.setCity("SPRINGFIELD");
        member.setState("IL");
        member.setZipCode("62704");
        member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
        member.setGender("F");

        String out = render(writer.toSegments(member));

        // Loop 2000 -> Loop 2100A order: INS, REF, [DTP], NM1, N3, N4, DMG
        int ins = out.indexOf("INS*");
        int nm1 = out.indexOf("NM1*IL");
        int n3 = out.indexOf("N3*");
        int n4 = out.indexOf("N4*");
        int dmg = out.indexOf("DMG*");
        assertTrue(ins >= 0 && nm1 > ins, () -> "NM1 must follow INS; got:\n" + out);
        assertTrue(n3 > nm1, () -> "N3 must follow NM1; got:\n" + out);
        assertTrue(n4 > n3, () -> "N4 must follow N3; got:\n" + out);
        assertTrue(dmg > n4, () -> "DMG must follow N4; got:\n" + out);
    }

    @Test
    void omitsAll2100ASegmentsWhenNoNameOrAddressOrDemographics() throws ValidationException {
        // A member with only the INS-level data must render byte-for-byte as before:
        // no NM1/N3/N4/DMG appear unless their source data is present.
        Member member = baseSubscriber();
        member.setSubscriberNumber("E12345");

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("INS*"), "INS should always be present");
        assertTrue(out.contains("REF*OF*E12345~"), "subscriber number REF should be present");
        assertFalse(out.contains("NM1"), () -> "no NM1 without a name; got:\n" + out);
        assertFalse(out.contains("N3"), () -> "no N3 without an address; got:\n" + out);
        assertFalse(out.contains("N4"), () -> "no N4 without an address; got:\n" + out);
        assertFalse(out.contains("DMG"), () -> "no DMG without demographics; got:\n" + out);
    }

    @Test
    void emitsResidenceN4OnlyWhenCityStateZipAllPresent() throws ValidationException {
        // N4 (MemberResidenceCityStateZipCode) requires city+state+zip; a partial address
        // must not blow up generation — the N4 is simply skipped.
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setAddressLine1("123 MAIN ST");
        member.setCity("SPRINGFIELD");
        // no state, no zip

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("N3*123 MAIN ST~"), () -> "N3 should still render; got:\n" + out);
        assertFalse(out.contains("N4"), () -> "N4 must be skipped when state/zip missing; got:\n" + out);
    }

    @Test
    void emitsMailingAddress2100CWhenMailingAddressPresent() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        // residence (2100A)
        member.setAddressLine1("123 MAIN ST");
        member.setCity("SPRINGFIELD");
        member.setState("IL");
        member.setZipCode("62704");
        // mailing (2100C) — a distinct PO box
        member.addAddress(Address.builder()
                .type(AddressType.MAILING)
                .line1("PO BOX 99")
                .city("SPRINGFIELD")
                .state("IL")
                .zipCode("62705")
                .build());

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("NM1*31~"), () -> "expected 2100C postal-address marker NM1*31; got:\n" + out);
        assertTrue(out.contains("N3*PO BOX 99~"), () -> "expected mailing N3; got:\n" + out);
        assertTrue(out.contains("N4*SPRINGFIELD*IL*62705~"), () -> "expected mailing N4; got:\n" + out);
        // 2100C must come after the 2100A residence (N4*...*62704) block.
        assertTrue(out.indexOf("NM1*31") > out.indexOf("N4*SPRINGFIELD*IL*62704"),
                () -> "mailing loop must follow residence loop; got:\n" + out);
    }

    @Test
    void omitsMailingAddressWhenNoMailingType() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.setAddressLine1("123 MAIN ST");
        member.setCity("SPRINGFIELD");
        member.setState("IL");
        member.setZipCode("62704");

        String out = render(writer.toSegments(member));

        assertFalse(out.contains("NM1*31"), () -> "no 2100C without a mailing address; got:\n" + out);
    }

    @Test
    void workAddressIsAcceptedButNotSerialized() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.addAddress(Address.builder()
                .type(AddressType.WORK)
                .line1("500 OFFICE PKWY").city("SPRINGFIELD").state("IL").zipCode("62704")
                .build());

        String out = render(writer.toSegments(member));

        assertFalse(out.contains("500 OFFICE PKWY"), () -> "WORK address must not be serialized; got:\n" + out);
    }

    @Test
    void dependentsAlsoGet2100ASegments() throws ValidationException {
        Member subscriber = baseSubscriber();
        subscriber.setLastName("DOE");
        subscriber.setFirstName("JANE");

        DependentMember dependent = new DependentMember();
        dependent.setMemberIndicator(MemberIndicator.NOT_INSURED);
        dependent.setRelationshipCode(IndividualRelationshipCode.SPOUSE);
        dependent.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        dependent.setLastName("DOE");
        dependent.setFirstName("JOHN");
        subscriber.addDependent(dependent);

        String out = render(writer.toSegments(subscriber));

        assertTrue(out.contains("NM1*IL*1*DOE*JANE~"), () -> "subscriber name; got:\n" + out);
        assertTrue(out.contains("NM1*IL*1*DOE*JOHN~"), () -> "dependent name; got:\n" + out);
        assertEquals(out.indexOf("JANE") < out.indexOf("JOHN"), true,
                "subscriber loop must precede dependent loop");
    }

    @Test
    void emitsNoReportingCategoryBlockWhenMemberHasNone() throws ValidationException {
        Member member = baseSubscriber();

        String out = render(writer.toSegments(member));

        assertFalse(out.contains("LS*2700"), () -> "no LS when there are no categories; got:\n" + out);
        assertFalse(out.contains("LE*2700"), () -> "no LE when there are no categories; got:\n" + out);
    }

    @Test
    void emitsReportingCategoryLoopMatchingTheBcbsmExample() throws ValidationException {
        // BCBSM companion guide (pp. 11-12) example: two 2750 occurrences under one LS/LE block.
        Member member = baseSubscriber();
        member.addReportingCategory(new ReportingCategory("INDIVIDUALREPNAME", "JOHN DOE"));
        member.addReportingCategory(new ReportingCategory("RELATIONSHIP", "4"));

        String flat = render(writer.toSegments(member)).replace("\n", "");

        assertTrue(flat.contains(
                        "LS*2700~"
                        + "LX*1~"
                        + "N1*75*INDIVIDUALREPNAME~"
                        + "REF*ZZ*JOHN DOE~"
                        + "LX*2~"
                        + "N1*75*RELATIONSHIP~"
                        + "REF*ZZ*4~"
                        + "LE*2700~"),
                () -> "expected the BCBSM 2700/2750 block; got:\n" + flat);
    }

    @Test
    void emitsReportingCategoryBlockAfterTheMemberCoverageSegments() throws ValidationException {
        Member member = baseSubscriber();
        member.setLastName("DOE");
        member.addReportingCategory(new ReportingCategory("RELATIONSHIP", "4"));

        String out = render(writer.toSegments(member));

        assertTrue(out.indexOf("NM1*IL") < out.indexOf("LS*2700"),
                () -> "reporting-category block must follow the 2100 member segments; got:\n" + out);
    }

    @Test
    void emitsReportingCategoryDateWhenPresent() throws ValidationException {
        Member member = baseSubscriber();
        ReportingCategory category = new ReportingCategory("APPLICATIONDATE", "X");
        category.setDate(LocalDateTime.of(2026, 3, 1, 0, 0));
        category.setDateQualifier("007"); // application/effective date qualifier
        member.addReportingCategory(category);

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("DTP*007*D8*20260301~"),
                () -> "expected a 2750 DTP when a category carries a date; got:\n" + out);
    }

    @Test
    void honorsACustomReferenceQualifier() throws ValidationException {
        Member member = baseSubscriber();
        ReportingCategory category = new ReportingCategory("SUBGROUP", "0042");
        category.setReferenceQualifier("DX"); // BCN carries sub-group under REF*DX rather than ZZ
        member.addReportingCategory(category);

        String out = render(writer.toSegments(member));

        assertTrue(out.contains("REF*DX*0042~"),
                () -> "expected the REF to use the category's own qualifier; got:\n" + out);
    }
}
