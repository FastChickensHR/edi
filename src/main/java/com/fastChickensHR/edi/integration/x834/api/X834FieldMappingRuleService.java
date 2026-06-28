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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class X834FieldMappingRuleService {

    private final X834FieldMappingRuleRepository repository;
    private final X834FieldMappingRuleMapper mapper;

    @Transactional(readOnly = true)
    public List<X834FieldMappingRuleEntity> findAllByIntegrationId(UUID integrationId) {
        return repository.findAllCurrentByIntegrationId(integrationId);
    }

    @Transactional(readOnly = true)
    public Optional<X834FieldMappingRuleEntity> findByIntegrationIdAndTargetField(
            UUID integrationId, X834MappableField targetEdiField) {
        return repository.findCurrentByIntegrationIdAndTargetField(integrationId, targetEdiField.name());
    }

    public X834FieldMappingRuleEntity create(UUID integrationId, X834FieldMappingRuleRequest request) {
        return repository.save(mapper.toNewEntity(integrationId, request));
    }

    /**
     * Appends a new version of a rule (append-only, bitemporal).
     * Closes the current row for the same target EDI field, then inserts a new row.
     * Returns empty if no currently-valid rule exists for that target field.
     */
    public Optional<X834FieldMappingRuleEntity> update(UUID integrationId, X834FieldMappingRuleRequest request) {
        X834MappableField targetField = X834MappableField.valueOf(request.targetEdiField());
        return repository.findCurrentByIntegrationIdAndTargetField(integrationId, targetField.name())
                .map(current -> {
                    mapper.closeSysTo(current);
                    repository.save(current);
                    return repository.save(mapper.toNewEntity(integrationId, request));
                });
    }

    /**
     * Logically deletes a rule by closing its valid-time period.
     * Returns false if no currently-valid rule exists for that target field.
     */
    public boolean delete(UUID integrationId, X834MappableField targetEdiField) {
        return repository.findCurrentByIntegrationIdAndTargetField(integrationId, targetEdiField.name())
                .map(current -> {
                    mapper.closeValidTo(current);
                    repository.save(current);
                    return true;
                })
                .orElse(false);
    }
}
