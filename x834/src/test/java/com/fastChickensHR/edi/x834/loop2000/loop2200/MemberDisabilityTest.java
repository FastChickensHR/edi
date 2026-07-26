/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2200;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.DisabilityTypeCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.DSBSegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberDisabilityTest {
    private final X834Context context = new X834Context();

    private String render(DSBSegment segment) {
        segment.setContext(context);
        return segment.render().trim();
    }

    @Test
    void rendersTheDisabilityTypeAlone() throws ValidationException {
        DSBSegment disability = MemberDisability.builder()
                .setDisabilityTypeCode(DisabilityTypeCode.SHORT_TERM_DISABILITY)
                .build();

        assertEquals("DSB*1~", render(disability));
        assertEquals(DisabilityTypeCode.SHORT_TERM_DISABILITY, disability.getDisabilityTypeCode());
    }

    @Test
    void treatsNoDisabilityAsAStatedAnswer() throws ValidationException {
        // 4 says "this member is not disabled", which is different from sending no Loop 2200 —
        // only the former overwrites a disability the carrier already holds.
        assertEquals("DSB*4~", render(MemberDisability.builder()
                .setDisabilityTypeCode(DisabilityTypeCode.NO_DISABILITY)
                .build()));
    }

    @Test
    void carriesTheWorkAndBenefitElements() throws ValidationException {
        DSBSegment disability = MemberDisability.builder()
                .setDisabilityTypeCode(DisabilityTypeCode.LONG_TERM_DISABILITY)
                .setQuantity("40")
                .setOccupationCode("1234")
                .setWorkIntensityCode("F")
                .setProductOptionCode("A1")
                .setMonetaryAmount("2500")
                .build();

        assertEquals("DSB*2*40*1234*F*A1*2500~", render(disability));
    }

    @Test
    void rejectsADisabilityWithNoType() {
        // DSB01 is mandatory, so a disability period cannot travel without saying what kind it is.
        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberDisability.builder().setQuantity("40").build());

        assertTrue(ex.getMessage().contains("DSB01"), ex.getMessage());
    }

    @Test
    void rejectsAnOccupationCodeOutsideElement1149sRange() {
        // Element 1149 is ID 4/6.
        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberDisability.builder()
                        .setDisabilityTypeCode(DisabilityTypeCode.SHORT_TERM_DISABILITY)
                        .setOccupationCode("123")
                        .build());

        assertTrue(ex.getMessage().contains("DSB03"), ex.getMessage());
    }

    @Test
    void rejectsAQuantityLongerThanElement380Allows() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> MemberDisability.builder()
                        .setDisabilityTypeCode(DisabilityTypeCode.SHORT_TERM_DISABILITY)
                        .setQuantity("1".repeat(DSBSegment.MAX_QUANTITY_LENGTH + 1))
                        .build());

        assertTrue(ex.getMessage().contains("DSB02"), ex.getMessage());
    }
}
