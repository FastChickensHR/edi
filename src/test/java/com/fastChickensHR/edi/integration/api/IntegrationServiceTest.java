/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import com.fastChickensHR.edi.integration.persistence.IntegrationEntity;
import com.fastChickensHR.edi.integration.persistence.IntegrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntegrationServiceTest {

    @Mock
    private IntegrationRepository repository;

    @Mock
    private IntegrationMapper mapper;

    @InjectMocks
    private IntegrationService service;

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final UUID ORGANIZATION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID CREATED_BY_USER_ID = UUID.fromString("660e8400-e29b-41d4-a716-446655440001");

    private static IntegrationRequest sampleRequest() {
        return new IntegrationRequest("Test", ORGANIZATION_ID, CREATED_BY_USER_ID, "FASTCHICKENS_HR", "STATE_OF_MICHIGAN", "X12_834");
    }

    // --- findAll ---

    @Test
    void findAll_returnsResultsFromRepository() {
        List<IntegrationEntity> entities = List.of(new IntegrationEntity(), new IntegrationEntity());
        when(repository.findAllCurrentByOrganizationId(ORGANIZATION_ID)).thenReturn(entities);

        List<IntegrationEntity> result = service.findAll(ORGANIZATION_ID);

        assertEquals(entities, result);
    }

    @Test
    void findAll_returnsEmptyListWhenNoActiveIntegrations() {
        when(repository.findAllCurrentByOrganizationId(ORGANIZATION_ID)).thenReturn(List.of());

        assertTrue(service.findAll(ORGANIZATION_ID).isEmpty());
    }

    // --- findById ---

    @Test
    void findById_returnsPresentWhenFound() {
        IntegrationEntity entity = new IntegrationEntity();
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.of(entity));

        Optional<IntegrationEntity> result = service.findById(INTEGRATION_ID, ORGANIZATION_ID);

        assertTrue(result.isPresent());
        assertSame(entity, result.get());
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.empty());

        assertTrue(service.findById(INTEGRATION_ID, ORGANIZATION_ID).isEmpty());
    }

    // --- create ---

    @Test
    void create_savesEntityWithNewlyGeneratedId() {
        IntegrationRequest request = sampleRequest();
        IntegrationEntity mapped = new IntegrationEntity();
        IntegrationEntity saved = new IntegrationEntity();
        when(mapper.toNewEntity(any(UUID.class), eq(request))).thenReturn(mapped);
        when(repository.save(mapped)).thenReturn(saved);

        IntegrationEntity result = service.create(request);

        assertSame(saved, result);
        verify(repository).save(mapped);
    }

    // --- update ---

    @Test
    void update_returnsEmptyWhenIntegrationNotFound() {
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.empty());

        Optional<IntegrationEntity> result = service.update(INTEGRATION_ID, ORGANIZATION_ID, sampleRequest());

        assertTrue(result.isEmpty());
    }

    @Test
    void update_closesSysThenInsertsNewRowAndReturnsItWhenFound() {
        IntegrationRequest request = sampleRequest();
        IntegrationEntity current = new IntegrationEntity();
        IntegrationEntity updated = new IntegrationEntity();
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.of(current));
        when(mapper.toNewEntity(INTEGRATION_ID, request)).thenReturn(updated);
        when(repository.save(current)).thenReturn(current);
        when(repository.save(updated)).thenReturn(updated);

        Optional<IntegrationEntity> result = service.update(INTEGRATION_ID, ORGANIZATION_ID, request);

        assertTrue(result.isPresent());
        assertSame(updated, result.get());
        verify(mapper).closeSysTo(current);
        verify(repository).save(current);
        verify(repository).save(updated);
    }

    // --- delete ---

    @Test
    void delete_returnsFalseWhenIntegrationNotFound() {
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.empty());

        assertFalse(service.delete(INTEGRATION_ID, ORGANIZATION_ID));
    }

    @Test
    void delete_closesValidToAndReturnsTrueWhenFound() {
        IntegrationEntity current = new IntegrationEntity();
        when(repository.findCurrentByIdAndOrganizationId(INTEGRATION_ID, ORGANIZATION_ID))
                .thenReturn(Optional.of(current));

        assertTrue(service.delete(INTEGRATION_ID, ORGANIZATION_ID));
        verify(mapper).closeValidTo(current);
        verify(repository).save(current);
    }
}
