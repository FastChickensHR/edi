/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The registration-tier contract of {@link EdiEnumLookup} (#157). Normalization is lossy — lowercase
 * with {@code _}, space and {@code -} stripped — so distinct identifiers routinely collide. These
 * examples pin which one wins, using purpose-built fixture enums rather than a production code list
 * so the contract stays legible when a real enum's codes change.
 */
class EdiEnumLookupTest {

    /**
     * A constant is always reachable by its own enum name, even when a later alias normalizes to the
     * same key. This is the shape of the three shadowing bugs #157 was filed for.
     */
    @Test
    void anAliasNeverShadowsAConstantsOwnName() {
        EdiEnumLookup<Purpose> lookup = new EdiEnumLookup<>(Purpose.class, "Purpose",
                Map.of("status update", Purpose.STATUS));

        assertEquals(Purpose.STATUS_UPDATE, lookup.fromString("STATUS_UPDATE"));
        assertEquals(Purpose.STATUS_UPDATE, lookup.fromString("status update"));
        assertEquals(Purpose.STATUS, lookup.fromString("ST"));
    }

    /** Nor a constant's own description — descriptions are registered before aliases. */
    @Test
    void anAliasNeverShadowsAConstantsOwnDescription() {
        EdiEnumLookup<Purpose> lookup = new EdiEnumLookup<>(Purpose.class, "Purpose",
                Map.of("employment end", Purpose.STATUS));

        assertEquals(Purpose.EMPLOYMENT_STOP, lookup.fromString("Employment End"));
        assertEquals(Purpose.EMPLOYMENT_STOP, lookup.fromString("employment end"));
    }

    /** An alias whose key is free still resolves — the tiering only blocks displacement. */
    @Test
    void aFreeKeyedAliasStillResolves() {
        EdiEnumLookup<Purpose> lookup = new EdiEnumLookup<>(Purpose.class, "Purpose",
                Map.of("status check", Purpose.STATUS));

        assertEquals(Purpose.STATUS, lookup.fromString("status check"));
    }

    /**
     * Two constants may share one description once normalized — X12 code lists are transcribed
     * verbatim, so "Long-term Care" and "Long Term Care" legitimately coexist. The first in
     * declaration order claims the key; the other stays reachable by its code and name.
     */
    @Test
    void aSharedDescriptionLeavesBothConstantsReachableByCodeAndName() {
        EdiEnumLookup<Care> lookup = new EdiEnumLookup<>(Care.class, "Care", null);

        assertEquals(Care.LONG_TERM_CARE, lookup.fromString("Long-term Care"));
        assertEquals(Care.LONG_TERM_CARE, lookup.fromString("Long Term Care"));

        assertEquals(Care.LONG_TERM_CARE_LTC, lookup.fromString("LTC"));
        assertEquals(Care.LONG_TERM_CARE_LTC, lookup.fromString("LONG_TERM_CARE_LTC"));
        assertEquals(Care.LONG_TERM_CARE, lookup.fromString("AJ"));
    }

    /**
     * Several constants may carry one X12 code — an enum that names member-level synonyms over a
     * shared code list emits identical bytes either way, so the first wins and both keep their names.
     */
    @Test
    void constantsMayShareOneCodeAndStayReachableByName() {
        EdiEnumLookup<Dates> lookup = new EdiEnumLookup<>(Dates.class, "Dates", null);

        assertEquals(Dates.EFFECTIVE, lookup.fromString("303"));
        assertEquals(Dates.EFFECTIVE, lookup.fromString("EFFECTIVE"));
        assertEquals(Dates.MAINTENANCE_EFFECTIVE, lookup.fromString("MAINTENANCE_EFFECTIVE"));
    }

    /** Two constants whose names normalize alike would leave one unreachable, so construction fails. */
    @Test
    void collidingNamesFailFast() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new EdiEnumLookup<>(CollidingNames.class, "Colliding Names", null));

        assertTrue(thrown.getMessage().contains("unreachable"), thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Colliding Names"), thrown.getMessage());
    }

    /** A code resolving to a constant that carries a different code is real ambiguity, not a synonym. */
    @Test
    void aCodeThatWouldResolveToADifferentlyCodedConstantFailsFast() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new EdiEnumLookup<>(CodeShadowsName.class, "Code Shadows Name", null));

        assertTrue(thrown.getMessage().contains("unreachable"), thrown.getMessage());
    }

    /** Unknown, null and blank input are rejected rather than silently defaulted. */
    @Test
    void rejectsUnknownInput() {
        EdiEnumLookup<Purpose> lookup = new EdiEnumLookup<>(Purpose.class, "Purpose", null);

        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("not-a-real-value"));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> lookup.fromString("   "));
        assertNotNull(lookup.fromString("ST"));
    }

    private enum Purpose implements EdiCodeEnum {
        STATUS("ST", "Status"),
        STATUS_UPDATE("SU", "Status Update"),
        EMPLOYMENT_STOP("337", "Employment End");

        private final String code;
        private final String description;

        Purpose(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private enum Care implements EdiCodeEnum {
        LONG_TERM_CARE("AJ", "Long-term Care"),
        LONG_TERM_CARE_LTC("LTC", "Long Term Care");

        private final String code;
        private final String description;

        Care(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /** Both constants deliberately carry code {@code 303}, as a synonym layer over a shared code list. */
    private enum Dates implements EdiCodeEnum {
        EFFECTIVE("303", "Maintenance Effective"),
        MAINTENANCE_EFFECTIVE("303", "Maintenance Effective");

        private final String code;
        private final String description;

        Dates(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /** {@code ZIP_CODE} and {@code ZIPCODE} both normalize to {@code zipcode}. */
    private enum CollidingNames implements EdiCodeEnum {
        ZIP_CODE("AA", "Zip"),
        ZIPCODE("BB", "Postal");

        private final String code;
        private final String description;

        CollidingNames(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /** {@code SU}'s code is claimed by the constant literally named {@code SU}, which carries {@code XX}. */
    private enum CodeShadowsName implements EdiCodeEnum {
        SU("XX", "Named like another's code"),
        STATUS_UPDATE("SU", "Status Update");

        private final String code;
        private final String description;

        CodeShadowsName(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
