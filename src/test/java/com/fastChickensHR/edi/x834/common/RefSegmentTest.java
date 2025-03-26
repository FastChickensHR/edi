/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.segments.RefSegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefSegmentTest {
    private static class TestRefSegment extends RefSegment {
        public static final String TEST_QUALIFIER = "1L";

        private TestRefSegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends AbstractBuilder<Builder> {
            public Builder() {
                this.ref01 = TEST_QUALIFIER;
            }

            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestRefSegment build() throws ValidationException {
                if (ref01 == null || ref01.isEmpty()) {
                    throw new ValidationException("ref01 (Reference Identification Qualifier) is required");
                }
                if (ref02 == null || ref02.isEmpty()) {
                    throw new ValidationException("ref02 (Reference Identification) is required");
                }
                return new TestRefSegment(this);
            }
        }
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef02("12345")
                .build();

        assertEquals("REF", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        String qualifier = "TJ";
        String identification = "ABC123";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef01(qualifier)
                .setRef02(identification)
                .build();

        String[] elements = segment.getElementValues();
        assertEquals(2, elements.length);
        assertEquals(qualifier, elements[0]);
        assertEquals(identification, elements[1]);
    }

    @Test
    void testDomainGetters() throws ValidationException {
        String qualifier = "TJ";
        String identification = "ABC123";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef01(qualifier)
                .setRef02(identification)
                .build();

        assertEquals(qualifier, segment.getReferenceIdentificationQualifier());
        assertEquals(identification, segment.getReferenceIdentification());

        assertEquals(qualifier, segment.getRef01());
        assertEquals(identification, segment.getRef02());
    }

    @Test
    void testBuilderWithSpecNamesSetters() throws ValidationException {
        String qualifier = "ZZ";
        String identification = "XYZ789";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef01(qualifier)
                .setRef02(identification)
                .build();

        assertEquals(qualifier, segment.getRef01());
        assertEquals(identification, segment.getRef02());
    }

    @Test
    void testBuilderWithDomainNameSetters() throws ValidationException {
        String qualifier = "ZZ";
        String identification = "XYZ789";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setReferenceIdentificationQualifier(qualifier)
                .setReferenceIdentification(identification)
                .build();

        assertEquals(qualifier, segment.getRef01());
        assertEquals(identification, segment.getRef02());
    }

    @Test
    void testRender() throws ValidationException {
        String qualifier = "Q4";
        String identification = "97531";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef01(qualifier)
                .setRef02(identification)
                .build();
        segment.setContext(new x834Context());

        String rendered = segment.render();
        assertEquals("REF*Q4*97531~", rendered.trim());
    }

    @Test
    void testDefaultQualifier() throws ValidationException {
        String identification = "ABC123";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef02(identification)
                .build();

        assertEquals(TestRefSegment.TEST_QUALIFIER, segment.getRef01());
        assertEquals(identification, segment.getRef02());
    }

    @Test
    void testValidationRequiresRef01AndRef02() {
        // Test missing ref01
        ValidationException exception1 = assertThrows(ValidationException.class, () -> {
            new TestRefSegment.Builder()
                    .setRef01("") // Empty value
                    .setRef02("12345")
                    .build();
        });
        assertTrue(exception1.getMessage().contains("ref01"));

        ValidationException exception2 = assertThrows(ValidationException.class, () -> {
            new TestRefSegment.Builder()
                    .setRef01("ZZ")
                    .setRef02("") // Empty value
                    .build();
        });
        assertTrue(exception2.getMessage().contains("ref02"));
    }

    @Test
    void testChainedSetters() throws ValidationException {
        String qualifier = "18";
        String identification = "98765";

        TestRefSegment segment = new TestRefSegment.Builder()
                .setRef01(qualifier)
                .setRef02(identification)
                .build();

        assertEquals(qualifier, segment.getRef01());
        assertEquals(identification, segment.getRef02());
    }
}