/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.Placement;
import com.fastChickensHR.edi.core.PlannedFile;
import com.fastChickensHR.edi.core.PlannedRecord;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvFileParserTest {

    private final CsvFileParser parser = new CsvFileParser();

    @Test
    void parsesFlatCsvIntoRecordsOfColumnPlacements() {
        String csv = "employeeId,firstName,lastName\nE1,Jane,Doe\nE2,John,\n";

        PlannedFile file = parser.parse(csv);

        assertEquals(Direction.INBOUND, file.direction());
        assertTrue(file.filePlacements().isEmpty());
        assertEquals(2, file.records().size());

        Map<String, String> row1 = byColumn(file.records().get(0));
        assertEquals("E1", row1.get("employeeId"));
        assertEquals("Jane", row1.get("firstName"));
        assertEquals("Doe", row1.get("lastName"));

        // An empty cell yields no placement (absence, not a blank value).
        Map<String, String> row2 = byColumn(file.records().get(1));
        assertEquals("John", row2.get("firstName"));
        assertFalse(row2.containsKey("lastName"));
    }

    @Test
    void honorsCsvQuotingOfEmbeddedDelimiters() {
        String csv = "id,name,city\n1,\"Doe, Jane\",\"Springfield\"\n";

        PlannedFile file = parser.parse(csv);

        Map<String, String> row = byColumn(file.records().get(0));
        assertEquals("Doe, Jane", row.get("name"));
        assertEquals("Springfield", row.get("city"));
    }

    @Test
    void emptyInputYieldsNoRecords() {
        assertEquals(0, parser.parse("").records().size());
    }

    private static Map<String, String> byColumn(PlannedRecord record) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Placement placement : record.placements()) {
            map.put(placement.position().location(), placement.value());
        }
        return map;
    }
}
