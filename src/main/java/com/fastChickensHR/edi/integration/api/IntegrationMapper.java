/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import com.fastChickensHR.edi.integration.*;
import com.fastChickensHR.edi.integration.persistence.IntegrationEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class IntegrationMapper {

    /**
     * Converts a persisted entity row to a response DTO.
     * Uses the domain object solely to compute the derived {@code direction}.
     */
    public IntegrationResponse toResponse(IntegrationEntity entity) {
        Integration domain = toDomain(entity);
        return new IntegrationResponse(
                entity.getIntegrationId(),
                entity.getName(),
                entity.getOrganizationId(),
                entity.getCreatedByUserId(),
                entity.getFromSystemValue(),
                entity.getToSystemValue(),
                entity.getFormat(),
                domain.direction().name(),
                entity.getSysFrom()
        );
    }

    /**
     * Builds a new (open-ended) entity row for a create or update request.
     * All four temporal fields are set: sys_from and valid_from to now(),
     * sys_to and valid_to to {@link IntegrationEntity#TEMPORAL_INFINITY}.
     */
    public IntegrationEntity toNewEntity(UUID integrationId, IntegrationRequest request) {
        Partner fromSystem = resolvePartnerFromValue(request.fromSystem());
        Partner toSystem = resolvePartnerFromValue(request.toSystem());

        Instant now = Instant.now();
        IntegrationEntity entity = new IntegrationEntity();
        entity.setIntegrationId(integrationId);
        entity.setSysFrom(now);
        entity.setSysTo(IntegrationEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(IntegrationEntity.TEMPORAL_INFINITY);
        entity.setName(request.name());
        entity.setOrganizationId(request.organizationId());
        entity.setCreatedByUserId(request.createdByUserId());
        entity.setFromSystemType(systemType(fromSystem));
        entity.setFromSystemValue(enumName(fromSystem));
        entity.setToSystemType(systemType(toSystem));
        entity.setToSystemValue(enumName(toSystem));
        entity.setFormat(IntegrationFormat.valueOf(request.format()).name());
        return entity;
    }

    /**
     * Closes the system-time period of a row, marking it as superseded.
     * Called before inserting an updated version of the same integration.
     */
    public void closeSysTo(IntegrationEntity entity) {
        entity.setSysTo(Instant.now());
    }

    /**
     * Closes the valid-time period of a row, marking it as logically deleted.
     */
    public void closeValidTo(IntegrationEntity entity) {
        entity.setValidTo(Instant.now());
    }

    // --- private helpers ---

    private Integration toDomain(IntegrationEntity entity) {
        return Integration.builder()
                .id(entity.getIntegrationId())
                .name(entity.getName())
                .owner(UserId.of(entity.getCreatedByUserId()))
                .fromSystem(resolvePartner(entity.getFromSystemType(), entity.getFromSystemValue()))
                .toSystem(resolvePartner(entity.getToSystemType(), entity.getToSystemValue()))
                .format(IntegrationFormat.valueOf(entity.getFormat()))
                .build();
    }

    /**
     * Resolves a flat value string to a Partner by trying InternalSystem first,
     * then ExternalSystem.
     */
    private Partner resolvePartnerFromValue(String value) {
        try {
            return InternalSystem.valueOf(value);
        } catch (IllegalArgumentException e) {
            return ExternalSystem.valueOf(value);
        }
    }

    private Partner resolvePartner(String type, String value) {
        return switch (type) {
            case "INTERNAL" -> InternalSystem.valueOf(value);
            case "EXTERNAL" -> ExternalSystem.valueOf(value);
            default -> throw new IllegalArgumentException("Unknown system type: " + type);
        };
    }

    private String systemType(Partner partner) {
        if (partner instanceof InternalSystem) return "INTERNAL";
        if (partner instanceof ExternalSystem) return "EXTERNAL";
        throw new IllegalArgumentException("Unknown partner type: " + partner);
    }

    private String enumName(Partner partner) {
        if (partner instanceof Enum<?> e) {
            return e.name();
        }
        throw new IllegalArgumentException("Partner must be an enum: " + partner);
    }
}
