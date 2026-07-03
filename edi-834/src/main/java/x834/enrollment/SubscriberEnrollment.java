/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.enrollment;

import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;

import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Aggregate representing the enrollment data required to build an 834 file
 * for a single subscriber: the subscriber {@link Member} together with the
 * {@link HealthCoverage} (HD) segments that apply to that employee/family.
 *
 * <p>This is the canonical input contract passed into the x834 builder.
 */
@Value
@Accessors(fluent = true)
public class SubscriberEnrollment {
    Member member;
    List<HealthCoverage> coverages;
}
