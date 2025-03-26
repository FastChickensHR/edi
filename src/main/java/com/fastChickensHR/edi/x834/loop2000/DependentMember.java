/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.segments.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dependent in an 834 file.
 * Cannot have dependents of its own.
 */
@Setter
@Getter
public class DependentMember extends BaseMember {
    private Member primaryMember;

    /**
     * Creates a new DependentMember with the specified context
     *
     * @param context The 834 context to use for this dependent member
     * @throws IllegalArgumentException if context is null
     */
    public DependentMember(x834Context context) {
        super(context);
    }

    @Override
    public void validate() throws ValidationException {
        if (relationshipCode == null) {
            throw new ValidationException("Dependent must have a relationship code");
        }
    }

    @Override
    public List<Segment> generateSegments() throws ValidationException {

        // Generate dependent-specific segments
        // This might be similar to Member's segment generation but with
        // dependent-specific segment identifiers

        return new ArrayList<>();
    }
}