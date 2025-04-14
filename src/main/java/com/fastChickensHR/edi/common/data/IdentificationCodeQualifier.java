/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Identification Code Qualifiers used in EDI transactions.
 * Contains codes and descriptions for various types of identification numbers.
 */
@Getter
public enum IdentificationCodeQualifier implements EdiCodeEnum {
    PETROEX("0", "Petroleum Industry Exchange (PETROEX) Number"),
    DUNS("1", "D-U-N-S Number, Dun & Bradstreet"),
    SCAC("2", "Standard Carrier Alpha Code (SCAC)"),
    FMC("3", "Federal Maritime Commission (Ocean) (FMC)"),
    IATA("4", "International Air Transport Association (IATA)"),
    PLANT_CODE("6", "Plant Code"),
    LOADING_DOCK("7", "Loading Dock"),
    DUNS_PLUS_4("9", "D-U-N-S+4, D-U-N-S Number with Four Character Suffix"),
    DODAAC("10", "Department of Defense Activity Address Code (DODAAC)"),
    DEA("11", "Drug Enforcement Administration (DEA)"),
    PHONE("12", "Telephone Number (Phone)"),
    FRRC("13", "Federal Reserve Routing Code (FRRC)"),
    SAN("15", "Standard Address Number (SAN)"),
    ZIP_CODE("16", "ZIP Code"),
    ABI("17", "Automated Broker Interface (ABI) Routing Code"),
    FIPS_55("19", "FIPS-55 (Named Populated Places)"),
    SPLC("20", "Standard Point Location Code (SPLC)"),
    HIN("21", "Health Industry Number (HIN)"),
    COPAS("22", "Council of Petroleum Accounting Societies code (COPAS)"),
    JOC("23", "Journal of Commerce (JOC)"),
    EIN("24", "Employer's Identification Number"),
    CARRIER_CUSTOMER_CODE("25", "Carrier's Customer Code"),
    PASC("26", "Petroleum Accountants Society of Canada Company Code"),
    GBLOC("27", "Government Bill Of Lading Office Code (GBLOC)"),
    API("28", "American Paper Institute"),
    GRID_LOCATION("29", "Grid Location and Facility Code"),
    API_LOCATION("30", "American Petroleum Institute Location Code"),
    BANK_ID("31", "Bank Identification Code/Number assigned to a bank within a country (non-USA)"),
    PROPERTY_OPERATOR("32", "Assigned by Property Operator"),
    CAGE("33", "Commercial and Government Entity (CAGE)"),
    SSN("34", "Social Security Number"),
    EMAIL_INTERNAL("35", "Electronic Mail Internal System Address Code"),
    CUSTOMS_BROKER("36", "Customs House Broker License Number"),
    UN_VENDOR("37", "United Nations Vendor Code"),
    COUNTRY_CODE("38", "Country Code"),
    LOCAL_UNION("39", "Local Union Number"),
    EMAIL_USER("40", "Electronic Mail User Code"),
    TELECOM_CARRIER("41", "Telecommunications Carrier Identification Code"),
    TELECOM_PSEUDO("42", "Telecommunications Pseudo Carrier Identification Code"),
    ALT_SSN("43", "Alternate Social Security Number"),
    RETURN_SEQUENCE("44", "Return Sequence Number"),
    DECLARATION_CONTROL("45", "Declaration Control Number"),
    ETIN("46", "Electronic Transmitter Identification Number (ETIN)"),
    TAX_AUTHORITY("47", "Tax Authority Identification"),
    EFIN("48", "Electronic Filer Identification Number (EFIN)"),
    STATE_ID("49", "State Identification Number"),
    BUS_LICENSE("50", "Business License Number"),
    FUEL_INV_ADJ("51", "Fuel Inventory Adjustment Identification"),
    BUILDING("53", "Building"),
    WAREHOUSE("54", "Warehouse"),
    PO_BOX("55", "Post Office Box"),
    DIVISION("56", "Division"),
    DEPARTMENT("57", "Department"),
    ORIGINATING_CO("58", "Originating Company Number"),
    RECEIVING_CO("59", "Receiving Company Number"),
    HOLDING_MORTGAGEE("61", "Holding Mortgagee Number"),
    SERVICING_MORTGAGEE("62", "Servicing Mortgagee Number"),
    SERVICER_HOLDER("63", "Servicer-holder Mortgagee Number"),
    ONE_CALL_AGENCY("64", "One Call Agency"),
    IPEDS("71", "Integrated Postsecondary Education Data System (IPEDS)"),
    ATP("72", "The College Board's Admission Testing Program (ATP)"),
    FICE("73", "Federal Interagency Commission on Education (FICE) number"),
    ACT("74", "American College Testing (ACT) list of postsecondary educational institutions"),
    STATE_PROVINCE("75", "State or Province Assigned Number"),
    LOCAL_SCHOOL("76", "Local School District or Jurisdiction Number"),
    NCES_CCD("77", "National Center for Education Statistics (NCES) Common Core of Data (CCD)"),
    COLLEGE_BOARD_ACT("78", "The College Board and ACT 6 digit code list of secondary educational institutions"),
    CIP("81", "Classification of Instructional Programs (CIP) coding structure"),
    HEGIS("82", "Higher Education General Information Survey (HEGIS)"),
    CONGRESSIONAL_DISTRICT("83", "Congressional District"),
    CA_ETHNIC("90", "California Ethnic Subgroups Code Table"),
    SELLER_ASSIGNED("91", "Assigned by Seller or Seller's Agent"),
    BUYER_ASSIGNED("92", "Assigned by Buyer or Buyer's Agent"),
    ORIG_CODE("93", "Code assigned by the organization originating the transaction set"),
    DEST_CODE("94", "Code assigned by the organization that is the ultimate destination"),
    TRANSPORTER("95", "Assigned By Transporter"),
    PIPELINE_OPERATOR("96", "Assigned By Pipeline Operator"),
    RECEIVERS_CODE("97", "Receiver's Code"),
    PURCHASING_OFFICE("98", "Purchasing Office"),
    OWCP("99", "Office of Workers Compensation Programs (OWCP) Agency Code"),
    CUSTOMS_CARRIER("A", "U.S. Customs Carrier Identification"),
    APPROVER_ID("A1", "Approver ID"),
    MAPAC("A2", "Military Assistance Program Address Code (MAPAC)"),
    THIRD_PARTY("A3", "Assigned by Third Party"),
    CLEARINGHOUSE("A4", "Assigned by Clearinghouse"),
    CUSIP("A5", "Committee on Uniform Security Identification Procedures (CUSIP) Number"),
    FINS("A6", "Financial Identification Numbering System (FINS) Number"),
    ACEID("A7", "Automated Commercial Environment Identification Code (ACEID)"),
    POSTAL_CODE("AA", "Postal Service Code"),
    EPA_ID("AB", "US Environmental Protection Agency (EPA) Identification Number"),
    ATTACHMENT_CONTROL("AC", "Attachment Control Number"),
    BCBS_PLAN("AD", "Blue Cross Blue Shield Association Plan Code"),
    ALBERTA_ENERGY("AE", "Alberta Energy Resources Conservation Board"),
    RENTAL_LOCATION("AF", "Rental Location Identifier"),
    AUTOMOTIVE_CA("AI", "Automotive Identifier for Canada Customs"),
    ANESTHESIA_LICENSE("AL", "Anesthesia License Number"),
    ALBERTA_PETROLEUM("AP", "Alberta Petroleum Marketing Commission"),
    BC_MINISTRY("BC", "British Columbia Ministry of Energy Mines and Petroleum Resources"),
    BLUE_CROSS("BD", "Blue Cross Provider Number"),
    CLLI("BE", "Common Language Location Identification (CLLI)"),
    BADGE("BG", "Badge Number"),
    CCRA_BUSINESS("BN", "Canada Customs & Revenue Agency (CCRA) Business Number"),
    BENEFIT_PLAN("BP", "Benefit Plan"),
    BLUE_SHIELD("BS", "Blue Shield Provider Number"),
    CHANGED_INSURED("C", "Insured's Changed Unique Identification Number"),
    INSURED("C1", "Insured or Subscriber"),
    HMO_PROVIDER("C2", "Health Maintenance Organization (HMO) Provider Number"),
    CUSTOMER_ID("C5", "Customer Identification File"),
    STATS_CAN_COLLEGE_COURSE("CA", "Statistics Canada Canadian College Student Information System Course Codes"),
    STATS_CAN_COLLEGE_INST("CB", "Statistics Canada Canadian College Student Information System Institution Codes"),
    STATS_CAN_UNIV_CURR("CC", "Statistics Canada University Student Information System Curriculum Codes"),
    CONTRACT_DIVISION("CD", "Contract Division"),
    CENSUS_FILER("CE", "Bureau of the Census Filer Identification Code"),
    CAN_FINANCIAL("CF", "Canadian Financial Institution Routing Number"),
    CHAMPUS("CI", "CHAMPUS (Civilian Health and Medical Program of the Uniformed Services) Identification Number"),
    CORRECTED_LOAN("CL", "Corrected Loan Number"),
    CUSTOMS_MID("CM", "U.S. Customs Service (USCS) Manufacturer Identifier (MID)"),
    NCES_COURSE("CN", "National Center for Education Statistics (NCES) Course Classification System"),
    CAN_PETROLEUM("CP", "Canadian Petroleum Association"),
    CREDIT_REPOSITORY("CR", "Credit Repository"),
    STATS_CAN_UNIV("CS", "Statistics Canada University Student Information System University Codes"),
    COURT_ID("CT", "Court Identification Code"),
    CENSUS_SCHEDULE_D("D", "Census Schedule D"),
    DOE_GUARANTOR("DG", "United States Department of Education Guarantor Identification Code"),
    DOE_LENDER("DL", "United States Department of Education Lender Identification Code"),
    DENTIST_LICENSE("DN", "Dentist License Number"),
    DOOR("DO", "Door"),
    DATA_PROCESSING("DP", "Data Processing Point"),
    GISB_DRN("DR", "Gas Industry Standards Board (GISB) Data Reference Number (DRN)"),
    DOE_SCHOOL("DS", "United States Department of Education School Identification Code"),
    HAZARD_INSURANCE("E", "Hazard Insurance Policy Number"),
    ARI_EC_LOCATION("EC", "ARI Electronic Commerce Location ID Code"),
    THEATRE("EH", "Theatre Number"),
    EMPLOYEE_ID("EI", "Employee Identification Number"),
    ELEVATOR("EL", "Elevator"),
    EPA("EP", "U.S. Environmental Protection Agency (EPA)"),
    INS_CO_ASSIGNED("EQ", "Insurance Company Assigned Identification Number"),
    MORTGAGEE_ASSIGNED("ER", "Mortgagee Assigned Identification Number"),
    AES_FILER("ES", "Automated Export System (AES) Filer Identification Code"),
    ETS_INTL("ET", "Educational Testing Service List of International Postsecondary Institutions"),
    DOC_CUSTODIAN("F", "Document Custodian Identification Number"),
    FACILITY_ID("FA", "Facility Identification"),
    FIELD_CODE("FB", "Field Code"),
    FED_COURT_JURISDICTION("FC", "Federal Court Jurisdiction Identifier"),
    FED_COURT_DIVISION("FD", "Federal Court Divisional Office Number"),
    FACILITY_FED_ID("FE", "Facility Federal Identification Number"),
    FEDERAL_TIN("FI", "Federal Taxpayer's Identification Number"),
    FED_JURISDICTION("FJ", "Federal Jurisdiction"),
    FLOOR("FL", "Floor"),
    EPA_LAB_CERT("FN", "U.S. Environmental Protection Agency (EPA) Laboratory Certification Identification"),
    PAYEE_ID("G", "Payee Identification Number"),
    PRIMARY_AGENT("GA", "Primary Agent Identification"),
    GAS_CODE("GC", "GAS*CODE"),
    CMS("HC", "Centers for Medicare and Medicaid Services"),
    HIC("HN", "Health Insurance Claim (HIC) Number"),
    HOUSE_GRAIN("HS", "House (Canadian Grain Elevator)"),
    SEC_MARKETING("I", "Secondary Marketing Investor Assigned Number"),
    UCC_EDI_COMM_ID("ID", "UCC EDI Communications ID (Comm ID)"),
    HEALTH_ID("II", "Standard Unique Health Identifier for each Individual in the United States"),
    CIP_PARTICIPANT("IP", "U.S. Customs Carrier Initiative Program (CIP) Participant Identification Number"),
    MERS_ORG_ID("J", "Mortgage Electronic Registration System Organization Identifier"),
    CENSUS_SCHEDULE_K("K", "Census Schedule K"),
    INVESTOR_ASSIGNED("L", "Investor Assigned Identification Number"),
    AGENCY_LOCATION("LC", "Agency Location Code (U.S. Government)"),
    NISO_LANGUAGE("LD", "NISO Z39.53 Language Codes"),
    ISO_LANGUAGE("LE", "ISO 639 Language Codes"),
    LABELER_ID("LI", "Labeler Identification Code (LIC)"),
    LOAN_NUMBER("LN", "Loan Number"),
    CERTIFICATE("M", "Certificate Number"),
    DISBURSING_STATION("M3", "Disbursing Station"),
    DOD_ROUTING("M4", "Department of Defense Routing Identifier Code (RIC)"),
    JURISDICTION_CODE("M5", "Jurisdiction Code"),
    DIVISION_OFFICE("M6", "Division Office Code"),
    MAIL_STOP("MA", "Mail Stop"),
    MED_INFO_BUREAU("MB", "Medical Information Bureau"),
    MEDICAID_PROVIDER("MC", "Medicaid Provider Number"),
    MANITOBA_MINES("MD", "Manitoba Department of Mines and Resources"),
    MEMBER_ID("MI", "Member Identification Number"),
    MARKET("MK", "Market"),
    MLS_VENDOR("ML", "Multiple Listing Service Vendor - Multiple Listing Service Identification"),
    MORTGAGE_ID("MN", "Mortgage Identification Number"),
    MAJOR_ORG_ENTITY("MO", "Major Organizational Entity"),
    MEDICARE_PROVIDER("MP", "Medicare Provider Number"),
    MEDICAID_RECIPIENT("MR", "Medicaid Recipient Identification Number"),
    INSURED_ID("N", "Insured's Unique Identification Number"),
    NAR_MLS("NA", "National Association of Realtors - Multiple Listing Service Identification"),
    MODE_DESIGNATOR("ND", "Mode Designator"),
    NAIC("NI", "National Association of Insurance Commissioners (NAIC) Identification"),
    NCIC("NO", "National Criminal Information Center Originating Agency"),
    ALIEN_REG("NR", "Non Resident Alien Registration Number"),
    OCCUPATION_CODE("OC", "Occupation Code"),
    OPAC("OP", "On-line Payment and Collection"),
    SECONDARY_AGENT("PA", "Secondary Agent Identification"),
    PUBLIC_ID("PB", "Public Identification"),
    PROVIDER_COMMERCIAL("PC", "Provider Commercial Number"),
    PAYOR_ID("PI", "Payor Identification"),
    PHARMACY_PROCESSOR("PP", "Pharmacy Processor Number"),
    PIER("PR", "Pier"),
    REGULATORY_AGENCY("RA", "Regulatory Agency Number"),
    REAL_ESTATE_AGENT("RB", "Real Estate Agent"),
    REAL_ESTATE_COMPANY("RC", "Real Estate Company"),
    REAL_ESTATE_BROKER("RD", "Real Estate Broker Identification"),
    REAL_ESTATE_LICENSE("RE", "Real Estate License Number"),
    ORIS_CODE("RI", "Office of Regulatory Information Systems (ORIS) Code"),
    RAMP("RP", "Ramp"),
    RAILROAD_TRACK("RT", "Railroad Track"),
    TITLE_INSURANCE("S", "Title Insurance Policy Number"),
    TERTIARY_AGENT("SA", "Tertiary Agent Identification"),
    SIN("SB", "Social Insurance Number"),
    SASKATCHEWAN_ENERGY("SD", "Saskatchewan Department of Energy Mines and Resources"),
    SUFFIX_CODE("SF", "Suffix Code"),
    SIC("SI", "Standard Industry Code (SIC)"),
    STATE_JURISDICTION("SJ", "State or Province Jurisdiction"),
    STATE_LOTTERY("SK", "State/Provincial Lottery License Number"),
    STATE_LICENSE("SL", "State License Number"),
    SPECIALTY_LICENSE("SP", "Specialty License Number"),
    STATE_LICENSE_TAG("ST", "State/Province License Tag"),
    SERVICE_PROVIDER("SV", "Service Provider Number"),
    SWIFT_ADDRESS("SW", "Society for Worldwide Interbank Financial Telecommunications (SWIFT) Address"),
    TIN("TA", "Taxpayer ID Number"),
    IRS_TERMINAL("TC", "Internal Revenue Service Terminal Code"),
    TRANSPORT4_LOCATION("TL", "Transport4 Location Code"),
    TRANSPORT4_SHIPPER("TS", "Transport4 Shipper Code"),
    DEPARTMENT_CODE("TZ", "Department Code"),
    CONSUMER_CREDIT("UC", "Consumer Credit Identification Number"),
    UNIT_ID_CODE("UI", "Unit Identification Code"),
    GLN("UL", "Global Location Number (GLN)"),
    UPIN("UP", "Unique Physician Identification Number (UPIN)"),
    URL("UR", "Uniform Resource Locator (URL)"),
    USIN("US", "Unique Supplier Identification Number (USIN)"),
    UNIT("UT", "Unit"),
    WINE_REGION("WR", "Wine Region Code"),
    EDU_LANGUAGE("WS", "Education Language Codes"),
    NCES_UNIT("X1", "National Center for Education Statistics Unit Identification Number"),
    CMS_PLANID("XV", "Centers for Medicare and Medicaid Services PlanID"),
    CMS_NPI("XX", "Centers for Medicare and Medicaid Services National Provider Identifier"),
    DISTRICT_ASSIGNED("XY", "District Assigned Number"),
    CONTRACTOR_ESTAB("ZC", "Contractor Establishment Code"),
    ZONE("ZN", "Zone"),
    TEMP_ID("ZY", "Temporary Identification Number"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<IdentificationCodeQualifier> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                IdentificationCodeQualifier.class,
                "Identification Code Qualifier",
                Map.ofEntries(
                        Map.entry("duns", DUNS),
                        Map.entry("dunsnumber", DUNS),
                        Map.entry("dun and bradstreet", DUNS),

                        Map.entry("phone number", PHONE),
                        Map.entry("telephone", PHONE),

                        Map.entry("ssn", SSN),
                        Map.entry("social security", SSN),

                        Map.entry("ein", EIN),
                        Map.entry("employer id", EIN),
                        Map.entry("tax id", EIN),

                        Map.entry("zip", ZIP_CODE),
                        Map.entry("postal code", ZIP_CODE),

                        Map.entry("npi", CMS_NPI),
                        Map.entry("national provider identifier", CMS_NPI),

                        Map.entry("upin", UPIN),
                        Map.entry("physician id", UPIN),

                        Map.entry("website", URL),
                        Map.entry("web address", URL)
                )
        );
    }

    IdentificationCodeQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an IdentificationCodeQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching IdentificationCodeQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static IdentificationCodeQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}