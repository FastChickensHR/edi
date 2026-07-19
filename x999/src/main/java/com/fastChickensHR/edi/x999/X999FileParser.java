/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x999;

import com.fastChickensHR.edi.core.Direction;
import com.fastChickensHR.edi.core.Field;
import com.fastChickensHR.edi.core.FileContent;
import com.fastChickensHR.edi.core.FileParser;
import com.fastChickensHR.edi.core.Location;
import com.fastChickensHR.edi.core.Record;
import com.fastChickensHR.edi.core.RecordLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * The X12 999 / 997 implementation of the {@link FileParser} seam: reads a functional acknowledgment
 * into a format-neutral {@link FileContent}. An acknowledgment replies to one functional group, so the
 * file-level fields carry the acknowledged group (functional id, group control number, group status),
 * and each {@link Record} carries one acknowledged transaction set (its control number and status) —
 * see {@link X999}.
 *
 * <p>The parser is dialect-dumb: it walks segments and emits the raw X12 codes as {@link Field}s at
 * their {@link X999} locations, leaving accept/reject interpretation and correlation to the caller. It
 * reads {@code AK1} (group), {@code AK2} (transaction set), {@code AK5}/{@code IK5} (transaction set
 * response) and {@code AK9} (group response); interior error-detail segments ({@code AK3/AK4/IK3/IK4})
 * are skipped in this pass. Delimiters are taken from the interchange's {@code ISA} envelope, defaulting
 * to {@code *} (element) and {@code ~} (segment).
 */
public final class X999FileParser implements FileParser {

    private static final char DEFAULT_ELEMENT_SEPARATOR = '*';
    private static final char DEFAULT_SEGMENT_TERMINATOR = '~';
    private static final int ISA_LENGTH = 106;

    @Override
    public FileContent parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return new FileContent(Direction.INBOUND, List.of(), List.of());
        }

        char elementSeparator = DEFAULT_ELEMENT_SEPARATOR;
        char segmentTerminator = DEFAULT_SEGMENT_TERMINATOR;
        if (raw.startsWith("ISA") && raw.length() >= ISA_LENGTH) {
            elementSeparator = raw.charAt(3);
            segmentTerminator = raw.charAt(ISA_LENGTH - 1);
        }

        List<Field> fileFields = new ArrayList<>();
        List<Record> records = new ArrayList<>();
        String pendingTransactionSetControlNumber = null;

        for (String rawSegment : raw.split(java.util.regex.Pattern.quote(String.valueOf(segmentTerminator)))) {
            String segment = rawSegment.strip();
            if (segment.isEmpty()) {
                continue;
            }
            String[] e = segment.split(java.util.regex.Pattern.quote(String.valueOf(elementSeparator)), -1);
            switch (e[0]) {
                case "ISA" -> addField(fileFields, RecordLevel.FILE, X999.INTERCHANGE_CONTROL_NUMBER, at(e, 13));
                case "TA1" -> {
                    addField(fileFields, RecordLevel.FILE, X999.ACKNOWLEDGED_INTERCHANGE_CONTROL_NUMBER, at(e, 1));
                    addField(fileFields, RecordLevel.FILE, X999.INTERCHANGE_ACK_STATUS, at(e, 4));
                }
                case "AK1" -> {
                    addField(fileFields, RecordLevel.FILE, X999.FUNCTIONAL_ID_CODE, at(e, 1));
                    addField(fileFields, RecordLevel.FILE, X999.GROUP_CONTROL_NUMBER, at(e, 2));
                }
                case "AK2" -> pendingTransactionSetControlNumber = at(e, 2);
                case "AK5", "IK5" -> {
                    List<Field> fields = new ArrayList<>();
                    addField(fields, RecordLevel.RECORD, X999.TRANSACTION_SET_CONTROL_NUMBER,
                            pendingTransactionSetControlNumber);
                    addField(fields, RecordLevel.RECORD, X999.TRANSACTION_SET_STATUS, at(e, 1));
                    if (!fields.isEmpty()) {
                        records.add(Record.of(fields));
                    }
                    pendingTransactionSetControlNumber = null;
                }
                case "AK9" -> addField(fileFields, RecordLevel.FILE, X999.GROUP_STATUS, at(e, 1));
                default -> { /* other segments (GS/ST/AK3/AK4/IK3/IK4/SE/GE/IEA) carry no field we surface */ }
            }
        }

        return new FileContent(Direction.INBOUND, fileFields, records);
    }

    /** The element at index {@code i}, trimmed; null when absent or blank (absence, not a blank value). */
    private static String at(String[] elements, int i) {
        if (i >= elements.length) {
            return null;
        }
        String value = elements[i].strip();
        return value.isEmpty() ? null : value;
    }

    private static void addField(List<Field> into, RecordLevel level, String location, String value) {
        if (value != null) {
            into.add(new Field(new Location(level, location), value));
        }
    }
}
