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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class X834IntegrationConfigService {

    private final X834IntegrationConfigRepository repository;
    private final X834IntegrationConfigMapper mapper;

    @Transactional(readOnly = true)
    public Optional<X834IntegrationConfigEntity> findByIntegrationId(UUID integrationId) {
        return repository.findCurrentByIntegrationId(integrationId);
    }

    public X834IntegrationConfigEntity create(UUID integrationId, X834IntegrationConfigRequest request) {
        return repository.save(mapper.toNewEntity(integrationId, request));
    }

    /**
     * Appends a new config version for an existing integration (append-only, bitemporal).
     * The current row's sys_to is closed first, then a new row is inserted.
     * Returns empty if no currently-valid config exists for the given integration id.
     */
    public Optional<X834IntegrationConfigEntity> update(UUID integrationId, X834IntegrationConfigRequest request) {
        return repository.findCurrentByIntegrationId(integrationId)
                .map(current -> {
                    mapper.closeSysTo(current);
                    repository.save(current);
                    return repository.save(mapper.toNewEntity(integrationId, request));
                });
    }

    /**
     * Logically deletes a config by closing its valid-time period.
     * Returns false if no currently-valid config exists for the given integration id.
     */
    public boolean delete(UUID integrationId) {
        return repository.findCurrentByIntegrationId(integrationId)
                .map(current -> {
                    mapper.closeValidTo(current);
                    repository.save(current);
                    return true;
                })
                .orElse(false);
    }
}
