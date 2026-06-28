/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response body for X12 834 integration configuration endpoints.
 */
public record X834IntegrationConfigResponse(
        UUID integrationId,
        String senderID,
        String receiverID,
        String elementSeparator,
        String policyNumber,
        String memberIdQualifier,
        String memberIndicator,
        String maintenanceTypeCode,
        LocalDateTime enrollmentDate,
        LocalDateTime coverageStartDate,
        LocalDateTime coverageEndDate,
        String referenceIdentification,
        String masterPolicyNumber,
        String planSponsorName,
        String payerName,
        String payerIdentification,
        Instant createdAt
) {
}
