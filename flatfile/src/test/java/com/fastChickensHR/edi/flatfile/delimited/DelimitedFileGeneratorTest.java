/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.flatfile.delimited;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Location;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.RecordLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fastChickensHR.edi.core.RecordLevel.RECORD;
import static com.fastChickensHR.edi.core.RecordLevel.SUBRECORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DelimitedFileGeneratorTest {

    private final DelimitedFileGenerator generator = new DelimitedFileGenerator();
    private final DelimitedFileParser parser = new DelimitedFileParser();

    private static Field f(RecordLevel level, String name, String value) {
        return new Field(new Location(level, name), value);
    }

    @Test
    void flatFileGeneratesPlainCsvWithoutTheReservedColumn() {
        FileContent file = new FileContent(Direction.OUTBOUND, List.of(), List.of(
                Record.of(List.of(f(RECORD, "employeeId", "E1"), f(RECORD, "firstName", "Jane"))),
                Record.of(List.of(f(RECORD, "employeeId", "E2"), f(RECORD, "firstName", "John")))));

        String csv = generator.generate(file);

        assertEquals("employeeId,firstName\nE1,Jane\nE2,John\n", csv);
        assertFalse(csv.contains(LinkedRows.RECORD_LEVEL_COLUMN));
    }

    @Test
    void nestedFileFlattensToLinkedRows() {
        Record subscriber = new Record(
                List.of(f(RECORD, "first", "Jane")),
                List.of(Record.of(List.of(f(SUBRECORD, "first", "Kid"))),
                        Record.of(List.of(f(SUBRECORD, "first", "Kid2")))));

        String csv = generator.generate(new FileContent(Direction.OUTBOUND, List.of(), List.of(subscriber)));

        assertEquals("recordLevel,first\nRECORD,Jane\nSUBRECORD,Kid\nSUBRECORD,Kid2\n", csv);
    }

    @Test
    void flatRoundTripPreservesRecords() {
        List<Record> records = List.of(
                Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "name", "Jane"))),
                Record.of(List.of(f(RECORD, "id", "2"), f(RECORD, "name", "John"))));

        FileContent back = parser.parse(generator.generate(new FileContent(Direction.OUTBOUND, List.of(), records)));

        assertEquals(records, back.records());
    }

    @Test
    void nestedRoundTripReconstructsChildren() {
        Record subscriber = new Record(
                List.of(f(RECORD, "first", "Jane")),
                List.of(Record.of(List.of(f(SUBRECORD, "first", "Kid"), f(SUBRECORD, "rel", "child")))));
        List<Record> records = List.of(subscriber);

        FileContent back = parser.parse(generator.generate(new FileContent(Direction.OUTBOUND, List.of(), records)));

        assertEquals(records, back.records());
    }

    @Test
    void raggedRowsRoundTripWithEmptyCellsAsAbsence() {
        List<Record> records = List.of(
                Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "extra", "X"))),
                Record.of(List.of(f(RECORD, "id", "2"))));

        FileContent back = parser.parse(generator.generate(new FileContent(Direction.OUTBOUND, List.of(), records)));

        assertEquals(records, back.records());
    }

    @Test
    void emptyFileGeneratesEmptyString() {
        assertEquals("", generator.generate(new FileContent(Direction.OUTBOUND, List.of(), List.of())));
    }

    @Test
    void fileLevelFieldsAreRejected() {
        FileContent file = new FileContent(Direction.OUTBOUND, List.of(f(RECORD, "senderId", "S1")), List.of());
        assertThrows(IllegalArgumentException.class, () -> generator.generate(file));
    }

    @Test
    void parserRejectsChildRowWithoutParent() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("recordLevel,first\nSUBRECORD,Kid\n"));
    }

    @Test
    void twoLevelNestingIsRejected() {
        // The linked-row convention flattens exactly one level of nesting (RECORD + its SUBRECORDs);
        // a SUBRECORD that itself has children has nowhere to go in a flat file.
        Record grandchild = Record.of(List.of(f(SUBRECORD, "first", "Grand")));
        Record child = new Record(List.of(f(SUBRECORD, "first", "Kid")), List.of(grandchild));
        Record subscriber = new Record(List.of(f(RECORD, "first", "Jane")), List.of(child));

        assertThrows(IllegalArgumentException.class,
                () -> generator.generate(new FileContent(Direction.OUTBOUND, List.of(), List.of(subscriber))));
    }

    @Test
    void embeddedQuoteIsDoubledOnGenerateAndRoundTrips() {
        List<Record> records = List.of(
                Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "note", "He said \"hi\""))));

        String csv = generator.generate(new FileContent(Direction.OUTBOUND, List.of(), records));

        assertEquals("id,note\n1,\"He said \"\"hi\"\"\"\n", csv);
        assertEquals(records, parser.parse(csv).records());
    }

    @Test
    void embeddedDelimiterIsQuotedOnGenerateAndRoundTrips() {
        List<Record> records = List.of(
                Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "name", "Doe, Jane"))));

        String csv = generator.generate(new FileContent(Direction.OUTBOUND, List.of(), records));

        assertEquals("id,name\n1,\"Doe, Jane\"\n", csv);
        assertEquals(records, parser.parse(csv).records());
    }
}
