/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.persistence;

import com.fastChickensHR.edi.integration.persistence.IntegrationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "x834_integration_configs")
@Getter
@Setter
@NoArgsConstructor
public class X834IntegrationConfigEntity {

    /**
     * Sentinel value representing an open-ended temporal boundary ("forever").
     * Matches {@link IntegrationEntity#TEMPORAL_INFINITY}.
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

    /** Valid time start — when this configuration became effective. */
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    /** Valid time end — {@link #TEMPORAL_INFINITY} for currently-valid rows. */
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    // --- EDI envelope ---

    @Column(name = "sender_id", nullable = false)
    private String senderID;

    @Column(name = "receiver_id", nullable = false)
    private String receiverID;

    /** Stored as enum name, e.g. {@code "PIPE"}, {@code "ASTERISK"}. */
    @Column(name = "element_separator", nullable = false)
    private String elementSeparator;

    // --- Enrollment context ---

    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @Column(name = "member_id_qualifier", nullable = false)
    private String memberIdQualifier;

    // --- Header ---

    @Column(name = "reference_identification")
    private String referenceIdentification;

    @Column(name = "master_policy_number", nullable = false)
    private String masterPolicyNumber;

    @Column(name = "plan_sponsor_name", nullable = false)
    private String planSponsorName;

    @Column(name = "payer_name", nullable = false)
    private String payerName;

    @Column(name = "payer_identification", nullable = false)
    private String payerIdentification;
}
