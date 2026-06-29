/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final UserId OWNER = UserId.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

    @Test
    void testOutboundDirectionWhenFromSystemIsInternal() {
        Integration integration = Integration.builder()
                .id(INTEGRATION_ID)
                .name("State of Michigan 834")
                .owner(OWNER)
                .fromSystem(InternalSystem.FASTCHICKENS_HR)
                .toSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .format(IntegrationFormat.X12_834)
                .build();

        assertEquals(Direction.OUTBOUND, integration.direction());
    }

    @Test
    void testInboundDirectionWhenFromSystemIsExternal() {
        Integration integration = Integration.builder()
                .id(INTEGRATION_ID)
                .name("Michigan Inbound Acknowledgment")
                .owner(OWNER)
                .fromSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .toSystem(InternalSystem.FASTCHICKENS_HR)
                .format(IntegrationFormat.X12_834)
                .build();

        assertEquals(Direction.INBOUND, integration.direction());
    }

    @Test
    void testBuilderSetsAllFields() {
        Integration integration = Integration.builder()
                .id(INTEGRATION_ID)
                .name("State of Michigan 834")
                .owner(OWNER)
                .fromSystem(InternalSystem.FASTCHICKENS_HR)
                .toSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .format(IntegrationFormat.X12_834)
                .build();

        assertEquals(INTEGRATION_ID, integration.getId());
        assertEquals("State of Michigan 834", integration.getName());
        assertEquals(OWNER, integration.getOwner());
        assertEquals(InternalSystem.FASTCHICKENS_HR, integration.getFromSystem());
        assertEquals(ExternalSystem.STATE_OF_MICHIGAN, integration.getToSystem());
        assertEquals(IntegrationFormat.X12_834, integration.getFormat());
    }

    @Test
    void testImmutability() {
        Integration a = Integration.builder()
                .id(INTEGRATION_ID)
                .name("State of Michigan 834")
                .owner(OWNER)
                .fromSystem(InternalSystem.FASTCHICKENS_HR)
                .toSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .format(IntegrationFormat.X12_834)
                .build();

        Integration b = Integration.builder()
                .id(INTEGRATION_ID)
                .name("State of Michigan 834")
                .owner(OWNER)
                .fromSystem(InternalSystem.FASTCHICKENS_HR)
                .toSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .format(IntegrationFormat.X12_834)
                .build();

        assertEquals(a, b);
    }

    @Test
    void testInternalSystemEnumValues() {
        assertEquals(1, InternalSystem.values().length);
        assertEquals("FastChickensHR", InternalSystem.FASTCHICKENS_HR.getDisplayName());
    }

    @Test
    void testExternalSystemEnumValues() {
        assertEquals(4, ExternalSystem.values().length);
        assertEquals("State of Michigan", ExternalSystem.STATE_OF_MICHIGAN.getDisplayName());
    }

    @Test
    void testIntegrationFormatEnumValues() {
        assertEquals(1, IntegrationFormat.values().length);
        assertEquals("X12 834", IntegrationFormat.X12_834.getDisplayName());
        assertEquals("ANSI X12 834 Benefit Enrollment and Maintenance", IntegrationFormat.X12_834.getDescription());
    }

    @Test
    void testExternalSystemCanBeFromSystem() {
        Integration integration = Integration.builder()
                .id(INTEGRATION_ID)
                .name("Michigan Inbound")
                .owner(OWNER)
                .fromSystem(ExternalSystem.STATE_OF_MICHIGAN)
                .toSystem(InternalSystem.FASTCHICKENS_HR)
                .format(IntegrationFormat.X12_834)
                .build();

        assertInstanceOf(ExternalSystem.class, integration.getFromSystem());
        assertInstanceOf(InternalSystem.class, integration.getToSystem());
    }

    @Test
    void testDirectionEnumValues() {
        assertEquals(2, Direction.values().length);
        assertNotNull(Direction.INBOUND);
        assertNotNull(Direction.OUTBOUND);
    }
}
