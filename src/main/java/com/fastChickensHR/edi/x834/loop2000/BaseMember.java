/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Base class for both primary members and dependents.
 */
@Getter
@Setter
public abstract class BaseMember {
    protected final x834Context context;

    protected String memberId;
    protected String memberIdQualifier;
    protected String subscriberNumber;
    protected String policyNumber;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    protected LocalDateTime birthDate;
    protected String gender;
    protected MemberIndicator memberIndicator;
    protected MaintenanceTypeCode maintenanceTypeCode;
    protected LocalDateTime enrollmentDate;
    protected LocalDateTime coverageStartDate;
    protected LocalDateTime coverageEndDate;
    protected IndividualRelationshipCode relationshipCode;
    protected String addressLine1;
    protected String addressLine2;
    protected String city;
    protected String state;
    protected String zipCode;
    protected String phoneNumber;
    protected String email;

    /**
     * Constructor for BaseMember
     *
     * @param context The 834 context to use for this member
     * @throws IllegalArgumentException if context is null
     */
    protected BaseMember(x834Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
    }

    /**
     * Validates this member has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    public abstract void validate() throws ValidationException;

    /**
     * Generates all the segments for this member
     *
     * @return List of segments in the correct order
     */
    public abstract List<Segment> generateSegments() throws ValidationException;
}