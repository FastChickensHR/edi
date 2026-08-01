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
 * Code values for the Transaction Set Identifier Code (ST01, X12 data element 143) in the
 * Transaction Set Header (ST). Names the transaction set that follows; the X12 834 transaction uses
 * {@link #BENEFIT_ENROLLMENT_AND_MAINTENANCE} ("834") under implementation guide 005010X220A1.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum TransactionSetIdentifierCode implements EdiCodeEnum {
  /** Insurance Plan Description — X12 code "100". */
  INSURANCE_PLAN_DESCRIPTION("100", "Insurance Plan Description"),
  /** Name and Address Lists — X12 code "101". */
  NAME_AND_ADDRESS_LISTS("101", "Name and Address Lists"),
  /** Associated Data — X12 code "102". */
  ASSOCIATED_DATA("102", "Associated Data"),
  /** Abandoned Property Filings — X12 code "103". */
  ABANDONED_PROPERTY_FILINGS("103", "Abandoned Property Filings"),
  /** Air Shipment Information — X12 code "104". */
  AIR_SHIPMENT_INFORMATION("104", "Air Shipment Information"),
  /** Business Entity Filings — X12 code "105". */
  BUSINESS_ENTITY_FILINGS("105", "Business Entity Filings"),
  /** Motor Carrier Rate Proposal — X12 code "106". */
  MOTOR_CARRIER_RATE_PROPOSAL("106", "Motor Carrier Rate Proposal"),
  /** Request for Motor Carrier Rate Proposal — X12 code "107". */
  REQUEST_FOR_MOTOR_CARRIER_RATE_PROPOSAL("107", "Request for Motor Carrier Rate Proposal"),
  /** Response to a Motor Carrier Rate Proposal — X12 code "108". */
  RESPONSE_TO_MOTOR_CARRIER_RATE_PROPOSAL("108", "Response to a Motor Carrier Rate Proposal"),
  /** Vessel Content Details — X12 code "109". */
  VESSEL_CONTENT_DETAILS("109", "Vessel Content Details"),
  /** Air Freight Details and Invoice — X12 code "110". */
  AIR_FREIGHT_DETAILS_AND_INVOICE("110", "Air Freight Details and Invoice"),
  /** Individual Insurance Policy and Client Information — X12 code "111". */
  INDIVIDUAL_INSURANCE_POLICY("111", "Individual Insurance Policy and Client Information"),
  /** Property Damage Report — X12 code "112". */
  PROPERTY_DAMAGE_REPORT("112", "Property Damage Report"),
  /** Election Campaign and Lobbyist Reporting — X12 code "113". */
  ELECTION_CAMPAIGN_REPORTING("113", "Election Campaign and Lobbyist Reporting"),
  /** Vehicle Shipping Order — X12 code "120". */
  VEHICLE_SHIPPING_ORDER("120", "Vehicle Shipping Order"),
  /** Vehicle Service — X12 code "121". */
  VEHICLE_SERVICE("121", "Vehicle Service"),
  /** Vehicle Damage — X12 code "124". */
  VEHICLE_DAMAGE("124", "Vehicle Damage"),
  /** Multilevel Railcar Load Details — X12 code "125". */
  MULTILEVEL_RAILCAR_LOAD_DETAILS("125", "Multilevel Railcar Load Details"),
  /** Vehicle Application Advice — X12 code "126". */
  VEHICLE_APPLICATION_ADVICE("126", "Vehicle Application Advice"),
  /** Vehicle Baying Order — X12 code "127". */
  VEHICLE_BAYING_ORDER("127", "Vehicle Baying Order"),
  /** Dealer Information — X12 code "128". */
  DEALER_INFORMATION("128", "Dealer Information"),
  /** Vehicle Carrier Rate Update — X12 code "129". */
  VEHICLE_CARRIER_RATE_UPDATE("129", "Vehicle Carrier Rate Update"),
  /** Student Educational Record (Transcript) — X12 code "130". */
  STUDENT_EDUCATIONAL_RECORD("130", "Student Educational Record (Transcript)"),
  /** Student Educational Record (Transcript) Acknowledgment — X12 code "131". */
  STUDENT_EDUCATIONAL_RECORD_ACKNOWLEDGMENT(
      "131", "Student Educational Record (Transcript) Acknowledgment"),
  /** Human Resource Information — X12 code "132". */
  HUMAN_RESOURCE_INFORMATION("132", "Human Resource Information"),
  /** Educational Institution Record — X12 code "133". */
  EDUCATIONAL_INSTITUTION_RECORD("133", "Educational Institution Record"),
  /** Student Aid Origination Record — X12 code "135". */
  STUDENT_AID_ORIGINATION_RECORD("135", "Student Aid Origination Record"),
  /** Educational Testing and Prospect Request and Report — X12 code "138". */
  EDUCATIONAL_TESTING("138", "Educational Testing and Prospect Request and Report"),
  /** Student Loan Guarantee Result — X12 code "139". */
  STUDENT_LOAN_GUARANTEE_RESULT("139", "Student Loan Guarantee Result"),
  /** Product Registration — X12 code "140". */
  PRODUCT_REGISTRATION("140", "Product Registration"),
  /** Product Service Claim Response — X12 code "141". */
  PRODUCT_SERVICE_CLAIM_RESPONSE("141", "Product Service Claim Response"),
  /** Product Service Claim — X12 code "142". */
  PRODUCT_SERVICE_CLAIM("142", "Product Service Claim"),
  /** Product Service Notification — X12 code "143". */
  PRODUCT_SERVICE_NOTIFICATION("143", "Product Service Notification"),
  /** Student Loan Transfer and Status Verification — X12 code "144". */
  STUDENT_LOAN_TRANSFER("144", "Student Loan Transfer and Status Verification"),
  /** Request for Student Educational Record (Transcript) — X12 code "146". */
  REQUEST_FOR_STUDENT_RECORD("146", "Request for Student Educational Record (Transcript)"),
  /** Response to Request for Student Educational Record (Transcript) — X12 code "147". */
  RESPONSE_TO_REQUEST_FOR_STUDENT_RECORD(
      "147", "Response to Request for Student Educational Record (Transcript)"),
  /** Report of Injury, Illness or Incident — X12 code "148". */
  INJURY_REPORT("148", "Report of Injury, Illness or Incident"),
  /** Notice of Tax Adjustment or Assessment — X12 code "149". */
  TAX_ADJUSTMENT_NOTICE("149", "Notice of Tax Adjustment or Assessment"),
  /** Tax Rate Notification — X12 code "150". */
  TAX_RATE_NOTIFICATION("150", "Tax Rate Notification"),
  /** Electronic Filing of Tax Return Data Acknowledgment — X12 code "151". */
  TAX_RETURN_DATA_ACKNOWLEDGMENT("151", "Electronic Filing of Tax Return Data Acknowledgment"),
  /** Statistical Government Information — X12 code "152". */
  STATISTICAL_GOVERNMENT_INFORMATION("152", "Statistical Government Information"),
  /** Unemployment Insurance Tax Claim or Charge Information — X12 code "153". */
  UNEMPLOYMENT_INSURANCE_TAX_CLAIM("153", "Unemployment Insurance Tax Claim or Charge Information"),
  /** Secured Interest Filing — X12 code "154". */
  SECURED_INTEREST_FILING("154", "Secured Interest Filing"),
  /** Business Credit Report — X12 code "155". */
  BUSINESS_CREDIT_REPORT("155", "Business Credit Report"),
  /** Notice of Power of Attorney — X12 code "157". */
  NOTICE_OF_POWER_OF_ATTORNEY("157", "Notice of Power of Attorney"),
  /** Tax Jurisdiction Sourcing — X12 code "158". */
  TAX_JURISDICTION_SOURCING("158", "Tax Jurisdiction Sourcing"),
  /** Motion Picture Booking Confirmation — X12 code "159". */
  MOTION_PICTURE_BOOKING_CONFIRMATION("159", "Motion Picture Booking Confirmation"),
  /** Transportation Automatic Equipment Identification — X12 code "160". */
  TRANSPORTATION_EQUIPMENT_IDENTIFICATION(
      "160", "Transportation Automatic Equipment Identification"),
  /** Train Sheet — X12 code "161". */
  TRAIN_SHEET("161", "Train Sheet"),
  /** Transportation Appointment Schedule Information — X12 code "163". */
  TRANSPORTATION_APPOINTMENT_SCHEDULE("163", "Transportation Appointment Schedule Information"),
  /** Revenue Receipts Statement — X12 code "170". */
  REVENUE_RECEIPTS_STATEMENT("170", "Revenue Receipts Statement"),
  /** Court and Law Enforcement Notice — X12 code "175". */
  COURT_NOTICE("175", "Court and Law Enforcement Notice"),
  /** Court Submission — X12 code "176". */
  COURT_SUBMISSION("176", "Court Submission"),
  /** Environmental Compliance Reporting — X12 code "179". */
  ENVIRONMENTAL_COMPLIANCE_REPORTING("179", "Environmental Compliance Reporting"),
  /** Return Merchandise Authorization and Notification — X12 code "180". */
  RETURN_MERCHANDISE_AUTHORIZATION("180", "Return Merchandise Authorization and Notification"),
  /** Royalty Regulatory Report — X12 code "185". */
  ROYALTY_REGULATORY_REPORT("185", "Royalty Regulatory Report"),
  /** Insurance Underwriting Requirements Reporting — X12 code "186". */
  INSURANCE_UNDERWRITING_REQUIREMENTS("186", "Insurance Underwriting Requirements Reporting"),
  /** Premium Audit Request and Return — X12 code "187". */
  PREMIUM_AUDIT_REQUEST("187", "Premium Audit Request and Return"),
  /** Educational Course Inventory — X12 code "188". */
  EDUCATIONAL_COURSE_INVENTORY("188", "Educational Course Inventory"),
  /** Application for Admission to Educational Institutions — X12 code "189". */
  ADMISSION_APPLICATION("189", "Application for Admission to Educational Institutions"),
  /** Student Enrollment Verification — X12 code "190". */
  STUDENT_ENROLLMENT_VERIFICATION("190", "Student Enrollment Verification"),
  /** Student Loan Pre-Claims and Claims — X12 code "191". */
  STUDENT_LOAN_CLAIMS("191", "Student Loan Pre-Claims and Claims"),
  /** Grant or Assistance Application — X12 code "194". */
  GRANT_APPLICATION("194", "Grant or Assistance Application"),
  /** Federal Communications Commission (FCC) License Application — X12 code "195". */
  FCC_LICENSE_APPLICATION("195", "Federal Communications Commission (FCC) License Application"),
  /** Contractor Cost Data Reporting — X12 code "196". */
  CONTRACTOR_COST_DATA_REPORTING("196", "Contractor Cost Data Reporting"),
  /** Real Estate Title Evidence — X12 code "197". */
  REAL_ESTATE_TITLE_EVIDENCE("197", "Real Estate Title Evidence"),
  /** Loan Verification Information — X12 code "198". */
  LOAN_VERIFICATION_INFORMATION("198", "Loan Verification Information"),
  /** Real Estate Settlement Information — X12 code "199". */
  REAL_ESTATE_SETTLEMENT_INFORMATION("199", "Real Estate Settlement Information"),
  // Add more codes as needed
  /** Benefit Enrollment and Maintenance — X12 code "834". */
  BENEFIT_ENROLLMENT_AND_MAINTENANCE("834", "Benefit Enrollment and Maintenance");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<TransactionSetIdentifierCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            TransactionSetIdentifierCode.class,
            "Transaction Set Identifier Code",
            Map.ofEntries(
                Map.entry("benefit enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Map.entry("enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Map.entry("healthcare enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                Map.entry("human resources", HUMAN_RESOURCE_INFORMATION),
                Map.entry("hr", HUMAN_RESOURCE_INFORMATION)));
  }

  TransactionSetIdentifierCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a TransactionSetIdentifierCode instance from any input string. Matches against codes,
   * names, descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching TransactionSetIdentifierCode
   * @throws IllegalArgumentException if no match is found
   */
  public static TransactionSetIdentifierCode fromString(String input) {
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
