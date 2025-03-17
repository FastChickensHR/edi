/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.constants;

import lombok.Getter;

@Getter
public enum SubElementSeparator {
    COLON(':'),
    BACKSLASH('\\'),
    GREATER_THAN('>');

    private final char value;

    SubElementSeparator(char value) {
        this.value = value;
    }

}