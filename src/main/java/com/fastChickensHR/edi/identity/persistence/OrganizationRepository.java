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

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {

    @Query(value = """
            SELECT * FROM organizations
            WHERE organization_id = :organizationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            ORDER BY valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<OrganizationEntity> findCurrentById(@Param("organizationId") UUID organizationId);

    @Query(value = """
            SELECT * FROM organizations
            WHERE sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            """, nativeQuery = true)
    List<OrganizationEntity> findAllCurrent();
}
