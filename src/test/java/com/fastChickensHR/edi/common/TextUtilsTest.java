/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    /**
     * Class under test: TextUtils
     * Method under test: spaces(int count)
     * <p>
     * This method generates a string consisting of a specified number of spaces.
     * Throws IllegalArgumentException if the count is negative.
     */

    @Test
    void testSpacesWithValidCount() {
        int count = 5;
        String expected = "     "; // Five spaces
        String result = TextUtils.spaces(count);
        assertEquals(expected, result, "The method should return a string with the specified count of spaces.");
    }

    @Test
    void testSpacesWithZeroCount() {
        int count = 0;
        String expected = ""; // No spaces
        String result = TextUtils.spaces(count);
        assertEquals(expected, result, "The method should return an empty string when count is zero.");
    }

    @Test
    void testSpacesWithNegativeCount() {
        int count = -1;
        assertThrows(IllegalArgumentException.class, () -> TextUtils.spaces(count),
                "The method should throw IllegalArgumentException for negative count.");
    }

}