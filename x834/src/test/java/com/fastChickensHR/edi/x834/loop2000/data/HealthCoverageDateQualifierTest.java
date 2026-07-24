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

class HealthCoverageDateQualifierTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(HealthCoverageDateQualifier.class)
    void resolvesFromCodeNameAndDescription(HealthCoverageDateQualifier constant) {
        assertEquals(constant, HealthCoverageDateQualifier.fromString(constant.getCode()));
        assertEquals(constant, HealthCoverageDateQualifier.fromString(constant.name()));
        assertEquals(constant, HealthCoverageDateQualifier.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, HealthCoverageDateQualifier expected) {
        assertEquals(expected, HealthCoverageDateQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
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
                Arguments.of("payment date", HealthCoverageDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> HealthCoverageDateQualifier.fromString(input));
    }
}
