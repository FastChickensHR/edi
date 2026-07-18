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
 * The CSV implementation of the {@link FileParser} seam: reads a flat, header-row CSV into a
 * format-neutral {@link FileContent}. A CSV is the degenerate <em>Flat</em> delimited file — one line
 * per record — so each data row becomes one {@link Record} and each non-empty cell becomes a
 * {@link Field} whose {@link Location} location is the column name.
 *
 * <p>The parser is intentionally dumb: it knows columns and cells, not domain meaning. In a plain flat
 * CSV every row is a top-level {@link Record} whose fields sit at {@link RecordLevel#RECORD} — the
 * inbound Field Map assigns each column's data element downstream. When the reserved
 * {@link Csv#RECORD_LEVEL_COLUMN} is present, the parser reconstructs nesting: {@code SUBRECORD} rows
 * attach as children of the {@code RECORD} row they follow, inverting {@link CsvFileGenerator}. Empty
 * cells produce no field (absence, not a blank value).
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
            boolean nested = headers.contains(Csv.RECORD_LEVEL_COLUMN);
            List<Record> records = nested ? parseNested(parser, headers) : parseFlat(parser, headers);
            return new FileContent(Direction.INBOUND, List.of(), records);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse CSV: " + e.getMessage(), e);
        }
    }

    private static List<Record> parseFlat(CSVParser parser, List<String> headers) {
        List<Record> records = new ArrayList<>();
        for (CSVRecord row : parser) {
            records.add(Record.of(rowFields(row, headers, RecordLevel.RECORD)));
        }
        return records;
    }

    private static List<Record> parseNested(CSVParser parser, List<String> headers) {
        List<Record> records = new ArrayList<>();
        List<Field> openFields = null;
        List<Record> openChildren = null;
        for (CSVRecord row : parser) {
            String level = row.isMapped(Csv.RECORD_LEVEL_COLUMN) ? row.get(Csv.RECORD_LEVEL_COLUMN) : "";
            if (RecordLevel.SUBRECORD.name().equals(level)) {
                if (openChildren == null) {
                    throw new IllegalArgumentException("SUBRECORD row has no preceding RECORD row");
                }
                openChildren.add(Record.of(rowFields(row, headers, RecordLevel.SUBRECORD)));
            } else if (RecordLevel.RECORD.name().equals(level)) {
                if (openFields != null) {
                    records.add(new Record(openFields, openChildren));
                }
                openFields = rowFields(row, headers, RecordLevel.RECORD);
                openChildren = new ArrayList<>();
            } else {
                throw new IllegalArgumentException(
                        "unknown " + Csv.RECORD_LEVEL_COLUMN + " value: '" + level + "'");
            }
        }
        if (openFields != null) {
            records.add(new Record(openFields, openChildren));
        }
        return records;
    }

    private static List<Field> rowFields(CSVRecord row, List<String> headers, RecordLevel level) {
        List<Field> fields = new ArrayList<>();
        for (String column : headers) {
            if (column.equals(Csv.RECORD_LEVEL_COLUMN) || !row.isMapped(column)) {
                continue;
            }
            String value = row.get(column);
            if (value != null && !value.isEmpty()) {
                fields.add(new Field(new Location(level, column), value));
            }
        }
        return fields;
    }
}
