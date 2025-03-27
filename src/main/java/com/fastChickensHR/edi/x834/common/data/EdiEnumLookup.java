/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common.data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class that helps with unified enum lookups.
 * Provides a standard way to find enum values from any string input.
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
     */
    public EdiEnumLookup(Class<T> enumClass, String enumName, Map<String, T> additionalMappings) {
        this.enumClass = enumClass;
        this.enumName = enumName;

        // Build a comprehensive lookup map
        Map<String, T> map = new HashMap<>();

        // Add all enum constants with various lookup methods
        for (T constant : enumClass.getEnumConstants()) {
            // Map code to enum
            map.put(constant.getCode(), constant);

            // Map normalized name and description to enum
            map.put(normalizeText(constant.name()), constant);
            map.put(normalizeText(constant.getDescription()), constant);

            // Map individual words in name and description
            Arrays.stream(constant.name().split("_"))
                    .map(EdiEnumLookup::normalizeText)
                    .forEach(word -> map.put(word, constant));

            Arrays.stream(constant.getDescription().split("\\s+"))
                    .map(EdiEnumLookup::normalizeText)
                    .forEach(word -> map.put(word, constant));
        }

        // Add all additional mappings
        if (additionalMappings != null) {
            additionalMappings.forEach((key, value) ->
                    map.put(normalizeText(key), value));
        }

        this.lookupMap = Collections.unmodifiableMap(map);
    }

    /**
     * Creates an EdiEnumLookup with standard mappings only.
     */
    public EdiEnumLookup(Class<T> enumClass, String enumName) {
        this(enumClass, enumName, null);
    }

    /**
     * Attempts to find an enum value from any input string.
     * Tries to match against codes, enum names, descriptions and additional mappings.
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
        T result = lookupMap.get(normalized);

        if (result == null) {
            throw new IllegalArgumentException("Invalid " + enumName + " value: " + input);
        }
        return result;
    }

    /**
     * Normalizes text for comparison by trimming whitespace, converting to lowercase,
     * and removing special characters.
     */
    private static String normalizeText(String text) {
        return text
                .trim()
                .toLowerCase()
                .replace("_", "")
                .replace(" ", "")
                .replace("-", "");
    }
}