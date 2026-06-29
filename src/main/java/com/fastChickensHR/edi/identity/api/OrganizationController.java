/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService service;
    private final OrganizationMapper mapper;

    public OrganizationController(OrganizationService service, OrganizationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<OrganizationResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> findById(@PathVariable UUID organizationId) {
        return service.findById(organizationId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrganizationResponse create(@RequestBody CreateOrganizationRequest request) {
        return mapper.toResponse(service.create(request));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> update(
            @PathVariable UUID organizationId,
            @RequestBody CreateOrganizationRequest request) {
        return service.update(organizationId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> delete(@PathVariable UUID organizationId) {
        return service.delete(organizationId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
