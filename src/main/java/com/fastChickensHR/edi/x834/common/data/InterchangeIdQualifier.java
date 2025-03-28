/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.data;

import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Interchange ID Qualifiers used in the ISA05 and ISA07 fields
 * of the EDI 834 Interchange Control Header (ISA) segment.
 */
@Getter
public enum InterchangeIdQualifier implements EdiCodeEnum {
    DUNS("01", "Duns (Dun & Bradstreet)"),
    SCAC("02", "SCAC (Standard Carrier Alpha Code)"),
    FMC("03", "FMC (Federal Maritime Commission)"),
    IATA("04", "IATA (International Air Transport Association)"),
    GLN("07", "Global Location Number (GLN)"),
    UCC_EDI_COMM_ID("08", "UCC EDI Communications ID (Comm ID)"),
    X121("09", "X.121 (CCITT)"),
    DOD_ACTIVITY_CODE("10", "Department of Defense (DoD) Activity Address Code"),
    DEA("11", "DEA (Drug Enforcement Administration)"),
    PHONE("12", "Phone (Telephone Companies)"),
    UCS_CODE("13", "UCS Code"),
    DUNS_PLUS_SUFFIX("14", "Duns Plus Suffix"),
    PASC_COMPANY_CODE("15", "Petroleum Accountants Society of Canada Company Code"),
    DUNS_WITH_4CHAR_SUFFIX("16", "Duns Number With 4-Character Suffix"),
    ABA_ROUTING_NUMBER("17", "American Bankers Association (ABA) Transit Routing Number"),
    AAR_CODE("18", "Association of American Railroads (AAR) Standard Distribution Code"),
    EDICA_COMM_ID("19", "EDI Council of Australia (EDICA) Communications ID Number"),
    HIN("20", "Health Industry Number (HIN)"),
    IPEDS("21", "Integrated Postsecondary Education Data System (IPEDS)"),
    FICE("22", "Federal Interagency Commission on Education (FICE)"),
    NCES("23", "National Center for Education Statistics Common Core of Data 12-Digit Number"),
    ATP_CODE("24", "The College Board's Admission Testing Program 4-Digit Code"),
    ACT_CODE("25", "ACT, Inc. 4-Digit Code of Postsecondary Institutions"),
    STATS_CANADA_LIST("26", "Statistics of Canada List of Postsecondary Institutions"),
    CARRIER_ID_HCFA("27", "Carrier Identification Number as assigned by HCFA"),
    FISCAL_INTERMEDIARY_ID("28", "Fiscal Intermediary Identification Number as assigned by HCFA"),
    MEDICARE_PROVIDER_ID("29", "Medicare Provider and Supplier Identification Number"),
    US_FEDERAL_TAX_ID("30", "U.S. Federal Tax Identification Number"),
    IAIABC_ID("31", "Jurisdiction Identification Number Plus 4 as assigned by IAIABC"),
    FEIN("32", "U.S. Federal Employer Identification Number (FEIN)"),
    NAIC("33", "National Association of Insurance Commissioners Company Code (NAIC)"),
    MEDICAID_PROVIDER_ID("34", "Medicaid Provider and Supplier Identification Number"),
    STATS_CANADA_COLLEGE_CODES("35", "Statistics Canada Canadian College Student Information System Institution Codes"),
    STATS_CANADA_UNIVERSITY_CODES("36", "Statistics Canada University Student Information System Institution Codes"),
    SPICA("37", "Society of Property Information Compilers and Analysts"),
    SECONDARY_INST_CODE("38", "The College Board and ACT, Inc. 6-Digit Code List of Secondary Institutions"),
    AMECOP("AM", "Association Mexicana del Codigo de Producto (AMECOP) Communication ID"),
    NRMA("NR", "National Retail Merchants Association (NRMA) - Assigned"),
    SAFER_ID("SA", "User Identification Number as assigned by SAFER System"),
    STANDARD_ADDRESS_NUMBER("SN", "Standard Address Number"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<InterchangeIdQualifier> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                InterchangeIdQualifier.class,
                "Interchange ID Qualifier",
                Map.<String, InterchangeIdQualifier>ofEntries(
                        Map.entry("d&b", DUNS),
                        Map.entry("dunsnumber", DUNS),
                        Map.entry("dun and bradstreet", DUNS),

                        Map.entry("standardcarrier", SCAC),

                        Map.entry("maritime", FMC),
                        Map.entry("federal maritime", FMC),

                        Map.entry("air", IATA),
                        Map.entry("airline", IATA),
                        Map.entry("airtransport", IATA),

                        Map.entry("gln", GLN),
                        Map.entry("globallocation", GLN),
                        Map.entry("ean", GLN),
                        Map.entry("ucc", GLN),

                        Map.entry("edi", UCC_EDI_COMM_ID),
                        Map.entry("uccedi", UCC_EDI_COMM_ID),
                        Map.entry("commid", UCC_EDI_COMM_ID),

                        Map.entry("ccitt", X121),
                        Map.entry("x.121", X121),

                        Map.entry("dod", DOD_ACTIVITY_CODE),
                        Map.entry("defense", DOD_ACTIVITY_CODE),
                        Map.entry("dodaac", DOD_ACTIVITY_CODE),

                        Map.entry("drug", DEA),
                        Map.entry("drugenforcement", DEA),

                        Map.entry("telephone", PHONE),
                        Map.entry("phonenumber", PHONE),

                        Map.entry("ucs", UCS_CODE),

                        Map.entry("dunssuffix", DUNS_PLUS_SUFFIX),

                        Map.entry("petroleum", PASC_COMPANY_CODE),
                        Map.entry("pasc", PASC_COMPANY_CODE),

                        Map.entry("duns4", DUNS_WITH_4CHAR_SUFFIX),
                        Map.entry("dunswith4", DUNS_WITH_4CHAR_SUFFIX),

                        Map.entry("aba", ABA_ROUTING_NUMBER),
                        Map.entry("routing", ABA_ROUTING_NUMBER),
                        Map.entry("bank", ABA_ROUTING_NUMBER),

                        Map.entry("aar", AAR_CODE),
                        Map.entry("railroad", AAR_CODE),
                        Map.entry("railways", AAR_CODE),

                        Map.entry("australia", EDICA_COMM_ID),
                        Map.entry("edica", EDICA_COMM_ID),

                        Map.entry("health", HIN),
                        Map.entry("healthcare", HIN),

                        Map.entry("postsecondary", IPEDS),

                        Map.entry("interagency", FICE),

                        Map.entry("k12", NCES),

                        Map.entry("collegeboard", ATP_CODE),
                        Map.entry("admissions", ATP_CODE),

                        Map.entry("act", ACT_CODE),

                        Map.entry("canadastats", STATS_CANADA_LIST),
                        Map.entry("statscanada", STATS_CANADA_LIST),

                        Map.entry("hcfa", CARRIER_ID_HCFA),

                        Map.entry("intermediary", FISCAL_INTERMEDIARY_ID),
                        Map.entry("fiscal", FISCAL_INTERMEDIARY_ID),

                        Map.entry("medicare", MEDICARE_PROVIDER_ID),

                        Map.entry("tax", US_FEDERAL_TAX_ID),
                        Map.entry("taxid", US_FEDERAL_TAX_ID),

                        Map.entry("iaiabc", IAIABC_ID),
                        Map.entry("workerscomp", IAIABC_ID),

                        Map.entry("ein", FEIN),
                        Map.entry("employerid", FEIN),

                        Map.entry("insurance", NAIC),
                        Map.entry("insurancecode", NAIC),

                        Map.entry("medicaid", MEDICAID_PROVIDER_ID),

                        Map.entry("collegecanada", STATS_CANADA_COLLEGE_CODES),

                        Map.entry("universitycanada", STATS_CANADA_UNIVERSITY_CODES),

                        Map.entry("property", SPICA),

                        Map.entry("secondary", SECONDARY_INST_CODE),
                        Map.entry("highschool", SECONDARY_INST_CODE),

                        Map.entry("mexico", AMECOP),
                        Map.entry("amecop", AMECOP),

                        Map.entry("retail", NRMA),
                        Map.entry("nrma", NRMA),

                        Map.entry("safer", SAFER_ID),
                        Map.entry("safety", SAFER_ID),

                        Map.entry("san", STANDARD_ADDRESS_NUMBER),
                        Map.entry("address", STANDARD_ADDRESS_NUMBER),

                        Map.entry("mutual", MUTUALLY_DEFINED),
                        Map.entry("agreed", MUTUALLY_DEFINED),
                        Map.entry("custom", MUTUALLY_DEFINED),
                        Map.entry("zz", MUTUALLY_DEFINED)
                )
        );
    }

    InterchangeIdQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an InterchangeIdQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching InterchangeIdQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static InterchangeIdQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}