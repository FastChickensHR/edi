/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.persistence;

import com.fastChickensHR.edi.common.TemporalConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationEntity {

    /** @see TemporalConstants#TEMPORAL_INFINITY */
    public static final Instant TEMPORAL_INFINITY = TemporalConstants.TEMPORAL_INFINITY;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "sys_from", nullable = false)
    private Instant sysFrom;

    @Column(name = "sys_to", nullable = false)
    private Instant sysTo;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Column(name = "name", nullable = false)
    private String name;
}
