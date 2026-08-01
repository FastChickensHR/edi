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
 * Code values for the X12 Identification Code Qualifier (data element 66), which designates the
 * system or authority behind an accompanying identification code. In the X12 834 (005010X220A1) it
 * appears as NM108 and N103/N104 to qualify identifiers such as tax IDs, SSNs, and payer/plan
 * identifiers.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum IdentificationCodeQualifier implements EdiCodeEnum {
  /** Petroleum Industry Exchange (PETROEX) Number — X12 code "0". */
  PETROEX("0", "Petroleum Industry Exchange (PETROEX) Number"),
  /** D-U-N-S Number, Dun &amp; Bradstreet — X12 code "1". */
  DUNS("1", "D-U-N-S Number, Dun & Bradstreet"),
  /** Standard Carrier Alpha Code (SCAC) — X12 code "2". */
  SCAC("2", "Standard Carrier Alpha Code (SCAC)"),
  /** Federal Maritime Commission (Ocean) (FMC) — X12 code "3". */
  FMC("3", "Federal Maritime Commission (Ocean) (FMC)"),
  /** International Air Transport Association (IATA) — X12 code "4". */
  IATA("4", "International Air Transport Association (IATA)"),
  /** Plant Code — X12 code "6". */
  PLANT_CODE("6", "Plant Code"),
  /** Loading Dock — X12 code "7". */
  LOADING_DOCK("7", "Loading Dock"),
  /** D-U-N-S+4, D-U-N-S Number with Four Character Suffix — X12 code "9". */
  DUNS_PLUS_4("9", "D-U-N-S+4, D-U-N-S Number with Four Character Suffix"),
  /** Department of Defense Activity Address Code (DODAAC) — X12 code "10". */
  DODAAC("10", "Department of Defense Activity Address Code (DODAAC)"),
  /** Drug Enforcement Administration (DEA) — X12 code "11". */
  DEA("11", "Drug Enforcement Administration (DEA)"),
  /** Telephone Number (Phone) — X12 code "12". */
  PHONE("12", "Telephone Number (Phone)"),
  /** Federal Reserve Routing Code (FRRC) — X12 code "13". */
  FRRC("13", "Federal Reserve Routing Code (FRRC)"),
  /** Standard Address Number (SAN) — X12 code "15". */
  SAN("15", "Standard Address Number (SAN)"),
  /** ZIP Code — X12 code "16". */
  ZIP_CODE("16", "ZIP Code"),
  /** Automated Broker Interface (ABI) Routing Code — X12 code "17". */
  ABI("17", "Automated Broker Interface (ABI) Routing Code"),
  /** FIPS-55 (Named Populated Places) — X12 code "19". */
  FIPS_55("19", "FIPS-55 (Named Populated Places)"),
  /** Standard Point Location Code (SPLC) — X12 code "20". */
  SPLC("20", "Standard Point Location Code (SPLC)"),
  /** Health Industry Number (HIN) — X12 code "21". */
  HIN("21", "Health Industry Number (HIN)"),
  /** Council of Petroleum Accounting Societies code (COPAS) — X12 code "22". */
  COPAS("22", "Council of Petroleum Accounting Societies code (COPAS)"),
  /** Journal of Commerce (JOC) — X12 code "23". */
  JOC("23", "Journal of Commerce (JOC)"),
  /** Employer's Identification Number — X12 code "24". */
  EIN("24", "Employer's Identification Number"),
  /** Carrier's Customer Code — X12 code "25". */
  CARRIER_CUSTOMER_CODE("25", "Carrier's Customer Code"),
  /** Petroleum Accountants Society of Canada Company Code — X12 code "26". */
  PASC("26", "Petroleum Accountants Society of Canada Company Code"),
  /** Government Bill Of Lading Office Code (GBLOC) — X12 code "27". */
  GBLOC("27", "Government Bill Of Lading Office Code (GBLOC)"),
  /** American Paper Institute — X12 code "28". */
  API("28", "American Paper Institute"),
  /** Grid Location and Facility Code — X12 code "29". */
  GRID_LOCATION("29", "Grid Location and Facility Code"),
  /** American Petroleum Institute Location Code — X12 code "30". */
  API_LOCATION("30", "American Petroleum Institute Location Code"),
  /**
   * Bank Identification Code/Number assigned to a bank within a country (non-USA) — X12 code "31".
   */
  BANK_ID("31", "Bank Identification Code/Number assigned to a bank within a country (non-USA)"),
  /** Assigned by Property Operator — X12 code "32". */
  PROPERTY_OPERATOR("32", "Assigned by Property Operator"),
  /** Commercial and Government Entity (CAGE) — X12 code "33". */
  CAGE("33", "Commercial and Government Entity (CAGE)"),
  /** Social Security Number — X12 code "34". */
  SSN("34", "Social Security Number"),
  /** Electronic Mail Internal System Address Code — X12 code "35". */
  EMAIL_INTERNAL("35", "Electronic Mail Internal System Address Code"),
  /** Customs House Broker License Number — X12 code "36". */
  CUSTOMS_BROKER("36", "Customs House Broker License Number"),
  /** United Nations Vendor Code — X12 code "37". */
  UN_VENDOR("37", "United Nations Vendor Code"),
  /** Country Code — X12 code "38". */
  COUNTRY_CODE("38", "Country Code"),
  /** Local Union Number — X12 code "39". */
  LOCAL_UNION("39", "Local Union Number"),
  /** Electronic Mail User Code — X12 code "40". */
  EMAIL_USER("40", "Electronic Mail User Code"),
  /** Telecommunications Carrier Identification Code — X12 code "41". */
  TELECOM_CARRIER("41", "Telecommunications Carrier Identification Code"),
  /** Telecommunications Pseudo Carrier Identification Code — X12 code "42". */
  TELECOM_PSEUDO("42", "Telecommunications Pseudo Carrier Identification Code"),
  /** Alternate Social Security Number — X12 code "43". */
  ALT_SSN("43", "Alternate Social Security Number"),
  /** Return Sequence Number — X12 code "44". */
  RETURN_SEQUENCE("44", "Return Sequence Number"),
  /** Declaration Control Number — X12 code "45". */
  DECLARATION_CONTROL("45", "Declaration Control Number"),
  /** Electronic Transmitter Identification Number (ETIN) — X12 code "46". */
  ETIN("46", "Electronic Transmitter Identification Number (ETIN)"),
  /** Tax Authority Identification — X12 code "47". */
  TAX_AUTHORITY("47", "Tax Authority Identification"),
  /** Electronic Filer Identification Number (EFIN) — X12 code "48". */
  EFIN("48", "Electronic Filer Identification Number (EFIN)"),
  /** State Identification Number — X12 code "49". */
  STATE_ID("49", "State Identification Number"),
  /** Business License Number — X12 code "50". */
  BUS_LICENSE("50", "Business License Number"),
  /** Fuel Inventory Adjustment Identification — X12 code "51". */
  FUEL_INV_ADJ("51", "Fuel Inventory Adjustment Identification"),
  /** Building — X12 code "53". */
  BUILDING("53", "Building"),
  /** Warehouse — X12 code "54". */
  WAREHOUSE("54", "Warehouse"),
  /** Post Office Box — X12 code "55". */
  PO_BOX("55", "Post Office Box"),
  /** Division — X12 code "56". */
  DIVISION("56", "Division"),
  /** Department — X12 code "57". */
  DEPARTMENT("57", "Department"),
  /** Originating Company Number — X12 code "58". */
  ORIGINATING_CO("58", "Originating Company Number"),
  /** Receiving Company Number — X12 code "59". */
  RECEIVING_CO("59", "Receiving Company Number"),
  /** Holding Mortgagee Number — X12 code "61". */
  HOLDING_MORTGAGEE("61", "Holding Mortgagee Number"),
  /** Servicing Mortgagee Number — X12 code "62". */
  SERVICING_MORTGAGEE("62", "Servicing Mortgagee Number"),
  /** Servicer-holder Mortgagee Number — X12 code "63". */
  SERVICER_HOLDER("63", "Servicer-holder Mortgagee Number"),
  /** One Call Agency — X12 code "64". */
  ONE_CALL_AGENCY("64", "One Call Agency"),
  /** Integrated Postsecondary Education Data System (IPEDS) — X12 code "71". */
  IPEDS("71", "Integrated Postsecondary Education Data System (IPEDS)"),
  /** The College Board's Admission Testing Program (ATP) — X12 code "72". */
  ATP("72", "The College Board's Admission Testing Program (ATP)"),
  /** Federal Interagency Commission on Education (FICE) number — X12 code "73". */
  FICE("73", "Federal Interagency Commission on Education (FICE) number"),
  /**
   * American College Testing (ACT) list of postsecondary educational institutions — X12 code "74".
   */
  ACT("74", "American College Testing (ACT) list of postsecondary educational institutions"),
  /** State or Province Assigned Number — X12 code "75". */
  STATE_PROVINCE("75", "State or Province Assigned Number"),
  /** Local School District or Jurisdiction Number — X12 code "76". */
  LOCAL_SCHOOL("76", "Local School District or Jurisdiction Number"),
  /** National Center for Education Statistics (NCES) Common Core of Data (CCD) — X12 code "77". */
  NCES_CCD("77", "National Center for Education Statistics (NCES) Common Core of Data (CCD)"),
  /**
   * The College Board and ACT 6 digit code list of secondary educational institutions — X12 code
   * "78".
   */
  COLLEGE_BOARD_ACT(
      "78", "The College Board and ACT 6 digit code list of secondary educational institutions"),
  /** Classification of Instructional Programs (CIP) coding structure — X12 code "81". */
  CIP("81", "Classification of Instructional Programs (CIP) coding structure"),
  /** Higher Education General Information Survey (HEGIS) — X12 code "82". */
  HEGIS("82", "Higher Education General Information Survey (HEGIS)"),
  /** Congressional District — X12 code "83". */
  CONGRESSIONAL_DISTRICT("83", "Congressional District"),
  /** California Ethnic Subgroups Code Table — X12 code "90". */
  CA_ETHNIC("90", "California Ethnic Subgroups Code Table"),
  /** Assigned by Seller or Seller's Agent — X12 code "91". */
  SELLER_ASSIGNED("91", "Assigned by Seller or Seller's Agent"),
  /** Assigned by Buyer or Buyer's Agent — X12 code "92". */
  BUYER_ASSIGNED("92", "Assigned by Buyer or Buyer's Agent"),
  /** Code assigned by the organization originating the transaction set — X12 code "93". */
  ORIG_CODE("93", "Code assigned by the organization originating the transaction set"),
  /** Code assigned by the organization that is the ultimate destination — X12 code "94". */
  DEST_CODE("94", "Code assigned by the organization that is the ultimate destination"),
  /** Assigned By Transporter — X12 code "95". */
  TRANSPORTER("95", "Assigned By Transporter"),
  /** Assigned By Pipeline Operator — X12 code "96". */
  PIPELINE_OPERATOR("96", "Assigned By Pipeline Operator"),
  /** Receiver's Code — X12 code "97". */
  RECEIVERS_CODE("97", "Receiver's Code"),
  /** Purchasing Office — X12 code "98". */
  PURCHASING_OFFICE("98", "Purchasing Office"),
  /** Office of Workers Compensation Programs (OWCP) Agency Code — X12 code "99". */
  OWCP("99", "Office of Workers Compensation Programs (OWCP) Agency Code"),
  /** U.S. Customs Carrier Identification — X12 code "A". */
  CUSTOMS_CARRIER("A", "U.S. Customs Carrier Identification"),
  /** Approver ID — X12 code "A1". */
  APPROVER_ID("A1", "Approver ID"),
  /** Military Assistance Program Address Code (MAPAC) — X12 code "A2". */
  MAPAC("A2", "Military Assistance Program Address Code (MAPAC)"),
  /** Assigned by Third Party — X12 code "A3". */
  THIRD_PARTY("A3", "Assigned by Third Party"),
  /** Assigned by Clearinghouse — X12 code "A4". */
  CLEARINGHOUSE("A4", "Assigned by Clearinghouse"),
  /** Committee on Uniform Security Identification Procedures (CUSIP) Number — X12 code "A5". */
  CUSIP("A5", "Committee on Uniform Security Identification Procedures (CUSIP) Number"),
  /** Financial Identification Numbering System (FINS) Number — X12 code "A6". */
  FINS("A6", "Financial Identification Numbering System (FINS) Number"),
  /** Automated Commercial Environment Identification Code (ACEID) — X12 code "A7". */
  ACEID("A7", "Automated Commercial Environment Identification Code (ACEID)"),
  /** Postal Service Code — X12 code "AA". */
  POSTAL_CODE("AA", "Postal Service Code"),
  /** US Environmental Protection Agency (EPA) Identification Number — X12 code "AB". */
  EPA_ID("AB", "US Environmental Protection Agency (EPA) Identification Number"),
  /** Attachment Control Number — X12 code "AC". */
  ATTACHMENT_CONTROL("AC", "Attachment Control Number"),
  /** Blue Cross Blue Shield Association Plan Code — X12 code "AD". */
  BCBS_PLAN("AD", "Blue Cross Blue Shield Association Plan Code"),
  /** Alberta Energy Resources Conservation Board — X12 code "AE". */
  ALBERTA_ENERGY("AE", "Alberta Energy Resources Conservation Board"),
  /** Rental Location Identifier — X12 code "AF". */
  RENTAL_LOCATION("AF", "Rental Location Identifier"),
  /** Automotive Identifier for Canada Customs — X12 code "AI". */
  AUTOMOTIVE_CA("AI", "Automotive Identifier for Canada Customs"),
  /** Anesthesia License Number — X12 code "AL". */
  ANESTHESIA_LICENSE("AL", "Anesthesia License Number"),
  /** Alberta Petroleum Marketing Commission — X12 code "AP". */
  ALBERTA_PETROLEUM("AP", "Alberta Petroleum Marketing Commission"),
  /** British Columbia Ministry of Energy Mines and Petroleum Resources — X12 code "BC". */
  BC_MINISTRY("BC", "British Columbia Ministry of Energy Mines and Petroleum Resources"),
  /** Blue Cross Provider Number — X12 code "BD". */
  BLUE_CROSS("BD", "Blue Cross Provider Number"),
  /** Common Language Location Identification (CLLI) — X12 code "BE". */
  CLLI("BE", "Common Language Location Identification (CLLI)"),
  /** Badge Number — X12 code "BG". */
  BADGE("BG", "Badge Number"),
  /** Canada Customs &amp; Revenue Agency (CCRA) Business Number — X12 code "BN". */
  CCRA_BUSINESS("BN", "Canada Customs & Revenue Agency (CCRA) Business Number"),
  /** Benefit Plan — X12 code "BP". */
  BENEFIT_PLAN("BP", "Benefit Plan"),
  /** Blue Shield Provider Number — X12 code "BS". */
  BLUE_SHIELD("BS", "Blue Shield Provider Number"),
  /** Insured's Changed Unique Identification Number — X12 code "C". */
  CHANGED_INSURED("C", "Insured's Changed Unique Identification Number"),
  /** Insured or Subscriber — X12 code "C1". */
  INSURED("C1", "Insured or Subscriber"),
  /** Health Maintenance Organization (HMO) Provider Number — X12 code "C2". */
  HMO_PROVIDER("C2", "Health Maintenance Organization (HMO) Provider Number"),
  /** Customer Identification File — X12 code "C5". */
  CUSTOMER_ID("C5", "Customer Identification File"),
  /** Statistics Canada Canadian College Student Information System Course Codes — X12 code "CA". */
  STATS_CAN_COLLEGE_COURSE(
      "CA", "Statistics Canada Canadian College Student Information System Course Codes"),
  /**
   * Statistics Canada Canadian College Student Information System Institution Codes — X12 code
   * "CB".
   */
  STATS_CAN_COLLEGE_INST(
      "CB", "Statistics Canada Canadian College Student Information System Institution Codes"),
  /** Statistics Canada University Student Information System Curriculum Codes — X12 code "CC". */
  STATS_CAN_UNIV_CURR(
      "CC", "Statistics Canada University Student Information System Curriculum Codes"),
  /** Contract Division — X12 code "CD". */
  CONTRACT_DIVISION("CD", "Contract Division"),
  /** Bureau of the Census Filer Identification Code — X12 code "CE". */
  CENSUS_FILER("CE", "Bureau of the Census Filer Identification Code"),
  /** Canadian Financial Institution Routing Number — X12 code "CF". */
  CAN_FINANCIAL("CF", "Canadian Financial Institution Routing Number"),
  /**
   * CHAMPUS (Civilian Health and Medical Program of the Uniformed Services) Identification Number —
   * X12 code "CI".
   */
  CHAMPUS(
      "CI",
      "CHAMPUS (Civilian Health and Medical Program of the Uniformed Services) Identification Number"),
  /** Corrected Loan Number — X12 code "CL". */
  CORRECTED_LOAN("CL", "Corrected Loan Number"),
  /** U.S. Customs Service (USCS) Manufacturer Identifier (MID) — X12 code "CM". */
  CUSTOMS_MID("CM", "U.S. Customs Service (USCS) Manufacturer Identifier (MID)"),
  /**
   * National Center for Education Statistics (NCES) Course Classification System — X12 code "CN".
   */
  NCES_COURSE("CN", "National Center for Education Statistics (NCES) Course Classification System"),
  /** Canadian Petroleum Association — X12 code "CP". */
  CAN_PETROLEUM("CP", "Canadian Petroleum Association"),
  /** Credit Repository — X12 code "CR". */
  CREDIT_REPOSITORY("CR", "Credit Repository"),
  /** Statistics Canada University Student Information System University Codes — X12 code "CS". */
  STATS_CAN_UNIV("CS", "Statistics Canada University Student Information System University Codes"),
  /** Court Identification Code — X12 code "CT". */
  COURT_ID("CT", "Court Identification Code"),
  /** Census Schedule D — X12 code "D". */
  CENSUS_SCHEDULE_D("D", "Census Schedule D"),
  /** United States Department of Education Guarantor Identification Code — X12 code "DG". */
  DOE_GUARANTOR("DG", "United States Department of Education Guarantor Identification Code"),
  /** United States Department of Education Lender Identification Code — X12 code "DL". */
  DOE_LENDER("DL", "United States Department of Education Lender Identification Code"),
  /** Dentist License Number — X12 code "DN". */
  DENTIST_LICENSE("DN", "Dentist License Number"),
  /** Door — X12 code "DO". */
  DOOR("DO", "Door"),
  /** Data Processing Point — X12 code "DP". */
  DATA_PROCESSING("DP", "Data Processing Point"),
  /** Gas Industry Standards Board (GISB) Data Reference Number (DRN) — X12 code "DR". */
  GISB_DRN("DR", "Gas Industry Standards Board (GISB) Data Reference Number (DRN)"),
  /** United States Department of Education School Identification Code — X12 code "DS". */
  DOE_SCHOOL("DS", "United States Department of Education School Identification Code"),
  /** Hazard Insurance Policy Number — X12 code "E". */
  HAZARD_INSURANCE("E", "Hazard Insurance Policy Number"),
  /** ARI Electronic Commerce Location ID Code — X12 code "EC". */
  ARI_EC_LOCATION("EC", "ARI Electronic Commerce Location ID Code"),
  /** Theatre Number — X12 code "EH". */
  THEATRE("EH", "Theatre Number"),
  /** Employee Identification Number — X12 code "EI". */
  EMPLOYEE_ID("EI", "Employee Identification Number"),
  /** Elevator — X12 code "EL". */
  ELEVATOR("EL", "Elevator"),
  /** U.S. Environmental Protection Agency (EPA) — X12 code "EP". */
  EPA("EP", "U.S. Environmental Protection Agency (EPA)"),
  /** Insurance Company Assigned Identification Number — X12 code "EQ". */
  INS_CO_ASSIGNED("EQ", "Insurance Company Assigned Identification Number"),
  /** Mortgagee Assigned Identification Number — X12 code "ER". */
  MORTGAGEE_ASSIGNED("ER", "Mortgagee Assigned Identification Number"),
  /** Automated Export System (AES) Filer Identification Code — X12 code "ES". */
  AES_FILER("ES", "Automated Export System (AES) Filer Identification Code"),
  /**
   * Educational Testing Service List of International Postsecondary Institutions — X12 code "ET".
   */
  ETS_INTL("ET", "Educational Testing Service List of International Postsecondary Institutions"),
  /** Document Custodian Identification Number — X12 code "F". */
  DOC_CUSTODIAN("F", "Document Custodian Identification Number"),
  /** Facility Identification — X12 code "FA". */
  FACILITY_ID("FA", "Facility Identification"),
  /** Field Code — X12 code "FB". */
  FIELD_CODE("FB", "Field Code"),
  /** Federal Court Jurisdiction Identifier — X12 code "FC". */
  FED_COURT_JURISDICTION("FC", "Federal Court Jurisdiction Identifier"),
  /** Federal Court Divisional Office Number — X12 code "FD". */
  FED_COURT_DIVISION("FD", "Federal Court Divisional Office Number"),
  /** Facility Federal Identification Number — X12 code "FE". */
  FACILITY_FED_ID("FE", "Facility Federal Identification Number"),
  /** Federal Taxpayer's Identification Number — X12 code "FI". */
  FEDERAL_TIN("FI", "Federal Taxpayer's Identification Number"),
  /** Federal Jurisdiction — X12 code "FJ". */
  FED_JURISDICTION("FJ", "Federal Jurisdiction"),
  /** Floor — X12 code "FL". */
  FLOOR("FL", "Floor"),
  /**
   * U.S. Environmental Protection Agency (EPA) Laboratory Certification Identification — X12 code
   * "FN".
   */
  EPA_LAB_CERT(
      "FN", "U.S. Environmental Protection Agency (EPA) Laboratory Certification Identification"),
  /** Payee Identification Number — X12 code "G". */
  PAYEE_ID("G", "Payee Identification Number"),
  /** Primary Agent Identification — X12 code "GA". */
  PRIMARY_AGENT("GA", "Primary Agent Identification"),
  /** GAS*CODE — X12 code "GC". */
  GAS_CODE("GC", "GAS*CODE"),
  /** Centers for Medicare and Medicaid Services — X12 code "HC". */
  CMS("HC", "Centers for Medicare and Medicaid Services"),
  /** Health Insurance Claim (HIC) Number — X12 code "HN". */
  HIC("HN", "Health Insurance Claim (HIC) Number"),
  /** House (Canadian Grain Elevator) — X12 code "HS". */
  HOUSE_GRAIN("HS", "House (Canadian Grain Elevator)"),
  /** Secondary Marketing Investor Assigned Number — X12 code "I". */
  SEC_MARKETING("I", "Secondary Marketing Investor Assigned Number"),
  /** UCC EDI Communications ID (Comm ID) — X12 code "ID". */
  UCC_EDI_COMM_ID("ID", "UCC EDI Communications ID (Comm ID)"),
  /** Standard Unique Health Identifier for each Individual in the United States — X12 code "II". */
  HEALTH_ID("II", "Standard Unique Health Identifier for each Individual in the United States"),
  /**
   * U.S. Customs Carrier Initiative Program (CIP) Participant Identification Number — X12 code
   * "IP".
   */
  CIP_PARTICIPANT(
      "IP", "U.S. Customs Carrier Initiative Program (CIP) Participant Identification Number"),
  /** Mortgage Electronic Registration System Organization Identifier — X12 code "J". */
  MERS_ORG_ID("J", "Mortgage Electronic Registration System Organization Identifier"),
  /** Census Schedule K — X12 code "K". */
  CENSUS_SCHEDULE_K("K", "Census Schedule K"),
  /** Investor Assigned Identification Number — X12 code "L". */
  INVESTOR_ASSIGNED("L", "Investor Assigned Identification Number"),
  /** Agency Location Code (U.S. Government) — X12 code "LC". */
  AGENCY_LOCATION("LC", "Agency Location Code (U.S. Government)"),
  /** NISO Z39.53 Language Codes — X12 code "LD". */
  NISO_LANGUAGE("LD", "NISO Z39.53 Language Codes"),
  /** ISO 639 Language Codes — X12 code "LE". */
  ISO_LANGUAGE("LE", "ISO 639 Language Codes"),
  /** Labeler Identification Code (LIC) — X12 code "LI". */
  LABELER_ID("LI", "Labeler Identification Code (LIC)"),
  /** Loan Number — X12 code "LN". */
  LOAN_NUMBER("LN", "Loan Number"),
  /** Certificate Number — X12 code "M". */
  CERTIFICATE("M", "Certificate Number"),
  /** Disbursing Station — X12 code "M3". */
  DISBURSING_STATION("M3", "Disbursing Station"),
  /** Department of Defense Routing Identifier Code (RIC) — X12 code "M4". */
  DOD_ROUTING("M4", "Department of Defense Routing Identifier Code (RIC)"),
  /** Jurisdiction Code — X12 code "M5". */
  JURISDICTION_CODE("M5", "Jurisdiction Code"),
  /** Division Office Code — X12 code "M6". */
  DIVISION_OFFICE("M6", "Division Office Code"),
  /** Mail Stop — X12 code "MA". */
  MAIL_STOP("MA", "Mail Stop"),
  /** Medical Information Bureau — X12 code "MB". */
  MED_INFO_BUREAU("MB", "Medical Information Bureau"),
  /** Medicaid Provider Number — X12 code "MC". */
  MEDICAID_PROVIDER("MC", "Medicaid Provider Number"),
  /** Manitoba Department of Mines and Resources — X12 code "MD". */
  MANITOBA_MINES("MD", "Manitoba Department of Mines and Resources"),
  /** Member Identification Number — X12 code "MI". */
  MEMBER_ID("MI", "Member Identification Number"),
  /** Market — X12 code "MK". */
  MARKET("MK", "Market"),
  /** Multiple Listing Service Vendor - Multiple Listing Service Identification — X12 code "ML". */
  MLS_VENDOR("ML", "Multiple Listing Service Vendor - Multiple Listing Service Identification"),
  /** Mortgage Identification Number — X12 code "MN". */
  MORTGAGE_ID("MN", "Mortgage Identification Number"),
  /** Major Organizational Entity — X12 code "MO". */
  MAJOR_ORG_ENTITY("MO", "Major Organizational Entity"),
  /** Medicare Provider Number — X12 code "MP". */
  MEDICARE_PROVIDER("MP", "Medicare Provider Number"),
  /** Medicaid Recipient Identification Number — X12 code "MR". */
  MEDICAID_RECIPIENT("MR", "Medicaid Recipient Identification Number"),
  /** Insured's Unique Identification Number — X12 code "N". */
  INSURED_ID("N", "Insured's Unique Identification Number"),
  /** National Association of Realtors - Multiple Listing Service Identification — X12 code "NA". */
  NAR_MLS("NA", "National Association of Realtors - Multiple Listing Service Identification"),
  /** Mode Designator — X12 code "ND". */
  MODE_DESIGNATOR("ND", "Mode Designator"),
  /** National Association of Insurance Commissioners (NAIC) Identification — X12 code "NI". */
  NAIC("NI", "National Association of Insurance Commissioners (NAIC) Identification"),
  /** National Criminal Information Center Originating Agency — X12 code "NO". */
  NCIC("NO", "National Criminal Information Center Originating Agency"),
  /** Non Resident Alien Registration Number — X12 code "NR". */
  ALIEN_REG("NR", "Non Resident Alien Registration Number"),
  /** Occupation Code — X12 code "OC". */
  OCCUPATION_CODE("OC", "Occupation Code"),
  /** On-line Payment and Collection — X12 code "OP". */
  OPAC("OP", "On-line Payment and Collection"),
  /** Secondary Agent Identification — X12 code "PA". */
  SECONDARY_AGENT("PA", "Secondary Agent Identification"),
  /** Public Identification — X12 code "PB". */
  PUBLIC_ID("PB", "Public Identification"),
  /** Provider Commercial Number — X12 code "PC". */
  PROVIDER_COMMERCIAL("PC", "Provider Commercial Number"),
  /** Payor Identification — X12 code "PI". */
  PAYOR_ID("PI", "Payor Identification"),
  /** Pharmacy Processor Number — X12 code "PP". */
  PHARMACY_PROCESSOR("PP", "Pharmacy Processor Number"),
  /** Pier — X12 code "PR". */
  PIER("PR", "Pier"),
  /** Regulatory Agency Number — X12 code "RA". */
  REGULATORY_AGENCY("RA", "Regulatory Agency Number"),
  /** Real Estate Agent — X12 code "RB". */
  REAL_ESTATE_AGENT("RB", "Real Estate Agent"),
  /** Real Estate Company — X12 code "RC". */
  REAL_ESTATE_COMPANY("RC", "Real Estate Company"),
  /** Real Estate Broker Identification — X12 code "RD". */
  REAL_ESTATE_BROKER("RD", "Real Estate Broker Identification"),
  /** Real Estate License Number — X12 code "RE". */
  REAL_ESTATE_LICENSE("RE", "Real Estate License Number"),
  /** Office of Regulatory Information Systems (ORIS) Code — X12 code "RI". */
  ORIS_CODE("RI", "Office of Regulatory Information Systems (ORIS) Code"),
  /** Ramp — X12 code "RP". */
  RAMP("RP", "Ramp"),
  /** Railroad Track — X12 code "RT". */
  RAILROAD_TRACK("RT", "Railroad Track"),
  /** Title Insurance Policy Number — X12 code "S". */
  TITLE_INSURANCE("S", "Title Insurance Policy Number"),
  /** Tertiary Agent Identification — X12 code "SA". */
  TERTIARY_AGENT("SA", "Tertiary Agent Identification"),
  /** Social Insurance Number — X12 code "SB". */
  SIN("SB", "Social Insurance Number"),
  /** Saskatchewan Department of Energy Mines and Resources — X12 code "SD". */
  SASKATCHEWAN_ENERGY("SD", "Saskatchewan Department of Energy Mines and Resources"),
  /** Suffix Code — X12 code "SF". */
  SUFFIX_CODE("SF", "Suffix Code"),
  /** Standard Industry Code (SIC) — X12 code "SI". */
  SIC("SI", "Standard Industry Code (SIC)"),
  /** State or Province Jurisdiction — X12 code "SJ". */
  STATE_JURISDICTION("SJ", "State or Province Jurisdiction"),
  /** State/Provincial Lottery License Number — X12 code "SK". */
  STATE_LOTTERY("SK", "State/Provincial Lottery License Number"),
  /** State License Number — X12 code "SL". */
  STATE_LICENSE("SL", "State License Number"),
  /** Specialty License Number — X12 code "SP". */
  SPECIALTY_LICENSE("SP", "Specialty License Number"),
  /** State/Province License Tag — X12 code "ST". */
  STATE_LICENSE_TAG("ST", "State/Province License Tag"),
  /** Service Provider Number — X12 code "SV". */
  SERVICE_PROVIDER("SV", "Service Provider Number"),
  /**
   * Society for Worldwide Interbank Financial Telecommunications (SWIFT) Address — X12 code "SW".
   */
  SWIFT_ADDRESS(
      "SW", "Society for Worldwide Interbank Financial Telecommunications (SWIFT) Address"),
  /** Taxpayer ID Number — X12 code "TA". */
  TIN("TA", "Taxpayer ID Number"),
  /** Internal Revenue Service Terminal Code — X12 code "TC". */
  IRS_TERMINAL("TC", "Internal Revenue Service Terminal Code"),
  /** Transport4 Location Code — X12 code "TL". */
  TRANSPORT4_LOCATION("TL", "Transport4 Location Code"),
  /** Transport4 Shipper Code — X12 code "TS". */
  TRANSPORT4_SHIPPER("TS", "Transport4 Shipper Code"),
  /** Department Code — X12 code "TZ". */
  DEPARTMENT_CODE("TZ", "Department Code"),
  /** Consumer Credit Identification Number — X12 code "UC". */
  CONSUMER_CREDIT("UC", "Consumer Credit Identification Number"),
  /** Unit Identification Code — X12 code "UI". */
  UNIT_ID_CODE("UI", "Unit Identification Code"),
  /** Global Location Number (GLN) — X12 code "UL". */
  GLN("UL", "Global Location Number (GLN)"),
  /** Unique Physician Identification Number (UPIN) — X12 code "UP". */
  UPIN("UP", "Unique Physician Identification Number (UPIN)"),
  /** Uniform Resource Locator (URL) — X12 code "UR". */
  URL("UR", "Uniform Resource Locator (URL)"),
  /** Unique Supplier Identification Number (USIN) — X12 code "US". */
  USIN("US", "Unique Supplier Identification Number (USIN)"),
  /** Unit — X12 code "UT". */
  UNIT("UT", "Unit"),
  /** Wine Region Code — X12 code "WR". */
  WINE_REGION("WR", "Wine Region Code"),
  /** Education Language Codes — X12 code "WS". */
  EDU_LANGUAGE("WS", "Education Language Codes"),
  /** National Center for Education Statistics Unit Identification Number — X12 code "X1". */
  NCES_UNIT("X1", "National Center for Education Statistics Unit Identification Number"),
  /** Centers for Medicare and Medicaid Services PlanID — X12 code "XV". */
  CMS_PLANID("XV", "Centers for Medicare and Medicaid Services PlanID"),
  /** Centers for Medicare and Medicaid Services National Provider Identifier — X12 code "XX". */
  CMS_NPI("XX", "Centers for Medicare and Medicaid Services National Provider Identifier"),
  /** District Assigned Number — X12 code "XY". */
  DISTRICT_ASSIGNED("XY", "District Assigned Number"),
  /** Contractor Establishment Code — X12 code "ZC". */
  CONTRACTOR_ESTAB("ZC", "Contractor Establishment Code"),
  /** Zone — X12 code "ZN". */
  ZONE("ZN", "Zone"),
  /** Temporary Identification Number — X12 code "ZY". */
  TEMP_ID("ZY", "Temporary Identification Number"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<IdentificationCodeQualifier> LOOKUP;

  static {
    // Include additional common terms users might enter
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("npi", CMS_NPI),
                Map.entry("national provider identifier", CMS_NPI),
                Map.entry("upin", UPIN),
                Map.entry("physician id", UPIN),
                Map.entry("website", URL),
                Map.entry("web address", URL)));
  }

  IdentificationCodeQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an IdentificationCodeQualifier instance from any input string. Matches against codes,
   * names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching IdentificationCodeQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static IdentificationCodeQualifier fromString(String input) {
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
