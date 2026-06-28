/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

import lombok.Getter;

/**
 * Represents internal systems that originate or receive EDI data within
 * FastChickensHR. When an {@link Integration} has an {@code InternalSystem}
 * as its {@code fromSystem}, the direction is {@link Direction#OUTBOUND}.
 */
@Getter
public enum InternalSystem implements Partner {
    FASTCHICKENS_HR("FastChickensHR");

    private final String displayName;

    InternalSystem(String displayName) {
        this.displayName = displayName;
    }
}
