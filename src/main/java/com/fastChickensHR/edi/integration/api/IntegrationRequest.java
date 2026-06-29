/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import java.util.UUID;

/**
 * Request body for creating or replacing an integration.
 *
 * <p>{@code fromSystem} and {@code toSystem} are flat enum value names,
 * e.g. {@code "FASTCHICKENS_HR"} or {@code "STATE_OF_MICHIGAN"}.</p>
 */
public record IntegrationRequest(
        String name,
        UUID organizationId,
        UUID createdByUserId,
        String fromSystem,
        String toSystem,
        String format
) {
}
