/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import com.fastChickensHR.edi.identity.persistence.OrganizationEntity;
import com.fastChickensHR.edi.identity.persistence.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    public OrganizationService(OrganizationRepository repository, OrganizationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<OrganizationEntity> findAll() {
        return repository.findAllCurrent();
    }

    public Optional<OrganizationEntity> findById(UUID organizationId) {
        return repository.findCurrentById(organizationId);
    }

    @Transactional
    public OrganizationEntity create(CreateOrganizationRequest request) {
        UUID organizationId = UUID.randomUUID();
        OrganizationEntity entity = mapper.toNewEntity(organizationId, request);
        return repository.save(entity);
    }

    @Transactional
    public Optional<OrganizationEntity> update(UUID organizationId, CreateOrganizationRequest request) {
        return repository.findCurrentById(organizationId).map(current -> {
            mapper.closeSysTo(current);
            repository.save(current);
            OrganizationEntity updated = mapper.toNewEntity(organizationId, request);
            return repository.save(updated);
        });
    }

    @Transactional
    public boolean delete(UUID organizationId) {
        return repository.findCurrentById(organizationId).map(current -> {
            mapper.closeValidTo(current);
            repository.save(current);
            return true;
        }).orElse(false);
    }
}
