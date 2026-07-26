/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterClassTest {

    @Test
    void basicIsUpperCaseDigitsSpaceAndItsPunctuation() {
        assertTrue(CharacterClass.BASIC.permitsAll("ACME CORP 123"));
        assertTrue(CharacterClass.BASIC.permitsAll("O'BRIEN-SMITH (JR.)"));
        assertTrue(CharacterClass.BASIC.permitsAll("A&B, C/D: E?F=G;H!I\"J+K"));
    }

    @Test
    void basicExcludesLowerCaseAndTheAtSign() {
        assertFalse(CharacterClass.BASIC.permits('a'));
        assertFalse(CharacterClass.BASIC.permits('@'));
        assertFalse(CharacterClass.BASIC.permitsAll("Acme Corp"));
    }

    @Test
    void extendedAddsLowerCaseAndTheAtSign() {
        assertTrue(CharacterClass.EXTENDED.permits('a'));
        assertTrue(CharacterClass.EXTENDED.permits('@'));
        assertTrue(CharacterClass.EXTENDED.permitsAll("jane.doe@example.com"));
    }

    @Test
    void extendedIsASupersetOfBasic() {
        for (char c : CharacterClass.BASIC.getCharacters().toCharArray()) {
            assertTrue(CharacterClass.EXTENDED.permits(c), "EXTENDED should contain the BASIC member: " + c);
        }
    }

    @Test
    void theExtrasAPartnerMayNameAreExactlyExtendedsAdditions() {
        // A partner that says "basic, plus lower case and @" names members of EXTENDED, not arbitrary
        // characters: that is the check a consumer runs over a seeded extra-character list.
        for (char c : CharacterClass.extendedOnlyCharacters().toCharArray()) {
            assertTrue(CharacterClass.EXTENDED.permits(c), "EXTENDED should contain its own addition: " + c);
            assertFalse(CharacterClass.BASIC.permits(c), "BASIC should not contain an EXTENDED-only character: " + c);
        }
    }

    @Test
    void neitherSetAdmitsNonAsciiOrControlCharacters() {
        for (CharacterClass characterClass : CharacterClass.values()) {
            assertFalse(characterClass.permits('ü'), characterClass + " should not admit language characters yet");
            assertFalse(characterClass.permits('\t'), characterClass + " should not admit control characters");
            assertFalse(characterClass.permits('\n'), characterClass + " should not admit control characters");
        }
    }

    @Test
    void separatorCharactersAreSetMembersBecauseDelimiterSafetyIsASeparateRule() {
        // '*' and ':' are BASIC members even though they are the usual element and component separators.
        // Membership therefore cannot be the delimiter check: a value carrying an active delimiter is
        // illegal regardless of which set the interchange picked.
        assertTrue(CharacterClass.BASIC.permits('*'));
        assertTrue(CharacterClass.BASIC.permits(':'));
        assertTrue(CharacterClass.EXTENDED.permits('~'));
    }

    @Test
    void firstViolationPointsAtTheOffendingIndex() {
        assertEquals(OptionalInt.of(4), CharacterClass.BASIC.firstViolation("ACME_CORP"));
        assertEquals(OptionalInt.empty(), CharacterClass.BASIC.firstViolation("ACME CORP"));
        assertEquals(OptionalInt.empty(), CharacterClass.BASIC.firstViolation(""));
    }

    @Test
    void anAbsentValueViolatesNothing() {
        assertTrue(CharacterClass.BASIC.permitsAll(null));
    }
}
