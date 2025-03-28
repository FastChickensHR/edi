/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000B;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayerTest {
    x834Context context = new x834Context();
    String entityIdentifierCode = "T3";
    String planSponsorName = "fake plan sponsor name";
    String identificationCodeQualifier = "FLL";
    String sponsorIdentifier = "FPO";

    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {


        Payer segment = new Payer.Builder()
                .setN102(planSponsorName)
                .setN104(sponsorIdentifier)
                .build();
        segment.setContext(context);

        assertEquals("N1", segment.getSegmentIdentifier(), "Expected segment identifier should be 'N1'");
        assertEquals("N1*IN*fake plan sponsor name*FI*FPO~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    @Test
    public void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        Payer segment = new Payer.Builder()
                .setN101(entityIdentifierCode)
                .setN102(planSponsorName)
                .setN103(identificationCodeQualifier)
                .setN104(sponsorIdentifier)
                .build();

        assertEquals(entityIdentifierCode, segment.getEntityIdentifierCode(), "Entity Identifier Code should match N101");
        assertEquals(planSponsorName, segment.getPlanSponsorName(), "Plan Sponsor Name should match N102");
        assertEquals(identificationCodeQualifier, segment.getIdentificationCodeQualifier(), "Identification Code Qualifier should match N103");
        assertEquals(sponsorIdentifier, segment.getSponsorIdentifier(), "Sponsor Identifier should match N104");
    }

    @Test
    public void testSettingSpecNamesGettingSpecNames() throws ValidationException {
        Payer segment = new Payer.Builder()
                .setEntityIdentifierCode(entityIdentifierCode)
                .setPlanSponsorName(planSponsorName)
                .setIdentificationCodeQualifier(identificationCodeQualifier)
                .setSponsorIdentifier(sponsorIdentifier)
                .build();

        assertEquals(entityIdentifierCode, segment.getN101(), "Entity Identifier Code should match N101");
        assertEquals(planSponsorName, segment.getN102(), "Plan Sponsor Name should match N102");
        assertEquals(identificationCodeQualifier, segment.getN103(), "Identification Code Qualifier should match N103");
        assertEquals(sponsorIdentifier, segment.getN104(), "Sponsor Identifier should match N104");
    }
}