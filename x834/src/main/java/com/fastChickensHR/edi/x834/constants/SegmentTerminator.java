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
 * Segment terminator character marking the end of each X12 segment.
 * {@code X834Context} defaults to {@link #TILDE} ('~'), the conventional 834 segment terminator.
 */
@Getter
public enum SegmentTerminator {
    /** Tilde '~' — the conventional X12 segment terminator (default). */
    TILDE('~'),
    /** Line feed '\n'. */
    LINE_FEED('\n'),
    /** Carriage return '\r'. */
    CARRIAGE_RETURN('\r'),
    /** Exclamation point '!'. */
    EXCLAMATION_POINT('!');

    private final char value;

    SegmentTerminator(char value) {
        this.value = value;
    }

}
