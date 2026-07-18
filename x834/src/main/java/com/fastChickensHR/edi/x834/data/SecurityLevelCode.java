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
 * Code values for the X12 Security Level Code (data element 1204), a shared ASC X12 code
 * set describing the sensitivity or confidentiality classification of information. Exposed
 * by the X12 834 (005010X220A1) library as a reusable code list.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a
 * value from its code, enum name, description, or a common synonym, and throws
 * {@link IllegalArgumentException} when the input matches none.
 */
@Getter
public enum SecurityLevelCode implements EdiCodeEnum {
    COMPANY_NON_CLASSIFIED("00", "Company Non-Classified"),
    COMPANY_INTERNAL("01", "Company Internal Use Only"),
    COMPANY_CONFIDENTIAL("02", "Company Confidential"),
    COMPANY_CONFIDENTIAL_RESTRICTED("03", "Company Confidential, Restricted (Need to Know)"),
    COMPANY_REGISTERED("04", "Company Registered (Signature Required)"),
    PERSONAL("05", "Personal"),
    SUPPLIER_PROPRIETARY("06", "Supplier Proprietary"),
    COMPANY_DEFINED("09", "Company Defined (Trading Partner Level)"),
    COMPETITION_SENSITIVE("11", "Competition Sensitive"),
    COURT_RESTRICTED("20", "Court Restricted"),
    JUVENILE_RECORD_RESTRICTED("21", "Juvenile Record Restricted"),
    GOVERNMENT_NON_CLASSIFIED("90", "Government Non-Classified"),
    GOVERNMENT_CONFIDENTIAL("92", "Government Confidential"),
    GOVERNMENT_SECRET("93", "Government Secret"),
    GOVERNMENT_TOP_SECRET("94", "Government Top Secret"),
    GOVERNMENT_DEFINED("99", "Government Defined (Trading Partner Level)"),
    MUTUALLY_DEFINED("ZZ", "Mutually Defined");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<SecurityLevelCode> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                SecurityLevelCode.class,
                "Security Level Code",
                Map.ofEntries(
                        Map.entry("non-classified", COMPANY_NON_CLASSIFIED),
                        Map.entry("unclassified", COMPANY_NON_CLASSIFIED),
                        Map.entry("company unclassified", COMPANY_NON_CLASSIFIED),
                        Map.entry("public", COMPANY_NON_CLASSIFIED),

                        Map.entry("internal", COMPANY_INTERNAL),
                        Map.entry("internal use", COMPANY_INTERNAL),
                        Map.entry("internal only", COMPANY_INTERNAL),
                        Map.entry("company internal", COMPANY_INTERNAL),

                        Map.entry("confidential", COMPANY_CONFIDENTIAL),
                        Map.entry("company conf", COMPANY_CONFIDENTIAL),

                        Map.entry("restricted", COMPANY_CONFIDENTIAL_RESTRICTED),
                        Map.entry("need to know", COMPANY_CONFIDENTIAL_RESTRICTED),
                        Map.entry("limited access", COMPANY_CONFIDENTIAL_RESTRICTED),
                        Map.entry("restricted access", COMPANY_CONFIDENTIAL_RESTRICTED),

                        Map.entry("registered", COMPANY_REGISTERED),
                        Map.entry("signature required", COMPANY_REGISTERED),
                        Map.entry("signed", COMPANY_REGISTERED),

                        Map.entry("personal information", PERSONAL),
                        Map.entry("private", PERSONAL),

                        Map.entry("proprietary", SUPPLIER_PROPRIETARY),
                        Map.entry("supplier", SUPPLIER_PROPRIETARY),
                        Map.entry("vendor proprietary", SUPPLIER_PROPRIETARY),

                        Map.entry("trading partner", COMPANY_DEFINED),
                        Map.entry("company specific", COMPANY_DEFINED),

                        Map.entry("competitive", COMPETITION_SENSITIVE),
                        Map.entry("competitor sensitive", COMPETITION_SENSITIVE),
                        Map.entry("competitive information", COMPETITION_SENSITIVE),

                        Map.entry("court", COURT_RESTRICTED),
                        Map.entry("legal restriction", COURT_RESTRICTED),

                        Map.entry("juvenile", JUVENILE_RECORD_RESTRICTED),
                        Map.entry("minor record", JUVENILE_RECORD_RESTRICTED),
                        Map.entry("juvenile record", JUVENILE_RECORD_RESTRICTED),

                        Map.entry("gov unclassified", GOVERNMENT_NON_CLASSIFIED),
                        Map.entry("government public", GOVERNMENT_NON_CLASSIFIED),

                        Map.entry("gov confidential", GOVERNMENT_CONFIDENTIAL),
                        Map.entry("government conf", GOVERNMENT_CONFIDENTIAL),

                        Map.entry("secret", GOVERNMENT_SECRET),
                        Map.entry("gov secret", GOVERNMENT_SECRET),

                        Map.entry("top secret", GOVERNMENT_TOP_SECRET),
                        Map.entry("ts", GOVERNMENT_TOP_SECRET),
                        Map.entry("gov ts", GOVERNMENT_TOP_SECRET),

                        Map.entry("gov defined", GOVERNMENT_DEFINED),
                        Map.entry("government specific", GOVERNMENT_DEFINED),

                        Map.entry("mutual", MUTUALLY_DEFINED),
                        Map.entry("agreed upon", MUTUALLY_DEFINED),
                        Map.entry("custom", MUTUALLY_DEFINED)
                )
        );
    }

    SecurityLevelCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a SecurityLevelCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching SecurityLevelCode
     * @throws IllegalArgumentException if no match is found
     */
    public static SecurityLevelCode fromString(String input) {
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