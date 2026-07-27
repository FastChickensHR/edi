/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.segments.Segment;

/**
 * The HD (Health Coverage) segment in the X12 834 (005010X220A1) Benefit Enrollment and
 * Maintenance transaction (Loop 2300), built by {@link X834MemberWriter} from a member's
 * {@link com.fastChickensHR.edi.x834.loop2000.loop2300.HealthCoverage} value objects.
 * <p>
 * In the 220A1 TR3 the HD segment carries only four data elements — HD01, HD03, HD04, HD05.
 * <strong>HD02 is Not Used</strong> (maintenance reason rides on INS04, not here), and
 * <strong>HD06 and everything beyond is Not Used</strong> — the medicare/COBRA/employment/student/
 * handicap attributes that a base X12 HD once carried live on the INS segment (Loop 2000) in this
 * transaction, where the library already models them ({@code INSSegment.ins06}–{@code ins10}).
 * HD02 is still a real element position, so it is emitted as an empty slot
 * ({@code HD*001**HLT*…}) to keep HD03+ in their correct positions; it is simply never populated.
 * <p>
 * Element/position map:
 * <ul>
 *     <li>HD01 = maintenance type code (required)</li>
 *     <li>HD02 = <em>Not Used</em> (structurally empty)</li>
 *     <li>HD03 = insurance line code (required)</li>
 *     <li>HD04 = plan coverage description (situational)</li>
 *     <li>HD05 = coverage level code (situational)</li>
 *     <li>HD06+ = <em>Not Used</em> — never emitted</li>
 * </ul>
 */
class HealthCoverageSegment extends Segment {
    static final String SEGMENT_ID = "HD";

    private final String hd01;
    private final String hd03;
    private final String hd04;
    private final String hd05;

    HealthCoverageSegment(String hd01, String hd03, String hd04, String hd05)
            throws ValidationException {
        this.hd01 = hd01;
        this.hd03 = hd03;
        this.hd04 = hd04;
        this.hd05 = hd05;

        if (hd01 == null || hd01.trim().isEmpty()) {
            throw new ValidationException("Maintenance Type Code (HD01) is required for Health Coverage");
        }
        if (hd03 == null || hd03.trim().isEmpty()) {
            throw new ValidationException("Insurance Line Code (HD03) is required for Health Coverage");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        // HD02 is Not Used in 220A1 but is a real element position, so it renders as an
        // empty slot to keep HD03/HD04/HD05 in their correct positions.
        return new String[]{hd01, null, hd03, hd04, hd05};
    }
}
