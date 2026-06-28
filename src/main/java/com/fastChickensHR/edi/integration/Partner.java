/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

/**
 * Marker interface for any system that can participate in an EDI {@link Integration}
 * as either a sender ({@code fromSystem}) or receiver ({@code toSystem}).
 *
 * <p>Implemented by {@link InternalSystem} and {@link ExternalSystem}.</p>
 */
public interface Partner {
    String getDisplayName();
}
