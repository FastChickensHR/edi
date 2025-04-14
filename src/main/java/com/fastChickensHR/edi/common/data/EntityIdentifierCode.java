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
 * Enumeration representing Entity Identifier Codes used in EDI transactions.
 * These codes identify organizational entities, functional groups, or areas of interest.
 */
@Getter
public enum EntityIdentifierCode implements EdiCodeEnum {
    ALTERNATE_INSURER("00", "Alternate Insurer"),
    COMPARABLE_RENTALS("0A", "Comparable Rentals"),
    INTERIM_FUNDING_ORGANIZATION("0B", "Interim Funding Organization"),
    NON_OCCUPANT_CO_BORROWER("0D", "Non-occupant Co-borrower"),
    LIST_OWNER("0E", "List Owner"),
    LIST_MAILER("0F", "List Mailer"),
    PRIMARY_ELECTRONIC_BUSINESS_CONTACT("0G", "Primary Electronic Business Contact"),
    STATE_DIVISION("0H", "State Division"),
    ALTERNATE_ELECTRONIC_BUSINESS_CONTACT("0I", "Alternate Electronic Business Contact"),
    PRIMARY_PRACTICE_LOCATION("0J", "Primary Practice Location"),
    PARTY_TO_DECLARE_GOODS("0P", "Party to Declare Goods"),
    LOAN_APPLICANT("01", "Loan Applicant"),
    PUMPER("001", "Pumper"),
    SUBGROUP("1A", "Subgroup"),
    APPLICANT("1B", "Applicant"),
    GROUP_PURCHASING_ORGANIZATION("1C", "Group Purchasing Organization (GPO)"),
    COOPERATIVE("1D", "Co-operative"),
    HEALTH_MAINTENANCE_ORGANIZATION("1E", "Health Maintenance Organization (HMO)"),
    ALLIANCE("1F", "Alliance"),
    ONCOLOGY_CENTER("1G", "Oncology Center"),
    KIDNEY_DIALYSIS_UNIT("1H", "Kidney Dialysis Unit"),
    PREFERRED_PROVIDER_ORGANIZATION("1I", "Preferred Provider Organization (PPO)"),
    CONNECTION("1J", "ConnectionThe name of pipeline company to which a well, lease or field is connected"),
    FRANCHISOR("1K", "Franchisor"),
    FRANCHISEE("1L", "Franchisee"),
    PREVIOUS_GROUP("1M", "Previous Group"),
    SHAREHOLDER("1N", "Shareholder"),
    ACUTE_CARE_HOSPITAL("1O", "Acute Care Hospital"),
    PROVIDER("1P", "Provider"),
    MILITARY_FACILITY("1Q", "Military Facility"),
    UNIVERSITY_COLLEGE_SCHOOL("1R", "University, College or School"),
    OUTPATIENT_SURGICENTER("1S", "Outpatient Surgicenter"),
    PHYSICIAN_CLINIC_GROUP_PRACTICE("1T", "Physician, Clinic or Group Practice"),
    LONG_TERM_CARE_FACILITY("1U", "Long Term Care Facility"),
    EXTENDED_CARE_FACILITY("1V", "Extended Care Facility"),
    PSYCHIATRIC_HEALTH_FACILITY("1W", "Psychiatric Health Facility"),
    LABORATORY("1X", "Laboratory"),
    RETAIL_PHARMACY("1Y", "Retail Pharmacy"),
    HOME_HEALTH_CARE("1Z", "Home Health Care"),
    LOAN_BROKER("02", "Loan Broker"),
    SURFACE_MANAGEMENT_ENTITY("002", "Surface Management Entity"),
    FEDERAL_STATE_COUNTY_CITY_FACILITY("2A", "Federal, State, County or City Facility"),
    THIRD_PARTY_ADMINISTRATOR("2B", "Third-Party Administrator"),
    CO_PARTICIPANT("2C", "Co-Participant"),
    MISCELLANEOUS_HEALTH_CARE_FACILITY("2D", "Miscellaneous Health Care Facility"),
    NON_HEALTH_CARE_MISCELLANEOUS_FACILITY("2E", "Non-Health Care Miscellaneous Facility"),
    STATE("2F", "State"),
    ASSIGNER("2G", "Assigner"),
    HOSPITAL_DISTRICT_OR_AUTHORITY("2H", "Hospital District or Authority"),
    CHURCH_OPERATED_FACILITY("2I", "Church Operated Facility"),
    INDIVIDUAL("2J", "Individual"),
    PARTNERSHIP("2K", "Partnership"),
    CORPORATION("2L", "Corporation"),
    AIR_FORCE_FACILITY("2M", "Air Force Facility"),
    ARMY_FACILITY("2N", "Army Facility"),
    NAVY_FACILITY("2O", "Navy Facility"),
    PUBLIC_HEALTH_SERVICE_FACILITY("2P", "Public Health Service Facility"),
    VETERANS_ADMINISTRATION_FACILITY("2Q", "Veterans Administration Facility"),
    FEDERAL_FACILITY("2R", "Federal Facility"),
    PUBLIC_HEALTH_SERVICE_INDIAN_SERVICE_FACILITY("2S", "Public Health Service Indian Service Facility"),
    DEPARTMENT_OF_JUSTICE_FACILITY("2T", "Department of Justice Facility"),
    OTHER_NOT_FOR_PROFIT_FACILITY("2U", "Other Not-for-profit Facility"),
    INDIVIDUAL_FOR_PROFIT_FACILITY("2V", "Individual for-profit Facility"),
    PARTNERSHIP_FOR_PROFIT_FACILITY("2W", "Partnership for-profit Facility"),
    CORPORATION_FOR_PROFIT_FACILITY("2X", "Corporation for-profit Facility"),
    GENERAL_MEDICAL_AND_SURGICAL_FACILITY("2Y", "General Medical and Surgical Facility"),
    HOSPITAL_UNIT_OF_AN_INSTITUTION("2Z", "Hospital Unit of an Institution (prison hospital, college infirmary, etc.)"),
    DEPENDENT("03", "Dependent"),
    APPLICATION_PARTY("003", "Application Party"),
    HOSPITAL_UNIT_WITHIN_INSTITUTION_FOR_MENTALLY_RETARDED("3A", "Hospital Unit Within an Institution for the Mentally Retarded"),
    PSYCHIATRIC_FACILITY("3B", "Psychiatric Facility"),
    TUBERCULOSIS_AND_OTHER_RESPIRATORY_DISEASES_FACILITY("3C", "Tuberculosis and Other Respiratory Diseases Facility"),
    OBSTETRICS_AND_GYNECOLOGY_FACILITY("3D", "Obstetrics and Gynecology Facility"),
    EYE_EAR_NOSE_AND_THROAT_FACILITY("3E", "Eye, Ear, Nose and Throat Facility"),
    REHABILITATION_FACILITY("3F", "Rehabilitation Facility"),
    ORTHOPEDIC_FACILITY("3G", "Orthopedic Facility"),
    CHRONIC_DISEASE_FACILITY("3H", "Chronic Disease Facility"),
    OTHER_SPECIALTY_FACILITY("3I", "Other Specialty Facility"),
    CHILDRENS_GENERAL_FACILITY("3J", "Children's General Facility"),
    CHILDRENS_HOSPITAL_UNIT_OF_AN_INSTITUTION("3K", "Children's Hospital Unit of an Institution"),
    CHILDRENS_PSYCHIATRIC_FACILITY("3L", "Children's Psychiatric Facility"),
    CHILDRENS_TUBERCULOSIS_AND_OTHER_RESPIRATORY_DISEASES_FACILITY("3M", "Children's Tuberculosis and Other Respiratory Diseases Facility"),
    CHILDRENS_EYE_EAR_NOSE_AND_THROAT_FACILITY("3N", "Children's Eye, Ear, Nose and Throat Facility"),
    CHILDRENS_REHABILITATION_FACILITY("3O", "Children's Rehabilitation Facility"),
    CHILDRENS_ORTHOPEDIC_FACILITY("3P", "Children's Orthopedic Facility"),
    CHILDRENS_CHRONIC_DISEASE_FACILITY("3Q", "Children's Chronic Disease Facility"),
    CHILDRENS_OTHER_SPECIALTY_FACILITY("3R", "Children's Other Specialty Facility"),
    INSTITUTION_FOR_MENTAL_RETARDATION("3S", "Institution for Mental Retardation"),
    ALCOHOLISM_AND_OTHER_CHEMICAL_DEPENDENCY_FACILITY("3T", "Alcoholism and Other Chemical Dependency Facility"),
    GENERAL_INPATIENT_CARE_FOR_AIDS_ARC_FACILITY("3U", "General Inpatient Care for AIDS/ARC Facility"),
    AIDS_ARC_UNIT("3V", "AIDS/ARC Unit"),
    SPECIALIZED_OUTPATIENT_PROGRAM_FOR_AIDS_ARC("3W", "Specialized Outpatient Program for AIDS/ARC"),
    ALCOHOL_DRUG_ABUSE_OR_DEPENDENCY_INPATIENT_UNIT("3X", "Alcohol/Drug Abuse or Dependency Inpatient Unit"),
    ALCOHOL_DRUG_ABUSE_OR_DEPENDENCY_OUTPATIENT_SERVICES("3Y", "Alcohol/Drug Abuse or Dependency Outpatient Services"),
    ARTHRITIS_TREATMENT_CENTER("3Z", "Arthritis Treatment Center"),
    ASSET_ACCOUNT_HOLDER("04", "Asset Account Holder"),
    SITE_OPERATOR("004", "Site Operator"),
    BIRTHING_ROOM_LDRP_ROOM("4A", "Birthing Room/LDRP Room"),
    BURN_CARE_UNIT("4B", "Burn Care Unit"),
    CARDIAC_CATHERIZATION_LABORATORY("4C", "Cardiac Catherization Laboratory"),
    OPEN_HEART_SURGERY_FACILITY("4D", "Open-Heart Surgery Facility"),
    CARDIAC_INTENSIVE_CARE_UNIT("4E", "Cardiac Intensive Care Unit"),
    ANGIOPLASTY_FACILITY("4F", "Angioplasty Facility"),
    CHRONIC_OBSTRUCTIVE_PULMONARY_DISEASE_SERVICE_FACILITY("4G", "Chronic Obstructive Pulmonary Disease Service Facility"),
    EMERGENCY_DEPARTMENT("4H", "Emergency Department"),
    TRAUMA_CENTER("4I", "Trauma Center (Certified)"),
    EXTRACORPOREAL_SHOCK_WAVE_LITHOTRIPTER_UNIT("4J", "Extracorporeal Shock-Wave Lithotripter (ESWL) Unit"),
    FITNESS_CENTER("4K", "Fitness Center"),
    GENETIC_COUNSELING_SCREENING_SERVICES("4L", "Genetic Counseling/Screening Services"),
    ADULT_DAY_CARE_PROGRAM_FACILITY("4M", "Adult Day Care Program Facility"),
    ALZHEIMERS_DIAGNOSTIC_ASSESSMENT_SERVICES("4N", "Alzheimer's Diagnostic/Assessment Services"),
    COMPREHENSIVE_GERIATRIC_ASSESSMENT_FACILITY("4O", "Comprehensive Geriatric Assessment Facility"),
    EMERGENCY_RESPONSE_GERIATRIC_UNIT("4P", "Emergency Response (Geriatric) Unit"),
    GERIATRIC_ACUTE_CARE_UNIT("4Q", "Geriatric Acute Care Unit"),
    GERIATRIC_CLINICS("4R", "Geriatric Clinics"),
    RESPITE_CARE_FACILITY("4S", "Respite Care Facility"),
    SENIOR_MEMBERSHIP_PROGRAM("4T", "Senior Membership Program"),
    PATIENT_EDUCATION_UNIT("4U", "Patient Education Unit"),
    COMMUNITY_HEALTH_PROMOTION_FACILITY("4V", "Community Health Promotion Facility"),
    WORKSITE_HEALTH_PROMOTION_FACILITY("4W", "Worksite Health Promotion Facility"),
    HEMODIALYSIS_FACILITY("4X", "Hemodialysis Facility"),
    HOME_HEALTH_SERVICES("4Y", "Home Health Services"),
    HOSPICE("4Z", "Hospice"),
    TENANT("05", "Tenant"),
    CONSTRUCTION_CONTRACTOR("005", "Construction Contractor"),
    MEDICAL_SURGICAL_OR_OTHER_INTENSIVE_CARE_UNIT("5A", "Medical Surgical or Other Intensive Care Unit"),
    HISOPATHOLOGY_LABORATORY("5B", "Hisopathology Laboratory"),
    BLOOD_BANK("5C", "Blood Bank"),
    NEONATAL_INTENSIVE_CARE_UNIT("5D", "Neonatal Intensive Care Unit"),
    OBSTETRICS_UNIT("5E", "Obstetrics Unit"),
    OCCUPATIONAL_HEALTH_SERVICES("5F", "Occupational Health Services"),
    ORGANIZED_OUTPATIENT_SERVICES("5G", "Organized Outpatient Services"),
    PEDIATRIC_ACUTE_INPATIENT_UNIT("5H", "Pediatric Acute Inpatient Unit"),
    PSYCHIATRIC_CHILD_ADOLESCENT_SERVICES("5I", "Psychiatric Child/Adolescent Services"),
    PSYCHIATRIC_CONSULTATION_LIAISON_SERVICES("5J", "Psychiatric Consultation-Liaison Services"),
    PSYCHIATRIC_EDUCATION_SERVICES("5K", "Psychiatric Education Services"),
    PSYCHIATRIC_EMERGENCY_SERVICES("5L", "Psychiatric Emergency Services"),
    PSYCHIATRIC_GERIATRIC_SERVICES("5M", "Psychiatric Geriatric Services"),
    PSYCHIATRIC_INPATIENT_UNIT("5N", "Psychiatric Inpatient Unit"),
    PSYCHIATRIC_OUTPATIENT_SERVICES("5O", "Psychiatric Outpatient Services"),
    PSYCHIATRIC_PARTIAL_HOSPITALIZATION_PROGRAM("5P", "Psychiatric Partial Hospitalization Program"),
    MEGAVOLTAGE_RADIATION_THERAPY_UNIT("5Q", "Megavoltage Radiation Therapy Unit"),
    RADIOACTIVE_IMPLANTS_UNIT("5R", "Radioactive Implants Unit"),
    THERAPEUTIC_RADIOISOTOPE_FACILITY("5S", "Therapeutic Radioisotope Facility"),
    X_RAY_RADIATION_THERAPY_UNIT("5T", "X-Ray Radiation Therapy Unit"),
    CT_SCANNER_UNIT("5U", "CT Scanner Unit"),
    DIAGNOSTIC_RADIOISOTOPE_FACILITY("5V", "Diagnostic Radioisotope Facility"),
    MAGNETIC_RESONANCE_IMAGING_FACILITY("5W", "Magnetic Resonance Imaging (MRI) Facility"),
    ULTRASOUND_UNIT("5X", "Ultrasound Unit"),
    REHABILITATION_INPATIENT_UNIT("5Y", "Rehabilitation Inpatient Unit"),
    REHABILITATION_OUTPATIENT_SERVICES("5Z", "Rehabilitation Outpatient Services"),
    RECIPIENT_OF_CIVIL_OR_LEGAL_LIABILITY_PAYMENT("06", "Recipient of Civil or Legal Liability Payment"),
    DRILLING_CONTRACTOR("006", "Drilling Contractor"),
    INSURER("IN", "Insurer"),
    TERMINAL_LOCATION("T3", "Terminal Location"),
    PLAN_SPONSOR("P5", "Plan Sponsor"),
    PRIMARY_TAX_PAYER("TP", "Primary Taxpayer");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<EntityIdentifierCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
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
                        Map.entry("hospice", HOSPICE)
                )
        );
    }

    EntityIdentifierCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an EntityIdentifierCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching EntityIdentifierCode
     * @throws IllegalArgumentException if no match is found
     */
    public static EntityIdentifierCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}