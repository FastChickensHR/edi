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
import com.fastChickensHR.edi.integration.x834.X834MappableField;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integrations/{integrationId}/mappings")
@RequiredArgsConstructor
public class X834FieldMappingRuleController {

    private final X834FieldMappingRuleService service;
    private final X834FieldMappingRuleMapper mapper;
    private final IntegrationService integrationService;

    @GetMapping
    public ResponseEntity<List<X834FieldMappingRuleResponse>> findAll(@PathVariable UUID integrationId) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.findAllByIntegrationId(integrationId).stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping("/{targetEdiField}")
    public ResponseEntity<X834FieldMappingRuleResponse> findOne(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.findByIntegrationIdAndTargetField(integrationId, targetEdiField)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public ResponseEntity<X834FieldMappingRuleResponse> create(
            @PathVariable UUID integrationId,
            @RequestBody X834FieldMappingRuleRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.create(integrationId, request)));
    }

    @PutMapping("/{targetEdiField}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN', 'OWNER')")
    public ResponseEntity<X834FieldMappingRuleResponse> update(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField,
            @RequestBody X834FieldMappingRuleRequest request) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.update(integrationId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{targetEdiField}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> delete(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField) {
        AuthenticatedUser caller = SecurityContextHelper.getCurrentUser();
        if (integrationService.findById(integrationId, caller.organizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return service.delete(integrationId, targetEdiField)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
