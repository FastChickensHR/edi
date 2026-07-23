/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2700;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * A single member reporting category — one occurrence of Loop 2710/2750 in the
 * X12 834 (005010X220A1).
 * <p>
 * A reporting category is a name/value pair the carrier collects against a
 * member: the {@link #name} labels what is being reported (carried in the 2750
 * {@code N1*75} segment) and the {@link #value} is what is reported for it
 * (carried in the following {@code REF} segment under {@link #referenceQualifier},
 * which defaults to {@code ZZ}, Mutually Defined). An occurrence may also carry a
 * {@link #date} (emitted as a {@code DTP} segment) when {@link #dateQualifier} is
 * set to state what the date means.
 * <p>
 * This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class ReportingCategory {
    /** The category label (2750 {@code N1*75} N102) — required. */
    private String name;
    /** The REF qualifier for the value (2750 {@code REF} REF01); defaults to {@code ZZ}. */
    private String referenceQualifier = "ZZ";
    /** The reported value (2750 {@code REF} REF02) — required. */
    private String value;
    /** An optional category date (2750 {@code DTP} DTP03). */
    private LocalDateTime date;
    /** The qualifier stating what {@link #date} means (2750 {@code DTP} DTP01); required when a date is present. */
    private String dateQualifier;

    public ReportingCategory() {
    }

    /**
     * @param name  the category label (2750 {@code N1*75})
     * @param value the reported value (2750 {@code REF})
     */
    public ReportingCategory(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
