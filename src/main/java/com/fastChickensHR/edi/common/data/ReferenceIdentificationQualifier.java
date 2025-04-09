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
 * Enumeration representing Reference Identification Qualifier codes used in the EDI
 * transactions, specifically in REF segments to indicate the type of reference.
 */
@Getter
public enum ReferenceIdentificationQualifier implements EdiCodeEnum {
    CONTRACTING_DISTRICT_NUMBER("00", "Contracting District Number"),
    SUPERVISORY_APPRAISER_CERTIFICATION_NUMBER("0A", "Supervisory Appraiser Certification Number"),
    STATE_LICENSE_NUMBER("0B", "State License Number"),
    LINKING_IDENTIFIER("0C", "Linking Identifier"),
    SUBJECT_PROPERTY_VERIFICATION_SOURCE("0D", "Subject Property Verification Source"),
    SUBJECT_PROPERTY_REFERENCE_NUMBER("0E", "Subject Property Reference Number"),
    SUBSCRIBER_NUMBER("0F", "Subscriber Number"),
    REVIEWER_FILE_NUMBER("0G", "Reviewer File Number"),
    COMPARABLE_PROPERTY_PENDING_SALE_REFERENCE_NUMBER("0H", "Comparable Property Pending Sale Reference Number"),
    COMPARABLE_PROPERTY_SALE_REFERENCE_NUMBER("0I", "Comparable Property Sale Reference Number"),
    SUBJECT_PROPERTY_NON_SALE_REFERENCE_NUMBER("0J", "Subject Property Non-Sale Reference Number"),
    POLICY_FORM_IDENTIFYING_NUMBER("0K", "Policy Form Identifying Number"),
    REFERENCED_BY("0L", "Referenced By"),
    MORTGAGE_IDENTIFICATION_NUMBER("0M", "Mortgage Identification Number"),
    ATTACHED_TO("0N", "Attached To"),
    REAL_ESTATE_OWNED_PROPERTY_IDENTIFIER("0P", "Real Estate Owned Property Identifier"),
    ABA_TRANSIT_ROUTING_NUMBER("01", "American Bankers Assoc. (ABA) Transit/Routing Number (Including Check Digit, 9 Digits)"),
    BLUE_CROSS_PROVIDER_NUMBER("1A", "Blue Cross Provider Number"),
    CATALOG_OF_FEDERAL_DOMESTIC_ASSISTANCE("01A", "Catalog of Federal Domestic Assistance"),
    BLUE_SHIELD_PROVIDER_NUMBER("1B", "Blue Shield Provider Number"),
    UNION_AGREEMENT("01B", "Union Agreement"),
    MEDICARE_PROVIDER_NUMBER("1C", "Medicare Provider Number"),
    MILSTRIP_DOCUMENT_NUMBER("01C", "Military Standard Requisitioning and Issue Procedures (MILSTRIP) Document Number"),
    MEDICAID_PROVIDER_NUMBER("1D", "Medicaid Provider Number"),
    FEDSTRIP_DOCUMENT_NUMBER("01D", "Federal Standard Requisitioning and Issue Procedures (FEDSTRIP) Document Number"),
    DENTIST_LICENSE_NUMBER("1E", "Dentist License Number"),
    FSS_ITEM_NUMBER("01E", "Federal Supply Schedule Special (FSS) Item Number"),
    ANESTHESIA_LICENSE_NUMBER("1F", "Anesthesia License Number"),
    PROVIDER_UPIN_NUMBER("1G", "Provider UPIN Number"),
    PAYMENT_RELATED_CLAUSE("01G", "Payment Related Clause"),
    TRICARE_IDENTIFICATION_NUMBER("1H", "TRICARE Identification Number"),
    SPECIAL_PRICE_AUTHORIZATION_NUMBER("01H", "Special Price Authorization Number"),
    DODIC("1I", "Department of Defense Identification Code (DoDIC)"),
    FACILITY_ID_NUMBER("1J", "Facility ID Number"),
    PAYERS_CLAIM_NUMBER("1K", "Payer's Claim Number"),
    GROUP_OR_POLICY_NUMBER("1L", "Group or Policy Number"),
    PPO_SITE_NUMBER("1M", "Preferred Provider Organization Site Number"),
    DRG_NUMBER("1N", "Diagnosis Related Group (DRG) Number"),
    CONSOLIDATION_SHIPMENT_NUMBER("1O", "Consolidation Shipment Number"),
    ACCESSORIAL_STATUS_CODE("1P", "Accessorial Status Code"),
    ERROR_IDENTIFICATION_CODE("1Q", "Error Identification Code"),
    STORAGE_INFORMATION_CODE("1R", "Storage Information Code"),
    APG_NUMBER("1S", "Ambulatory Patient Group (APG) Number"),
    RUG_NUMBER("1T", "Resource Utilization Group (RUG) Number"),
    PAY_GRADE("1U", "Pay Grade"),
    RELATED_VENDOR_ORDER_NUMBER("1V", "Related Vendor Order Number"),
    MEMBER_IDENTIFICATION_NUMBER("1W", "Member Identification Number"),
    CREDIT_OR_DEBIT_ADJUSTMENT_NUMBER("1X", "Credit or Debit Adjustment Number"),
    REPAIR_ACTION_NUMBER("1Y", "Repair Action Number"),
    FINANCIAL_DETAIL_CODE("1Z", "Financial Detail Code"),
    SWIFT_IDENTIFICATION("02", "Society for Worldwide Interbank Financial Telecommunication (S.W.I.F.T.) Identification (8 or 11 Characters)"),
    IMPORT_LICENSE_NUMBER("2A", "Import License Number"),
    TERMINAL_RELEASE_ORDER_NUMBER("2B", "Terminal Release Order Number"),
    LONG_TERM_DISABILITY_POLICY_NUMBER("2C", "Long-term Disability Policy Number"),
    AERNO("2D", "Aeronautical Equipment Reference Number (AERNO)"),
    FOREIGN_MILITARY_SALES_CASE_NUMBER("2E", "Foreign Military Sales Case Number"),
    CONSOLIDATED_INVOICE_NUMBER("2F", "Consolidated Invoice Number"),
    AMENDMENT("2G", "Amendment"),
    ASSIGNED_BY_TRANSACTION_SET_SENDER("2H", "Assigned by transaction set sender"),
    TRACKING_NUMBER("2I", "Tracking Number"),
    FLOOR_NUMBER("2J", "Floor Number"),
    FDA_PRODUCT_TYPE("2K", "Food and Drug Administration (FDA) Product Type"),
    AAR_RAILWAY_ACCOUNTING_RULES("2L", "Association of American Railroads (AAR) Railway Accounting Rules"),
    FCC_IDENTIFIER("2M", "Federal Communications Commission (FCC) Identifier"),
    FCC_TRADE_BRAND_IDENTIFIER("2N", "Federal Communications Commission (FCC) Trade/Brand Identifier"),
    OSHA_CLAIM_NUMBER("2O", "Occupational Safety and Health Administration (OSHA) Claim Number"),
    SUBDIVISION_IDENTIFIER("2P", "Subdivision Identifier"),
    FDA_ACCESSION_NUMBER("2Q", "Food and Drug Administration (FDA) Accession Number"),
    COUPON_REDEMPTION_NUMBER("2R", "Coupon Redemption Number"),
    CATALOG("2S", "Catalog"),
    SUB_SUBHOUSE_BILL_OF_LADING("2T", "Sub-subhouse Bill of Lading"),
    PAYER_IDENTIFICATION_NUMBER("2U", "Payer Identification Number"),
    ACRN("2V", "Special Government Accounting Classification Reference Number (ACRN)"),
    CHANGE_ORDER_AUTHORITY("2W", "Change Order Authority"),
    SUPPLEMENTAL_AGREEMENT_AUTHORITY("2X", "Supplemental Agreement Authority"),
    INTERNAL_ORDER_NUMBER("IL", "Internal Order Number"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined"),
    MASTER_POLICY_NUMBER("38", "Master Policy Number"),
    DEPARTMENT_NUMBER("DX", "Department/Agency Number"),
    OPERATOR_IDENTIFICATION_NUMBER("OF", "Operator Identification Number"),
    WAGE_DETERMINATION("2Y", "Wage Determination");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<ReferenceIdentificationQualifier> LOOKUP;

    static {
        // Build lookup map with common variations
        LOOKUP = new EdiEnumLookup<>(
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
                        Map.entry("claim number", PAYERS_CLAIM_NUMBER)
                )
        );
    }

    ReferenceIdentificationQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a ReferenceIdentificationQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching ReferenceIdentificationQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static ReferenceIdentificationQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}