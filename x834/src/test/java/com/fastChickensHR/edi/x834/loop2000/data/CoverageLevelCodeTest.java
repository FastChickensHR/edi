/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoverageLevelCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(CoverageLevelCode.class)
    void resolvesFromCodeNameAndDescription(CoverageLevelCode constant) {
        assertEquals(constant, CoverageLevelCode.fromString(constant.getCode()));
        assertEquals(constant, CoverageLevelCode.fromString(constant.name()));
        assertEquals(constant, CoverageLevelCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, CoverageLevelCode expected) {
        assertEquals(expected, CoverageLevelCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("kids only", CoverageLevelCode.CHILDREN_ONLY),
                Arguments.of("child only", CoverageLevelCode.CHILDREN_ONLY),
                Arguments.of("just children", CoverageLevelCode.CHILDREN_ONLY),
                Arguments.of("dep only", CoverageLevelCode.DEPENDENTS_ONLY),
                Arguments.of("just dependents", CoverageLevelCode.DEPENDENTS_ONLY),
                Arguments.of("ee and children", CoverageLevelCode.EMPLOYEE_AND_CHILDREN),
                Arguments.of("employee + children", CoverageLevelCode.EMPLOYEE_AND_CHILDREN),
                Arguments.of("employee+children", CoverageLevelCode.EMPLOYEE_AND_CHILDREN),
                Arguments.of("parent and children", CoverageLevelCode.EMPLOYEE_AND_CHILDREN),
                Arguments.of("ee only", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("just employee", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("self only", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("self", CoverageLevelCode.EMPLOYEE_ONLY),
                Arguments.of("ee and spouse", CoverageLevelCode.EMPLOYEE_AND_SPOUSE),
                Arguments.of("employee + spouse", CoverageLevelCode.EMPLOYEE_AND_SPOUSE),
                Arguments.of("employee+spouse", CoverageLevelCode.EMPLOYEE_AND_SPOUSE),
                Arguments.of("family coverage", CoverageLevelCode.FAMILY),
                Arguments.of("whole family", CoverageLevelCode.FAMILY),
                Arguments.of("all family", CoverageLevelCode.FAMILY),
                Arguments.of("ind", CoverageLevelCode.INDIVIDUAL),
                Arguments.of("single", CoverageLevelCode.INDIVIDUAL),
                Arguments.of("spouse + children", CoverageLevelCode.SPOUSE_AND_CHILDREN),
                Arguments.of("spouse+children", CoverageLevelCode.SPOUSE_AND_CHILDREN),
                Arguments.of("partner and children", CoverageLevelCode.SPOUSE_AND_CHILDREN),
                Arguments.of("just spouse", CoverageLevelCode.SPOUSE_ONLY),
                Arguments.of("partner only", CoverageLevelCode.SPOUSE_ONLY),
                Arguments.of("2 party", CoverageLevelCode.TWO_PARTY),
                Arguments.of("two-party", CoverageLevelCode.TWO_PARTY),
                Arguments.of("couple", CoverageLevelCode.TWO_PARTY));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> CoverageLevelCode.fromString(input));
    }
}
