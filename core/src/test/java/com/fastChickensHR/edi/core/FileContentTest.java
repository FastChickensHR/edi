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

class FileContentTest {

    @Test
    void buildsAnOrderedTreeAndDefendsItsCollections() {
        Field ssn = new Field(new Location(RecordLevel.RECORD, "REF*34"), "123456789");
        Field depName = new Field(new Location(RecordLevel.SUBRECORD, "NM1*03"), "Doe");
        Record dependent = Record.of(List.of(depName));
        Record subscriber = new Record(List.of(ssn), List.of(dependent));
        FileContent file = new FileContent(Direction.OUTBOUND, List.of(), List.of(subscriber));

        assertEquals(Direction.OUTBOUND, file.direction());
        assertEquals(1, file.records().size());
        assertEquals(1, file.records().get(0).children().size());
        assertThrows(UnsupportedOperationException.class,
                () -> file.records().get(0).fields().add(ssn));
    }

    @Test
    void nullValueMeansOmit() {
        Field omit = new Field(new Location(RecordLevel.RECORD, "REF*1L"), null);
        assertTrue(omit.isOmitted());
        assertTrue(omit.valueIfPresent().isEmpty());
    }

    @Test
    void positionRequiresLevelAndLocation() {
        assertThrows(IllegalArgumentException.class, () -> new Location(null, "x"));
        assertThrows(IllegalArgumentException.class, () -> new Location(RecordLevel.FILE, "  "));
    }
}
