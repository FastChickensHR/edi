/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

/**
 * Request body for creating or replacing an X12 834 integration configuration.
 *
 * <p>{@code elementSeparator} is a flat enum name, e.g. {@code "PIPE"}.</p>
 */
public record X834IntegrationConfigRequest(
        String senderID,
        String receiverID,
        String elementSeparator,
        String policyNumber,
        String memberIdQualifier,
        String referenceIdentification,
        String masterPolicyNumber,
        String planSponsorName,
        String payerName,
        String payerIdentification
) {
}
