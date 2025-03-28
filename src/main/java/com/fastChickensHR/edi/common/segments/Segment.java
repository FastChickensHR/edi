/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Setter;

/**
 * Base class for all EDI segments.
 * Now context-aware to reduce direct dependency on X834Document.
 */
@Setter
public abstract class Segment {
    protected x834Context context;

    /**
     * @return The x834Context for this segment
     */
    protected x834Context getContext() {
        return context;
    }

    /**
     * @return The segment identifier (e.g., "ST", "GS")
     */
    public abstract String getSegmentIdentifier();

    /**
     * @return Array of element values for this segment
     */
    public abstract String[] getElementValues();

    /**
     * Renders the segment as a properly formatted EDI segment string
     *
     * @return The formatted segment string
     */
    public String render() {
        if (context == null) {
            throw new IllegalStateException("Context must be set before rendering segment");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(getSegmentIdentifier());

        String[] elements = getElementValues();

        if (elements == null || elements.length == 0) {
            throw new IllegalStateException("Element values array cannot be null or empty");
        }

        // Find the last non-null value's index
        int lastNonNullIndex = -1;
        for (int i = elements.length - 1; i >= 0; i--) {
            if (elements[i] != null) {
                lastNonNullIndex = i;
                break;
            }
        }

        // Render only up to the last non-null element
        for (int i = 0; i <= lastNonNullIndex; i++) {
            builder.append(context.getElementSeparator());
            if (elements[i] != null) {
                builder.append(elements[i]);
            }
        }

        builder.append(context.getSegmentTerminator());
        builder.append(context.getLineTerminator());

        return builder.toString();
    }
}