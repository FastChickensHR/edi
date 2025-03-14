/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BeginningSegmentTest {
    x834Context context = new x834Context();

    /**
     * Tests for getSegmentIdentifier method in BeginningSegment class.
     * This method is expected to always return the constant value "BGN".
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        // Create a concrete implementation of the abstract BeginningSegment for testing
        BeginningSegment segment = createValidSegment();

        // Assert that the segment identifier is "BGN"
        assertEquals("BGN", segment.getSegmentIdentifier());
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        // Given
        String transactionPurposeCode = "01";
        String referenceId = "REF123";
        String date = "20230101";
        String time = "1200";
        String timeZone = "ET";
        String originalRef = "ORIG123";
        String transactionType = "T1";
        String actionCode = "A1";
        String securityLevel = "S1";

        // When
        BeginningSegment segment = new BeginningSegmentImpl.Builder(context)
                .setBgn01(transactionPurposeCode)
                .setBgn02(referenceId)
                .setBgn03(date)
                .setBgn04(time)
                .setBgn05(timeZone)
                .setBgn06(originalRef)
                .setBgn07(transactionType)
                .setBgn08(actionCode)
                .setBgn09(securityLevel)
                .build();

        // Then
        assertEquals(transactionPurposeCode, segment.getTransactionSetPurposeCode());
        assertEquals(referenceId, segment.getReferenceIdentification());
        assertEquals(date, segment.getDate());
        assertEquals(time, segment.getTime());
        assertEquals(timeZone, segment.getTimeZoneCode());
        assertEquals(originalRef, segment.getOriginalReferenceNumber());
        assertEquals(transactionType, segment.getTransactionTypeCode());
        assertEquals(actionCode, segment.getActionCode());
        assertEquals(securityLevel, segment.getSecurityLevelCode());
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        // Given
        String transactionPurposeCode = "01";
        String referenceId = "REF123";
        String date = "20230101";
        String time = "1200";
        String timeZone = "ET";
        String originalRef = "ORIG123";
        String transactionType = "T1";
        String actionCode = "A1";
        String securityLevel = "S1";

        // When
        BeginningSegment segment = new BeginningSegmentImpl.Builder(context)
                .setTransactionSetPurposeCode(transactionPurposeCode)
                .setReferenceIdentification(referenceId)
                .setDate(date)
                .setTime(time)
                .setTimeZoneCode(timeZone)
                .setOriginalReferenceNumber(originalRef)
                .setTransactionTypeCode(transactionType)
                .setActionCode(actionCode)
                .setSecurityLevelCode(securityLevel)
                .build();

        // Then
        assertEquals(transactionPurposeCode, segment.getBgn01());
        assertEquals(referenceId, segment.getBgn02());
        assertEquals(date, segment.getBgn03());
        assertEquals(time, segment.getBgn04());
        assertEquals(timeZone, segment.getBgn05());
        assertEquals(originalRef, segment.getBgn06());
        assertEquals(transactionType, segment.getBgn07());
        assertEquals(actionCode, segment.getBgn08());
        assertEquals(securityLevel, segment.getBgn09());
    }

    /**
     * Tests the EdiSegment formatting through rendering.
     */
    @Test
    void testRenderSegmentFormatting() throws ValidationException {
        // Given
        BeginningSegment segment = new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02("REF123")
                .setBgn03("20230101")
                .setBgn04("1200")
                .setBgn05("ET")
                .setBgn06("ORIG123")
                .setBgn07("T1")
                .setBgn08("A1")
                .setBgn09("S1")
                .build();

        // When
        String rendered = segment.render().trim();

        // Then
        assertEquals("BGN*01*REF123*20230101*1200*ET*ORIG123*T1*A1*S1~", rendered);
    }

    /**
     * Tests the getElementValues method.
     */
    @Test
    void testGetElementValues() throws ValidationException {
        // Given
        BeginningSegment segment = new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02("REF123")
                .setBgn03("20230101")
                .setBgn04("1200")
                .setBgn05("ET")
                .build();

        // When
        String[] elementValues = segment.getElementValues();

        // Then
        assertNotNull(elementValues);
        assertEquals(9, elementValues.length);
        assertEquals("01", elementValues[0]);
        assertEquals("REF123", elementValues[1]);
        assertEquals("20230101", elementValues[2]);
        assertEquals("1200", elementValues[3]);
        assertEquals("ET", elementValues[4]);
        assertNull(elementValues[5]);
        assertNull(elementValues[6]);
        assertNull(elementValues[7]);
        assertNull(elementValues[8]);
    }

    /**
     * Tests the default values.
     */
    @Test
    void testDefaultValues() throws ValidationException {
        BeginningSegment segment = new BeginningSegmentImpl.Builder(context)
                .setBgn02("REF123")
                .setBgn03("20230101")
                .build();

        assertEquals(BeginningSegment.DEFAULT_TRANSACTION_SET_PURPOSE_CODE, segment.getBgn01());
    }

    /* Validation Tests for BGN01 - Transaction Set Purpose Code */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn01_ShouldThrowValidationException(String invalidValue) {
        BeginningSegment.Builder builder = new BeginningSegmentImpl.Builder(context)
                .setBgn01(invalidValue)
                .setBgn02("REF123")
                .setBgn03("20230101");

        assertThrows(ValidationException.class, builder::build);
    }

    /* Validation Tests for BGN02 - Reference Identification */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn02_ShouldThrowValidationException(String invalidValue) {
        BeginningSegment.Builder builder = new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02(invalidValue)
                .setBgn03("20230101");

        assertThrows(ValidationException.class, builder::build);
    }

    /* Validation Tests for BGN03 - Date */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidBgn03_ShouldThrowValidationException(String invalidValue) {
        BeginningSegment.Builder builder = new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02("REF123")
                .setBgn03(invalidValue);

        assertThrows(ValidationException.class, builder::build);
    }

    /**
     * Tests that the optional fields (BGN04-BGN09) don't cause validation exceptions when not provided.
     */
    @Test
    void testOptionalFieldsAreOptional() throws ValidationException {
        // When/Then - No exceptions should be thrown for missing optional fields
        assertDoesNotThrow(() -> new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02("REF123")
                .setBgn03("20230101")
                .build());
    }

    // Helper method to create a valid segment for tests
    private BeginningSegment createValidSegment() throws ValidationException {
        return new BeginningSegmentImpl.Builder(context)
                .setBgn01("01")
                .setBgn02("REF123")
                .setBgn03("20230101")
                .build();
    }

    // Concrete implementation of the abstract class for testing
    private static class BeginningSegmentImpl extends BeginningSegment {
        protected BeginningSegmentImpl(Builder builder) throws ValidationException {
            super(builder);
        }

        public static class Builder extends BeginningSegment.Builder {
            public Builder(x834Context context) {
                super(context);
            }

            @Override
            public BeginningSegmentImpl build() throws ValidationException {
                return new BeginningSegmentImpl(this);
            }
        }
    }
}