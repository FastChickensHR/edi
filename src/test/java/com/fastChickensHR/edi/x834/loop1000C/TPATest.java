/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop1000C;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TPATest {
    x834Context context = new x834Context();
    String entityIdentifierCode = "TP";
    String tpaName = "TPA Organization Name";
    String identificationCodeQualifier = "PI";
    String tpaIdentifier = "12345";

    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        TPA segment = new TPA.Builder()
                .setN102(tpaName)
                .setN103(identificationCodeQualifier)
                .setN104(tpaIdentifier)
                .build();
        segment.setContext(context);

        assertEquals("N1", segment.getSegmentIdentifier(), "Expected segment identifier should be 'N1'");
        assertEquals("N1*TP*TPA Organization Name*PI*12345~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    @Test
    public void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        TPA segment = new TPA.Builder()
                .setN101(entityIdentifierCode)
                .setN102(tpaName)
                .setN103(identificationCodeQualifier)
                .setN104(tpaIdentifier)
                .build();

        assertEquals(entityIdentifierCode, segment.getEntityIdentifierCode(), "Entity Identifier Code should match N101");
        assertEquals(tpaName, segment.getTPAName(), "TPA Name should match N102");
        assertEquals(identificationCodeQualifier, segment.getIdentificationCodeQualifier(), "Identification Code Qualifier should match N103");
        assertEquals(tpaIdentifier, segment.getTPAIdentifier(), "TPA Identifier should match N104");
    }

    @Test
    public void testSettingSpecNamesGettingSpecNames() throws ValidationException {
        TPA segment = new TPA.Builder()
                .setEntityIdentifierCode(entityIdentifierCode)
                .setTPAName(tpaName)
                .setIdentificationCodeQualifier(identificationCodeQualifier)
                .setTPAIdentifier(tpaIdentifier)
                .build();

        assertEquals(entityIdentifierCode, segment.getN101(), "Entity Identifier Code should match N101");
        assertEquals(tpaName, segment.getN102(), "TPA Name should match N102");
        assertEquals(identificationCodeQualifier, segment.getN103(), "Identification Code Qualifier should match N103");
        assertEquals(tpaIdentifier, segment.getN104(), "TPA Identifier should match N104");
    }
}