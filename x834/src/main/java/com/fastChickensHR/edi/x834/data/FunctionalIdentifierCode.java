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
 * Code values for the Functional Identifier Code (GS01, X12 data element 479) in the Functional
 * Group Header (GS). It names the business family of the transaction sets in the group; for the X12
 * 834 Benefit Enrollment and Maintenance transaction the value is {@link #BENEFIT_ENROLLMENT}
 * ("BE").
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum FunctionalIdentifierCode implements EdiCodeEnum {
  /** Account Analysis (822) — X12 code "AA". */
  ACCOUNT_ANALYSIS("AA", "Account Analysis (822)"),
  /** Logistics Service Request (219) — X12 code "AB". */
  LOGISTICS_SERVICE_REQUEST("AB", "Logistics Service Request (219)"),
  /** Associated Data (102) — X12 code "AC". */
  ASSOCIATED_DATA("AC", "Associated Data (102)"),
  /** Individual Life, Annuity and Disability Application (267) — X12 code "AD". */
  LIFE_ANNUITY_APPLICATION("AD", "Individual Life, Annuity and Disability Application (267)"),
  /** Premium Audit Request and Return (187) — X12 code "AE". */
  PREMIUM_AUDIT("AE", "Premium Audit Request and Return (187)"),
  /** Application for Admission to Educational Institutions (189) — X12 code "AF". */
  EDUCATIONAL_ADMISSION("AF", "Application for Admission to Educational Institutions (189)"),
  /** Application Advice (824) — X12 code "AG". */
  APPLICATION_ADVICE("AG", "Application Advice (824)"),
  /** Logistics Service Response (220) — X12 code "AH". */
  LOGISTICS_SERVICE_RESPONSE("AH", "Logistics Service Response (220)"),
  /** Automotive Inspection Detail (928) — X12 code "AI". */
  AUTOMOTIVE_INSPECTION("AI", "Automotive Inspection Detail (928)"),
  /** Student Educational Record (Transcript) Acknowledgment (131) — X12 code "AK". */
  TRANSCRIPT_ACKNOWLEDGMENT("AK", "Student Educational Record (Transcript) Acknowledgment (131)"),
  /** Set Cancellation (998) — X12 code "AL". */
  SET_CANCELLATION("AL", "Set Cancellation (998)"),
  /** Item Information Request (893) — X12 code "AM". */
  ITEM_INFORMATION_REQUEST("AM", "Item Information Request (893)"),
  /** Return Merchandise Authorization and Notification (180) — X12 code "AN". */
  RETURN_MERCHANDISE_AUTH("AN", "Return Merchandise Authorization and Notification (180)"),
  /** Income or Asset Offset (521) — X12 code "AO". */
  INCOME_ASSET_OFFSET("AO", "Income or Asset Offset (521)"),
  /** Abandoned Property Filings (103) — X12 code "AP". */
  ABANDONED_PROPERTY("AP", "Abandoned Property Filings (103)"),
  /** U.S. Customs Manifest (309) — X12 code "AQ". */
  CUSTOMS_MANIFEST("AQ", "U.S. Customs Manifest (309)"),
  /** Warehouse Stock Transfer Shipment Advice (943) — X12 code "AR". */
  WAREHOUSE_STOCK_TRANSFER("AR", "Warehouse Stock Transfer Shipment Advice (943)"),
  /** Transportation Appointment Schedule Information (163) — X12 code "AS". */
  TRANSPORTATION_APPOINTMENT("AS", "Transportation Appointment Schedule Information (163)"),
  /** Animal Toxicological Data (249) — X12 code "AT". */
  ANIMAL_TOXICOLOGICAL_DATA("AT", "Animal Toxicological Data (249)"),
  /** U.S. Customs Status Information (350) — X12 code "AU". */
  CUSTOMS_STATUS_INFO("AU", "U.S. Customs Status Information (350)"),
  /** U.S. Customs Carrier General Order Status (352) — X12 code "AV". */
  CUSTOMS_GENERAL_ORDER("AV", "U.S. Customs Carrier General Order Status (352)"),
  /** Warehouse Inventory Adjustment Advice (947) — X12 code "AW". */
  WAREHOUSE_INVENTORY_ADJ("AW", "Warehouse Inventory Adjustment Advice (947)"),
  /** U.S. Customs Events Advisory Details (353) — X12 code "AX". */
  CUSTOMS_EVENTS_ADVISORY("AX", "U.S. Customs Events Advisory Details (353)"),
  /** U.S. Customs Automated Manifest Archive Status (354) — X12 code "AY". */
  CUSTOMS_MANIFEST_ARCHIVE("AY", "U.S. Customs Automated Manifest Archive Status (354)"),
  /** U.S. Customs Acceptance/Rejection (355) — X12 code "AZ". */
  CUSTOMS_ACCEPTANCE("AZ", "U.S. Customs Acceptance/Rejection (355)"),
  /** U.S. Customs Permit to Transfer Request (356) — X12 code "BA". */
  CUSTOMS_PERMIT_TRANSFER("BA", "U.S. Customs Permit to Transfer Request (356)"),
  /** U.S. Customs In-Bond Information (357) — X12 code "BB". */
  CUSTOMS_IN_BOND("BB", "U.S. Customs In-Bond Information (357)"),
  /** Business Credit Report (155) — X12 code "BC". */
  BUSINESS_CREDIT_REPORT("BC", "Business Credit Report (155)"),
  /** U.S. Customs Consist Information (358) — X12 code "BD". */
  CUSTOMS_CONSIST_INFO("BD", "U.S. Customs Consist Information (358)"),
  /** Benefit Enrollment and Maintenance (834) — X12 code "BE". */
  BENEFIT_ENROLLMENT("BE", "Benefit Enrollment and Maintenance (834)"),
  /** Business Entity Filings (105) — X12 code "BF". */
  BUSINESS_ENTITY_FILINGS("BF", "Business Entity Filings (105)"),
  /** Motor Carrier Bill of Lading (211) — X12 code "BL". */
  MOTOR_CARRIER_BILL_LADING("BL", "Motor Carrier Bill of Lading (211)"),
  /** Shipment and Billing Notice (857) — X12 code "BS". */
  SHIPMENT_BILLING_NOTICE("BS", "Shipment and Billing Notice (857)"),
  /** Purchase Order Change Acknowledgment/Request - Seller Initiated (865) — X12 code "CA". */
  PURCHASE_ORDER_CHANGE(
      "CA", "Purchase Order Change Acknowledgment/Request - Seller Initiated (865)"),
  /** Unemployment Insurance Tax Claim or Charge Information (153) — X12 code "CB". */
  UNEMPLOYMENT_INSURANCE("CB", "Unemployment Insurance Tax Claim or Charge Information (153)"),
  /** Clauses and Provisions (504) — X12 code "CC". */
  CLAUSES_PROVISIONS("CC", "Clauses and Provisions (504)"),
  /** Credit/Debit Adjustment (812) — X12 code "CD". */
  CREDIT_DEBIT_ADJUSTMENT("CD", "Credit/Debit Adjustment (812)"),
  /** Cartage Work Assignment (222) — X12 code "CE". */
  CARTAGE_WORK_ASSIGNMENT("CE", "Cartage Work Assignment (222)"),
  /** Corporate Financial Adjustment Information (844 and 849) — X12 code "CF". */
  CORPORATE_FINANCIAL("CF", "Corporate Financial Adjustment Information (844 and 849)"),
  /** Car Handling Information (420) — X12 code "CH". */
  CAR_HANDLING_INFO("CH", "Car Handling Information (420)"),
  /** Consolidated Service Invoice/Statement (811) — X12 code "CI". */
  CONSOLIDATED_INVOICE("CI", "Consolidated Service Invoice/Statement (811)"),
  /** Manufacturer Coupon Family Code Structure (877) — X12 code "CJ". */
  MANUFACTURER_COUPON_FAMILY("CJ", "Manufacturer Coupon Family Code Structure (877)"),
  /** Manufacturer Coupon Redemption Detail (881) — X12 code "CK". */
  MANUFACTURER_COUPON_REDEMPTION("CK", "Manufacturer Coupon Redemption Detail (881)"),
  /** Election Campaign and Lobbyist Reporting (113) — X12 code "CL". */
  ELECTION_CAMPAIGN("CL", "Election Campaign and Lobbyist Reporting (113)"),
  /** Component Parts Content (871) — X12 code "CM". */
  COMPONENT_PARTS("CM", "Component Parts Content (871)"),
  /** Coupon Notification (887) — X12 code "CN". */
  COUPON_NOTIFICATION("CN", "Coupon Notification (887)"),
  /** Cooperative Advertising Agreements (290) — X12 code "CO". */
  COOPERATIVE_ADVERTISING("CO", "Cooperative Advertising Agreements (290)"),
  /** Electronic Proposal Information (251, 805) — X12 code "CP". */
  ELECTRONIC_PROPOSAL("CP", "Electronic Proposal Information (251, 805)"),
  /** Commodity Movement Services Response (874) — X12 code "CQ". */
  COMMODITY_MOVEMENT_RESPONSE("CQ", "Commodity Movement Services Response (874)"),
  /** Rail Carhire Settlements (414) — X12 code "CR". */
  RAIL_CARHIRE_SETTLEMENTS("CR", "Rail Carhire Settlements (414)"),
  /** Cryptographic Service Message (815) — X12 code "CS". */
  CRYPTOGRAPHIC_SERVICE("CS", "Cryptographic Service Message (815)"),
  /** Application Control Totals (831) — X12 code "CT". */
  APPLICATION_CONTROL("CT", "Application Control Totals (831)"),
  /** Commodity Movement Services (873) — X12 code "CU". */
  COMMODITY_MOVEMENT_SERVICE("CU", "Commodity Movement Services (873)"),
  /** Commercial Vehicle Safety and Credentials Information Exchange (285) — X12 code "CV". */
  COMMERCIAL_VEHICLE_SAFETY(
      "CV", "Commercial Vehicle Safety and Credentials Information Exchange (285)"),
  /** Educational Institution Record (133) — X12 code "CW". */
  EDUCATIONAL_INSTITUTION_RECORD("CW", "Educational Institution Record (133)"),
  /** Contract Completion Status (567) — X12 code "D3". */
  CONTRACT_COMPLETION("D3", "Contract Completion Status (567)"),
  /** Contract Abstract (561) — X12 code "D4". */
  CONTRACT_ABSTRACT("D4", "Contract Abstract (561)"),
  /** Contract Payment Management Report (568) — X12 code "D5". */
  CONTRACT_PAYMENT("D5", "Contract Payment Management Report (568)"),
  /** Debit Authorization (828) — X12 code "DA". */
  DEBIT_AUTHORIZATION("DA", "Debit Authorization (828)"),
  /** Shipment Delivery Discrepancy Information (854) — X12 code "DD". */
  SHIPMENT_DELIVERY_DISCREPANCY("DD", "Shipment Delivery Discrepancy Information (854)"),
  /** Market Development Fund Allocation (883) — X12 code "DF". */
  MARKET_DEVELOPMENT_FUND("DF", "Market Development Fund Allocation (883)"),
  /** Dealer Information (128) — X12 code "DI". */
  DEALER_INFORMATION("DI", "Dealer Information (128)"),
  /** Equipment Order (422) — X12 code "DM". */
  EQUIPMENT_ORDER("DM", "Equipment Order (422)"),
  /** Data Status Tracking (242) — X12 code "DS". */
  DATA_STATUS_TRACKING("DS", "Data Status Tracking (242)"),
  /** Direct Exchange Delivery and Return Information (894, 895) — X12 code "DX". */
  DIRECT_EXCHANGE("DX", "Direct Exchange Delivery and Return Information (894, 895)"),
  /** Educational Course Inventory (188) — X12 code "EC". */
  EDUCATIONAL_COURSE("EC", "Educational Course Inventory (188)"),
  /** Student Educational Record (Transcript) (130) — X12 code "ED". */
  STUDENT_EDUCATIONAL_RECORD("ED", "Student Educational Record (Transcript) (130)"),
  /** Railroad Equipment Inquiry or Advice (456) — X12 code "EI". */
  RAILROAD_EQUIPMENT_INQUIRY("EI", "Railroad Equipment Inquiry or Advice (456)"),
  /** Equipment Inspection — X12 code "EN". */
  EQUIPMENT_INSPECTION("EN", "Equipment Inspection"),
  /** Environmental Compliance Reporting (179) — X12 code "EP". */
  ENVIRONMENTAL_COMPLIANCE("EP", "Environmental Compliance Reporting (179)"),
  /** Revenue Receipts Statement (170) — X12 code "ER". */
  REVENUE_RECEIPTS("ER", "Revenue Receipts Statement (170)"),
  /** Notice of Employment Status (540) — X12 code "ES". */
  EMPLOYMENT_STATUS("ES", "Notice of Employment Status (540)"),
  /** Railroad Event Report (451) — X12 code "EV". */
  RAILROAD_EVENT_REPORT("EV", "Railroad Event Report (451)"),
  /** Excavation Communication (620) — X12 code "EX". */
  EXCAVATION_COMMUNICATION("EX", "Excavation Communication (620)"),
  /** Functional or Implementation Acknowledgment Transaction Sets (997, 999) — X12 code "FA". */
  FUNCTIONAL_ACKNOWLEDGMENT(
      "FA", "Functional or Implementation Acknowledgment Transaction Sets (997, 999)"),
  /** Freight Invoice (859) — X12 code "FB". */
  FREIGHT_INVOICE("FB", "Freight Invoice (859)"),
  /** Court and Law Enforcement Information (175, 176) — X12 code "FC". */
  COURT_LAW_ENFORCEMENT("FC", "Court and Law Enforcement Information (175, 176)"),
  /** Motor Carrier Loading and Route Guide (217) — X12 code "FG". */
  MOTOR_CARRIER_LOADING("FG", "Motor Carrier Loading and Route Guide (217)"),
  /** Financial Reporting (821, 827) — X12 code "FR". */
  FINANCIAL_REPORTING("FR", "Financial Reporting (821, 827)"),
  /** File Transfer (996) — X12 code "FT". */
  FILE_TRANSFER("FT", "File Transfer (996)"),
  /** Damage Claim Transaction Sets (920, 924, 925, 926) — X12 code "GC". */
  DAMAGE_CLAIM("GC", "Damage Claim Transaction Sets (920, 924, 925, 926)"),
  /** General Request, Response or Confirmation (814) — X12 code "GE". */
  GENERAL_REQUEST("GE", "General Request, Response or Confirmation (814)"),
  /** Response to a Load Tender (990) — X12 code "GF". */
  RESPONSE_TO_LOAD_TENDER("GF", "Response to a Load Tender (990)"),
  /** Intermodal Group Loading Plan (715) — X12 code "GL". */
  INTERMODAL_GROUP_LOADING("GL", "Intermodal Group Loading Plan (715)"),
  /** Grocery Products Invoice (880) — X12 code "GP". */
  GROCERY_PRODUCTS_INVOICE("GP", "Grocery Products Invoice (880)"),
  /** Statistical Government Information (152) — X12 code "GR". */
  STATISTICAL_GOVERNMENT("GR", "Statistical Government Information (152)"),
  /** Grant or Assistance Application (194) — X12 code "GT". */
  GRANT_ASSISTANCE("GT", "Grant or Assistance Application (194)"),
  /** Eligibility, Coverage or Benefit Information (271) — X12 code "HB". */
  ELIGIBILITY_BENEFIT_INFO("HB", "Eligibility, Coverage or Benefit Information (271)"),
  /** Health Care Claim (837) — X12 code "HC". */
  HEALTH_CARE_CLAIM("HC", "Health Care Claim (837)"),
  /** Health Care Services Review Information (278) — X12 code "HI". */
  HEALTH_CARE_SERVICES_REVIEW("HI", "Health Care Services Review Information (278)"),
  /** Health Care Information Status Notification (277) — X12 code "HN". */
  HEALTH_CARE_INFO_STATUS("HN", "Health Care Information Status Notification (277)"),
  /** Health Care Claim Payment/Advice (835) — X12 code "HP". */
  HEALTH_CARE_CLAIM_PAYMENT("HP", "Health Care Claim Payment/Advice (835)"),
  /** Health Care Claim Status Request (276) — X12 code "HR". */
  HEALTH_CARE_CLAIM_STATUS("HR", "Health Care Claim Status Request (276)"),
  /** Eligibility, Coverage or Benefit Inquiry (270) — X12 code "HS". */
  ELIGIBILITY_INQUIRY("HS", "Eligibility, Coverage or Benefit Inquiry (270)"),
  /** Human Resource Information (132) — X12 code "HU". */
  HUMAN_RESOURCE_INFO("HU", "Human Resource Information (132)"),
  /** Health Care Benefit Coordination Verification (269) — X12 code "HV". */
  HEALTH_CARE_BENEFIT_COORDINATION("HV", "Health Care Benefit Coordination Verification (269)"),
  /** Air Freight Details and Invoice (110, 980) — X12 code "IA". */
  AIR_FREIGHT_DETAILS("IA", "Air Freight Details and Invoice (110, 980)"),
  /** Inventory Inquiry/Advice (846) — X12 code "IB". */
  INVENTORY_INQUIRY("IB", "Inventory Inquiry/Advice (846)"),
  /** Rail Advance Interchange Consist (418) — X12 code "IC". */
  RAIL_ADVANCE_INTERCHANGE("IC", "Rail Advance Interchange Consist (418)"),
  /** Insurance/Annuity Application Status (273) — X12 code "ID". */
  INSURANCE_APPLICATION_STATUS("ID", "Insurance/Annuity Application Status (273)"),
  /** Insurance Producer Administration (252) — X12 code "IE". */
  INSURANCE_PRODUCER_ADMIN("IE", "Insurance Producer Administration (252)"),
  /** Individual Insurance Policy and Client Information (111) — X12 code "IF". */
  INDIVIDUAL_INSURANCE_POLICY("IF", "Individual Insurance Policy and Client Information (111)"),
  /** Direct Store Delivery Summary Information (882) — X12 code "IG". */
  DIRECT_STORE_DELIVERY("IG", "Direct Store Delivery Summary Information (882)"),
  /** Commercial Vehicle Safety Reports (284) — X12 code "IH". */
  COMMERCIAL_VEHICLE_SAFETY_REPORTS("IH", "Commercial Vehicle Safety Reports (284)"),
  /** Report of Injury, Illness or Incident (148) — X12 code "IJ". */
  INJURY_ILLNESS_REPORT("IJ", "Report of Injury, Illness or Incident (148)"),
  /** Motor Carrier Freight Details and Invoice (210, 980) — X12 code "IM". */
  MOTOR_CARRIER_FREIGHT("IM", "Motor Carrier Freight Details and Invoice (210, 980)"),
  /** Invoice Information (810) — X12 code "IN". */
  INVOICE_INFORMATION("IN", "Invoice Information (810)"),
  /** Ocean Shipment Billing Details (310, 312, 980) — X12 code "IO". */
  OCEAN_SHIPMENT_BILLING("IO", "Ocean Shipment Billing Details (310, 312, 980)"),
  /** Rail Carrier Freight Details and Invoice (410, 980) — X12 code "IR". */
  RAIL_CARRIER_FREIGHT("IR", "Rail Carrier Freight Details and Invoice (410, 980)"),
  /** Estimated Time of Arrival and Car Scheduling (421) — X12 code "IS". */
  ESTIMATED_TIME_ARRIVAL("IS", "Estimated Time of Arrival and Car Scheduling (421)"),
  /** Joint Interest Billing and Operating Expense Statement (819) — X12 code "JB". */
  JOINT_INTEREST_BILLING("JB", "Joint Interest Billing and Operating Expense Statement (819)"),
  /** Commercial Vehicle Credentials (286) — X12 code "KM". */
  COMMERCIAL_VEHICLE_CREDENTIALS("KM", "Commercial Vehicle Credentials (286)"),
  /** Federal Communications Commission (FCC) License Application (195) — X12 code "LA". */
  FCC_LICENSE_APPLICATION(
      "LA", "Federal Communications Commission (FCC) License Application (195)"),
  /** Lockbox (823) — X12 code "LB". */
  LOCKBOX("LB", "Lockbox (823)"),
  /** Locomotive Information (436) — X12 code "LI". */
  LOCOMOTIVE_INFORMATION("LI", "Locomotive Information (436)"),
  /** Property and Casualty Loss Notification (272) — X12 code "LN". */
  PROPERTY_CASUALTY_LOSS("LN", "Property and Casualty Loss Notification (272)"),
  /** Logistics Reassignment (536) — X12 code "LR". */
  LOGISTICS_REASSIGNMENT("LR", "Logistics Reassignment (536)"),
  /** Asset Schedule (851) — X12 code "LS". */
  ASSET_SCHEDULE("LS", "Asset Schedule (851)"),
  /** Student Loan Transfer and Status Verification (144) — X12 code "LT". */
  STUDENT_LOAN_TRANSFER("LT", "Student Loan Transfer and Status Verification (144)"),
  /** Motor Carrier Summary Freight Bill Manifest (224) — X12 code "MA". */
  MOTOR_CARRIER_SUMMARY("MA", "Motor Carrier Summary Freight Bill Manifest (224)"),
  /** Request for Motor Carrier Rate Proposal (107) — X12 code "MC". */
  REQUEST_MOTOR_CARRIER_RATE("MC", "Request for Motor Carrier Rate Proposal (107)"),
  /** Department of Defense Inventory Management (527) — X12 code "MD". */
  DOD_INVENTORY_MANAGEMENT("MD", "Department of Defense Inventory Management (527)"),
  /** Mortgage Origination (198, 200, 201, 245, 261, 262, 263, 833, 872) — X12 code "ME". */
  MORTGAGE_ORIGINATION("ME", "Mortgage Origination (198, 200, 201, 245, 261, 262, 263, 833, 872)"),
  /** Market Development Fund Settlement (884) — X12 code "MF". */
  MARKET_DEVELOPMENT_FUND_SETTLEMENT("MF", "Market Development Fund Settlement (884)"),
  /** Mortgage Servicing Transaction Sets (203, 206, 259, 260, 264, 266) — X12 code "MG". */
  MORTGAGE_SERVICING("MG", "Mortgage Servicing Transaction Sets (203, 206, 259, 260, 264, 266)"),
  /** Motor Carrier Rate Proposal (106) — X12 code "MH". */
  MOTOR_CARRIER_RATE_PROPOSAL("MH", "Motor Carrier Rate Proposal (106)"),
  /** Motor Carrier Shipment Status Inquiry (213) — X12 code "MI". */
  MOTOR_CARRIER_SHIPMENT_STATUS("MI", "Motor Carrier Shipment Status Inquiry (213)"),
  /** Secondary Mortgage Market Loan Delivery (202) — X12 code "MJ". */
  SECONDARY_MORTGAGE_MARKET("MJ", "Secondary Mortgage Market Loan Delivery (202)"),
  /** Response to a Motor Carrier Rate Proposal (108) — X12 code "MK". */
  RESPONSE_MOTOR_CARRIER_RATE("MK", "Response to a Motor Carrier Rate Proposal (108)"),
  /** Medical Event Reporting (500) — X12 code "MM". */
  MEDICAL_EVENT_REPORTING("MM", "Medical Event Reporting (500)"),
  /** Mortgage Note (205) — X12 code "MN". */
  MORTGAGE_NOTE("MN", "Mortgage Note (205)"),
  /** Maintenance Service Order (650) — X12 code "MO". */
  MAINTENANCE_SERVICE_ORDER("MO", "Maintenance Service Order (650)"),
  /** Motion Picture Booking Confirmation (159) — X12 code "MP". */
  MOTION_PICTURE_BOOKING("MP", "Motion Picture Booking Confirmation (159)"),
  /** Consolidators Freight Bill and Invoice (223) — X12 code "MQ". */
  CONSOLIDATORS_FREIGHT_BILL("MQ", "Consolidators Freight Bill and Invoice (223)"),
  /** Multilevel Railcar Load Details (125) — X12 code "MR". */
  MULTILEVEL_RAILCAR_LOAD("MR", "Multilevel Railcar Load Details (125)"),
  /** Material Safety Data Sheet (848) — X12 code "MS". */
  MATERIAL_SAFETY_DATA("MS", "Material Safety Data Sheet (848)"),
  /** Electronic Form Structure (868) — X12 code "MT". */
  ELECTRONIC_FORM_STRUCTURE("MT", "Electronic Form Structure (868)"),
  /** Material Obligation Validation (517) — X12 code "MV". */
  MATERIAL_OBLIGATION_VALIDATION("MV", "Material Obligation Validation (517)"),
  /** Rail Waybill Response (427) — X12 code "MW". */
  RAIL_WAYBILL_RESPONSE("MW", "Rail Waybill Response (427)"),
  /** Material Claim (847) — X12 code "MX". */
  MATERIAL_CLAIM("MX", "Material Claim (847)"),
  /** Response to a Cartage Work Assignment (225) — X12 code "MY". */
  RESPONSE_TO_CARTAGE("MY", "Response to a Cartage Work Assignment (225)"),
  /** Motor Carrier Package Status (240) — X12 code "MZ". */
  MOTOR_CARRIER_PACKAGE_STATUS("MZ", "Motor Carrier Package Status (240)"),
  /** Nonconformance Report (842) — X12 code "NC". */
  NONCONFORMANCE_REPORT("NC", "Nonconformance Report (842)"),
  /** Name and Address Lists (101) — X12 code "NL". */
  NAME_AND_ADDRESS_LISTS("NL", "Name and Address Lists (101)"),
  /** Notice of Power of Attorney (157) — X12 code "NP". */
  NOTICE_OF_POWER_OF_ATTORNEY("NP", "Notice of Power of Attorney (157)"),
  /** Secured Receipt or Acknowledgment (993) — X12 code "NR". */
  SECURED_RECEIPT("NR", "Secured Receipt or Acknowledgment (993)"),
  /** Notice of Tax Adjustment or Assessment (149) — X12 code "NT". */
  NOTICE_OF_TAX_ADJUSTMENT("NT", "Notice of Tax Adjustment or Assessment (149)"),
  /** Cargo Insurance Advice of Shipment (362) — X12 code "OC". */
  CARGO_INSURANCE_ADVICE("OC", "Cargo Insurance Advice of Shipment (362)"),
  /** Order Group - Grocery (875, 876) — X12 code "OG". */
  ORDER_GROUP_GROCERY("OG", "Order Group - Grocery (875, 876)"),
  /** Organizational Relationships (816) — X12 code "OR". */
  ORGANIZATIONAL_RELATIONSHIPS("OR", "Organizational Relationships (816)"),
  /** Warehouse Shipping Order (940) — X12 code "OW". */
  WAREHOUSE_SHIPPING_ORDER("OW", "Warehouse Shipping Order (940)"),
  /** Price Authorization Acknowledgment/Status (845) — X12 code "PA". */
  PRICE_AUTHORIZATION("PA", "Price Authorization Acknowledgment/Status (845)"),
  /** Railroad Parameter Trace Registration (455) — X12 code "PB". */
  RAILROAD_PARAMETER_TRACE("PB", "Railroad Parameter Trace Registration (455)"),
  /** Purchase Order Change Request - Buyer Initiated (860) — X12 code "PC". */
  PURCHASE_ORDER_CHANGE_REQUEST("PC", "Purchase Order Change Request - Buyer Initiated (860)"),
  /** Product Activity Data (852) — X12 code "PD". */
  PRODUCT_ACTIVITY_DATA("PD", "Product Activity Data (852)"),
  /** Periodic Compensation (256) — X12 code "PE". */
  PERIODIC_COMPENSATION("PE", "Periodic Compensation (256)"),
  /** Annuity Activity (268) — X12 code "PF". */
  ANNUITY_ACTIVITY("PF", "Annuity Activity (268)"),
  /** Insurance Plan Description (100) — X12 code "PG". */
  INSURANCE_PLAN_DESCRIPTION("PG", "Insurance Plan Description (100)"),
  /** Pricing History (503) — X12 code "PH". */
  PRICING_HISTORY("PH", "Pricing History (503)"),
  /** Patient Information (275) — X12 code "PI". */
  PATIENT_INFORMATION("PI", "Patient Information (275)"),
  /** Project Schedule Reporting (806) — X12 code "PJ". */
  PROJECT_SCHEDULE("PJ", "Project Schedule Reporting (806)"),
  /** Project Cost Reporting (839) and Contractor Cost Data Reporting (196) — X12 code "PK". */
  PROJECT_COST_REPORTING(
      "PK", "Project Cost Reporting (839) and Contractor Cost Data Reporting (196)"),
  /** Railroad Problem Log Inquiry or Advice (452) — X12 code "PL". */
  RAILROAD_PROBLEM_LOG("PL", "Railroad Problem Log Inquiry or Advice (452)"),
  /** Product Source Information (244) — X12 code "PN". */
  PRODUCT_SOURCE_INFORMATION("PN", "Product Source Information (244)"),
  /** Purchase Order (850) — X12 code "PO". */
  PURCHASE_ORDER("PO", "Purchase Order (850)"),
  /** Property Damage Report (112) — X12 code "PQ". */
  PROPERTY_DAMAGE_REPORT("PQ", "Property Damage Report (112)"),
  /** Purchase Order Acknowledgment (855) — X12 code "PR". */
  PURCHASE_ORDER_ACKNOWLEDGMENT("PR", "Purchase Order Acknowledgment (855)"),
  /** Planning Schedule with Release Capability (830) — X12 code "PS". */
  PLANNING_SCHEDULE("PS", "Planning Schedule with Release Capability (830)"),
  /** Product Transfer and Resale Report (867) — X12 code "PT". */
  PRODUCT_TRANSFER("PT", "Product Transfer and Resale Report (867)"),
  /** Motor Carrier Shipment Pickup Notification (216) — X12 code "PU". */
  MOTOR_CARRIER_SHIPMENT_PICKUP("PU", "Motor Carrier Shipment Pickup Notification (216)"),
  /** Purchase Order Shipment Management Document (250) — X12 code "PV". */
  PURCHASE_ORDER_SHIPMENT("PV", "Purchase Order Shipment Management Document (250)"),
  /** Healthcare Provider Information (274) — X12 code "PW". */
  HEALTHCARE_PROVIDER_INFO("PW", "Healthcare Provider Information (274)"),
  /** Payment Cancellation Request (829) — X12 code "PY". */
  PAYMENT_CANCELLATION_REQUEST("PY", "Payment Cancellation Request (829)"),
  /** Product Information (878, 879, 888, 889, 896) — X12 code "QG". */
  PRODUCT_INFORMATION("QG", "Product Information (878, 879, 888, 889, 896)"),
  /** Transportation Carrier Shipment Status Message (214) — X12 code "QM". */
  TRANSPORTATION_CARRIER_SHIPMENT("QM", "Transportation Carrier Shipment Status Message (214)"),
  /** Ocean Shipment Status Information (313, 315) — X12 code "QO". */
  OCEAN_SHIPMENT_STATUS("QO", "Ocean Shipment Status Information (313, 315)"),
  /** Payment Order/Remittance Advice (820) — X12 code "RA". */
  PAYMENT_ORDER("RA", "Payment Order/Remittance Advice (820)"),
  /** Railroad Clearance (470) — X12 code "RB". */
  RAILROAD_CLEARANCE("RB", "Railroad Clearance (470)"),
  /** Receiving Advice/Acceptance Certificate (861) — X12 code "RC". */
  RECEIVING_ADVICE("RC", "Receiving Advice/Acceptance Certificate (861)"),
  /** Royalty Regulatory Report (185) — X12 code "RD". */
  ROYALTY_REGULATORY_REPORT("RD", "Royalty Regulatory Report (185)"),
  /** Warehouse Stock Receipt Advice (944) — X12 code "RE". */
  WAREHOUSE_STOCK_RECEIPT("RE", "Warehouse Stock Receipt Advice (944)"),
  /** Request for Routing Instructions (753) — X12 code "RF". */
  REQUEST_FOR_ROUTING("RF", "Request for Routing Instructions (753)"),
  /** Routing Instructions (754) — X12 code "RG". */
  ROUTING_INSTRUCTIONS("RG", "Routing Instructions (754)"),
  /** Railroad Reciprocal Switch File (433) — X12 code "RH". */
  RAILROAD_RECIPROCAL_SWITCH("RH", "Railroad Reciprocal Switch File (433)"),
  /** Routing and Carrier Instruction (853) — X12 code "RI". */
  ROUTING_AND_CARRIER("RI", "Routing and Carrier Instruction (853)"),
  /** Railroad Mark Register Update Activity (434) — X12 code "RJ". */
  RAILROAD_MARK_REGISTER("RJ", "Railroad Mark Register Update Activity (434)"),
  /** Standard Transportation Commodity Code Master (435) — X12 code "RK". */
  STANDARD_TRANSPORTATION_CODE("RK", "Standard Transportation Commodity Code Master (435)"),
  /** Rail Industrial Switch List (423) — X12 code "RL". */
  RAIL_INDUSTRIAL_SWITCH("RL", "Rail Industrial Switch List (423)"),
  /** Railroad Station Master File (431) — X12 code "RM". */
  RAILROAD_STATION_MASTER("RM", "Railroad Station Master File (431)"),
  /** Requisition Transaction (511) — X12 code "RN". */
  REQUISITION_TRANSACTION("RN", "Requisition Transaction (511)"),
  /** Ocean Booking Information (300, 301, 303) — X12 code "RO". */
  OCEAN_BOOKING_INFORMATION("RO", "Ocean Booking Information (300, 301, 303)"),
  /** Commission Sales Report (818) — X12 code "RP". */
  COMMISSION_SALES_REPORT("RP", "Commission Sales Report (818)"),
  /** Request for Quotation (840) and Procurement Notices (836) — X12 code "RQ". */
  REQUEST_FOR_QUOTATION("RQ", "Request for Quotation (840) and Procurement Notices (836)"),
  /** Response to Request For Quotation (843) — X12 code "RR". */
  RESPONSE_TO_REQUEST_FOR_QUOTATION("RR", "Response to Request For Quotation (843)"),
  /** Order Status Information (869, 870) — X12 code "RS". */
  ORDER_STATUS_INFORMATION("RS", "Order Status Information (869, 870)"),
  /** Report of Test Results (863) — X12 code "RT". */
  TEST_RESULTS_REPORT("RT", "Report of Test Results (863)"),
  /** Railroad Retirement Activity (429) — X12 code "RU". */
  RAILROAD_RETIREMENT("RU", "Railroad Retirement Activity (429)"),
  /** Railroad Junctions and Interchanges Activity (437) — X12 code "RV". */
  RAILROAD_JUNCTIONS("RV", "Railroad Junctions and Interchanges Activity (437)"),
  /** Rail Revenue Waybill (426) — X12 code "RW". */
  RAIL_REVENUE_WAYBILL("RW", "Rail Revenue Waybill (426)"),
  /** Rail Deprescription (432) — X12 code "RX". */
  RAIL_DEPRESCRIPTION("RX", "Rail Deprescription (432)"),
  /** Request for Student Educational Record (Transcript) (146) — X12 code "RY". */
  REQUEST_STUDENT_RECORD("RY", "Request for Student Educational Record (Transcript) (146)"),
  /** Response to Request for Student Educational Record (Transcript) (147) — X12 code "RZ". */
  RESPONSE_STUDENT_RECORD(
      "RZ", "Response to Request for Student Educational Record (Transcript) (147)"),
  /** Air Shipment Information (104) — X12 code "SA". */
  AIR_SHIPMENT_INFORMATION("SA", "Air Shipment Information (104)"),
  /** Rail Carrier Services Settlement (424) — X12 code "SB". */
  RAIL_CARRIER_SERVICES("SB", "Rail Carrier Services Settlement (424)"),
  /** Price/Sales Catalog (832) — X12 code "SC". */
  PRICE_SALES_CATALOG("SC", "Price/Sales Catalog (832)"),
  /** Student Loan Pre-Claims and Claims (191) — X12 code "SD". */
  STUDENT_LOAN_CLAIMS("SD", "Student Loan Pre-Claims and Claims (191)"),
  /** Shipper's Export Declaration (601) — X12 code "SE". */
  SHIPPERS_EXPORT_DECLARATION("SE", "Shipper's Export Declaration (601)"),
  /** Ship Notice/Manifest (856) — X12 code "SH". */
  SHIP_NOTICE_MANIFEST("SH", "Ship Notice/Manifest (856)"),
  /** Shipment Information (858) — X12 code "SI". */
  SHIPMENT_INFORMATION("SI", "Shipment Information (858)"),
  /** Transportation Automatic Equipment Identification (160) — X12 code "SJ". */
  TRANSPORTATION_EQUIPMENT_ID("SJ", "Transportation Automatic Equipment Identification (160)"),
  /** Student Aid Origination Record (135, 139) — X12 code "SL". */
  STUDENT_AID_ORIGINATION("SL", "Student Aid Origination Record (135, 139)"),
  /** Motor Carrier Load Tender (204) — X12 code "SM". */
  MOTOR_CARRIER_LOAD_TENDER("SM", "Motor Carrier Load Tender (204)"),
  /** Rail Route File Maintenance (475) — X12 code "SN". */
  RAIL_ROUTE_FILE("SN", "Rail Route File Maintenance (475)"),
  /**
   * Ocean Shipment Information (304, 309, 311, 317, 319, 322, 323, 324, 325, 326, 350, 352, 353,
   * 354, 355, 356, 357, 358, 361) — X12 code "SO".
   */
  OCEAN_SHIPMENT_INFORMATION(
      "SO",
      "Ocean Shipment Information (304, 309, 311, 317, 319, 322, 323, 324, 325, 326, 350, 352, 353, 354, 355, 356, 357, 358, 361)"),
  /** Specifications/Technical Information (841) — X12 code "SP". */
  SPECIFICATIONS_TECHNICAL("SP", "Specifications/Technical Information (841)"),
  /** Production Sequence (866) — X12 code "SQ". */
  PRODUCTION_SEQUENCE("SQ", "Production Sequence (866)"),
  /** Rail Carrier Shipment Information (404, 419) — X12 code "SR". */
  RAIL_CARRIER_SHIPMENT("SR", "Rail Carrier Shipment Information (404, 419)"),
  /** Shipping Schedule (862) — X12 code "SS". */
  SHIPPING_SCHEDULE("SS", "Shipping Schedule (862)"),
  /** Railroad Service Commitment Advice (453) — X12 code "ST". */
  RAILROAD_SERVICE_COMMITMENT("ST", "Railroad Service Commitment Advice (453)"),
  /** Account Assignment/Inquiry and Service/Status (248) — X12 code "SU". */
  ACCOUNT_ASSIGNMENT("SU", "Account Assignment/Inquiry and Service/Status (248)"),
  /** Student Enrollment Verification (190) — X12 code "SV". */
  STUDENT_ENROLLMENT_VERIFICATION("SV", "Student Enrollment Verification (190)"),
  /** Warehouse Shipping Advice (945) — X12 code "SW". */
  WAREHOUSE_SHIPPING_ADVICE("SW", "Warehouse Shipping Advice (945)"),
  /** Electronic Filing of Tax Return Data Acknowledgment (151) — X12 code "TA". */
  TAX_RETURN_ACKNOWLEDGMENT("TA", "Electronic Filing of Tax Return Data Acknowledgment (151)"),
  /** Trailer or Container Repair Billing (412) — X12 code "TB". */
  TRAILER_CONTAINER_REPAIR("TB", "Trailer or Container Repair Billing (412)"),
  /** Trading Partner Profile (838) — X12 code "TD". */
  TRADING_PARTNER_PROFILE("TD", "Trading Partner Profile (838)"),
  /** Tax or Fee Exemption Certification (283) — X12 code "TE". */
  TAX_FEE_EXEMPTION("TE", "Tax or Fee Exemption Certification (283)"),
  /** Electronic Filing of Tax Return Data (813) — X12 code "TF". */
  ELECTRONIC_TAX_FILING("TF", "Electronic Filing of Tax Return Data (813)"),
  /** Tax Information Exchange (826) — X12 code "TI". */
  TAX_INFORMATION_EXCHANGE("TI", "Tax Information Exchange (826)"),
  /** Tax Jurisdiction Sourcing (158) — X12 code "TJ". */
  TAX_JURISDICTION_SOURCING("TJ", "Tax Jurisdiction Sourcing (158)"),
  /** Motor Carrier Delivery Trailer Manifest (212) — X12 code "TM". */
  MOTOR_CARRIER_DELIVERY("TM", "Motor Carrier Delivery Trailer Manifest (212)"),
  /** Tax Rate Notification (150) — X12 code "TN". */
  TAX_RATE_NOTIFICATION("TN", "Tax Rate Notification (150)"),
  /** Real Estate Title Services (197, 199, 265, 485, 486) — X12 code "TO". */
  REAL_ESTATE_TITLE("TO", "Real Estate Title Services (197, 199, 265, 485, 486)"),
  /** Rail Rate Transactions (460, 463, 466, 468, 485, 486, 490, 492, 494) — X12 code "TP". */
  RAIL_RATE_TRANSACTIONS(
      "TP", "Rail Rate Transactions (460, 463, 466, 468, 485, 486, 490, 492, 494)"),
  /** Train Sheet (161) — X12 code "TR". */
  TRAIN_SHEET("TR", "Train Sheet (161)"),
  /** Transportation Services Tender (602) — X12 code "TS". */
  TRANSPORTATION_SERVICES_TENDER("TS", "Transportation Services Tender (602)"),
  /** Educational Testing and Prospect Request and Report (138) — X12 code "TT". */
  EDUCATIONAL_TESTING("TT", "Educational Testing and Prospect Request and Report (138)"),
  /** Trailer Usage Report (227) — X12 code "TU". */
  TRAILER_USAGE_REPORT("TU", "Trailer Usage Report (227)"),
  /** Text Message (864) — X12 code "TX". */
  TEXT_MESSAGE("TX", "Text Message (864)"),
  /** Retail Account Characteristics (885) — X12 code "UA". */
  RETAIL_ACCOUNT_CHARACTERISTICS("UA", "Retail Account Characteristics (885)"),
  /** Customer Call Reporting (886) — X12 code "UB". */
  CUSTOMER_CALL_REPORTING("UB", "Customer Call Reporting (886)"),
  /** Secured Interest Filing (154) — X12 code "UC". */
  SECURED_INTEREST_FILING("UC", "Secured Interest Filing (154)"),
  /** Deduction Research Report (891) — X12 code "UD". */
  DEDUCTION_RESEARCH_REPORT("UD", "Deduction Research Report (891)"),
  /** Underwriting Information Services (255) — X12 code "UI". */
  UNDERWRITING_INFORMATION("UI", "Underwriting Information Services (255)"),
  /** Motor Carrier Pickup Manifest (215) — X12 code "UP". */
  MOTOR_CARRIER_PICKUP_MANIFEST("UP", "Motor Carrier Pickup Manifest (215)"),
  /** Insurance Underwriting Requirements Reporting (186) — X12 code "UW". */
  INSURANCE_UNDERWRITING("UW", "Insurance Underwriting Requirements Reporting (186)"),
  /** Vehicle Application Advice (126) — X12 code "VA". */
  VEHICLE_APPLICATION_ADVICE("VA", "Vehicle Application Advice (126)"),
  /** Vehicle Baying Order (127) — X12 code "VB". */
  VEHICLE_BAYING_ORDER("VB", "Vehicle Baying Order (127)"),
  /** Vehicle Shipping Order (120) — X12 code "VC". */
  VEHICLE_SHIPPING_ORDER("VC", "Vehicle Shipping Order (120)"),
  /** Vehicle Damage (124) — X12 code "VD". */
  VEHICLE_DAMAGE("VD", "Vehicle Damage (124)"),
  /** Vessel Content Details (109) — X12 code "VE". */
  VESSEL_CONTENT_DETAILS("VE", "Vessel Content Details (109)"),
  /** Vehicle Carrier Rate Update (129) — X12 code "VH". */
  VEHICLE_CARRIER_RATE("VH", "Vehicle Carrier Rate Update (129)"),
  /** Voter Registration Information (280) — X12 code "VI". */
  VOTER_REGISTRATION("VI", "Voter Registration Information (280)"),
  /** Vehicle Service (121) — X12 code "VS". */
  VEHICLE_SERVICE("VS", "Vehicle Service (121)"),
  /** Product Service Transaction Sets (140, 141, 142, 143) — X12 code "WA". */
  PRODUCT_SERVICE("WA", "Product Service Transaction Sets (140, 141, 142, 143)"),
  /** Rail Carrier Waybill Interchange (417) — X12 code "WB". */
  RAIL_WAYBILL_INTERCHANGE("WB", "Rail Carrier Waybill Interchange (417)"),
  /** Vendor Performance Review (501) — X12 code "WG". */
  VENDOR_PERFORMANCE("WG", "Vendor Performance Review (501)"),
  /** Wage Determination (288) — X12 code "WI". */
  WAGE_DETERMINATION("WI", "Wage Determination (288)"),
  /** Well Information (625) — X12 code "WL". */
  WELL_INFORMATION("WL", "Well Information (625)"),
  /** Shipment Weights (440) — X12 code "WR". */
  SHIPMENT_WEIGHTS("WR", "Shipment Weights (440)"),
  /** Rail Waybill Request (425) — X12 code "WT". */
  RAIL_WAYBILL_REQUEST("WT", "Rail Waybill Request (425)");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<FunctionalIdentifierCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("acknowledgment", FUNCTIONAL_ACKNOWLEDGMENT)));
  }

  FunctionalIdentifierCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a FunctionalIdentifierCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching FunctionalIdentifierCode
   * @throws IllegalArgumentException if no match is found
   */
  public static FunctionalIdentifierCode fromString(String input) {
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
