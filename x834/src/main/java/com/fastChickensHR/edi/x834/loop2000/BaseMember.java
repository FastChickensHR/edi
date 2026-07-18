/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.segments.Segment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Base domain class for both primary members and dependents.
 * <p>
 * This is a plain data object describing a person. It is intentionally
 * decoupled from any specific EDI transaction format; serialization to
 * the X12 834 wire format is the responsibility of a dedicated writer
 * (see {@link X834MemberWriter}).
 */
@Getter
@Setter
public abstract class BaseMember {
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
     * Loop 2300 (and other trailing) segments that belong to <em>this</em> member — most
     * notably health coverage (HD) and any custom REF extensions. They are emitted by
     * {@link X834MemberWriter} at the end of this member's own segment stream, so a member's
     * coverage stays nested inside its own loop rather than being batched after every member.
     */
    private final List<Segment> additionalSegments = new ArrayList<>();

    /**
     * Appends a trailing (Loop 2300) segment to this member — e.g. an HD coverage segment
     * or a custom REF extension. Order is preserved.
     *
     * @param segment the segment to emit within this member's loop
     */
    public void addSegment(Segment segment) {
        additionalSegments.add(segment);
    }

    /**
     * Validates this member has the minimum required fields.
     *
     * @throws ValidationException If validation fails
     */
    public abstract void validate() throws ValidationException;
}
