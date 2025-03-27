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
 * Enumeration representing Maintenance Type Codes used in the HD03 field
 * of the EDI 834 Health Coverage (HD) segment.
 */
@Getter
public enum MaintenanceTypeCode implements EdiCodeEnum {
    ADDITION("001", "Addition"),
    CHANGE("002", "Change"),
    CANCELLATION("003", "Cancellation"),
    REINSTATEMENT("004", "Reinstatement"),
    CHANGE_WITH_MEMBER_ID("021", "Change with Member ID Change"),
    CHANGE_ADDRESS("022", "Change Address"),
    CHANGE_DEMOGRAPHIC("023", "Change Demographic Data"),
    CHANGE_BENEFIT_COVERAGE("024", "Change Benefit/Coverage Data"),
    CHANGE_BILLING("025", "Change Billing/Payment Data"),
    CHANGE_EMPLOYMENT("026", "Change Employment Data"),
    COBRA_ELECTION("030", "COBRA Election");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<MaintenanceTypeCode> LOOKUP;

    static {
        // Include additional common terms and phrases users might search for
        LOOKUP = new EdiEnumLookup<>(
                MaintenanceTypeCode.class,
                "Maintenance Type Code",
                Map.<String, MaintenanceTypeCode>ofEntries(
                        Map.entry("1", ADDITION),
                        Map.entry("add", ADDITION),
                        Map.entry("new", ADDITION),
                        Map.entry("enroll", ADDITION),
                        Map.entry("enrollment", ADDITION),
                        Map.entry("create", ADDITION),
                        Map.entry("join", ADDITION),

                        Map.entry("2", CHANGE),
                        Map.entry("modify", CHANGE),
                        Map.entry("update", CHANGE),
                        Map.entry("edit", CHANGE),
                        Map.entry("alter", CHANGE),
                        Map.entry("amend", CHANGE),
                        Map.entry("revise", CHANGE),

                        Map.entry("3", CANCELLATION),
                        Map.entry("cancel", CANCELLATION),
                        Map.entry("terminate", CANCELLATION),
                        Map.entry("end", CANCELLATION),
                        Map.entry("delete", CANCELLATION),
                        Map.entry("remove", CANCELLATION),
                        Map.entry("stop", CANCELLATION),

                        Map.entry("4", REINSTATEMENT),
                        Map.entry("reinstate", REINSTATEMENT),
                        Map.entry("restore", REINSTATEMENT),
                        Map.entry("reactivate", REINSTATEMENT),
                        Map.entry("resume", REINSTATEMENT),

                        Map.entry("21", CHANGE_WITH_MEMBER_ID),
                        Map.entry("id change", CHANGE_WITH_MEMBER_ID),
                        Map.entry("change id", CHANGE_WITH_MEMBER_ID),
                        Map.entry("update id", CHANGE_WITH_MEMBER_ID),
                        Map.entry("new member id", CHANGE_WITH_MEMBER_ID),
                        Map.entry("member id update", CHANGE_WITH_MEMBER_ID),
                        Map.entry("subscriber id", CHANGE_WITH_MEMBER_ID),

                        Map.entry("22", CHANGE_ADDRESS),
                        Map.entry("address change", CHANGE_ADDRESS),
                        Map.entry("update address", CHANGE_ADDRESS),
                        Map.entry("new address", CHANGE_ADDRESS),
                        Map.entry("moved", CHANGE_ADDRESS),
                        Map.entry("relocation", CHANGE_ADDRESS),
                        Map.entry("address update", CHANGE_ADDRESS),

                        Map.entry("23", CHANGE_DEMOGRAPHIC),
                        Map.entry("demographic change", CHANGE_DEMOGRAPHIC),
                        Map.entry("change name", CHANGE_DEMOGRAPHIC),
                        Map.entry("update personal info", CHANGE_DEMOGRAPHIC),
                        Map.entry("personal information", CHANGE_DEMOGRAPHIC),
                        Map.entry("name change", CHANGE_DEMOGRAPHIC),
                        Map.entry("gender change", CHANGE_DEMOGRAPHIC),

                        Map.entry("24", CHANGE_BENEFIT_COVERAGE),
                        Map.entry("benefit change", CHANGE_BENEFIT_COVERAGE),
                        Map.entry("update benefits", CHANGE_BENEFIT_COVERAGE),
                        Map.entry("change coverage", CHANGE_BENEFIT_COVERAGE),
                        Map.entry("plan change", CHANGE_BENEFIT_COVERAGE),
                        Map.entry("coverage update", CHANGE_BENEFIT_COVERAGE),

                        Map.entry("25", CHANGE_BILLING),
                        Map.entry("billing change", CHANGE_BILLING),
                        Map.entry("payment update", CHANGE_BILLING),
                        Map.entry("update payment", CHANGE_BILLING),
                        Map.entry("payment method", CHANGE_BILLING),
                        Map.entry("banking info", CHANGE_BILLING),
                        Map.entry("bank account", CHANGE_BILLING),

                        Map.entry("26", CHANGE_EMPLOYMENT),
                        Map.entry("employment change", CHANGE_EMPLOYMENT),
                        Map.entry("job change", CHANGE_EMPLOYMENT),
                        Map.entry("employment update", CHANGE_EMPLOYMENT),
                        Map.entry("job status", CHANGE_EMPLOYMENT),
                        Map.entry("position change", CHANGE_EMPLOYMENT),
                        Map.entry("department change", CHANGE_EMPLOYMENT),

                        Map.entry("30", COBRA_ELECTION),
                        Map.entry("cobra", COBRA_ELECTION),
                        Map.entry("elect cobra", COBRA_ELECTION),
                        Map.entry("cobra enrollment", COBRA_ELECTION),
                        Map.entry("consolidated omnibus", COBRA_ELECTION),
                        Map.entry("continuation coverage", COBRA_ELECTION)
                )
        );
    }

    MaintenanceTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a MaintenanceTypeCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching MaintenanceTypeCode
     * @throws IllegalArgumentException if no match is found
     */
    public static MaintenanceTypeCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}