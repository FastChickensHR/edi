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
 * Enumeration representing Functional Identifier Codes used in the EDI GS segment,
 * specifically in GS01 element. These codes identify the family or business area
 * of the transaction sets in a functional group.
 */
@Getter
public enum FunctionalIdentifierCode implements EdiCodeEnum {
    ACCOUNT_ANALYSIS("AA", "Account Analysis (822)"),
    LOGISTICS_SERVICE_REQUEST("AB", "Logistics Service Request (219)"),
    ASSOCIATED_DATA("AC", "Associated Data (102)"),
    LIFE_ANNUITY_APPLICATION("AD", "Individual Life, Annuity and Disability Application (267)"),
    PREMIUM_AUDIT("AE", "Premium Audit Request and Return (187)"),
    EDUCATIONAL_ADMISSION("AF", "Application for Admission to Educational Institutions (189)"),
    APPLICATION_ADVICE("AG", "Application Advice (824)"),
    LOGISTICS_SERVICE_RESPONSE("AH", "Logistics Service Response (220)"),
    AUTOMOTIVE_INSPECTION("AI", "Automotive Inspection Detail (928)"),
    TRANSCRIPT_ACKNOWLEDGMENT("AK", "Student Educational Record (Transcript) Acknowledgment (131)"),
    SET_CANCELLATION("AL", "Set Cancellation (998)"),
    ITEM_INFORMATION_REQUEST("AM", "Item Information Request (893)"),
    RETURN_MERCHANDISE_AUTH("AN", "Return Merchandise Authorization and Notification (180)"),
    INCOME_ASSET_OFFSET("AO", "Income or Asset Offset (521)"),
    ABANDONED_PROPERTY("AP", "Abandoned Property Filings (103)"),
    CUSTOMS_MANIFEST("AQ", "U.S. Customs Manifest (309)"),
    WAREHOUSE_STOCK_TRANSFER("AR", "Warehouse Stock Transfer Shipment Advice (943)"),
    TRANSPORTATION_APPOINTMENT("AS", "Transportation Appointment Schedule Information (163)"),
    ANIMAL_TOXICOLOGICAL_DATA("AT", "Animal Toxicological Data (249)"),
    CUSTOMS_STATUS_INFO("AU", "U.S. Customs Status Information (350)"),
    CUSTOMS_GENERAL_ORDER("AV", "U.S. Customs Carrier General Order Status (352)"),
    WAREHOUSE_INVENTORY_ADJ("AW", "Warehouse Inventory Adjustment Advice (947)"),
    CUSTOMS_EVENTS_ADVISORY("AX", "U.S. Customs Events Advisory Details (353)"),
    CUSTOMS_MANIFEST_ARCHIVE("AY", "U.S. Customs Automated Manifest Archive Status (354)"),
    CUSTOMS_ACCEPTANCE("AZ", "U.S. Customs Acceptance/Rejection (355)"),
    CUSTOMS_PERMIT_TRANSFER("BA", "U.S. Customs Permit to Transfer Request (356)"),
    CUSTOMS_IN_BOND("BB", "U.S. Customs In-Bond Information (357)"),
    BUSINESS_CREDIT_REPORT("BC", "Business Credit Report (155)"),
    CUSTOMS_CONSIST_INFO("BD", "U.S. Customs Consist Information (358)"),
    BENEFIT_ENROLLMENT("BE", "Benefit Enrollment and Maintenance (834)"),
    BUSINESS_ENTITY_FILINGS("BF", "Business Entity Filings (105)"),
    MOTOR_CARRIER_BILL_LADING("BL", "Motor Carrier Bill of Lading (211)"),
    SHIPMENT_BILLING_NOTICE("BS", "Shipment and Billing Notice (857)"),
    PURCHASE_ORDER_CHANGE("CA", "Purchase Order Change Acknowledgment/Request - Seller Initiated (865)"),
    UNEMPLOYMENT_INSURANCE("CB", "Unemployment Insurance Tax Claim or Charge Information (153)"),
    CLAUSES_PROVISIONS("CC", "Clauses and Provisions (504)"),
    CREDIT_DEBIT_ADJUSTMENT("CD", "Credit/Debit Adjustment (812)"),
    CARTAGE_WORK_ASSIGNMENT("CE", "Cartage Work Assignment (222)"),
    CORPORATE_FINANCIAL("CF", "Corporate Financial Adjustment Information (844 and 849)"),
    CAR_HANDLING_INFO("CH", "Car Handling Information (420)"),
    CONSOLIDATED_INVOICE("CI", "Consolidated Service Invoice/Statement (811)"),
    MANUFACTURER_COUPON_FAMILY("CJ", "Manufacturer Coupon Family Code Structure (877)"),
    MANUFACTURER_COUPON_REDEMPTION("CK", "Manufacturer Coupon Redemption Detail (881)"),
    ELECTION_CAMPAIGN("CL", "Election Campaign and Lobbyist Reporting (113)"),
    COMPONENT_PARTS("CM", "Component Parts Content (871)"),
    COUPON_NOTIFICATION("CN", "Coupon Notification (887)"),
    COOPERATIVE_ADVERTISING("CO", "Cooperative Advertising Agreements (290)"),
    ELECTRONIC_PROPOSAL("CP", "Electronic Proposal Information (251, 805)"),
    COMMODITY_MOVEMENT_RESPONSE("CQ", "Commodity Movement Services Response (874)"),
    RAIL_CARHIRE_SETTLEMENTS("CR", "Rail Carhire Settlements (414)"),
    CRYPTOGRAPHIC_SERVICE("CS", "Cryptographic Service Message (815)"),
    APPLICATION_CONTROL("CT", "Application Control Totals (831)"),
    COMMODITY_MOVEMENT_SERVICE("CU", "Commodity Movement Services (873)"),
    COMMERCIAL_VEHICLE_SAFETY("CV", "Commercial Vehicle Safety and Credentials Information Exchange (285)"),
    EDUCATIONAL_INSTITUTION_RECORD("CW", "Educational Institution Record (133)"),
    CONTRACT_COMPLETION("D3", "Contract Completion Status (567)"),
    CONTRACT_ABSTRACT("D4", "Contract Abstract (561)"),
    CONTRACT_PAYMENT("D5", "Contract Payment Management Report (568)"),
    DEBIT_AUTHORIZATION("DA", "Debit Authorization (828)"),
    SHIPMENT_DELIVERY_DISCREPANCY("DD", "Shipment Delivery Discrepancy Information (854)"),
    MARKET_DEVELOPMENT_FUND("DF", "Market Development Fund Allocation (883)"),
    DEALER_INFORMATION("DI", "Dealer Information (128)"),
    EQUIPMENT_ORDER("DM", "Equipment Order (422)"),
    DATA_STATUS_TRACKING("DS", "Data Status Tracking (242)"),
    DIRECT_EXCHANGE("DX", "Direct Exchange Delivery and Return Information (894, 895)"),
    EDUCATIONAL_COURSE("EC", "Educational Course Inventory (188)"),
    STUDENT_EDUCATIONAL_RECORD("ED", "Student Educational Record (Transcript) (130)"),
    RAILROAD_EQUIPMENT_INQUIRY("EI", "Railroad Equipment Inquiry or Advice (456)"),
    EQUIPMENT_INSPECTION("EN", "Equipment Inspection"),
    ENVIRONMENTAL_COMPLIANCE("EP", "Environmental Compliance Reporting (179)"),
    REVENUE_RECEIPTS("ER", "Revenue Receipts Statement (170)"),
    EMPLOYMENT_STATUS("ES", "Notice of Employment Status (540)"),
    RAILROAD_EVENT_REPORT("EV", "Railroad Event Report (451)"),
    EXCAVATION_COMMUNICATION("EX", "Excavation Communication (620)"),
    FUNCTIONAL_ACKNOWLEDGMENT("FA", "Functional or Implementation Acknowledgment Transaction Sets (997, 999)"),
    FREIGHT_INVOICE("FB", "Freight Invoice (859)"),
    COURT_LAW_ENFORCEMENT("FC", "Court and Law Enforcement Information (175, 176)"),
    MOTOR_CARRIER_LOADING("FG", "Motor Carrier Loading and Route Guide (217)"),
    FINANCIAL_REPORTING("FR", "Financial Reporting (821, 827)"),
    FILE_TRANSFER("FT", "File Transfer (996)"),
    DAMAGE_CLAIM("GC", "Damage Claim Transaction Sets (920, 924, 925, 926)"),
    GENERAL_REQUEST("GE", "General Request, Response or Confirmation (814)"),
    RESPONSE_TO_LOAD_TENDER("GF", "Response to a Load Tender (990)"),
    INTERMODAL_GROUP_LOADING("GL", "Intermodal Group Loading Plan (715)"),
    GROCERY_PRODUCTS_INVOICE("GP", "Grocery Products Invoice (880)"),
    STATISTICAL_GOVERNMENT("GR", "Statistical Government Information (152)"),
    GRANT_ASSISTANCE("GT", "Grant or Assistance Application (194)"),
    ELIGIBILITY_BENEFIT_INFO("HB", "Eligibility, Coverage or Benefit Information (271)"),
    HEALTH_CARE_CLAIM("HC", "Health Care Claim (837)"),
    HEALTH_CARE_SERVICES_REVIEW("HI", "Health Care Services Review Information (278)"),
    HEALTH_CARE_INFO_STATUS("HN", "Health Care Information Status Notification (277)"),
    HEALTH_CARE_CLAIM_PAYMENT("HP", "Health Care Claim Payment/Advice (835)"),
    HEALTH_CARE_CLAIM_STATUS("HR", "Health Care Claim Status Request (276)"),
    ELIGIBILITY_INQUIRY("HS", "Eligibility, Coverage or Benefit Inquiry (270)"),
    HUMAN_RESOURCE_INFO("HU", "Human Resource Information (132)"),
    HEALTH_CARE_BENEFIT_COORDINATION("HV", "Health Care Benefit Coordination Verification (269)"),
    AIR_FREIGHT_DETAILS("IA", "Air Freight Details and Invoice (110, 980)"),
    INVENTORY_INQUIRY("IB", "Inventory Inquiry/Advice (846)"),
    RAIL_ADVANCE_INTERCHANGE("IC", "Rail Advance Interchange Consist (418)"),
    INSURANCE_APPLICATION_STATUS("ID", "Insurance/Annuity Application Status (273)"),
    INSURANCE_PRODUCER_ADMIN("IE", "Insurance Producer Administration (252)"),
    INDIVIDUAL_INSURANCE_POLICY("IF", "Individual Insurance Policy and Client Information (111)"),
    DIRECT_STORE_DELIVERY("IG", "Direct Store Delivery Summary Information (882)"),
    COMMERCIAL_VEHICLE_SAFETY_REPORTS("IH", "Commercial Vehicle Safety Reports (284)"),
    INJURY_ILLNESS_REPORT("IJ", "Report of Injury, Illness or Incident (148)"),
    MOTOR_CARRIER_FREIGHT("IM", "Motor Carrier Freight Details and Invoice (210, 980)"),
    INVOICE_INFORMATION("IN", "Invoice Information (810)"),
    OCEAN_SHIPMENT_BILLING("IO", "Ocean Shipment Billing Details (310, 312, 980)"),
    RAIL_CARRIER_FREIGHT("IR", "Rail Carrier Freight Details and Invoice (410, 980)"),
    ESTIMATED_TIME_ARRIVAL("IS", "Estimated Time of Arrival and Car Scheduling (421)"),
    JOINT_INTEREST_BILLING("JB", "Joint Interest Billing and Operating Expense Statement (819)"),
    COMMERCIAL_VEHICLE_CREDENTIALS("KM", "Commercial Vehicle Credentials (286)"),
    FCC_LICENSE_APPLICATION("LA", "Federal Communications Commission (FCC) License Application (195)"),
    LOCKBOX("LB", "Lockbox (823)"),
    LOCOMOTIVE_INFORMATION("LI", "Locomotive Information (436)"),
    PROPERTY_CASUALTY_LOSS("LN", "Property and Casualty Loss Notification (272)"),
    LOGISTICS_REASSIGNMENT("LR", "Logistics Reassignment (536)"),
    ASSET_SCHEDULE("LS", "Asset Schedule (851)"),
    STUDENT_LOAN_TRANSFER("LT", "Student Loan Transfer and Status Verification (144)"),
    MOTOR_CARRIER_SUMMARY("MA", "Motor Carrier Summary Freight Bill Manifest (224)"),
    REQUEST_MOTOR_CARRIER_RATE("MC", "Request for Motor Carrier Rate Proposal (107)"),
    DOD_INVENTORY_MANAGEMENT("MD", "Department of Defense Inventory Management (527)"),
    MORTGAGE_ORIGINATION("ME", "Mortgage Origination (198, 200, 201, 245, 261, 262, 263, 833, 872)"),
    MARKET_DEVELOPMENT_FUND_SETTLEMENT("MF", "Market Development Fund Settlement (884)"),
    MORTGAGE_SERVICING("MG", "Mortgage Servicing Transaction Sets (203, 206, 259, 260, 264, 266)"),
    MOTOR_CARRIER_RATE_PROPOSAL("MH", "Motor Carrier Rate Proposal (106)"),
    MOTOR_CARRIER_SHIPMENT_STATUS("MI", "Motor Carrier Shipment Status Inquiry (213)"),
    SECONDARY_MORTGAGE_MARKET("MJ", "Secondary Mortgage Market Loan Delivery (202)"),
    RESPONSE_MOTOR_CARRIER_RATE("MK", "Response to a Motor Carrier Rate Proposal (108)"),
    MEDICAL_EVENT_REPORTING("MM", "Medical Event Reporting (500)"),
    MORTGAGE_NOTE("MN", "Mortgage Note (205)"),
    MAINTENANCE_SERVICE_ORDER("MO", "Maintenance Service Order (650)"),
    MOTION_PICTURE_BOOKING("MP", "Motion Picture Booking Confirmation (159)"),
    CONSOLIDATORS_FREIGHT_BILL("MQ", "Consolidators Freight Bill and Invoice (223)"),
    MULTILEVEL_RAILCAR_LOAD("MR", "Multilevel Railcar Load Details (125)"),
    MATERIAL_SAFETY_DATA("MS", "Material Safety Data Sheet (848)"),
    ELECTRONIC_FORM_STRUCTURE("MT", "Electronic Form Structure (868)"),
    MATERIAL_OBLIGATION_VALIDATION("MV", "Material Obligation Validation (517)"),
    RAIL_WAYBILL_RESPONSE("MW", "Rail Waybill Response (427)"),
    MATERIAL_CLAIM("MX", "Material Claim (847)"),
    RESPONSE_TO_CARTAGE("MY", "Response to a Cartage Work Assignment (225)"),
    MOTOR_CARRIER_PACKAGE_STATUS("MZ", "Motor Carrier Package Status (240)"),
    NONCONFORMANCE_REPORT("NC", "Nonconformance Report (842)"),
    NAME_AND_ADDRESS_LISTS("NL", "Name and Address Lists (101)"),
    NOTICE_OF_POWER_OF_ATTORNEY("NP", "Notice of Power of Attorney (157)"),
    SECURED_RECEIPT("NR", "Secured Receipt or Acknowledgment (993)"),
    NOTICE_OF_TAX_ADJUSTMENT("NT", "Notice of Tax Adjustment or Assessment (149)"),
    CARGO_INSURANCE_ADVICE("OC", "Cargo Insurance Advice of Shipment (362)"),
    ORDER_GROUP_GROCERY("OG", "Order Group - Grocery (875, 876)"),
    ORGANIZATIONAL_RELATIONSHIPS("OR", "Organizational Relationships (816)"),
    WAREHOUSE_SHIPPING_ORDER("OW", "Warehouse Shipping Order (940)"),
    PRICE_AUTHORIZATION("PA", "Price Authorization Acknowledgment/Status (845)"),
    RAILROAD_PARAMETER_TRACE("PB", "Railroad Parameter Trace Registration (455)"),
    PURCHASE_ORDER_CHANGE_REQUEST("PC", "Purchase Order Change Request - Buyer Initiated (860)"),
    PRODUCT_ACTIVITY_DATA("PD", "Product Activity Data (852)"),
    PERIODIC_COMPENSATION("PE", "Periodic Compensation (256)"),
    ANNUITY_ACTIVITY("PF", "Annuity Activity (268)"),
    INSURANCE_PLAN_DESCRIPTION("PG", "Insurance Plan Description (100)"),
    PRICING_HISTORY("PH", "Pricing History (503)"),
    PATIENT_INFORMATION("PI", "Patient Information (275)"),
    PROJECT_SCHEDULE("PJ", "Project Schedule Reporting (806)"),
    PROJECT_COST_REPORTING("PK", "Project Cost Reporting (839) and Contractor Cost Data Reporting (196)"),
    RAILROAD_PROBLEM_LOG("PL", "Railroad Problem Log Inquiry or Advice (452)"),
    PRODUCT_SOURCE_INFORMATION("PN", "Product Source Information (244)"),
    PURCHASE_ORDER("PO", "Purchase Order (850)"),
    PROPERTY_DAMAGE_REPORT("PQ", "Property Damage Report (112)"),
    PURCHASE_ORDER_ACKNOWLEDGMENT("PR", "Purchase Order Acknowledgment (855)"),
    PLANNING_SCHEDULE("PS", "Planning Schedule with Release Capability (830)"),
    PRODUCT_TRANSFER("PT", "Product Transfer and Resale Report (867)"),
    MOTOR_CARRIER_SHIPMENT_PICKUP("PU", "Motor Carrier Shipment Pickup Notification (216)"),
    PURCHASE_ORDER_SHIPMENT("PV", "Purchase Order Shipment Management Document (250)"),
    HEALTHCARE_PROVIDER_INFO("PW", "Healthcare Provider Information (274)"),
    PAYMENT_CANCELLATION_REQUEST("PY", "Payment Cancellation Request (829)"),
    PRODUCT_INFORMATION("QG", "Product Information (878, 879, 888, 889, 896)"),
    TRANSPORTATION_CARRIER_SHIPMENT("QM", "Transportation Carrier Shipment Status Message (214)"),
    OCEAN_SHIPMENT_STATUS("QO", "Ocean Shipment Status Information (313, 315)"),
    PAYMENT_ORDER("RA", "Payment Order/Remittance Advice (820)"),
    RAILROAD_CLEARANCE("RB", "Railroad Clearance (470)"),
    RECEIVING_ADVICE("RC", "Receiving Advice/Acceptance Certificate (861)"),
    ROYALTY_REGULATORY_REPORT("RD", "Royalty Regulatory Report (185)"),
    WAREHOUSE_STOCK_RECEIPT("RE", "Warehouse Stock Receipt Advice (944)"),
    REQUEST_FOR_ROUTING("RF", "Request for Routing Instructions (753)"),
    ROUTING_INSTRUCTIONS("RG", "Routing Instructions (754)"),
    RAILROAD_RECIPROCAL_SWITCH("RH", "Railroad Reciprocal Switch File (433)"),
    ROUTING_AND_CARRIER("RI", "Routing and Carrier Instruction (853)"),
    RAILROAD_MARK_REGISTER("RJ", "Railroad Mark Register Update Activity (434)"),
    STANDARD_TRANSPORTATION_CODE("RK", "Standard Transportation Commodity Code Master (435)"),
    RAIL_INDUSTRIAL_SWITCH("RL", "Rail Industrial Switch List (423)"),
    RAILROAD_STATION_MASTER("RM", "Railroad Station Master File (431)"),
    REQUISITION_TRANSACTION("RN", "Requisition Transaction (511)"),
    OCEAN_BOOKING_INFORMATION("RO", "Ocean Booking Information (300, 301, 303)"),
    COMMISSION_SALES_REPORT("RP", "Commission Sales Report (818)"),
    REQUEST_FOR_QUOTATION("RQ", "Request for Quotation (840) and Procurement Notices (836)"),
    RESPONSE_TO_REQUEST_FOR_QUOTATION("RR", "Response to Request For Quotation (843)"),
    ORDER_STATUS_INFORMATION("RS", "Order Status Information (869, 870)"),
    TEST_RESULTS_REPORT("RT", "Report of Test Results (863)"),
    RAILROAD_RETIREMENT("RU", "Railroad Retirement Activity (429)"),
    RAILROAD_JUNCTIONS("RV", "Railroad Junctions and Interchanges Activity (437)"),
    RAIL_REVENUE_WAYBILL("RW", "Rail Revenue Waybill (426)"),
    RAIL_DEPRESCRIPTION("RX", "Rail Deprescription (432)"),
    REQUEST_STUDENT_RECORD("RY", "Request for Student Educational Record (Transcript) (146)"),
    RESPONSE_STUDENT_RECORD("RZ", "Response to Request for Student Educational Record (Transcript) (147)"),
    AIR_SHIPMENT_INFORMATION("SA", "Air Shipment Information (104)"),
    RAIL_CARRIER_SERVICES("SB", "Rail Carrier Services Settlement (424)"),
    PRICE_SALES_CATALOG("SC", "Price/Sales Catalog (832)"),
    STUDENT_LOAN_CLAIMS("SD", "Student Loan Pre-Claims and Claims (191)"),
    SHIPPERS_EXPORT_DECLARATION("SE", "Shipper's Export Declaration (601)"),
    SHIP_NOTICE_MANIFEST("SH", "Ship Notice/Manifest (856)"),
    SHIPMENT_INFORMATION("SI", "Shipment Information (858)"),
    TRANSPORTATION_EQUIPMENT_ID("SJ", "Transportation Automatic Equipment Identification (160)"),
    STUDENT_AID_ORIGINATION("SL", "Student Aid Origination Record (135, 139)"),
    MOTOR_CARRIER_LOAD_TENDER("SM", "Motor Carrier Load Tender (204)"),
    RAIL_ROUTE_FILE("SN", "Rail Route File Maintenance (475)"),
    OCEAN_SHIPMENT_INFORMATION("SO", "Ocean Shipment Information (304, 309, 311, 317, 319, 322, 323, 324, 325, 326, 350, 352, 353, 354, 355, 356, 357, 358, 361)"),
    SPECIFICATIONS_TECHNICAL("SP", "Specifications/Technical Information (841)"),
    PRODUCTION_SEQUENCE("SQ", "Production Sequence (866)"),
    RAIL_CARRIER_SHIPMENT("SR", "Rail Carrier Shipment Information (404, 419)"),
    SHIPPING_SCHEDULE("SS", "Shipping Schedule (862)"),
    RAILROAD_SERVICE_COMMITMENT("ST", "Railroad Service Commitment Advice (453)"),
    ACCOUNT_ASSIGNMENT("SU", "Account Assignment/Inquiry and Service/Status (248)"),
    STUDENT_ENROLLMENT_VERIFICATION("SV", "Student Enrollment Verification (190)"),
    WAREHOUSE_SHIPPING_ADVICE("SW", "Warehouse Shipping Advice (945)"),
    TAX_RETURN_ACKNOWLEDGMENT("TA", "Electronic Filing of Tax Return Data Acknowledgment (151)"),
    TRAILER_CONTAINER_REPAIR("TB", "Trailer or Container Repair Billing (412)"),
    TRADING_PARTNER_PROFILE("TD", "Trading Partner Profile (838)"),
    TAX_FEE_EXEMPTION("TE", "Tax or Fee Exemption Certification (283)"),
    ELECTRONIC_TAX_FILING("TF", "Electronic Filing of Tax Return Data (813)"),
    TAX_INFORMATION_EXCHANGE("TI", "Tax Information Exchange (826)"),
    TAX_JURISDICTION_SOURCING("TJ", "Tax Jurisdiction Sourcing (158)"),
    MOTOR_CARRIER_DELIVERY("TM", "Motor Carrier Delivery Trailer Manifest (212)"),
    TAX_RATE_NOTIFICATION("TN", "Tax Rate Notification (150)"),
    REAL_ESTATE_TITLE("TO", "Real Estate Title Services (197, 199, 265, 485, 486)"),
    RAIL_RATE_TRANSACTIONS("TP", "Rail Rate Transactions (460, 463, 466, 468, 485, 486, 490, 492, 494)"),
    TRAIN_SHEET("TR", "Train Sheet (161)"),
    TRANSPORTATION_SERVICES_TENDER("TS", "Transportation Services Tender (602)"),
    EDUCATIONAL_TESTING("TT", "Educational Testing and Prospect Request and Report (138)"),
    TRAILER_USAGE_REPORT("TU", "Trailer Usage Report (227)"),
    TEXT_MESSAGE("TX", "Text Message (864)"),
    RETAIL_ACCOUNT_CHARACTERISTICS("UA", "Retail Account Characteristics (885)"),
    CUSTOMER_CALL_REPORTING("UB", "Customer Call Reporting (886)"),
    SECURED_INTEREST_FILING("UC", "Secured Interest Filing (154)"),
    DEDUCTION_RESEARCH_REPORT("UD", "Deduction Research Report (891)"),
    UNDERWRITING_INFORMATION("UI", "Underwriting Information Services (255)"),
    MOTOR_CARRIER_PICKUP_MANIFEST("UP", "Motor Carrier Pickup Manifest (215)"),
    INSURANCE_UNDERWRITING("UW", "Insurance Underwriting Requirements Reporting (186)"),
    VEHICLE_APPLICATION_ADVICE("VA", "Vehicle Application Advice (126)"),
    VEHICLE_BAYING_ORDER("VB", "Vehicle Baying Order (127)"),
    VEHICLE_SHIPPING_ORDER("VC", "Vehicle Shipping Order (120)"),
    VEHICLE_DAMAGE("VD", "Vehicle Damage (124)"),
    VESSEL_CONTENT_DETAILS("VE", "Vessel Content Details (109)"),
    VEHICLE_CARRIER_RATE("VH", "Vehicle Carrier Rate Update (129)"),
    VOTER_REGISTRATION("VI", "Voter Registration Information (280)"),
    VEHICLE_SERVICE("VS", "Vehicle Service (121)"),
    PRODUCT_SERVICE("WA", "Product Service Transaction Sets (140, 141, 142, 143)"),
    RAIL_WAYBILL_INTERCHANGE("WB", "Rail Carrier Waybill Interchange (417)"),
    VENDOR_PERFORMANCE("WG", "Vendor Performance Review (501)"),
    WAGE_DETERMINATION("WI", "Wage Determination (288)"),
    WELL_INFORMATION("WL", "Well Information (625)"),
    SHIPMENT_WEIGHTS("WR", "Shipment Weights (440)"),
    RAIL_WAYBILL_REQUEST("WT", "Rail Waybill Request (425)");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<FunctionalIdentifierCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                FunctionalIdentifierCode.class,
                "Functional Identifier Code",
                Map.ofEntries(
                        Map.entry("be", BENEFIT_ENROLLMENT),
                        Map.entry("834", BENEFIT_ENROLLMENT),
                        Map.entry("benefits enrollment", BENEFIT_ENROLLMENT),
                        Map.entry("enrollment", BENEFIT_ENROLLMENT),

                        Map.entry("hp", HEALTH_CARE_CLAIM_PAYMENT),
                        Map.entry("835", HEALTH_CARE_CLAIM_PAYMENT),
                        Map.entry("payment", HEALTH_CARE_CLAIM_PAYMENT),
                        Map.entry("claim payment", HEALTH_CARE_CLAIM_PAYMENT),

                        Map.entry("hc", HEALTH_CARE_CLAIM),
                        Map.entry("837", HEALTH_CARE_CLAIM),
                        Map.entry("claim", HEALTH_CARE_CLAIM),

                        Map.entry("hs", ELIGIBILITY_INQUIRY),
                        Map.entry("270", ELIGIBILITY_INQUIRY),
                        Map.entry("eligibility request", ELIGIBILITY_INQUIRY),

                        Map.entry("hb", ELIGIBILITY_BENEFIT_INFO),
                        Map.entry("271", ELIGIBILITY_BENEFIT_INFO),
                        Map.entry("eligibility response", ELIGIBILITY_BENEFIT_INFO),

                        Map.entry("po", PURCHASE_ORDER),
                        Map.entry("850", PURCHASE_ORDER),
                        Map.entry("order", PURCHASE_ORDER),

                        Map.entry("in", INVOICE_INFORMATION),
                        Map.entry("810", INVOICE_INFORMATION),
                        Map.entry("invoice", INVOICE_INFORMATION),

                        Map.entry("sh", SHIP_NOTICE_MANIFEST),
                        Map.entry("856", SHIP_NOTICE_MANIFEST),
                        Map.entry("asn", SHIP_NOTICE_MANIFEST),
                        Map.entry("advance shipment notice", SHIP_NOTICE_MANIFEST),

                        Map.entry("fa", FUNCTIONAL_ACKNOWLEDGMENT),
                        Map.entry("997", FUNCTIONAL_ACKNOWLEDGMENT),
                        Map.entry("999", FUNCTIONAL_ACKNOWLEDGMENT),
                        Map.entry("ack", FUNCTIONAL_ACKNOWLEDGMENT),
                        Map.entry("acknowledgment", FUNCTIONAL_ACKNOWLEDGMENT)
                )
        );
    }

    FunctionalIdentifierCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a FunctionalIdentifierCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching FunctionalIdentifierCode
     * @throws IllegalArgumentException if no match is found
     */
    public static FunctionalIdentifierCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}