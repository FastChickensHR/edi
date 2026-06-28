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
     * Appends a new row for an existing integration (full replacement, append-only).
     * Returns empty if no active integration exists for the given id.
     */
    public Optional<IntegrationEntity> update(UUID integrationId, IntegrationRequest request) {
        if (repository.findCurrentById(integrationId).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(repository.save(mapper.toNewEntity(integrationId, request)));
    }

    /**
     * Soft-deletes an integration by appending a tombstone row.
     * Returns false if no active integration exists for the given id.
     */
    public boolean delete(UUID integrationId) {
        return repository.findCurrentById(integrationId)
                .map(current -> {
                    repository.save(mapper.toTombstoneEntity(integrationId, current));
                    return true;
                })
                .orElse(false);
    }
}
