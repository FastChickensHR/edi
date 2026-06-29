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

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, UUID> {

    /**
     * Hot path: called on every API-key-authenticated request.
     * The partial index {@code idx_api_keys_hash_current} makes this fast.
     */
    @Query(value = """
            SELECT * FROM api_keys
            WHERE key_hash = :keyHash
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            LIMIT 1
            """, nativeQuery = true)
    Optional<ApiKeyEntity> findCurrentByKeyHash(@Param("keyHash") String keyHash);

    @Query(value = """
            SELECT * FROM api_keys
            WHERE organization_id = :organizationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            """, nativeQuery = true)
    List<ApiKeyEntity> findAllCurrentByOrganizationId(
            @Param("organizationId") UUID organizationId);
}
