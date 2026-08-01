/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import java.util.Map;
import lombok.Getter;

/**
 * Code values for the Interchange ID Qualifier (ISA05 and ISA07, X12 data element I05) in the
 * Interchange Control Header (ISA) of an X12 834 interchange (005010X220A1). Qualifies the kind of
 * sender ID (ISA06) and receiver ID (ISA08) that follows.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum InterchangeIdQualifier implements EdiCodeEnum {
  /** Duns (Dun &amp; Bradstreet) — X12 code "01". */
  DUNS("01", "Duns (Dun & Bradstreet)"),
  /** SCAC (Standard Carrier Alpha Code) — X12 code "02". */
  SCAC("02", "SCAC (Standard Carrier Alpha Code)"),
  /** FMC (Federal Maritime Commission) — X12 code "03". */
  FMC("03", "FMC (Federal Maritime Commission)"),
  /** IATA (International Air Transport Association) — X12 code "04". */
  IATA("04", "IATA (International Air Transport Association)"),
  /** Global Location Number (GLN) — X12 code "07". */
  GLN("07", "Global Location Number (GLN)"),
  /** UCC EDI Communications ID (Comm ID) — X12 code "08". */
  UCC_EDI_COMM_ID("08", "UCC EDI Communications ID (Comm ID)"),
  /** X.121 (CCITT) — X12 code "09". */
  X121("09", "X.121 (CCITT)"),
  /** Department of Defense (DoD) Activity Address Code — X12 code "10". */
  DOD_ACTIVITY_CODE("10", "Department of Defense (DoD) Activity Address Code"),
  /** DEA (Drug Enforcement Administration) — X12 code "11". */
  DEA("11", "DEA (Drug Enforcement Administration)"),
  /** Phone (Telephone Companies) — X12 code "12". */
  PHONE("12", "Phone (Telephone Companies)"),
  /** UCS Code — X12 code "13". */
  UCS_CODE("13", "UCS Code"),
  /** Duns Plus Suffix — X12 code "14". */
  DUNS_PLUS_SUFFIX("14", "Duns Plus Suffix"),
  /** Petroleum Accountants Society of Canada Company Code — X12 code "15". */
  PASC_COMPANY_CODE("15", "Petroleum Accountants Society of Canada Company Code"),
  /** Duns Number With 4-Character Suffix — X12 code "16". */
  DUNS_WITH_4CHAR_SUFFIX("16", "Duns Number With 4-Character Suffix"),
  /** American Bankers Association (ABA) Transit Routing Number — X12 code "17". */
  ABA_ROUTING_NUMBER("17", "American Bankers Association (ABA) Transit Routing Number"),
  /** Association of American Railroads (AAR) Standard Distribution Code — X12 code "18". */
  AAR_CODE("18", "Association of American Railroads (AAR) Standard Distribution Code"),
  /** EDI Council of Australia (EDICA) Communications ID Number — X12 code "19". */
  EDICA_COMM_ID("19", "EDI Council of Australia (EDICA) Communications ID Number"),
  /** Health Industry Number (HIN) — X12 code "20". */
  HIN("20", "Health Industry Number (HIN)"),
  /** Integrated Postsecondary Education Data System (IPEDS) — X12 code "21". */
  IPEDS("21", "Integrated Postsecondary Education Data System (IPEDS)"),
  /** Federal Interagency Commission on Education (FICE) — X12 code "22". */
  FICE("22", "Federal Interagency Commission on Education (FICE)"),
  /**
   * National Center for Education Statistics Common Core of Data 12-Digit Number — X12 code "23".
   */
  NCES("23", "National Center for Education Statistics Common Core of Data 12-Digit Number"),
  /** The College Board's Admission Testing Program 4-Digit Code — X12 code "24". */
  ATP_CODE("24", "The College Board's Admission Testing Program 4-Digit Code"),
  /** ACT, Inc. 4-Digit Code of Postsecondary Institutions — X12 code "25". */
  ACT_CODE("25", "ACT, Inc. 4-Digit Code of Postsecondary Institutions"),
  /** Statistics of Canada List of Postsecondary Institutions — X12 code "26". */
  STATS_CANADA_LIST("26", "Statistics of Canada List of Postsecondary Institutions"),
  /** Carrier Identification Number as assigned by HCFA — X12 code "27". */
  CARRIER_ID_HCFA("27", "Carrier Identification Number as assigned by HCFA"),
  /** Fiscal Intermediary Identification Number as assigned by HCFA — X12 code "28". */
  FISCAL_INTERMEDIARY_ID("28", "Fiscal Intermediary Identification Number as assigned by HCFA"),
  /** Medicare Provider and Supplier Identification Number — X12 code "29". */
  MEDICARE_PROVIDER_ID("29", "Medicare Provider and Supplier Identification Number"),
  /** U.S. Federal Tax Identification Number — X12 code "30". */
  US_FEDERAL_TAX_ID("30", "U.S. Federal Tax Identification Number"),
  /** Jurisdiction Identification Number Plus 4 as assigned by IAIABC — X12 code "31". */
  IAIABC_ID("31", "Jurisdiction Identification Number Plus 4 as assigned by IAIABC"),
  /** U.S. Federal Employer Identification Number (FEIN) — X12 code "32". */
  FEIN("32", "U.S. Federal Employer Identification Number (FEIN)"),
  /** National Association of Insurance Commissioners Company Code (NAIC) — X12 code "33". */
  NAIC("33", "National Association of Insurance Commissioners Company Code (NAIC)"),
  /** Medicaid Provider and Supplier Identification Number — X12 code "34". */
  MEDICAID_PROVIDER_ID("34", "Medicaid Provider and Supplier Identification Number"),
  /**
   * Statistics Canada Canadian College Student Information System Institution Codes — X12 code
   * "35".
   */
  STATS_CANADA_COLLEGE_CODES(
      "35", "Statistics Canada Canadian College Student Information System Institution Codes"),
  /** Statistics Canada University Student Information System Institution Codes — X12 code "36". */
  STATS_CANADA_UNIVERSITY_CODES(
      "36", "Statistics Canada University Student Information System Institution Codes"),
  /** Society of Property Information Compilers and Analysts — X12 code "37". */
  SPICA("37", "Society of Property Information Compilers and Analysts"),
  /**
   * The College Board and ACT, Inc. 6-Digit Code List of Secondary Institutions — X12 code "38".
   */
  SECONDARY_INST_CODE(
      "38", "The College Board and ACT, Inc. 6-Digit Code List of Secondary Institutions"),
  /** Association Mexicana del Codigo de Producto (AMECOP) Communication ID — X12 code "AM". */
  AMECOP("AM", "Association Mexicana del Codigo de Producto (AMECOP) Communication ID"),
  /** National Retail Merchants Association (NRMA) - Assigned — X12 code "NR". */
  NRMA("NR", "National Retail Merchants Association (NRMA) - Assigned"),
  /** User Identification Number as assigned by SAFER System — X12 code "SA". */
  SAFER_ID("SA", "User Identification Number as assigned by SAFER System"),
  /** Standard Address Number — X12 code "SN". */
  STANDARD_ADDRESS_NUMBER("SN", "Standard Address Number"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<InterchangeIdQualifier> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("zz", MUTUALLY_DEFINED)));
  }

  InterchangeIdQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an InterchangeIdQualifier instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching InterchangeIdQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static InterchangeIdQualifier fromString(String input) {
    return LOOKUP.fromString(input);
  }

  /**
   * Returns the raw X12 code value for this constant (not the enum name), so the enum renders
   * directly into an EDI element.
   */
  @Override
  public String toString() {
    return code;
  }
}
