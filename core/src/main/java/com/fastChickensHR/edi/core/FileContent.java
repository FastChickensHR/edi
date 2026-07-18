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
 * The format-neutral, fully-resolved file a {@link FileGenerator} serializes (or a {@link FileParser}
 * produces). An ordered tree: file-level {@code fileFields} (header/trailer, once per file) plus
 * one {@link Record} per subject. This is the pivot between the app's requirements engine and a
 * format's dialect — the app speaks only this, and each format interprets the {@link Location}s.
 */
public record FileContent(Direction direction, List<Field> fileFields, List<Record> records) {
    public FileContent {
        if (direction == null) {
            throw new IllegalArgumentException("direction is required");
        }
        fileFields = fileFields == null ? List.of() : List.copyOf(fileFields);
        records = records == null ? List.of() : List.copyOf(records);
    }
}
