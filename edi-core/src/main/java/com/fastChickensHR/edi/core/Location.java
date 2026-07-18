/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * A spec-anchored token address on a File. The format spec owns the <b>structural</b> tokens (segment
 * identifier, delimiters) and they are never overridable; {@code name} carries only the
 * <b>configurable</b> token(s) — an 834 REF qualifier, a CSV column — interpreted by the format's own
 * dialect below the kernel. {@code level} is the tree depth the address sits at.
 */
public record Location(RecordLevel level, String name) {
    public Location {
        if (level == null) {
            throw new IllegalArgumentException("level is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
