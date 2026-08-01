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
 * Code values for the X12 Reference Identification Qualifier (data element 128), which states the
 * meaning of an accompanying reference identifier. In the X12 834 (005010X220A1) it appears as
 * REF01 to type reference numbers such as subscriber, group/policy, and member identifiers.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum ReferenceIdentificationQualifier implements EdiCodeEnum {
  /** Contracting District Number — X12 code "00". */
  CONTRACTING_DISTRICT_NUMBER("00", "Contracting District Number"),
  /** Supervisory Appraiser Certification Number — X12 code "0A". */
  SUPERVISORY_APPRAISER_CERTIFICATION_NUMBER("0A", "Supervisory Appraiser Certification Number"),
  /** State License Number — X12 code "0B". */
  STATE_LICENSE_NUMBER("0B", "State License Number"),
  /** Linking Identifier — X12 code "0C". */
  LINKING_IDENTIFIER("0C", "Linking Identifier"),
  /** Subject Property Verification Source — X12 code "0D". */
  SUBJECT_PROPERTY_VERIFICATION_SOURCE("0D", "Subject Property Verification Source"),
  /** Subject Property Reference Number — X12 code "0E". */
  SUBJECT_PROPERTY_REFERENCE_NUMBER("0E", "Subject Property Reference Number"),
  /** Subscriber Number — X12 code "0F". */
  SUBSCRIBER_NUMBER("0F", "Subscriber Number"),
  /** Reviewer File Number — X12 code "0G". */
  REVIEWER_FILE_NUMBER("0G", "Reviewer File Number"),
  /** Comparable Property Pending Sale Reference Number — X12 code "0H". */
  COMPARABLE_PROPERTY_PENDING_SALE_REFERENCE_NUMBER(
      "0H", "Comparable Property Pending Sale Reference Number"),
  /** Comparable Property Sale Reference Number — X12 code "0I". */
  COMPARABLE_PROPERTY_SALE_REFERENCE_NUMBER("0I", "Comparable Property Sale Reference Number"),
  /** Subject Property Non-Sale Reference Number — X12 code "0J". */
  SUBJECT_PROPERTY_NON_SALE_REFERENCE_NUMBER("0J", "Subject Property Non-Sale Reference Number"),
  /** Policy Form Identifying Number — X12 code "0K". */
  POLICY_FORM_IDENTIFYING_NUMBER("0K", "Policy Form Identifying Number"),
  /** Referenced By — X12 code "0L". */
  REFERENCED_BY("0L", "Referenced By"),
  /** Mortgage Identification Number — X12 code "0M". */
  MORTGAGE_IDENTIFICATION_NUMBER("0M", "Mortgage Identification Number"),
  /** Attached To — X12 code "0N". */
  ATTACHED_TO("0N", "Attached To"),
  /** Real Estate Owned Property Identifier — X12 code "0P". */
  REAL_ESTATE_OWNED_PROPERTY_IDENTIFIER("0P", "Real Estate Owned Property Identifier"),
  /**
   * American Bankers Assoc. (ABA) Transit/Routing Number (Including Check Digit, 9 Digits) — X12
   * code "01".
   */
  ABA_TRANSIT_ROUTING_NUMBER(
      "01",
      "American Bankers Assoc. (ABA) Transit/Routing Number (Including Check Digit, 9 Digits)"),
  /** Blue Cross Provider Number — X12 code "1A". */
  BLUE_CROSS_PROVIDER_NUMBER("1A", "Blue Cross Provider Number"),
  /** Catalog of Federal Domestic Assistance — X12 code "01A". */
  CATALOG_OF_FEDERAL_DOMESTIC_ASSISTANCE("01A", "Catalog of Federal Domestic Assistance"),
  /** Blue Shield Provider Number — X12 code "1B". */
  BLUE_SHIELD_PROVIDER_NUMBER("1B", "Blue Shield Provider Number"),
  /** Union Agreement — X12 code "01B". */
  UNION_AGREEMENT("01B", "Union Agreement"),
  /** Medicare Provider Number — X12 code "1C". */
  MEDICARE_PROVIDER_NUMBER("1C", "Medicare Provider Number"),
  /**
   * Military Standard Requisitioning and Issue Procedures (MILSTRIP) Document Number — X12 code
   * "01C".
   */
  MILSTRIP_DOCUMENT_NUMBER(
      "01C", "Military Standard Requisitioning and Issue Procedures (MILSTRIP) Document Number"),
  /** Medicaid Provider Number — X12 code "1D". */
  MEDICAID_PROVIDER_NUMBER("1D", "Medicaid Provider Number"),
  /**
   * Federal Standard Requisitioning and Issue Procedures (FEDSTRIP) Document Number — X12 code
   * "01D".
   */
  FEDSTRIP_DOCUMENT_NUMBER(
      "01D", "Federal Standard Requisitioning and Issue Procedures (FEDSTRIP) Document Number"),
  /** Dentist License Number — X12 code "1E". */
  DENTIST_LICENSE_NUMBER("1E", "Dentist License Number"),
  /** Federal Supply Schedule Special (FSS) Item Number — X12 code "01E". */
  FSS_ITEM_NUMBER("01E", "Federal Supply Schedule Special (FSS) Item Number"),
  /** Anesthesia License Number — X12 code "1F". */
  ANESTHESIA_LICENSE_NUMBER("1F", "Anesthesia License Number"),
  /** Provider UPIN Number — X12 code "1G". */
  PROVIDER_UPIN_NUMBER("1G", "Provider UPIN Number"),
  /** Payment Related Clause — X12 code "01G". */
  PAYMENT_RELATED_CLAUSE("01G", "Payment Related Clause"),
  /** TRICARE Identification Number — X12 code "1H". */
  TRICARE_IDENTIFICATION_NUMBER("1H", "TRICARE Identification Number"),
  /** Special Price Authorization Number — X12 code "01H". */
  SPECIAL_PRICE_AUTHORIZATION_NUMBER("01H", "Special Price Authorization Number"),
  /** Department of Defense Identification Code (DoDIC) — X12 code "1I". */
  DODIC("1I", "Department of Defense Identification Code (DoDIC)"),
  /** Facility ID Number — X12 code "1J". */
  FACILITY_ID_NUMBER("1J", "Facility ID Number"),
  /** Payer's Claim Number — X12 code "1K". */
  PAYERS_CLAIM_NUMBER("1K", "Payer's Claim Number"),
  /** Group or Policy Number — X12 code "1L". */
  GROUP_OR_POLICY_NUMBER("1L", "Group or Policy Number"),
  /** Preferred Provider Organization Site Number — X12 code "1M". */
  PPO_SITE_NUMBER("1M", "Preferred Provider Organization Site Number"),
  /** Diagnosis Related Group (DRG) Number — X12 code "1N". */
  DRG_NUMBER("1N", "Diagnosis Related Group (DRG) Number"),
  /** Consolidation Shipment Number — X12 code "1O". */
  CONSOLIDATION_SHIPMENT_NUMBER("1O", "Consolidation Shipment Number"),
  /** Accessorial Status Code — X12 code "1P". */
  ACCESSORIAL_STATUS_CODE("1P", "Accessorial Status Code"),
  /** Error Identification Code — X12 code "1Q". */
  ERROR_IDENTIFICATION_CODE("1Q", "Error Identification Code"),
  /** Storage Information Code — X12 code "1R". */
  STORAGE_INFORMATION_CODE("1R", "Storage Information Code"),
  /** Ambulatory Patient Group (APG) Number — X12 code "1S". */
  APG_NUMBER("1S", "Ambulatory Patient Group (APG) Number"),
  /** Resource Utilization Group (RUG) Number — X12 code "1T". */
  RUG_NUMBER("1T", "Resource Utilization Group (RUG) Number"),
  /** Pay Grade — X12 code "1U". */
  PAY_GRADE("1U", "Pay Grade"),
  /** Related Vendor Order Number — X12 code "1V". */
  RELATED_VENDOR_ORDER_NUMBER("1V", "Related Vendor Order Number"),
  /** Member Identification Number — X12 code "1W". */
  MEMBER_IDENTIFICATION_NUMBER("1W", "Member Identification Number"),
  /** Credit or Debit Adjustment Number — X12 code "1X". */
  CREDIT_OR_DEBIT_ADJUSTMENT_NUMBER("1X", "Credit or Debit Adjustment Number"),
  /** Repair Action Number — X12 code "1Y". */
  REPAIR_ACTION_NUMBER("1Y", "Repair Action Number"),
  /** Financial Detail Code — X12 code "1Z". */
  FINANCIAL_DETAIL_CODE("1Z", "Financial Detail Code"),
  /**
   * Society for Worldwide Interbank Financial Telecommunication (S.W.I.F.T.) Identification (8 or
   * 11 Characters) — X12 code "02".
   */
  SWIFT_IDENTIFICATION(
      "02",
      "Society for Worldwide Interbank Financial Telecommunication (S.W.I.F.T.) Identification (8 or 11 Characters)"),
  /** Import License Number — X12 code "2A". */
  IMPORT_LICENSE_NUMBER("2A", "Import License Number"),
  /** Terminal Release Order Number — X12 code "2B". */
  TERMINAL_RELEASE_ORDER_NUMBER("2B", "Terminal Release Order Number"),
  /** Long-term Disability Policy Number — X12 code "2C". */
  LONG_TERM_DISABILITY_POLICY_NUMBER("2C", "Long-term Disability Policy Number"),
  /** Aeronautical Equipment Reference Number (AERNO) — X12 code "2D". */
  AERNO("2D", "Aeronautical Equipment Reference Number (AERNO)"),
  /** Foreign Military Sales Case Number — X12 code "2E". */
  FOREIGN_MILITARY_SALES_CASE_NUMBER("2E", "Foreign Military Sales Case Number"),
  /** Consolidated Invoice Number — X12 code "2F". */
  CONSOLIDATED_INVOICE_NUMBER("2F", "Consolidated Invoice Number"),
  /** Amendment — X12 code "2G". */
  AMENDMENT("2G", "Amendment"),
  /** Assigned by transaction set sender — X12 code "2H". */
  ASSIGNED_BY_TRANSACTION_SET_SENDER("2H", "Assigned by transaction set sender"),
  /** Tracking Number — X12 code "2I". */
  TRACKING_NUMBER("2I", "Tracking Number"),
  /** Floor Number — X12 code "2J". */
  FLOOR_NUMBER("2J", "Floor Number"),
  /** Food and Drug Administration (FDA) Product Type — X12 code "2K". */
  FDA_PRODUCT_TYPE("2K", "Food and Drug Administration (FDA) Product Type"),
  /** Association of American Railroads (AAR) Railway Accounting Rules — X12 code "2L". */
  AAR_RAILWAY_ACCOUNTING_RULES(
      "2L", "Association of American Railroads (AAR) Railway Accounting Rules"),
  /** Federal Communications Commission (FCC) Identifier — X12 code "2M". */
  FCC_IDENTIFIER("2M", "Federal Communications Commission (FCC) Identifier"),
  /** Federal Communications Commission (FCC) Trade/Brand Identifier — X12 code "2N". */
  FCC_TRADE_BRAND_IDENTIFIER(
      "2N", "Federal Communications Commission (FCC) Trade/Brand Identifier"),
  /** Occupational Safety and Health Administration (OSHA) Claim Number — X12 code "2O". */
  OSHA_CLAIM_NUMBER("2O", "Occupational Safety and Health Administration (OSHA) Claim Number"),
  /** Subdivision Identifier — X12 code "2P". */
  SUBDIVISION_IDENTIFIER("2P", "Subdivision Identifier"),
  /** Food and Drug Administration (FDA) Accession Number — X12 code "2Q". */
  FDA_ACCESSION_NUMBER("2Q", "Food and Drug Administration (FDA) Accession Number"),
  /** Coupon Redemption Number — X12 code "2R". */
  COUPON_REDEMPTION_NUMBER("2R", "Coupon Redemption Number"),
  /** Catalog — X12 code "2S". */
  CATALOG("2S", "Catalog"),
  /** Sub-subhouse Bill of Lading — X12 code "2T". */
  SUB_SUBHOUSE_BILL_OF_LADING("2T", "Sub-subhouse Bill of Lading"),
  /** Payer Identification Number — X12 code "2U". */
  PAYER_IDENTIFICATION_NUMBER("2U", "Payer Identification Number"),
  /** Special Government Accounting Classification Reference Number (ACRN) — X12 code "2V". */
  ACRN("2V", "Special Government Accounting Classification Reference Number (ACRN)"),
  /** Change Order Authority — X12 code "2W". */
  CHANGE_ORDER_AUTHORITY("2W", "Change Order Authority"),
  /** Supplemental Agreement Authority — X12 code "2X". */
  SUPPLEMENTAL_AGREEMENT_AUTHORITY("2X", "Supplemental Agreement Authority"),
  /** Internal Order Number — X12 code "IL". */
  INTERNAL_ORDER_NUMBER("IL", "Internal Order Number"),
  /** Mutually Defined — X12 code "ZZ". */
  MUTUALLY_DEFINED("ZZ", "Mutually Defined"),
  /** Master Policy Number — X12 code "38". */
  MASTER_POLICY_NUMBER("38", "Master Policy Number"),
  /** Department/Agency Number — X12 code "DX". */
  DEPARTMENT_NUMBER("DX", "Department/Agency Number"),
  /** Client Reporting Category — X12 code "17". */
  CLIENT_REPORTING_CATEGORY("17", "Client Reporting Category"),
  /** Client Number — X12 code "23". */
  CLIENT_NUMBER("23", "Client Number"),
  /** Case Number — X12 code "3H". */
  CASE_NUMBER("3H", "Case Number"),
  /** Personal Identification Number (PIN) — X12 code "4A". */
  PERSONAL_IDENTIFICATION_NUMBER("4A", "Personal Identification Number (PIN)"),
  /** Cross Reference Number — X12 code "6O". */
  CROSS_REFERENCE_NUMBER("6O", "Cross Reference Number"),
  /** Group Number — X12 code "6P". */
  GROUP_NUMBER("6P", "Group Number"),
  /** Personal ID Number — X12 code "ABB". */
  PERSONAL_ID_NUMBER("ABB", "Personal ID Number"),
  /** National Council for Prescription Drug Programs Pharmacy Number — X12 code "D3". */
  NCPDP_PHARMACY_NUMBER("D3", "National Council for Prescription Drug Programs Pharmacy Number"),
  /** Health Insurance Claim (HIC) Number — X12 code "F6". */
  HEALTH_INSURANCE_CLAIM_NUMBER("F6", "Health Insurance Claim (HIC) Number"),
  /** Position Code — X12 code "P5". */
  POSITION_CODE("P5", "Position Code"),
  /** Prior Identifier Number — X12 code "Q4". */
  PRIOR_IDENTIFIER_NUMBER("Q4", "Prior Identifier Number"),
  /** Unit Number — X12 code "QQ". */
  UNIT_NUMBER("QQ", "Unit Number"),
  /** Payment Category — X12 code "9V". */
  PAYMENT_CATEGORY("9V", "Payment Category"),
  /** Class of Contract Code — X12 code "CE". */
  CLASS_OF_CONTRACT_CODE("CE", "Class of Contract Code"),
  /** Service Contract (Coverage) Number — X12 code "E8". */
  SERVICE_CONTRACT_NUMBER("E8", "Service Contract (Coverage) Number"),
  /** Medical Assistance Category — X12 code "M7". */
  MEDICAL_ASSISTANCE_CATEGORY("M7", "Medical Assistance Category"),
  /** Rate code number — X12 code "RB". */
  RATE_CODE_NUMBER("RB", "Rate code number"),
  /** Internal Control Number — X12 code "X9". */
  INTERNAL_CONTROL_NUMBER("X9", "Internal Control Number"),
  /** Social Security Number — X12 code "34". */
  SOCIAL_SECURITY_NUMBER("34", "Social Security Number"),
  /** Wage Determination — X12 code "2Y". */
  WAGE_DETERMINATION("2Y", "Wage Determination");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<ReferenceIdentificationQualifier> LOOKUP;

  static {
    // Build lookup map with common variations
    LOOKUP =
        new EdiEnumLookup<>(
            ReferenceIdentificationQualifier.class,
            "Reference Identification Qualifier",
            Map.ofEntries(
                // Common lookup mappings for frequently used codes
                Map.entry("member id", MEMBER_IDENTIFICATION_NUMBER),
                Map.entry("subscriber id", SUBSCRIBER_NUMBER),
                Map.entry("member number", MEMBER_IDENTIFICATION_NUMBER),
                Map.entry("subscriber", SUBSCRIBER_NUMBER),
                Map.entry("tracking", TRACKING_NUMBER),
                Map.entry("state license", STATE_LICENSE_NUMBER),
                Map.entry("medicare", MEDICARE_PROVIDER_NUMBER),
                Map.entry("medicaid", MEDICAID_PROVIDER_NUMBER),
                Map.entry("policy number", GROUP_OR_POLICY_NUMBER),
                Map.entry("mortgage id", MORTGAGE_IDENTIFICATION_NUMBER),
                Map.entry("tracking id", TRACKING_NUMBER),
                Map.entry("claim number", PAYERS_CLAIM_NUMBER),
                Map.entry("ssn", SOCIAL_SECURITY_NUMBER),
                Map.entry("social security number", SOCIAL_SECURITY_NUMBER)));
  }

  ReferenceIdentificationQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a ReferenceIdentificationQualifier instance from any input string. Matches against codes,
   * names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching ReferenceIdentificationQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static ReferenceIdentificationQualifier fromString(String input) {
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
