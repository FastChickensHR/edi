/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity.api;

import java.time.Instant;
import java.util.UUID;

public record OrganizationResponse(
        UUID organizationId,
        String name,
        Instant createdAt
) {
}
