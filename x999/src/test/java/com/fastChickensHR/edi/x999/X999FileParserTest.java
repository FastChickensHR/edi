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
import com.fastChickensHR.edi.core.Record;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class X999FileParserTest {

    private final X999FileParser parser = new X999FileParser();

    // A well-formed 106-char ISA (element sep '*', segment terminator '~', ISA13 = 000000123).
    private static final String ISA =
            "ISA*00*          *00*          *ZZ*SENDER         *ZZ*RECEIVER       *260119*1200*U*00501*000000123*0*P*:~";

    @Test
    void parsesAnAcceptedAcknowledgment() {
        String ack = ISA
                + "GS*FA*SENDER*RECEIVER*20260119*1200*42*X*005010X231A1~"
                + "ST*999*0001~"
                + "AK1*BE*000000042~"
                + "AK2*834*0001~"
                + "IK5*A~"
                + "AK9*A*1*1*1~"
                + "SE*5*0001~GE*1*42~IEA*1*000000123~";

        FileContent out = parser.parse(ack);

        assertEquals(Direction.INBOUND, out.direction());
        assertEquals("000000123", file(out, X999.INTERCHANGE_CONTROL_NUMBER)); // ISA13
        assertEquals("BE", file(out, X999.FUNCTIONAL_ID_CODE));                // AK101
        assertEquals("000000042", file(out, X999.GROUP_CONTROL_NUMBER));       // AK102 (acked GS06)
        assertEquals("A", file(out, X999.GROUP_STATUS));                       // AK901

        assertEquals(1, out.records().size());
        Record ts = out.records().get(0);
        assertEquals("0001", rec(ts, X999.TRANSACTION_SET_CONTROL_NUMBER));    // AK202 (acked ST02)
        assertEquals("A", rec(ts, X999.TRANSACTION_SET_STATUS));               // IK501
    }

    @Test
    void parsesARejectionWithMultipleTransactionSets() {
        String ack = ISA
                + "AK1*BE*000000042~"
                + "AK2*834*0001~IK5*A~"
                + "AK2*834*0002~IK5*R~"
                + "AK9*P*2*2*1~";

        FileContent out = parser.parse(ack);

        assertEquals("P", file(out, X999.GROUP_STATUS)); // partially accepted
        assertEquals(2, out.records().size());
        assertEquals("0001", rec(out.records().get(0), X999.TRANSACTION_SET_CONTROL_NUMBER));
        assertEquals("A", rec(out.records().get(0), X999.TRANSACTION_SET_STATUS));
        assertEquals("0002", rec(out.records().get(1), X999.TRANSACTION_SET_CONTROL_NUMBER));
        assertEquals("R", rec(out.records().get(1), X999.TRANSACTION_SET_STATUS));
    }

    @Test
    void reads997StyleAk5AndSkipsErrorDetailSegments() {
        String ack = ISA
                + "AK1*BE*000000042~"
                + "AK2*834*0007~"
                + "AK3*NM1*8**8~AK4*3*1068*7~" // error-detail segments — skipped this pass
                + "AK5*E~"
                + "AK9*E*1*1*1~";

        FileContent out = parser.parse(ack);

        assertEquals("E", file(out, X999.GROUP_STATUS));
        assertEquals(1, out.records().size());
        assertEquals("0007", rec(out.records().get(0), X999.TRANSACTION_SET_CONTROL_NUMBER));
        assertEquals("E", rec(out.records().get(0), X999.TRANSACTION_SET_STATUS));
    }

    @Test
    void fallsBackToDefaultDelimitersWhenNoIsaEnvelope() {
        // No ISA — the parser defaults to '*' / '~'.
        String ack = "AK1*BE*000000042~AK2*834*0001~IK5*A~AK9*A*1*1*1~";

        FileContent out = parser.parse(ack);

        assertEquals("000000042", file(out, X999.GROUP_CONTROL_NUMBER));
        assertNull(file(out, X999.INTERCHANGE_CONTROL_NUMBER)); // no ISA present
        assertEquals(1, out.records().size());
        assertEquals("0001", rec(out.records().get(0), X999.TRANSACTION_SET_CONTROL_NUMBER));
    }

    @Test
    void blankInputProducesAnEmptyFileContent() {
        FileContent out = parser.parse("   ");
        assertTrue(out.fileFields().isEmpty());
        assertTrue(out.records().isEmpty());
        assertEquals(Direction.INBOUND, out.direction());
    }

    private static String file(FileContent fc, String location) {
        return valueAt(fc.fileFields(), location);
    }

    private static String rec(Record record, String location) {
        return valueAt(record.fields(), location);
    }

    private static String valueAt(List<Field> fields, String location) {
        return fields.stream()
                .filter(f -> f.location().name().equals(location))
                .map(Field::value)
                .findFirst()
                .orElse(null);
    }
}
