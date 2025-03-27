/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BenefitStatusCodeTest {

    @Test
    void testEnumValues() {
        BenefitStatusCode[] values = BenefitStatusCode.values();
        assertEquals(6, values.length, "Expected 6 benefit status codes");

        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.ACTIVE), "ACTIVE status is missing");
        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.COBRA), "COBRA status is missing");
        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.DISABLED), "DISABLED status is missing");
        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.RETIREE), "RETIREE status is missing");
        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.SURVIVING_INSURED), "SURVIVING_INSURED status is missing");
        assertTrue(Arrays.asList(values).contains(BenefitStatusCode.TERMINATED), "TERMINATED status is missing");
    }

    @Test
    void testEnumProperties() {
        assertEquals("A", BenefitStatusCode.ACTIVE.getCode());
        assertEquals("Active", BenefitStatusCode.ACTIVE.getDescription());

        assertEquals("C", BenefitStatusCode.COBRA.getCode());
        assertEquals("COBRA", BenefitStatusCode.COBRA.getDescription());

        assertEquals("D", BenefitStatusCode.DISABLED.getCode());
        assertEquals("Disabled", BenefitStatusCode.DISABLED.getDescription());

        assertEquals("R", BenefitStatusCode.RETIREE.getCode());
        assertEquals("Retiree", BenefitStatusCode.RETIREE.getDescription());

        assertEquals("S", BenefitStatusCode.SURVIVING_INSURED.getCode());
        assertEquals("Surviving Insured", BenefitStatusCode.SURVIVING_INSURED.getDescription());

        assertEquals("T", BenefitStatusCode.TERMINATED.getCode());
        assertEquals("Terminated", BenefitStatusCode.TERMINATED.getDescription());
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, BenefitStatusCode expected) throws Exception {
        Field lookupField = BenefitStatusCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<BenefitStatusCode> lookup = (EdiEnumLookup<BenefitStatusCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("a", BenefitStatusCode.ACTIVE),
                Arguments.of("active coverage", BenefitStatusCode.ACTIVE),
                Arguments.of("current", BenefitStatusCode.ACTIVE),
                Arguments.of("enrolled", BenefitStatusCode.ACTIVE),

                Arguments.of("c", BenefitStatusCode.COBRA),
                Arguments.of("cobra coverage", BenefitStatusCode.COBRA),
                Arguments.of("consolidated omnibus budget reconciliation act", BenefitStatusCode.COBRA),
                Arguments.of("continuation", BenefitStatusCode.COBRA),
                Arguments.of("continuation coverage", BenefitStatusCode.COBRA),
                Arguments.of("continued benefits", BenefitStatusCode.COBRA),
                Arguments.of("extended coverage", BenefitStatusCode.COBRA),
                Arguments.of("cobra eligible", BenefitStatusCode.COBRA),
                Arguments.of("cobra qualified", BenefitStatusCode.COBRA),

                Arguments.of("d", BenefitStatusCode.DISABLED),
                Arguments.of("disability", BenefitStatusCode.DISABLED),
                Arguments.of("on disability", BenefitStatusCode.DISABLED),
                Arguments.of("disability benefits", BenefitStatusCode.DISABLED),
                Arguments.of("ltd", BenefitStatusCode.DISABLED),
                Arguments.of("long term disability", BenefitStatusCode.DISABLED),
                Arguments.of("short term disability", BenefitStatusCode.DISABLED),
                Arguments.of("std", BenefitStatusCode.DISABLED),

                Arguments.of("r", BenefitStatusCode.RETIREE),
                Arguments.of("retired", BenefitStatusCode.RETIREE),
                Arguments.of("retirement", BenefitStatusCode.RETIREE),
                Arguments.of("retirement benefits", BenefitStatusCode.RETIREE),
                Arguments.of("pension", BenefitStatusCode.RETIREE),
                Arguments.of("pensioner", BenefitStatusCode.RETIREE),
                Arguments.of("former employee", BenefitStatusCode.RETIREE),
                Arguments.of("emeritus", BenefitStatusCode.RETIREE),
                Arguments.of("retired employee", BenefitStatusCode.RETIREE),

                Arguments.of("s", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("survivor", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("s", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("survivor", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("surviving dependent", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("widow", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("widower", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("bereaved", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("surviving spouse", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("survivor benefits", BenefitStatusCode.SURVIVING_INSURED),
                Arguments.of("death benefit", BenefitStatusCode.SURVIVING_INSURED),

                Arguments.of("t", BenefitStatusCode.TERMINATED),
                Arguments.of("term", BenefitStatusCode.TERMINATED),
                Arguments.of("termed", BenefitStatusCode.TERMINATED),
                Arguments.of("termination", BenefitStatusCode.TERMINATED),
                Arguments.of("cancelled", BenefitStatusCode.TERMINATED),
                Arguments.of("canceled", BenefitStatusCode.TERMINATED),
                Arguments.of("ended", BenefitStatusCode.TERMINATED),
                Arguments.of("expired", BenefitStatusCode.TERMINATED),
                Arguments.of("discontinued", BenefitStatusCode.TERMINATED),
                Arguments.of("no longer active", BenefitStatusCode.TERMINATED),
                Arguments.of("no longer employed", BenefitStatusCode.TERMINATED),
                Arguments.of("separation", BenefitStatusCode.TERMINATED),
                Arguments.of("let go", BenefitStatusCode.TERMINATED),
                Arguments.of("laid off", BenefitStatusCode.TERMINATED),
                Arguments.of("fired", BenefitStatusCode.TERMINATED),
                Arguments.of("inactive", BenefitStatusCode.TERMINATED)
        );
    }
}