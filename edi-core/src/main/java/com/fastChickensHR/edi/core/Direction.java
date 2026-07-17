/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/** Whether a {@link PlannedFile} flows into fastChickens (parsed) or out to a vendor (emitted). */
public enum Direction {
    INBOUND,
    OUTBOUND
}
