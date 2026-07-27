/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberCommunicationsNumbersTest {
    private final X834Context context = new X834Context();

    private String render(PERSegment segment) {
        segment.setContext(context);
        return segment.render().trim();
    }

    @Test
    void rendersTheAnthemWorkedExampleShape() throws ValidationException {
        // Anthem's guide sends PER*IP**HP*<phone> — PER01 IP, PER02 absent (the member is already
        // named by the preceding NM1), then the qualifier/number pair.
        PERSegment per = MemberCommunicationsNumbers.builder()
                .addCommunicationNumber(CommunicationNumberQualifier.HOME_PHONE, "5551234567")
                .build();

        assertEquals("PER*IP**HP*5551234567~", render(per));
    }

    @Test
    void carriesUpToThreeChannelsInCallOrder() throws ValidationException {
        // BCBSM MembersEdge asks for EM/HP/WP — three channels in one PER, filling PER03/04,
        // PER05/06 and PER07/08 in the order they were added.
        PERSegment per = MemberCommunicationsNumbers.builder()
                .addCommunicationNumber(CommunicationNumberQualifier.ELECTRONIC_MAIL, "jane@example.com")
                .addCommunicationNumber(CommunicationNumberQualifier.HOME_PHONE, "5551234567")
                .addCommunicationNumber(CommunicationNumberQualifier.WORK_PHONE, "5559876543")
                .build();

        assertEquals("PER*IP**EM*jane@example.com*HP*5551234567*WP*5559876543~", render(per));
    }

    @Test
    void per01DefaultsToInsuredParty() throws ValidationException {
        PERSegment per = MemberCommunicationsNumbers.builder()
                .addCommunicationNumber(CommunicationNumberQualifier.TELEPHONE, "5551234567")
                .build();

        assertEquals("PER", per.getSegmentIdentifier());
        assertEquals(MemberCommunicationsNumbers.INSURED_PARTY, per.getContactFunctionCode());
    }

    @Test
    void rejectsAFourthChannelRatherThanDroppingIt() throws ValidationException {
        MemberCommunicationsNumbers.Builder per = MemberCommunicationsNumbers.builder()
                .addCommunicationNumber(CommunicationNumberQualifier.ELECTRONIC_MAIL, "jane@example.com")
                .addCommunicationNumber(CommunicationNumberQualifier.HOME_PHONE, "5551234567")
                .addCommunicationNumber(CommunicationNumberQualifier.WORK_PHONE, "5559876543");

        ValidationException ex = assertThrows(ValidationException.class,
                () -> per.addCommunicationNumber(CommunicationNumberQualifier.CELLULAR_PHONE, "5550000000"));

        assertTrue(ex.getMessage().contains("at most 3"), ex.getMessage());
    }

    @Test
    void rejectsANumberWithoutItsQualifier() {
        // X12 syntax rule P0304: a lone number would render as PER*IP***5551234567, which is
        // non-conformant — the receiver cannot tell what kind of number it is.
        ValidationException ex = assertThrows(ValidationException.class,
                () -> new UnqualifiedPer().setContactFunctionCode("IP").build());

        assertTrue(ex.getMessage().contains("P0304"), ex.getMessage());
    }

    @Test
    void rejectsAPerCarryingNoChannelAtAll() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberCommunicationsNumbers.builder().build());

        assertTrue(ex.getMessage().contains("no communication number"), ex.getMessage());
    }

    @Test
    void rejectsACommunicationNumberLongerThanElement364Allows() {
        String tooLong = "5".repeat(PERSegment.MAX_COMMUNICATION_NUMBER_LENGTH + 1);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberCommunicationsNumbers.builder()
                        .addCommunicationNumber(CommunicationNumberQualifier.HOME_PHONE, tooLong)
                        .build());

        assertTrue(ex.getMessage().contains("256"), ex.getMessage());
    }

    /** Sets PER04 without PER03, which the public builder's paired API cannot express. */
    private static final class UnqualifiedPer extends PERSegment.AbstractBuilder<UnqualifiedPer> {
        private UnqualifiedPer() {
            this.per04 = "5551234567";
        }

        @Override
        protected UnqualifiedPer self() {
            return this;
        }

        @Override
        public PERSegment build() throws ValidationException {
            return new PERSegment(this) {
            };
        }
    }
}
