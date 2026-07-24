/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import java.util.List;

/**
 * The outcome of {@link X834Document#generateDocument()}: <em>either</em> the finished X12 834
 * <em>or</em> the complete list of reasons it could not be produced — never both, never neither.
 *
 * <p>This is the single failure-reporting channel for generation. Rather than throw on the first
 * problem, generation accumulates every problem it can (all build-time checks, every member's
 * assembly, every delimiter-unsafe value) into one {@link Failure}, so the source can be fixed in a
 * single round-trip instead of one failed generation at a time.
 */
public sealed interface GenerationResult {

    /** Generation succeeded; {@link #document()} is the complete X12 834 string. */
    record Success(String document) implements GenerationResult {}

    /** Generation failed; {@link #errors()} lists every reason, accumulated in one pass. */
    record Failure(List<GenerationError> errors) implements GenerationResult {
        public Failure {
            errors = List.copyOf(errors);
        }
    }
}
