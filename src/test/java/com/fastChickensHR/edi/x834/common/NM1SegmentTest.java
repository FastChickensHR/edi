/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.segments.NM1Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NM1SegmentTest {

    private x834Context context;
    private final String entityIdentifierCode = "IL";
    private final String entityTypeQualifier = "1";
    private final String lastNameOrOrg = "DOE";
    private final String firstName = "JOHN";
    private final String middleName = "Q";
    private final String namePrefix = "MR";
    private final String nameSuffix = "JR";
    private final String idCodeQualifier = "34";
    private final String idCode = "123456789";

    private static class TestNM1Segment extends NM1Segment {
        protected TestNM1Segment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestNM1Segment build() throws ValidationException {
                validateRequiredFields();
                return new TestNM1Segment(this);
            }

            private void validateRequiredFields() throws ValidationException {
                if (nm101 == null || nm101.isEmpty()) {
                    throw new ValidationException("Entity Identifier Code (NM101) is required");
                }
                if (nm102 == null || nm102.isEmpty()) {
                    throw new ValidationException("Entity Type Qualifier (NM102) is required");
                }
                if (nm103 == null || nm103.isEmpty()) {
                    throw new ValidationException("Name Last or Organization Name (NM103) is required");
                }
            }
        }
    }

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        NM1Segment segment = createMinimalSegment();
        assertEquals("NM1", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        NM1Segment segment = createFullSegment();
        String[] elements = segment.getElementValues();

        assertEquals(9, elements.length);
        assertEquals(entityIdentifierCode, elements[0]);
        assertEquals(entityTypeQualifier, elements[1]);
        assertEquals(lastNameOrOrg, elements[2]);
        assertEquals(firstName, elements[3]);
        assertEquals(middleName, elements[4]);
        assertEquals(namePrefix, elements[5]);
        assertEquals(nameSuffix, elements[6]);
        assertEquals(idCodeQualifier, elements[7]);
        assertEquals(idCode, elements[8]);
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        NM1Segment segment = new TestNM1Segment.Builder()
                .setNm101(entityIdentifierCode)
                .setNm102(entityTypeQualifier)
                .setNm103(lastNameOrOrg)
                .build();

        assertEquals(entityIdentifierCode, segment.getEntityIdentifierCode());
        assertEquals(entityTypeQualifier, segment.getEntityTypeQualifier());
        assertEquals(lastNameOrOrg, segment.getLastName());
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        NM1Segment segment = new TestNM1Segment.Builder()
                .setEntityIdentifierCode(entityIdentifierCode)
                .setEntityTypeQualifier(entityTypeQualifier)
                .setLastName(lastNameOrOrg)
                .build();

        assertEquals(entityIdentifierCode, segment.getNm101());
        assertEquals(entityTypeQualifier, segment.getNm102());
        assertEquals(lastNameOrOrg, segment.getNm103());
    }

    @Test
    void testRender() throws ValidationException {
        NM1Segment segment = createFullSegment();
        segment.setContext(context);

        assertEquals("NM1*IL*1*DOE*JOHN*Q*MR*JR*34*123456789~", segment.render().trim(), "Render should properly format the segment");

    }

    @Test
    void testValidationRequiresNm101() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new TestNM1Segment.Builder()
                        .setNm102(entityTypeQualifier)
                        .setNm103(lastNameOrOrg)
                        .build()
        );

        assertTrue(exception.getMessage().contains("Entity Identifier Code"));
    }

    @Test
    void testValidationRequiresNm102() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new TestNM1Segment.Builder()
                        .setNm101(entityIdentifierCode)
                        .setNm103(lastNameOrOrg)
                        .build()
        );

        assertTrue(exception.getMessage().contains("Entity Type Qualifier"));
    }

    @Test
    void testValidationRequiresNm103() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new TestNM1Segment.Builder()
                        .setNm101(entityIdentifierCode)
                        .setNm102(entityTypeQualifier)
                        .build()
        );

        assertTrue(exception.getMessage().contains("Name Last or Organization Name"));
    }

    @Test
    void testBuilderIsProperlyChainable() throws ValidationException {
        TestNM1Segment segment = new TestNM1Segment.Builder()
                .setNm101(entityIdentifierCode)
                .setNm102(entityTypeQualifier)
                .setNm103(lastNameOrOrg)
                .setNm104(firstName)
                .setNm105(middleName)
                .setNm106(namePrefix)
                .setNm107(nameSuffix)
                .setNm108(idCodeQualifier)
                .setNm109(idCode)
                .build();

        assertNotNull(segment);
    }

    @Test
    void testDomainMethodsReturnsCorrectValues() throws ValidationException {
        NM1Segment segment = createFullSegment();

        assertEquals(entityIdentifierCode, segment.getEntityIdentifierCode());
        assertEquals(entityTypeQualifier, segment.getEntityTypeQualifier());
        assertEquals(lastNameOrOrg, segment.getLastName());
        assertEquals(firstName, segment.getFirstName());
        assertEquals(middleName, segment.getMiddleName());
        assertEquals(namePrefix, segment.getNamePrefix());
        assertEquals(nameSuffix, segment.getNameSuffix());
        assertEquals(idCodeQualifier, segment.getIdentificationCodeQualifier());
        assertEquals(idCode, segment.getIdentificationCode());
    }

    private NM1Segment createMinimalSegment() throws ValidationException {
        return new TestNM1Segment.Builder()
                .setNm101(entityIdentifierCode)
                .setNm102(entityTypeQualifier)
                .setNm103(lastNameOrOrg)
                .build();
    }

    private NM1Segment createFullSegment() throws ValidationException {
        return new TestNM1Segment.Builder()
                .setNm101(entityIdentifierCode)
                .setNm102(entityTypeQualifier)
                .setNm103(lastNameOrOrg)
                .setNm104(firstName)
                .setNm105(middleName)
                .setNm106(namePrefix)
                .setNm107(nameSuffix)
                .setNm108(idCodeQualifier)
                .setNm109(idCode)
                .build();
    }
}