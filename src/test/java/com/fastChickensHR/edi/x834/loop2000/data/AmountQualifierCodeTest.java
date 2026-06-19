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
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AmountQualifierCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(5, AmountQualifierCode.values().length);
        assertTrue(Arrays.asList(AmountQualifierCode.values()).contains(AmountQualifierCode.PREMIUM_AMOUNT));
    }

    @Test
    void testEnumProperties() {
        assertEquals("B9", AmountQualifierCode.COINSURANCE_ACTUAL.getCode());
        assertEquals("Co-Insurance - Actual", AmountQualifierCode.COINSURANCE_ACTUAL.getDescription());

        assertEquals("D2", AmountQualifierCode.DEDUCTIBLE_AMOUNT.getCode());
        assertEquals("FK", AmountQualifierCode.PREMIUM_AMOUNT.getCode());
        assertEquals("P3", AmountQualifierCode.SPEND_DOWN.getCode());
        assertEquals("R", AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT.getCode());
    }

    @Test
    void testToStringReturnsCode() {
        assertEquals("FK", AmountQualifierCode.PREMIUM_AMOUNT.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFromString() throws Exception {
        Field lookupField = AmountQualifierCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<AmountQualifierCode> lookup =
                (EdiEnumLookup<AmountQualifierCode>) lookupField.get(null);

        assertEquals(AmountQualifierCode.PREMIUM_AMOUNT, lookup.fromString("FK"));
        assertEquals(AmountQualifierCode.PREMIUM_AMOUNT, lookup.fromString("premium"));
        assertEquals(AmountQualifierCode.PREMIUM_AMOUNT, lookup.fromString("monthly premium"));

        assertEquals(AmountQualifierCode.DEDUCTIBLE_AMOUNT, lookup.fromString("deductible"));
        assertEquals(AmountQualifierCode.DEDUCTIBLE_AMOUNT, lookup.fromString("ded"));

        assertEquals(AmountQualifierCode.COINSURANCE_ACTUAL, lookup.fromString("coinsurance"));
        assertEquals(AmountQualifierCode.SPEND_DOWN, lookup.fromString("spenddown"));
        assertEquals(AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT, lookup.fromString("expenditure"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
    }

    @Test
    void testStaticFromString() {
        assertEquals(AmountQualifierCode.PREMIUM_AMOUNT, AmountQualifierCode.fromString("FK"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    @SuppressWarnings("unchecked")
    void testAllLookupValues(String input, AmountQualifierCode expected) throws Exception {
        Field lookupField = AmountQualifierCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<AmountQualifierCode> lookup =
                (EdiEnumLookup<AmountQualifierCode>) lookupField.get(null);
        assertEquals(expected, lookup.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("B9", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("D2", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("FK", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("P3", AmountQualifierCode.SPEND_DOWN),
                Arguments.of("R", AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT),

                Arguments.of("premium", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("monthly premium", AmountQualifierCode.PREMIUM_AMOUNT),
                Arguments.of("deductible", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("ded", AmountQualifierCode.DEDUCTIBLE_AMOUNT),
                Arguments.of("coinsurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("co-insurance", AmountQualifierCode.COINSURANCE_ACTUAL),
                Arguments.of("spenddown", AmountQualifierCode.SPEND_DOWN),
                Arguments.of("expenditure", AmountQualifierCode.EXPECTED_EXPENDITURE_AMOUNT)
        );
    }
}
