/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.data.InterchangeUsageIndicator;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class for InterchangeControlHeader.
 * Focuses only on testing the X834-specific defaults and behavior, as general ISA behavior
 * is already tested in ISASegmentTest.
 */
class InterchangeControlHeaderTest {
    String interchangeControlNumber = "FakeControlNumber";
    @Test
    void testDefaultValues() throws ValidationException {
        x834Context context = new x834Context();
        context.setSenderID("SENDER123");
        context.setReceiverID("RECEIVER456");
        context.setDocumentDate(LocalDateTime.of(2023, 6, 30, 0, 0));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context).setInterchangeControlNumber(interchangeControlNumber).build();

        assertEquals(InterchangeControlHeader.DEFAULT_AUTHORIZATION_INFO_QUALIFIER, header.getAuthorizationInformationQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_AUTHORIZATION_INFO, header.getAuthorizationInformation());
        assertEquals(InterchangeControlHeader.DEFAULT_SECURITY_INFO_QUALIFIER, header.getSecurityInformationQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_SECURITY_INFO, header.getSecurityInformation());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_SENDER_QUALIFIER, header.getInterchangeSenderQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER, header.getInterchangeReceiverQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_REPETITION_SEPARATOR, header.getInterchangeControlStandardsIdentifier());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_CONTROL_VERSION, header.getInterchangeControlVersionNumber());
        assertEquals(InterchangeControlHeader.DEFAULT_ACKNOWLEDGMENT_REQUESTED, header.getAcknowledgmentRequested());
        assertEquals(InterchangeControlHeader.DEFAULT_USAGE_INDICATOR, header.getUsageIndicator());
        assertEquals(InterchangeControlHeader.DEFAULT_COMPONENT_ELEMENT_SEPARATOR, header.getComponentElementSeparator());

        assertEquals("SENDER123      ", header.getInterchangeSenderID());
        assertEquals(context.getReceiverID(), header.getInterchangeReceiverID());
        assertEquals(context.getFormattedDocumentDate(), header.getInterchangeDate());
        assertEquals(context.getFormattedDocumentTime(), header.getInterchangeTime());
        assertEquals(context, header.getContext());
    }

    @Test
    void testPaddingOfSenderID() throws ValidationException {
        x834Context context = new x834Context();
        context.setSenderID("ABC");
        context.setReceiverID("RECEIVER456");
        context.setDocumentDate(LocalDateTime.of(2023, 6, 30, 0, 0));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context).setInterchangeControlNumber(interchangeControlNumber).build();

        assertEquals("ABC            ", header.getInterchangeSenderID());
    }

    @Test
    void testContextAccess() throws ValidationException {
        x834Context context = new x834Context();
        context.setSenderID("SENDER123");
        context.setReceiverID("RECEIVER456");

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context).setInterchangeControlNumber(interchangeControlNumber).build();

        assertSame(context, header.getContext());
    }

    @Test
    void testCustomValuesOverrideDefaults() throws ValidationException {
        x834Context context = new x834Context();
        context.setSenderID("SENDER123");
        context.setReceiverID("RECEIVER456");
        context.setDocumentDate(LocalDateTime.of(2023, 6, 30, 0, 0));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context)
                .setIsa11("*")
                .setInterchangeControlNumber(interchangeControlNumber)
                .setIsa15(InterchangeUsageIndicator.fromString("P").getCode())
                .build();

        assertEquals("*", header.getInterchangeControlStandardsIdentifier());
        assertEquals(InterchangeUsageIndicator.fromString("P"), header.getUsageIndicator());

        assertEquals(InterchangeControlHeader.DEFAULT_AUTHORIZATION_INFO_QUALIFIER, header.getAuthorizationInformationQualifier());
    }
}