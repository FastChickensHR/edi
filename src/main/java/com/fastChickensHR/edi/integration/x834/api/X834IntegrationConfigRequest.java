/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import java.time.LocalDateTime;

/**
 * Request body for creating or replacing an X12 834 integration configuration.
 *
 * <p>{@code elementSeparator}, {@code memberIndicator}, and {@code maintenanceTypeCode}
 * are flat enum names, e.g. {@code "PIPE"}, {@code "INSURED"}, {@code "ADDITION"}.</p>
 */
public record X834IntegrationConfigRequest(
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
        String payerIdentification
) {
}
