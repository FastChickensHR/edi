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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MemberDateQualifierTest {

    @Test
    void testEnumValues() {
        assertEquals(21, MemberDateQualifier.values().length,
                "MemberDateQualifier should have 21 enum values");

        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BENEFIT_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BENEFIT_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.BIRTH));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COVERAGE_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COVERAGE_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.EFFECTIVE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ELIGIBILITY_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ELIGIBILITY_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.ENROLLMENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.EXPIRATION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.HIRE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.MAINTENANCE_EFFECTIVE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.RETIREMENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.SIGNATURE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.STATUS_CHANGE));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.TERMINATION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_QUALIFYING_EVENT));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_ELECTION));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_BEGIN));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.COBRA_END));
        assertTrue(Arrays.asList(MemberDateQualifier.values()).contains(MemberDateQualifier.PREMIUM_PAID_TO_DATE));
    }

    @Test
    void testEnumProperties() {
        assertEquals("348", MemberDateQualifier.BENEFIT_BEGIN.getCode());
        assertEquals("Benefit Begin", MemberDateQualifier.BENEFIT_BEGIN.getDescription());

        assertEquals("349", MemberDateQualifier.BENEFIT_END.getCode());
        assertEquals("Benefit End", MemberDateQualifier.BENEFIT_END.getDescription());

        assertEquals("007", MemberDateQualifier.BIRTH.getCode());
        assertEquals("Birth", MemberDateQualifier.BIRTH.getDescription());

        assertEquals("356", MemberDateQualifier.COVERAGE_BEGIN.getCode());
        assertEquals("Coverage Begin", MemberDateQualifier.COVERAGE_BEGIN.getDescription());

        assertEquals("357", MemberDateQualifier.COVERAGE_END.getCode());
        assertEquals("Coverage End", MemberDateQualifier.COVERAGE_END.getDescription());

        assertEquals("303", MemberDateQualifier.EFFECTIVE.getCode());
        assertEquals("Effective", MemberDateQualifier.EFFECTIVE.getDescription());

        assertEquals("336", MemberDateQualifier.ELIGIBILITY_BEGIN.getCode());
        assertEquals("Eligibility Begin", MemberDateQualifier.ELIGIBILITY_BEGIN.getDescription());

        assertEquals("337", MemberDateQualifier.ELIGIBILITY_END.getCode());
        assertEquals("Eligibility End", MemberDateQualifier.ELIGIBILITY_END.getDescription());

        assertEquals("338", MemberDateQualifier.ENROLLMENT.getCode());
        assertEquals("Enrollment", MemberDateQualifier.ENROLLMENT.getDescription());

        assertEquals("036", MemberDateQualifier.EXPIRATION.getCode());
        assertEquals("Expiration", MemberDateQualifier.EXPIRATION.getDescription());

        assertEquals("296", MemberDateQualifier.HIRE.getCode());
        assertEquals("Hire", MemberDateQualifier.HIRE.getDescription());

        assertEquals("304", MemberDateQualifier.MAINTENANCE_EFFECTIVE.getCode());
        assertEquals("Maintenance Effective", MemberDateQualifier.MAINTENANCE_EFFECTIVE.getDescription());

        assertEquals("473", MemberDateQualifier.RETIREMENT.getCode());
        assertEquals("Retirement", MemberDateQualifier.RETIREMENT.getDescription());

        assertEquals("405", MemberDateQualifier.SIGNATURE.getCode());
        assertEquals("Signature", MemberDateQualifier.SIGNATURE.getDescription());

        assertEquals("582", MemberDateQualifier.STATUS_CHANGE.getCode());
        assertEquals("Status Change", MemberDateQualifier.STATUS_CHANGE.getDescription());

        assertEquals("036", MemberDateQualifier.TERMINATION.getCode());
        assertEquals("Termination", MemberDateQualifier.TERMINATION.getDescription());

        assertEquals("297", MemberDateQualifier.COBRA_QUALIFYING_EVENT.getCode());
        assertEquals("COBRA Qualifying Event", MemberDateQualifier.COBRA_QUALIFYING_EVENT.getDescription());

        assertEquals("295", MemberDateQualifier.COBRA_ELECTION.getCode());
        assertEquals("COBRA Election", MemberDateQualifier.COBRA_ELECTION.getDescription());

        assertEquals("276", MemberDateQualifier.COBRA_BEGIN.getCode());
        assertEquals("COBRA Begin", MemberDateQualifier.COBRA_BEGIN.getDescription());

        assertEquals("277", MemberDateQualifier.COBRA_END.getCode());
        assertEquals("COBRA End", MemberDateQualifier.COBRA_END.getDescription());

        assertEquals("343", MemberDateQualifier.PREMIUM_PAID_TO_DATE.getCode());
        assertEquals("Premium Paid to Date", MemberDateQualifier.PREMIUM_PAID_TO_DATE.getDescription());
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, MemberDateQualifier expected) throws Exception {
        Field lookupField = MemberDateQualifier.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<MemberDateQualifier> lookup = (EdiEnumLookup<MemberDateQualifier>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input),
                "Lookup for '" + input + "' should return " + expected);
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("348", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("349", MemberDateQualifier.BENEFIT_END),
                Arguments.of("007", MemberDateQualifier.BIRTH),
                Arguments.of("356", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("357", MemberDateQualifier.COVERAGE_END),
                Arguments.of("303", MemberDateQualifier.EFFECTIVE),
                Arguments.of("336", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("337", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("338", MemberDateQualifier.ENROLLMENT),
                Arguments.of("036", MemberDateQualifier.EXPIRATION), // Note: Also used for TERMINATION
                Arguments.of("296", MemberDateQualifier.HIRE),
                Arguments.of("304", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("473", MemberDateQualifier.RETIREMENT),
                Arguments.of("405", MemberDateQualifier.SIGNATURE),
                Arguments.of("582", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("297", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("295", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("276", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("277", MemberDateQualifier.COBRA_END),
                Arguments.of("343", MemberDateQualifier.PREMIUM_PAID_TO_DATE),
                Arguments.of("7", MemberDateQualifier.BIRTH),
                Arguments.of("BENEFIT_BEGIN", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("BENEFIT_END", MemberDateQualifier.BENEFIT_END),
                Arguments.of("BIRTH", MemberDateQualifier.BIRTH),
                Arguments.of("COVERAGE_BEGIN", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("COVERAGE_END", MemberDateQualifier.COVERAGE_END),
                Arguments.of("EFFECTIVE", MemberDateQualifier.EFFECTIVE),
                Arguments.of("ELIGIBILITY_BEGIN", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("ELIGIBILITY_END", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("ENROLLMENT", MemberDateQualifier.ENROLLMENT),
                Arguments.of("EXPIRATION", MemberDateQualifier.EXPIRATION),
                Arguments.of("HIRE", MemberDateQualifier.HIRE),
                Arguments.of("MAINTENANCE_EFFECTIVE", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("RETIREMENT", MemberDateQualifier.RETIREMENT),
                Arguments.of("SIGNATURE", MemberDateQualifier.SIGNATURE),
                Arguments.of("STATUS_CHANGE", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("TERMINATION", MemberDateQualifier.TERMINATION),
                Arguments.of("COBRA_QUALIFYING_EVENT", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("COBRA_ELECTION", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("COBRA_BEGIN", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("COBRA_END", MemberDateQualifier.COBRA_END),
                Arguments.of("PREMIUM_PAID_TO_DATE", MemberDateQualifier.PREMIUM_PAID_TO_DATE),

                Arguments.of("Benefit Begin", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("Benefit End", MemberDateQualifier.BENEFIT_END),
                Arguments.of("Birth", MemberDateQualifier.BIRTH),
                Arguments.of("Coverage Begin", MemberDateQualifier.COVERAGE_BEGIN),
                Arguments.of("Coverage End", MemberDateQualifier.COVERAGE_END),
                Arguments.of("Effective", MemberDateQualifier.EFFECTIVE),
                Arguments.of("Eligibility Begin", MemberDateQualifier.ELIGIBILITY_BEGIN),
                Arguments.of("Eligibility End", MemberDateQualifier.ELIGIBILITY_END),
                Arguments.of("Enrollment", MemberDateQualifier.ENROLLMENT),
                Arguments.of("Expiration", MemberDateQualifier.EXPIRATION),
                Arguments.of("Hire", MemberDateQualifier.HIRE),
                Arguments.of("Maintenance Effective", MemberDateQualifier.MAINTENANCE_EFFECTIVE),
                Arguments.of("Retirement", MemberDateQualifier.RETIREMENT),
                Arguments.of("Signature", MemberDateQualifier.SIGNATURE),
                Arguments.of("Status Change", MemberDateQualifier.STATUS_CHANGE),
                Arguments.of("Termination", MemberDateQualifier.TERMINATION),
                Arguments.of("COBRA Qualifying Event", MemberDateQualifier.COBRA_QUALIFYING_EVENT),
                Arguments.of("COBRA Election", MemberDateQualifier.COBRA_ELECTION),
                Arguments.of("COBRA Begin", MemberDateQualifier.COBRA_BEGIN),
                Arguments.of("COBRA End", MemberDateQualifier.COBRA_END),
                Arguments.of("Premium Paid to Date", MemberDateQualifier.PREMIUM_PAID_TO_DATE),

                Arguments.of("benefit start", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("benefits begin", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("start of benefits", MemberDateQualifier.BENEFIT_BEGIN),
                Arguments.of("benefits start date", MemberDateQualifier.BENEFIT_BEGIN),

                Arguments.of("benefit stop", MemberDateQualifier.BENEFIT_END),
                Arguments.of("benefits terminate", MemberDateQualifier.BENEFIT_END),
                Arguments.of("end of benefits", MemberDateQualifier.BENEFIT_END),
                Arguments.of("benefits end date", MemberDateQualifier.BENEFIT_END),

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
                Arguments.of("premium current through", MemberDateQualifier.PREMIUM_PAID_TO_DATE)
        );
    }
}