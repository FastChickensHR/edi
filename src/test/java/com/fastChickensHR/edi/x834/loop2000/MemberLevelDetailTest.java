/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.ConfidentialityCode;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberLevelDetailTest {
    String maintenanceReasonCode = MaintenanceReasonCode.ACTIVE.toString();
    String employmentStatusCode = EmploymentStatusCode.FULL_TIME.toString();
    String confidentialityCode = ConfidentialityCode.LOW.toString();
    @Test
    void testGetSegmentIdentifierReturnsExpectedValue() throws ValidationException {
        x834Context context = new x834Context();
        MemberLevelDetail segment = new MemberLevelDetail.Builder()
                .setMemberIndicator("Y")
                .setIndividualRelationshipCode("18")
                .setMaintenanceTypeCode("001")
                .setMaintenanceReasonCode(maintenanceReasonCode)
                .setBenefitStatusCode("A")
                .setMedicarePlanCode("D")
                .setCobraQualifyingEventCode("1")
                .setEmploymentStatusCode(employmentStatusCode)
                .setStudentStatusCode("F")
                .setHandicapIndicator("N")
                .setDeathDate("20250101")
                .build();

        segment.setContext(context);

        assertEquals("INS", segment.getSegmentIdentifier(), "Expected segment identifier should be 'INS'");
        assertEquals("INS*Y*18*001*AC*A*D*1*2*F*N*20250101~", segment.render().trim(),
                "The segment is not formatted correctly.");
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        MemberLevelDetail segment = new MemberLevelDetail.Builder()
                .setIns01("Y")
                .setIns02("18")
                .setIns03("001")
                .setIns04(maintenanceReasonCode)
                .setIns05("A")
                .setIns06("D")
                .setIns07("1")
                .setIns08(employmentStatusCode)
                .setIns09("F")
                .setIns10("N")
                .setIns11("20250101")
                .setIns12(confidentialityCode)
                .build();

        assertEquals("Y", segment.getMemberIndicator(), "MemberIndicator should match INS01");
        assertEquals("18", segment.getIndividualRelationshipCode(), "IndividualRelationshipCode should match INS02");
        assertEquals("001", segment.getMaintenanceTypeCode(), "MaintenanceTypeCode should match INS03");
        assertEquals(maintenanceReasonCode, segment.getMaintenanceReasonCode(), "MaintenanceReasonCode should match INS04");
        assertEquals("A", segment.getBenefitStatusCode(), "BenefitStatusCode should match INS05");
        assertEquals("D", segment.getMedicarePlanCode(), "MedicarePlanCode should match INS06");
        assertEquals("1", segment.getCOBRAQualifyingEventCode(), "COBRAQualifierCode should match INS07");
        assertEquals(employmentStatusCode, segment.getEmploymentStatusCode(), "EmploymentStatusCode should match INS08");
        assertEquals("F", segment.getStudentStatusCode(), "StudentStatusCode should match INS09");
        assertEquals("N", segment.getHandicapIndicator(), "HandicapIndicator should match INS10");
        assertEquals("20250101", segment.getDeathDate(), "DeathDate should match INS11");
        assertEquals(confidentialityCode, segment.getConfidentialityCode(), "ConfidentialityCode should match INS12");
    }

    @Test
    void testSettingDomainNamesGettingSpecNames() throws ValidationException {
        MemberLevelDetail segment = new MemberLevelDetail.Builder()
                .setMemberIndicator("Y")
                .setIndividualRelationshipCode("18")
                .setMaintenanceTypeCode("001")
                .setMaintenanceReasonCode(maintenanceReasonCode)
                .setBenefitStatusCode("A")
                .setMedicarePlanCode("D")
                .setCobraQualifyingEventCode("1")
                .setEmploymentStatusCode(employmentStatusCode)
                .setStudentStatusCode("F")
                .setHandicapIndicator("N")
                .setDeathDate("20250101")
                .setConfidentialityCode(confidentialityCode)
                .build();

        assertEquals("Y", segment.getIns01(), "MemberIndicator should match INS01");
        assertEquals("18", segment.getIns02(), "IndividualRelationshipCode should match INS02");
        assertEquals("001", segment.getIns03(), "MaintenanceTypeCode should match INS03");
        assertEquals(maintenanceReasonCode, segment.getIns04(), "MaintenanceReasonCode should match INS04");
        assertEquals("A", segment.getIns05(), "BenefitStatusCode should match INS05");
        assertEquals("D", segment.getIns06(), "MedicarePlanCode should match INS06");
        assertEquals("1", segment.getIns07(), "COBRAQualifierCode should match INS07");
        assertEquals(employmentStatusCode, segment.getIns08(), "EmploymentStatusCode should match INS08");
        assertEquals("F", segment.getIns09(), "StudentStatusCode should match INS09");
        assertEquals("N", segment.getIns10(), "HandicapIndicator should match INS10");
        assertEquals("20250101", segment.getIns11(), "DeathDate should match INS11");
        assertEquals(confidentialityCode, segment.getIns12(), "ConfidentialityCode should match INS12");
    }
}