/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a dependent in a benefit enrollment.
 * Cannot have dependents of its own.
 * <p>
 * Pure domain object; X12 834 serialization is handled by
 * {@link X834MemberWriter}.
 */
@Setter
@Getter
public class DependentMember extends BaseMember {
    private Member primaryMember;

    public DependentMember() {
        // No-arg constructor; the X12 834 context is no longer a concern of the domain model.
    }

    @Override
    public void validate() throws ValidationException {
        if (relationshipCode == null) {
            throw new ValidationException("Dependent must have a relationship code");
        }
    }
}
