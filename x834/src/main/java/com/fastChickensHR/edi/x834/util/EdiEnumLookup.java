/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that helps with unified enum lookups. Provides a standard way to find enum values
 * from any string input.
 *
 * <p>Normalization is lossy by design (lowercase, {@code _}/space/{@code -} stripped), so
 * near-identical strings collide. Keys are therefore registered in tiers, upholding two invariants:
 * <b>every constant is reachable by its own name</b>, and <b>every code resolves to a constant that
 * actually carries that code</b>. A convenience key can never displace either.
 *
 * <ol>
 *   <li><b>Enum names are authoritative</b> — registered first. Two constants whose names normalize
 *       alike would leave one permanently unreachable, so that throws at construction.
 *   <li><b>Codes are authoritative but may be shared</b> — several constants legitimately carry one
 *       X12 code (an enum that names member-level synonyms over a shared code list, say, where the
 *       wire output is identical either way). The first in declaration order wins. A code that
 *       would instead resolve to a constant carrying a <em>different</em> code is real ambiguity,
 *       and throws.
 *   <li><b>Descriptions are best-effort</b> — registered only when the key is still free. Two
 *       constants may share one once normalized, since X12 code lists are transcribed verbatim
 *       ("Long-term Care" vs "Long Term Care"); the loser stays reachable by name and code.
 *   <li><b>Additional mappings (aliases) are best-effort</b> — likewise free-keys-only, so an alias
 *       can never displace a constant's own name, code or description.
 * </ol>
 */
public final class EdiEnumLookup<T extends Enum<T> & EdiCodeEnum> {

  private final Class<T> enumClass;
  private final String enumName;
  private final Map<String, T> lookupMap;

  /**
   * Creates an EdiEnumLookup for the specified enum class with standard and additional mappings.
   *
   * @param enumClass the Class object of the enum type
   * @param enumName the name of the enum type (for error messages)
   * @param additionalMappings optional additional text mappings to enum constants
   * @throws IllegalArgumentException if two constants would claim the same normalized name, or if a
   *     code would resolve to a constant carrying a different code
   */
  public EdiEnumLookup(Class<T> enumClass, String enumName, Map<String, T> additionalMappings) {
    this.enumClass = enumClass;
    this.enumName = enumName;

    Map<String, T> map = new HashMap<>();

    for (T constant : enumClass.getEnumConstants()) {
      String key = normalizeText(constant.name());
      T existing = map.putIfAbsent(key, constant);
      if (existing != null) {
        throw new IllegalArgumentException(
            shadowed(constant, "name", constant.name(), key, existing));
      }
    }

    for (T constant : enumClass.getEnumConstants()) {
      String key = normalizeText(constant.getCode());
      T existing = map.putIfAbsent(key, constant);
      if (existing != null && !existing.getCode().equals(constant.getCode())) {
        throw new IllegalArgumentException(
            shadowed(constant, "code", constant.getCode(), key, existing));
      }
    }

    for (T constant : enumClass.getEnumConstants()) {
      map.putIfAbsent(normalizeText(constant.getDescription()), constant);
    }

    if (additionalMappings != null) {
      additionalMappings.forEach((key, value) -> map.putIfAbsent(normalizeText(key), value));
    }

    this.lookupMap = Collections.unmodifiableMap(map);
  }

  private String shadowed(T constant, String kind, String raw, String normalized, T existing) {
    return enumName
        + ": "
        + constant.name()
        + " "
        + kind
        + " '"
        + raw
        + "' normalizes to '"
        + normalized
        + "', already claimed by "
        + existing.name()
        + " — one of them would be unreachable";
  }

  /** Creates an EdiEnumLookup with standard mappings only. */
  public EdiEnumLookup(Class<T> enumClass, String enumName) {
    this(enumClass, enumName, null);
  }

  /**
   * Attempts to find an enum value from any input string. Tries to match against codes, enum names,
   * descriptions and additional mappings.
   *
   * @param input the string to look up
   * @return the matching enum value
   * @throws IllegalArgumentException if no match is found
   */
  public T fromString(String input) {
    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty");
    }

    String normalized = normalizeText(input);
    T result = this.lookupMap.get(normalized);

    if (result == null) {
      throw new IllegalArgumentException("Invalid " + enumName + " value: " + input);
    }
    return result;
  }

  /**
   * Normalizes text for comparison by trimming whitespace, converting to lowercase, and removing
   * special characters.
   */
  private static String normalizeText(String text) {
    return text.trim().toLowerCase().replace("_", "").replace(" ", "").replace("-", "");
  }
}
