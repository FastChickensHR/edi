/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100C;

import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberMailingAddressTest {

    @Test
    void testDefaultEntityIdentifierCode() throws ValidationException {
        MemberMailingAddress address = MemberMailingAddress.builder()
                .build();

        assertEquals("31", address.getNm101());
        assertEquals("31", address.getEntityIdentifierCode());
    }

    @Test
    void testEntityIdentifierCodeEnum() {
        assertEquals("31", MemberMailingAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getCode());
        assertEquals("Postal Mailing Address", MemberMailingAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getDescription());
    }

    @Test
    void testSetEntityIdentifierCodeUsingEnum() throws ValidationException {
        MemberMailingAddress address = MemberMailingAddress.builder()
                .setEntityIdentifierCode(MemberMailingAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS)
                .build();

        assertEquals("31", address.getNm101());
    }
}