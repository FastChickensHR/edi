/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.segments.N1Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class N1SegmentTest {

    private static class TestN1Segment extends N1Segment {
        public static final String TEST_ENTITY_CODE = "TEST";

        private TestN1Segment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestN1Segment build() throws ValidationException {
                return new TestN1Segment(this);
            }
        }
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestN1Segment segment = new TestN1Segment.Builder().build();
        assertEquals("N1", segment.getSegmentIdentifier(), "Segment ID should be N1");
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String entityCode = "ABC";
        String name = "Test Name";
        String qualifier = "QR";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setN101(entityCode)
                .setN102(name)
                .setN103(qualifier)
                .setN104(identifier)
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(4, elements.length, "Should return array with 4 elements");
        assertEquals(entityCode, elements[0], "First element should be the entity code");
        assertEquals(name, elements[1], "Second element should be the name");
        assertEquals(qualifier, elements[2], "Third element should be the qualifier");
        assertEquals(identifier, elements[3], "Fourth element should be the identifier");
    }

    @Test
    void testDomainGetters() throws ValidationException {
        String entityCode = "ABC";
        String name = "Test Name";
        String qualifier = "QR";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode(entityCode)
                .setPlanSponsorName(name)
                .setIdentificationCodeQualifier(qualifier)
                .setSponsorIdentifier(identifier)
                .build();

        assertEquals(entityCode, segment.getEntityIdentifierCode(), "getEntityIdentifierCode should return the correct value");
        assertEquals(name, segment.getPlanSponsorName(), "getPlanSponsorName should return the correct value");
        assertEquals(qualifier, segment.getIdentificationCodeQualifier(), "getIdentificationCodeQualifier should return the correct value");
        assertEquals(identifier, segment.getSponsorIdentifier(), "getSponsorIdentifier should return the correct value");
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String entityCode = "ABC";
        String name = "Test Name";
        String qualifier = "QR";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setN101(entityCode)
                .setN102(name)
                .setN103(qualifier)
                .setN104(identifier)
                .build();

        assertEquals(entityCode, segment.getN101(), "N101 should be set correctly");
        assertEquals(name, segment.getN102(), "N102 should be set correctly");
        assertEquals(qualifier, segment.getN103(), "N103 should be set correctly");
        assertEquals(identifier, segment.getN104(), "N104 should be set correctly");
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String entityCode = "ABC";
        String name = "Test Name";
        String qualifier = "QR";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode(entityCode)
                .setPlanSponsorName(name)
                .setIdentificationCodeQualifier(qualifier)
                .setSponsorIdentifier(identifier)
                .build();

        assertEquals(entityCode, segment.getN101(), "N101 should be set correctly");
        assertEquals(name, segment.getN102(), "N102 should be set correctly");
        assertEquals(qualifier, segment.getN103(), "N103 should be set correctly");
        assertEquals(identifier, segment.getN104(), "N104 should be set correctly");
    }

    @Test
    void testRender() throws ValidationException {
        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode("ABC")
                .setPlanSponsorName("Test Name")
                .setIdentificationCodeQualifier("QR")
                .setSponsorIdentifier("123456")
                .build();

        segment.setContext(new x834Context());
        assertEquals("N1*ABC*Test Name*QR*123456~", segment.render().trim(), "Render should properly format the segment");
    }
}