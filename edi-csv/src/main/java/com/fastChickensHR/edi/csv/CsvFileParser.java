/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.FileLevel;
import com.fastChickensHR.edi.core.FileParser;
import com.fastChickensHR.edi.core.Placement;
import com.fastChickensHR.edi.core.PlannedFile;
import com.fastChickensHR.edi.core.PlannedRecord;
import com.fastChickensHR.edi.core.Position;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CSV implementation of the {@link FileParser} seam (ADR-0313): reads a flat, header-row CSV into a
 * format-neutral {@link PlannedFile}. A CSV is the degenerate <em>Flat</em> delimited file — one line
 * per record — so each data row becomes one {@link PlannedRecord} and each non-empty cell becomes a
 * {@link Placement} whose {@link Position} location is the column name.
 *
 * <p>The parser is intentionally dumb: it knows columns and cells, not domain meaning. A flat CSV row's
 * cells carry no inherent tree depth, so every {@code Position} is emitted at {@link FileLevel#EMPLOYEE}
 * as a neutral placeholder — the inbound Field Map assigns each column's data element (and thus its real
 * level and whether a row is an employee or a dependent) downstream. Empty cells produce no placement
 * (absence, not a blank value).
 */
public final class CsvFileParser implements FileParser {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build();

    @Override
    public PlannedFile parse(String raw) {
        try (CSVParser parser = CSVParser.parse(raw == null ? "" : raw, FORMAT)) {
            List<String> headers = parser.getHeaderNames();
            List<PlannedRecord> records = new ArrayList<>();
            for (CSVRecord row : parser) {
                records.add(PlannedRecord.of(rowPlacements(row, headers)));
            }
            return new PlannedFile(Direction.INBOUND, List.of(), records);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse CSV: " + e.getMessage(), e);
        }
    }

    private static List<Placement> rowPlacements(CSVRecord row, List<String> headers) {
        List<Placement> placements = new ArrayList<>();
        for (String column : headers) {
            if (!row.isMapped(column)) {
                continue;
            }
            String value = row.get(column);
            if (value != null && !value.isEmpty()) {
                placements.add(new Placement(new Position(FileLevel.EMPLOYEE, column), value));
            }
        }
        return placements;
    }
}
