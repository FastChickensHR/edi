/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.FileGenerator;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.RecordLevel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * The CSV implementation of the {@link FileGenerator} seam (ADR-0313): writes a format-neutral
 * {@link FileContent} out as a flat, header-row CSV. The inverse of {@link CsvFileParser}.
 *
 * <p>Each {@link Record} becomes a row and each {@link Field} a cell under its column (the field's
 * {@link com.fastChickensHR.edi.core.Location} name); an omitted field is an empty cell. Nesting —
 * which flat CSV cannot express directly — is flattened into <em>linked rows</em>: a parent row
 * followed by its child rows, each tagged in a reserved {@link Csv#RECORD_LEVEL_COLUMN}. When nothing
 * nests, that column is omitted and the output is a plain flat CSV that round-trips through the parser.
 */
public final class CsvFileGenerator implements FileGenerator {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
            .setRecordSeparator("\n")
            .build();

    @Override
    public String generate(FileContent file) {
        if (!file.fileFields().isEmpty()) {
            throw new IllegalArgumentException(
                    "CSV has no file-level row; FileContent.fileFields must be empty");
        }
        if (file.records().isEmpty()) {
            return "";
        }

        boolean nested = file.records().stream().anyMatch(r -> !r.children().isEmpty());

        LinkedHashSet<String> columns = new LinkedHashSet<>();
        for (Record record : file.records()) {
            collectColumns(record, columns);
        }

        List<String> header = new ArrayList<>();
        if (nested) {
            header.add(Csv.RECORD_LEVEL_COLUMN);
        }
        header.addAll(columns);

        StringWriter out = new StringWriter();
        try (CSVPrinter printer = new CSVPrinter(out, FORMAT)) {
            printer.printRecord(header);
            for (Record record : file.records()) {
                printRow(printer, header, record, RecordLevel.RECORD);
                for (Record child : record.children()) {
                    if (!child.children().isEmpty()) {
                        throw new IllegalArgumentException(
                                "CSV supports one level of nesting; a SUBRECORD cannot have children");
                    }
                    printRow(printer, header, child, RecordLevel.SUBRECORD);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to generate CSV: " + e.getMessage(), e);
        }
        return out.toString();
    }

    private static void collectColumns(Record record, LinkedHashSet<String> columns) {
        for (Field field : record.fields()) {
            if (!field.isOmitted()) {
                columns.add(field.location().name());
            }
        }
        for (Record child : record.children()) {
            collectColumns(child, columns);
        }
    }

    private static void printRow(CSVPrinter printer, List<String> header, Record record, RecordLevel level)
            throws IOException {
        Map<String, String> values = fieldValues(record);
        List<String> cells = new ArrayList<>(header.size());
        for (String column : header) {
            if (column.equals(Csv.RECORD_LEVEL_COLUMN)) {
                cells.add(level.name());
            } else {
                cells.add(values.getOrDefault(column, ""));
            }
        }
        printer.printRecord(cells);
    }

    private static Map<String, String> fieldValues(Record record) {
        Map<String, String> values = new LinkedHashMap<>();
        for (Field field : record.fields()) {
            if (!field.isOmitted()) {
                values.putIfAbsent(field.location().name(), field.value());
            }
        }
        return values;
    }
}
