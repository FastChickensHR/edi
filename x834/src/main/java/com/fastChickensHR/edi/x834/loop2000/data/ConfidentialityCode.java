/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Confidentiality Codes (X12 element 1165) emitted at the
 * INS13 field of the EDI 834 transaction.
 *
 * <p>Element 1165 defines exactly three codes: {@code O}, {@code R}, {@code U}. The
 * former list carried six invented codes ({@code C}, {@code V}, {@code N}, {@code L},
 * {@code M}, {@code H}) and was missing {@code O}; both have been corrected.
 */
@Getter
public enum ConfidentialityCode implements EdiCodeEnum {
    OTHER_RESTRICTIONS("O", "Other Restrictions"),
    RESTRICTED("R", "Restricted Access"),
    UNRESTRICTED("U", "Unrestricted Access");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<ConfidentialityCode> LOOKUP;

    static {
        // Colloquial aliases; codes, enum names and descriptions are matched automatically.
        LOOKUP = new EdiEnumLookup<>(
                ConfidentialityCode.class,
                "Confidentiality Code",
                Map.ofEntries(
                        Map.entry("open", UNRESTRICTED),
                        Map.entry("unrestricted", UNRESTRICTED),
                        Map.entry("restricted", RESTRICTED),
                        Map.entry("other", OTHER_RESTRICTIONS)
                )
        );
    }

    ConfidentialityCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a ConfidentialityCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching ConfidentialityCode
     * @throws IllegalArgumentException if no match is found
     */
    public static ConfidentialityCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}
