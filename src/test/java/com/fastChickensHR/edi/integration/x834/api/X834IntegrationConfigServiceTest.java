/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import com.fastChickensHR.edi.integration.x834.persistence.X834IntegrationConfigEntity;
import com.fastChickensHR.edi.integration.x834.persistence.X834IntegrationConfigRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class X834IntegrationConfigServiceTest {

    @Mock
    private X834IntegrationConfigRepository repository;

    @Mock
    private X834IntegrationConfigMapper mapper;

    @InjectMocks
    private X834IntegrationConfigService service;

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    private static X834IntegrationConfigRequest sampleRequest() {
        return new X834IntegrationConfigRequest(
                "FASTCHKN",
                "MICHGVEDI",
                "PIPE",
                "MIPA2023",
                "34",
                "220701MI834",
                "MIHHS-EMP-2023",
                "FASTCHKN",
                "FASTCHKN INSURANCE",
                "123456789"
        );
    }

    // --- findByIntegrationId ---

    @Test
    void findByIntegrationId_returnsPresentWhenFound() {
        X834IntegrationConfigEntity entity = new X834IntegrationConfigEntity();
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.of(entity));

        Optional<X834IntegrationConfigEntity> result = service.findByIntegrationId(INTEGRATION_ID);

        assertTrue(result.isPresent());
        assertSame(entity, result.get());
    }

    @Test
    void findByIntegrationId_returnsEmptyWhenNotFound() {
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.empty());

        assertTrue(service.findByIntegrationId(INTEGRATION_ID).isEmpty());
    }

    // --- create ---

    @Test
    void create_savesEntityWithIntegrationId() {
        X834IntegrationConfigRequest request = sampleRequest();
        X834IntegrationConfigEntity mapped = new X834IntegrationConfigEntity();
        X834IntegrationConfigEntity saved = new X834IntegrationConfigEntity();
        when(mapper.toNewEntity(eq(INTEGRATION_ID), eq(request))).thenReturn(mapped);
        when(repository.save(mapped)).thenReturn(saved);

        X834IntegrationConfigEntity result = service.create(INTEGRATION_ID, request);

        assertSame(saved, result);
        verify(repository).save(mapped);
    }

    // --- update ---

    @Test
    void update_returnsEmptyWhenConfigNotFound() {
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.empty());

        Optional<X834IntegrationConfigEntity> result = service.update(INTEGRATION_ID, sampleRequest());

        assertTrue(result.isEmpty());
    }

    @Test
    void update_closesSysThenInsertsNewRowAndReturnsItWhenFound() {
        X834IntegrationConfigRequest request = sampleRequest();
        X834IntegrationConfigEntity current = new X834IntegrationConfigEntity();
        X834IntegrationConfigEntity updated = new X834IntegrationConfigEntity();
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.of(current));
        when(mapper.toNewEntity(INTEGRATION_ID, request)).thenReturn(updated);
        when(repository.save(current)).thenReturn(current);
        when(repository.save(updated)).thenReturn(updated);

        Optional<X834IntegrationConfigEntity> result = service.update(INTEGRATION_ID, request);

        assertTrue(result.isPresent());
        assertSame(updated, result.get());
        verify(mapper).closeSysTo(current);
        verify(repository).save(current);
        verify(repository).save(updated);
    }

    // --- delete ---

    @Test
    void delete_returnsFalseWhenConfigNotFound() {
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.empty());

        assertFalse(service.delete(INTEGRATION_ID));
    }

    @Test
    void delete_closesValidToAndReturnsTrueWhenFound() {
        X834IntegrationConfigEntity current = new X834IntegrationConfigEntity();
        when(repository.findCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(Optional.of(current));

        assertTrue(service.delete(INTEGRATION_ID));
        verify(mapper).closeValidTo(current);
        verify(repository).save(current);
    }
}
