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
 * Enumeration representing Time Codes used in EDI transactions.
 * These codes specify time zones and time offsets.
 */
@Getter
public enum TimeCode implements EdiCodeEnum {
    ISO_P01("01", "Equivalent to ISO P01"),
    ISO_P02("02", "Equivalent to ISO P02"),
    ISO_P03("03", "Equivalent to ISO P03"),
    ISO_P04("04", "Equivalent to ISO P04"),
    ISO_P05("05", "Equivalent to ISO P05"),
    ISO_P06("06", "Equivalent to ISO P06"),
    ISO_P07("07", "Equivalent to ISO P07"),
    ISO_P08("08", "Equivalent to ISO P08"),
    ISO_P09("09", "Equivalent to ISO P09"),
    ISO_P10("10", "Equivalent to ISO P10"),
    ISO_P11("11", "Equivalent to ISO P11"),
    ISO_P12("12", "Equivalent to ISO P12"),
    ISO_M12("13", "Equivalent to ISO M12"),
    ISO_M11("14", "Equivalent to ISO M11"),
    ISO_M10("15", "Equivalent to ISO M10"),
    ISO_M09("16", "Equivalent to ISO M09"),
    ISO_M08("17", "Equivalent to ISO M08"),
    ISO_M07("18", "Equivalent to ISO M07"),
    ISO_M06("19", "Equivalent to ISO M06"),
    ISO_M05("20", "Equivalent to ISO M05"),
    ISO_M04("21", "Equivalent to ISO M04"),
    ISO_M03("22", "Equivalent to ISO M03"),
    ISO_M02("23", "Equivalent to ISO M02"),
    ISO_M01("24", "Equivalent to ISO M01"),
    ISO_M2_30("25", "Equivalent to ISO M2:30"),
    ISO_M3_30("26", "Equivalent to ISO M3:30"),
    ISO_P5_30("27", "Equivalent to ISO P5:30"),
    ISO_P9_30("28", "Equivalent to ISO P9:30"),
    ISO_P10_30("29", "Equivalent to ISO P10:30"),
    ALASKA_DAYLIGHT("AD", "Alaska Daylight Time"),
    ALASKA_STANDARD("AS", "Alaska Standard Time"),
    ALASKA_TIME("AT", "Alaska Time"),
    CENTRAL_DAYLIGHT("CD", "Central Daylight Time"),
    CENTRAL_STANDARD("CS", "Central Standard Time"),
    CENTRAL_TIME("CT", "Central Time"),
    EASTERN_DAYLIGHT("ED", "Eastern Daylight Time"),
    EASTERN_STANDARD("ES", "Eastern Standard Time"),
    EASTERN_TIME("ET", "Eastern Time"),
    GREENWICH_MEAN("GM", "Greenwich Mean Time"),
    HAWAII_ALEUTIAN_DAYLIGHT("HD", "Hawaii-Aleutian Daylight Time"),
    HAWAII_ALEUTIAN_STANDARD("HS", "Hawaii-Aleutian Standard Time"),
    HAWAII_ALEUTIAN_TIME("HT", "Hawaii-Aleutian Time"),
    LOCAL_TIME("LT", "Local Time"),
    MOUNTAIN_DAYLIGHT("MD", "Mountain Daylight Time"),
    MOUNTAIN_STANDARD("MS", "Mountain Standard Time"),
    MOUNTAIN_TIME("MT", "Mountain Time"),
    NEWFOUNDLAND_DAYLIGHT("ND", "Newfoundland Daylight Time"),
    NEWFOUNDLAND_STANDARD("NS", "Newfoundland Standard Time"),
    NEWFOUNDLAND_TIME("NT", "Newfoundland Time"),
    PACIFIC_DAYLIGHT("PD", "Pacific Daylight Time"),
    PACIFIC_STANDARD("PS", "Pacific Standard Time"),
    PACIFIC_TIME("PT", "Pacific Time"),
    ATLANTIC_DAYLIGHT("TD", "Atlantic Daylight Time"),
    ATLANTIC_STANDARD("TS", "Atlantic Standard Time"),
    ATLANTIC_TIME("TT", "Atlantic Time"),
    UNIVERSAL_TIME("UT", "Universal Time Coordinate");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<TimeCode> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                TimeCode.class,
                "Time Code",
                Map.ofEntries(
                        Map.entry("alaska daylight", ALASKA_DAYLIGHT),
                        Map.entry("alaska standard", ALASKA_STANDARD),
                        Map.entry("alaska", ALASKA_TIME),
                        Map.entry("central daylight", CENTRAL_DAYLIGHT),
                        Map.entry("central standard", CENTRAL_STANDARD),
                        Map.entry("central", CENTRAL_TIME),
                        Map.entry("eastern daylight", EASTERN_DAYLIGHT),
                        Map.entry("eastern standard", EASTERN_STANDARD),
                        Map.entry("eastern", EASTERN_TIME),
                        Map.entry("greenwich", GREENWICH_MEAN),
                        Map.entry("gmt", GREENWICH_MEAN),
                        Map.entry("hawaii daylight", HAWAII_ALEUTIAN_DAYLIGHT),
                        Map.entry("hawaii standard", HAWAII_ALEUTIAN_STANDARD),
                        Map.entry("hawaii", HAWAII_ALEUTIAN_TIME),
                        Map.entry("local", LOCAL_TIME),
                        Map.entry("mountain daylight", MOUNTAIN_DAYLIGHT),
                        Map.entry("mountain standard", MOUNTAIN_STANDARD),
                        Map.entry("mountain", MOUNTAIN_TIME),
                        Map.entry("newfoundland daylight", NEWFOUNDLAND_DAYLIGHT),
                        Map.entry("newfoundland standard", NEWFOUNDLAND_STANDARD),
                        Map.entry("newfoundland", NEWFOUNDLAND_TIME),
                        Map.entry("pacific daylight", PACIFIC_DAYLIGHT),
                        Map.entry("pacific standard", PACIFIC_STANDARD),
                        Map.entry("pacific", PACIFIC_TIME),
                        Map.entry("atlantic daylight", ATLANTIC_DAYLIGHT),
                        Map.entry("atlantic standard", ATLANTIC_STANDARD),
                        Map.entry("atlantic", ATLANTIC_TIME),
                        Map.entry("universal", UNIVERSAL_TIME),
                        Map.entry("utc", UNIVERSAL_TIME),
                        Map.entry("coordinated universal time", UNIVERSAL_TIME)
                )
        );
    }

    TimeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a TimeCode instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching TimeCode
     * @throws IllegalArgumentException if no match is found
     */
    public static TimeCode fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}