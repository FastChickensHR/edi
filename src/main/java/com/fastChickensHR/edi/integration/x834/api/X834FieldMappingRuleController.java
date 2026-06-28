/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import com.fastChickensHR.edi.integration.x834.X834MappableField;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integrations/{integrationId}/mappings")
@RequiredArgsConstructor
public class X834FieldMappingRuleController {

    private final X834FieldMappingRuleService service;
    private final X834FieldMappingRuleMapper mapper;

    @GetMapping
    public List<X834FieldMappingRuleResponse> findAll(@PathVariable UUID integrationId) {
        return service.findAllByIntegrationId(integrationId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{targetEdiField}")
    public ResponseEntity<X834FieldMappingRuleResponse> findOne(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField) {
        return service.findByIntegrationIdAndTargetField(integrationId, targetEdiField)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public X834FieldMappingRuleResponse create(
            @PathVariable UUID integrationId,
            @RequestBody X834FieldMappingRuleRequest request) {
        return mapper.toResponse(service.create(integrationId, request));
    }

    @PutMapping("/{targetEdiField}")
    public ResponseEntity<X834FieldMappingRuleResponse> update(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField,
            @RequestBody X834FieldMappingRuleRequest request) {
        return service.update(integrationId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{targetEdiField}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID integrationId,
            @PathVariable X834MappableField targetEdiField) {
        return service.delete(integrationId, targetEdiField)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
