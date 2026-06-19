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
 * Enumeration representing Coverage Level Codes used in the HD05 field
 * of the EDI 834 Health Coverage (HD) segment in Loop 2300.
 * <p>
 * Source: HIPAA 005010X220A1 Benefit Enrollment and Maintenance implementation guide.
 */
@Getter
public enum CoverageLevelCode implements EdiCodeEnum {
    CHILDREN_ONLY("CHD", "Children Only"),
    DEPENDENTS_ONLY("DEP", "Dependents Only"),
    EMPLOYEE_AND_CHILDREN("ECH", "Employee and Children"),
    EMPLOYEE_ONLY("EMP", "Employee Only"),
    EMPLOYEE_AND_SPOUSE("ESP", "Employee and Spouse"),
    FAMILY("FAM", "Family"),
    INDIVIDUAL("IND", "Individual"),
    SPOUSE_AND_CHILDREN("SPC", "Spouse and Children"),
    SPOUSE_ONLY("SPO", "Spouse Only"),
    TWO_PARTY("TWO", "Two Party");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<CoverageLevelCode> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                CoverageLevelCode.class,
                "Coverage Level Code",
                Map.<String, CoverageLevelCode>ofEntries(
                        Map.entry("kids only", CHILDREN_ONLY),
                        Map.entry("child only", CHILDREN_ONLY),
                        Map.entry("just children", CHILDREN_ONLY),

                        Map.entry("dep only", DEPENDENTS_ONLY),
                        Map.entry("just dependents", DEPENDENTS_ONLY),

                        Map.entry("ee and children", EMPLOYEE_AND_CHILDREN),
                        Map.entry("employee + children", EMPLOYEE_AND_CHILDREN),
                        Map.entry("employee+children", EMPLOYEE_AND_CHILDREN),
                        Map.entry("parent and children", EMPLOYEE_AND_CHILDREN),

                        Map.entry("ee only", EMPLOYEE_ONLY),
                        Map.entry("just employee", EMPLOYEE_ONLY),
                        Map.entry("self only", EMPLOYEE_ONLY),
                        Map.entry("self", EMPLOYEE_ONLY),

                        Map.entry("ee and spouse", EMPLOYEE_AND_SPOUSE),
                        Map.entry("employee + spouse", EMPLOYEE_AND_SPOUSE),
                        Map.entry("employee+spouse", EMPLOYEE_AND_SPOUSE),

                        Map.entry("family coverage", FAMILY),
                        Map.entry("whole family", FAMILY),
                        Map.entry("all family", FAMILY),

                        Map.entry("ind", INDIVIDUAL),
                        Map.entry("single", INDIVIDUAL),

                        Map.entry("spouse + children", SPOUSE_AND_CHILDREN),
                        Map.entry("spouse+children", SPOUSE_AND_CHILDREN),
                        Map.entry("partner and children", SPOUSE_AND_CHILDREN),

                        Map.entry("just spouse", SPOUSE_ONLY),
                        Map.entry("partner only", SPOUSE_ONLY),

                        Map.entry("2 party", TWO_PARTY),
                        Map.entry("two-party", TWO_PARTY),
                        Map.entry("couple", TWO_PARTY)
                )
        );
    }

    CoverageLevelCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a CoverageLevelCode instance from any input string.
     *
     * @param input the string to look up
     * @return the matching CoverageLevelCode
     * @throws IllegalArgumentException if no match is found
     */
    public static CoverageLevelCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
