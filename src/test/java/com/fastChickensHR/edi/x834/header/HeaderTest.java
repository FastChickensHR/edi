/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop1000A.SponsorName;
import com.fastChickensHR.edi.x834.loop1000B.Payer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    private x834Context context;

    @BeforeEach
    void setUp() {
        context = new x834Context();
        // Configure context with any necessary test values
    }

    @Test
    void testHeaderConstructionWithContext() {
        // Test basic construction with context
        Header header = new Header(context);

        assertNotNull(header);
        assertEquals(context, header.getContext());
    }

    @Test
    void testHeaderSettersAndGetters() {
        // Test setters and getters for the header fields
        Header header = new Header(context);

        String interchangeControlNumber = "000000001";
        String groupControlNumber = "42";
        String transactionSetIdentifierCode = "834";
        String transactionSetControlNumber = "4321";
        String referenceIdentification = "REF123";
        String masterPolicyNumber = "POL123456";
        String planSponsorName = "FastChickensHR Corp";
        String payerName = "Insurance Co";
        String payerIdentification = "PAYERID123";

        header.setInterchangeControlNumber(interchangeControlNumber);
        header.setGroupControlNumber(groupControlNumber);
        header.setTransactionSetIdentifierCode(transactionSetIdentifierCode);
        header.setTransactionSetControlNumber(transactionSetControlNumber);
        header.setReferenceIdentification(referenceIdentification);
        header.setMasterPolicyNumber(masterPolicyNumber);
        header.setPlanSponsorName(planSponsorName);
        header.setPayerName(payerName);
        header.setPayerIdentification(payerIdentification);

        // Verify all fields were set correctly
        assertEquals(interchangeControlNumber, header.getInterchangeControlNumber());
        assertEquals(groupControlNumber, header.getGroupControlNumber());
        assertEquals(transactionSetIdentifierCode, header.getTransactionSetIdentifierCode());
        assertEquals(transactionSetControlNumber, header.getTransactionSetControlNumber());
        assertEquals(referenceIdentification, header.getReferenceIdentification());
        assertEquals(masterPolicyNumber, header.getMasterPolicyNumber());
        assertEquals(planSponsorName, header.getPlanSponsorName());
        assertEquals(payerName, header.getPayerName());
        assertEquals(payerIdentification, header.getPayerIdentification());
    }

    @Test
    void testCustomBuilderSettersAndGetters() {
        // Test setting and getting custom builders
        Header header = new Header(context);

        InterchangeControlHeader.Builder interchangeBuilder = new InterchangeControlHeader.Builder(context);
        FunctionalGroupHeader.Builder functionalBuilder = new FunctionalGroupHeader.Builder(context);
        TransactionSetHeader.Builder transactionSetBuilder = new TransactionSetHeader.Builder();
        BeginningSegment.Builder beginningSegmentBuilder = new BeginningSegment.Builder(context);
        FileEffectiveDate.Builder fileEffectiveDateBuilder = new FileEffectiveDate.Builder(context);
        TransactionSetPolicyNumber.Builder policyNumberBuilder = new TransactionSetPolicyNumber.Builder();
        SponsorName.Builder sponsorBuilder = new SponsorName.Builder();
        Payer.Builder payerBuilder = new Payer.Builder();

        header.setCustomInterchangeBuilder(interchangeBuilder);
        header.setCustomFunctionalBuilder(functionalBuilder);
        header.setCustomTransactionSetBuilder(transactionSetBuilder);
        header.setCustomBeginningSegmentBuilder(beginningSegmentBuilder);
        header.setCustomFileEffectiveDateBuilder(fileEffectiveDateBuilder);
        header.setCustomPolicyNumberBuilder(policyNumberBuilder);
        header.setCustomSponsorBuilder(sponsorBuilder);
        header.setCustomPayerBuilder(payerBuilder);

        // Verify all builders were set correctly
        assertEquals(interchangeBuilder, header.getCustomInterchangeBuilder());
        assertEquals(functionalBuilder, header.getCustomFunctionalBuilder());
        assertEquals(transactionSetBuilder, header.getCustomTransactionSetBuilder());
        assertEquals(beginningSegmentBuilder, header.getCustomBeginningSegmentBuilder());
        assertEquals(fileEffectiveDateBuilder, header.getCustomFileEffectiveDateBuilder());
        assertEquals(policyNumberBuilder, header.getCustomPolicyNumberBuilder());
        assertEquals(sponsorBuilder, header.getCustomSponsorBuilder());
        assertEquals(payerBuilder, header.getCustomPayerBuilder());
    }

    @Test
    void testGenerateSegments() throws ValidationException {
        // Test segment generation with required fields
        Header header = new Header(context);
        header.setInterchangeControlNumber("000000001");
        header.setReferenceIdentification("REF123");
        header.setMasterPolicyNumber("POL123456");
        header.setPlanSponsorName("FastChickensHR Corp");
        header.setPayerName("Insurance Co");
        header.setPayerIdentification("PAYERID123");

        List<Segment> segments = header.generateSegments();

        assertNotNull(segments);
        assertFalse(segments.isEmpty());

        // Should contain segments for ISA, GS, ST, BGN, DTP, REF, N1 (sponsor), N1 (payer)
        // The exact number depends on how many segments each component creates
        assertTrue(segments.size() >= 8);

        // Verify segment identifiers in expected order
        assertEquals("ISA", segments.get(0).getSegmentIdentifier());
        assertEquals("GS", segments.get(1).getSegmentIdentifier());
        assertEquals("ST", segments.get(2).getSegmentIdentifier());
        assertEquals("BGN", segments.get(3).getSegmentIdentifier());
        // Additional segments would follow
    }

    @Test
    void testValidation() {
        // Test validation with missing required fields
        Header header = new Header(context);
        // Intentionally not setting required fields

        Exception exception = assertThrows(ValidationException.class, () -> {
            header.validate();
        });

        // The validation message should mention missing fields
        assertTrue(exception.getMessage().contains("required"));
    }

    @Test
    void testHeaderBuilder() throws ValidationException {
        // Test the Header.Builder functionality
        Header.Builder builder = new Header.Builder(context)
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("42")
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("4321")
                .setReferenceIdentification("REF123")
                .setMasterPolicyNumber("POL123456")
                .setPlanSponsorName("FastChickensHR Corp")
                .setPayerName("Insurance Co")
                .setPayerIdentification("PAYERID123");

        Header header = builder.build();

        // Verify all fields were set correctly through the builder
        assertEquals("000000001", header.getInterchangeControlNumber());
        assertEquals("42", header.getGroupControlNumber());
        assertEquals("834", header.getTransactionSetIdentifierCode());
        assertEquals("4321", header.getTransactionSetControlNumber());
        assertEquals("REF123", header.getReferenceIdentification());
        assertEquals("POL123456", header.getMasterPolicyNumber());
        assertEquals("FastChickensHR Corp", header.getPlanSponsorName());
        assertEquals("Insurance Co", header.getPayerName());
        assertEquals("PAYERID123", header.getPayerIdentification());
    }

    @Test
    void testBuilderWithCustomBuilders() throws ValidationException {
        // Test setting custom builders through the Header.Builder
        InterchangeControlHeader.Builder interchangeBuilder = new InterchangeControlHeader.Builder(context);
        FunctionalGroupHeader.Builder functionalBuilder = new FunctionalGroupHeader.Builder(context);

        Header.Builder builder = new Header.Builder(context)
                .setInterchangeControlHeaderBuilder(interchangeBuilder)
                .setFunctionalGroupHeaderBuilder(functionalBuilder);

        Header header = builder.build();

        // Verify custom builders were set correctly
        assertEquals(interchangeBuilder, header.getCustomInterchangeBuilder());
        assertEquals(functionalBuilder, header.getCustomFunctionalBuilder());
    }

    @Test
    void testBuilderWithNullContext() {
        // Test that builder throws exception when context is null
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Header.Builder(null);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("context cannot be null"));
    }

    @Test
    void testBuilderDefaultValues() throws ValidationException {
        // Test default values in the builder
        Header header = new Header.Builder(context).build();

        // Verify default values against what we expect
        assertEquals("1", header.getGroupControlNumber());
        assertEquals("834", header.getTransactionSetIdentifierCode());
        assertEquals("0001", header.getTransactionSetControlNumber());

        // Other fields should be null since they're required
        assertNull(header.getInterchangeControlNumber());
        assertNull(header.getReferenceIdentification());
        assertNull(header.getMasterPolicyNumber());
        assertNull(header.getPlanSponsorName());
        assertNull(header.getPayerName());
        assertNull(header.getPayerIdentification());
    }
}