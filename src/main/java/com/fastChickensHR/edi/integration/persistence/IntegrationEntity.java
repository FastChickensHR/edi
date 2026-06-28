/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "integrations")
@Getter
@Setter
@NoArgsConstructor
public class IntegrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "integration_id", nullable = false)
    private UUID integrationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "from_system_type", nullable = false)
    private String fromSystemType;

    @Column(name = "from_system_value", nullable = false)
    private String fromSystemValue;

    @Column(name = "to_system_type", nullable = false)
    private String toSystemType;

    @Column(name = "to_system_value", nullable = false)
    private String toSystemValue;

    @Column(name = "format", nullable = false)
    private String format;
}
