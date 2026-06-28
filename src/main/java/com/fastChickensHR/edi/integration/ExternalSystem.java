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
 * Represents external trading partners that send or receive EDI data from
 * FastChickensHR. When an {@link Integration} has an {@code ExternalSystem}
 * as its {@code fromSystem}, the direction is {@link Direction#INBOUND}.
 */
@Getter
public enum ExternalSystem implements Partner {
    STATE_OF_MICHIGAN("State of Michigan");

    private final String displayName;

    ExternalSystem(String displayName) {
        this.displayName = displayName;
    }
}
