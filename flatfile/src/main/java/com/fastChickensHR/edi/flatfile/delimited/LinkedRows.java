/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.flatfile.delimited;

/**
 * The delimited variant's linked-row nesting convention. A delimited file has no native way to
 * express a parent record with children, so nesting is flattened into <em>linked rows</em>: a parent
 * row followed by its child rows, each tagged with its depth in a reserved {@link #RECORD_LEVEL_COLUMN}.
 *
 * <p>Internal to {@code flatfile.delimited} — it hangs on the delimited variant's <em>header column</em>,
 * which a positional variant like fixed-width has no equivalent of, so it is not shared (see #136).
 */
final class LinkedRows {
    private LinkedRows() {
    }

    /**
     * Reserved column that tags each row's depth so nested records survive the flat delimited shape.
     * A row's cell is the {@link com.fastChickensHR.edi.core.RecordLevel} name — {@code RECORD} for a
     * top-level subject, {@code SUBRECORD} for one of its children, which follow their parent in order.
     * An absent header column means a plain flat file (every row is a top-level record).
     */
    static final String RECORD_LEVEL_COLUMN = "recordLevel";
}
