/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link AMTSegment}.
 */
class AMTSegmentTest {

    private X834Context context;

    @BeforeEach
    void setUp() {
        context = new X834Context();
    }

    @Test
    void testSegmentIdConstant() {
        assertEquals("AMT", AMTSegment.SEGMENT_ID);
    }

    @Test
    void testSuccessfulBuildWithRequiredFields() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmountQualifierCode("FK")
                .setMonetaryAmount("250.00")
                .build();

        assertNotNull(segment);
        assertEquals("FK", segment.getAmountQualifierCode());
        assertEquals("250.00", segment.getMonetaryAmount());
        assertNull(segment.getCreditDebitFlagCode());
        assertEquals("AMT", segment.getSegmentIdentifier());
    }

    @Test
    void testSuccessfulBuildWithAllFields() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmt01("D2")
                .setAmt02("500.00")
                .setAmt03("C")
                .build();

        assertEquals("D2", segment.getAmt01());
        assertEquals("500.00", segment.getAmt02());
        assertEquals("C", segment.getAmt03());
        assertEquals("D2", segment.getAmountQualifierCode());
        assertEquals("500.00", segment.getMonetaryAmount());
        assertEquals("C", segment.getCreditDebitFlagCode());
    }

    @Test
    void testMissingAmountQualifierCode() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new AMTSegment.Builder()
                        .setMonetaryAmount("100.00")
                        .build());
        assertTrue(ex.getMessage().contains("Amount Qualifier Code"));
    }

    @Test
    void testMissingMonetaryAmount() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new AMTSegment.Builder()
                        .setAmountQualifierCode("FK")
                        .build());
        assertTrue(ex.getMessage().contains("Monetary Amount"));
    }

    @Test
    void testEmptyAmountQualifierCode() {
        assertThrows(ValidationException.class, () ->
                new AMTSegment.Builder()
                        .setAmountQualifierCode("   ")
                        .setMonetaryAmount("100.00")
                        .build());
    }

    @Test
    void testInvalidCreditDebitFlag() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new AMTSegment.Builder()
                        .setAmountQualifierCode("FK")
                        .setMonetaryAmount("100.00")
                        .setCreditDebitFlagCode("X")
                        .build());
        assertTrue(ex.getMessage().contains("Credit/Debit"));
    }

    @Test
    void testValidCreditFlag() throws ValidationException {
        AMTSegment c = new AMTSegment.Builder()
                .setAmountQualifierCode("FK")
                .setMonetaryAmount("100.00")
                .setCreditDebitFlagCode("C")
                .build();
        assertEquals("C", c.getCreditDebitFlagCode());

        AMTSegment d = new AMTSegment.Builder()
                .setAmountQualifierCode("FK")
                .setMonetaryAmount("100.00")
                .setCreditDebitFlagCode("D")
                .build();
        assertEquals("D", d.getCreditDebitFlagCode());
    }

    @Test
    void testRenderMinimal() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmt01("FK")
                .setAmt02("250.00")
                .build();
        segment.setContext(context);

        assertEquals("AMT*FK*250.00~\n", segment.render());
    }

    @Test
    void testRenderFull() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmt01("D2")
                .setAmt02("500.00")
                .setAmt03("C")
                .build();
        segment.setContext(context);

        assertEquals("AMT*D2*500.00*C~\n", segment.render());
    }

    @Test
    void testNullContextRender() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmt01("FK")
                .setAmt02("250.00")
                .build();
        assertThrows(IllegalStateException.class, segment::render);
    }

    @Test
    void testGetElementValues() throws ValidationException {
        AMTSegment segment = new AMTSegment.Builder()
                .setAmt01("FK")
                .setAmt02("250.00")
                .setAmt03("D")
                .build();
        String[] values = segment.getElementValues();
        assertEquals(3, values.length);
        assertEquals("FK", values[0]);
        assertEquals("250.00", values[1]);
        assertEquals("D", values[2]);
    }
}
