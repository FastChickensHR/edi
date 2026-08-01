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
 * Code values for the X12 Entity Identifier Code (data element 98), which identifies the
 * organizational entity, physical location, property, or individual described by a name loop. In
 * the X12 834 (005010X220A1) it appears as NM101 (and N101) to label parties such as the sponsor,
 * payer, and insured.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum EntityIdentifierCode implements EdiCodeEnum {
  /** Alternate Insurer — X12 code "00". */
  ALTERNATE_INSURER("00", "Alternate Insurer"),
  /** Comparable Rentals — X12 code "0A". */
  COMPARABLE_RENTALS("0A", "Comparable Rentals"),
  /** Interim Funding Organization — X12 code "0B". */
  INTERIM_FUNDING_ORGANIZATION("0B", "Interim Funding Organization"),
  /** Non-occupant Co-borrower — X12 code "0D". */
  NON_OCCUPANT_CO_BORROWER("0D", "Non-occupant Co-borrower"),
  /** List Owner — X12 code "0E". */
  LIST_OWNER("0E", "List Owner"),
  /** List Mailer — X12 code "0F". */
  LIST_MAILER("0F", "List Mailer"),
  /** Primary Electronic Business Contact — X12 code "0G". */
  PRIMARY_ELECTRONIC_BUSINESS_CONTACT("0G", "Primary Electronic Business Contact"),
  /** State Division — X12 code "0H". */
  STATE_DIVISION("0H", "State Division"),
  /** Alternate Electronic Business Contact — X12 code "0I". */
  ALTERNATE_ELECTRONIC_BUSINESS_CONTACT("0I", "Alternate Electronic Business Contact"),
  /** Primary Practice Location — X12 code "0J". */
  PRIMARY_PRACTICE_LOCATION("0J", "Primary Practice Location"),
  /** Party to Declare Goods — X12 code "0P". */
  PARTY_TO_DECLARE_GOODS("0P", "Party to Declare Goods"),
  /** Loan Applicant — X12 code "01". */
  LOAN_APPLICANT("01", "Loan Applicant"),
  /** Pumper — X12 code "001". */
  PUMPER("001", "Pumper"),
  /** Subgroup — X12 code "1A". */
  SUBGROUP("1A", "Subgroup"),
  /** Applicant — X12 code "1B". */
  APPLICANT("1B", "Applicant"),
  /** Group Purchasing Organization (GPO) — X12 code "1C". */
  GROUP_PURCHASING_ORGANIZATION("1C", "Group Purchasing Organization (GPO)"),
  /** Co-operative — X12 code "1D". */
  COOPERATIVE("1D", "Co-operative"),
  /** Health Maintenance Organization (HMO) — X12 code "1E". */
  HEALTH_MAINTENANCE_ORGANIZATION("1E", "Health Maintenance Organization (HMO)"),
  /** Alliance — X12 code "1F". */
  ALLIANCE("1F", "Alliance"),
  /** Oncology Center — X12 code "1G". */
  ONCOLOGY_CENTER("1G", "Oncology Center"),
  /** Kidney Dialysis Unit — X12 code "1H". */
  KIDNEY_DIALYSIS_UNIT("1H", "Kidney Dialysis Unit"),
  /** Preferred Provider Organization (PPO) — X12 code "1I". */
  PREFERRED_PROVIDER_ORGANIZATION("1I", "Preferred Provider Organization (PPO)"),
  /**
   * ConnectionThe name of pipeline company to which a well, lease or field is connected — X12 code
   * "1J".
   */
  CONNECTION(
      "1J", "ConnectionThe name of pipeline company to which a well, lease or field is connected"),
  /** Franchisor — X12 code "1K". */
  FRANCHISOR("1K", "Franchisor"),
  /** Franchisee — X12 code "1L". */
  FRANCHISEE("1L", "Franchisee"),
  /** Previous Group — X12 code "1M". */
  PREVIOUS_GROUP("1M", "Previous Group"),
  /** Shareholder — X12 code "1N". */
  SHAREHOLDER("1N", "Shareholder"),
  /** Acute Care Hospital — X12 code "1O". */
  ACUTE_CARE_HOSPITAL("1O", "Acute Care Hospital"),
  /** Provider — X12 code "1P". */
  PROVIDER("1P", "Provider"),
  /** Military Facility — X12 code "1Q". */
  MILITARY_FACILITY("1Q", "Military Facility"),
  /** University, College or School — X12 code "1R". */
  UNIVERSITY_COLLEGE_SCHOOL("1R", "University, College or School"),
  /** Outpatient Surgicenter — X12 code "1S". */
  OUTPATIENT_SURGICENTER("1S", "Outpatient Surgicenter"),
  /** Physician, Clinic or Group Practice — X12 code "1T". */
  PHYSICIAN_CLINIC_GROUP_PRACTICE("1T", "Physician, Clinic or Group Practice"),
  /** Long Term Care Facility — X12 code "1U". */
  LONG_TERM_CARE_FACILITY("1U", "Long Term Care Facility"),
  /** Extended Care Facility — X12 code "1V". */
  EXTENDED_CARE_FACILITY("1V", "Extended Care Facility"),
  /** Psychiatric Health Facility — X12 code "1W". */
  PSYCHIATRIC_HEALTH_FACILITY("1W", "Psychiatric Health Facility"),
  /** Laboratory — X12 code "1X". */
  LABORATORY("1X", "Laboratory"),
  /** Retail Pharmacy — X12 code "1Y". */
  RETAIL_PHARMACY("1Y", "Retail Pharmacy"),
  /** Home Health Care — X12 code "1Z". */
  HOME_HEALTH_CARE("1Z", "Home Health Care"),
  /** Loan Broker — X12 code "02". */
  LOAN_BROKER("02", "Loan Broker"),
  /** Surface Management Entity — X12 code "002". */
  SURFACE_MANAGEMENT_ENTITY("002", "Surface Management Entity"),
  /** Federal, State, County or City Facility — X12 code "2A". */
  FEDERAL_STATE_COUNTY_CITY_FACILITY("2A", "Federal, State, County or City Facility"),
  /** Third-Party Administrator — X12 code "2B". */
  THIRD_PARTY_ADMINISTRATOR("2B", "Third-Party Administrator"),
  /** Co-Participant — X12 code "2C". */
  CO_PARTICIPANT("2C", "Co-Participant"),
  /** Miscellaneous Health Care Facility — X12 code "2D". */
  MISCELLANEOUS_HEALTH_CARE_FACILITY("2D", "Miscellaneous Health Care Facility"),
  /** Non-Health Care Miscellaneous Facility — X12 code "2E". */
  NON_HEALTH_CARE_MISCELLANEOUS_FACILITY("2E", "Non-Health Care Miscellaneous Facility"),
  /** State — X12 code "2F". */
  STATE("2F", "State"),
  /** Assigner — X12 code "2G". */
  ASSIGNER("2G", "Assigner"),
  /** Hospital District or Authority — X12 code "2H". */
  HOSPITAL_DISTRICT_OR_AUTHORITY("2H", "Hospital District or Authority"),
  /** Church Operated Facility — X12 code "2I". */
  CHURCH_OPERATED_FACILITY("2I", "Church Operated Facility"),
  /** Individual — X12 code "2J". */
  INDIVIDUAL("2J", "Individual"),
  /** Partnership — X12 code "2K". */
  PARTNERSHIP("2K", "Partnership"),
  /** Corporation — X12 code "2L". */
  CORPORATION("2L", "Corporation"),
  /** Air Force Facility — X12 code "2M". */
  AIR_FORCE_FACILITY("2M", "Air Force Facility"),
  /** Army Facility — X12 code "2N". */
  ARMY_FACILITY("2N", "Army Facility"),
  /** Navy Facility — X12 code "2O". */
  NAVY_FACILITY("2O", "Navy Facility"),
  /** Public Health Service Facility — X12 code "2P". */
  PUBLIC_HEALTH_SERVICE_FACILITY("2P", "Public Health Service Facility"),
  /** Veterans Administration Facility — X12 code "2Q". */
  VETERANS_ADMINISTRATION_FACILITY("2Q", "Veterans Administration Facility"),
  /** Federal Facility — X12 code "2R". */
  FEDERAL_FACILITY("2R", "Federal Facility"),
  /** Public Health Service Indian Service Facility — X12 code "2S". */
  PUBLIC_HEALTH_SERVICE_INDIAN_SERVICE_FACILITY(
      "2S", "Public Health Service Indian Service Facility"),
  /** Department of Justice Facility — X12 code "2T". */
  DEPARTMENT_OF_JUSTICE_FACILITY("2T", "Department of Justice Facility"),
  /** Other Not-for-profit Facility — X12 code "2U". */
  OTHER_NOT_FOR_PROFIT_FACILITY("2U", "Other Not-for-profit Facility"),
  /** Individual for-profit Facility — X12 code "2V". */
  INDIVIDUAL_FOR_PROFIT_FACILITY("2V", "Individual for-profit Facility"),
  /** Partnership for-profit Facility — X12 code "2W". */
  PARTNERSHIP_FOR_PROFIT_FACILITY("2W", "Partnership for-profit Facility"),
  /** Corporation for-profit Facility — X12 code "2X". */
  CORPORATION_FOR_PROFIT_FACILITY("2X", "Corporation for-profit Facility"),
  /** General Medical and Surgical Facility — X12 code "2Y". */
  GENERAL_MEDICAL_AND_SURGICAL_FACILITY("2Y", "General Medical and Surgical Facility"),
  /** Hospital Unit of an Institution (prison hospital, college infirmary, etc.) — X12 code "2Z". */
  HOSPITAL_UNIT_OF_AN_INSTITUTION(
      "2Z", "Hospital Unit of an Institution (prison hospital, college infirmary, etc.)"),
  /** Dependent — X12 code "03". */
  DEPENDENT("03", "Dependent"),
  /** Application Party — X12 code "003". */
  APPLICATION_PARTY("003", "Application Party"),
  /** Hospital Unit Within an Institution for the Mentally Retarded — X12 code "3A". */
  HOSPITAL_UNIT_WITHIN_INSTITUTION_FOR_MENTALLY_RETARDED(
      "3A", "Hospital Unit Within an Institution for the Mentally Retarded"),
  /** Psychiatric Facility — X12 code "3B". */
  PSYCHIATRIC_FACILITY("3B", "Psychiatric Facility"),
  /** Tuberculosis and Other Respiratory Diseases Facility — X12 code "3C". */
  TUBERCULOSIS_AND_OTHER_RESPIRATORY_DISEASES_FACILITY(
      "3C", "Tuberculosis and Other Respiratory Diseases Facility"),
  /** Obstetrics and Gynecology Facility — X12 code "3D". */
  OBSTETRICS_AND_GYNECOLOGY_FACILITY("3D", "Obstetrics and Gynecology Facility"),
  /** Eye, Ear, Nose and Throat Facility — X12 code "3E". */
  EYE_EAR_NOSE_AND_THROAT_FACILITY("3E", "Eye, Ear, Nose and Throat Facility"),
  /** Rehabilitation Facility — X12 code "3F". */
  REHABILITATION_FACILITY("3F", "Rehabilitation Facility"),
  /** Orthopedic Facility — X12 code "3G". */
  ORTHOPEDIC_FACILITY("3G", "Orthopedic Facility"),
  /** Chronic Disease Facility — X12 code "3H". */
  CHRONIC_DISEASE_FACILITY("3H", "Chronic Disease Facility"),
  /** Other Specialty Facility — X12 code "3I". */
  OTHER_SPECIALTY_FACILITY("3I", "Other Specialty Facility"),
  /** Children's General Facility — X12 code "3J". */
  CHILDRENS_GENERAL_FACILITY("3J", "Children's General Facility"),
  /** Children's Hospital Unit of an Institution — X12 code "3K". */
  CHILDRENS_HOSPITAL_UNIT_OF_AN_INSTITUTION("3K", "Children's Hospital Unit of an Institution"),
  /** Children's Psychiatric Facility — X12 code "3L". */
  CHILDRENS_PSYCHIATRIC_FACILITY("3L", "Children's Psychiatric Facility"),
  /** Children's Tuberculosis and Other Respiratory Diseases Facility — X12 code "3M". */
  CHILDRENS_TUBERCULOSIS_AND_OTHER_RESPIRATORY_DISEASES_FACILITY(
      "3M", "Children's Tuberculosis and Other Respiratory Diseases Facility"),
  /** Children's Eye, Ear, Nose and Throat Facility — X12 code "3N". */
  CHILDRENS_EYE_EAR_NOSE_AND_THROAT_FACILITY("3N", "Children's Eye, Ear, Nose and Throat Facility"),
  /** Children's Rehabilitation Facility — X12 code "3O". */
  CHILDRENS_REHABILITATION_FACILITY("3O", "Children's Rehabilitation Facility"),
  /** Children's Orthopedic Facility — X12 code "3P". */
  CHILDRENS_ORTHOPEDIC_FACILITY("3P", "Children's Orthopedic Facility"),
  /** Children's Chronic Disease Facility — X12 code "3Q". */
  CHILDRENS_CHRONIC_DISEASE_FACILITY("3Q", "Children's Chronic Disease Facility"),
  /** Children's Other Specialty Facility — X12 code "3R". */
  CHILDRENS_OTHER_SPECIALTY_FACILITY("3R", "Children's Other Specialty Facility"),
  /** Institution for Mental Retardation — X12 code "3S". */
  INSTITUTION_FOR_MENTAL_RETARDATION("3S", "Institution for Mental Retardation"),
  /** Alcoholism and Other Chemical Dependency Facility — X12 code "3T". */
  ALCOHOLISM_AND_OTHER_CHEMICAL_DEPENDENCY_FACILITY(
      "3T", "Alcoholism and Other Chemical Dependency Facility"),
  /** General Inpatient Care for AIDS/ARC Facility — X12 code "3U". */
  GENERAL_INPATIENT_CARE_FOR_AIDS_ARC_FACILITY(
      "3U", "General Inpatient Care for AIDS/ARC Facility"),
  /** AIDS/ARC Unit — X12 code "3V". */
  AIDS_ARC_UNIT("3V", "AIDS/ARC Unit"),
  /** Specialized Outpatient Program for AIDS/ARC — X12 code "3W". */
  SPECIALIZED_OUTPATIENT_PROGRAM_FOR_AIDS_ARC("3W", "Specialized Outpatient Program for AIDS/ARC"),
  /** Alcohol/Drug Abuse or Dependency Inpatient Unit — X12 code "3X". */
  ALCOHOL_DRUG_ABUSE_OR_DEPENDENCY_INPATIENT_UNIT(
      "3X", "Alcohol/Drug Abuse or Dependency Inpatient Unit"),
  /** Alcohol/Drug Abuse or Dependency Outpatient Services — X12 code "3Y". */
  ALCOHOL_DRUG_ABUSE_OR_DEPENDENCY_OUTPATIENT_SERVICES(
      "3Y", "Alcohol/Drug Abuse or Dependency Outpatient Services"),
  /** Arthritis Treatment Center — X12 code "3Z". */
  ARTHRITIS_TREATMENT_CENTER("3Z", "Arthritis Treatment Center"),
  /** Asset Account Holder — X12 code "04". */
  ASSET_ACCOUNT_HOLDER("04", "Asset Account Holder"),
  /** Site Operator — X12 code "004". */
  SITE_OPERATOR("004", "Site Operator"),
  /** Birthing Room/LDRP Room — X12 code "4A". */
  BIRTHING_ROOM_LDRP_ROOM("4A", "Birthing Room/LDRP Room"),
  /** Burn Care Unit — X12 code "4B". */
  BURN_CARE_UNIT("4B", "Burn Care Unit"),
  /** Cardiac Catherization Laboratory — X12 code "4C". */
  CARDIAC_CATHERIZATION_LABORATORY("4C", "Cardiac Catherization Laboratory"),
  /** Open-Heart Surgery Facility — X12 code "4D". */
  OPEN_HEART_SURGERY_FACILITY("4D", "Open-Heart Surgery Facility"),
  /** Cardiac Intensive Care Unit — X12 code "4E". */
  CARDIAC_INTENSIVE_CARE_UNIT("4E", "Cardiac Intensive Care Unit"),
  /** Angioplasty Facility — X12 code "4F". */
  ANGIOPLASTY_FACILITY("4F", "Angioplasty Facility"),
  /** Chronic Obstructive Pulmonary Disease Service Facility — X12 code "4G". */
  CHRONIC_OBSTRUCTIVE_PULMONARY_DISEASE_SERVICE_FACILITY(
      "4G", "Chronic Obstructive Pulmonary Disease Service Facility"),
  /** Emergency Department — X12 code "4H". */
  EMERGENCY_DEPARTMENT("4H", "Emergency Department"),
  /** Trauma Center (Certified) — X12 code "4I". */
  TRAUMA_CENTER("4I", "Trauma Center (Certified)"),
  /** Extracorporeal Shock-Wave Lithotripter (ESWL) Unit — X12 code "4J". */
  EXTRACORPOREAL_SHOCK_WAVE_LITHOTRIPTER_UNIT(
      "4J", "Extracorporeal Shock-Wave Lithotripter (ESWL) Unit"),
  /** Fitness Center — X12 code "4K". */
  FITNESS_CENTER("4K", "Fitness Center"),
  /** Genetic Counseling/Screening Services — X12 code "4L". */
  GENETIC_COUNSELING_SCREENING_SERVICES("4L", "Genetic Counseling/Screening Services"),
  /** Adult Day Care Program Facility — X12 code "4M". */
  ADULT_DAY_CARE_PROGRAM_FACILITY("4M", "Adult Day Care Program Facility"),
  /** Alzheimer's Diagnostic/Assessment Services — X12 code "4N". */
  ALZHEIMERS_DIAGNOSTIC_ASSESSMENT_SERVICES("4N", "Alzheimer's Diagnostic/Assessment Services"),
  /** Comprehensive Geriatric Assessment Facility — X12 code "4O". */
  COMPREHENSIVE_GERIATRIC_ASSESSMENT_FACILITY("4O", "Comprehensive Geriatric Assessment Facility"),
  /** Emergency Response (Geriatric) Unit — X12 code "4P". */
  EMERGENCY_RESPONSE_GERIATRIC_UNIT("4P", "Emergency Response (Geriatric) Unit"),
  /** Geriatric Acute Care Unit — X12 code "4Q". */
  GERIATRIC_ACUTE_CARE_UNIT("4Q", "Geriatric Acute Care Unit"),
  /** Geriatric Clinics — X12 code "4R". */
  GERIATRIC_CLINICS("4R", "Geriatric Clinics"),
  /** Respite Care Facility — X12 code "4S". */
  RESPITE_CARE_FACILITY("4S", "Respite Care Facility"),
  /** Senior Membership Program — X12 code "4T". */
  SENIOR_MEMBERSHIP_PROGRAM("4T", "Senior Membership Program"),
  /** Patient Education Unit — X12 code "4U". */
  PATIENT_EDUCATION_UNIT("4U", "Patient Education Unit"),
  /** Community Health Promotion Facility — X12 code "4V". */
  COMMUNITY_HEALTH_PROMOTION_FACILITY("4V", "Community Health Promotion Facility"),
  /** Worksite Health Promotion Facility — X12 code "4W". */
  WORKSITE_HEALTH_PROMOTION_FACILITY("4W", "Worksite Health Promotion Facility"),
  /** Hemodialysis Facility — X12 code "4X". */
  HEMODIALYSIS_FACILITY("4X", "Hemodialysis Facility"),
  /** Home Health Services — X12 code "4Y". */
  HOME_HEALTH_SERVICES("4Y", "Home Health Services"),
  /** Hospice — X12 code "4Z". */
  HOSPICE("4Z", "Hospice"),
  /** Tenant — X12 code "05". */
  TENANT("05", "Tenant"),
  /** Construction Contractor — X12 code "005". */
  CONSTRUCTION_CONTRACTOR("005", "Construction Contractor"),
  /** Medical Surgical or Other Intensive Care Unit — X12 code "5A". */
  MEDICAL_SURGICAL_OR_OTHER_INTENSIVE_CARE_UNIT(
      "5A", "Medical Surgical or Other Intensive Care Unit"),
  /** Hisopathology Laboratory — X12 code "5B". */
  HISOPATHOLOGY_LABORATORY("5B", "Hisopathology Laboratory"),
  /** Blood Bank — X12 code "5C". */
  BLOOD_BANK("5C", "Blood Bank"),
  /** Neonatal Intensive Care Unit — X12 code "5D". */
  NEONATAL_INTENSIVE_CARE_UNIT("5D", "Neonatal Intensive Care Unit"),
  /** Obstetrics Unit — X12 code "5E". */
  OBSTETRICS_UNIT("5E", "Obstetrics Unit"),
  /** Occupational Health Services — X12 code "5F". */
  OCCUPATIONAL_HEALTH_SERVICES("5F", "Occupational Health Services"),
  /** Organized Outpatient Services — X12 code "5G". */
  ORGANIZED_OUTPATIENT_SERVICES("5G", "Organized Outpatient Services"),
  /** Pediatric Acute Inpatient Unit — X12 code "5H". */
  PEDIATRIC_ACUTE_INPATIENT_UNIT("5H", "Pediatric Acute Inpatient Unit"),
  /** Psychiatric Child/Adolescent Services — X12 code "5I". */
  PSYCHIATRIC_CHILD_ADOLESCENT_SERVICES("5I", "Psychiatric Child/Adolescent Services"),
  /** Psychiatric Consultation-Liaison Services — X12 code "5J". */
  PSYCHIATRIC_CONSULTATION_LIAISON_SERVICES("5J", "Psychiatric Consultation-Liaison Services"),
  /** Psychiatric Education Services — X12 code "5K". */
  PSYCHIATRIC_EDUCATION_SERVICES("5K", "Psychiatric Education Services"),
  /** Psychiatric Emergency Services — X12 code "5L". */
  PSYCHIATRIC_EMERGENCY_SERVICES("5L", "Psychiatric Emergency Services"),
  /** Psychiatric Geriatric Services — X12 code "5M". */
  PSYCHIATRIC_GERIATRIC_SERVICES("5M", "Psychiatric Geriatric Services"),
  /** Psychiatric Inpatient Unit — X12 code "5N". */
  PSYCHIATRIC_INPATIENT_UNIT("5N", "Psychiatric Inpatient Unit"),
  /** Psychiatric Outpatient Services — X12 code "5O". */
  PSYCHIATRIC_OUTPATIENT_SERVICES("5O", "Psychiatric Outpatient Services"),
  /** Psychiatric Partial Hospitalization Program — X12 code "5P". */
  PSYCHIATRIC_PARTIAL_HOSPITALIZATION_PROGRAM("5P", "Psychiatric Partial Hospitalization Program"),
  /** Megavoltage Radiation Therapy Unit — X12 code "5Q". */
  MEGAVOLTAGE_RADIATION_THERAPY_UNIT("5Q", "Megavoltage Radiation Therapy Unit"),
  /** Radioactive Implants Unit — X12 code "5R". */
  RADIOACTIVE_IMPLANTS_UNIT("5R", "Radioactive Implants Unit"),
  /** Therapeutic Radioisotope Facility — X12 code "5S". */
  THERAPEUTIC_RADIOISOTOPE_FACILITY("5S", "Therapeutic Radioisotope Facility"),
  /** X-Ray Radiation Therapy Unit — X12 code "5T". */
  X_RAY_RADIATION_THERAPY_UNIT("5T", "X-Ray Radiation Therapy Unit"),
  /** CT Scanner Unit — X12 code "5U". */
  CT_SCANNER_UNIT("5U", "CT Scanner Unit"),
  /** Diagnostic Radioisotope Facility — X12 code "5V". */
  DIAGNOSTIC_RADIOISOTOPE_FACILITY("5V", "Diagnostic Radioisotope Facility"),
  /** Magnetic Resonance Imaging (MRI) Facility — X12 code "5W". */
  MAGNETIC_RESONANCE_IMAGING_FACILITY("5W", "Magnetic Resonance Imaging (MRI) Facility"),
  /** Ultrasound Unit — X12 code "5X". */
  ULTRASOUND_UNIT("5X", "Ultrasound Unit"),
  /** Rehabilitation Inpatient Unit — X12 code "5Y". */
  REHABILITATION_INPATIENT_UNIT("5Y", "Rehabilitation Inpatient Unit"),
  /** Rehabilitation Outpatient Services — X12 code "5Z". */
  REHABILITATION_OUTPATIENT_SERVICES("5Z", "Rehabilitation Outpatient Services"),
  /** Recipient of Civil or Legal Liability Payment — X12 code "06". */
  RECIPIENT_OF_CIVIL_OR_LEGAL_LIABILITY_PAYMENT(
      "06", "Recipient of Civil or Legal Liability Payment"),
  /** Drilling Contractor — X12 code "006". */
  DRILLING_CONTRACTOR("006", "Drilling Contractor"),
  /** Insurer — X12 code "IN". */
  INSURER("IN", "Insurer"),
  /** Terminal Location — X12 code "T3". */
  TERMINAL_LOCATION("T3", "Terminal Location"),
  /** Plan Sponsor — X12 code "P5". */
  PLAN_SPONSOR("P5", "Plan Sponsor"),
  /** Primary Taxpayer — X12 code "TP". */
  PRIMARY_TAX_PAYER("TP", "Primary Taxpayer"),
  /** Third Party Administrator (TPA) — X12 code "TV". */
  THIRD_PARTY_ADMINISTRATOR_TPA("TV", "Third Party Administrator (TPA)"),
  /** Broker or Sales Office — X12 code "BO". */
  BROKER_OR_SALES_OFFICE("BO", "Broker or Sales Office"),
  /** Insured or Subscriber — X12 code "IL". */
  INSURED_OR_SUBSCRIBER("IL", "Insured or Subscriber"),
  /** Corrected Insured — X12 code "74". */
  CORRECTED_INSURED("74", "Corrected Insured"),
  /** Employer — X12 code "36". */
  EMPLOYER("36", "Employer"),
  /** Information Source — X12 code "ACV". */
  INFORMATION_SOURCE("ACV", "Information Source"),
  /** Managed Care — X12 code "QK". */
  MANAGED_CARE("QK", "Managed Care"),
  /** NM101 of the 2100C mailing-address loop — the position this library already emits. */
  POSTAL_MAILING_ADDRESS("31", "Postal Mailing Address"),
  /** Participant — X12 code "75". */
  PARTICIPANT("75", "Participant");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<EntityIdentifierCode> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
            EntityIdentifierCode.class,
            "Entity Identifier Code",
            Map.ofEntries(
                Map.entry("hmo", HEALTH_MAINTENANCE_ORGANIZATION),
                Map.entry("ppo", PREFERRED_PROVIDER_ORGANIZATION),
                Map.entry("tpa", THIRD_PARTY_ADMINISTRATOR),
                Map.entry("provider", PROVIDER),
                Map.entry("dependent", DEPENDENT),
                Map.entry("hospital", ACUTE_CARE_HOSPITAL),
                Map.entry("pharmacy", RETAIL_PHARMACY),
                Map.entry("lab", LABORATORY),
                Map.entry("hospice", HOSPICE)));
  }

  EntityIdentifierCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets an EntityIdentifierCode instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching EntityIdentifierCode
   * @throws IllegalArgumentException if no match is found
   */
  public static EntityIdentifierCode fromString(String input) {
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
