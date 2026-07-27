/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

/**
 * Test-only bridge to the package-private {@link Segment#setContext}, for tests that
 * live outside the {@code com.fastChickensHR.edi.x834} package (header/trailer) and
 * need to inject a rendering context before calling {@link Segment#render()}.
 */
public final class SegmentTestSupport {
    private SegmentTestSupport() {
    }

    /** Injects the rendering context into the given segment. */
    public static void setContext(Segment segment, X834Context context) {
        segment.setContext(context);
    }
}
