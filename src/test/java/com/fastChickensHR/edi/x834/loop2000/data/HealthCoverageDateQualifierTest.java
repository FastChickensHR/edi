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

class HealthCoverageDateQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(7, HealthCoverageDateQualifier.values().length);

        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.EFFECTIVE_DATE));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.EXPIRATION_DATE));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.ELIGIBILITY_BEGIN));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.ELIGIBILITY_END));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.COBRA_BEGIN));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.COBRA_END));
        assertTrue(Arrays.asList(HealthCoverageDateQualifier.values()).contains(HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("348", HealthCoverageDateQualifier.EFFECTIVE_DATE.getCode());
        assertEquals("Plan Begin Date", HealthCoverageDateQualifier.EFFECTIVE_DATE.getDescription());

        assertEquals("349", HealthCoverageDateQualifier.EXPIRATION_DATE.getCode());
        assertEquals("Plan End Date", HealthCoverageDateQualifier.EXPIRATION_DATE.getDescription());

        assertEquals("356", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN.getCode());
        assertEquals("Eligibility Begin Date", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN.getDescription());

        assertEquals("357", HealthCoverageDateQualifier.ELIGIBILITY_END.getCode());
        assertEquals("Eligibility End Date", HealthCoverageDateQualifier.ELIGIBILITY_END.getDescription());

        assertEquals("343", HealthCoverageDateQualifier.COBRA_BEGIN.getCode());
        assertEquals("COBRA Begin Date", HealthCoverageDateQualifier.COBRA_BEGIN.getDescription());

        assertEquals("344", HealthCoverageDateQualifier.COBRA_END.getCode());
        assertEquals("COBRA End Date", HealthCoverageDateQualifier.COBRA_END.getDescription());

        assertEquals("309", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE.getCode());
        assertEquals("Premium Paid To Date", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE.getDescription());

        for (HealthCoverageDateQualifier qualifier : HealthCoverageDateQualifier.values()) {
            assertEquals(qualifier.getCode(), qualifier.toString());
        }
    }

    @Test
    void testFromString() {
        assertEquals(HealthCoverageDateQualifier.EFFECTIVE_DATE, HealthCoverageDateQualifier.fromString("348"));
        assertEquals(HealthCoverageDateQualifier.EXPIRATION_DATE, HealthCoverageDateQualifier.fromString("349"));
        assertEquals(HealthCoverageDateQualifier.ELIGIBILITY_BEGIN, HealthCoverageDateQualifier.fromString("356"));
        assertEquals(HealthCoverageDateQualifier.ELIGIBILITY_END, HealthCoverageDateQualifier.fromString("357"));
        assertEquals(HealthCoverageDateQualifier.COBRA_BEGIN, HealthCoverageDateQualifier.fromString("343"));
        assertEquals(HealthCoverageDateQualifier.COBRA_END, HealthCoverageDateQualifier.fromString("344"));
        assertEquals(HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE, HealthCoverageDateQualifier.fromString("309"));

        assertThrows(IllegalArgumentException.class, () -> HealthCoverageDateQualifier.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, HealthCoverageDateQualifier expected) throws Exception {
        Field lookupField = HealthCoverageDateQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<HealthCoverageDateQualifier> lookup = (EdiEnumLookup<HealthCoverageDateQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("348", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("349", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("356", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("357", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("343", HealthCoverageDateQualifier.COBRA_BEGIN),
                Arguments.of("344", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("309", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),

                Arguments.of("EFFECTIVE_DATE", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("EXPIRATION_DATE", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("ELIGIBILITY_BEGIN", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("ELIGIBILITY_END", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("COBRA_BEGIN", HealthCoverageDateQualifier.COBRA_BEGIN),
                Arguments.of("COBRA_END", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("PREMIUM_PAID_TO_DATE", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),

                Arguments.of("Plan Begin Date", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("Plan End Date", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("Eligibility Begin Date", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("Eligibility End Date", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("COBRA Begin Date", HealthCoverageDateQualifier.COBRA_BEGIN),
                Arguments.of("COBRA End Date", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("Premium Paid To Date", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),

                Arguments.of("start date", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("begin date", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("start", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("begins", HealthCoverageDateQualifier.EFFECTIVE_DATE),
                Arguments.of("effective", HealthCoverageDateQualifier.EFFECTIVE_DATE),

                Arguments.of("end date", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("termination date", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("expires", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("expiry", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("ending", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("termination", HealthCoverageDateQualifier.EXPIRATION_DATE),
                Arguments.of("cancellation", HealthCoverageDateQualifier.EXPIRATION_DATE),

                Arguments.of("eligibility start", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("eligible from", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("eligible start", HealthCoverageDateQualifier.ELIGIBILITY_BEGIN),

                Arguments.of("eligibility stop", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("eligible until", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("eligible end", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("benefits end", HealthCoverageDateQualifier.ELIGIBILITY_END),
                Arguments.of("benefits stop", HealthCoverageDateQualifier.ELIGIBILITY_END),

                Arguments.of("cobra start", HealthCoverageDateQualifier.COBRA_BEGIN),
                Arguments.of("cobra effective", HealthCoverageDateQualifier.COBRA_BEGIN),
                Arguments.of("consolidated omnibus budget reconciliation act start", HealthCoverageDateQualifier.COBRA_BEGIN),

                Arguments.of("cobra stop", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("cobra termination", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("cobra expiration", HealthCoverageDateQualifier.COBRA_END),
                Arguments.of("consolidated omnibus budget reconciliation act end", HealthCoverageDateQualifier.COBRA_END),

                Arguments.of("premium paid", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("paid through", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("paid to", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("paid until", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("premium through", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("payment date", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE)
        );
    }
}