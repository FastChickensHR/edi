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

        String out = new DelimitedFileGenerator(pipe).generate(content());

        assertEquals("id|name\n1|Jane\n2|John\n", out);
        assertEquals(records, new DelimitedFileParser(pipe).parse(out).records());
    }

    @Test
    void tabDelimitedFormatRoundTrips() {
        DelimitedFormat tab = DelimitedFormat.builder().delimiter('\t').build();

        String out = new DelimitedFileGenerator(tab).generate(content());

        assertEquals("id\tname\n1\tJane\n2\tJohn\n", out);
        assertEquals(records, new DelimitedFileParser(tab).parse(out).records());
    }

    @Test
    void headerlessFormatOmitsTheHeaderRow() {
        DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();

        String out = new DelimitedFileGenerator(noHeader).generate(content());

        assertEquals("1,Jane\n2,John\n", out);
    }

    @Test
    void headerlessParseCurrentlyDropsAllFields() {
        // KNOWN BUG (FastChickensHR/edi#152): headerless generate works (above), but headerless parse
        // has no column names, so every cell is dropped and each row becomes an empty Record — a silent
        // data-loss asymmetry. Pinned as current behavior (a regression net) until the bug is fixed.
        DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();

        List<Record> back = new DelimitedFileParser(noHeader).parse("1,Jane\n2,John\n").records();

        assertEquals(List.of(Record.of(List.of()), Record.of(List.of())), back);
    }

    @Test
    void escapeCharacterEscapesEmbeddedQuoteInsteadOfDoublingIt() {
        // With an escape char set, an embedded quote is rendered as \" rather than the CSV default of
        // doubling it (""). Contrast DelimitedFileGeneratorTest.embeddedQuoteIsDoubledOnGenerateAndRoundTrips.
        DelimitedFormat escaped = DelimitedFormat.builder().escape('\\').build();
        List<Record> recs = List.of(
                Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "note", "He said \"hi\""))));

        String out = new DelimitedFileGenerator(escaped).generate(new FileContent(Direction.OUTBOUND, List.of(), recs));

        assertEquals("id,note\n1,\"He said \\\"hi\\\"\"\n", out);
        assertEquals(recs, new DelimitedFileParser(escaped).parse(out).records());
    }

    @Test
    void nullQuoteProducesAnUnquotedFormatThatRoundTrips() {
        DelimitedFormat unquoted = DelimitedFormat.builder().quote(null).build();

        String out = new DelimitedFileGenerator(unquoted).generate(content());

        assertEquals("id,name\n1,Jane\n2,John\n", out);
        assertEquals(records, new DelimitedFileParser(unquoted).parse(out).records());
    }

    @Test
    void customRecordSeparatorIsHonored() {
        DelimitedFormat crlf = DelimitedFormat.builder().recordSeparator("\r\n").build();

        String out = new DelimitedFileGenerator(crlf).generate(content());

        assertEquals("id,name\r\n1,Jane\r\n2,John\r\n", out);
    }
}
