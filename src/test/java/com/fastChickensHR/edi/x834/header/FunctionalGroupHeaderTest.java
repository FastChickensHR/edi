package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalGroupHeaderTest {

    /**
     * Tests for getSegmentIdentifier method in FunctionalGroupHeader class.
     * This method is expected to always return the constant value "GS".
     */
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder()
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04("20231115")
                .setGs05("1230")
                .build();

        assertEquals("GS", header.getSegmentIdentifier(), "Expected segment identifier should be 'GS'");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        FunctionalGroupHeader header = new FunctionalGroupHeader.Builder()
                .setGs01("ZZ")
                .setGs02("SenderCode")
                .setGs03("ReceiverCode")
                .setGs04("20231010")
                .setGs05("1015")
                .setGs06("123456")
                .setGs07("X")
                .setGs08("005010X220A1")
                .build();

        assertEquals("ZZ", header.getFunctionalIdentifierCode(), "Functional Identifier Code should match GS01");
        assertEquals("SenderCode", header.getApplicationSenderCode(), "Application Sender Code should match GS02");
        assertEquals("ReceiverCode", header.getApplicationReceiverCode(), "Application Receiver Code should match GS03");
        assertEquals("20231010", header.getDate(), "Date should match GS04");
        assertEquals("1015", header.getTime(), "Time should match GS05");
        assertEquals("123456", header.getGroupControlNumber(), "Group Control Number should match GS06");
        assertEquals("X", header.getResponsibleAgencyCode(), "Responsible Agency Code should match GS07");
        assertEquals("005010X220A1", header.getVersionReleaseIndustryCode(), "Version/Release/Industry Code should match GS08");
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        FunctionalGroupHeader.Builder builder = new FunctionalGroupHeader.Builder()
                .setFunctionalIdentifierCode("BE")
                .setApplicationSenderCode("AppSender")
                .setApplicationReceiverCode("AppReceiver")
                .setDate("20231116")
                .setTime("1245")
                .setGroupControlNumber("789012")
                .setResponsibleAgencyCode("X")
                .setVersionReleaseIndustryCode("005010X220A2");

        FunctionalGroupHeader header = builder.build();

        assertEquals("BE", header.getGs01(), "GS01 should match Functional Identifier Code");
        assertEquals("AppSender", header.getGs02(), "GS02 should match Application Sender Code");
        assertEquals("AppReceiver", header.getGs03(), "GS03 should match Application Receiver Code");
        assertEquals("20231116", header.getGs04(), "GS04 should match Date");
        assertEquals("1245", header.getGs05(), "GS05 should match Time");
        assertEquals("789012", header.getGs06(), "GS06 should match Group Control Number");
        assertEquals("X", header.getGs07(), "GS07 should match Responsible Agency Code");
        assertEquals("005010X220A2", header.getGs08(), "GS08 should match Version/Release/Industry Identifier Code");
    }
}