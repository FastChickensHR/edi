/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834;

/**
 * The set of X12 834 EDI fields that can be populated via field mapping rules.
 *
 * <p>Each constant names an EDI field whose value varies per member and must be
 * derived from the HR system's payload at document generation time.
 * Fields with an associated {@code valueMappings} table are enum lookups;
 * date fields pass the source value through directly.</p>
 */
public enum X834MappableField {

    /** INS/HD segment: the type of enrollment change (e.g. ADDITION, CANCELLATION). */
    MAINTENANCE_TYPE_CODE,

    /** INS segment: whether the member is insured (INSURED or NOT_INSURED). */
    MEMBER_INDICATOR,

    /** DTP segment: the date the member enrolled. */
    ENROLLMENT_DATE,

    /** DTP segment: the date coverage becomes effective. */
    COVERAGE_START_DATE,

    /** DTP segment: the date coverage ends; absent for open-ended coverage. */
    COVERAGE_END_DATE
}
