/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMemberEntity, UUID> {

    @Query(value = """
            SELECT * FROM organization_members
            WHERE organization_id = :organizationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            """, nativeQuery = true)
    List<OrganizationMemberEntity> findAllCurrentByOrganizationId(
            @Param("organizationId") UUID organizationId);

    @Query(value = """
            SELECT * FROM organization_members
            WHERE user_id = :userId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            """, nativeQuery = true)
    List<OrganizationMemberEntity> findAllCurrentByUserId(
            @Param("userId") UUID userId);

    @Query(value = """
            SELECT * FROM organization_members
            WHERE organization_id = :organizationId
              AND user_id = :userId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            LIMIT 1
            """, nativeQuery = true)
    Optional<OrganizationMemberEntity> findCurrentByOrganizationIdAndUserId(
            @Param("organizationId") UUID organizationId,
            @Param("userId") UUID userId);
}
