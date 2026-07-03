/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberDateQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(21, MemberDateQualifier.values().length,
                "MemberDateQualifier should have 21 enum values");

        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BENEFIT_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BENEFIT_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BIRTH));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COVERAGE_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COVERAGE_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.EFFECTIVE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ELIGIBILITY_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ELIGIBILITY_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ENROLLMENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.EXPIRATION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.HIRE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.MAINTENANCE_EFFECTIVE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.RETIREMENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.SIGNATURE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.STATUS_CHANGE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.TERMINATION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_QUALIFYING_EVENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_ELECTION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("348", MemberDateQualifier.BENEFIT_BEGIN.getCode());
        assertEquals("349", MemberDateQualifier.BENEFIT_END.getCode());
        assertEquals("007", MemberDateQualifier.BIRTH.getCode());
        assertEquals("356", MemberDateQualifier.COVERAGE_BEGIN.getCode());
        assertEquals("357", MemberDateQualifier.COVERAGE_END.getCode());
        assertEquals("303", MemberDateQualifier.EFFECTIVE.getCode());
        assertEquals("336", MemberDateQualifier.ELIGIBILITY_BEGIN.getCode());
        assertEquals("337", MemberDateQualifier.ELIGIBILITY_END.getCode());
        assertEquals("285", MemberDateQualifier.HIRE.getCode());
        assertEquals("286", MemberDateQualifier.RETIREMENT.getCode());
        assertEquals("380", MemberDateQualifier.SIGNATURE.getCode());
        assertEquals("328", MemberDateQualifier.STATUS_CHANGE.getCode());
        assertEquals("036", MemberDateQualifier.TERMINATION.getCode());
        assertEquals("301", MemberDateQualifier.COBRA_QUALIFYING_EVENT.getCode());
        assertEquals("288", MemberDateQualifier.COBRA_ELECTION.getCode());
        assertEquals("340", MemberDateQualifier.COBRA_BEGIN.getCode());
        assertEquals("341", MemberDateQualifier.COBRA_END.getCode());
        assertEquals("289", MemberDateQualifier.PREMIUM_PAID_TO_DATE.getCode());
    }
}