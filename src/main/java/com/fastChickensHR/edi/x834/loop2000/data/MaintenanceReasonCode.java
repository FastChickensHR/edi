/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.common.data.EdiCodeEnum;
import com.fastChickensHR.edi.x834.common.data.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Maintenance Reason Codes used in the HD04 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum MaintenanceReasonCode implements EdiCodeEnum {
    ACTIVE("AC", "Active"),
    BIRTH("BH", "Birth"),
    COBRA("CO", "COBRA"),
    DISABILITY("DI", "Disability"),
    DEATH("DN", "Death"),
    RETIREMENT("ET", "Retirement"),
    MARRIAGE("MA", "Marriage"),
    STRIKE("ST", "Strike"),
    TERMINATION("TN", "Termination"),
    VOLUNTARY_WITHDRAWAL("VO", "Voluntary Withdrawal"),
    ADMINISTRATIVE_ERROR("XN", "Administrative Error");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MaintenanceReasonCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                MaintenanceReasonCode.class,
                "Maintenance Reason Code",
                Map.<String, MaintenanceReasonCode>ofEntries(
                        Map.entry("currently active", ACTIVE),
                        Map.entry("active status", ACTIVE),
                        Map.entry("current", ACTIVE),
                        Map.entry("employed", ACTIVE),

                        Map.entry("newborn", BIRTH),
                        Map.entry("new child", BIRTH),
                        Map.entry("baby", BIRTH),
                        Map.entry("child birth", BIRTH),
                        Map.entry("new dependent", BIRTH),

                        Map.entry("consolidated omnibus budget reconciliation act", COBRA),
                        Map.entry("continuation coverage", COBRA),

                        Map.entry("disabled", DISABILITY),
                        Map.entry("medical disability", DISABILITY),
                        Map.entry("unable to work", DISABILITY),
                        Map.entry("incapacitated", DISABILITY),

                        Map.entry("deceased", DEATH),
                        Map.entry("died", DEATH),
                        Map.entry("passed away", DEATH),
                        Map.entry("fatality", DEATH),

                        Map.entry("retired", RETIREMENT),
                        Map.entry("retiree", RETIREMENT),
                        Map.entry("pension", RETIREMENT),

                        Map.entry("married", MARRIAGE),
                        Map.entry("wedding", MARRIAGE),
                        Map.entry("spouse", MARRIAGE),
                        Map.entry("life event", MARRIAGE),

                        Map.entry("labor strike", STRIKE),
                        Map.entry("union strike", STRIKE),
                        Map.entry("work stoppage", STRIKE),
                        Map.entry("walkout", STRIKE),

                        Map.entry("terminated", TERMINATION),
                        Map.entry("fired", TERMINATION),
                        Map.entry("let go", TERMINATION),
                        Map.entry("layoff", TERMINATION),
                        Map.entry("laid off", TERMINATION),
                        Map.entry("employment ended", TERMINATION),

                        Map.entry("quit", VOLUNTARY_WITHDRAWAL),
                        Map.entry("resigned", VOLUNTARY_WITHDRAWAL),
                        Map.entry("voluntary termination", VOLUNTARY_WITHDRAWAL),
                        Map.entry("opted out", VOLUNTARY_WITHDRAWAL),
                        Map.entry("withdrawal", VOLUNTARY_WITHDRAWAL),

                        Map.entry("admin error", ADMINISTRATIVE_ERROR),
                        Map.entry("correction", ADMINISTRATIVE_ERROR),
                        Map.entry("mistake", ADMINISTRATIVE_ERROR),
                        Map.entry("error", ADMINISTRATIVE_ERROR),
                        Map.entry("clerical error", ADMINISTRATIVE_ERROR)
                )
        );
    }

    MaintenanceReasonCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MaintenanceReasonCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching MaintenanceReasonCode
     * @throws IllegalArgumentException if no match is found
     */
    public static MaintenanceReasonCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}