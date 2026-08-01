/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * The outbound seam: a format's pure serializer. Given a fully-resolved {@link FileContent},
 * produce the file's text in that format's dialect — interpreting each {@link Location} into its
 * own tokens (834: Location &rarr; loop/segment/element; delimited: Location &rarr; column).
 * Implementations hold no domain logic; all resolution happened upstream in the consuming
 * application. The kernel <em>programs to</em> this interface; the format modules (x834, flatfile)
 * implement it.
 */
public interface FileGenerator {
  /**
   * Serializes the fully-resolved content into this format's text.
   *
   * @param file the format-neutral content to serialize
   * @return the file's complete text in this format's dialect
   */
  String generate(FileContent file);
}
