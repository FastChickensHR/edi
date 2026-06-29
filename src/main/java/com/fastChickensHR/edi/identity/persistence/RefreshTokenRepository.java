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

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    /**
     * Hot path: called on every token refresh request.
     * The partial index {@code idx_refresh_tokens_hash_current} makes this fast.
     */
    @Query(value = """
            SELECT * FROM refresh_tokens
            WHERE token_hash = :tokenHash
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
              AND expires_at >  now()
            LIMIT 1
            """, nativeQuery = true)
    Optional<RefreshTokenEntity> findCurrentByTokenHash(@Param("tokenHash") String tokenHash);
}
