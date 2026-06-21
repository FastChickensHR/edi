/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.testdata;

import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;

import java.util.List;

/**
 * Holder for a generated subscriber {@link Member} together with the
 * {@link HealthCoverage} (HD) segments that apply to that employee/family.
 */
public record GeneratedEmployee(Member member, List<HealthCoverage> coverages) {
}
