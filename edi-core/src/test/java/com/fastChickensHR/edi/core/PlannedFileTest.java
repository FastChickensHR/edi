/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlannedFileTest {

    @Test
    void buildsAnOrderedTreeAndDefendsItsCollections() {
        Placement ssn = new Placement(new Position(FileLevel.EMPLOYEE, "REF*34"), "123456789");
        Placement depName = new Placement(new Position(FileLevel.DEPENDENT, "NM1*03"), "Doe");
        PlannedRecord dependent = PlannedRecord.of(List.of(depName));
        PlannedRecord subscriber = new PlannedRecord(List.of(ssn), List.of(dependent));
        PlannedFile file = new PlannedFile(Direction.OUTBOUND, List.of(), List.of(subscriber));

        assertEquals(Direction.OUTBOUND, file.direction());
        assertEquals(1, file.records().size());
        assertEquals(1, file.records().get(0).children().size());
        assertThrows(UnsupportedOperationException.class,
                () -> file.records().get(0).placements().add(ssn));
    }

    @Test
    void nullValueMeansOmit() {
        Placement omit = new Placement(new Position(FileLevel.EMPLOYEE, "REF*1L"), null);
        assertTrue(omit.isOmitted());
        assertTrue(omit.valueIfPresent().isEmpty());
    }

    @Test
    void positionRequiresLevelAndLocation() {
        assertThrows(IllegalArgumentException.class, () -> new Position(null, "x"));
        assertThrows(IllegalArgumentException.class, () -> new Position(FileLevel.FILE, "  "));
    }
}
