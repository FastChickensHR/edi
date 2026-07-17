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
 * One subject's data on a File (a Subscriber). {@code placements} are the subject's own
 * Values-at-Positions (its EMPLOYEE-level lines plus coverage); {@code children} are the nested
 * dependent Records (DEPENDENT-level child-groups). In a Flat File a Record collapses to a single
 * line; in a Grouped File it spans many.
 */
public record PlannedRecord(List<Placement> placements, List<PlannedRecord> children) {
    public PlannedRecord {
        placements = placements == null ? List.of() : List.copyOf(placements);
        children = children == null ? List.of() : List.copyOf(children);
    }

    /** A leaf Record with no nested dependents. */
    public static PlannedRecord of(List<Placement> placements) {
        return new PlannedRecord(placements, List.of());
    }
}
