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
import java.util.Map;
import lombok.Getter;

/**
 * Code values for the X12 Time Code (data element 623), which identifies the time zone or ISO
 * offset that applies to an accompanying time. In the X12 834 (005010X220A1) it qualifies times
 * reported alongside date/time segments.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum TimeCode implements EdiCodeEnum {
  /** Equivalent to ISO P01 — X12 code "01". */
  ISO_P01("01", "Equivalent to ISO P01"),
  /** Equivalent to ISO P02 — X12 code "02". */
  ISO_P02("02", "Equivalent to ISO P02"),
  /** Equivalent to ISO P03 — X12 code "03". */
  ISO_P03("03", "Equivalent to ISO P03"),
  /** Equivalent to ISO P04 — X12 code "04". */
  ISO_P04("04", "Equivalent to ISO P04"),
  /** Equivalent to ISO P05 — X12 code "05". */
  ISO_P05("05", "Equivalent to ISO P05"),
  /** Equivalent to ISO P06 — X12 code "06". */
  ISO_P06("06", "Equivalent to ISO P06"),
  /** Equivalent to ISO P07 — X12 code "07". */
  ISO_P07("07", "Equivalent to ISO P07"),
  /** Equivalent to ISO P08 — X12 code "08". */
  ISO_P08("08", "Equivalent to ISO P08"),
  /** Equivalent to ISO P09 — X12 code "09". */
  ISO_P09("09", "Equivalent to ISO P09"),
  /** Equivalent to ISO P10 — X12 code "10". */
  ISO_P10("10", "Equivalent to ISO P10"),
  /** Equivalent to ISO P11 — X12 code "11". */
  ISO_P11("11", "Equivalent to ISO P11"),
  /** Equivalent to ISO P12 — X12 code "12". */
  ISO_P12("12", "Equivalent to ISO P12"),
  /** Equivalent to ISO M12 — X12 code "13". */
  ISO_M12("13", "Equivalent to ISO M12"),
  /** Equivalent to ISO M11 — X12 code "14". */
  ISO_M11("14", "Equivalent to ISO M11"),
  /** Equivalent to ISO M10 — X12 code "15". */
  ISO_M10("15", "Equivalent to ISO M10"),
  /** Equivalent to ISO M09 — X12 code "16". */
  ISO_M09("16", "Equivalent to ISO M09"),
  /** Equivalent to ISO M08 — X12 code "17". */
  ISO_M08("17", "Equivalent to ISO M08"),
  /** Equivalent to ISO M07 — X12 code "18". */
  ISO_M07("18", "Equivalent to ISO M07"),
  /** Equivalent to ISO M06 — X12 code "19". */
  ISO_M06("19", "Equivalent to ISO M06"),
  /** Equivalent to ISO M05 — X12 code "20". */
  ISO_M05("20", "Equivalent to ISO M05"),
  /** Equivalent to ISO M04 — X12 code "21". */
  ISO_M04("21", "Equivalent to ISO M04"),
  /** Equivalent to ISO M03 — X12 code "22". */
  ISO_M03("22", "Equivalent to ISO M03"),
  /** Equivalent to ISO M02 — X12 code "23". */
  ISO_M02("23", "Equivalent to ISO M02"),
  /** Equivalent to ISO M01 — X12 code "24". */
  ISO_M01("24", "Equivalent to ISO M01"),
  /** Alaska Daylight Time — X12 code "AD". */
  ALASKA_DAYLIGHT("AD", "Alaska Daylight Time"),
  /** Alaska Standard Time — X12 code "AS". */
  ALASKA_STANDARD("AS", "Alaska Standard Time"),
  /** Alaska Time — X12 code "AT". */
  ALASKA_TIME("AT", "Alaska Time"),
  /** Central Daylight Time — X12 code "CD". */
  CENTRAL_DAYLIGHT("CD", "Central Daylight Time"),
  /** Central Standard Time — X12 code "CS". */
  CENTRAL_STANDARD("CS", "Central Standard Time"),
  /** Central Time — X12 code "CT". */
  CENTRAL_TIME("CT", "Central Time"),
  /** Eastern Daylight Time — X12 code "ED". */
  EASTERN_DAYLIGHT("ED", "Eastern Daylight Time"),
  /** Eastern Standard Time — X12 code "ES". */
  EASTERN_STANDARD("ES", "Eastern Standard Time"),
  /** Eastern Time — X12 code "ET". */
  EASTERN_TIME("ET", "Eastern Time"),
  /** Greenwich Mean Time — X12 code "GM". */
  GREENWICH_MEAN("GM", "Greenwich Mean Time"),
  /** Hawaii-Aleutian Daylight Time — X12 code "HD". */
  HAWAII_ALEUTIAN_DAYLIGHT("HD", "Hawaii-Aleutian Daylight Time"),
  /** Hawaii-Aleutian Standard Time — X12 code "HS". */
  HAWAII_ALEUTIAN_STANDARD("HS", "Hawaii-Aleutian Standard Time"),
  /** Hawaii-Aleutian Time — X12 code "HT". */
  HAWAII_ALEUTIAN_TIME("HT", "Hawaii-Aleutian Time"),
  /** Local Time — X12 code "LT". */
  LOCAL_TIME("LT", "Local Time"),
  /** Mountain Daylight Time — X12 code "MD". */
  MOUNTAIN_DAYLIGHT("MD", "Mountain Daylight Time"),
  /** Mountain Standard Time — X12 code "MS". */
  MOUNTAIN_STANDARD("MS", "Mountain Standard Time"),
  /** Mountain Time — X12 code "MT". */
  MOUNTAIN_TIME("MT", "Mountain Time"),
  /** Newfoundland Daylight Time — X12 code "ND". */
  NEWFOUNDLAND_DAYLIGHT("ND", "Newfoundland Daylight Time"),
  /** Newfoundland Standard Time — X12 code "NS". */
  NEWFOUNDLAND_STANDARD("NS", "Newfoundland Standard Time"),
  /** Newfoundland Time — X12 code "NT". */
  NEWFOUNDLAND_TIME("NT", "Newfoundland Time"),
  /** Pacific Daylight Time — X12 code "PD". */
  PACIFIC_DAYLIGHT("PD", "Pacific Daylight Time"),
  /** Pacific Standard Time — X12 code "PS". */
  PACIFIC_STANDARD("PS", "Pacific Standard Time"),
  /** Pacific Time — X12 code "PT". */
  PACIFIC_TIME("PT", "Pacific Time"),
  /** Atlantic Daylight Time — X12 code "TD". */
  ATLANTIC_DAYLIGHT("TD", "Atlantic Daylight Time"),
  /** Atlantic Standard Time — X12 code "TS". */
  ATLANTIC_STANDARD("TS", "Atlantic Standard Time"),
  /** Atlantic Time — X12 code "TT". */
  ATLANTIC_TIME("TT", "Atlantic Time"),
  /** Universal Time Coordinate — X12 code "UT". */
  UNIVERSAL_TIME("UT", "Universal Time Coordinate");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<TimeCode> LOOKUP;

  static {
    // Include additional common terms users might enter
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("coordinated universal time", UNIVERSAL_TIME)));
  }

  TimeCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a TimeCode instance from any input string. Matches against codes, names, descriptions, and
   * common variations.
   *
   * @param input the string to look up
   * @return the matching TimeCode
   * @throws IllegalArgumentException if no match is found
   */
  public static TimeCode fromString(String input) {
    return LOOKUP.fromString(input);
  }

  /**
   * Returns the raw X12 code value for this constant (not the enum name), so the enum renders
   * directly into an EDI element.
   */
  @Override
  public String toString() {
    return code;
  }
}
