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
    void testDefaultEntityIdentifierCode() throws ValidationException {
        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .build();

        assertEquals("31", address.getNm101());
        assertEquals("31", address.getEntityIdentifierCode());
    }

    @Test
    void testEntityIdentifierCodeEnum() {
        assertEquals("31", MemberResidenceStreetAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getCode());
        assertEquals("Postal Mailing Address", MemberResidenceStreetAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS.getDescription());
    }

    @Test
    void testSetEntityIdentifierCodeUsingEnum() throws ValidationException {
        MemberResidenceStreetAddress address = MemberResidenceStreetAddress.builder()
                .setEntityIdentifierCode(MemberResidenceStreetAddress.EntityIdentifierCode.POSTAL_MAILING_ADDRESS)
                .build();

        assertEquals("31", address.getNm101());
    }
}