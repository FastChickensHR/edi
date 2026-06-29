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
import com.fastChickensHR.edi.identity.persistence.OrganizationMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationMemberService {

    private final OrganizationMemberRepository repository;
    private final OrganizationMemberMapper mapper;

    public OrganizationMemberService(OrganizationMemberRepository repository,
                                     OrganizationMemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<OrganizationMemberEntity> findAllByOrganizationId(UUID organizationId) {
        return repository.findAllCurrentByOrganizationId(organizationId);
    }

    @Transactional
    public OrganizationMemberEntity addMember(UUID organizationId, AddMemberRequest request) {
        MemberRole role = MemberRole.valueOf(request.role());
        OrganizationMemberEntity entity = mapper.toNewEntity(organizationId, request.userId(), role);
        return repository.save(entity);
    }

    @Transactional
    public Optional<OrganizationMemberEntity> updateRole(UUID organizationId, UUID userId, String newRole) {
        return repository.findCurrentByOrganizationIdAndUserId(organizationId, userId).map(current -> {
            mapper.closeSysTo(current);
            repository.save(current);
            OrganizationMemberEntity updated = mapper.toNewEntity(
                    organizationId, userId, MemberRole.valueOf(newRole));
            return repository.save(updated);
        });
    }

    @Transactional
    public boolean removeMember(UUID organizationId, UUID userId) {
        return repository.findCurrentByOrganizationIdAndUserId(organizationId, userId).map(current -> {
            mapper.closeValidTo(current);
            repository.save(current);
            return true;
        }).orElse(false);
    }
}
