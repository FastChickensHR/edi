/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.TimeCode;
import com.fastChickensHR.edi.common.data.TransactionSetPurposeCode;
import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BGNSegmentTest {
    x834Context context = new x834Context();
    String transactionType = "T1";
    String actionCode = "A1";
    String securityLevel = "ZZ";

    /**
     * Tests for getSegmentIdentifier method in BGNSegment class.
     * This method is expected to always return the constant value "BGN".
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        BGNSegmentImpl segment = createValidSegment();
        assertEquals("BGN", segment.getSegmentIdentifier(), "Segment identifier should be 'BGN'");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        // Create segment using spec names
        BGNSegmentImpl segment = new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02("12345")
                .setBgn03("20250101")
                .setBgn04("1200")
                .setBgn05("ET")
                .setBgn06("ORIGREF123")
                .setBgn07(transactionType)
                .setBgn08(actionCode)
                .setBgn09(securityLevel)
                .build();

        // Retrieve using domain names
        assertEquals(TransactionSetPurposeCode.ORIGINAL, segment.getTransactionSetPurposeCode());
        assertEquals("12345", segment.getReferenceIdentification());
        assertEquals("20250101", segment.getDate());
        assertEquals("1200", segment.getTime());
        assertEquals(TimeCode.EASTERN_TIME, segment.getTimeZoneCode());
        assertEquals("ORIGREF123", segment.getOriginalReferenceNumber());
        assertEquals(transactionType, segment.getTransactionTypeCode().getCode());
        assertEquals(actionCode, segment.getActionCode().getCode());
        assertEquals(securityLevel, segment.getSecurityLevelCode().getCode());
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        // Create segment using domain names
        BGNSegmentImpl segment = new BGNSegmentImpl.Builder()
                .setTransactionSetPurposeCode("00")
                .setReferenceIdentification("12345")
                .setDate("20250101")
                .setTime("1200")
                .setTimeZoneCode("ET")
                .setOriginalReferenceNumber("ORIGREF123")
                .setBgn07(transactionType)
                .setBgn08(actionCode)
                .setBgn09(securityLevel)
                .build();

        // Retrieve using spec names
        assertEquals(TransactionSetPurposeCode.ORIGINAL, segment.getBgn01());
        assertEquals("12345", segment.getBgn02());
        assertEquals("20250101", segment.getBgn03());
        assertEquals("1200", segment.getBgn04());
        assertEquals(TimeCode.EASTERN_TIME, segment.getBgn05());
        assertEquals("ORIGREF123", segment.getBgn06());
        assertEquals(transactionType, segment.getTransactionTypeCode().getCode());
        assertEquals(actionCode, segment.getActionCode().getCode());
        assertEquals(securityLevel, segment.getSecurityLevelCode().getCode());
    }

    /**
     * Tests the EdiSegment formatting through rendering.
     */
    @Test
    void testRenderSegmentFormatting() throws ValidationException {
        BGNSegmentImpl segment = createValidSegment();
        String expected = "BGN*00*12345*20250101*1200*ET*ORIGREF123*T1*A1*ZZ~";
        segment.setContext(context);
        assertEquals(expected, segment.render().trim());
    }

    /**
     * Tests the getElementValues method.
     */
    @Test
    void testGetElementValues() throws ValidationException {
        BGNSegmentImpl segment = createValidSegment();
        String[] expected = {
                "00", "12345", "20250101", "1200", "ET",
                "ORIGREF123", "T1", "A1", "ZZ"
        };
        assertArrayEquals(expected, segment.getElementValues());
    }

    /**
     * Tests null handling in getElementValues.
     */
    @Test
    void testGetElementValuesWithNullValues() throws ValidationException {
        BGNSegmentImpl segment = new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02("12345")
                .setBgn03("20250101")
                .build();

        String[] values = segment.getElementValues();
        assertEquals("00", values[0]);
        assertEquals("12345", values[1]);
        assertEquals("20250101", values[2]);
    }

    /* Validation Tests for BGN01 - Transaction Set Purpose Code */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn01_ShouldThrowValidationException(String invalidValue) {
        BGNSegmentImpl.Builder builder = new BGNSegmentImpl.Builder();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.setBgn01(invalidValue));
        assertTrue(exception.getMessage().contains("Input cannot be null or empty"), "Exception message should mention BGN01");
    }

    /* Validation Tests for BGN02 - Reference Identification */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn02_ShouldThrowValidationException(String invalidValue) {
        BGNSegmentImpl.Builder builder = new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02(invalidValue)
                .setBgn03("20250101");

        ValidationException exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("BGN02"),
                "Exception message should mention BGN02: " + exception.getMessage());
    }

    /* Validation Tests for BGN03 - Date */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn03_ShouldThrowValidationException(String invalidValue) {
        BGNSegmentImpl.Builder builder = new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02("12345")
                .setBgn03(invalidValue);

        ValidationException exception = assertThrows(ValidationException.class, builder::build);
        assertTrue(exception.getMessage().contains("BGN03"),
                "Exception message should mention BGN03: " + exception.getMessage());
    }

    /**
     * Tests that the optional fields (BGN04-BGN09) don't cause validation exceptions when not provided.
     */
    @Test
    void testOptionalFieldsAreOptional() throws ValidationException {
        BGNSegmentImpl segment = new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02("12345")
                .setBgn03("20250101")
                .build();

        assertNotNull(segment);
        assertEquals(TransactionSetPurposeCode.ORIGINAL, segment.getBgn01());
        assertEquals("12345", segment.getBgn02());
        assertEquals("20250101", segment.getBgn03());
        assertNull(segment.getBgn04());
        assertNull(segment.getBgn05());
        assertNull(segment.getBgn06());
        assertNull(segment.getBgn07());
        assertNull(segment.getBgn08());
        assertNull(segment.getBgn09());
    }

    private BGNSegmentImpl createValidSegment() throws ValidationException {
        return new BGNSegmentImpl.Builder()
                .setBgn01("00")
                .setBgn02("12345")
                .setBgn03("20250101")
                .setBgn04("1200")
                .setBgn05("ET")
                .setBgn06("ORIGREF123")
                .setBgn07(transactionType)
                .setBgn08(actionCode)
                .setBgn09(securityLevel)
                .build();
    }

    private static class BGNSegmentImpl extends BGNSegment {
        protected BGNSegmentImpl(Builder builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends BGNSegment.AbstractBuilder<Builder> {
            @Override
            protected Builder self() {
                return this;
            }

            @Override
            public BGNSegmentImpl build() throws ValidationException {
                return new BGNSegmentImpl(this);
            }
        }
    }
}