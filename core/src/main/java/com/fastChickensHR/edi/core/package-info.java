/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * The format-neutral file kernel: {@link com.fastChickensHR.edi.core.FileContent} is the pivot
 * every format speaks — an ordered tree of {@link com.fastChickensHR.edi.core.Record}s and {@link
 * com.fastChickensHR.edi.core.Field}s addressed by {@link com.fastChickensHR.edi.core.Location}.
 * Format modules implement {@link com.fastChickensHR.edi.core.FileGenerator} and {@link
 * com.fastChickensHR.edi.core.FileParser} to serialize and read it; start at {@link
 * com.fastChickensHR.edi.core.FileContent}.
 */
package com.fastChickensHR.edi.core;
