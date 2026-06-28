/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.converters;

import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Enrollment-specific configuration applied when converting domain objects to
 * X12 834 member records.
 * <p>
 * Domain objects (Person, Dependent) carry demographic data but deliberately
 * know nothing about enrollment rules. This object captures the plan-level
 * configuration — policy numbers, dates, qualifier codes — that belongs to the
 * enrollment, not the person.
 */
@Value
@Builder
public class EnrollmentContext {
    /** HD/INS policy number. Required. */
    String policyNumber;

    /**
     * REF qualifier for the member ID (e.g. {@code "34"} = SSN,
     * {@code "0F"} = subscriber number). Defaults to {@code "34"}.
     */
    @Builder.Default
    String memberIdQualifier = "34";

    /**
     * Subscriber number override. When {@code null}, the converter derives the
     * number from the primary member's employeeId.
     */
    String subscriberNumber;

    /** Defaults to {@link MemberIndicator#INSURED}. */
    @Builder.Default
    MemberIndicator memberIndicator = MemberIndicator.INSURED;

    /** Defaults to {@link MaintenanceTypeCode#ADDITION}. */
    @Builder.Default
    MaintenanceTypeCode maintenanceTypeCode = MaintenanceTypeCode.ADDITION;

    /** Date the member was enrolled. Required. */
    LocalDateTime enrollmentDate;

    /** Date coverage begins. Required. */
    LocalDateTime coverageStartDate;

    /** Date coverage ends. {@code null} = open-ended. */
    LocalDateTime coverageEndDate;
}
