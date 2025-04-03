/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.dates.TimeFormat;
import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GSSegmentTest {

    // Concrete implementation of GSSegment for testing
    private static class TestGSSegment extends GSSegment {
        protected TestGSSegment(AbstractBuilder<?> builder) throws ValidationException {
            super(builder);
        }

        // Concrete builder implementation for testing
        public static class Builder extends AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            public TestGSSegment build() throws ValidationException {
                return new TestGSSegment(this);
            }
        }
    }

    private TestGSSegment.Builder builder;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        builder = new TestGSSegment.Builder();
        testDateTime = LocalDateTime.of(2023, 11, 15, 12, 30, 0);
    }

    @Test
    void testGetSegmentIdentifier() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("PO")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("GS", segment.getSegmentIdentifier());
    }

    @Test
    void testGetElementValues() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        String[] elements = segment.getElementValues();

        assertEquals(8, elements.length);
        assertEquals("BE", elements[0]);
        assertEquals("SENDER", elements[1]);
        assertEquals("RECEIVER", elements[2]);
        assertEquals("20231115", elements[3]);
        assertEquals("1230", elements[4]);
        assertEquals("12345", elements[5]);
        assertEquals("X", elements[6]);
        assertEquals("005010X220A1", elements[7]);
    }

    /* Tests for all getters */

    @Test
    void testGetFunctionalIdentifierCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals(FunctionalIdentifierCode.fromString("BE"), segment.getFunctionalIdentifierCode());
        assertEquals(FunctionalIdentifierCode.fromString("BE"), segment.getGs01());
    }

    @Test
    void testGetApplicationSenderCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("SENDER", segment.getApplicationSenderCode());
        assertEquals("SENDER", segment.getGs02());
    }

    @Test
    void testGetApplicationReceiverCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("RECEIVER", segment.getApplicationReceiverCode());
        assertEquals("RECEIVER", segment.getGs03());
    }

    @Test
    void testGetTransactionSetCreationDate() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("20231115", segment.getTransactionSetCreationDate());
        assertEquals("20231115", segment.getGs04());
    }

    @Test
    void testGetTransactionSetCreationTime() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("1230", segment.getTransactionSetCreationTime());
        assertEquals("1230", segment.getGs05());
    }

    @Test
    void testGetGroupControlNumber() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("12345", segment.getGroupControlNumber());
        assertEquals("12345", segment.getGs06());
    }

    @Test
    void testGetResponsibleAgencyCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("X", segment.getResponsibleAgencyCode().getCode());
        assertEquals("X", segment.getGs07().getCode());
    }

    @Test
    void testGetVersionReleaseIndustryCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("005010X220A1", segment.getVersionReleaseIndustryCode().getCode());
        assertEquals("005010X220A1", segment.getGs08().getCode());
    }

    /* Tests for all setter methods */

    @Test
    void testSetFunctionalIdentifierCode() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals(FunctionalIdentifierCode.fromString("BE"), segment.getFunctionalIdentifierCode());
    }

    @Test
    void testSetGs01() throws ValidationException {
        TestGSSegment segment = builder
                .setGs01("PO")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals(FunctionalIdentifierCode.fromString("PO"), segment.getFunctionalIdentifierCode());
    }

    @Test
    void testSetGs02() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setGs02("TESTSENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("TESTSENDER", segment.getApplicationSenderCode());
    }

    @Test
    void testSetGs03() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setGs03("TESTRECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("TESTRECEIVER", segment.getApplicationReceiverCode());
    }

    @Test
    void testSetGs04() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setGs04(LocalDateTime.of(2023, 10, 1, 9, 15, 0), DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("20231001", segment.getTransactionSetCreationDate());
    }

    @Test
    void testSetGs05() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setGs05(LocalDateTime.of(2023, 10, 1, 9, 15, 0), TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("0915", segment.getTransactionSetCreationTime());
    }

    @Test
    void testSetGs06() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGs06("67890")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("67890", segment.getGroupControlNumber());
    }

    @Test
    void testSetGs07() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setGs07("T")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build();

        assertEquals("T", segment.getResponsibleAgencyCode().getCode());
    }

    @Test
    void testSetGs08() throws ValidationException {
        TestGSSegment segment = builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setGs08("004061")
                .build();

        assertEquals("004061", segment.getVersionReleaseIndustryCode().getCode());
    }

    /* Validation tests for required fields */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs02_ShouldThrowValidationException(String invalidValue) {
        Exception exception = assertThrows(ValidationException.class, () -> builder
                .setFunctionalIdentifierCode("BE")
                .setGs02(invalidValue)
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build());

        assertTrue(exception.getMessage().contains("GS02"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs03_ShouldThrowValidationException(String invalidValue) {
        Exception exception = assertThrows(ValidationException.class, () -> builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setGs03(invalidValue)
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGroupControlNumber("12345")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build());

        assertTrue(exception.getMessage().contains("GS03"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs06_ShouldThrowValidationException(String invalidValue) {
        Exception exception = assertThrows(ValidationException.class, () -> builder
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("SENDER")
                .setApplicationReceiverCode("RECEIVER")
                .setTransactionSetCreationDate(testDateTime, DateFormat.DATE)
                .setTransactionSetCreationTime(testDateTime, TimeFormat.TIME)
                .setGs06(invalidValue)
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A1")
                .build());

        assertTrue(exception.getMessage().contains("GS06"));
    }
}