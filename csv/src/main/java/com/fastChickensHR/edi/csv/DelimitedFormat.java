/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;

/**
 * The shape of a delimited flat file: how a line is split into cells and how cells are quoted,
 * escaped, and terminated. A {@code DelimitedFormat} is a small, edi-owned value that
 * <em>wraps</em> Apache Commons CSV's {@link CSVFormat} — the wrap is deliberate: callers configure
 * a curated set of knobs (delimiter, quote, escape, quoting policy, record separator, header
 * handling) and never touch the underlying engine, so the dependency stays an implementation detail
 * the module can swap or extend without breaking consumers.
 *
 * <p>Build one with a preset factory such as {@link #csv()} for a known convention, or with
 * {@link #builder()} to match a foreign system's file exactly.
 */
public final class DelimitedFormat {

    private final char delimiter;
    private final Character quote;
    private final Character escape;
    private final QuoteMode quoteMode;
    private final String recordSeparator;
    private final boolean header;

    private DelimitedFormat(Builder builder) {
        this.delimiter = builder.delimiter;
        this.quote = builder.quote;
        this.escape = builder.escape;
        this.quoteMode = builder.quoteMode;
        this.recordSeparator = builder.recordSeparator;
        this.header = builder.header;
    }

    /**
     * The pinned CSV convention: comma-delimited, {@code "}-quoted with quote-doubling (no escape
     * character), minimal quoting, LF ({@code \n}) record separator, and a leading header row.
     * This is the format the module's round-trip tests verify; it is the one preset guaranteed to
     * survive parse-then-generate unchanged.
     */
    public static DelimitedFormat csv() {
        return builder()
                .delimiter(',')
                .quote('"')
                .escape(null)
                .quoteMode(QuoteMode.MINIMAL)
                .recordSeparator("\n")
                .header(true)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Whether files in this format carry a leading header row. */
    boolean hasHeader() {
        return header;
    }

    /** The Commons CSV format for reading — header-aware when {@link #hasHeader()}. */
    CSVFormat parseFormat() {
        CSVFormat.Builder builder = base();
        if (header) {
            builder.setHeader().setSkipHeaderRecord(true);
        }
        return builder.build();
    }

    /** The Commons CSV format for writing; the header row (when any) is printed by the generator. */
    CSVFormat generateFormat() {
        return base().build();
    }

    private CSVFormat.Builder base() {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter(delimiter)
                .setQuote(quote)
                .setEscape(escape)
                .setQuoteMode(quoteMode)
                .setRecordSeparator(recordSeparator);
    }

    /**
     * Assembles a custom {@link DelimitedFormat}. Defaults mirror {@link #csv()}; override only the
     * knobs a target file differs on.
     */
    public static final class Builder {
        private char delimiter = ',';
        private Character quote = '"';
        private Character escape = null;
        private QuoteMode quoteMode = QuoteMode.MINIMAL;
        private String recordSeparator = "\n";
        private boolean header = true;

        private Builder() {
        }

        public Builder delimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        /** The quote character, or {@code null} for an unquoted format. */
        public Builder quote(Character quote) {
            this.quote = quote;
            return this;
        }

        /**
         * The escape character, or {@code null} to escape an embedded quote by doubling it (the CSV
         * convention).
         */
        public Builder escape(Character escape) {
            this.escape = escape;
            return this;
        }

        public Builder quoteMode(QuoteMode quoteMode) {
            this.quoteMode = quoteMode;
            return this;
        }

        public Builder recordSeparator(String recordSeparator) {
            this.recordSeparator = recordSeparator;
            return this;
        }

        public Builder header(boolean header) {
            this.header = header;
            return this;
        }

        public DelimitedFormat build() {
            return new DelimitedFormat(this);
        }
    }
}
