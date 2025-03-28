/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MemberIndicatorTest {

    @Test
    void testEnumValues() {
        assertEquals(2, MemberIndicator.values().length);
        assertEquals("Y", MemberIndicator.INSURED.getCode());
        assertEquals("N", MemberIndicator.NOT_INSURED.getCode());
    }

    @Test
    void testEnumProperties() {
        assertEquals("Insured", MemberIndicator.INSURED.getDescription());
        assertEquals("Not Insured", MemberIndicator.NOT_INSURED.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(MemberIndicator.INSURED, MemberIndicator.fromString("Y"));
        assertEquals(MemberIndicator.NOT_INSURED, MemberIndicator.fromString("N"));

        assertEquals(MemberIndicator.INSURED, MemberIndicator.fromString("y"));
        assertEquals(MemberIndicator.NOT_INSURED, MemberIndicator.fromString("n"));

        assertEquals(MemberIndicator.INSURED, MemberIndicator.fromString("Insured"));
        assertEquals(MemberIndicator.NOT_INSURED, MemberIndicator.fromString("Not Insured"));

        assertEquals(MemberIndicator.INSURED, MemberIndicator.fromString("covered"));
        assertEquals(MemberIndicator.NOT_INSURED, MemberIndicator.fromString("not covered"));

        assertThrows(IllegalArgumentException.class, () -> MemberIndicator.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, MemberIndicator expected) throws Exception {
        Field lookupField = MemberIndicator.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MemberIndicator> lookup =
                (EdiEnumLookup<MemberIndicator>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
        assertEquals(expected, MemberIndicator.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("Y", MemberIndicator.INSURED),
                Arguments.of("N", MemberIndicator.NOT_INSURED),

                Arguments.of("INSURED", MemberIndicator.INSURED),
                Arguments.of("NOT_INSURED", MemberIndicator.NOT_INSURED),

                Arguments.of("Insured", MemberIndicator.INSURED),
                Arguments.of("Not Insured", MemberIndicator.NOT_INSURED),

                Arguments.of("y", MemberIndicator.INSURED),
                Arguments.of("yes", MemberIndicator.INSURED),
                Arguments.of("covered", MemberIndicator.INSURED),
                Arguments.of("enrolled", MemberIndicator.INSURED),
                Arguments.of("active", MemberIndicator.INSURED),
                Arguments.of("eligible", MemberIndicator.INSURED),
                Arguments.of("has coverage", MemberIndicator.INSURED),
                Arguments.of("has insurance", MemberIndicator.INSURED),
                Arguments.of("policyholder", MemberIndicator.INSURED),
                Arguments.of("member", MemberIndicator.INSURED),
                Arguments.of("participant", MemberIndicator.INSURED),

                Arguments.of("n", MemberIndicator.NOT_INSURED),
                Arguments.of("no", MemberIndicator.NOT_INSURED),
                Arguments.of("not covered", MemberIndicator.NOT_INSURED),
                Arguments.of("not enrolled", MemberIndicator.NOT_INSURED),
                Arguments.of("inactive", MemberIndicator.NOT_INSURED),
                Arguments.of("ineligible", MemberIndicator.NOT_INSURED),
                Arguments.of("no coverage", MemberIndicator.NOT_INSURED),
                Arguments.of("no insurance", MemberIndicator.NOT_INSURED),
                Arguments.of("non-member", MemberIndicator.NOT_INSURED),
                Arguments.of("non member", MemberIndicator.NOT_INSURED),
                Arguments.of("terminated", MemberIndicator.NOT_INSURED),
                Arguments.of("cancelled", MemberIndicator.NOT_INSURED)
        );
    }
}