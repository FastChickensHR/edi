/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.flatfile.delimited;

import static com.fastChickensHR.edi.core.RecordLevel.RECORD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.Location;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.RecordLevel;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Proves the parser/generator are configurable through {@link DelimitedFormat}, not hard-wired to
 * comma-separated values. Same records, a different format ⇒ a different file that still
 * round-trips.
 */
class DelimitedFormatTest {

  private static Field f(RecordLevel level, String name, String value) {
    return new Field(new Location(level, name), value);
  }

  private final List<Record> records =
      List.of(
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
  void headerlessParseNamesCellsByTheir1BasedColumnPosition() {
    // Regression for FastChickensHR/edi#152: a headerless file has no column names to read, so
    // every cell used to be dropped and each row became an empty Record — silent data loss.
    // Cells are now addressed positionally, 1-based like the element numbering elsewhere.
    DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();

    List<Record> back = new DelimitedFileParser(noHeader).parse("1,Jane\n2,John\n").records();

    assertEquals(
        List.of(
            Record.of(List.of(f(RECORD, "1", "1"), f(RECORD, "2", "Jane"))),
            Record.of(List.of(f(RECORD, "1", "2"), f(RECORD, "2", "John")))),
        back);
  }

  @Test
  void headerlessRoundTripsOnceTheColumnsArePositional() {
    // The asymmetry #152 described is gone: what the headerless parser produces, the headerless
    // generator writes back byte-identically.
    DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();
    String raw = "1,Jane\n2,John\n";

    FileContent parsed = new DelimitedFileParser(noHeader).parse(raw);

    assertEquals(
        raw,
        new DelimitedFileGenerator(noHeader)
            .generate(new FileContent(Direction.OUTBOUND, List.of(), parsed.records())));
  }

  @Test
  void headerlessParseNamesEachCellByWhereItActuallySits() {
    // Ragged rows are named by position, not by the widest row: a short row simply has no field
    // for the columns it does not reach, and an empty cell stays absent rather than blank.
    DelimitedFormat noHeader = DelimitedFormat.builder().header(false).build();

    List<Record> back = new DelimitedFileParser(noHeader).parse("1,Jane,x\n2,,\n3\n").records();

    assertEquals(
        List.of(
            Record.of(List.of(f(RECORD, "1", "1"), f(RECORD, "2", "Jane"), f(RECORD, "3", "x"))),
            Record.of(List.of(f(RECORD, "1", "2"))),
            Record.of(List.of(f(RECORD, "1", "3")))),
        back);
  }

  @Test
  void escapeCharacterEscapesEmbeddedQuoteInsteadOfDoublingIt() {
    // With an escape char set, an embedded quote is rendered as \" rather than the CSV default of
    // doubling it (""). Contrast
    // DelimitedFileGeneratorTest.embeddedQuoteIsDoubledOnGenerateAndRoundTrips.
    DelimitedFormat escaped = DelimitedFormat.builder().escape('\\').build();
    List<Record> recs =
        List.of(Record.of(List.of(f(RECORD, "id", "1"), f(RECORD, "note", "He said \"hi\""))));

    String out =
        new DelimitedFileGenerator(escaped)
            .generate(new FileContent(Direction.OUTBOUND, List.of(), recs));

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
