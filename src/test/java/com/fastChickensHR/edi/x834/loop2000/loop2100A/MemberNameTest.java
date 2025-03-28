/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberNameTest {

    private x834Context context;
    private final String lastName = "DOE";
    private final String firstName = "JOHN";
    private final String middleName = "Q";
    private final String namePrefix = "MR";
    private final String nameSuffix = "JR";
    private final String idCodeQualifier = "34";
    private final String idCode = "123456789";
    private final String entityType = "1";

    @BeforeEach
    void setUp() {
        context = new x834Context();
    }

    @Test
    void testSettingSpecNamesGettingDomainNames() throws ValidationException {
        MemberName.Builder builder = MemberName.builder().setLastName(lastName).setFirstName(firstName).setMiddleName(middleName).setNamePrefix(namePrefix).setNameSuffix(nameSuffix).setIdentificationCodeQualifier(idCodeQualifier).setIdentificationCode(idCode);
        MemberName memberName = builder.build();

        assertEquals(MemberName.ENTITY_IDENTIFIER_CODE, memberName.getNm101());
        assertEquals(entityType, memberName.getNm102());
        assertEquals(lastName, memberName.getNm103());
        assertEquals(firstName, memberName.getNm104());
        assertEquals(middleName, memberName.getNm105());
        assertEquals(namePrefix, memberName.getNm106());
        assertEquals(nameSuffix, memberName.getNm107());
        assertEquals(idCodeQualifier, memberName.getNm108());
        assertEquals(idCode, memberName.getNm109());
    }

    @Test
    void testBuilderSetsDefaultValues() throws ValidationException {
        MemberName memberName = MemberName.builder().build();

        assertEquals(MemberName.ENTITY_IDENTIFIER_CODE, memberName.getEntityIdentifierCode());
        assertEquals(MemberName.PERSON_ENTITY_TYPE, memberName.getEntityTypeQualifier());
    }

    @Test
    void testOverridingDefaultValues() throws ValidationException {
        String customEntityIdentifierCode = "QD";
        MemberName memberName = MemberName.builder().setEntityIdentifierCode(customEntityIdentifierCode).setEntityTypeQualifier(entityType).build();

        assertEquals(customEntityIdentifierCode, memberName.getEntityIdentifierCode());
    }

    @Test
    void testRender() throws ValidationException {
        MemberName segment = createFullMemberName();
        segment.setContext(context);

        assertEquals("NM1*IL*1*DOE*JOHN*Q*MR*JR*34*123456789~", segment.render().trim(), "Render should properly format the segment");
    }

    @Test
    void testValidationRequiresEntityIdentifierCode() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberName.Builder builder = MemberName.builder();
            builder.setEntityIdentifierCode(null);
            builder.build();
        });

        assertTrue(exception.getMessage().contains("Entity Identifier Code"));
    }

    @Test
    void testValidationRequiresEntityTypeQualifier() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberName.Builder builder = MemberName.builder();
            builder.setEntityTypeQualifier(null);
            builder.build();
        });

        assertTrue(exception.getMessage().contains("Entity Type Qualifier"));
    }

    @Test
    void testBuilderIsProperlyChainable() throws ValidationException {
        MemberName memberName = MemberName.builder().setNm102("2").setNm103(lastName).setNm104(firstName).setNm105(middleName).setNm106(namePrefix).setNm107(nameSuffix).setNm108(idCodeQualifier).setNm109(idCode).build();

        assertNotNull(memberName);
        assertEquals(MemberName.ENTITY_IDENTIFIER_CODE, memberName.getEntityIdentifierCode());
        assertEquals("2", memberName.getEntityTypeQualifier());
        assertEquals(lastName, memberName.getLastName());
        assertEquals(firstName, memberName.getFirstName());
        assertEquals(middleName, memberName.getMiddleName());
        assertEquals(namePrefix, memberName.getNamePrefix());
        assertEquals(nameSuffix, memberName.getNameSuffix());
        assertEquals(idCodeQualifier, memberName.getIdentificationCodeQualifier());
        assertEquals(idCode, memberName.getIdentificationCode());
    }

    private MemberName createFullMemberName() throws ValidationException {
        return MemberName.builder().setLastName(lastName).setFirstName(firstName).setMiddleName(middleName).setNamePrefix(namePrefix).setNameSuffix(nameSuffix).setIdentificationCodeQualifier(idCodeQualifier).setIdentificationCode(idCode).build();
    }
}