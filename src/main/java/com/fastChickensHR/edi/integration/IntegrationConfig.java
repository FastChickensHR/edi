/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

import java.util.UUID;

/**
 * Marker interface for format-specific integration configuration objects.
 *
 * <p>Each EDI format (X12 834, X12 837, etc.) has its own implementation
 * carrying the configuration fields required to generate documents for that format.
 * All implementations are associated with a single {@link Integration} via
 * {@link #getIntegrationId()}.</p>
 */
public interface IntegrationConfig {
    UUID getIntegrationId();
}
