/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntegrationRepository extends JpaRepository<IntegrationEntity, UUID> {

    @Query("""
            SELECT e FROM IntegrationEntity e
            WHERE e.integrationId = :integrationId AND e.deleted = false
            ORDER BY e.createdAt DESC
            LIMIT 1
            """)
    Optional<IntegrationEntity> findCurrentById(@Param("integrationId") UUID integrationId);

    @Query(value = """
            SELECT * FROM (
                SELECT *, ROW_NUMBER() OVER (PARTITION BY integration_id ORDER BY created_at DESC) AS rn
                FROM integrations
            ) t WHERE t.rn = 1 AND t.is_deleted = false
            """, nativeQuery = true)
    List<IntegrationEntity> findAllCurrent();
}
