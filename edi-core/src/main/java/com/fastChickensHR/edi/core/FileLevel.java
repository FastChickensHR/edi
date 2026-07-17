/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * Depth in a File's ordered tree — where a {@link Position} sits.
 *
 * <ul>
 *   <li>{@code FILE} — header/trailer lines, once per file;</li>
 *   <li>{@code EMPLOYEE} — a subject {@link PlannedRecord}'s own lines (and its coverage);</li>
 *   <li>{@code DEPENDENT} — a dependent child-group's lines within a Record.</li>
 * </ul>
 */
public enum FileLevel {
    FILE,
    EMPLOYEE,
    DEPENDENT
}
