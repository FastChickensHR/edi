/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Security Information Qualifiers used in the ISA03 field
 * of the EDI 834 Interchange Control Header (ISA) segment.
 */
@Getter
public enum SecurityInformationQualifier implements EdiCodeEnum {
    NO_SECURITY_INFORMATION("00", "No Security Information Present"),
    PASSWORD("01", "Password");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<SecurityInformationQualifier> LOOKUP;

    static {
        // Include some additional common terms and abbreviations
        LOOKUP = new EdiEnumLookup<>(
                SecurityInformationQualifier.class,
                "Security Information Qualifier",
                Map.ofEntries(
                        Map.entry("none", NO_SECURITY_INFORMATION),
                        Map.entry("no security", NO_SECURITY_INFORMATION),
                        Map.entry("nosecurity", NO_SECURITY_INFORMATION),
                        Map.entry("no info", NO_SECURITY_INFORMATION),
                        Map.entry("noinfo", NO_SECURITY_INFORMATION),
                        Map.entry("empty", NO_SECURITY_INFORMATION),
                        Map.entry("blank", NO_SECURITY_INFORMATION),

                        Map.entry("pwd", PASSWORD),
                        Map.entry("pass", PASSWORD),
                        Map.entry("passcode", PASSWORD),
                        Map.entry("secret", PASSWORD),
                        Map.entry("credentials", PASSWORD),
                        Map.entry("authentication", PASSWORD)
                )
        );
    }

    SecurityInformationQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a SecurityInformationQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching SecurityInformationQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static SecurityInformationQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}