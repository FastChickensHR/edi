/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.csv;

/**
 * Shared CSV conventions for the parse/generate pair.
 */
final class Csv {
    private Csv() {
    }

    /**
     * Reserved column that tags each row's depth so nested records survive the flat CSV shape.
     * A row's cell is the {@link com.fastChickensHR.edi.core.RecordLevel} name — {@code RECORD} for a
     * top-level subject, {@code SUBRECORD} for one of its children, which follow their parent in order.
     * Absent header column means a plain flat CSV (every row is a top-level record).
     */
    static final String RECORD_LEVEL_COLUMN = "recordLevel";
}
