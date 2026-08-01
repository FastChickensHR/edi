/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import java.util.List;

/**
 * The outcome of {@link ElementSpec#permits(java.util.Collection)}: whether a proposed set of codes
 * is a subset of what the standard permits at one position, and if not, exactly which codes are not
 * members.
 *
 * <p>The unknown codes are reported in the order they were proposed, so a caller narrowing a code
 * list (a trading-partner restriction, say) can point at the offending entries in the order it
 * holds them.
 */
public record SubsetResult(ElementPosition position, List<String> unknownCodes) {

  public SubsetResult {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null");
    }
    unknownCodes = List.copyOf(unknownCodes);
  }

  /** Whether every proposed code is permitted at {@link #position()}. */
  public boolean ok() {
    return unknownCodes.isEmpty();
  }

  /** A one-line explanation suitable for a failed check's message. */
  public String describe() {
    if (ok()) {
      return position.display() + " permits every proposed code";
    }
    return position.display() + " does not permit: " + String.join(", ", unknownCodes);
  }
}
