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
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/members")
public class OrganizationMemberController {

    private final OrganizationMemberService service;
    private final OrganizationMemberMapper mapper;

    public OrganizationMemberController(OrganizationMemberService service,
                                        OrganizationMemberMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MemberResponse> findAll(@PathVariable UUID organizationId) {
        return service.findAllByOrganizationId(organizationId)
                .stream().map(mapper::toResponse).toList();
    }

    @PostMapping
    public MemberResponse addMember(
            @PathVariable UUID organizationId,
            @RequestBody AddMemberRequest request) {
        return mapper.toResponse(service.addMember(organizationId, request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MemberResponse> updateRole(
            @PathVariable UUID organizationId,
            @PathVariable UUID userId,
            @RequestBody Map<String, String> body) {
        return service.updateRole(organizationId, userId, body.get("role"))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable UUID organizationId,
            @PathVariable UUID userId) {
        return service.removeMember(organizationId, userId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
