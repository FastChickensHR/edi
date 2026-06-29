/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common;

import java.time.Instant;

/**
 * Shared temporal sentinel constants used across persistence and EDI layers.
 */
public final class TemporalConstants {

    private TemporalConstants() {}

    /**
     * Sentinel value representing an open-ended temporal boundary ("forever").
     * Stored as {@code '9999-12-31 23:59:59+00'} in PostgreSQL {@code TIMESTAMPTZ} columns.
     */
    public static final Instant TEMPORAL_INFINITY = Instant.parse("9999-12-31T23:59:59Z");
}
