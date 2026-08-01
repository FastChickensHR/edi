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
  /**
   * Reads this format's text into the format-neutral tree.
   *
   * @param raw the file's complete text in this format's dialect
   * @return the parsed content, direction {@link Direction#INBOUND}
   */
  FileContent parse(String raw);
}
