/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import com.fastChickensHR.edi.auth.AuthenticatedUser;
import com.fastChickensHR.edi.auth.SecurityContextHelper;
import com.fastChickensHR.edi.integration.api.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/integrations/{integrationId}/config")
@RequiredArgsConstructor
public class X834IntegrationConfigController {

    private final X834IntegrationConfigService service;
    private final X834IntegrationConfigMapper mapper;
    private final IntegrationService integrationService;

    @GetMapping
    public ResponseEntity<X834IntegrationConfigResponse> findByIntegrationId(
            @PathVariable UUID integrationId) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.findByIntegrationId(integrationId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public ResponseEntity<X834IntegrationConfigResponse> create(
            @PathVariable UUID integrationId,
            @RequestBody X834IntegrationConfigRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.create(integrationId, request)));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public ResponseEntity<X834IntegrationConfigResponse> update(
            @PathVariable UUID integrationId,
            @RequestBody X834IntegrationConfigRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.update(integrationId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> delete(@PathVariable UUID integrationId) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.delete(integrationId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
