/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class N3SegmentTest {

    private x834Context context;
    private final String addressLine1 = "123 MAIN STREET";
    private final String addressLine2 = "APT 4B";

    // Test implementation of N3Segment for testing purposes
    private static class TestN3Segment extends N3Segment {
        protected TestN3Segment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestN3Segment build() throws ValidationException {
                validateRequiredFields();
                return new TestN3Segment(this);
            }

            private void validateRequiredFields() throws ValidationException {
                if (n301 == null || n301.isEmpty()) {
                    throw new ValidationException("Address Line 1 (N301) is required");
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
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .build();
        assertEquals("N3", segment.getSegmentIdentifier());
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        TestN3Segment.Builder builder = TestN3Segment.builder();

        // Setting using spec field names
        builder.n301 = addressLine1;
        builder.n302 = addressLine2;

        TestN3Segment segment = builder.build();

        // Getting using domain field names
        assertEquals(addressLine1, segment.getAddressLine1());
        assertEquals(addressLine2, segment.getAddressLine2());
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        // Get the element values array which uses the spec field names internally
        String[] elements = segment.getElementValues();

        assertEquals(addressLine1, elements[0]); // n301
        assertEquals(addressLine2, elements[1]); // n302
    }

    @Test
    void testDirectAccessToSpecFields() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        assertEquals(addressLine1, segment.getN301());
        assertEquals(addressLine2, segment.getN302());
    }

    @Test
    void testRenderWithBothAddressLines() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        segment.setContext(context);

        String rendered = segment.render().trim();
        String expected = "N3*" + addressLine1 + "*" + addressLine2 + "~";

        assertEquals(expected, rendered);
    }

    @Test
    void testRenderWithOnlyAddressLine1() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .build();

        segment.setContext(context);

        String rendered = segment.render().trim();
        String expected = "N3*" + addressLine1 + "~";

        assertEquals(expected, rendered);
    }

    @Test
    void testValidationRequiresAddressLine1() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            TestN3Segment.Builder builder = TestN3Segment.builder();
            builder.n301 = null;
            builder.build();
        });

        assertTrue(exception.getMessage().contains("Address Line 1"));
    }

    @Test
    void testBuilderIsProperlyChainable() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        assertNotNull(segment);
        assertEquals(addressLine1, segment.getAddressLine1());
        assertEquals(addressLine2, segment.getAddressLine2());
    }

    @Test
    void testBuilderMethodsUsingSpecNames() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setN301(addressLine1)
                .setN302(addressLine2)
                .build();

        assertNotNull(segment);
        assertEquals(addressLine1, segment.getN301());
        assertEquals(addressLine2, segment.getN302());
    }

    @Test
    void testNullAddressLine2() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .build();

        assertNotNull(segment);
        assertEquals(addressLine1, segment.getAddressLine1());
        assertNull(segment.getAddressLine2());

        String[] elements = segment.getElementValues();
        assertEquals(2, elements.length);
        assertEquals(addressLine1, elements[0]);
        assertNull(elements[1]);
    }

    @Test
    void testEmptyAddressLine2() throws ValidationException {
        TestN3Segment segment = TestN3Segment.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2("")
                .build();

        assertNotNull(segment);
        assertEquals(addressLine1, segment.getAddressLine1());
        assertEquals("", segment.getAddressLine2());

        segment.setContext(context);
        String rendered = segment.render().trim();
        String expected = "N3*" + addressLine1 + "*~";

        assertEquals(expected, rendered);
    }
}