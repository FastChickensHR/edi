/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834;

import com.fastChickensHR.edi.integration.IntegrationConfig;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Format-specific configuration for an X12 834 Benefit Enrollment integration.
 *
 * <p>Captures all static fields needed to construct an 834 document for a given
 * client integration — the EDI envelope settings, enrollment context, and header
 * details that are constant across all documents produced by this integration.</p>
 *
 * <p>Dynamic per-document values (interchangeControlNumber, groupControlNumber,
 * documentDate) are not stored here; they are computed at generation time.</p>
 */
@Value
@Builder
public class X834IntegrationConfig implements IntegrationConfig {

    UUID integrationId;

    // --- EDI envelope ---
    String senderID;
    String receiverID;
    ElementSeparator elementSeparator;

    // --- Enrollment context ---
    String policyNumber;
    String memberIdQualifier;

    // --- Header ---
    String referenceIdentification;
    String masterPolicyNumber;
    String planSponsorName;
    String payerName;
    String payerIdentification;
}
