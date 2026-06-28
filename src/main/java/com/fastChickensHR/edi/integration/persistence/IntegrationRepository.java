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

    /**
     * Returns the currently-valid, currently-known row for a given integration.
     *
     * <p>"Currently known" = sys_to is open (TEMPORAL_INFINITY).
     * "Currently valid" = valid_from ≤ now() < valid_to.</p>
     */
    @Query(value = """
            SELECT * FROM integrations
            WHERE integration_id = :integrationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            ORDER BY valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<IntegrationEntity> findCurrentById(@Param("integrationId") UUID integrationId);

    /**
     * Returns one currently-valid, currently-known row per distinct integration_id.
     *
     * <p>The exclusion constraint ensures at most one such row exists per integration,
     * so the result set contains exactly one entry per active integration.</p>
     */
    @Query(value = """
            SELECT * FROM integrations
            WHERE sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            """, nativeQuery = true)
    List<IntegrationEntity> findAllCurrent();
}
