/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.data.InterchangeUsageIndicator;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for InterchangeControlHeader.
 * Focuses only on testing the X834-specific defaults and behavior, as general ISA behavior
 * is already tested in ISASegmentTest.
 */
class InterchangeControlHeaderTest {
    String interchangeControlNumber = "FakeControlNumber";
    @Test
    void testDefaultValues() throws ValidationException {
        X834Context context = new X834Context();
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
        assertEquals("RECEIVER456    ", header.getInterchangeReceiverID());
        assertEquals("230630", header.getInterchangeDate());
        assertEquals(context.getFormattedDocumentTime(), header.getInterchangeTime());
        assertEquals(context, header.getContext());
    }

    @Test
    void testPaddingOfSenderID() throws ValidationException {
        X834Context context = new X834Context();
        context.setSenderID("ABC");
        context.setReceiverID("RECEIVER456");
        context.setDocumentDate(LocalDateTime.of(2023, 6, 30, 0, 0));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context).setInterchangeControlNumber(interchangeControlNumber).build();

        assertEquals("ABC            ", header.getInterchangeSenderID());
    }

    /**
     * Render golden for the ISA segment assembled from context defaults. Whole-string equality pins the
     * defaults ({@code 00}/{@code 00}/{@code 30}/{@code ZZ}/{@code ^}/{@code 00501}/{@code 0}/{@code T}/{@code :}),
     * both the sender ID (ISA06) and receiver ID (ISA08) right-padded to 15 characters, and the ISA09
     * interchange date as the ISA-native 6-digit {@code YYMMDD} — distinct from the 8-digit {@code CCYYMMDD}
     * that GS04/BGN03/DTP carry — the fixed-width layout the getter assertions never render.
     */
    @Test
    void rendersIsaSegmentFromContextDefaults() throws ValidationException {
        X834Context context = new X834Context();
        context.setSenderID("SENDER123");
        context.setReceiverID("RECEIVER456");
        context.setDocumentDate(LocalDateTime.of(2023, 6, 30, 0, 0));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context)
                .setInterchangeControlNumber("000000001")
                .build();
        header.setContext(context);

        assertEquals(
                "ISA*00*          *00*          *30*SENDER123      *ZZ*RECEIVER456    *230630*0000*^*00501*000000001*0*T*:~\n",
                header.render());
    }

    @Test
    void testCustomValuesOverrideDefaults() throws ValidationException {
        X834Context context = new X834Context();
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