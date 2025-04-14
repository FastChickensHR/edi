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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class N1SegmentTest {
    String entityCode = "01";
    String qualifier = "PB";

    private static class TestN1Segment extends N1Segment {

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
        TestN1Segment segment = new TestN1Segment.Builder().setN101(entityCode).setN102("fake name").build();
        assertEquals("N1", segment.getSegmentIdentifier(), "Segment ID should be N1");
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String name = "Test Name";
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
        String name = "Test Name";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode(entityCode)
                .setPlanSponsorName(name)
                .setIdentificationCodeQualifier(qualifier)
                .setSponsorIdentifier(identifier)
                .build();

        assertEquals(entityCode, segment.getEntityIdentifierCode().getCode(), "getEntityIdentifierCode should return the correct value");
        assertEquals(name, segment.getPlanSponsorName(), "getPlanSponsorName should return the correct value");
        assertEquals(qualifier, segment.getIdentificationCodeQualifier().getCode(), "getIdentificationCodeQualifier should return the correct value");
        assertEquals(identifier, segment.getSponsorIdentifier(), "getSponsorIdentifier should return the correct value");
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String name = "Test Name";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setN101(entityCode)
                .setN102(name)
                .setN103(qualifier)
                .setN104(identifier)
                .build();

        assertEquals(entityCode, segment.getN101().getCode(), "N101 should be set correctly");
        assertEquals(name, segment.getN102(), "N102 should be set correctly");
        assertEquals(qualifier, segment.getN103().getCode(), "N103 should be set correctly");
        assertEquals(identifier, segment.getN104(), "N104 should be set correctly");
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String name = "Test Name";
        String identifier = "123456";

        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode(entityCode)
                .setPlanSponsorName(name)
                .setIdentificationCodeQualifier(qualifier)
                .setSponsorIdentifier(identifier)
                .build();

        assertEquals(entityCode, segment.getN101().getCode(), "N101 should be set correctly");
        assertEquals(name, segment.getN102(), "N102 should be set correctly");
        assertEquals(qualifier, segment.getN103().getCode(), "N103 should be set correctly");
        assertEquals(identifier, segment.getN104(), "N104 should be set correctly");
    }

    @Test
    void testRender() throws ValidationException {
        TestN1Segment segment = new TestN1Segment.Builder()
                .setEntityIdentifierCode(entityCode)
                .setPlanSponsorName("Test Name")
                .setIdentificationCodeQualifier(qualifier)
                .setSponsorIdentifier("123456")
                .build();

        segment.setContext(new x834Context());
        assertEquals("N1*01*Test Name*PB*123456~", segment.render().trim(), "Render should properly format the segment");
    }
}