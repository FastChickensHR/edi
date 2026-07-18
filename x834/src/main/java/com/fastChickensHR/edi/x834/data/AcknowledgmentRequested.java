/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Code values for the Acknowledgment Requested field (ISA14, X12 data element I13) in the
 * Interchange Control Header (ISA) of an X12 834 Benefit Enrollment and Maintenance
 * interchange (005010X220A1). Indicates whether the sender requests a TA1 interchange
 * acknowledgment.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a
 * value from its code, enum name, description, or a common synonym, and throws
 * {@link IllegalArgumentException} when the input matches none.
 */
@Getter
public enum AcknowledgmentRequested implements EdiCodeEnum {
    NO_ACKNOWLEDGMENT("0", "No Interchange Acknowledgment Requested"),
    ACKNOWLEDGMENT_REQUESTED("1", "Interchange Acknowledgment Requested (TA1)");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<AcknowledgmentRequested> LOOKUP;

    static {
        // Include additional common terms users might enter
        LOOKUP = new EdiEnumLookup<>(
                AcknowledgmentRequested.class,
                "Acknowledgment Requested",
                Map.ofEntries(
                        Map.entry("0", NO_ACKNOWLEDGMENT),
                        Map.entry("no", NO_ACKNOWLEDGMENT),
                        Map.entry("none", NO_ACKNOWLEDGMENT),
                        Map.entry("not required", NO_ACKNOWLEDGMENT),
                        Map.entry("no acknowledgment", NO_ACKNOWLEDGMENT),
                        Map.entry("no ack", NO_ACKNOWLEDGMENT),

                        Map.entry("1", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("yes", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("required", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("requested", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("ack", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("ta1", ACKNOWLEDGMENT_REQUESTED),
                        Map.entry("acknowledgment", ACKNOWLEDGMENT_REQUESTED)
                )
        );
    }

    AcknowledgmentRequested(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets an AcknowledgmentRequested instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching AcknowledgmentRequested
     * @throws IllegalArgumentException if no match is found
     */
    public static AcknowledgmentRequested fromString(String input) {
        return LOOKUP.fromString(input);
    }

    /**
     * Returns the raw X12 code value for this constant (not the enum name), so the enum
     * renders directly into an EDI element.
     */
    @Override
    public String toString() {
        return code;
    }
}