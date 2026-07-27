/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProviderLoopSegmentsTest {
    private final X834Context context = new X834Context();

    private String render(Segment segment) {
        segment.setContext(context);
        return segment.render().trim();
    }

    @Test
    void namesTheProviderWithFloridaBluesMandatedNpiQualifier() throws ValidationException {
        // Florida Blue B13: NM108 must be XX (NPI) — "Only the above code is valid".
        ProviderName provider = ProviderName.builder()
                .setLastName("WELBY")
                .setFirstName("MARCUS")
                .setProviderIdentification(IdentificationCodeQualifier.CMS_NPI, "1234567893")
                .build();

        assertEquals("NM1*1P*1*WELBY*MARCUS****XX*1234567893~", render(provider));
        assertEquals("1234567893", provider.getProviderIdentifier());
    }

    @Test
    void namesTheProviderWithCareFirstsLegacyServiceProviderQualifier() throws ValidationException {
        // CareFirst uses SV, its own legacy ID, "until CareFirst provides the NPI" — the same
        // position, a different qualifier, which is why it is a carrier answer rather than a default.
        ProviderName provider = ProviderName.builder()
                .setLastName("WELBY")
                .setProviderIdentification(IdentificationCodeQualifier.SERVICE_PROVIDER, "SV99881")
                .build();

        assertEquals("NM1*1P*1*WELBY*****SV*SV99881~", render(provider));
    }

    @Test
    void namesAProviderWithNoIdentifierAtAll() throws ValidationException {
        ProviderName provider = ProviderName.builder().setLastName("WELBY").build();

        assertEquals("NM1*1P*1*WELBY~", render(provider));
    }

    @Test
    void rejectsAnIdentifierWithoutItsQualifier() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> ProviderName.builder()
                        .setLastName("WELBY")
                        .setIdentificationCode("1234567893")
                        .build());

        assertTrue(ex.getMessage().contains("NM108"), ex.getMessage());
    }

    @Test
    void rejectsAQualifierWithoutItsIdentifier() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> ProviderName.builder()
                        .setLastName("WELBY")
                        .setIdentificationCodeQualifier(IdentificationCodeQualifier.CMS_NPI.getCode())
                        .build());

        assertTrue(ex.getMessage().contains("NM109"), ex.getMessage());
    }

    @Test
    void rendersTheAnthemPcpChangeShape() throws ValidationException {
        // Anthem's PCP-change scenario (p. 7): PLA*2*1P*<date>**<reason>. PLA04 (time) is empty but
        // must hold its slot, or the reason would land in PLA04.
        PLASegment change = ProviderChange.builder()
                .setActionCode(ActionCode.CHANGE)
                .setDate("20260101")
                .setMaintenanceReasonCode(MaintenanceReasonCode.TERMINATION_OF_BENEFITS)
                .build();

        assertEquals("PLA*2*1P*20260101**07~", render(change));
    }

    @Test
    void trimsTheTrailingSlotsWhenNoReasonIsGiven() throws ValidationException {
        // Without PLA05 there is nothing after PLA03, so the empty PLA04 must not render either.
        PLASegment change = ProviderChange.builder()
                .setActionCode(ActionCode.ADD)
                .setDate("20260101")
                .build();

        assertEquals("PLA*1*1P*20260101~", render(change));
    }

    @Test
    void exposesItsElementsByName() throws ValidationException {
        PLASegment change = ProviderChange.builder()
                .setActionCode(ActionCode.DELETE)
                .setDate("20261231")
                .build();

        assertEquals("PLA", change.getSegmentIdentifier());
        assertEquals(ActionCode.DELETE, change.getActionCode());
        assertEquals("20261231", change.getDate());
    }

    @Test
    void rejectsAChangeWithNoAction() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> ProviderChange.builder().setDate("20260101").build());

        assertTrue(ex.getMessage().contains("PLA01"), ex.getMessage());
    }

    @Test
    void rejectsAChangeWithNoDate() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> ProviderChange.builder().setActionCode(ActionCode.CHANGE).build());

        assertTrue(ex.getMessage().contains("PLA03"), ex.getMessage());
    }

    @Test
    void rejectsADateThatIsNotEightCharacters() {
        // Element 373 is DT 8/8 — a short date would silently shift the receiver's parse.
        ValidationException ex = assertThrows(ValidationException.class,
                () -> ProviderChange.builder()
                        .setActionCode(ActionCode.CHANGE)
                        .setDate("260101")
                        .build());

        assertTrue(ex.getMessage().contains("CCYYMMDD"), ex.getMessage());
    }
}
