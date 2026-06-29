/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import com.fastChickensHR.edi.identity.MemberRole;
import com.fastChickensHR.edi.identity.persistence.OrganizationMemberEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrganizationMemberMapper {

    public MemberResponse toResponse(OrganizationMemberEntity entity) {
        return new MemberResponse(
                entity.getOrganizationId(),
                entity.getUserId(),
                entity.getRole(),
                entity.getValidFrom()
        );
    }

    public OrganizationMemberEntity toNewEntity(UUID organizationId, UUID userId, MemberRole role) {
        Instant now = Instant.now();
        OrganizationMemberEntity entity = new OrganizationMemberEntity();
        entity.setOrganizationId(organizationId);
        entity.setUserId(userId);
        entity.setRole(role);
        entity.setSysFrom(now);
        entity.setSysTo(OrganizationMemberEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(OrganizationMemberEntity.TEMPORAL_INFINITY);
        return entity;
    }

    public void closeSysTo(OrganizationMemberEntity entity) {
        entity.setSysTo(Instant.now());
    }

    public void closeValidTo(OrganizationMemberEntity entity) {
        entity.setValidTo(Instant.now());
    }
}
