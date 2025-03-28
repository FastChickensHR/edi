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

class MaintenanceReasonCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(11, MaintenanceReasonCode.values().length);

        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.ACTIVE));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.BIRTH));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.COBRA));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.DISABILITY));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.DEATH));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.RETIREMENT));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.MARRIAGE));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.STRIKE));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.TERMINATION));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL));
        assertTrue(Arrays.asList(MaintenanceReasonCode.values()).contains(MaintenanceReasonCode.ADMINISTRATIVE_ERROR));
    }

    @Test
    void testEnumProperties() {
        assertEquals("AC", MaintenanceReasonCode.ACTIVE.getCode());
        assertEquals("Active", MaintenanceReasonCode.ACTIVE.getDescription());

        assertEquals("BH", MaintenanceReasonCode.BIRTH.getCode());
        assertEquals("Birth", MaintenanceReasonCode.BIRTH.getDescription());

        assertEquals("CO", MaintenanceReasonCode.COBRA.getCode());
        assertEquals("COBRA", MaintenanceReasonCode.COBRA.getDescription());

        assertEquals("DI", MaintenanceReasonCode.DISABILITY.getCode());
        assertEquals("Disability", MaintenanceReasonCode.DISABILITY.getDescription());

        assertEquals("DN", MaintenanceReasonCode.DEATH.getCode());
        assertEquals("Death", MaintenanceReasonCode.DEATH.getDescription());

        assertEquals("ET", MaintenanceReasonCode.RETIREMENT.getCode());
        assertEquals("Retirement", MaintenanceReasonCode.RETIREMENT.getDescription());

        assertEquals("MA", MaintenanceReasonCode.MARRIAGE.getCode());
        assertEquals("Marriage", MaintenanceReasonCode.MARRIAGE.getDescription());

        assertEquals("ST", MaintenanceReasonCode.STRIKE.getCode());
        assertEquals("Strike", MaintenanceReasonCode.STRIKE.getDescription());

        assertEquals("TN", MaintenanceReasonCode.TERMINATION.getCode());
        assertEquals("Termination", MaintenanceReasonCode.TERMINATION.getDescription());

        assertEquals("VO", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL.getCode());
        assertEquals("Voluntary Withdrawal", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL.getDescription());

        assertEquals("XN", MaintenanceReasonCode.ADMINISTRATIVE_ERROR.getCode());
        assertEquals("Administrative Error", MaintenanceReasonCode.ADMINISTRATIVE_ERROR.getDescription());

        for (MaintenanceReasonCode code : MaintenanceReasonCode.values()) {
            assertEquals(code.getCode(), code.toString());
        }
    }

    @Test
    void testFromString() {
        assertEquals(MaintenanceReasonCode.ACTIVE, MaintenanceReasonCode.fromString("AC"));
        assertEquals(MaintenanceReasonCode.BIRTH, MaintenanceReasonCode.fromString("BH"));
        assertEquals(MaintenanceReasonCode.COBRA, MaintenanceReasonCode.fromString("CO"));
        assertEquals(MaintenanceReasonCode.DISABILITY, MaintenanceReasonCode.fromString("DI"));
        assertEquals(MaintenanceReasonCode.DEATH, MaintenanceReasonCode.fromString("DN"));
        assertEquals(MaintenanceReasonCode.RETIREMENT, MaintenanceReasonCode.fromString("ET"));
        assertEquals(MaintenanceReasonCode.MARRIAGE, MaintenanceReasonCode.fromString("MA"));
        assertEquals(MaintenanceReasonCode.STRIKE, MaintenanceReasonCode.fromString("ST"));
        assertEquals(MaintenanceReasonCode.TERMINATION, MaintenanceReasonCode.fromString("TN"));
        assertEquals(MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL, MaintenanceReasonCode.fromString("VO"));
        assertEquals(MaintenanceReasonCode.ADMINISTRATIVE_ERROR, MaintenanceReasonCode.fromString("XN"));

        assertThrows(IllegalArgumentException.class, () -> MaintenanceReasonCode.fromString("InvalidCode"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, MaintenanceReasonCode expected) throws Exception {
        Field lookupField = MaintenanceReasonCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MaintenanceReasonCode> lookup = (EdiEnumLookup<MaintenanceReasonCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("AC", MaintenanceReasonCode.ACTIVE),
                Arguments.of("BH", MaintenanceReasonCode.BIRTH),
                Arguments.of("CO", MaintenanceReasonCode.COBRA),
                Arguments.of("DI", MaintenanceReasonCode.DISABILITY),
                Arguments.of("DN", MaintenanceReasonCode.DEATH),
                Arguments.of("ET", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("MA", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("ST", MaintenanceReasonCode.STRIKE),
                Arguments.of("TN", MaintenanceReasonCode.TERMINATION),
                Arguments.of("VO", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("XN", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),

                Arguments.of("ACTIVE", MaintenanceReasonCode.ACTIVE),
                Arguments.of("BIRTH", MaintenanceReasonCode.BIRTH),
                Arguments.of("COBRA", MaintenanceReasonCode.COBRA),
                Arguments.of("DISABILITY", MaintenanceReasonCode.DISABILITY),
                Arguments.of("DEATH", MaintenanceReasonCode.DEATH),
                Arguments.of("RETIREMENT", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("MARRIAGE", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("STRIKE", MaintenanceReasonCode.STRIKE),
                Arguments.of("TERMINATION", MaintenanceReasonCode.TERMINATION),
                Arguments.of("VOLUNTARY_WITHDRAWAL", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("ADMINISTRATIVE_ERROR", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),

                Arguments.of("Active", MaintenanceReasonCode.ACTIVE),
                Arguments.of("Birth", MaintenanceReasonCode.BIRTH),
                Arguments.of("COBRA", MaintenanceReasonCode.COBRA),
                Arguments.of("Disability", MaintenanceReasonCode.DISABILITY),
                Arguments.of("Death", MaintenanceReasonCode.DEATH),
                Arguments.of("Retirement", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("Marriage", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("Strike", MaintenanceReasonCode.STRIKE),
                Arguments.of("Termination", MaintenanceReasonCode.TERMINATION),
                Arguments.of("Voluntary Withdrawal", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("Administrative Error", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),

                Arguments.of("currently active", MaintenanceReasonCode.ACTIVE),
                Arguments.of("active status", MaintenanceReasonCode.ACTIVE),
                Arguments.of("current", MaintenanceReasonCode.ACTIVE),
                Arguments.of("employed", MaintenanceReasonCode.ACTIVE),

                Arguments.of("newborn", MaintenanceReasonCode.BIRTH),
                Arguments.of("new child", MaintenanceReasonCode.BIRTH),
                Arguments.of("baby", MaintenanceReasonCode.BIRTH),
                Arguments.of("child birth", MaintenanceReasonCode.BIRTH),
                Arguments.of("new dependent", MaintenanceReasonCode.BIRTH),

                Arguments.of("consolidated omnibus budget reconciliation act", MaintenanceReasonCode.COBRA),
                Arguments.of("continuation coverage", MaintenanceReasonCode.COBRA),

                Arguments.of("disabled", MaintenanceReasonCode.DISABILITY),
                Arguments.of("medical disability", MaintenanceReasonCode.DISABILITY),
                Arguments.of("unable to work", MaintenanceReasonCode.DISABILITY),
                Arguments.of("incapacitated", MaintenanceReasonCode.DISABILITY),

                Arguments.of("deceased", MaintenanceReasonCode.DEATH),
                Arguments.of("died", MaintenanceReasonCode.DEATH),
                Arguments.of("passed away", MaintenanceReasonCode.DEATH),
                Arguments.of("fatality", MaintenanceReasonCode.DEATH),

                Arguments.of("retired", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("retiree", MaintenanceReasonCode.RETIREMENT),
                Arguments.of("pension", MaintenanceReasonCode.RETIREMENT),

                Arguments.of("married", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("wedding", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("spouse", MaintenanceReasonCode.MARRIAGE),
                Arguments.of("life event", MaintenanceReasonCode.MARRIAGE),

                Arguments.of("labor strike", MaintenanceReasonCode.STRIKE),
                Arguments.of("union strike", MaintenanceReasonCode.STRIKE),
                Arguments.of("work stoppage", MaintenanceReasonCode.STRIKE),
                Arguments.of("walkout", MaintenanceReasonCode.STRIKE),

                Arguments.of("terminated", MaintenanceReasonCode.TERMINATION),
                Arguments.of("fired", MaintenanceReasonCode.TERMINATION),
                Arguments.of("let go", MaintenanceReasonCode.TERMINATION),
                Arguments.of("layoff", MaintenanceReasonCode.TERMINATION),
                Arguments.of("laid off", MaintenanceReasonCode.TERMINATION),

                Arguments.of("quit", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("resigned", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("voluntary termination", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("opted out", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),
                Arguments.of("withdrawal", MaintenanceReasonCode.VOLUNTARY_WITHDRAWAL),

                Arguments.of("admin error", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),
                Arguments.of("correction", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),
                Arguments.of("mistake", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),
                Arguments.of("error", MaintenanceReasonCode.ADMINISTRATIVE_ERROR),
                Arguments.of("clerical error", MaintenanceReasonCode.ADMINISTRATIVE_ERROR)
                );
    }
}