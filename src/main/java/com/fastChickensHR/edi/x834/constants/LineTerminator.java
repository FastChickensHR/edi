/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.constants;

public enum LineTerminator {
    LF("\n"),
    CRLF("\r\n"),
    NONE("");

    private final String value;

    LineTerminator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
