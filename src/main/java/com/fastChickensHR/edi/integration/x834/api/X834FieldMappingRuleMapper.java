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
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
public class X834FieldMappingRuleMapper {

    /**
     * Converts a persisted entity row to a response DTO.
     */
    public X834FieldMappingRuleResponse toResponse(X834FieldMappingRuleEntity entity) {
        return new X834FieldMappingRuleResponse(
                entity.getIntegrationId(),
                entity.getSourceField(),
                entity.getTargetEdiField(),
                entity.getValueMappings(),
                entity.getSysFrom()
        );
    }

    /**
     * Builds a new (open-ended) entity row for a create or update request.
     * Validates {@code targetEdiField} eagerly against {@link X834MappableField}.
     */
    public X834FieldMappingRuleEntity toNewEntity(UUID integrationId, X834FieldMappingRuleRequest request) {
        X834MappableField.valueOf(request.targetEdiField());

        Instant now = Instant.now();
        X834FieldMappingRuleEntity entity = new X834FieldMappingRuleEntity();
        entity.setIntegrationId(integrationId);
        entity.setSysFrom(now);
        entity.setSysTo(X834FieldMappingRuleEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(X834FieldMappingRuleEntity.TEMPORAL_INFINITY);
        entity.setSourceField(request.sourceField());
        entity.setTargetEdiField(request.targetEdiField());
        entity.setValueMappings(request.valueMappings() != null ? request.valueMappings() : Map.of());
        return entity;
    }

    /**
     * Closes the system-time period of a row, marking it as superseded.
     */
    public void closeSysTo(X834FieldMappingRuleEntity entity) {
        entity.setSysTo(Instant.now());
    }

    /**
     * Closes the valid-time period of a row, marking it as logically deleted.
     */
    public void closeValidTo(X834FieldMappingRuleEntity entity) {
        entity.setValidTo(Instant.now());
    }
}
