/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member validMember() {
        Member member = new Member();
        member.setMemberIndicator(MemberIndicator.INSURED);
        member.setRelationshipCode(IndividualRelationshipCode.SELF);
        member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        return member;
    }

    @Test
    void validMemberPassesValidation() {
        assertDoesNotThrow(() -> validMember().validate());
    }

    @Test
    void missingMemberIndicatorThrows() {
        Member member = validMember();
        member.setMemberIndicator(null);
        ValidationException ex = assertThrows(ValidationException.class, member::validate);
        assertTrue(ex.getMessage().contains("member indicator"));
    }

    @Test
    void missingRelationshipCodeThrows() {
        Member member = validMember();
        member.setRelationshipCode(null);
        ValidationException ex = assertThrows(ValidationException.class, member::validate);
        assertTrue(ex.getMessage().contains("relationship code"));
    }

    @Test
    void missingMaintenanceTypeCodeThrows() {
        Member member = validMember();
        member.setMaintenanceTypeCode(null);
        ValidationException ex = assertThrows(ValidationException.class, member::validate);
        assertTrue(ex.getMessage().contains("maintenance type code"));
    }

    @Test
    void invalidDependentFailsMemberValidation() {
        Member member = validMember();
        DependentMember dependent = new DependentMember();
        // No relationship code — DependentMember.validate() rejects this.
        member.addDependent(dependent);

        ValidationException ex = assertThrows(ValidationException.class, member::validate);
        assertTrue(ex.getMessage().contains("relationship code"));
    }

    @Test
    void validDependentPassesMemberValidation() {
        Member member = validMember();
        DependentMember dependent = new DependentMember();
        dependent.setRelationshipCode(IndividualRelationshipCode.SPOUSE);
        member.addDependent(dependent);

        assertDoesNotThrow(member::validate);
    }
}
