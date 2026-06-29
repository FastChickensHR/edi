/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import com.fastChickensHR.edi.auth.AuthenticatedUser;
import com.fastChickensHR.edi.auth.SecurityContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integrations")
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService service;
    private final IntegrationMapper mapper;

    @GetMapping
    public List<IntegrationResponse> findAll() {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        return service.findAll(caller.organizationId()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntegrationResponse> findById(@PathVariable UUID id) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        return service.findById(id, caller.organizationId())
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public IntegrationResponse create(@RequestBody IntegrationRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (!caller.organizationId().equals(request.organizationId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Cannot create an integration for a different organization");
        }
        return mapper.toResponse(service.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public ResponseEntity<IntegrationResponse> update(
            @PathVariable UUID id,
            @RequestBody IntegrationRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        return service.update(id, caller.organizationId(), request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        return service.delete(id, caller.organizationId())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
