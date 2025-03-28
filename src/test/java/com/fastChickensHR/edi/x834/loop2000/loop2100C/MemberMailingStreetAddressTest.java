/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberMailingStreetAddressTest {

    @Test
    void testValidAddress() throws ValidationException {
        MemberMailingStreetAddress address = MemberMailingStreetAddress.builder()
                .setAddressLine1("123 Main Street")
                .setAddressLine2("Apt 4B")
                .build();

        assertEquals("123 Main Street", address.getAddressLine1());
        assertEquals("Apt 4B", address.getAddressLine2());
        assertEquals("N3", address.getSegmentIdentifier());
    }

    @Test
    void testAddressWithoutLine2() throws ValidationException {
        MemberMailingStreetAddress address = MemberMailingStreetAddress.builder()
                .setAddressLine1("123 Main Street")
                .build();

        assertEquals("123 Main Street", address.getAddressLine1());
        assertNull(address.getAddressLine2());
    }

    @Test
    void testValidationWithEmptyLine1() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberMailingStreetAddress.builder()
                    .setAddressLine1("")
                    .setAddressLine2("Apt 4B")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Address Line 1"));
    }

    @Test
    void testValidationWithNullLine1() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            MemberMailingStreetAddress.builder()
                    .setAddressLine2("Apt 4B")
                    .build();
        });

        assertTrue(exception.getMessage().contains("Address Line 1"));
    }

    @Test
    void testDirectElementAccess() throws ValidationException {
        MemberMailingStreetAddress address = MemberMailingStreetAddress.builder()
                .setN301("123 Main Street")
                .setN302("Apt 4B")
                .build();

        assertEquals("123 Main Street", address.getN301());
        assertEquals("Apt 4B", address.getN302());

        // Test array access
        String[] elements = address.getElementValues();
        assertEquals(2, elements.length);
        assertEquals("123 Main Street", elements[0]);
        assertEquals("Apt 4B", elements[1]);
    }

    @Test
    void testMixedSetters() throws ValidationException {
        MemberMailingStreetAddress address = MemberMailingStreetAddress.builder()
                .setAddressLine1("123 Main Street")
                .setN302("Apt 4B")
                .build();

        assertEquals("123 Main Street", address.getAddressLine1());
        assertEquals("Apt 4B", address.getAddressLine2());
    }

    @Test
    void testEquivalentSetters() throws ValidationException {
        MemberMailingStreetAddress address1 = MemberMailingStreetAddress.builder()
                .setAddressLine1("123 Main Street")
                .setAddressLine2("Apt 4B")
                .build();

        MemberMailingStreetAddress address2 = MemberMailingStreetAddress.builder()
                .setN301("123 Main Street")
                .setN302("Apt 4B")
                .build();

        assertEquals(address1.getN301(), address2.getN301());
        assertEquals(address1.getN302(), address2.getN302());
    }
}