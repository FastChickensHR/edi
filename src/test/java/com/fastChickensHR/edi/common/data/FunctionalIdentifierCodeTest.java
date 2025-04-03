/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalIdentifierCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("AA", FunctionalIdentifierCode.ACCOUNT_ANALYSIS.getCode());
        assertEquals("Account Analysis (822)", FunctionalIdentifierCode.ACCOUNT_ANALYSIS.getDescription());
        assertEquals("BE", FunctionalIdentifierCode.BENEFIT_ENROLLMENT.getCode());
        assertEquals("Benefit Enrollment and Maintenance (834)", FunctionalIdentifierCode.BENEFIT_ENROLLMENT.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (FunctionalIdentifierCode code : FunctionalIdentifierCode.values()) {
            assertNotNull(code.getCode());
            assertNotNull(code.getDescription());
            assertFalse(code.getCode().isEmpty());
            assertFalse(code.getDescription().isEmpty());
        }
    }

    @Test
    void testFromString() {
        FunctionalIdentifierCode aa = FunctionalIdentifierCode.valueOf("ACCOUNT_ANALYSIS");
        FunctionalIdentifierCode be = FunctionalIdentifierCode.valueOf("BENEFIT_ENROLLMENT");

        assertEquals(aa, FunctionalIdentifierCode.ACCOUNT_ANALYSIS);
        assertEquals(be, FunctionalIdentifierCode.BENEFIT_ENROLLMENT);

        assertThrows(IllegalArgumentException.class, () -> FunctionalIdentifierCode.valueOf("NON_EXISTENT"));
    }
}