/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.constants;

import lombok.Getter;

/**
 * Data element separator characters used between elements within an X12 segment.
 * {@code X834Context} defaults to {@link #ASTERISK} ('*'), the conventional 834 element separator.
 */
@Getter
public enum ElementSeparator {
    /** Asterisk '*' — the conventional X12 element separator (default). */
    ASTERISK('*'),
    /** Caret '^'. */
    CARET('^'),
    /** Pipe '|'. */
    PIPE('|');

    private final char value;

    ElementSeparator(char value) {
        this.value = value;
    }

}
