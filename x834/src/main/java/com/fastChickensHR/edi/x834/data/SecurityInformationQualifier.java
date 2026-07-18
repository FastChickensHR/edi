/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Code values for the Security Information Qualifier (ISA03, X12 data element I03) in the
 * Interchange Control Header (ISA) of an X12 834 interchange (005010X220A1). Qualifies the
 * type of security information carried in ISA04.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a
 * value from its code, enum name, description, or a common synonym, and throws
 * {@link IllegalArgumentException} when the input matches none.
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

    /**
     * Returns the raw X12 code value for this constant (not the enum name), so the enum
     * renders directly into an EDI element.
     */
    @Override
    public String toString() {
        return code;
    }
}