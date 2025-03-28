/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InterchangeControlVersionNumberTest {

    @Test
    void testEnumValues() {
        assertEquals(20, InterchangeControlVersionNumber.values().length);

        assertNotNull(InterchangeControlVersionNumber.X12_1987);
        assertNotNull(InterchangeControlVersionNumber.X12_DRAFT_1988);
        assertNotNull(InterchangeControlVersionNumber.X12_1997);
        assertNotNull(InterchangeControlVersionNumber.X12_2003);
    }

    @Test
    void testEnumProperties() {
        assertEquals("00200", InterchangeControlVersionNumber.X12_1987.getCode());
        assertEquals("ASC X12 Standards Issued by ANSI in 1987",
                InterchangeControlVersionNumber.X12_1987.getDescription());

        assertEquals("00201", InterchangeControlVersionNumber.X12_DRAFT_1988.getCode());
        assertEquals("Draft Standards for Trial Use Approved by ASC X12 Through August 1988",
                InterchangeControlVersionNumber.X12_DRAFT_1988.getDescription());

        assertEquals("00400", InterchangeControlVersionNumber.X12_1997.getCode());
        assertEquals("ASC X12 Standards Issued by ANSI in 1997",
                InterchangeControlVersionNumber.X12_1997.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(InterchangeControlVersionNumber.X12_1987,
                InterchangeControlVersionNumber.fromString("00200"));

        assertEquals(InterchangeControlVersionNumber.X12_1987,
                InterchangeControlVersionNumber.fromString("1987"));
        assertEquals(InterchangeControlVersionNumber.X12_1987,
                InterchangeControlVersionNumber.fromString("x12 1987"));

        assertThrows(IllegalArgumentException.class, () ->
                InterchangeControlVersionNumber.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, InterchangeControlVersionNumber expected) throws Exception {
        assertEquals(expected, InterchangeControlVersionNumber.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() throws Exception {
        return Stream.of(
                Arguments.of("00200", InterchangeControlVersionNumber.X12_1987),
                Arguments.of("1987", InterchangeControlVersionNumber.X12_1987),

                Arguments.of("00201", InterchangeControlVersionNumber.X12_DRAFT_1988),
                Arguments.of("1988", InterchangeControlVersionNumber.X12_DRAFT_1988),

                Arguments.of("00204", InterchangeControlVersionNumber.X12_DRAFT_1989),
                Arguments.of("1989", InterchangeControlVersionNumber.X12_DRAFT_1989),

                Arguments.of("00300", InterchangeControlVersionNumber.X12_1992),

                Arguments.of("00301", InterchangeControlVersionNumber.X12_DRAFT_OCT_1990),

                Arguments.of("00302", InterchangeControlVersionNumber.X12_DRAFT_OCT_1991),

                Arguments.of("00303", InterchangeControlVersionNumber.X12_DRAFT_OCT_1992),

                Arguments.of("00304", InterchangeControlVersionNumber.X12_DRAFT_OCT_1993),

                Arguments.of("00305", InterchangeControlVersionNumber.X12_DRAFT_OCT_1994),

                Arguments.of("00306", InterchangeControlVersionNumber.X12_DRAFT_OCT_1995),

                Arguments.of("00307", InterchangeControlVersionNumber.X12_DRAFT_OCT_1996),

                Arguments.of("00400", InterchangeControlVersionNumber.X12_1997),

                Arguments.of("00401", InterchangeControlVersionNumber.X12_OCT_1997),

                Arguments.of("00402", InterchangeControlVersionNumber.X12_OCT_1998),

                Arguments.of("00403", InterchangeControlVersionNumber.X12_OCT_1999),

                Arguments.of("00404", InterchangeControlVersionNumber.X12_OCT_2000),

                Arguments.of("00405", InterchangeControlVersionNumber.X12_OCT_2001),

                Arguments.of("00406", InterchangeControlVersionNumber.X12_OCT_2002),

                Arguments.of("00500", InterchangeControlVersionNumber.X12_2003),

                Arguments.of("00501", InterchangeControlVersionNumber.X12_OCT_2003)
        );
    }
}