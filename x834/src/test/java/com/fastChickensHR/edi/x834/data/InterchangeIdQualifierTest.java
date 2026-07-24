/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterchangeIdQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(InterchangeIdQualifier.class)
    void resolvesFromCodeNameAndDescription(InterchangeIdQualifier constant) {
        assertEquals(constant, InterchangeIdQualifier.fromString(constant.getCode()));
        assertEquals(constant, InterchangeIdQualifier.fromString(constant.name()));
        assertEquals(constant, InterchangeIdQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, InterchangeIdQualifier expected) {
        assertEquals(expected, InterchangeIdQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
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
                Arguments.of("zz", InterchangeIdQualifier.MUTUALLY_DEFINED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> InterchangeIdQualifier.fromString(input));
    }
}
