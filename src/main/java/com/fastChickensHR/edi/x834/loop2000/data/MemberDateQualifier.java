/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Date Time Qualifiers (DTP01) for Member Level Dates.
 * These qualifiers indicate the type of date being provided for a member.
 */
@Getter
public enum MemberDateQualifier implements EdiCodeEnum {
    BENEFIT_BEGIN("348", "Benefit Begin"),
    BENEFIT_END("349", "Benefit End"),
    BIRTH("007", "Birth"),
    COVERAGE_BEGIN("356", "Coverage Begin"),
    COVERAGE_END("357", "Coverage End"),
    EFFECTIVE("303", "Effective"),
    ELIGIBILITY_BEGIN("336", "Eligibility Begin"),
    ELIGIBILITY_END("337", "Eligibility End"),
    ENROLLMENT("338", "Enrollment"),
    EXPIRATION("036", "Expiration"),
    HIRE("296", "Hire"),
    MAINTENANCE_EFFECTIVE("304", "Maintenance Effective"),
    RETIREMENT("473", "Retirement"),
    SIGNATURE("405", "Signature"),
    STATUS_CHANGE("582", "Status Change"),
    TERMINATION("036", "Termination"),
    COBRA_QUALIFYING_EVENT("297", "COBRA Qualifying Event"),
    COBRA_ELECTION("295", "COBRA Election"),
    COBRA_BEGIN("276", "COBRA Begin"),
    COBRA_END("277", "COBRA End"),
    PREMIUM_PAID_TO_DATE("343", "Premium Paid to Date");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MemberDateQualifier> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                MemberDateQualifier.class,
                "Member Date Qualifier",
                Map.ofEntries(
                        Map.entry("348", BENEFIT_BEGIN),
                        Map.entry("benefit start", BENEFIT_BEGIN),
                        Map.entry("benefits begin", BENEFIT_BEGIN),
                        Map.entry("start of benefits", BENEFIT_BEGIN),
                        Map.entry("benefits start date", BENEFIT_BEGIN),

                        Map.entry("349", BENEFIT_END),
                        Map.entry("benefit stop", BENEFIT_END),
                        Map.entry("benefits terminate", BENEFIT_END),
                        Map.entry("end of benefits", BENEFIT_END),
                        Map.entry("benefits end date", BENEFIT_END),

                        Map.entry("007", BIRTH),
                        Map.entry("7", BIRTH),
                        Map.entry("dob", BIRTH),
                        Map.entry("date of birth", BIRTH),
                        Map.entry("birthdate", BIRTH),
                        Map.entry("birthday", BIRTH),
                        Map.entry("born", BIRTH),

                        Map.entry("356", COVERAGE_BEGIN),
                        Map.entry("coverage start", COVERAGE_BEGIN),
                        Map.entry("insurance begin", COVERAGE_BEGIN),
                        Map.entry("policy start", COVERAGE_BEGIN),
                        Map.entry("start of coverage", COVERAGE_BEGIN),

                        Map.entry("357", COVERAGE_END),
                        Map.entry("coverage stop", COVERAGE_END),
                        Map.entry("insurance end", COVERAGE_END),
                        Map.entry("policy end", COVERAGE_END),
                        Map.entry("end of coverage", COVERAGE_END),

                        Map.entry("303", EFFECTIVE),
                        Map.entry("effective date", EFFECTIVE),
                        Map.entry("start date", EFFECTIVE),
                        Map.entry("begins", EFFECTIVE),
                        Map.entry("active from", EFFECTIVE),

                        Map.entry("336", ELIGIBILITY_BEGIN),
                        Map.entry("eligibility start", ELIGIBILITY_BEGIN),
                        Map.entry("start of eligibility", ELIGIBILITY_BEGIN),
                        Map.entry("eligible from", ELIGIBILITY_BEGIN),
                        Map.entry("begins eligibility", ELIGIBILITY_BEGIN),

                        Map.entry("337", ELIGIBILITY_END),
                        Map.entry("eligibility stop", ELIGIBILITY_END),
                        Map.entry("end of eligibility", ELIGIBILITY_END),
                        Map.entry("eligible until", ELIGIBILITY_END),
                        Map.entry("eligibility termination", ELIGIBILITY_END),

                        Map.entry("338", ENROLLMENT),
                        Map.entry("enrolled", ENROLLMENT),
                        Map.entry("enrollment date", ENROLLMENT),
                        Map.entry("date enrolled", ENROLLMENT),
                        Map.entry("signup", ENROLLMENT),
                        Map.entry("registration", ENROLLMENT),

                        Map.entry("036", EXPIRATION), // Note this is a duplicate code with TERMINATION
                        Map.entry("expires", EXPIRATION),
                        Map.entry("expiration date", EXPIRATION),
                        Map.entry("valid until", EXPIRATION),
                        Map.entry("expire", EXPIRATION),

                        Map.entry("296", HIRE),
                        Map.entry("hire date", HIRE),
                        Map.entry("hired", HIRE),
                        Map.entry("date of hire", HIRE),
                        Map.entry("employment start", HIRE),
                        Map.entry("started work", HIRE),

                        Map.entry("304", MAINTENANCE_EFFECTIVE),
                        Map.entry("maintenance date", MAINTENANCE_EFFECTIVE),
                        Map.entry("change effective", MAINTENANCE_EFFECTIVE),
                        Map.entry("update effective", MAINTENANCE_EFFECTIVE),
                        Map.entry("modification date", MAINTENANCE_EFFECTIVE),

                        Map.entry("473", RETIREMENT),
                        Map.entry("retired", RETIREMENT),
                        Map.entry("retirement date", RETIREMENT),
                        Map.entry("date of retirement", RETIREMENT),
                        Map.entry("pension start", RETIREMENT),

                        Map.entry("405", SIGNATURE),
                        Map.entry("signed", SIGNATURE),
                        Map.entry("signature date", SIGNATURE),
                        Map.entry("date signed", SIGNATURE),
                        Map.entry("authorization", SIGNATURE),
                        Map.entry("consent", SIGNATURE),

                        Map.entry("582", STATUS_CHANGE),
                        Map.entry("status update", STATUS_CHANGE),
                        Map.entry("changed status", STATUS_CHANGE),
                        Map.entry("status modification", STATUS_CHANGE),

                        Map.entry("term", TERMINATION),
                        Map.entry("terminated", TERMINATION),
                        Map.entry("termination date", TERMINATION),
                        Map.entry("employment end", TERMINATION),
                        Map.entry("separation", TERMINATION),

                        Map.entry("297", COBRA_QUALIFYING_EVENT),
                        Map.entry("qualifying event", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra event", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra qualification", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra trigger", COBRA_QUALIFYING_EVENT),

                        Map.entry("295", COBRA_ELECTION),
                        Map.entry("elected cobra", COBRA_ELECTION),
                        Map.entry("cobra choice", COBRA_ELECTION),
                        Map.entry("cobra selection", COBRA_ELECTION),
                        Map.entry("chose cobra", COBRA_ELECTION),

                        Map.entry("276", COBRA_BEGIN),
                        Map.entry("cobra start", COBRA_BEGIN),
                        Map.entry("cobra effective", COBRA_BEGIN),
                        Map.entry("cobra coverage start", COBRA_BEGIN),
                        Map.entry("start of cobra", COBRA_BEGIN),

                        Map.entry("277", COBRA_END),
                        Map.entry("cobra stop", COBRA_END),
                        Map.entry("cobra termination", COBRA_END),
                        Map.entry("cobra coverage end", COBRA_END),
                        Map.entry("end of cobra", COBRA_END),

                        Map.entry("343", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid through", PREMIUM_PAID_TO_DATE),
                        Map.entry("premium paid", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid to", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid thru", PREMIUM_PAID_TO_DATE),
                        Map.entry("premium current through", PREMIUM_PAID_TO_DATE)
                )
        );
    }

    MemberDateQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MemberDateQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching MemberDateQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static MemberDateQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Gets a MemberDateQualifier instance specifically by its EDI code.
     * Note: Code "036" is used for both EXPIRATION and TERMINATION.
     * This method returns EXPIRATION by default for code "036".
     *
     * @param code the EDI code to look up
     * @return the matching MemberDateQualifier
     * @throws IllegalArgumentException if no matching code is found
     */
    public static MemberDateQualifier fromCode(String code) {
        for (MemberDateQualifier value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Member Date Qualifier Code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}