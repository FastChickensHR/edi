/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member (employee/participant/person).
 * May be a primary member (subscriber) carrying zero or more dependents.
 * <p>
 * This is a pure domain object — it carries no knowledge of the X12 834
 * wire format. Translation to 834 segments is performed by
 * {@link X834MemberWriter}.
 */
@Getter
@Setter
public class Member extends BaseMember {
    private final List<DependentMember> dependents = new ArrayList<>();

    public Member() {
        // No-arg constructor; the X12 834 context is no longer a concern of the domain model.
    }

    /**
     * Adds a dependent to this member.
     *
     * @param dependent The dependent member
     */
    public void addDependent(DependentMember dependent) {
        dependents.add(dependent);
    }

    /**
     * Validates this member has the minimum required fields: the INS-segment trio
     * (member indicator, relationship code, maintenance type code) that
     * {@link X834MemberWriter} dereferences unconditionally, plus each dependent's
     * own validation.
     *
     * @throws ValidationException If validation fails
     */
    @Override
    public void validate() throws ValidationException {
        if (memberIndicator == null) {
            throw new ValidationException("Member must have a member indicator (INS01)");
        }
        if (relationshipCode == null) {
            throw new ValidationException("Member must have a relationship code (INS02)");
        }
        if (maintenanceTypeCode == null) {
            throw new ValidationException("Member must have a maintenance type code (INS03)");
        }
        for (DependentMember dependent : dependents) {
            dependent.validate();
        }
    }
}
