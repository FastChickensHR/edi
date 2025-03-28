/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import com.fastChickensHR.edi.common.EdiEnumLookup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentStatusCodeTest {

    @Test
    void testEnumValues() {
        assertEquals(8, StudentStatusCode.values().length);
        assertEquals("F", StudentStatusCode.FULL_TIME.getCode());
        assertEquals("P", StudentStatusCode.PART_TIME.getCode());
        assertEquals("N", StudentStatusCode.NOT_A_STUDENT.getCode());
        assertEquals("C", StudentStatusCode.CONTINUING_EDUCATION.getCode());
        assertEquals("G", StudentStatusCode.GRADUATED.getCode());
        assertEquals("B", StudentStatusCode.ON_BREAK.getCode());
        assertEquals("L", StudentStatusCode.LEAVE_OF_ABSENCE.getCode());
        assertEquals("U", StudentStatusCode.UNKNOWN.getCode());
    }

    @Test
    void testEnumProperties() {
        assertEquals("Full-time Student", StudentStatusCode.FULL_TIME.getDescription());
        assertEquals("Part-time Student", StudentStatusCode.PART_TIME.getDescription());
        assertEquals("Not a Student", StudentStatusCode.NOT_A_STUDENT.getDescription());
        assertEquals("Continuing Education", StudentStatusCode.CONTINUING_EDUCATION.getDescription());
        assertEquals("Graduated", StudentStatusCode.GRADUATED.getDescription());
        assertEquals("On School Break/Vacation", StudentStatusCode.ON_BREAK.getDescription());
        assertEquals("Leave of Absence", StudentStatusCode.LEAVE_OF_ABSENCE.getDescription());
        assertEquals("Unknown", StudentStatusCode.UNKNOWN.getDescription());
    }

    @Test
    void testFromString() {
        assertEquals(StudentStatusCode.FULL_TIME, StudentStatusCode.fromString("F"));
        assertEquals(StudentStatusCode.PART_TIME, StudentStatusCode.fromString("P"));
        assertEquals(StudentStatusCode.NOT_A_STUDENT, StudentStatusCode.fromString("N"));
        assertEquals(StudentStatusCode.CONTINUING_EDUCATION, StudentStatusCode.fromString("C"));
        assertEquals(StudentStatusCode.GRADUATED, StudentStatusCode.fromString("G"));
        assertEquals(StudentStatusCode.ON_BREAK, StudentStatusCode.fromString("B"));
        assertEquals(StudentStatusCode.LEAVE_OF_ABSENCE, StudentStatusCode.fromString("L"));
        assertEquals(StudentStatusCode.UNKNOWN, StudentStatusCode.fromString("U"));

        assertEquals(StudentStatusCode.FULL_TIME, StudentStatusCode.fromString("f"));
        assertEquals(StudentStatusCode.PART_TIME, StudentStatusCode.fromString("p"));

        assertEquals(StudentStatusCode.FULL_TIME, StudentStatusCode.fromString("Full-time Student"));
        assertEquals(StudentStatusCode.PART_TIME, StudentStatusCode.fromString("Part-time Student"));

        assertEquals(StudentStatusCode.FULL_TIME, StudentStatusCode.fromString("full time"));
        assertEquals(StudentStatusCode.PART_TIME, StudentStatusCode.fromString("parttime"));

        assertThrows(IllegalArgumentException.class, () -> StudentStatusCode.fromString("invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideLookupValues")
    void testAllLookupValues(String input, StudentStatusCode expected) throws Exception {
        Field lookupField = StudentStatusCode.class.getDeclaredField("LOOKUP");
        lookupField.setAccessible(true);
        EdiEnumLookup<StudentStatusCode> lookup =
                (EdiEnumLookup<StudentStatusCode>) lookupField.get(null);

        assertEquals(expected, lookup.fromString(input));
        assertEquals(expected, StudentStatusCode.fromString(input));
    }

    private static Stream<Arguments> provideLookupValues() {
        return Stream.of(
                Arguments.of("F", StudentStatusCode.FULL_TIME),
                Arguments.of("P", StudentStatusCode.PART_TIME),
                Arguments.of("N", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("C", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("G", StudentStatusCode.GRADUATED),
                Arguments.of("B", StudentStatusCode.ON_BREAK),
                Arguments.of("L", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("U", StudentStatusCode.UNKNOWN),

                Arguments.of("FULL_TIME", StudentStatusCode.FULL_TIME),
                Arguments.of("PART_TIME", StudentStatusCode.PART_TIME),
                Arguments.of("NOT_A_STUDENT", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("CONTINUING_EDUCATION", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("GRADUATED", StudentStatusCode.GRADUATED),
                Arguments.of("ON_BREAK", StudentStatusCode.ON_BREAK),
                Arguments.of("LEAVE_OF_ABSENCE", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("UNKNOWN", StudentStatusCode.UNKNOWN),

                Arguments.of("Full-time Student", StudentStatusCode.FULL_TIME),
                Arguments.of("Part-time Student", StudentStatusCode.PART_TIME),
                Arguments.of("Not a Student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("Continuing Education", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("Graduated", StudentStatusCode.GRADUATED),
                Arguments.of("On School Break/Vacation", StudentStatusCode.ON_BREAK),
                Arguments.of("Leave of Absence", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("Unknown", StudentStatusCode.UNKNOWN),

                Arguments.of("f", StudentStatusCode.FULL_TIME),
                Arguments.of("fulltime", StudentStatusCode.FULL_TIME),
                Arguments.of("full time", StudentStatusCode.FULL_TIME),
                Arguments.of("full-time", StudentStatusCode.FULL_TIME),
                Arguments.of("ft", StudentStatusCode.FULL_TIME),
                Arguments.of("full", StudentStatusCode.FULL_TIME),
                Arguments.of("full load", StudentStatusCode.FULL_TIME),
                Arguments.of("full course load", StudentStatusCode.FULL_TIME),
                Arguments.of("regular student", StudentStatusCode.FULL_TIME),

                Arguments.of("p", StudentStatusCode.PART_TIME),
                Arguments.of("parttime", StudentStatusCode.PART_TIME),
                Arguments.of("part time", StudentStatusCode.PART_TIME),
                Arguments.of("part-time", StudentStatusCode.PART_TIME),
                Arguments.of("pt", StudentStatusCode.PART_TIME),
                Arguments.of("partial", StudentStatusCode.PART_TIME),
                Arguments.of("partial load", StudentStatusCode.PART_TIME),
                Arguments.of("reduced schedule", StudentStatusCode.PART_TIME),
                Arguments.of("half time", StudentStatusCode.PART_TIME),

                Arguments.of("n", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("non student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("non-student", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not enrolled", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("no longer enrolled", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not in school", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("no school", StudentStatusCode.NOT_A_STUDENT),
                Arguments.of("not attending", StudentStatusCode.NOT_A_STUDENT),

                Arguments.of("c", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("cont ed", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("continuing ed", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("cont education", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("adult education", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("extension", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("professional development", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("certificate program", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("non-degree", StudentStatusCode.CONTINUING_EDUCATION),
                Arguments.of("non degree", StudentStatusCode.CONTINUING_EDUCATION),

                Arguments.of("g", StudentStatusCode.GRADUATED),
                Arguments.of("grad", StudentStatusCode.GRADUATED),
                Arguments.of("alumni", StudentStatusCode.GRADUATED),
                Arguments.of("alumnus", StudentStatusCode.GRADUATED),
                Arguments.of("alumna", StudentStatusCode.GRADUATED),

                Arguments.of("b", StudentStatusCode.ON_BREAK),
                Arguments.of("break", StudentStatusCode.ON_BREAK),
                Arguments.of("vacation", StudentStatusCode.ON_BREAK),
                Arguments.of("school break", StudentStatusCode.ON_BREAK),
                Arguments.of("holiday", StudentStatusCode.ON_BREAK),

                Arguments.of("l", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("leave", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("loa", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("sabbatical", StudentStatusCode.LEAVE_OF_ABSENCE),
                Arguments.of("temporary leave", StudentStatusCode.LEAVE_OF_ABSENCE),

                Arguments.of("u", StudentStatusCode.UNKNOWN),
                Arguments.of("unk", StudentStatusCode.UNKNOWN),
                Arguments.of("not specified", StudentStatusCode.UNKNOWN),
                Arguments.of("not provided", StudentStatusCode.UNKNOWN),
                Arguments.of("unspecified", StudentStatusCode.UNKNOWN)
        );
    }
}