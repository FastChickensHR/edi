/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import lombok.Getter;

/**
 * Date Time Qualifiers (DTP01) for Member Level Dates.
 * These qualifiers indicate the type of date being provided for a member.
 */
@Getter
public enum MemberDateQualifier {
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

    MemberDateQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MemberDateQualifier instance from its code value.
     *
     * @param code the code to look up
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