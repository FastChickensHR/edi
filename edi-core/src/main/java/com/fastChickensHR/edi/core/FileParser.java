/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * The inbound seam: a format's parser — the dual of {@link FileEmitter}. Reads a file's text in a
 * format's dialect into a format-neutral {@link PlannedFile}. Defined now to keep the kernel
 * bidirectional; implemented when the inbound (CSV) convergence lands (ADR-0313, decision 8).
 */
public interface FileParser {
    PlannedFile parse(String raw);
}
