/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InterchangeIdQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(41, InterchangeIdQualifier.values().length);

        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.DUNS));
        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.SCAC));
        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.FMC));
        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.IATA));
        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.GLN));
        assertTrue(Arrays.asList(InterchangeIdQualifier.values()).contains(InterchangeIdQualifier.MUTUALLY_DEFINED));
    }

    @Test
    void testEnumProperties() {
        assertEquals("01", InterchangeIdQualifier.DUNS.getCode());
        assertEquals("Duns (Dun & Bradstreet)", InterchangeIdQualifier.DUNS.getDescription());

        assertEquals("02", InterchangeIdQualifier.SCAC.getCode());
        assertEquals("SCAC (Standard Carrier Alpha Code)", InterchangeIdQualifier.SCAC.getDescription());

        assertEquals("07", InterchangeIdQualifier.GLN.getCode());
        assertEquals("Global Location Number (GLN)", InterchangeIdQualifier.GLN.getDescription());

        assertEquals("30", InterchangeIdQualifier.US_FEDERAL_TAX_ID.getCode());
        assertEquals("U.S. Federal Tax Identification Number", InterchangeIdQualifier.US_FEDERAL_TAX_ID.getDescription());

        assertEquals("ZZ", InterchangeIdQualifier.MUTUALLY_DEFINED.getCode());
        assertEquals("Mutually Defined", InterchangeIdQualifier.MUTUALLY_DEFINED.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(InterchangeIdQualifier.DUNS, InterchangeIdQualifier.fromString("01"));
        assertEquals(InterchangeIdQualifier.SCAC, InterchangeIdQualifier.fromString("02"));
        assertEquals(InterchangeIdQualifier.GLN, InterchangeIdQualifier.fromString("07"));
        assertEquals(InterchangeIdQualifier.FEIN, InterchangeIdQualifier.fromString("32"));
        assertEquals(InterchangeIdQualifier.AMECOP, InterchangeIdQualifier.fromString("AM"));
        assertEquals(InterchangeIdQualifier.MUTUALLY_DEFINED, InterchangeIdQualifier.fromString("ZZ"));

        assertEquals(InterchangeIdQualifier.DUNS, InterchangeIdQualifier.fromString("duns"));
        assertEquals(InterchangeIdQualifier.FEIN, InterchangeIdQualifier.fromString("FEIN"));

        assertEquals(InterchangeIdQualifier.DUNS, InterchangeIdQualifier.fromString("dun and bradstreet"));
        assertEquals(InterchangeIdQualifier.FEIN, InterchangeIdQualifier.fromString("employerid"));

        assertThrows(IllegalArgumentException.class, () -> InterchangeIdQualifier.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, InterchangeIdQualifier expected) throws Exception {
        Field lookupField = InterchangeIdQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<InterchangeIdQualifier> lookup = (EdiEnumLookup<InterchangeIdQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("d&b", InterchangeIdQualifier.DUNS),
                Arguments.of("dunsnumber", InterchangeIdQualifier.DUNS),
                Arguments.of("dun and bradstreet", InterchangeIdQualifier.DUNS),

                Arguments.of("standardcarrier", InterchangeIdQualifier.SCAC),

                Arguments.of("maritime", InterchangeIdQualifier.FMC),
                Arguments.of("federal maritime", InterchangeIdQualifier.FMC),

                Arguments.of("air", InterchangeIdQualifier.IATA),
                Arguments.of("airline", InterchangeIdQualifier.IATA),
                Arguments.of("airtransport", InterchangeIdQualifier.IATA),

                Arguments.of("gln", InterchangeIdQualifier.GLN),
                Arguments.of("globallocation", InterchangeIdQualifier.GLN),
                Arguments.of("ean", InterchangeIdQualifier.GLN),
                Arguments.of("ucc", InterchangeIdQualifier.GLN),

                Arguments.of("edi", InterchangeIdQualifier.UCC_EDI_COMM_ID),
                Arguments.of("uccedi", InterchangeIdQualifier.UCC_EDI_COMM_ID),
                Arguments.of("commid", InterchangeIdQualifier.UCC_EDI_COMM_ID),

                Arguments.of("ccitt", InterchangeIdQualifier.X121),
                Arguments.of("x.121", InterchangeIdQualifier.X121),

                Arguments.of("dod", InterchangeIdQualifier.DOD_ACTIVITY_CODE),
                Arguments.of("defense", InterchangeIdQualifier.DOD_ACTIVITY_CODE),
                Arguments.of("dodaac", InterchangeIdQualifier.DOD_ACTIVITY_CODE),

                Arguments.of("drug", InterchangeIdQualifier.DEA),
                Arguments.of("drugenforcement", InterchangeIdQualifier.DEA),

                Arguments.of("telephone", InterchangeIdQualifier.PHONE),
                Arguments.of("phonenumber", InterchangeIdQualifier.PHONE),

                Arguments.of("ucs", InterchangeIdQualifier.UCS_CODE),

                Arguments.of("dunssuffix", InterchangeIdQualifier.DUNS_PLUS_SUFFIX),

                Arguments.of("petroleum", InterchangeIdQualifier.PASC_COMPANY_CODE),
                Arguments.of("pasc", InterchangeIdQualifier.PASC_COMPANY_CODE),

                Arguments.of("duns4", InterchangeIdQualifier.DUNS_WITH_4CHAR_SUFFIX),
                Arguments.of("dunswith4", InterchangeIdQualifier.DUNS_WITH_4CHAR_SUFFIX),

                Arguments.of("aba", InterchangeIdQualifier.ABA_ROUTING_NUMBER),
                Arguments.of("routing", InterchangeIdQualifier.ABA_ROUTING_NUMBER),
                Arguments.of("bank", InterchangeIdQualifier.ABA_ROUTING_NUMBER),

                Arguments.of("aar", InterchangeIdQualifier.AAR_CODE),
                Arguments.of("railroad", InterchangeIdQualifier.AAR_CODE),
                Arguments.of("railways", InterchangeIdQualifier.AAR_CODE),

                Arguments.of("australia", InterchangeIdQualifier.EDICA_COMM_ID),
                Arguments.of("edica", InterchangeIdQualifier.EDICA_COMM_ID),

                Arguments.of("health", InterchangeIdQualifier.HIN),
                Arguments.of("healthcare", InterchangeIdQualifier.HIN),

                Arguments.of("postsecondary", InterchangeIdQualifier.IPEDS),

                Arguments.of("interagency", InterchangeIdQualifier.FICE),

                Arguments.of("k12", InterchangeIdQualifier.NCES),

                Arguments.of("collegeboard", InterchangeIdQualifier.ATP_CODE),
                Arguments.of("admissions", InterchangeIdQualifier.ATP_CODE),

                Arguments.of("act", InterchangeIdQualifier.ACT_CODE),

                Arguments.of("canadastats", InterchangeIdQualifier.STATS_CANADA_LIST),
                Arguments.of("statscanada", InterchangeIdQualifier.STATS_CANADA_LIST),

                Arguments.of("hcfa", InterchangeIdQualifier.CARRIER_ID_HCFA),

                Arguments.of("intermediary", InterchangeIdQualifier.FISCAL_INTERMEDIARY_ID),
                Arguments.of("fiscal", InterchangeIdQualifier.FISCAL_INTERMEDIARY_ID),

                Arguments.of("medicare", InterchangeIdQualifier.MEDICARE_PROVIDER_ID),

                Arguments.of("tax", InterchangeIdQualifier.US_FEDERAL_TAX_ID),
                Arguments.of("taxid", InterchangeIdQualifier.US_FEDERAL_TAX_ID),

                Arguments.of("iaiabc", InterchangeIdQualifier.IAIABC_ID),
                Arguments.of("workerscomp", InterchangeIdQualifier.IAIABC_ID),

                Arguments.of("ein", InterchangeIdQualifier.FEIN),
                Arguments.of("employerid", InterchangeIdQualifier.FEIN),

                Arguments.of("insurance", InterchangeIdQualifier.NAIC),
                Arguments.of("insurancecode", InterchangeIdQualifier.NAIC),

                Arguments.of("medicaid", InterchangeIdQualifier.MEDICAID_PROVIDER_ID),

                Arguments.of("collegecanada", InterchangeIdQualifier.STATS_CANADA_COLLEGE_CODES),

                Arguments.of("universitycanada", InterchangeIdQualifier.STATS_CANADA_UNIVERSITY_CODES),

                Arguments.of("property", InterchangeIdQualifier.SPICA),

                Arguments.of("secondary", InterchangeIdQualifier.SECONDARY_INST_CODE),
                Arguments.of("highschool", InterchangeIdQualifier.SECONDARY_INST_CODE),

                Arguments.of("mexico", InterchangeIdQualifier.AMECOP),
                Arguments.of("amecop", InterchangeIdQualifier.AMECOP),

                Arguments.of("retail", InterchangeIdQualifier.NRMA),
                Arguments.of("nrma", InterchangeIdQualifier.NRMA),

                Arguments.of("safer", InterchangeIdQualifier.SAFER_ID),
                Arguments.of("safety", InterchangeIdQualifier.SAFER_ID),

                Arguments.of("san", InterchangeIdQualifier.STANDARD_ADDRESS_NUMBER),
                Arguments.of("address", InterchangeIdQualifier.STANDARD_ADDRESS_NUMBER),

                Arguments.of("mutual", InterchangeIdQualifier.MUTUALLY_DEFINED),
                Arguments.of("agreed", InterchangeIdQualifier.MUTUALLY_DEFINED),
                Arguments.of("custom", InterchangeIdQualifier.MUTUALLY_DEFINED),
                Arguments.of("zz", InterchangeIdQualifier.MUTUALLY_DEFINED)
        );
    }
}