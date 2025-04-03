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
 * Enumeration representing Version Codes used in EDI transactions,
 * specifically in the GS08 segment element of the Functional Group Header.
 */
@Getter
public enum VersionCode implements EdiCodeEnum {
    VERSION_001000("001000", "ASC X12 Standards Approved by ANSI in 1983"),
    VERSION_002000("002000", "ASC X12 Standards Approved by ANSI in 1986"),
    VERSION_002001("002001", "Draft Standards Approved by ASC X12 in November 1987"),
    VERSION_002002("002002", "Draft Standards Approved by ASC X12 through February 1988"),
    VERSION_002003("002003", "Draft Standards Approved by ASC X12 through August 1988"),
    VERSION_002031("002031", "Draft Standards Approved by ASC X12 through February 1989"),
    VERSION_002040("002040", "Draft Standards Approved by ASC X12 through May 1989"),
    VERSION_002041("002041", "Draft Standards Approved by ASC X12 through October 1989"),
    VERSION_002042("002042", "Draft Standards Approved by ASC X12 through February 1990"),
    VERSION_003000("003000", "ASC X12 Standards Approved by ANSI in 1992"),
    VERSION_003010("003010", "Draft Standards Approved by ASC X12 through June 1990"),
    VERSION_003011("003011", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1991"),
    VERSION_003012("003012", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1991"),
    VERSION_003020("003020", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1991"),
    VERSION_003021("003021", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1992"),
    VERSION_003022("003022", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1992"),
    VERSION_003030("003030", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1992"),
    VERSION_003031("003031", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1993"),
    VERSION_003032("003032", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1993"),
    VERSION_003040("003040", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1993"),
    VERSION_003041("003041", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1994"),
    VERSION_003042("003042", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1994"),
    VERSION_003050("003050", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1994"),
    VERSION_003051("003051", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1995"),
    VERSION_003052("003052", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1995"),
    VERSION_003060("003060", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1995"),
    VERSION_003061("003061", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1996"),
    VERSION_003062("003062", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1996"),
    VERSION_003070("003070", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through October 1996"),
    VERSION_003071("003071", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through February 1997"),
    VERSION_003072("003072", "Draft Standards Approved for Publication by ASC X12 Procedures Review Board through June 1997"),
    VERSION_004000("004000", "ASC X12 Standards Approved by ANSI in 1997"),
    VERSION_004010("004010", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1997"),
    VERSION_004011("004011", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 1998"),
    VERSION_004012("004012", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 1998"),
    VERSION_004020("004020", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1998"),
    VERSION_004021("004021", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 1999"),
    VERSION_004022("004022", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 1999"),
    VERSION_004030("004030", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 1999"),
    VERSION_004031("004031", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 2000"),
    VERSION_004032("004032", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 2000"),
    VERSION_004040("004040", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2000"),
    VERSION_004041("004041", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 2001"),
    VERSION_004042("004042", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 2001"),
    VERSION_004050("004050", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2001"),
    VERSION_004051("004051", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 2002"),
    VERSION_004052("004052", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 2002"),
    VERSION_004060("004060", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2002"),
    VERSION_004061("004061", "Standards Approved for Publication by ASC X12 Procedures Review Board through February 2003"),
    VERSION_004062("004062", "Standards Approved for Publication by ASC X12 Procedures Review Board through June 2003"),
    VERSION_005000("005000", "ASC X12 Standards Approved by ANSI in 2003"),
    VERSION_005010("005010", "Standards Approved for Publication by ASC X12 Procedures Review Board through October 2003"),
    VERSION_005010X220A1("005010X220A1", "Standards Approved for Publication by ASC X12");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<VersionCode> LOOKUP;

    static {
        // Creating an empty lookup map as requested
        LOOKUP = new EdiEnumLookup<>(
                VersionCode.class,
                "Version Code",
                Map.of()  // Empty map, no additional lookups
        );
    }

    VersionCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a VersionCode instance from any input string.
     * Matches against codes, names, and enum names.
     *
     * @param input the string to look up
     * @return the matching VersionCode
     * @throws IllegalArgumentException if no match is found
     */
    public static VersionCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}