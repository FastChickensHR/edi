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
 * Sub-element (component) separator character used between components within a composite
 * data element. {@code X834Context} defaults to {@link #GREATER_THAN} ('>').
 */
@Getter
public enum SubElementSeparator {
    /** Colon ':'. */
    COLON(':'),
    /** Backslash '\'. */
    BACKSLASH('\\'),
    /** Greater-than '&gt;' (default). */
    GREATER_THAN('>');

    private final char value;

    SubElementSeparator(char value) {
        this.value = value;
    }

}