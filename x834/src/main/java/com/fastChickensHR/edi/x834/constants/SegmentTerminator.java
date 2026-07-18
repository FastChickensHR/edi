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
public enum SegmentTerminator {
    TILDE('~'),
    LINE_FEED('\n'),
    CARRIAGE_RETURN('\r'),
    EXCLAMATION_POINT('!');

    private final char value;

    SegmentTerminator(char value) {
        this.value = value;
    }

}
