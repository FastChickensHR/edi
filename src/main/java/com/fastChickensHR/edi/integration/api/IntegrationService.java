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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class IntegrationService {

    private final IntegrationRepository repository;
    private final IntegrationMapper mapper;

    @Transactional(readOnly = true)
    public List<IntegrationEntity> findAll() {
        return repository.findAllCurrent();
    }

    @Transactional(readOnly = true)
    public Optional<IntegrationEntity> findById(UUID integrationId) {
        return repository.findCurrentById(integrationId);
    }

    public IntegrationEntity create(IntegrationRequest request) {
        return repository.save(mapper.toNewEntity(UUID.randomUUID(), request));
    }

    /**
     * Appends a new version for an existing integration (append-only, bitemporal).
     * The current row's sys_to is closed first to prevent exclusion-constraint violations,
     * then the new row is inserted with open-ended temporal boundaries.
     * Returns empty if no currently-valid integration exists for the given id.
     */
    public Optional<IntegrationEntity> update(UUID integrationId, IntegrationRequest request) {
        return repository.findCurrentById(integrationId)
                .map(current -> {
                    mapper.closeSysTo(current);
                    repository.save(current);
                    return repository.save(mapper.toNewEntity(integrationId, request));
                });
    }

    /**
     * Logically deletes an integration by closing its valid-time period.
     * Returns false if no currently-valid integration exists for the given id.
     */
    public boolean delete(UUID integrationId) {
        return repository.findCurrentById(integrationId)
                .map(current -> {
                    mapper.closeValidTo(current);
                    repository.save(current);
                    return true;
                })
                .orElse(false);
    }
}
