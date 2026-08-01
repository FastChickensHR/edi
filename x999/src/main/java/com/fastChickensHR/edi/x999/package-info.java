/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * X12 999 (implementation acknowledgment) parsing: {@link
 * com.fastChickensHR.edi.x999.X999FileParser} reads the envelope and the AK1/IK5/AK9 verdict
 * segments into the core kernel's {@link com.fastChickensHR.edi.core.FileContent}.
 */
package com.fastChickensHR.edi.x999;
