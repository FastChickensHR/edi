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
 * The format-neutral, fully-resolved file a {@link FileGenerator} serializes (or a {@link
 * FileParser} produces). An ordered tree: file-level {@code fileFields} (header/trailer, once per
 * file) plus one {@link Record} per subject. This is the pivot between the consuming application
 * and a format's dialect — the caller speaks only this, and each format interprets the {@link
 * Location}s.
 *
 * @param direction whether this content was parsed from a partner file or is being emitted to one
 * @param fileFields the file-level (header/trailer) fields, appearing once per file
 * @param records one {@link Record} per subject, in file order
 */
public record FileContent(Direction direction, List<Field> fileFields, List<Record> records) {
  /** Rejects a null direction and normalizes null lists to empty immutable copies. */
  public FileContent {
    if (direction == null) {
      throw new IllegalArgumentException("direction is required");
    }
    fileFields = fileFields == null ? List.of() : List.copyOf(fileFields);
    records = records == null ? List.of() : List.copyOf(records);
  }
}
