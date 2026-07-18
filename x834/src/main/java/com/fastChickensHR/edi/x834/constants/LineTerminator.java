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
 * Optional line terminator appended after each rendered segment for human readability.
 * This is cosmetic and distinct from the X12 {@link SegmentTerminator}. {@code X834Context}
 * defaults to {@link #LF}.
 */
@Getter
public enum LineTerminator {
    /** Line feed "\n" (default). */
    LF("\n"),
    /** Carriage return + line feed "\r\n". */
    CRLF("\r\n"),
    /** No line terminator (segments run together). */
    NONE("");

    private final String value;

    LineTerminator(String value) {
        this.value = value;
    }

}
