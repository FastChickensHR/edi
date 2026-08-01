/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * Delimited flat files (CSV and its variants) over the core kernel. {@link
 * com.fastChickensHR.edi.flatfile.delimited.DelimitedFormat} pins the dialect (delimiter, quoting,
 * header row); {@link com.fastChickensHR.edi.flatfile.delimited.DelimitedFileGenerator} writes it
 * and {@link com.fastChickensHR.edi.flatfile.delimited.DelimitedFileParser} reads it back. Start at
 * {@link com.fastChickensHR.edi.flatfile.delimited.DelimitedFormat#csv()}.
 */
package com.fastChickensHR.edi.flatfile.delimited;
