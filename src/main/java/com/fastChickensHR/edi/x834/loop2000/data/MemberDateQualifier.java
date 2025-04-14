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
import com.fastChickensHR.edi.common.data.DateTimeQualifier;
import lombok.Getter;

import java.util.*;

/**
 * Represents Member Level Date Qualifiers, which are a subset of DateTimeQualifier.
 * This enum maps the member-specific qualifiers to the appropriate DateTimeQualifier codes.
 */
@Getter
public enum MemberDateQualifier implements EdiCodeEnum {
    BENEFIT_BEGIN(DateTimeQualifier.BENEFIT_BEGIN),
    BENEFIT_END(DateTimeQualifier.BENEFIT_END),
    BIRTH(DateTimeQualifier.EFFECTIVE),
    COVERAGE_BEGIN(DateTimeQualifier.ELIGIBILITY_BEGIN),
    COVERAGE_END(DateTimeQualifier.ELIGIBILITY_END),
    EFFECTIVE(DateTimeQualifier.MAINTENANCE_EFFECTIVE),
    ELIGIBILITY_BEGIN(DateTimeQualifier.EMPLOYMENT_BEGIN),
    ELIGIBILITY_END(DateTimeQualifier.EMPLOYMENT_END),
    ENROLLMENT(DateTimeQualifier.ENROLLMENT),
    EXPIRATION(DateTimeQualifier.EXPIRATION_DATE),
    HIRE(DateTimeQualifier.EMPLOYMENT_OR_HIRE),
    MAINTENANCE_EFFECTIVE(DateTimeQualifier.MAINTENANCE_EFFECTIVE),
    RETIREMENT(DateTimeQualifier.RETIREMENT),
    SIGNATURE(DateTimeQualifier.APPLICANT_SIGNED),
    STATUS_CHANGE(DateTimeQualifier.CHANGED),
    TERMINATION(DateTimeQualifier.EXPIRATION_DATE), // Same code as EXPIRATION
    COBRA_QUALIFYING_EVENT(DateTimeQualifier.COBRA_QUALIFYING_EVENT),
    COBRA_ELECTION(DateTimeQualifier.COBRA),
    COBRA_BEGIN(DateTimeQualifier.COBRA_BEGIN),
    COBRA_END(DateTimeQualifier.COBRA_END),
    PREMIUM_PAID_TO_DATE(DateTimeQualifier.PREMIUM_PAID_TO_DATE);

    private final DateTimeQualifier dateTimeQualifier;
    private static final EdiEnumLookup<MemberDateQualifier> LOOKUP;

    MemberDateQualifier(DateTimeQualifier dateTimeQualifier) {
        this.dateTimeQualifier = dateTimeQualifier;
    }

    /**
     * Get the code value for this member date qualifier
     * @return The EDI code associated with this qualifier
     */
    public String getCode() {
        return dateTimeQualifier.getCode();
    }

    @Override
    public String toString() {
        return getCode();
    }

    /**
     * Get the description for this member date qualifier
     * @return The description associated with this qualifier
     */
    public String getDescription() {
        return dateTimeQualifier.getDescription();
    }

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                MemberDateQualifier.class,
                "Member Date Qualifier",
                Map.<String, MemberDateQualifier>ofEntries(
                        Map.entry("benefit start", BENEFIT_BEGIN),
                        Map.entry("benefits begin", BENEFIT_BEGIN),
                        Map.entry("start of benefits", BENEFIT_BEGIN),
                        Map.entry("benefits start date", BENEFIT_BEGIN),

                        Map.entry("benefit stop", BENEFIT_END),
                        Map.entry("benefits terminate", BENEFIT_END),
                        Map.entry("end of benefits", BENEFIT_END),
                        Map.entry("benefits end date", BENEFIT_END),

                        Map.entry("7", BIRTH),
                        Map.entry("dob", BIRTH),
                        Map.entry("date of birth", BIRTH),
                        Map.entry("birthdate", BIRTH),
                        Map.entry("birthday", BIRTH),
                        Map.entry("born", BIRTH),

                        Map.entry("coverage start", COVERAGE_BEGIN),
                        Map.entry("insurance begin", COVERAGE_BEGIN),
                        Map.entry("policy start", COVERAGE_BEGIN),
                        Map.entry("start of coverage", COVERAGE_BEGIN),

                        Map.entry("coverage stop", COVERAGE_END),
                        Map.entry("insurance end", COVERAGE_END),
                        Map.entry("policy end", COVERAGE_END),
                        Map.entry("end of coverage", COVERAGE_END),

                        Map.entry("effective date", EFFECTIVE),
                        Map.entry("start date", EFFECTIVE),
                        Map.entry("begins", EFFECTIVE),
                        Map.entry("active from", EFFECTIVE),

                        Map.entry("eligibility start", ELIGIBILITY_BEGIN),
                        Map.entry("start of eligibility", ELIGIBILITY_BEGIN),
                        Map.entry("eligible from", ELIGIBILITY_BEGIN),
                        Map.entry("begins eligibility", ELIGIBILITY_BEGIN),

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

                        Map.entry("expires", EXPIRATION),
                        Map.entry("expiration date", EXPIRATION),
                        Map.entry("valid until", EXPIRATION),
                        Map.entry("expire", EXPIRATION),

                        Map.entry("hire date", HIRE),
                        Map.entry("hired", HIRE),
                        Map.entry("date of hire", HIRE),
                        Map.entry("employment start", HIRE),
                        Map.entry("started work", HIRE),

                        Map.entry("maintenance date", MAINTENANCE_EFFECTIVE),
                        Map.entry("change effective", MAINTENANCE_EFFECTIVE),
                        Map.entry("update effective", MAINTENANCE_EFFECTIVE),
                        Map.entry("modification date", MAINTENANCE_EFFECTIVE),

                        Map.entry("retired", RETIREMENT),
                        Map.entry("retirement date", RETIREMENT),
                        Map.entry("date of retirement", RETIREMENT),
                        Map.entry("pension start", RETIREMENT),

                        Map.entry("signed", SIGNATURE),
                        Map.entry("signature date", SIGNATURE),
                        Map.entry("date signed", SIGNATURE),
                        Map.entry("authorization", SIGNATURE),
                        Map.entry("consent", SIGNATURE),

                        Map.entry("status update", STATUS_CHANGE),
                        Map.entry("changed status", STATUS_CHANGE),
                        Map.entry("status modification", STATUS_CHANGE),

                        Map.entry("term", TERMINATION),
                        Map.entry("terminated", TERMINATION),
                        Map.entry("termination date", TERMINATION),
                        Map.entry("employment end", TERMINATION),
                        Map.entry("separation", TERMINATION),

                        Map.entry("qualifying event", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra event", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra qualification", COBRA_QUALIFYING_EVENT),
                        Map.entry("cobra trigger", COBRA_QUALIFYING_EVENT),

                        Map.entry("elected cobra", COBRA_ELECTION),
                        Map.entry("cobra choice", COBRA_ELECTION),
                        Map.entry("cobra selection", COBRA_ELECTION),
                        Map.entry("chose cobra", COBRA_ELECTION),

                        Map.entry("cobra start", COBRA_BEGIN),
                        Map.entry("cobra effective", COBRA_BEGIN),
                        Map.entry("cobra coverage start", COBRA_BEGIN),
                        Map.entry("start of cobra", COBRA_BEGIN),

                        Map.entry("cobra stop", COBRA_END),
                        Map.entry("cobra termination", COBRA_END),
                        Map.entry("cobra coverage end", COBRA_END),
                        Map.entry("end of cobra", COBRA_END),

                        Map.entry("paid through", PREMIUM_PAID_TO_DATE),
                        Map.entry("premium paid", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid to", PREMIUM_PAID_TO_DATE),
                        Map.entry("paid thru", PREMIUM_PAID_TO_DATE),
                        Map.entry("premium current through", PREMIUM_PAID_TO_DATE)
                )
        );
    }

}