/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * The outbound seam: a format's pure serializer. Given a fully-resolved {@link PlannedFile}, produce
 * the file's text in that format's dialect — interpreting each {@link Position} into its own tokens
 * (834: Position &rarr; loop/segment/element; CSV: Position &rarr; column). Implementations hold no
 * domain logic; all resolution happened upstream in the app's requirements engine. The kernel
 * <em>programs to</em> this interface; the format modules (edi-834, edi-csv) implement it.
 */
public interface FileEmitter {
    String emit(PlannedFile file);
}
