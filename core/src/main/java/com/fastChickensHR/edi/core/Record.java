/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

import java.util.List;

/**
 * One subject's data on a file (e.g. a subscriber). {@code fields} are the subject's own
 * values (its {@code RECORD}-level fields plus coverage); {@code children} are the nested
 * child records ({@code SUBRECORD}-level groups, e.g. dependents). In a flat file a record
 * collapses to a single line; in a grouped file it spans many.
 */
public record Record(List<Field> fields, List<Record> children) {
    public Record {
        fields = fields == null ? List.of() : List.copyOf(fields);
        children = children == null ? List.of() : List.copyOf(children);
    }

    /** A leaf Record with no nested dependents. */
    public static Record of(List<Field> fields) {
        return new Record(fields, List.of());
    }
}
