/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import com.fastChickensHR.edi.identity.persistence.OrganizationEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrganizationMapper {

    public OrganizationResponse toResponse(OrganizationEntity entity) {
        return new OrganizationResponse(
                entity.getOrganizationId(),
                entity.getName(),
                entity.getSysFrom()
        );
    }

    public OrganizationEntity toNewEntity(UUID organizationId, CreateOrganizationRequest request) {
        Instant now = Instant.now();
        OrganizationEntity entity = new OrganizationEntity();
        entity.setOrganizationId(organizationId);
        entity.setSysFrom(now);
        entity.setSysTo(OrganizationEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(OrganizationEntity.TEMPORAL_INFINITY);
        entity.setName(request.name());
        return entity;
    }

    public void closeSysTo(OrganizationEntity entity) {
        entity.setSysTo(Instant.now());
    }

    public void closeValidTo(OrganizationEntity entity) {
        entity.setValidTo(Instant.now());
    }
}
