/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import com.fastChickensHR.edi.integration.persistence.IntegrationEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationMapperTest {

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final UUID OWNER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    private final IntegrationMapper mapper = new IntegrationMapper();

    // --- toNewEntity ---

    @Test
    void toNewEntity_setsAllFieldsCorrectlyForOutboundIntegration() {
        IntegrationRequest request = new IntegrationRequest(
                "State of Michigan 834", OWNER_ID, "FASTCHICKENS_HR", "STATE_OF_MICHIGAN", "X12_834");

        IntegrationEntity entity = mapper.toNewEntity(INTEGRATION_ID, request);

        assertEquals(INTEGRATION_ID, entity.getIntegrationId());
        assertEquals("State of Michigan 834", entity.getName());
        assertEquals(OWNER_ID, entity.getOwnerId());
        assertEquals("INTERNAL", entity.getFromSystemType());
        assertEquals("FASTCHICKENS_HR", entity.getFromSystemValue());
        assertEquals("EXTERNAL", entity.getToSystemType());
        assertEquals("STATE_OF_MICHIGAN", entity.getToSystemValue());
        assertEquals("X12_834", entity.getFormat());
        assertNotNull(entity.getSysFrom());
        assertEquals(IntegrationEntity.TEMPORAL_INFINITY, entity.getSysTo());
        assertNotNull(entity.getValidFrom());
        assertEquals(IntegrationEntity.TEMPORAL_INFINITY, entity.getValidTo());
    }

    @Test
    void toNewEntity_resolvesExternalFromSystem() {
        IntegrationRequest request = new IntegrationRequest(
                "Inbound", OWNER_ID, "STATE_OF_MICHIGAN", "FASTCHICKENS_HR", "X12_834");

        IntegrationEntity entity = mapper.toNewEntity(INTEGRATION_ID, request);

        assertEquals("EXTERNAL", entity.getFromSystemType());
        assertEquals("STATE_OF_MICHIGAN", entity.getFromSystemValue());
        assertEquals("INTERNAL", entity.getToSystemType());
        assertEquals("FASTCHICKENS_HR", entity.getToSystemValue());
    }

    @Test
    void toNewEntity_throwsOnUnknownSystem() {
        IntegrationRequest request = new IntegrationRequest(
                "Bad", OWNER_ID, "UNKNOWN_SYSTEM", "STATE_OF_MICHIGAN", "X12_834");

        assertThrows(IllegalArgumentException.class, () -> mapper.toNewEntity(INTEGRATION_ID, request));
    }

    @Test
    void toNewEntity_throwsOnUnknownFormat() {
        IntegrationRequest request = new IntegrationRequest(
                "Bad", OWNER_ID, "FASTCHICKENS_HR", "STATE_OF_MICHIGAN", "NOT_A_FORMAT");

        assertThrows(IllegalArgumentException.class, () -> mapper.toNewEntity(INTEGRATION_ID, request));
    }

    // --- closeValidTo (delete) ---

    @Test
    void closeValidTo_setsValidToToNowAndLeavesOtherFieldsUnchanged() {
        IntegrationEntity current = buildEntity();
        Instant before = Instant.now();

        mapper.closeValidTo(current);

        assertFalse(current.getValidTo().isBefore(before));
        assertTrue(current.getValidTo().isBefore(Instant.now().plusSeconds(1)));
        assertEquals(INTEGRATION_ID, current.getIntegrationId());
        assertEquals("Test Integration", current.getName());
    }

    // --- toResponse ---

    @Test
    void toResponse_mapsAllFieldsAndDerivesOutboundDirection() {
        IntegrationEntity entity = buildEntity();

        IntegrationResponse response = mapper.toResponse(entity);

        assertEquals(INTEGRATION_ID, response.integrationId());
        assertEquals("Test Integration", response.name());
        assertEquals(OWNER_ID, response.ownerId());
        assertEquals("FASTCHICKENS_HR", response.fromSystem());
        assertEquals("STATE_OF_MICHIGAN", response.toSystem());
        assertEquals("X12_834", response.format());
        assertEquals("OUTBOUND", response.direction());
        assertEquals(entity.getSysFrom(), response.createdAt());
    }

    @Test
    void toResponse_derivesInboundDirectionWhenFromSystemIsExternal() {
        IntegrationEntity entity = buildEntity();
        entity.setFromSystemType("EXTERNAL");
        entity.setFromSystemValue("STATE_OF_MICHIGAN");
        entity.setToSystemType("INTERNAL");
        entity.setToSystemValue("FASTCHICKENS_HR");

        IntegrationResponse response = mapper.toResponse(entity);

        assertEquals("INBOUND", response.direction());
        assertEquals("STATE_OF_MICHIGAN", response.fromSystem());
        assertEquals("FASTCHICKENS_HR", response.toSystem());
    }

    // --- helpers ---

    private IntegrationEntity buildEntity() {
        IntegrationEntity entity = new IntegrationEntity();
        entity.setIntegrationId(INTEGRATION_ID);
        entity.setSysFrom(Instant.now());
        entity.setSysTo(IntegrationEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(Instant.now());
        entity.setValidTo(IntegrationEntity.TEMPORAL_INFINITY);
        entity.setName("Test Integration");
        entity.setOwnerId(OWNER_ID);
        entity.setFromSystemType("INTERNAL");
        entity.setFromSystemValue("FASTCHICKENS_HR");
        entity.setToSystemType("EXTERNAL");
        entity.setToSystemValue("STATE_OF_MICHIGAN");
        entity.setFormat("X12_834");
        return entity;
    }
}
