/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

/**
 * Represents the direction of data flow for an {@link Integration}.
 * Direction is derived from the systems involved — it is not stored directly
 * on the integration.
 */
public enum Direction {
    OUTBOUND,
    INBOUND
}
