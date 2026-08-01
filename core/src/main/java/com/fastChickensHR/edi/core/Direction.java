/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.core;

/**
 * Whether a {@link FileContent} flows into the consuming application (parsed) or out to a trading
 * partner (emitted).
 */
public enum Direction {
  /** Parsed from a trading partner's file into the consuming application. */
  INBOUND,
  /** Emitted by the consuming application toward a trading partner. */
  OUTBOUND
}
