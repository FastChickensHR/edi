/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberPolicyNumberTest {
    x834Context context = new x834Context();
    String referenceIdentification = "POLICY123456";

    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        MemberPolicyNumber segment = MemberPolicyNumber.builder()
                .setRef02(referenceIdentification)
                .build();
        segment.setContext(context);

        assertEquals("REF", segment.getSegmentIdentifier(), "Expected segment identifier should be 'REF'");
        assertEquals("REF*1L*POLICY123456~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    @Test
    public void testSettingAndGettingValues() throws ValidationException {
        MemberPolicyNumber segment = MemberPolicyNumber.builder()
                .setRef01("ZZ") // Override default
                .setRef02(referenceIdentification)
                .build();

        assertEquals("ZZ", segment.getRef01().getCode(), "Reference Identification Qualifier should match ref01");
        assertEquals(referenceIdentification, segment.getRef02(), "Reference Identification should match ref02");

        assertEquals("ZZ", segment.getReferenceIdentificationQualifier().getCode(), "Reference Identification Qualifier should match ref01");
        assertEquals(referenceIdentification, segment.getReferenceIdentification(), "Reference Identification should match ref02");
    }

    @Test
    public void testDefaultValuesAreSet() throws ValidationException {
        MemberPolicyNumber segment = MemberPolicyNumber.builder()
                .setRef02(referenceIdentification)
                .build();

        assertEquals(MemberPolicyNumber.DEFAULT_ENTITY_IDENTIFIER_CODE, segment.getRef01().getCode(),
                "Reference Identification Qualifier should be set to default value");
    }

    @Test
    public void testValidationRequiresRef02() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberPolicyNumber.builder().build();
        }, "Should throw ValidationException when ref02 is not set");

        assertTrue(exception.getMessage().contains("ref02"),
                "Exception message should mention the missing ref02 field");
    }

    @Test
    public void testValidationRejectsEmptyRef02() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberPolicyNumber.builder()
                    .setRef02("")
                    .build();
        }, "Should throw ValidationException when ref02 is empty");

        assertTrue(exception.getMessage().contains("ref02"),
                "Exception message should mention the empty ref02 field");
    }
}