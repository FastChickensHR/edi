/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.TransactionSetIdentifierCode;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class STSegmentTest {
    x834Context context = new x834Context();

    @Test
    void testCreation_WithValidValues_ShouldCreateSegment() throws ValidationException {
        String transactionSetIdentifier = "834";
        String transactionSetControlNumber = "0001";
        String implementationConventionReference = "005010X220A1";

        STSegment segment = new STSegment.Builder()
                .setTransactionSetIdentifierCode(transactionSetIdentifier)
                .setTransactionSetControlNumber(transactionSetControlNumber)
                .setImplementationConventionReference(implementationConventionReference)
                .build();

        assertEquals(transactionSetIdentifier, segment.getTransactionSetIdentifierCode().getCode());
        assertEquals(transactionSetControlNumber, segment.getTransactionSetControlNumber());
        assertEquals(implementationConventionReference, segment.getImplementationConventionReference());
    }

    @Test
    void testCreation_WithEnumValue_ShouldCreateSegment() throws ValidationException {
        TransactionSetIdentifierCode code = TransactionSetIdentifierCode.BENEFIT_ENROLLMENT_AND_MAINTENANCE;
        String transactionSetControlNumber = "0001";

        STSegment segment = new STSegment.Builder()
                .setTransactionSetIdentifierCode(code.getCode())
                .setTransactionSetControlNumber(transactionSetControlNumber)
                .build();

        assertEquals(code.getCode(), segment.getTransactionSetIdentifierCode().getCode());
        assertEquals(transactionSetControlNumber, segment.getTransactionSetControlNumber());
        assertNull(segment.getImplementationConventionReference(), "Implementation convention reference should be null when not set");
    }

    @Test
    void testToString_ShouldReturnFormattedString() throws ValidationException {
        STSegment segment = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("0001")
                .setImplementationConventionReference("005010X220A1")
                .build();

        segment.setContext(context);

        String result = segment.render().trim();
        assertEquals("ST*834*0001*005010X220A1~", result);
    }

    @Test
    void testToString_WithOptionalFieldsOmitted_ShouldReturnFormattedString() throws ValidationException {
        STSegment segment = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("0001")
                .build();
        segment.setContext(context);

        String result = segment.render().trim();
        assertEquals("ST*834*0001~", result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void testValidation_InvalidTransactionSetIdentifierCode_ShouldThrowException(String invalidValue) {
        STSegment.Builder builder = new STSegment.Builder()
                .setTransactionSetControlNumber("0001");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.setTransactionSetIdentifierCode(invalidValue));
        assertTrue(exception.getMessage().contains("cannot be null or empty"),
                "Exception message should mention the invalid field");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void testValidation_InvalidTransactionSetControlNumber_ShouldThrowException(String invalidValue) {
        STSegment.Builder builder = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber(invalidValue);

        Exception exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("Transaction Set Control Number") ||
                        exception.getMessage().contains("control number"),
                "Exception message should mention the invalid field");
    }

    @Test
    void testValidation_TransactionSetIdentifierCode_ShouldThrowException() {
        STSegment.Builder builder = new STSegment.Builder();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> builder.setTransactionSetIdentifierCode("12345"));

        assertTrue(exception.getMessage().contains("Invalid Transaction Set Identifier Code value"),
                "Exception message should mention the invalid field");
    }

    @Test
    void testValidation_TooLongTransactionSetControlNumber_ShouldThrowException() {
        String tooLongNumber = "12345678901234567890";
        STSegment.Builder builder = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber(tooLongNumber);

        Exception exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("Transaction Set Control Number") ||
                        exception.getMessage().contains("control number"),
                "Exception message should mention the invalid field");
    }

    @Test
    void testValidation_TransactionSetControlNumber_ShouldThrowException() {
        STSegment.Builder builder = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("12");

        Exception exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("Transaction Set Control Number") ||
                        exception.getMessage().contains("control number"),
                "Exception message should mention the invalid field");
    }

    @Test
    void testEquals_WithDifferentSegments_ShouldNotBeEqual() throws ValidationException {
        STSegment segment1 = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("0001")
                .build();

        STSegment segment2 = new STSegment.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("0002")
                .build();

        assertNotEquals(segment1, segment2);
    }
}