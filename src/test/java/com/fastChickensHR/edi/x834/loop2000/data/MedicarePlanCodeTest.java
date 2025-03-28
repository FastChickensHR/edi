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

class MedicarePlanCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(7, MedicarePlanCode.values().length,
                "MedicarePlanCode should have 7 enum values");

        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.HOSPITAL_ONLY));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.MEDICAL_ONLY));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.HOSPITAL_AND_MEDICAL));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.MEDICARE_ADVANTAGE));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.PRESCRIPTION_DRUG));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.MEDIGAP));
        assertTrue(Arrays.asList(MedicarePlanCode.values()).contains(MedicarePlanCode.NO_MEDICARE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("A", MedicarePlanCode.HOSPITAL_ONLY.getCode());
        assertEquals("Hospital Only (Part A)", MedicarePlanCode.HOSPITAL_ONLY.getDescription());

        assertEquals("B", MedicarePlanCode.MEDICAL_ONLY.getCode());
        assertEquals("Medical Only (Part B)", MedicarePlanCode.MEDICAL_ONLY.getDescription());

        assertEquals("C", MedicarePlanCode.HOSPITAL_AND_MEDICAL.getCode());
        assertEquals("Hospital and Medical (Parts A and B)", MedicarePlanCode.HOSPITAL_AND_MEDICAL.getDescription());

        assertEquals("D", MedicarePlanCode.MEDICARE_ADVANTAGE.getCode());
        assertEquals("Medicare Advantage (Part C)", MedicarePlanCode.MEDICARE_ADVANTAGE.getDescription());

        assertEquals("E", MedicarePlanCode.PRESCRIPTION_DRUG.getCode());
        assertEquals("Prescription Drug (Part D)", MedicarePlanCode.PRESCRIPTION_DRUG.getDescription());

        assertEquals("F", MedicarePlanCode.MEDIGAP.getCode());
        assertEquals("Medigap (Medicare Supplement)", MedicarePlanCode.MEDIGAP.getDescription());

        assertEquals("N", MedicarePlanCode.NO_MEDICARE.getCode());
        assertEquals("No Medicare Coverage", MedicarePlanCode.NO_MEDICARE.getDescription());
    }

    @Test
    void testFromString() throws Exception {
        // Access the LOOKUP field
        Field lookupField = MedicarePlanCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MedicarePlanCode> lookup = (EdiEnumLookup<MedicarePlanCode>) lookupField.get(null);

        // Test exact code matches
        assertEquals(MedicarePlanCode.HOSPITAL_ONLY, lookup.fromString("A"));
        assertEquals(MedicarePlanCode.MEDICAL_ONLY, lookup.fromString("B"));
        assertEquals(MedicarePlanCode.HOSPITAL_AND_MEDICAL, lookup.fromString("C"));
        assertEquals(MedicarePlanCode.MEDICARE_ADVANTAGE, lookup.fromString("D"));
        assertEquals(MedicarePlanCode.PRESCRIPTION_DRUG, lookup.fromString("E"));
        assertEquals(MedicarePlanCode.MEDIGAP, lookup.fromString("F"));
        assertEquals(MedicarePlanCode.NO_MEDICARE, lookup.fromString("N"));

        assertEquals(MedicarePlanCode.HOSPITAL_ONLY, lookup.fromString("a"));
        assertEquals(MedicarePlanCode.MEDICAL_ONLY, lookup.fromString("b"));

        assertEquals(MedicarePlanCode.HOSPITAL_ONLY, lookup.fromString("HOSPITAL_ONLY"));
        assertEquals(MedicarePlanCode.MEDICAL_ONLY, lookup.fromString("MEDICAL_ONLY"));

        assertEquals(MedicarePlanCode.HOSPITAL_ONLY, lookup.fromString("Hospital Only (Part A)"));
        assertEquals(MedicarePlanCode.MEDICAL_ONLY, lookup.fromString("Medical Only (Part B)"));

        assertEquals(MedicarePlanCode.HOSPITAL_ONLY, lookup.fromString("Part A"));
        assertEquals(MedicarePlanCode.MEDICAL_ONLY, lookup.fromString("Part B"));
        assertEquals(MedicarePlanCode.MEDICARE_ADVANTAGE, lookup.fromString("Part C"));
        assertEquals(MedicarePlanCode.PRESCRIPTION_DRUG, lookup.fromString("Part D"));

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(null));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, MedicarePlanCode expected) throws Exception {
        Field lookupField = MedicarePlanCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MedicarePlanCode> lookup = (EdiEnumLookup<MedicarePlanCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("A", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("B", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("C", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("D", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("E", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("F", MedicarePlanCode.MEDIGAP),
                Arguments.of("N", MedicarePlanCode.NO_MEDICARE),

                Arguments.of("a", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("b", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("c", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("d", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("e", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("f", MedicarePlanCode.MEDIGAP),
                Arguments.of("n", MedicarePlanCode.NO_MEDICARE),

                Arguments.of("HOSPITAL_ONLY", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("MEDICAL_ONLY", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("HOSPITAL_AND_MEDICAL", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("MEDICARE_ADVANTAGE", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("PRESCRIPTION_DRUG", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("MEDIGAP", MedicarePlanCode.MEDIGAP),
                Arguments.of("NO_MEDICARE", MedicarePlanCode.NO_MEDICARE),

                Arguments.of("Hospital Only (Part A)", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("Medical Only (Part B)", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("Hospital and Medical (Parts A and B)", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("Medicare Advantage (Part C)", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("Prescription Drug (Part D)", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("Medigap (Medicare Supplement)", MedicarePlanCode.MEDIGAP),
                Arguments.of("No Medicare Coverage", MedicarePlanCode.NO_MEDICARE),

                Arguments.of("part a", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("hospital insurance", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("inpatient", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("hospital care", MedicarePlanCode.HOSPITAL_ONLY),
                Arguments.of("skilled nursing", MedicarePlanCode.HOSPITAL_ONLY),

                Arguments.of("part b", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("medical insurance", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("outpatient", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("doctor visits", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("physician services", MedicarePlanCode.MEDICAL_ONLY),
                Arguments.of("preventive services", MedicarePlanCode.MEDICAL_ONLY),

                Arguments.of("parts a and b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("ab", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("a and b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("a & b", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("hospital and medical", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("original medicare", MedicarePlanCode.HOSPITAL_AND_MEDICAL),
                Arguments.of("traditional medicare", MedicarePlanCode.HOSPITAL_AND_MEDICAL),

                Arguments.of("part c", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("advantage", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("ma", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("managed care", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("medicare health plan", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("hmo", MedicarePlanCode.MEDICARE_ADVANTAGE),
                Arguments.of("ppo", MedicarePlanCode.MEDICARE_ADVANTAGE),

                Arguments.of("part d", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("drug coverage", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("prescription coverage", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("rx", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("medication", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("pdp", MedicarePlanCode.PRESCRIPTION_DRUG),
                Arguments.of("pharmacy", MedicarePlanCode.PRESCRIPTION_DRUG),

                Arguments.of("supplement", MedicarePlanCode.MEDIGAP),
                Arguments.of("supplemental", MedicarePlanCode.MEDIGAP),
                Arguments.of("gap coverage", MedicarePlanCode.MEDIGAP),
                Arguments.of("med supp", MedicarePlanCode.MEDIGAP),
                Arguments.of("supplementary", MedicarePlanCode.MEDIGAP),
                Arguments.of("secondary insurance", MedicarePlanCode.MEDIGAP),
                Arguments.of("gap insurance", MedicarePlanCode.MEDIGAP),

                Arguments.of("no coverage", MedicarePlanCode.NO_MEDICARE),
                Arguments.of("not enrolled", MedicarePlanCode.NO_MEDICARE)
        );
    }
}