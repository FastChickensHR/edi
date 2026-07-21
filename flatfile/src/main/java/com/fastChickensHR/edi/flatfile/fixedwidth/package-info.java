/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * Reserved for the fixed-width flat-file variant — a sibling of {@code flatfile.delimited} over the
 * same {@code core} {@code FileParser}/{@code FileGenerator} seam, differing in how a line becomes
 * cells: slicing by column position instead of splitting on a delimiter. The two variants share only
 * that seam — no base class, and nothing below it (fixed-width is positional and headerless, so it has
 * no equivalent of the delimited variant's header-column conventions).
 *
 * <p>Designed-for, not built: it graduates when a real fixed-width feed + fixture appears. The v1 shape
 * is settled in edi #136 — a single-layout, flat variant with an edi-owned {@code FixedWidthFormat}
 * (explicit per-field width/alignment/pad; strict by default). See #136 and the closed family map #127.
 */
package com.fastChickensHR.edi.flatfile.fixedwidth;
