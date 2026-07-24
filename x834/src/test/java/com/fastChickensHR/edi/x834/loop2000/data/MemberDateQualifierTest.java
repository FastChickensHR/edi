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

/**
 * Exercises {@link MemberDateQualifier#fromString(String)} — the public entry point this wave added,
 * since the enum previously carried a fully-populated {@code LOOKUP} table that nothing could reach.
 *
 * <p>Unlike a plain code enum, {@code MemberDateQualifier} is a projection onto
 * {@link com.fastChickensHR.edi.x834.data.DateTimeQualifier}, and several members deliberately share
 * one underlying code/description (e.g. {@code EXPIRATION} and {@code TERMINATION} both map to
 * {@code EXPIRATION_DATE}). Code- and description-based round-trips are therefore ambiguous by design,
 * so the exhaustive round-trip here is by <em>enum name</em> (always unique); the code/description
 * ambiguity is documented on {@code fromString} rather than asserted.
 */
class MemberDateQualifierTest {

    /** Every member qualifier resolves from its own (unique) enum name. */
    @ParameterizedTest
    @EnumSource(MemberDateQualifier.class)
    void resolvesFromEnumName(MemberDateQualifier constant) {
        assertEquals(constant, MemberDateQualifier.fromString(constant.name()));
    }

    /** The member-level synonyms callers actually type resolve to the right qualifier. */
    @ParameterizedTest
    @MethodSource("aliases")
    void resolvesFromCommonAliases(String input, MemberDateQualifier expected) {
        assertEquals(expected, MemberDateQualifier.fromString(input));
    }

    private static Stream<Arguments> aliases() {
        return Stream.of(
                Arguments.of("benefit start", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("benefits begin", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("start of benefits", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("benefits start date", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("benefit stop", MemberDateQualifier.BENEFIT_END),
                Arguments.of("benefits terminate", MemberDateQualifier.BENEFIT_END),
                Arguments.of("end of benefits", MemberDateQualifier.BENEFIT_END),
                Arguments.of("benefits end date", MemberDateQualifier.BENEFIT_END),
                Arguments.of("7", MemberDateQualifier.BIRTH),
                Arguments.of("dob", MemberDateQualifier.BIRTH),
                Arguments.of("date of birth", MemberDateQualifier.BIRTH),
                Arguments.of("birthdate", MemberDateQualifier.BIRTH),
                Arguments.of("birthday", MemberDateQualifier.BIRTH),
                Arguments.of("born", MemberDateQualifier.BIRTH),
                Arguments.of("coverage start", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("insurance begin", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("policy start", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("start of coverage", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("coverage stop", MemberDateQualifier.COVERAGE_END),
                Arguments.of("insurance end", MemberDateQualifier.COVERAGE_END),
                Arguments.of("policy end", MemberDateQualifier.COVERAGE_END),
                Arguments.of("end of coverage", MemberDateQualifier.COVERAGE_END),
                Arguments.of("effective date", MemberDateQualifier.EFFECTIVE),
                Arguments.of("start date", MemberDateQualifier.EFFECTIVE),
                Arguments.of("begins", MemberDateQualifier.EFFECTIVE),
                Arguments.of("active from", MemberDateQualifier.EFFECTIVE),
                Arguments.of("eligibility start", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("start of eligibility", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("eligible from", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("begins eligibility", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("eligibility stop", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("end of eligibility", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("eligible until", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("eligibility termination", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("300", MemberDateQualifier.ENROLLMENT),
                Arguments.of("enrolled", MemberDateQualifier.ENROLLMENT),
                Arguments.of("enrollment date", MemberDateQualifier.ENROLLMENT),
                Arguments.of("date enrolled", MemberDateQualifier.ENROLLMENT),
                Arguments.of("signup", MemberDateQualifier.ENROLLMENT),
                Arguments.of("registration", MemberDateQualifier.ENROLLMENT),
                Arguments.of("expires", MemberDateQualifier.EXPIRATION),
                Arguments.of("expiration date", MemberDateQualifier.EXPIRATION),
                Arguments.of("valid until", MemberDateQualifier.EXPIRATION),
                Arguments.of("expire", MemberDateQualifier.EXPIRATION),
                Arguments.of("hire date", MemberDateQualifier.HIRE),
                Arguments.of("hired", MemberDateQualifier.HIRE),
                Arguments.of("date of hire", MemberDateQualifier.HIRE),
                Arguments.of("employment start", MemberDateQualifier.HIRE),
                Arguments.of("started work", MemberDateQualifier.HIRE),
                Arguments.of("maintenance date", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("change effective", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("update effective", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("modification date", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("retired", MemberDateQualifier.RETIREMENT),
                Arguments.of("retirement date", MemberDateQualifier.RETIREMENT),
                Arguments.of("date of retirement", MemberDateQualifier.RETIREMENT),
                Arguments.of("pension start", MemberDateQualifier.RETIREMENT),
                Arguments.of("signed", MemberDateQualifier.SIGNATURE),
                Arguments.of("signature date", MemberDateQualifier.SIGNATURE),
                Arguments.of("date signed", MemberDateQualifier.SIGNATURE),
                Arguments.of("authorization", MemberDateQualifier.SIGNATURE),
                Arguments.of("consent", MemberDateQualifier.SIGNATURE),
                Arguments.of("status update", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("changed status", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("status modification", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("term", MemberDateQualifier.TERMINATION),
                Arguments.of("terminated", MemberDateQualifier.TERMINATION),
                Arguments.of("termination date", MemberDateQualifier.TERMINATION),
                Arguments.of("employment end", MemberDateQualifier.TERMINATION),
                Arguments.of("separation", MemberDateQualifier.TERMINATION),
                Arguments.of("qualifying event", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("cobra event", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("cobra qualification", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("cobra trigger", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("elected cobra", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("cobra choice", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("cobra selection", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("chose cobra", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("cobra start", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("cobra effective", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("cobra coverage start", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("start of cobra", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("cobra stop", MemberDateQualifier.COBRA_END),
                Arguments.of("cobra termination", MemberDateQualifier.COBRA_END),
                Arguments.of("cobra coverage end", MemberDateQualifier.COBRA_END),
                Arguments.of("end of cobra", MemberDateQualifier.COBRA_END),
                Arguments.of("paid through", MemberDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("premium paid", MemberDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("paid to", MemberDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("paid thru", MemberDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("premium current through", MemberDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    /** Null, blank, and unrecognized input are rejected, not silently defaulted. */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-a-real-value"})
    void rejectsNullEmptyAndUnknown(String input) {
        assertThrows(IllegalArgumentException.class, () -> MemberDateQualifier.fromString(input));
    }
}
