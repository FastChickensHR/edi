/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import com.fastChickensHR.edi.identity.persistence.UserEntity;
import com.fastChickensHR.edi.identity.persistence.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<UserEntity> findById(UUID userId) {
        return repository.findCurrentById(userId);
    }

    @Transactional
    public UserEntity create(CreateUserRequest request) {
        UUID userId = UUID.randomUUID();
        String passwordHash = passwordEncoder.encode(request.password());
        UserEntity entity = mapper.toNewEntity(userId, request.email(), passwordHash);
        return repository.save(entity);
    }

    @Transactional
    public Optional<UserEntity> updateEmail(UUID userId, String newEmail) {
        return repository.findCurrentById(userId).map(current -> {
            mapper.closeSysTo(current);
            repository.save(current);
            UserEntity updated = mapper.toNewEntity(userId, newEmail, current.getPasswordHash());
            return repository.save(updated);
        });
    }

    @Transactional
    public boolean delete(UUID userId) {
        return repository.findCurrentById(userId).map(current -> {
            mapper.closeValidTo(current);
            repository.save(current);
            return true;
        }).orElse(false);
    }
}
