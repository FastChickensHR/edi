/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Location;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.RecordLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fastChickensHR.edi.core.RecordLevel.RECORD;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Proves the parser/generator are configurable through {@link DelimitedFormat}, not hard-wired to
 * comma-separated values. Same records, a different format ⇒ a different file that still round-trips.
 */
class DelimitedFormatTest {

    private static Field f(RecordLevel level, String name, String value) {
        return new Field(new Location(level, name), value);
    }

    private final List<Record> records = List.of(
            Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "name", "Jane"))),
            Record.of(List.of(f(RECORD, "id", "2"), f(RECORD, "name", "John"))));

    private FileContent content() {
        return new FileContent(Direction.OUTBOUND, List.of(), records);
    }

    @Test
    void pipeDelimitedFormatUsesThePipeSeparatorAndRoundTrips() {
        DelimitedFormat pipe = DelimitedFormat.builder().delimiter('|').build();

        String out = new CsvFileGenerator(pipe).generate(content());

        assertEquals("id|name\n1|Jane\n2|John\n", out);
        assertEquals(records, new CsvFileParser(pipe).parse(out).records());
    }

    @Test
    void tabDelimitedFormatRoundTrips() {
        DelimitedFormat tab = DelimitedFormat.builder().delimiter('\t').build();

        String out = new CsvFileGenerator(tab).generate(content());

        assertEquals("id\tname\n1\tJane\n2\tJohn\n", out);
        assertEquals(records, new CsvFileParser(tab).parse(out).records());
    }

    @Test
    void headerlessFormatOmitsTheHeaderRow() {
        DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();

        String out = new CsvFileGenerator(noHeader).generate(content());

        assertEquals("1,Jane\n2,John\n", out);
    }
}
