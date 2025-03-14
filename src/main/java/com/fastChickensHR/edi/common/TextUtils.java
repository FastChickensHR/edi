/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common;

import java.util.logging.Logger;

/**
 * Utility class providing functions for text formatting commonly used in EDI files,
 * particularly for handling fixed-width fields and padding requirements.
 */
public final class TextUtils {
    private static final Logger LOGGER = Logger.getLogger(TextUtils.class.getName());

    private TextUtils() {
        // Private constructor to prevent instantiation
        // This is a utility class with static methods only
    }

    /**
     * Creates a string of spaces with the specified length.
     *
     * @param count The number of spaces to generate
     * @return A string containing the specified number of spaces
     */
    public static String spaces(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Space count cannot be negative");
        }
        return " ".repeat(count);
    }

    /**
     * Pads a string to a fixed width with spaces on the right.
     * If the input is longer than the width, it will be truncated and a warning will be logged.
     *
     * @param input The string to pad
     * @param width The desired fixed width
     * @return A string padded to the specified width
     */
    public static String padRight(String input, int width) {
        if (input == null) {
            return spaces(width);
        }

        if (input.length() > width) {
            String substring = input.substring(0, width);
            LOGGER.warning("Input string '" + input + "' exceeds specified width of " + width +
                    ". String will be truncated to: '" + substring + "'");
            return substring;
        }

        return input + spaces(width - input.length());
    }

    /**
     * Pads a string to a fixed width with spaces on the left.
     * If the input is longer than the width, it will be truncated from the left and a warning will be logged.
     *
     * @param input The string to pad
     * @param width The desired fixed width
     * @return A string padded to the specified width
     */
    public static String padLeft(String input, int width) {
        if (input == null) {
            return spaces(width);
        }

        if (input.length() > width) {
            String substring = input.substring(input.length() - width);
            LOGGER.warning("Input string '" + input + "' exceeds specified width of " + width +
                    ". String will be truncated to: '" + substring + "'");
            return substring;
        }

        return spaces(width - input.length()) + input;
    }
}