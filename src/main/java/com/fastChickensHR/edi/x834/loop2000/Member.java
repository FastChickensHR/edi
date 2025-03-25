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
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member (employee/participant/person) in an 834 file.
 * May be a primary member (subscriber) or dependent.
 */
@Getter
@Setter
public class Member extends BaseMember {
    private final List<DependentMember> dependents = new ArrayList<>();

    /**
     * Creates a new Member with the specified context
     *
     * @param context The 834 context to use for this member
     * @throws IllegalArgumentException if context is null
     */
    public Member(x834Context context) {
        super(context);
    }

    /**
     * Adds a dependent to this member
     *
     * @param dependent The dependent member
     */
    public void addDependent(DependentMember dependent) {
        dependents.add(dependent);
    }

    /**
     * Validates this member has the minimum required fields
     *
     * @throws ValidationException If validation fails
     */
    @Override
    public void validate() throws ValidationException {
        // Implementation omitted for shortness
    }

    /**
     * Generates all the segments for this member and its dependents
     *
     * @return List of segments in the correct order
     */
    @Override
    public List<Segment> generateSegments() throws ValidationException {
        // Implementation omitted for shortness
        return new ArrayList<>();
    }
}