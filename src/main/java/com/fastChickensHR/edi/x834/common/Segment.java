/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.common;

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
        for (String element : elements) {
            builder.append(context.getElementSeparator());
            if (element != null) {
                builder.append(element);
            }
        }

        builder.append(context.getSegmentTerminator());
        builder.append(context.getLineTerminator());

        return builder.toString();
    }
}