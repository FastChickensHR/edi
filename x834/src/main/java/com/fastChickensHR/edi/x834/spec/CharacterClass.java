/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import java.util.BitSet;
import java.util.OptionalInt;

/**
 * The two character sets X12 names — the vocabulary a trading partner picks from when it restricts what
 * an interchange's data may contain (X12.6 §3.3.2; HIPAA 005010 TR3 Appendix B.1.1.2.2–B.1.1.2.3).
 *
 * <p>{@link #BASIC} is upper-case letters, digits, space and a short list of punctuation.
 * {@link #EXTENDED} adds lower-case letters, {@code @}, and further punctuation. A partner that says
 * "basic set, plus lower case and {@code @}" is naming {@code BASIC} plus two members of
 * {@code EXTENDED}: consumers seed the named set plus an explicit extra-character list and check each
 * extra with {@code EXTENDED.permits(c)}. There is no free-form character-set configuration here.
 *
 * <p><strong>Delimiters are a separate rule.</strong> Membership in a character set does not make a
 * character safe to put in data: X12 has no escape mechanism, so a character serving as an active
 * delimiter may never occur in a data value (x12.org RFI 2611) — and {@code *} and {@code :}, the usual
 * element and component separators, are members of {@code BASIC}. Enforcement therefore pairs a
 * character-set check with a delimiter check against the interchange's own separators, rather than
 * subtracting one interchange's delimiter choice from these published sets.
 *
 * <p><strong>Scope of {@code EXTENDED}.</strong> Its members are the 005010 TR3 Table B.2 list. That
 * appendix is explicitly a partial transcription of X12.6 §3.3.2, which also admits select
 * language-specific characters (e.g. {@code ü}); those stay out until a trading partner demands them,
 * so every published set is ASCII. Two members — {@code ^} and {@code `} — are transcribed
 * inconsistently across secondary sources; both are included here because the TR3 table lists them,
 * and {@code ^} is in practice excluded by the delimiter rule above (5010 recommends it as the
 * repetition separator).
 */
public enum CharacterClass {

    /** Upper-case letters, digits, space, and {@code ! " & ' ( ) * + , - . / : ; ? =}. */
    BASIC(basicMembers()),

    /** {@link #BASIC} plus lower-case letters and {@code % ~ @ [ ] _ { } \ | < > ^ ` # $}. */
    EXTENDED(basicMembers() + lowerCase() + "%~@[]_{}\\|<>^`#$");

    private static final String BASIC_SPECIALS = "!\"&'()*+,-./:;?=";
    private static final String EXTENDED_ONLY_SPECIALS = "%~@[]_{}\\|<>^`#$";

    private final String characters;
    private final BitSet members;

    CharacterClass(String characters) {
        this.characters = characters;
        this.members = new BitSet(128);
        characters.chars().forEach(members::set);
    }

    private static String basicMembers() {
        return upperCase() + digits() + " " + BASIC_SPECIALS;
    }

    private static String upperCase() {
        return range('A', 'Z');
    }

    private static String lowerCase() {
        return range('a', 'z');
    }

    private static String digits() {
        return range('0', '9');
    }

    private static String range(char from, char to) {
        StringBuilder builder = new StringBuilder(to - from + 1);
        for (char c = from; c <= to; c++) {
            builder.append(c);
        }
        return builder.toString();
    }

    /** Every character in this set, in a stable order: letters, digits, space, then punctuation. */
    public String getCharacters() {
        return characters;
    }

    /** Whether this set contains {@code candidate}. */
    public boolean permits(char candidate) {
        return candidate < 128 && members.get(candidate);
    }

    /** Whether every character of {@code value} is a member of this set. An empty value trivially is. */
    public boolean permitsAll(CharSequence value) {
        return firstViolation(value).isEmpty();
    }

    /**
     * The index of the first character of {@code value} that this set does not contain, or empty when
     * every character is a member. The index — not just the character — so a caller can report where in
     * a long value the offending character sits.
     */
    public OptionalInt firstViolation(CharSequence value) {
        if (value == null) {
            return OptionalInt.empty();
        }
        for (int i = 0; i < value.length(); i++) {
            if (!permits(value.charAt(i))) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /** The characters {@link #EXTENDED} adds to {@link #BASIC}: lower-case letters and its punctuation. */
    public static String extendedOnlyCharacters() {
        return lowerCase() + EXTENDED_ONLY_SPECIALS;
    }
}
