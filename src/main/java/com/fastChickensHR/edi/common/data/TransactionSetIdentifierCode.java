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
 * Enumeration representing Transaction Set Identifier Codes used in EDI formats.
 * These codes identify the transaction set type in the ST segment (ST01 element).
 */
@Getter
public enum TransactionSetIdentifierCode implements EdiCodeEnum {
    INSURANCE_PLAN_DESCRIPTION("100", "Insurance Plan Description"),
    NAME_AND_ADDRESS_LISTS("101", "Name and Address Lists"),
    ASSOCIATED_DATA("102", "Associated Data"),
    ABANDONED_PROPERTY_FILINGS("103", "Abandoned Property Filings"),
    AIR_SHIPMENT_INFORMATION("104", "Air Shipment Information"),
    BUSINESS_ENTITY_FILINGS("105", "Business Entity Filings"),
    MOTOR_CARRIER_RATE_PROPOSAL("106", "Motor Carrier Rate Proposal"),
    REQUEST_FOR_MOTOR_CARRIER_RATE_PROPOSAL("107", "Request for Motor Carrier Rate Proposal"),
    RESPONSE_TO_MOTOR_CARRIER_RATE_PROPOSAL("108", "Response to a Motor Carrier Rate Proposal"),
    VESSEL_CONTENT_DETAILS("109", "Vessel Content Details"),
    AIR_FREIGHT_DETAILS_AND_INVOICE("110", "Air Freight Details and Invoice"),
    INDIVIDUAL_INSURANCE_POLICY("111", "Individual Insurance Policy and Client Information"),
    PROPERTY_DAMAGE_REPORT("112", "Property Damage Report"),
    ELECTION_CAMPAIGN_REPORTING("113", "Election Campaign and Lobbyist Reporting"),
    VEHICLE_SHIPPING_ORDER("120", "Vehicle Shipping Order"),
    VEHICLE_SERVICE("121", "Vehicle Service"),
    VEHICLE_DAMAGE("124", "Vehicle Damage"),
    MULTILEVEL_RAILCAR_LOAD_DETAILS("125", "Multilevel Railcar Load Details"),
    VEHICLE_APPLICATION_ADVICE("126", "Vehicle Application Advice"),
    VEHICLE_BAYING_ORDER("127", "Vehicle Baying Order"),
    DEALER_INFORMATION("128", "Dealer Information"),
    VEHICLE_CARRIER_RATE_UPDATE("129", "Vehicle Carrier Rate Update"),
    STUDENT_EDUCATIONAL_RECORD("130", "Student Educational Record (Transcript)"),
    STUDENT_EDUCATIONAL_RECORD_ACKNOWLEDGMENT("131", "Student Educational Record (Transcript) Acknowledgment"),
    HUMAN_RESOURCE_INFORMATION("132", "Human Resource Information"),
    EDUCATIONAL_INSTITUTION_RECORD("133", "Educational Institution Record"),
    STUDENT_AID_ORIGINATION_RECORD("135", "Student Aid Origination Record"),
    EDUCATIONAL_TESTING("138", "Educational Testing and Prospect Request and Report"),
    STUDENT_LOAN_GUARANTEE_RESULT("139", "Student Loan Guarantee Result"),
    PRODUCT_REGISTRATION("140", "Product Registration"),
    PRODUCT_SERVICE_CLAIM_RESPONSE("141", "Product Service Claim Response"),
    PRODUCT_SERVICE_CLAIM("142", "Product Service Claim"),
    PRODUCT_SERVICE_NOTIFICATION("143", "Product Service Notification"),
    STUDENT_LOAN_TRANSFER("144", "Student Loan Transfer and Status Verification"),
    REQUEST_FOR_STUDENT_RECORD("146", "Request for Student Educational Record (Transcript)"),
    RESPONSE_TO_REQUEST_FOR_STUDENT_RECORD("147", "Response to Request for Student Educational Record (Transcript)"),
    INJURY_REPORT("148", "Report of Injury, Illness or Incident"),
    TAX_ADJUSTMENT_NOTICE("149", "Notice of Tax Adjustment or Assessment"),
    TAX_RATE_NOTIFICATION("150", "Tax Rate Notification"),
    TAX_RETURN_DATA_ACKNOWLEDGMENT("151", "Electronic Filing of Tax Return Data Acknowledgment"),
    STATISTICAL_GOVERNMENT_INFORMATION("152", "Statistical Government Information"),
    UNEMPLOYMENT_INSURANCE_TAX_CLAIM("153", "Unemployment Insurance Tax Claim or Charge Information"),
    SECURED_INTEREST_FILING("154", "Secured Interest Filing"),
    BUSINESS_CREDIT_REPORT("155", "Business Credit Report"),
    NOTICE_OF_POWER_OF_ATTORNEY("157", "Notice of Power of Attorney"),
    TAX_JURISDICTION_SOURCING("158", "Tax Jurisdiction Sourcing"),
    MOTION_PICTURE_BOOKING_CONFIRMATION("159", "Motion Picture Booking Confirmation"),
    TRANSPORTATION_EQUIPMENT_IDENTIFICATION("160", "Transportation Automatic Equipment Identification"),
    TRAIN_SHEET("161", "Train Sheet"),
    TRANSPORTATION_APPOINTMENT_SCHEDULE("163", "Transportation Appointment Schedule Information"),
    REVENUE_RECEIPTS_STATEMENT("170", "Revenue Receipts Statement"),
    COURT_NOTICE("175", "Court and Law Enforcement Notice"),
    COURT_SUBMISSION("176", "Court Submission"),
    ENVIRONMENTAL_COMPLIANCE_REPORTING("179", "Environmental Compliance Reporting"),
    RETURN_MERCHANDISE_AUTHORIZATION("180", "Return Merchandise Authorization and Notification"),
    ROYALTY_REGULATORY_REPORT("185", "Royalty Regulatory Report"),
    INSURANCE_UNDERWRITING_REQUIREMENTS("186", "Insurance Underwriting Requirements Reporting"),
    PREMIUM_AUDIT_REQUEST("187", "Premium Audit Request and Return"),
    EDUCATIONAL_COURSE_INVENTORY("188", "Educational Course Inventory"),
    ADMISSION_APPLICATION("189", "Application for Admission to Educational Institutions"),
    STUDENT_ENROLLMENT_VERIFICATION("190", "Student Enrollment Verification"),
    STUDENT_LOAN_CLAIMS("191", "Student Loan Pre-Claims and Claims"),
    GRANT_APPLICATION("194", "Grant or Assistance Application"),
    FCC_LICENSE_APPLICATION("195", "Federal Communications Commission (FCC) License Application"),
    CONTRACTOR_COST_DATA_REPORTING("196", "Contractor Cost Data Reporting"),
    REAL_ESTATE_TITLE_EVIDENCE("197", "Real Estate Title Evidence"),
    LOAN_VERIFICATION_INFORMATION("198", "Loan Verification Information"),
    REAL_ESTATE_SETTLEMENT_INFORMATION("199", "Real Estate Settlement Information"),
    // Add more codes as needed
    BENEFIT_ENROLLMENT_AND_MAINTENANCE("834", "Benefit Enrollment and Maintenance");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<TransactionSetIdentifierCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                TransactionSetIdentifierCode.class,
                "Transaction Set Identifier Code",
                Map.ofEntries(
                        Map.entry("benefit enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                        Map.entry("enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                        Map.entry("healthcare enrollment", BENEFIT_ENROLLMENT_AND_MAINTENANCE),
                        Map.entry("human resources", HUMAN_RESOURCE_INFORMATION),
                        Map.entry("hr", HUMAN_RESOURCE_INFORMATION)
                )
        );
    }

    TransactionSetIdentifierCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a TransactionSetIdentifierCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching TransactionSetIdentifierCode
     * @throws IllegalArgumentException if no match is found
     */
    public static TransactionSetIdentifierCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}