/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.RecordLevel;
import com.fastChickensHR.edi.core.FileParser;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CSV implementation of the {@link FileParser} seam (ADR-0313): reads a flat, header-row CSV into a
 * format-neutral {@link FileContent}. A CSV is the degenerate <em>Flat</em> delimited file — one line
 * per record — so each data row becomes one {@link Record} and each non-empty cell becomes a
 * {@link Field} whose {@link Location} location is the column name.
 *
 * <p>The parser is intentionally dumb: it knows columns and cells, not domain meaning. A flat CSV row's
 * cells carry no inherent tree depth, so every {@code Location} is emitted at {@link RecordLevel#RECORD}
 * as a neutral placeholder — the inbound Field Map assigns each column's data element (and thus its real
 * level and whether a row is an employee or a dependent) downstream. Empty cells produce no field
 * (absence, not a blank value).
 */
public final class CsvFileParser implements FileParser {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build();

    @Override
    public FileContent parse(String raw) {
        try (CSVParser parser = CSVParser.parse(raw == null ? "" : raw, FORMAT)) {
            List<String> headers = parser.getHeaderNames();
            List<Record> records = new ArrayList<>();
            for (CSVRecord row : parser) {
                records.add(Record.of(rowPlacements(row, headers)));
            }
            return new FileContent(Direction.INBOUND, List.of(), records);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse CSV: " + e.getMessage(), e);
        }
    }

    private static List<Field> rowPlacements(CSVRecord row, List<String> headers) {
        List<Field> fields = new ArrayList<>();
        for (String column : headers) {
            if (!row.isMapped(column)) {
                continue;
            }
            String value = row.get(column);
            if (value != null && !value.isEmpty()) {
                fields.add(new Field(new Location(RecordLevel.RECORD, column), value));
            }
        }
        return fields;
    }
}
