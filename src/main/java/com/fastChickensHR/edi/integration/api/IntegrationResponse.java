/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import java.time.Instant;
import java.util.UUID;

/**
 * Response body for integration endpoints.
 *
 * <p>{@code direction} is a read-only derived field computed from {@code fromSystem}.</p>
 */
public record IntegrationResponse(
        UUID integrationId,
        String name,
        UUID organizationId,
        UUID createdByUserId,
        String fromSystem,
        String toSystem,
        String format,
        String direction,
        Instant createdAt
) {
}
