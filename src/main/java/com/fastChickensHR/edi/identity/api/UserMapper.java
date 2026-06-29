/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import com.fastChickensHR.edi.identity.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class UserMapper {

    public UserResponse toResponse(UserEntity entity) {
        return new UserResponse(
                entity.getUserId(),
                entity.getEmail(),
                entity.getSysFrom()
        );
    }

    public UserEntity toNewEntity(UUID userId, String email, String passwordHash) {
        Instant now = Instant.now();
        UserEntity entity = new UserEntity();
        entity.setUserId(userId);
        entity.setSysFrom(now);
        entity.setSysTo(UserEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(UserEntity.TEMPORAL_INFINITY);
        entity.setEmail(email);
        entity.setPasswordHash(passwordHash);
        return entity;
    }

    public void closeSysTo(UserEntity entity) {
        entity.setSysTo(Instant.now());
    }

    public void closeValidTo(UserEntity entity) {
        entity.setValidTo(Instant.now());
    }
}
