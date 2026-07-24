/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import java.util.Objects;

/**
 * A single, structured reason an {@link X834Document} could not be generated.
 *
 * <p>Every failure carries a {@link Phase} (was the structure invalid before serialization, or did
 * a value fail to serialize), a free-form {@code location} label naming where it arose (a component
 * like {@code "Header"} or {@code "Member[3]"}, or a segment identifier like {@code "HD"}), and a
 * human-readable {@code message}. Consumers enumerate these off a {@link GenerationResult.Failure}
 * rather than parsing a flattened exception string.
 */
public record GenerationError(Phase phase, String location, String message) {

    public GenerationError {
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(location, "location");
        Objects.requireNonNull(message, "message");
    }

    /** The generation stage a {@link GenerationError} arose in. */
    public enum Phase {
        /** The document's structure or configuration was invalid before serialization was attempted. */
        BUILD,
        /** A value could not be serialized into a conformant X12 segment. */
        RENDER
    }

    /** A single-line rendering — {@code PHASE | location | message} — for log and exception surfaces. */
    public String formatted() {
        return phase + " | " + location + " | " + message;
    }
}
