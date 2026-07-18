/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * The inbound seam: a format's parser — the dual of {@link FileGenerator}. Reads a file's text in a
 * format's dialect into a format-neutral {@link FileContent}, keeping the kernel bidirectional.
 */
public interface FileParser {
    FileContent parse(String raw);
}
