/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/integrations/{integrationId}/config")
@RequiredArgsConstructor
public class X834IntegrationConfigController {

    private final X834IntegrationConfigService service;
    private final X834IntegrationConfigMapper mapper;

    @GetMapping
    public ResponseEntity<X834IntegrationConfigResponse> findByIntegrationId(
            @PathVariable UUID integrationId) {
        return service.findByIntegrationId(integrationId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public X834IntegrationConfigResponse create(
            @PathVariable UUID integrationId,
            @RequestBody X834IntegrationConfigRequest request) {
        return mapper.toResponse(service.create(integrationId, request));
    }

    @PutMapping
    public ResponseEntity<X834IntegrationConfigResponse> update(
            @PathVariable UUID integrationId,
            @RequestBody X834IntegrationConfigRequest request) {
        return service.update(integrationId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID integrationId) {
        return service.delete(integrationId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
