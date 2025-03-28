/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionSetPolicyNumberTest {
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        TransactionSetPolicyNumber segment = new TransactionSetPolicyNumber.Builder()
                .setRef01("idk")
                .setRef02("asdf")
                .build();

        segment.setContext(new x834Context());

        assertEquals("REF", segment.getSegmentIdentifier(), "Expected segment identifier should be 'REF'");
        assertEquals("REF*idk*asdf~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        String referenceIdentificationQualifier = "1";
        String masterPolicyNumber = "2";
        TransactionSetPolicyNumber segment = new TransactionSetPolicyNumber.Builder()
                .setRef01(referenceIdentificationQualifier)
                .setRef02(masterPolicyNumber)
                .build();

        assertEquals(referenceIdentificationQualifier, segment.getReferenceIdentificationQualifier());
        assertEquals(masterPolicyNumber, segment.getMasterPolicyNumber());
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        String referenceIdentificationQualifier = "1";
        String masterPolicyNumber = "2";
        TransactionSetPolicyNumber segment = new TransactionSetPolicyNumber.Builder()
                .setReferenceIdentificationQualifier(referenceIdentificationQualifier)
                .setMasterPolicyNumber(masterPolicyNumber)
                .build();

        assertEquals(referenceIdentificationQualifier, segment.getRef01());
        assertEquals(masterPolicyNumber, segment.getRef02());
    }
}