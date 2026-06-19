/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link IDCSegment}.
 */
class IDCSegmentTest {

    private x834Context context;

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testSegmentIdConstant() {
        assertEquals("IDC", IDCSegment.SEGMENT_ID);
    }

    @Test
    void testSuccessfulBuildWithRequiredFields() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setPlanCoverageDescription("MEDICAL PLAN")
                .setIdentificationCardTypeCode("H")
                .setIdentificationCardActionCode("A")
                .build();

        assertNotNull(segment);
        assertEquals("MEDICAL PLAN", segment.getPlanCoverageDescription());
        assertEquals("H", segment.getIdentificationCardTypeCode());
        assertEquals("A", segment.getIdentificationCardActionCode());
        assertNull(segment.getIdentificationCardCount());
        assertEquals("IDC", segment.getSegmentIdentifier());
    }

    @Test
    void testSuccessfulBuildWithAllFields() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setIdc01("DENTAL PLAN")
                .setIdc02("D")
                .setIdc03("R")
                .setIdc04("2")
                .build();

        assertEquals("DENTAL PLAN", segment.getIdc01());
        assertEquals("D", segment.getIdc02());
        assertEquals("R", segment.getIdc03());
        assertEquals("2", segment.getIdc04());
        assertEquals("2", segment.getIdentificationCardCount());
    }

    @Test
    void testMissingPlanCoverageDescription() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setIdentificationCardTypeCode("H")
                        .setIdentificationCardActionCode("A")
                        .build());
        assertTrue(ex.getMessage().contains("Plan Coverage Description"));
    }

    @Test
    void testMissingCardTypeCode() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setPlanCoverageDescription("HEALTH")
                        .setIdentificationCardActionCode("A")
                        .build());
        assertTrue(ex.getMessage().contains("Identification Card Type Code"));
    }

    @Test
    void testMissingActionCode() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setPlanCoverageDescription("HEALTH")
                        .setIdentificationCardTypeCode("H")
                        .build());
        assertTrue(ex.getMessage().contains("Identification Card Action Code"));
    }

    @Test
    void testPlanCoverageDescriptionTooLong() {
        StringBuilder longDesc = new StringBuilder();
        for (int i = 0; i < 51; i++) longDesc.append("X");

        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setPlanCoverageDescription(longDesc.toString())
                        .setIdentificationCardTypeCode("H")
                        .setIdentificationCardActionCode("A")
                        .build());
        assertTrue(ex.getMessage().contains("50 characters or less"));
    }

    @Test
    void testNonNumericCardCount() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setPlanCoverageDescription("HEALTH")
                        .setIdentificationCardTypeCode("H")
                        .setIdentificationCardActionCode("A")
                        .setIdentificationCardCount("ABC")
                        .build());
        assertTrue(ex.getMessage().contains("numeric"));
    }

    @Test
    void testNegativeCardCount() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new IDCSegment.Builder()
                        .setPlanCoverageDescription("HEALTH")
                        .setIdentificationCardTypeCode("H")
                        .setIdentificationCardActionCode("A")
                        .setIdentificationCardCount("-1")
                        .build());
        assertTrue(ex.getMessage().contains("non-negative"));
    }

    @Test
    void testEmptyCardCountIsAllowed() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setPlanCoverageDescription("HEALTH")
                .setIdentificationCardTypeCode("H")
                .setIdentificationCardActionCode("A")
                .setIdentificationCardCount("")
                .build();
        assertNotNull(segment);
    }

    @Test
    void testRenderMinimal() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setIdc01("MEDICAL")
                .setIdc02("H")
                .setIdc03("A")
                .build();
        segment.setContext(context);

        assertEquals("IDC*MEDICAL*H*A~\n", segment.render());
    }

    @Test
    void testRenderFull() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setIdc01("DENTAL")
                .setIdc02("D")
                .setIdc03("R")
                .setIdc04("3")
                .build();
        segment.setContext(context);

        assertEquals("IDC*DENTAL*D*R*3~\n", segment.render());
    }

    @Test
    void testNullContextRender() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setIdc01("MEDICAL")
                .setIdc02("H")
                .setIdc03("A")
                .build();
        assertThrows(IllegalStateException.class, segment::render);
    }

    @Test
    void testGetElementValues() throws ValidationException {
        IDCSegment segment = new IDCSegment.Builder()
                .setIdc01("MEDICAL")
                .setIdc02("H")
                .setIdc03("A")
                .setIdc04("1")
                .build();
        String[] values = segment.getElementValues();
        assertEquals(4, values.length);
        assertEquals("MEDICAL", values[0]);
        assertEquals("H", values[1]);
        assertEquals("A", values[2]);
        assertEquals("1", values[3]);
    }
}
