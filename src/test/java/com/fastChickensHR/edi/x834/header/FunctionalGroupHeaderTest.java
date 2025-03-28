/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupHeaderTest {
    x834Context context = new x834Context();
    /**
     * Tests for getSegmentIdentifier method in FunctionalGroupHeader class.
     * This method is expected to always return the constant value "GS".
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        FunctionalGroupHeader segment = new FunctionalGroupHeader.Builder(context)
                .setGs01("BE")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("1")
                .build();
        segment.setContext(context);

        assertEquals("GS", segment.getSegmentIdentifier(), "Expected segment identifier should be 'GS'");
        assertEquals("GS*BE*SenderCode*ReceiverCode*20231115*1230*1*X*005010X220A1~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setGs01("ZZ")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04(LocalDateTime.of(2023, 11, 15,0,0,0))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("123456")
                .setGs07("X")
                .setGs08("005010X220A1")
                .build();

        assertEquals("ZZ", header.getFunctionalIdentifierCode(), "Functional Identifier Code should match GS01");
        assertEquals("SenderCode", header.getApplicationSenderCode(), "Application Sender Code should match GS02");
        assertEquals("ReceiverCode", header.getApplicationReceiverCode(), "Application Receiver Code should match GS03");
        assertEquals("20231115", header.getTransactionSetCreationDate(), "Date should match GS04");
        assertEquals("1230", header.getTime(), "Time should match GS05");
        assertEquals("123456", header.getGroupControlNumber(), "Group Control Number should match GS06");
        assertEquals("X", header.getResponsibleAgencyCode(), "Responsible Agency Code should match GS07");
        assertEquals("005010X220A1", header.getVersionReleaseIndustryCode(), "Version/Release/Industry Code should match GS08");
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder(context)
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("AppSender")
                .setApplicationReceiverCode("AppReceiver")
                .setTransactionSetCreationDate(LocalDateTime.of(2023, 11, 15,0,0,0))
                .setTransactionSetCreationTime(LocalDateTime.of(2023, 11, 15,0,0,0))
                .setGroupControlNumber("789012")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A2");

        FunctionalGroupHeader header = builder.build();

        assertEquals("BE", header.getGs01(), "GS01 should match Functional Identifier Code");
        assertEquals("AppSender", header.getGs02(), "GS02 should match Application Sender Code");
        assertEquals("AppReceiver", header.getGs03(), "GS03 should match Application Receiver Code");
        assertEquals("20231115", header.getGs04(), "GS04 should match Date");
        assertEquals("0000", header.getGs05(), "GS05 should match Time");
        assertEquals("789012", header.getGs06(), "GS06 should match Group Control Number");
        assertEquals("X", header.getGs07(), "GS07 should match Responsible Agency Code");
        assertEquals("005010X220A2", header.getGs08(), "GS08 should match Version/Release/Industry Identifier Code");
    }

    /**
     * Tests the EdiSegment formatting.
     */
    @Test
    void testToEdiSegmentFormatting() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("123456")
                .setGs07("X")
                .setGs08("005010X220A1")
                .build();
        header.setContext(context);

        String expected = "GS*HC*SenderCode*ReceiverCode*20231115*1230*123456*X*005010X220A1~";
        assertEquals(expected, header.render().trim(),
                "The EDI segment should be formatted correctly with default context");
    }

    /**
     * Tests the getElementValues method.
     */
    @Test
    void testGetElementValues() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("123456")
                .setGs07("X")
                .setGs08("005010X220A1")
                .build();

        String[] expectedValues = {"HC", "SenderCode", "ReceiverCode", "20231115", "1230", "123456", "X", "005010X220A1"};
        assertArrayEquals(expectedValues, header.getElementValues(),
                "Element values array should contain all field values in correct order");
    }

    /* Validation Tests for GS02 - Application Sender Code */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs02_ShouldThrowValidationException(String invalidValue) {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02(invalidValue)
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0));

        ValidationException exception = assertThrows(ValidationException.class, builder::build,
                "Building with invalid GS02 should throw ValidationException");
        assertTrue(exception.getMessage().contains("GS02"),
                "Exception message should mention the field name GS02");
    }

    /* Validation Tests for GS03 - Application Receiver Code */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs03_ShouldThrowValidationException(String invalidValue) {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03(invalidValue)
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0));

        ValidationException exception = assertThrows(ValidationException.class, builder::build,
                "Building with invalid GS03 should throw ValidationException");
        assertTrue(exception.getMessage().contains("GS03"),
                "Exception message should mention the field name GS03");
    }

    /* Validation Tests for GS06 - Group Control Number */

    @ParameterizedTest
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs06_ShouldThrowValidationException(String invalidValue) {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06(invalidValue);

        ValidationException exception = assertThrows(ValidationException.class, builder::build,
                "Building with invalid GS06 should throw ValidationException");
        assertTrue(exception.getMessage().contains("GS06"),
                "Exception message should mention the field name GS06");
    }

    /* Validation Tests for GS08 - Version/Release/Industry Identifier Code */

    @ParameterizedTest
    @ValueSource(strings = {" ", "   "})
    void testInvalidGs08_ShouldThrowValidationException(String invalidValue) {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("123456")
                .setGs07("X")
                .setGs08(invalidValue);

        ValidationException exception = assertThrows(ValidationException.class, builder::build,
                "Building with invalid GS08 should throw ValidationException");
        assertTrue(exception.getMessage().contains("GS08"),
                "Exception message should mention the field name GS08");
    }

    /**
     * Tests updating a field after construction.
     */
    @Test
    void testUpdateFieldAfterConstruction() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder(context)
                .setGs01("HC")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04((LocalDateTime.of(2023, 11, 15,0,0,0)))
                .setGs05(LocalDateTime.of(2023, 11, 15,12, 30, 0))
                .setGs06("123456")
                .setGroupControlNumber("999999")
                .build();

        assertEquals("999999", header.getGs06(),
                "GS06 should be updated after setGroupControlNumber");
        assertEquals("999999", header.getGroupControlNumber(),
                "getGroupControlNumber should return the updated value");
    }
}