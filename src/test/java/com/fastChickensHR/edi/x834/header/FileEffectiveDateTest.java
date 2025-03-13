package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.dates.DateFormat;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileEffectiveDateTest {
    x834Context context = new x834Context();

    @Test
    public void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {


        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .build();
        segment.setContext(context);

        assertEquals("DTP", segment.getSegmentIdentifier(), "Expected segment identifier should be 'DTP'");
        assertEquals("DTP*007*CCYYMMDD*20250313~", segment.render().trim(), "The segment is not formatted correctly.");
    }

    /**
     * Tests setting and getting values via the spec names and retrieving via the business domain names.
     */
    @Test
    public void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        String dateTimeQualifier = "1";
        String dateTimeFormat = DateFormat.DATE.getFormat();
        String dateTimePeriod = context.getFormattedDocumentDate();
        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .setDtp01(dateTimeQualifier)
                .setDtp02(dateTimeFormat)
                .setDtp03(dateTimePeriod)
                .build();

        assertEquals(dateTimeQualifier, segment.getDateTimeQualifier(), "DateTimeQualifier should match DTP01");
        assertEquals(dateTimeFormat, segment.getDateTimeFormat(), "DateTimeFormat should match DTP02");
        assertEquals(dateTimePeriod, segment.getDateTimePeriod(), "DateTimePeriod should match DTP03");
    }

    /**
     * Tests setting and getting values via the business domain names and retrieving via the spec names.
     */
    @Test
    public void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        String dateTimeQualifier = "1";
        String dateTimeFormat = DateFormat.DATE.getFormat();
        String dateTimePeriod = context.getFormattedDocumentDate();
        FileEffectiveDate segment = new FileEffectiveDate.Builder(context)
                .setDateTimeQualifier(dateTimeQualifier)
                .setDateTimeFormat(dateTimeFormat)
                .setDateTimePeriod(dateTimePeriod)
                .build();

        assertEquals(dateTimeQualifier, segment.getDtp01(), "DateTimeQualifier should match DTP01");
        assertEquals(dateTimeFormat, segment.getDtp02(), "DateTimeFormat should match DTP02");
        assertEquals(dateTimePeriod, segment.getDtp03(), "DateTimePeriod should match DTP03");
    }
}