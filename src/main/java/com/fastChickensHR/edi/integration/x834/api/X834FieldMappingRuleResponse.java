/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Response body for field mapping rule endpoints.
 */
public record X834FieldMappingRuleResponse(
        UUID integrationId,
        String sourceField,
        String targetEdiField,
        Map<String, String> valueMappings,
        Instant createdAt
) {
}
