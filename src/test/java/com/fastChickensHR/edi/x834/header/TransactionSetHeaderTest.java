/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for TransactionSetHeader.
 * Verifies the behavior of the segment identifier, field getters/setters,
 * and validation functionality.
 */
public class TransactionSetHeaderTest {

    /**
     * Tests for getSegmentIdentifier method in TransactionSetHeader class.
     * This method is expected to always return the constant value "ST".
     */
    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        x834Context context = new x834Context();

        TransactionSetHeader segment = new TransactionSetHeader.Builder()
                .setSt01("834")
                .setSt02("0001")
                .setSt03("005010X220A1")
                .build();
        segment.setContext(context);

        assertEquals("ST", segment.getSegmentIdentifier(), "Expected segment identifier should be 'ST'");
        assertEquals("ST*834*0001*005010X220A1~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    public void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        TransactionSetHeader header = new TransactionSetHeader.Builder()
                .setSt01("834")
                .setSt02("0001")
                .setSt03("005010X220A1")
                .build();

        assertEquals("834", header.getTransactionSetIdentifierCode(), "Transaction Set Identifier Code should match ST01");
        assertEquals("0001", header.getTransactionSetControlNumber(), "Transaction Set Control Number should match ST02");
        assertEquals("005010X220A1", header.getImplementationConventionReference(), "Implementation Convention Reference should match ST03");
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    public void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        TransactionSetHeader.Builder builder = new TransactionSetHeader.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("1234")
                .setImplementationConventionReference("005010X220A2");

        TransactionSetHeader header = builder.build();

        assertEquals("834", header.getSt01(), "ST01 should match Transaction Set Identifier Code");
        assertEquals("1234", header.getSt02(), "ST02 should match Transaction Set Control Number");
        assertEquals("005010X220A2", header.getSt03(), "ST03 should match Implementation Convention Reference");
    }

    /**
     * Tests that the default values are correctly applied when not explicitly set.
     */
    @Test
    public void testDefaultValues() throws ValidationException {
        TransactionSetHeader header = new TransactionSetHeader.Builder().build();

        assertEquals(TransactionSetHeader.DEFAULT_TRANSACTION_SET_ID, header.getSt01(),
                "ST01 should default to DEFAULT_TRANSACTION_SET_ID");
        assertEquals(TransactionSetHeader.DEFAULT_CONTROL_NUMBER, header.getSt02(),
                "ST02 should default to DEFAULT_CONTROL_NUMBER");
        assertEquals(TransactionSetHeader.DEFAULT_CONVENTION_REFERENCE, header.getSt03(),
                "ST03 should default to DEFAULT_CONVENTION_REFERENCE");
    }

    /**
     * Tests that validation correctly rejects an invalid ST01 (Transaction Set Identifier Code).
     */
    @Test
    public void testBuilder_InvalidSt01_ShouldThrowValidationException() {
        TransactionSetHeader.Builder builder = new TransactionSetHeader.Builder()
                .setSt01("") // Invalid st01
                .setSt02("0001")
                .setSt03("005010X220A1");

        assertThrows(ValidationException.class, builder::build,
                "Building with invalid st01 should throw ValidationException.");
    }

    /**
     * Tests that validation correctly rejects an invalid ST02 (Transaction Set Control Number).
     */
    @Test
    public void testBuilder_InvalidSt02_ShouldThrowValidationException() {
        TransactionSetHeader.Builder builder = new TransactionSetHeader.Builder()
                .setSt01("834")
                .setSt02("") // Invalid st02
                .setSt03("005010X220A1");

        assertThrows(ValidationException.class, builder::build,
                "Building with invalid st02 should throw ValidationException.");
    }
}