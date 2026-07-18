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
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("348", MemberDateQualifier.BENEFIT_BEGIN.getCode());
        assertEquals("289", MemberDateQualifier.PREMIUM_PAID_TO_DATE.getCode());
    }
}