/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * Depth in a File's ordered tree — where a {@link Location} sits.
 *
 * <ul>
 *   <li>{@code FILE} — header/trailer fields, once per file;</li>
 *   <li>{@code RECORD} — a subject record's own fields (and its coverage);</li>
 *   <li>{@code SUBRECORD} — a child record's fields, nested within a record.</li>
 * </ul>
 */
public enum RecordLevel {
    FILE,
    RECORD,
    SUBRECORD
}
