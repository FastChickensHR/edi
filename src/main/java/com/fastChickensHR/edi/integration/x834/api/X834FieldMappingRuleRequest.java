/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import java.util.Map;

/**
 * Request body for creating or replacing a field mapping rule.
 *
 * <p>{@code targetEdiField} must be a valid {@link com.fastChickensHR.edi.integration.x834.X834MappableField} name.
 * {@code valueMappings} maps HR system values to EDI enum names; use an empty map for date pass-through rules.</p>
 */
public record X834FieldMappingRuleRequest(
        String sourceField,
        String targetEdiField,
        Map<String, String> valueMappings
) {
}
