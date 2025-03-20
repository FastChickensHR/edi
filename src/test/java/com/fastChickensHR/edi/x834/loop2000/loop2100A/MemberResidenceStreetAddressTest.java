/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberResidenceStreetAddressTest {

    @Test
    void testCreateWithAddressLine1Only() throws ValidationException {
        String addressLine1 = "123 Main Street";

        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setAddressLine1(addressLine1)
                .build();

        assertEquals(addressLine1, address.getAddressLine1());
        assertEquals(addressLine1, address.getN301());
        assertNull(address.getAddressLine2());
        assertNull(address.getN302());
    }

    @Test
    void testCreateWithBothAddressLines() throws ValidationException {
        String addressLine1 = "123 Main Street";
        String addressLine2 = "Apt 4B";

        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        assertEquals(addressLine1, address.getAddressLine1());
        assertEquals(addressLine2, address.getAddressLine2());
        assertEquals(addressLine1, address.getN301());
        assertEquals(addressLine2, address.getN302());
    }

    @Test
    void testCreateWithDirectN3ElementSetters() throws ValidationException {
        String addressLine1 = "123 Main Street";
        String addressLine2 = "Apt 4B";

        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setN301(addressLine1)
                .setN302(addressLine2)
                .build();

        assertEquals(addressLine1, address.getAddressLine1());
        assertEquals(addressLine2, address.getAddressLine2());
    }

    @Test
    void testSegmentIdentifier() throws ValidationException {
        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setAddressLine1("123 Main Street")
                .build();

        assertEquals("N3", address.getSegmentIdentifier());
    }

    @Test
    void testElementValues() throws ValidationException {
        String addressLine1 = "123 Main Street";
        String addressLine2 = "Apt 4B";

        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .build();

        String[] elements = address.getElementValues();
        assertEquals(addressLine1, elements[0]);
        assertEquals(addressLine2, elements[1]);
        assertEquals(2, elements.length);
    }
}