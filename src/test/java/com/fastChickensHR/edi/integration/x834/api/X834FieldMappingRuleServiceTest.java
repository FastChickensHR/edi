/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import com.fastChickensHR.edi.integration.x834.X834MappableField;
import com.fastChickensHR.edi.integration.x834.persistence.X834FieldMappingRuleEntity;
import com.fastChickensHR.edi.integration.x834.persistence.X834FieldMappingRuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class X834FieldMappingRuleServiceTest {

    @Mock
    private X834FieldMappingRuleRepository repository;

    @Mock
    private X834FieldMappingRuleMapper mapper;

    @InjectMocks
    private X834FieldMappingRuleService service;

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    private static X834FieldMappingRuleRequest maintenanceTypeRequest() {
        return new X834FieldMappingRuleRequest(
                "emp_status",
                "MAINTENANCE_TYPE_CODE",
                Map.of("A", "ADDITION", "T", "CANCELLATION")
        );
    }

    private static X834FieldMappingRuleRequest coverageStartRequest() {
        return new X834FieldMappingRuleRequest(
                "benefit_start_dt",
                "COVERAGE_START_DATE",
                Map.of()
        );
    }

    // --- findAllByIntegrationId ---

    @Test
    void findAllByIntegrationId_returnsResultsFromRepository() {
        List<X834FieldMappingRuleEntity> entities = List.of(
                new X834FieldMappingRuleEntity(), new X834FieldMappingRuleEntity());
        when(repository.findAllCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(entities);

        List<X834FieldMappingRuleEntity> result = service.findAllByIntegrationId(INTEGRATION_ID);

        assertEquals(entities, result);
    }

    @Test
    void findAllByIntegrationId_returnsEmptyWhenNoRules() {
        when(repository.findAllCurrentByIntegrationId(INTEGRATION_ID)).thenReturn(List.of());

        assertTrue(service.findAllByIntegrationId(INTEGRATION_ID).isEmpty());
    }

    // --- findByIntegrationIdAndTargetField ---

    @Test
    void findByIntegrationIdAndTargetField_returnsPresentWhenFound() {
        X834FieldMappingRuleEntity entity = new X834FieldMappingRuleEntity();
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.of(entity));

        Optional<X834FieldMappingRuleEntity> result =
                service.findByIntegrationIdAndTargetField(INTEGRATION_ID, X834MappableField.MAINTENANCE_TYPE_CODE);

        assertTrue(result.isPresent());
        assertSame(entity, result.get());
    }

    @Test
    void findByIntegrationIdAndTargetField_returnsEmptyWhenNotFound() {
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.empty());

        assertTrue(service.findByIntegrationIdAndTargetField(
                INTEGRATION_ID, X834MappableField.MAINTENANCE_TYPE_CODE).isEmpty());
    }

    // --- create ---

    @Test
    void create_savesEntityWithIntegrationId() {
        X834FieldMappingRuleRequest request = maintenanceTypeRequest();
        X834FieldMappingRuleEntity mapped = new X834FieldMappingRuleEntity();
        X834FieldMappingRuleEntity saved = new X834FieldMappingRuleEntity();
        when(mapper.toNewEntity(eq(INTEGRATION_ID), eq(request))).thenReturn(mapped);
        when(repository.save(mapped)).thenReturn(saved);

        X834FieldMappingRuleEntity result = service.create(INTEGRATION_ID, request);

        assertSame(saved, result);
        verify(repository).save(mapped);
    }

    // --- update ---

    @Test
    void update_returnsEmptyWhenRuleNotFound() {
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.empty());

        Optional<X834FieldMappingRuleEntity> result = service.update(INTEGRATION_ID, maintenanceTypeRequest());

        assertTrue(result.isEmpty());
    }

    @Test
    void update_closesSysThenInsertsNewRowAndReturnsItWhenFound() {
        X834FieldMappingRuleRequest request = maintenanceTypeRequest();
        X834FieldMappingRuleEntity current = new X834FieldMappingRuleEntity();
        X834FieldMappingRuleEntity updated = new X834FieldMappingRuleEntity();
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.of(current));
        when(mapper.toNewEntity(INTEGRATION_ID, request)).thenReturn(updated);
        when(repository.save(current)).thenReturn(current);
        when(repository.save(updated)).thenReturn(updated);

        Optional<X834FieldMappingRuleEntity> result = service.update(INTEGRATION_ID, request);

        assertTrue(result.isPresent());
        assertSame(updated, result.get());
        verify(mapper).closeSysTo(current);
        verify(repository).save(current);
        verify(repository).save(updated);
    }

    // --- delete ---

    @Test
    void delete_returnsFalseWhenRuleNotFound() {
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.empty());

        assertFalse(service.delete(INTEGRATION_ID, X834MappableField.MAINTENANCE_TYPE_CODE));
    }

    @Test
    void delete_closesValidToAndReturnsTrueWhenFound() {
        X834FieldMappingRuleEntity current = new X834FieldMappingRuleEntity();
        when(repository.findCurrentByIntegrationIdAndTargetField(
                INTEGRATION_ID, "MAINTENANCE_TYPE_CODE")).thenReturn(Optional.of(current));

        assertTrue(service.delete(INTEGRATION_ID, X834MappableField.MAINTENANCE_TYPE_CODE));
        verify(mapper).closeValidTo(current);
        verify(repository).save(current);
    }
}
