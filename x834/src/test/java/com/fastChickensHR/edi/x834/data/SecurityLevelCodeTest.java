/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityLevelCodeTest {

    /**
     * Every constant resolves from its own X12 code, its enum name, and its description — the three
     * round-trips {@link com.fastChickensHR.edi.x834.util.EdiEnumLookup} registers for each constant.
     * Driving this from {@link EnumSource} rather than a hand-listed table also guarantees no
     * constant's code, name, or description silently collides with another's in the shared lookup map.
     */
    @ParameterizedTest
    @EnumSource(SecurityLevelCode.class)
    void resolvesFromCodeNameAndDescription(SecurityLevelCode constant) {
        assertEquals(constant, SecurityLevelCode.fromString(constant.getCode()));
        assertEquals(constant, SecurityLevelCode.fromString(constant.name()));
        assertEquals(constant, SecurityLevelCode.fromString(constant.getDescription()));
    }

    /** The human-friendly aliases callers actually type resolve to the right constant. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, SecurityLevelCode expected) {
        assertEquals(expected, SecurityLevelCode.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("non-classified", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("unclassified", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("company unclassified", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("public", SecurityLevelCode.COMPANY_NON_CLASSIFIED),
                Arguments.of("internal", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("internal use", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("internal only", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("company internal", SecurityLevelCode.COMPANY_INTERNAL),
                Arguments.of("confidential", SecurityLevelCode.COMPANY_CONFIDENTIAL),
                Arguments.of("company conf", SecurityLevelCode.COMPANY_CONFIDENTIAL),
                Arguments.of("restricted", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("need to know", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("limited access", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("restricted access", SecurityLevelCode.COMPANY_CONFIDENTIAL_RESTRICTED),
                Arguments.of("registered", SecurityLevelCode.COMPANY_REGISTERED),
                Arguments.of("signature required", SecurityLevelCode.COMPANY_REGISTERED),
                Arguments.of("signed", SecurityLevelCode.COMPANY_REGISTERED),
                Arguments.of("personal information", SecurityLevelCode.PERSONAL),
                Arguments.of("private", SecurityLevelCode.PERSONAL),
                Arguments.of("proprietary", SecurityLevelCode.SUPPLIER_PROPRIETARY),
                Arguments.of("supplier", SecurityLevelCode.SUPPLIER_PROPRIETARY),
                Arguments.of("vendor proprietary", SecurityLevelCode.SUPPLIER_PROPRIETARY),
                Arguments.of("trading partner", SecurityLevelCode.COMPANY_DEFINED),
                Arguments.of("company specific", SecurityLevelCode.COMPANY_DEFINED),
                Arguments.of("competitive", SecurityLevelCode.COMPETITION_SENSITIVE),
                Arguments.of("competitor sensitive", SecurityLevelCode.COMPETITION_SENSITIVE),
                Arguments.of("competitive information", SecurityLevelCode.COMPETITION_SENSITIVE),
                Arguments.of("court", SecurityLevelCode.COURT_RESTRICTED),
                Arguments.of("legal restriction", SecurityLevelCode.COURT_RESTRICTED),
                Arguments.of("juvenile", SecurityLevelCode.JUVENILE_RECORD_RESTRICTED),
                Arguments.of("minor record", SecurityLevelCode.JUVENILE_RECORD_RESTRICTED),
                Arguments.of("juvenile record", SecurityLevelCode.JUVENILE_RECORD_RESTRICTED),
                Arguments.of("gov unclassified", SecurityLevelCode.GOVERNMENT_NON_CLASSIFIED),
                Arguments.of("government public", SecurityLevelCode.GOVERNMENT_NON_CLASSIFIED),
                Arguments.of("gov confidential", SecurityLevelCode.GOVERNMENT_CONFIDENTIAL),
                Arguments.of("government conf", SecurityLevelCode.GOVERNMENT_CONFIDENTIAL),
                Arguments.of("secret", SecurityLevelCode.GOVERNMENT_SECRET),
                Arguments.of("gov secret", SecurityLevelCode.GOVERNMENT_SECRET),
                Arguments.of("top secret", SecurityLevelCode.GOVERNMENT_TOP_SECRET),
                Arguments.of("ts", SecurityLevelCode.GOVERNMENT_TOP_SECRET),
                Arguments.of("gov ts", SecurityLevelCode.GOVERNMENT_TOP_SECRET),
                Arguments.of("gov defined", SecurityLevelCode.GOVERNMENT_DEFINED),
                Arguments.of("government specific", SecurityLevelCode.GOVERNMENT_DEFINED),
                Arguments.of("mutual", SecurityLevelCode.MUTUALLY_DEFINED),
                Arguments.of("agreed upon", SecurityLevelCode.MUTUALLY_DEFINED),
                Arguments.of("custom", SecurityLevelCode.MUTUALLY_DEFINED));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> SecurityLevelCode.fromString(input));
    }
}
