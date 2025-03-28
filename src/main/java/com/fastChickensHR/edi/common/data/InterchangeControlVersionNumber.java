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
 * Enumeration representing Interchange Control Version Numbers used in EDI transactions,
 * specifically in the ISA12 segment of the interchange control header.
 */
@Getter
public enum InterchangeControlVersionNumber implements EdiCodeEnum {
    X12_1987("00200", "ASC X12 Standards Issued by ANSI in 1987"),
    X12_DRAFT_1988("00201", "Draft Standards for Trial Use Approved by ASC X12 Through August 1988"),
    X12_DRAFT_1989("00204", "Draft Standards for Trial Use Approved by ASC X12 Through May 1989"),
    X12_1992("00300", "ASC X12 Standards Issued by ANSI in 1992"),
    X12_DRAFT_OCT_1990("00301", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board Through October 1990"),
    X12_DRAFT_OCT_1991("00302", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board Through October 1991"),
    X12_DRAFT_OCT_1992("00303", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board Through October 1992"),
    X12_DRAFT_OCT_1993("00304", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board through October 1993"),
    X12_DRAFT_OCT_1994("00305", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board through October 1994"),
    X12_DRAFT_OCT_1995("00306", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board through October 1995"),
    X12_DRAFT_OCT_1996("00307", "Draft Standards for Trial Use Approved for Publication by ASC X12 Procedures Review Board through October 1996"),
    X12_1997("00400", "ASC X12 Standards Issued by ANSI in 1997"),
    X12_OCT_1997("00401", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1997"),
    X12_OCT_1998("00402", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1998"),
    X12_OCT_1999("00403", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1999"),
    X12_OCT_2000("00404", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2000"),
    X12_OCT_2001("00405", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2001"),
    X12_OCT_2002("00406", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2002"),
    X12_2003("00500", "ASC X12 Standards Issued by ANSI in 2003"),
    X12_OCT_2003("00501", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2003");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<InterchangeControlVersionNumber> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                InterchangeControlVersionNumber.class,
                "Interchange Control Version Number",
                Map.<String, InterchangeControlVersionNumber>ofEntries(
                        Map.entry("00200", X12_1987),
                        Map.entry("1987", X12_1987),

                        Map.entry("00201", X12_DRAFT_1988),
                        Map.entry("1988", X12_DRAFT_1988),

                        Map.entry("00204", X12_DRAFT_1989),
                        Map.entry("1989", X12_DRAFT_1989),

                        Map.entry("00300", X12_1992),

                        Map.entry("00301", X12_DRAFT_OCT_1990),
                        Map.entry("1990", X12_DRAFT_OCT_1990),

                        Map.entry("00302", X12_DRAFT_OCT_1991),
                        Map.entry("1991", X12_DRAFT_OCT_1991),

                        Map.entry("00303", X12_DRAFT_OCT_1992),

                        Map.entry("00304", X12_DRAFT_OCT_1993),
                        Map.entry("1993", X12_DRAFT_OCT_1993),

                        Map.entry("00305", X12_DRAFT_OCT_1994),
                        Map.entry("1994", X12_DRAFT_OCT_1994),

                        Map.entry("00306", X12_DRAFT_OCT_1995),
                        Map.entry("1995", X12_DRAFT_OCT_1995),

                        Map.entry("00307", X12_DRAFT_OCT_1996),
                        Map.entry("1996", X12_DRAFT_OCT_1996),

                        Map.entry("00400", X12_1997),
                        Map.entry("1997", X12_1997),

                        Map.entry("00401", X12_OCT_1997),

                        Map.entry("00402", X12_OCT_1998),
                        Map.entry("1998", X12_OCT_1998),

                        Map.entry("00403", X12_OCT_1999),
                        Map.entry("1999", X12_OCT_1999),

                        Map.entry("00404", X12_OCT_2000),
                        Map.entry("2000", X12_OCT_2000),

                        Map.entry("00405", X12_OCT_2001),
                        Map.entry("2001", X12_OCT_2001),

                        Map.entry("00406", X12_OCT_2002),
                        Map.entry("2002", X12_OCT_2002),

                        Map.entry("00500", X12_2003),

                        Map.entry("00501", X12_OCT_2003)
                )
        );
    }

    InterchangeControlVersionNumber(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an InterchangeControlVersionNumber instance from any input string.
     * Matches against codes, years, and common variations.
     *
     * @param input the string to look up
     * @return the matching InterchangeControlVersionNumber
     * @throws IllegalArgumentException if no match is found
     */
    public static InterchangeControlVersionNumber fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}