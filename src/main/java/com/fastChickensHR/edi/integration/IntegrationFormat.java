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
 * Represents the EDI transmission format used by an {@link Integration}.
 */
@Getter
public enum IntegrationFormat {
    X12_834("X12 834", "ANSI X12 834 Benefit Enrollment and Maintenance");

    private final String displayName;
    private final String description;

    IntegrationFormat(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
