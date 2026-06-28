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

    /**
     * Sentinel value representing an open-ended temporal boundary ("forever").
     * Stored as {@code '9999-12-31 23:59:59+00'} in PostgreSQL.
     */
    public static final Instant TEMPORAL_INFINITY = Instant.parse("9999-12-31T23:59:59Z");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "integration_id", nullable = false)
    private UUID integrationId;

    /** System time start — when this row was recorded in the database. */
    @Column(name = "sys_from", nullable = false)
    private Instant sysFrom;

    /** System time end — {@link #TEMPORAL_INFINITY} until superseded by a correction. */
    @Column(name = "sys_to", nullable = false)
    private Instant sysTo;

    /** Valid time start — when this state became effective in the real world. */
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    /** Valid time end — {@link #TEMPORAL_INFINITY} for currently-valid rows; set to deletion time when deleted. */
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

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
