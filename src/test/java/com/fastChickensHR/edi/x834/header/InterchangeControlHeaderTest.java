package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.common.TextUtils;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InterchangeControlHeader.
 * Verifies the behavior of the segment identifier, field getters/setters,
 * and validation functionality.
 */
class InterchangeControlHeaderTest {
    x834Context context = new x834Context();

    /**
     * Tests for getSegmentIdentifier method in InterchangeControlHeader class.
     * This method is expected to always return the constant value "ISA".
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        InterchangeControlHeader header = createSampleHeader();
        assertEquals("ISA", header.getSegmentIdentifier());
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        // Create a header with spec names
        InterchangeControlHeader header = createHeaderWithSpecNames();

        // Verify domain name getters return expected values
        assertEquals("00", header.getAuthorizationInformationQualifier());
        assertEquals(TextUtils.spaces(10), header.getAuthorizationInformation());
        assertEquals("00", header.getSecurityInformationQualifier());
        assertEquals(TextUtils.spaces(10), header.getSecurityInformation());
        assertEquals("ZZ", header.getInterchangeSenderQualifier());
        assertEquals(TextUtils.padRight("SENDERNAME",15), header.getInterchangeSenderID());
        assertEquals("ZZ", header.getInterchangeReceiverQualifier());
        assertEquals(TextUtils.padRight("RECEIVERNAME", 15), header.getInterchangeReceiverID());
        assertEquals("230415", header.getInterchangeDate());
        assertEquals("1200", header.getInterchangeTime());
        assertEquals("^", header.getInterchangeControlStandardsIdentifier());
        assertEquals("00501", header.getInterchangeControlVersionNumber());
        assertEquals("000000001", header.getInterchangeControlNumber());
        assertEquals("0", header.getAcknowledgmentRequested());
        assertEquals("P", header.getUsageIndicator());
        assertEquals(":", header.getComponentElementSeparator());
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        // Create a header with domain names
        InterchangeControlHeader header = createHeaderWithDomainNames();

        // Verify spec name fields contain expected values
        assertEquals("00", header.getIsa01());
        assertEquals(TextUtils.spaces(10), header.getIsa02());
        assertEquals("00", header.getIsa03());
        assertEquals(TextUtils.spaces(10), header.getIsa04());
        assertEquals("ZZ", header.getIsa05());
        assertEquals(TextUtils.padRight("SENDERNAME",15), header.getIsa06());
        assertEquals("ZZ", header.getIsa07());
        assertEquals(TextUtils.padRight("RECEIVERNAME", 15), header.getIsa08());
        assertEquals("230415", header.getIsa09());
        assertEquals("1200", header.getIsa10());
        assertEquals("^", header.getIsa11());
        assertEquals("00501", header.getIsa12());
        assertEquals("000000001", header.getIsa13());
        assertEquals("0", header.getIsa14());
        assertEquals("P", header.getIsa15());
        assertEquals(":", header.getIsa16());
    }

    /**
     * Tests the EdiSegment formatting.
     */
    @Test
    void testToEdiSegmentFormatting() throws ValidationException {
        InterchangeControlHeader header = createSampleHeader();
        header.setContext(context);

        String expectedEdiSegment =
                "ISA*00*          *00*          *ZZ*SENDERNAME     *ZZ*RECEIVERNAME   *230415*1200*^*00501*000000001*0*P*:~";

        assertEquals(expectedEdiSegment, header.render().trim());
    }

    /**
     * Tests the getElementValues method.
     */
    @Test
    void testGetElementValues() throws ValidationException {
        InterchangeControlHeader header = createSampleHeader();
        String[] elements = header.getElementValues();

        assertEquals(16, elements.length);
        assertEquals("00", elements[0]);
        assertEquals(TextUtils.spaces(10), elements[1]);
        assertEquals("00", elements[2]);
        assertEquals(TextUtils.spaces(10), elements[3]);
        assertEquals("ZZ", elements[4]);
        assertEquals(TextUtils.padRight("SENDERNAME", 15), elements[5]);
        assertEquals("ZZ", elements[6]);
        assertEquals(TextUtils.padRight("RECEIVERNAME", 15), elements[7]);
        assertEquals("230415", elements[8]);
        assertEquals("1200", elements[9]);
        assertEquals("^", elements[10]);
        assertEquals("00501", elements[11]);
        assertEquals("000000001", elements[12]);
        assertEquals("0", elements[13]);
        assertEquals("P", elements[14]);
        assertEquals(":", elements[15]);
    }

    /**
     * Tests that the default values are correctly applied when not explicitly set.
     */
    @Test
    void testDefaultValues() throws ValidationException {
        // Create a minimal header with only required fields
        InterchangeControlHeader header = createMinimalHeader();

        // Check that defaults are applied
        assertEquals(InterchangeControlHeader.DEFAULT_AUTHORIZATION_INFO_QUALIFIER, header.getAuthorizationInformationQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_AUTHORIZATION_INFO, header.getAuthorizationInformation());
        assertEquals(InterchangeControlHeader.DEFAULT_SECURITY_INFO_QUALIFIER, header.getSecurityInformationQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_SECURITY_INFO, header.getSecurityInformation());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_SENDER_QUALIFIER, header.getInterchangeSenderQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_RECEIVER_QUALIFIER, header.getInterchangeReceiverQualifier());
        assertEquals(InterchangeControlHeader.DEFAULT_REPETITION_SEPARATOR, header.getInterchangeControlStandardsIdentifier());
        assertEquals(InterchangeControlHeader.DEFAULT_INTERCHANGE_CONTROL_VERSION, header.getInterchangeControlVersionNumber());
        assertEquals(InterchangeControlHeader.DEFAULT_ACKNOWLEDGMENT_REQUESTED, header.getAcknowledgmentRequested());
        assertEquals(InterchangeControlHeader.DEFAULT_USAGE_INDICATOR, header.getUsageIndicator());
    }

    /* Validation Tests for ISA06 - Interchange Sender ID */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidIsa06_ShouldThrowValidationException(String invalidValue) {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            InterchangeControlHeader.Builder builder = new InterchangeControlHeader.Builder(context)
                    .setInterchangeSenderID(invalidValue)
                    .setInterchangeReceiverID("RECEIVERNAME")
                    .setInterchangeControlNumber("000000001")
                    .setComponentElementSeparator(":");

            builder.build();
        });

        assertTrue(exception.getMessage().contains("ISA06"));
    }

    /* Validation Tests for ISA08 - Interchange Receiver ID */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidIsa08_ShouldThrowValidationException(String invalidValue) {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            InterchangeControlHeader.Builder builder = new InterchangeControlHeader.Builder(context)
                    .setInterchangeSenderID("SENDERNAME")
                    .setInterchangeReceiverID(invalidValue)
                    .setInterchangeControlNumber("000000001")
                    .setComponentElementSeparator(":");

            builder.build();
        });

        assertTrue(exception.getMessage().contains("ISA08"));
    }

    /* Validation Tests for ISA13 - Interchange Control Number */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidIsa13_ShouldThrowValidationException(String invalidValue) {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            InterchangeControlHeader.Builder builder = new InterchangeControlHeader.Builder(context)
                    .setInterchangeSenderID("SENDERNAME")
                    .setInterchangeReceiverID("RECEIVERNAME")
                    .setInterchangeControlNumber(invalidValue)
                    .setComponentElementSeparator(":");

            builder.build();
        });

        assertTrue(exception.getMessage().contains("ISA13"));
    }

    /* Validation Tests for ISA16 - Component Element Separator */

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ",})
    void testInvalidIsa16_ShouldThrowValidationException(String invalidValue) {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            InterchangeControlHeader.Builder builder = new InterchangeControlHeader.Builder(context)
                    .setInterchangeSenderID("SENDERNAME")
                    .setInterchangeReceiverID("RECEIVERNAME")
                    .setInterchangeControlNumber("000000001")
                    .setComponentElementSeparator(invalidValue);

            builder.build();
        });

        assertTrue(exception.getMessage().contains("ISA16"));
    }

    /**
     * Tests valid date formats.
     */
    @Test
    void testValidDateFormats() throws ValidationException {
        LocalDate now = LocalDate.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyMMdd"));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context)
                .setInterchangeSenderID("SENDERNAME")
                .setInterchangeReceiverID("RECEIVERNAME")
                .setInterchangeControlNumber("000000001")
                .setInterchangeDate(dateStr)
                .setComponentElementSeparator(":")
                .build();

        assertEquals(dateStr, header.getInterchangeDate());
    }

    /**
     * Tests valid time formats.
     */
    @Test
    void testValidTimeFormats() throws ValidationException {
        LocalTime now = LocalTime.now();
        String timeStr = now.format(DateTimeFormatter.ofPattern("HHmm"));

        InterchangeControlHeader header = new InterchangeControlHeader.Builder(context)
                .setInterchangeSenderID("SENDERNAME")
                .setInterchangeReceiverID("RECEIVERNAME")
                .setInterchangeControlNumber("000000001")
                .setInterchangeTime(timeStr)
                .setComponentElementSeparator(":")
                .build();

        assertEquals(timeStr, header.getInterchangeTime());
    }

    // Helper methods to create test objects

    private InterchangeControlHeader createSampleHeader() throws ValidationException {
        return new InterchangeControlHeader.Builder(context)
                .setAuthorizationInformationQualifier("00")
                .setAuthorizationInformation(TextUtils.spaces(10))
                .setSecurityInformationQualifier("00")
                .setSecurityInformation(TextUtils.spaces(10))
                .setInterchangeSenderQualifier("ZZ")
                .setInterchangeSenderID("SENDERNAME")
                .setInterchangeReceiverQualifier("ZZ")
                .setInterchangeReceiverID("RECEIVERNAME")
                .setInterchangeDate("230415")
                .setInterchangeTime("1200")
                .setInterchangeControlStandardsIdentifier("^")
                .setInterchangeControlVersionNumber("00501")
                .setInterchangeControlNumber("000000001")
                .setAcknowledgmentRequested("0")
                .setUsageIndicator("P")
                .setComponentElementSeparator(":")
                .build();
    }

    private InterchangeControlHeader createHeaderWithSpecNames() throws ValidationException {
        return new InterchangeControlHeader.Builder(context)
                .setIsa01("00")
                .setIsa02(TextUtils.spaces(10))
                .setIsa03("00")
                .setIsa04(TextUtils.spaces(10))
                .setIsa05("ZZ")
                .setIsa06("SENDERNAME")
                .setIsa07("ZZ")
                .setIsa08("RECEIVERNAME")
                .setIsa09("230415")
                .setIsa10("1200")
                .setIsa11("^")
                .setIsa12("00501")
                .setIsa13("000000001")
                .setIsa14("0")
                .setIsa15("P")
                .setIsa16(":")
                .build();
    }

    private InterchangeControlHeader createHeaderWithDomainNames() throws ValidationException {
        return new InterchangeControlHeader.Builder(context)
                .setAuthorizationInformationQualifier("00")
                .setAuthorizationInformation(TextUtils.spaces(10))
                .setSecurityInformationQualifier("00")
                .setSecurityInformation(TextUtils.spaces(10))
                .setInterchangeSenderQualifier("ZZ")
                .setInterchangeSenderID("SENDERNAME")
                .setInterchangeReceiverQualifier("ZZ")
                .setInterchangeReceiverID("RECEIVERNAME")
                .setInterchangeDate("230415")
                .setInterchangeTime("1200")
                .setInterchangeControlStandardsIdentifier("^")
                .setInterchangeControlVersionNumber("00501")
                .setInterchangeControlNumber("000000001")
                .setAcknowledgmentRequested("0")
                .setUsageIndicator("P")
                .setComponentElementSeparator(":")
                .build();
    }

    private InterchangeControlHeader createMinimalHeader() throws ValidationException {
        return new InterchangeControlHeader.Builder(context)
                .setInterchangeSenderID("SENDERNAME")
                .setInterchangeReceiverID("RECEIVERNAME")
                .setInterchangeControlNumber("000000001")
                .setComponentElementSeparator(":")
                .build();
    }
}