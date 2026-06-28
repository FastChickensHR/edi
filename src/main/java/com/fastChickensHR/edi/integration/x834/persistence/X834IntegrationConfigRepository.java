/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface X834IntegrationConfigRepository extends JpaRepository<X834IntegrationConfigEntity, UUID> {

    /**
     * Returns the currently-valid, currently-known config row for a given integration.
     *
     * <p>"Currently known" = sys_to is open (TEMPORAL_INFINITY).
     * "Currently valid" = valid_from ≤ now() < valid_to.</p>
     */
    @Query(value = """
            SELECT * FROM x834_integration_configs
            WHERE integration_id = :integrationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            ORDER BY valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<X834IntegrationConfigEntity> findCurrentByIntegrationId(@Param("integrationId") UUID integrationId);
}
