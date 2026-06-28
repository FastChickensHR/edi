/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Represents a configured EDI integration between two systems.
 *
 * <p>An integration is an implementation of an EDI method — it captures who owns
 * the integration, which systems are involved, and which format is used to
 * transmit healthcare enrollment data.</p>
 *
 * <p>{@link Direction} is derived from the systems: if {@code fromSystem} is an
 * {@link InternalSystem}, the integration is {@link Direction#OUTBOUND}; if
 * {@code fromSystem} is an {@link ExternalSystem}, it is {@link Direction#INBOUND}.</p>
 */
@Value
@Builder
public class Integration {
    UUID id;
    String name;
    UserId owner;
    Partner fromSystem;
    Partner toSystem;
    IntegrationFormat format;

    /**
     * Derives the direction of data flow from the systems involved.
     *
     * @return {@link Direction#OUTBOUND} when {@code fromSystem} is an {@link InternalSystem},
     *         {@link Direction#INBOUND} when {@code fromSystem} is an {@link ExternalSystem}.
     * @throws IllegalStateException if {@code fromSystem} is null or an unrecognized type
     */
    public Direction direction() {
        if (fromSystem instanceof InternalSystem) {
            return Direction.OUTBOUND;
        } else if (fromSystem instanceof ExternalSystem) {
            return Direction.INBOUND;
        }
        throw new IllegalStateException("fromSystem must be an InternalSystem or ExternalSystem");
    }
}
