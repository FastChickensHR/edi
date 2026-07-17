/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

import java.util.Optional;

/**
 * One resolved Value at a {@link Position}. A {@code null} value means <b>OMIT</b> — the position is
 * described, but nothing is emitted for this subject (e.g. a situational REF with no value).
 */
public record Placement(Position position, String value) {
    public Placement {
        if (position == null) {
            throw new IllegalArgumentException("position is required");
        }
    }

    /** {@code true} when this placement emits nothing (OMIT). */
    public boolean isOmitted() {
        return value == null;
    }

    public Optional<String> valueIfPresent() {
        return Optional.ofNullable(value);
    }
}
