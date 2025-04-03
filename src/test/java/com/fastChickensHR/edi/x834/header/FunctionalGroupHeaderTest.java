/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupHeaderTest {
    private x834Context context;
    private LocalDateTime testDateTime;
    private String formattedDate;
    private String formattedTime;
    private String senderID;
    private String receiverID;
    private String groupControlNumber;

    @BeforeEach
    void setUp() {
        context = new x834Context();
        testDateTime = LocalDateTime.of(2023, 11, 15, 12, 30, 0);
        formattedDate = "20231115";
        formattedTime = "1230";
        senderID = "SENDER123";
        receiverID = "RECEIVER456";
        groupControlNumber = "123456789";

        context.setDocumentDate(testDateTime)
                .setSenderID(senderID)
                .setReceiverID(receiverID)
                .setFormattedDocumentDate(formattedDate)
                .setFormattedDocumentTime(formattedTime)
                .setGroupControlNumber(groupControlNumber);
    }

    /**
     * Tests that the FunctionalGroupHeader uses the correct default values
     * and applies context values correctly.
     */
    @Test
    void testDefaultValuesFromBuilder() throws ValidationException {
        // Build a header with only the required context
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();

        // Verify the default values are set correctly
        assertEquals(FunctionalGroupHeader.DEFAULT_FUNCTIONAL_ID_CODE, header.getFunctionalIdentifierCode(),
                "Should use the default functional ID code (BE)");
        assertEquals(FunctionalGroupHeader.DEFAULT_RESPONSIBLE_AGENCY_CODE, header.getResponsibleAgencyCode(),
                "Should use the default responsible agency code (X)");
        assertEquals(FunctionalGroupHeader.DEFAULT_VERSION_CODE, header.getVersionReleaseIndustryCode(),
                "Should use the default version code (005010X220A1)");

        // Verify context values are copied correctly
        assertEquals(senderID, header.getApplicationSenderCode(),
                "Should use the sender ID from context");
        assertEquals(receiverID, header.getApplicationReceiverCode(),
                "Should use the receiver ID from context");
        assertEquals(formattedDate, header.getTransactionSetCreationDate(),
                "Should use the formatted date from context");
        assertEquals(formattedTime, header.getTransactionSetCreationTime(),
                "Should use the formatted time from context");
    }

    /**
     * Tests that the FunctionalGroupHeader returns the correct segment identifier.
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();
        assertEquals("GS", header.getSegmentIdentifier(),
                "Segment identifier should always be 'GS'");
    }

    /**
     * Tests that the builder applies default values but allows overriding them.
     */
    @Test
    void testOverridingDefaultValues() throws ValidationException {
        // Define non-default values
        FunctionalIdentifierCode customFunctionalId = FunctionalIdentifierCode.fromString("FA");
        String customResponsibleAgency = "Y";
        String customVersionCode = "005010X999";

        // Build with overridden values
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setFunctionalIdentifierCode(customFunctionalId.getCode())
                .setResponsibleAgencyCode(customResponsibleAgency)
                .setVersionReleaseIndustryCode(customVersionCode)
                .build();

        // Verify the custom values are used instead of defaults
        assertEquals(customFunctionalId, header.getFunctionalIdentifierCode(),
                "Should use the custom functional ID code");
        assertEquals(customResponsibleAgency, header.getResponsibleAgencyCode(),
                "Should use the custom responsible agency code");
        assertEquals(customVersionCode, header.getVersionReleaseIndustryCode(),
                "Should use the custom version code");

        // Verify context values are still copied correctly
        assertEquals(senderID, header.getApplicationSenderCode(),
                "Should still use the sender ID from context");
        assertEquals(receiverID, header.getApplicationReceiverCode(),
                "Should still use the receiver ID from context");
    }
}