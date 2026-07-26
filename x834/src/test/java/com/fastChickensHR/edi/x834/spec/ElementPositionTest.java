/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElementPositionTest {

    @Test
    void displaysTheCanonicalSpelling() {
        assertEquals("2000 INS08", ElementPosition.of("2000", "INS", 8).display());
        assertEquals("HEADER BGN01", ElementPosition.of("HEADER", "BGN", 1).display());
        assertEquals("2100A NM109", ElementPosition.of("2100A", "NM1", 9).display());
        assertEquals("2000 INS06-1", ElementPosition.of("2000", "INS", 6, 1).display());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000 INS08", "HEADER BGN01", "2100A NM109", "2300 HD05", "2000 INS06-1", "TRAILER IEA02"})
    void parseRoundTripsTheCanonicalSpelling(String canonical) {
        assertEquals(canonical, ElementPosition.parse(canonical).display());
    }

    @Test
    void takesTheOrdinalFromTheLastTwoDigitsSoSegmentIdsEndingInADigitStillSplit() {
        ElementPosition city = ElementPosition.parse("2100A N401");
        assertEquals("N4", city.segment(), "N401 is N4 element 01, not N40 element 1");
        assertEquals(1, city.ordinal());

        ElementPosition identificationQualifier = ElementPosition.parse("2100A NM108");
        assertEquals("NM1", identificationQualifier.segment());
        assertEquals(8, identificationQualifier.ordinal());

        ElementPosition addressLine = ElementPosition.parse("2100C N302");
        assertEquals("N3", addressLine.segment());
        assertEquals(2, addressLine.ordinal());
    }

    @Test
    void acceptsTheHyphenatedSpellingAndNormalisesIt() {
        assertEquals(ElementPosition.of("2100A", "NM1", 8), ElementPosition.parse("2100A NM1-08"));
        assertEquals("2100A NM108", ElementPosition.parse("2100A NM1-08").display());
    }

    @Test
    void parsesACompositeComponent() {
        ElementPosition medicarePlan = ElementPosition.parse("2000 INS06-1");
        assertEquals("INS", medicarePlan.segment());
        assertEquals(6, medicarePlan.ordinal());
        assertEquals(1, medicarePlan.component());
        assertTrue(medicarePlan.isComponent());
    }

    @Test
    void anOrdinaryPositionHasNoComponent() {
        ElementPosition employmentStatus = ElementPosition.parse("2000 INS08");
        assertEquals(ElementPosition.NO_COMPONENT, employmentStatus.component());
        assertFalse(employmentStatus.isComponent());
    }

    @Test
    void aComponentIsADifferentPositionFromItsElement() {
        assertFalse(ElementPosition.of("2000", "INS", 6, 1).equals(ElementPosition.of("2000", "INS", 6)));
    }

    @Test
    void ignoresSurroundingWhitespace() {
        assertEquals(ElementPosition.of("2000", "INS", 8), ElementPosition.parse("  2000 INS08 "));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "INS08",            // no loop
            "2000 INS",         // no ordinal
            "2000 INS8",        // one-digit ordinal is not the canonical spelling
            "2000 ins08",       // segment ids are upper case
            "2000 INS08-",      // dangling component separator
            "2000 INS08-12",    // component ordinals are a single digit
            "2000 INS08 extra",
            ""})
    void rejectsTextThatIsNotAnElementPosition(String text) {
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.parse(text));
    }

    @Test
    void rejectsNullText() {
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.parse(null));
    }

    @Test
    void rejectsMalformedComponents() {
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "ins", 8));
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "INSXX", 8));
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "INS", 0));
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "INS", 100));
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "INS", 6, 10));
        assertThrows(IllegalArgumentException.class, () -> ElementPosition.of("2000", "INS", 6, -1));
    }
}
