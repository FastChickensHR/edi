/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.*;
import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ISASegmentTest {

    /**
     * Concrete implementation of ISASegment for testing purposes.
     */
    private static class TestISASegment extends ISASegment {
        private TestISASegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        /**
         * Builder for TestISASegment.
         */
        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public TestISASegment build() throws ValidationException {
                return new TestISASegment(this);
            }
        }

        /**
         * Creates a new Builder instance.
         *
         * @return a new Builder
         */
        public static Builder builder() {
            return new Builder();
        }
    }

    // Test data for valid segment
    private static final AuthorizationInformationQualifier VALID_ISA01 = AuthorizationInformationQualifier.fromString("00");
    private static final String VALID_ISA02 = "          ";
    private static final SecurityInformationQualifier VALID_ISA03 = SecurityInformationQualifier.fromString("00");
    private static final String VALID_ISA04 = "          ";
    private static final InterchangeIdQualifier VALID_ISA05 = InterchangeIdQualifier.fromString("30");
    private static final String VALID_ISA06 = "SENDER123456";
    private static final InterchangeIdQualifier VALID_ISA07 = InterchangeIdQualifier.fromString("ZZ");
    private static final String VALID_ISA08 = "RECEIVER123456";
    private static final String VALID_ISA09 = "230630";
    private static final String VALID_ISA10 = "1200";
    private static final String VALID_ISA11 = "^";
    private static final InterchangeControlVersionNumber VALID_ISA12 = InterchangeControlVersionNumber.fromString("00501");
    private static final String VALID_ISA13 = "000000001";
    private static final AcknowledgmentRequested VALID_ISA14 = AcknowledgmentRequested.fromString("0");
    private static final InterchangeUsageIndicator VALID_ISA15 = InterchangeUsageIndicator.fromString("P");
    private static final String VALID_ISA16 = ":";

    /**
     * Creates a builder with all required fields populated with valid values.
     *
     * @return A builder instance with all fields set to valid values
     */
    private TestISASegment.Builder createValidBuilder() throws ValidationException {
        return TestISASegment.builder()
                .setIsa01(VALID_ISA01.getCode())
                .setIsa02(VALID_ISA02)
                .setIsa03(VALID_ISA03.getCode())
                .setIsa04(VALID_ISA04)
                .setIsa05(VALID_ISA05.getCode())
                .setIsa06(VALID_ISA06)
                .setIsa07(VALID_ISA07.getCode())
                .setIsa08(VALID_ISA08)
                .setIsa09(VALID_ISA09)
                .setIsa10(VALID_ISA10)
                .setIsa11(VALID_ISA11)
                .setIsa12(VALID_ISA12.getCode())
                .setIsa13(VALID_ISA13)
                .setIsa14(VALID_ISA14.getCode())
                .setIsa15(VALID_ISA15.getCode())
                .setIsa16(VALID_ISA16);
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        TestISASegment segment = createValidBuilder().build();
        assertEquals("ISA", segment.getSegmentIdentifier());
    }

    @Test
    void testGetters() throws ValidationException {
        TestISASegment segment = createValidBuilder().build();

        assertEquals(VALID_ISA01, segment.getAuthorizationInformationQualifier());
        assertEquals(VALID_ISA02, segment.getAuthorizationInformation());
        assertEquals(VALID_ISA03, segment.getSecurityInformationQualifier());
        assertEquals(VALID_ISA04, segment.getSecurityInformation());
        assertEquals(VALID_ISA05, segment.getInterchangeSenderQualifier());
        assertEquals(VALID_ISA06 + "   ", segment.getInterchangeSenderID());
        assertEquals(VALID_ISA07, segment.getInterchangeReceiverQualifier());
        assertEquals(VALID_ISA08 + " ", segment.getInterchangeReceiverID());
        assertEquals(VALID_ISA09, segment.getInterchangeDate());
        assertEquals(VALID_ISA10, segment.getInterchangeTime());
        assertEquals(VALID_ISA11, segment.getInterchangeControlStandardsIdentifier());
        assertEquals(VALID_ISA12, segment.getInterchangeControlVersionNumber());
        assertEquals(VALID_ISA13, segment.getInterchangeControlNumber());
        assertEquals(VALID_ISA14, segment.getAcknowledgmentRequested());
        assertEquals(VALID_ISA15, segment.getUsageIndicator());
        assertEquals(VALID_ISA16, segment.getComponentElementSeparator());
    }

    @Test
    void testBuilderSetters() throws ValidationException {
        TestISASegment segment = TestISASegment.builder()
                .setIsa01(VALID_ISA01.getCode())
                .setIsa02(VALID_ISA02)
                .setIsa03(VALID_ISA03.getCode())
                .setIsa04(VALID_ISA04)
                .setIsa05(VALID_ISA05.getCode())
                .setIsa06(VALID_ISA06)
                .setIsa07(VALID_ISA07.getCode())
                .setIsa08(VALID_ISA08)
                .setIsa09(VALID_ISA09)
                .setIsa10(VALID_ISA10)
                .setIsa11(VALID_ISA11)
                .setIsa12(VALID_ISA12.getCode())
                .setIsa13(VALID_ISA13)
                .setIsa14(VALID_ISA14.getCode())
                .setIsa15(VALID_ISA15.getCode())
                .setIsa16(VALID_ISA16)
                .build();

        assertEquals(VALID_ISA01, segment.getAuthorizationInformationQualifier());
        assertEquals(VALID_ISA02, segment.getAuthorizationInformation());
        assertEquals(VALID_ISA03, segment.getSecurityInformationQualifier());
        assertEquals(VALID_ISA04, segment.getSecurityInformation());
        assertEquals(VALID_ISA05, segment.getInterchangeSenderQualifier());
        assertEquals(VALID_ISA06 + "   ", segment.getInterchangeSenderID());
        assertEquals(VALID_ISA07, segment.getInterchangeReceiverQualifier());
        assertEquals(VALID_ISA08 + " ", segment.getInterchangeReceiverID());
        assertEquals(VALID_ISA09, segment.getInterchangeDate());
        assertEquals(VALID_ISA10, segment.getInterchangeTime());
        assertEquals(VALID_ISA11, segment.getInterchangeControlStandardsIdentifier());
        assertEquals(VALID_ISA12, segment.getInterchangeControlVersionNumber());
        assertEquals(VALID_ISA13, segment.getInterchangeControlNumber());
        assertEquals(VALID_ISA14, segment.getAcknowledgmentRequested());
        assertEquals(VALID_ISA15, segment.getUsageIndicator());
        assertEquals(VALID_ISA16, segment.getComponentElementSeparator());
    }

    @ParameterizedTest
    @MethodSource("requiredFieldsSource")
    void testRequiredFieldsValidation(Supplier<Exception> exceptionSupplier) {
        Exception exception = exceptionSupplier.get();

        assertTrue(
                exception instanceof ValidationException || exception instanceof IllegalArgumentException,
                "Exception should be either ValidationException or IllegalArgumentException, but got: " + exception.getClass().getSimpleName()
        );
    }

    private static Stream<Arguments> requiredFieldsSource() {
        return Stream.of(
                Arguments.of((Supplier<Exception>) () -> captureException("isa01")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa02")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa03")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa04")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa05")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa06")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa07")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa08")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa09")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa10")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa11")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa12")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa13")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa14")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa15")),
                Arguments.of((Supplier<Exception>) () -> captureException("isa16"))
        );
    }

    private static Exception captureException(String fieldName) {
        try {
            TestISASegment.Builder builder = createBuilderForField(fieldName);
            builder.build();
            // If no exception is thrown (which shouldn't happen), return a new RuntimeException
            return new RuntimeException("No validation exception thrown for " + fieldName);
        } catch (Exception e) {
            // Return the caught exception
            return e;
        }
    }

    private static TestISASegment.Builder createBuilderForField(String fieldName) {
        TestISASegment.Builder builder = TestISASegment.builder();

        // Set all valid fields by default
        if (!"isa01".equals(fieldName)) builder.setIsa01(VALID_ISA01.getCode());
        if (!"isa02".equals(fieldName)) builder.setIsa02(VALID_ISA02);
        if (!"isa03".equals(fieldName)) builder.setIsa03(VALID_ISA03.getCode());
        if (!"isa04".equals(fieldName)) builder.setIsa04(VALID_ISA04);
        if (!"isa05".equals(fieldName)) builder.setIsa05(VALID_ISA05.getCode());
        if (!"isa06".equals(fieldName)) builder.setIsa06(VALID_ISA06);
        if (!"isa07".equals(fieldName)) builder.setIsa07(VALID_ISA07.getCode());
        if (!"isa08".equals(fieldName)) builder.setIsa08(VALID_ISA08);
        if (!"isa09".equals(fieldName)) builder.setIsa09(VALID_ISA09);
        if (!"isa10".equals(fieldName)) builder.setIsa10(VALID_ISA10);
        if (!"isa11".equals(fieldName)) builder.setIsa11(VALID_ISA11);
        if (!"isa12".equals(fieldName)) builder.setIsa12(VALID_ISA12.getCode());
        if (!"isa13".equals(fieldName)) builder.setIsa13(VALID_ISA13);
        if (!"isa14".equals(fieldName)) builder.setIsa14(VALID_ISA14.getCode());
        if (!"isa15".equals(fieldName)) builder.setIsa15(VALID_ISA15.getCode());
        if (!"isa16".equals(fieldName)) builder.setIsa16(VALID_ISA16);

        // For enum fields, set invalid values; for string fields, set null
        switch (fieldName) {
            case "isa01":
                builder.setIsa01("chitty bang bang");
                break;
            case "isa03":
                builder.setIsa03("chitty bang bang");
                break;
            case "isa05":
                builder.setIsa05("chitty bang bang");
                break;
            case "isa07":
                builder.setIsa07("chitty bang bang");
                break;
            case "isa12":
                builder.setIsa12("chitty bang bang");
                break;
            case "isa14":
                builder.setIsa14("chitty bang bang");
                break;
            case "isa15":
                builder.setIsa15("chitty bang bang");
                break;
            // For string fields, use null values
            case "isa02":
            case "isa04":
            case "isa06":
            case "isa08":
            case "isa09":
            case "isa10":
            case "isa11":
            case "isa13":
            case "isa16":
                // Don't set these fields at all, leaving them null/unset
                break;
        }

        return builder;
    }

    @Test
    void testFormattingValidation() throws ValidationException {
        TestISASegment.Builder invalidIcnBuilder = createValidBuilder().setIsa13("");
        ValidationException icnException = assertThrows(ValidationException.class, invalidIcnBuilder::build);
        assertTrue(icnException.getMessage().contains("Interchange Control Number"),
                "Error message should mention control number issue");

        TestISASegment.Builder invalidSeparatorBuilder = createValidBuilder().setIsa16("");
        ValidationException separatorException = assertThrows(ValidationException.class, invalidSeparatorBuilder::build);
        assertTrue(separatorException.getMessage().contains("Component Element Separator"),
                "Error message should mention separator issue");
    }

    @Test
    void testEmptySegment() {
        TestISASegment.Builder emptyBuilder = TestISASegment.builder();
        ValidationException exception = assertThrows(ValidationException.class, emptyBuilder::build);
        assertTrue(exception.getMessage().contains("cannot be blank"),
                "Empty builder should throw validation exception for missing required fields");
    }
}