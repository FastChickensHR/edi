/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * Reserved for the fixed-width flat-file variant — a sibling of {@code flatfile.delimited} over the
 * same {@code core} seam and the shared {@link com.fastChickensHR.edi.flatfile.LinkedRows} nesting
 * convention, differing only in how a line is split into cells (column positions instead of a
 * delimiter). Designed-for, not built: it graduates when a real fixed-width feed appears. See edi #127.
 */
package com.fastChickensHR.edi.flatfile.fixedwidth;
