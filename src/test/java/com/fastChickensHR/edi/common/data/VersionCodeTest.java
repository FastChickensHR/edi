/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VersionCodeTest {

    @Test
    void testEnumValues() {
        assertEquals("001000", VersionCode.VERSION_001000.getCode());
        assertEquals("ASC X12 Standards Approved by ANSI in 1983", VersionCode.VERSION_001000.getDescription());

        assertEquals("003050", VersionCode.VERSION_003050.getCode());
        assertEquals("Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1994",
                VersionCode.VERSION_003050.getDescription());

        assertEquals("005010", VersionCode.VERSION_005010.getCode());
        assertEquals("Standards Approved for Publication by ASC X12 Procedures Review Board through October 2003",
                VersionCode.VERSION_005010.getDescription());
    }

    @Test
    void testEnumProperties() {
        for (VersionCode version : VersionCode.values()) {
            assertNotNull(version.getCode(), "Code should not be null");
            assertFalse(version.getCode().isEmpty(), "Code should not be empty");

            assertNotNull(version.getDescription(), "Description should not be null");
            assertFalse(version.getDescription().isEmpty(), "Description should not be empty");
        }
    }

    @Test
    void testFromString() {
        assertEquals(VersionCode.VERSION_001000, VersionCode.fromString("001000"));
        assertEquals(VersionCode.VERSION_003010, VersionCode.fromString("003010"));
        assertEquals(VersionCode.VERSION_004030, VersionCode.fromString("004030"));
        assertEquals(VersionCode.VERSION_005010, VersionCode.fromString("005010"));

        assertEquals(VersionCode.VERSION_003050, VersionCode.fromString("VERSION_003050"));

        assertThrows(IllegalArgumentException.class, () -> VersionCode.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> VersionCode.fromString("999999"));
        assertThrows(IllegalArgumentException.class, () -> VersionCode.fromString(null));
    }

    @Test
    void testToString() {
        assertEquals("001000", VersionCode.VERSION_001000.toString());
        assertEquals("003070", VersionCode.VERSION_003070.toString());
        assertEquals("005010", VersionCode.VERSION_005010.toString());
    }

    @Test
    void testAllVersionsAreDefined() {
        assertEquals(53, VersionCode.values().length,
                "Expected 53 version codes to be defined");

        assertTrue(Arrays.stream(VersionCode.values())
                        .anyMatch(v -> v.getCode().equals("001000")),
                "Version 001000 should be defined");

        assertTrue(Arrays.stream(VersionCode.values())
                        .anyMatch(v -> v.getCode().equals("003010")),
                "Version 003010 should be defined");

        assertTrue(Arrays.stream(VersionCode.values())
                        .anyMatch(v -> v.getCode().equals("004010")),
                "Version 004010 should be defined");

        assertTrue(Arrays.stream(VersionCode.values())
                        .anyMatch(v -> v.getCode().equals("005010")),
                "Version 005010 should be defined");
    }
}