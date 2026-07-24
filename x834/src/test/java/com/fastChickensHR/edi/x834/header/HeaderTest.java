/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.segments.Segment;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.testsupport.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    private X834Context context;

    @BeforeEach
    void setUp() {
        context = new X834Context()
                .setSenderID("FASTCHKN")
                .setReceiverID("MICHGVEDI")
                .setElementSeparator(ElementSeparator.PIPE)
                .setDocumentDate(LocalDateTime.of(2023, 8, 1, 0, 0))
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("42");
    }

    @Test
    void testHeaderConstructionWithContext() {
        // Test basic construction with context
        Header header = new Header(context);

        assertNotNull(header);
        assertEquals(context, header.getContext());
    }

    @Test
    void testGenerateSegments() throws ValidationException {
        Header header = new Header(context);
        header.setReferenceIdentification("REF123");
        header.setMasterPolicyNumber("POL123456");
        header.setPlanSponsorName("FastChickensHR Corp");
        header.setPayerName("Insurance Co");
        header.setTransactionSetIdentifierCode("834");

        List<Segment> segments = header.generateSegments();

        assertNotNull(segments);
        assertFalse(segments.isEmpty());

        assertTrue(segments.size() >= 8);

        assertEquals("ISA", segments.get(0).getSegmentIdentifier());
        assertEquals("GS", segments.get(1).getSegmentIdentifier());
        assertEquals("ST", segments.get(2).getSegmentIdentifier());
        assertEquals("BGN", segments.get(3).getSegmentIdentifier());
    }

    /**
     * Whole-payload golden for the assembled header: every segment {@link Header#generateSegments()}
     * produces (ISA, GS, ST, BGN, DTP file-effective-date, REF policy number, N1 sponsor, N1 payer),
     * rendered in order into one string and compared against an on-disk golden. Whole-payload equality
     * pins segment order, element positions, and each segment's terminator at once — and, because this
     * {@code context} uses the pipe element separator, proves that separator propagates into every
     * rendered segment. The document date is pinned via the context, so the golden is deterministic.
     * <p>
     * Regenerate after an intentional format change with {@code -Dupdate.goldens=true} (see
     * {@link TestFixtures}).
     */
    @Test
    void rendersFullHeaderPayload() throws ValidationException {
        Header header = new Header.Builder(context)
                .setReferenceIdentification("REF123")
                .setMasterPolicyNumber("POL123456")
                .setPlanSponsorName("FastChickensHR Corp")
                .setPayerName("Insurance Co")
                .setPayerIdentification("PAYERID123")
                .build();

        StringBuilder rendered = new StringBuilder();
        for (Segment segment : header.generateSegments()) {
            segment.setContext(context);
            rendered.append(segment.render());
        }

        TestFixtures.assertMatchesGolden("golden/header-full-envelope.834", rendered.toString());
    }

    @Test
    void testValidation() {
        Header header = new Header(context);
        Exception exception = assertThrows(ValidationException.class, header::validate);

        assertTrue(exception.getMessage().contains("required"));
    }

    @Test
    void testHeaderBuilder() throws ValidationException {
        Header.Builder builder = new Header.Builder(context)
                .setTransactionSetIdentifierCode("834")
                .setReferenceIdentification("REF123")
                .setMasterPolicyNumber("POL123456")
                .setPlanSponsorName("FastChickensHR Corp")
                .setPayerName("Insurance Co")
                .setPayerIdentification("PAYERID123");

        Header header = builder.build();

        assertEquals("834", header.getTransactionSetIdentifierCode());
        assertEquals("REF123", header.getReferenceIdentification());
        assertEquals("POL123456", header.getMasterPolicyNumber());
        assertEquals("FastChickensHR Corp", header.getPlanSponsorName());
        assertEquals("Insurance Co", header.getPayerName());
        assertEquals("PAYERID123", header.getPayerIdentification());
    }

    @Test
    void testBuilderWithCustomBuilders() throws ValidationException {
        InterchangeControlHeader.Builder interchangeBuilder = new InterchangeControlHeader.Builder(context);
        FunctionalGroupHeader.Builder functionalBuilder = new FunctionalGroupHeader.Builder(context);

        Header.Builder builder = new Header.Builder(context)
                .setInterchangeControlHeaderBuilder(interchangeBuilder)
                .setFunctionalGroupHeaderBuilder(functionalBuilder);

        Header header = builder.build();

        assertEquals(interchangeBuilder, header.getCustomInterchangeBuilder());
        assertEquals(functionalBuilder, header.getCustomFunctionalBuilder());
    }

    @Test
    void testBuilderDefaultValues() throws ValidationException {
        Header header = new Header.Builder(context).build();

        assertEquals("834", header.getTransactionSetIdentifierCode());

        assertNull(header.getReferenceIdentification());
        assertNull(header.getMasterPolicyNumber());
        assertNull(header.getPlanSponsorName());
        assertNull(header.getPayerName());
        assertNull(header.getPayerIdentification());
    }
}