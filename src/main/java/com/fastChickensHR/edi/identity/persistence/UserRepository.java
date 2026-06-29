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

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = """
            SELECT * FROM users
            WHERE user_id = :userId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            ORDER BY valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<UserEntity> findCurrentById(@Param("userId") UUID userId);

    @Query(value = """
            SELECT * FROM users
            WHERE email = :email
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            LIMIT 1
            """, nativeQuery = true)
    Optional<UserEntity> findCurrentByEmail(@Param("email") String email);
}
