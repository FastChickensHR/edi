/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.data.CoordinationOfBenefitsCode;
import com.fastChickensHR.edi.x834.data.DisabilityTypeCode;
import com.fastChickensHR.edi.x834.data.FrequencyCode;
import com.fastChickensHR.edi.x834.data.HealthRelatedCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.data.PayerResponsibilitySequenceCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.Address;
import com.fastChickensHR.edi.x834.loop2000.AddressType;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.GenderCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.HealthInformation;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Income;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Language;
import com.fastChickensHR.edi.x834.loop2000.loop2200.Disability;
import com.fastChickensHR.edi.x834.loop2000.loop2300.HealthCoverage;
import com.fastChickensHR.edi.x834.loop2000.loop2310.Provider;
import com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefits;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class X834MemberWriterTest {

  private final X834Context context =
      new X834Context().setInterchangeControlNumber("000000001").setGroupControlNumber("1");
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
  void emitsEnrollmentDateAsDtp300DistinctFromCoverageBeginDtp356() throws ValidationException {
    // enrollmentDate is the member-level enrollment date: DTP*300 (Enrollment Signature Date),
    // the code the 834 TR3 permits at Loop 2000. It must NOT collapse onto coverageStartDate's
    // DTP*356 (Eligibility Begin) — both are emitted, as separate member-level DTP segments.
    Member member = baseSubscriber();
    member.setEnrollmentDate(LocalDateTime.of(2026, 2, 1, 0, 0));
    member.setCoverageStartDate(LocalDateTime.of(2026, 1, 1, 0, 0));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("DTP*300*D8*20260201~"),
        () -> "expected enrollment DTP*300 (Enrollment Signature Date); got:\n" + out);
    assertTrue(
        out.contains("DTP*356*D8*20260101~"),
        () -> "expected coverage-begin DTP*356 (Eligibility Begin); got:\n" + out);
    assertFalse(
        out.contains("DTP*382"),
        () -> "382 (Enrollment) is not a permitted member-level DTP01; got:\n" + out);
    assertTrue(
        out.indexOf("DTP*300") < out.indexOf("DTP*356"),
        () -> "enrollment DTP should precede the coverage-begin DTP; got:\n" + out);
  }

  @Test
  void omitsEnrollmentDtpWhenEnrollmentDateAbsent() throws ValidationException {
    Member member = baseSubscriber();
    member.setCoverageStartDate(LocalDateTime.of(2026, 1, 1, 0, 0));

    String out = render(writer.toSegments(member));

    assertFalse(
        out.contains("DTP*300"), () -> "no enrollment DTP without an enrollmentDate; got:\n" + out);
  }

  @Test
  void emitsMemberNameNM1WhenLastNamePresent() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setFirstName("JANE");
    member.setMiddleName("Q");

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("NM1*IL*1*DOE*JANE*Q~"),
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

    assertTrue(
        out.contains("NM1*IL*1*DOE*JANE****34*123456789~"),
        () -> "expected member NM1 with NM108/NM109 (SSN); got:\n" + out);
  }

  @Test
  void omitsIdentificationCodeWhenNM108AndNM109NotBothPresent() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setNameIdQualifier("34"); // qualifier only, no code

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("NM1*IL*1*DOE~"),
        () -> "unpaired NM108 must be dropped (no NM1 id elements); got:\n" + out);
  }

  @Test
  void emitsDemographicsDMGWhenBirthDatePresent() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
    member.setGender(GenderCode.MALE);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("DMG*D8*19800115*M~"), () -> "expected member demographics DMG; got:\n" + out);
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
    assertTrue(
        out.contains("N4*SPRINGFIELD*IL*62704~"), () -> "expected residence N4; got:\n" + out);
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
    member.setGender(GenderCode.FEMALE);

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
    assertTrue(out.contains("REF*0F*E12345~"), "subscriber number REF should be present");
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
    assertFalse(
        out.contains("N4"), () -> "N4 must be skipped when state/zip missing; got:\n" + out);
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
    Address mailing = new Address();
    mailing.setType(AddressType.MAILING);
    mailing.setLine1("PO BOX 99");
    mailing.setCity("SPRINGFIELD");
    mailing.setState("IL");
    mailing.setZipCode("62705");
    member.addAddress(mailing);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("NM1*31*1~"),
        () -> "expected 2100C postal-address marker NM1*31*1; got:\n" + out);
    assertTrue(out.contains("N3*PO BOX 99~"), () -> "expected mailing N3; got:\n" + out);
    assertTrue(out.contains("N4*SPRINGFIELD*IL*62705~"), () -> "expected mailing N4; got:\n" + out);
    // 2100C must come after the 2100A residence (N4*...*62704) block.
    assertTrue(
        out.indexOf("NM1*31") > out.indexOf("N4*SPRINGFIELD*IL*62704"),
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
    assertEquals(
        out.indexOf("JANE") < out.indexOf("JOHN"),
        true,
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

    assertTrue(
        flat.contains(
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

    assertTrue(
        out.indexOf("NM1*IL") < out.indexOf("LS*2700"),
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

    assertTrue(
        out.contains("DTP*007*D8*20260301~"),
        () -> "expected a 2750 DTP when a category carries a date; got:\n" + out);
  }

  @Test
  void honorsACustomReferenceQualifier() throws ValidationException {
    Member member = baseSubscriber();
    ReportingCategory category = new ReportingCategory("SUBGROUP", "0042");
    category.setReferenceQualifier("DX"); // BCN carries sub-group under REF*DX rather than ZZ
    member.addReportingCategory(category);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("REF*DX*0042~"),
        () -> "expected the REF to use the category's own qualifier; got:\n" + out);
  }

  @Test
  void emitsMemberCommunicationsPerFromExplicitChannels() throws ValidationException {
    Member member = baseSubscriber();
    member.addCommunication(CommunicationNumberQualifier.ELECTRONIC_MAIL, "jane@example.com");
    member.addCommunication(CommunicationNumberQualifier.WORK_PHONE, "5559876543");

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("PER*IP**EM*jane@example.com*WP*5559876543~"),
        () -> "expected one PER carrying both channels in order; got:\n" + out);
  }

  @Test
  void emitsPhoneNumberAndEmailUnderTheirConventionalQualifiers() throws ValidationException {
    // The phoneNumber/email conveniences finally reach the wire: HP and EM. Before PER support
    // existed they were settable but never serialized, so the values vanished silently.
    Member member = baseSubscriber();
    member.setPhoneNumber("5551234567");
    member.setEmail("jane@example.com");

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("PER*IP**HP*5551234567*EM*jane@example.com~"),
        () -> "expected phoneNumber as HP and email as EM; got:\n" + out);
  }

  @Test
  void anExplicitChannelTakesPrecedenceOverTheConvenienceField() throws ValidationException {
    // A caller who says "this number is a work phone" means it; phoneNumber must not also add
    // the same person's number a second time under HP.
    Member member = baseSubscriber();
    member.addCommunication(CommunicationNumberQualifier.HOME_PHONE, "5550001111");
    member.setPhoneNumber("5551234567");

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("PER*IP**HP*5550001111~"),
        () -> "expected the explicit HP channel to win; got:\n" + out);
    assertFalse(
        out.contains("5551234567"),
        () -> "the convenience field should not add a second HP; got:\n" + out);
  }

  @Test
  void emitsThePerAfterTheNm1AndBeforeTheResidenceAddress() throws ValidationException {
    // 834 Loop 2100A order: NM1, PER, N3, N4, DMG.
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setPhoneNumber("5551234567");
    member.setAddressLine1("1 MAIN ST");
    member.setCity("ANYTOWN");
    member.setState("MN");
    member.setZipCode("55555");
    member.setBirthDate(LocalDateTime.of(1990, 5, 4, 0, 0));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("NM1*IL") < out.indexOf("PER*IP"),
        () -> "PER must follow the member NM1; got:\n" + out);
    assertTrue(
        out.indexOf("PER*IP") < out.indexOf("N3*"),
        () -> "PER must precede the residence N3; got:\n" + out);
    assertTrue(
        out.indexOf("N4*") < out.indexOf("DMG*"), () -> "N4 must still precede DMG; got:\n" + out);
  }

  @Test
  void omitsThePerWhenTheMemberHasNoWayToBeReached() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");

    String out = render(writer.toSegments(member));

    assertFalse(
        out.contains("PER"),
        () -> "no PER for a member carrying no communication numbers; got:\n" + out);
  }

  @Test
  void ignoresAnIncompleteChannelRatherThanEmittingADanglingQualifier() throws ValidationException {
    // A qualifier with no number would violate X12 rule P0304; it is skipped, and with nothing
    // else to say the PER is suppressed entirely rather than rendered empty.
    Member member = baseSubscriber();
    member.addCommunication(CommunicationNumberQualifier.HOME_PHONE, "");

    String out = render(writer.toSegments(member));

    assertFalse(out.contains("PER"), () -> "an empty number contributes no channel; got:\n" + out);
  }

  @Test
  void rejectsAMemberCarryingMoreChannelsThanOnePerCanHold() throws ValidationException {
    // The 834 permits three. A fourth is a loud failure rather than a silently dropped
    // contact number — the data-loss class this segment exists to end.
    Member member = baseSubscriber();
    member.addCommunication(CommunicationNumberQualifier.ELECTRONIC_MAIL, "jane@example.com");
    member.addCommunication(CommunicationNumberQualifier.HOME_PHONE, "5551234567");
    member.addCommunication(CommunicationNumberQualifier.WORK_PHONE, "5559876543");
    member.addCommunication(CommunicationNumberQualifier.CELLULAR_PHONE, "5550000000");

    ValidationException ex =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(ex.getMessage().contains("at most 3"), ex.getMessage());
  }

  @Test
  void emitsTheIcmAfterTheDmg() throws ValidationException {
    // 834 Loop 2100A order: NM1, PER, N3, N4, DMG, ICM.
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
    Income income = new Income(FrequencyCode.MONTHLY, "4500");
    income.setLocationIdentifier("DEPT42");
    member.setIncome(income);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("ICM*4*4500**DEPT42~"),
        () -> "expected the BCBS Kansas ICM shape; got:\n" + out);
    assertTrue(
        out.indexOf("DMG*") < out.indexOf("ICM*"),
        () -> "the ICM must follow the DMG; got:\n" + out);
  }

  @Test
  void emitsTheIcmEvenWhenTheMemberHasNoDemographics() throws ValidationException {
    // The DMG is emitted only when a birth date is present; the ICM must not depend on it.
    Member member = baseSubscriber();
    member.setIncome(new Income(FrequencyCode.HOURLY, "24.50"));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("ICM*H*24.50~"), () -> "expected the ICM; got:\n" + out);
    assertFalse(out.contains("DMG*"), () -> "no DMG without a birth date; got:\n" + out);
  }

  @Test
  void omitsTheIcmWhenTheMemberHasNoIncome() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");

    String out = render(writer.toSegments(member));

    assertFalse(out.contains("ICM"), () -> "no ICM without an income; got:\n" + out);
  }

  @Test
  void rejectsAnIncomeCarryingOnlyTheDepartmentNumber() throws ValidationException {
    // The Kansas case: a sponsor holding the department number but not the wage cannot emit the
    // department alone, because ICM01/ICM02 are mandatory.
    Member member = baseSubscriber();
    Income income = new Income();
    income.setLocationIdentifier("DEPT42");
    member.setIncome(income);

    ValidationException ex =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(ex.getMessage().contains("ICM01"), ex.getMessage());
  }

  @Test
  void emitsAnIcmForEachDependentThatCarriesOne() throws ValidationException {
    Member subscriber = baseSubscriber();
    subscriber.setIncome(new Income(FrequencyCode.MONTHLY, "4500"));
    DependentMember dependent = new DependentMember();
    dependent.setMemberIndicator(MemberIndicator.NOT_INSURED);
    dependent.setRelationshipCode(IndividualRelationshipCode.SPOUSE);
    dependent.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
    dependent.setIncome(new Income(FrequencyCode.WEEKLY, "800"));
    subscriber.addDependent(dependent);

    String out = render(writer.toSegments(subscriber));

    assertTrue(out.contains("ICM*4*4500~"), () -> "expected the subscriber's ICM; got:\n" + out);
    assertTrue(out.contains("ICM*1*800~"), () -> "expected the dependent's own ICM; got:\n" + out);
  }

  @Test
  void emitsThe2100ATailInSpecOrderIcmThenHlhThenLui() throws ValidationException {
    // 834 Loop 2100A order: NM1, PER, N3, N4, DMG, EC, ICM, AMT, HLH, LUI.
    Member member = baseSubscriber();
    member.setLastName("DOE");
    member.setBirthDate(LocalDateTime.of(1980, 1, 15, 0, 0));
    member.setIncome(new Income(FrequencyCode.MONTHLY, "4500"));
    member.setHealthInformation(new HealthInformation(HealthRelatedCode.TOBACCO_USE));
    member.addLanguage(new Language("SPANISH"));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("HLH*T~"), () -> "expected the HLH; got:\n" + out);
    assertTrue(out.contains("LUI***SPANISH~"), () -> "expected the LUI; got:\n" + out);
    assertTrue(out.indexOf("DMG*") < out.indexOf("ICM*"), () -> "ICM follows DMG; got:\n" + out);
    assertTrue(out.indexOf("ICM*") < out.indexOf("HLH*"), () -> "HLH follows ICM; got:\n" + out);
    assertTrue(out.indexOf("HLH*") < out.indexOf("LUI*"), () -> "LUI follows HLH; got:\n" + out);
  }

  @Test
  void emitsOneLuiPerLanguageInOrder() throws ValidationException {
    Member member = baseSubscriber();
    member.addLanguage(new Language("SPANISH"));
    member.addLanguage(new Language("VIETNAMESE"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("LUI***SPANISH~") < out.indexOf("LUI***VIETNAMESE~"),
        () -> "expected both languages, in the order added; got:\n" + out);
  }

  @Test
  void emitsTheDisabilityLoopWithItsPeriodDates() throws ValidationException {
    // BCBSM asks for DTP*360 / DTP*361. The guide reads "DTP01 … Start / DTP02 … End", but
    // DTP02 is the date-format qualifier, so two dates are two DTP segments.
    Member member = baseSubscriber();
    Disability disability = new Disability(DisabilityTypeCode.SHORT_TERM_DISABILITY);
    disability.setStartDate(LocalDateTime.of(2026, 3, 1, 0, 0));
    disability.setEndDate(LocalDateTime.of(2026, 6, 30, 0, 0));
    member.addDisability(disability);

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("DSB*1~"), () -> "expected the DSB; got:\n" + out);
    assertTrue(
        out.contains("DTP*360*D8*20260301~"), () -> "expected the period start; got:\n" + out);
    assertTrue(out.contains("DTP*361*D8*20260630~"), () -> "expected the period end; got:\n" + out);
    assertTrue(
        out.indexOf("DSB*1~") < out.indexOf("DTP*360"),
        () -> "the DSB opens the loop, before its dates; got:\n" + out);
  }

  @Test
  void emitsTheDisabilityLoopAfterThe2100LoopsAndBeforeTheCoverageSegments()
      throws ValidationException {
    // 834 loop order: 2100A/2100C → 2200 → 2300.
    Member member = baseSubscriber();
    member.setLastName("DOE");
    Address mailing = new Address();
    mailing.setType(AddressType.MAILING);
    mailing.setLine1("PO BOX 1");
    member.addAddress(mailing);
    member.addDisability(new Disability(DisabilityTypeCode.LONG_TERM_DISABILITY));
    member.addSegment(
        new RefSegment.Builder()
            .setReferenceIdentificationQualifier("1L")
            .setReferenceIdentification("PLAN9")
            .build());

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("NM1*31") < out.indexOf("DSB*2~"),
        () -> "the 2200 loop must follow the 2100C block; got:\n" + out);
    assertTrue(
        out.indexOf("DSB*2~") < out.indexOf("REF*1L*PLAN9"),
        () -> "the 2200 loop must precede the 2300 segments; got:\n" + out);
  }

  @Test
  void omitsHlhLuiAndDsbWhenTheMemberCarriesNone() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");

    String out = render(writer.toSegments(member));

    assertFalse(out.contains("HLH"), () -> "no HLH; got:\n" + out);
    assertFalse(out.contains("LUI"), () -> "no LUI; got:\n" + out);
    assertFalse(out.contains("DSB"), () -> "no DSB; got:\n" + out);
  }

  @Test
  void emitsTheDisabilityDsbEvenWithNoPeriodDates() throws ValidationException {
    Member member = baseSubscriber();
    member.addDisability(new Disability(DisabilityTypeCode.PERMANENT_OR_TOTAL_DISABILITY));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("DSB*3~"), () -> "expected the DSB; got:\n" + out);
    assertFalse(out.contains("DTP*360"), () -> "no period start without a date; got:\n" + out);
  }

  @Test
  void emitsTheCoverageLoopWithItsBenefitDates() throws ValidationException {
    Member member = baseSubscriber();
    HealthCoverage coverage = new HealthCoverage("021", "HLT");
    coverage.setPlanCoverageDescription("PREMIUM HEALTH");
    coverage.setCoverageLevelCode("FAM");
    coverage.setStartDate(LocalDateTime.of(2026, 1, 1, 0, 0));
    coverage.setEndDate(LocalDateTime.of(2026, 6, 30, 0, 0));
    member.addHealthCoverage(coverage);

    String out = render(writer.toSegments(member));

    // HD02 is Not Used in 220A1 but is a real element position, so it renders as an empty
    // slot to keep HD03/HD04/HD05 in their correct positions.
    assertTrue(
        out.contains("HD*021**HLT*PREMIUM HEALTH*FAM~"), () -> "expected the HD; got:\n" + out);
    assertTrue(
        out.contains("DTP*348*D8*20260101~"), () -> "expected the benefit begin; got:\n" + out);
    assertTrue(
        out.contains("DTP*349*D8*20260630~"), () -> "expected the benefit end; got:\n" + out);
    assertTrue(
        out.indexOf("HD*021") < out.indexOf("DTP*348"),
        () -> "the HD opens the loop, before its dates; got:\n" + out);
  }

  @Test
  void emitsTheCoverageLoopAfterTheTrailingSegments() throws ValidationException {
    Member member = baseSubscriber();
    member.addSegment(
        new RefSegment.Builder()
            .setReferenceIdentificationQualifier("1L")
            .setReferenceIdentification("PLAN9")
            .build());
    member.addHealthCoverage(new HealthCoverage("001", "DEN"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("REF*1L*PLAN9") < out.indexOf("HD*001**DEN~"),
        () -> "the member's trailing segments precede the 2300 coverage block; got:\n" + out);
  }

  @Test
  void emitsOneCoverageLoopPerCoverageInOrder() throws ValidationException {
    Member member = baseSubscriber();
    member.addHealthCoverage(new HealthCoverage("001", "HLT"));
    member.addHealthCoverage(new HealthCoverage("001", "DEN"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("HD*001**HLT~") < out.indexOf("HD*001**DEN~"),
        () -> "expected both coverages, in the order added; got:\n" + out);
  }

  @Test
  void emitsTheCoverageHdEvenWithNoBenefitDates() throws ValidationException {
    Member member = baseSubscriber();
    member.addHealthCoverage(new HealthCoverage("024", "VIS"));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("HD*024**VIS~"), () -> "expected the HD; got:\n" + out);
    assertFalse(out.contains("DTP*348"), () -> "no benefit begin without a date; got:\n" + out);
    assertFalse(out.contains("DTP*349"), () -> "no benefit end without a date; got:\n" + out);
  }

  @Test
  void rejectsACoverageMissingItsInsuranceLine() {
    Member member = baseSubscriber();
    member.addHealthCoverage(new HealthCoverage("021", null));

    ValidationException thrown =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(
        thrown.getMessage().contains("Insurance Line Code"),
        () -> "expected the HD03 requirement; got: " + thrown.getMessage());
  }

  @Test
  void rejectsACoverageMissingItsMaintenanceType() {
    Member member = baseSubscriber();
    member.addHealthCoverage(new HealthCoverage(null, "HLT"));

    ValidationException thrown =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(
        thrown.getMessage().contains("Maintenance Type Code"),
        () -> "expected the HD01 requirement; got: " + thrown.getMessage());
  }

  @Test
  void emitsTheProviderLoopAsLxThenNm1() throws ValidationException {
    Member member = baseSubscriber();
    member.addProvider(new Provider("WELBY", IdentificationCodeQualifier.CMS_NPI, "1234567893"));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("LX*1~"), () -> "expected the 2310 LX; got:\n" + out);
    assertTrue(
        out.contains("NM1*1P*1*WELBY*****XX*1234567893~"),
        () -> "expected the provider NM1; got:\n" + out);
    assertTrue(
        out.indexOf("LX*1~") < out.indexOf("NM1*1P"),
        () -> "LX opens the loop, before the NM1; got:\n" + out);
  }

  @Test
  void emitsThePlaOnlyWhenTheAssignmentIsChanging() throws ValidationException {
    // A provider merely stated (no change action) gets LX + NM1 and no PLA.
    Member member = baseSubscriber();
    member.addProvider(new Provider("WELBY"));

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("NM1*1P*1*WELBY~"), () -> "expected the provider NM1; got:\n" + out);
    assertFalse(out.contains("PLA"), () -> "no PLA without a change action; got:\n" + out);
  }

  @Test
  void emitsTheAnthemPcpChangeBlock() throws ValidationException {
    Member member = baseSubscriber();
    Provider pcp = new Provider("WELBY", IdentificationCodeQualifier.CMS_NPI, "1234567893");
    pcp.setChangeAction(ActionCode.CHANGE);
    pcp.setChangeDate(LocalDateTime.of(2026, 1, 1, 0, 0));
    pcp.setChangeReason(MaintenanceReasonCode.TERMINATION_OF_BENEFITS);
    member.addProvider(pcp);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("PLA*2*1P*20260101**07~"),
        () -> "expected Anthem's PLA*2*1P*<date>**<reason>; got:\n" + out);
    assertTrue(
        out.indexOf("NM1*1P") < out.indexOf("PLA*2"),
        () -> "the PLA closes the loop, after the NM1; got:\n" + out);
  }

  @Test
  void numbersEachProviderLoopFromOne() throws ValidationException {
    Member member = baseSubscriber();
    member.addProvider(new Provider("WELBY"));
    member.addProvider(new Provider("KILDARE"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("LX*1~") < out.indexOf("NM1*1P*1*WELBY~"),
        () -> "first provider is LX*1; got:\n" + out);
    assertTrue(
        out.indexOf("NM1*1P*1*WELBY~") < out.indexOf("LX*2~"),
        () -> "the second LX opens its own loop; got:\n" + out);
    assertTrue(
        out.indexOf("LX*2~") < out.indexOf("NM1*1P*1*KILDARE~"),
        () -> "second provider follows LX*2; got:\n" + out);
  }

  @Test
  void emitsTheProviderLoopAfterTheCoverageSegmentsAndBeforeTheCobBlock()
      throws ValidationException {
    // 834 loop order: 2300 (HD) → 2310 (provider) → 2320 (COB) → 2700.
    Member member = baseSubscriber();
    member.addSegment(
        new RefSegment.Builder()
            .setReferenceIdentificationQualifier("1L")
            .setReferenceIdentification("PLAN9")
            .build());
    member.addProvider(new Provider("WELBY"));
    member.addCoordinationOfBenefits(
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.PRIMARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS));
    member.addReportingCategory(new ReportingCategory("CLASS", "0042"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("REF*1L*PLAN9") < out.indexOf("LX*1~"),
        () -> "the 2310 loop must follow the 2300 segments; got:\n" + out);
    assertTrue(
        out.indexOf("LX*1~") < out.indexOf("COB*P"),
        () -> "the 2310 loop must precede the 2320 block; got:\n" + out);
    assertTrue(
        out.indexOf("COB*P") < out.indexOf("LS*2700"),
        () -> "the 2320 block must still precede the 2700 block; got:\n" + out);
  }

  @Test
  void omitsTheProviderLoopWhenTheMemberHasNoProvider() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");

    String out = render(writer.toSegments(member));

    assertFalse(out.contains("LX*"), () -> "no 2310 loop without a provider; got:\n" + out);
    assertFalse(out.contains("PLA"), () -> "and no PLA; got:\n" + out);
  }

  @Test
  void rejectsAProviderChangeMissingItsEffectiveDate() throws ValidationException {
    // PLA03 is mandatory; "this provider changed, at no particular time" is not applicable.
    Member member = baseSubscriber();
    Provider pcp = new Provider("WELBY");
    pcp.setChangeAction(ActionCode.CHANGE);
    member.addProvider(pcp);

    ValidationException ex =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(ex.getMessage().contains("PLA03"), ex.getMessage());
  }

  @Test
  void rejectsMoreProvidersThanThe834Permits() throws ValidationException {
    Member member = baseSubscriber();
    for (int i = 0; i < Provider.MAX_PER_MEMBER + 1; i++) {
      member.addProvider(new Provider("DOC" + i));
    }

    ValidationException ex =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(ex.getMessage().contains("at most 30"), ex.getMessage());
  }

  @Test
  void emitsTheCareFirstEveryRowCobStatement() throws ValidationException {
    Member member = baseSubscriber();
    member.addCoordinationOfBenefits(
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.UNKNOWN,
            CoordinationOfBenefitsCode.NO_COORDINATION_OF_BENEFITS));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("COB*U**6~"), () -> "expected CareFirst's bare every-row COB; got:\n" + out);
  }

  @Test
  void emitsTheFullBcbsmMedicareCobBlockInLoopOrder() throws ValidationException {
    // BCBSM's Medicare block: COB, then the group-number REF*6P, the 344/345 coordination
    // dates, and the 2330 NM1 naming the other plan.
    Member member = baseSubscriber();
    CoordinationOfBenefits medicare =
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.SECONDARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS);
    medicare.setPolicyIdentifier("1EG4TE5MK73");
    medicare.setGroupNumber("GRP001");
    medicare.setBeginDate(LocalDateTime.of(2026, 1, 1, 0, 0));
    medicare.setEndDate(LocalDateTime.of(2026, 12, 31, 0, 0));
    medicare.setRelatedEntityName("MEDICARE PART A");
    member.addCoordinationOfBenefits(medicare);

    String out = render(writer.toSegments(member));

    assertTrue(out.contains("COB*S*1EG4TE5MK73*1~"), () -> "expected the COB; got:\n" + out);
    assertTrue(
        out.contains("REF*6P*GRP001~"), () -> "expected the group-number REF*6P; got:\n" + out);
    assertTrue(
        out.contains("DTP*344*D8*20260101~"), () -> "expected COB begin DTP*344; got:\n" + out);
    assertTrue(
        out.contains("DTP*345*D8*20261231~"), () -> "expected COB end DTP*345; got:\n" + out);
    assertTrue(
        out.contains("NM1*IN*2*MEDICARE PART A~"), () -> "expected the 2330 NM1; got:\n" + out);

    assertTrue(out.indexOf("COB*S") < out.indexOf("REF*6P"), () -> "REF follows COB; got:\n" + out);
    assertTrue(
        out.indexOf("REF*6P") < out.indexOf("DTP*344"), () -> "DTP follows REF; got:\n" + out);
    assertTrue(
        out.indexOf("DTP*345") < out.indexOf("NM1*IN"),
        () -> "2330 NM1 closes the loop; got:\n" + out);
  }

  @Test
  void emitsTheCobBlockAfterTheCoverageSegmentsAndBeforeTheReportingCategories()
      throws ValidationException {
    // 834 loop order: 2300 (HD) → 2320/2330 (COB) → 2700 (reporting categories).
    Member member = baseSubscriber();
    member.addSegment(
        new RefSegment.Builder()
            .setReferenceIdentificationQualifier("1L")
            .setReferenceIdentification("PLAN9")
            .build());
    member.addCoordinationOfBenefits(
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.PRIMARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS));
    member.addReportingCategory(new ReportingCategory("CLASS", "0042"));

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("REF*1L*PLAN9") < out.indexOf("COB*P"),
        () -> "the 2320 block must follow the 2300 segments; got:\n" + out);
    assertTrue(
        out.indexOf("COB*P") < out.indexOf("LS*2700"),
        () -> "the 2320 block must precede the 2700 block; got:\n" + out);
  }

  @Test
  void emitsOneBlockPerOtherPlanInOrder() throws ValidationException {
    // BCBSM repeats the Medicare loop up to twice — Part A and Part B.
    Member member = baseSubscriber();
    CoordinationOfBenefits partA =
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.PRIMARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS);
    partA.setRelatedEntityName("MEDA");
    CoordinationOfBenefits partB =
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.SECONDARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS);
    partB.setRelatedEntityName("MEDB");
    member.addCoordinationOfBenefits(partA);
    member.addCoordinationOfBenefits(partB);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.indexOf("NM1*IN*2*MEDA~") < out.indexOf("NM1*IN*2*MEDB~"),
        () -> "expected both loops, in the order added; got:\n" + out);
    assertTrue(
        out.indexOf("COB*P") < out.indexOf("NM1*IN*2*MEDA~")
            && out.indexOf("NM1*IN*2*MEDA~") < out.indexOf("COB*S"),
        () -> "each 2330 must stay inside its own 2320; got:\n" + out);
  }

  @Test
  void omitsTheCobBlockWhenTheMemberHasNoOtherCoverage() throws ValidationException {
    Member member = baseSubscriber();
    member.setLastName("DOE");

    String out = render(writer.toSegments(member));

    assertFalse(
        out.contains("COB"),
        () -> "no 2320 block for a member with no other coverage; got:\n" + out);
  }

  @Test
  void omitsTheOptionalCobPartsThatAreAbsent() throws ValidationException {
    Member member = baseSubscriber();
    member.addCoordinationOfBenefits(
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.PRIMARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS));

    String out = render(writer.toSegments(member));

    assertFalse(out.contains("REF*6P"), () -> "no REF without a group number; got:\n" + out);
    assertFalse(out.contains("DTP*344"), () -> "no DTP without a begin date; got:\n" + out);
    assertFalse(
        out.contains("NM1*IN"), () -> "no 2330 without a related entity name; got:\n" + out);
  }

  @Test
  void honorsACustomGroupNumberQualifier() throws ValidationException {
    Member member = baseSubscriber();
    CoordinationOfBenefits other =
        new CoordinationOfBenefits(
            PayerResponsibilitySequenceCode.PRIMARY,
            CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS);
    other.setGroupNumber("SUB77");
    other.setGroupNumberQualifier("ZZ");
    member.addCoordinationOfBenefits(other);

    String out = render(writer.toSegments(member));

    assertTrue(
        out.contains("REF*ZZ*SUB77~"),
        () -> "expected the caller's own REF qualifier; got:\n" + out);
  }

  @Test
  void rejectsMoreOtherPlansThanThe834Permits() throws ValidationException {
    Member member = baseSubscriber();
    for (int i = 0; i < CoordinationOfBenefits.MAX_PER_MEMBER + 1; i++) {
      member.addCoordinationOfBenefits(
          new CoordinationOfBenefits(
              PayerResponsibilitySequenceCode.PRIMARY,
              CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS));
    }

    ValidationException ex =
        assertThrows(ValidationException.class, () -> writer.toSegments(member));

    assertTrue(ex.getMessage().contains("at most 5"), ex.getMessage());
  }

  @Test
  void emitsAPerForEachDependentThatCarriesOne() throws ValidationException {
    Member subscriber = baseSubscriber();
    subscriber.setEmail("jane@example.com");
    DependentMember dependent = new DependentMember();
    dependent.setMemberIndicator(MemberIndicator.NOT_INSURED);
    dependent.setRelationshipCode(IndividualRelationshipCode.CHILD);
    dependent.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
    dependent.setPhoneNumber("5552223333");
    subscriber.addDependent(dependent);

    String out = render(writer.toSegments(subscriber));

    assertTrue(
        out.contains("PER*IP**EM*jane@example.com~"),
        () -> "expected the subscriber's PER; got:\n" + out);
    assertTrue(
        out.contains("PER*IP**HP*5552223333~"),
        () -> "expected the dependent's own PER; got:\n" + out);
  }
}
