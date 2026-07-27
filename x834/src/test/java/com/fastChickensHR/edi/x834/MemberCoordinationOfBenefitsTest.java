/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.CoordinationOfBenefitsCode;
import com.fastChickensHR.edi.x834.data.PayerResponsibilitySequenceCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberCoordinationOfBenefitsTest {
    private final X834Context context = new X834Context();

    private String render(Segment segment) {
        segment.setContext(context);
        return segment.render().trim();
    }

    @Test
    void rendersTheCareFirstEveryRowForm() throws ValidationException {
        // CareFirst puts COB*U**6~ on every medical and dental row: payer sequence unknown, and
        // explicitly no coordination. COB02 is absent, so the element renders empty rather than
        // being trimmed away — COB03 after it has to keep its position.
        COBSegment cob = MemberCoordinationOfBenefits.builder()
                .setPayerResponsibility(PayerResponsibilitySequenceCode.UNKNOWN)
                .setCoordinationOfBenefitsCode(CoordinationOfBenefitsCode.NO_COORDINATION_OF_BENEFITS)
                .build();

        assertEquals("COB*U**6~", render(cob));
    }

    @Test
    void rendersTheBcbsmMedicareForm() throws ValidationException {
        // BCBSM's Medicare block: S for Secondary (Employed), the MBI in COB02, and 1 for
        // "benefits are coordinated".
        COBSegment cob = MemberCoordinationOfBenefits.builder()
                .setPayerResponsibility(PayerResponsibilitySequenceCode.SECONDARY)
                .setReferenceIdentification("1EG4TE5MK73")
                .setCoordinationOfBenefitsCode(CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS)
                .build();

        assertEquals("COB*S*1EG4TE5MK73*1~", render(cob));
    }

    @Test
    void trimsTrailingAbsentElements() throws ValidationException {
        // COB01 alone is a legal statement; the empty COB02/COB03 must not render as "COB*P**~".
        COBSegment cob = MemberCoordinationOfBenefits.builder()
                .setPayerResponsibility(PayerResponsibilitySequenceCode.PRIMARY)
                .build();

        assertEquals("COB*P~", render(cob));
    }

    @Test
    void exposesItsElementsByName() throws ValidationException {
        COBSegment cob = MemberCoordinationOfBenefits.builder()
                .setPayerResponsibility(PayerResponsibilitySequenceCode.PRIMARY)
                .setReferenceIdentification("POL123")
                .setCoordinationOfBenefitsCode(CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS)
                .build();

        assertEquals("COB", cob.getSegmentIdentifier());
        assertEquals(PayerResponsibilitySequenceCode.PRIMARY, cob.getPayerResponsibility());
        assertEquals("POL123", cob.getReferenceIdentification());
        assertEquals(CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS, cob.getCoordinationOfBenefitsCode());
    }

    @Test
    void rejectsACobWithNoPayerResponsibility() {
        // X12 marks COB01 optional, but a COB saying nothing about payment order is not actionable.
        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberCoordinationOfBenefits.builder()
                        .setCoordinationOfBenefitsCode(CoordinationOfBenefitsCode.COORDINATION_OF_BENEFITS)
                        .build());

        assertTrue(ex.getMessage().contains("COB01"), ex.getMessage());
    }

    @Test
    void rejectsAPolicyIdentifierLongerThanElement127Allows() {
        String tooLong = "X".repeat(COBSegment.MAX_REFERENCE_IDENTIFICATION_LENGTH + 1);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberCoordinationOfBenefits.builder()
                        .setPayerResponsibility(PayerResponsibilitySequenceCode.PRIMARY)
                        .setReferenceIdentification(tooLong)
                        .build());

        assertTrue(ex.getMessage().contains("50"), ex.getMessage());
    }

    @Test
    void relatedEntityNamesTheOtherPlanAsANonPersonInsurer() throws ValidationException {
        // The same Medicare concept is a different literal per carrier — BCN wants MEDA, Medicare
        // Advantage wants MEDICARE PART A — so the name is supplied, not defaulted.
        CoordinationOfBenefitsRelatedEntityName entity = CoordinationOfBenefitsRelatedEntityName.builder()
                .setRelatedEntityName("MEDICARE PART A")
                .build();

        assertEquals("NM1*IN*2*MEDICARE PART A~", render(entity));
        assertEquals("MEDICARE PART A", entity.getRelatedEntityName());
    }
}
