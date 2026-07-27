/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.SegmentTestSupport;
import com.fastChickensHR.edi.x834.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.x834.data.ResponsibleAgencyCode;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupHeaderTest {
    private X834Context context;
    private LocalDateTime testDateTime;
    private String formattedDate;
    private String formattedTime;
    private String senderID;
    private String receiverID;
    private String groupControlNumber;

    @BeforeEach
    void setUp() {
        context = new X834Context();
        testDateTime = LocalDateTime.of(2023, 11, 15, 12, 30, 0);
        formattedDate = "20231115";
        formattedTime = "1230";
        senderID = "SENDER123";
        receiverID = "RECEIVER456";
        groupControlNumber = "123456789";

        context.setDocumentDate(testDateTime)
                .setSenderID(senderID)
                .setReceiverID(receiverID)
                .setGroupControlNumber(groupControlNumber);
    }

    /**
     * Tests that the FunctionalGroupHeader uses the correct default values
     * and applies context values correctly.
     */
    @Test
    void testDefaultValuesFromBuilder() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();

        assertEquals("BE", header.getFunctionalIdentifierCode().getCode(),
                "Should use the default functional ID code (BE)");
        assertEquals(ResponsibleAgencyCode.ASC_X12, header.getResponsibleAgencyCode(),
                "Should use the default responsible agency code (X)");
        assertEquals("005010X220A1", header.getVersionReleaseIndustryCode().getCode(),
                "Should use the default version code (005010X220A1)");

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
     * GS02/GS03 are the <em>application</em> party codes, independent of the ISA06/ISA08
     * interchange identifiers: a partner may assign a sender code distinct from the mailbox ID
     * and mandate a GS03 that is not the interchange receiver ID.
     */
    @Test
    void applicationPartyCodesOverrideTheInterchangeIdentifiers() throws ValidationException {
        context.setApplicationSenderCode("SNDRCODE9")
                .setApplicationReceiverCode("RBG005010X220A1");

        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();

        assertEquals("SNDRCODE9", header.getApplicationSenderCode(),
                "GS02 should be the application sender code, not the ISA06 sender ID");
        assertEquals("RBG005010X220A1", header.getApplicationReceiverCode(),
                "GS03 should be the application receiver code, not the ISA08 receiver ID");
    }

    /** Each side falls back independently — setting one must not drag the other off the ISA id. */
    @Test
    void anUnsetApplicationCodeFallsBackWhileTheOtherSideOverrides() throws ValidationException {
        context.setApplicationReceiverCode("AICK");

        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();

        assertEquals(senderID, header.getApplicationSenderCode(),
                "GS02 should fall back to the ISA06 sender ID when no application sender code is set");
        assertEquals("AICK", header.getApplicationReceiverCode(),
                "GS03 should use the application receiver code that was set");
    }

    /** A blank code is "unset", matching how the generator treats absent/blank envelope fields. */
    @Test
    void blankApplicationCodesFallBackToTheInterchangeIdentifiers() throws ValidationException {
        context.setApplicationSenderCode("")
                .setApplicationReceiverCode("   ");

        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();

        assertEquals(senderID, header.getApplicationSenderCode(),
                "A blank application sender code should fall back to the ISA06 sender ID");
        assertEquals(receiverID, header.getApplicationReceiverCode(),
                "A blank application receiver code should fall back to the ISA08 receiver ID");
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
     * Render golden for the GS segment assembled from context defaults. Whole-string equality pins
     * that the {@code BE}/{@code X}/{@code 005010X220A1} defaults and the sender, receiver, formatted
     * date/time, and group control number pulled from {@link X834Context} all land in the right GS
     * element positions — a single assertion covering what the per-field getter checks above verify
     * piecemeal, plus the rendered layout they never touch. The document date is pinned via the
     * context (never {@code now()}), keeping the golden deterministic.
     */
    @Test
    void rendersGsSegmentFromContextDefaults() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context).build();
        SegmentTestSupport.setContext(header, context);

        assertEquals("GS*BE*SENDER123*RECEIVER456*20231115*1230*123456789*X*005010X220A1~\n",
                header.render());
    }

    /**
     * Tests that the builder applies default values but allows overriding them.
     */
    @Test
    void testOverridingDefaultValues() throws ValidationException {
        FunctionalIdentifierCode customFunctionalId = FunctionalIdentifierCode.fromString("FA");
        String customResponsibleAgency = "T";
        String customVersionCode = "004050";

        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setFunctionalIdentifierCode(customFunctionalId.getCode())
                .setResponsibleAgencyCode(customResponsibleAgency)
                .setVersionReleaseIndustryCode(customVersionCode)
                .build();

        assertEquals(customFunctionalId, header.getFunctionalIdentifierCode(),
                "Should use the custom functional ID code");
        assertEquals(customResponsibleAgency, header.getResponsibleAgencyCode().getCode(),
                "Should use the custom responsible agency code");
        assertEquals(customVersionCode, header.getVersionReleaseIndustryCode().getCode(),
                "Should use the custom version code");

        assertEquals(senderID, header.getApplicationSenderCode(),
                "Should still use the sender ID from context");
        assertEquals(receiverID, header.getApplicationReceiverCode(),
                "Should still use the receiver ID from context");
    }
}